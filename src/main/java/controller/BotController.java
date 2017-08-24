package controller;

import java.io.IOException;
import java.util.Arrays;
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
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
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

		JSONObject jsonResult = new JSONObject(map);
		JSONObject rsl = jsonResult.getJSONObject("originalRequest");
		JSONObject data = rsl.getJSONObject("data");
		JSONObject source = data.getJSONObject("source");
		String userId = source.getString("userId");

		System.out.println("**********Result*************");
		System.out.println("********param***userId**************" + userId);
		System.out.println("************* ******************");
		/********************************/
		LineMessagingService client = LineMessagingServiceBuilder.create(
				"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=")
				.build();
		System.out.println("**client***" + client);
		/*********************************************/
		/****************************/
		Map<String, Object> json = new HashMap<String, Object>();
		Product prodSearch = new Product();
		prodSearch = rep.findOne(1);

		/*
		 * json.put("speech", " The cost of product is:" + prodSearch.getPrice());
		 * json.put("displayText", " The cost of product is:" + prodSearch.getPrice());
		 * 
		 * json.put("source", "apiai-onlinestore-shipping");
		 */

		System.out.println("************* ******************" + map.get("result"));

		ImageMessage textMessage = new ImageMessage(
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg",
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");

		PushMessage pushMessage = new PushMessage("Ub682199b78467a3f13d9cfb217127857", textMessage);

		Response<BotApiResponse> response = LineMessagingServiceBuilder.create(
				"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=")
				.build().pushMessage(pushMessage).execute();
		System.out.println(response.code() + " " + response.message());
		/******************************************/
		carouselForUser(
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg",
				"Ub682199b78467a3f13d9cfb217127857", "title1",
				"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=");
		/***********************************************/

		return json;

	}

	// Method for send caraousel template message to user
	private void carouselForUser(String poster_url, String userId, String title, String lChannelAccessToken) {
		CarouselTemplate carouselTemplate = new CarouselTemplate(Arrays.asList(
				new CarouselColumn(poster_url, title, "Select one for more info",
						Arrays.asList(new MessageAction("Full Data", "Title \"" + title + "\""),
								new MessageAction("Summary", "Plot \"" + title + "\""),
								new MessageAction("Poster", "Poster \"" + title + "\""))),
				new CarouselColumn(poster_url, title, "Select one for more info",
						Arrays.asList(new MessageAction("Released Date", "Released \"" + title + "\""),
								new MessageAction("Actors", "Actors \"" + title + "\""),
								new MessageAction("Awards", "Awards \"" + title + "\"")))));
		TemplateMessage templateMessage = new TemplateMessage("Your search result", carouselTemplate);
		PushMessage pushMessage = new PushMessage(userId, templateMessage);
		try {
			Response<BotApiResponse> response = LineMessagingServiceBuilder.create(lChannelAccessToken).build()
					.pushMessage(pushMessage).execute();
			System.out.println(response.code() + " " + response.message());
		} catch (IOException e) {
			System.out.println("Exception is raised ");
			e.printStackTrace();
		}
	}

}
