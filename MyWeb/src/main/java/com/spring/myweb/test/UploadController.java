package com.spring.myweb.test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/fileupload")
public class UploadController {

	@GetMapping("/upload")
	public void form() {}

	@PostMapping("/upload_ok")
	public void upload(MultipartFile file) {

		String fileRealName = file.getOriginalFilename(); //파일 원본명.
		long size = file.getSize(); //파일 크기

		System.out.println("파일명: " + fileRealName);
		System.out.println("파일 크기: " + size + "bytes");

		/*
		 사용자가 첨부한 파일은 DB에 저장하는 것을 선호하지 않습니다.
		 DB 용량을 파일 첨부에 사용하는 것은 비용적으로도 좋지 않기 때문입니다.
		 그렇기 때문에 상용되는 서비스들이 파일을 처리하는 방법은 따로 파일 전용 서버를 구축하여
		 저장하고, DB에는 파일의 저장 경로를 지정하는 것이 일반적입니다.

		 파일 업로드 시 파일명이 동일한 파일이 이미 존재할 수도 있고,
		 사용자가 업로드 하는 파일명이 영어 이외의 언어로 되어있을 수 있습니다.
		 타 언어를 지원하지 않는 환경에서는 정상 동작이 되지 않을 수 있습니다. (리눅스)
		 고유한 랜덤 문자를 통해 DB와 서버에 저장할 파일명을 새롭게 만들어 줍니다.
		 */
		UUID uuid = UUID.randomUUID();
		System.out.println("uuid: " + uuid.toString());

		String fileName = uuid.toString();
		fileName = fileName.replace("-", "");
		System.out.println("fileName: " + fileName);

		String fileExtension = fileRealName.substring(
				fileRealName.lastIndexOf("."), fileRealName.length()
				); //확장자
		System.out.println("확장자명: " + fileExtension);

		//DB에는 파일 경로를 저장한다고 가정하고, 실제 파일은 서버 컴퓨터의 로컬 경로에 저장하는 방식.
		String uploadFolder = "C:/test/upload";

		File f = new File(uploadFolder);
		if(!f.exists()) {
			System.out.println("폴더가 존재하지 않음!");
			f.mkdirs();
		}

		File saveFile = new File(uploadFolder + "/" + fileName + fileExtension);

		try {
			//매개값으로 받은 첨부 파일을 지정한 로컬 경로에 보내는 메서드.
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	// 첨부파일이 여러 개 전달되는 경우 1
	@PostMapping("/upload_ok2")
	public String upload2(MultipartHttpServletRequest files) {

		String uploadFolder = "C:/test/upload";

		List<MultipartFile> list = files.getFiles("files");

		for(MultipartFile m : list) {
			try {
				File saveFile = new File(uploadFolder + "/" + m.getOriginalFilename());
				m.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		return "fileupload/upload_ok";
	}

	// 첨부파일이 여러 개 전달되는 경우 2
	@PostMapping("upload_ok3")
	public String upload3(@RequestParam("file") List<MultipartFile> list) {
		
		System.out.println(list.toString());
		
		String uploadFolder = "C:/test/upload";
		
		for(MultipartFile m : list) {
			try {
				if(m.getSize() == 0) break;
				File saveFile = new File(uploadFolder + "/" + m.getOriginalFilename());
				m.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}

		return "fileupload/upload_ok";

	}





}











