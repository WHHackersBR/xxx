package com.vi.pojo;

import java.sql.Timestamp;

/**
 * VFailureId entity. @author MyEclipse Persistence Tools
 */

public class VFailureId implements java.io.Serializable {

	// Fields

	private String fid;
	private String machType;
	private String testItem;
	private String rateValue;
	private String measValue;
	//private String tolerance;
	//private String machId;
	//private String machTime;
	//private String transferBit;
	private String errorCode;
	//private String description;
	//private String msg1;
	//private Timestamp eventTime;

	// Constructors

	/** default constructor */
	public VFailureId() {
	}

	/** full constructor */
	public VFailureId(String fid, String machType, String testItem, String rateValue,
			String measValue, String errorCode) {
		this.fid = fid;
		this.testItem = testItem;
		this.rateValue = rateValue;
		this.measValue = measValue;
		//this.tolerance = tolerance;
		//this.machId = machId;
		this.machType = machType;
		//this.machTime = machTime;
		//this.transferBit = transferBit;
		this.errorCode = errorCode;
		//this.description = description;
		//this.msg1 = msg1;
		//this.eventTime = eventTime;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getTestItem() {
		return this.testItem;
	}

	public void setTestItem(String testItem) {
		this.testItem = testItem;
	}

	public String getRateValue() {
		return this.rateValue;
	}

	public void setRateValue(String rateValue) {
		this.rateValue = rateValue;
	}

	public String getMeasValue() {
		return this.measValue;
	}

	public void setMeasValue(String measValue) {
		this.measValue = measValue;
	}

	public String getMachType() {
		return this.machType;
	}

	public void setMachType(String machType) {
		this.machType = machType;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VFailureId))
			return false;
		VFailureId castOther = (VFailureId) other;

		return ((this.getFid() == castOther.getFid()) || (this.getFid() != null
				&& castOther.getFid() != null && this.getFid().equals(
				castOther.getFid())))
				&& ((this.getTestItem() == castOther.getTestItem()) || (this
						.getTestItem() != null
						&& castOther.getTestItem() != null && this
						.getTestItem().equals(castOther.getTestItem())))
				&& ((this.getRateValue() == castOther.getRateValue()) || (this
						.getRateValue() != null
						&& castOther.getRateValue() != null && this
						.getRateValue().equals(castOther.getRateValue())))
				&& ((this.getMeasValue() == castOther.getMeasValue()) || (this
						.getMeasValue() != null
						&& castOther.getMeasValue() != null && this
						.getMeasValue().equals(castOther.getMeasValue())))
				//&& ((this.getTolerance() == castOther.getTolerance()) || (this
				//		.getTolerance() != null
				//		&& castOther.getTolerance() != null && this
				//		.getTolerance().equals(castOther.getTolerance())))
				//&& ((this.getMachId() == castOther.getMachId()) || (this
				//		.getMachId() != null
				//		&& castOther.getMachId() != null && this.getMachId()
				//		.equals(castOther.getMachId())))
				&& ((this.getMachType() == castOther.getMachType()) || (this
						.getMachType() != null
						&& castOther.getMachType() != null && this
						.getMachType().equals(castOther.getMachType())))
			/*	&& ((this.getMachTime() == castOther.getMachTime()) || (this
						.getMachTime() != null
						&& castOther.getMachTime() != null && this
						.getMachTime().equals(castOther.getMachTime())))
				&& ((this.getTransferBit() == castOther.getTransferBit()) || (this
						.getTransferBit() != null
						&& castOther.getTransferBit() != null && this
						.getTransferBit().equals(castOther.getTransferBit())))*/
				&& ((this.getErrorCode() == castOther.getErrorCode()) || (this
						.getErrorCode() != null
						&& castOther.getErrorCode() != null && this
						.getErrorCode().equals(castOther.getErrorCode())))
			/*	&& ((this.getDescription() == castOther.getDescription()) || (this
						.getDescription() != null
						&& castOther.getDescription() != null && this
						.getDescription().equals(castOther.getDescription())))
				&& ((this.getMsg1() == castOther.getMsg1()) || (this.getMsg1() != null
						&& castOther.getMsg1() != null && this.getMsg1()
						.equals(castOther.getMsg1())))
				&& ((this.getEventTime() == castOther.getEventTime()) || (this
						.getEventTime() != null
						&& castOther.getEventTime() != null && this
						.getEventTime().equals(castOther.getEventTime())))*/
						;
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFid() == null ? 0 : this.getFid().hashCode());
		result = 37 * result
				+ (getMachType() == null ? 0 : this.getMachType().hashCode());
		result = 37 * result
				+ (getTestItem() == null ? 0 : this.getTestItem().hashCode());
		result = 37 * result
				+ (getRateValue() == null ? 0 : this.getRateValue().hashCode());
		result = 37 * result
				+ (getMeasValue() == null ? 0 : this.getMeasValue().hashCode());
		result = 37 * result
				+ (getErrorCode() == null ? 0 : this.getErrorCode().hashCode());
		result = 37 * result
		/*		+ (getTolerance() == null ? 0 : this.getTolerance().hashCode());
		result = 37 * result
				+ (getMachId() == null ? 0 : this.getMachId().hashCode());
		result = 37 * result
				+ (getMachTime() == null ? 0 : this.getMachTime().hashCode());
		result = 37
				* result
				+ (getTransferBit() == null ? 0 : this.getTransferBit()
						.hashCode());
		result = 37 * result
				+ (getDescription() == null ? 0 : this.getDescription()
						.hashCode());
		result = 37 * result
				+ (getMsg1() == null ? 0 : this.getMsg1().hashCode());
		result = 37 * result
				+ (getEventTime() == null ? 0 : this.getEventTime().hashCode())*/
				;
		return result;
	}

}