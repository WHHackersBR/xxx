package com.converter;

import java.math.BigDecimal;
import java.sql.Date;



/**
 * SvgSide entity. @author MyEclipse Persistence Tools
 */

public class SvgSide  implements java.io.Serializable {


    // Fields    

     private SvgSideId id;
     private BigDecimal layerSn;
     private String side;
     private String style;
     private String stokeWidth;
     private String color;
     private String comments;
     

    // Constructors

    
	/** full constructor */
    public SvgSide(SvgSideId id, String side, BigDecimal layerSn, String style, String stokeWidth, String color, String comments) {
        this.id = id;
        this.side = side;
        this.layerSn = layerSn;
        this.style = style;
        this.stokeWidth = stokeWidth;
        this.color = color;
        this.comments = comments;
    }

   
    // Property accessors


	/** default constructor */
    public SvgSide() {
    }

	/** minimal constructor */
    public SvgSide(SvgSideId id) {
        this.id = id;
    }

    /**
	 * @return the side
	 */
	public String getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(String side) {
		this.side = side;
	}

	public SvgSideId getId() {
        return this.id;
    }
    
    public void setId(SvgSideId id) {
        this.id = id;
    }

    public BigDecimal getLayerSn() {
        return this.layerSn;
    }
    
    public void setLayerSn(BigDecimal layerSn) {
        this.layerSn = layerSn;
    }

    public String getStyle() {
        return this.style;
    }
    
    public void setStyle(String style) {
        this.style = style;
    }

    public String getStokeWidth() {
        return this.stokeWidth;
    }
    
    public void setStokeWidth(String stokeWidth) {
        this.stokeWidth = stokeWidth;
    }

    public String getColor() {
        return this.color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }

    public String getComments() {
        return this.comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }









}