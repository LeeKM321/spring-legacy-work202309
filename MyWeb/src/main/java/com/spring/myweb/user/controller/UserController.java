package com.spring.myweb.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//회원가입 페이지로 이동
	@GetMapping("/userJoin")
	public void userJoin() {}

}










