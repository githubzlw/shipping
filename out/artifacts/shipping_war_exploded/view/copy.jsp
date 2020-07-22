<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'copy.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="${ctx}/My97DatePicker/WdatePicker.js">
	</script>
	<script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>	
	<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
  </head>
  
<style type="text/css">        
  #table1 input{
    width: 100%;
  }

    /*添加一行样式*/  
   .p_add{
    color:#ff4d09;
   }           
   .add{
        margin-left: 63%;
  font-size: 30px;
  font-weight: 700;
   }
   
     /*去除一行样式*/             
   .del{
  margin-left: 35px;
  font-size: 30px;
  font-weight: 700;
   }

 .tan{position:fixed;top:200px;left:20%;background-color:rgba(0,0,0,.6);
       padding:8px 12px;z-index:10;color:#fff;display:none;}
	
</style>  

<script type="text/javascript">

$(function(){  
    /**
     *计算重量
     */
    $('.n_weight').blur(function(){
    	var totalNW = 0;
   	    $('.n_weight').each(function(i){
   	    	var weight = $(this).val();
   	    	if(isNaN(weight) || weight == "" || weight == undefined){
   	    		return true;
   	    	}else{
   	    		totalNW +=Number(weight);
   	    		$('#totalNW').val(Number(totalNW).toFixed(2));
   	    	}	   	    	
   	    })
   	    
    })
}) 


        var rmb_flag = true;
	    //验算出口人民币和采购价是否一致
	    function save_order(orderStatus){
	    		    	
	    	//验证是否已经上传合同	
	        var haveBargain = true;
	    	var purno = "";
	    	$('#table1').find('tr').each(function(i){
	    		if(i>0){
	    			getDetailByProjectId($(this).find('.order-id'),2);
	    			purno = $(this).find('.order-id').val();
	    			if(!purno){
	    				return false;
	    			}
	    			haveBargain = $(this).find('td:last').find('input:last').val();
	    			if(!haveBargain){
	    				showNotice(purno+"未上传合同",2000);
	    				return false;
	    			}
	    		}
	    	})	    	
    		if(!haveBargain){
    			return false;
    		}
	    	
	    	
	    	//验证是否出货完成
	    	verify();
	    	
	    		
	    	//获取总共合同数
	    	var totalSize = $('#table1').find('tr').length-1;
	    	$('#totalSize').val(totalSize);
	    	
	    	
	    	var totalExportPrice = 0;
	    	var totalPurchasePrice = 0;
	 
	    	$('.export-cn').each(function(){
	    		var exportPrice = $(this).val();
	   	    	if(isNaN(exportPrice) || exportPrice == "" || exportPrice == undefined){
	   	    		return true;
	   	    	}else{
	   	    		totalExportPrice +=Number(exportPrice);
	   	    	}	   	    
	    	})
	    	
	    	
	    	$('.export-cn1').each(function(){
	    		var purchasePrice = $(this).val();
	   	    	if(isNaN(purchasePrice) || purchasePrice == "" || purchasePrice == undefined){
	   	    		return true;
	   	    	}else{
	   	    		totalPurchasePrice +=Number(purchasePrice);
	   	    	}	   	    
	    	})
	    	
	    	if(Number(totalExportPrice).toFixed(2) != Number(totalPurchasePrice).toFixed(2)){
	    		alert("出口人民币和采购总价不一致！");
	    		return false;
	    	}	
	    	
	    	//验算毛重是否大于净重
	    	var totalGW = $('#totalGW').val();
	    	var totalNW = $('#totalNW').val();
	    	if(Number(totalGW) < Number(totalNW)){
	    		alert("毛重不能小于净重！");
	    		return false;
	    	}
	    	
	    	//验证净重是否相等
	     	var totalNWCal = 0;
	   	    $('.n_weight').each(function(i){
	   	    	var weight = $(this).val();
	   	    	if(isNaN(weight) || weight == "" || weight == undefined){
	   	    		return true;
	   	    	}else{
	   	    		totalNWCal +=Number(weight);
	   	    	}	   	    	
	   	    })
	   	    totalNWCal = Number(totalNWCal).toFixed(2);
	   	    totalNW =  Number(totalNW).toFixed(2);
	    	if(totalNW != totalNWCal){
	    		alert("净重不相等！");
	    		return false;
	    	}
	    	
	    	
	    	
	    	
	    	
	    	
	    	//验算清关总价是否等于数量*单价	
	       var flag = true;	
	       $('.unit-price-all').each(function(){	
	    	  var priceAll = $(this).val(); 
	    	  var quantity = $(this).parent().parent().find('input').eq(2).val();
	    	  var unitPrice = $(this).parent().parent().find('input').eq(4).val();
	    	  var calPrice = priceAll / quantity;
	    	  if(isNaN(calPrice) || calPrice == "" || calPrice == undefined){
	    		  if(unitPrice != ''){
			    		alert("清关总价不等于数量*单价！");
			    		flag = false;
			    		return false;
			    	}	 	    	
	    	  }else{
	    		  if(Number(calPrice).toFixed(0) != Number(unitPrice).toFixed(0)){
			    		alert("清关总价不等于数量*单价！");
			    		flag = false;
			    		return false;
			    	}	 	    	  
	    	  }
	    	
	      })    		    	
	        if(!(flag)){
	        	return false;	
	        }	       
	       
	        
	        //判断出口金额是否大于合同金额
	        if(!rmb_flag){
	        	return false;
	        }	       
	        
 	       //判断境内货源地不能为空
 	       var destinationFlag = true;
	        $('.sourceDestination').each(function(){
	        	var item = $(this).parents('tr').find('td:first').find('input').val();
	        	var sourceDestination = $(this).val();
	        	if(item && !sourceDestination){
	        		showNotice('境内货源地不能为空',2000);
	        		destinationFlag = false;
	        		return false;
	        	}
	        })
 	        
	        if(!destinationFlag){
	        	return false;
	        }
	        
	        if($('#contract_date').val() == null || $('#contract_date').val() == ''){
 	        	showNotice('请输入外销合同时间',2000);
 	        	return false;
 	        }
	        
	        //查看是否有发货地址
	        var exportPlace = $('input[name="exportPlace"]:checked').val();
	        if(!exportPlace){
	        	showNotice('发货地址不能为空',2000);
	        	return false;
	        }
	        
	        //普通用户才能修改报关状态（预保存0或者正式保存1）
		     $('#orderStatus').val(orderStatus);
	        //上传excel的文件名
 	        var fileName = $('#fileName').val();
	        if(!checkProduct() && !fileName && orderStatus == 1){
	        	showNotice('多合同多品类，请上传详情excel',2000);
	        	return false;
	        } 
	        
	        
	    	$('#order_form').submit();
	    }
	    
	    
	    
	    /**
	     *确认货币单元
	     */
	    function confirm_currency(obj){
	    	var select_currency = $("#select_currency").val();
	    	var currency = $(obj).val();
    		if (confirm("确认修改货币单位?")) {	  
    			$("#select_currency").val(currency);
	    	}else{
	    	  $(":radio[name='currency'][value='" + select_currency + "']").attr("checked",true);
	    	}
	    	
	    }
	    
	    
        /**
         *验证次合同是否出货
         */
	  function verify(){
		  	//验算合同是否已出运完
	    	$('#table1').find('tr').each(function(i){
	    		if(i > 0){
		    		 var purno = $(this).find('td:first').find('input').val();
		    		 if(purno != null && purno != '' && purno != undefined){
		    			 
		    		    var rmb = $(this).find('td').eq(5).find('input').val(); 
		    		    var money = $(this).find('td').eq(2).find('input').val(); 
		    		    if(rmb == null || rmb == '' || rmb == undefined){
		    		    	rmb = 0;
		    		    }
		    			var u_flag = true; 
				    	$.ajax({
							type : "post",
							datatype : "json",
							async : false, 
							url : "../GetContractAmountServlet",
							data : {
								"purno" : purno ,
								"rmb" : rmb,
								"money" : money 
							},
							success : function(result) {
								var dataObj = eval("("+result+")");								
								u_flag = dataObj.flag;
					    		 if(u_flag == false){
					    			 showNotice(purno+"出口总金额已大于合同金额",2000);
					    			 rmb_flag = false;
					    			 return false;
					    		 }
							},
							error : function() {
			
							}
						});			    		
		    		 }
	    		}

	    	})	    	
	  }
	    
	    
      //根据合同号获取出货，工厂信息
      function getDetailByProjectId(obj,status){
    	  var purno = $(obj).val();
    	    if(!purno){
    	       return false; 	
    	    }
	    	$.ajax({
				type : "post",
				datatype : "json",
				async : false, 
				url : "GetProjectERPServlet",
				data : {
					"purno" : purno
				},
				success : function(result) {
					var dataObj = eval("("+result+")");		
					$(obj).parents('tr').find('td:eq(1)').find('input').val(dataObj.factoryName);
					$(obj).parents('tr').find('td:eq(2)').find('input').val(dataObj.totalPrice);
					if(status == 1){
						if(Number(dataObj.totaltimes) > Number(dataObj.times)){
							$(obj).parents('tr').find('td:eq(3)').find('input').val(Number(dataObj.times)+1);
						}
						$(obj).parents('tr').find('td:eq(4)').find('input').val(dataObj.totaltimes == '0' ? 1 : dataObj.totaltimes);
						$(obj).parents('tr').find('td:eq(5)').find('input').val(dataObj.balancePrice);
					}			
					$(obj).parents('tr').find('td:last').find('span:eq(0)').text(dataObj.times == 0 ? '还未出货' : '已出货'+dataObj.times+'次');
					$(obj).parents('tr').find('td:last').find('span:eq(1)').text('合同总金额:'+dataObj.totalPrice);
					$(obj).parents('tr').find('td:last').find('span:eq(2)').text('已出货金额:'+dataObj.totalPay);
	
					$(obj).parents('tr').find('td:last').find('input:last').val(dataObj.haveBargain);
					if(dataObj.haveBargain == false){
						$(obj).parents('tr').find('td:last').find('span:eq(0)').html('合同未上传');
						showNotice('未查询到合同'+purno+',请填写正确格式',2000);
						return false;
					}
					
					$(obj).parents('tr').find('td:last').find('span:eq(3)').text('未出货金额:'+dataObj.balancePrice);
					//如果已出货完成给予提醒
					if(dataObj.balancePrice == 0){
						showNotice(purno+'已出完货,请确认',2000);
						return false;
					}
					
				},
				error : function() {
                    
				}
			});		
      }
       
	    
          
       
       
       //根据合同号获取出货，工厂信息
       function getUnitPrice(){
    	   cal_price();
           var tl = $('#contract_ul').find('li').length;
    	   for(var i=0;i<tl;i++){
    		 var purnos = $('#contract_ul').find('li').eq(i).attr('field');      		   
    		 $.ajax({
 				type : "post",
 				datatype : "json",
 				async : false, 
 				url : "../GetInvoice",
 				data : {
 					"purnos" : purnos
 				},
 				success : function(result) {
    					var dataObj = eval("("+result+")");	
    					var totalQty = dataObj.totalQty;
    					var totalPrice = dataObj.totalPrice;
    					var uPrice = dataObj.uPrice;
    					var product = dataObj.product;
    					$('#product_table').find('tr').eq(i+1).find('td').eq(2).find('input').val(totalQty);
    					$('#product_table').find('tr').eq(i+1).find('td').eq(5).find('input').val(uPrice);
    					$('#product_table').find('tr').eq(i+1).find('td').eq(6).find('input').val(totalPrice);
    					$('#product_table').find('tr').eq(i+1).find('td').eq(1).find('input').val(product);
    				},
    				error : function() {
    					   
    					 
    				}
    			});	
    	   }	
 	   }
       
       
       //判断是否是多合同多品名情况
       function checkProduct(){
    	   //查询合同个数
    	   var count1 = 0;
    	   //品类数量
    	   var count2 = 0;
    	   $('.order-id').each(function(){
    		   if($(this).val()){
    			   count1++;
    		   }
    	   })
    	    
    	   for(var i=1;i<=13;i++){
    		   var itemchn = $('input[name="itemchn'+i+'"]').val();
    		   if(itemchn){
    			   count2++;  
    		   }
    	   }
    	  
    	     //如果多合同多品类返回fasle，必须上传excel表
	    	  if(count1 > 1 && count2 > 1){
	    		  return false;
	    	  }else{
	    		  return true;
	    	  }	   
       }
       
       
       
       
       //上传项目、品名
       function upload(obj){	
       		var path = $(obj).val();
       	    sppath = path.split("\\");
       	    var fileName = sppath[sppath.length-1];	  	   
       	    if(fileName == null || fileName == '' || fileName == undefined){
       	    	return false;
       	    }else{
      	        var gen = fileName.lastIndexOf("."); 
       	        var type = fileName.substr(gen); 
       	        if(type.toLowerCase()  != ".xls" && type.toLowerCase() !=".xlsx"){
       	            showNotice('请上传excel文件',2000);
       	            return false;
       	        }
       	    }                     		      		  
       		 //先上传后获取上传文件路径
       		 $(obj).parents('form').ajaxSubmit({
       			type: "post",
       			url: "../UploadExcelServlet",
       	     	dataType: "text",
       	     	success: function(str){
	       	     	var result = eval('(' + str + ')');		
	       	     	var fileName = result.fileName;  
	       		    $('#fileName').val(fileName); 		
	       		    showNotice('上传成功',2000);
	       		    //$(obj).val('');
       	     	},
       			error: function(){
       				showNotice('上传失败',2000);
       			    $(obj).val('');
       			}       	     	
       	 	}); 	 		    

        }
       
       
       
       
	      /**
	       *提示弹框
	       */
	      function showNotice(message,time){
	    	  $('.tan').text(message).show();
	    	  setTimeout("$('.tan').hide()", time);
	      }	


 //合同输入table新增一行
 function addTr(){    	   
	  $('#table1').append($('#table1').find('tr:last').clone()); 
	  $('#table1').find('tr:last').find('td:eq(6)').find('span').text('');	
	  var index = $('#table1').find('tr').length-1;
	  $('#table1').find('tr:last').find('input').each(function(){
		   $(this).val('');
		   var name = $(this).attr('name');
		   var num = name.replace(/[^0-9]/g, "");
		   name = name.replace(num,index);
		   $(this).attr('name',name);
	  })
	  $('#totalSize').val(index);
 }
 
 //删除一行
 function delTr(){
	   if($('#table1').find('tr').length < 3){    		  
		   return false;
	   }
	   $('#table1').find('tr:last').remove();
	   $('#totalSize').val($('#table1').find('tr').length-1);
 }
 
  </script>
  
  <body>	
  <div class="tan"></div>	
  <h1>复制出运单</h1>
   <form action="SaveServlet" method="post" id="order_form">
   	<table border="1">
   	    <input type="hidden" name="orderStatus" id="orderStatus">
     	<input type="hidden" name="fileName" id="fileName">
      	<input type="hidden" id="totalSize" name="totalSize" value="10">
   		采购：<input name="purchase" type="text" value="<%=request.getAttribute("purchase")%>"/>
   		销售：<input name="sale" type="text" value="<%=request.getAttribute("sale")%>"/>
   		客户名：<input name="clientName" type="text" value="<%=request.getAttribute("clientName")%>"/>
   		客户公司名称：<input style="width: 294px;" name="companyName" id="company_name" type="text" value="<%=request.getAttribute("companyName")%>"/>
	   	<br/>
	    <c:if test="${attrSource == 1 && auth == 1}">
	   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
	   	<br/>
	   	</c:if>
	    <c:if test="${attrSource == 0}">
	   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
	   	<br/>
	   	</c:if>
	    <c:if test="${attrSource == 1 && auth != 1}">
	    <span style="display:none;">
	   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
	   	</span>
	   	<br/>
	   	</c:if>
	   	<table border="1" id="table1">
	   		<tr>
	   			<td>采购合同号(格式：合10000-1A)</td>
	   			<td>工厂名称</td>
	   			<td>合同金额</td>
	   			<td>第几次出货批次</td>
	   			<td>总共几次出货批次</td>
	   			<td>本次出口人民币金额</td>
	   			<td>参考数据</td>
	   		</tr>
	   		

		    	<tr>
	   			<td><input type="text" name="purno1" class="order-id" onblur="getDetailByProjectId(this,1)"/></td>
	   			<td><input type="text" name="factory1" /></td>
	   			<td><input type="text" name="money1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input type="text" name="times1"/></td>
	   			<td><input type="text" name="totaltimes1"/></td>
	   			<td><input type="text" name="rmb1"  class="export-cn" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>	   			
	   			<td style="color:#1605f3"><span style="padding-right: 5px;"></span><span></span><br><span></span><span></span><input type="hidden"/></td>
	   			</tr> 

	   		
	   	</table>
	   	<p class="p_add"><span class="add" onclick="addTr()">+</span><span onclick="addTr()">(添加一行)</span><span class="del" onclick="delTr()">-</span><span onclick="delTr()">(删除一行)</span></p>
	   	希望出口日期:<input name="hopeDate" type="text" onClick="WdatePicker()"/>
	   	预计工厂可送仓日期:<input name="estimateDate" type="text" onClick="WdatePicker()"/>
	   	<br/>
	   	
	   	交易方式 Term:<input name="transaction1" type="text"/>
	   	货运方式 :<input type="radio" name="transaction2" value="海运">海运
				<input type="radio" name="transaction2" value="陆运">陆运
				<input type="radio" name="transaction2" value="空运">空运
				<input type="radio" name="transaction2" value="快递">快递
	   	<br/>
	   	总体积(CBM):<input name="volume" type="text"/>
	   	总毛重 G.W(KGS):<input name="totalGW" type="text" id="totalGW"/>
	   	总净总 N.W(KGS): <input name="totalNW" type="text" id="totalNW"/>
	   	<br/>
	   	
	   	From:<input name="fromwhere" type="text" value="<%=request.getAttribute("fromwhere")%>"/>
	   	To:<input name="towhere" type="text" value="<%=request.getAttribute("towhere")%>" id="destinationPort"/>
	   	<br/>
	   	Package 包装类型:<input name="package" type="text"/>
	   	Package 包装数量:<input name="packageNum" type="text"/>
	   	<br/>
	   	支付币种：<input type="radio" name="currency" value="USD" checked onchange="confirm_currency(this)">美元
				<input type="radio" name="currency" value="EUR" onchange="confirm_currency(this)">欧元
				<input type="radio" name="currency" value="GBP" onchange="confirm_currency(this)">英镑
				<input type="radio" name="currency" value="RMB" onchange="confirm_currency(this)">人民币
				<input type="radio" name="currency" value="AUD" onchange="confirm_currency(this)">澳元
	   	<br/>
	   
	   	<br/>
	   托盘/木箱尺寸：<input name="palletDimension" type="text">	<br/>
	   小箱件数(美国货必填)：<input name="casketQuantity" type="number"><br/>
	   订框箱型：<select name="casketType">
			   <option></option>
			   <option <c:if test="${casketType eq '40尺高柜'}">selected</c:if>>40尺高柜</option>
			   <option <c:if test="${casketType eq '40尺平柜'}">selected</c:if>>40尺平柜</option>
			   <option <c:if test="${casketType eq '20尺柜'}">selected</c:if>>20尺柜</option>
			   <option <c:if test="${casketType eq '拼箱'}">selected</c:if>>拼箱</option>
			   <option <c:if test="${casketType eq '特殊柜'}">selected</c:if>>特殊柜</option>
			 </select>  <br/>
	  特殊要求备注(用&ltbr&gt换行)：<textarea name="detailed" cols="45" rows="5"><%=request.getAttribute("detailed")%></textarea>
	   	<br/>
	   	<table border="1">
	   		<tr>
	   			<td width="80px">Item英文名</td>
	   			<td width="80px">Item中文名</td>
			    <td width="80px">Quantity(请只填数字)</td>
			    <td width="80px">数量单位</td>
			    <td width="80px"><strong>采购价 总价(只填数字 单位:RMB)</strong></td>
			    <td width="80px">Unit Price</td>
			    <td width="80px">清关总价</td>
			    <td width="80px">Shipping Mark</td>
			    <td width="80px"><p>N.W.(请只填数字 单位:kg)</p></td>
			    <td width="80px">境内货源地</td>
			    <td width="80px">实际报关总价（会计填）</td>
			    <td width="150px">HS Code （物流填）</td>
			    <td width="80px">退税率 （物流填）</td>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid1")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng1" value="<%=request.getAttribute("itemeng1")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn1" value="<%=request.getAttribute("itemchn1")%>"/></td>
	   			<td><input size="10" type="text" name="quantity1"/></td>
	   			<td><select name="unit1" style="width: 99%;"><option <c:if test="${unit1 == '个'}">selected</c:if>>个</option><option <c:if test="${unit1 == '件'}">selected</c:if>>件</option><option <c:if test="${unit1 == '套'}">selected</c:if>>套</option><option <c:if test="${unit1 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice1" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice1"/></td>
	   			<td><input size="10" type="text" name="unitpriceall1"  class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark1" value="<%=request.getAttribute("shopingmark1")%>"/></td>
	   			<td><input size="10" type="text" name="nw1" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination1" value="<%=request.getAttribute("sourceDestination1")==null?"":request.getAttribute("sourceDestination1")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode1"/></td>
	   			<td><input size="10" type="text" name="rate1"/></td>
	   			<td><input size="10" type="hidden" name="itemid1"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng1"/></td>
	   			<td><input size="10" type="text" name="itemchn1"/></td>
	   			<td><input size="10" type="text" name="quantity1"/></td>
	   			<td><select name="unit1" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice1" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice1"/></td>
	   			<td><input size="10" type="text" name="unitpriceall1" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark1" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw1" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination1"/></td>
	   			<td><input size="10" type="text" name="trueprice1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode1"/></td>
	   			<td><input size="10" type="text" name="rate1"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid2")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng2" value="<%=request.getAttribute("itemeng2")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn2" value="<%=request.getAttribute("itemchn2")%>"/></td>
	   			<td><input size="10" type="text" name="quantity2"/></td>
	   			<td><select name="unit2" style="width: 99%;"><option <c:if test="${unit2 == '个'}">selected</c:if>>个</option><option <c:if test="${unit2 == '件'}">selected</c:if>>件</option><option <c:if test="${unit2 == '套'}">selected</c:if>>套</option><option <c:if test="${unit2 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice2" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice2"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark2" value="<%=request.getAttribute("shopingmark2")%>"/></td>
	   			<td><input size="10" type="text" name="nw2" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination2" value="<%=request.getAttribute("sourceDestination2")==null?"":request.getAttribute("sourceDestination2")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice2" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode2"/></td>
	   			<td><input size="10" type="text" name="rate2"/></td>
	   			<td><input size="10" type="hidden" name="itemid2"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng2"/></td>
	   			<td><input size="10" type="text" name="itemchn2"/></td>
	   			<td><input size="10" type="text" name="quantity2"/></td>
	   			<td><select name="unit2" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice2" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice2"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark2" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw2" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination2"/></td>
	   			<td><input size="10" type="text" name="trueprice2" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode2"/></td>
	   			<td><input size="10" type="text" name="rate2"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid3")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng3" value="<%=request.getAttribute("itemeng3")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn3" value="<%=request.getAttribute("itemchn3")%>"/></td>
	   			<td><input size="10" type="text" name="quantity3"/></td>
	   			<td><select name="unit3" style="width: 99%;"><option <c:if test="${unit3 == '个'}">selected</c:if>>个</option><option <c:if test="${unit3 == '件'}">selected</c:if>>件</option><option <c:if test="${unit3 == '套'}">selected</c:if>>套</option><option <c:if test="${unit3 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice3" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice3"/></td>
	   			<td><input size="10" type="text" name="unitpriceall3" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark3" value="<%=request.getAttribute("shopingmark3")%>"/></td>
	   			<td><input size="10" type="text" name="nw3" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination3" value="<%=request.getAttribute("sourceDestination3")==null?"":request.getAttribute("sourceDestination3")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice3" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode3"/></td>
	   			<td><input size="10" type="text" name="rate3"/></td>
	   			<td><input size="10" type="hidden" name="itemid3" /></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng3"/></td>
	   			<td><input size="10" type="text" name="itemchn3"/></td>
	   			<td><input size="10" type="text" name="quantity3"/></td>
	   			<td><select name="unit3" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice3" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice3"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark3" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw3" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination3"/></td>
	   			<td><input size="10" type="text" name="trueprice3" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode3"/></td>
	   			<td><input size="10" type="text" name="rate3"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid4")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng4" value="<%=request.getAttribute("itemeng4")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn4" value="<%=request.getAttribute("itemchn4")%>"/></td>
	   			<td><input size="10" type="text" name="quantity4"/></td>
	   			<td><select name="unit4" style="width: 99%;"><option <c:if test="${unit4 == '个'}">selected</c:if>>个</option><option <c:if test="${unit4 == '件'}">selected</c:if>>件</option><option <c:if test="${unit4 == '套'}">selected</c:if>>套</option><option <c:if test="${unit4 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice4" class="export-cn1" /></td>
	   			<td><input size="10" type="text" name="unitprice4" /></td>
	   			<td><input size="10" type="text" name="unitpriceall4" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark4" value="<%=request.getAttribute("shopingmark4")%>"/></td>
	   			<td><input size="10" type="text" name="nw4" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination4" value="<%=request.getAttribute("sourceDestination4")==null?"":request.getAttribute("sourceDestination4")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice4" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode4" /></td>
	   			<td><input size="10" type="text" name="rate4" /></td>
	   			<td><input size="10" type="hidden" name="itemid4"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng4"/></td>
	   			<td><input size="10" type="text" name="itemchn4"/></td>
	   			<td><input size="10" type="text" name="quantity4"/></td>
	   			<td><select name="unit4" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice4" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice4"/></td>
	   			<td><input size="10" type="text" name="unitpriceall4" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark4" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw4" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination4"/></td>
	   			<td><input size="10" type="text" name="trueprice4" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode4"/></td>
	   			<td><input size="10" type="text" name="rate4"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid5")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng5" value="<%=request.getAttribute("itemeng5")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn5" value="<%=request.getAttribute("itemchn5")%>"/></td>
	   			<td><input size="10" type="text" name="quantity5"/></td>
	   			<td><select name="unit5" style="width: 99%;"><option <c:if test="${unit5 == '个'}">selected</c:if>>个</option><option <c:if test="${unit5 == '件'}">selected</c:if>>件</option><option <c:if test="${unit5 == '套'}">selected</c:if>>套</option><option <c:if test="${unit5 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice5" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice5" /></td>
	   			<td><input size="10" type="text" name="unitpriceall5"  class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark5" value="<%=request.getAttribute("shopingmark5")%>"/></td>
	   			<td><input size="10" type="text" name="nw5" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination5" value="<%=request.getAttribute("sourceDestination5")==null?"":request.getAttribute("sourceDestination5")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice5" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode5" /></td>
	   			<td><input size="10" type="text" name="rate5" /></td>
	   			<td><input size="10" type="hidden" name="itemid5"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng5"/></td>
	   			<td><input size="10" type="text" name="itemchn5"/></td>
	   			<td><input size="10" type="text" name="quantity5"/></td>
	   			<td><select name="unit5" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice5" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice5"/></td>
	   			<td><input size="10" type="text" name="unitpriceall5" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark5" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw5" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination5"/></td>
	   			<td><input size="10" type="text" name="trueprice5" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode5"/></td>
	   			<td><input size="10" type="text" name="rate5"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid6")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng6" value="<%=request.getAttribute("itemeng6")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn6" value="<%=request.getAttribute("itemchn6")%>"/></td>
	   			<td><input size="10" type="text" name="quantity6"/></td>
	   			<td><select name="unit6" style="width: 99%;"><option <c:if test="${unit6 == '个'}">selected</c:if>>个</option><option <c:if test="${unit6 == '件'}">selected</c:if>>件</option><option <c:if test="${unit6 == '套'}">selected</c:if>>套</option><option <c:if test="${unit6 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice6" class="export-cn1" /></td>
	   			<td><input size="10" type="text" name="unitprice6"/></td>
	   			<td><input size="10" type="text" name="unitpriceall6" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark6" value="<%=request.getAttribute("shopingmark6")%>"/></td>
	   			<td><input size="10" type="text" name="nw6" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination6" value="<%=request.getAttribute("sourceDestination6")==null?"":request.getAttribute("sourceDestination6")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice6"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode6" /></td>
	   			<td><input size="10" type="text" name="rate6" /></td>
	   			<td><input size="10" type="hidden" name="itemid6"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng6"/></td>
	   			<td><input size="10" type="text" name="itemchn6"/></td>
	   			<td><input size="10" type="text" name="quantity6"/></td>
	   			<td><select name="unit6" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice6" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice6"/></td>
	   			<td><input size="10" type="text" name="unitpriceall6" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark6" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw6" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination6"/></td>
	   			<td><input size="10" type="text" name="trueprice6" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode6"/></td>
	   			<td><input size="10" type="text" name="rate6"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid7")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng7" value="<%=request.getAttribute("itemeng7")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn7" value="<%=request.getAttribute("itemchn7")%>"/></td>
	   			<td><input size="10" type="text" name="quantity7"/></td>
	   			<td><select name="unit7" style="width: 99%;"><option <c:if test="${unit7 == '个'}">selected</c:if>>个</option><option <c:if test="${unit7 == '件'}">selected</c:if>>件</option><option <c:if test="${unit7 == '套'}">selected</c:if>>套</option><option <c:if test="${unit7 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice7" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice7"/></td>
	   			<td><input size="10" type="text" name="unitpriceall7" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark7" value="<%=request.getAttribute("shopingmark7")%>"/></td>
	   			<td><input size="10" type="text" name="nw7" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination7" value="<%=request.getAttribute("sourceDestination7")==null?"":request.getAttribute("sourceDestination7")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice7"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode7" /></td>
	   			<td><input size="10" type="text" name="rate7" /></td>
	   			<td><input size="10" type="hidden" name="itemid7"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng7"/></td>
	   			<td><input size="10" type="text" name="itemchn7"/></td>
	   			<td><input size="10" type="text" name="quantity7"/></td>
	   			<td><select name="unit7" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice7" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice7"/></td>
	   			<td><input size="10" type="text" name="unitpriceall7" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark7" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw7" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination7"/></td>
	   			<td><input size="10" type="text" name="trueprice7" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode7"/></td>
	   			<td><input size="10" type="text" name="rate7"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid8")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng8" value="<%=request.getAttribute("itemeng8")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn8" value="<%=request.getAttribute("itemchn8")%>"/></td>
	   			<td><input size="10" type="text" name="quantity8" /></td>
	   			<td><select name="unit8" style="width: 99%;"><option <c:if test="${unit8 == '个'}">selected</c:if>>个</option><option <c:if test="${unit8 == '件'}">selected</c:if>>件</option><option <c:if test="${unit8 == '套'}">selected</c:if>>套</option><option <c:if test="${unit8 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice8" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice8"/></td>
	   			<td><input size="10" type="text" name="unitpriceall8" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark8" value="<%=request.getAttribute("shopingmark8")%>"/></td>
	   			<td><input size="10" type="text" name="nw8" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination8" value="<%=request.getAttribute("sourceDestination8")==null?"":request.getAttribute("sourceDestination8")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice8" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode8"/></td>
	   			<td><input size="10" type="text" name="rate8"/></td>
	   			<td><input size="10" type="hidden" name="itemid8"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng8"/></td>
	   			<td><input size="10" type="text" name="itemchn8"/></td>
	   			<td><input size="10" type="text" name="quantity8"/></td>
	   			<td><select name="unit8" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice8" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice8"/></td>
	   			<td><input size="10" type="text" name="unitpriceall8" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark8" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw8" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination8"/></td>
	   			<td><input size="10" type="text" name="trueprice8" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode8"/></td>
	   			<td><input size="10" type="text" name="rate8"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid9")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng9" value="<%=request.getAttribute("itemeng9")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn9" value="<%=request.getAttribute("itemchn9")%>"/></td>
	   			<td><input size="10" type="text" name="quantity9"/></td>
	   			<td><select name="unit9" style="width: 99%;"><option <c:if test="${unit9 == '个'}">selected</c:if>>个</option><option <c:if test="${unit9 == '件'}">selected</c:if>>件</option><option <c:if test="${unit9 == '套'}">selected</c:if>>套</option><option <c:if test="${unit9 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice9" class="export-cn1" /></td>
	   			<td><input size="10" type="text" name="unitprice9"/></td>
	   			<td><input size="10" type="text" name="unitpriceall9" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark9" value="<%=request.getAttribute("shopingmark9")%>"/></td>
	   			<td><input size="10" type="text" name="nw9" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination9" value="<%=request.getAttribute("sourceDestination9")==null?"":request.getAttribute("sourceDestination9")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice9"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode9" /></td>
	   			<td><input size="10" type="text" name="rate9" /></td>
	   			<td><input size="10" type="hidden" name="itemid9" /></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng9"/></td>
	   			<td><input size="10" type="text" name="itemchn9"/></td>
	   			<td><input size="10" type="text" name="quantity9"/></td>	   			
	   			<td><select name="unit9" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice9" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice9"/></td>
	   			<td><input size="10" type="text" name="unitpriceall9" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark9" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw9" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination9"/></td>
	   			<td><input size="10" type="text" name="trueprice9" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode9"/></td>
	   			<td><input size="10" type="text" name="rate9"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid10")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng10" value="<%=request.getAttribute("itemeng10")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn10" value="<%=request.getAttribute("itemchn10")%>"/></td>
	   			<td><input size="10" type="text" name="quantity10"/></td>
	   			<td><select name="unit10" style="width: 99%;"><option <c:if test="${unit10 == '个'}">selected</c:if>>个</option><option <c:if test="${unit10 == '件'}">selected</c:if>>件</option><option <c:if test="${unit10 == '套'}">selected</c:if>>套</option><option <c:if test="${unit10 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice10" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice10"/></td>
	   			<td><input size="10" type="text" name="unitpriceall10" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark10" value="<%=request.getAttribute("shopingmark10")%>"/></td>
	   			<td><input size="10" type="text" name="nw10" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination10" value="<%=request.getAttribute("sourceDestination10")==null?"":request.getAttribute("sourceDestination10")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice10" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode10" /></td>
	   			<td><input size="10" type="text" name="rate10" /></td>
	   			<td><input size="10" type="hidden" name="itemid10"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng10"/></td>
	   			<td><input size="10" type="text" name="itemchn10"/></td>
	   			<td><input size="10" type="text" name="quantity10"/></td>
	   			<td><select name="unit10" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice10" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice10"/></td>
	   			<td><input size="10" type="text" name="unitpriceall10" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark10" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw10" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination10"/></td>
	   			<td><input size="10" type="text" name="trueprice10" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode10"/></td>
	   			<td><input size="10" type="text" name="rate10"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid11")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng11" value="<%=request.getAttribute("itemeng11")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn11" value="<%=request.getAttribute("itemchn11")%>"/></td>
	   			<td><input size="10" type="text" name="quantity11"/></td>
	   			<td><select name="unit11" style="width: 99%;"><option <c:if test="${unit11 == '个'}">selected</c:if>>个</option><option <c:if test="${unit11 == '件'}">selected</c:if>>件</option><option <c:if test="${unit11 == '套'}">selected</c:if>>套</option><option <c:if test="${unit11 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice11" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice11"/></td>
	   			<td><input size="10" type="text" name="unitpriceall11" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark11" value="<%=request.getAttribute("shopingmark11")%>"/></td>
	   			<td><input size="10" type="text" name="nw11" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination11" value="<%=request.getAttribute("sourceDestination11")==null?"":request.getAttribute("sourceDestination11")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice11" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode11"/></td>
	   			<td><input size="10" type="text" name="rate11"/></td>
	   			<td><input size="10" type="hidden" name="itemid11" /></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng11"/></td>
	   			<td><input size="10" type="text" name="itemchn11"/></td>
	   			<td><input size="10" type="text" name="quantity11"/></td>
	   			<td><select name="unit11" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice11" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice11"/></td>
	   			<td><input size="10" type="text" name="unitpriceall11" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark11" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw11" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination11"/></td>
	   			<td><input size="10" type="text" name="trueprice11" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode11"/></td>
	   			<td><input size="10" type="text" name="rate11"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid12")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng12" value="<%=request.getAttribute("itemeng12")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn12" value="<%=request.getAttribute("itemchn12")%>"/></td>
	   			<td><input size="10" type="text" name="quantity12"/></td>
	   			<td><select name="unit12" style="width: 99%;"><option <c:if test="${unit12 == '个'}">selected</c:if>>个</option><option <c:if test="${unit12 == '件'}">selected</c:if>>件</option><option <c:if test="${unit12 == '套'}">selected</c:if>>套</option><option <c:if test="${unit12 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice12" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice12" /></td>
	   			<td><input size="10" type="text" name="unitpriceall12" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark12" value="<%=request.getAttribute("shopingmark12")%>"/></td>
	   			<td><input size="10" type="text" name="nw12" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination12" value="<%=request.getAttribute("sourceDestination12")==null?"":request.getAttribute("sourceDestination12")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice12" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode12"/></td>
	   			<td><input size="10" type="text" name="rate12"/></td>
	   			<td><input size="10" type="hidden" name="itemid12"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng12"/></td>
	   			<td><input size="10" type="text" name="itemchn12"/></td>
	   			<td><input size="10" type="text" name="quantity12"/></td>
	   			<td><select name="unit12" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice12" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice12"/></td>
	   			<td><input size="10" type="text" name="unitpriceall12" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark12" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw12" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination12"/></td>
	   			<td><input size="10" type="text" name="trueprice12" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode12"/></td>
	   			<td><input size="10" type="text" name="rate12"/></td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid13")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng13" value="<%=request.getAttribute("itemeng13")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn13" value="<%=request.getAttribute("itemchn13")%>"/></td>
	   			<td><input size="10" type="text" name="quantity13"/></td>
	   			<td><select name="unit13" style="width: 99%;"><option <c:if test="${unit13 == '个'}">selected</c:if>>个</option><option <c:if test="${unit13 == '件'}">selected</c:if>>件</option><option <c:if test="${unit13 == '套'}">selected</c:if>>套</option><option <c:if test="${unit13 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice13" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice13"/></td>
	   			<td><input size="10" type="text" name="unitpriceall13" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark13" value="<%=request.getAttribute("shopingmark13")%>"/></td>
	   			<td><input size="10" type="text" name="nw13" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination13" value="<%=request.getAttribute("sourceDestination13")==null?"":request.getAttribute("sourceDestination13")%>"/></td>
	   			<td><input size="10" type="text" name="trueprice13" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode13" value=""/></td>
	   			<td><input size="10" type="text" name="rate13" value=""/></td>
	   			<td><input size="10" type="hidden" name="itemid13" value=""/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng13"/></td>
	   			<td><input size="10" type="text" name="itemchn13"/></td>
	   			<td><input size="10" type="text" name="quantity13"/></td>
	   			<td><select name="unit13" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice13" class="export-cn1"/></td>
	   			<td><input size="10" type="text" name="unitprice13"/></td>
	   			<td><input size="10" type="text" name="unitpriceall13" class="unit-price-all"/></td>
	   			<td><input size="10" type="text" name="shopingmark13" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw13" /></td>
	   			<td><input size="10" type="text" name="sourceDestination13"/></td>
	   			<td><input size="10" type="text" name="trueprice13" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode13"/></td>
	   			<td><input size="10" type="text" name="rate13"/></td>
		   		<%} %>
	   		</tr>
	   	</table>
	   	<br/>
	   	指定货代(如果有)(用&ltbr&gt换行)：<textarea name="frieght"  cols="45" rows="5"><%=request.getAttribute("frieght")%></textarea>
	   	<br/>
	   		*物流填写：报关发票号：<input name="Nonum" type="text" value=""/>
	   	发票日期：<input name="date" type="text" onClick="WdatePicker()" value=""/></input>
	   	sailing Date:<input name="saildate" type="text" onClick="WdatePicker()" value=""/>
	   		<br/>
	   	货代:<input name="huodai" type="text" value="<%=request.getAttribute("huodai")%>"/>
	   	运费:<input name="yunfei" type="text"/>(* FOB 无运费)
	   	<br/>
   	 	运费方式 :<input type="radio" name="yunfeifs" value="COLLECT"/>到付
			<input type="radio" name="yunfeifs" value="PREPAID"/>预付
			保费:<input name="premium" type="text"/>
			<br/>
		   外销合同时间：<input id="contract_date" name="waixiaotime" type="text" onClick="WdatePicker()"/>
		   发货地址：<input type="radio" name="exportPlace" value="公司">公司
		        <input type="radio" name="exportPlace" value="仓库">仓库
		        <input type="radio" name="exportPlace" value="工厂">工厂

   	</table>
   <br/>
    </form>
   <form onsubmit="return false;" method="post" enctype="multipart/form-data">	   
	   <p>多合同多品类详情上传（Excel）：<input type="file" name="file" class="pull-left" onchange="upload(this)"></p>
   </form>
   <p style="color:red;">正式保存 多合同多品类请上传Excel</p>
   <input type="button" value="预保存" onclick="save_order(0)"></input>
   <input type="button" value="正式保存" onclick="save_order(1)"></input>
  
  </body>
  <script type="text/javascript">
				
				
		    window.onload = function(){
		    var checked = '<%=request.getAttribute("currency")%>'
		        var radios = document.getElementsByName('currency');
		        var len = radios.length;
		        for(var i=0;i<len;i++){
		            if(checked==radios[i].value){
		                radios[i].setAttribute('checked','checked');
		            }
		        }
		       var checkedss='<%=request.getAttribute("transaction2")%>';
		        var radioss = document.getElementsByName('transaction2');
		        var lens = radioss.length;
		        for(var j=0;j<lens;j++){
		            if(checkedss==radioss[j].value){
		                radioss[j].setAttribute('checked','checked');
		            }
		        }
		        var checkedfs='<%=request.getAttribute("yunfeifs")%>';
		        var radiofs = document.getElementsByName('yunfeifs');
		        var lenfs = radiofs.length;
		        for(var t=0;t<lenfs;t++){
		            if(checkedfs==radiofs[t].value){
		                radiofs[t].setAttribute('checked','checked');
		            }
		        }
		    }
		</script>
</html>
