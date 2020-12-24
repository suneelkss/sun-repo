package com.marta.admin.hibernate;

/**
 * BcwtsGbsShippingHandling generated by MyEclipse - Hibernate Tools
 */

public class BcwtsGbsShippingHandling  implements java.io.Serializable {


    // Fields    

	private static final long serialVersionUID = 1L;
	private Long gbsshippingandhandlingid;
	private String minrange;
	private String maxrange;
	private String dimensions;
	private Double weight;
	private Double shipping;
	private Double handling;
	private Double insurance;
	private String location;
	private String activestatus;


    // Constructors

    /** default constructor */
    public BcwtsGbsShippingHandling() {
    }

    
    /** full constructor */
    public BcwtsGbsShippingHandling(String minrange, String maxrange, String dimensions, Double weight, Double shipping, Double handling, Double insurance, String location, String activestatus) {
        this.minrange = minrange;
        this.maxrange = maxrange;
        this.dimensions = dimensions;
        this.weight = weight;
        this.shipping = shipping;
        this.handling = handling;
        this.insurance = insurance;
        this.location = location;
        this.activestatus = activestatus;
    }

   
    // Property accessors

    public Long getGbsshippingandhandlingid() {
        return this.gbsshippingandhandlingid;
    }
    
    public void setGbsshippingandhandlingid(Long gbsshippingandhandlingid) {
        this.gbsshippingandhandlingid = gbsshippingandhandlingid;
    }

    public String getMinrange() {
        return this.minrange;
    }
    
    public void setMinrange(String minrange) {
        this.minrange = minrange;
    }

    public String getMaxrange() {
        return this.maxrange;
    }
    
    public void setMaxrange(String maxrange) {
        this.maxrange = maxrange;
    }

    public String getDimensions() {
        return this.dimensions;
    }
    
    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Double getWeight() {
        return this.weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getShipping() {
        return this.shipping;
    }
    
    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public Double getHandling() {
        return this.handling;
    }
    
    public void setHandling(Double handling) {
        this.handling = handling;
    }

    public Double getInsurance() {
        return this.insurance;
    }
    
    public void setInsurance(Double insurance) {
        this.insurance = insurance;
    }

    public String getLocation() {
        return this.location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivestatus() {
        return this.activestatus;
    }
    
    public void setActivestatus(String activestatus) {
        this.activestatus = activestatus;
    }
   








}