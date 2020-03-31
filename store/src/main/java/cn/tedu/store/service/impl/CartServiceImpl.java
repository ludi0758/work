package cn.tedu.store.service.impl;

import java.nio.channels.AcceptPendingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.cj.x.protobuf.MysqlxCrud.Insert;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Product;
import cn.tedu.store.mapper.CartMapper;
import cn.tedu.store.service.CartService;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.CartNotFoundException;
import cn.tedu.store.service.ex.DeleteException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.service.ex.UpdateException;
import cn.tedu.store.vo.CartVO;

/*
 * 处理购物车数据的业务层实现类
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CartMapper cartMapper;

	@Override
	public void addToCart(Integer uid, String username, Integer pid, Integer amount) {
		//创建当前时间对象now
		Date now = new Date();
		//调用findByUidAndPid()查询购物车详情
		Cart cart = cartMapper.findByUidAndPid(uid, pid);
		//判断查询结果是否为null
		if(cart == null){
			//是：表示该用户的购物车没有该商品，则需要执行Insert操作
			//调用productService.getById()得到商品详情，该数据中包含商品价格
			Product product = productService.getById(pid);
			//创建新的Cart对象
			Cart newCart = new Cart();
			//补全Cart对象的属性：uid>参数uid
			newCart.setUid(uid);
			//补全Cart对象的属性：pid>参数pid
			newCart.setPid(pid);
			//补全Cart对象的属性：num>参数num
			newCart.setNum(amount);
			//补全Cart对象的属性：price>以上查询到的商品详情中包含价格
			newCart.setPrice(product.getPrice());
			//补全Cart对象的属性：4个日志>参数username,now
			newCart.setCreatedTime(now);
			newCart.setCreatedUser(username);
			newCart.setModifiedTime(now);
			newCart.setModifiedUser(username);
			//调用insert()插入数据
			this.insert(newCart);
		}else{
			//否：表示该用户的购物车以有该商品，则需要执行update操作增加数量
			//从查询结果中获取cid
			Integer cid = cart.getCid();
			//从查询结果中取出原有数量，与参数amount相加，得到新的数量
			Integer n = amount + cart.getNum();
			//调用updateNumByCid()执行修改数量
			this.updateNumByCid(cid, n, username, now);
		}
	}

	@Override
	public void addNum(Integer cid, Integer uid, String username) {
		// 根据参数cid调用findByCid()查询购物车详情数据
		Cart result = findByCid(cid);
		  // 判断查询结果是否为null
		if(result == null){
		  // 是：抛出CartNotFoundException
			throw new CartNotFoundException("增加商品数量失败，尝试访问的商品不存在");
		}
		  // 判断查询结果中的uid与参数uid是否不匹配
		if(!result.getUid().equals(uid)){
			throw new AccessDeniedException("增加商品数量失败，非法访问已经被拒绝");
			// 是：抛出AccessDeniedException
		}
		  
		  // 从查询结果中取出原数量，增加1，得到新的数量
		Integer newNum = result.getNum() + 1;
		  // 判断自定义规则：新的数量应该在哪个范围之内
		
		  // 调用updateNumByCid()执行更新数量
		updateNumByCid(cid, newNum, username, new Date());
	}

	@Override
	public void delete(Integer[] cids, Integer uid) {
		  deleteByCids(cids, uid);
		}
	
	@Override
	public List<CartVO> getByUid(Integer uid) {
		return findByUid(uid);
	}


	@Override
	public List<CartVO> getByCids(Integer[] cid,Integer uid) {
		List<CartVO> carts = findByCids(cid);
		
		Iterator<CartVO> it = carts.iterator();
		
		while(it.hasNext()){
			CartVO cart = it.next();
			if(!cart.getUid().equals(uid)){
				it.remove();
			}
		}
		return carts;
	}

	
	/**
	   * 删除某用户的若干个购物车数据
	   * @param cids 被删除的购物车数据的id
	   * @param uid 用户的id
	   */
	  private void deleteByCids(Integer[] cids, Integer uid) {
	    Integer rows = cartMapper.deleteByCids(cids, uid);
	    if (rows < 1) {
	      throw new DeleteException("删除购物车数据失败！删除购物车数据时出现未知错误，请联系系统管理员！");
	    }
	  }
	
	/**
	 * 插入购物车数据
	 * @param cart购物车数据
	 */
	private void insert(Cart cart){
		Integer rows = cartMapper.insert(cart);
		if(rows != 1){
			throw new InsertException();
		}
	}

	/**
	 * 修改购物车中商品的数量
	 * @param cid 购物车数据id
	 * @param num 新的数量
	 * @param modifiedUser 最后修改人
	 * @param modifiedTime 最后修改时间
	 */
	private void updateNumByCid(Integer cid,Integer num,
			String modifiedUser,Date modifiedTime){
		Integer rows = cartMapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
		if(rows != 1){
			throw new UpdateException();
		}
	}

	/**
	 * 查询某用户在购物车添加的某商品的详情
	 * @param uid 用户的id
	 * @param pid 商品的id
	 * @return 匹配的购物车详情，如果该用户没有将该商品添加到购物车，则返回null
	 */
	private Cart findByUidAndPid(Integer uid,Integer pid){
		return cartMapper.findByUidAndPid(uid, pid);
	}

	/**
	 * 根据购物车数据的id查询购物车详情
	 * @param cid 购物车数据的id
	 * @return 匹配的购物车详情
	 */
	private Cart findByCid(Integer cid){
		return cartMapper.findByCid(cid);
	}
	
	/**
	 * 查询某用户的购物车列表
	 * @param uid 用户id
	 * @return 用户的购物车列表
	 */
	private List<CartVO> findByUid(Integer uid) {
		return cartMapper.findByUid(uid);
	}
	
	/**
	 * 查询若干个数据id匹配的购物车列表
	 * @param cid
	 * @return
	 */
	private List<CartVO> findByCids(Integer[] cid){
		return cartMapper.findByCids(cid);
	}

}
