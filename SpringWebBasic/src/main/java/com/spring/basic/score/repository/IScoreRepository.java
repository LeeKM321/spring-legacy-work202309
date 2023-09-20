package com.spring.basic.score.repository;

import java.util.List;

import com.spring.basic.score.entity.Score;

// 역할 명세(추상화):
// 성적 정보를 잘 저장하고, 잘 조회하고, 잘 삭제하고, 잘 수정해야 한다.
// Score에 관련된 여러가지 동작들을 명세하여 구현하는 클래스들이
// 동일한 동작을 약속하게 하자.
public interface IScoreRepository {
	
	//성적 정보 전체 조회
	List<Score> findAll();
	
	//성적 정보 등록
	void save(Score score);

	//성적 정보 개별 조회
	Score findByStuNum(int stuNum);

	//성적 정보 삭제
	void deleteByStuNum(int stuNum);

	//성적 정보 수정
	void modify(Score modScore);
	
	

}
















