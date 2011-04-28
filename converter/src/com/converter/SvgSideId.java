package com.converter;



/**
 * SvgSideId entity. @author MyEclipse Persistence Tools
 */

public class SvgSideId  implements java.io.Serializable {


    // Fields    

     private String articleNo;
     private String as;
     private String layerName;


    // Constructors

    /** default constructor */
    public SvgSideId() {
    }

    
    /** full constructor */
    public SvgSideId(String articleNo, String as, String layerName) {
        this.articleNo = articleNo;
        this.as = as;
        this.layerName = layerName;
    }

   
    // Property accessors

    public String getArticleNo() {
        return this.articleNo;
    }
    
    public void setArticleNo(String articleNo) {
        this.articleNo = articleNo;
    }

    public String getAs() {
        return this.as;
    }
    
    public void setAs(String as) {
        this.as = as;
    }

    public String getLayerName() {
        return this.layerName;
    }
    
    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SvgSideId) ) return false;
		 SvgSideId castOther = ( SvgSideId ) other; 
         
		 return ( (this.getArticleNo()==castOther.getArticleNo()) || ( this.getArticleNo()!=null && castOther.getArticleNo()!=null && this.getArticleNo().equals(castOther.getArticleNo()) ) )
 && ( (this.getAs()==castOther.getAs()) || ( this.getAs()!=null && castOther.getAs()!=null && this.getAs().equals(castOther.getAs()) ) )
 && ( (this.getLayerName()==castOther.getLayerName()) || ( this.getLayerName()!=null && castOther.getLayerName()!=null && this.getLayerName().equals(castOther.getLayerName()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getArticleNo() == null ? 0 : this.getArticleNo().hashCode() );
         result = 37 * result + ( getAs() == null ? 0 : this.getAs().hashCode() );
         result = 37 * result + ( getLayerName() == null ? 0 : this.getLayerName().hashCode() );
         return result;
   }   





}