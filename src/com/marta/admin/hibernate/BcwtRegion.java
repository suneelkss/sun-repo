package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;

public class BcwtRegion implements java.io.Serializable {


    // Fields    

     private Long regionid;
     private String regionname;
     private String regioncode;
     private Set bcwtbreezecardproducts = new HashSet(0);
     private Set bcwtproduct = new HashSet(0);
//   Property accessors
	public String getRegioncode() {
		return regioncode;
	}
	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}
	public Long getRegionid() {
		return regionid;
	}
	public void setRegionid(Long regionid) {
		this.regionid = regionid;
	}
	public String getRegionname() {
		return regionname;
	}
	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
    
	public Set getBcwtbreezecardproducts() {
		return bcwtbreezecardproducts;
	}
	public void setBcwtbreezecardproducts(Set bcwtbreezecardproducts) {
		this.bcwtbreezecardproducts = bcwtbreezecardproducts;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */

	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((regionid == null) ? 0 : regionid.hashCode());
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
		final BcwtRegion other = (BcwtRegion) obj;
		if (regionid == null) {
			if (other.regionid != null)
				return false;
		} else if (!regionid.equals(other.regionid))
			return false;
		return true;
	}
	public Set getBcwtproduct() {
		return bcwtproduct;
	}
	public void setBcwtproduct(Set bcwtproduct) {
		this.bcwtproduct = bcwtproduct;
	}
	
 
    

}
