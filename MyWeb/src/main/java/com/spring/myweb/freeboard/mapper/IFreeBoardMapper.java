package com.spring.myweb.freeboard.mapper;

import java.util.List;

import com.spring.myweb.freeboard.entity.FreeBoard;

public interface IFreeBoardMapper {
	
	//글 등록
	void regist(FreeBoard freeBoard);
	
	//글 목록
	List<FreeBoard> getList();
	
	//상세보기
	FreeBoard getContent(int bno);
	
	//수정
	void update(FreeBoard freeBoard);
	
	//삭제
	void delete(int bno);
	
}








