package com.vi.pojo;

import java.sql.Timestamp;

/**
 * TabViPo entity. @author MyEclipse Persistence Tools
 */

public class TabViPo implements java.io.Serializable {

	// Fields

	private TabViPoId id;

	// Constructors

	/** default constructor */
	public TabViPo() {
	}

	/** full constructor */
	public TabViPo(TabViPoId id) {
		this.id = id;
	}

	// Property accessors

	public TabViPoId getId() {
		return this.id;
	}

	public void setId(TabViPoId id) {
		this.id = id;
	}

}