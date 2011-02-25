<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><s:text name="editFailureReportTitle"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <div id="content">
	   <s:form theme="xhtml">
	   		
				<s:textfield key="formData.poNo"  labelposition="left" readonly="true" />
				<s:textfield key="formData.itemNr"  labelposition="left" readonly="true"  />
				<s:textfield key="formData.versionAS"  labelposition="left" readonly="true"   />
				<s:textfield key="formData.currentFid"  labelposition="left" readonly="true"  />
				<s:textfield key="formData.workstationNr"  labelposition="left" readonly="true"  />
				<s:textfield key="formData.side"  labelposition="left" readonly="true"  />
				<s:textfield key="failureReportData.partname"  labelposition="left" readonly="true"  />
				<s:textfield key="failureReportData.positionNr"  labelposition="left" readonly="true"  />
				<s:textfield key="formData.operatorID"  labelposition="left" readonly="true"  />
				<s:textfield key="oldFailureCode"  labelposition="left" readonly="true" />	
				<s:select 	key="failureReportData.failureCode" list="failureReportData.failureCodes" 
							listKey="failureCodeAbbr" listValue="failurCodeName"  labelposition="left" />
				<s:textarea key="failureReportData.failureDescription"  labelposition="left" />
	
				<table border="0">			
			    <tr>				    				    
				    <td><s:submit key="buttonCancel" action="cancelEditFailure_ItemFailureList" theme="css_xhtml" /></td>
				    <td><s:submit key="buttonSave" action="editFailure_ItemFailureList" theme="css_xhtml"/></td>
			    </tr>
				</table>			
			
		</s:form>
	</div>

  </body>
</html>
