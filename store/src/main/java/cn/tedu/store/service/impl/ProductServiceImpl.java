package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.ProductMapper;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.service.ex.ProductException;
import cn.tedu.store.service.ex.ProductOutOfStockException;
import cn.tedu.store.service.ex.UpdateException;

/**
 * 处理商品数据的业务层实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	public Product getById(Integer id) {
		//调用自身私有方法查询数据
		Product product = findById(id);
		//检查查询结果数据是否为null
		if(product == null){
			// 是：抛出ProductNotFoundException
			throw new ProductException("获取商品详情失败！尝试访问的商品数据不存在！");
		}

		// 将不必要响应给客户端的属性值设为null
		product.setCreatedTime(null);
		product.setCreatedUser(null);
		product.setModifiedTime(null);
		product.setModifiedUser(null);
		product.setPriority(null);
		product.setCategoryId(null);
		product.setPriority(null);
		//返回数据
		return product;
	}

	@Override
	public List<Product> getHotList() {
		//调用私有方法查询得到列表
		List<Product> list = findHotList();
		//遍历列表，将不需要响应给客户端的数据属性设置为null
		for (Product product : list) {
			product.setCreatedTime(null);
			product.setCreatedUser(null);
			product.setModifiedTime(null);
			product.setModifiedUser(null);
			product.setPriority(null);
			product.setCategoryId(null);
			product.setPriority(null);

		}
		return list;
	}

	@Override
	  public void reduceNum(Integer id, Integer amount, String username) {
	    // 基于参数id调用findById()查询商品数据
	    Product result = findById(id);
	    // 判断查询结果是否为null
	    if (result == null) {
	      // 是：ProductNotFoundException
	      throw new ProductException("减少商品库存失败！尝试访问的商品数据不存在！");
	    }
	    // 通过查询结果可以得到原库存值，结合参数amount，计算得到新的库存值
	    Integer num = result.getNum() - amount;
	    // 判断新的库存值是否<0
	    if (num < 0) {
	      // 是：抛出ProductOutOfStockException
	      throw new ProductOutOfStockException("更新商品库存失败！商品库存不足！");
	    }
	    // 调用updateNumById()更新商品的库存
	    updateNumById(id, num, username, new Date());
	  }
	
	
	/**
	 * 更新商品的库存
	 * @param id 商品的id
	 * @param num 新的库存值
	 * @param modifiedUser 最后修改人
	 * @param modifiedTime 最后修改时间
	 */
	private void updateNumById(Integer id, Integer num, String modifiedUser, Date modifiedTime) {
		Integer rows = productMapper.updateNumById(id, num, modifiedUser, modifiedTime);
		if (rows != 1) {
			throw new UpdateException("更新商品库存失败！更新库存值时出现未知错误，请联系系统管理员！");
		}
	}

	/**
	 * 根据商品id查询商品详情
	 * @param id 商品id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	private Product findById(Integer id){
		return productMapper.findById(id);

	}

	/**
	 * 查询热销商品排行的前4个商品
	 * @return 热销商品排行的前4个商品
	 */
	private List<Product> findHotList(){
		return productMapper.findHotList();
	}

}
