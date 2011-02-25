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
    
    <title><s:text name="failureReportTitle"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<sx:head debug="false" cache="false" compressed="false" />
	

  </head>
  
  <script type="text/javascript" src="./js/updateParentWindow.js"></script>
  	
  <script type="text/javascript">
	dojo.event.topic.subscribe("/afterAddedReport", function(data, request, widget){
   
    //data : text returned from request(the html)
    //request: XMLHttpRequest object
    //widget: widget that published the topic
		if(document.getElementById("inputPOS").value==""){
		alert("请输入子板位置");
		}else{
	    closeNoChanges();
	    }
	});
  </script>
  	
  <body>
     <div id="content">
	   <s:form theme="xhtml" id="frm_failureReport" action="addFailureReport_Interface">
	   		
				<table border="0">			
				<s:textfield key="formData.poNo" readonly="true" />
				<s:textfield key="formData.itemNr" readonly="true"  />
				<s:textfield key="formData.versionAS" readonly="true"   />
				<s:textfield key="formData.currentFid" readonly="true"  />
				<s:textfield key="formData.workstationNr" readonly="true"  />
				<s:textfield key="formData.side" readonly="true"  />
				<s:textfield key="failureReportData.partname" readonly="true"  />
				<s:textfield id="inputPOS" key="failureReportData.positionNr" />
				<s:textfield key="formData.operatorID" readonly="true"  />
				<s:select 	key="failureReportData.failureCode" list="failureReportData.failureCodes" 
							listKey="failureCodeAbbr" listValue="failurCodeName"/>
				<s:textarea key="failureReportData.failureDescription" />
			    <tr>				    				    
				    <td><sx:submit key="buttonSave" afterNotifyTopics="/afterAddedReport"  /></td>
				    <td><s:submit key="buttonCancel" onclick="closeNoChanges()" /></td>				   
			    </tr>
				</table>					
			
		</s:form>
	</div>

  </body>
</html>
