package com.converter;

/**
 * TabWorkstation entity. @author MyEclipse Persistence Tools
 */
public class TabWorkstation extends AbstractTabWorkstation implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TabWorkstation() {
	}

	/** full constructor */
	public TabWorkstation(String side, String type, String code,
			String equipContent, String machId) {
		super(side, type, code, equipContent, machId);
	}

}
