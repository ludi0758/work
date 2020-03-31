package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.AddressService;
import cn.tedu.store.service.CartService;
import cn.tedu.store.service.OrderService;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;

/*
 * 处理订单数据和订单商品数据的业务层实现类
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private AddressService addressService;
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;

	@Override
	@Transactional
	public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
		// 创建当前时间对象now
		Date now = new Date();
		// 根据参数aid调用addressService.getByAid()查询收货地址详情
		Address address = addressService.getByAid(aid);
		// 根据参数cids调用cartService.getByCids()，得到List<CartVO>
		List<CartVO> list = cartService.getByCids(cids, uid);
		// 定义totalPrice变量
		Long totalPrice = (long) 0;
		// 遍历以上查询到的List<CartVO>，计算出totalPrice
		for (CartVO cartVO : list) {
			Long price = cartVO.getPrice();
			totalPrice = totalPrice + price;
		}
		// 创建Order对象
		Order order = new Order();
		// 补全Order对象中的属性：uid > 参数uid
		order.setUid(uid);
		// 补全Order对象中的属性：recv* > 收货地址详情
		order.setRecvName(address.getCreatedUser());
		order.setRecvPhone(address.getPhone());
		order.setRecvProvince(address.getProvinceName());
		order.setRecvCity(address.getCityName());
		order.setRecvArea(address.getAreaName());
		order.setRecvAddress(address.getAddress());
		// 补全Order对象中的属性：orderTime > now
		order.setOrderTime(now);
		// 补全Order对象中的属性：payTime > null
		order.setPayTime(null);
		// 补全Order对象中的属性：totalPrice > totalPrice
		order.setTotalPrice(totalPrice);
		// 补全Order对象中的属性：status > 0
		order.setStatus(0);
		// 补全Order对象中的属性：4个日志
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedTime(now);
		order.setModifiedUser(username);
		// 调用insertOrder(Order order)插入订单数据
		insertOrder(order);
		// 遍历查询到的List<CartVO>
		for (CartVO cart : list) {
			// -- 创建OrderItem对象
			OrderItem item = new OrderItem();
			// -- 补全OrderItem对象中的属性：oid > order.getOid();
			item.setOid(order.getOid());
			// -- 补全OrderItem对象中的属性：pid, title, image, price, num > CartVO对象中的属性
			item.setPid(cart.getPid());
			item.setTitle(cart.getTitle());
			item.setImage(cart.getImage());
			item.setPrice(cart.getPrice());
			item.setNum(cart.getNum());
			// -- 补全OrderItem对象中的属性：4个日志
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifiedTime(now);
			item.setModifiedUser(username);
			// -- 多次调用insertOrderItem(OrderItem orderItem)插入订单商品数据
			insertOrderItem(item);
			// 减去商品的库存
			productService.reduceNum(cart.getPid(), cart.getNum(), username);
		}

		// 删除购物车对应的数据
		cartService.delete(cids, uid);

		// 返回订单数据
		Order result = new Order();
		result.setOid(order.getOid());
		result.setTotalPrice(order.getTotalPrice());
		return result;
	}

	public void insertOrder(Order order) {
		Integer rows = orderMapper.insertOrder(order);
		if (rows != 1) {
			throw new InsertException("创建订单失败！插入订单数据时出现未知错误，请联系系统管理员！");
		}
	}

	public void insertOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.insertOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertException("创建订单失败！插入订单商品数据时出现未知错误，请联系系统管理员！");
		}
	}

}
