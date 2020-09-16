<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
<body>

<form action="/AddItemOfContractServlet">
    <input type="hidden" value="${cproid}" name="cproid">
    <input type="hidden" value="${pdix}" name="itemsize">
<table>
    <tr>
        <td width="20px"  style="word-wrap:break-word;" >Item英文名</td>
        <td width="20px"  style="word-wrap:break-word;" >Item中文名</td>
        <td width="10px"  style="word-wrap:break-word;" >Quantity(请只填数字)</td>
        <td width="80px" >数量单位</td>
        <td width="50px"  style="word-wrap:break-word;" ><strong>采购价 总价(只填数字 单位:RMB)(格式：100000.00)</strong></td>
        <td width="40px"  style="word-wrap:break-word;" >Unit Price(对外销售单价)</td>
        <td width="50px"  style="word-wrap:break-word;" >(客户)清关总价(格式：100000.00)</td>
        <td width="50px">Shipping Mark</td>
        <td width="50px"><p>N.W.(请只填数字 单位:kg)</p></td>
        <td width="50px"  style="word-wrap:break-word;" >境内货源地</td>
        <td width="50px"  style="word-wrap:break-word;" >实际报关总价(会计填)(格式：100000.00)</td>
        <td width="50px">HS Code (物流填)</td>
        <td width="50px">退税率 (物流填 *%)</td>
    </tr>
    <tr>
        <td><input  type="text"  class="citemeng" name="itemeng" value="${itemeng}"/></td>
        <td><input  type="text"  class="citemchn" name="itemchn" value="${itemchn}"  onblur="checkProductName(this)"/></td>
        <td><input  type="text"  class="cquantity" name="quantity" value="${quantity}"/></td>
        <td><input  type="text"  class="cunit" name="unit" value="${unit}"/></td>
        <td><input  type="text" name="purprice" class="cpurprice export-cn1" value="${purprice}" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
        <td><input  type="text"  class="cunitprice" name="unitprice" value="${unitprice}" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
        <td><input  type="text" name="unitpriceall" class="cunitpriceall unit-price-all" value="${unitpriceall}" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
        <td><input  type="text" name="shopingmark" value="${shopingmark}" class="cshopingmark"/></td>
        <td><input  type="text" name="nw" class="cnw n_weight" value="${nw}"/></td>
        <td><input  type="text" name="sourceDestination" value="${sourceDestination}" class="csourceDestination sourceDestination"/></td>
        <td><input  type="text" name="trueprice" class="ctrueprice true-price" value="${trueprice}" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/>
        </td>
        <td><input  type="text" name="hscode" value="${hscode}" class="chscode"/></td>
        <td><input  type="text" name="rate" value="${rate}" class="crate"/>
            <input  type="hidden" name="itemid" value="${itemid}" class="citemid"/>
        </td>
    </tr>

    <tr>
        <td>合同</td>
        <td>报关金额</td>
        <td>报关数量</td>
    </tr>
    <c:forEach items="${items}" var="item" varStatus="sdex">
        <tr>
            <td><select name="itemcontractpurno${sdex.index}">
                <c:forEach items="${purnos}" var="p">
                    <c:if test="${item.purno==p}"><option value="" selected>${p}</option></c:if>
                    <c:if test="${item.purno!=p}"><option value="">${p}</option></c:if>
                </c:forEach>
            </select></td>
            <td><input value="${item.amount}" name="itemcontractamount${sdex.index}"></td>
            <td><input value="${item.quantity}" name="itemcontractquantity${sdex.index}">
                <input value="${item.id}" name="itemcontractid${sdex.index}" type="hidden"></td>
        </tr>
    </c:forEach>

    <c:if test="${size >0}">
    <c:forEach  begin="1" step="1" end="${size}" varStatus="sdex">
        <tr>
            <td><select name="itemcontractpurno${sdex.index+sdix}">
                <c:forEach items="${purnos}" var="p">
                    <option value="">${p}</option>
                </c:forEach>
            </select></td>
            <td><input value="" name="itemcontractamount${sdex.index+sdix}"></td>
            <td><input value="" name="itemcontractquantity${sdex.index+sdix}">
                <input value="${item.id}" name="itemcontractid${sdex.index+sdix}" type="hidden"></td>
        </tr>

    </c:forEach>
    </c:if>

</table>
<input type="submit" value="更新">
</form>
</body>
</html>
