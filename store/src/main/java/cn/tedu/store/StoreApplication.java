package cn.tedu.store;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
@SpringBootApplication
@MapperScan("cn.tedu.store.mapper")
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
	
	/**
	 * 获取MultipartConfigElement
	 * 
	 * 添加了<pre>@Bean</pre>注解了方法，会被Spring框架调用，并管理返回的对象
	 * 
	 * @return MultipartConfigElement类型的对象,是上传文件的配置类型的对象
	 */
	@Bean
	public MultipartConfigElement getMultipartConfigElement(){
		MultipartConfigFactory factory = new MultipartConfigFactory();
		
		//上传的文件的最大大小
		factory.setMaxFileSize(DataSize.ofKilobytes(5120));
		
		//请求的数据量的最大大小
		factory.setMaxRequestSize(DataSize.ofMegabytes(20));
		
		return factory.createMultipartConfig();
	}

}