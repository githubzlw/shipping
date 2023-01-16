<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
  <h1 align="center">出货到款对应</h1>
      <form action="ShipmentPaymentReParidServlet" id="form1">
          <label>时间：
              <select name="year">
                  <option <c:if test="${year==2020 }">selected="selected"</c:if> value="2020">2020年</option>
                  <option <c:if test="${year==2021 }">selected="selected"</c:if> value="2021">2021年</option>
                  <option <c:if test="${year==2022 }">selected="selected"</c:if> value="2022">2022年</option>
                  <option <c:if test="${year==2023 }">selected="selected"</c:if> value="2023">2023年</option>
                  <option <c:if test="${year==2024 }">selected="selected"</c:if> value="2024">2024年</option>
                  <option <c:if test="${year==2025 }">selected="selected"</c:if> value="2025">2025年</option>
              </select>
          </label>
          <label>客户名：<input type="text" class="form-control p_q_r" id="customerName" name="customerName"  value="${customerName }"></label>
          <label>配对状态：
              <select name="parid">
                  <option <c:if test="${parid=='未配对' }">selected="selected"</c:if> value="未配对">未配对</option>
                  <option <c:if test="${parid=='部分配对' }">selected="selected"</c:if> value="部分配对">部分配对</option>
                  <option <c:if test="${parid=='已配对' }">selected="selected"</c:if> value="已配对">已配对</option>
              </select>
          </label>

          <input type="submit" value="查询">
      </form>
  <form action="ShipmentPaymentReParidPrintServlet" id="form2">
      <input type="hidden" name="year2" value="${year }">
      <input type="hidden" name="customerName2" value="${customerName }">
      <input type="hidden" name="parid2" value="${parid }">
      <input type="button" value="导出配对数据" onclick="print(this)">
  </form>

  <table border="1" id="item_table" style="table-layout:fixed;">
      <tr>
          <td><Strong>出运号</Strong></td>
          <td><Strong>客户id</Strong></td>
          <td><Strong>出运品名</Strong></td>
          <td><Strong>实际销售额（清关金额）</Strong></td>
          <td><Strong>实际对外发票金额（报关金额）</Strong></td>
          <td><Strong>项目号</Strong></td>
          <td><Strong>银行到账序号</Strong></td>
          <td><Strong>到账日期</Strong></td>
          <td><Strong>到账金额</Strong></td>
          <td><Strong>配对金额</Strong></td>
          <td><Strong>配对状态</Strong></td>

      </tr>
      <tbody id="t-produce-body">
      <c:forEach items="${bankAList}" var="bankA" varStatus="sdex">

              <tr class="citem-tr-parent item-tr-parent${sdex.index}">
                  <td>${bankA.proId}</td>
                  <td>${bankA.erpCustomerId}</td>
                  <td>${bankA.itemchn}</td>
                  <td>${bankA.unitPriceall}</td>
                  <td>${bankA.truePrice}</td>
                  <td>${bankA.itemNo}</td>
                  <td>${bankA.transactionReferenceNumber}</td>
                  <td>${bankA.transactionDate}</td>
                  <td>${bankA.tradeAmount}</td>
                  <td>${bankA.pairedAmount}</td>
                  <td>${bankA.paired}</td>
              </tr>
          </c:forEach>
      </tbody>
  </table>




<%--  <form action="ShipmentPaymentServlet" id="form1">--%>
<%--	  <input type="submit" value="计算">--%>
<%--  </form>--%>
<%--  <form action="ShipmentPaymentReParidServlet" id="form2">--%>
<%--	  <input type="submit" value="配对结果">--%>
<%--  </form>--%>

<%--  结果：<br/>--%>
<%--  <table border="1" id="table">--%>
<%--	  <tr>--%>
<%--            <td><Strong>TID序号</Strong></td>--%>
<%--            <td><Strong>客户ID</Strong></td>--%>
<%--            <td><Strong>日期</Strong></td>--%>
<%--            <td><Strong>项目号</Strong></td>--%>
<%--            <td><Strong>到账金额</Strong></td>--%>
<%--            <td><Strong>是否配对</Strong></td>--%>
<%--            <td><Strong>已经配对金额</Strong></td>--%>
<%--            <td><Strong>本次配多少</Strong></td>--%>
<%--            <td><Strong>操作</Strong></td>--%>
<%--	  </tr>--%>
<%--  </table>--%>
<%--  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>--%>
<%--  <script type="text/javascript">--%>

