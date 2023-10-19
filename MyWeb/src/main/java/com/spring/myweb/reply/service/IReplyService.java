package com.spring.myweb.reply.service;

import java.util.List;

import com.spring.myweb.reply.dto.ReplyListResponseDTO;
import com.spring.myweb.reply.dto.ReplyRegistDTO;
import com.spring.myweb.reply.entity.Reply;

public interface IReplyService {
	
	void replyRegist(ReplyRegistDTO dto);//댓글 등록
	List<ReplyListResponseDTO> getList(int bno, int pageNum);//목록 요청
	int getTotal(int bno); //댓글 개수 (페이징, PageCreator는 사용하지 않습니다.)
	
	String pwCheck(int rno);//비밀번호 확인
	void update(Reply reply);//댓글 수정
	void delete(int rno);//댓글 삭제

}
