/**
 * 
 */
package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtMenuDTO;
import com.marta.admin.hibernate.BcwtPatronAddress;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;

/**
 * @author Subamathi
 *
 */

public class MenuDAO extends MartaBaseDAO{
	final String ME = "MenuDAO: " ;
	/**
	 * to get the menus details
	 * return List
	 */
	public List getMenusForPatronType(Long patronTypeId) throws Exception {
		final String MY_NAME = ME + "getMenusForPatronType: ";
		BcwtsLogger.debug(MY_NAME);
		List menuList = null;
		Session session = null;
		Transaction transaction = null;
		BcwtMenuDTO bcwtMenuDTO = null;		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select menu.menuid,menu.menuname," +
					"menu.parentmenuid,menu.menuactionlink from BcwtMenuMaster menu," +
					"BcwtPatronTypeMenuAssociation assoc where menu.activestatus = '"
					+ Constants.ACTIVE_STATUS
					+ "' and menu.menuid = assoc.bcwtmenumaster.menuid and assoc.bcwtpatrontype.patrontypeid ="
					+ patronTypeId
					+ " order by menu.menuid";
			List menuListToIterate = session.createQuery(query).list();
			menuList = new ArrayList();
			for (Iterator iter = menuListToIterate.iterator(); iter
					.hasNext();) {				
				Object[] menu = (Object[]) iter
						.next();
				if(!menu[1].toString().equalsIgnoreCase("Breeze Serial Numbers")  ){
				if( !menu[1].toString().equalsIgnoreCase("GBS - Orders")){
					bcwtMenuDTO = new BcwtMenuDTO();
				bcwtMenuDTO.setMenuid(new Long(menu[0].toString()));
				bcwtMenuDTO.setMenuname(menu[1].toString());
				bcwtMenuDTO.setParentmenuid(new Long(menu[2].toString()));
				if(null != menu[3]){
					bcwtMenuDTO.setMenuactionlink(menu[3].toString());
				}
				menuList.add(bcwtMenuDTO);
			}
				}	
			}
			session.flush();
			BcwtsLogger.info(MY_NAME + "got menu list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return menuList;
	}
}
