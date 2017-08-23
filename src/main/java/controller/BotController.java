package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.client.LineMessagingServiceBuilder;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
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
		LineMessagingService client = LineMessagingServiceBuilder.create(
				"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=")
				.build();
		System.out.println("**client***" + client);
		/*********************************************/
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue().toString());
		}

		JSONObject jsonResult = new JSONObject(map);
		JSONObject rsl = jsonResult.getJSONObject("originalRequest");
		JSONObject data = rsl.getJSONObject("data");
		JSONObject source = data.getJSONObject("source");
		JSONObject param = source.getJSONObject("userId");

		// String city = param.getString("shipping-zone");

		System.out.println("**********Result****userId***********");
		System.out.println("********param**shipping-zone**************" + param);
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

		PushMessage pushMessage = new PushMessage("Ub682199b78467a3f13d9cfb217127857", textMessage);

		Response<BotApiResponse> response = LineMessagingServiceBuilder.create(
				"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=")
				.build().pushMessage(pushMessage).execute();
		System.out.println(response.code() + " " + response.message());

		return json;

	}

}
