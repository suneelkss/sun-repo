package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * PartnerRegistrationstatus generated by MyEclipse - Hibernate Tools
 */

public class PartnerRegistrationStatus  implements java.io.Serializable {


    // Fields    

     private Long registrationStatusid;
     private String registrationStatus;
     private Set partnerNewregistrations = new HashSet(0);


    // Constructors

    /** default constructor */
    public PartnerRegistrationStatus() {
    }

	/** minimal constructor */
    public PartnerRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
    
    /** full constructor */
    public PartnerRegistrationStatus(String registrationStatus, Set partnerNewregistrations) {
        this.registrationStatus = registrationStatus;
        this.partnerNewregistrations = partnerNewregistrations;
    }

   
    // Property accessors

    public Long getRegistrationStatusid() {
        return this.registrationStatusid;
    }
    
    public void setRegistrationStatusid(Long registrationStatusid) {
        this.registrationStatusid = registrationStatusid;
    }

    public String getRegistrationStatus() {
        return this.registrationStatus;
    }
    
    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Set getPartnerNewregistrations() {
        return this.partnerNewregistrations;
    }
    
    public void setPartnerNewregistrations(Set partnerNewregistrations) {
        this.partnerNewregistrations = partnerNewregistrations;
    }
   








}