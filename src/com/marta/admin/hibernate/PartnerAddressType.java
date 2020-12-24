package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * PartnerAddresstype generated by MyEclipse - Hibernate Tools
 */

public class PartnerAddressType  implements java.io.Serializable {


    // Fields    

     private Long addressTypeid;
     private String addressType;
     private Set partnerAddresses = new HashSet(0);


    // Constructors

    /** default constructor */
    public PartnerAddressType() {
    }

    
    /** full constructor */
    public PartnerAddressType(String addressType, Set partnerAddresses) {
        this.addressType = addressType;
        this.partnerAddresses = partnerAddresses;
    }

   
    // Property accessors

    public Long getAddressTypeid() {
        return this.addressTypeid;
    }
    
    public void setAddressTypeid(Long addressTypeid) {
        this.addressTypeid = addressTypeid;
    }

    public String getAddressType() {
        return this.addressType;
    }
    
    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public Set getPartnerAddresses() {
        return this.partnerAddresses;
    }
    
    public void setPartnerAddresses(Set partnerAddresses) {
        this.partnerAddresses = partnerAddresses;
    }
   








}