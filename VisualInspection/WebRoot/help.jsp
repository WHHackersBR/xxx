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
  
  <script type="text/javascript">
  //alert(opener.document.getElementById("pageLabel.partNo").value);
  //alert(opener.${pageLabel.partNo});
  //${pageLabel.partNo}=opener.${pageLabel.partNo};
	function checkInput(){
		if(document.getElementById("inputPOS").value==""){
		alert("请输入子板位置");
		return false;
		}else if(document.getElementById("inputFailCode").value=="9999"){
		alert("请选择错误代码");
		return false;
		}else{
		return true;
	    }
	}
  </script>

  	
  <body>
     <div>
     <table>
    <s:form method ="POST" enctype ="multipart/form-data">
     <tr>
		<td><s:textfield labelposition="left" key="formData.itemNr" readonly="true"></s:textfield></td>
		<td><s:textfield cssStyle="width:1cm" labelposition="left" key="formData.versionAS" readonly="true"></s:textfield></td>
		<td><s:reset value="关闭窗口" onclick="window.close();" align="middle"/></td>
	</tr>

	<tr>
		<td><s:text name="formData.viDoc"/></td>
	</tr>

	<tr>
		<td><a href="<s:property value="formData.viDocReal"/>" ><s:property value="formData.viDoc"  />
			</a></td>
		
	</tr>
	</s:form>
	</table>		
	</div>
	<s:iterator></s:iterator>
	<br/>
	<br/>
	说明:<br/>
	1,未完待续<br/>
	2,<br/>
	3,<br/>
	
  </body>
</html>
