package com.goodee.library.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	// 로그인 화면 이동
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String openLoginForm() {
		LOGGER.info("[MemberController] openLoginForm();");
		return "member/login_form";		
	}

	// 로그인 기능
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String loginMember(MemberVo vo, HttpSession session) { //HttpSession session 의존성주입:session을 쓰겠다
		LOGGER.info("[MemberController] loginMember();");
		String nextPage = "member/login_success";
		MemberVo loginedMember = memberService.loginMember(vo);
		if(loginedMember == null) {
			nextPage = "member/login_fail";
		} else {
			session.setAttribute("loginMember", loginedMember); // 첫번째는 키 두번째는 value값
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage;
	}
	
	// 로그아웃 기능
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logoutMember(HttpSession session) {
		LOGGER.info("[MemberController] logoutMember();");
		session.invalidate();
		return "redirect:/";  // /url로 이동해달라
	}
	
//	// 회원 목록 이동
//	@RequestMapping(method=RequestMethod.GET)
//	public ModelAndView listupMember() {
//		LOGGER.info("[MemberController] listupMember();");
//		// 1. 목록 정보 조회
//		List<MemberVo> memberVos = memberService.listupMember();
//		// 2. 목록 전달
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("memberVos", memberVos);
//		// 3. 뷰 선택
//		mav.setViewName("member/listup");
//		return mav;
//	}
	
//	// 회원 목록 이동 - ModelAndView(2)
//	@RequestMapping(method=RequestMethod.GET)
//	public ModelAndView listupMember(ModelAndView mav) {
//		LOGGER.info("[MemberController] listupMember();");
//		// 1. 목록 정보 조회
//		List<MemberVo> memberVos = memberService.listupMember();
//		// 2. 목록 전달
//		mav.addObject("memberVos", memberVos);
//		// 3. 뷰 선택
//		mav.setViewName("member/listup");
//		return mav;
//	}

	// 회원 목록 이동 - Model(1)
	@RequestMapping(method=RequestMethod.GET)
	public String listupMember(Model model) {
		LOGGER.info("[MemberController] listupMember();");
		// 1. 목록 정보 조회
		List<MemberVo> memberVos = memberService.listupMember();
		// 2. 목록 전달
		model.addAttribute("memberVos", memberVos);
		return "member/listup";
	}
	
	// 회원 정보 수정 화면 이동
	// url에서 /member/4(m_no) 라는 값을 받게오면 그 4라는값을 id로 넣겠다.
	//@RequestMapping(value="/{m_id}", method=RequestMethod.GET)
	@RequestMapping(value="/{m_no}", method=RequestMethod.GET)
	public String modifyMember(@PathVariable int m_no, HttpSession session) {
		LOGGER.info("[MemberController] modifyMember();");
		// 다른 사람의 정보 수정 할수있다고 기준(관리자 있어야함)
		// 1. url에 있는 m_no 기준 select
		// 2. 수정 화면 
		
		// 내 정보만 수정 O
		// 1. 세션에 있는 m_no 기준
		// 2. 수정 화면
		MemberVo loginedMemberVo = (MemberVo)session.getAttribute("loginMember");
		String nextPage = "";
		if(loginedMemberVo == null) {
			// 로그인 화면 이동
			nextPage = "redirect:/member/login";
		} else {
			// 수정 화면 이동
			nextPage = "member/modify_form";
		}
		return nextPage;
	}
	
	// 회원 정보 수정 기능
	@RequestMapping(value="/{m_no}", method=RequestMethod.POST)
	public String modifyMemberConfirm(MemberVo vo, HttpSession session) {
		LOGGER.info("[MemberController] modifyMemberConfirm();");
		// 1. 회원 정보 수정(DB)  insert 는 넣은갯수, update는 변환갯수 사용 ,정보 수정 이 3개는 int다
		int result = memberService.modifyMember(vo);
		if(result > 0) {
			// 2. 세션 정보 변경 , 비어있는 vo 만듦
			MemberVo loginedMemberVo = new MemberVo();
			loginedMemberVo = memberService.getLoginedMemberVo(vo.getM_no()); //vo.getM_no() = 4다 
			session.setAttribute("loginMember", loginedMemberVo);
			session.setMaxInactiveInterval(60*30);			
			// 3. 성공 화면 이동
			return "member/modify_success";
		} else {
			// 3. 실패 화면 이동
			return "member/modify_fail";
		}
	}
	
	// 비밀번호 설정 화면 이동
	@RequestMapping(value="/findPassword", method=RequestMethod.GET)
	public String findPasswordForm() {
		LOGGER.info("[MemberController] findPasswordForm();");
		return "member/find_password_form";
	}
	
	// 비밀번호 설정 기능
	@RequestMapping(value="/findPassword", method=RequestMethod.POST)
	public String findPasswordConfirm(MemberVo vo) { //vo 를 받아서 전달
		LOGGER.info("[MemberController] findPasswordConfirm();");
		int result = memberService.findPasswordConfirm(vo);
		if(result <= 0) {
			return "member/find_password_fail";
		}else {
			return "member/find_password_success";
		}
	}
	
}
