package com.spring.myweb.freeboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.freeboard.entity.FreeBoard;
import com.spring.myweb.freeboard.mapper.IFreeBoardMapper;


@ExtendWith(SpringExtension.class) //테스트 환경을 만들어 주는 Junit5 객체 로딩
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/db-config.xml"})
public class FreeBoardMapperTest {
	
	@Autowired
	private IFreeBoardMapper mapper;
	
	//단위 테스트 (unit test) - 가장 작은 단위의 테스트 (기능별 테스트 -> 메서드별 테스트)
	//테스트 시나리오
	//- 단언(Assertion) 기법
	
	@Test
	@DisplayName("Mapper 계층의 regist를 호출하면서"
			+ "FreeBoard를 전달하면 데이터가 INSERT 될 것이다.")
	void registTest() {
		//given - when - then 패턴을 따릅니다. (권장 사항)
		
		//given: 테스트를 위해 주어질 데이터 세팅 (parameter) - 지금은 생략
		
		for(int i=1; i<=300; i++) {
			//when: 테스트 실제 상황 세팅
			mapper.regist(FreeBoard.builder()
									.title("페이징 테스트 제목 " + i)
									.writer("page1234")
									.content("테스트 내용입니다. " + i)
									.build());
		}
		
//		mapper.regist(FreeBoard.builder()
//				.title("메롱메롱")
//				.writer("kim1234")
//				.content("테스트 중이니까 조용히 하세요!")
//				.build());
		
		//then: 테스트 결과를 확인.	
	}
	
	@Test
	@DisplayName("조회 시 전체 글 목록이 올 것이고, 조회된 글의 개수는 11개일 것이다.")
	void getListTest() {
		
		//when
		List<FreeBoard> list = mapper.getList(Page.builder()
													.pageNo(4)
													.amount(10)
													.build());
		for(FreeBoard board : list) {
			System.out.println(board);
		}
		System.out.println("조회된 글의 개수: " + list.size());
		
		//then
		assertEquals(list.size(), 10);
		
	}
	
	@Test
	@DisplayName("글 번호가 11번인 글을 조회하면 "
			+ "글쓴이는 'kim1234'일 것이고, 글 제목은 '메롱메롱' 일 것이다.")
	void getContentTest() {
		
		//given
		int bno = 11;
		
		//when
		FreeBoard board = mapper.getContent(bno);
		
		//then
		assertEquals(board.getWriter(), "kim1234");
		assertTrue(board.getTitle().equals("메롱메롱"));	
	}
	
	//글 번호가 1번인 글의 제목과 내용을 수정 후 다시 조회했을 때
	//수정한 제목과 내용으로 바뀌었음을 단언하세요.
	@Test
	@DisplayName("글 번호가 1번인 글의 제목과 내용을 수정 후 다시 조회했을 때 "
			+ "수정한 제목과 내용으로 바뀌었음을 단언한다.")
	void updateTest() {
		//given
		FreeBoard board = FreeBoard.builder()
				.bno(1)
				.title("수정된 글 제목")
				.content("수정된 글 내용내용")
				.build();
		
		//when
		mapper.update(board);
		
		//then
		FreeBoard b = mapper.getContent(board.getBno()); //DB에서 조회결과 얻어오기.
		assertEquals(board.getTitle(), b.getTitle());
		assertEquals(board.getContent(), b.getContent());
	}
	
	//글 번호가 2번인 글을 삭제한 후에 전체 목록을 조회했을 때
	//글의 개수는 11개일 것이고
	//2번 글을 조회했을 때 null이 리턴됨을 단언하세요. -> assertNull(객체)
	
	@Test
	@DisplayName("글 번호가 2번인 글을 삭제한 후에 전체 목록을 조회했을 때"
			+ "	글의 개수는 11개일 것이고"
			+ "	2번 글을 조회했을 때 null이 리턴되어야 한다.,")
	void deleteTest() {
		//given
		int bno = 2;
		
		//when
		mapper.delete(bno);
		
		//then
//		assertEquals(mapper.getList().size(), 11);
		assertNull(mapper.getContent(bno));
	}
	
	

}











