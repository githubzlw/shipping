<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>录入项目</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
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
	<script type="text/javascript" src="My97DatePicker/WdatePicker.js">
	</script>
	 <script type="text/javascript">
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
<body>
<div align="center">
	 
<form action="SaveProjectServlet" method="post" onsubmit="return  fnSubmit();">
<table width="960" align="center" cellspacing="2" bgcolor="3e6096">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF" class="title"><div align="center" style="color:#333333; font-size:24px">录入项目</div></td>
  </tr>
  <tr>
    <td style="border-bottom:dashed #CCCCCC 1px">&nbsp;</td>
  </tr>
  <tr>
    <td><table width="800" align="center" cellspacing="2" style="margin-top:10px" class="title">
  <tr>
    <td height="43"  colspan="2">项目ID：
      <input name="proId" id="proId" type="text" /></td>
    <td colspan="2">项目名称：
      <input name="proName" type="text" size="30" /></td>
  </tr>
  <tr>
    <td height="43" colspan="2">采购：<input name="purchase" type="text" /></td>
    <td colspan="2">跟单销售:
        <input name="documentarySales" type="text" /></td>
  </tr>
  <tr>
    <td width="266" height="43">项目USD金额本次:
          <input name="proThisUSD" type="text" size="15" /></td>
    <td colspan="2" >项目USD金额一年:
          <input name="proYearUSD" type="text" size="10" /></td>
    <td width="266"> 类型:
          <select name="classification">
             <option value="交期拖延">交期拖延</option>
             <option value="质量有问题">质量有问题</option>
             <option value="质量交期都有问题">质量交期都有问题</option>
          </select></td>
  </tr>
  <tr>
    <td colspan="4"></td>
    </tr>
  <tr>
    <td colspan="4" style="border-top:dashed #CCCCCC 1px">质量问题描述:<br /><textarea name="qualityDepict"   onblur="checkWord(255,this)"   cols="100" rows="4"></textarea></td>
    </tr>
  <tr>
    <td height="43" colspan="3">质检有没有发现:
          <input name="qualityTesting" type="radio" value="发现" />
          发现
          <input name="qualityTesting" type="radio" value="未发现" />
        未发现</td>
         <td width="266" height="43">项目是否完结:
          <input name="isend" type="checkbox" size="15" value="1"/></td>
    </tr>
  <tr>
    <td colspan="4" style="border-top:dashed #CCCCCC 1px">交期问题描述:<br>
        <textarea name="deliveryDepict" cols="100" rows="3"  onblur="checkWord(255,this)"  ></textarea></td>
    </tr>
  <tr>
    <td height="43" colspan="2">最新交期:
      <input name="newestDelivery" type="text" onClick="WdatePicker()" /></td>
    <td colspan="2">原定交期:
          <input name="scheduledDelivery" type="text" onClick="WdatePicker()" /></td>
  </tr>
  <tr >
    <td colspan="2" align="right"><input style="width: 100px;height: 35px;font-size: 1em;margin-right: 20px;" type="submit" value="保存数据" /></td>
    <td colspan="2" align="left"><input style="width: 100px;height: 35px;font-size: 1em;" onclick="fnback();" type="button" value="返回列表"></td>
  </tr>
  	 
  <tr>
    <td colspan="2">&nbsp;</td>
    <td colspan="2">&nbsp;</td>
  </tr>
</table></td>
  </tr>
</table>
  </form>
  <script type="text/javascript">
  	function fnSubmit(){
  		var proId = document.getElementById("proId").value.trim();
  		if(proId==""){
  			alert("项目ID不能为空");
  			return false;
  		}else{
       	 this.submit;	
  		}
  	}
  	
  	function fnback() {
  		window.history.go(-1);
  		//window.location = "Index";
	}
  </script>
</body>
</html>