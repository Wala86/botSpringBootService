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
		JSONObject message = data.getJSONObject("message");
		String userId = source.getString("userId");
		String userMessage = message.getString("text");
		String timestamp = jsonResult.getString("timestamp");

		System.out.println("**********Result*************");
		System.out.println("********param***userId**************" + userId);
		System.out.println("************* ******************");
		/************************************/
		JSONObject result = jsonResult.getJSONObject("result");
		JSONObject metadata = result.getJSONObject("metadata");
		String intentName = metadata.getString("intentName");
		JSONObject parameters = result.getJSONObject("parameters");
		JSONObject fulfillment = result.getJSONObject("fulfillment");
		String speech = fulfillment.getString("speech");

		String hairStyle = parameters.getString("hair-style");
		String hairColor = parameters.getString("hair-color");
		System.out.println("********param***intentName**************" + intentName);
		System.out.println("********param***hairStyle**************" + hairStyle);
		System.out.println("********param***hairColor**************" + hairColor);
		System.out.println("********param***userMessage**************" + userMessage);
		System.out.println("********param***speech**************" + speech);
		System.out.println("********param***timestamp**************" + timestamp);
		/***********************************/
		if (intentName == "recommendation" || intentName.equals("recommendation")) {
			System.out.println("********amira**************");

			if (!(hairStyle.equals("")) && (!hairColor.equals(""))) {
				/*************** send Image ******************/
				
				System.out.println("********yassine**************");

				ImageMessage textMessage = new ImageMessage(
						"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg",
						"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");

				PushMessage pushMessage = new PushMessage(userId, textMessage);

				Response<BotApiResponse> response = LineMessagingServiceBuilder.create(
						"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=")
						.build().pushMessage(pushMessage).execute();
				System.out.println(response.code() + " " + response.message());
				/*************** send carousel ******************/
				carouselForUser(userId,
						"mmud/Cez+bvYykKzBnemzXm6fAXOPg6s9SEYD52jcBdCeFM/sxyIJxQaz9xpC0i2fW73wibxwtkHH45DNy6f9M8wj5GYAYxNf4NOZo0kfI68PmQzlbqqCQrg4C89zAtSlpp6YtH8/EJGk5MWZUTtbQdB04t89/1O/w1cDnyilFU=",
						"Mutsuko", "Orino",
						"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg",
						"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRTH27Sxx6jQ5IraidAQovMU1OTnQWL-hqfN0kiEF5JoRXVoQ8N-g");
				/***********************************************/
			}

		}
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

		return json;

	}

	// Method for send carousel template message to user
	private void carouselForUser(String userId, String lChannelAccessToken, String nameSatff1, String nameSatff2,
			String poster1_url, String poster2_url) {
		CarouselTemplate carouselTemplate = new CarouselTemplate(
				Arrays.asList(
						new CarouselColumn(poster1_url, nameSatff1, "Select one for more info",
								Arrays.asList(new MessageAction("Call", "Call \"" + nameSatff1 + "\""),
										new MessageAction("Send email", "Send email \"" + nameSatff1 + "\""),
										new MessageAction("Availability of",
												"Availability of \"" + nameSatff1 + "\""))),
						new CarouselColumn(poster2_url, nameSatff2, "Select one for more info", Arrays.asList(
								new MessageAction("Call", "Call \"" + nameSatff2 + "\""),
								new MessageAction("Send email", "Send email \"" + nameSatff2 + "\""),
								new MessageAction("Availability of", "Availability of \"" + nameSatff2 + "\"")))));
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
