package com.vi.pojo;

/**
 * TabWorkstation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TabWorkstation implements java.io.Serializable {

	// Fields

	private String workstationNo;
	private String side;
	private String type;
	private String code;
	private String equipContent;

	// Constructors

	/** default constructor */
	public TabWorkstation() {
	}

	/** minimal constructor */
	public TabWorkstation(String workstationNo) {
		this.workstationNo = workstationNo;
	}

	/** full constructor */
	public TabWorkstation(String workstationNo, String side, String type,
			String code, String equipContent) {
		this.workstationNo = workstationNo;
		this.side = side;
		this.type = type;
		this.code = code;
		this.equipContent = equipContent;
	}

	// Property accessors

	public String getWorkstationNo() {
		return this.workstationNo;
	}

	public void setWorkstationNo(String workstationNo) {
		this.workstationNo = workstationNo;
	}

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEquipContent() {
		return this.equipContent;
	}

	public void setEquipContent(String equipContent) {
		this.equipContent = equipContent;
	}

}