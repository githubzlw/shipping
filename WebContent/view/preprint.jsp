<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'preprint.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js">
	</script>
  </head>
  
  <body>	
   	<table border="1">
   		<input type="text" name="id" style="display:none;" value="<%=request.getAttribute("id")%>"/>
   		采购：<%=request.getAttribute("purchase")%> &nbsp;&nbsp;&nbsp;&nbsp;
   		销售：<%=request.getAttribute("sale")%>&nbsp;&nbsp;&nbsp;&nbsp;
   		客户名：<%=request.getAttribute("clientName")%>&nbsp;&nbsp;&nbsp;&nbsp;
   		客户公司名：<%=request.getAttribute("companyName") == null ? "" : request.getAttribute("companyName")%>&nbsp;&nbsp;&nbsp;&nbsp;
   		<c:if test="${attrSource == 1 && auth == 1}">
	   	收货人地址：<%=request.getAttribute("address")%><br/>
	   	</c:if>
   		<c:if test="${attrSource == 0}">
	   	收货人地址：<%=request.getAttribute("address")%><br/>
	   	</c:if>
	   	<c:if test="${currency != 'USD'}">
	   	<span style="color:red;">注意:订单的货币单元是 (${currency})</span>
	   	</c:if>
	   	<table border="1">
	   		<tr>
	   			<td width="80px">采购合同号</td>
	   			<td width="80px">工厂名称</td>
	   			<td width="80px">合同金额</td>
	   			<td width="80px">第几次出货批次</td>
	   			<td width="80px">总共几次出货批次</td>
	   			<td width="80px">本次出口人民币金额</td>
	   		</tr>
	   			
			<%
		    Integer totalSize = Integer.parseInt(request.getAttribute("totalSize").toString());
	        for(int i=0;i<totalSize;i++){
	    	%>
	   		<tr>
	   			<td><%=request.getAttribute("purno"+(i+1))%></td>
	   			<td><%=request.getAttribute("factory"+(i+1))%></td>
	   			<td><%=request.getAttribute("money"+(i+1))%></td>
	   			<td><%=request.getAttribute("times"+(i+1))%></td>
	   			<td><%=request.getAttribute("totaltimes"+(i+1))%></td>
	   			<td><%=request.getAttribute("rmb"+(i+1))%></td>
	   		</tr>
		   	  <% 	  
	   			}
	   		   %>	
	   		
	   	</table>
	   	<br/>
	   	希望出口日期:<%=request.getAttribute("hopeDate")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	预计工厂可送仓日期:<%=request.getAttribute("estimateDate")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	交易方式 Term:<%=request.getAttribute("transaction1")%><br/>
	   	货运方式 :<%=request.getAttribute("transaction2")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	总体积(CBM):<%=request.getAttribute("volume")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	总毛重 G.W(KGS):<%=request.getAttribute("totalGW")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	总净总 N.W(KGS): <%=request.getAttribute("totalNW")%><br/>
	   	From:<%=request.getAttribute("fromwhere")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	To:<%=request.getAttribute("towhere")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	Package 包装类型:<%=request.getAttribute("package")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	Package 包装数量:<%=request.getAttribute("packageNum")%><br/>
	   	支付币种：<%=request.getAttribute("currency")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	托盘/木箱尺寸：<%=request.getAttribute("palletDimension")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	小箱件数：<%=request.getAttribute("casketQuantity")%><br/>
	   	订框箱型：<%=request.getAttribute("casketType")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	特殊要求备注：<%=request.getAttribute("detailed")%><br/>
	   	<table border="1">
	   		<tr>
	   			<td width="50px">Item英文名</td>
	   			<td width="50px">Item中文名</td>
			    <td width="50px">Quantity(请只填数字)</td>
			    <td width="50px">数量单位</td>
			    <td width="50px"><strong>采购价 总价(只填数字 单位:RMB)</strong></td>
			    <td width="80px">Unit Price(对外销售单价)</td>
			    <td width="80px">(客户)清关总价</td>
			    <td width="50px">Shipping Mark</td>
			    <td width="50px"><p>N.W.(请只填数字 单位:kg)</p></td>
			    <td width="50px">境内货源地</td>
			    <td width="50px">实际报关总价(会计填)</td>
			    <td width="50px">HS Code(物流填)</td>
			    <td width="50px">退税率(物流填)</td>
	   		</tr>
	   			<%
	   			if(request.getAttribute("itemid1")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng1")%></td>
	   			<td><%=request.getAttribute("itemchn1")%></td>
	   			<td><%=request.getAttribute("quantity1")%></td>
	   			<td><%=request.getAttribute("unit1")%></td>
	   			<td><%=request.getAttribute("purprice1")%></td>
	   			<td><%=request.getAttribute("unitprice1")%></td>
	   			<td><%=request.getAttribute("unitpriceall1")%></td>
	   			<td><%=request.getAttribute("shopingmark1")%></td>
	   			<td><%=request.getAttribute("nw1")%></td>
	   			<td><%=request.getAttribute("sourceDestination1")%></td>
	   			<td><%=request.getAttribute("trueprice1")%></td>
	   			<td><%=request.getAttribute("hscode1")%></td>
	   			<td><%=request.getAttribute("rate1")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid2")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng2")%></td>
	   			<td><%=request.getAttribute("itemchn2")%></td>
	   			<td><%=request.getAttribute("quantity2")%></td>
	   			<td><%=request.getAttribute("unit2")%></td>
	   			<td><%=request.getAttribute("purprice2")%></td>
	   			<td><%=request.getAttribute("unitprice2")%></td>
	   			<td><%=request.getAttribute("unitpriceall2")%></td>
	   			<td><%=request.getAttribute("shopingmark2")%></td>
	   			<td><%=request.getAttribute("nw2")%></td>
	   			<td><%=request.getAttribute("sourceDestination2")%></td>
	   			<td><%=request.getAttribute("trueprice2")%></td>
	   			<td><%=request.getAttribute("hscode2")%></td>
	   			<td><%=request.getAttribute("rate2")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid3")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng3")%></td>
	   			<td><%=request.getAttribute("itemchn3")%></td>
	   			<td><%=request.getAttribute("quantity3")%></td>
	   			<td><%=request.getAttribute("unit3")%></td>
	   			<td><%=request.getAttribute("purprice3")%></td>
	   			<td><%=request.getAttribute("unitprice3")%></td>
	   			<td><%=request.getAttribute("unitpriceall3")%></td>
	   			<td><%=request.getAttribute("shopingmark3")%></td>
	   			<td><%=request.getAttribute("nw3")%></td>
	   			<td><%=request.getAttribute("sourceDestination3")%></td>
	   			<td><%=request.getAttribute("trueprice3")%></td>
	   			<td><%=request.getAttribute("hscode3")%></td>
	   			<td><%=request.getAttribute("rate3")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid4")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng4")%></td>
	   			<td><%=request.getAttribute("itemchn4")%></td>
	   			<td><%=request.getAttribute("quantity4")%></td>
	   			<td><%=request.getAttribute("unit4")%></td>
	   			<td><%=request.getAttribute("purprice4")%></td>
	   			<td><%=request.getAttribute("unitprice4")%></td>
	   			<td><%=request.getAttribute("unitpriceall4")%></td>
	   			<td><%=request.getAttribute("shopingmark4")%></td>
	   			<td><%=request.getAttribute("nw4")%></td>
	   			<td><%=request.getAttribute("sourceDestination4")%></td>
	   			<td><%=request.getAttribute("trueprice4")%></td>
	   			<td><%=request.getAttribute("hscode4")%></td>
	   			<td><%=request.getAttribute("rate4")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid5")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng5")%></td>
	   			<td><%=request.getAttribute("itemchn5")%></td>
	   			<td><%=request.getAttribute("quantity5")%></td>
	   			<td><%=request.getAttribute("unit5")%></td>
	   			<td><%=request.getAttribute("purprice5")%></td>
	   			<td><%=request.getAttribute("unitprice5")%></td>
	   			<td><%=request.getAttribute("unitpriceall5")%></td>
	   			<td><%=request.getAttribute("shopingmark5")%></td>
	   			<td><%=request.getAttribute("nw5")%></td>
	   			<td><%=request.getAttribute("sourceDestination5")%></td>
	   			<td><%=request.getAttribute("trueprice5")%></td>
	   			<td><%=request.getAttribute("hscode5")%></td>
	   			<td><%=request.getAttribute("rate5")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid6")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng6")%></td>
	   			<td><%=request.getAttribute("itemchn6")%></td>
	   			<td><%=request.getAttribute("quantity6")%></td>
	   			<td><%=request.getAttribute("unit6")%></td>
	   			<td><%=request.getAttribute("purprice6")%></td>
	   			<td><%=request.getAttribute("unitprice6")%></td>
	   			<td><%=request.getAttribute("unitpriceall6")%></td>
	   			<td><%=request.getAttribute("shopingmark6")%></td>
	   			<td><%=request.getAttribute("nw6")%></td>
	   			<td><%=request.getAttribute("sourceDestination6")%></td>
	   			<td><%=request.getAttribute("trueprice6")%></td>
	   			<td><%=request.getAttribute("hscode6")%></td>
	   			<td><%=request.getAttribute("rate6")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid7")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng7")%></td>
	   			<td><%=request.getAttribute("itemchn7")%></td>
	   			<td><%=request.getAttribute("quantity7")%></td>
	   			<td><%=request.getAttribute("unit7")%></td>
	   			<td><%=request.getAttribute("purprice7")%></td>
	   			<td><%=request.getAttribute("unitprice7")%></td>
	   			<td><%=request.getAttribute("unitpriceall7")%></td>
	   			<td><%=request.getAttribute("shopingmark7")%></td>
	   			<td><%=request.getAttribute("nw7")%></td>
	   			<td><%=request.getAttribute("sourceDestination7")%></td>
	   			<td><%=request.getAttribute("trueprice7")%></td>
	   			<td><%=request.getAttribute("hscode7")%></td>
	   			<td><%=request.getAttribute("rate7")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid8")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng8")%></td>
	   			<td><%=request.getAttribute("itemchn8")%></td>
	   			<td><%=request.getAttribute("quantity8")%></td>
	   			<td><%=request.getAttribute("unit8")%></td>
	   			<td><%=request.getAttribute("purprice8")%></td>
	   			<td><%=request.getAttribute("unitprice8")%></td>
	   			<td><%=request.getAttribute("unitpriceall8")%></td>
	   			<td><%=request.getAttribute("shopingmark8")%></td>
	   			<td><%=request.getAttribute("nw8")%></td>
	   			<td><%=request.getAttribute("sourceDestination8")%></td>
	   			<td><%=request.getAttribute("trueprice8")%></td>
	   			<td><%=request.getAttribute("hscode8")%></td>
	   			<td><%=request.getAttribute("rate8")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid9")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng9")%></td>
	   			<td><%=request.getAttribute("itemchn9")%></td>
	   			<td><%=request.getAttribute("quantity9")%></td>
	   			<td><%=request.getAttribute("unit9")%></td>
	   			<td><%=request.getAttribute("purprice9")%></td>
	   			<td><%=request.getAttribute("unitprice9")%></td>
	   			<td><%=request.getAttribute("unitpriceall9")%></td>
	   			<td><%=request.getAttribute("shopingmark9")%></td>
	   			<td><%=request.getAttribute("nw9")%></td>
	   			<td><%=request.getAttribute("sourceDestination9")%></td>
	   			<td><%=request.getAttribute("trueprice9")%></td>
	   			<td><%=request.getAttribute("hscode9")%></td>
	   			<td><%=request.getAttribute("rate9")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid10")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng10")%></td>
	   			<td><%=request.getAttribute("itemchn10")%></td>
	   			<td><%=request.getAttribute("quantity10")%></td>
	   			<td><%=request.getAttribute("unit10")%></td>
	   			<td><%=request.getAttribute("purprice10")%></td>
	   			<td><%=request.getAttribute("unitprice10")%></td>
	   			<td><%=request.getAttribute("unitpriceall10")%></td>
	   			<td><%=request.getAttribute("shopingmark10")%></td>
	   			<td><%=request.getAttribute("nw10")%></td>
	   			<td><%=request.getAttribute("sourceDestination10")%></td>
	   			<td><%=request.getAttribute("trueprice10")%></td>
	   			<td><%=request.getAttribute("hscode10")%></td>
	   			<td><%=request.getAttribute("rate10")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid11")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng11")%></td>
	   			<td><%=request.getAttribute("itemchn11")%></td>
	   			<td><%=request.getAttribute("quantity11")%></td>
	   			<td><%=request.getAttribute("unit11")%></td>
	   			<td><%=request.getAttribute("purprice11")%></td>
	   			<td><%=request.getAttribute("unitprice11")%></td>
	   			<td><%=request.getAttribute("unitpriceall11")%></td>
	   			<td><%=request.getAttribute("shopingmark11")%></td>
	   			<td><%=request.getAttribute("nw11")%></td>
	   			<td><%=request.getAttribute("sourceDestination11")%></td>
	   			<td><%=request.getAttribute("trueprice11")%></td>
	   			<td><%=request.getAttribute("hscode11")%></td>
	   			<td><%=request.getAttribute("rate11")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid12")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng12")%></td>
	   			<td><%=request.getAttribute("itemchn12")%></td>
	   			<td><%=request.getAttribute("quantity12")%></td>
	   			<td><%=request.getAttribute("unit12")%></td>
	   			<td><%=request.getAttribute("purprice12")%></td>
	   			<td><%=request.getAttribute("unitprice12")%></td>
	   			<td><%=request.getAttribute("unitpriceall12")%></td>
	   			<td><%=request.getAttribute("shopingmark12")%></td>
	   			<td><%=request.getAttribute("nw12")%></td>
	   			<td><%=request.getAttribute("sourceDestination12")%></td>
	   			<td><%=request.getAttribute("trueprice12")%></td>
	   			<td><%=request.getAttribute("hscode12")%></td>
	   			<td><%=request.getAttribute("rate12")%></td>
	   		</tr>
		   		<%}
	   			if(request.getAttribute("itemid13")!=null){
	   				%>
	   		<tr>
	   			<td><%=request.getAttribute("itemeng13")%></td>
	   			<td><%=request.getAttribute("itemchn13")%></td>
	   			<td><%=request.getAttribute("quantity13")%></td>
	   			<td><%=request.getAttribute("unit13")%></td>
	   			<td><%=request.getAttribute("purprice13")%></td>
	   			<td><%=request.getAttribute("unitprice13")%></td>
	   			<td><%=request.getAttribute("unitpriceall13")%></td>
	   			<td><%=request.getAttribute("shopingmark13")%></td>
	   			<td><%=request.getAttribute("nw13")%></td>
	   			<td><%=request.getAttribute("sourceDestination13")%></td>
	   			<td><%=request.getAttribute("trueprice13")%></td>
	   			<td><%=request.getAttribute("hscode13")%></td>
	   			<td><%=request.getAttribute("rate13")%></td>
	   		</tr>
		   		<%} %>
	   	</table>
	   	<br/>
	   	指定货代：<%=request.getAttribute("frieght")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	报关发票号：<%=request.getAttribute("Nonum")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	发票日期：<%=request.getAttribute("date")%><br/>
	   	sailing Date:<%=request.getAttribute("saildate")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	Arrive Date:<%=request.getAttribute("arriveDate") == null ? "" : request.getAttribute("arriveDate")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	货代:<%=request.getAttribute("huodai")%>&nbsp;&nbsp;&nbsp;&nbsp;
	   	运费:<%=request.getAttribute("yunfei")%>&nbsp;&nbsp;&nbsp;&nbsp;
   	 	运费方式 :<%=request.getAttribute("yunfeifs")%>
	   	保费:<%=request.getAttribute("premium")%>&nbsp;&nbsp;&nbsp;&nbsp;
			<br/>
	   	外销合同时间：<%=request.getAttribute("waixiaotime")%><br/>
	   	发货地址：<%=request.getAttribute("exportPlace")%><br/>
			
   	</table>
   <br/>
 	<a href="javascript:window.print();">打印</a>
 	<a href='InfoServlet?id=<%=request.getAttribute("id")%>' target='showframe'>修改</a>
  </body>
</html>
