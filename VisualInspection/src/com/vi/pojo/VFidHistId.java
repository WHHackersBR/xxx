package com.vi.pojo;

import java.sql.Timestamp;


/**
 * VFidHistId entity. @author MyEclipse Persistence Tools
 */

public class VFidHistId  implements java.io.Serializable {


    // Fields    

     private String fid;


    // Constructors

    /** default constructor */
    public VFidHistId() {
    }

	/** minimal constructor */
    public VFidHistId(String fid) {
        this.fid = fid;
    }
    
   
    // Property accessors

    public String getFid() {
        return this.fid;
    }
    
    public void setFid(String fid) {
        this.fid = fid;
    }



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VFidHistId) ) return false;
		 VFidHistId castOther = ( VFidHistId ) other; 
         
		 return ( (this.getFid()==castOther.getFid()) || ( this.getFid()!=null && castOther.getFid()!=null && this.getFid().equals(castOther.getFid()) ) )

;
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFid() == null ? 0 : this.getFid().hashCode() );
         return result;
   }   





}