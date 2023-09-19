package com.spring.basic.score.entity;

import com.spring.basic.score.dto.ScoreRequestDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 Entity 클래스
 - 실제 데이터베이스에 저장된 테이블(값의 모음) 형태와 1:1로 매칭되는 클래스.
 - DB 테이블 내에 존재하는 속성만을 필드로 가져야 합니다.
 - 상속이나 구현체 여서는 안되고, 존재하지 않는 컬럼값을 가지는 것도 안됩니다. (가장 pure한 객체)
 - 절대로 요청이나 응답값을 전달하는 클래스로 사용하지 않습니다. (DTO의 역할)
 */

@Setter @Getter @ToString
@EqualsAndHashCode 
@NoArgsConstructor
@AllArgsConstructor
public class Score {

	private String stuName; //학생 이름
	private int kor, eng, math; // 국, 영, 수 점수

	private int stuNum; //학번
	private int total; //총점
	private double average; //평균
	private Grade grade; //학점

	public Score(ScoreRequestDTO dto) {
		this.stuName = dto.getName();
		changeScore(dto);
	}

	public void changeScore(ScoreRequestDTO dto) {
		this.kor = dto.getKor();
		this.eng = dto.getEng();
		this.math = dto.getMath();
		calcTotalAndAvg(); // 총점, 평균 계산
		calcGrade(); // 학점 계산
	}

	private void calcGrade() {
		if (average >= 90) {
			this.grade = Grade.A;
		} else if (average >= 80) {
			this.grade = Grade.B;
		} else if (average >= 70) {
			this.grade = Grade.C;
		} else if (average >= 60) {
			this.grade = Grade.D;
		} else {
			this.grade = Grade.F;
		}
	}

	private void calcTotalAndAvg() {
		this.total = kor + eng + math;
		this.average = total / 3.0;
	}


}








