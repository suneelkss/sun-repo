package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;


/**
 * Bcwtmenumaster generated by MyEclipse - Hibernate Tools
 */

public class BcwtMenuMaster  implements java.io.Serializable {


    // Fields

     private Long menuid;
     private String menuname;
     private Long parentmenuid;
     private String menuactionlink;
     private String activestatus;
     private Set bcwtpatrontypemenuassociations = new HashSet(0);


    // Constructors

    /** default constructor */
    public BcwtMenuMaster() {
    }


    // Property accessors

    public Long getMenuid() {
        return this.menuid;
    }

    public void setMenuid(Long menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return this.menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public Long getParentmenuid() {
        return this.parentmenuid;
    }

    public void setParentmenuid(Long parentmenuid) {
        this.parentmenuid = parentmenuid;
    }

    public String getMenuactionlink() {
        return this.menuactionlink;
    }

    public void setMenuactionlink(String menuactionlink) {
        this.menuactionlink = menuactionlink;
    }

    public String getActivestatus() {
        return this.activestatus;
    }

    public void setActivestatus(String activestatus) {
        this.activestatus = activestatus;
    }

    public Set getBcwtpatrontypemenuassociations() {
        return this.bcwtpatrontypemenuassociations;
    }

    public void setBcwtpatrontypemenuassociations(Set bcwtpatrontypemenuassociations) {
        this.bcwtpatrontypemenuassociations = bcwtpatrontypemenuassociations;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((menuid == null) ? 0 : menuid.hashCode());
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
		final BcwtMenuMaster other = (BcwtMenuMaster) obj;
		if (menuid == null) {
			if (other.menuid != null)
				return false;
		} else if (!menuid.equals(other.menuid))
			return false;
		return true;
	}

}