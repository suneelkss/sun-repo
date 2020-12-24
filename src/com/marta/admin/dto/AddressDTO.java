package com.marta.admin.dto;
/**
 * This DTO is used to store the address details.
 * @author admin
 *
 */
public class AddressDTO implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Long patronaddressid;
    private Long patronid;
    private String state;
    private String nickname;
    private String addresseename;
    private String address1;
    private String address2;
    private String city;
    private String zip;
    private String activestatus;
    private String istemporary;
    private String address;
    private Long stateid;
    private String statecode;
    private String zip5;
    private String zip4;
    
	/**
	 * @return the activestatus
	 */
	public String getActivestatus() {
		return activestatus;
	}
	/**
	 * @param activestatus the activestatus to set
	 */
	public void setActivestatus(String activestatus) {
		this.activestatus = activestatus;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}
	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * @return the addresseename
	 */
	public String getAddresseename() {
		return addresseename;
	}
	/**
	 * @param addresseename the addresseename to set
	 */
	public void setAddresseename(String addresseename) {
		this.addresseename = addresseename;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the istemporary
	 */
	public String getIstemporary() {
		return istemporary;
	}
	/**
	 * @param istemporary the istemporary to set
	 */
	public void setIstemporary(String istemporary) {
		this.istemporary = istemporary;
	}
	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}
	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/**
	 * @return the patronaddressid
	 */
	public Long getPatronaddressid() {
		return patronaddressid;
	}
	/**
	 * @param patronaddressid the patronaddressid to set
	 */
	public void setPatronaddressid(Long patronaddressid) {
		this.patronaddressid = patronaddressid;
	}
	/**
	 * @return the patronid
	 */
	public Long getPatronid() {
		return patronid;
	}
	/**
	 * @param patronid the patronid to set
	 */
	public void setPatronid(Long patronid) {
		this.patronid = patronid;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the statecode
	 */
	public String getStatecode() {
		return statecode;
	}
	/**
	 * @param statecode the statecode to set
	 */
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	/**
	 * @return the stateid
	 */
	public Long getStateid() {
		return stateid;
	}
	/**
	 * @param stateid the stateid to set
	 */
	public void setStateid(Long stateid) {
		this.stateid = stateid;
	}
	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return the zip4
	 */
	public String getZip4() {
		return zip4;
	}
	/**
	 * @param zip4 the zip4 to set
	 */
	public void setZip4(String zip4) {
		this.zip4 = zip4;
	}
	/**
	 * @return the zip5
	 */
	public String getZip5() {
		return zip5;
	}
	/**
	 * @param zip5 the zip5 to set
	 */
	public void setZip5(String zip5) {
		this.zip5 = zip5;
	}
    
	
}
