<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>删除项目</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<script type="text/javascript">
function fnSubmit(){
	var proId = document.getElementById("proId").value.trim();
	if(proId == ""){
		alert("请输入 IssueID");
		return false;
	}
}
</script>
<body>
  <h2>删除项目</h2>
  <br/>
  <form action="DeleteProjectServlet" onsubmit="return fnSubmit();" method="post">
  	 IssueID：<input id="proId" name="proId" type="text"/>
  <input type="submit" value="确认删除"/>
  </form>
</body>
</html>