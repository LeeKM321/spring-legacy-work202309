package com.spring.myweb.reply.dto;

import com.spring.myweb.reply.entity.Reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyUpdateRequestDTO {
	
	private int replyNo;
	private String replyPw;
	private String replyText;
	
	public Reply toEntity(ReplyUpdateRequestDTO dto) {
		return Reply.builder()
					.replyNo(this.replyNo)
					.replyPw(this.replyPw)
					.replyText(this.replyText)
					.build();
	}

}
















