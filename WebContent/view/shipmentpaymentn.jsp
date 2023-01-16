<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出货到款对应</title>

	  <meta http-equiv="pragma" content="no-cache">
	  <meta http-equiv="cache-control" content="no-cache">
	  <meta http-equiv="expires" content="0">
	  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	  <meta http-equiv="description" content="This is my page">

      <script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>
      <script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>

  </head>

  <body>

  <p>过去2年银行进账 减去 报关金额:${sumCzPrice}</p>

  <form action="ShipmentPaymentReParidNServlet" method="post" id="order_form">
    <table border="1" id="item_table" style="table-layout:fixed;">
      <tr>
          <td><Strong>银行到账序号</Strong></td>
          <td><Strong>客户ID</Strong></td>
          <td><Strong>金蝶ID</Strong></td>
          <td><Strong>到账日期</Strong></td>
        <c:if test="${bsFlag == '2'}">
          <td><Strong>亟待状态</Strong></td>
        </c:if>
          <td><Strong>项目号</Strong></td>
          <td><Strong>发票号</Strong></td>
          <td><Strong>货币</Strong></td>
          <td><Strong>到账金额</Strong></td>
          <td><Strong>认领到本项目金额</Strong></td>
<%--          <td><Strong>总应收</Strong></td>--%>
<%--          <td><Strong>是否配对</Strong></td>--%>
          <td><Strong>配对到本次出口金额(跟单)</Strong></td>
          <td><Strong>已经分配的出口金额</Strong></td>
          <td><Strong>财务按需修改金额</Strong></td>
<%--          <td><Strong>应收金额</Strong></td>--%>

      </tr>
        <tbody id="t-produce-body">
              <c:forEach items="${bankAList}" var="bankA" varStatus="sdex">

                  <tr class="citem-tr-parent item-tr-parent${sdex.index}">
                      <td>
                          <c:if test="${bankA.bsFlag == '1'}">
                              <span style="color: red;">*</span>
                          </c:if>
                              ${bankA.transactionReferenceNumber}</td>
                      <td>${bankA.erpCustomerId}</td>
                      <td>${bankA.kingdeeId}</td>
                      <td>${bankA.transactionDate}</td>
                      <c:if test="${bsFlag == '2'}">
                        <td>${bankA.needPaired}</td>
                      </c:if>


                      <td>${bankA.itemNo}
                          <input type="hidden" id="itemNo" name="itemNo${sdex.index+1}"  value="${bankA.itemNo}"/>
                      </td>
                      <td>${bankA.invoice}</td>
                      <td>${bankA.tradeCurrency}</td>
                      <td>${bankA.tradeAmount}
                          <input type="hidden" id="tradeAmount" name="tradeAmount${sdex.index+1}"  value="${bankA.tradeAmount}"/>
                      </td>
                      <td id="declarationAmountF" ><p style="color:blue" >${bankA.declarationAmountF}</p></td>
<%--                      <td>${bankA.totalReceipts}</td>--%>
<%--                      <td>${bankA.paired}--%>
<%--                          <input type="hidden" id="paired" name="paired${sdex.index+1}"  value="${bankA.paired}"/>--%>
<%--                      </td>--%>
                      <td><input size="10" type="text" class="citemeng" id="pairedLcamount" name="pairedLcamount${sdex.index+1}" value="0"  onblur="jsSumPairedLcamount(this)" />
                          <input type="hidden" id="rId" name="rId${sdex.index+1}"  value="${bankA.rId}"/>
                          <input type="hidden" id="aId" name="aId${sdex.index+1}"  value="${bankA.aId}"/>
                      </td>

                      <td>${bankA.pairedAmount}
                          <input type="hidden" id="pairedAmount" name="pairedAmount${sdex.index+1}"  value="${bankA.pairedAmount}"/>
                          <input type="hidden" id="isExtraInvoice" name="isExtraInvoice${sdex.index+1}"  value="${bankA.isExtraInvoice}"/>
                      </td>
<%--                      <c:if test="${bankA.paired != '已配对'}">--%>

                          <td>
                              <input size="10" type="text" id="pairedSsamount" class="citemeng" name="pairedSsamount${sdex.index+1}" value="${bankA.pairedSsamount}" onblur="jsSumPairedSsamount(this)"/>
                          </td>
