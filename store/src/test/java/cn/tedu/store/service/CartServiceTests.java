package cn.tedu.store.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.service.ex.ServiceException;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

	@Autowired CartService service;

	@Test
	public void addToCart(){
		try {
			Integer uid = 1;
			String username = "小刘同学";
			Integer pid = 10000001;
			Integer amount = 3;
			service.addToCart(uid, username, pid, amount);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void getByUid() {
		Integer uid = 9;
		List<CartVO> list = service.getByUid(uid);
		System.err.println("count=" + list.size());
		for (CartVO item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void addNum(){
		try {
			Integer uid = 9;
			String username = "小刘同学";
			Integer cid = 10;
			service.addNum(cid, uid, username);
		} catch (ServiceException e) {
			System.err.println(e.getClass().getName());
			System.err.println(e.getMessage());
		}
	}
	@Test
	  public void findByCids() {
	    Integer[] cids = { 10, 8, 14, 12, 6, 15, 16, 18, 20 };
	    Integer uid = 9;
	    List<CartVO> list = service.getByCids(cids, uid);
	    System.err.println("count=" + list.size());
	    for (CartVO item : list) {
	      System.err.println(item);
	    }
	  }
}
