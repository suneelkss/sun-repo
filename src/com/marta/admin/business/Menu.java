/**
 * 
 */
package com.marta.admin.business;

import java.util.List;

/**
 * @author Subamathi
 *
 */
public class Menu implements java.io.Serializable{
	
	private Long menuId;
	private String menuName;
	private String actionLink;
	private List subMenus;
	public String getActionLink() {
		return actionLink;
	}
	public void setActionLink(String actionLink) {
		this.actionLink = actionLink;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public List getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List subMenus) {
		this.subMenus = subMenus;
	}
}
