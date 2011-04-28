//close pupup and let parent execute addFailureReport-Action
function updateParent() {
	//check that parent is still open
	if (window.opener && !window.opener.closed){  		
		window.opener.location='addFailureReport_Interface.action';
		window.opener.focus();
		window.close();
		
	}
}
//close popup and show parent
function closeNoChanges() {
	//check that parent is still open
	if (window.opener && !window.opener.closed){		
		window.opener.focus();
	}
	window.close();
}	
	