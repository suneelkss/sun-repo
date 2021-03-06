package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * PartnerCompanystatus generated by MyEclipse - Hibernate Tools
 */

public class PartnerCompanyStatus  implements java.io.Serializable {


    // Fields    

     private Long companyStatusId;
     private String companyStatus;
     private Set partnerCompanyinfos = new HashSet(0);


    // Constructors

    /** default constructor */
    public PartnerCompanyStatus() {
    }

	/** minimal constructor */
    public PartnerCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }
    
    /** full constructor */
    public PartnerCompanyStatus(String companyStatus, Set partnerCompanyinfos) {
        this.companyStatus = companyStatus;
        this.partnerCompanyinfos = partnerCompanyinfos;
    }

   
    // Property accessors

    public Long getCompanyStatusId() {
        return this.companyStatusId;
    }
    
    public void setCompanyStatusId(Long companyStatusId) {
        this.companyStatusId = companyStatusId;
    }

    public String getCompanyStatus() {
        return this.companyStatus;
    }
    
    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }

    public Set getPartnerCompanyinfos() {
        return this.partnerCompanyinfos;
    }
    
    public void setPartnerCompanyinfos(Set partnerCompanyinfos) {
        this.partnerCompanyinfos = partnerCompanyinfos;
    }
   








}