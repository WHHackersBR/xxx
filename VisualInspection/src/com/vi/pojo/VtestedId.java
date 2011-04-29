package com.vi.pojo;
// default package

import java.sql.Timestamp;


/**
 * VtestedId entity. @author MyEclipse Persistence Tools
 */

public class VtestedId  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fid;
    private String machType;
    private String side;


    // Constructors

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


	/** default constructor */
    public VtestedId() {
    }

    
    /** full constructor */
    public VtestedId(String fid, String machType, String side) {
        this.fid = fid;
        this.machType = machType;
        this.side = side;
    }

   
    // Property accessors

    public String getFid() {
        return this.fid;
    }
    
    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getMachType() {
        return this.machType;
    }
    
    public void setMachType(String machType) {
        this.machType = machType;
    }



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VtestedId) ) return false;
		 VtestedId castOther = ( VtestedId ) other; 
         
		 return ( (this.getFid()==castOther.getFid()) || ( this.getFid()!=null && castOther.getFid()!=null && this.getFid().equals(castOther.getFid()) ) )
 && ( (this.getMachType()==castOther.getMachType()) || ( this.getMachType()!=null && castOther.getMachType()!=null && this.getMachType().equals(castOther.getMachType()) ) )
 && ( (this.getSide()==castOther.getSide()) || ( this.getSide()!=null && castOther.getSide()!=null && this.getSide().equals(castOther.getSide()) ) )
;
   	}
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFid() == null ? 0 : this.getFid().hashCode() );
         result = 37 * result + ( getMachType() == null ? 0 : this.getMachType().hashCode() );
         result = 37 * result + ( getSide() == null ? 0 : this.getSide().hashCode() );
         return result;
   }   





}