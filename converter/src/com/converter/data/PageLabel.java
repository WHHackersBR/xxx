package com.converter.data;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

public class PageLabel {

	private String partNo;
	private String partAs;
	private String side;
	private String userManual;
	private File viDocument;
	private String viDocReview;
	private String viDocReal;
	private List<String> csSide;
	private List<String> ssSide;
	//private List<String> viDocList;
	private Boolean chooseCs;
	private Boolean chooseSs;
	private String contentType;
	private String fileName;
	private String creator;
	private Timestamp createDate;
	private String approver;
	private Timestamp approveDate;
	private String active;
	


	/**
	 * @return the csSide
	 */
	public List<String> getCsSide() {
		return csSide;
	}
	/**
	 * @param csSide the csSide to set
	 */
	public void setCsSide(List<String> csSide) {
		this.csSide = csSide;
	}
	/**
	 * @return the ssSide
	 */
	public List<String> getSsSide() {
		return ssSide;
	}
	/**
	 * @param ssSide the ssSide to set
	 */
	public void setSsSide(List<String> ssSide) {
		this.ssSide = ssSide;
	}
	/**
	 * @return the chooseCs
	 */
	public Boolean getChooseCs() {
		return chooseCs;
	}
	/**
	 * @param chooseCs the chooseCs to set
	 */
	public void setChooseCs(Boolean chooseCs) {
		this.chooseCs = chooseCs;
	}
	/**
	 * @return the chooseSs
	 */
	public Boolean getChooseSs() {
		return chooseSs;
	}
	/**
	 * @param chooseSs the chooseSs to set
	 */
	public void setChooseSs(Boolean chooseSs) {
		this.chooseSs = chooseSs;
	}
	public String getPartAs() {
		return partAs;
	}
	public void setPartAs(String partAs) {
		this.partAs = partAs;
	}
	public String getPartNo() {
		return partNo.toUpperCase();
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo.toUpperCase();
	}
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	/**
	 * @return the userManual
	 */
	public String getUserManual() {
		return userManual;
	}
	/**
	 * @param userManual the userManual to set
	 */
	public void setUserManual(String userManual) {
		this.userManual = userManual;
	}
	/**
	 * @return the viDocument
	 */
	public File getViDocument() {
		return viDocument;
	}
	/**
	 * @param viDocument the viDocument to set
	 */
	public void setViDocument(File viDocument) {
		this.viDocument = viDocument;
	}
	/**
	 * @return the viDocReview
	 */
	public String getViDocReview() {
		return viDocReview;
	}
	/**
	 * @param viDocReview the viDocReview to set
	 */
	public void setViDocReview(String viDocReview) {
		this.viDocReview = viDocReview;
	}
	/**
	 * @return the contentType
	 */
	public String getViDocumentContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setViDocumentContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the fileName
	 */
	public String getViDocumentFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setViDocumentFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the viDocReal
	 */
	public String getViDocReal() {
		return viDocReal;
	}
	/**
	 * @param viDocReal the viDocReal to set
	 */
	public void setViDocReal(String viDocReal) {
		this.viDocReal = viDocReal;
	}
	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the createDate
	 */
	public Timestamp getCreateDate() {
		return createDate;
	}
	/**
	 * @param approver the approver to set
	 */
	public void setApprover(String approver) {
		this.approver = approver;
	}
	/**
	 * @return the approver
	 */
	public String getApprover() {
		return approver;
	}
	/**
	 * @param approveDate the approveDate to set
	 */
	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}
	/**
	 * @return the approveDate
	 */
	public Timestamp getApproveDate() {
		return approveDate;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

}
