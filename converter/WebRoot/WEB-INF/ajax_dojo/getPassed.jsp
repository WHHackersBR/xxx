<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 
<html>

	<head>
		<title>Current FID result</title>
		<sx:head debug="true" cache="false" compressed="false" />
	</head>

	<body>
		 <table>
			<tr>
				<s:if test="formData.failed">
   					<td><sx:submit key="failure" afterNotifyTopics="get_passed,change_currentID" cssStyle="background-color:red"/></td>
				</s:if>
			    <s:else>
  					<td><sx:submit key="passed" afterNotifyTopics="get_passed,change_currentID" cssStyle="background-color:green"/></td>
				</s:else>					
			</tr>
		</table>
	</body>

</html>
