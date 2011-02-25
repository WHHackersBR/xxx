package com.vi.data;

/**
 * Needed for the passedList and the other list on 
 * allTestsOverview, so Struts2 can iterate through it
 * 
 * @author Johannes Sauer
 *
 */
public class Tested {

	private String fid;
	private String operatorID;
	private String time;
	private String confirmation;
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public String getOperatorID() {
		return operatorID;
	}
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}	
}
