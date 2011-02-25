package com.vi.pojo;

/**
 * TabTestedId entity. @author MyEclipse Persistence Tools
 */

//@SuppressWarnings("serial")
public class TabTestedId implements java.io.Serializable {

	// Fields

	private String fid;
	private String workstationNo;

	// Constructors

	/** default constructor */
	public TabTestedId() {
	}

	/** full constructor */
	public TabTestedId(String fid, String workstationNo) {
		this.fid = fid;
		this.workstationNo = workstationNo;
	}

	// Property accessors

	public String getFid() {
		return this.fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getWorkstationNo() {
		return this.workstationNo;
	}

	public void setWorkstationNo(String workstationNo) {
		this.workstationNo = workstationNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TabTestedId))
			return false;
		TabTestedId castOther = (TabTestedId) other;

		return ((this.getFid() == castOther.getFid()) || (this.getFid() != null
				&& castOther.getFid() != null && this.getFid().equals(
				castOther.getFid())))
				&& ((this.getWorkstationNo() == castOther.getWorkstationNo()) || (this
						.getWorkstationNo() != null
						&& castOther.getWorkstationNo() != null && this
						.getWorkstationNo()
						.equals(castOther.getWorkstationNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFid() == null ? 0 : this.getFid().hashCode());
		result = 37
				* result
				+ (getWorkstationNo() == null ? 0 : this.getWorkstationNo()
						.hashCode());
		return result;
	}

}