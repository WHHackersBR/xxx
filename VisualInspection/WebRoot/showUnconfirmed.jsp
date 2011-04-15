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
    
    <title><s:text name="showUnconfirmedTitle"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <!--  script type="text/javascript" src="./js/updateParentWindow.js"></script-->
  <body>
 	<s:form>
	    <div>
	    	<s:submit key="closeWindow" onclick="window.opener=null;window.close();"/>
	    </div>
	    <div>
	    	<s:text name="unconfirmedText"/>	       
	    </div>		
	    <div>
	    	<table border="1">
	    		<tr>	    			
	    			<th><s:text name="poNoText"/></th>
	    			<th><s:text name="workstationNrText"/></th>
	    			<th><s:text name="workstationDescriptionText"/></th>	    			
	    		</tr>
			   
			    <s:iterator value="unconfirmedList">			    		    	
			
				<tr>			    	
				    <td><s:property value="poNo"/></td>
				    <td><s:property value="workstationNr"/></td>
				    <td><s:property value="workstationDescription"/></td>				   		
			    </tr>
			    </s:iterator>
		    </table>
	
		</div>
	</s:form>
  </body>
</html>
