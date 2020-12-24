package com.marta.admin.dto;

import java.io.Serializable;

import com.marta.admin.hibernate.PartnerBatchDetailsId;

/**
 * This DTO is used to store the Tma Edit Queue Details 
 * link details.
 * @author admin
 *
 */
public class TmaEditQueueDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String breezecardid;
	private String customermemberid;
	private String companyname;
	private String action;
	private int actiontype;
	private PartnerBatchDetailsId partnerBatchDetailsId;
	
	
	

	public PartnerBatchDetailsId getPartnerBatchDetailsId() {
		return partnerBatchDetailsId;
	}
	public void setPartnerBatchDetailsId(PartnerBatchDetailsId partnerBatchDetailsId) {
		this.partnerBatchDetailsId = partnerBatchDetailsId;
	}
	/**
	 * @return the actiontype
	 */
	public int getActiontype() {
		return actiontype;
	}
	/**
	 * @param actiontype the actiontype to set
	 */
	public void setActiontype(int actiontype) {
		this.actiontype = actiontype;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the breezecardid
	 */
	public String getBreezecardid() {
		return breezecardid;
	}
	/**
	 * @param breezecardid the breezecardid to set
	 */
	public void setBreezecardid(String breezecardid) {
		this.breezecardid = breezecardid;
	}
	/**
	 * @return the companyname
	 */
	public String getCompanyname() {
		return companyname;
	}
	/**
	 * @param companyname the companyname to set
	 */
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	/**
	 * @return the customermemberid
	 */
	public String getCustomermemberid() {
		return customermemberid;
	}
	/**
	 * @param customermemberid the customermemberid to set
	 */
	public void setCustomermemberid(String customermemberid) {
		this.customermemberid = customermemberid;
	}
	
	
	
}