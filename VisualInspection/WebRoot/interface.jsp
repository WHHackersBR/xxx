<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    
    <title><s:text name="interfaceTitle"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--> 
	<link rel="stylesheet" type="text/css" href="./ext/resources/css/ext-all.css" />
	<script type="text/javascript" src="./ext/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="./ext/ext-all.js"></script>
			
	<sx:head debug="false" cache="false" compressed="false" />
	
	<link rel="stylesheet" type="text/css" href="./css/interface.css">
  </head>
  <script type="text/javascript" src="./js/openWindow.js"></script>
  
  <script type="text/javascript">
  	//document.getElementById("inputFID").focus();
  	//alert("test01");
    function publish_change_side() {
        dojo.event.topic.publish("change_side");
    }
    function publish_change_operatorID() {
        dojo.event.topic.publish("change_operatorID");
    }
    function publish_change_currentID() {
    	if(event.keyCode==13){
			//var otxt=document.getElementById("inputFID");
			//alert("你按了回车");
       		dojo.event.topic.publish("change_currentID");        
			//otxt.value="";
	        document.getElementById("inputFID").focus();
 		} 
    }
    function updateCurrentFID() {//call in the failureReport.jsp
    	document.getElementById('inputFID').value=document.getElementById('currentFID').value;
    	dojo.event.topic.publish("change_currentID");
    	//alert("setForcus");
        //document.getElementById("inputFID").focus();
    }
    function setFocusFID() {//call in the failureReport.jsp
        document.getElementById("inputFID").focus();
    }

	dojo.event.topic.subscribe("/clear_FID", function(data, request, widget){
   
    //data : text returned from request(the html)
    //request: XMLHttpRequest object
    //widget: widget that published the topic
        document.getElementById("inputFID").focus();
        document.getElementById("inputFID").value="";
        dojo.event.topic.publish("get_poCompleted");
	});
    function publish_get_WorkstationDescription() {
     	//if(event.keyCode==13){
	        dojo.event.topic.publish("get_WorkstationDescription");
	    //    event.keyCode=9;
	    //    document.getElementById("operatiorID").focus();
        //}        
    } 
    function getFocus_OPID()    {
     	if(event.keyCode==13){
	        document.getElementById("operatiorID").focus();
	        event.keyCode=9;
        }        
    } 
    
    function publish_get_itemNr_and_version() {
     	if(event.keyCode==13){
	        dojo.event.topic.publish("get_itemNr_and_version"); 
	        event.keyCode=9;
	        document.getElementById("workstationNo").focus();
        }       
    }
    
    //make sure everything is loaded on startup
    function publish_all() {
    	dojo.event.topic.publish("get_itemNr_and_version");      
    	dojo.event.topic.publish("get_WorkstationDescription");
    	dojo.event.topic.publish("change_side");
     	dojo.event.topic.publish("change_operatorID");
        dojo.event.topic.publish("change_currentID");        
    }
    
    
  </script>
  
  <body>  	
  	<div id="content" 	style="background-color: beige;">
    <table>
	 <tr>			    		    				    
	    <s:form id="frm_ItemNrAndVersion">
	    <td style="width:2.5cm;"><s:textfield key="formData.poNo" labelposition="left" cssStyle="width:100%;" onkeydown="publish_get_itemNr_and_version();"/></td>						    				    			   	
	    <td><sx:div cssStyle="width:4cm;" href="fillItemList.action" listenTopics="get_itemNr_and_version" formId="frm_ItemNrAndVersion"/></td>					   							    				   	
	   	</s:form>	

	    <s:form id="frm_poCompleted">
	    <td><sx:div cssStyle="width:2cm;" href="poCompleted.action" listenTopics="get_poCompleted" formId="frm_poCompleted" updateFreq="15000"/></td>
	   	</s:form>	

	    <s:form id="frm_WorkstationDescription"  onsubmit="return false">
	    <td id="workstationNo" style="width:1.5cm;"><s:textfield key="formData.workstationNr" labelposition="left" cssStyle="width:100%;" onkeydown="getFocus_OPID();" onblur="publish_get_WorkstationDescription();"/></td>				    			   		
	    <td><sx:div cssStyle="width:100%;" href="fillWorkstationDescription.action" listenTopics="get_WorkstationDescription" formId="frm_WorkstationDescription"/></td>					   							    				   	
	   	</s:form>	
	   
	   	<s:form id="frm_side_Id">
	   	<td id="changeSide"><sx:div cssStyle="width:100%;" href="changeSide.action" listenTopics="change_side" formId="frm_side_Id" /></td>
	   	</s:form>
	   	
	   	<s:form id="frm_operatorID_Id" onsubmit="return false">
	   	<td id="operatiorID"><sx:div cssStyle="width:100%;" href="changeOperatorID.action" labelposition="left" listenTopics="change_operatorID" formId="frm_operatorID_Id" /></td>
	   	</s:form>
		
	    <s:form action="startPO" onsubmit="document.getElementById('currentFID').value='';document.getElementById('inputFID').focus();">
	    	<td><sx:submit cssStyle="width:1.5cm;" key="startPO" targets="svgDiv" />	</td>			    		
	    </s:form>
	    
	    <s:form>
	    <td><sx:submit cssStyle="width:1.5cm;" key="poHistory" targets="dummy" onclick="document.getElementById('inputFID').focus();openWindow('openPoInformation.action')" />
	    </td>
	    <td><sx:submit cssStyle="width:1.5cm;" key="openingPO" targets="dummy" onclick="document.getElementById('inputFID').focus();openWindow('openShowUnconfirmed.action')"/>
	    </td>
	    </s:form>
	    	
	 </tr>
	</table>
	<table style="height:5%;">
	 <tr>
    	<s:form>
	    	<td><s:submit key="storeSvgInDB" action="goToStoreSvgInDB"/></td>
	    </s:form>
	    
	    <s:form id="frm_current_Id">
	    <td><s:textfield cssStyle="width:3.2cm;" id="inputFID" key="formData.fid" labelposition="left" onkeydown="publish_change_currentID();"/></td>				   		
	    <td><sx:div cssStyle="width:100%;" labelposition="left" href="fillCurrentFID.action" listenTopics="change_currentID" formId="frm_current_Id" afterNotifyTopics="/clear_FID"/></td>					   							    				   	
	   	</s:form>				   					   				    

		<s:form action="testComplete_Interface">									    				    
	   	<td><sx:submit key="failure" cssStyle="width:1.2cm;background-color:orange" onclick="document.getElementById('inputFID').value=document.getElementById('currentFID').value;" afterNotifyTopics="change_currentID"/></td>
		</s:form>
		
		<s:form>
	    <td><sx:submit cssStyle="width:2.2cm;" key="singleBoardErrList"  targets="dummy" onclick="document.getElementById('inputFID').focus();openWindow('openItemFailureList_ItemFailureList.action')"/></td>
	    <td><sx:submit cssStyle="width:2.2cm;" key="currentPoDetails"  onclick="openWindow('openPoAllTestsOverview_AllTestsOverview.action');"/></td>
    	</s:form>
	</tr>			
	    
    </table>

	</div>	
	<sx:div id="svgDiv" executeScripts="true">
		 <iframe src="createSvg/generated.svg"  width="100%" height="85%"></iframe>
	</sx:div>
  </body>
</html>

