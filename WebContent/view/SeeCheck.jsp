<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'update.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js">
	</script>
  </head>
  
  <body>	
  <h1>查看出运单</h1>
   <form action="UpdateServlet" method="post">
   		<input type="text" name="id" style="display:none;" value="<%=request.getAttribute("id")%>"/>
   		采购：<input name="purchase" type="text" value="<%=request.getAttribute("purchase")%>"/>
   		销售：<input name="sale" type="text" value="<%=request.getAttribute("sale")%>"/>
   		客户名：<input name="clientName" type="text" value="<%=request.getAttribute("clientName")%>"/>
	   	<br/>
	   	<c:if test="${attrSource == 1 && auth == 1}">
	   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
	   	<br/>
	   	</c:if>
	   	<c:if test="${attrSource == 0}">
	   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
	   	<br/>
	   	</c:if>
	   	<table border="1">
	   		<tr>
	   			<td>采购合同号</td>
	   			<td>工厂名称</td>
	   			<td>合同金额</td>
	   			<td>第几次出货批次</td>
	   			<td>总共几次出货批次</td>
	   			<td>本次出口人民币金额</td>
	   		</tr>
	   				<%
   				    Integer totalSize = Integer.parseInt(request.getAttribute("totalSize").toString());
   					if(totalSize != 0){
   			        for(int i=0;i<totalSize;i++){
   			    	%>
   			    	 <tr>
	   			    	<td><input type="text" name="purno<%=i+1%>" class="order-id" value="<%=request.getAttribute("purno"+(i+1))%>" onblur="getDetailByProjectId(this,1)"/></td>
			   			<td><input type="text" name="factory<%=i+1%>" value="<%=request.getAttribute("factory"+(i+1))%>"/></td>
			   			<td><input type="text" name="money<%=i+1%>" value="<%=request.getAttribute("money"+(i+1))%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
			   			<td><input type="text" name="times<%=i+1%>" value="<%=request.getAttribute("times"+(i+1))%>"/></td>
			   			<td><input type="text" name="totaltimes<%=i+1%>" value="<%=request.getAttribute("totaltimes"+(i+1))%>"/></td>
			   			<td><input type="text" name="rmb<%=i+1%>" class="export-cn" value="<%=request.getAttribute("rmb"+(i+1))%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			     </tr>
	   			    <% 	  
	   			      }
   					}else{
   					%>	
   					<tr>
	   			    	<td><input type="text" name="purno1" class="order-id" onblur="getDetailByProjectId(this,1)"/></td>
			   			<td><input type="text" name="factory1"/></td>
			   			<td><input type="text" name="money1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
			   			<td><input type="text" name="times1"/></td>
			   			<td><input type="text" name="totaltimes1"/></td>
			   			<td><input type="text" name="rmb1" class="export-cn"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			     </tr>
   					<% 	
   					}
	   				%>	
	   		
	   	</table>
	   	<br/>
	   	希望出口日期:<input name="hopeDate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("hopeDate")%>"/>
	   	预计工厂可送仓日期:<input name="estimateDate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("estimateDate")%>"/>
	   	<br/>
	   	
	   	交易方式 Term:<input name="transaction1" type="text" value="<%=request.getAttribute("transaction1")%>"/>
	   	货运方式 :<input type="radio" name="transaction2" value="海运">海运
				<input type="radio" name="transaction2" value="陆运">陆运
				<input type="radio" name="transaction2" value="空运">空运
				<input type="radio" name="transaction2" value="快递">快递
	   	<br/>
	   	总体积(CBM):<input name="volume" type="text" value="<%=request.getAttribute("volume")%>"/>
	   	总毛重 G.W(KGS):<input name="totalGW" type="text" value="<%=request.getAttribute("totalGW")%>"/>
	   	总净总 N.W(KGS): <input name="totalNW" type="text" value="<%=request.getAttribute("totalNW")%>"/>
	   	<br/>
	   	
	   	From:<input name="fromwhere" type="text" value="<%=request.getAttribute("fromwhere")%>"/>
	   	To:<input name="towhere" type="text" value="<%=request.getAttribute("towhere")%>"/>
	   	<br/>
	   	Package 包装类型:<input name="package" type="text" value="<%=request.getAttribute("package")%>"/>
	   	Package 包装数量:<input name="packageNum" type="text" value="<%=request.getAttribute("packageNum")%>"/>
	   	<br/>
	   	支付币种：<input type="radio" name="currency" value="USD">美元
				<input type="radio" name="currency" value="EUR">欧元
				<input type="radio" name="currency" value="GBP">英镑
				<input type="radio" name="currency" value="RMB">人民币
				<input type="radio" name="currency" value="AUD">澳元
	   	<br/>
	   
	   	<br/>
	   装箱提货明细(用&ltbr&gt换行)：<textarea name="detailed" cols="45" rows="5"><%=request.getAttribute("detailed")%></textarea>
	   	<br/>
	   	<table border="1">
	   		<tr>
	   			<td width="80px">Item英文名</td>
	   			<td width="80px">Item中文名</td>
			    <td width="80px">Quantity(请只填数字)</td>
			    <td width="80px">数量单位</td>
			    <td width="80px"><strong>采购价 总价(只填数字 单位:RMB)</strong></td>
			    <td width="80px">Unit Price(对外销售单价)</td>
			    <td width="80px">(客户)清关总价</td>
			    <td width="80px">Shipping Mark</td>
			    <td width="80px"><p>N.W.(请只填数字 单位:kg)</p></td>
			    <td width="80px">境内货源地</td>
			    <td width="80px">实际报关总价(会计填)</td>
			    <td width="150px">HS Code (物流填)</td>
			    <td width="80px">退税率 (物流填 *%)</td>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid1")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng1" value="<%=request.getAttribute("itemeng1")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn1" value="<%=request.getAttribute("itemchn1")%>"/></td>
	   			<td><input size="10" type="text" name="quantity1" value="<%=request.getAttribute("quantity1")%>"/></td>
	   			<td><select name="unit1" style="width: 99%;"><option <c:if test="${unit1 == '个'}">selected</c:if>>个</option><option <c:if test="${unit1 == '件'}">selected</c:if>>件</option><option <c:if test="${unit1 == '套'}">selected</c:if>>套</option><option <c:if test="${unit1 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice1" class="export-cn1" value="<%=request.getAttribute("purprice1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice1" value="<%=request.getAttribute("unitprice1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall1" class="unit-price-all" value="<%=request.getAttribute("unitpriceall1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark1" value="<%=request.getAttribute("shopingmark1")%>"/></td>
	   			<td><input size="10" type="text" name="nw1" class="n_weight" value="<%=request.getAttribute("nw1")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination1" value="<%=request.getAttribute("sourceDestination1")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice1" class="true-price" value="<%=request.getAttribute("trueprice1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid1")%>"/></td>
	   			<td><input size="20" type="text" name="hscode1" value="<%=request.getAttribute("hscode1")%>"/></td>
	   			<td><input size="10" type="text" name="rate1" value="<%=request.getAttribute("rate1")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid1" value="<%=request.getAttribute("itemid1")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng1"/></td>
	   			<td><input size="10" type="text" name="itemchn1"/></td>
	   			<td><input size="10" type="text" name="quantity1"/></td>
	   			<td><select name="unit1" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice1" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall1" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark1" value="N/M"/></td>
	   			<td><input size="10" type="text" class="n_weight" name="nw1"/></td>
	   			<td><input size="10" type="text" name="sourceDestination1" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice1" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode1"/></td>
	   			<td><input size="10" type="text" name="rate1"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid2")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng2" value="<%=request.getAttribute("itemeng2")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn2" value="<%=request.getAttribute("itemchn2")%>"/></td>
	   			<td><input size="10" type="text" name="quantity2" value="<%=request.getAttribute("quantity2")%>"/></td>
	   			<td><select name="unit2" style="width: 99%;"><option <c:if test="${unit2 == '个'}">selected</c:if>>个</option><option <c:if test="${unit2 == '件'}">selected</c:if>>件</option><option <c:if test="${unit2 == '套'}">selected</c:if>>套</option><option <c:if test="${unit2 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice2" class="export-cn1" value="<%=request.getAttribute("purprice2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice2" value="<%=request.getAttribute("unitprice2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all" value="<%=request.getAttribute("unitpriceall2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark2" value="<%=request.getAttribute("shopingmark2")%>"/></td>
	   			<td><input size="10" type="text" name="nw2" class="n_weight" value="<%=request.getAttribute("nw2")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination2" value="<%=request.getAttribute("sourceDestination2")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice2" class="true-price" value="<%=request.getAttribute("trueprice2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid2")%>"/></td>
	   			<td><input size="20" type="text" name="hscode2" value="<%=request.getAttribute("hscode2")%>"/></td>
	   			<td><input size="10" type="text" name="rate2" value="<%=request.getAttribute("rate2")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid2" value="<%=request.getAttribute("itemid2")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng2"/></td>
	   			<td><input size="10" type="text" name="itemchn2"/></td>
	   			<td><input size="10" type="text" name="quantity2"/></td>
	   			<td><select name="unit2" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice2" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice2" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark2" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw2" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination2" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice2" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode2"/></td>
	   			<td><input size="10" type="text" name="rate2"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid3")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng3" value="<%=request.getAttribute("itemeng3")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn3" value="<%=request.getAttribute("itemchn3")%>"/></td>
	   			<td><input size="10" type="text" name="quantity3" value="<%=request.getAttribute("quantity3")%>"/></td>
	   			<td><select name="unit3" style="width: 99%;"><option <c:if test="${unit3 == '个'}">selected</c:if>>个</option><option <c:if test="${unit3 == '件'}">selected</c:if>>件</option><option <c:if test="${unit3 == '套'}">selected</c:if>>套</option><option <c:if test="${unit3 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice3" class="export-cn1" value="<%=request.getAttribute("purprice3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice3" value="<%=request.getAttribute("unitprice3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall3" class="unit-price-all" value="<%=request.getAttribute("unitpriceall3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark3" value="<%=request.getAttribute("shopingmark3")%>"/></td>
	   			<td><input size="10" type="text" name="nw3" value="<%=request.getAttribute("nw3")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination3" value="<%=request.getAttribute("sourceDestination3")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice3" class="true-price" value="<%=request.getAttribute("trueprice3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid3")%>"/></td>
	   			<td><input size="20" type="text" name="hscode3" value="<%=request.getAttribute("hscode3")%>"/></td>
	   			<td><input size="10" type="text" name="rate3" value="<%=request.getAttribute("rate3")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid3" value="<%=request.getAttribute("itemid3")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng3"/></td>
	   			<td><input size="10" type="text" name="itemchn3"/></td>
	   			<td><input size="10" type="text" name="quantity3"/></td>
	   			<td><select name="unit3" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice3" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice3" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall3" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark3" value="N/M"/></td>
	   			<td><input size="10" type="text" name="sourceDestination3" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="nw3" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="trueprice3" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode3"/></td>
	   			<td><input size="10" type="text" name="rate3"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid4")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng4" value="<%=request.getAttribute("itemeng4")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn4" value="<%=request.getAttribute("itemchn4")%>"/></td>
	   			<td><input size="10" type="text" name="quantity4" value="<%=request.getAttribute("quantity4")%>"/></td>
	   			<td><select name="unit4" style="width: 99%;"><option <c:if test="${unit4 == '个'}">selected</c:if>>个</option><option <c:if test="${unit4 == '件'}">selected</c:if>>件</option><option <c:if test="${unit4 == '套'}">selected</c:if>>套</option><option <c:if test="${unit4 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice4" class="export-cn1" value="<%=request.getAttribute("purprice4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice4" value="<%=request.getAttribute("unitprice4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall4" class="unit-price-all" value="<%=request.getAttribute("unitpriceall4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark4" value="<%=request.getAttribute("shopingmark4")%>"/></td>
	   			<td><input size="10" type="text" name="nw4" class="n_weight" value="<%=request.getAttribute("nw4")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination4" value="<%=request.getAttribute("sourceDestination4")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice4" class="true-price" value="<%=request.getAttribute("trueprice4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid4")%>"/></td>
	   			<td><input size="20" type="text" name="hscode4" value="<%=request.getAttribute("hscode4")%>"/></td>
	   			<td><input size="10" type="text" name="rate4" value="<%=request.getAttribute("rate4")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid4" value="<%=request.getAttribute("itemid4")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng4"/></td>
	   			<td><input size="10" type="text" name="itemchn4"/></td>
	   			<td><input size="10" type="text" name="quantity4"/></td>
	   			<td><select name="unit4" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice4" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice4" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall4" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark4" value="N/M"/></td>
	   			<td><input size="10" type="text" name="sourceDestination4" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="nw4" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="trueprice4" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode4"/></td>
	   			<td><input size="10" type="text" name="rate4"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid5")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng5" value="<%=request.getAttribute("itemeng5")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn5" value="<%=request.getAttribute("itemchn5")%>"/></td>
	   			<td><input size="10" type="text" name="quantity5" value="<%=request.getAttribute("quantity5")%>"/></td>
	   			<td><select name="unit5" style="width: 99%;"><option <c:if test="${unit5 == '个'}">selected</c:if>>个</option><option <c:if test="${unit5 == '件'}">selected</c:if>>件</option><option <c:if test="${unit5 == '套'}">selected</c:if>>套</option><option <c:if test="${unit5 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice5" class="export-cn1" value="<%=request.getAttribute("purprice5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice5" value="<%=request.getAttribute("unitprice5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall5" class="unit-price-all" value="<%=request.getAttribute("unitpriceall5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid5")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark5" value="<%=request.getAttribute("shopingmark5")%>"/></td>
	   			<td><input size="10" type="text" name="nw5" value="<%=request.getAttribute("nw5")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination5" value="<%=request.getAttribute("sourceDestination5")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice5" class="true-price" value="<%=request.getAttribute("trueprice5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode5" value="<%=request.getAttribute("hscode5")%>"/></td>
	   			<td><input size="10" type="text" name="rate5" value="<%=request.getAttribute("rate5")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid5" value="<%=request.getAttribute("itemid5")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng5"/></td>
	   			<td><input size="10" type="text" name="itemchn5"/></td>
	   			<td><input size="10" type="text" name="quantity5"/></td>
	   			<td><select name="unit5" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice5" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice5" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall5" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark5" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw5" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination5" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice5" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode5"/></td>
	   			<td><input size="10" type="text" name="rate5"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid6")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng6" value="<%=request.getAttribute("itemeng6")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn6" value="<%=request.getAttribute("itemchn6")%>"/></td>
	   			<td><input size="10" type="text" name="quantity6" value="<%=request.getAttribute("quantity6")%>"/></td>
	   			<td><select name="unit6" style="width: 99%;"><option <c:if test="${unit6 == '个'}">selected</c:if>>个</option><option <c:if test="${unit6 == '件'}">selected</c:if>>件</option><option <c:if test="${unit6 == '套'}">selected</c:if>>套</option><option <c:if test="${unit6 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice6" class="export-cn1" value="<%=request.getAttribute("purprice6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice6" value="<%=request.getAttribute("unitprice6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall6" class="unit-price-all" value="<%=request.getAttribute("unitpriceall6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid6")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark6" value="<%=request.getAttribute("shopingmark6")%>"/></td>
	   			<td><input size="10" type="text" name="nw6" class="n_weight" value="<%=request.getAttribute("nw6")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination6" value="<%=request.getAttribute("sourceDestination6")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice6" class="true-price" value="<%=request.getAttribute("trueprice6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode6" value="<%=request.getAttribute("hscode6")%>"/></td>
	   			<td><input size="10" type="text" name="rate6" value="<%=request.getAttribute("rate6")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid6" value="<%=request.getAttribute("itemid6")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng6"/></td>
	   			<td><input size="10" type="text" name="itemchn6"/></td>
	   			<td><input size="10" type="text" name="quantity6"/></td>
	   			<td><select name="unit6" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice6" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice6" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall6" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark6" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw6" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination6" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice6" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode6"/></td>
	   			<td><input size="10" type="text" name="rate6"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid7")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng7" value="<%=request.getAttribute("itemeng7")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn7" value="<%=request.getAttribute("itemchn7")%>"/></td>
	   			<td><input size="10" type="text" name="quantity7" value="<%=request.getAttribute("quantity7")%>"/></td>
	   			<td><select name="unit7" style="width: 99%;"><option <c:if test="${unit7 == '个'}">selected</c:if>>个</option><option <c:if test="${unit7 == '件'}">selected</c:if>>件</option><option <c:if test="${unit7 == '套'}">selected</c:if>>套</option><option <c:if test="${unit7 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice7" class="export-cn1" value="<%=request.getAttribute("purprice7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice7" value="<%=request.getAttribute("unitprice7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall7" class="unit-price-all" value="<%=request.getAttribute("unitpriceall7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid7")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark7" value="<%=request.getAttribute("shopingmark7")%>"/></td>
	   			<td><input size="10" type="text" name="nw7" value="<%=request.getAttribute("nw7")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination7" value="<%=request.getAttribute("sourceDestination7")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice7" class="true-price" value="<%=request.getAttribute("trueprice7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode7" value="<%=request.getAttribute("hscode7")%>"/></td>
	   			<td><input size="10" type="text" name="rate7" value="<%=request.getAttribute("rate7")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid7" value="<%=request.getAttribute("itemid7")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng7"/></td>
	   			<td><input size="10" type="text" name="itemchn7"/></td>
	   			<td><input size="10" type="text" name="quantity7"/></td>
	   			<td><select name="unit7" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice7" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice7" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall7" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark7" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw7" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination7" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice7" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode7"/></td>
	   			<td><input size="10" type="text" name="rate7"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid8")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng8" value="<%=request.getAttribute("itemeng8")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn8" value="<%=request.getAttribute("itemchn8")%>"/></td>
	   			<td><input size="10" type="text" name="quantity8" value="<%=request.getAttribute("quantity8")%>"/></td>
	   			<td><select name="unit8" style="width: 99%;"><option <c:if test="${unit8 == '个'}">selected</c:if>>个</option><option <c:if test="${unit8 == '件'}">selected</c:if>>件</option><option <c:if test="${unit8 == '套'}">selected</c:if>>套</option><option <c:if test="${unit8 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice8"  class="export-cn1" value="<%=request.getAttribute("purprice8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice8" value="<%=request.getAttribute("unitprice8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall8" class="unit-price-all" value="<%=request.getAttribute("unitpriceall8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid8")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark8" value="<%=request.getAttribute("shopingmark8")%>"/></td>
	   			<td><input size="10" type="text" name="nw8" value="<%=request.getAttribute("nw8")%>" class="n_weight"/></td>
                <td><input size="10" type="text" name="sourceDestination8" value="<%=request.getAttribute("sourceDestination8")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice8" class="true-price" value="<%=request.getAttribute("trueprice8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode8" value="<%=request.getAttribute("hscode8")%>"/></td>
	   			<td><input size="10" type="text" name="rate8" value="<%=request.getAttribute("rate8")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid8" value="<%=request.getAttribute("itemid8")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng8"/></td>
	   			<td><input size="10" type="text" name="itemchn8"/></td>
	   			<td><input size="10" type="text" name="quantity8"/></td>
	   			<td><select name="unit8" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice8" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice8" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall8" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark8" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw8" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination8" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice8" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode8"/></td>
	   			<td><input size="10" type="text" name="rate8"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid9")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng9" value="<%=request.getAttribute("itemeng9")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn9" value="<%=request.getAttribute("itemchn9")%>"/></td>
	   			<td><input size="10" type="text" name="quantity9" value="<%=request.getAttribute("quantity9")%>"/></td>
	   			<td><select name="unit9" style="width: 99%;"><option <c:if test="${unit9 == '个'}">selected</c:if>>个</option><option <c:if test="${unit9 == '件'}">selected</c:if>>件</option><option <c:if test="${unit9 == '套'}">selected</c:if>>套</option><option <c:if test="${unit9 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice9" class="export-cn1" value="<%=request.getAttribute("purprice9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice9" value="<%=request.getAttribute("unitprice9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall9" class="unit-price-all" value="<%=request.getAttribute("unitpriceall9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid9")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark9" value="<%=request.getAttribute("shopingmark9")%>"/></td>
	   			<td><input size="10" type="text" name="nw9" value="<%=request.getAttribute("nw9")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination9" value="<%=request.getAttribute("sourceDestination9")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice9" class="true-price" value="<%=request.getAttribute("trueprice9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode9" value="<%=request.getAttribute("hscode9")%>"/></td>
	   			<td><input size="10" type="text" name="rate9" value="<%=request.getAttribute("rate9")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid9" value="<%=request.getAttribute("itemid9")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng9"/></td>
	   			<td><input size="10" type="text" name="itemchn9"/></td>
	   			<td><input size="10" type="text" name="quantity9"/></td>
	   			<td><select name="unit9" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice9" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice9" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall9" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark9" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw9" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination9" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice9" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode9"/></td>
	   			<td><input size="10" type="text" name="rate9"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid10")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng10" value="<%=request.getAttribute("itemeng10")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn10" value="<%=request.getAttribute("itemchn10")%>"/></td>
	   			<td><input size="10" type="text" name="quantity10" value="<%=request.getAttribute("quantity10")%>"/></td>
	   			<td><select name="unit10" style="width: 99%;"><option <c:if test="${unit10 == '个'}">selected</c:if>>个</option><option <c:if test="${unit10 == '件'}">selected</c:if>>件</option><option <c:if test="${unit10 == '套'}">selected</c:if>>套</option><option <c:if test="${unit10 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice10" class="export-cn1" value="<%=request.getAttribute("purprice10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice10" value="<%=request.getAttribute("unitprice10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall10" class="unit-price-all" value="<%=request.getAttribute("unitpriceall10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid10")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark10" value="<%=request.getAttribute("shopingmark10")%>"/></td>
	   			<td><input size="10" type="text" name="nw10" value="<%=request.getAttribute("nw10")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination10" value="<%=request.getAttribute("sourceDestination10")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice10" class="true-price" value="<%=request.getAttribute("trueprice10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode10" value="<%=request.getAttribute("hscode10")%>"/></td>
	   			<td><input size="10" type="text" name="rate10" value="<%=request.getAttribute("rate10")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid10" value="<%=request.getAttribute("itemid10")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng10"/></td>
	   			<td><input size="10" type="text" name="itemchn10"/></td>
	   			<td><input size="10" type="text" name="quantity10"/></td>
	   			<td><select name="unit10" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice10" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice10" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall10" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark10" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw10" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination10" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice10" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode10"/></td>
	   			<td><input size="10" type="text" name="rate10"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid11")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng11" value="<%=request.getAttribute("itemeng11")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn11" value="<%=request.getAttribute("itemchn11")%>"/></td>
	   			<td><input size="10" type="text" name="quantity11" value="<%=request.getAttribute("quantity11")%>"/></td>
	   			<td><select name="unit11" style="width: 99%;"><option <c:if test="${unit11 == '个'}">selected</c:if>>个</option><option <c:if test="${unit11 == '件'}">selected</c:if>>件</option><option <c:if test="${unit11 == '套'}">selected</c:if>>套</option><option <c:if test="${unit11 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice11" class="export-cn1" value="<%=request.getAttribute("purprice11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice11" value="<%=request.getAttribute("unitprice11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall11" class="unit-price-all" value="<%=request.getAttribute("unitpriceall11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid11")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark11" value="<%=request.getAttribute("shopingmark11")%>"/></td>
	   			<td><input size="10" type="text" name="nw11" value="<%=request.getAttribute("nw11")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination11" value="<%=request.getAttribute("sourceDestination11")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice11" class="true-price" value="<%=request.getAttribute("trueprice11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode11" value="<%=request.getAttribute("hscode11")%>"/></td>
	   			<td><input size="10" type="text" name="rate11" value="<%=request.getAttribute("rate11")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid11" value="<%=request.getAttribute("itemid11")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng11"/></td>
	   			<td><input size="10" type="text" name="itemchn11"/></td>
	   			<td><input size="10" type="text" name="quantity11"/></td>
	   			<td><select name="unit11" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice11" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice11" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall11" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark11" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw11" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination11" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice11" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode11"/></td>
	   			<td><input size="10" type="text" name="rate11"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid12")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng12" value="<%=request.getAttribute("itemeng12")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn12" value="<%=request.getAttribute("itemchn12")%>"/></td>
	   			<td><input size="10" type="text" name="quantity12" value="<%=request.getAttribute("quantity12")%>"/></td>
	   			<td><select name="unit12" style="width: 99%;"><option <c:if test="${unit12 == '个'}">selected</c:if>>个</option><option <c:if test="${unit12 == '件'}">selected</c:if>>件</option><option <c:if test="${unit12 == '套'}">selected</c:if>>套</option><option <c:if test="${unit12 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice12" class="export-cn1" value="<%=request.getAttribute("purprice12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice12" value="<%=request.getAttribute("unitprice12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall12" class="unit-price-all" value="<%=request.getAttribute("unitpriceall12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid12")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark12" value="<%=request.getAttribute("shopingmark12")%>"/></td>
	   			<td><input size="10" type="text" name="nw12" value="<%=request.getAttribute("nw12")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination12" value="<%=request.getAttribute("sourceDestination12")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice12" class="true-price" value="<%=request.getAttribute("trueprice12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode12" value="<%=request.getAttribute("hscode12")%>"/></td>
	   			<td><input size="10" type="text" name="rate12" value="<%=request.getAttribute("rate12")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid12" value="<%=request.getAttribute("itemid12")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng12"/></td>
	   			<td><input size="10" type="text" name="itemchn12"/></td>
	   			<td><input size="10" type="text" name="quantity12"/></td>
	   			<td><select name="unit12" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice12" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice12" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall12" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark12" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw12" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination12" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice12" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode12"/></td>
	   			<td><input size="10" type="text" name="rate12"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid13")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng13" value="<%=request.getAttribute("itemeng13")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn13" value="<%=request.getAttribute("itemchn13")%>"/></td>
	   			<td><input size="10" type="text" name="quantity13" value="<%=request.getAttribute("quantity13")%>"/></td>
	   			<td><select name="unit13" style="width: 99%;"><option <c:if test="${unit13 == '个'}">selected</c:if>>个</option><option <c:if test="${unit13 == '件'}">selected</c:if>>件</option><option <c:if test="${unit13 == '套'}">selected</c:if>>套</option><option <c:if test="${unit13 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice13" class="export-cn1" value="<%=request.getAttribute("purprice13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice13" value="<%=request.getAttribute("unitprice13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall13" class="unit-price-all" value="<%=request.getAttribute("unitpriceall13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid13")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark13" value="<%=request.getAttribute("shopingmark13")%>"/></td>
	   			<td><input size="10" type="text" name="nw13" value="<%=request.getAttribute("nw13")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination13" value="<%=request.getAttribute("sourceDestination13")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice13" class="true-price" value="<%=request.getAttribute("trueprice13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode13" value="<%=request.getAttribute("hscode13")%>"/></td>
	   			<td><input size="10" type="text" name="rate13" value="<%=request.getAttribute("rate13")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid13" value="<%=request.getAttribute("itemid13")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng13"/></td>
	   			<td><input size="10" type="text" name="itemchn13"/></td>
	   			<td><input size="10" type="text" name="quantity13"/></td>
	   			<td><select name="unit13" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice13" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice13" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall13" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark13" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw13" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination13" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice13" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode13"/></td>
	   			<td><input size="10" type="text" name="rate13"/></td>
		   		<%} %>
	   		</tr>
	   	</table>
	   	<br/>
	   	指定货代（如果有）(用&ltbr&gt换行)：<textarea name="frieght"  cols="45" rows="5"><%=request.getAttribute("frieght")%></textarea>
	   	<br/>
	   		*物流填写：报关发票号：<input name="Nonum" type="text" value="<%=request.getAttribute("Nonum")%>"/>
	   	发票日期：<input name="date" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("date")%>"/></input>
	   	sailing Date:<input name="saildate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("saildate")%>"/>
	   		<br/>
	   	货代:<input name="huodai" type="text" value="<%=request.getAttribute("huodai")%>"/>
	   	运费:<input name="yunfei" type="text" value="<%=request.getAttribute("yunfei")%>"/>(* FOB 无运费)
   	 	<br/>
   	 	运费方式 :<input type="radio" name="yunfeifs" value="COLLECT"/>到付
			<input type="radio" name="yunfeifs" value="PREPAID"/>预付
		保费:<input name="premium" type="text" value="<%=request.getAttribute("premium")%>"/>
			<br/>
				<script type="text/javascript">
		    window.onload = function(){
		    var checked = '<%=request.getAttribute("currency")%>'
		        var radios = document.getElementsByName('currency');
		        var len = radios.length;
		        for(var i=0;i<len;i++){
		            if(checked==radios[i].value){
		                radios[i].setAttribute('checked','checked');
		            }
		        }
		       var checkedss='<%=request.getAttribute("transaction2")%>';
		        var radioss = document.getElementsByName('transaction2');
		        var lens = radioss.length;
		        for(var j=0;j<lens;j++){
		            if(checkedss==radioss[j].value){
		                radioss[j].setAttribute('checked','checked');
		            }
		        }
		        var checkedfs='<%=request.getAttribute("yunfeifs")%>';
		        var radiofs = document.getElementsByName('yunfeifs');
		        var lenfs = radiofs.length;
		        for(var t=0;t<lenfs;t++){
		            if(checkedfs==radiofs[t].value){
		                radiofs[t].setAttribute('checked','checked');
		            }
		        }
		    }
		</script>
   <br/>
   <%--<input type="submit" value="保存修改"></input>
   --%></form>
   <%--<form action="DeleteServlet">
   <table>
   		<input type="hidden" name="id" value="<%=request.getAttribute("id")%>"/>
   		<input type="submit" onclick="return confirm('真的要删除吗?')" name="submit1" value="删除">
   		<input type="submit" value="删除"></input>
   		</table>
   </form>
  --%></body>
</html>
