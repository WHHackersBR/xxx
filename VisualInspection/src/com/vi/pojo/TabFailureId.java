package com.vi.pojo;

/**
 * TabFailureId entity. @author MyEclipse Persistence Tools
 */

public class TabFailureId implements java.io.Serializable {

	// Fields

	private String fid;
	private String workstationNo;
	private String pieceLocation;
	private String componentLocation;
	private String failureCode;

	// Constructors

	/** default constructor */
	public TabFailureId() {
	}

	/** full constructor */
	public TabFailureId(String fid,String workstationNo, String pieceLocation,
			String componentLocation, String failureCode) {
		this.fid = fid;
		this.workstationNo = workstationNo;
		this.pieceLocation = pieceLocation;
		this.componentLocation = componentLocation;
		this.failureCode = failureCode;
	}

	// Property accessors


	public String getPieceLocation() {
		return this.pieceLocation;
	}

	/**
	 * @return the fid
	 */
	public String getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(String fid) {
		this.fid = fid;
	}

	/**
	 * @return the workstationNo
	 */
	public String getWorkstationNo() {
		return workstationNo;
	}

	/**
	 * @param workstationNo the workstationNo to set
	 */
	public void setWorkstationNo(String workstationNo) {
		this.workstationNo = workstationNo;
	}

	public void setPieceLocation(String pieceLocation) {
		this.pieceLocation = pieceLocation;
	}

	public String getComponentLocation() {
		return this.componentLocation;
	}

	public void setComponentLocation(String componentLocation) {
		this.componentLocation = componentLocation;
	}

	public String getFailureCode() {
		return this.failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TabFailureId))
			return false;
		TabFailureId castOther = (TabFailureId) other;

		return ((this.getFid() == castOther.getFid()) || (this
				.getFid() != null
				&& castOther.getFid() != null && this.getFid()
				.equals(castOther.getFid())))
				&& ((this.getWorkstationNo() == castOther.getWorkstationNo()) || (this
						.getWorkstationNo() != null
						&& castOther.getWorkstationNo() != null && this
						.getWorkstationNo()
						.equals(castOther.getWorkstationNo())))
				&& ((this.getPieceLocation() == castOther.getPieceLocation()) || (this
						.getPieceLocation() != null
						&& castOther.getPieceLocation() != null && this
						.getPieceLocation()
						.equals(castOther.getPieceLocation())))
				&& ((this.getComponentLocation() == castOther
						.getComponentLocation()) || (this
						.getComponentLocation() != null
						&& castOther.getComponentLocation() != null && this
						.getComponentLocation().equals(
								castOther.getComponentLocation())))
				&& ((this.getFailureCode() == castOther.getFailureCode()) || (this
						.getFailureCode() != null
						&& castOther.getFailureCode() != null && this
						.getFailureCode().equals(castOther.getFailureCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getFid() == null ? 0 : this.getFid().hashCode());

		result = 37 * result
				+ (getWorkstationNo() == null ? 0 : this.getWorkstationNo().hashCode());
		
		result = 37
				* result
				+ (getPieceLocation() == null ? 0 : this.getPieceLocation()
						.hashCode());
		result = 37
				* result
				+ (getComponentLocation() == null ? 0 : this
						.getComponentLocation().hashCode());
		result = 37
				* result
				+ (getFailureCode() == null ? 0 : this.getFailureCode()
						.hashCode());
		return result;
	}

}