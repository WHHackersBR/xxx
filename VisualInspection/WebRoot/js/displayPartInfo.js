var SVGDocument = null;
var SVGRoot = null;
var lastElement = null;
var dataWin = null;
var absoluteURL= null;
var originalStrokeWidth=null;
var isOriginal=false;
var layerListCS=new Array(5);
var layerListSS=new Array(5);
var compLocation;

//var xmlDoc;
var color=new Array("blueviolet","brown","chartreuse","darkorange","magenta","olivedrab"
		,"orangered","royalblue","tan","yellowgreen");

function Init(evt,absURL,layerListA,layerListB,initSide) {
	SVGDocument = evt.target.ownerDocument;
	SVGRoot = document.documentElement;
	absoluteURL=absURL;
	//oriMenu = contextMenu.firstChild ;
	xmlDoc=getURL("../menudefine.xml",menuLoaded); //载入菜单
	 // var parser=new DOMParser();
	  //alert(xmlDoc.content);
	  //xmlDoc1=parser.parseFromString(xmlDoc.content,"text/xml");
	//alert(xmlDoc1);
	
	//alert(layerListA.substring(1,layerListA.length-1).length);
	//alert(layerListB.substring(1,layerListB.length-1).length);
	/*layerListA=layerListA.substring(1,layerListA.length-1);
	if(layerListA.length > 0){
		layerListCS=layerListA.split(",");
	}*/
	layerListCS=layerListA.substring(1,layerListA.length-1).split(",");
	layerListSS=layerListB.substring(1,layerListB.length-1).split(",");
	
	//alert(layerListCS.length);
	//alert(layerListSS.length);
	chooseSide(initSide);
}
// load the right click menus
function menuLoaded(xmlDoc)
{
	if(xmlDoc.success)
	{
	var newMenuRoot = parseXML(xmlDoc.content, contextMenu);
    //*alert(newMenuRoot.getElementsByTagName("menu").item(0).getAttribute("id"));
    //*alert(newMenuRoot.firstChild.getElementsByTagName("menu"));
	
    var anode = newMenuRoot.childNodes.item(1).childNodes;
    for (var i=0;i < anode.length;i=i+1){
        if ( anode.item(i).hasAttributes() ){
        //alert(anode.item(i).nodeName);
        	if( anode.item(i).getAttribute("id") == "layer" ){
        	    var txt = "<menu id='layer'>\n";
        	    txt = txt + "<header>单层选择 : Layer</header>\n"
        		var layerIndex = 1;	
        		for(var j=0;j<layerListCS.length-1 ;j=j+2,layerIndex=layerIndex+1){
        			txt = txt + "<item" + " checked='no' onactivate='chooseLayer(\"" + layerListCS[j].replace(/ /g,"") + "\")'>" + layerListCS[j].replace(/ /g,"") + "</item>\n" ;
        		}
        		for(var j=0;j<layerListSS.length-1 ;j=j+2,layerIndex=layerIndex+1){
        			txt = txt + "<item" + " checked='no' onactivate='chooseLayer(\"" + layerListSS[j].replace(/ /g,"") + "\")'>" + layerListSS[j].replace(/ /g,"") + "</item>\n" ;
        		}
    			txt = txt + "<item" + " checked='no' onactivate='chooseLayer(\"clearAll\")'>"+ "Clear All" +"</item>\n" ;
        	    txt = txt + "</menu>";
        		//alert(txt);
        	    var layerMenu = parseXML(txt, contextMenu);
        	    //replace the layer select content
        		newMenuRoot.childNodes.item(1).replaceChild(layerMenu,anode.item(i));
                //alert(anode.item(i).getAttribute("id"));
        	}
        }
    }
    //*alert(newMenuRoot.getElementsByTagName("item").item(0).getAttribute("id"));
    //*alert(newMenuRoot.getElementsByTagName("item")).item(0).textContent;
    //*alert(newMenuRoot.ownerDocument.getElementsByTagName("item").item(2).getAttribute("id"));
    contextMenu.replaceChild(newMenuRoot, contextMenu.firstChild);//替换菜单
	}
}

