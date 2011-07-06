<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><s:text name="defind part"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<sx:head debug="false" cache="false" compressed="false" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/interface.css" >
	

  </head>
  
  <script type="text/javascript" src="./js/updateParentWindow.js"></script>
  	
  <script type="text/javascript">
  window.close();
	dojo.event.topic.subscribe("/afterAddedReport", function(data, request, widget){
   
    //data : text returned from request(the html)
    //request: XMLHttpRequest object
    //widget: widget that published the topic
	 //   closeNoChanges();
	 window.opener.parent.updateCurrentFID();
	 window.opener.parent.focus();
	 window.opener.parent.setFocusFID();
	 window.opener=null;window.close();
	});
	function checkInput(){
		if(document.getElementById("inputPOS").value==""){
		alert("请输入子板位置");
		return false;
		}else if(document.getElementById("inputFailCode").value=="9999"){
		alert("请选择错误代码");
		return false;
		}else{
		return true;
	    }
	}
  </script>
  	
  <body>
     <div id="content">
 	</div>

  </body>
</html>
