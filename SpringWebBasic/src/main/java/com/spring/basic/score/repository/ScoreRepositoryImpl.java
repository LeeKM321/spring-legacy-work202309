package com.spring.basic.score.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.spring.basic.score.dto.ScoreRequestDTO;
import com.spring.basic.score.entity.Score;

//@Component -> 3계층 이외의 객체를 빈으로 등록할 때 사용.
@Repository // 빈 등록 -> Service가 의존하는 객체니까
public class ScoreRepositoryImpl implements IScoreRepository {

	// key: 학번, value: 성적 정보를 담은 객체
	private static final Map<Integer, Score> SCORE_MAP;
	
	// 학번에 사용할 일련번호
	private static int sequence;
	
	static {
		SCORE_MAP = new HashMap<>();
		Score stu1 = new Score(new ScoreRequestDTO("뽀로로", 100, 34, 91));
		Score stu2 = new Score(new ScoreRequestDTO("춘식이", 77, 99, 87));
		Score stu3 = new Score(new ScoreRequestDTO("대길이", 98, 66, 85));
		
		stu1.setStuNum(++sequence);
		stu2.setStuNum(++sequence);
		stu3.setStuNum(++sequence);
		
		SCORE_MAP.put(stu1.getStuNum(), stu1);
		SCORE_MAP.put(stu2.getStuNum(), stu2);
		SCORE_MAP.put(stu3.getStuNum(), stu3);
	}
	
	@Override
	public List<Score> findAll() {
		// SCORE_MAP은 번호가 key, 객체가 value로 이루어져 있음.
		// 객체들만 전부 뽑아서 List로 만들 것이기 때문에 
		// SCORE_MAP에서 value들만 전부 뽑아낸 뒤, ArrayList의 생성자의 매개값으로 전달해서
		// List로 포장.
		List<Score> scoreList = new ArrayList<>(SCORE_MAP.values());
		return scoreList;
	}

	@Override
	public void save(Score score) {
		score.setStuNum(++sequence);
		SCORE_MAP.put(score.getStuNum(), score);
	}
	
	@Override
	public Score findByStuNum(int stuNum) {
		return SCORE_MAP.get(stuNum);
	}
	
	@Override
	public void deleteByStuNum(int stuNum) {
		SCORE_MAP.remove(stuNum);
	}
	
	@Override
	public void modify(Score modScore) {
		SCORE_MAP.put(modScore.getStuNum(), modScore);
		
	}

}







