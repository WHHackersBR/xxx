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
	    </div>
	    <div>
	    </div>		
	    <div>
	    	<table width="100%">
	    		<tr>	    			
		    	<td colspan="4"><s:submit align="center" key="closeWindow" onclick="window.opener=null;window.close();"/></td>
		    	</tr>
	    		<tr style="background-color:beige">	    			
			    	<td align="center" colspan="4"><s:text name="unconfirmedText"/></td>	       
		    	</tr>
	    		<tr style="background-color:#A5ABFF">	    			
	    			<th><s:text name="poNoText"/></th>
	    			<th><s:text name="workstationNrText"/></th>
	    			<th><s:text name="workstationDescriptionText"/></th>	    			
	    			<th><s:text name="side"/></th>	    			
	    		</tr>
			   
			    <s:iterator value="unconfirmedList" status="stuts">			    		    	
			
				<s:if test="#stuts.odd == true">
				<tr style="background-color:#EBEDFF">			    	
				    <td align="center"><s:property value="poNo"/></td>
				    <td align="center"><s:property value="workstationNr"/></td>
				    <td align="center"><s:property value="workstationDescription"/></td>				   		
				    <td align="center"><s:property value="side"/></td>				   		
			    </tr>
			    </s:if>
			    <s:else>
				    <td align="center"><s:property value="poNo"/></td>
				    <td align="center"><s:property value="workstationNr"/></td>
				    <td align="center"><s:property value="workstationDescription"/></td>				   		
				    <td align="center"><s:property value="side"/></td>				   		
			    </s:else>
			    </s:iterator>
		    </table>
	
		</div>
	</s:form>
  </body>
</html>
