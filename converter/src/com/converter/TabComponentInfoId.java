package com.converter;



/**
 * TabComponentInfoId entity. @author MyEclipse Persistence Tools
 */

public class TabComponentInfoId  implements java.io.Serializable {


    // Fields    

     private String itemNo;
     private String as;
     private String refdes;


    // Constructors

    /** default constructor */
    public TabComponentInfoId() {
    }

    
    /** full constructor */
    public TabComponentInfoId(String itemNo, String as, String refdes) {
        this.itemNo = itemNo;
        this.as = as;
        this.refdes = refdes;
    }

   
    // Property accessors

    public String getItemNo() {
        return this.itemNo;
    }
    
    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getAs() {
        return this.as;
    }
    
    public void setAs(String as) {
        this.as = as;
    }

    public String getRefdes() {
        return this.refdes;
    }
    
    public void setRefdes(String refdes) {
        this.refdes = refdes;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TabComponentInfoId) ) return false;
		 TabComponentInfoId castOther = ( TabComponentInfoId ) other; 
         
		 return ( (this.getItemNo()==castOther.getItemNo()) || ( this.getItemNo()!=null && castOther.getItemNo()!=null && this.getItemNo().equals(castOther.getItemNo()) ) )
 && ( (this.getAs()==castOther.getAs()) || ( this.getAs()!=null && castOther.getAs()!=null && this.getAs().equals(castOther.getAs()) ) )
 && ( (this.getRefdes()==castOther.getRefdes()) || ( this.getRefdes()!=null && castOther.getRefdes()!=null && this.getRefdes().equals(castOther.getRefdes()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getItemNo() == null ? 0 : this.getItemNo().hashCode() );
         result = 37 * result + ( getAs() == null ? 0 : this.getAs().hashCode() );
         result = 37 * result + ( getRefdes() == null ? 0 : this.getRefdes().hashCode() );
         return result;
   }   





}