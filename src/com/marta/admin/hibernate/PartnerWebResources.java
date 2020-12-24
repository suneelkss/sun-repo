package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * PartnerWebresources generated by MyEclipse - Hibernate Tools
 */

public class PartnerWebResources  implements java.io.Serializable {


    // Fields    

     private Long resourceId;
     private String resourceName;
     private Set partnerRoleaccesses = new HashSet(0);


    // Constructors

    /** default constructor */
    public PartnerWebResources() {
    }

    
    /** full constructor */
    public PartnerWebResources(String resourceName, Set partnerRoleaccesses) {
        this.resourceName = resourceName;
        this.partnerRoleaccesses = partnerRoleaccesses;
    }

   
    // Property accessors

    public Long getResourceId() {
        return this.resourceId;
    }
    
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return this.resourceName;
    }
    
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Set getPartnerRoleaccesses() {
        return this.partnerRoleaccesses;
    }
    
    public void setPartnerRoleaccesses(Set partnerRoleaccesses) {
        this.partnerRoleaccesses = partnerRoleaccesses;
    }
   








}