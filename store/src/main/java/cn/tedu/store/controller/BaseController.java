package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

/**
 * 控制器类的基类
 * @author syk
 *
 */
abstract class BaseController {
	  
	  /**
	   * 响应到客户端的、表示操作成功的状态值
	   */
	  protected static final int OK = 2000;
	  
	  /**
	   * 获取Session中的用户id
	   * @param session session HttpSession对象
	   * @return 当前登陆的用户的id
	   */
	  protected final Integer getUidFromSession(HttpSession session) {
	    return Integer.valueOf(session.getAttribute("uid").toString());
	  }
	  
	  /**
	   * 获取Session中的用户名
	   * @param session session HttpSession对象
	   * @return 当前登陆的用户的名
	   */
	  protected final String getUsernameFromSession(HttpSession session) {
	    return session.getAttribute("username").toString();
	  }
	  
}