package com.marta.admin.hibernate;

import java.util.Date;


/**
 * PartnerHotlistHistory generated by MyEclipse - Hibernate Tools
 */

public class PartnerHotlistHistory  implements java.io.Serializable {


    // Fields    

     private Long srNo;
     private String cardNumber;
     private String companyName;
     private String hotlistFlag;
     private String unhotlistFlag;
     private Date hotlistReqDate;
     private Date unhotlistReqDate;
     private String firstName;
     private String lastName;
     private String nfsId;
     private String adminId;
     private String tmaName;
     private String memberId;
     private String email;
     private String phone;
     private String benefitId;
     private String reqSource;


    // Constructors

    /** default constructor */
    public PartnerHotlistHistory() {
    }

	/** minimal constructor */
    public PartnerHotlistHistory(String cardNumber, String companyName, String hotlistFlag, String unhotlistFlag) {
        this.cardNumber = cardNumber;
        this.companyName = companyName;
        this.hotlistFlag = hotlistFlag;
        this.unhotlistFlag = unhotlistFlag;
    }
    
    /** full constructor */
    public PartnerHotlistHistory(String cardNumber, String companyName, String hotlistFlag, String unhotlistFlag, Date hotlistReqDate, Date unhotlistReqDate, String firstName, String lastName, String nfsId, String adminId, String tmaName, String memberId, String email, String phone, String benefitId, String reqSource) {
        this.cardNumber = cardNumber;
        this.companyName = companyName;
        this.hotlistFlag = hotlistFlag;
        this.unhotlistFlag = unhotlistFlag;
        this.hotlistReqDate = hotlistReqDate;
        this.unhotlistReqDate = unhotlistReqDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nfsId = nfsId;
        this.adminId = adminId;
        this.tmaName = tmaName;
        this.memberId = memberId;
        this.email = email;
        this.phone = phone;
        this.benefitId = benefitId;
        this.reqSource = reqSource;
    }

   
    // Property accessors

    public Long getSrNo() {
        return this.srNo;
    }
    
    public void setSrNo(Long srNo) {
        this.srNo = srNo;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHotlistFlag() {
        return this.hotlistFlag;
    }
    
    public void setHotlistFlag(String hotlistFlag) {
        this.hotlistFlag = hotlistFlag;
    }

    public String getUnhotlistFlag() {
        return this.unhotlistFlag;
    }
    
    public void setUnhotlistFlag(String unhotlistFlag) {
        this.unhotlistFlag = unhotlistFlag;
    }

    public Date getHotlistReqDate() {
        return this.hotlistReqDate;
    }
    
    public void setHotlistReqDate(Date hotlistReqDate) {
        this.hotlistReqDate = hotlistReqDate;
    }

    public Date getUnhotlistReqDate() {
        return this.unhotlistReqDate;
    }
    
    public void setUnhotlistReqDate(Date unhotlistReqDate) {
        this.unhotlistReqDate = unhotlistReqDate;
    }

    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNfsId() {
        return this.nfsId;
    }
    
    public void setNfsId(String nfsId) {
        this.nfsId = nfsId;
    }

    public String getAdminId() {
        return this.adminId;
    }
    
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getTmaName() {
        return this.tmaName;
    }
    
    public void setTmaName(String tmaName) {
        this.tmaName = tmaName;
    }

    public String getMemberId() {
        return this.memberId;
    }
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBenefitId() {
        return this.benefitId;
    }
    
    public void setBenefitId(String benefitId) {
        this.benefitId = benefitId;
    }

    public String getReqSource() {
        return this.reqSource;
    }
    
    public void setReqSource(String reqSource) {
        this.reqSource = reqSource;
    }
   








}