package com.example.controller;

import com.example.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
@RequestMapping("/properties")
@RefreshScope
public class PropViewController
{
	@Value("${key1}")
	private String val1;

	@Value("${key2}")
	private String val2;

	@Value("${key3}")
	private String val3;

	@Value("${key4}")
	private String val4;

	@Value("${key5}")
	private String val5;

	@Value("${key6:junkvalue}")
	private String val6;

	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(value = "/getConfigProps", method = RequestMethod.GET)
	public String getConfigProps(Model model)
	{
		TestDao dao = (TestDao)applicationContext.getBean("dataSource");
		//TestDao dao = (TestDao) context.getBean("dataSource");
		dao.connect();

		ArrayList<String> arrList = new ArrayList<>();
		arrList.add(val1);
		arrList.add(val2);
		arrList.add(val3);
		arrList.add(val4);
		arrList.add(val5);
		arrList.add(val6);
		arrList.add(dao.getUrl());
		arrList.add(dao.getUsername());
		arrList.add(dao.getPassword());
		model.addAttribute("properties", arrList);
		System.out.println(arrList.toString());
		return "propsListDisplay";
	}

}