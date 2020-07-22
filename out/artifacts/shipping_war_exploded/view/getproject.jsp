<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<style type="text/css">
<!--
#bg {background-color:#598ac7;
width:1100px;
margin-left:auto;
margin-right:auto;
color:#FFFFFF;
font-family:"微软雅黑", "幼圆", "宋体";}
.title {
	color: #FFFFFF;
	font-family:"微软雅黑", "幼圆", "宋体";
	font-size:14px;
}
a:link {
	color: #FFFFFF;
}
-->
</style>
<script type="text/javascript">
function fnGetProject(){
	document.getElementById("form").submit();
}

function fnColor()
{//even odd
	$("#tableone tr:gt(0):odd td").css("background-color","CFFCE1");
	$("#tableone tr:gt(0):even td").css("background-color","FFFFFF");
	$("#tabletwo tr:gt(0):even td").css("background-color","FFFFFF");
	$("#tabletwo tr:gt(0):odd td").css("background-color","CFFCE1");
	$("#tableThree tr:gt(0):even td").css("background-color","FFFFFF");
	$("#tableThree tr:gt(0):odd td").css("background-color","CFFCE1");
	 $("table tr[class=redcolor] td").css("background-color","FFC0CB");

}

function fnAdd(){
	window.location = "view/addProject.jsp";
}

 
</script>
<body onload="fnColor();">
<h1 align="center">工融-问题项目汇总</h1>
<table width="100%" align="center" cellspacing="2" bgcolor="3e6096">
   
 <tr>
  <td   > <div align="left" style="color:#FFFFFF; font-size:20px">列表上项目 每周2,5各开一次会讨论，讨论结果，由跟单销售上传ERP</div>
	 
 </tr>
  <tr>
    <td><table  align="center" cellspacing="2" style="margin-top:10px" class="title">
  <tr>
   
 <td height="43"   style="border-bottom:dashed #CCCCCC 1px">
<a href="view/addProject.jsp"  ><img src="img/button1.png" width="88" height="30"  border="0"></a>
</td>
 <td height="43"   style="border-bottom:dashed #CCCCCC 1px"><form id="form" method="post" action="GetProjectServlet">
	<h2>项目状态：<select onChange="fnGetProject()" name="state" style="width: 130px;height: 30px;font-size: 16px;font-weight: bold;" >
			<option <c:out value="${state=='0'?'selected=selected':''}" />   value="0" >未解决</option>
			<option <c:out value="${state=='1'?'selected=selected':''}" /> value="1" >已解决</option>
		</select></h2>
