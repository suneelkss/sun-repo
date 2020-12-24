/**
 * 
 */
package com.marta.admin.dto;

/**
 * @author Subamathi
 *
 */
public class BcwtMenuDTO {
	 private Long menuid;
     private String menuname;
     private Long parentmenuid;
     private String menuactionlink;
     private String activestatus; 
	public String getActivestatus() {
		return activestatus;
	}
	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}
	public String getMenuactionlink() {
		return menuactionlink;
	}
	public void setMenuactionlink(String menuactionlink) {
		this.menuactionlink = menuactionlink;
	}
	public Long getMenuid() {
		return menuid;
	}
	public void setMenuid(Long menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public Long getParentmenuid() {
		return parentmenuid;
	}
	public void setParentmenuid(Long parentmenuid) {
		this.parentmenuid = parentmenuid;
	}
}