<%--                      </c:if>--%>
<%--                      <td>--%>
<%--                          <input size="10" type="text" class="citemeng" name="pairedYs${sdex.index+1}" value="${bankA.pairedYs}"/>--%>
<%--                      </td>--%>

                      <input type="hidden" id="bankAListSize" name="bankAListSize" value="${bankAListSize}"/>
                      <input type="hidden" id="proId" name="proId" value="${proId}"/>
                      <input type="hidden" id="bsFlag" name="bsFlag" value="${bsFlag}"/>


              </c:forEach>


<%--                      <p>总应收金额：</p>      <input size="10" type="text" class="citemeng" id='zysje' name="zysje" value="${bankA.pairedYs}"/>--%>

        </tbody>
    </table>

      <div style=" width: 80%; margin: 10px 0;display: block;text-align: left; margin-left: 55%">配对到本次出口金额SUM:<div id="showH" style="display: contents;"></div></div>
      <div style=" width: 80%; margin: 10px 0;display: block;text-align: left; margin-left: 55%">财务按需修改金额SUM:<div id="showC" style="display: contents;"></div></div>

      <div>未收到但本次要出口的金额</div>
      <table border="1" id="item_table1" style="table-layout:fixed;">
          <tr>
              <td><Strong>项目号</Strong></td>
              <td><Strong>配对到本次出口金额</Strong></td>
          </tr>
          <tbody id="t-produce-body1">
          <c:forEach items="${bankYsList}" var="bankA" varStatus="sdex">

             <tr class="citem-tr-parent item-tr-parent${sdex.index}">
                <td>${bankA.caseNo}
                    <input type="hidden" id="caseNo" name="caseNo${sdex.index+1}"  value="${bankA.caseNo}"/>
                    <input type="hidden" id="isExtraInvoice" name="isExtraInvoice${sdex.index+1}"  value="${bankA.isExtraInvoice}"/>
                </td>
                <td>
                    <input size="10" type="text" class="citemeng" name="pairedYs${sdex.index+1}" value="${bankA.pairedYs}"/>
                </td>
                 <input type="hidden" id="proIdYs" name="proIdYs" value="${proId}"/>
                 <input type="hidden" id="bankYsListSize" name="bankYsListSize" value="${bankYsListSize}"/>
             </tr>

          </c:forEach>
          </tbody>
      </table>

      <input type="button" style="margin-top: 50px;" value="提交" onclick="update_paired()"></input>
  </form>




  </body>

  <script type="text/javascript">
      function update_paired(){

          var tr = $('#item_table').children("tbody").children('tr');


          var isSubmit = true;
          tr.each(function(){

              // 本次配对
              var cutNum= $(this).find("[id='pairedLcamount']").val();

              // 认领金额
              var declarationAmountF= $(this).find("[id='declarationAmountF']").text();

              // 已配对金额
              var pairedAmount= $(this).find("[id='pairedAmount']").val();

              // if(pairedAmount==''){
              //     pairedAmount =0;
              // }
              if(typeof(cutNum)!="undefined" && typeof(declarationAmountF)!=''){

                  if(parseFloat(cutNum)> (parseFloat(declarationAmountF) -parseFloat(pairedAmount)) ){
                      // alert("本次配对金额大于了认领金额减去已配对金额");
                      isSubmit = false;
                      return false;
                  }
              }
          });

          if(isSubmit)
          {
              $("#order_form").submit();
          }else {
              // alert('本次配对金额大于了认领金额减去已配对金额');
              alert('配对到本次出口金额(跟单)大于了认领到本项目金额减去已经分配的出口金额');

          }


          // $('#order_form').submit();
      }

      function jsSumPairedLcamount(){
          var tr = $('#item_table').children("tbody").children('tr');
          var sumPa=0;

              tr.each(function(){

                  // 本次配对
                  var cutNum= $(this).find("[id='pairedLcamount']").val();

                  if(typeof(cutNum)!="undefined"){
                      sumPa += parseFloat(cutNum);
                  }


              });
              $("#showH").html(sumPa);

      }

      function jsSumPairedSsamount(){
          var tr = $('#item_table').children("tbody").children('tr');
          var sumCw=0;

          tr.each(function(){

              //财务配对
              var cwNum= $(this).find("[id='pairedSsamount']").val();

              if(typeof(cwNum)!="undefined"){
                  sumCw += parseFloat(cwNum);
              }
          });
          $("#showC").html(sumCw);

      }



  </script>


</html>
