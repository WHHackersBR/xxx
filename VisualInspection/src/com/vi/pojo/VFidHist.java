package com.vi.pojo;

import java.sql.Timestamp;



/**
 * VFidHist entity. @author MyEclipse Persistence Tools
 */

public class VFidHist  implements java.io.Serializable {


    // Fields    

     private VFidHistId id;

     private String articleNo;
     private String estand;
     private String revision;
     private String machId;
     private Timestamp createTime;
     private Timestamp eventTime;
     private String poNo;
     private String machType;
     private String psw;
     private String transferBit;
     private String fidEnd;
     private String quantity;

    // Constructors

    /** default constructor */
    public VFidHist() {
    }

    
    /** full constructor */
    public VFidHist(VFidHistId id) {
        this.id = id;
    }

   
    // Property accessors

    public VFidHistId getId() {
        return this.id;
    }
    
    public void setId(VFidHistId id) {
        this.id = id;
    }


	/**
	 * @return the articleNo
	 */
	public String getArticleNo() {
		return articleNo;
	}


	/**
	 * @param articleNo the articleNo to set
	 */
	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}


	/**
	 * @return the estand
	 */
	public String getEstand() {
		return estand;
	}


	/**
	 * @param estand the estand to set
	 */
	public void setEstand(String estand) {
		this.estand = estand;
	}


	/**
	 * @return the revision
	 */
	public String getRevision() {
		return revision;
	}


	/**
	 * @param revision the revision to set
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}


	/**
	 * @return the machId
	 */
	public String getMachId() {
		return machId;
	}


	/**
	 * @param machId the machId to set
	 */
	public void setMachId(String machId) {
		this.machId = machId;
	}


	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}


	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}


	/**
	 * @return the eventTime
	 */
	public Timestamp getEventTime() {
		return eventTime;
	}


	/**
	 * @param eventTime the eventTime to set
	 */
	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}


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
	 * @return the machType
	 */
	public String getMachType() {
		return machType;
	}


	/**
	 * @param machType the machType to set
	 */
	public void setMachType(String machType) {
		this.machType = machType;
	}


	/**
	 * @return the psw
	 */
	public String getPsw() {
		return psw;
	}


	/**
	 * @param psw the psw to set
	 */
	public void setPsw(String psw) {
		this.psw = psw;
	}


	/**
	 * @return the transferBit
	 */
	public String getTransferBit() {
		return transferBit;
	}


	/**
	 * @param transferBit the transferBit to set
	 */
	public void setTransferBit(String transferBit) {
		this.transferBit = transferBit;
	}


	/**
	 * @return the fidEnd
	 */
	public String getFidEnd() {
		return fidEnd;
	}


	/**
	 * @param fidEnd the fidEnd to set
	 */
	public void setFidEnd(String fidEnd) {
		this.fidEnd = fidEnd;
	}


	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}


	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
   








}