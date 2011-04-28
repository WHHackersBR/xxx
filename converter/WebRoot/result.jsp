<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
 
<body>
<h1>Struts 2 optiontransferselect example</h1>
<table>
<s:iterator value="leftAntivirusList">
<tr>
<s:property />
</tr>
</s:iterator>
</table>

 
<h4>
   Left AntiVirus : <s:property value="leftAntivirus"/> 
</h4> 
 
<h4>
   Right AntiVirus : <s:property value="rightAntivirus"/> 
</h4> 
 
<h4>
   Left Numbers : <s:property value="leftNumber"/> 
</h4> 
 
<h4>
   Right Numbers : <s:property value="rightNumber"/> 
</h4> 
 
</body>
</html>