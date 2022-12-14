<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="java.util.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'update.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<script type="text/javascript" src="${ctx}/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-1.4.2.min.js"></script>	
	<script type="text/javascript" src="${ctx}/js/jquery-form.js"></script>
	<style type="text/css">
	
	   #table1{
            border-collapse: collapse;
    		border-spacing: 0;
        }
       #table1 td{text-align:center;padding:5px 0;}
        #table1 input{ 
         width: 96%;
	    height: 100%;
	    min-height: 35px;       
       }
	       /*添加一行样式*/  
       .p_add{
        color:#ff4d09;
       }           
       .add{
            margin-left: 76%;
		    font-size: 20px;
		    font-weight: 700;
		    display: contents;
       }
        /*去除一行样式*/             
       .del{
/*             margin-left: 35px; */
		    font-size: 20px;
		    font-weight: 700;
       }
       .tan{position:fixed;top:200px;left:20%;background-color:rgba(0,0,0,.6);
       padding:8px 12px;z-index:10;color:#fff;display:none;}
	
	
	
	.reginon_con{border:1px solid #ddd;padding:10px;display: inline-block;}
	.add input{margin-right:15px;}
	.red{color:red;}
	.green{color:green;}
	ul{list-style: none;margin:0;padding:0;}
	em,i{font-style:normal;}
	.mt10{margin-top:10px;}
	.ml10{margin-left:10px;}
	.mr30{margin-right:30px;}
	.mb10{margin-bottom:10px;}
	</style>
	
    <script type="text/javascript">

	 var adminName =  "${adminName}";
    
     $(function(){  
    	 
 	         //页面加载时直接验证合同出运信息
 			//验证是否已经上传合同	
    	 if(adminName.toUpperCase() == 'FUN' || adminName.toUpperCase() == 'CANDY'){
    	 }else{
	 		    var haveBargain1 = true;
	 			var purno1 = "";
	 			$('#table1').find('tr').each(function(i){
	 				if(i>0){
	 					getDetailByProjectId($(this).find('.order-id'),2);
	 					purno1 = $(this).find('.order-id').val();
	 					haveBargain1 = $(this).find('td:last').find('input:last').val();
	 					if(!haveBargain1){
	 						showNotice(purno1+"未上传合同",2000);  
	 						return false;
	 					}
	 				}
	 			})    	 
    	 }
    	 
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
	 
	 
	     
	 
	 	 //验算出口人民币和采购价是否一致
	    function update_order(orderStatus){
    	 
         var exchangeFlag = true;                  
    	 if(adminName.toUpperCase() == 'FUN' || adminName.toUpperCase() == 'CANDY'){ 		 
  	    	$('.true-price').each(function(){
 	    		if(!($(this).val() == null || $(this).val() == '' || $(this).val() == undefined || $(this).val() == 0)){
 	    			var truePrice = $(this).val();
 	    			var colInput = $(this).parents('tr').find('.export-cn1').val();
 	    			
 	    			var currency = $('#select_currency').val();
    				var exchange = 0.0;
    				if(currency == 'USD'){
    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
    					if(exchange > 7){
    						alert('当前汇率比已大于7');  
    					}
    					exchange = Number(exchange/1.16).toFixed(2);
    					if(exchange > 7){
    						showNotice('退税后汇率比大于7，请验算',2000);  
    						exchangeFlag = false;
    						return false;
    					}			    					
    				}
    				if(currency == 'GBP'){
    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
    					if(exchange > 9){
    						alert('当前汇率比已大于9',2000);  
    					}
    					exchange = Number(exchange/1.16).toFixed(2);
    					if(exchange > 9){
    						showNotice('退税后汇率比大于9，请验算',2000);  
    						exchangeFlag = false;
    						return false;
    					}			  
    				}
    				if(currency == 'EUR'){
    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
    					if(exchange > 8){
    						alert('当前汇率比已大于8');  
    					}
    					exchange = Number(exchange/1.16).toFixed(2);
    					if(exchange > 8){
    						showNotice('退税后汇率比大于8，请验算',2000);  
    						exchangeFlag = false;
    						return false;
    					}			  
    				}
    				if(currency == 'AUD'){
    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
    					if(exchange > 5.5){
    						alert('当前汇率比已大于5.5'); 
    					}
    					exchange = Number(exchange/1.16).toFixed(2);
    					if(exchange > 5.5){
    						showNotice('退税后汇率比大于5.5，请验算',2000); 
    						exchangeFlag = false;
    						return false;
    					}			  
    				}	    				
 	    			
 	    		}
 	    	})
    		 
 	    	//验证汇率是否在范围中
 	    	if(!exchangeFlag){
 	    		return false;
 	    	}
 	 
    		 $('#order_form').submit();
    	 }else{
     	 
    		 //报关单需要客户公司名称
 	    	if(!$('#company_name').val()){
 	    		showNotice('请输入客户公司名称',2000); 
 				return false;
 	    	}
    		 
     	    //获取总个数
     	    var index = $('#table1').find('tr').length-1;
     	    $('#totalSize').val(index);
 	    	
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
 	    		showNotice('出口人民币和采购总价不一致！',2000); 
 	    		return false;
 	    	}	 
 	    	
 	    	$('.true-price').each(function(){
 	    		if(!($(this).val() == null || $(this).val() == '' || $(this).val() == undefined || $(this).val() == 0)){
 	    			
 	    			
 	    			
	 	    		    if(adminName.toUpperCase() == '施姐808' ||adminName.toUpperCase() == 'MANDY'||adminName.toUpperCase() == 'roseli'){
	 	    	   			    var truePrice = $(this).val();
	 	 	    			    var colInput = $(this).parents('tr').find('.export-cn1').val();
		 	    			    var currency = $('#select_currency').val();
			    				var exchange = 0.0;
			    				if(currency == 'USD'){
			    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
			    					if(exchange > 7){
			    						alert('当前汇率比已大于7'); 
			    					}
			    					exchange = Number(exchange/1.16).toFixed(2);
			    					if(exchange > 7){
			    						showNotice('退税后汇率比大于7，请验算',2000);
			    						exchangeFlag = false;
			    						return false;
			    					}			    					
			    				}
			    				if(currency == 'GBP'){
			    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
			    					if(exchange > 9){
			    						alert('当前汇率比已大于9');
			    					}
			    					exchange = Number(exchange/1.16).toFixed(2);
			    					if(exchange > 9){
			    						showNotice('退税后汇率比大于9，请验算',2000);
			    						exchangeFlag = false;
			    						return false;
			    					}			  
			    				}
			    				if(currency == 'EUR'){
			    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
			    					if(exchange > 8){
			    						alert('当前汇率比已大于8');
			    					}
			    					exchange = Number(exchange/1.16).toFixed(2);
			    					if(exchange > 8){
			    						showNotice('退税后汇率比大于8，请验算',2000);
			    						exchangeFlag = false;
			    						return false;
			    					}			  
			    				}
			    				if(currency == 'AUD'){
			    					exchange = Number(colInput)/Number(truePrice).toFixed(2);
			    					if(exchange > 5.5){
			    						alert('当前汇率比已大于5.5');
			    					}
			    					exchange = Number(exchange/1.16).toFixed(2);
			    					if(exchange > 5.5){
			    						showNotice('退税后汇率比大于5.5，请验算',2000);
			    						exchangeFlag = false;
			    						return false;
			    					}			  
			    				}	    				
	 	    		   }else{
	 	    			    var truePrice = $(this).val();
	 	 	    			var colInput = $(this).parents('tr').find('.export-cn1').val();
	 	 	    			var defaultVal = $(this).parents('tr').find('.export-cn1').attr('field');
	 	 	    			if(colInput != defaultVal){
	 	 	    				alert('采购价改变，请重新确认报关总价!');
	 	 	    				$(this).val(0);   				
	 	 	    			}
	 	    		   }
 	    		}
 	    	})
 	    
 	    	
     	//验算毛重是否大于净重
     	var totalGW = $('#totalGW').val();
     	var totalNW = $('#totalNW').val();
     	if(Number(totalGW) < Number(totalNW)){
     		showNotice('毛重不能小于净重！',2000);
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
     		showNotice('净重不相等！',2000);
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
 			    		showNotice('清关总价不等于数量*单价！',2000);
 			    		flag = false;
 			    		return false;
 			    	}	 	    
 	    	  }else{
 	    		  if(Number(calPrice).toFixed(0) != Number(unitPrice).toFixed(0)){
 			    		showNotice('清关总价不等于数量*单价！',2000);
 			    		flag = false;
 			    		return false;
 			    	}	 	    
 	    	  }    	  	  
 	      })  
 	    		    	
 	        if(!(flag)){
 	        	return false;	
 	        }
 	    
 		  //当输入起运和到港时间同步到CRM系统
 	    	var saildate = $('#saildate').val();
 	    	var arriveDate = $('#arriveDate').val();
 	    	var destinationPort = $('#destinationPort').val();
 	    	if(saildate != null && saildate != '' && saildate != undefined && arriveDate !=null && arriveDate != '' && arriveDate != undefined){
 	    		var orderIds = '';
 		    	$('.order-id').each(function(){
 		    		var order_id = $(this).val();
 		    		if(!(order_id == null || order_id == '' || order_id == undefined)){
 		    			var reg = /[a-zA-Z]/g;
 		    			order_id.replace(reg,"");
 		    			order_id = "SHS"+order_id.replace("合","");	    			
 		    			orderIds += order_id+',';
 		    		}
 		    	})
 		    	//同步运输数据
 		    	$.ajax({
 					type : "post",
 					datatype : "json",
 					url : "SyncShippingDateToCrmServlet",
 					data : {
 						"orderIds" : orderIds,
 						"saildate" : saildate,
 						"arriveDate" : arriveDate,
 						"destinationPort" : destinationPort
 					},
 					success : function(result) {
 						
 					},
 					error : function() {
 						
 					}
 				});
 	    	}           
 	    
 	    		    	
 	    	//验证汇率是否在范围中
 	    	if(!exchangeFlag){
 	    		return false;
 	    	}
 	    	 	    	
 	    	if(adminName.toUpperCase() != '施姐808' && adminName.toUpperCase() != 'MANDY'  && adminName.toUpperCase() != 'roseli'){
 	    		//验证需要提供电子出货确认单
 	 	    	var id = $('#proId').val();
 	    		var isShipingFlag = true;
 		        if(id > 18061){
 		        	var tl = $('#div_list').find('p').length;
 		        	var li = tl;
 		        	$('.order-id').each(function(){
 		        		 var orderId = $(this).val();
 		        		 var reg = new RegExp("[a-zA-Z]","g");
 		        		 orderId = orderId.replace(reg,"").replace("合","");
 		        		 orderId = $.trim(orderId);
 		        		 //获取录入目的
 		        		 var isExtraInvoice = $(this).parents('tr').find('input[type="radio"]:checked').val();
 		        		 
 		        		 //判断是否存在该合同
 		        		 var isContract = false;
 		        		 for(var i=0;i<li;i++){
 		        			var projectNo = $('#div_list').find('p').eq(i).find('a').text(); 
//  		        			projectNo = projectNo.replace("SHS","");
 		        			if(projectNo.indexOf(orderId) != -1){
 		        				isContract = true;
 		        			}
 		        		 }	
	        			 if(isExtraInvoice == 1){
		        			isContract = true;
		        		 }
 		        		 if(!isContract){
 		        			isShipingFlag = false;
 		        		 }		        		 		        		 
 		        	})
 		        	
 		        	if(!isShipingFlag){
 		        		showNotice('请录入每个合同准予电子出货单号',4000);
 		        		return false;
 		        	}	        	
 		        }
 		        
 		        
 		       if($('#contract_date').val() == null || $('#contract_date').val() == ''){
 	 	        	showNotice('请输入外销合同时间',2000);
 	 	        	return false;
 	 	        }
 	 	    	
 	 	    	
 	 	        //判断运输国家是否为空
 	 	        var destinationPort = $('#destinationPort').val();
 	 	        if(destinationPort == null || destinationPort == ''){
 	 	        	showNotice('出运国家不能为空',2000);
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
 		        
 		        //查看是否有发货地址
 		        var exportPlace = $('input[name="exportPlace"]:checked').val();
 		        if(!exportPlace){
 		        	showNotice('发货地址不能为空',2000);
 		        	return false;
 		        }
 		        
 		        //普通用户才能修改报关状态（预保存0或者正式保存1）
 		        $('#orderStatus').val(orderStatus);
 		        //正式保存时，判断是否上传多品类excel
 		        var fileName = $('#fileName').val();
 		        if(!checkProduct() && !fileName && orderStatus == 1){
 		        	showNotice('多合同多品类，请上传详情excel',2000);
 		        	return false;
 		        } 
 	    	}	    	
 	    	
 	    	
 	    	$('#order_form').submit();   	
    	 }
    	  
	    
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
	    
	    
	    
	  //根据合同号获取出货，工厂信息
	    function getDetailByProjectId(obj,status){
		  
    	 if(adminName.toUpperCase() == 'FUN' || adminName.toUpperCase() == 'CANDY'){
    		 return false;
    	 }
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

	   				$(obj).parents('tr').find('td:last').find('input:last').val(dataObj.haveBargain);
	   				if(dataObj.haveBargain == false){
	   					$(obj).parents('tr').find('td:eq(6)').find('span:eq(0)').html('合同未上传');
	   					showNotice('未查询到合同'+purno+',请填写正确格式',2000);
	   					return false;
	   				}	   				
	   				
	   				$(obj).parents('tr').find('td:eq(6)').find('span:eq(0)').text(dataObj.times == 0 ? '还未出货' : '已出货'+dataObj.times+'次');
	   				$(obj).parents('tr').find('td:eq(6)').find('span:eq(1)').text('合同总金额:'+dataObj.totalPrice);
	   				$(obj).parents('tr').find('td:eq(6)').find('span:eq(2)').text('已出货金额:'+dataObj.totalPay);	
	   				
	   				
	   				if(status == 1){
		   				$(obj).parents('tr').find('td:eq(1)').find('input').val(dataObj.factoryName);
		   				$(obj).parents('tr').find('td:eq(2)').find('input').val(dataObj.totalPrice);
// 	   					if(Number(dataObj.totaltimes) > Number(dataObj.times)){
// 	   						$(obj).parents('tr').find('td:eq(3)').find('input').val(Number(dataObj.times)+1);
// 	   					}
// 	   					$(obj).parents('tr').find('td:eq(4)').find('input').val(dataObj.totaltimes == '0' ? 1 : dataObj.totaltimes);
// 	   					$(obj).parents('tr').find('td:eq(5)').find('input').val(dataObj.balancePrice);
	   					
		   				$(obj).parents('tr').find('td:eq(6)').find('span:eq(3)').text('未出货金额:'+dataObj.balancePrice);
		   				//如果已出货完成给予提醒
		   				if(dataObj.balancePrice == 0){
		   					showNotice(purno+'已出完货,请确认',2000);
		   					return false;
		   				}
	   				}			
			

	   				
	   			},
	   			error : function() {
	                  
	   			}
	   		});		
	    }
	    
	    //合同输入table新增一行
	       function addTr(){    	   
	    	  $('#table1').append($('#table1').find('tr:last').clone());
	    	  $('#table1').find('tr:last').find('td:eq(6)').find('span').text('');	    	  
	    	  var index = $('#table1').find('tr').length-1;
	    	  $('#table1').find('tr:last').find('input').each(function(){	    	 		 
	    		   var name = $(this).attr('name');
	    		   var num = name.replace(/[^0-9]/g, "");
	    		   name = name.replace(num,index);
	    		   $(this).attr('name',name);
	    		   if($(this).attr('type') != 'radio'){
	    			   $(this).val('');
	    		   }else{
	    			   if($(this).is(":checked")){
	    				   var $val = $(this).val();
	    				   $('#table1').find('input[name="isExtraInvoice'+(index-1)+'"][value="'+$val+'"]').attr('checked','true');
	    			   }
	    		   }   	    		   
	    	  })
	    	  $('#table1').find('tr:last').find('td:last').find('input:first').val('');
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
	 
	       //运费平均加法
	       //用于运费保费根据采购金额平均分配，计算新的出口总金额
	       function calExportAmount(obj,type){
	    	   
	    	   if(adminName.toUpperCase() == 'FUN' || adminName.toUpperCase() == 'CANDY'){
	    		   var a = Number($(obj).val());  //运费或者保费价格	    	   
		    	   //获取采购总价
		    	   var totalPurchasePrice = 0;
		    	   $('.export-cn1').each(function(){
		 	    		var purchasePrice = $(this).val();
		 	   	    	if(isNaN(purchasePrice) || purchasePrice == "" || purchasePrice == undefined){
		 	   	    		return true;
		 	   	    	}else{
		 	   	    		totalPurchasePrice +=Number(purchasePrice);
		 	   	    	}	   	    
		 	    	})
		 	    	
		 	    	 //算出运费保费比例价格并且重新赋值
		 	    	 $('.export-cn1').each(function(){
		 	    		 var addAmount = 0;
		 	    		 var purchasePrice = $(this).val();
		 	    		 var rate = 0.0;
		 	    		 if(purchasePrice){
		 	    			purchasePrice = Number(purchasePrice);
		 	    			rate = purchasePrice/totalPurchasePrice;
		 	    			addAmount = Number(a*purchasePrice/totalPurchasePrice).toFixed(2);
		 	    			var id = $(this).parents('tr').find('.true-price').next().val();
		 	    			getPriceById(id,addAmount,$(this),type,rate);
		 	    		 }
		 	    	 })
	    	   }	    	
	       }
	       
	       
	       //根据id获取价格
	       function getPriceById(id,addAmount,obj,type,rate){
	    	   $.ajax({
		   			type : "post",
		   			datatype : "json",
		   			async : true, 	
		   			url : "GetProductExportPriceServlet",
		   			data : {
		   				"id" : id
		   			},
		   			success : function(result) {
		   				var data = eval("("+result+")");	
		   				var price = data.trueprice;
		   				var ori_premium = 0;
		   				var ori_shippingFee = 0;
		   				if($('#shipping_fee').attr('field')){
		   					ori_shippingFee = $('#shipping_fee').attr('field');
		   				}
		   				if($('#premium').attr('field')){
		   					ori_premium = $('#premium').attr('field');
		   				}
		   				
		   				var premium = 0;
		   				var shippingFee = 0;
		   				//1:运费失焦事件，加上保费
		   				//2：保费失焦事件，加上运费
		   				if(type == 1){
		   					var str = $('#premium').val();
		   					if(str){
		   						premium = Number(str*rate).toFixed(2);
		   					}
		   					ori_shippingFee = Number(ori_shippingFee*rate).toFixed(2);
		   				}else if(type == 2){
		   					var str = $('#shipping_fee').val();
		   					if(str){
		   						shippingFee = Number(str*rate).toFixed(2);		   						
		   					}
	   						ori_premium = Number(ori_premium*rate).toFixed(2);
		   				}
		   				price = Number(price) + Number(addAmount) + Number(shippingFee) + Number(premium) - Number(ori_premium)- Number(ori_shippingFee);
		   				price = Number(price).toFixed(2);
	 	    			$(obj).parents('tr').find('.true-price').val(price);
		   			},
		   			error : function() {
		                  
		   			}
		   		});		
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
	       			url: "UploadExcelServlet",
	       	     	dataType: "text",
	       	     	success: function(str){
		       	     	var result = eval('(' + str + ')');		
		       	     	var fileName = result.fileName;  
		       		    $('#fileName').val(fileName); 		
		       		 if(fileName!=null&&fileName!=""){
		       		    	showNotice('上传成功',2000);
		       		   }else{
		       		     showNotice('上传失败，请仔细阅读提醒',2000);
		       		    }
		       		    //$(obj).val('');
	       	     	},
	       			error: function(){
	       				showNotice('上传失败',2000);
	       			    $(obj).val('');
	       			}       	     	
	       	 	}); 	 		    

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
	       
	      
	      //判断多品类多合同是否上传表格
	      $(function(){
	    	    var fileName = $('#fileName').val();
		        if(!checkProduct() && !fileName){
		        	$('#show').show();
		        }   
	      })
	      
	      
	      
	      //上传项目、品名
	       function uploadImg(obj){	
	       		var path = $(obj).val();
	       	    sppath = path.split("\\");
	       	    var fileName = sppath[sppath.length-1];	  	   
	       	    if(fileName == null || fileName == '' || fileName == undefined){
	       	    	return false;
	       	    }else{
	      	        var gen = fileName.lastIndexOf("."); 
	       	        var type = fileName.substr(gen); 
	       	        if(".jpg|.png|.bmp|.jpeg".toUpperCase().indexOf(type.toUpperCase())==-1){
	       	            showNotice('请上传jpg、png、bmp、jpeg格式的图片',2000);
	       	            return false;
	       	        }
	       	    }                     		      		  
	       		 //先上传后获取上传文件路径
	       		 $(obj).parents('form').ajaxSubmit({
	       			type: "post",
	       			url: "UploadInvoiceImgServlet",
	       	     	dataType: "text",
	       	     	success: function(str){
		       	     	var result = eval('(' + str + ')');		
		       	     	var fileName = result.fileName;  
		       		    $(obj).parents('form').find('input[name="filename"]').val(fileName);	
		       		    showNotice('上传成功',2000);
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
  </script>
  </head>
  <body>
  <div class="tan"></div>	
  <h1>修改出运单</h1><%--<h3 id="show" style="text-align: center;color: red;display:none;">注意：属于多合同、多品类报关，您还未上传品类Excel</h3>--%>
   <form action="UpdateServlet" method="post" id="order_form">
   	<table border="1">
   	  <tr>
	   	  <td>
	   	    <input type="hidden" name="orderStatus" id="orderStatus" value="<%=request.getAttribute("orderStatus")%>">
	   	    <input type="hidden" name="fileName" id="fileName" value="<%=request.getAttribute("excelPath")%>">
	   	    <input type="hidden" id="adminName" name="adminName" value="${adminName}">
	   	    <input type="hidden" id="totalSize" name="totalSize" value="<%=Integer.parseInt(request.getAttribute("totalSize").toString())%>">
	   	    <input type="hidden" id="select_currency"  value="${currency}">
	   		<input type="text" name="id" style="display:none;" value="<%=request.getAttribute("id")%>"/>
	   		采购：<input name="purchase" type="text" value="<%=request.getAttribute("purchase")%>"/>
	   		销售：<input name="sale" type="text" value="<%=request.getAttribute("sale")%>"/>
	   		客户名：<input name="clientName" type="text" value="<%=request.getAttribute("clientName")%>"/>
	   		客户公司名称：<input style="width: 294px;" name="companyName" id="company_name" type="text" value="<%=request.getAttribute("companyName")%>"/>
		   	<br/>
		   	<c:if test="${attrSource == 1 && sessionScope.auth == 1}">
		   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
		   	<br/>
		   	</c:if>
		   	<c:if test="${attrSource == 0}">
		   	收货人地址(用&ltbr&gt换行)：<textarea name="address" cols="45" rows="5"><%=request.getAttribute("address")%></textarea>
		   	<br/>
		   	</c:if>
		   	</td>
	  </tr>
	</table>
	   	<table border="1" id="table1">
	   		<tr>
	   			<td>采购合同号(格式：合10000-1A)</td>
	   			<td>工厂名称</td>
	   			<td>合同金额(格式：100000.00)</td>
	   			<td>第几次出货批次</td>
	   			<td>总共几次出货批次</td>
	   			<td>本次出口人民币金额(格式：100000.00)</td>
	   			<td>参考数据</td>
	   			<td>录入目的</td>
	   		</tr>	 
	   			
   					<%
   				    Integer totalSize = Integer.parseInt(request.getAttribute("totalSize").toString());
   					if(totalSize != 0){
   			        for(int i=0;i<totalSize;i++){
   			    	%>
   			    	 <tr>
	   			    	<td><input type="text" name="purno<%=i+1%>" class="order-id" value="<%=request.getAttribute("purno"+(i+1))%>" onblur="getDetailByProjectId(this,1)"/></td>
			   			<td><input type="text" name="factory<%=i+1%>" value="<%=request.getAttribute("factory"+(i+1))%>"/></td>
			   			<td><input type="text" name="money<%=i+1%>" value="<%=request.getAttribute("money"+(i+1))%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
			   			<td><input type="text" name="times<%=i+1%>" value="<%=request.getAttribute("times"+(i+1))%>"/></td>
			   			<td><input type="text" name="totaltimes<%=i+1%>" value="<%=request.getAttribute("totaltimes"+(i+1))%>"/></td>
			   			<td><input type="text" name="rmb<%=i+1%>" class="export-cn" value="<%=request.getAttribute("rmb"+(i+1))%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
			   			<td style="color:#1605f3"><span style="padding-right: 5px;"></span><span></span><br><span></span><span></span></td>
			   			<td><input type="radio" style="width: auto;" name="isExtraInvoice<%=i+1%>" value="0" 
			   			    <%
			   			     if("0".equals(request.getAttribute("isExtraInvoice"+(i+1)))){
			   			    %>
			   			    checked
			   			    <%	 
			   			     }
			   			    %>/>正常<input type="radio" style="width: auto;" name="isExtraInvoice<%=i+1%>" value="1"
			   			     <%
			   			     if("1".equals(request.getAttribute("isExtraInvoice"+(i+1)))){
			   			     %>
			   			     checked
			   			     <%	 
			   			     }
			   			     %>
			   			    />带票</td>  
			   			<td><input type="hidden" name="conid<%=i+1%>" value="<%=request.getAttribute("conid"+(i+1))%>"/><input type="hidden"/></td>  
	   			     </tr>
	   			    <% 	  
	   			      }
   					}else{
   					%>	
   					<tr>
	   			    	<td><input type="text" name="purno1" class="order-id" onblur="getDetailByProjectId(this,1)"/></td>
			   			<td><input type="text" name="factory1"/></td>
			   			<td><input type="text" name="money1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
			   			<td><input type="text" name="times1"/></td>
			   			<td><input type="text" name="totaltimes1"/></td>
			   			<td><input type="text" name="rmb1" class="export-cn"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
			   			<td style="color:#1605f3"><span style="padding-right: 5px;"></span><span></span><br><span></span><span></span></td>
			   			<td><input type="radio" style="width: auto;" name="isExtraInvoice1" value="0" />正常<input type="radio" name="isExtraInvoice1" value="1"/>带票</td>  
			   			<td><input type="hidden" style="width: auto;" name="conid1"/><input type="hidden"/></td>  
	   			     </tr>
   					<% 	
   					}
	   				%>	
	   			
	   		
	   	</table>
	   	<p class="p_add"><span class="add" onclick="addTr()">+</span><span onclick="addTr()">(添加一行)</span></p>
	   	希望出口日期:<input name="hopeDate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("hopeDate")%>"/>
	   	预计工厂可送仓日期:<input name="estimateDate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("estimateDate")%>"/>
	   	<br/>
	   	
	   	交易方式 Term:<input name="transaction1" type="text" value="<%=request.getAttribute("transaction1")%>"/>
	   	货运方式 :<input type="radio" name="transaction2" value="海运">海运
				<input type="radio" name="transaction2" value="陆运">陆运
				<input type="radio" name="transaction2" value="空运">空运
				<input type="radio" name="transaction2" value="快递">快递
	   	<br/>
	   	总体积(CBM):<input name="volume" type="text" value="<%=request.getAttribute("volume")%>"/>
	   	总毛重 G.W(KGS):<input name="totalGW" type="text" id="totalGW" value="<%=request.getAttribute("totalGW")%>"/>
	   	总净重 N.W(KGS): <input name="totalNW" type="text" id="totalNW" value="<%=request.getAttribute("totalNW")%>"/>
	   	<br/>
	   	
	   	From:<input name="fromwhere" type="text" value="<%=request.getAttribute("fromwhere")%>"/>
	   	To:<input name="towhere" id="destinationPort" type="text" value="<%=request.getAttribute("towhere")%>"/>
	   	<br/>
	   	Package 包装类型:<input name="package" type="text" value="<%=request.getAttribute("package")%>"/>
	   	Package 包装数量:<input name="packageNum" type="text" value="<%=request.getAttribute("packageNum")%>"/>
	   	<br/>
	   	支付币种：
	   	        
	   	        <input type="radio" name="currency" value="USD" onchange="confirm_currency(this)">美元
				<input type="radio" name="currency" value="EUR" onchange="confirm_currency(this)">欧元
				<input type="radio" name="currency" value="GBP" onchange="confirm_currency(this)">英镑
				<input type="radio" name="currency" value="RMB" onchange="confirm_currency(this)">人民币
				<input type="radio" name="currency" value="AUD" onchange="confirm_currency(this)">澳元
	   	<br/>
	   
	   	<br/>
	    托盘/木箱尺寸：<input name="palletDimension" type="text" value="<%=request.getAttribute("palletDimension")%>">	<br/>
	   小箱件数(美国货必填)：<input name="casketQuantity" type="number" value="<%=request.getAttribute("casketQuantity")%>">	<br/>
	   订框箱型：<select name="casketType">
			   <option></option>
			   <option <c:if test="${casketType eq '40尺高柜'}">selected</c:if>>40尺高柜</option>
			   <option <c:if test="${casketType eq '40尺平柜'}">selected</c:if>>40尺平柜</option>
			   <option <c:if test="${casketType eq '20尺柜'}">selected</c:if>>20尺柜</option>
			   <option <c:if test="${casketType eq '拼箱'}">selected</c:if>>拼箱</option>
			   <option <c:if test="${casketType eq '特殊柜'}">selected</c:if>>特殊柜</option>
			 </select>  
	 <br/><br/>
	  特殊要求备注(用&ltbr&gt换行)：<textarea name="detailed" cols="45" rows="5"><%=request.getAttribute("detailed")%></textarea>
	   	<br/>
	   	<table border="1" id="item_table" style="table-layout:fixed;">
	   		<tr>
	   			<td width="20px"  style="word-wrap:break-word;" >Item英文名</td>
	   			<td width="20px"  style="word-wrap:break-word;" >Item中文名</td>
			    <td width="10px"  style="word-wrap:break-word;" >Quantity(请只填数字)</td>
			    <td width="50px"  style="word-wrap:break-word;" >数量单位</td>
			    <td width="50px"  style="word-wrap:break-word;" ><strong>采购价 总价(只填数字 单位:RMB)(格式：100000.00)</strong></td>
			    <td width="40px"  style="word-wrap:break-word;" >Unit Price(对外销售单价)</td>
			    <td width="50px"  style="word-wrap:break-word;" >(客户)清关总价(格式：100000.00)</td>
			    <td width="50px">Shipping Mark</td>
			    <td width="50px"><p>N.W.(请只填数字 单位:kg)</p></td>
			    <td width="50px"  style="word-wrap:break-word;" >境内货源地</td>
			    <td width="50px"  style="word-wrap:break-word;" >实际报关总价(会计填)(格式：100000.00)</td>
			    <td width="50px">HS Code (物流填)</td>
			    <td width="50px">退税率 (物流填 *%)</td>
			    <td width="50px">合同号</td>
			    <td width="50px">工厂名</td>
			    <td width="50px">报关数量</td>
			    <td width="50px">报关金额</td>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid1")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng1" value="<%=request.getAttribute("itemeng1")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn1" value="<%=request.getAttribute("itemchn1")%>"/></td>
	   			<td><input size="10" type="text" name="quantity1" value="<%=request.getAttribute("quantity1")%>"/></td>
	   			<td><select name="unit1" style="width: 99%;"><option <c:if test="${unit1 == '个'}">selected</c:if>>个</option><option <c:if test="${unit1 == '件'}">selected</c:if>>件</option><option <c:if test="${unit1 == '套'}">selected</c:if>>套</option><option <c:if test="${unit1 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice1")%>" type="text" name="purprice1" class="export-cn1" value="<%=request.getAttribute("purprice1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice1" value="<%=request.getAttribute("unitprice1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall1" class="unit-price-all" value="<%=request.getAttribute("unitpriceall1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark1" value="<%=request.getAttribute("shopingmark1")%>"/></td>
	   			<td><input size="10" type="text" name="nw1" class="n_weight" value="<%=request.getAttribute("nw1")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination1" value="<%=request.getAttribute("sourceDestination1")==null?"":request.getAttribute("sourceDestination1")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice1" class="true-price" value="<%=request.getAttribute("trueprice1")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')" <c:if test="${sessionScope.auth != 1}">readonly</c:if>/><input type="hidden" value="<%=request.getAttribute("itemid1")%>"/></td>
	   			<td><input size="20" type="text" name="hscode1" value="<%=request.getAttribute("hscode1")%>"/></td>
	   			<td><input size="10" type="text" name="rate1" value="<%=request.getAttribute("rate1")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid1" value="<%=request.getAttribute("itemid1")%>"/></td>
				<td><input name=""></td>
				<td><input name=""></td>
				<td><input name=""></td>
				<td><input name=""></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng1"/></td>
	   			<td><input size="10" type="text" name="itemchn1"/></td>
	   			<td><input size="10" type="text" name="quantity1"/></td>
	   			<td><select name="unit1" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice1" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall1" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark1" value="N/M"/></td>
	   			<td><input size="10" type="text" class="n_weight" name="nw1"/></td>
	   			<td><input size="10" type="text" name="sourceDestination1" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice1" class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity2" value="<%=request.getAttribute("quantity2")%>"/></td>
	   			<td><select name="unit2" style="width: 99%;"><option <c:if test="${unit2 == '个'}">selected</c:if>>个</option><option <c:if test="${unit2 == '件'}">selected</c:if>>件</option><option <c:if test="${unit2 == '套'}">selected</c:if>>套</option><option <c:if test="${unit2 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice2")%>" type="text" name="purprice2" class="export-cn1" value="<%=request.getAttribute("purprice2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice2" value="<%=request.getAttribute("unitprice2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all" value="<%=request.getAttribute("unitpriceall2")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark2" value="<%=request.getAttribute("shopingmark2")%>"/></td>
	   			<td><input size="10" type="text" name="nw2" class="n_weight" value="<%=request.getAttribute("nw2")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination2" value="<%=request.getAttribute("sourceDestination2")==null?"":request.getAttribute("sourceDestination2")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice2" class="true-price" value="<%=request.getAttribute("trueprice2")%>" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid2")%>"/></td>
	   			<td><input size="20" type="text" name="hscode2" value="<%=request.getAttribute("hscode2")%>"/></td>
	   			<td><input size="10" type="text" name="rate2" value="<%=request.getAttribute("rate2")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid2" value="<%=request.getAttribute("itemid2")%>"/></td>

		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng2"/></td>
	   			<td><input size="10" type="text" name="itemchn2"/></td>
	   			<td><input size="10" type="text" name="quantity2"/></td>
	   			<td><select name="unit2" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice2" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice2" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall2" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark2" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw2" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination2" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice2" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity3" value="<%=request.getAttribute("quantity3")%>"/></td>
	   			<td><select name="unit3" style="width: 99%;"><option <c:if test="${unit3 == '个'}">selected</c:if>>个</option><option <c:if test="${unit3 == '件'}">selected</c:if>>件</option><option <c:if test="${unit3 == '套'}">selected</c:if>>套</option><option <c:if test="${unit3 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice3")%>" type="text" name="purprice3" class="export-cn1" value="<%=request.getAttribute("purprice3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice3" value="<%=request.getAttribute("unitprice3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall3" class="unit-price-all" value="<%=request.getAttribute("unitpriceall3")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark3" value="<%=request.getAttribute("shopingmark3")%>"/></td>
	   			<td><input size="10" type="text" name="nw3" value="<%=request.getAttribute("nw3")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination3" value="<%=request.getAttribute("sourceDestination3")==null?"":request.getAttribute("sourceDestination3")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice3" class="true-price" value="<%=request.getAttribute("trueprice3")%>" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid3")%>"/></td>
	   			<td><input size="20" type="text" name="hscode3" value="<%=request.getAttribute("hscode3")%>"/></td>
	   			<td><input size="10" type="text" name="rate3" value="<%=request.getAttribute("rate3")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid3" value="<%=request.getAttribute("itemid3")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng3"/></td>
	   			<td><input size="10" type="text" name="itemchn3"/></td>
	   			<td><input size="10" type="text" name="quantity3"/></td>
	   			<td><select name="unit3" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice3" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice3" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall3" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark3" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw3" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination3" class="sourceDestination"/></td>   			
	   			<td><input size="10" type="text" name="trueprice3" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity4" value="<%=request.getAttribute("quantity4")%>"/></td>
	   			<td><select name="unit4" style="width: 99%;"><option <c:if test="${unit4 == '个'}">selected</c:if>>个</option><option <c:if test="${unit4 == '件'}">selected</c:if>>件</option><option <c:if test="${unit4 == '套'}">selected</c:if>>套</option><option <c:if test="${unit4 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice4")%>" type="text" name="purprice4" class="export-cn1" value="<%=request.getAttribute("purprice4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice4" value="<%=request.getAttribute("unitprice4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall4" class="unit-price-all" value="<%=request.getAttribute("unitpriceall4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark4" value="<%=request.getAttribute("shopingmark4")%>"/></td>
	   			<td><input size="10" type="text" name="nw4" class="n_weight" value="<%=request.getAttribute("nw4")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination4" value="<%=request.getAttribute("sourceDestination4")==null?"":request.getAttribute("sourceDestination4")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice4" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice4")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid4")%>"/></td>
	   			<td><input size="20" type="text" name="hscode4" value="<%=request.getAttribute("hscode4")%>"/></td>
	   			<td><input size="10" type="text" name="rate4" value="<%=request.getAttribute("rate4")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid4" value="<%=request.getAttribute("itemid4")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng4"/></td>
	   			<td><input size="10" type="text" name="itemchn4"/></td>
	   			<td><input size="10" type="text" name="quantity4"/></td>
	   			<td><select name="unit4" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice4" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice4" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall4" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark4" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw4" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination4" class="sourceDestination"/></td>	   		
	   			<td><input size="10" type="text" name="trueprice4" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity5" value="<%=request.getAttribute("quantity5")%>"/></td>
	   			<td><select name="unit5" style="width: 99%;"><option <c:if test="${unit5 == '个'}">selected</c:if>>个</option><option <c:if test="${unit5 == '件'}">selected</c:if>>件</option><option <c:if test="${unit5 == '套'}">selected</c:if>>套</option><option <c:if test="${unit5 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice5")%>" type="text" name="purprice5" class="export-cn1" value="<%=request.getAttribute("purprice5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice5" value="<%=request.getAttribute("unitprice5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall5" class="unit-price-all" value="<%=request.getAttribute("unitpriceall5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid5")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark5" value="<%=request.getAttribute("shopingmark5")%>"/></td>
	   			<td><input size="10" type="text" name="nw5" value="<%=request.getAttribute("nw5")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination5" value="<%=request.getAttribute("sourceDestination5")==null?"":request.getAttribute("sourceDestination5")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice5" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice5")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode5" value="<%=request.getAttribute("hscode5")%>"/></td>
	   			<td><input size="10" type="text" name="rate5" value="<%=request.getAttribute("rate5")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid5" value="<%=request.getAttribute("itemid5")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng5"/></td>
	   			<td><input size="10" type="text" name="itemchn5"/></td>
	   			<td><input size="10" type="text" name="quantity5"/></td>
	   			<td><select name="unit5" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice5" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice5" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall5" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark5" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw5" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination5" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice5" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity6" value="<%=request.getAttribute("quantity6")%>"/></td>
	   			<td><select name="unit6" style="width: 99%;"><option <c:if test="${unit6 == '个'}">selected</c:if>>个</option><option <c:if test="${unit6 == '件'}">selected</c:if>>件</option><option <c:if test="${unit6 == '套'}">selected</c:if>>套</option><option <c:if test="${unit6 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice6")%>" type="text" name="purprice6" class="export-cn1" value="<%=request.getAttribute("purprice6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice6" value="<%=request.getAttribute("unitprice6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall6" class="unit-price-all" value="<%=request.getAttribute("unitpriceall6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid6")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark6" value="<%=request.getAttribute("shopingmark6")%>"/></td>
	   			<td><input size="10" type="text" name="nw6" class="n_weight" value="<%=request.getAttribute("nw6")%>"/></td>
	   			<td><input size="10" type="text" name="sourceDestination6" value="<%=request.getAttribute("sourceDestination6")==null?"":request.getAttribute("sourceDestination6")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice6" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice6")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode6" value="<%=request.getAttribute("hscode6")%>"/></td>
	   			<td><input size="10" type="text" name="rate6" value="<%=request.getAttribute("rate6")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid6" value="<%=request.getAttribute("itemid6")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng6"/></td>
	   			<td><input size="10" type="text" name="itemchn6"/></td>
	   			<td><input size="10" type="text" name="quantity6"/></td>
	   			<td><select name="unit6" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice6" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice6" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall6" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark6" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw6" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination6" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice6" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity7" value="<%=request.getAttribute("quantity7")%>"/></td>
	   			<td><select name="unit7" style="width: 99%;"><option <c:if test="${unit7 == '个'}">selected</c:if>>个</option><option <c:if test="${unit7 == '件'}">selected</c:if>>件</option><option <c:if test="${unit7 == '套'}">selected</c:if>>套</option><option <c:if test="${unit7 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice7")%>" type="text" name="purprice7" class="export-cn1" value="<%=request.getAttribute("purprice7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice7" value="<%=request.getAttribute("unitprice7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall7" class="unit-price-all" value="<%=request.getAttribute("unitpriceall7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid7")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark7" value="<%=request.getAttribute("shopingmark7")%>"/></td>
	   			<td><input size="10" type="text" name="nw7" value="<%=request.getAttribute("nw7")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination7" value="<%=request.getAttribute("sourceDestination7")==null?"":request.getAttribute("sourceDestination7")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice7" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice7")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode7" value="<%=request.getAttribute("hscode7")%>"/></td>
	   			<td><input size="10" type="text" name="rate7" value="<%=request.getAttribute("rate7")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid7" value="<%=request.getAttribute("itemid7")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng7"/></td>
	   			<td><input size="10" type="text" name="itemchn7"/></td>
	   			<td><input size="10" type="text" name="quantity7"/></td>
	   			<td><select name="unit7" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice7" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice7" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall7" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark7" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw7" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination7" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice7" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity8" value="<%=request.getAttribute("quantity8")%>"/></td>
	   			<td><select name="unit8" style="width: 99%;"><option <c:if test="${unit8 == '个'}">selected</c:if>>个</option><option <c:if test="${unit8 == '件'}">selected</c:if>>件</option><option <c:if test="${unit8 == '套'}">selected</c:if>>套</option><option <c:if test="${unit8 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice8")%>" type="text" name="purprice8"  class="export-cn1" value="<%=request.getAttribute("purprice8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice8" value="<%=request.getAttribute("unitprice8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall8" class="unit-price-all" value="<%=request.getAttribute("unitpriceall8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid8")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark8" value="<%=request.getAttribute("shopingmark8")%>"/></td>
	   			<td><input size="10" type="text" name="nw8" value="<%=request.getAttribute("nw8")%>" class="n_weight"/></td>
                <td><input size="10" type="text" name="sourceDestination8" value="<%=request.getAttribute("sourceDestination8")==null?"":request.getAttribute("sourceDestination8")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice8" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice8")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode8" value="<%=request.getAttribute("hscode8")%>"/></td>
	   			<td><input size="10" type="text" name="rate8" value="<%=request.getAttribute("rate8")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid8" value="<%=request.getAttribute("itemid8")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng8"/></td>
	   			<td><input size="10" type="text" name="itemchn8"/></td>
	   			<td><input size="10" type="text" name="quantity8"/></td>
	   			<td><select name="unit8" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice8" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice8" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall8" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark8" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw8" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination8" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice8" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity9" value="<%=request.getAttribute("quantity9")%>"/></td>
	   			<td><select name="unit9" style="width: 99%;"><option <c:if test="${unit9 == '个'}">selected</c:if>>个</option><option <c:if test="${unit9 == '件'}">selected</c:if>>件</option><option <c:if test="${unit9 == '套'}">selected</c:if>>套</option><option <c:if test="${unit9 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice9")%>" type="text" name="purprice9" class="export-cn1" value="<%=request.getAttribute("purprice9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice9" value="<%=request.getAttribute("unitprice9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall9" class="unit-price-all" value="<%=request.getAttribute("unitpriceall9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid9")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark9" value="<%=request.getAttribute("shopingmark9")%>"/></td>
	   			<td><input size="10" type="text" name="nw9" value="<%=request.getAttribute("nw9")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination9" value="<%=request.getAttribute("sourceDestination9")==null?"":request.getAttribute("sourceDestination9")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice9" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice9")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode9" value="<%=request.getAttribute("hscode9")%>"/></td>
	   			<td><input size="10" type="text" name="rate9" value="<%=request.getAttribute("rate9")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid9" value="<%=request.getAttribute("itemid9")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng9"/></td>
	   			<td><input size="10" type="text" name="itemchn9"/></td>
	   			<td><input size="10" type="text" name="quantity9"/></td>
	   			<td><select name="unit9" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice9" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice9" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall9" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark9" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw9" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination9" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice9" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity10" value="<%=request.getAttribute("quantity10")%>"/></td>
	   			<td><select name="unit10" style="width: 99%;"><option <c:if test="${unit10 == '个'}">selected</c:if>>个</option><option <c:if test="${unit10 == '件'}">selected</c:if>>件</option><option <c:if test="${unit10 == '套'}">selected</c:if>>套</option><option <c:if test="${unit10 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice10")%>" type="text" name="purprice10" class="export-cn1" value="<%=request.getAttribute("purprice10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice10" value="<%=request.getAttribute("unitprice10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall10" class="unit-price-all" value="<%=request.getAttribute("unitpriceall10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid10")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark10" value="<%=request.getAttribute("shopingmark10")%>"/></td>
	   			<td><input size="10" type="text" name="nw10" value="<%=request.getAttribute("nw10")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination10" value="<%=request.getAttribute("sourceDestination10")==null?"":request.getAttribute("sourceDestination10")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice10" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice10")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode10" value="<%=request.getAttribute("hscode10")%>"/></td>
	   			<td><input size="10" type="text" name="rate10" value="<%=request.getAttribute("rate10")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid10" value="<%=request.getAttribute("itemid10")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng10"/></td>
	   			<td><input size="10" type="text" name="itemchn10"/></td>
	   			<td><input size="10" type="text" name="quantity10"/></td>
	   			<td><select name="unit10" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice10" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice10" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall10" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark10" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw10" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination10" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice10" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity11" value="<%=request.getAttribute("quantity11")%>"/></td>
	   			<td><select name="unit11" style="width: 99%;"><option <c:if test="${unit11 == '个'}">selected</c:if>>个</option><option <c:if test="${unit11 == '件'}">selected</c:if>>件</option><option <c:if test="${unit11 == '套'}">selected</c:if>>套</option><option <c:if test="${unit11 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice11")%>" type="text" name="purprice11" class="export-cn1" value="<%=request.getAttribute("purprice11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice11" value="<%=request.getAttribute("unitprice11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall11" class="unit-price-all" value="<%=request.getAttribute("unitpriceall11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid11")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark11" value="<%=request.getAttribute("shopingmark11")%>"/></td>
	   			<td><input size="10" type="text" name="nw11" value="<%=request.getAttribute("nw11")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination11" value="<%=request.getAttribute("sourceDestination11")==null?"":request.getAttribute("sourceDestination11")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice11" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice11")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode11" value="<%=request.getAttribute("hscode11")%>"/></td>
	   			<td><input size="10" type="text" name="rate11" value="<%=request.getAttribute("rate11")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid11" value="<%=request.getAttribute("itemid11")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng11"/></td>
	   			<td><input size="10" type="text" name="itemchn11"/></td>
	   			<td><input size="10" type="text" name="quantity11"/></td>
	   			<td><select name="unit11" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice11" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice11" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall11" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark11" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw11" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination11" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice11" class="true-price"  <c:if test="${sessionScope.auth != 1}">readonly</c:if> onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
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
	   			<td><input size="10" type="text" name="quantity12" value="<%=request.getAttribute("quantity12")%>"/></td>
	   			<td><select name="unit12" style="width: 99%;"><option <c:if test="${unit12 == '个'}">selected</c:if>>个</option><option <c:if test="${unit12 == '件'}">selected</c:if>>件</option><option <c:if test="${unit12 == '套'}">selected</c:if>>套</option><option <c:if test="${unit12 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice12")%>" type="text" name="purprice12" class="export-cn1" value="<%=request.getAttribute("purprice12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice12" value="<%=request.getAttribute("unitprice12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall12" class="unit-price-all" value="<%=request.getAttribute("unitpriceall12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid12")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark12" value="<%=request.getAttribute("shopingmark12")%>"/></td>
	   			<td><input size="10" type="text" name="nw12" value="<%=request.getAttribute("nw12")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination12" value="<%=request.getAttribute("sourceDestination12")==null?"":request.getAttribute("sourceDestination12")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice12" class="true-price" <c:if test="${sessionScope.auth != 1}">readonly</c:if> value="<%=request.getAttribute("trueprice12")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode12" value="<%=request.getAttribute("hscode12")%>"/></td>
	   			<td><input size="10" type="text" name="rate12" value="<%=request.getAttribute("rate12")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid12" value="<%=request.getAttribute("itemid12")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng12"/></td>
	   			<td><input size="10" type="text" name="itemchn12"/></td>
	   			<td><input size="10" type="text" name="quantity12"/></td>
	   			<td><select name="unit12" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice12" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice12" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall12" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark12" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw12" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination12" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice12"  <c:if test="${sessionScope.auth != 1}">readonly</c:if> class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode12"/></td>
	   			<td><input size="10" type="text" name="rate12"/></td>
				<td>
					<tr>
					<input type="text" name="concat[].no">
				</tr>



				</td>
		   		<%} %>
	   		</tr>
	   		<tr>
	   			<%
	   			if(request.getAttribute("itemid13")!=null){
	   				%>
	   			<td><input size="10" type="text" name="itemeng13" value="<%=request.getAttribute("itemeng13")%>"/></td>
	   			<td><input size="10" type="text" name="itemchn13" value="<%=request.getAttribute("itemchn13")%>"/></td>
	   			<td><input size="10" type="text" name="quantity13" value="<%=request.getAttribute("quantity13")%>"/></td>
	   			<td><select name="unit13" style="width: 99%;"><option <c:if test="${unit13 == '个'}">selected</c:if>>个</option><option <c:if test="${unit13 == '件'}">selected</c:if>>件</option><option <c:if test="${unit13 == '套'}">selected</c:if>>套</option><option <c:if test="${unit13 == '台'}">selected</c:if>>台</option></select></td>
	   			<td><input size="10" field="<%=request.getAttribute("purprice13")%>" type="text" name="purprice13" class="export-cn1" value="<%=request.getAttribute("purprice13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice13" value="<%=request.getAttribute("unitprice13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall13" class="unit-price-all" value="<%=request.getAttribute("unitpriceall13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/><input type="hidden" value="<%=request.getAttribute("itemid13")%>"/></td>
	   			<td><input size="10" type="text" name="shopingmark13" value="<%=request.getAttribute("shopingmark13")%>"/></td>
	   			<td><input size="10" type="text" name="nw13" value="<%=request.getAttribute("nw13")%>" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination13" value="<%=request.getAttribute("sourceDestination13")==null?"":request.getAttribute("sourceDestination13")%>" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice13" <c:if test="${sessionScope.auth != 1}">readonly</c:if> class="true-price" value="<%=request.getAttribute("trueprice13")%>" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode13" value="<%=request.getAttribute("hscode13")%>"/></td>
	   			<td><input size="10" type="text" name="rate13" value="<%=request.getAttribute("rate13")%>"/></td>
	   			<td><input size="10" type="hidden" name="itemid13" value="<%=request.getAttribute("itemid13")%>"/></td>
		   		<%
		   			}else{
		   		%>
		   		<td><input size="10" type="text" name="itemeng13"/></td>
	   			<td><input size="10" type="text" name="itemchn13"/></td>
	   			<td><input size="10" type="text" name="quantity13"/></td>
	   			<td><select name="unit13" style="width: 99%;"><option>个</option><option>件</option><option>套</option><option>台</option></select></td>
	   			<td><input size="10" type="text" name="purprice13" class="export-cn1" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitprice13" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="unitpriceall13" class="unit-price-all" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="10" type="text" name="shopingmark13" value="N/M"/></td>
	   			<td><input size="10" type="text" name="nw13" class="n_weight"/></td>
	   			<td><input size="10" type="text" name="sourceDestination13" class="sourceDestination"/></td>
	   			<td><input size="10" type="text" name="trueprice13"  <c:if test="${sessionScope.auth != 1}">readonly</c:if> class="true-price" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="value=value.replace(/[^\d\.]/g,'')"/></td>
	   			<td><input size="20" type="text" name="hscode13"/></td>
	   			<td><input size="10" type="text" name="rate13"/></td>
		   		<%} %>
	   		</tr>
	   	</table>
	   	<br/>
	   	指定货代（如果有）(用&ltbr&gt换行)：<textarea name="frieght"  cols="45" rows="5"><%=request.getAttribute("frieght")%></textarea>
	   	<br/>
	   		*物流填写：报关发票号：<input name="Nonum" type="text" value="<%=request.getAttribute("Nonum")%>"/>
	   	发票日期：<input name="date" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("date")%>"/></input>
	   	sailing Date:<input name="saildate" id="saildate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("saildate")%>"/>
	   	Arrive Date:<input name="arriveDate" id="arriveDate" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("arriveDate")%>"/>
	   		<br/>
	   	货代:<input name="huodai" type="text" value="<%=request.getAttribute("huodai")%>"/>
	   	运费:<input name="yunfei" type="text" field="<%=request.getAttribute("yunfei")%>" value="<%=request.getAttribute("yunfei")%>" id="shipping_fee" onblur="calExportAmount(this,1)"/>(* FOB 无运费)
   	 	<br/>
   	 	运费方式 :<input type="radio" name="yunfeifs" value="COLLECT"/>到付
			<input type="radio" name="yunfeifs" value="PREPAID"/>预付
		保费:<input name="premium" type="text" field="<%=request.getAttribute("premium")%>" value="<%=request.getAttribute("premium")%>" id="premium" onblur="calExportAmount(this,2)"/>
			<br/>
		外销合同时间:<input name="waixiaotime" id="contract_date" type="text" onClick="WdatePicker()" value="<%=request.getAttribute("waixiaotime")%>"/>
		发货地址：<input type="radio" name="exportPlace" value="公司" <c:if test="${exportPlace == '公司'}">checked</c:if>>公司
		        <input type="radio" name="exportPlace" value="仓库" <c:if test="${exportPlace == '仓库'}">checked</c:if>>仓库
		        <input type="radio" name="exportPlace" value="工厂" <c:if test="${exportPlace == '工厂'}">checked</c:if>>工厂
			<br/>
				<script type="text/javascript">
		    window.onload = function(){
		    var checked = '<%=request.getAttribute("currency")%>'
		    	if(checked == 'GBP'){
                   alert("货币单位是GBP");
				}
				if(checked == 'EUR'){
				   alert("货币单位是EUR");
				}
				if(checked == 'AUD'){
				   alert("货币单位是AUD");
				}
				if(checked == 'RMB'){
				   alert("货币单位是RMB");
				}
		    
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
   	</table>
   <br/>  
   </form>
   <div>
     <span>请先下载本出运单专用模板：<a href="/shipping/GetProductExcelServlet?proId=<%=request.getAttribute("id")%>">拆分报关品名.xlsx</a>填写时候注意不要留空，如要加行，需整行增加，重复内容请复制补全,不允许合并单元格处理。</span>
   </div>
   <%
    if(request.getAttribute("excelPath")!=null && !"".equals(request.getAttribute("excelPath"))){
   	%>
   <div>
     <span>已上传报关品名下载：<a href="/shipping/DownloadDrawbackServlet?fileName=<%=request.getAttribute("excelPath")%>">已上传报关品名.xlsx</a></span>
   </div>
   	<%	
    }
    %>
   <%--<form onsubmit="return false;" method="post" enctype="multipart/form-data">
	   <p>多合同多品类详情上传（Excel）：<input type="file" name="file" class="pull-left" onchange="upload(this)"></p>
   </form>--%>
   
    <div class="region">
		<h3>准予电子出货确认单</h3>
		<div class="reginon_con">
			<p>请输入【准予电子出货确认单】编号：(本栏目可后补)</p>
			<div class="add">
				<input type="text" id="serialNumber">
				<button onclick="addShipping('<%=request.getAttribute("id")%>')">添加</button> 
			</div>
			<div class="del mt10 mb10" id="div_list">
			<span>已录入出运单：</span>
			 	<%
   				    Integer total4 = Integer.parseInt(request.getAttribute("total4").toString());
   					if(total4 != 0){
   			        for(int i=0;i<total4;i++){
   			        	if(request.getAttribute("serialNumber"+(i+1)) != null){  
						  if(request.getAttribute("isComplete"+(i+1)) != null && Integer.parseInt(request.getAttribute("isComplete"+(i+1)).toString()) == 1){
						 %>
						   <p>
							<a target="_blank" href="http://www.kuaizhizao.cn/complaint/detail?id=<%=request.getAttribute("sid"+(i+1))%>" class="green"><%=request.getAttribute("serialNumber"+(i+1))%>，已确认</a>
							<button class="ml10 mr30" onclick="del('<%=request.getAttribute("shippingId"+(i+1))%>',this)">删除</button>						 
					       </p>
						 <%	 
						  }else{
						 %>	
						   <p>
							<a target="_blank" href="http://www.kuaizhizao.cn/complaint/detail?id=<%=request.getAttribute("sid"+(i+1))%>" class="red ml10"><%=request.getAttribute("serialNumber"+(i+1))%>，未确认</a>
							<button class="ml10" onclick="del('<%=request.getAttribute("shippingId"+(i+1))%>',this)">删除</button>
					       </p>
						<%  
						  }
						%>
					
						
				<%	        		
		        	     }
	   			        }
	   				  }
				%>
			</div>
			<ul id="un_finish">
			   <%
   				    Integer total5 = Integer.parseInt(request.getAttribute("total4").toString());
   					if(total5 != 0){
   			        for(int i=0;i<total5;i++){
   			        	if(request.getAttribute("serialNumber"+(i+1)) == null){
   			    %>
							<li filed="<%=request.getAttribute("projectNo"+(i+1))%>"><em>未录入出运单的项目号：</em><span><%=request.getAttribute("projectNo"+(i+1))%></span></li>
				<%	        		
	        	        }
   			         }
   				  }
				%>
			</ul>
		</div>
	</div>
   
  
   <c:choose>
	   <c:when test="${sessionScope.auth == 1}">
	       <input type="button" style="margin-top: 50px;" value="保存修改" onclick="update_order(1)"></input>
	   </c:when>
	   <c:otherwise>
	       <p style="color:red;" style="margin-top: 50px;" >正式保存 多合同多品类请上传Excel</p>
		   <input type="button" value="预保存修改" onclick="update_order(0)"></input>
		   <input type="button" value="保存修改" onclick="update_order(1)"></input>
	   </c:otherwise>   
   </c:choose>
   <form action="DeleteServlet">
   <table>
   		<input type="hidden" name="id" value="<%=request.getAttribute("id")%>" id="proId"/>
   		<input type="submit" onclick="return confirm('真的要删除吗?')" name="submit1" value="删除">
   		<%--<input type="submit" value="删除"></input>
   		--%></table>
   </form>
   
    <div class="region">
		<h3>相关发票截图上传：</h3>
		<div>
		   <form onsubmit="return false;" method="post" enctype="multipart/form-data">
	          	<div class="add">
	          	    <input type="hidden" name="filename" id="img_file_name">
					<input type="text" id="factoryName" placeholder="开票工厂">
					<input type="file" name="file1" onchange="uploadImg(this)">
					<button onclick="addInvoice('<%=request.getAttribute("id")%>')" style="margin-left: -60px;">上传</button> 
				</div>
			</form>
        </div>
        <div class="mt10 mb10" id="div_list">
			<span>已上传发票：</span>
				<%
   				    Integer totalpro5 = Integer.parseInt(request.getAttribute("totalpro5").toString());
   					if(totalpro5 != 0){
   			        for(int i=0;i<totalpro5;i++){
   			        	if(request.getAttribute("picName"+(i+1)) != null){  
   			     %>
   			            <p><span><%=request.getAttribute("factoryName"+(i+1))%></span><a href="/shipping/DownloadInvoiceImgServlet?fileName=<%=request.getAttribute("picName"+(i+1))%>" style="padding-left: 30px;"><%=request.getAttribute("picName"+(i+1))%></a><button class="ml10 mr30" onclick="delInvoice('<%=request.getAttribute("invoiceId"+(i+1))%>',this)">删除</button></p>
   			     <%  		
   			        	}
   			          }	
   				    }
   			     %>  	        	
		</div>
   </div>
   
  
  </body>
  
<script type="text/javascript">

function addShipping(proId){
	var serialNumber = $('#serialNumber').val();
	if(!serialNumber){
		showNotice('请输入电子出货单编号',2000);	
		 return false; 	
	}
	//获取合同列表
	var contractList = "";
	$('.order-id').each(function(){
		if($(this).val()){
			contractList +=$(this).val()+",";
		}
	})
	if(contractList!=''){
		contractList = contractList.substring(0, contractList.length-1);
	}
	
	
      	$.ajax({
   			type : "post",
   			datatype : "json",
   			async : false, 	
   			url : "AddShippingServlet",
   			data : {
   				"serialNumber" : serialNumber,
   				"contractList" : contractList,
   				"proId" : proId
   			},
   			success : function(result) {
   				var dataObj = eval("("+result+")");	
   				var shippingId = dataObj.shippingId;
   				var isComplete = dataObj.isComplete;
   				var projectNo = dataObj.projectNo;  				
   				var sid = dataObj.sid;  				
   				if(shippingId && isComplete == 1){
   					$('#div_list').append('<p><a target="_blank" href="http://www.kuaizhizao.cn/complaint/detail?id='+sid+'" class="green">'+serialNumber+'，已确认</a><button class="ml10 mr30" onclick="del('+shippingId+',this)">删除</button></p>');
   					del_unFinish(projectNo);
   				}
   				if(shippingId && isComplete == 0){
   					$('#div_list').append('<p><a target="_blank" href="http://www.kuaizhizao.cn/complaint/detail?id='+sid+'" class="red ml10">'+serialNumber+'，未确认</a><button class="ml10 mr30" onclick="del('+shippingId+',this)">删除</button></p>');
   					del_unFinish(projectNo);
   				}   
   				if(!shippingId){
   					showNotice(dataObj.message,2000);
   				}
   			},
   			error : function() {
                  
   			}
   		});		
}


//判断移除未上传选项
function del_unFinish(projectNo){
	//删除
	$('#un_finish').find('li').each(function(){
		var str = $(this).attr('filed');
		if(str && projectNo == str){
			$(this).remove();
		}
	})	
} 





function del(id,obj){
	
	if(confirm("是否确认删除?")){
		if(!id){
			showNotice('未获取到id',2000);	
			return false; 	
		}
	   	$.ajax({
	   			type : "post",
	   			datatype : "json",
	   			async : false, 	
	   			url : "DeleteShippingServlet",
	   			data : {
	   				"id" : id
	   			},
	   			success : function(result) {
	   				showNotice('删除成功',1000); 		
	   				$(obj).prev().remove();
	   				$(obj).remove();
	   			},
	   			error : function() {
	                  
	   			}
	   		});		
	}
}

//添加发票图
function addInvoice(proId){
	var factoryName = $('#factoryName').val();
	if(!factoryName){
		showNotice('请输入开票工厂名',2000);	
		 return false; 	
	}
	var fileName = $('#img_file_name').val();
	if(!fileName){
		showNotice('请上传发票截图',2000);	
		 return false; 	
	}
	
      	$.ajax({
   			type : "post",
   			datatype : "json",
   			async : false, 	
   			url : "AddInvoiceImgServlet",
   			data : {
   				"factoryName" : factoryName,
   				"fileName" : fileName,
   				"proId" : proId
   			},
   			success : function(result) {
   				var dataObj = eval("("+result+")");	
   				var invoiceId = dataObj.invoiceId; 				
   				if(invoiceId){
   					$('#div_list').append('<p><span>'+factoryName+'</span><a href="/shipping/DownloadInvoiceImgServlet?fileName='+fileName+'" style="padding-left: 30px;">'+fileName+'</a><button class="ml10 mr30" onclick="delInvoice('+invoiceId+',this)">删除</button></p>');
   				}  				
   				if(!invoiceId){
   					showNotice(dataObj.message,2000);
   				}
   			},
   			error : function() {
                  
   			}
   		});		
}

//删除发票
function delInvoice(id,obj){
	
	if(confirm("是否确认删除?")){
		if(!id){
			showNotice('未获取到id',2000);	
			return false; 	
		}
	   	$.ajax({
	   			type : "post",
	   			datatype : "json",
	   			async : false, 	
	   			url : "DeleteInvoiceImgServlet",
	   			data : {
	   				"id" : id
	   			},
	   			success : function(result) {
	   				showNotice('删除成功',1000); 		
	   				$(obj).parent().remove();
	   			},
	   			error : function() {
	                  
	   			}
	   		});		
	}
}



</script> 
</html>

