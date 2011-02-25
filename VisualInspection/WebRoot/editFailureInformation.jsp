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
    
    <title><s:text name="editFailureInformationTitle"/></title>
    
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
	   							
				<s:textfield key="fid" readonly="true" />
				<s:textfield key="partname" readonly="true"  />
				<s:textfield key="positionNr" readonly="true"  />
				<s:textfield key="workstationNr" readonly="true"  />				
				<s:textfield key="formData.operatorID" readonly="true"  />
				<s:textfield key="oldFailureCode" readonly="true" />	
				<s:select 	key="failureCode" list="failureReportData.failureCodes" 
							listKey="failureCodeAbbr" listValue="failurCodeName"/>
				<s:textarea key="failureDescription" />
				<s:textfield key="operatorID" readonly="true"  />	
				<s:textfield key="confirmation" readonly="true"  />	
				<table border="0">			
			    <tr>				    				    
				    <td><s:submit key="buttonCancel" action="cancelEditFailureInformation_AllTestsOverview" theme="css_xhtml"/></td>
				    <td><s:submit key="buttonSave" action="editFailureInformation_AllTestsOverview" theme="css_xhtml"/></td>
			    </tr>
				</table>
						
		</s:form>
	</div>

  </body>
</html>
