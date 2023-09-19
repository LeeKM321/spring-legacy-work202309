package com.spring.basic.score.dto;

import lombok.*;

/*
 DTO (Data Transfer Object): 데이터 전송(이동) 객체라는 의미
 - 계층간 데이터 교환을 위한 객체.
 - 로직을 갖고 있지 않은 순수한 데이터 객체로 활용. getter / setter 메서드만 갖는다.
 
 VO (Value Object): 좀 더 특정한 결과 값을 담는 객체.
 값 자체를 표현하기 때문에 객체의 불변성을 보장해야 하며 setter 메서드는 갖지 않는 것을 권장.
 */

@Setter @Getter @ToString
@NoArgsConstructor 
@AllArgsConstructor
@EqualsAndHashCode
public class ScoreRequestDTO {
	
	private String name; //학생 이름
	private int kor, eng, math; //국, 영, 수 점수

}
