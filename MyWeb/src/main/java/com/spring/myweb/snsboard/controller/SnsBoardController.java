package com.spring.myweb.snsboard.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myweb.snsboard.dto.SnsBoardRequestDTO;
import com.spring.myweb.snsboard.dto.SnsBoardResponseDTO;
import com.spring.myweb.snsboard.service.SnsBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/snsboard")
@RequiredArgsConstructor
@Slf4j
public class SnsBoardController {

	private final SnsBoardService service;
	
	@GetMapping("/snsList")
	public ModelAndView snsList() {
		ModelAndView mv = new ModelAndView();
//		mv.addObject("name", "value");
		mv.setViewName("snsboard/snsList");
		return mv;
	}
	
	@PostMapping()
	public String upload(SnsBoardRequestDTO dto) {
		service.insert(dto);
		return "uploadSuccess";
	}
	
	@GetMapping("/{page}")
	public List<SnsBoardResponseDTO> getList(@PathVariable int page) {
//		System.out.println("/snsboard/getList: GET!");
		log.info("/snsboard/getList: GET!");
		return service.getList(page);		
	}
	
	/*
	# 게시글의 이미지 파일 전송 요청
	이 요청은 img 태그의 src 속성을 통해서 요청이 들어오고 있습니다.
	snsList.jsp 페이지가 로딩되면서, 글 목록을 가져오고 있고, JS를 이용해서 화면을 꾸밀 때
	img 태그의 src에 작성된 요청 url을 통해 자동으로 요청이 들어오게 됩니다.
	요청을 받아주는 메서드를 선언하여 경로에 지정된 파일을 보낼 예정입니다.
	*/
	@GetMapping("/display/{fileLoca}/{fileName}")
	public ResponseEntity<?> getImage(@PathVariable String fileLoca, @PathVariable String fileName) {
//		System.out.println("fileLoca: " + fileLoca);
//		System.out.println("fileName: " + fileName);
		log.info("fileLoca: " + fileLoca);
		log.info("fileName: {}", fileName);
		
		File file = new File("C:/test/upload/" + fileLoca + "/" + fileName);
		System.out.println(file.toString()); // 완성된 경로.
		
		//응답에 대한 여러가지 정보를 전달할 수 있는 객체 ResponseEntity
		//응답 내용, 응답이 성공했는지에 대한 여부, 응답에 관련된 여러 설정들을 지원합니다.
		ResponseEntity<byte[]> result = null;
		
		HttpHeaders headers = new HttpHeaders(); //응답 헤더 객체 생성.
		
		try {
			//probeContentType: 매개값으로 전달받은 파일의 타입이 무엇인지를 문자열로 반환.
			//사용자에게 보여주고자 하는 데이터가 어떤 파일인지에 따라 응답 상태 코드를 다르게 리턴하고
			//컨텐트 타입을 제공해서 화면단에서 판단할 수 있게 처리할 수 있다.
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			headers.add("value", "hello");
			
			//ResponseEntity 객체에 전달하고자 하는 파일을 byte[]로 변환해서 전달. (파일의 손상을 막기 위해)
			//header 내용도 같이 포함, 응답 상태 코드를 원하는 형태로 전달이 가능.
			
			// 생성자를 이용하여 ResponseEntity 생성하는 법.
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
			// static 메서드를 활용하여 ResponseEntity 객체를 생성하는 법.
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return result;
	}
	
	//다운로드 요청
	@GetMapping("/download/{fileLoca}/{fileName}")
	public ResponseEntity<?> download(@PathVariable String fileLoca, 
									  @PathVariable String fileName) {
		
		File file = new File("C:/test/upload/" + fileLoca + "/" + fileName);
		
		ResponseEntity<byte[]> result = null;
		
		HttpHeaders header = new HttpHeaders();
		
		//응답하는 본문을 브라우저가 어떻게 표시해야 할 지 알려주는 헤더 정보를 추가합니다.
		//inline인 경우 웹 페이지 화면에 표시되고, attachment인 경우 다운로드를 제공합니다.
		
		//request객체의 getHeader("User-Agent") -> 단어를 뽑아서 확인
		//ie: Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko  
		//chrome: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36
	
		//파일명 한글처리(Chrome browser) 크롬
		//header.add("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") );
		//파일명 한글처리(Edge) 엣지 
		//header.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
		//파일명 한글처리(Trident) IE
		//Header.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " "));

		header.add("Content-Disposition", "attachment; filename=" + fileName);
		
		try {
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			result = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return result;
	}
	
	
	@GetMapping("/content/{bno}")
	public ResponseEntity<?> getContent(@PathVariable int bno) {
		return ResponseEntity.ok().body(service.getContent(bno));
	}
	
	@DeleteMapping("/{bno}")
	public ResponseEntity<?> delete(@PathVariable int bno, HttpSession session) {
		
		String id = (String) session.getAttribute("login");
		SnsBoardResponseDTO dto = service.getContent(bno);
		
		if(id == null || !id.equals(dto.getWriter())) {
//			return "noAuth";
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		service.delete(bno);
		
		//글이 삭제되었다면 더 이상 이미지도 존재할 필요가 없으므로
		//이미지도 함께 삭제해 주셔야 합니다.
		//File 객체 생성 -> 생성자에 지우고자 하는 파일의 경로 지정
		//메서드 delete() -> return type이 boolean. 삭제 성공 시 true, 실패 시 false.
		File file = new File(dto.getUploadPath() + dto.getFileLoca() + "/" + dto.getFileName());
		return file.delete() ? 
				ResponseEntity.status(HttpStatus.OK).build() 
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	//좋아요 버튼 클릭 처리
	@PostMapping("/like")
	public String likeConfirm(@RequestBody Map<String, String> params) {
		log.info("/like: POST, params: {}", params);
		
		return service.searchLike(params);	
	}
	
	//회원이 글 목록으로 진입 시 좋아요 게시물 리스트 체크
	@GetMapping("/likeList/{userId}")
	public List<Integer> likeList(@PathVariable String userId) {
		log.info("/snsboard/likeList: GET, userId: {}", userId);
		return service.likeList(userId);
	}
	
	
	
	
	
	
	
	
}














