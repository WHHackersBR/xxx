package com.vi.pojo;

/**
 * ErrMsg entity. @author MyEclipse Persistence Tools
 */

public class ErrMsg implements java.io.Serializable {

	// Fields

	private String errCode;
	private String errDesc;
	private String solution;
	private String comments;

	// Constructors

	/** default constructor */
	public ErrMsg() {
	}

	/** minimal constructor */
	public ErrMsg(String errCode) {
		this.errCode = errCode;
	}

	/** full constructor */
	public ErrMsg(String errCode, String errDesc, String solution,
			String comments) {
		this.errCode = errCode;
		this.errDesc = errDesc;
		this.solution = solution;
		this.comments = comments;
	}

	// Property accessors

	public String getErrCode() {
		return this.errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrDesc() {
		return this.errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getSolution() {
		return this.solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}