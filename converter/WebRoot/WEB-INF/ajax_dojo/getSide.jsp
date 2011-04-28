<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>

	<head>
		<title>Side result</title>

	</head>

	<body>
		<table >
			<tr>
			
				<td><s:radio key="formData.side" labelposition="left" list="formData.sideList" onchange="publish_change_side();"  /></td>
				
			</tr>
		</table>
	</body>

</html>
