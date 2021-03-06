package com.marta.admin.hibernate;



/**
 * PartnerBatchdetailsHistoryId generated by MyEclipse - Hibernate Tools
 */

public class PartnerBatchDetailsHistoryId  implements java.io.Serializable {


    // Fields    

     private Long batchJobid;
     private Long orderSeqid;


    // Constructors

    /** default constructor */
    public PartnerBatchDetailsHistoryId() {
    }

    
    /** full constructor */
    public PartnerBatchDetailsHistoryId(Long batchJobid, Long orderSeqid) {
        this.batchJobid = batchJobid;
        this.orderSeqid = orderSeqid;
    }

   
    // Property accessors

    public Long getBatchJobid() {
        return this.batchJobid;
    }
    
    public void setBatchJobid(Long batchJobid) {
        this.batchJobid = batchJobid;
    }

    public Long getOrderSeqid() {
        return this.orderSeqid;
    }
    
    public void setOrderSeqid(Long orderSeqid) {
        this.orderSeqid = orderSeqid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof PartnerBatchDetailsHistoryId) ) return false;
		 PartnerBatchDetailsHistoryId castOther = ( PartnerBatchDetailsHistoryId ) other; 
         
		 return ( (this.getBatchJobid()==castOther.getBatchJobid()) || ( this.getBatchJobid()!=null && castOther.getBatchJobid()!=null && this.getBatchJobid().equals(castOther.getBatchJobid()) ) )
 && ( (this.getOrderSeqid()==castOther.getOrderSeqid()) || ( this.getOrderSeqid()!=null && castOther.getOrderSeqid()!=null && this.getOrderSeqid().equals(castOther.getOrderSeqid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getBatchJobid() == null ? 0 : this.getBatchJobid().hashCode() );
         result = 37 * result + ( getOrderSeqid() == null ? 0 : this.getOrderSeqid().hashCode() );
         return result;
   }   





}