<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
    <style>
       table{ background:#000000;} 
       table td{ background:#FFF} 
    </style>
<script type="text/javascript">
function fnGetProject(){
	document.getElementById("form").submit();
}

function fnColor()
{//even odd
	$("#tableone tr:eq(0) td").css("background-color","7DB3D8");
	$("#tabletwo tr:eq(0) td").css("background-color","7DB3D8");
	$("#tableone tr:gt(0):even td").css("background-color","8dc850");
	$("#tabletwo tr:gt(0):even td").css("background-color","8dc850");
}

function fnAdd(){
	window.location = "view/addProject.jsp";
}
</script>
<body onload="fnColor();">
<div>
<h1>工融-问题项目汇总</h1>
<h3>列表上项目 每周2,5各开一次会讨论，讨论结果，由跟单销售上传ERP</h3>
<div>

<form id="form" action="GetProjectServlet">
 	<div onclick="fnAdd();" style="background-image:url('img/button1.png');width: 90px;height: 30px;"></div>
 	<div style="margin-left: 150px;margin-top: -26px;">问题状态：<select onchange="fnGetProject()" name="state" >
			<option <c:out value="${state=='0'?'selected=selected':''}" />   value="0" >未解决</option>
			<option <c:out value="${state=='1'?'selected=selected':''}" /> value="1" >已解决</option>
		</select></div>
</form>
</div>
		<div>
			所有交期拖延:
			<div>
				 
				<table id="tableone"   width="90%"  border="0" cellspacing="1" cellpadding="0">
					<tr  >
						<td >IssueNo</td>
						<td>项目ID</td>
						<td>项目名称</td>
						<td>跟单销售</td>
						<td>采购</td>
						<td>项目金额本次</td>
						<td>项目金额一年</td>
						<td>原定交期</td>
						<td>最新交期</td>
						<td>详情</td>
					</tr>
					<c:forEach  var="fuwa" items="${delivery}" >
						<tr >
						<td><c:out value="${fuwa[8]}" /></td>
						<td><a href='http://192.168.1.2/erp/po/CaseInPo.aspx?id=<c:out value="${fuwa[0]}" />'><c:out value="${fuwa[0]}" /></a></td>
						<td><c:out value="${fuwa[1]}" /></td>
						<td><c:out value="${fuwa[2]}" /></td>
						<td><c:out value="${fuwa[3]}" /></td>
						<td><c:out value="${fuwa[4]}" /></td>
						<td><c:out value="${fuwa[5]}" /></td>
						<td><c:out value="${fuwa[6]}" /></td>
						<td>
						 <c:set value="${fn:substring(fuwa[7], 0, fn:indexOf(fuwa[7],','))}" var="name2" ></c:set>
						 <c:out value="${name2==''?'':name2}"  />
						 </td>
						
						<td><a href="ProjectInfoServlet?id=${fuwa[8]}">查看详情</a></td>
					</tr>
					</c:forEach>
					<tr >
						<td>sdsd</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
					</tr>
					<tr >
						<td>sdsd</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
					</tr>
					<tr >
						<td>sdsd</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
					</tr>
					<tr >
						<td>sdsd</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
						<td>adfaf</td>
					</tr>
				</table>
			</div>
		</div>
		<br/>
		<div>
			所有质量问题 :
			<div>
				<table  id="tabletwo" width="90%"  border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td>项目名称</td>
						<td>跟单销售</td>
						<td>采购</td>
						<td>项目金额本次</td>
						<td>项目金额一年</td>
						<td>原定交期</td>
						<td>最新交期</td>
						<td>销售对问题解释</td>
						<td>采购对问题解释</td>
						<td>质检对问题的解释</td>
						<td>详情</td>
					</tr>
					<c:forEach  var="fuwa" items="${quality}" >
						<tr>
						<td><c:out value="${fuwa[0]}" /></td>
						<td><c:out value="${fuwa[1]}" /></td>
						<td><c:out value="${fuwa[2]}" /></td>
						<td><c:out value="${fuwa[3]}" /></td>
						<td><c:out value="${fuwa[4]}" /></td>
						<td><c:out value="${fuwa[5]}" /></td>
						<td><c:out value="${fuwa[6]}" /></td>
						<td><c:out value="${fuwa[7]}" /></td>
						<td><c:out value="${fuwa[8]}" /></td>
						<td><c:out value="${fuwa[9]}" /></td>
						<td><a href="ProjectInfoServlet?id=${fuwa[10]}">查看详情</a></td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</body>
</html>