var doc;
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
var tx = 0, ty = 0;
var lastX;
var lastY;
var down = false;
var krotax,krotay;
var g = document.getElementById( "movement" );

//var xmlDoc;
var color=new Array("blueviolet","brown","chartreuse","darkorange","magenta","olivedrab"
		,"orangered","royalblue","tan","yellowgreen");

function Init(evt,absURL,layerListA,layerListB,initSide) {
	SVGDocument = evt.target.ownerDocument;
	SVGRoot = document.documentElement;
	absoluteURL=absURL;
	doc = evt.getTarget().getOwnerDocument();
	svg1 = doc.getDocumentElement();
	g.addEventListener( "mousedown", Down, false );
	g.addEventListener( "mouseup", Up, false );
	g.addEventListener( "mousemove", Move, false );
	
	  // to set Event at SVG-Doc 

	  //svg1.setAttribute("onkeydown", "suchen(evt)");

	  //svg1.setAttribute("onmousemove", "pcb_locate_drag(evt)");
	  //svg1.setAttribute("width", "100%");
	  //svg1.setAttribute("height", "100%");

	svg1Viewbox = ""+svg1.getAttribute("viewBox");
	  // to detect Pixel of Viewbox
	  var vbox = svg1Viewbox.split( ' ' );
	  svg1Vx = Number(vbox[0]);
	  svg1Vy = Number(vbox[1]);
	  svg1Vwidth = Number(vbox[3]);
	  svg1Vheight = Number(vbox[4]); 
	  //alert("svg1Vx & svg1Vwidth & svg1Vheight " + svg1Vx + ":" + svg1Vy + ":" + svg1Vwidth + ":" + svg1Vheight)

	//oriMenu = contextMenu.firstChild ;
	xmlDoc=getURL("../menudefine.xml",menuLoaded); //载入菜单
	 // var parser=new DOMParser();
	  //alert(xmlDoc.content);
	  //xmlDoc1=parser.parseFromString(xmlDoc.content,"text/xml");
	/*layerListA=layerListA.substring(1,layerListA.length-1);
	if(layerListA.length > 0){
		layerListCS=layerListA.split(",");
	}*/
	layerListCS=layerListA.substring(1,layerListA.length-1).split(",");
	layerListSS=layerListB.substring(1,layerListB.length-1).split(",");
	
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
        	    txt = txt + "<header>单层选择</header>\n"
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
        	}
        }
    }
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
				useTag.setAttribute( "color", "rgb(0,255,255)");
				//useTag.setAttribute( "box-shadow", "rgb(0,255,255)");
				
				originalStrokeWidth=useTag.getAttribute("stroke-width");
				var newStrokeWidth=Number(originalStrokeWidth);
				useTag.setAttribute( "stroke-width", String(newStrokeWidth*3) );
				
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
	if (element != null){
		
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
      		dataWin=parent.open(absoluteURL + "/goToFailureReport.action?partname="+  encodeURIComponent(evt.currentTarget.id), 
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

function panelError(evt){
  		if (dataWin!==null) {
  			dataWin.close();
  		} 
  		dataWin=parent.open(absoluteURL + "/goToFailureReport.action?partname=" + encodeURIComponent("#LP"), 
  							"failureReportWindow",  
	         				"width=600, height=500, left=400, top=0, scrollbars=no, toolbar=no,location=no, menubar=no, resizable=yes, status=yes");
		
  		//make sure the popup has the correct parent set
  		
		if (dataWin == null) {
			alert("Cannot open new window");
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

//drawing rotate
function rotate(degree){

  //calculate  and rotate image position 
 //rotate position
  rotx =  svg1Vx+(svg1Vwidth/2);
  roty =  svg1Vy+(svg1Vheight/2);
  
  //rotate image
  rott = doc.getElementById("draft");
  transform = rott.getAttribute('transform');
  transform = transform.substring(transform.indexOf("matrix"),transform.length);
  rott.setAttribute('transform', "rotate("+ degree +" "+ rotx +" "+ roty +")" + transform);
	element = document.getElementById("ID_0");
	if (element != null){
		var transformTxt;
		elementTxt = element.getElementsByTagName("text");
		if (elementTxt != null ){
			for(var m=1; m < elementTxt.length; m = m+1 ){
				transformTxt = elementTxt.item(m).getAttribute("transform");
				transformTxt = transformTxt.substring(0,
						(transformTxt.indexOf("rotate")==-1 ? transformTxt.length : transformTxt.indexOf("rotate")));
				elementTxt.item(m).setAttribute("transform", transformTxt + "rotate(" + -degree +")");
			}
		}
	}

  //LabelWinkel customize
 /*       var gover = doc.getElementsByTagName("g");
        var anzahlid = gover.getLength();
        for (var i=0;i<anzahlid;i++){
        var aktid = gover.item(i);
        goverid = aktid.getAttribute("id");
                if  (goverid == "") {} else {
                gkz = goverid.substr(0,1);
                gkz1 = goverid.substr(1,10);
                gkz2 = goverid.substr(1,1);
                if  (gkz2 <= "9") {continue;}
                    if  (gkz == "_") {
                   EP = gkz+gkz1;

                   setzt = doc.getElementById(goverid);
                   rottag=setzt.getFirstChild();
                   rottrans = rottag.getAttribute("transform");

              var rottrans = rottrans.split(" ");
              rottransw1 = String(rottrans[6]);
              var rottransw2 = rottransw1.split("(");
              rottransw = Number(rottransw2[1]);
              rottransx = String(rottrans[7]);
              rottransw1 = String(rottrans[8]);
              var rottransw2 = rottransw1.split(")");
              rottransy = Number(rottransw2[0]);
              rottransue = ((rottrans[0])+" "+(rottrans[1])+" "+(rottrans[2])+" "+(rottrans[3])+" "+(rottrans[4])+" "+(rottrans[5]));

              if (rota == 0)    {if (rottransw == 270){rottransw = 90}; if (rottransw == 180){rottransw = 0}  }
              if (rota == 90)   {if (rottransw == 90){rottransw = 270}; if (rottransw == 180){rottransw = 0}   }
              if (rota == 180)  {if (rottransw == 90){rottransw = 270}; if (rottransw == 0){rottransw = 180}   }
              if (rota == 270)  {if (rottransw == 270){rottransw = 90}; if (rottransw == 0){rottransw = 180}   }

              rottag.setAttribute("transform",""+rottransue+" rotate("+rottransw+" "+rottransx+" "+rottransy+")");
                    }
                }
        }


  //calculate scale 
        rotscale1 = svg1Vheight/Zgrossw;
        rotscale2 = svg1Vwidth/Zgrossh;
        if (rotscale1 <= rotscale2) {rotscale = rotscale1} else {rotscale = rotscale2}
        if  (rotscale > 1.1) {krotv = "-"}
        if  (rotscale < 0.9) {krotv = "+"}
        if  (rotscale >= 0.9 && rotscale <= 1.1){krotv = "-";}

  //customize view window 
      if (rotscale == 1){}else{
         
       if (rota == 90 || rota == 270){
       var deltaf = (svg1Vwidth-svg1Vheight)/2
       svg1.setCurrentScale(rotscale*0.95);
       trans1 =  svg1.getCurrentTranslate();
       trans1.setX(krotv+deltaf*rotscale);
       trans1.setY(krotv+deltaf*rotscale);
       }
       if (rota == 0 || rota == 180){
       
        if (AeLage == 1){
           svg1.setCurrentScale(0.9);
           trans1 =  svg1.getCurrentTranslate();
           trans1.setX((Zgrossw-Zgrossw*0.9)/2 );
           trans1.setY((Zgrossh-Zgrossh*0.9)/2 );
           }else{
           svg1.setCurrentScale(1);
           trans1 =  svg1.getCurrentTranslate();
           trans1.setX(0);
           trans1.setY(0);
           }
       }
      }


  //edit kontext menue 
        layaerst1 = ("Drehen"+rota);
        UpdateMenu(null, layaerst1, 'enabled', 'Dreh', false);
        UpdateMenu(null, layaerst2, 'enabled', 'Dreh', false);
        layaerst2 = layaerst1;

*/
}

//key events
function suchen(evt){
  
  if (evt == "[object KeyEvent]"){
     var key = evt.getKeyCode();
     }else{key = evt}
    //alert("key"+key);

    // key W call lable
    if (key == 87){
       Beztausch("label1")};

    // key E call refdes
    if (key == 69){
      Beztausch("gkz1")};

    // key Z call refdes
    if (key == 90){
    Beztausch("gkz2")};

    // key P search same BOMPos
    if (key == 80){
      GruppenMark('P')};

    // key A search Attributen a. same BE
    if (key == 65){
      GruppenMark('+')};

    // key T call Teil-Step
    if (key == 84){
        if (Tstep == 0){Tstep = 1;
        Meldung2('Section-Step on','#f00',2500)
        }else {
        Tstep = 0;
        Meldung2('Section-Step off','#0f0',2500)
        } 
        UpdateMenu(null, 'Tstepm', 'checked', null, false);
        PosiLoopresetall()
        if (Tstep == 1){
          for (var i=0;i<1000;i++){
          loopct = loopc + 1;
          suchidmd = doc.getElementById("P_"+loopct);
          leer4 = "!"+suchidmd+"!";
            if (leer4 == "!null!"){
            loopc = 0;
            suchen (40);
              break;
            }
          suchen (40);
          }
       }
     }  
    // key S BE-search
    if (key == 83){
       suchenEP(evt)};

    //key Esc reset
    if (key == 27){
      reseten()};

    //Loop +
    if (key == 40){
       suchidmd = doc.getElementById("Infomeld");
       leer4 = "!"+suchidmd+"!";
       if (leer4 != "!null!"){
       suchidmd1 = suchidmd.getFirstChild();
       suchidmd2 = suchidmd1.getData();
       if (suchidmd2=="P: Letzte "){
       return;
       }
       }
      Skip2 = "ja"
      PosiLoop('+')};

    //Loop -
    if (key == 38){
      LoopMark = LoopMark-2;
      PosiLoop('-')};

     //Loop ++
    if (key == 39){
       suchidmd = doc.getElementById("Infomeld");
       leer4 = "!"+suchidmd+"!";
       if (leer4 != "!null!"){
       suchidmd1 = suchidmd.getFirstChild();
       suchidmd2 = suchidmd1.getData();
       if (suchidmd2=="P: Letzte "){
       PosiLoopresetall();
       }else{
       PosiLoop('++');}
       return;}
       PosiLoop('+');
       PosiLoop('++')};

    //Loop --
    if (key == 37){
      PosiLoop('--')};

    //key F fixieren
    if (key == 70){
      Masters()};

    //key G font size
    if (key == 71){
      Schriftgross()};
      
    //not for SNC key Leer Ersatztypen
    if (key == 32){
    // Ersatz()
    };
      
    //key Q QData
    if (key == 81){
     // QDaten("0")
     };

    //key M measure
    if (key == 77){
      Drehen(0);
      reseten();
      Meldung1("Measure ! - only rotate 0 -","#000");
      MessenOn = "On";}
      
    //key H help
    if (key == 72){
      Hilfe()};


      
}

//mouse position
function pcb_locate_drag(evt) {
    //alert("bullshit")
    pcX0 = evt.getClientX() ;
    pcY0 = evt.getClientY() ;

    //alert(pcX0)
 
// measure   
 //if ( rota == 0 && 1 == 0){   
    //if (  1 == 0){
  //set.svgBox Pixel 
    svg1.setAttribute("width", svg1Vwidth);
    svg1.setAttribute("height", svg1Vheight);
    
  //readout window data
    trans3m =  svg1.getCurrentTranslate();
    getxm = trans3m.getX();
    getym = trans3m.getY();
    getsm = svg1.getCurrentScale();
    //alert(getxm)
  //readout scale from DB-Daten  
	//  alert(doc);
    //var TScaleid = doc.getElementById("SCALE");
	//alert(TScaleid);
    //var TScaleid = TScaleid.getFirstChild();
    //var TScalei = Number (TScaleid.getData());
	//alert(TScalei);
    
  //recalculate x y data from DB-daten 
    pcX0m = (svg1Vx+(pcX0-getxm)/getsm-krotax);
    //alert(svg1Vx+":" + pcX0 +":"+ getxm+":"+getsm+":"+krotax);
   //if (si == 0){
       pcY0m = ((svg1Vy+(pcY0-getym)/getsm-krotay))-5;
    //   }else{ 
    //   pcY0m = (((svg1Vy-(pcY0-getym)/getsm-krotay)+svg1Vheight))-5;
    //   } 
       
  //format x y display  
    pcX0ma =  String(pcX0m).substring(0,(String(pcX0m).indexOf("."))+4);
    pcY0ma =  String(pcY0m).substring(0,(String(pcY0m).indexOf("."))+4);
    
    
  //x y display
    mtext = "x:"+pcX0ma+" y:"+pcY0ma;
    
    Meldung(mtext,'#F44')
      minfo = doc.getElementById("Infomeld");
      minfo.setAttribute('style', "text-anchor:middle;fill:#F44")
      minfo.setAttribute('y', (svg1Vy+(pcY0+20-getym)/getsm));
      minfo.setAttribute('x', (svg1Vx+(pcX0-getxm)/getsm));
  //}

    
}


//add move function
function Down(evt)
{
  lastX = evt.clientX
  lastY = evt.clientY
  down = true;
    //alert("x:"+lastX + " y:" +lastY);
}

function Up(evt)
{
	
  down = false;
 
}

function Move(evt)
{
  if( down==false )
    return;
 
  var x = evt.clientX
  var y = evt.clientY
  var dx = x - lastX
  var dy = y - lastY
  lastX = x
  lastY = y
  if( evt.shiftKey )
  {
    document.rootElement.currentTranslate.x += dx
    document.rootElement.currentTranslate.y += dy
  }
  else
  {
  	dx = dx / 150;
  	dy = dy / 150;
    tx += dx
    ty += dy
    g.setAttribute( "transform", "translate("+tx+","+ty+")" )
  }
}

//display message 
function Meldung(Infotext,Infotextc){
      Meldungsreset()

      var texta = doc.createElement('text');
      texta.setAttribute('y', svg1Vy+10);
      texta.setAttribute('x', svg1Vx+5);
      texta.setAttribute('id', "Infomeld");
  /* CSS set */
      texta.setAttribute('style', "font-family:Arial;font-size:"+ 1.1*11/svg1.getCurrentScale()+" pt;fill:#000;stroke:"+Infotextc+";stroke-width:0.5")
  /* Text set */
      textai = doc.createTextNode(Infotext);
      texta.appendChild(textai);
  /* Knoten zum Baum hinzufuegen */
      var parent = doc.getDocumentElement();
      parent.appendChild(texta);
}

//reset display message 
function Meldungsreset(){
       suchidan = doc.getElementById("Infomeld");
       leer4 = "!"+suchidan+"!";
       if (leer4 == "!null!"){return;}
       Knoten = suchidan.getParentNode();
       Knoten.removeChild(suchidan);
}



