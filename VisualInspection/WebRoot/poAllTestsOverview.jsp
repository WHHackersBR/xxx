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
    
    <title><s:text name="poAllTestsTitle"/></title>
    
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
	    			<td><s:textfield key="formData.workstationNr" labelposition="left" readonly="true"/></td>
	    			<td><s:textfield key="formData.workstationDescription" labelposition="left" readonly="true"/></td>    			
	    		</tr>
	    		<tr>
	    			<td><s:textfield key="nrOfPassed" labelposition="left" readonly="true"/></td>
	    			<td><s:textfield key="nrOfFailed" labelposition="left" readonly="true"/></td>    			
	    		</tr>	    		
	    		<tr>
	    		    <td colspan="4"><s:submit key="confirmAll" action="toDO"/></td>   		
	    			<td colspan="4"><s:submit key="closeWindow" onclick="closeNoChanges()"/></td>    			
	    		</tr>
	    	</table>
	    </div>
	    <div>
	    	<s:text name="passedListText"/>	       
	    </div>		
	    <div>
	    	<table border="1">
	    		<tr>	    			
	    			<th><s:text name="operationText"/></th>
	    			<th><s:text name="fidText"/></th>
	    			<th><s:text name="operatorIdText"/></th>
	    			<th><s:text name="timeText"/></th>
	    			<th><s:text name="confirmationText"/></th>	    				    			
	    		</tr>
			   
			    <s:iterator value="passedList">
			    		    	
	   			<s:url id="removeUrl" action="deleteTested_AllTestsOverview">
					<s:param name="fid" value="fid" />
					<s:param name="workstationNr" value="formData.workstationNr" />					
				</s:url>	
			
				<tr>			    	
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="time"/></td>
				    <td><s:property value="confirmation"/></td>		
			    </tr>
			    </s:iterator>
		   		<tr>
		   		<td><s:text name="preTreatmentErrorText"/></td>		   		
		   		</tr>	    		    
	    		<tr>	    			
	    			<th><s:text name="operationText"/></th>
	    			<th><s:text name="fidText"/></th>
	    			<th><s:text name="operatorIdText"/></th>
	    			<th><s:text name="timeText"/></th>
	    			<th><s:text name="confirmationText"/></th>	    				    			
	    		</tr>
			   
			    <s:iterator value="preTreatmentErrorList">
			    		    	
	   			<s:url id="removeUrl" action="deleteTested_AllTestsOverview">
					<s:param name="fid" value="fid" />
					<s:param name="workstationNr" value="formData.workstationNr" />					
				</s:url>	
			
				<tr>			    	
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="time"/></td>
				    <td><s:property value="confirmation"/></td>		
			    </tr>
			    </s:iterator>
		    </table>
		</div>
		<div>
	    	<s:text name="failedListText"/>	       
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
	    			<th><s:text name="operatorIdText"/></th>
	    			<th><s:text name="confirmationText"/></th>
	    		</tr>
			   
			    <s:iterator value="failedList">
			    
		    	<s:url id="editUrl" action="goToEditFailureInformation">
					<s:param name="fid" value="fid" />
					<s:param name="workstationNr" value="formData.workstationNr" />
					<s:param name="positionNr" value="pieceLocation" />
					<s:param name="partname" value="componentLocation" />
					<s:param name="failureCode" value="failureCode" />
					<s:param name="failureDescription" value="failureDescription" />
					<s:param name="operatorID" value="operatorID" />
					<s:param name="confirmation" value="confirmation" />
				</s:url>
	   			<s:url id="removeUrl" action="delFailureInformation_AllTestsOverview">
					<s:param name="fid" value="fid" />
					<s:param name="workstationNr" value="formData.workstationNr" />
					<s:param name="positionNr" value="pieceLocation" />
					<s:param name="partname" value="componentLocation" />
					<s:param name="failureCode" value="failureCode" />
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
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="confirmation"/></td>	
			    </tr>
			    </s:iterator>
		    </table>
	
		</div>
	</s:form>
  </body>
</html>
