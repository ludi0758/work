package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.ProductService;
import cn.tedu.store.util.JsonResult;

@RestController
@RequestMapping("products")
public class ProductConreoller extends 	BaseController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("list/hot")
	public JsonResult<List<Product>> getHotList(){
		List<Product> data = productService.getHotList();
		return new JsonResult<>(OK,data);
	}
	
	@GetMapping("{id}/details")
	public JsonResult<Product> getById(@PathVariable("id") Integer id){
		Product data = productService.getById(id);
		return new JsonResult<>(OK,data);
	}
}
