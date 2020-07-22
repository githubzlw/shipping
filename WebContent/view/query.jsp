<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String adminName = (String)session.getAttribute("adminName");
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
	
	
	
<!--     <script type="text/javascript" src="http://192.168.1.151:8080/user/setCookie?cname=nina"></script> -->
	<script type="text/javascript" src="${ctx}/My97DatePicker/WdatePicker.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<%--<script type="text/javascript">
		function bt1(){
			var tab=document.getElementById("table");
			  <%
				  if(request.getAttribute("total")!=null)
				  {
					  int total=(Integer)request.getAttribute("total");
					  for(int j=1;j<total;j++){
			  %>
					  var newTR =tab.insertRow(tab.rows.length);
					  var newNameTD = newTR.insertCell(0);
					  newNameTD.innerHTML = "<%=request.getAttribute("nonum"+j)%>";
					  var newNameTD1 = newTR.insertCell(1);
					  newNameTD1.innerHTML = "<%=request.getAttribute("dateTime"+j)%>";
					  var newNameTD2 = newTR.insertCell(2);
					  newNameTD2.innerHTML = "<a href='' target='showframe' value='sdfdsag'></a>";
			  <%
			  		}
				  }
			  %>
		}
	
	
	</script>
  --%></head>
  
  <body>
    <% if("ERIC".equals(adminName)){ 
    %>
   <form action="QuestServlet">
  	  按 发票号进行查询：<input type="text" name="nonum">
    <input type="submit" value="查询">
    </form>
    <form action="QuestServlet">
 	 按 HS Code查询：<input type="text" name="hscode">
  <input type="submit" value="查询">
  </form>
    <form action="QuestServlet">
 	 按  客户名查询：<input type="text" name="clientName">
  <input type="submit" value="查询">
  </form>
    <form action="QuestServlet">
 	 按  合同号查询：<input type="text" name="purno">
  <input type="submit" value="查询">
  </form>       
    <% 
    }else{
    %>
    <form action="QuestServlet">
  	  按 发票号进行查询：<input type="text" name="nonum">
    <input type="submit" value="查询">
    </form>
    <form action="QuestServlet">
 	 按 HS Code查询：<input type="text" name="hscode">
  <input type="submit" value="查询">
  </form>
    <form action="QuestServlet">
 	 按  客户名查询：<input type="text" name="clientName">
  <input type="submit" value="查询">
  </form>
    <form action="QuestServlet">
 	 按  合同号查询：<input type="text" name="purno">
  <input type="submit" value="查询">
  </form>
    <form action="QuestServlet">
 	 按  录入人员姓名查询：<input type="text" name="adminName">
  <input type="submit" value="查询">
  </form>
    <form action="QuestServlet">
 	 按 产品英文名查询：<input type="text" name="goodsEng">
  <input type="submit" value="查询">
  </form>
  <form action="QuestServlet">
 	 按 产品中文名查询：<input type="text" name="goodsChn">
      <input type="submit" value="查询">
  </form>
  <form action="QuestServlet"> 	
 	 按联系单填写日期查询：开始<input type="text" name="time1" onClick="WdatePicker()" value="${time1}">
 	 结束<input type="text" name="time2" onClick="WdatePicker()" value="${time2}">
 	 <br>
 	 按联系单报关日期查询：开始<input type="text" name="time3" onClick="WdatePicker()" value="${time3}">
 	 结束<input type="text" name="time4" onClick="WdatePicker()" value="${time4}">
 	 <input type="radio" name="isShipping" value="0" checked>全部 
 	 <input type="radio" name="isShipping" value="1" <c:if test="${isShipping == 1}">checked</c:if>>准予
 	 <input type="radio" name="isShipping" value="2" <c:if test="${isShipping == 2}">checked</c:if>>未准予
      <input type="submit" value="查询">
  </form>
    <%	
    }
    %>
  
  
  查询结果：<br/>
  
   <table  border="1" id="table">
  <tr>
  <td>发票号</td>
  <td>输入人员名字</td>
  <td>录入时间</td>
  <td>操作</td>
  <td>操作1</td>
  <td>操作2</td>
  <td>操作3</td>
  <td>操作4</td>
  <td>操作5</td>
  <td>操作6</td>
  <td>操作7</td>
  <td>报关总价录入</td>
  <td>HScode录入</td>
  <td>税率录入</td>
  <td>电子准予出货单</td>
  <td>保存状态</td>
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
		  newNameTD.innerHTML = "<%=request.getAttribute("nonum"+j)%>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "<%=request.getAttribute("adminName"+j)%>";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<%=request.getAttribute("timeDate"+j)%>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<a href='QingGuanInvoiceServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>清关Invoice</a>";
		  var newNameTD4 = newTR.insertCell(4);
		  newNameTD4.innerHTML = "<a href='InvoiceServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>报关Invoice</a>";
		  var newNameTD5 = newTR.insertCell(5);
		  newNameTD5.innerHTML = "<a href='PackingServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>点击查看packing</a>";
		  var newNameTD6 = newTR.insertCell(6);
		  newNameTD6.innerHTML = "<a href='CostomServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>点击查看报关单</a>";
		  var newNameTD7 = newTR.insertCell(7);
		  newNameTD7.innerHTML = "<a href='DetailListServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>出口货物明细单</a>";
		  var newNameTD8 = newTR.insertCell(8);
		  newNameTD8.innerHTML = "<a href='InfoServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>修改</a>";
		  var newNameTD9 = newTR.insertCell(9);
		  newNameTD9.innerHTML = "<a href='forOnRecord?id=<%=request.getAttribute("id"+j)%>' target='showframe'>备案</a>";
		  var newNameTD10 = newTR.insertCell(10);
		  newNameTD10.innerHTML = "<a href='contract?id=<%=request.getAttribute("id"+j)%>' target='showframe'>外销合同</a>";
		  
		  //报关金额是否确认
		  var newNameTD11 = newTR.insertCell(11);
		  <%
		  if(request.getAttribute("trueprice"+j) != null && !"".equals(request.getAttribute("trueprice"+j))){
	      %>
	      newNameTD11.innerHTML = "<span>已录</span>";
		  <%	  
		  }else{
		  %>	 	  
		  newNameTD11.innerHTML = "<span style='color: red;'>未录</span>"; 
		  <% 
		  }
		  %>
		  
		  //HS code是否录入
		  var newNameTD12 = newTR.insertCell(12);
		  <%
		  if(request.getAttribute("hscode"+j) != null && !"".equals(request.getAttribute("hscode"+j))){
	      %>
	      newNameTD12.innerHTML = "<span>已录</span>";
		  <%	  
		  }else{
		  %>	 	  
		  newNameTD12.innerHTML = "<span style='color: red;'>未录</span>"; 
		  <% 
		  }
		  %>

		  //退税率是否录入
		  var newNameTD13 = newTR.insertCell(13);
		  <%
		  if(request.getAttribute("rate"+j) != null && !"".equals(request.getAttribute("rate"+j))){
	      %>
	      newNameTD13.innerHTML = "<span>已录</span>";
		  <%	  
		  }else{
		  %>	 	  
		  newNameTD13.innerHTML = "<span style='color: red;'>未录</span>"; 
		  <% 
		  }
		  %>
		  
		  //电子出货单是否提供
		  var newNameTD13 = newTR.insertCell(14);
		  <%
		  if(Integer.parseInt(request.getAttribute("state"+j).toString()) == 2 && request.getAttribute("complete"+j).toString() != "true"){
	      %>
	      newNameTD13.innerHTML = "<span style='color: red;'>已录未确认</span>";
		  <%	  
		  }else if(request.getAttribute("complete"+j).toString() == "true"){
		  %>	 	  
		  newNameTD13.innerHTML = "<span style='color: #14da14;'>已录已确认</span>"; 
		  <% 
		  }else{
		  %>	 	  
		  newNameTD13.innerHTML = "<span style='color: red;'>未录不可出货</span>"; 
		  <% 
		  }
		  %>
		  var newNameTD14 = newTR.insertCell(15);
		  <%
		  System.out.print(request.getAttribute("orderStatus"+j));
		  if(Integer.parseInt(request.getAttribute("orderStatus"+j).toString()) == 1){
	      %>
	      newNameTD14.innerHTML = "<span></span>";
		  <%	  
		  }else{
		  %>	 	  
		  newNameTD14.innerHTML = "<span style='color: red;'>暂存</span>"; 
		  <% 
		  }
		  %>
		  
	  <%
	  }
	  }
	  %>
  </script>
  <br/>
  <a href="javascript:window.print();">打印</a>
  </body>
</html>
