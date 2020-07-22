<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
   			<td>
   			<div style='float:up;height:50%'>出口口岸</div>
			<div style='float:down;height:50%'><Strong>SHANGHAI</Strong></div>
			</td>
   			<td colspan="2">
   			<div style='float:up;height:50%'>备案号</div>
			<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="2">
   			<div style='float:up;height:50%'>出口日期</div>
			<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td>
   			<div style='float:up;height:50%'>申报日期</div>
			<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td>
	   			<div style='float:up;height:50%'>经营单位</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("seller") %></Strong></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>运输方式</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("transaction2") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>运输工具名称</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>提运单号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td>
	   			<div style='float:up;height:50%'>提运单位</div>
				<div style='float:down;height:50%'>3101960157</div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>贸易方式</div>
				<div style='float:down;height:50%'>一般贸易</div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>征免性质</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>结汇方式</div>
				<div style='float:down;height:50%'><Strong>T/T</Strong></div>
			</td>
   		</tr>
   		<tr>
   			<td>
	   			<div style='float:up;height:50%'>许可证号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>抵运国</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("towhere") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>指运港</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("towhere") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>境内货源地</div>
				<div style='float:down;height:50%'><Strong>SHANGHAI</Strong></div>
			</td>
   		</tr>
   		<tr>
   			<td>
	   			<div style='float:up;height:50%'>批准文号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>成交方式</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("transaction1") %></Strong></div>
			</td>
   			<td colspan="2">
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
   			<td>
	   			<div style='float:up;height:50%'>合同协议号</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("nonum") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>件数</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("packagenum") %></Strong></div>
			</td>
   			<td colspan="2">
	   			<div style='float:up;height:50%'>包装种类</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("package") %></Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>毛重(公斤)</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("gw") %>&nbsp;&nbsp;KGS</Strong></div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>净重(公斤)</div>
				<div style='float:down;height:50%'><Strong><%=request.getAttribute("nw") %>&nbsp;&nbsp;KGS</Strong></div>
			</td>
   		</tr>
   		<tr>
   			<td>
	   			<div style='float:up;height:50%'>集装箱号</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td colspan="4">
	   			<div style='float:up;height:50%'>随附单据</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   			<td>
	   			<div style='float:up;height:50%'>生产厂家</div>
				<div style='float:down;height:50%'>&nbsp;</div>
			</td>
   		</tr>
   		<tr>
   			<td colspan="6">
   				<div style='float:up;height:50%'> 标记唛码及备注</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td>HS	商品编号</td>
   			<td>商品名称、规格型号</td>
   			<td>数量及单位</td>
   			<td>最终目的国(地区)单价</td>
   			<td>总价</td>
   			<td>币制</td>
   		</tr>
   		
   		<tr>
   			<td colspan="6">
   				<div style='float:up;height:10%'>税费征收情况</div>
				<div style='float:down;height:30%'>&nbsp;</div>
				<div style='float:down;height:30%'>&nbsp;</div>
				<div style='float:down;height:30%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td>
   				<div style='float:up;height:50%'>录入员</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>录入单位</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td colspan="2" rowspan="4">
   				<div style='float:up;height:10%'>兹声明以上申报无讹并承担法律责任</div>
				<div style='float:down;height:22%'>&nbsp;</div>
				<div style='float:down;height:22%'>&nbsp;</div>
				<div style='float:down;height:23%'>&nbsp;</div>
				<div style='float:down;height:23%'>&nbsp;</div>
   			</td>
   			<td colspan="2">
   				<div style='float:up;height:50%'>海关审单批注及放行日期(签章)</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td colspan="2">
   				<div style='float:up;height:50%'>报关员</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>审单</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>审价</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td colspan="2">
   				<div style='float:up;height:50%'>单位地址</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>征税</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>统计</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   		</tr>
   		<tr>
   			<td>
   				<div style='float:up;height:50%'>邮编</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>电话</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>查验</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
   			<td>
   				<div style='float:up;height:50%'>放行</div>
				<div style='float:down;height:50%'>&nbsp;</div>
   			</td>
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
		  var newTR =tab.insertRow(tab.rows.length-5);
		  var newNameTD = newTR.insertCell(0);
		  newNameTD.innerHTML = "<a href='#' onclick='openwin(\"<%=request.getAttribute("hscode"+j)%>\")'><%=request.getAttribute("hscode"+j)%></a>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "<%=request.getAttribute("itemall"+j)%>";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<td><div style='float:up;height:50%'><%=request.getAttribute("quantity"+j)%></div><div style='float:down;height:50%'><%=request.getAttribute("nw"+j)%></div></td>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<td><div style='float:up;height:50%'><%=request.getAttribute("unitprice"+j)%></div><div style='float:down;height:50%'><%=request.getAttribute("amounts"+j)%></div></td>";
		  var newNameTD4 = newTR.insertCell(4);
		  newNameTD4.innerHTML = "<%=request.getAttribute("amount"+j)%>";
		  var newNameTD5 = newTR.insertCell(5);
		  newNameTD5.innerHTML = "<%=request.getAttribute("currency")%>";
	  <%
	  }
	  }
	  %>
  </script>
  </body>
</html>
