<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>

	<head>
		<title>Error message</title>

	</head>

	<body>
	<table>
		<tr>
				<td style="color:red"><s:property escape="false" value="outmessage" /></td>
		</tr>
	</table>											
	</body>

</html>
