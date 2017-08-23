package com.example.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	// @RequestMapping(value = "/webhook", method = RequestMethod.POST)
	// private Map<String,Object> webhook(@RequestParam("shipping-zone") String
	// shippingZone, @RequestParam("action") String action) throws JSONException {
	//
	//
	//
	// Map<String,Object> json = new HashMap<String,Object>();
	//
	// json.put("speech", " The cost of shipping to is 1000 euros.");
	// json.put("displayText", " The cost of shipping to is 1000 euros.");
	//
	// json.put( "source", "apiai-onlinestore-shipping");
	// System.out.println("*************
	// "+shippingZone+"******************"+action);
	// return json;
	//
	// }

	@RequestMapping(value = "/webhook", method = RequestMethod.POST)
	private @ResponseBody Map<String, Object> webhook(@RequestBody Map<String, Object> obj) throws JSONException {

		Map<String, Object> json = new HashMap<String, Object>();
		System.out.println("************RequestBody*****************" + obj);

		String Speech = "The cost of shipping to  is  for " + obj.get("result");
		/*
		 * json.put("speech", " The cost of shipping to  is  1000 euros.");
		 * json.put("displayText", " The cost of shipping to  is  1000 euros.");
		 * json.put( "source", "apiai-onlinestore-shipping");
		 */
		json.put("type", "image");
		json.put("originalContentUrl",
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");
		json.put("previewImageUrl",
				"https://i.pinimg.com/736x/96/a0/54/96a0544ab7b6fa7cbdddff9c5d8397be--japanese-hairstyles-korean-hairstyles.jpg");

		System.out.println("************* ******************" + obj.get("result"));
		return json;

	}

}
