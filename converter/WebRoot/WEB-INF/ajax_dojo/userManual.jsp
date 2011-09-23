<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 
<%@ page isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title><s:text name="viDocumentMaintenance"/></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/interface.css" >
	
	<s:head />
	<sx:head debug="false" cache="false" compressed="false"/>

  </head>
  
  <body>
    <%
  String role=(String)session.getAttribute("role");
  %>
     <div>
     <table>
    <s:form action="uploadManual" id="uploadManual" method ="POST" enctype ="multipart/form-data">
     <tr>
		<td><s:textfield labelposition="left" key="pageLabel.partNo" readonly="true"></s:textfield></td>
		<td><s:textfield cssStyle="width:1cm" labelposition="left" key="pageLabel.partAs" readonly="true"></s:textfield></td>
		<td><s:reset value="关闭窗口" onclick="window.close();" align="middle"/></td>
    </tr>
    <tr>
		<td><s:textfield key="pageLabel.creator" readonly="true"></s:textfield></td>
		<td><s:textfield key="pageLabel.createDate" readonly="true"></s:textfield></td>
		<td><s:textfield key="pageLabel.approver" readonly="true"></s:textfield></td>
		<td><s:textfield key="pageLabel.approveDate" readonly="true"></s:textfield></td>
		<td><s:textfield key="pageLabel.active" readonly="true"></s:textfield></td>
    </tr>

	<tr>
		<%
		if(role.equalsIgnoreCase("creator"))
		{
		%>
		<td><s:file labelposition="left" key="pageLabel.viDocument"/></td> 
		<td><s:submit cssStyle="width:1.5cm;" key="buttonUpload"/></td>
		<%
		}
		%>
		
	</tr>
	</s:form>
	</table>
	<table>
	<s:form>
		<td><s:text name="pageLabel.viDocReview"/></td>

	<!--tr style="background-color:beige;">
		<td><a href="<s:property value="pageLabel.viDocReal"/>" ><s:property value="pageLabel.viDocReview"  />
			</a></td><td><s:submit key="buttonSave"/></td>
		
	</tr-->
	    <s:iterator value="manualList">
	    <%
		if(role.equalsIgnoreCase("creator"))
		{
		%>
		<s:url id="removeUrl" action="delManualFile" >
		<s:param name="viDocReview" value="viDocReal" />
		</s:url>
		<%
		}
		%>
	
		<tr style="background-color:beige;">
	    	<td><s:a href="%{removeUrl}" ><s:text name="delText"/></s:a></td>
		    <td><a href="<s:property value="viDocReal"/>" >
		    <s:property value="viDocReview"/></a></td>
	    </tr>
	    </s:iterator>
		<!-- Example 1: simple example -->
		<!--s:updownselect
		list="pageLabel.viDocList"
		name="pageLabel.viDocReview"
		headerKey="-1"
		headerValue="--- Please Order Them Accordingly ---"
		emptyOption="true" 
		multiple="false"
		allowMoveUp="false"
		allowMoveDown="false"
		allowSelectAll="false"
	
	/-->
	</s:form>
	</table>		
	</div>
	<s:iterator></s:iterator>
	<br/>
	<br/>
	说明:<br/>
	1,目检附件将被从本地盘上传到服务器上,请保留本地盘文件一周<br/>
	2,上传的目检附件不支持中文文件名(可以上传但无法浏览也无法删除)<br/>
	3,上传的目检附件不大于2M,可以传多个个文件<br/>
	
  </body>
</html>
