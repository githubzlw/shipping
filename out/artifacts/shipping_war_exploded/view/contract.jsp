<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>外销合同</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
   	<table border="1" id="table" style="margin-left:0px;margin-top:0px;font-family:宋体;color:black;font-size:13px;text-align:left">
   		<tr>
   			<td colspan="3"><strong>SHANGHAI  SOURCING</strong></td>
   			<td rowspan="3" colspan="2"><img src="js/cerong.png"/></td>
   		</tr>
   		<tr>
   			<td colspan="3">188  MIDDLE FUJIAN ROAD，SUITE 1407，SHANGHAI， CHINA</td>
   		</tr>
   		<tr>
   			<td colspan="3">TEL:086 21 61021102         FAX (China):086 21 63757266        FAX (USA):1-800-886-7379  </td>
   		</tr>
   		<tr>
   			<td colspan="5" align="center"><h1><strong>PROFORMA INVOICE</strong></h1></td>
   		</tr>
   		<tr>
   			<td><strong>Customer Name</strong></td>
   			<td colspan="2"><%=request.getAttribute("clientName")%></td>
   			<td><strong>Date of Invoice</strong></td>
   			<td><%=request.getAttribute("date")%></td>
   			
   		</tr>
   		<tr>
   			<td><strong>Customer Address</strong></td>
   			<td colspan="2"><%=request.getAttribute("address")%></td>
   			<td><strong>Voucher NO.</strong></td>
   			<td><%=request.getAttribute("nonum")%></td>
   		</tr>
   		<tr>
   			<td colspan="5" align="left">Client Order NO.:</td>
   		</tr>
   		<%--<tr>
   			<td colspan="3" rowspan="2"><strong>To:</strong><%=request.getAttribute("address")%></td>
   			<td colspan="2"><strong>Term of delivery and payment:</strong></td>
   		</tr>
   		<tr>
   			<td colspan="2"><%=request.getAttribute("transaction1")%></td>
   		</tr>
   		<tr>
   			<td><strong>Vessel/flight:</strong></td>
   			<td><strong>From:</strong></td>
   			<td><strong>TO:</strong></td>
   			<td><strong>B/L No:</strong></td>
   			<td><strong>Sailing Date:</strong></td>
   		</tr>
   		<tr>
   			<td><%=request.getAttribute("transaction2")%></td>
   			<td><%=request.getAttribute("fromwhere")%></td>
   			<td><%=request.getAttribute("towhere")%></td>
   			<td></td>
   			<td><%=request.getAttribute("saildate")%></td>
   		</tr>
   		<tr>
   			<td rowspan="2"><strong>SHIPPING MARKS</strong></td>
   			<td><strong>ITEM Name of Commodity</strong></td>
   			<td><strong>Quantity</strong></td>
   			<td><strong>Unit Price</strong></td>
   			<td><strong>Amount</strong></td>
   		</tr>
   		
   		
   		
   		<tr>
   			<td><strong>Specification</strong></td>
   			<td><strong>PCS</strong></td>
   			<td><strong><%=request.getAttribute("currency")%></strong></td>
   			<td><strong><%=request.getAttribute("currency")%></strong></td>
   		</tr>
   		--%>
   		<tr>
   			<td><strong>Title/Description</strong></td>
   			<td><strong>UM</strong></td>
   			<td><strong>Quantity</strong></td>
   			<td><strong>Unit Price(USD/PC)</strong></td>
   			<td><strong>Total Price</strong></td>
   		</tr>
   		
   		<tr>
   			<td colspan="4"> Grand Total</td>
   			<td><%=request.getAttribute("totalPrice")%></td>
   		</tr>
   		<%--<tr>
   			<td colspan="5"><strong>TOTAL: ShoppingCost <%=request.getAttribute("yunfei")%></strong></td>
   		</tr>
   		<tr>
   			<td colspan="5"><strong>TOTAL:<%=request.getAttribute("totalMoney")%></strong></td>
   		</tr>
   	--%></table>
   	<script type="text/javascript">
	  var tab=document.getElementById("table");
	  <%
	  if(request.getAttribute("total")!=null)
	  {
		  int total=(Integer)request.getAttribute("total");
		  for(int j=0;j<total;j++){
			  
	  %>
		  var newTR =tab.insertRow(tab.rows.length-1);
		  var newNameTD = newTR.insertCell(0);
		  newNameTD.innerHTML = "<%=request.getAttribute("itemall"+j)%>";
		  var newNameTD1 = newTR.insertCell(1);
		  newNameTD1.innerHTML = "EA";
		  var newNameTD2 = newTR.insertCell(2);
		  newNameTD2.innerHTML = "<%=request.getAttribute("quantity"+j)%>";
		  var newNameTD3 = newTR.insertCell(3);
		  newNameTD3.innerHTML = "<%=request.getAttribute("trueprice"+j)%>";
		  var newNameTD4 = newTR.insertCell(4);
		  newNameTD4.innerHTML = "<%=request.getAttribute("amount"+j)%>";
	  <%
	  }
	  }
	  %>
  </script>
  	<strong>Price term: </strong>FOB SHANGHAI<br/>
  	<strong>Packing: </strong>Packed in common cartons<br/>
  	<strong>Payment Term</strong>&nbsp&nbsp Pay 50% of the total cost  first, rest cost should be paid before shipping,<br/>	
  	<strong>Delivery Term</strong><br/>
  	<strong>Lead Time</strong>&nbsp&nbsp P15 days finish mass production after receiving your payment.<br/>
  	<strong>Bank Account Information</strong><br/>
  	BANK NAME：Shanghai Sourcing Ltd.<br/>
  	Bank Address: 	NO.431 FU ZHOU ROAD,SHANGHAI CHINA<br/>
  	SWIFT CODE: 	BKCHCNBJ300<br/>
