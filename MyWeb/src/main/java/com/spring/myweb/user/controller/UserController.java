package com.spring.myweb.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.myweb.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService service;
	
	//회원가입 페이지로 이동
	@GetMapping("/userJoin")
	public void userJoin() {}
	
	//아이디 중복 확인(비동기)
	/*
	 @PathVariable은 URL 경로에 변수를 포함시켜 주는 방식
	 null이나 공백이 들어갈 수 있는 파라미터라면 적용하지 않는 것을 추천
	 파라미터 값에 .이 포함되어 있다면 .뒤의 값은 잘린다는 것을 알아두세요.
	 {}안에 변수명을 지어주시고, @PathVariable 괄호 안에 영역을 지목해서
	 값을 받아옵니다.
	 */
	@GetMapping("/{account}")
	@ResponseBody
	public String idCheck(@PathVariable String account) {
		System.out.println("클라이언트로부터 전달된 아이디: " + account);
		int result = service.idCheck(account);
		if(result == 1) return "duplicated";
		else return "ok";
	}
	
	
	

}










