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
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--> 
			
  </head>
  <body>
    <s:form action="uploadAction" id="showSvg">
    <tr>
	 <td><s:file key="filepath" label="本地文件"/> 
	 <sx:submit value="上传文件" targets="svgDiv"/>
	 </td>
	 </tr>
	 </s:form>
	<sx:div id="svgDiv">
	 <embed src=<s:property value="filename"/>
	 		width="100%" height="85%" type="image/svg+xml">
	 </embed>
	</sx:div>

	</body>
</html>
