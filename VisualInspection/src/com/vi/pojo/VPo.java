package com.vi.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
// default package



/**
 * VPo entity. @author MyEclipse Persistence Tools
 */

public class VPo  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VPoId id;

     private String estand;
     private String revision;
     private BigDecimal qtyPlan;
     private BigDecimal qtyProduce;
     private Timestamp firstTime;
     private Timestamp eventTime;
     private String transferBit;
     private BigDecimal qtyFailure;


    // Constructors

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
	 * @return the qtyPlan
	 */
	public BigDecimal getQtyPlan() {
		return qtyPlan;
	}


	/**
	 * @param qtyPlan the qtyPlan to set
	 */
	public void setQtyPlan(BigDecimal qtyPlan) {
		this.qtyPlan = qtyPlan;
	}


	/**
	 * @return the qtyProduce
	 */
	public BigDecimal getQtyProduce() {
		return qtyProduce;
	}


	/**
	 * @param qtyProduce the qtyProduce to set
	 */
	public void setQtyProduce(BigDecimal qtyProduce) {
		this.qtyProduce = qtyProduce;
	}


	/**
	 * @return the firstTime
	 */
	public Timestamp getFirstTime() {
		return firstTime;
	}


	/**
	 * @param firstTime the firstTime to set
	 */
	public void setFirstTime(Timestamp firstTime) {
		this.firstTime = firstTime;
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
	 * @return the qtyFailure
	 */
	public BigDecimal getQtyFailure() {
		return qtyFailure;
	}


	/**
	 * @param qtyFailure the qtyFailure to set
	 */
	public void setQtyFailure(BigDecimal qtyFailure) {
		this.qtyFailure = qtyFailure;
	}


	/** default constructor */
    public VPo() {
    }

    
    /** full constructor */
    public VPo(VPoId id) {
        this.id = id;
    }

   
    // Property accessors

    public VPoId getId() {
        return this.id;
    }
    
    public void setId(VPoId id) {
        this.id = id;
    }
   








}