<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
你好，这是学生管理系统
<form method="post" action="login.do" >
	用户名:<input name="username"/><br/>
	密码 :<input name="password"/><br/>
	<input type="submit" value="提交">
</form>
还没有帐号？<a href="register.jsp">立即注册</a>
</body>
</html>