package com.marta.admin.hibernate;



/**
 * Bcwtgbsproductdetails generated by MyEclipse - Hibernate Tools
 */

public class BcwtGbsProductDetails  implements java.io.Serializable {


    // Fields    

     private Long gbsproductid;
     private BcwtOrderInfo bcwtorderinfo;
     private BcwtProduct bcwtproduct;
     private String addvaluestatus;
     private Double price;

    // Constructors

    /**
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}



	/** default constructor */
    public BcwtGbsProductDetails() {
    }

	
   
    // Property accessors

    public Long getGbsproductid() {
        return this.gbsproductid;
    }
    
    public void setGbsproductid(Long gbsproductid) {
        this.gbsproductid = gbsproductid;
    }

    public BcwtOrderInfo getBcwtorderinfo() {
        return this.bcwtorderinfo;
    }
    
    public void setBcwtorderinfo(BcwtOrderInfo bcwtorderinfo) {
        this.bcwtorderinfo = bcwtorderinfo;
    }

    

    public BcwtProduct getBcwtproduct() {
		return bcwtproduct;
	}



	public void setBcwtproduct(BcwtProduct bcwtproduct) {
		this.bcwtproduct = bcwtproduct;
	}



	public String getAddvaluestatus() {
        return this.addvaluestatus;
    }
    
    public void setAddvaluestatus(String addvaluestatus) {
        this.addvaluestatus = addvaluestatus;
    }
   








}