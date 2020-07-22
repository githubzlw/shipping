<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出口货物明细单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<h1 align="center">出口货物明细单</h1>
  	
  	<h2>出口企业名称：上海凯融信息科技有限公司</h2>
  	<table border="1">
  		<tr>
  			<td rowspan="2">开  户<br/>银  行	</td>
  			<td rowspan="2" colspan="2">中国银行上海市福州路支行</td>
  			<td>信用证编号</td>
  			<td></td>
  			<td>填制单位编号</td>
  			<td colspan="2">3101960157</td>
  		</tr>
  		<tr>
  			<td>银行编号</td>
  			<td>044094-860016005008093001</td>
  			<td>外运公司编号</td>
  			<td colspan="2"></td>
  		</tr>
  		<tr>
  			<td rowspan="3">经营单位<br/>(SHIPPER)</td>
  			<td rowspan="3" colspan="2">上海凯融信息科技有限公司<br/>Shanghai CS Manufacturing Co.</td>
  			<td colspan="5">合同（合约）号: <%=request.getAttribute("purnoall") %></td>
  		</tr>
  		<tr>
  			<td>开证日期</td>
  			<td></td>
  			<td>收到日期</td>
  			<td colspan="2"></td>
  		</tr>
  		<tr>
  			<td>金额</td>
  			<td><%=request.getAttribute("totalMoney")%></td>
  			<td>收汇方式</td>
  			<td colspan="2">T/T</td>
  		</tr>
  		<tr>
  			<td>收货人<br/>(CONSIGNEE)</td>
  			<td colspan="2"><%=request.getAttribute("clientName")%></td>
  			<td>货物性质</td>
  			<td>（一般）贸易（）补偿（）退运</td>
  			<td>贸易国别（地区）</td>
  			<td colspan="2"><%=request.getAttribute("towhere")%></td>
  		</tr>
  		<tr>
  			<td rowspan="3">提单或承运收据</td>
  			<td rowspan="3">通知人NOTIFY</td>
  			<td rowspan="3">SAME  AS CONSIGNEE</td>
  			<td>中间商及名称地址</td>
  			<td colspan="4">佣金&nbsp;&nbsp;&nbsp;%</td>
  		</tr>
  		<tr>
  			<td>出口口岸</td>
  			<td><%=request.getAttribute("fromwhere")%></td>
  			<td>目的港</td>
  			<td colspan="2"><%=request.getAttribute("towhere")%></td>
  		</tr>
  		<tr>
  			<td>可否转运</td>
  			<td></td>
  			<td>可否分批</td>
  			<td colspan="2"></td>
  		</tr>
  		<tr>
  			<td>运费FREIGHT(&nbsp;&nbsp;&nbsp;&nbsp;)</td>
  			<td><%=request.getAttribute("yunfeifs")%></td>
  			<td>提单:&nbsp;&nbsp;张</td>
  			<td>装运期限</td>
  			<td></td>
  			<td>有效期限</td>
  			<td colspan="2"></td>
  		</tr>
  		<tr>
  			<td rowspan="2">标记<br/>唛码</td>
  			<td rowspan="2">货名规格及货号</td>
  			<td rowspan="2">件数及包装</td>
  			<td rowspan="2">数量及单位</td>
  			<td rowspan="2">毛重<br/>（KGS）</td>
  			<td rowspan="2">净重<br/>（KGS）</td>
  			<td colspan="2">价格（成交条件）</td>
  		</tr>
  		<tr>
  			<td>单价</td>
  			<td>总价</td>
  		</tr>
  		<tr>
  			<td>N/M</td>
  			<td colspan="7"><%=request.getAttribute("allContent")%></td>
  		</tr>
  		<tr>
  			<td></td>
  			<td colspan="3"></td>
  			<td colspan="4">总体积：<%=request.getAttribute("volume")%> CBM(体积)</td>
  		</tr>
  		
  		
  		
  	</table>
  </body>
</html>
