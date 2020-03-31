package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

/*
 * 处理订单数据和订单商品数据的持久层接口
 */
public interface OrderService {
	
	/**
	 * 创建订单
	 * @param aid 收货地址数据的id
	 * @param cids
	 * @param uid
	 * @param username
	 * @return
	 */
	Order createOrder(Integer aid, Integer[] cids, 
			Integer uid, String username);
}