</form></td>
    </tr>
 <tr>
    <td colspan="9" >所有交期问题 :</td>
    </tr>
  <tr>
    <td colspan="9" ><table id="tableone" width="100%" cellspacing="2">
  <tr bgcolor="#5a8bc8" style="color: #FFFFFF;">
    <td width="2%" bgcolor="#5a8bc8" style="font-size:14px" align="center"><div>ID</div></td>
    <td width="4%" bgcolor="#5a8bc8" style="font-size:14px"><div align="center">项目ID</div></td>
    <td width="8%" style="font-size:14px"><div align="center">项目名称</div></td>
    <td width="8%" style="font-size:14px"><div align="center">跟单销售,采购,质检</div></td>
    <td width="3%" style="font-size:14px"><div align="center">金额</div></td>
    <td width="7%" style="font-size:14px"><div align="center">原定交期</div></td>
    <td width="7%" style="font-size:14px"><div align="center">最新交期</div></td>
    <td width="3%" style="font-size:14px"><div align="center">扣款</div></td>
    <td width="20%" style="font-size:14px"><div align="center">销售对问题解释</div></td>
    <td width="20%" style="font-size:14px"><div align="center">采购对问题解释</div></td>
    <td width="10%" style="font-size:14px"><div align="center">领导指令</div></td>
    <td width="3%" style="font-size:14px"><div align="center">详情</div></td>
  </tr>
		<c:forEach  var="fuwa" items="${delivery}" >
		<tr bgcolor="#FFFFFF"  <c:out value="${fuwa[14]=='1'?'':'class=redcolor'}" />>
		<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[8]}" /></label></td>
		<td style="color:#333333;"><a style="font-size:14px; color:#333333" href='http://192.168.1.2/erp/po/CaseInPo.aspx?id=<c:out value="${fuwa[0]}" />'><c:out value="${fuwa[0]}" /></a></td>
		<td style="color:#333333;"><c:out value="${fuwa[1]}" /></td>
		<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[2]}" />,<br/><c:out value="${fuwa[3]}" />,<br/><c:out value="${fuwa[15]}" /></label></td>
		<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[4]}" />/<c:out value="${fuwa[5]}" /></label></td>
		<td style="color:#333333;"><c:out value="${fuwa[6]}" /></td>
		<td style="color:#333333;">
		 <c:set value="${fn:substring(fuwa[7], 0, fn:indexOf(fuwa[7],','))}" var="name2" ></c:set>
		 <c:out value="${name2==''?'':name2}"  />
		 </td>
		 <!-- （今天-原定交期）-客户原因造成的拖延天数-公司特许的交期拖延）* 30 -->
		<td style="color:red;">
		 <c:out value="${fuwa[9]}" />
		</td>
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[10]}" /></label>
		</td>
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[11]}" /></label>
		</td>
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[13]}" /></label>
		</td>
		<td style="color:#333333;"><a href="ProjectInfoServlet?id=${fuwa[8]}" style="font-size:14px; color:#333333">详情</a></td>
	</tr>
	</c:forEach> 
  </table>
  </td></tr>
  
  <tr>
    <td colspan="9"></td>
    </tr>
  <tr>
    <td colspan="9" style="border-top:dashed #CCCCCC 1px">&nbsp;</td>
    </tr>
  
  <tr>
    <td colspan="9" >所有质量问题 :</td>
    </tr>
  <tr>
    <td colspan="9" ><table  id="tabletwo" width="100%" cellspacing="2">
      <tr bgcolor="#5a8bc8" style="color: #FFFFFF;"  >
     <td width="2%" bgcolor="#5a8bc8" style="font-size:14px" align="center"><div>ID</div></td>
    <td width="4%" bgcolor="#5a8bc8" style="font-size:14px"><div align="center">项目ID</div></td>
    <td width="8%" style="font-size:14px"><div align="center">项目名称</div></td>
    <td width="7%" style="font-size:14px"><div align="center">跟单销售,采购,质检</div></td>
    <td width="3%" style="font-size:14px"><div align="center">金额</div></td>
    <td width="7%" style="font-size:14px"><div align="center">原定交期</div></td>
    <td width="7%" style="font-size:14px"><div align="center">最新交期</div></td>
    <td width="8%" style="font-size:14px"><div align="center">交期/质检扣款</div></td>
    <td width="14%" style="font-size:14px"><div align="center">销售对问题解释</div></td>
    <td width="14%" style="font-size:14px"><div align="center">采购对问题解释</div></td>
    <td width="14%" style="font-size:14px"><div align="center">质检对问题的解释</div></td>
    <td width="8%" style="font-size:14px"><div align="center">领导指令</div></td>
    <td width="3%" style="font-size:14px"><div align="center">详情</div></td>
   
      </tr>
      
	  <c:forEach  var="fuwa" items="${quality}" >
						<tr <c:out value="${fuwa[15]=='1'?'':'class=redcolor'}" />>
						<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[10]}" /></label></td>
						<td style="color:#333333;"><c:out value="${fuwa[11]}" /></td>
						<td style="color:#333333;"><c:out value="${fuwa[0]}" /></td>
						<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[1]}" />,<br><c:out value="${fuwa[2]}" />,<br/><c:out value="${fuwa[16]}" /></label></td>
						<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[3]}" />/<c:out value="${fuwa[4]}" /></label></td>
						<td style="color:#333333;"><c:out value="${fuwa[5]}" /></td>
						 
