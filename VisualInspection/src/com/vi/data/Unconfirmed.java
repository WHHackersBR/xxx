package com.vi.data;

/**
 * Needed for the unconfirmedList, so Struts2 can iterate through it
 * 
 * @author Johannes Sauer
 *
 */
public class Unconfirmed {

	private String poNo;
	private String workstationNr;
	private String workstationDescription;
	
	
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
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
}
