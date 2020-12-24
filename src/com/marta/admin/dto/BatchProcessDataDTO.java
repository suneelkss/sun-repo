package com.marta.admin.dto;

import java.io.Serializable;

import com.marta.admin.hibernate.PartnerBatchDetails;

public class BatchProcessDataDTO implements Serializable{

	private String employeeID;
	private String testing;
	private String action;
	private String cardID;
	private String replaceCardID;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneType;
	private String phone;
	private int benefit1ID;
	private String benefit1Type;
	private String benefit1Action;
	private int benefit2ID;
	private String benefit2Type;
	private String benefit2Action;
	private int benefit3ID;
	private String benefit3Type;
	private String benefit3Action;
	private int benefit4ID;
	private String benefit4Type;
	private String benefit4Action;
	private String notes;
	//statuts and error added
	private String status;
	private String error;
	private int passCount;
	private int failCount;
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
public BatchProcessDataDTO() {
		
	}

	public BatchProcessDataDTO(PartnerBatchDetails partnerBatchDetails) {
		this.employeeID = partnerBatchDetails.getCustomerMemberId();
		this.setTesting(partnerBatchDetails.getTest());
		this.setAction(partnerBatchDetails.getAction());
		this.setCardID(partnerBatchDetails.getCardSerialNo());
		this.setReplaceCardID(partnerBatchDetails.getReplaceCardSerialId());
		this.setFirstName(partnerBatchDetails.getFirstName());
		this.setLastName(partnerBatchDetails.getLastName());
		this.setEmail(partnerBatchDetails.getEmail());
		this.setPhone(partnerBatchDetails.getPhone());
		this.setPhoneType(partnerBatchDetails.getPhoneType());
		this.setBenefit1ID(Integer.parseInt(partnerBatchDetails.getBenefitsId()));
	}	
	
	public String getBenefit1Action() {
		return benefit1Action;
	}
	public void setBenefit1Action(String benefit1Action) {
		this.benefit1Action = benefit1Action;
	}
	public int getBenefit1ID() {
		return benefit1ID;
	}
	public void setBenefit1ID(int benefit1ID) {
		this.benefit1ID = benefit1ID;
	}
	public String getBenefit1Type() {
		return benefit1Type;
	}
	public void setBenefit1Type(String benefit1Type) {
		this.benefit1Type = benefit1Type;
	}
	public String getBenefit2Action() {
		return benefit2Action;
	}
	public void setBenefit2Action(String benefit2Action) {
		this.benefit2Action = benefit2Action;
	}
	public int getBenefit2ID() {
		return benefit2ID;
	}
	public void setBenefit2ID(int benefit2ID) {
		this.benefit2ID = benefit2ID;
	}
	public String getBenefit2Type() {
		return benefit2Type;
	}
	public void setBenefit2Type(String benefit2Type) {
		this.benefit2Type = benefit2Type;
	}
	public String getBenefit3Action() {
		return benefit3Action;
	}
	public void setBenefit3Action(String benefit3Action) {
		this.benefit3Action = benefit3Action;
	}
	public int getBenefit3ID() {
		return benefit3ID;
	}
	public void setBenefit3ID(int benefit3ID) {
		this.benefit3ID = benefit3ID;
	}
	public String getBenefit3Type() {
		return benefit3Type;
	}
	public void setBenefit3Type(String benefit3Type) {
		this.benefit3Type = benefit3Type;
	}
	public String getBenefit4Action() {
		return benefit4Action;
	}
	public void setBenefit4Action(String benefit4Action) {
		this.benefit4Action = benefit4Action;
	}
	public int getBenefit4ID() {
		return benefit4ID;
	}
	public void setBenefit4ID(int benefit4ID) {
		this.benefit4ID = benefit4ID;
	}
	public String getBenefit4Type() {
		return benefit4Type;
	}
	public void setBenefit4Type(String benefit4Type) {
		this.benefit4Type = benefit4Type;
	}
	public String getCardID() {
		return cardID;
	}
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
	public String getReplaceCardID() {
		return replaceCardID;
	}
	public void setReplaceCardID(String replaceCardID) {
		this.replaceCardID = replaceCardID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTesting() {
		return testing;
	}
	public void setTesting(String testing) {
		this.testing = testing;
	}
	public String getDataAsString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(this.employeeID);
		stringBuffer.append(","+this.testing);
		stringBuffer.append(","+this.action);
		stringBuffer.append(","+this.cardID);
		stringBuffer.append(","+this.replaceCardID);
		stringBuffer.append(","+this.firstName);
		stringBuffer.append(","+this.lastName);
		stringBuffer.append(","+this.email);
		stringBuffer.append(","+this.phoneType);
		stringBuffer.append(","+this.phone);
		stringBuffer.append(","+this.benefit1ID);
		stringBuffer.append(","+this.benefit1Type);
		stringBuffer.append(","+this.benefit1Action);
		stringBuffer.append(","+this.benefit2ID);
		stringBuffer.append(","+this.benefit2Type);
		stringBuffer.append(","+this.benefit2Action);
		stringBuffer.append(","+this.benefit3ID);
		stringBuffer.append(","+this.benefit3Type);
		stringBuffer.append(","+this.benefit3Action);
		stringBuffer.append(","+this.benefit4ID);
		stringBuffer.append(","+this.benefit4Type);
		stringBuffer.append(","+this.benefit4Action);
		stringBuffer.append(","+this.notes);
		stringBuffer.append(","+this.status);
		stringBuffer.append(","+this.error);
		
		return stringBuffer.toString();
	}

	
}
