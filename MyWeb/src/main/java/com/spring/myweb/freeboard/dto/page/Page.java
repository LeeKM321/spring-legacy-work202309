package com.spring.myweb.freeboard.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
@Builder
public class Page {
	
	private int pageNo; // 클라이언트가 보낸 페이지 번호
	private int amount; // 한 페이지에 보여질 게시물 수
	
	//검색 요청에 필요한 필드를 추가
	private String keyword;
	private String condition;
	
	public Page() {
		this.pageNo = 1;
		this.amount = 10;
	}
	
	public void setPageNo(int pageNo) {
		if(pageNo < 1 || pageNo > Integer.MAX_VALUE) {
			this.pageNo = 1;
			return;
		}
		this.pageNo = pageNo;
	}
	
	public void setAmount(int amount) {
		if(amount < 10 || amount > 30 || (amount % 10 != 0)) {
			this.amount = 10;
			return;
		}
		this.amount = amount;
	}
	
	public int getPageStart() {
		/*
		 pageNo: 1 -> return 0
		 pageNo: 2 -> return 10
		 pageNo: 3 -> return 20
		 pageNo: 4 -> return 30
		 */
		return (pageNo - 1) * amount;
	}
	
	public int getPageEnd() {
		return pageNo * amount;
	}
	

}














