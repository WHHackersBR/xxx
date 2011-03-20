<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 
<%@ page isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    
    <title>图形文件上传及格式转换 DXF->SVG</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<s:head />
	<sx:head debug="false" cache="false" compressed="false" />

	<link rel="stylesheet" type="text/css" href="./css/interface.css">
				
  </head>
  
  <script type="text/javascript" src="./js/openWindow.js"></script>
  <script type="text/javascript">
  	dojo.event.topic.subscribe("/sideMainenance", function(data, request, widget){
   
    //data : text returned from request(the html)
    //request: XMLHttpRequest object
    //widget: widget that published the topic
    //alert("openwindos:");
	openWindow('sideMaintenance.action');
	});
  </script>
  
  <body>
  <div id="content"	align="left" style="background-color: beige;">
  <table>
    <tr>
    <s:form action="uploadAction" id="showSvg">
		 <td Style="height:1cm;"><s:file labelposition="left" key="pageLabel.filePath"/></td> 
		 <td><sx:submit cssStyle="width:1.5cm;" key="pageLabel.uploadFile" targets="photoDiv"/></td>
	</s:form>

    <s:form action="sideMaintenance" target="_blank">
			<td><s:hidden key="pageLabel.partNo"></s:hidden></td>
		<td><s:hidden key="pageLabel.partAs"></s:hidden></td>
		 <td><s:submit cssStyle="width:2cm;" key="pageLabel.SideLayer"/></td>
    </s:form>
    <!--<s:form action="optionTransferSelectAction">
		 <td><sx:submit cssStyle="width:2cm;" key="pageLabel.SideLayer"/></td>
    </s:form > action="loadSvg" 
    
    -->
   
    <s:form action="loadSvg">
		<td><s:textfield labelposition="left" key="pageLabel.partNo"></s:textfield></td>
		<td><s:textfield cssStyle="width:1cm" labelposition="left" key="pageLabel.partAs"></s:textfield></td>
	    <td><s:checkbox labelposition="left" key="pageLabel.csSide" value="true"/></td>
	    <td><s:checkbox labelposition="left" key="pageLabel.ssSide" value="true"/></td>
        <td><sx:submit cssStyle="width:1.5cm;" key="pageLabel.showPhoto" targets="photoDiv" /></td>			    		
    </s:form>
	</tr>
  </table>
  </div>
	<sx:div id="photoDiv" executeScripts="true">
   		<embed src=<s:property value="filename"/> width="100%" height="85%"
		type="image/svg-xml"/>   
	</sx:div>

	</body>
</html>
