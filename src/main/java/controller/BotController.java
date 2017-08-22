package controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import domain.Product;
import services.ProductService;

@RestController
public class BotController {

	private ProductService productService;

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	@RequestMapping(value = "/webhook", method = RequestMethod.POST)
	private @ResponseBody Map<String, Object> webhook(@RequestBody Map<String, Object> obj) throws JSONException {
		System.out.println("**********webhook/***************");
		Map<String, Object> json = new HashMap<String, Object>();
/*
		Product prod = new Product();
		prod.setId(1);
		prod.setDescription("prod1");
		prod.setPrice(500);
		this.productService.saveProduct(prod);
		Product prodSearch = new Product();
		prodSearch = this.productService.getProductById(1);
		*/
		json.put("speech", " The cost of product is:" + prodSearch.getPrice());
		json.put("displayText", " The cost of product is:" + prodSearch.getPrice());

		json.put("source", "apiai-onlinestore-shipping");
		System.out.println("************* ******************" + obj.get("result"));
		return json;

	}
}
