<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<html>

	<head>
		<title>Message</title>

	</head>

	<body>
	<table>
		<tr>
			<s:form>
			<td style="color:red">
				<s:property escape="false" value="outmessage" />
			</td>
			</s:form>	
		</tr>
		<tr>
		<td>
		<s:reset value="关闭窗口" onclick="window.opener=null;window.close();" align="middle"/>
		</td>
		</tr>
	</table>											
	</body>

</html>
