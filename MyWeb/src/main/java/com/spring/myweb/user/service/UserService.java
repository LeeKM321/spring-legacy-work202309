package com.spring.myweb.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.user.dto.UserInfoResponseDTO;
import com.spring.myweb.user.dto.UserJoinRequestDTO;
import com.spring.myweb.user.entity.User;
import com.spring.myweb.user.mapper.IUserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final IUserMapper mapper;
	private final BCryptPasswordEncoder encoder;

	public int idCheck(String account) {
		return mapper.idCheck(account);
	}

	public void join(UserJoinRequestDTO dto) {
		//회원 비밀번호 암호화 인코딩
		System.out.println("암호화 하기 전 비번: " + dto.getUserPw());
		
		//비밀번호를 암호화해서 dto에 다시 저장하기
		String securePw = encoder.encode(dto.getUserPw());
		System.out.println("암호화 후 비번: " + securePw);
		dto.setUserPw(securePw);
		
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

	public String login(String userId, String userPw) {
		String dbPw = mapper.login(userId);
		if(dbPw != null) {
			//날것의 비밀번호와 암호화된 비밀번호의 일치 여부를 알려주는 matches()
			if(encoder.matches(userPw, dbPw)) {
				return userId;
			}
		}
		return null;
	}

	public UserInfoResponseDTO getInfo(String id) {
		User user = mapper.getInfo(id);
		return UserInfoResponseDTO.toDTO(user);	
	}
	
	
}












