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
	    	<table style="background-color:beige" width="100%">
	    		<tr>
	    			<td colspan="1"><s:textfield key="formData.poNo"  labelposition="left" readonly="true"/></td>
	    			<td colspan="1"><s:textfield key="formData.itemNr" labelposition="left" readonly="true"/></td>
	    			<td colspan="1"><s:textfield key="formData.versionAS" labelposition="left" readonly="true"/></td>
	    		</tr>
	    		<tr>
	    			<td colspan="1"><s:textfield key="formData.workstationNr" labelposition="left" readonly="true"/></td>
	    			<td colspan="2"><s:textfield cssStyle="width:8cm;" key="formData.workstationDescription" labelposition="left" readonly="true"/></td>    			
	    		</tr>
	    		<tr>
	    			<td><s:textfield key="nrOfPassed" labelposition="left" readonly="true"/></td>
	    			<td><s:textfield key="nrOfFailed" labelposition="left" readonly="true"/></td>    			
	    		</tr>	    		
	    		<tr>
	    		    <td colspan="1">
	    		    <s:submit key="confirmAll" action="confirmAll_AllTestsOverview"/></td>   		
	    			<td colspan="1">
	    			<s:submit key="closeWindow" onclick="window.opener.parent.setFocusFID();window.opener=null;window.close();"/></td>    			
	    		</tr>
	    	</table>
	    </div>
	    <div>
	    </div>		
	    <div>
	    	<table width="100%">
	    		<tr>	    			
	    			<td style="background-color:lime" colspan="5" align="center">
	    			<s:text name="passedListText"/> 
	    			</td>	       
	    		</tr>	    			
	    		<tr style="background-color:#A5ABFF">	    			
	    			<th><s:text name="operationText"/></th>
	    			<th><s:text name="fidText"/></th>
	    			<th><s:text name="operatorIdText"/></th>
	    			<th><s:text name="timeText"/></th>
	    			<th><s:text name="confirmationText"/></th>	    				    			
	    		</tr>
			   
			    <s:iterator value="passedList"  status="stuts">
			    		    	
	   			<s:url id="removeUrl" action="deleteTested_AllTestsOverview">
					<s:param name="fid" value="fid" />
					<s:param name="workstationNr" value="formData.workstationNr" />					
				</s:url>	
			
				<s:if test="#stuts.odd == true">
				<tr style="background-color:#EBEDFF">			    	
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="time"/></td>
				    <td><s:property value="confirmation"/></td>		
			    </tr>
			    </s:if>
			    <s:else>
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="time"/></td>
				    <td><s:property value="confirmation"/></td>		
			    </s:else>
			    </s:iterator>
		   		<tr>
		   		<td style="background-color:yellow" colspan="5" align="center">
		   		<s:text name="preTreatmentErrorText"/>
		   		</td>		   		
		   		</tr>	    		    
	    		<tr style="background-color:#A5ABFF">	    			
	    			<th><s:text name="operationText"/></th>
	    			<th><s:text name="fidText"/></th>
	    			<th><s:text name="operatorIdText"/></th>
	    			<th><s:text name="timeText"/></th>
	    			<th><s:text name="confirmationText"/></th>	    				    			
	    		</tr>
			   
			    <s:iterator value="preTreatmentErrorList" status="stuts">
			    		    	
	   			<s:url id="removeUrl" action="deleteTested_AllTestsOverview">
					<s:param name="fid" value="fid" />
					<s:param name="workstationNr" value="formData.workstationNr" />					
				</s:url>	
			
				<s:if test="#stuts.odd == true">
				<tr style="background-color:#EBEDFF">			    	
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="time"/></td>
				    <td><s:property value="confirmation"/></td>		
			    </tr>
			    </s:if>
			    <s:else>
			    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
				    <td><s:property value="fid"/></td>
				    <td><s:property value="operatorID"/></td>
				    <td><s:property value="time"/></td>
				    <td><s:property value="confirmation"/></td>		
			    </s:else>
			    </s:iterator>
		    </table>
		</div>
		<div>
	    </div>	
		<div>
	    	<table width="100%">
	    		<tr>
			    	<td style="background-color:#FF0033" colspan="11" align="center"><s:text name="failedListText"/></td>	       
	    		</tr>
	    		<tr style="background-color:#A5ABFF">
	    			<th colspan="2"><s:text name="operationText"/></th>
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
			   
			    <s:iterator value="failedList"  status="stuts">
			    
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
			
				<s:if test="#stuts.odd == true">
				<tr  style="background-color:#EBEDFF">
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
			    </s:if>
			    <s:else>
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
			    </s:else>
			    </s:iterator>
		    </table>
	
		</div>
	</s:form>
  </body>
</html>
