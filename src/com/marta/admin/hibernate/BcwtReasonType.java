package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * Bcwtreasontype generated by MyEclipse - Hibernate Tools
 */

public class BcwtReasonType  implements java.io.Serializable {


    // Fields    

     private Long reasontypeid;
     private String reasontype;
     private Set bcwtcancelledorders = new HashSet(0);


    // Constructors

    /** default constructor */
    public BcwtReasonType() {
    }

    
    /** full constructor */
    public BcwtReasonType(String reasontype, Set bcwtcancelledorders) {
        this.reasontype = reasontype;
        this.bcwtcancelledorders = bcwtcancelledorders;
    }

   
    // Property accessors

    public Long getReasontypeid() {
        return this.reasontypeid;
    }
    
    public void setReasontypeid(Long reasontypeid) {
        this.reasontypeid = reasontypeid;
    }

    public String getReasontype() {
        return this.reasontype;
    }
    
    public void setReasontype(String reasontype) {
        this.reasontype = reasontype;
    }

    public Set getBcwtcancelledorders() {
        return this.bcwtcancelledorders;
    }
    
    public void setBcwtcancelledorders(Set bcwtcancelledorders) {
        this.bcwtcancelledorders = bcwtcancelledorders;
    }
   








}