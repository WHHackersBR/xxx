package com.vi.data;

import java.util.ArrayList;

/**
 * Made a Spring bean with this class, so that formData can be shared and accessed easily 
 * on several pages
 * 
 * @author Johannes Sauer
 *
 */
public class FormData {

	private String poNo;
	private String itemNr;
	private String versionAS;
	private String workstationNr;
	private String workstationDescription;
	private String side;
	private String operatorID;
	private String fid;
	private String currentFid;
	private String poCompleted;
	private String viDocReal;
	private String viDoc;
	private String help;
	
	public void setPoCompleted(String poCompleted) {
		this.poCompleted = poCompleted;
	}

	private boolean failed;

	private ArrayList<String> itemList;
	private ArrayList<String> versionASList;
	private ArrayList<String> sideList;
	
	public FormData(){
		//initialize lists, etc.
		itemList = new ArrayList<String>();
		versionASList = new ArrayList<String>();
		sideList = new ArrayList<String>();
		sideList.add("CS");
		sideList.add("SS");
		//sideList.add("V-Flip");
		failed=false;
	}
	
	public String getPoNo() {
		return poNo==null?poNo:poNo.trim();
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
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
	public String getSide() {
		return side;
	}
	public void setSide(String side) {
		this.side = side;
	}
	public String getOperatorID() {
		return operatorID==null?operatorID:operatorID.trim();
	}
	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public ArrayList<String> getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList<String> itemList) {
		this.itemList = itemList;
	}
	public ArrayList<String> getVersionASList() {
		return versionASList;
	}
	public void setVersionASList(ArrayList<String> versionASList) {
		this.versionASList = versionASList;
	}
	public ArrayList<String> getSideList() {
		return sideList;
	}
	public void setSideList(ArrayList<String> sideList) {
		this.sideList = sideList;
	}

	public String getFid() {
		return fid==null?fid:fid.trim();
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getCurrentFid() {
		return currentFid;
	}

	public void setCurrentFid(String currentFid) {
		this.currentFid = currentFid;
	}

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}
	/**
	 * @return the poCompleted
	 */
	public String getPoCompleted() {
		return poCompleted;
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
	 * @return the viDoc
	 */
	public String getViDoc() {
		return viDoc;
	}

	/**
	 * @param viDoc the viDoc to set
	 */
	public void setViDoc(String viDoc) {
		this.viDoc = viDoc;
	}

	/**
	 * @return the help
	 */
	public String getHelp() {
		return help;
	}

	/**
	 * @param help the help to set
	 */
	public void setHelp(String help) {
		this.help = help;
	}

	/**
	 * @param poCompleted the poCompleted to set
	 */
	
	
}
