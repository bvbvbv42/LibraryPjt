package com.goodee.library.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	private static final Logger LOGGER =
			LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	public int createMember(MemberVo vo) {
		LOGGER.info("[MemberService] createMember();");
		int result = 0; //기본값이 0
		
		//false(0보다크지않다면) 의 뜻 = 중복이 없다면 
		if(dao.isMemberCheck(vo.getM_id())==false) {
			result = dao.insertMember(vo);
		}
		return result;
	}
	
	// 먼저 id를 기준으로 정보를 가져옴 but 비번틀리면 정보를 다지울꺼임
	
	public MemberVo loginMember(MemberVo vo) { // MemberVo : id랑 비번만있음 (login_form.jsp보면됨)
		LOGGER.info("[MemberService] loginMember();");
		// Dao야 내가 vo 형태로 정보를 줄테니 db에서 정보좀 가져와
		MemberVo loginedMember = dao.selectMember(vo);
		return loginedMember;
	}
	
	public List<MemberVo> listupMember(){
		LOGGER.info("[MemberService] listupMember();");
		return dao.selectMemberList();
	}
	
	public int modifyMember(MemberVo vo) {
		LOGGER.info("[MemberService] modifyMember();");
		return dao.updateMember(vo);
	}
	
	// 회원 단일 정보 조회
	public MemberVo getLoginedMemberVo(int m_no) { // MemberVo : 정보들을 틀에 담아서 보내줌 ,int m_no 계속바뀜 ==> int abcd이렇게적어도 상관없음 
		LOGGER.info("[MemberService] getLoginedMemberVo();");
		return dao.selectMemberOne(m_no);
	}

	public int findPasswordConfirm(MemberVo vo) {
		// 1. 입력한 정보와 일치하는 사용자가 있는지 확인
		MemberVo selectedMember = dao.selectMemberOne(vo); // vo 정보를 전달 //3개의 정보가있음
	    int result = 0;
	    if(selectedMember != null) {
	    	// 2. 새로운 비밀번호 생성
	    		String newPassword = createNewPassword();
	    	// 3. 생성된 비밀번호 업데이트
	    		result = dao.updatePassword(vo.getM_id(),newPassword);
	    		if(result > 0) {
	    			// 4. 메일보내기
	    			sendNewPasswordByMail(vo.getM_mail(), newPassword);
	    			
	    		}
	    }
		
		
		return result;
	}	
	
	
	
	
	// 메일로 비밀번호 보내기
	
	//String toMailAddr, String newPw 위에서 받아서 이메소드에서만 사용할게
	private void sendNewPasswordByMail(String toMailAddr, String newPw) {     // mail을 보내는거는 보낸뒤 받아오는 데이터가없으니까 void
		LOGGER.info("[MemberService] sendNewPasswordByMail();");									   								  // 메소드를 수행하고 결과물이 나왔을때 void를 사용x
		
		final MimeMessagePreparator mime = new MimeMessagePreparator() {  //공장장
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception{
				final MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeHelper.setTo(toMailAddr); //어디로 메일보낼껀가
				mimeHelper.setSubject("[구디 도서관] 새로운 비밀번호 안내입니다.");
				mimeHelper.setText("새 비밀번호 :  "+ newPw, true); // html 형태로읽을건가? html이면 true
				
			}
			
		};
		javaMailSenderImpl.send(mime);  //배송기사
		
	}
	
	
	
	
	// 비밀번호 생성
	private String createNewPassword() {    // 일하고나면 산출물이 String이다
		LOGGER.info("[MemberService] createNewPassword();");
	    char[] chars = new char[] {
		         '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		         'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
		         'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
		         'u', 'v', 'w', 'x', 'y', 'z'
		         };
	    StringBuffer sb = new StringBuffer(); // String을 붙일때?
	    SecureRandom sr = new SecureRandom(); // 랜덤값을 돌려 랜덤의 값을 가져옴 
	    sr.setSeed(new Date().getTime());
		
	    // ex) 10이나오면 인덱스 10의값인 a가 나온다
	    int index = 0; 
		int length = chars.length; // 배열의 길이
		for(int i = 0; i < 8; i++) {
			index = sr.nextInt(length); //sr의 범주를 0부터 length까지 중 하나를 뽑는다
			if(index % 2 == 0) {
//				sb.append(chars[index]);  //chars의 값을 가져온다
//				sb.append(String.valueOf(chars[index])); //String으로 바꾼다
				sb.append(String.valueOf(chars[index]).toUpperCase()); // String 으로 바꾼것을 대문자로 바꾼다.
			}else {
				sb.append(String.valueOf(chars[index]).toLowerCase());
			}
		}
		return toString();
	}
}
