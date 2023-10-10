<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h2>게시글 등록</h2>
	<!-- 
		form 태그에 action이 생략되어 있는 경우에는 마지막 요청 url을 재활용 합니다.
		ex) 지금 보고있는 게시글 등록 화면 요청 url -> /board/write: GET
		    작성 후 등록 버튼 클릭 -> action이 생략되어 있는 상태 -> 마지막 요청인 /board/write: POST
	 -->
	<form method="post">
		<p>
			# 작성자: <input type="text" name="writer"> <br>
			# 제목: <input type="text" name="title"> <br>
			# 내용: <textarea rows="3" name="content"></textarea> <br>
			<input type="submit" value="등록"> 
		</p>
	</form>

</body>
</html>
















