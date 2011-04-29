package com.vi.data;

/**
 * Needed for the poInformationList on poInformation/History, so Struts2 can iterate through it
 * 
 * @author Johannes Sauer
 *
 */
public class PoPlanInformation {

	private String poNo;
	private String itemNo;
	private String as_;
	private String planQty;
	private String releaseDate;
	/**
	 * @return the poNo
	 */
	public String getPoNo() {
		return poNo;
	}
	/**
	 * @param poNo the poNo to set
	 */
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	/**
	 * @return the itemNo
	 */
	public String getItemNo() {
		return itemNo;
	}
	/**
	 * @param itemNo the itemNo to set
	 */
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	/**
	 * @return the as_
	 */
	public String getAs_() {
		return as_;
	}
	/**
	 * @param as the as_ to set
	 */
	public void setAs_(String as) {
		as_ = as;
	}
	/**
	 * @return the planQty
	 */
	public String getPlanQty() {
		return planQty;
	}
	/**
	 * @param planQty the planQty to set
	 */
	public void setPlanQty(String planQty) {
		this.planQty = planQty;
	}
	/**
	 * @return the releaseDate
	 */
	public String getReleaseDate() {
		return releaseDate;
	}
	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
		
	
}

