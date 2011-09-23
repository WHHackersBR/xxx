package com.vi.pojo;

import java.sql.Blob;


/**
 * SvgFile entity. @author MyEclipse Persistence Tools
 */

public class SvgFile implements java.io.Serializable {

	// Fields

	private SvgFileId id;
	private Blob sourcefile;
	private String userManual;
	private String modifier;
	private String active;

	// Constructors


	/** default constructor */
	public SvgFile() {
	}

	public Blob getSourcefile() {
		return sourcefile;
	}

	public void setSourcefile(Blob sourcefile) {
		this.sourcefile = sourcefile;
	}


	/** minimal constructor */
	public SvgFile(SvgFileId id) {
		this.id = id;
	}

	/** full constructor */
	public SvgFile(SvgFileId id, Blob Sourcefile) {
		this.id = id;
		this.sourcefile = Sourcefile;
	}

	// Property accessors

	public SvgFileId getId() {
		return this.id;
	}

	public void setId(SvgFileId id) {
		this.id = id;
	}

	/**
	 * @return the userManual
	 */
	public String getUserManual() {
		return userManual;
	}

	/**
	 * @param userManual the userManual to set
	 */
	public void setUserManual(String userManual) {
		this.userManual = userManual;
	}

	/**
	 * @return the modifier
	 */
	public String getModifier() {
		return modifier;
	}

	/**
	 * @param modifier the modifier to set
	 */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}


}