package com.converter;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * TabComponentInfo entity. @author MyEclipse Persistence Tools
 */

public class TabComponentInfo  implements java.io.Serializable {


    // Fields    

     private TabComponentInfoId id;
     private BigDecimal ppi;
     private String componentNumber;
     private String componentAs;
     private BigDecimal componentPpi;
     private String fillColor;
     private String strokeWidth;
     private String strokeColor;
     private Timestamp eventTime;


    // Constructors

    /** default constructor */
    public TabComponentInfo() {
    }

	/** minimal constructor */
    public TabComponentInfo(TabComponentInfoId id) {
        this.id = id;
    }
    
    /** full constructor */
    public TabComponentInfo(TabComponentInfoId id, BigDecimal ppi, String componentNumber, String componentAs, BigDecimal componentPpi, String fillColor, String strokeWidth, String strokeColor, Timestamp eventTime) {
        this.id = id;
        this.ppi = ppi;
        this.componentNumber = componentNumber;
        this.componentAs = componentAs;
        this.componentPpi = componentPpi;
        this.fillColor = fillColor;
        this.strokeWidth = strokeWidth;
        this.strokeColor = strokeColor;
        this.eventTime = eventTime;
    }

   
    // Property accessors

    public TabComponentInfoId getId() {
        return this.id;
    }
    
    public void setId(TabComponentInfoId id) {
        this.id = id;
    }

    public BigDecimal getPpi() {
        return this.ppi;
    }
    
    public void setPpi(BigDecimal ppi) {
        this.ppi = ppi;
    }

    public String getComponentNumber() {
        return this.componentNumber;
    }
    
    public void setComponentNumber(String componentNumber) {
        this.componentNumber = componentNumber;
    }

    public String getComponentAs() {
        return this.componentAs;
    }
    
    public void setComponentAs(String componentAs) {
        this.componentAs = componentAs;
    }

    public BigDecimal getComponentPpi() {
        return this.componentPpi;
    }
    
    public void setComponentPpi(BigDecimal componentPpi) {
        this.componentPpi = componentPpi;
    }

    public String getFillColor() {
        return this.fillColor;
    }
    
    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
    }

    public String getStrokeWidth() {
        return this.strokeWidth;
    }
    
    public void setStrokeWidth(String strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getStrokeColor() {
        return this.strokeColor;
    }
    
    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public Timestamp getEventTime() {
        return this.eventTime;
    }
    
    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }
   








}