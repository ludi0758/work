package cn.tedu.store;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	@Test
	public void md5(){
		String password="123";
		String salt = UUID.randomUUID().toString();
		System.err.println(salt);
		String result = DigestUtils.md5Hex(password);
		System.err.println(result);
	}

}
