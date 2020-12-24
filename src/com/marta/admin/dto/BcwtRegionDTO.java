package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtRegionDTO implements Serializable{
	
	

    private Long regionid;
    private String regionname;
    private String regioncode;
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
    

}
