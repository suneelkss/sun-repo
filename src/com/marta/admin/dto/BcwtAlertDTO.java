package com.marta.admin.dto;

import java.io.Serializable;

public class BcwtAlertDTO implements Serializable {
	
	private String alertTitle;
	private String dateToReleaseAlert;
	private String dateToDiscontinueAlert;
	private String partnerToReceiveAlert;
	private String alertMessage;
	private String manualAlertId;
	
	//to have edit and delete link
	private String isShow;
	private String isShowRemove;


	
	private Long patronId;
	
	public Long getPatronId() {
		return patronId;
	}
	public void setPatronId(Long patronId) {
		this.patronId = patronId;
	}
	public String getAlertMessage() {
		return alertMessage;
	}
	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}
	public String getAlertTitle() {
		return alertTitle;
	}
	public void setAlertTitle(String alertTitle) {
		this.alertTitle = alertTitle;
	}
	public String getDateToDiscontinueAlert() {
		return dateToDiscontinueAlert;
	}
	public void setDateToDiscontinueAlert(String dateToDiscontinueAlert) {
		this.dateToDiscontinueAlert = dateToDiscontinueAlert;
	}
	public String getDateToReleaseAlert() {
		return dateToReleaseAlert;
	}
	public void setDateToReleaseAlert(String dateToReleaseAlert) {
		this.dateToReleaseAlert = dateToReleaseAlert;
	}
	public String getPartnerToReceiveAlert() {
		return partnerToReceiveAlert;
	}
	public void setPartnerToReceiveAlert(String partnerToReceiveAlert) {
		this.partnerToReceiveAlert = partnerToReceiveAlert;
	}
	public String getManualAlertId() {
		return manualAlertId;
	}
	public void setManualAlertId(String manualAlertId) {
		this.manualAlertId = manualAlertId;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsShowRemove() {
		return isShowRemove;
	}
	public void setIsShowRemove(String isShowRemove) {
		this.isShowRemove = isShowRemove;
	}
	
}
