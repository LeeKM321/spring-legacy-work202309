package com.spring.myweb.user.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.user.entity.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/db-config.xml")
public class UserMapperTest {
	
	@Autowired
	private IUserMapper mapper;
	
	@Test
	@DisplayName("회원 가입을 진행했을 때 회원가입이 성공해야 한다.")
	void registTest() {
		User user = User.builder()
				.userId("kim1234")
				.userPw("kkk1111!")
				.userName("김철수")
				.userEmail1("kim1234")
				.userEmail2("gmail.com")
				.build();
		mapper.join(user);
	}
	
	@Test
	@DisplayName("존재하는 회원 아이디를 조회했을 시 1이 리턴되어야 한다.")
	void checkIdTest() {
		String id = "abc1234";
		
		assertEquals(1, mapper.idCheck(id));
	}
	
	@Test
	@DisplayName("존재하는 회원 아이디를 입력 했을 시 그 회원의 비밀번호가 리턴되어야 한다.")
	void loginTest() {
		String id = "abc1234";
		
		assertNotNull(mapper.login(id));
		assertEquals("aaa1111!", mapper.login(id));
	}
	
	@Test
	@DisplayName("존재하지 않는 회원의 아이디를 전달하면 null이 올 것이다.")
	void getInfoTest() {
		assertNull(mapper.getInfo("merong"));
	}
	
	@Test
	@DisplayName("id를 제외한 회원의 정보를 수정할 수 있어야 한다.")
	void updateTest() {
		User user = User.builder()
				.userId("abc1234")
				.userPw("aaa1111!")
				.userName("홍수정")
				.userEmail1("abc4321")
				.userEmail2("gmail.com")
				.addrBasic("서울특별시 강동구")
				.addrDetail("풍성로41길 64-19")
				.addrZipNum("05383")
				.build();
		mapper.updateUser(user);
		
		assertEquals(user.getUserEmail1(), mapper.getInfo("abc1234").getUserEmail1());
	}
	
	
	
	
	

}









