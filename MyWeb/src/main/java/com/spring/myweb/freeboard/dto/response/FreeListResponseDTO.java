package com.spring.myweb.freeboard.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.spring.myweb.freeboard.entity.FreeBoard;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// 클라이언트 측으로 게시글 목록을 줄 때의 스펙 정의.
@Getter @ToString @EqualsAndHashCode
public class FreeListResponseDTO {

	private int bno;
	private String title;
	private String writer;
	private String date;

	public FreeListResponseDTO(FreeBoard board) {
		super();
		this.bno = board.getBno();
		this.title = board.getTitle();
		this.writer = board.getWriter();
		this.date = makePrettierDateString(board.getRegDate());
	}

	static String makePrettierDateString(LocalDateTime regDate) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		return dtf.format(regDate);
	}



	
}








