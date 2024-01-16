package com.goodee.library.member;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	private static final Logger LOGGER =
			LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	MemberDao dao;
	
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
}
