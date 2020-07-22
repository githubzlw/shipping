<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>Cookie</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    
	<script type="text/javascript">
       function	queryProject(){
    	   var projectId = document.getElementById("projectId").value; 
    	   if(projectId == '' || projectId == null || projectId == undefined){
    		   alert("输入不正确");
    		   return false;
    	   }
    	   window.location = "/shipping/QueryByProjectIdServlet?projectId="+projectId; 	   
       }	
	
	</script>
	
	
	<style type="text/css">
	  .z-select{
		    margin: auto;
		    position: fixed;
		    top: 0px;
		    left: 0;
		    right: 0;
		    bottom: 0px;
		    text-align: center;
		    width: 330px;
		    height: 80px;
		    z-index: 9999;
		    display: block;
		    background-color: #fff;
		    padding-top: 35px;  
	  }
	 #select{
	     width: 150px;
         height: 35px;
	 } 
	 .z-select button{
	  height:30px;	 
	 } 
	  
	#cover{ 
	    position:fixed;left:0px;top:0px;
	    background:rgba(0, 0, 0, 0.4);
	    width:100%;  /*宽度设置为100%，这样才能使隐藏背景层覆盖原页面*/
	    height:100%;
	    filter:alpha(opacity=60);  /*设置透明度为60%*/
	    opacity:0.6;  /*非IE浏览器下设置透明度为60%*/
	    display:block; 
	    z-Index:999;  
	}
	#modal{ 
	        position: absolute;
		   width: 20px;
		   height: 20px;
		   top: 0;
		   right: 0;
		   background-color: rgba(0,0,0,.5);
		   cursor: pointer;
		   z-Index: 9999;
		   color: #fff;
		   text-align: center;
		   line-height: 20px;
		   font-size: 16px;
	}
	
	</style>
	</head>
  <body>
<!--   <form action="../QueryByProjectIdServlet"  method="post"> -->
   <div style="padding-bottom: 11px;padding-top: 15px;">
   <input placeholder="输入项目号" id="projectId"><input type="button" value="查询 " onclick="queryProject()">
   </div>
   
  
  <div class="select_div" style="display:none;">
      <input type="hidden" id="pro_id">
	     <!--页面的遮罩层-->
	  <div id="cover"></div>
		  <!--页面的弹出框-->	
	   <div class="z-select">
	   <div id="modal" onclick="closeDiv()">
		    <div id="close">x</div>
		</div>
		  <select id="select">
		   <option></option> 
		  </select>
		  <button onclick="add_auth()">确认添加</button>
	  </div>
  </div> 
  
  
  
   <table  border="1" id="table" style="width: 53%;">
  <tr>
  <td style="width: 23%;">项目号</td>
  <td>客户名</td>
  <td>录入时间</td>
  <td>操作</td>
  <td>操作1</td>
  <td>操作2</td>
  </tr>
  </table>
  

<!--   </form> -->
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
  <script type="text/javascript">
	  var tab=document.getElementById("table");
	  <%
	  if(request.getAttribute("total")!=null)
	  {
		  int total=(Integer)request.getAttribute("total");
	  		if(total==0)
	  			return;
		  for(int j=total-1;j>=0;j--){
	  %>
		  var newTR =tab.insertRow(tab.rows.length);
		  var newNameTD = newTR.insertCell(0);
		  newNameTD.innerHTML = "<%=request.getAttribute("nonum"+j)%>";
		  var newNameTD = newTR.insertCell(1);
		  newNameTD.innerHTML = "<%=request.getAttribute("clientName"+j)%>";
		  var newNameTD1 = newTR.insertCell(2);
		  newNameTD1.innerHTML = "<%=request.getAttribute("timeDate"+j)%>";

		  <%
			  int status=(Integer)request.getAttribute("status"+j);
			  if(status==0){
			%>
				//var newNameTD2 = newTR.insertCell(2);
			  //newNameTD2.innerHTML = "过期不能修改";
				var newNameTD2 = newTR.insertCell(3);
			  newNameTD2.innerHTML = "<a href='CheckServer?id=<%=request.getAttribute("id"+j)%>' target='showframe'>查看</a>";
			<%
			  }else{
		  %>
			  var newNameTD2 = newTR.insertCell(3);
			  newNameTD2.innerHTML = "<a href='InfoServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>修改</a>";
			<%}%>

		  
		  var newNameTD3 = newTR.insertCell(4);
		  newNameTD3.innerHTML = "<a href='CopyServlet?id=<%=request.getAttribute("id"+j)%>' target='showframe'>复制</a>";
		  
		  var newNameTD3 = newTR.insertCell(5);
		  newNameTD3.innerHTML = "<a href='javascript:void(0);' onclick='show(<%=request.getAttribute("id"+j)%>)'>添加跟单</a>";
	  <%
	  }
	  }
	  %>
	  
	  
	  
	  
	  var select=document.getElementById("select");
	  <%
	  if(request.getAttribute("totalAuth")!=null)
	  {
		  int totalAuth=(Integer)request.getAttribute("totalAuth");
	  		if(totalAuth==0)
	  			return;
		  for(int i=totalAuth-1;i>=0;i--){
	  %>
      select.options.add(new Option('<%=request.getAttribute("authName"+i)%>','<%=request.getAttribute("authId"+i)%>'));
	  <%
	  }
	  }
	  %>
	  
	  
	  function show(id){		  
		  $('#pro_id').val(id);
		  $('.select_div').show();
	  }
	  
	  function closeDiv(){
		  $('.select_div').hide();
	  }
	  
	  
 function add_auth(){	
	var authName = $('#select option:selected').text();
    var authId = $('#select').val();
    var proId = $('#pro_id').val();  
     if (confirm("确认添加"+authName+"?")) {	
		  
		  if(!(authId && proId)){
			  return;
		  }
		  
		  $.ajax({
				type : "post",
				datatype : "json",
				url : "AddProjectAuthServlet",
				data : {
					"authId" : authId,
					"proId" : proId
				},
				success : function(result) {
					alert("添加成功");
					closeDiv();
				},
				error : function() {
					alert("添加失败");	
				}
			});
	  }
 }  
	  
  </script>
  </body>
</html>
