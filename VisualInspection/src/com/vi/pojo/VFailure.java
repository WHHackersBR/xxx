package com.vi.pojo;

import java.sql.Timestamp;

/**
 * VFailure entity. @author MyEclipse Persistence Tools
 */

public class VFailure implements java.io.Serializable {

	// Fields

	private VFailureId id;

	private String tolerance;
	private String machId;
	private String machTime;
	private String transferBit;
	private String description;
	private String msg1;
	private String confirm;

	// Constructors

	/**
	 * @return the tolerance
	 */
	public String getTolerance() {
		return tolerance;
	}
	/**
	 * @param tolerance the tolerance to set
	 */
	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	/** default constructor */
	public VFailure() {
	}

	/** full constructor */
	public VFailure(VFailureId id) {
		this.id = id;
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


	// Property accessors

	public VFailureId getId() {
		return this.id;
	}

	public void setId(VFailureId id) {
		this.id = id;
	}

}