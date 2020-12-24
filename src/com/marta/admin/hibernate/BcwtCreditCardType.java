package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;

/**
 * Bcwtcreditcardtype generated by MyEclipse - Hibernate Tools
 */

public class BcwtCreditCardType  implements java.io.Serializable {

    // Fields
     private Long creditcardtypeid;
     private String creditcardname;
     private Long paymentmode;
     private Set bcwtpatronpaymentcards = new HashSet(0);

    // Constructors

    /** default constructor */
    public BcwtCreditCardType() {
    }

    // Property accessors

    public Long getCreditcardtypeid() {
        return this.creditcardtypeid;
    }

    public void setCreditcardtypeid(Long creditcardtypeid) {
        this.creditcardtypeid = creditcardtypeid;
    }

    public String getCreditcardname() {
        return this.creditcardname;
    }

    public void setCreditcardname(String creditcardname) {
        this.creditcardname = creditcardname;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public Set getBcwtpatronpaymentcards() {
		return bcwtpatronpaymentcards;
	}

	public void setBcwtpatronpaymentcards(Set bcwtpatronpaymentcards) {
		this.bcwtpatronpaymentcards = bcwtpatronpaymentcards;
	}

	public Long getPaymentmode() {
		return paymentmode;
	}

	public void setPaymentmode(Long paymentmode) {
		this.paymentmode = paymentmode;
	}

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((creditcardtypeid == null) ? 0 : creditcardtypeid.hashCode());
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
		final BcwtCreditCardType other = (BcwtCreditCardType) obj;
		if (creditcardtypeid == null) {
			if (other.creditcardtypeid != null)
				return false;
		} else if (!creditcardtypeid.equals(other.creditcardtypeid))
			return false;
		return true;
	}

}