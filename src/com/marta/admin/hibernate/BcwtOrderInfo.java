package com.marta.admin.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Bcwtorderinfo generated by MyEclipse - Hibernate Tools
 */

public class BcwtOrderInfo  implements java.io.Serializable {


    // Fields    

     private Long orderinfoid;
     private BcwtOrder bcwtorder;
     private Long noofcards;
     private Double cashvalue;
     private Date ccauthorisationdate;
     private String releasebreezecard;
     private Long nextfareorderid;
	 private Long batchid;
	 private Double discountedamount;
     private Set bcwtgbsproductdetailses = new HashSet(0);


    // Constructors

    /** default constructor */
    public BcwtOrderInfo() {
    }

	/** minimal constructor */
    public BcwtOrderInfo(BcwtOrder bcwtorder) {
        this.bcwtorder = bcwtorder;
    }
    
    /** full constructor */
    public BcwtOrderInfo(BcwtOrder bcwtorder, Long noofcards, Set bcwtgbsproductdetailses) {
        this.bcwtorder = bcwtorder;
        this.noofcards = noofcards;
        this.bcwtgbsproductdetailses = bcwtgbsproductdetailses;
    }

   
    // Property accessors

    public Long getOrderinfoid() {
        return this.orderinfoid;
    }
    
    public void setOrderinfoid(Long orderinfoid) {
        this.orderinfoid = orderinfoid;
    }

    public BcwtOrder getBcwtorder() {
        return this.bcwtorder;
    }
    
    public void setBcwtorder(BcwtOrder bcwtorder) {
        this.bcwtorder = bcwtorder;
    }

    public Long getNoofcards() {
        return this.noofcards;
    }
    
    public void setNoofcards(Long noofcards) {
        this.noofcards = noofcards;
    }

    public Set getBcwtgbsproductdetailses() {
        return this.bcwtgbsproductdetailses;
    }
    
    public void setBcwtgbsproductdetailses(Set bcwtgbsproductdetailses) {
        this.bcwtgbsproductdetailses = bcwtgbsproductdetailses;
    }

	public Double getCashvalue() {
		return cashvalue;
	}

	public void setCashvalue(Double cashvalue) {
		this.cashvalue = cashvalue;
	}

	public Date getCcauthorisationdate() {
		return ccauthorisationdate;
	}

	public void setCcauthorisationdate(Date ccauthorisationdate) {
		this.ccauthorisationdate = ccauthorisationdate;
	}

	public String getReleasebreezecard() {
		return releasebreezecard;
	}

	public void setReleasebreezecard(String releasebreezecard) {
		this.releasebreezecard = releasebreezecard;
	}

	/**
	 * @return the batchid
	 */
	public Long getBatchid() {
		return batchid;
	}

	/**
	 * @param batchid the batchid to set
	 */
	public void setBatchid(Long batchid) {
		this.batchid = batchid;
	}

	/**
	 * @return the nextfareorderid
	 */
	public Long getNextfareorderid() {
		return nextfareorderid;
	}

	/**
	 * @param nextfareorderid the nextfareorderid to set
	 */
	public void setNextfareorderid(Long nextfareorderid) {
		this.nextfareorderid = nextfareorderid;
	}

	/**
	 * @return the discountedamount
	 */
	public Double getDiscountedamount() {
		return discountedamount;
	}

	/**
	 * @param discountedamount the discountedamount to set
	 */
	public void setDiscountedamount(Double discountedamount) {
		this.discountedamount = discountedamount;
	}
   








}