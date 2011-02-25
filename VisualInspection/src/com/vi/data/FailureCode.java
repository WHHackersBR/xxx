package com.vi.data;

/**
 * Needed for the failureCodesList, so Struts2 can iterate through it
 * 
 * @author Johannes Sauer
 *
 */
public class FailureCode {
	private String failureCodeAbbr;
	private String failurCodeName;
	
	public String getFailureCodeAbbr() {
		return failureCodeAbbr;
	}
	public void setFailureCodeAbbr(String failureCodeAbbr) {
		this.failureCodeAbbr = failureCodeAbbr;
	}
	public String getFailurCodeName() {
		return failurCodeName;
	}
	public void setFailurCodeName(String failurCodeName) {
		this.failurCodeName = failurCodeName;
	}	
}
