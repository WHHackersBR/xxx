package com.converter;

import java.sql.Blob;

//import oracle.sql.BLOB;


/**
 * SvgFile entity. @author MyEclipse Persistence Tools
 */

public class SvgFile implements java.io.Serializable {

	// Fields

	private SvgFileId id;
	private Blob sourcefile;
	private String articleNo;
	private String estand;
	private String side;

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

	public String getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}

	public String getEstand() {
		return estand;
	}

	public void setEstand(String estand) {
		this.estand = estand;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
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


}