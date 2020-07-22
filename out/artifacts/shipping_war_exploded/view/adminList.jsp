<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>管理者</title>
    
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
  <a href="view/increase.jsp" target="showframe">增加管理员</a>
  
   <table  border="1" id="table">
  <tr>
  <td>用户名</td>
  <td>密码</td>
  <td>权限</td>
  <td>操作</td>
  </tr>
  </table>
  <script type="text/javascript">
	  var tab=document.getElementById("table");
	  <%
	  if(request.getAttribute("total")!=null)
	  {
		  int total=(Integer)request.getAttribute("total");
	  		if(total==0)
	  			return;
		  for(int j=0;j<total;j++){
	  %>
		  var newTR =tab.insertRow(tab.rows.length);
		  var newNameTD = newTR.insertCell(0);
		  newNameTD.innerHTML = "<%=request.getAttribute("name"+j)%>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "<%=request.getAttribute("pass"+j)%>";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<%=request.getAttribute("auth"+j)%>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<a href='adminInfo?id=<%=request.getAttribute("id"+j)%>' target='showframe'>修改</a>";
	  <%
	  }
	  }
	  %>
  </script>
  </body>
</html>
