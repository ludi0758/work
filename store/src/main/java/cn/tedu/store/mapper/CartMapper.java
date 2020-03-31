package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

/*
 * 处理购物车数据的持久层接口
 */
public interface CartMapper {
	
	/**
	 * 插入购物车数据
	 * @param cart购物车数据
	 * @return 受影响的行数
	 */
	Integer insert(Cart cart);
	
	/**
	 * 删除某用户的若干个购物车数据
	 * @param cids
	 * @param uid
	 * @return
	 */
	Integer deleteByCids(@Param("cids") Integer[] cids,@Param("uid") Integer uid);
	
	/**
	 * 修改购物车中商品的数量
	 * @param cid 购物车数据id
	 * @param num 新的数量
	 * @param modifiedUser 最后修改人
 	 * @param modifiedTime 最后修改时间
	 * @return 受影响的行数
	 */
	Integer updateNumByCid(
		@Param("cid") Integer cid,
		@Param("num") Integer num,
		@Param("modifiedUser") String modifiedUser,
		@Param("modifiedTime") Date modifiedTime
	);
	
	/**
	 * 查询某用户在购物车添加的某商品的详情
	 * @param uid 用户的id
	 * @param pid 商品的id
	 * @return 匹配的购物车详情，如果该用户没有将该商品添加到购物车，则返回null
	 */
	Cart findByUidAndPid(
		@Param("uid") Integer uid,
		@Param("pid") Integer pid
	);
	
	/**
	 * 根据购物车数据的id查询购物车详情
	 * @param cid 购物车数据的id
	 * @return 匹配的购物车详情
	 */
	Cart findByCid(Integer cid);
	
	
	List<CartVO> findByUid(Integer uid);
	
	/**
	 * 查询若干个数据id匹配的购物车列表
	 * @param cid
	 * @return
	 */
	List<CartVO> findByCids(Integer[] cid);
}
