<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>">
<title>项目详情</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
</head>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js">
</script>
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
 <style type="text/css">
<!--
#bg {background-color:#598ac7;
width:960px;
margin-left:auto;
margin-right:auto;
color:#FFFFFF;
font-family:"微软雅黑", "幼圆", "宋体";}
.title {color: #FFFFFF;
font-family:"微软雅黑", "幼圆", "宋体";
font-size:16px;}
-->
</style>
<script type="text/javascript">
	function fnback() {
		history.go(-1);
	}
	function fn() {
		fnClassification();
		fnstate();
		fnqualityChange();
		//（（今天-原定交期）-客户原因造成的拖延天数-公司特许的交期拖延）* 30
		var delayDay = document.getElementById("delayDay").value.trim();
		var firmDelayDay = document.getElementById("firmDelayDay").value.trim();
		if(delayDay == ""){
			delayDay = 0;
		}
		if(firmDelayDay == ""){
			firmDelayDay = 0;
		}
		var oDate1 = new Date();

		var scheduledDelivery = document.getElementById("scheduledDelivery").value.trim();
		if(scheduledDelivery == ""){
			document.getElementById("monney").innerHTML = 0;
    		document.getElementById("monney1").value = 0;
			return ;
		}
		var scheduledDeliveryt = scheduledDelivery.toString();
		var str1 = scheduledDeliveryt.replace(/-/g, "/");
		//// str =  str.replace("T"," "); 
		var oDate2 = new Date(str1);
		var oDate3 = oDate1 - oDate2;
		var time = parseInt(Math.abs(oDate3) / 1000 / 60 / 60 / 24);
		//alert(time+","+delayDay);
		var monneyone = time - parseInt(delayDay) - parseInt(firmDelayDay);
		var monney = monneyone * 30;
		if(monney<0){
			monney = 0;
			monneyone = 0;
		}
		//今天-原始交期 - 客户延期- 公司交期
		var classification = $("#state").find("option:selected").text();
    	if(classification == "未解决"){
			document.getElementById("delay").innerHTML = monneyone;
    	}else{ 
    		var statetime = document.getElementById("statetime").innerHTML.trim();
    		if(statetime == ""){
    			return ;
    		}
    		var statetimet = statetime.toString();
    		var statetimestr = statetimet.replace(/-/g, "/");
    		//// str =  str.replace("T"," "); 
    		var oDate4 = new Date(statetimestr)-oDate2;
    		var time1 = parseInt(Math.abs(oDate4) / 1000 / 60 / 60 / 24);
    		//alert(time+","+delayDay);
    		var monneyone = time1 - parseInt(delayDay) - parseInt(firmDelayDay);
    		 
    		document.getElementById("delay").innerHTML = monneyone+"天";
    		document.getElementById("delay_input").value = monneyone;

    		document.getElementById("monney").innerHTML = monneyone*30;
    		document.getElementById("monney1").value = monney*30;
    		return;
    	}
		if (oDate3 < 0) {
			document.getElementById("monney").innerHTML = 0;
    		document.getElementById("monney1").value = 0;
			return;
		}
		
		document.getElementById("monney").innerHTML = monney;
		document.getElementById("monney1").value = monney;
		
	}

	function fnUpdate(val) {
		fn();
	}

 
	
    function fnSubmit(){
		var delayDay = document.getElementById("delayDay").value.trim();
		var delayDayNone = document.getElementById("delayDayNone").value.trim();
		if(delayDayNone==""){
			document.getElementById("delayDayNone").value = delayDay + "," + delayDayNone;
		}
		else if(delayDayNone.substring(0,delayDayNone.indexOf(","))!=delayDay){
			document.getElementById("delayDayNone").value = delayDay + "," + delayDayNone;
		}

		var firmDelayDay = document.getElementById("firmDelayDay").value.trim();
		var firmDelayDayNone = document.getElementById("firmDelayDayNone").value.trim();
		if(firmDelayDayNone==""){
			document.getElementById("firmDelayDayNone").value = firmDelayDay + "," + firmDelayDayNone;
		}
		else if(firmDelayDayNone.substring(0,firmDelayDayNone.indexOf(","))!=firmDelayDay){
			document.getElementById("firmDelayDayNone").value = firmDelayDay + "," + firmDelayDayNone;
		}

		var newestDelivery = document.getElementById("newestDelivery").value.trim();
		var newestDeliveryNone = document.getElementById("newestDeliveryNone").value.trim();
		if(newestDeliveryNone==""){
			document.getElementById("newestDeliveryNone").value = newestDelivery + "," + newestDeliveryNone;
		}
		else if(newestDeliveryNone.substring(0,newestDeliveryNone.indexOf(","))!=newestDelivery){
			document.getElementById("newestDeliveryNone").value = newestDelivery + "," + newestDeliveryNone;
		}
    }
    
    function fnClassification(){
    	var classification = $("#classification").find("option:selected").text();
    	if(classification == "交期拖延"){
    		//质量问题描述:"+"质检有没有发现”+“质检对问题的解释:”
    		document.getElementById("qualityDepict").style.display = "none";
    		document.getElementById("qualityTesting").style.display = "none";
    		document.getElementById("expoundTesting").style.display = "none";
    		document.getElementById("tpsdeductMoney").style.display = "none";
    		document.getElementById("deliveryDepict").style.display = "block";
    		document.getElementById("newestDeliveryTwo").style.display = "block";
    		document.getElementById("displayone").style.display = "block";
    		document.getElementById("displaytow").style.display = "block";
    		document.getElementById("displaydelay").style.display = "block";
    		document.getElementById("displaythree").style.display = "block";
    	}
  /*   	else if(classification == "质量有问题"){
    		//交期问题描述:最新交期:原定交期:
    		document.getElementById("deliveryDepict").style.display = "none";
    		document.getElementById("newestDeliveryTwo").style.display = "none";
    		document.getElementById("displayone").style.display = "none";
    		document.getElementById("displaytow").style.display = "none";
    		document.getElementById("displaydelay").style.display = "none";
    		document.getElementById("displaythree").style.display = "none";
    		document.getElementById("qualityDepict").style.display = "block";
    		document.getElementById("qualityTesting").style.display = "block";
    		document.getElementById("expoundTesting").style.display = "block";
    		document.getElementById("tpsdeductMoney").style.display = "block";
    	}else if(classification == "质量交期都有问题")*/
    	else{
    		document.getElementById("deliveryDepict").style.display = "block";
    		document.getElementById("newestDeliveryTwo").style.display = "block";
    		document.getElementById("qualityDepict").style.display = "block";
    		document.getElementById("qualityTesting").style.display = "block";
    		document.getElementById("expoundTesting").style.display = "block";
    		document.getElementById("displayone").style.display = "block";
    		document.getElementById("displaytow").style.display = "block";
    		document.getElementById("displaydelay").style.display = "block";
    		document.getElementById("displaythree").style.display = "block";
    		document.getElementById("tpsdeductMoney").style.display = "block";
    	}
    }
    
    function fnstate(){
    	var classification = $("#state").find("option:selected").text();
    	if(classification == "未解决"){
    	document.getElementById("explain_tr").style.display = "none";
    	}else{
        	document.getElementById("explain_tr").style.display = "block";
    	}
    }
    //
    function fnqualityChange(){
    	var qualityLiabilityRatio = $("#qualityLiabilityRatio").find("option:selected").text();
    	if(qualityLiabilityRatio=="0:0"){
        	document.getElementById("psdeductMoney_label").innerHTML = 0;
    	}
    	else if(qualityLiabilityRatio=="1:0"){
    		document.getElementById("psdeductMoney_label").innerHTML = 200;
    	}else{
    		document.getElementById("psdeductMoney_label").innerHTML = 300;
    	}
    }

	 function checkWord(len,evt){
			//len为英文字符的个数，中文自动为其一般数量
			//evt是欲检测的对象
			   var str = evt.value;
			    var myLen = 0;
			    for(i=0; i<str.length&&myLen<=len; i++){
			        if(str.charCodeAt(i)>0&&str.charCodeAt(i)<128)
			            myLen++;
			        else
			            myLen+=2;
			        }
			    if(myLen>len){
			        alert("您输入超过限定长度");
			        evt.value = str.substring(0,i-1);
			    }
			}
