package com.spring.myweb.freeboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.myweb.freeboard.dto.FreeModifyRequestDTO;
import com.spring.myweb.freeboard.dto.FreeRegistRequestDTO;
import com.spring.myweb.freeboard.service.IFreeBoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {
	
	private final IFreeBoardService service;
	
	//목록 화면
	@GetMapping("/freeList")
	public void freeList(Model model) {
		System.out.println("/freeboard/freeList: GET!");
		
		model.addAttribute("boardList", service.getList());
	}
	
	//글쓰기 페이지를 열어주는 메서드
	@GetMapping("/freeRegist")
	public void regist() {}
	
	//글 등록 처리
	@PostMapping("/freeRegist")
	public String regist(FreeRegistRequestDTO dto) {
		service.regist(dto);
		return "redirect:/freeboard/freeList";
	}
	
	//글 상세보기 처리
	@GetMapping("/content")
	public String getContent(int bno, Model model) {
		model.addAttribute("article", service.getContent(bno));
		return "freeboard/freeDetail";
	}
	
	//글 수정 요청
	@PostMapping("/modify")
	public String modify(FreeModifyRequestDTO dto) {
		service.update(dto);
		return "redirect:/freeboard/content?bno=" + dto.getBno();
	}
	
	//글 삭제 요청
	@PostMapping("/delete")
	public String delete(int bno) {
		service.delete(bno);
		return "redirect:/freeboard/freeList";
	}
	
	

}












