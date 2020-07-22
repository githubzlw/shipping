<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">
// 		function openwins(){
<%-- 			 <% --%>
// 			 int checkpwd=(Integer)session.getAttribute("checkpwd");
// 			  if(checkpwd==1){
<%-- 				  %> --%>
// 				alert("dddddd");  
<%-- 				<% --%>
// 			  }else{
<%-- 				%> --%>
// 				window.open("check.jsp","newwindow","height=200,width=400,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no")
<%-- 				<% --%>
// 			  }
<%-- 			  %> --%>

            function exit_login(){
            	top.location.href = "/shipping/ToLogin";
		    }
            
            
            
	</script>
	</head>
  <a href="save.jsp" target="showframe">录入保存</a>
  <br/>
   <%
  	int auth=(Integer)session.getAttribute("auth");   
  	if(auth==1){
  		%>
  		 <%--<a href='#' onclick='openwins()'>查询验证</a>--%>
	  <a href="../QuestFirstServlet" target="showframe">查询</a>
	  <br/>
	  <a href="../CheckAll?view=summary" target="showframe">汇总</a>
	  <br/>
	  <a href="../CheckAll?view=drawbackSummary" target="showframe">退税汇总</a>
	  <br/>
	  <a href="../AdminList" target="showframe">管理用户</a>
	  <br/>
  		<%
  	}
  %>
  <% 
  String adminName = (String)session.getAttribute("adminName");
  if("ERIC".equals(adminName)){
  %>
  <a href="../QuestFirstServlet" target="showframe">查询</a><br/>
  <%
  }
  %>
  <a href="#"  onclick="exit_login()">退出登录</a>
 <%--
  <a href="../ListByCookieServlet" target="showframe">录入历史</a>
  <br/>
  --%><body>
  </body>
</html>
