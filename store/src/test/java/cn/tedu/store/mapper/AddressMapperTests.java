package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.Address;

@RunWith(SpringRunner.class)
@SpringBootTest 
public class AddressMapperTests {

	@Autowired
	private AddressMapper mapper;

	@Test
	public void insert(){
		Address address = new Address();
		address.setName("小王同学");
		address.setUid(2);
		address.setIsDefault(1);
		Integer rows = mapper.insert(address);
		System.err.println(rows);
	}

	@Test
	public void countByUid(){
		Integer uid = 1;
		Integer rows = mapper.countByUid(uid );
		System.err.println(rows);
	}

	@Test
	public void findByUid() {
		Integer uid = 1;
		List<Address> list = mapper.findByUid(uid);
		System.err.println("count=" + list.size());
		for (Address item : list) {
			System.err.println(item);
		}
	}
	
	@Test
	public void findByAid() {
		Integer aid = 14;
		Address address = mapper.findByAid(aid);
		System.err.println(address);
	}
	
	@Test
	public void deleteByAid(){
		Integer aid = 14;
		Integer rows = mapper.deleteByAid(aid);
		System.err.println(rows);
	}
	
	@Test
	public void findLastModified(){
		Integer uid = 9;
		Address result = mapper.findLastModified(uid);
		System.err.println(result);
	}

}
