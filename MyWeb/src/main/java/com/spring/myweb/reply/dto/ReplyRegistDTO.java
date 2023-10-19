package com.spring.myweb.reply.dto;

import com.spring.myweb.reply.entity.Reply;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReplyRegistDTO {
	
	private int bno;
	private String replyText;
	private String replyId;
	private String replyPw;
	
	public Reply toEntity(ReplyRegistDTO dto) {
		return Reply.builder()
				.bno(dto.getBno())
				.replyText(dto.getReplyText())
				.replyWriter(getReplyId())
				.replyPw(dto.getReplyPw())
				.build();
	}
	

}













