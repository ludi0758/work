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
public class OrderServiceTests {

	@Autowired OrderService service;

	@Test
	  public void createOrder() {
	    try {
	      Integer aid = 18;
	      Integer[] cids = { 5,6,7,8 };
	      Integer uid = 9;
	      String username = "HAHA";
	      service.createOrder(aid, cids, uid, username);
	      System.err.println("OK.");
	    } catch (ServiceException e) {
	      System.err.println(e.getClass().getName());
	      System.err.println(e.getMessage());
	    }
	  }

	
}
