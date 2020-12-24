package com.marta.admin.dto;

/**
 * This DTO used to store partner login details.
 * @author Raj
 *
 */
public class PartnerAdminDetailsDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long partnerid;
	private Long parentPartnerid;
	private Long roleid;
	private String lastname;
	private String firstname;
	private Long companyid;
	private String phoneone;
	private String phonetwo;
	private String fax;
	private Long securityquestionid;
	private String securityanswer;
	private String notes;
	private String statusflag;
	private String email;
	private String username;
	private String password;
	private String address1;
	private String address2;
	private String city;
	private String state;
    private Long partnerId;
	
	private String zip;
	// added for view edit profile
	
	private String rolename;
	private String companyname;
	private String currentusername;
	private String purchaseOrderNo;
	private String companyId;
	
	
	
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}
	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}
	/**
	 * @return the currentusername
	 */
	public String getCurrentusername() {
		return currentusername;
	}
	/**
	 * @param currentusername the currentusername to set
	 */
	public void setCurrentusername(String currentusername) {
		this.currentusername = currentusername;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the companyid
	 */
	public Long getCompanyid() {
		return companyid;
	}
	/**
	 * @param companyid the companyid to set
	 */
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * @return the partnerid
	 */
	public Long getPartnerid() {
		return partnerid;
	}
	/**
	 * @param partnerid the partnerid to set
	 */
	public void setPartnerid(Long partnerid) {
		this.partnerid = partnerid;
	}
	/**
	 * @return the phoneone
	 */
	public String getPhoneone() {
		return phoneone;
	}
	/**
	 * @param phoneone the phoneone to set
	 */
	public void setPhoneone(String phoneone) {
		this.phoneone = phoneone;
	}
	/**
	 * @return the phonetwo
	 */
	public String getPhonetwo() {
		return phonetwo;
	}
	/**
	 * @param phonetwo the phonetwo to set
	 */
	public void setPhonetwo(String phonetwo) {
		this.phonetwo = phonetwo;
	}
	/**
	 * @return the roleid
	 */
	public Long getRoleid() {
		return roleid;
	}
	/**
	 * @param roleid the roleid to set
	 */
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	/**
	 * @return the securityanswer
	 */
	public String getSecurityanswer() {
		return securityanswer;
	}
	/**
	 * @param securityanswer the securityanswer to set
	 */
	public void setSecurityanswer(String securityanswer) {
		this.securityanswer = securityanswer;
	}
	/**
	 * @return the securityquestionid
	 */
	public Long getSecurityquestionid() {
		return securityquestionid;
	}
	/**
	 * @param securityquestionid the securityquestionid to set
	 */
	public void setSecurityquestionid(Long securityquestionid) {
		this.securityquestionid = securityquestionid;
	}
	/**
	 * @return the statusflag
	 */
	public String getStatusflag() {
		return statusflag;
	}
	/**
	 * @param statusflag the statusflag to set
	 */
	public void setStatusflag(String statusflag) {
		this.statusflag = statusflag;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the parentPartnerid
	 */
	public Long getParentPartnerid() {
		return parentPartnerid;
	}
	/**
	 * @param parentPartnerid the parentPartnerid to set
	 */
	public void setParentPartnerid(Long parentPartnerid) {
		this.parentPartnerid = parentPartnerid;
	}
	
}
