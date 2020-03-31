package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Cart;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.vo.CartVO;

@RunWith(SpringRunner.class)
@SpringBootTest 
public class OrderMapperTests {
	
	@Autowired
	private OrderMapper mapper;
	
	@Test
	public void insertOrder(){
		Order order = new Order();
		order.setUid(1);
		Integer rows = mapper.insertOrder(order );
		System.err.println(rows);
	}
	
	@Test
	public void insertOrderItem(){
		OrderItem order = new OrderItem();
		order.setOid(1);
		Integer rows = mapper.insertOrderItem(order);
		System.err.println(rows);
	}
	
}
