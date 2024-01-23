<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<link href="<c:url value='/resources/css/include/nav.css'/>" rel="stylesheet" type="text/css">	
	<nav>
	   <div id="nav_wrap">
<%@ page import="com.goodee.library.member.MemberVo" %>
	   	  <%
	   	  	MemberVo loginedMember = (MemberVo)session.getAttribute("loginMember"); //key 값으로 가져온다
	   	  	if(loginedMember == null){
	   	  	
	   	  %>
		  	      <div class="menu">
	         <ul>
	            <li>
	               <a href="<c:url value='/member/login'/>">로그인</a> <!-- a태그는 기본적으로 GET메소드 -->
	            </li>
	            <li>
	               <a href="<c:url value='/member/create'/>">회원가입</a>
	            </li>
	         </ul>
	      </div>
	      <%} else{ %>
	      	      <div class="menu">
	         <ul>
	            <li>
	               <a href="<c:url value='/member/logout'/>">로그아웃</a> <!-- a태그는 기본적으로 GET메소드 -->
	            </li>
	            <li>
	            <!-- 맨위 MemberVo loginedMember = (MemberVo)session.getAttribute("loginMember"); //key 값으로 가져온다 와같음  -->
	               <a href="<c:url value='/member/${loginMember.m_no}'/>">계정수정</a>
	            </li>
	            <li>
	               <a href="<c:url value='/member' />">회원목록</a>
	            </li>
	            <li>
	            	<a href="<c:url value='/book/create'/>">도서등록</a>
	            </li>
	            <li>
	            	<a href="<c:url value='/book'/>">도서목록</a>
	            </li>
	         </ul>
	      </div>
			<div class="search">
			   <form action="<c:url value='/book'/>" name="search_book_form" method="get">
			      <input type="text" name="b_name" placeholder="검색하고자 하는 도서 이름을 입력하세요.">
			      <!-- submit으로 보내면 바로 정보가 간다 -->
			      <input type="submit" value="검색">
			   </form>
			</div>
		  <% } %>
	   </div>
	</nav>