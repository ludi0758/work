package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.User;
import cn.tedu.store.mapper.UserMapper;
import cn.tedu.store.service.UserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.PasswordNotMatchException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.service.ex.UserNotFoundException;
import cn.tedu.store.service.ex.UsernameDuplicateException;

/**
 * 处理用户数据的业务层实现类
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public void reg(User user) {
		// 日志
		System.err.println("UserServiceImpl.reg()");
		// 通过参数user获取尝试注册的用户名
		String username = user.getUsername();
		// 调用userMapper.findByUsername()方法执行查询
		User result = userMapper.findByUsername(username);
		// 判断查询结果是否不为null
		if (result != null) {
			// 是：查询到了数据，表示用户名已经被占用，则抛出UsernameDuplicationException
			throw new UsernameDuplicateException("注册失败，用户名已经被占用！");
		}

		// 如果代码能执行到这一行，则表示没有查到数据，表示用户名未被注册，则允许注册
		// 创建当前时间对象：
		Date now = new Date();
		// 向参数user中补全数据：salt, password，涉及加密处理，暂不处理
		String salt = UUID.randomUUID().toString();
		user.setSalt(salt);
		String md5Password = getMd5Password(user.getPassword(), salt);
		user.setPassword(md5Password);
		// 向参数user中补全数据：is_delete(0)
		user.setIsDelete(0);
		// 向参数user中补全数据：4项日志(now, user.getUsername())
		user.setCreatedUser(username);
		user.setCreatedTime(now);
		user.setModifiedUser(username);
		user.setModifiedTime(now);
		// 调用userMapper.insert()执行插入数据，并获取返回的受影响行数
		Integer rows = userMapper.insert(user);
		// 判断受影响的行数是否不为1
		if (rows != 1) {
			// 是：插入数据失败，则抛出InsertException
			throw new InsertException("注册失败，保存注册数据时出现未知错误，请联系系统管理员！");
		}
	}

	@Override
	public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
		// 日志
		System.err.println("UserServiceImpl.changePassword()");
		// 调用userMapper.findByUid()查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果(result)是否为null
		if (result == null) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败，尝试访问的用户数据不存在！");
		}

		// 判断查询结果(result)中的isDelete属性是否为1
		if (result.getIsDelete() == 1) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改密码失败，用户数据已被删除！");
		}

		// 日志
		System.err.println("\t验证原密码：");
		System.err.println("\t数据库中的原密码：" + result.getPassword());
		// 从查询结果(result)中取出盐值(salt)
		String salt = result.getSalt();
		// 基于参数oldPassword和盐值执行加密
		String oldMd5Password = getMd5Password(oldPassword, salt);
		// 判断以上加密结果与查询结果(result)中的密码是否不匹配
		if (!oldMd5Password.equals(result.getPassword())) {
			// 是：抛出PasswordNotMatchException
			throw new PasswordNotMatchException("修改密码失败，原密码错误！");
		}

		// 日志
		System.err.println("\t验证通过，更新密码：");
		// 基于参数newPassword和盐值执行加密
		String newMd5Password = getMd5Password(newPassword, salt);
		// 调用userMapper.updatePasswordByUid()执行更新密码（最后修改人是参数username），并获取返回值
		Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
		// 判断返回结果是否不为1
		if (rows != 1) {
			// 是：抛出UpdateException
			throw new UpdateException("修改密码失败，更新密码时出现未知错误，请联系系统管理员！");
		}
	}

	@Override
	public void changeAvatar(Integer uid, String username, String avatar) {
		// 日志
		System.err.println("UserServiceImpl.changeAvatar()");
		// 调用userMapper.findByUid()查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果(result)是否为null
		if (result == null) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户头像失败，尝试访问的用户数据不存在！");
		}

		// 判断查询结果(result)中的isDelete属性是否为1
		if (result.getIsDelete() == 1) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户头像失败，用户数据已被删除！");
		}
		
		Date modifiedTime = new Date();
		//调用userMapper.updateInfoByUid()更新,并获取返回值
		Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, modifiedTime);
		//判断返回值是否不为1
		if(rows != 1){
			//是:抛出UpdateException
			throw new UpdateException("修改用户头像失败,更新用户头像时出现未知错误,请联系系统管理员");
		}
	}
	
	@Override
	public void changeInfo(Integer uid, String username, User user) {
		// 日志
		System.err.println("UserServiceImpl.changeInfo()");
		// 调用userMapper.findByUid()查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果(result)是否为null
		if (result == null) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户资料失败，尝试访问的用户数据不存在！");
		}

		// 判断查询结果(result)中的isDelete属性是否为1
		if (result.getIsDelete() == 1) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("修改用户资料失败，用户数据已被删除！");
		}

		//像参数user中补充数据:uid>参数uid
		user.setUid(uid);
		//向参数user中u补充数据:modifiedUser>参数username
		user.setModifiedUser(username);
		//向参数user中u补充数据:modifiedTime>参数new Date()
		user.setModifiedTime(new Date());
		//调用userMapper.updateInfoByUid()更新,并获取返回值
		Integer rows = userMapper.updateInfoByUid(user);
		//判断返回值是否不为1
		if(rows != 1){
			//是:抛出UpdateException
			throw new UpdateException("修改用户资料失败,更新用户资料时出现未知错误,请联系系统管理员");
		}
	}

	@Override
	public User login(String username, String password) {
		// 基于参数username调用userMapper,findByUsername()查询用户数据
		User result = userMapper.findByUsername(username);
		// 判断查询结果(result)是否为null
		if (result == null) {
			// 是:抛出UserNotFoundException
			throw new UserNotFoundException("登录失败，用户名不存在！");
		}
		// 判断查询结果(result)中的isDelete是否为1
		if (result.getIsDelete() == 1) {
			// 是:抛出UserNotFoundException
			throw new UserNotFoundException("登陆失败,用户名数据已经被删除");
		}

		// 查询结果(result)中获取盐值
		String salt = result.getSalt();
		// 基于参数password和盐值,调用getMd5Password()执行加密
		String md5Password = getMd5Password(password, salt);
		System.err.println(result.getPassword());
		// 判断查询结果(result)中的密码和以上加密结果是否不一样
		if (!md5Password.equals(result.getPassword())) {
			// 是:抛出PasswordNotMatchException
			throw new PasswordNotMatchException("登陆失败,密码错误");
		}

		// 创建新的User对象
		User user = new User();
		// 将查询结果中的uid,username,avatat设置和到新的User对象的对应属性中
		user.setUid(result.getUid());
		user.setAvatar(result.getAvatar());
		user.setUsername(result.getUsername());
		// 返回新创建的User对象
		return user;
	}

	public User getInfo(Integer uid) {
		//日志
		System.err.println("UserServiceImpl.getInfo()");
		// 调用userMapper.findByUid()查询用户数据
		User result = userMapper.findByUid(uid);
		// 判断查询结果(result)是否为null
		if (result == null) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("获取用户数据失败，尝试访问的用户数据不存在！");
		}

		// 判断查询结果(result)中的isDelete属性是否为1
		if (result.getIsDelete() == 1) {
			// 是：抛出UserNotFoundException
			throw new UserNotFoundException("获取用户数据失败，用户数据已被删除！");
		}

		// 创建新的User对象
		User user = new User();
		// 通过查询结果向新的user对象中封装属性:username,phone,email,gender
		user.setUsername(result.getUsername());
		user.setGender(result.getGender());
		user.setPhone(result.getPhone());
		user.setEmail(result.getEmail());
		//返回新的user对象
		return user;
	}


	/**
	 * 执行密码加密，获取加密后的结果
	 * 
	 * @param password 原始密码
	 * @param salt 盐值
	 * @return 加密后的结果
	 */
	private String getMd5Password(String password, String salt) {
		// 加密标准：使用salt+password+salt作为被运算数据，循环加密3次
		String result = salt + password + salt;
		for (int i = 0; i < 3; i++) {
			result = DigestUtils.md5Hex(result);
		}
		System.err.println("\tpassword=" + password);
		System.err.println("\tsalt=" + salt);
		System.err.println("\tmd5Password=" + result);
		return result;
	}



}
