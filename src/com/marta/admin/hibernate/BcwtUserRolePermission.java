package com.marta.admin.hibernate;

import java.util.HashSet;
import java.util.Set;

public class BcwtUserRolePermission implements java.io.Serializable {
	private Long rolepermissionid;
	private String addown;
	private String editown;
	private String deleteown;
	private String editothers;
	private String deleteothers;
	private String activestatus;
	private String cancelown;
	private String cancelothers;
	private BcwtPatronType bcwtpatrontype;
	public String getActivestatus() {
		return activestatus;
	}
	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}
	public String getAddown() {
		return addown;
	}
	public void setAddown(String addown) {
		this.addown = addown;
	}
	
	public BcwtPatronType getBcwtpatrontype() {
		return bcwtpatrontype;
	}
	public void setBcwtpatrontype(BcwtPatronType bcwtpatrontype) {
		this.bcwtpatrontype = bcwtpatrontype;
	}
	public String getDeleteothers() {
		return deleteothers;
	}
	public void setDeleteothers(String deleteothers) {
		this.deleteothers = deleteothers;
	}
	public String getDeleteown() {
		return deleteown;
	}
	public void setDeleteown(String deleteown) {
		this.deleteown = deleteown;
	}
	public String getEditothers() {
		return editothers;
	}
	public void setEditothers(String editothers) {
		this.editothers = editothers;
	}
	public String getEditown() {
		return editown;
	}
	public void setEditown(String editown) {
		this.editown = editown;
	}
	public Long getRolepermissionid() {
		return rolepermissionid;
	}
	public void setRolepermissionid(Long rolepermissionid) {
		this.rolepermissionid = rolepermissionid;
	}
	public String getCancelothers() {
		return cancelothers;
	}
	public void setCancelothers(String cancelothers) {
		this.cancelothers = cancelothers;
	}
	public String getCancelown() {
		return cancelown;
	}
	public void setCancelown(String cancelown) {
		this.cancelown = cancelown;
	}
}
