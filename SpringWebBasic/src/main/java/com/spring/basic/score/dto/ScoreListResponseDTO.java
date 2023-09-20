package com.spring.basic.score.dto;

import com.spring.basic.score.entity.Grade;
import com.spring.basic.score.entity.Score;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

// Entity는 요청이나 응답을 담는 값으로 쓰지 말라 했죠?
// 데이터베이스에서 조회된 값을 화면으로 응답할 때, 해당 화면이 무엇이냐에 따라
// 조회된 값을 가공하거나 추가하거나 제외하고 전달해야 할 필요성이 있기 때문에
// 그에 맞는 응답 DTO를 생성해서 전달하는 것이 각자의 역할을 침해하지 않는 설계일 것입니다.
@Getter @ToString @EqualsAndHashCode
public class ScoreListResponseDTO {
	
	private int stuNum;
	private String maskingName;
	private double average;
	private Grade grade;
	
	public ScoreListResponseDTO(Score s) {
		this.stuNum = s.getStuNum();
		this.maskingName = makeMaskingName(s.getStuName());
		this.average = s.getAverage();
		this.grade = s.getGrade();
	}
	
	// 이름을 첫 글자만 빼고 나머지를 *로 처리하기
	private String makeMaskingName(String originalName) {
		// valueOf(): 다른 타입을 문자열로 변환하는 메서드 
		String maskingName = String.valueOf(originalName.charAt(0));
		for(int i=1; i<originalName.length(); i++) {
			maskingName += "*";
		}
		return maskingName;
	}
	

}










