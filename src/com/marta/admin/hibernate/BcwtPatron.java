package com.marta.admin.hibernate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * Bcwtpatron generated by MyEclipse - Hibernate Tools
 */

public class BcwtPatron  implements java.io.Serializable {


    // Fields

     private Long patronid;
     private BcwtPatronType bcwtpatrontype;
     private String firstname;
     private String middlename;
     private String lastname;
     private String emailid;
     private String patronpassword;
     private String phonenumber;
     private Date dateofbirth;
     private String isautogenerated;
     private String lockstatus;
     private String activestatus;
     private Long logincount;
     private Date lastlogin;
     private Date createddate;
     private Long parentpatronid;
     private Set bcwtpatronaddresses = new HashSet(0);
     private Set bcwtorders = new HashSet(0);
     private Set bcwteventses = new HashSet(0);
     private Set bcwtpatronbreezecards = new HashSet(0);
     private Set bcwtpwdverifiies = new HashSet(0);
     private Set bcwtpatronpaymentcardses = new HashSet(0);


    // Constructors

    /** default constructor */
    public BcwtPatron() {
    }


    // Property accessors

    public Long getPatronid() {
        return this.patronid;
    }

    public void setPatronid(Long patronid) {
        this.patronid = patronid;
    }

    public BcwtPatronType getBcwtpatrontype() {
        return this.bcwtpatrontype;
    }

    public void setBcwtpatrontype(BcwtPatronType bcwtpatrontype) {
        this.bcwtpatrontype = bcwtpatrontype;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return this.middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailid() {
        return this.emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getPatronpassword() {
        return this.patronpassword;
    }

    public void setPatronpassword(String patronpassword) {
        this.patronpassword = patronpassword;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Date getDateofbirth() {
        return this.dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getIsautogenerated() {
        return this.isautogenerated;
    }

    public void setIsautogenerated(String isautogenerated) {
        this.isautogenerated = isautogenerated;
    }

    public String getLockstatus() {
        return this.lockstatus;
    }

    public void setLockstatus(String lockstatus) {
        this.lockstatus = lockstatus;
    }

    public String getActivestatus() {
        return this.activestatus;
    }

    public void setActivestatus(String activestatus) {
        this.activestatus = activestatus;
    }

    public Long getLogincount() {
        return this.logincount;
    }

    public void setLogincount(Long logincount) {
        this.logincount = logincount;
    }

    public Date getLastlogin() {
        return this.lastlogin;
    }

    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Set getBcwtpwdverifiies() {
        return this.bcwtpwdverifiies;
    }

    public void setBcwtpwdverifiies(Set bcwtpwdverifiies) {
        this.bcwtpwdverifiies = bcwtpwdverifiies;
    }

    public Set getBcwtpatronaddresses() {
        return this.bcwtpatronaddresses;
    }

    public void setBcwtpatronaddresses(Set bcwtpatronaddresses) {
        this.bcwtpatronaddresses = bcwtpatronaddresses;
    }

    public Set getBcwtpatronbreezecards() {
        return this.bcwtpatronbreezecards;
    }

    public void setBcwtpatronbreezecards(Set bcwtpatronbreezecards) {
        this.bcwtpatronbreezecards = bcwtpatronbreezecards;
    }

    public Set getBcwtpatronpaymentcardses() {
        return this.bcwtpatronpaymentcardses;
    }

    public void setBcwtpatronpaymentcardses(Set bcwtpatronpaymentcardses) {
        this.bcwtpatronpaymentcardses = bcwtpatronpaymentcardses;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((patronid == null) ? 0 : patronid.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BcwtPatron other = (BcwtPatron) obj;
		if (patronid == null) {
			if (other.patronid != null)
				return false;
		} else if (!patronid.equals(other.patronid))
			return false;
		return true;
	}


	public Date getCreateddate() {
		return createddate;
	}


	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}


	public Long getParentpatronid() {
		return parentpatronid;
	}


	public void setParentpatronid(Long parentpatronid) {
		this.parentpatronid = parentpatronid;
	}
	
	public Set getBcwtorders() {
        return this.bcwtorders;
    }
    
    public void setBcwtorders(Set bcwtorders) {
        this.bcwtorders = bcwtorders;
    }

    public Set getBcwteventses() {
        return this.bcwteventses;
    }
    
    public void setBcwteventses(Set bcwteventses) {
        this.bcwteventses = bcwteventses;
    }


}