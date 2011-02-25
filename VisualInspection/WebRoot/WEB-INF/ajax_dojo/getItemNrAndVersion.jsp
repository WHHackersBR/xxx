<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>

<head><title>Item number result</title>
	<!-- link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/interface.css" -->

</head>

<body>
<table>
<tr>
<td><s:select cssStyle="width:3cm;" key="formData.itemNr" labelposition="left" list="formData.itemList" /></td>
<td><s:select cssStyle="width:1cm;" key="formData.versionAS" labelposition="left" list="formData.versionASList" /></td>
</tr>
</table>
</body>

</html> 