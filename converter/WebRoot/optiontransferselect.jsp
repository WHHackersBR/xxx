<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<s:head />
</head>
 
<body>
<h1>Struts 2 optiontransferselect example</h1>
 
<s:form action="resultAction" namespace="/" method="POST" >
 
<s:optiontransferselect
     label="Lucky Numbers"
     name="leftNumber"
     list="{'1 - One ', '2 - Two', '3 - Three', '4 - Four', '5 - Five'}"
     doubleName="rightNumber"
     doubleList="{'10 - Ten','20 - Twenty','30 - Thirty','40 - Forty','50 - Fifty'}"
 />
 
<s:optiontransferselect
     label="Favourite Antivirus"
     name="leftAntivirus"
     leftTitle="Left Antivirus Title"
     rightTitle="Right Antivirus Title"
     list="leftAntivirusList"
     multiple="true"
     headerKey="-1"
     headerValue="--- Please Select ---"
     doubleList="rightAntivirusList"
     doubleName="rightAntivirus"
     doubleHeaderKey="-1"
     doubleHeaderValue="--- Please Select ---"
 />
 
<s:submit value="submit" name="submit" />
 
</s:form>
 
</body>
</html>