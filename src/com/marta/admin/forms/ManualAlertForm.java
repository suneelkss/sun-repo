package com.marta.admin.forms;

import org.apache.struts.validator.ValidatorActionForm;

public class ManualAlertForm extends ValidatorActionForm{
	
	private String alertTitle;
	private String dateToReleaseAlert;
	private String dateToDiscontinueAlert;
	//private String partnerToReceiveAlert;
	private String alertMessage;
	//for edit values
	private String editAlertTitle;
	private String editDateToReleaseAlert;
	private String editDateToDiscontinueAlert;
	private String editAlertMessage;
	private String editManualAlertId;
	
	//to check date function
	private String currentDate;
	
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
	public String getEditAlertMessage() {
		return editAlertMessage;
	}
	public void setEditAlertMessage(String editAlertMessage) {
		this.editAlertMessage = editAlertMessage;
	}
	public String getEditAlertTitle() {
		return editAlertTitle;
	}
	public void setEditAlertTitle(String editAlertTitle) {
		this.editAlertTitle = editAlertTitle;
	}
	public String getEditDateToDiscontinueAlert() {
		return editDateToDiscontinueAlert;
	}
	public void setEditDateToDiscontinueAlert(String editDateToDiscontinueAlert) {
		this.editDateToDiscontinueAlert = editDateToDiscontinueAlert;
	}
	public String getEditDateToReleaseAlert() {
		return editDateToReleaseAlert;
	}
	public void setEditDateToReleaseAlert(String editDateToReleaseAlert) {
		this.editDateToReleaseAlert = editDateToReleaseAlert;
	}
	public String getEditManualAlertId() {
		return editManualAlertId;
	}
	public void setEditManualAlertId(String editManualAlertId) {
		this.editManualAlertId = editManualAlertId;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	

}
