<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'check.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  </head>
  <style type="text/css">
	  .error-tip{background-color: #ff4d09;}
	  table,table tr th, table tr td { border:1px solid #0094ff; }

  </style>
  <body>
  <form action="CheckProjectERPPrintServlet" id="form">
	  <input type="button" value="导出报表" onclick="print(this)">
  </form>
  <table>
	  <thead>
	  <th>编号</th>
	  <th>实际报关金额</th>
	  <th>采购价总价</th>
	  <th>货币</th>
	  </thead>

	  <tbody>
	  <c:forEach items="${lst}" var="l">
<tr class="${l.flag==1?'error-tip':''}">
	<td>${l.contractno}</td>
	<td>${l.totalPrice}</td>
	<td>${l.friMoney}</td>
	<td>${l.currency}</td>
</tr>

	  </c:forEach>


	  </tbody>
  </table>

  	</body>

  <script type="text/javascript">
	  function print(obj){
		  $(obj).parents('form').submit();
	  }


  </script>
</html>
