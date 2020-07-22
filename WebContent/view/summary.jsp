<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>汇总</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<h1 align="center">汇总</h1>
  	 <form action="SummaryServlet">
  	   <select name="select1">
	    <option value ="2013">2013年</option>
	    <option value ="2014" selected="selected">2014年</option>
	    <option value ="2015">2015年</option>
	    <option value ="2016">2016年</option>
	    <option value ="2017">2017年</option>
	    <option value ="2018">2018年</option>
	    <option value ="2019">2019年</option>
	    <option value ="2020">2020年</option>
	  </select>
  	   <select name="select2">
	    <option value ="01" selected="selected">1月</option>
	    <option value ="02">2月</option>
	    <option value ="03">3月</option>
	    <option value ="04">4月</option>
	    <option value ="05">5月</option>
	    <option value ="06">6月</option>
	    <option value ="07">7月</option>
	    <option value ="08">8月</option>
	    <option value ="09">9月</option>
	    <option value ="10">10月</option>
	    <option value ="11">11月</option>
	    <option value ="12">12月</option>
	  </select>
  <input type="submit" value="列表">
  </form>
  
结果：<br/>
   	<table border="1" id="table">
   		<tr>
   			<td><Strong>序号</Strong></td>
   			<td><Strong>发票号</Strong></td>
   			<td><Strong>出口日期</Strong></td>
   			<td><Strong>运输方式</Strong></td>
   			<td><Strong>发货港</Strong></td>
   			<td><Strong>目的港</Strong></td>
   			<td><Strong>HS</Strong></td>
   			<td><Strong>英文名称</Strong></td>
   			<td><Strong>中文名称</Strong></td>
   			<td><Strong>成交方式</Strong></td>
   			<td><Strong>报关总金额</Strong></td>
   			<td><Strong>清关总金额</Strong></td>
   			<td><Strong>销售</Strong></td>
   			<td><Strong>采购</Strong></td>
   			<td><Strong>合同号</Strong></td>
   			<td><Strong>合同金额</Strong></td>
   			<td><Strong>出口批次</Strong></td>
   			<td><Strong>客户名称</Strong></td>
   			<td><Strong>退税率</Strong></td>
   			<td><Strong>货代</Strong></td>
   			<td><Strong>运费</Strong></td>
   		</tr>
   	</table>
   			<script type="text/javascript">
	  var tab=document.getElementById("table");
	  <%
	  if(request.getAttribute("total")!=null)
	  {
		  int total=(Integer)request.getAttribute("total");
		  for(int j=0;j<total;j++){
	  %>
		  var newTR =tab.insertRow(tab.rows.length);
		  var newNameTD = newTR.insertCell(0);
		  newNameTD.innerHTML = "<%=request.getAttribute("num"+j)%>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "<a href='InfoServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'><%=request.getAttribute("nonum"+j)%></a>";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<%=request.getAttribute("saildate"+j)%>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<%=request.getAttribute("tran2"+j)%>";
		  var newNameTD4 = newTR.insertCell(4);
		  newNameTD4.innerHTML = "<%=request.getAttribute("fromwhere"+j)%>";
		  var newNameTD5 = newTR.insertCell(5);
		  newNameTD5.innerHTML = "<%=request.getAttribute("towhere"+j)%>";
		  var newNameTD6 = newTR.insertCell(6);
		  newNameTD6.innerHTML = "<%=request.getAttribute("hscode"+j)%>";
		  var newNameTD7 = newTR.insertCell(7);
		  newNameTD7.innerHTML = "<%=request.getAttribute("itemeng"+j)%>";
		  var newNameTD8 = newTR.insertCell(8);
		  newNameTD8.innerHTML = "<%=request.getAttribute("itemchn"+j)%>";
		  var newNameTD9 = newTR.insertCell(9);
		  newNameTD9.innerHTML = "<%=request.getAttribute("tran1"+j)%>";
		  var newNameTD10 = newTR.insertCell(10);
		  newNameTD10.innerHTML = "<%=request.getAttribute("totalmoney"+j)%>";
		  var newNameTD11 = newTR.insertCell(11);
		  newNameTD11.innerHTML = "<%=request.getAttribute("unitpriceall"+j)%>";
		  var newNameTD12 = newTR.insertCell(12);
		  newNameTD12.innerHTML = "<%=request.getAttribute("sale"+j)%>";
		  var newNameTD13 = newTR.insertCell(13);
		  newNameTD13.innerHTML = "<%=request.getAttribute("purchase"+j)%>";
		  var newNameTD14 = newTR.insertCell(14);
		  newNameTD14.innerHTML = "<%=request.getAttribute("purnoall"+j)%>";
		  var newNameTD15 = newTR.insertCell(15);
		  newNameTD15.innerHTML = "<%=request.getAttribute("moneyall"+j)%>";
		  var newNameTD16 = newTR.insertCell(16);
		  newNameTD16.innerHTML = "<%=request.getAttribute("timesall"+j)%>";
		  var newNameTD17 = newTR.insertCell(17);
		  newNameTD17.innerHTML = "<%=request.getAttribute("clientName"+j)%>";
		  var newNameTD18 = newTR.insertCell(18);
		  newNameTD18.innerHTML = "<%=request.getAttribute("rate"+j)%>";
		  var newNameTD19 = newTR.insertCell(19);
		  newNameTD19.innerHTML = "<%=request.getAttribute("huodai"+j)%>";
		  var newNameTD20 = newTR.insertCell(20);
		  newNameTD20.innerHTML = "<%=request.getAttribute("yunfei"+j)%>";
	  <%
	  }
	  }
	  %>
  </script>
  </body>
</html>
