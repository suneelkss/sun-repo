package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;

public class UpassAddress implements java.io.Serializable {


    // Fields

     private Long upassaddressid;
     
     public Long getUpassaddressid() {
		return upassaddressid;
	}


	public void setUpassaddressid(Long upassaddressid) {
		this.upassaddressid = upassaddressid;
	}


	

	private UpassSchools upassSchools;
     private BcwtState bcwtstate;
     private String nickname;
     private String addresseename;
     private String address1;
     private String address2;
     private String city;
     private String zip;
     private String activestatus;
     private String istemporary;
     


    // Constructors

    /** default constructor */
    public UpassAddress() {
    }


    // Property accessors

     

    public BcwtState getBcwtstate() {
        return this.bcwtstate;
    }

    public UpassSchools getUpassSchools() {
		return upassSchools;
	}


	public void setUpassSchools(UpassSchools upassSchools) {
		this.upassSchools = upassSchools;
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

  


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((upassaddressid == null) ? 0 : upassaddressid.hashCode());
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
		final UpassAddress other = (UpassAddress) obj;
		if (upassaddressid == null) {
			if (other.upassaddressid != null)
				return false;
		} else if (!upassaddressid.equals(other.upassaddressid))
			return false;
		return true;
	}


}