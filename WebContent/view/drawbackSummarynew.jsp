<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>退税汇总</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/jquery-1.3.2.min.js"></script>
  </head>
  
  <body>
  	<h1 align="center">退税汇总</h1>
  	 <form action="NewDrawBackSummaryServlet" id="form1">
  	   <select name="select1">
  	   <c:if test="${year==2013 }"><option  value ="2013">2013年</option></c:if>
  	   <c:if test="${year==2014 }"> <option   value ="2014" >2014年</option></c:if>
  	   <c:if test="${year==2015 }"><option  value ="2015">2015年</option></c:if>
  	   <c:if test="${year==2016 }"><option  value ="2016">2016年</option></c:if>
  	   <c:if test="${year==2017 }"><option  value ="2017">2017年</option></c:if>
  	   <c:if test="${year==2018 }"><option  value ="2018">2018年</option></c:if>
  	   <c:if test="${year==2019 }"><option  value ="2019">2019年</option></c:if>
  	   <c:if test="${year==2020 }"><option  value ="2020">2020年</option></c:if>
	    <option  value ="2013">2013年</option>
	    <option   value ="2014" >2014年</option>
	    <option  value ="2015">2015年</option>
	    <option  value ="2016">2016年</option>
	    <option  value ="2017">2017年</option>
	    <option  value ="2018">2018年</option>
	    <option  value ="2019" >2019年</option>
	    <option  value ="2020">2020年</option>
	  </select>
  	   <select name="select2">
  	   <c:if test="${month==1 }"><option value ="01" >1月</option></c:if>
  	   <c:if test="${month==2 }"><option value ="02" >2月</option></c:if>
  	   <c:if test="${month==3 }"><option value ="03" >3月</option></c:if>
  	   <c:if test="${month==4 }"><option value ="04" >4月</option></c:if>
  	   <c:if test="${month==5 }"><option value ="05" >5月</option></c:if>
  	   <c:if test="${month==6 }"><option value ="06" >6月</option></c:if>
  	   <c:if test="${month==7 }"><option value ="07" >7月</option></c:if>
  	   <c:if test="${month==8 }"><option value ="08" >8月</option></c:if>
  	   <c:if test="${month==9 }"><option value ="09" >9月</option></c:if>
  	   <c:if test="${month==10 }"><option value ="10" >10月</option></c:if>
  	   <c:if test="${month==11 }"><option value ="11" >11月</option></c:if>
  	   <c:if test="${month==12 }"><option value ="12" >12月</option></c:if>
	    <option value ="01" >1月</option>
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
 <%-- <form action="DrawBackPrintServlet" id="form2">
     <input type="hidden" name="select1">
     <input type="hidden" name="select2">
     <input type="button" value="导出退税表" onclick="print(this)">
  </form>
  <form action="InvoicePrintServlet" id="form3">
     <input type="hidden" name="select1">
     <input type="hidden" name="select2">
     <input type="button" value="导出开增值税发票信息表" onclick="print(this)">
  </form>--%>
  
结果：<br/>
<p style="color: #14bd14;font-weight: 600;">报关金额统计：美元：${totalTruepriceUSD}  欧元：${totalTruepriceEUR}  英镑：${totalTruepriceGBP}  澳元：${totalTruepriceAUD}</p>
<p style="color: #14bd14;font-weight: 600;">合同总金额：人民币${totalPurprice}</p>
<p style="color: #14bd14;font-weight: 600;">预估退税总金额：人民币${totalbackMoney}</p>
   	<table border="1" id="table">
   		<tr>
   			<td><Strong>月份</Strong></td>
   			<td><Strong>销售</Strong></td>
   			<td><Strong>序号</Strong></td>
   			<td><Strong>合同版号汇总</Strong></td>
   			<td><Strong>运费</Strong></td>
   			<td><Strong>中文名称</Strong></td>
   			<td><Strong>客户名称</Strong></td>
   			<td><Strong>出口日期</Strong></td>
   			<td><Strong>增票(合同)总金额</Strong></td>
   			<td><Strong>报关单总金额</Strong></td>
   			<td><Strong>海关编码</Strong></td>
   			<td><Strong>退税率</Strong></td>
   			<td><Strong>估计退税金额</Strong></td>
   		</tr>	
   		<%-- <tr>
   			<td></td>
   			<td></td>
   			<td></td>
   			<td></td>
   			<td></td>
   			<td></td>
   			<td></td>
   			<td></td>
   			<td><Strong><%=request.getAttribute("totalPurprice")%></Strong></td>
   			<td><Strong><%=request.getAttribute("totalTrueprice")%></Strong></td>
   			<td></td>
   			<td></td>
   			<td><Strong><%=request.getAttribute("totalBackMoney")%></Strong></td>
   		</tr> --%>
   	</table>
   	<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>	
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
		  newNameTD.innerHTML = "<%=request.getAttribute("month"+j)%>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "<%=request.getAttribute("sale"+j)%>";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<%=request.getAttribute("num"+j)%>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<%=request.getAttribute("purnoall"+j)%>";
		  var newNameTD4 = newTR.insertCell(4);
		  newNameTD4.innerHTML = "<%=request.getAttribute("yunfei"+j)%>";
		  var newNameTD5 = newTR.insertCell(5);
		  newNameTD5.innerHTML = "<%=request.getAttribute("itemchn"+j)%>";
		  var newNameTD6 = newTR.insertCell(6);
		  newNameTD6.innerHTML = "<%=request.getAttribute("clientName"+j)%>";
		  var newNameTD7 = newTR.insertCell(7);
		  newNameTD7.innerHTML = "<%=request.getAttribute("saildate"+j)%>";
		  var newNameTD8 = newTR.insertCell(8);
		  newNameTD8.innerHTML = "<%=request.getAttribute("purprice"+j)%>";
		  var newNameTD9 = newTR.insertCell(9);
		  newNameTD9.innerHTML = "<%=request.getAttribute("trueprice"+j)%>";
		  var newNameTD10 = newTR.insertCell(10);
		  newNameTD10.innerHTML = "<%=request.getAttribute("hscode"+j)%>";
		  var newNameTD11 = newTR.insertCell(11);
		  newNameTD11.innerHTML = "<%=request.getAttribute("rate"+j)%>";
		  var newNameTD12 = newTR.insertCell(12);
		  newNameTD12.innerHTML = "<%=request.getAttribute("backMoney"+j)%>";
	  <%
	  }	
	  }
	  %>
	  
	  //导出
	  function print(obj){
		 var select1 = $('#form1').find('select[name="select1"]').val();
		 var select2 =  $('#form1').find('select[name="select2"]').val();
		 $(obj).parents('form').find('input[name="select1"]').val(select1);
		 $(obj).parents('form').find('input[name="select2"]').val(select2);
		 $(obj).parents('form').submit();
	  }
	  
  </script>
  </body>
</html>
