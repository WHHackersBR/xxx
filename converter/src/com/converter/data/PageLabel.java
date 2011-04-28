package com.converter.data;

import java.io.File;
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
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
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

}
