package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.service.CartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;

@RestController
@RequestMapping("carts")
public class CartController extends BaseController{

	@Autowired
	private CartService cartService;

	@RequestMapping("add")
	public JsonResult<Void> addToCart(Integer pid,Integer amount,HttpSession session){
		//从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		//调用业务方法执行功能
		cartService.addToCart(uid, username, pid, amount);
		//响应成功
		return new JsonResult<>(OK);
	}

	@GetMapping({"", "/"})
	public JsonResult<List<CartVO>> getByUid(HttpSession session) {
		// 从Session中获取uid
		Integer uid = getUidFromSession(session);
		// 执行查询，获取数据
		List<CartVO> date = cartService.getByUid(uid);
		// 返回成功与数据
		return new JsonResult<>(OK,date);
	}

	@RequestMapping("{cid}/num/add")
	public JsonResult<Void> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		cartService.addNum(cid, uid, username);
		return new JsonResult<>(OK);
	}
	// http://localhost:8080/carts/get_by_cids?cids=6&cids=8&cids=15&cids=16
	@GetMapping("get_by_cids")
	public JsonResult<List<CartVO>> getByCids(Integer[] cids, HttpSession session) {
		Integer uid = getUidFromSession(session);
		List<CartVO> data = cartService.getByCids(cids, uid);
		return new JsonResult<>(OK, data);
	}
}