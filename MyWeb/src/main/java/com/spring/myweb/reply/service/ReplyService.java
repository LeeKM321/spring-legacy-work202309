package com.spring.myweb.reply.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.myweb.freeboard.dto.page.Page;
import com.spring.myweb.reply.dto.ReplyListResponseDTO;
import com.spring.myweb.reply.dto.ReplyRequestDTO;
import com.spring.myweb.reply.dto.ReplyUpdateRequestDTO;
import com.spring.myweb.reply.entity.Reply;
import com.spring.myweb.reply.mapper.IReplyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService implements IReplyService {
	
	private final IReplyMapper mapper;
	private final BCryptPasswordEncoder encoder;

	@Override
	public void replyRegist(ReplyRequestDTO dto) {
		dto.setReplyPw(encoder.encode(dto.getReplyPw())); //비밀번호 암호화
		mapper.replyRegist(dto.toEntity(dto));
	}

	@Override
	public List<ReplyListResponseDTO> getList(int bno, int pageNum) {
		
		Page page = Page.builder()
						.pageNo(pageNum) //화면에서 전달된 페이지 번호
						.amount(5) // 댓글은 한 화면에 5개씩 // 댓글은 한 화면에 5개씩
						.build();	
		
		Map<String, Object> map = new HashMap<>();
		map.put("paging", page);
		map.put("boardNo", bno);

		List<ReplyListResponseDTO> dtoList = new ArrayList<>();
		for(Reply reply : mapper.getList(map)) {
			dtoList.add(new ReplyListResponseDTO(reply));
		}	
		return dtoList;
	}

	@Override
	public int getTotal(int bno) {
		return mapper.getTotal(bno);
	}

	@Override
	public String pwCheck(int rno) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(ReplyUpdateRequestDTO dto) {
		if(encoder.matches(dto.getReplyPw(), mapper.pwCheck(dto.getReplyNo()))) {
			mapper.update(dto.toEntity(dto));
			return "updateSuccess";
		} else {
			return "pwFail";
		}
	}

	@Override
	public String delete(int rno, String replyPw) {
		if(encoder.matches(replyPw, mapper.pwCheck(rno))) {
			mapper.delete(rno);
			return "delSuccess";
		} else {
			return "pwFail";
		}

	}

}

















