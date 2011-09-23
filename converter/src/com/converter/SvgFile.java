package com.converter;

import java.sql.Blob;
import java.sql.Timestamp;

//import oracle.sql.BLOB;


/**
 * SvgFile entity. @author MyEclipse Persistence Tools
 */

public class SvgFile implements java.io.Serializable {

	// Fields

	private SvgFileId id;
	private Blob sourcefile;
	private String userManual;
	private String modifier;
	private Timestamp modify_date;
	private String approver;
	private Timestamp approve_date;
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

	public void setModify_date(Timestamp modify_date) {
		this.modify_date = modify_date;
	}

	public Timestamp getModify_date() {
		return modify_date;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprove_date(Timestamp approve_date) {
		this.approve_date = approve_date;
	}

	public Timestamp getApprove_date() {
		return approve_date;
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