ACCOUNT NO. : 	044094-8600-16005008093014<br/>
COMPANY ADDRESS: 	NO.188 MIDDLE FUJIAN ROAD,SUITE 1407,SHANGHAI， 200001<br/>
Intermediary Bank Name(for US Customers)：Bank of China, New York Branch<br/>
Intermediary Bank ABA Routing#: 026003269<br/>
Note: 	1. Be sure to spell our name correctly. (e.g., Sourcing not souring, Shanghai not shangai)<br/>
&nbsp&nbsp&nbsp&nbsp&nbsp	2. Error in wiring process can delay production by 1-2 weeks. Please be careful.<br/>
&nbsp&nbsp&nbsp&nbsp&nbsp	3. Keep a copy of your wire transfer receipt, scan it and email to us for faster processing.<br/>
&nbsp&nbsp&nbsp&nbsp&nbsp	4. Intermediary bank info is optional. Leave it out if your wire transfer form doesn't have this field.<br/>
Please always write down SWIFT CODE on your form, because that's the most important number.<br/>
<strong>CERTIFICATION:</strong><br/>
I certify that this invoice is correct and in accordance with the contract, and that the costs<br/>
included herein have been incurred, represent payments made by the Contractor except as otherwise<br/>
authorized in the payment provisions of the contract, and properly reflect the work performed. <br/>
  BUYER: 	&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
  &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp	SELLER:<br/>
   &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
   &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
   &nbsp&nbsp&nbsp<strong>SHANGHAI SOURCING</strong><br/>
  &nbsp&nbsp&nbsp&nbsp&nbsp___________________&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
  ____________________<br/>
   	&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp(Signature) &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp(Signature)<br/>
  <%--
  
  <table id="table2">
  	<tr>
  		<td><strong>Price term: </strong></td>
   		<td colspan="4">FOB SHANGHAI</td>
  	</tr>
  	<tr>
  		<td><strong>Packing:</strong></td>
   		<td colspan="4">Packed in common cartons</td>
  	</tr>
  	<tr>
  		<td ><strong>Payment Term</strong></td>
   		<td rowspan="2" colspan="4">Pay 50% of the total cost  first, rest cost should be paid before shipping,	</td>
  	</tr>
  	<tr>
  		<td ><strong>Delivery Term</strong></td>
  	</tr>
  	<tr>
  		<td ><strong>Lead Time</strong></td>
  		<td colspan="4" colspan="4">P15 days finish mass production after receiving your payment.</td>
  	</tr>
 	 <tr>
  		<td colspan="5" ><strong>Bank Account Information</strong></td>
  	</tr>
  	<tr>
  		<td>ACCOUNT NAME：</td>
  		<td colspan="4">Shanghai Sourcing Ltd.</td>
  	</tr>
  	<tr>
  		<td>BANK NAME：</td>
  		<td colspan="4">BANK OF CHINA SHANGHAI BRANCH</td>
  	</tr>
  	<tr>
  		<td>Bank Address: </td>
  		<td colspan="4">NO.431 FU ZHOU ROAD,SHANGHAI CHINA</td>
  	</tr>
  	<tr>
  		<td>SWIFT CODE:</td>
  		<td colspan="4">BKCHCNBJ300</td>
  	</tr>
  	<tr>
  		<td>ACCOUNT NO. :</td>
  		<td colspan="4">044094-8600-16005008093014</td>
  	</tr>
  	<tr>
  		<td>COMPANY ADDRESS:</td>
  		<td colspan="4">NO.188 MIDDLE FUJIAN ROAD,SUITE 1407,SHANGHAI， 200001</td>
  	</tr>
  	<tr>
  		<td colspan="5">Intermediary Bank Name(for US Customers)：Bank of China, New York Branch</td>
  	</tr>
  	<tr>
  		<td colspan="5">Intermediary Bank ABA Routing#: 026003269</td>
  	</tr>
  	<tr>
  		<td>Note: </td>
  		<td colspan="4">1. Be sure to spell our name correctly.  (e.g., Sourcing not souring, Shanghai not shangai)</td>
  	</tr>
  	<tr>
  		<td></td>
  		<td colspan="4">2. Error in wiring process can delay production by 1-2 weeks.  Please be careful.</td>
  	</tr>
  	<tr>
  		<td></td>
  		<td colspan="4">3. Keep a copy of your wire transfer receipt, scan it and email to us for faster processing.</td>
  	</tr>
  	<tr>
  		<td></td>
  		<td colspan="4">4. Intermediary bank info is optional.  Leave it out if your wire transfer form doesn't have this field. </td>
  	</tr>
  	<tr>
  		<td colspan="5">Please always write down SWIFT CODE on your form, because that's the most important number.</td>
  	</tr>
  	<tr>
  		<td colspan="5"><strong>CERTIFICATION:</strong></td>
  	</tr>
  	<tr>
  		<td colspan="5">I certify that this invoice is correct and in accordance with the contract, and that the costs</td>
  	</tr>
  	<tr>
  		<td colspan="5">included herein have been incurred, represent payments made by the Contractor except as otherwise </td>
  	</tr>
  	<tr>
  		<td colspan="5">authorized in the payment provisions of the contract, and properly reflect the work performed. </td>
  	</tr>
  	<tr>
  		<td>BUYER:</td>
  		<td colspan="2"></td>
  		<td>SELLER:</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td colspan="5"></td>
  	</tr>
  	<tr>
  		<td colspan="3"></td>
  		<td colspan="2"><strong>SHANGHAI SOURCING</strong></td>
  	</tr>
  	<tr>
  		<td colspan="2">________________________</td>
  		<td></td>
  		<td colspan="2">________________________</td>
  	</tr>
  	<tr>
  		<td></td>
  		<td>(Signature)</td>
  		<td></td>
  		<td>(Signature)</td>
  		<td></td>
  	</tr>
  	
  </table>
  <br/>
   --%>
  
  </body>
</html>
