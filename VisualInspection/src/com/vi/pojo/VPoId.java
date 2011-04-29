package com.vi.pojo;
// default package



/**
 * VPoId entity. @author MyEclipse Persistence Tools
 */

public class VPoId  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String poNo;
     private String articleNo;
     private String machType;


    // Constructors

    /** default constructor */
    public VPoId() {
    }

	/** minimal constructor */
    public VPoId(String poNo, String articleNo, String machType) {
        this.poNo = poNo;
        this.articleNo = articleNo;
        this.machType = machType;
    }
    

   
    // Property accessors

    public String getPoNo() {
        return this.poNo;
    }
    
    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getArticleNo() {
        return this.articleNo;
    }
    
    public void setArticleNo(String articleNo) {
        this.articleNo = articleNo;
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
		 if ( !(other instanceof VPoId) ) return false;
		 VPoId castOther = ( VPoId ) other; 
         
		 return ( (this.getPoNo()==castOther.getPoNo()) || ( this.getPoNo()!=null && castOther.getPoNo()!=null && this.getPoNo().equals(castOther.getPoNo()) ) )
 && ( (this.getArticleNo()==castOther.getArticleNo()) || ( this.getArticleNo()!=null && castOther.getArticleNo()!=null && this.getArticleNo().equals(castOther.getArticleNo()) ) )
 && ( (this.getMachType()==castOther.getMachType()) || ( this.getMachType()!=null && castOther.getMachType()!=null && this.getMachType().equals(castOther.getMachType()) ) )
 	;
	}
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPoNo() == null ? 0 : this.getPoNo().hashCode() );
         result = 37 * result + ( getArticleNo() == null ? 0 : this.getArticleNo().hashCode() );
         result = 37 * result + ( getMachType() == null ? 0 : this.getMachType().hashCode() );
         return result;
   }   





}