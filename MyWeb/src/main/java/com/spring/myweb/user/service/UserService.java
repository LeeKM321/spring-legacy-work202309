package com.spring.myweb.user.service;

import org.springframework.stereotype.Service;

import com.spring.myweb.user.mapper.IUserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final IUserMapper mapper;

	public int idCheck(String account) {
		return mapper.idCheck(account);
	}
	
	
}












