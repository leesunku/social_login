<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.2.min.js">
</script>
<script type="text/javascript">
$(function(){
	$("#naverLoginBte").click(function(){
	location.href = "naverLogin";
	});
});
</script>
</head>
<body>
<h1>소셜 로그인 메인 페이지</h1>
<button id="naverLoginBte">네이버 아이디로 로그인</button>
</body>
</html>