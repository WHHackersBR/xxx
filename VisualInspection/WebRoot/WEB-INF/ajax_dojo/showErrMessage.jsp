<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>

	<head>
		<title>Error message</title>

	</head>

	<body>
	<table>
		<tr>
			<td style="color:red">
				<s:if test="formData.failed">							
				<td style="color:green"><s:property escape="false" value="errorOutput" /></td>
				</s:if>
				<s:else>
				<td style="background-color:red;"><s:property escape="false" value="errorOutput" /></td>
				</s:else>							
			</td>	
			
		</tr>
	</table>											
	</body>

</html>
