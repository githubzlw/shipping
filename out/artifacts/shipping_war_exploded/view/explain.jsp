<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'explain.jsp' starting page</title>
    
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
  <form action="SaveExplainServlet">
  <table border="1">
  		<%
	   			if(request.getAttribute("id")!=null){
	   				%>
	   				<input type="text" name="id" style="display:none;" value="<%=request.getAttribute("id")%>"/>
	   				<br/>
		   		<%} %>
   		HS CODE：<input name="hscode" type="text" value="<%=request.getAttribute("hscode")%>"/>
   		<br/>
  			内容：<textarea name="content"><%=request.getAttribute("content")%></textarea>
  			<br/>
   		</table>
   		<br/>
   <input type="submit" value="保存"></input>
  </form>
  	</body>
</html>
