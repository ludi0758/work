package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/*
 * 处理购物车数据的业务层接口
 */
public interface CartService {

	/**
	 * 将商品添加到购物车
	 * @param uid 用户的id
	 * @param username 用户名
	 * @param pid 商品的id
	 * @param amount 添加的数量
	 */
	void addToCart(Integer uid,String username,Integer pid,Integer amount);

	/**
	 * 查询某用户的购物车列表
	 * @param uid 用户id
	 * @return 用户的购物车列表
	 */
	List<CartVO> getByUid(Integer uid);

	/**
	 * 增加购物车商品的数量
	 * @param cid 购物车数据的id 
	 * @param uid 用户的id
	 * @param username 最后修改人
	 */
	void addNum(Integer cid, Integer uid, String username);

	/**
	 * 查询若干个数据id匹配的购物车列表
	 * @param cids 若干个数据id
	 * @param uid 用户的id
	 * @return 匹配的购物车列表
	 */
	List<CartVO> getByCids(Integer[] cids, Integer uid);
	
	void delete(Integer[] cids, Integer uid);

}
