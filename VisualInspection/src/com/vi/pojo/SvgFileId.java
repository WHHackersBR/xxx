package com.vi.pojo;


/**
 * SvgFileId entity. @author MyEclipse Persistence Tools
 */

public class SvgFileId implements java.io.Serializable {

	// Fields

	private String articleNo;
	private String estand;
	private String side;

	// Constructors

	/** default constructor */
	public SvgFileId() {
	}

	/** full constructor */
	public SvgFileId(String articleNo, String estand, String side) {
		this.articleNo = articleNo;
		this.estand = estand;
		this.side = side;
	}

	// Property accessors

	public String getArticleNo() {
		return this.articleNo;
	}

	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}

	public String getEstand() {
		return this.estand;
	}

	public void setEstand(String estand) {
		this.estand = estand;
	}

	public String getSide() {
		return this.side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SvgFileId))
			return false;
		SvgFileId castOther = (SvgFileId) other;

		return ((this.getArticleNo() == castOther.getArticleNo()) || (this
				.getArticleNo() != null
				&& castOther.getArticleNo() != null && this.getArticleNo()
				.equals(castOther.getArticleNo())))
				&& ((this.getEstand() == castOther.getEstand()) || (this
						.getEstand() != null
						&& castOther.getEstand() != null && this.getEstand()
						.equals(castOther.getEstand())))
				&& ((this.getSide() == castOther.getSide()) || (this.getSide() != null
						&& castOther.getSide() != null && this.getSide()
						.equals(castOther.getSide())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getArticleNo() == null ? 0 : this.getArticleNo().hashCode());
		result = 37 * result
				+ (getEstand() == null ? 0 : this.getEstand().hashCode());
		result = 37 * result
				+ (getSide() == null ? 0 : this.getSide().hashCode());
		return result;
	}

}