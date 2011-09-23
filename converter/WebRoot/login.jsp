<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>DXF to SVG file converter for visual inspection</title>
    
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
    <form action="login.action" method="post">
    <table border="1">
    <tr>
    <td>Domain:</td>
    <td><input type="text" name="domain" value="<s:property value='domain'/>"/></td>
    </tr>
    <tr>
    <td>Account:</td>
    <td><input type="text" name="username"></td>
    </tr>
    <tr>
    <td>Password:</td>
    <td><input type="password" name="password"></td>
    </tr>
    <tr>
    <td colspan="2"><input type="submit" value="Submit"></td>
    </tr>
    </table>
    </form>
    <s:property value="message"/>

<table width="100%">
    	<tr>
		<td style="background-color:beige" align="center" colspan="9">
		<s:text name="User List"/>
		</td>
		</tr>	       
		<tr style="background-color:#A5ABFF">
			<th><s:text name="Domain"/></th>
			<th><s:text name="Account"/></th>
			<th><s:text name="Name"/></th>
			<th><s:text name="Role"/></th>
			<th><s:text name="Department"/></th>
		</tr>
 
	  	<s:iterator value="tabLoginList" status="stuts">
	  	<tr style="background-color:#EBEDFF">
		    <td><s:property value="id.domain"/></td>
		    <td><s:property value="id.account"/></td>
		    <td><s:property value="name"/></td>
		    <td><s:property value="role"/></td>
		    <td><s:property value="department"/></td>
		</tr>
		</s:iterator>
</table>
  </body>
</html>
