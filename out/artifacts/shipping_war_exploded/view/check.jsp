<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'check.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	</head>
  <body>
  <form action="CheckServlet" id="forms">
  <table border="1">
  			请输入密码：<input type="password" name="password"></input><%--
  			 onkeydown="if(event.keyCode==13){tijiao();}"
  			--%><br/>
   	</table>
   		<input type="submit" value="确定"></input>
   </form>
   <%
   	if(request.getAttribute("code")!=null)
	  {
	   %>
	   <div id="div" style="color:#F00"><%=request.getAttribute("code")%></div>
	   <%
	  }
   %>
  	</body>
</html>
