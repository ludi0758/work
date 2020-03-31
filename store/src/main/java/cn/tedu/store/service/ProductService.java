package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Product;

/**
 * 处理商品数据的业务层接口
 */
public interface ProductService {
	
	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情
	 */
	Product getById(Integer id);
	
	/**
	 * 查询热销商品排行的前4个商品
	 * @return 查询热销商品排行的前4个商品
	 */
	List<Product> getHotList();
	
	
	/**
	   * 减少商品的库存
	   * @param id 商品的id
	   * @param amount 减少的数量
	   * @param username 用户名
	   */
	  void reduceNum(Integer id, Integer amount, String username);

}
