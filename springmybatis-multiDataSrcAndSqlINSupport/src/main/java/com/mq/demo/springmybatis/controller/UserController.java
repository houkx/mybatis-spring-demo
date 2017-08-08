/**
 * 
 */
package com.mq.demo.springmybatis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mq.demo.springmybatis.service.UserCountService;

@Controller
public class UserController {
	@Resource
	private UserCountService userCountService;

	@RequestMapping("/")
	@ResponseBody
	String home() {
		return "userCount=" + userCountService.countUsers();
	}

	@RequestMapping("/admin")
	@ResponseBody
	String a() {
		return "adminCount=" + userCountService.countAdmins();
	}

	@RequestMapping("/add")
	@ResponseBody
	boolean addUser(@RequestParam("name") String name) {
		userCountService.addUser(name);
		return true;
	}
	@RequestMapping("/addAdmin")
	@ResponseBody
	boolean addAdmin(@RequestParam("name") String name) {
		userCountService.addAdmin(name);
		return true;
	}
}
