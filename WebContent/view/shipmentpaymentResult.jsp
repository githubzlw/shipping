<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>出货到款对应</title>

	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">
	  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	  <meta http-equiv="description" content="This is my page">

  </head>

  <body>
  <h1 align="center">出货到款配对结果</h1>
  结果：<br/>
  <table border="1" id="table">
	  <tr>
		  <td><Strong>出运号</Strong></td>
		  <td><Strong>ERP客户ID</Strong></td>
		  <td><Strong>项目号</Strong></td>
		  <td><Strong>这票货对应的实际销售额</Strong></td>
		  <td><Strong>这票货对应的实际发票金额</Strong></td>
		  <td><Strong>配对的银行序号</Strong></td>
		  <td><Strong>报关金额</Strong></td>
		  <td><Strong>申报发票RMB</Strong></td>
		  <td><Strong>配对结果</Strong></td>


	  </tr>
  </table>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript">

	  var tab = document.getElementById("table");
	  <%
	  if(request.getAttribute("total")!=null)
	  {
		  int total=(Integer)request.getAttribute("total");
		  for(int j=0;j<total;j++){
	  %>
		  var newTR =tab.insertRow(tab.rows.length);
		  var newNameTD = newTR.insertCell(0);
		  newNameTD.innerHTML = "<%=request.getAttribute("shipmentNoF"+j)%>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "<%=request.getAttribute("erpCustomerIdF"+j)%>";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<%=request.getAttribute("itemNoF"+j)%>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<%=request.getAttribute("actualSalesF"+j)%>";
		  var newNameTD4 = newTR.insertCell(4);
		  newNameTD4.innerHTML = "<%=request.getAttribute("actualInvoiceAmountF"+j)%>";
		  var newNameTD5 = newTR.insertCell(5);
		  newNameTD5.innerHTML = "<%=request.getAttribute("pairedBankNoF"+j)%>";
		  var newNameTD6 = newTR.insertCell(6);
		  newNameTD6.innerHTML = "<%=request.getAttribute("declarationAmountF"+j)%>";
		  var newNameTD8 = newTR.insertCell(7);
		  newNameTD8.innerHTML = "<%=request.getAttribute("generalDeclarationInvoiceF"+j)%>";
		  var newNameTD9 = newTR.insertCell(8);
		  newNameTD9.innerHTML = "<%=request.getAttribute("pairedF"+j)%>";



	  <%
	  }
	  }
	  %>



  </script>

  </body>
</html>
