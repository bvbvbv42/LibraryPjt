package com.goodee.library.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MemberLoginInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest req,
				HttpServletResponse resp, Object handler) throws Exception {
			
			HttpSession session = req.getSession(); //서버관통하는 session정보를 가져올수있음
			if(session != null) {
				Object obj = session.getAttribute("loginMember"); // object대신 vo도사용가능
				if(obj != null)
					return true;  //컨트롤러에있는 메소드 실행 허용한다
			}
			resp.sendRedirect(req.getContextPath() + "/member/login");
					return false;
		}
}
