package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import domain.Product;
import repositories.ProductRepository;

@RestController
public class BotController {

	@Autowired
	ProductRepository rep;

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	@RequestMapping(value = "/webhook", method = RequestMethod.POST)
	private @ResponseBody Map<String, Object> webhook(@RequestBody Map<String, Object> map) throws JSONException {
		System.out.println("**********webhook/***************");
		JSONObject jsonResult = new JSONObject(map);
		JSONObject rsl = jsonResult.getJSONObject("result");
		JSONObject param = rsl.getJSONObject("parameters");
		String city = param.getString("shipping-zone");

		System.out.println("**********Result***************");
		System.out.println("********param**shipping-zone**************" + city);
		System.out.println("************* ******************");
		Map<String, Object> json = new HashMap<String, Object>();
		Product prodSearch = new Product();
		prodSearch = rep.findOne(1);

		/*
		 * json.put("speech", " The cost of product is:" + prodSearch.getPrice());
		 * json.put("displayText", " The cost of product is:" + prodSearch.getPrice());
		 * 
		 * json.put("source", "apiai-onlinestore-shipping");
		 */
		json.put("type", "image");
		json.put("originalContentUrl",
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");
		json.put("previewImageUrl",
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");

		System.out.println("************* ******************" + map.get("result"));

		return json;

	}

}
