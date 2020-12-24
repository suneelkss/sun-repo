/**
 * 
 */
package com.marta.admin.dto;

/**
 * @author Subamathi
 *
 */
public class BcwtStateDTO  implements java.io.Serializable {
	
	//Fields
	private static final long serialVersionUID = 1L;
	private Long stateid;
    private String statename;
    private String statecode;
	public String getStatecode() {
		return statecode;
	}
	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}
	public Long getStateid() {
		return stateid;
	}
	public void setStateid(Long stateid) {
		this.stateid = stateid;
	}
	public String getStatename() {
		return statename;
	}
	public void setStatename(String statename) {
		this.statename = statename;
	}
}
