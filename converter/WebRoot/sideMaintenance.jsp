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
    
    <title><s:text name="sideMaintenanceTitle"/></title>
    
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
     <div id="content">
	   <s:form action="sideContentaction">
     <tr>
		<td><s:textfield labelposition="left" key="pageLabel.partNo" readonly="true"></s:textfield></td>
		<td><s:textfield cssStyle="width:1cm" labelposition="left" key="pageLabel.partAs" readonly="true"></s:textfield></td>
     </tr>
		<s:optiontransferselect
				name="pageLabel.csSide"
				doubleName="pageLabel.ssSide"
				leftTitle="CS"
				rightTitle="SS"
				multiple="true"
				headerKey="-1"
				doubleHeaderKey="-1"
				headerValue="--- Please Select ---"
				doubleHeaderValue="--- Please Select ---"
				list="leftCsList"
				doubleList="rightSsList"
				addToRightLabel="导入右侧>>" 
				addToLeftLabel="导入左侧<<" 
				addAllToRightLabel="全部导入右侧" 
				addAllToLeftLabel="全部导入左侧" 
				selectAllLabel="选中所有" 		 
			/>
	
	    <td><s:submit key="buttonSave" onclick="window.opener=null;window.close();" />
	    	<s:reset key="buttonCancel" onclick="window.opener=null;window.close();" /></td>				   
			
		</s:form>
	</div>

  </body>
</html>
