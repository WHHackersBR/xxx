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
    
    <title><s:text name="itemFailureListTitle"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript" src="./js/updateParentWindow.js"></script>
  <body>
 	<s:form>
	    <div>
	    	<table border="1">
	    		<tr>
	    			<td colspan="4"><s:textfield key="formData.poNo"  labelposition="left" readonly="true"/></td>
	    			<td colspan="2"><s:textfield key="formData.itemNr" labelposition="left" readonly="true"/></td>
	    			<td colspan="2"><s:textfield key="formData.versionAS" labelposition="left" readonly="true"/></td>
	    		</tr>
	    		<tr>
	    			<td><s:textfield key="formData.currentFid" labelposition="left" readonly="true"/></td>
	    			<td><s:submit key="changeToPassed" action="changeToPassed_ItemFailureList"/></td>    			
	    		</tr>
	    		<tr>
	    			<td colspan="4"><s:textfield key="formData.workstationNr" labelposition="left" readonly="true"/></td>
	    			<td><s:textfield key="formData.side" labelposition="left" readonly="true"/></td>    		
	    		</tr>
	    		<tr>
	    		    		
	    			<td colspan="4"><s:submit key="closeWindow" onclick="window.opener=null;window.close();"/></td>    			
	    		</tr>
	    	</table>
	    </div>
	    <div>
	    	<s:text name="errorDetailsList"/>	       
	    </div>		
	    <div>
	    	<table border="1">
	    		<tr>
	    			<th></th>
	    			<th></th>
	    			<th><s:text name="fidText"/></th>
	    			<th><s:text name="pieLocText"/></th>
	    			<th><s:text name="compLocText"/></th>
	    			<th><s:text name="failCodeText"/></th>
	    			<th><s:text name="failDefText"/></th>
	    			<th><s:text name="failDescText"/></th>
	    			<th><s:text name="timeText"/></th>
	    		</tr>
			   
			    <s:iterator value="itemFailureList">
			    
		    	<s:url id="editUrl" action="goToEditFailureReport">
					<s:param name="fid" value="fid" />
					<s:param name="formData.workstationNr" value="formData.workstationNr" />
					<s:param name="failureReportData.positionNr" value="pieceLocation" />
					<s:param name="failureReportData.partname" value="componentLocation" />
					<s:param name="failureReportData.failureCode" value="failureCode" />
					<s:param name="failureReportData.failureDescription" value="failureDescription" />
				</s:url>
	   			<s:url id="removeUrl" action="delFailure_ItemFailureList">
					<s:param name="fid" value="fid" />
					<s:param name="formData.workstationNr" value="formData.workstationNr" />
					<s:param name="failureReportData.positionNr" value="pieceLocation" />
					<s:param name="failureReportData.partname" value="componentLocation" />
					<s:param name="failureReportData.failureCode" value="failureCode" />
				</s:url>	
			
				<tr>
			    	<td><s:a href="%{editUrl}"   ><s:text name="editText"/></s:a></td>
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="pieceLocation"/></td>
				    <td><s:property value="componentLocation"/></td>
				    <td><s:property value="failureCode"/></td>
				    <td><s:property value="failureDefinition"/></td>
				    <td><s:property value="failureDescription"/></td>
				    <td><s:property value="time"/></td>
		
			    </tr>
			    </s:iterator>
		    </table>
	
		</div>
	</s:form>
  </body>
</html>
