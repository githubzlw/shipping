<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出运联系单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
	  <frameset cols="120,*">
		  <frame src="view/left.jsp">
		  <% int auth=(Integer)session.getAttribute("auth");
		  	if(auth==1){
		  		%>
		  <frame src="QuestFirstServlet" name="showframe" id="frame_standard">
		  		<%
		  	}else{
		  %>
		  <frame src="view/save.jsp" name="showframe" id="frame_standard">
		  <%} %>
		</frameset>
  
  <%--<frameset cols="20%,80%">
	<frame name="frame1">
	<a href="view/save.jsp" target="frame2">保存</a>
	
	</frame>
	<frame name="frame2" />
	
	<iframe name="frame1" src="view/left.jsp" frameborder="1">
	</iframe>
	<iframe name="frame2">
	
	</iframe>
</frameset> 
	--%><%--<iframe name="frame1">
	<a href="save.jsp" target="frame2">保存</a>
	</form>
	</ifram>
	<iframe name="frame2">
	</frame>
  --%>
</html>
