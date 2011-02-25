<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %> 
<html>

<head><title>Svg result</title>

</head>
<body>

<sx:div  executeScripts="true">

   	<embed src="createSvg/generated.svg" width="100%" height="85%"
	type="image/svg-xml"
	pluginspage="http://www.adobe.com/svg/viewer/install/" />   
		
</sx:div>

</body>

</html> 