<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>增加录入者</title>
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
    <form action="adminUpdate" method="post">
   		<input name="id" type="hidden" value="<%=request.getAttribute("id") %>"/><br/>
   		用户名：<input name="name" type="text" value="<%=request.getAttribute("name") %>"/><br/>
   		密&nbsp;码：<input name="pass" type="text" value="<%=request.getAttribute("pass") %>"/><br/>
		<select name="auth" id="auth">
		    <option value="1">管理员</option>
			<option value="0">录入者</option>
		</select>
   		<br/>
   		 <input type="submit" value="修改"></input>
    </form>
   		<form action="adminDelete" method="post">
   			<input name="id" type="hidden" value="<%=request.getAttribute("id") %>"/><br/>
   		 	<input type="submit" value="删除"></input>
   		</form>
   		 
   		 <script type="text/javascript" >
	  var authtab=document.getElementById("auth");
	  var auth="<%=request.getAttribute("auth")%>";
	  for(var i=0;i<authtab.options.length;i++)
	     {
	         if(authtab.options[i].value==auth)
	         {
	        	 authtab.options[i].selected = true;
	             break;
	         }
	  }
	  </script>
  </body>
</html>
