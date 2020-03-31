package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileStateException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.UserService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.service.ex.UsernameDuplicateException;
import cn.tedu.store.util.JsonResult;

@RequestMapping("users")
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 响应到客户端的、表示操作成功的状态值
	 */
	private static final int OK = 2000;

	// http://localhost:8080/users/reg?username=controller&password=1234
	@RequestMapping("reg")
	public JsonResult<Void> reg(User user) {
		// 调用业务对象执行注册
		userService.reg(user);
		// 返回成功
		return new JsonResult<>(OK);
	}
	
	@RequestMapping("login")
	public JsonResult<User> login(String username,String password,HttpSession session){
		//日志
		System.out.println("UserController.login()");
		System.err.println("\tusername=" + username);
		System.err.println("\tpassword=" + password);
		//调用userService.login()方法执行登陆，并获取返回结果（成功登陆的用户数据）
		User data = userService.login(username, password);
		//将返回的结果保存到session中
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		//将结果响应给客户端
		return new JsonResult<>(OK,data);
	}
	
	@RequestMapping("password/change")
	public JsonResult<Void>  changePassword(String oldPassword,
			String newPassword,HttpSession session){
		//日志
		System.err.println("UserController.changePassword()");
		System.err.println("\toldPassword="+oldPassword);
		System.err.println("\tnewPassword="+newPassword);
		//从session中取出uid和username
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		//调用userService.changePassword()执行修改密码
		userService.changePassword(uid, username, oldPassword, newPassword);
		
		//返回操作成功
		return new JsonResult<>(OK);
	}
	
	@GetMapping("info/show")
	public JsonResult<User> showInfo(HttpSession session){
		//从session中获取uid
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		//调用userService.getInfo()获取数据
		User data = userService.getInfo(uid);
		//响应成功及数据
		return new JsonResult<>(OK,data);
	}
	@RequestMapping("info/change")
	public JsonResult<Void> changeInfo(User user,HttpSession session){
		//从session中获取uid和username
		Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
		String username = session.getAttribute("username").toString();
		//调用userService.changeInfo()个人资料
		userService.changeInfo(uid, username, user);
		//响应成功
		return new JsonResult<>(OK);
	}
	
	/**
	 * 上传头像时，允许使用的文件的最大大小，使用字节为单位
	 */
	@Value("${project.avatar-max-size}")
	private int avatarMaxSize;
	
	/**
	 * 上传头像时，允许使用的头像文件的MIME类型
	 */
	@Value("${project.avatar-types}")
	private List<String> avatarTypes;
	
	@PostMapping("avatar/change")
	public JsonResult<String> changeAvatar(MultipartFile file,HttpSession session){
		//日志
		System.out.println("UserController.changeAvatar()");
		//判断上传的文件是否为空
		boolean isEmpty = file.isEmpty();
		System.err.println(isEmpty);
		if(isEmpty){
			throw new FileEmptyException("上传头像失败！请选择有效的图片文件后再上传");
		}
		
		//判断上传的文件大小
		long size = file.getSize();
		System.err.println(size);
		if(size>avatarMaxSize){
			throw new FileSizeException("上传头像失败,不允许上传超过"+(avatarMaxSize/1024)+"KB");
		}
		
		//判断上传的文件类型
		String contentType = file.getContentType();
		System.err.println("\tcontentType"+contentType);
		if(!avatarTypes.contains(contentType)){
			throw new FileTypeException("上传头像失败!只允许上传以下类型的图片文件:\n\n"+avatarTypes);
		}
		
		//注意:SpringBoot框架默认限制了上传的文件的大小
		
		//上传的文件保存的文件夹的名称
		String dirName = "upload";
		//ServletContext的getRealPath()用于获取webapp下的某个文件夹的真实路径
		String parentPath = session.getServletContext().getRealPath(dirName);
		System.err.println("\tparent path=" + parentPath);
		//上传的文件保存到哪个文件夹
		File parent = new File(parentPath);
		if(!parent.exists()){
			parent.mkdir();
		}
		
		//上传的文件保存时使用什么文件名,只要保证后上传的文件不覆盖之前上传的文件即可,
		String filename = "" + System.currentTimeMillis() + System.nanoTime();
		//上传的文件保存时使用什么扩展名,应该是客户端上传时使用的扩展名
		String originamFilename = file.getOriginalFilename();
		System.err.println(originamFilename);
		int beginIndex = originamFilename.lastIndexOf(".");
		String suffix = originamFilename.substring(beginIndex);
		//上传的文件保存时是用什么文件名
		System.err.println(originamFilename);
		System.err.println(beginIndex);
		String child = filename + suffix;
		System.err.println(child);
		// 上传的文件保存到那里去
		File dest = new File(parent, child);
		
		//保存文件
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			throw new FileStateException("上传头像失败！源文件可能已被删除，无法访问到源文件！请稍后再次尝试");
		} catch (IOException e) {
			throw new FileUploadIOException("上传头像失败！处理文件时出现读写错误！请稍后再次尝试！");
		}
		
		//将上传文件的路径记录在数据库中
		Integer uid = Integer .valueOf(session.getAttribute("uid").toString());
		String username= session.getAttribute("username").toString();
		String avatar = "/" + dirName + "/" +child;
		userService.changeAvatar(uid, username, avatar);
		//响应成功
		return new JsonResult<>(OK,avatar);
	}
	
}
