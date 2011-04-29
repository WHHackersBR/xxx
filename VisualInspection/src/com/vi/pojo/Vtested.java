package com.vi.pojo;

import java.sql.Timestamp;
// default package



/**
 * Vtested entity. @author MyEclipse Persistence Tools
 */

public class Vtested  implements java.io.Serializable {


    // Fields    

     private VtestedId id;
     private String testResult;
     private String machId;
     private String machTime;
     private String estand;
     private String revision;
     private String transferBit;
     private String psw;
     private String articleNo;
     private String confirm;
     private String msg1;
     private Timestamp eventTime;


    // Constructors

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
	 * @return the testResult
	 */
	public String getTestResult() {
		return testResult;
	}


	/**
	 * @param testResult the testResult to set
	 */
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}


	/**
	 * @return the machTime
	 */
	public String getMachTime() {
		return machTime;
	}


	/**
	 * @param machTime the machTime to set
	 */
	public void setMachTime(String machTime) {
		this.machTime = machTime;
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
	 * @return the confirm
	 */
	public String getConfirm() {
		return confirm;
	}


	/**
	 * @param confirm the confirm to set
	 */
	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}


	/**
	 * @return the msg1
	 */
	public String getMsg1() {
		return msg1;
	}


	/**
	 * @param msg1 the msg1 to set
	 */
	public void setMsg1(String msg1) {
		this.msg1 = msg1;
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


	/** default constructor */
    public Vtested() {
    }

    
    /** full constructor */
    public Vtested(VtestedId id) {
        this.id = id;
    }

   
    // Property accessors

    public VtestedId getId() {
        return this.id;
    }
    
    public void setId(VtestedId id) {
        this.id = id;
    }
   








}