<td style="color:#333333;">
		 <c:set value="${fn:substring(fuwa[6], 0, fn:indexOf(fuwa[6],','))}" var="name2" ></c:set>
		 <c:out value="${name2==''?'':name2}"  />
		 </td>
						
						<td style="color:#333333;color: red"><label style="font-size: 10px;"><c:out value="${fuwa[14]}" />/<c:out value="${fuwa[13]}" /></label></td>
 
						
						<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[7]}" /></label></td>
						<td style="color:#333333;" ><label style="font-size: 10px;"><c:out value="${fuwa[8]}" /></label></td>
						<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[9]}" /></label></td>
						<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[12]}" /></label></td>
						<td style="color:#333333;"><a  style="font-size:14px; color:#333333" href="ProjectInfoServlet?id=${fuwa[10]}">详情</a></td>
					</tr>
					</c:forEach>
	   
    </table></td>
  </tr>
   
  <tr>
    <td colspan="9"></td>
    </tr>
  <tr>
    <td colspan="9" style="border-top:dashed #CCCCCC 1px">&nbsp;</td>
    </tr>
  
  <tr>
    <td colspan="9" >所有质量和交期都有问题 :</td>
    </tr>
  <tr>
    <td colspan="9" >
  <table id="tableThree" width="100%" cellspacing="2">
  <tr bgcolor="#5a8bc8" style="color: #FFFFFF;">
    <td width="2%" bgcolor="#5a8bc8" style="font-size:14px" align="center"><div>ID</div></td>
    <td width="4%" bgcolor="#5a8bc8" style="font-size:14px"><div align="center">项目ID</div></td>
    <td width="8%" style="font-size:14px"><div align="center">项目名称</div></td>
    <td width="8%" style="font-size:14px"><div align="center">跟单销售，采购,质检</div></td>
    <td width="3%" style="font-size:14px"><div align="center">金额</div></td>
    <td width="7%" style="font-size:14px"><div align="center">原定交期</div></td>
    <td width="7%" style="font-size:14px"><div align="center">最新交期</div></td>
    <td width="8%" style="font-size:14px"><div align="center">交期/质检扣款</div></td>
    <td width="14%" style="font-size:14px"><div align="center">销售对问题解释</div></td>
    <td width="14%" style="font-size:14px"><div align="center">采购对问题解释</div></td>
    <td width="14%" style="font-size:14px"><div align="center">质检对问题的解释</div></td>
    <td width="8%" style="font-size:14px"><div align="center">领导指令</div></td>
    <td width="3%" style="font-size:14px"><div align="center">详情</div></td>
  </tr>
		<c:forEach  var="fuwa" items="${deliveryQuality}" >
		<tr bgcolor="#FFFFFF"  <c:out value="${fuwa[15]=='1'?'':'class=redcolor'}" />>
		<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[8]}" /></label></td>
		<td style="color:#333333;"><a style="font-size:14px; color:#333333" href='http://192.168.1.2/erp/po/CaseInPo.aspx?id=<c:out value="${fuwa[0]}" />'><c:out value="${fuwa[0]}" /></a></td>
		<td style="color:#333333;"><c:out value="${fuwa[1]}" /></td>
		<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[2]}" />,<br><c:out value="${fuwa[3]}" />,<br/><c:out value="${fuwa[16]}" /></label></td>
		<td style="color:#333333;"><label style="font-size: 10px;"><c:out value="${fuwa[4]}" />/<c:out value="${fuwa[5]}" /></label></td>
		<td style="color:#333333;"><c:out value="${fuwa[6]}" /></td>
		<td style="color:#333333;">
		 <c:set value="${fn:substring(fuwa[7], 0, fn:indexOf(fuwa[7],','))}" var="name2" ></c:set>
		 <c:out value="${name2==''?'':name2}"  />
		 </td>
		 <!-- （今天-原定交期）-客户原因造成的拖延天数-公司特许的交期拖延）* 30 -->
		<td style="color:red;">
		 <c:out value="${fuwa[9]}" />/ <c:out value="${fuwa[14]}" />
		</td>
	 
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[10]}" /></label>
		</td>
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[11]}" /></label>
		</td>
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[12]}" /></label>
		</td>
		<td style="color:#333333;"><label style="font-size: 10px;">
		 <c:out value="${fuwa[13]}" /></label>
		</td>
		<td style="color:#333333;"><a href="ProjectInfoServlet?id=${fuwa[8]}" style="font-size:14px; color:#333333">详情</a></td>
	</tr>
	</c:forEach> 
  </table>
  </td></tr>
  <tr>
    <td colspan="9" style="border-top:dashed #CCCCCC 1px">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="9">&nbsp;</td>
    </tr>
  
</table>
</body>
</html>