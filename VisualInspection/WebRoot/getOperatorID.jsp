<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page isELIgnored="false"%>
<html>

	<head>
		<title>operatorID result</title>

	</head>

	<body>
		<table>
			<tr>
				<td><s:textfield key="formData.operatorID" size="15" onblur="publish_change_operatorID();" /></td>													
			</tr>	
		</table>
	</body>

</html>
