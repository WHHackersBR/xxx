package com.vi.pojo;

/**
 * TabWorkstation entity. @author MyEclipse Persistence Tools
 */

public class TabWorkstation implements java.io.Serializable {

	// Fields

	private String workstationNo;
	private String side;
	private String type;
	private String code;
	private String equipContent;
	private String machId;
	private String machType;

	// Constructors

	/** default constructor */
	public TabWorkstation() {
	}

	/** full constructor */
	public TabWorkstation(String side, String type, String code,
			String equipContent, String machId, String machType) {
		this.side = side;
		this.type = type;
		this.code = code;
		this.equipContent = equipContent;
		this.machId = machId;
		this.machType = machType;
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

	public String getMachId() {
		return this.machId;
	}

	public void setMachId(String machId) {
		this.machId = machId;
	}

	public String getMachType() {
		return this.machType;
	}

	public void setMachType(String machType) {
		this.machType = machType;
	}

}