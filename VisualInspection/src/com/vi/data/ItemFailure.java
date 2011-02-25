package com.vi.data;

/**
 * Needed for the failuresList on item failures page, so Struts2 can iterate through it
 * 
 * @author Johannes Sauer
 *
 */
public class ItemFailure {

	private String fid;
	private String pieceLocation; //=private String partname;
	private String componentLocation; //=private String positionNr;	
	private String failureCode;
	private String failureDefinition;
	private String failureDescription;
	private String time;
	
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getPieceLocation() {
		return pieceLocation;
	}
	public void setPieceLocation(String pieceLocation) {
		this.pieceLocation = pieceLocation;
	}
	public String getComponentLocation() {
		return componentLocation;
	}
	public void setComponentLocation(String componentLocation) {
		this.componentLocation = componentLocation;
	}
	public String getFailureCode() {
		return failureCode;
	}
	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}
	public String getFailureDescription() {
		return failureDescription;
	}
	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}
	public String getFailureDefinition() {
		return failureDefinition;
	}
	public void setFailureDefinition(String failureDefinition) {
		this.failureDefinition = failureDefinition;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
