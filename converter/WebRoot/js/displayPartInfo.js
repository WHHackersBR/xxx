var SVGDocument = null;
var SVGRoot = null;
var lastElement = null;
var dataWin = null;
var absoluteURL= null;
var originalStrokeWidth=null;
var isOriginal=false;
var layerListCS=new Array(5);
var layerListSS=new Array(5);

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
        			txt = txt + "<item" + " checked='no' onactivate='chooseSide(\"" + layerListCS[j].replace(/ /g,"") + "\")'>" + layerListCS[j].replace(/ /g,"") + "</item>\n" ;
        		}
        		for(var j=0;j<layerListSS.length-1 ;j=j+2,layerIndex=layerIndex+1){
        			txt = txt + "<item" + " checked='no' onactivate='chooseSide(\"" + layerListSS[j].replace(/ /g,"") + "\")'>" + layerListSS[j].replace(/ /g,"") + "</item>\n" ;
        		}
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

function chooseSide(side) {
	//var colorIndex=0;
	var useTag=document.getElementsByTagName("path");
	var useTagC=document.getElementsByTagName("circle");
  	for(var i = 0; i < useTag.length;i=i+1 ) {
	  	useTag.item(i).setAttribute("display","none");
  	}
  	for(var i = 0; i < useTagC.length;i=i+1 ) {
	  	useTagC.item(i).setAttribute("display","none");
  	}
	switch (side){
	case "CS":
		//length 1 means a empty layer list
		for(var j=0;j<layerListCS.length-1 ;j=j+2){
			//useTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < useTag.length;i=i+1 ) {
			  	//alert("CS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"") == layerListCS[j].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
			  	useTag.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CSin: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < useTagC.length;i=i+1 ) {
			  	//alert("CSC: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[j].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
			  	useTagC.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CSinC: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		break;
	case "SS":
		for(var j=0;j<layerListSS.length-1;j=j+2){
			//useTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < useTag.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
			  	useTag.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < useTagC.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
			  	useTagC.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		break;
	case "AB":
		//length 1 means a empty layer list
		//alert("CS layerlist:" + layerListCS.length);
		//alert("ss layerlist: " +layerListSS.length);
		for(var j=0;j<layerListCS.length-1 ;j=j+2){
			//useTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < useTag.length;i=i+1 ) {
			  	//alert("CS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[j].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
			  	useTag.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < useTagC.length;i=i+1 ) {
			  	//alert("CS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListCS[j]);
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[j].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
			  	useTagC.item(i).setAttribute("color",layerListCS[j+1]);
			  	//alert("CS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		for(var j=0;j<layerListSS.length-1;j=j+2){
			//useTag=document.getElementsByTagName("path");
		  	for(var i = 0; i < useTag.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
			  	useTag.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		  	for(var i = 0; i < useTagC.length;i=i+1 ) {
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+layerListSS[j]);
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[j].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
			  	useTagC.item(i).setAttribute("color",layerListSS[j+1]);
			  	//alert("SS: "+ i+":"+useTag.item(i).getAttribute("layer")+":"+useTag.item(i).getAttribute("color"));
		  	}
		  	}
		}
		break;
	case "layer01":
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[0].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[0].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	break;
	case "layer02":
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[1].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[1].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	break;
	case "layer03":
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[2].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListCS[2].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	break;
	case "layer04":
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[0].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[0].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	break;
	case "layer05":
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[1].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[1].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	break;
	case "layer06":
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[2].replace(/ /g,"")){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==layerListSS[2].replace(/ /g,"")){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	break;
	default:
	  	for(var i = 0; i < useTag.length;i=i+1 ) {
		  	if(useTag.item(i).getAttribute("layer").replace(/ /g,"")==side){
			  	useTag.item(i).setAttribute("display","inline");
		  	}
		  	}
	  	for(var i = 0; i < useTagC.length;i=i+1 ) {
		  	if(useTagC.item(i).getAttribute("layer").replace(/ /g,"")==side){
			  	useTagC.item(i).setAttribute("display","inline");
		  	}
		  	}
	}
}

//open popup when a part is clicked on
function aMouseClick(evt) {
		//alert("test001");
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

