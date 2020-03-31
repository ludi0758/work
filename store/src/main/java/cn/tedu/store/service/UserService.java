package cn.tedu.store.service;

import cn.tedu.store.entity.User;

/**
 * 处理用户数据的业务接口
 */
public interface UserService {
	
	/**
	 * 用户注册
	 * @param user  客户端提交的用户数据
	 */
	void reg(User user);
	
	/**
	 * 修改密码
	 * @param uid 用户的id
	 * @param username 用户名
	 * @param oldPassworrd 原密码
	 * @param newPassword 新密码
	 */
	void changePassword(Integer  uid ,String username,String oldPassworrd, String newPassword);
	
	/**
	 * 修改头像
	 * @param uid 用户的id
	 * @param username 用户名
	 * @param avatar 新头像的路径
	 */
	void changeAvatar(Integer uid,String username,String avatar);
	
	/**
	 * 修改用户个人资料
	 * @param uid 用户的id
	 * @param username 用户名
	 * @param user 封装了新的用户个人资料的对象
	 */
	void changeInfo(Integer uid,String username,User user);
	
	/**
	 * 用户登陆
	 * @param username 用户名
	 * @param password 用户密码
	 * @return 成功登陆的用户信息
	 */
	User login(String username,String password);
	
	/**
	 * 获取用户个人资料
	 * @param uid 用户的id
	 * @return 封装了用户个人资料的对象
	 */
	User getInfo(Integer uid);
	
	
	
}
