package com.goodee.library.member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/member")   // 클래스 모든 메소드앞에 /member가 붙게된다.
public class MemberController {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	MemberService memberService;
	
	// 회원가입 화면 이동 
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String openMemberCreate() {
		LOGGER.info("[MemberController] openMemberCraete();");
		// WEB-INF/views/member/create.jsp
		return "member/create";
	}
	
	// 회원가입 기능 수행
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String createMember(MemberVo vo) {
		LOGGER.info("[MemberController] createMember();");
		String nextPage = "member/create_success";
		if(memberService.createMember(vo) <= 0) {
			nextPage = "member/create_fail";
		}
		return nextPage;
	}


}
