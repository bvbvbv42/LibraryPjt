<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>        <!-- 누군가 보게되면 UTF-8로 반영해주세요 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>  <!-- html을 사용하겠습니다 -->
<html>
<head>
	<meta charset="UTF-8">
	<title>도서관리시스템</title>
	<!-- jstl이 보유하고있는 c:url을 사용:contextpath가 앞에 있다고하고 진행함 -->
	<link href="<c:url value='/resources/css/home.css'/>" rel="stylesheet" type="text/css">
</head>
<body>
	<!-- header.jsp가 바로밑에있는 공간에 들어감  -->
	<jsp:include page="include/header.jsp"/>
	<jsp:include page="include/nav.jsp"/>
	<section>
	   <div id="section_wrap">
	      <div class="word">
	         <h3>HOME</h3>
	      </div>
	   </div>
	</section>
</body>
</html>
