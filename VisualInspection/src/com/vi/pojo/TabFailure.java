package com.vi.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * TabFailure entity. @author MyEclipse Persistence Tools
 */

public class TabFailure implements java.io.Serializable {

	// Fields

	private TabFailureId id;
	private String machTime;
	private Timestamp eventTime;
	private String failureDescription;
	private String side;
	private BigDecimal transferBit;

	// Constructors

	/** default constructor */
	public TabFailure() {
	}

	/** minimal constructor */
	public TabFailure(TabFailureId id) {
		this.id = id;
	}

	/** full constructor */
	public TabFailure(TabFailureId id, String machTime, Timestamp eventTime,
			String failureDescription, String side, BigDecimal transferBit) {
		this.id = id;
		this.machTime = machTime;
		this.eventTime = eventTime;
		this.failureDescription = failureDescription;
		this.side = side;
		this.transferBit = transferBit;
	}

	// Property accessors

	public TabFailureId getId() {
		return this.id;
	}

	public void setId(TabFailureId id) {
		this.id = id;
	}

	public String getMachTime() {
		return this.machTime;
	}

	public void setMachTime(String machTime) {
		this.machTime = machTime;
	}

	public Timestamp getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	public String getFailureDescription() {
		return this.failureDescription;
	}

	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public BigDecimal getTransferBit() {
		return this.transferBit;
	}

	public void setTransferBit(BigDecimal transferBit) {
		this.transferBit = transferBit;
	}

}