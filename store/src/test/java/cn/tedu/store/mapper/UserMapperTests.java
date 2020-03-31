package cn.tedu.store.mapper;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {

	@Autowired
	private UserMapper mapper;

	@Test
	public void insert() {
		User user = new User();
		user.setUsername("project");
		user.setPassword("1234");
		user.setSalt("salt");
		user.setGender(0);
		user.setPhone("13800138002");
		user.setEmail("project@163.com");
		user.setAvatar("avatar");
		user.setIsDelete(0);
		user.setCreatedUser("系统管理员");
		user.setCreatedTime(new Date());
		user.setModifiedUser("超级管理员");
		user.setModifiedTime(new Date());
		Integer rows = mapper.insert(user);
		System.err.println("rows=" + rows);
		System.err.println(user);
	}

	@Test
	public void updatePasswordByUid() {
		Integer uid = 1;
		String password = "888888";
		String modifiedUser = "系统管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updatePasswordByUid(uid, password, modifiedUser, modifiedTime);
		System.err.println("rows=" + rows);
	}
	
	@Test
	public void updateInfoByUid(){
		User user = new User();
		user.setUid(2);
		user.setPhone("1313131313");
		user.setEmail("lucy@tedu.com");
		user.setGender(0);
		Integer rows = mapper.updateInfoByUid(user);
		System.err.println(rows);
	}
	
	@Test
	public void updateAvatarByUid(){
		Integer uid = 2;
		String avatar = "888";
		String modifiedUser = "头像管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateAvatarByUid(uid, avatar, modifiedUser, modifiedTime);
		System.err.println(rows);
	}
	
	@Test
	public void findByUid(){
		Integer uid = 1;
		User result = mapper.findByUid(uid);
		System.err.println(result);
	}
	
	@Test
	public void findByUsername() {
		String username = "project";
		User result = mapper.findByUsername(username);
		System.err.println(result);
	}
}