</script>
<body onload="fn();">
	<div align="center">
		<form action="UpdateProjectServlet" method="post" onsubmit="return fnSubmit();">
			<input name="id" value="<c:out value="${project.id}" />"
				type="hidden" />
		
			<table width="960" align="center" cellspacing="2" bgcolor="3e6096">
			 <tr>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td bgcolor="#FFFFFF" class="title"><div align="center" style="color:#333333; font-size:24px">项目详情</div></td>
			  </tr>
			 <tr>
			    <td style="border-bottom:dashed #CCCCCC 1px">&nbsp;</td>
			  </tr>
			  <tr>
			    <td><table width="800" align="center" cellspacing="2" style="margin-top:10px" class="title">
			    
			  <tr>
					<td colspan="2" height="43"  colspan="2">项目ID:<input name="proId"
						value="<c:out value="${project.proId}" />" type="text"
						disabled="disabled" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					 项目名称:<input name="proName" size="40"
						value="<c:out value="${project.proName}" />" type="text"
						 /></td>
				</tr>

				<tr align="left">
					<td colspan="2" height="43">采购: <input name="purchase"
						value="<c:out value="${project.purchase}"/>" type="text"
						/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					 跟单销售: <input name="documentarySales"
						value="<c:out value="${project.documentarySales}"/>" type="text"
						 /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a
						href='http://192.168.1.2/erp/po/CaseInPo.aspx?id=<c:out value="${project.proId}" />'  target="_blank" style="color: #22DDB8" >《链接到ERP》</a></td>
				</tr>

				<tr align="left">
					<td colspan="2" height="43">项目USD金额本次: <input name="proThisUSD"
						value="<c:out value="${project.proThisUSD}" />" type="text"
						 size="10" /> &nbsp; &nbsp; 项目USD金额一年: <input
						name="proYearUSD" value="<c:out value="${project.proYearUSD}"  />"
						  type="text" size="10" /> &nbsp; &nbsp;  &nbsp; &nbsp;</td>
				</tr>
				<tr align="left">
					<td colspan="2" height="43">类型: <select id="classification" 
						name="classification" onchange="fnClassification();">
							<option
								<c:out value="${project.classification=='交期拖延'?'selected=selected':''}" />>交期拖延</option>
							<option
								<c:out value="${project.classification=='质量有问题'?'selected=selected':''}" />>质量有问题</option>
							<option
								<c:out value="${project.classification=='质量交期都有问题'?'selected=selected':''}" />>质量交期都有问题</option>
					</select>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;
					状态:<select id="state" name="state" onchange="fnstate();">
							<option
								<c:out value="${project.state=='0'?'selected=selected':''}" />
								value="0">未解决</option>
							<option
								<c:out value="${project.state!='0'?'selected=selected':''}" />
								value="1">已解决</option>
					</select>
					<label id="statetime"><c:out value="${project.state!='0'?project.statetime:''}" /></label>
					</td>
				</tr>
				
				
				
				<tr align="left"  id="explain_tr"  style="display: none">
					<td colspan="4" height="43" align="left"  style="border-top:dashed #CCCCCC 1px">更改状态的理由:<br> <textarea
						 onblur="checkWord(255,this)"	name="explain"  id="explain_value" cols="100" rows="3"><c:out
								value="${project.explain}" /></textarea></td>
				</tr>
			 
				
				 <tr>
    <td colspan="4"></td>
    </tr>
  <tr>
				 
				<tr   >
					<td colspan="4" height="43" align="left"  id="qualityDepict" style="border-top:dashed #CCCCCC 1px">质量问题描述:<br /> <textarea
							 name="qualityDepict" cols="100"  onblur="checkWord(255,this)"  rows="4"><c:out
								value="${project.qualityDepict}" /></textarea></td>
				</tr>
				<tr align="right">
					<td colspan="2" id="qualityTesting" style="display: none"   align="left">质检有没有发现: <input
						name="qualityTesting" disabled="disabled"
						<c:out value="${project.qualityTesting=='发现'?'checked=checked':''}" />
						type="radio" value="发现" /> 发现 <input name="qualityTesting" disabled="disabled" <c:out value="${project.qualityTesting=='未发现'?'checked=checked':''}" />
						type="radio" value="未发现" /> 未发现
					  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					销售：采购 责任比例:  <select id="qualityLiabilityRatio" name="qualityLiabilityRatio" onchange="fnqualityChange();">
							<option
								<c:out value="${project.qualityLiabilityRatio=='00'?'selected=selected':''}" />
								value="00">0:0</option>
							<option
								<c:out value="${project.qualityLiabilityRatio=='11'?'selected=selected':''}" />
								value="11">1:1</option>
							<option
								<c:out value="${project.qualityLiabilityRatio=='21'?'selected=selected':''}" />
								value="21">2:1</option>
							<option
								<c:out value="${project.qualityLiabilityRatio=='12'?'selected=selected':''}" />
								value="12">1:2</option>
							<option
								<c:out value="${project.qualityLiabilityRatio=='01'?'selected=selected':''}" />
								value="01">0:1</option>
							<option
								<c:out value="${project.qualityLiabilityRatio=='10'?'selected=selected':''}" />
								value="10">1:0</option>
					</select></td>
				</tr>
 
 					
				<tr align="left" style="display: none" id="tpsdeductMoney">
					<td colspan="2" height="43">采购+销售质量扣款: <input id="psdeductMoney_input" name="psdeductMoney"
						value="<c:out value="${project.psdeductMoney}"/>" type="hidden" 
						/><label id="psdeductMoney_label"><c:out value="${project.psdeductMoney}"/></label> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
					质检质量扣款: <input name="tdeductMoney"  size="12"
						value="<c:out value="${project.tdeductMoney==''||project.tdeductMoney==null?90:project.tdeductMoney}"/>" type="text"
						 /> 
						  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
						  质检名字: <input id="qualityName_input" name="qualityName"
						value="<c:out value="${project.psdeductMoney}"/>" type="text" size="16"
						/> 
						 </tr>
				 
				<tr align="left" >
					<td colspan="4" height="43" align="left"  style="border-top:dashed #CCCCCC 1px" id="deliveryDepict">交期问题描述:<br> <textarea
							name="deliveryDepict" cols="100" rows="3"  onblur="checkWord(255,this)" ><c:out
								value="${project.deliveryDepict}" /></textarea></td>
				</tr>
				
				<tr align="left" id="displayone" style="display: none">
					<td colspan="2" height="43">
					<input type="hidden" id="monney1" name="deductMoney"/>
					<label  style="color: red;">累计交期扣款: </label><label style="color: red;" id="monney"></label>
					<label style="color: red;">元</label>
					 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					销售：采购 责任比例:  <select name="liabilityRatio"  >
							<option
								<c:out value="${project.liabilityRatio=='11'?'selected=selected':''}" />
								value="11">1:1</option>
							<option
								<c:out value="${project.liabilityRatio=='21'?'selected=selected':''}" />
								value="21">2:1</option>
							<option
								<c:out value="${project.liabilityRatio=='12'?'selected=selected':''}" />
								value="12">1:2</option>
							<option
								<c:out value="${project.liabilityRatio=='01'?'selected=selected':''}" />
								value="01">0:1</option>
							<option
								<c:out value="${project.liabilityRatio=='10'?'selected=selected':''}" />
								value="10">1:0</option>
					</select></td>
				</tr>
				
			
				<tr align="left" id="displaytow" style="display: none">
					<td height="43">客户原因造成的拖延天数:
					<input  type="hidden" id="delayDayNone" 
						value="<c:out value="${project.delayDay}" />"  name="delayDay"/>
					 <c:set value="${fn:substring(project.delayDay, 0, fn:indexOf(project.delayDay,','))}" var="name1" ></c:set>
					 
					<input type="text"
						id="delayDay"  size="4" onkeyup="fnUpdate(this);" 
						value="<c:out value="${name1==''?'0':name1}"  />" />天
						
					<c:forEach var="w" items="${fn:split(project.delayDay,',')}" begin="1">
						<c:out value="${w}" />
					</c:forEach>
						</td>
					<td>&nbsp;&nbsp;公司特许的交期拖延: 
					<input  type="hidden" id="firmDelayDayNone" 
						value="<c:out value="${project.firmDelayDay}" />"  name="firmDelayDay"/>
					 <c:set value="${fn:substring(project.firmDelayDay, 0, fn:indexOf(project.firmDelayDay,','))}" var="name2" ></c:set>
					<input  value="<c:out value="${name2==''||name2=='0'?'7':name2}"    />" type="text"  id="firmDelayDay" size="4" onkeyup="fnUpdate(this);"/>天
					<c:forEach var="f" items="${fn:split(project.firmDelayDay,',')}" begin="1">
						<c:out value="${f}" />
					</c:forEach>
					
					</td>
				</tr>
				
				<tr align="left" id="displaydelay" style="display: none">
					 
					<td>拖延天数:  <input  type="hidden" id="delay_input" 
						value="<c:out value="${project.delay}" />"  name="delay"/>
					<label  id="delay" ><c:out value="${project.delay==''?'0':project.delay}" />天</label>
					</td>
				</tr>
				

				<tr align="left"  id="newestDeliveryTwo" style="display: none" >
					<td  height="43">最新交期:
					<input  type="hidden" id="newestDeliveryNone" 
						value="<c:out value="${project.newestDelivery}" />"  name="newestDelivery"/>
					 <c:set value="${fn:substring(project.newestDelivery, 0, fn:indexOf(project.newestDelivery,','))}" var="name2" ></c:set>
					 
					<input type="text" onFocus="WdatePicker()" 
					
						id="newestDelivery"  
						value="<c:out value="${name2==''?'':name2}"  />" />
					<%-- <div title="${fn:substring(project.newestDelivery, fn:indexOf(project.newestDelivery,','),30)}">移至此处查看最新交期历史</div>	 --%>
					<c:forEach var="n" items="${fn:split(project.newestDelivery,',')}" begin="1">
						<c:out value="${n}" />
					</c:forEach></td>
					<td  >
				&nbsp;&nbsp;  原定交期: <input name="scheduledDelivery" id="scheduledDelivery"
						id="scheduledDelivery"
						value="<c:out value="${project.scheduledDelivery}" />" type="text"
						disabled="disabled" onclick="WdatePicker()" /></td>
				</tr>
				<tr align="left" id="displaythree" style="display: none">
					<td colspan="2" height="43">最新交期解释: <input name="newestDeliveryTeason"
						type="text" size="80" value="<c:out value="${project.newestDeliveryTeason}" />"/>
					</td>
				</tr>
				  <tr>
    <td colspan="4"></td>
    </tr>
  <tr>
				<tr align="right">
					<td colspan="4" style="border-top:dashed #CCCCCC 1px" align="left"><p>
							采购对问题的解释:<br />
							<textarea name="expoundPurchase"  onblur="checkWord(255,this)" cols="100" rows="3"><c:out
									value="${project.expoundPurchase}" /></textarea>
							<br /><div  id="expoundTesting" > 质检对问题的解释: （拖交期类的项目，质检不用解释）
							<textarea name="expoundTesting"  onblur="checkWord(255,this)" cols="100" rows="3"><c:out
									value="${project.expoundTesting}" /></textarea></div>
						</p>
						<p>
							销售对问题的解释:<br />
							<textarea name="expoundSale"  onblur="checkWord(255,this)" cols="100" rows="3"><c:out
									value="${project.expoundSale}" /></textarea>
						</p>
						 </td>
				</tr>
			   <tr>
    <td colspan="4"></td>
    </tr>
  <tr>
				<tr align="center">
					<td colspan="4" align="center" style="border-top:dashed #CCCCCC 1px"><p>
							客户赔款金额: <input name="lossesMonney"
								value="<c:out value="${project.lossesMonney}" />" type="text"
								type="text" /> 客户赔款计算: <input name="lossesExpound"
								value="<c:out value="${project.lossesExpound}" />" type="text"
								size="40" />
						</p>
						<p>
							工厂扣款金额: <input name="FactoryDeduction"
								value="<c:out value="${project.FactoryDeduction}" />"
								type="text" /> 工厂扣款计算方式: <input type="text"
								name="FactoryCompute"
								value="<c:out value="${project.FactoryCompute}" />" size="40" />
						</p></td>
				</tr>
				<tr align="left"  >
					<td colspan="4" style="border-top:dashed #CCCCCC 1px" >领导指令${project.correcting}: <textarea  onblur="checkWord(255,this)" name="instruction" cols="100" rows="3"><c:out
									value="${project.instruction}" /></textarea>
									<div><input type="checkbox"  <c:out value="${project.correcting=='1'?'checked=checked':''}" /> name="correcting" value="1"/>谈清楚了， 改正进行中
									<span style="margin-left: 300px;">项目是否完结:
          <input name="isend" type="checkbox" size="15" <c:out value="${project.isend=='1'?'checked=checked':''}" /> value="1"/></span>
									</div>
					</td>
				</tr>
				
				<tr align="center">
					<td colspan="2"  height="73" align="center"><input style="width: 100px;height: 35px;font-size: 1em;" type="submit" value="保存数据"> 
					&nbsp;&nbsp;&nbsp;&nbsp;<input style="width: 100px;height: 35px;font-size: 1em;" onclick="fnback();" type="button" value="返回列表"></td>
				  </tr>
			  <tr>
			    <td colspan="2">&nbsp;</td>
			    <td colspan="2">&nbsp;</td>
			  </tr>
			</table></td>
			  </tr>
			</table>
		</form>
	</div>
</body>
</html>