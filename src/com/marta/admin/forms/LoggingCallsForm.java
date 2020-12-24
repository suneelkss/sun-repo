package com.marta.admin.forms;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;
import com.marta.admin.utils.Constants;
public class LoggingCallsForm extends ValidatorActionForm{
	
		private String companyName;
		private String companyId;
		private String administratorEmail ;
		private String administratorPhoneNumber ;
		private String administratorUserName ;
		private String reasonForCall ;
		private String dateOfCall ;
		private String timeStampForCall;
		private String note;
		private String fromDate;
		private String firstname;
		private String emailid;
		private String partner;
		private String others;
		private String userTypeName;
		private String patronId;
		private String patronTypeId;
		private String adminpatronTypeId;
		private String adminpatronId;
		private FormFile logFile;
		
		private String logType;
		private String userName;
		private String userType;
		
		private String newadministratorEmail;
		
		private String hiddenLogType;
		private String hiddenPatronId;
		private String hiddenPatronTypeId;
		private String hiddenEmailId;
		
		
		public void reset(ActionMapping arg0, HttpServletRequest arg1) {
			// TODO Auto-generated method stub
			this.reasonForCall = "";
			this.note = "";
		}
		
		/**
		 * @return the hiddenEmailId
		 */
		public String getHiddenEmailId() {
			return hiddenEmailId;
		}
		/**
		 * @param hiddenEmailId the hiddenEmailId to set
		 */
		public void setHiddenEmailId(String hiddenEmailId) {
			this.hiddenEmailId = hiddenEmailId;
		}
		/**
		 * @return the hiddenPatronId
		 */
		public String getHiddenPatronId() {
			return hiddenPatronId;
		}
		/**
		 * @param hiddenPatronId the hiddenPatronId to set
		 */
		public void setHiddenPatronId(String hiddenPatronId) {
			this.hiddenPatronId = hiddenPatronId;
		}
		/**
		 * @return the hiddenPatronTypeId
		 */
		public String getHiddenPatronTypeId() {
			return hiddenPatronTypeId;
		}
		/**
		 * @param hiddenPatronTypeId the hiddenPatronTypeId to set
		 */
		public void setHiddenPatronTypeId(String hiddenPatronTypeId) {
			this.hiddenPatronTypeId = hiddenPatronTypeId;
		}
		/**
		 * @return the hiddenLogType
		 */
		public String getHiddenLogType() {
			return hiddenLogType;
		}
		/**
		 * @param hiddenLogType the hiddenLogType to set
		 */
		public void setHiddenLogType(String hiddenLogType) {
			this.hiddenLogType = hiddenLogType;
		}
		public String getNewadministratorEmail() {
			return newadministratorEmail;
		}
		public void setNewadministratorEmail(String newadministratorEmail) {
			this.newadministratorEmail = newadministratorEmail;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getLogType() {
			return logType;
		}
		public void setLogType(String logType) {
			this.logType = logType;
		}
		public String getPatronId() {
			return patronId;
		}
		public void setPatronId(String patronId) {
			this.patronId = patronId;
		}
		public String getUserTypeName() {
			return userTypeName;
		}
		public void setUserTypeName(String userTypeName) {
			this.userTypeName = userTypeName;
		}
		public String getOthers() {
			return others;
		}
		public void setOthers(String others) {
			this.others = others;
		}
		public String getPartner() {
			return partner;
		}
		public void setPartner(String partner) {
			this.partner = partner;
		}
		public String getEmailid() {
			return emailid;
		}
		public void setEmailid(String emailid) {
			this.emailid = emailid;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getAdministratorEmail() {
			return administratorEmail;
		}
		public void setAdministratorEmail(String administratorEmail) {
			this.administratorEmail = administratorEmail;
		}
		public String getAdministratorPhoneNumber() {
			return administratorPhoneNumber;
		}
		public void setAdministratorPhoneNumber(String administratorPhoneNumber) {
			this.administratorPhoneNumber = administratorPhoneNumber;
		}
		
		public String getCompanyId() {
			return companyId;
		}
		public void setCompanyId(String companyId) {
			this.companyId = companyId;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getDateOfCall() {
			return dateOfCall;
		}
		public void setDateOfCall(String dateOfCall) {
			this.dateOfCall = dateOfCall;
		}
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		public String getReasonForCall() {
			return reasonForCall;
		}
		public void setReasonForCall(String reasonForCall) {
			this.reasonForCall = reasonForCall;
		}
		public String getTimeStampForCall() {
			return timeStampForCall;
		}
		public void setTimeStampForCall(String timeStampForCall) {
			this.timeStampForCall = timeStampForCall;
		}
		public String getAdministratorUserName() {
			return administratorUserName;
		}
		public void setAdministratorUserName(String administratorUserName) {
			this.administratorUserName = administratorUserName;
		}
		public String getPatronTypeId() {
			return patronTypeId;
		}
		public void setPatronTypeId(String patronTypeId) {
			this.patronTypeId = patronTypeId;
		}
		public String getAdminpatronId() {
			return adminpatronId;
		}
		public void setAdminpatronId(String adminpatronId) {
			this.adminpatronId = adminpatronId;
		}
		public String getAdminpatronTypeId() {
			return adminpatronTypeId;
		}
		public void setAdminpatronTypeId(String adminpatronTypeId) {
			this.adminpatronTypeId = adminpatronTypeId;
		}
		public String getUserType() {
			return userType;
		}
		public void setUserType(String userType) {
			this.userType = userType;
		}
		/**
		 * @return the logFile
		 */
		public FormFile getLogFile() {
			return logFile;
		}
		/**
		 * @param logFile the logFile to set
		 */
		public void setLogFile(FormFile logFile) {
			this.logFile = logFile;
		}
}
