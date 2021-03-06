package com.marta.admin.hibernate;



/**
 * Bcwtbarcode generated by MyEclipse - Hibernate Tools
 */

public class BcwtBarCode  implements java.io.Serializable {


    // Fields

     private Long barcodeid;
     private Long orderid;
     private String barcode;


    // Constructors

    /** default constructor */
    public BcwtBarCode() {
    }

	/** minimal constructor */
    public BcwtBarCode(Long orderid) {
        this.orderid = orderid;
    }

    /** full constructor */
    public BcwtBarCode(Long orderid, String barcode) {
        this.orderid = orderid;
        this.barcode = barcode;
    }


    // Property accessors

    public Long getBarcodeid() {
        return this.barcodeid;
    }

    public void setBarcodeid(Long barcodeid) {
        this.barcodeid = barcodeid;
    }

    public Long getOrderid() {
        return this.orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }









}