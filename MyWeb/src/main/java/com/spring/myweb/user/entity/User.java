package com.spring.myweb.user.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 CREATE TABLE users(
    user_id VARCHAR2(50) PRIMARY KEY,
    user_pw VARCHAR2(50) NOT NULL,
    user_name VARCHAR2(50) NOT NULL,
    user_phone1 VARCHAR2(50),
    user_phone2 VARCHAR2(50),
    user_email1 VARCHAR2(50),
    user_email2 VARCHAR2(50),
    addr_basic VARCHAR2(300),
    addr_detail VARCHAR2(300),
    addr_zip_num VARCHAR2(50),
    reg_date DATE DEFAULT sysdate
);
 */

@Getter @Setter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	
	private String userId;
	private String userPw;
	private String userName;
	private String userPhone1;
	private String userPhone2;
	private String userEmail1;
	private String userEmail2;
	private String addrBasic;
	private String addrDetail;
	private String addrZipNum;
	private LocalDateTime regDate;

}













