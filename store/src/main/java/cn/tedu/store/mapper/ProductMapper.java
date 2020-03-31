package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Product;


/**
 * 处理商品数据的持久层接口
 */
public interface ProductMapper {

	/**
	 * 根据商品id查询商品详情
	 * @param id 商品的id
	 * @return 匹配的商品详情，如果没有匹配的数据，则返回null
	 */
	Product findById(Integer id);

	/**
	 * 查询热销商品排行的前4个商品
	 * @return 热销商品排行的前4个商品
	 */
	List<Product> findHotList();


	/**
	 * 更新商品的库存
	 * @param id 商品的id
	 * @param num 新的库存值
	 * @param modifiedUser 最后修改人
	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updateNumById(
			@Param("id") Integer id, 
			@Param("num") Integer num, 
			@Param("modifiedUser") String modifiedUser, 
			@Param("modifiedTime") Date modifiedTime
			);

}
