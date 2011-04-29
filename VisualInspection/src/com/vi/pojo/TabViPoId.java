package com.vi.pojo;

import java.sql.Timestamp;

/**
 * TabViPoId entity. @author MyEclipse Persistence Tools
 */

public class TabViPoId implements java.io.Serializable {

	// Fields

	private String poNo;
	private String workstationNo;
	private String itemNo;
	private String as;
	private String side;
	private String operatorId;
	private String machTime;

	// Constructors

	/** default constructor */
	public TabViPoId() {
	}

	/** full constructor */
	public TabViPoId(String poNo, String workstationNo, String itemNo,
			String as, String side, String operatorId, String machTime) {
		this.poNo = poNo;
		this.workstationNo = workstationNo;
		this.itemNo = itemNo;
		this.as = as;
		this.side = side;
		this.operatorId = operatorId;
		this.machTime = machTime;
	}

	// Property accessors

	public String getPoNo() {
		return this.poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getWorkstationNo() {
		return this.workstationNo;
	}

	public void setWorkstationNo(String workstationNo) {
		this.workstationNo = workstationNo;
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

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getMachTime() {
		return this.machTime;
	}

	public void setMachTime(String machTime) {
		this.machTime = machTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TabViPoId))
			return false;
		TabViPoId castOther = (TabViPoId) other;

		return ((this.getPoNo() == castOther.getPoNo()) || (this.getPoNo() != null
				&& castOther.getPoNo() != null && this.getPoNo().equals(
				castOther.getPoNo())))
				&& ((this.getWorkstationNo() == castOther.getWorkstationNo()) || (this
						.getWorkstationNo() != null
						&& castOther.getWorkstationNo() != null && this
						.getWorkstationNo()
						.equals(castOther.getWorkstationNo())))
				&& ((this.getItemNo() == castOther.getItemNo()) || (this
						.getItemNo() != null
						&& castOther.getItemNo() != null && this.getItemNo()
						.equals(castOther.getItemNo())))
				&& ((this.getAs() == castOther.getAs()) || (this.getAs() != null
						&& castOther.getAs() != null && this.getAs().equals(
						castOther.getAs())))
				&& ((this.getSide() == castOther.getSide()) || (this.getSide() != null
						&& castOther.getSide() != null && this.getSide()
						.equals(castOther.getSide())))
				&& ((this.getOperatorId() == castOther.getOperatorId()) || (this
						.getOperatorId() != null
						&& castOther.getOperatorId() != null && this
						.getOperatorId().equals(castOther.getOperatorId())))
				&& ((this.getMachTime() == castOther.getMachTime()) || (this
						.getMachTime() != null
						&& castOther.getMachTime() != null && this
						.getMachTime().equals(castOther.getMachTime())))
				;
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getPoNo() == null ? 0 : this.getPoNo().hashCode());
		result = 37
				* result
				+ (getWorkstationNo() == null ? 0 : this.getWorkstationNo()
						.hashCode());
		result = 37 * result
				+ (getItemNo() == null ? 0 : this.getItemNo().hashCode());
		result = 37 * result + (getAs() == null ? 0 : this.getAs().hashCode());
		result = 37 * result
				+ (getSide() == null ? 0 : this.getSide().hashCode());
		result = 37
				* result
				+ (getOperatorId() == null ? 0 : this.getOperatorId()
						.hashCode());
		result = 37 * result
				+ (getMachTime() == null ? 0 : this.getMachTime().hashCode());
		return result;
	}

}