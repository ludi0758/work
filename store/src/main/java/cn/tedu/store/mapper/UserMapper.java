package cn.tedu.store.mapper;

import java.util.Date;
import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;

/**
 * 处理用户数据的持久层接口
 */
public interface UserMapper {

	/**
	 * 处理用户数据
	 * @param user用户数据
	 * @return 受影响的行数
	 */
	Integer insert(User user);

	/**
	 * 更新用户头像
	 * @param uid 用户的id 
	 * @param avatar 新头像的路径
	 * @param modifiedUser 最后修改人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updateAvatarByUid(
			@Param("uid") Integer uid,
			@Param("avatar") String avatar,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime
			);

	/**
	 * 修改用户密码
	 * @param uid 用户id
	 * @param password 新密码
	 * @param modifiedUser 最后修改人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updatePasswordByUid(@Param("uid") Integer uid,
			@Param("password") String password,
			@Param("modifiedUser") String modifiedUser,
			@Param("modifiedTime") Date modifiedTime);

	/**
	 * 更新用户的个人资料
	 * @param user 封装了用户的id和新个人资料的对象,可以更新的属性有:手机号码,电子邮箱,性别
	 * @return 受影响的行数
	 */
	Integer updateInfoByUid(User user);

	/**
	 * 根据用户id查询用户数据
	 * @param uid 用户id
	 * @return 匹配的用户数据,如果没有匹配的数据,则返回null
	 */
	User findByUid(Integer uid);

	/**
	 * 根据用户名查询用户数据
	 * @param username用户名
	 * @return 匹配的用户数据,如果没有匹配的数据,则返回null
	 */
	User findByUsername(String username);

}




