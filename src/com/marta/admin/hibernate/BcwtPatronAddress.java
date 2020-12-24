package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * Bcwtpatronaddress generated by MyEclipse - Hibernate Tools
 */

public class BcwtPatronAddress  implements java.io.Serializable {


    // Fields

     private Long patronaddressid;
     private BcwtPatron bcwtpatron;
     private BcwtState bcwtstate;
     private String nickname;
     private String addresseename;
     private String address1;
     private String address2;
     private String city;
     private String zip;
     private String activestatus;
     private String istemporary;
     private Set bcwtorders = new HashSet(0);
     private Set bcwtpatronpaymentcardses = new HashSet(0);


    // Constructors

    /** default constructor */
    public BcwtPatronAddress() {
    }


    // Property accessors

    public Long getPatronaddressid() {
        return this.patronaddressid;
    }

    public void setPatronaddressid(Long patronaddressid) {
        this.patronaddressid = patronaddressid;
    }

    public BcwtPatron getBcwtpatron() {
        return this.bcwtpatron;
    }

    public void setBcwtpatron(BcwtPatron bcwtpatron) {
        this.bcwtpatron = bcwtpatron;
    }

    public BcwtState getBcwtstate() {
        return this.bcwtstate;
    }

    public void setBcwtstate(BcwtState bcwtstate) {
        this.bcwtstate = bcwtstate;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddresseename() {
        return this.addresseename;
    }

    public void setAddresseename(String addresseename) {
        this.addresseename = addresseename;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

   

    public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getActivestatus() {
        return this.activestatus;
    }

    public void setActivestatus(String activestatus) {
        this.activestatus = activestatus;
    }

    public String getIstemporary() {
        return this.istemporary;
    }

    public void setIstemporary(String istemporary) {
        this.istemporary = istemporary;
    }

    public Set getBcwtorders() {
        return this.bcwtorders;
    }

    public void setBcwtorders(Set bcwtorders) {
        this.bcwtorders = bcwtorders;
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
		result = PRIME * result + ((patronaddressid == null) ? 0 : patronaddressid.hashCode());
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
		final BcwtPatronAddress other = (BcwtPatronAddress) obj;
		if (patronaddressid == null) {
			if (other.patronaddressid != null)
				return false;
		} else if (!patronaddressid.equals(other.patronaddressid))
			return false;
		return true;
	}


}