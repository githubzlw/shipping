<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>详细信息Costom</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function openwin(sas){
			window.open("ExplainServlet?hscode="+sas,"newwindow","height=200,width=400,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no")
		}
	</script>
  </head>
  
  <body>
  	<h1 align="center">中华人民共和国海关出口货物报关单</h1>
   	<table border="1" id="table">
   		<tr>
   			<td colspan="2">
   			<div style='float:up;height:50%'>境内发货人（913101017862841399）</div>
			<div style='float:down;height:50%'><Strong>上海凯融信息科技有限公司</Strong></div>
			</td>
   			<td colspan="2">
   			<div style='float:up;height:50%'>出境关别</div>
			<div style='float:down;height:50%'><strong><%=request.getAttribute("fromwhere") %></strong></div>
			</td>
   			<td colspan="2">
   			<div style='float:up;height:50%'>出口日期</div>
			<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td>
   			<div style='float:up;height:50%'>申报日期</div>
			<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="2">
   			<div style='float:up;height:50%'>备案号</div>
			<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>境外收货人</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("companyName") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>运输方式</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("transaction2") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>运输工具名称及航次号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="3">
	   			<div style='float:up;height:50%'>提运单号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>生产销售单位（913101017862841399）</div>
				<div style='float:down;height:50%'>上海凯融信息科技有限公司</div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>监管方式</div>
				<div style='float:down;height:50%'>一般贸易</div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>征免性质</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="3">
	   			<div style='float:up;height:50%'>许可证号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>合同协议号</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("nonum") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>贸易国（地区）</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("towhere") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>运抵国（地区）</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("towhere") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>指运港</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("towhere") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>离境口岸</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("fromwhere") %></Strong></div>
			</td>
   		</tr>
   		<tr>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>包装种类</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("package") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>件数</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("packagenum") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>毛重（千克）</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("gw") %>&nbsp;&nbsp;</Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>净重（千克）</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("nw") %>&nbsp;&nbsp;</Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>成交方式</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("transaction1") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>运费</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("yunfei") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>保费</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("premium") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>杂费</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td colspan="9">
   				<div style='float:up;height:50%'> 随附单证及编号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td colspan="9">
   				<div style='float:up;height:50%'> 标记唛码及编号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td>项号</td>
   			<td>商品编号</td>
   			<td>商品名称及规格型号</td>
   			<td>数量及单位</td>
   			<td>单价/总价/币制</td>
   			<td>原产国（地区）</td>
   			<td>最终目的国(地区)</td>
   			<td>境内货源地</td>
   			<td>征免</td>
   		</tr>
   		
   		<c:forEach var="obj" items="${list}" varStatus="i">
   		<tr>
   		    <td>${i.index+1}</td>
   			<td><a href='#' onclick="openwin('${obj.hscode}')">${obj.hscode}</a></td>
   			<td>${obj.itemall}</td>
   			<td><div style='float:up;height:50%'>${obj.quantity}</div><div style='float:down;height:50%'>${obj.nw}</div></td>
   			<td><div style='float:up;height:33%'>${obj.unitprice}</div><div style='float:down;height:33%'>${obj.amount}</div><div style='float:down;height:33%'>${currency}</div></td>
   			<td>中国</td>
   			<td><%=request.getAttribute("towhere")%></td>
   			<td>${obj.sourceDestination}</td>
   			<td>照章征税</td>
   		</tr>
   		</c:forEach>
   		
   		
   		<tr>
   			<td colspan="9">
   				<div>
	   				<div style="float: left;padding-right: 10%;">特殊关系确认：无</div>
	   				<div style="float: left;padding-right: 20%;">价格影响确认：无</div>
					<div style="float: left;padding-right: 10%;">支付特许权使用费确认：无</div>
					<div>自报自缴：&nbsp;</div>
   				</div>
   			</td>
   		</tr>
   		<tr>
   			<td colspan="6">
   				<div style='float:up;height:50%;display: inline;'>
	   				<div style="float: left;padding-right: 5%;">报关人员</div>
					<div style="float: left;padding-right: 7%;">报关人员证号</div>
					<div style="float: left;padding-right: 7%;">电话</div>
					<div>兹申明对以上内容承担如实申报、依法纳税之法律责任</div>
				</div>
   				<div style='float:down;height:50%;display: inline;'>
	   				<div style="float: left;padding-right: 50%;">申报单位</div>
					<div>申报单位（签章）</div>
				</div>
   			</td>
   			<td colspan="3">
   				<div style='float:up;height:50%'>海关批注及签章</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>   			
   		</tr> 	
   		</table>
   	<script type="text/javascript">
// 	  var tab=document.getElementById("table");
<%-- 	  <% --%>
// 	  if(request.getAttribute("total")!=null)
// 	  {
// 		  int total=(Integer)request.getAttribute("total");
// 		  for(int j=0;j<total;j++){
<%-- 	  %> --%>
// 		  var newTR =tab.insertRow(tab.rows.length-5);
// 		  var newNameTD2 = newTR.insertCell(0);
<%-- 		  newNameTD2.innerHTML = "<td><%=(j+1)%></td>"; --%>
// 		  var newNameTD = newTR.insertCell(1);
<%-- 		  newNameTD.innerHTML = "<a href='#' onclick='openwin(\"<%=request.getAttribute("hscode"+j)%>\")'><%=request.getAttribute("hscode"+j)%></a>"; --%>
// 		  var newNameTD1 = newTR.insertCell(2);
<%-- 		  newNameTD1.innerHTML = "<%=request.getAttribute("itemall"+j)%>"; --%>
// 		  var newNameTD2 = newTR.insertCell(3);
<%-- 		  newNameTD2.innerHTML = "<td><div style='float:up;height:50%'><%=request.getAttribute("quantity"+j)%></div><div style='float:down;height:50%'><%=request.getAttribute("nw"+j)%></div></td>"; --%>
// 		  var newNameTD3 = newTR.insertCell(4);
<%-- 		  newNameTD3.innerHTML = "<td><div style='float:up;height:50%'><%=request.getAttribute("unitprice"+j)%></div><div style='float:down;height:50%'><%=request.getAttribute("amounts"+j)%></div></td>"; --%>
// 		  var newNameTD4 = newTR.insertCell(5);
<%-- 		  newNameTD4.innerHTML = "<%=request.getAttribute("amount"+j)%>"; --%>
// 		  var newNameTD5 = newTR.insertCell(6);
<%-- 		  newNameTD5.innerHTML = "<%=request.getAttribute("currency")%>"; --%>
<%-- 	  <% --%>
// 	  }
// 	  }
<%-- 	  %> --%>
  </script>
  </body>
</html>
