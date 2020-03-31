package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;

/*
 * 处理收货地址数据的业务层接口
 */
public interface AddressService {
	
	
	/**
	 * 增加新的收货地址
	 * @param uid 用户id
	 * @param username 用户名
	 * @param address 收货地址数据
	 */
	void addnew(Integer uid,String username,Address address);

	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户的id
	 * @return 该用户的收货地址列表
	 */
	List<Address> getByUid(Integer uid);
	
	/**
	 * 查询某用户的收货地址列表
	 * @param aid 收获地址的id
	 * @return 该用户的收货地址列表
	 */
	Address getByAid(Integer aid);
	
	/**
	 * 设置默认收货地址
	 * @param aid 需要被设置为默认的收货地址id
	 * @param uid 用户id
	 * @param username 用户名
	 */
	void setDefault(Integer aid,Integer uid, String username);
	
	/**
	 * 删除收货地址
	 * @param aid 需要被删除的收货地址id
	 * @param uid 用户id
	 * @param username 用户名
	 */
	void delete(Integer aid,Integer uid,String username);
}