<%--	  var tab = document.getElementById("table");--%>
<%--	  <%--%>
<%--	  if(request.getAttribute("total")!=null)--%>
<%--	  {--%>
<%--		  int total=(Integer)request.getAttribute("total");--%>
<%--		  for(int j=0;j<total;j++){--%>
<%--	  %>--%>

<%--          var newTR =tab.insertRow(tab.rows.length);--%>
<%--          var newNameTD = newTR.insertCell(0);--%>
<%--          newNameTD.innerHTML = "<%=request.getAttribute("shipmentNoF"+j)%>";--%>
<%--          var newNameTD1 = newTR.insertCell(1);--%>
<%--          newNameTD1.innerHTML = "<%=request.getAttribute("erpCustomerIdF"+j)%>";--%>
<%--          var newNameTD2 = newTR.insertCell(2);--%>
<%--          newNameTD2.innerHTML = "<%=request.getAttribute("transactionDateF"+j)%>";--%>
<%--          var newNameTD3 = newTR.insertCell(3);--%>
<%--          newNameTD3.innerHTML = "<%=request.getAttribute("itemNoF"+j)%>";--%>
<%--          var newNameTD4 = newTR.insertCell(4);--%>
<%--          newNameTD4.innerHTML = "<%=request.getAttribute("declarationAmountF"+j)%>";--%>
<%--          var newNameTD5 = newTR.insertCell(5);--%>
<%--          newNameTD5.innerHTML = "<%=request.getAttribute("pairedF"+j)%>";--%>
<%--          var newNameTD6 = newTR.insertCell(6);--%>
<%--          newNameTD6.innerHTML = "<%=request.getAttribute("pairedAmountF"+j)%>";--%>
<%--          var newNameTD7 = newTR.insertCell(7);--%>
<%--          newNameTD7.innerHTML = "<%=request.getAttribute("tradeAmountF"+j)%>";--%>
<%--		  var newNameTD9 = newTR.insertCell(8);--%>
<%--		  newNameTD9.innerHTML = "<a href='ShipmentPairedServlet?" +--%>
<%--				  "amountClaimFormIdF=<%=request.getAttribute("amountClaimFormIdF"+j)%>&itemNoF=<%=request.getAttribute("itemNoF"+j)%>"+--%>
<%--		  "&declarationAmountF=<%=request.getAttribute("declarationAmountF"+j)%>&shipmentNoF=<%=request.getAttribute("shipmentNoF"+j)%>"+--%>
<%--	  "&erpCustomerIdF=<%=request.getAttribute("erpCustomerIdF"+j)%>&actualSalesF=<%=request.getAttribute("actualSalesF"+j)%>"+--%>
<%--			  "&actualInvoiceAmountF=<%=request.getAttribute("actualInvoiceAmountF"+j)%>&pairedBankNoF=<%=request.getAttribute("pairedBankNoF"+j)%>&generalDeclarationInvoiceF=<%=request.getAttribute("generalDeclarationInvoiceF"+j)%>&idF=<%=request.getAttribute("idF"+j)%>' target='showframe'>提交</a>";--%>

<%--	  <%--%>
<%--	  }--%>
<%--	  }--%>
<%--	  %>--%>

<%--  </script>--%>
  <script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript">

<%--      <%--%>
<%--        if(request.getAttribute("year")!=null){--%>
<%--      %>--%>
<%--         $('#form1').find('select[name="select1"]').val('<%=request.getAttribute("year")%>')--%>
<%--      <%--%>
<%--      }--%>
<%--      %>--%>

      //导出
      function print(obj) {
          //var select1 = $('#form1').find('select[name="select1"]').val();
          //$(obj).parents('form').find('input[name="select1"]').val(select1);

          //$(obj).parents('form').submit();
          $(obj).parents('form').submit();
      }
  </script>
  </body>
</html>
