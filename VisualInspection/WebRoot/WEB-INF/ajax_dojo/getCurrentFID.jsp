<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>

	<head>
		<title>Current FID result</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/interface.css" >

	</head>

	<body>
		 <table>
			<tr>
				<td><s:textfield id="currentFID" cssStyle="width:3.2cm;" labelposition="left" key="formData.currentFid" readonly="true"/></td>
				<s:if test="formData.failed">							
				<td style="color:green"><s:property escape="false" value="errorOutput" /></td>
				</s:if>
				<s:else>
				<td style="background-color:red;"><s:property escape="false" value="errorOutput" /></td>
				</s:else>							
			</tr>
		</table>
	</body>

</html>
