package com.vi.data;

/**
 * Needed for the poInformationList on poInformation/History, so Struts2 can iterate through it
 * 
 * @author Johannes Sauer
 *
 */
public class PoInformation {

	private String itemNr;
	private String versionAS;
	private String workstationNr;
	private String workstationDescription;
	private String testedQty;
	private String passedQty;
	private String failedQty;
	
	
	
	public String getItemNr() {
		return itemNr;
	}
	public void setItemNr(String itemNr) {
		this.itemNr = itemNr;
	}
	public String getVersionAS() {
		return versionAS;
	}
	public void setVersionAS(String versionAS) {
		this.versionAS = versionAS;
	}
	public String getWorkstationNr() {
		return workstationNr;
	}
	public void setWorkstationNr(String workstationNr) {
		this.workstationNr = workstationNr;
	}
	public String getWorkstationDescription() {
		return workstationDescription;
	}
	public void setWorkstationDescription(String workstationDescription) {
		this.workstationDescription = workstationDescription;
	}
	public String getTestedQty() {
		return testedQty;
	}
	public void setTestedQty(String testedQty) {
		this.testedQty = testedQty;
	}
	public String getPassedQty() {
		return passedQty;
	}
	public void setPassedQty(String passedQty) {
		this.passedQty = passedQty;
	}
	public String getFailedQty() {
		return failedQty;
	}
	public void setFailedQty(String failedQty) {
		this.failedQty = failedQty;
	}
	
	
}

