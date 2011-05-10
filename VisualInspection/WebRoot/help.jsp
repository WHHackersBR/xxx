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
	使用说明:<br/>
	
	1,同样的订单FID删除与修改只能在与输入机器上相同的工位号上进行<br/>
	2,订单明细中的所有FID是指在本工序上的FID，并非指当前工位<br/>
	3,不安装的零件显示为灰色(取决于是否使用了BOM文件),并且边框很细,鼠标移动到零件上时无弹出信息<br/>
	
	关于鼠标右击功能:<br/>
	1,零件面显示分CS面单独显示,SS面单独显示,以及全部显示<br/>
	2,单层选择可以单独显示各层<br/>
	3,搜索功能将指定条件的零件填充特定颜色显示出来<br/>
		搜索条件包括:零件位置(鼠标移动到零件上方弹出框的开始部分),<br/>
					零件号(鼠标移动到零件上方弹出框的中间部分),<br/>
					零件描述(鼠标移动到零件上方弹出框的最后部分),<br/>
					同型显示,相同零件号的零件,零件号以鼠标最后滑过零件上方所弹出的零件号为准,<br/>
					清除搜索,将上述搜索时所做标志清除.<br/>
	4,Zoom + 放大一个单位,zoom - 缩小一个单位,暂不能指定放大缩小的单位百分比<br/>
  </body>
</html>
