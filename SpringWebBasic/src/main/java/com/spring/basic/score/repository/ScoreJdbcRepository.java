package com.spring.basic.score.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.basic.score.entity.Grade;
import com.spring.basic.score.entity.Score;

@Repository("jdbc")
public class ScoreJdbcRepository implements IScoreRepository {
	
	//데이터베이스 접속에 필요한 정보들을 변수화. (데이터베이스 주소, 계정명, 비밀번호)
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "hr";
	private String password = "hr";
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	//데이터베이스 연동을 전담하는 객체는 무분별한 객체 생성을 막기 위해
	//싱글톤 디자인 패턴을 구축하는 것이 일반적.
	//우리는 Spring Framework를 사용 중 -> 컨테이너 내의 객체들을 기본적으로 Singleton으로 유지.
	
	
	//객체가 생성될 때 오라클 데이터베이스 커넥터 드라이버를 강제 구동해서 연동 준비.
	public ScoreJdbcRepository() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public List<Score> findAll() {
		
		//조회된 정보를 모두 담아서 한번에 리턴하기 위한 리스트.
		List<Score> scoreList = new ArrayList<>();
		
		//1.
		String sql = "SELECT * FROM score";
		
		try {
			//2.
			conn = DriverManager.getConnection(url, username, password);
			
			//3.
			pstmt = conn.prepareStatement(sql);
			
			//4. -> 물음표 없으므로 생략
			
			//5. 실행하고자 하는 sql이 SELECT인 경우에는 
			//INSERT, UPDATE, DELETE와는 다른 메서드를 호출합니다.
			//메서드의 실행 결과는 sql의 조회 결과를 들고 있는 객체 ResultSet이 리턴됩니다.
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // 조회된 행이 하나라도 존재한다면 true, 존재하지 않는다면 false.
				//타겟으로 잡힌 행의 데이터를 얻어옵니다.
				Score s = new Score(
							rs.getInt("stu_num"),
							rs.getString("stu_name"),
							rs.getInt("kor"),
							rs.getInt("eng"),
							rs.getInt("math"),
							rs.getInt("total"),
							rs.getDouble("average"),
							Grade.valueOf(rs.getString("grade"))
						);
				scoreList.add(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//6.
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return scoreList;
	}

	@Override
	public void save(Score score) {
		try {
			//1. sql을 문자열로 준비해 주세요.
			//변수 또는 객체에 들어있는 값으로 sql을 완성해야 한다면, 해당 자리에 ?를 찍어 주세요.
			String sql = "INSERT INTO score "
						+ "VALUES(score_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";
			
			//2. 데이터베이스에 접속을 담당하는 Connection 객체를 메서드를 통해 받아옵니다.
			//접속 정보를 함께 전달합니다.
			conn = DriverManager.getConnection(url, username, password);
			
			//3. 이제 접속을 할 수 있게 됐으니, SQL을 실행할 수 있는 PreparedStatement를 받아옵시다.
			//직접 생성하지 않고, 메서드를 통해 받아옵니다.
			pstmt = conn.prepareStatement(sql);
			
			//4. sql이 아직 완성되지 않았기 때문에, 물음표를 채워서 sql을 완성 시킵시다.
			// sql을 pstmt에게 전달했기 때문에 pstmt 객체를 이용해서 ?를 채웁니다.
			pstmt.setString(1, score.getStuName());
			pstmt.setInt(2, score.getKor());
			pstmt.setInt(3, score.getEng());
			pstmt.setInt(4, score.getMath());
			pstmt.setInt(5, score.getTotal());
			pstmt.setDouble(6, score.getAverage());
			pstmt.setString(7, String.valueOf(score.getGrade()));
			
			//5. sql을 다 완성했다면, pstmt에게 sql을 실행하라는 명령을 내립니다.
			int rn = pstmt.executeUpdate(); //리턴 타입이 int -> sql 실행 성공 시 1, 실패 시 0
			if(rn == 1) {
				System.out.println("INSERT 성공!");
			} else {
				System.out.println("INSERT 실패!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//6. sql 실행까지 마무리가 되었다면, 사용했던 객체들을 해제합니다.
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public Score findByStuNum(int stuNum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByStuNum(int stuNum) {
		
		String sql = "DELETE FROM score WHERE stu_num = ?";
		try {
			conn = DriverManager.getConnection(url, username, password);
			conn.setAutoCommit(false); // 오토커밋 취소
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stuNum);
			
			int result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("DELETE 성공!");
				conn.commit();
			} else {
				System.out.println("DELETE 실패!");
				conn.rollback();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close(); conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void modify(Score modScore) {
		// TODO Auto-generated method stub

	}

}
