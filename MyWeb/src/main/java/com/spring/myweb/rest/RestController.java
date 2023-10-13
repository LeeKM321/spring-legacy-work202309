package com.spring.myweb.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest")
public class RestController {
	
	@GetMapping("/view")
	public String viewPage() {
		return "test/test1";
	}
	
	@PostMapping("/object")
	public String object() {
		System.out.println("비동기 방식의 요청 들어옴!");
		
		return null;
	}

}














