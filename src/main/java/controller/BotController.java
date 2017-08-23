package controller;

import java.io.IOException;
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

import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;

import domain.Product;
import repositories.ProductRepository;
import retrofit2.Response;

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
	private @ResponseBody Map<String, Object> webhook(@RequestBody Map<String, Object> map)
			throws JSONException, IOException {
		System.out.println("**********webhook/***************" + map);
		System.out.println("******************************");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}
		/*
		 * JSONObject jsonResult = new JSONObject(map); JSONObject rsl =
		 * jsonResult.getJSONObject("result"); JSONObject param =
		 * rsl.getJSONObject("parameters");
		 */
		// String city = param.getString("shipping-zone");

		System.out.println("**********Result***************");
		// System.out.println("********param**shipping-zone**************" + city);
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
		ImageMessage textMessage = new ImageMessage(
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg",
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");
		ReplyMessage replyMessage = new ReplyMessage("415707c0f42b4bc5a411bcc197789d5e", textMessage);
		Response<BotApiResponse> response = LineMessagingServiceBuilder.create(
				"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=")
				.build().replyMessage(replyMessage).execute();
		System.out.println(response.code() + " " + response.message());

		return json;

	}

}
