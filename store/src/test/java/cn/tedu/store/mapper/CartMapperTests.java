package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest 
public class CartMapperTests {
	
	@Autowired
	private CartMapper mapper;
	
	@Test
	public void insert(){
		Cart cart = new Cart();
		cart.setUid(1);
		cart.setPid(2);
		cart.setNum(3);
		cart.setPrice(4L);
		Integer rows = mapper.insert(cart);
		System.err.println(rows);
	}
	
	@Test
	public void findByCid(){
		Integer cid = 10;
		Cart cart = mapper.findByCid(cid);
		System.err.println(cart);
	}
	
	@Test
	public void findByUidAndPid(){
		Integer pid = 2;
		Integer uid = 1;
		Cart cart = mapper.findByUidAndPid(uid, pid);
		System.err.println(cart);
	}
	
	@Test
	public void updateNumBycid(){
		Integer num = 10;
		Integer cid = 1;
		String modifiedUser = "头像管理员";
		Date modifiedTime = new Date();
		Integer rows = mapper.updateNumByCid(cid, num, modifiedUser, modifiedTime);
		System.err.println(rows);
	}
	
	@Test
	public void findByUid(){
		Integer uid = 9;
		List<CartVO> list = mapper.findByUid(uid);
		System.err.println("count=" + list);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
	
	@Test
	public void findByCids(){
		Integer[] cids = {1,3,6,9,2,77,4};
		List<CartVO> list = mapper.findByCids(cids);
		System.err.println("count=" + list);
		for (CartVO cartVO : list) {
			System.err.println(cartVO);
		}
	}
}
