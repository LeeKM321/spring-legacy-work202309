package com.spring.myweb.user.service;

import org.springframework.stereotype.Service;

import com.spring.myweb.user.dto.UserJoinRequestDTO;
import com.spring.myweb.user.entity.User;
import com.spring.myweb.user.mapper.IUserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final IUserMapper mapper;

	public int idCheck(String account) {
		return mapper.idCheck(account);
	}

	public void join(UserJoinRequestDTO dto) {
		//dto를 entity로 변환
		User user = User.builder()
				.userId(dto.getUserId())
				.userPw(dto.getUserPw())
				.userName(dto.getUserName())
				.userPhone1(dto.getUserPhone1())
				.userPhone2(dto.getUserPhone2())
				.userEmail1(dto.getUserEmail1())
				.userEmail2(dto.getUserEmail2())
				.addrBasic(dto.getAddrBasic())
				.addrDetail(dto.getAddrDetail())
				.addrZipNum(dto.getAddrZipNum())
				.build();
		mapper.join(user);
	}

	public boolean login(String userId, String userPw) {
		String loginPw = mapper.login(userId);
		if(loginPw.equals(userPw)) {
			return true;
		} else {
			return false;
		}
	}
	
	
}












