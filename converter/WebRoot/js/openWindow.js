//open a popup in which the target will be displayed
function openWindow(target){
	//open new popup window
    //need absolute URL, i think due to security reasons
    //second param must not contain whitespaces!
    dataWin=window.open("http://" + location.host +"/converter/" + target , 
      							"ItemFailureListWindow",  
		         				"width=1100, height=500, left=5, top=0, scrollbars=yes, toolbar=no,location=no, menubar=no, resizable=yes, status=yes");
	//make sure the popup has the correct parent set
	if (dataWin.opener == null) dataWin.opener = parent;    				      
      		//alert("openwindos:"+target);
     dataWin.focus();
   
}