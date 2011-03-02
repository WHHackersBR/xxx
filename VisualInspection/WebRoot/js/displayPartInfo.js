var SVGDocument = null;
var SVGRoot = null;
var lastElement = null;
var dataWin = null;
var absoluteURL= null;
var originalStrokeWidth=null;
var isOriginal=false;

function Init(evt,absURL) {
	SVGDocument = evt.target.ownerDocument;
	SVGRoot = document.documentElement;
	absoluteURL=absURL;
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

//open popup when a part is clicked on
function aMouseClick(evt) {
		
		//only react to left mouse button click
		if (evt.getButton()==0) {
			//close old window
      		if (dataWin!==null) {
      			dataWin.close();
      		} 
      		//open new popup window
      		//need absolute URL, i think due to security reasons
      		// second param must not contain whitespaces!
      		dataWin=parent.open(absoluteURL + "/goToFailureReport.action?partname="+  evt.currentTarget.id , 
      							"failureReportWindow",  
		         				"width=600, height=500, left=400, top=0, scrollbars=no, toolbar=no,location=no, menubar=no, resizable=yes, status=yes");
			//make sure the popup has the correct parent set
			if (dataWin.opener == null) dataWin.opener = parent;    				      
      		//parent.document.getElementById("inputFID");
			parent.setFocus_FID();
			//parent.
      		dataWin.focus();
    	}	
		
	
}

