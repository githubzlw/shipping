<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>到账出运关联结果</title>

	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">
	  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	  <meta http-equiv="description" content="This is my page">

      <script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>
      <script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>

  </head>

  <body>


<table border="1" id="item_table2A" style="table-layout:fixed;">

    <h3>银行到账与报关金额配对信息</h3>
    <tr>
<%--        <td><Strong>汇款人</Strong></td>--%>
<%--        <td><Strong>银行流水号</Strong></td>--%>
<%--        <td><Strong>到账金额</Strong></td>--%>
<%--        <td><Strong>项目号</Strong></td>--%>
        <td><Strong>出运单号</Strong></td>
        <td><Strong>银行到账认领金额</Strong></td>
        <td><Strong>已配对金额</Strong></td>
        <td><Strong>报关金额</Strong></td>

        <td><Strong>配对日期</Strong></td>
        <td><Strong>未收到但本次要出口的金额</Strong></td>


    </tr>
    <tbody id="t2-produce-bodyA">
    <c:forEach items="${bankWTwoAList}" var="bankA" varStatus="sdex">
        <tr class="citem-tr-parent item-tr-parent${sdex.index}">
<%--            <td>${bankA.payersName}</td>--%>
<%--            <td>${bankA.transactionReferenceNumber}</td>--%>
<%--            <td>${bankA.tradeAmount}</td>--%>
            <td><a href="InfoServlet?id=${bankA.proId}"> ${bankA.proId}</a></td>
            <td>${bankA.ifmoney}</td>
            <td>${bankA.pairedAmount}</td>
            <td>${bankA.declarationAmountF}</td>
            <td>${bankA.financialConfirmationTime}</td>
            <td>${bankA.chaE}</td>



        </tr>
    </c:forEach>
    </tbody>
</table>

<%--<table border="1" id="item_tableA" style="table-layout:fixed;">--%>
<%--    <h3>1）有银行到账但没关联报关金额(全部)</h3>--%>
<%--    <tr>--%>
<%--        <td><Strong>汇款人</Strong></td>--%>
<%--        <td><Strong>银行流水号</Strong></td>--%>
<%--        <td><Strong>到账金额</Strong></td>--%>
<%--        <td><Strong>项目号</Strong></td>--%>
<%--        <td><Strong>出运单号</Strong></td>--%>
<%--        <td><Strong>报关金额</Strong></td>--%>
<%--        <td><Strong>认领金额</Strong></td>--%>
<%--        <td><Strong>认领日期</Strong></td>--%>

<%--    </tr>--%>
<%--    <tbody id="t-produce-bodyA">--%>
<%--    <c:forEach items="${bankATwoAList}" var="bankA" varStatus="sdex">--%>
<%--        <tr class="citem-tr-parent item-tr-parent${sdex.index}">--%>
<%--            <td>${bankA.payersName}</td>--%>
<%--            <td>${bankA.transactionReferenceNumber}</td>--%>
<%--            <td>${bankA.tradeAmount}</td>--%>
<%--            <td>${bankA.caseNo}</td>--%>
<%--            <td>${bankA.proId}</td>--%>
<%--            <td>${bankA.declarationAmountF}</td>--%>
<%--            <td>${bankA.ifmoney}</td>--%>
<%--            <td>${bankA.financialConfirmationTime}</td>--%>

<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </tbody>--%>



<%--</table>--%>
<%--<table border="1" id="item_table1A" style="table-layout:fixed;">--%>

<%--    <h3>2）有报关金额但没关联银行到账的(全部)</h3>--%>
<%--    <tr>--%>
<%--        <td><Strong>汇款人</Strong></td>--%>
<%--        <td><Strong>银行流水号</Strong></td>--%>
<%--        <td><Strong>到账金额</Strong></td>--%>
<%--        <td><Strong>项目号</Strong></td>--%>
<%--        <td><Strong>出运单号</Strong></td>--%>
<%--        <td><Strong>报关金额</Strong></td>--%>
<%--        <td><Strong>认领金额</Strong></td>--%>
<%--        <td><Strong>认领日期</Strong></td>--%>

<%--    </tr>--%>
<%--    <tbody id="t1-produce-bodyA">--%>
<%--    <c:forEach items="${bankBTwoAList}" var="bankA" varStatus="sdex">--%>
<%--        <tr class="citem-tr-parent item-tr-parent${sdex.index}">--%>
<%--            <td>${bankA.payersName}</td>--%>
<%--            <td>${bankA.transactionReferenceNumber}</td>--%>
<%--            <td>${bankA.tradeAmount}</td>--%>
<%--            <td>${bankA.caseNo}</td>--%>
<%--            <td>${bankA.proId}</td>--%>
<%--            <td>${bankA.declarationAmountF}</td>--%>
<%--            <td>${bankA.ifmoney}</td>--%>
<%--            <td>${bankA.financialConfirmationTime}</td>--%>

<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--    </tbody>--%>
<%--</table>--%>




  </body>

  <script type="text/javascript">
  </script>


</html>
