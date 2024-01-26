package com.goodee.library.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService { // 저장된 파일 정보를 string으로 받는다
	
	public String upload(MultipartFile file) {
		String result="";
		// 파일 서버 저장 (서버 = 내 본체)
		// UUID = 긴 랜덤값
		String fileOriName = file.getOriginalFilename(); // 파일이름을 김.kpg으로 했으면 그 "김.jpg"을 뜻함
		// 확장자 떼오기 lastIndexOf-> 뒤에서부터 
		String fileExtension = fileOriName.substring(
				fileOriName.lastIndexOf("."),fileOriName.length()); 
		// 서버에 저장하기
		// 그냥 sts3 버젼 ->String uploadDir = "C:\\library\\upload\\";
		
		// 젠킨스 버전
		String uploadDir = "/var/lib/tomcat9/webapps/upload/";
		
		// UUID 만들기
		UUID uuid = UUID.randomUUID();
		
		// 하이픈 넣으면 안되서 공백으로 바꾼다
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		// 서버에 저장되는 파일 (껍데기만 만듦)
		File saveFile= new File(uploadDir+"\\"+uniqueName+fileExtension);
		
		if(!saveFile.exists())
			saveFile.mkdirs();
		
		// 파일 넣기 ( 파일 넣을때는 try catch필요)
		try {
			file.transferTo(saveFile); //(파일 넣기)
			result = uniqueName + fileExtension;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