//show and hide the labels
function ShowMyTooltip(evt,display) {
	try {
		var targetElement = evt.target.getParentNode();
		//get <text and <use nodes
		var partInfo = targetElement.getElementsByTagName("text").item(0);
		var useTag= targetElement.getElementsByTagName("use").item(0);

		if(display){	
			compLocation = targetElement.getAttribute("id");
			//change color and stroke width of selected part
			//only change it once/ the first time it's touched
			//otherwise storkeWidth would get huge quickly	
			if(!isOriginal){
				useTag.setAttribute( "color", "rgb(0,0,255)");
				
				originalStrokeWidth=useTag.getAttribute( "stroke-width" );
				var newStrokeWidth=Number(originalStrokeWidth);
				useTag.setAttribute( "stroke-width", String(newStrokeWidth) );
				
				//display the part info
				partInfo.setAttribute( "display", "inline");
				
				isOriginal=true;
			}
		}
		else{			
			//compLocation = "";

			//change color and stroke width back to normal
			useTag.removeAttribute( "color" );
			
			useTag.setAttribute( "stroke-width", originalStrokeWidth );
			
			//hide the part info
			partInfo.setAttribute( "display", "none");
			
			isOriginal=false;
		}
		
				
		
		
	}
	catch (er) {
	}
}

function searchComp(keyword){
	element = document.getElementById("ID_0");
	if (element != null && keyword != "clearHighlight"){
		
		elementUse = element.getElementsByTagName("use");
		if (elementUse != null ){
			for(var m=0; m < elementUse.length; m = m+1 ){
				elementUse.item(m).setAttribute("fill-opacity","0");
			}
		}
	}
	switch (keyword){
	case "refdes":
		refdes = prompt("Reference description:\n请输入零件位置号, 例如:  C701 ","");
		if (refdes != null && trim(refdes) != ""){
			refdes = trim(refdes.toUpperCase());
			element = document.getElementById(refdes);
			if (element != null){
				element = element.getElementsByTagName("use").item(0);
				element.setAttribute("fill", "blue");
				element.setAttribute("fill-opacity", "0.5");
			}else{
				alert(refdes + " does not exist");
			}
		}
		break;
	case "partNumber":
		part = prompt("Part Number:\n请输入零件号,例如: A5E00210763","");
		if (part != null && trim(part) != ""){
			part = trim(part.toUpperCase());
			element = document.getElementById("ID_0");
			if (element != null){
				
				elementxt = element.getElementsByTagName("text");
				if (elementxt != null ){
					for(var m=0; m < elementxt.length; m = m+1 ){
						var elementspan=elementxt.item(m).getElementsByTagName("tspan").item(0);
						if (elementspan != null && elementspan.firstChild !=null && elementspan.firstChild.nodeValue !=null){
							elementspan = elementspan.firstChild.nodeValue;
							elementspan = elementspan.substring(elementspan.indexOf("|"),elementspan.lastIndexOf("|"));
							elementspan = trim(elementspan.substring(elementspan.indexOf(":")+1)).toUpperCase();
							if (elementspan.search(part) != -1){
								elementuse=elementxt.item(m).parentNode.getElementsByTagName("use").item(0);
									elementuse.setAttribute("fill", "blue");
									elementuse.setAttribute("fill-opacity", "0.5");
							}
						}
					}
				}else{
					alert(part + " does not exist");
				}
				
			}else{
				alert(part + " does not exist");
			}
		}
		break;
	case "partDesc":
		desc = prompt("Part label:\n请输入零件描述, 例如:  330u或35v ","");
		if (desc != null && trim(desc) != ""){
			desc = trim(desc).toUpperCase();
			element = document.getElementById("ID_0");
			if (element != null){
				
				elementxt = element.getElementsByTagName("text");
				if (elementxt != null ){
					for(var m=0; m < elementxt.length; m = m+1 ){
						var elementspan=elementxt.item(m).getElementsByTagName("tspan").item(0);
						if (elementspan != null && elementspan.firstChild !=null && elementspan.firstChild.nodeValue !=null){
							elementspan = elementspan.firstChild.nodeValue;
							elementspan = trim(elementspan.substring(elementspan.indexOf("| Label:")+8)).toUpperCase();
							//alert(elementspan);
							if (elementspan.search(desc) != -1){
								elementuse=elementxt.item(m).parentNode.getElementsByTagName("use").item(0);
									elementuse.setAttribute("fill", "blue");
									elementuse.setAttribute("fill-opacity", "0.5");
							}
						}
					}
				}else{
					alert(desc + " does not exist");
				}
				
			}else{
				alert(desc + " does not exist");
			}
		}
		break;
		
	case "samePart":
		//alert(evt);
		if (compLocation != null && compLocation!=""){
			var targetElement = document.getElementById(compLocation);

			var elementspan=targetElement.getElementsByTagName("tspan").item(0);
			if (elementspan != null && elementspan.firstChild !=null && elementspan.firstChild.nodeValue !=null){
				elementspan = elementspan.firstChild.nodeValue;
				elementspan = elementspan.substring(elementspan.indexOf("|"),elementspan.lastIndexOf("|"));
				part = trim(elementspan.substring(elementspan.indexOf(":")+1)).toUpperCase();
				element = document.getElementById("ID_0");
				if (element != null){
					
					elementxt = element.getElementsByTagName("text");
					if (elementxt != null ){
						for(var m=0; m < elementxt.length; m = m+1 ){
							elementspan=elementxt.item(m).getElementsByTagName("tspan").item(0);
							if (elementspan != null && elementspan.firstChild !=null && elementspan.firstChild.nodeValue !=null){
								elementspan = elementspan.firstChild.nodeValue;
								elementspan = elementspan.substring(elementspan.indexOf("|"),elementspan.lastIndexOf("|"));
								elementspan = trim(elementspan.substring(elementspan.indexOf(":")+1)).toUpperCase();
								if (elementspan.search(part) != -1){
									elementuse=elementxt.item(m).parentNode.getElementsByTagName("use").item(0);
										elementuse.setAttribute("fill", "blue");
										elementuse.setAttribute("fill-opacity", "0.5");
								}
							}
						}
					}
				}
			}
		}else{
			alert("Please move over a part")
		}
		break;
		
	case "clearHighlight":
		element = document.getElementById("ID_0");
		if (element != null){
			
			elementUse = element.getElementsByTagName("use");
			if (elementUse != null ){
				for(var m=0; m < elementUse.length; m = m+1 ){
					//elementUse.item(m).setAttribute("fill","white");
					elementUse.item(m).setAttribute("fill-opacity","0");
					//if (elementUse.item(m).parentNode.getAttribute("id") == "C323"){
					//	alert(elementUse.item(m).parentNode.getAttribute("onmousemove")+ 
					//			elementUse.item(m).parentNode.childNodes.item(2).nodeName);
					//}
				}
			}
		}
		break;
	default:
}
}

