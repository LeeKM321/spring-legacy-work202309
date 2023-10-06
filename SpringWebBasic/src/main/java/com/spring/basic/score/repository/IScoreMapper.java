package com.spring.basic.score.repository;

import java.util.List;

import com.spring.basic.score.entity.Score;

public interface IScoreMapper {

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











