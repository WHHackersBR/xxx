package com.vi.pojo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * TabTested entity. @author MyEclipse Persistence Tools
 */

public class TabTested implements java.io.Serializable {

	// Fields

	private TabTestedId id;
	private String testResult;
	private String itemNo;
	private String as;
	private String poNo;
	private String fake;
	private String operatorId;
	private String side;
	private BigDecimal transferBit;
	private Timestamp eventTime;
	private String machTime;
	private Set tabFailures = new HashSet(0);

	// Constructors

	/** default constructor */
	public TabTested() {
	}

	/** minimal constructor */
	public TabTested(TabTestedId id, String testResult, String itemNo,
			String as, String poNo, String fake, String side) {
		this.id = id;
		this.testResult = testResult;
		this.itemNo = itemNo;
		this.as = as;
		this.poNo = poNo;
		this.fake = fake;
		this.side = side;
	}

	/** full constructor */
	public TabTested(TabTestedId id, String testResult, String itemNo,
			String as, String poNo, String fake, String operatorId,
			String side, BigDecimal transferBit, Timestamp eventTime,
			String machTime, Set tabFailures) {
		this.id = id;
		this.testResult = testResult;
		this.itemNo = itemNo;
		this.as = as;
		this.poNo = poNo;
		this.fake = fake;
		this.operatorId = operatorId;
		this.side = side;
		this.transferBit = transferBit;
		this.eventTime = eventTime;
		this.machTime = machTime;
		this.tabFailures = tabFailures;
	}

	// Property accessors

	public TabTestedId getId() {
		return this.id;
	}

	public void setId(TabTestedId id) {
		this.id = id;
	}

	public String getTestResult() {
		return this.testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getAs() {
		return this.as;
	}

	public void setAs(String as) {
		this.as = as;
	}

	public String getPoNo() {
		return this.poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getFake() {
		return this.fake;
	}

	public void setFake(String fake) {
		this.fake = fake;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
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

	public Timestamp getEventTime() {
		return this.eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
		this.eventTime = eventTime;
	}

	public String getMachTime() {
		return this.machTime;
	}

	public void setMachTime(String machTime) {
		this.machTime = machTime;
	}

	public Set getTabFailures() {
		return this.tabFailures;
	}

	public void setTabFailures(Set tabFailures) {
		this.tabFailures = tabFailures;
	}

}