function chooseSide(side) {
	//var colorIndex=0;
	var pathTag=document.getElementsByTagName("path");
	var circleTag=document.getElementsByTagName("circle");
  	for(var i = 0; i < pathTag.length;i=i+1 ) {
	  	pathTag.item(i).setAttribute("display","none");
  	}
  	for(var i = 0; i < circleTag.length;i=i+1 ) {
	  	circleTag.item(i).setAttribute("display","none");
  	}
	switch (side){
	case "CS":
		//length 1 means a empty layer list
		for(var j=0;j<layerListCS.length-1 ;j=j+2){
			//pathTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < pathTag.length;i=i+1 ) {
			  	//alert("CS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(pathTag.item(i).getAttribute("layer").replace(/ /g,"") == layerListCS[j].replace(/ /g,"")){
			  	pathTag.item(i).setAttribute("display","inline");
			  	pathTag.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CSin: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < circleTag.length;i=i+1 ) {
			  	//alert("CSC: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(circleTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[j].replace(/ /g,"")){
			  	circleTag.item(i).setAttribute("display","inline");
			  	circleTag.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CSinC: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		break;
	case "SS":
		for(var j=0;j<layerListSS.length-1;j=j+2){
			//pathTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < pathTag.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(pathTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	pathTag.item(i).setAttribute("display","inline");
			  	pathTag.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < circleTag.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(circleTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	circleTag.item(i).setAttribute("display","inline");
			  	circleTag.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		break;
	case "AB":
		//length 1 means a empty layer list
		//alert("CS layerlist:" + layerListCS.length);
		//alert("ss layerlist: " +layerListSS.length);
		for(var j=0;j<layerListCS.length-1 ;j=j+2){
			//pathTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < pathTag.length;i=i+1 ) {
			  	//alert("CS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(pathTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[j].replace(/ /g,"")){
			  	pathTag.item(i).setAttribute("display","inline");
			  	pathTag.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < circleTag.length;i=i+1 ) {
			  	//alert("CS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(circleTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[j].replace(/ /g,"")){
			  	circleTag.item(i).setAttribute("display","inline");
			  	circleTag.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		for(var j=0;j<layerListSS.length-1;j=j+2){
			//pathTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < pathTag.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(pathTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	pathTag.item(i).setAttribute("display","inline");
			  	pathTag.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < circleTag.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(circleTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	circleTag.item(i).setAttribute("display","inline");
			  	circleTag.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+pathTag.item(i).getAttribute("layer")+":"+pathTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		break;
	default:
	  	for(var i = 0; i < pathTag.length;i=i+1 ) {
		  	if(pathTag.item(i).getAttribute("layer").replace(/ /g,"")==side){
			  	pathTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < circleTag.length;i=i+1 ) {
		  	if(circleTag.item(i).getAttribute("layer").replace(/ /g,"")==side){
			  	circleTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	}
}

function chooseLayer(layer) {

	var pathTag=document.getElementsByTagName("path");
	var circleTag=document.getElementsByTagName("circle");
	if(layer == "clearAll"){
	  	for(var i = 0; i < pathTag.length;i=i+1 ) {
		  	pathTag.item(i).setAttribute("display","none");
	  	}
	  	for(var i = 0; i < circleTag.length;i=i+1 ) {
		  	circleTag.item(i).setAttribute("display","none");
	  	}
	}else{
	  	for(var i = 0; i < pathTag.length;i=i+1 ) {
		  	if(pathTag.item(i).getAttribute("layer").replace(/ /g,"")==layer){
		  		if (pathTag.item(i).getAttribute("display") == "inline"){
				  	pathTag.item(i).setAttribute("display","none");
		  		}else{
				  	pathTag.item(i).setAttribute("display","inline");
		  		}
		  	}
		  	}
	  	for(var i = 0; i < circleTag.length;i=i+1 ) {
		  	if(circleTag.item(i).getAttribute("layer").replace(/ /g,"")==layer){
		  		if (circleTag.item(i).getAttribute("display") == "inline"){
				  	circleTag.item(i).setAttribute("display","none");
		  		}else{
				  	circleTag.item(i).setAttribute("display","inline");
		  		}
		  	}
		  	}
	}

		

}
function chooseTech(tech) {
	switch (tech){
	case "SMD": 
		element = document.getElementById("ID_0");
		if (element != null){
			elementComp = element.getElementsByTagName("use");
			//alert(element.childNodes.);
			if (elementComp != null ){
				for(var m=0; m < elementComp.length; m = m+1 ){
					var elementTech=elementComp.item(m).parentNode;
					//alert(elementTech.getAttribute("tech"));
					//break;
					if (elementTech != null && elementTech.getAttribute("tech") == "SMD"){
						elementTech.setAttribute("display","inline");
					}else{
						elementTech.setAttribute("display","none");
					}
				}
			}
		}
		break;
	case "THT": 
		element = document.getElementById("ID_0");
		if (element != null){
			elementComp = element.getElementsByTagName("use");
			//alert(element.childNodes.);
			if (elementComp != null ){
				for(var m=0; m < elementComp.length; m = m+1 ){
					var elementTech=elementComp.item(m).parentNode;
					//alert(elementTech.getAttribute("tech"));
					//break;
					if (elementTech != null && elementTech.getAttribute("tech") == "THRU"){
						elementTech.setAttribute("display","inline");
					}else{
						elementTech.setAttribute("display","none");
					}
				}
			}
		}
		break;
	case "ALL": 
		element = document.getElementById("ID_0");
		if (element != null){
			elementComp = element.getElementsByTagName("use");
			//alert(element.childNodes.);
			if (elementComp != null ){
				for(var m=0; m < elementComp.length; m = m+1 ){
					var elementTech=elementComp.item(m).parentNode;
					//alert(elementTech.getAttribute("tech"));
					//break;
					if (elementTech != null ){
						elementTech.setAttribute("display","inline");
					}
				}
			}
		}
		break;
	}
}

//open popup when a part is clicked on
function aMouseClick(evt) {
		//alert(evt);
		//only react to left mouse button click
		if (evt.getButton()==0) {
			//close old window
      		if (dataWin!==null) {
      			dataWin.close();
      		} 
      		//open new popup window
      		//need absolute URL, i think due to security reasons
      		// second param must not contain whitespaces!
      		//alert("abc"+ window);
      		//parent.open(absoluteURL);
      		//dataWin=parent.open("http://" + location.host +"/VisualInspection/goToFailureReport.action?partname="+  evt.currentTarget.id);
      		dataWin=parent.open(absoluteURL + "/goToFailureReport.action?partname="+  evt.currentTarget.id, 
      							"failureReportWindow",  
		         				"width=600, height=500, left=400, top=0, scrollbars=no, toolbar=no,location=no, menubar=no, resizable=yes, status=yes");
			
      		//make sure the popup has the correct parent set
      		
			if (dataWin == null) {
				alert("Cannot open new window");
				//parent.setFocusFID();
			}//else{*/
				
				//if (dataWin.opener == null) dataWin.opener = parent;  
	      		//parent.document.getElementById("inputFID");
				//parent.setFocusFID();
				
	      		//dataWin.focus();
			//}
      		
    	}
}
		
function trim(str){  //删除左右两端的空格
	 return str.replace(/(^\s*)|(\s*$)/g, "");
	}
function ltrim(str){  //删除左边的空格
 return str.replace(/(^\s*)/g,"");
}
function rtrim(str){  //删除右边的空格
 return str.replace(/(\s*$)/g,"");
}
