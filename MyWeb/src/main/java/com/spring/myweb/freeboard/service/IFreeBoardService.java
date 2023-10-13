package com.spring.myweb.freeboard.service;

import java.util.List;

import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.freeboard.dto.request.FreeModifyRequestDTO;
import com.spring.myweb.freeboard.dto.request.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.dto.response.FreeDetailResponseDTO;
import com.spring.myweb.freeboard.dto.response.FreeListResponseDTO;

public interface IFreeBoardService {

	//글 등록
	void regist(FreeRegistRequestDTO dto);

	//글 목록
	List<FreeListResponseDTO> getList(Page page);
	
	//총 게시물 개수
	int getTotal(Page page);

	//상세보기
	FreeDetailResponseDTO getContent(int bno);

	//수정
	void update(FreeModifyRequestDTO freeBoard);

	//삭제
	void delete(int bno);

}
