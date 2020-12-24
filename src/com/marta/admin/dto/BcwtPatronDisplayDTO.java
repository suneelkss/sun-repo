package com.marta.admin.dto;

public class BcwtPatronDisplayDTO {
	
	private String patronId;
	private String firstName;
	private String lastName;
	private int activeStatus;
	
	
	
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getPatronId() {
		return patronId;
	}
	public void setPatronId(String patronId) {
		this.patronId = patronId;
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
	
	
	
	
	

}
