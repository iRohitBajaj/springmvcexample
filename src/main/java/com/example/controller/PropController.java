package com.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping("/properties")
public class PropController
{
	@Value("${key1}")
	private String val1;

	@Value("${key2}")
	private String val2;


	@RequestMapping(value = "/getAllProps", method = RequestMethod.GET)
	public String getAllProps(Model model)
	{
		ArrayList<String> arrList = new ArrayList<>();
		arrList.add(val1);
		arrList.add(val2);
		model.addAttribute("properties", arrList);
		arrList.forEach( entry -> System.out.println("key value is : "+entry.toString()));
		return "propsListDisplay";
	}
}