package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.ex.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService service;

	@Test
	public void reg() {
		try {
			User user = new User();
			user.setUsername("reg");
			user.setPassword("1234");
			user.setGender(0);
			user.setPhone("13800138003");
			user.setEmail("service@163.com");
			user.setAvatar("avatar");
			service.reg(user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
		}
	}

	@Test
	public void changePassword() {
		try {
			Integer uid = 5;
			String username = "密码管理员";
			String oldPassword = "1234";
			String newPassword = "0000";
			service.changePassword(uid, username, oldPassword, newPassword);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void changeAvatar() {
		try {
			Integer uid = 5;
			String username = "管理员";
			String avatar = "1234";
			service.changeAvatar(uid, username, avatar);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void changeInfo() {
		try {
			Integer uid = 5;
			String username = "资料管理员";
			User user = new User();
			user.setPhone("13100131888");
			user.setEmail("henry@tedu.cn");
			user.setGender(1);
			service.changeInfo(uid, username, user);
			System.err.println("OK.");
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
	@Test
	public void login() {
	    try {
	      String username = "reg";
	      String password = "1234";
	      User result = service.login(username, password);
	      System.err.println("OK.");
	      System.err.println(result);
	    } catch (ServiceException e) {
	      System.err.println(e.getClass().getName());
	      System.err.println(e.getMessage());
	    }
	}
	
	@Test
	public void getInfo() {
		try {
			Integer uid = 2;
			User result = service.getInfo(uid);
			System.err.println("OK.");
			System.err.println(result);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	
}