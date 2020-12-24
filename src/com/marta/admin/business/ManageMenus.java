package com.marta.admin.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.marta.admin.dao.MenuDAO;
import com.marta.admin.dto.BcwtMenuDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;

/**
 * @author Subamathi
 * This is the business class for menus
 */
public class ManageMenus {

	final String ME = "ManageMenus: " ;
	
	/**
	 * Method to get patrontype menus
	 * 
	 * @param patronTypeId	 
	 * @return List
	 * @throws MartaException
	 */
	public List getMenusForPatronType(Long patronTypeId,String contexName) throws MartaException {
		final String MY_NAME = ME + "getMenusForPatronType: ";
		BcwtsLogger.info(MY_NAME);			
		MenuDAO menuDAO = null;
		List menusTemp = new ArrayList();	
		Menu menus = null;
		try{
			BcwtsLogger.debug(MY_NAME + "getting patron type specific menus");
			menuDAO = new MenuDAO();
			List menuDTOFromDB = menuDAO.getMenusForPatronType(patronTypeId);	
			for (Iterator iter = menuDTOFromDB.iterator(); iter.hasNext();) {
				BcwtMenuDTO bcwtMenuDTO = (BcwtMenuDTO) iter.next();
				if(bcwtMenuDTO.getParentmenuid().intValue() == 0
						){
					menus = new Menu();
					menus.setMenuId(bcwtMenuDTO.getMenuid());
					menus.setMenuName(bcwtMenuDTO.getMenuname());
					menus.setActionLink(bcwtMenuDTO.getMenuactionlink());
					menus.setSubMenus(getSubMenus(bcwtMenuDTO.getMenuid(),menuDTOFromDB));
					menusTemp.add(menus);
				}
				
			}
			BcwtsLogger.info(MY_NAME + "returning patron type specific menus");
		}catch(Exception e){
			BcwtsLogger.error(MY_NAME + e.getMessage());
			throw new MartaException(PropertyReader
			.getValue(MartaConstants.BCWTS_MENUS_FIND));
		}
		return menusTemp;
	}
	
	/**
	 * Method to get sub menus.
	 * 
	 * @param menuId
	 * @param menusFromDB
	 * @return
	 */
	private List getSubMenus(Long menuId,List menusFromDB){
		List subMenus = new ArrayList();
		Menu menus = null;
		for (Iterator iter = menusFromDB.iterator(); iter.hasNext();) {
			BcwtMenuDTO bcwtMenuDTO = (BcwtMenuDTO) iter.next();
			if(menuId.intValue() == bcwtMenuDTO.getParentmenuid().intValue()){
				menus = new Menu();
				menus.setMenuId(bcwtMenuDTO.getMenuid());
				menus.setMenuName(bcwtMenuDTO.getMenuname());
				menus.setActionLink(bcwtMenuDTO.getMenuactionlink());
				subMenus.add(menus);
			}
		}
		return subMenus;
	}
}
