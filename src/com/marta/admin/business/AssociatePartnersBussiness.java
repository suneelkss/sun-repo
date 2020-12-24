/**
 * 
 */
package com.marta.admin.business;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


import com.marta.admin.dao.SearchDAO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.SearchForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;



public class AssociatePartnersBussiness extends DispatchAction {
	final String ME = "AssociatePartnersAction: ";
	

	/**
	 * Method to get PartnerSearch Details.
	 * 
	 * @return partnerSearch
	 * @throws MartaException
	 */
	public List getPartnerDetails(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
	
		final String MY_NAME = ME + " getAdminSearchList: ";
		BcwtsLogger.debug(MY_NAME + " getting user information ");
		List patronList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			patronList = searchDAO.getPartnerDetails(searchDTO,patronId);
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
		}
		return patronList;	
	}
	
	
	public List gbsPatronSearch(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
		
		final String MY_NAME = ME + " gbsPatronSearch: ";
		BcwtsLogger.debug(MY_NAME + " getting gbs patron information ");
		List gbsPatronList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			gbsPatronList = searchDAO.getGbsPatronDetails(searchDTO,patronId);
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
		}
		return gbsPatronList;	
	}
	
   public List getpartnerMemberSearch(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
		
		final String MY_NAME = ME + " partnerMemberSearch: ";
		BcwtsLogger.debug(MY_NAME + " getting partner Member Search information ");
		List partnerMemberSearchList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			partnerMemberSearchList = searchDAO.getpartnerMemberSearchDetails(searchDTO,patronId);
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
		}
		return partnerMemberSearchList;	
	}
	
	
   /**
	 * Method to get BreezeCard Search Details.
	 * 
	 * @return breezeCard Search
	 * @throws MartaException
	 */
	public List getBreezeCardDetails(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
	
		
		final String MY_NAME = ME + " getBreezeCardDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting breeze card user information ");
		List breezecCardList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			breezecCardList = searchDAO.getBreezeCardDetails(searchDTO,patronId);
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
		}
		return breezecCardList;	
	}
	/**
	 * Method to get order search details
	 * 
	 * @param searchDTO
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public List getOrderDetails(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
		
		final String MY_NAME = ME + " getOrderDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting order search details ");
		List orderList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			orderList = searchDAO.getOrderDetails(searchDTO,patronId);
			BcwtsLogger.info(MY_NAME + " got order search List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting order search list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_SEARCH_FIND));
		}
		return orderList;	
	}
	 /**
	 * Method to get AccountAdmin  Search Details.
	 * 
	 * @return accountAdmin Search
	 * @throws MartaException
	 */
	public List getAccountAdminDetails(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
		
		final String MY_NAME = ME + " getAdminSearchList: ";
		BcwtsLogger.debug(MY_NAME + " getting user information ");
		List patronList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			patronList = searchDAO.getAccountAdminDetails(searchDTO,patronId);
			BcwtsLogger.info(MY_NAME + " got user List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
		}
		return patronList;	
	}
	public List displayPartnerSearchList(String patronId,String patronTypeId,String emailId) throws MartaException{
		
		final String MY_NAME = ME + " displayPartnerSearchList: ";
		BcwtsLogger.debug(MY_NAME + " displayPartnerSearchList ");
		List patronList = null;
		try {
			SearchDAO searchDAO = new SearchDAO();
			patronList = searchDAO.displayPartnerSearchList(patronId,patronTypeId,emailId);
			BcwtsLogger.info(MY_NAME + "displayPartnerSearchList");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in display PartnerSearchList :"+e.getMessage());
		}
		return patronList;	
	}


/*
		public List getOrderDetails(BcwtSearchDTO searchDTO,String patronId) throws MartaException{
		
			final String MY_NAME = ME + " getAdminSearchList: ";
			BcwtsLogger.debug(MY_NAME + " getting user information ");
			List orderList = null;
			try {
				SearchDAO searchDAO = new SearchDAO();
				orderList = searchDAO.getPartnerDetails(searchDTO,patronId);
				BcwtsLogger.info(MY_NAME + " got user List");
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + " Exception in getting user list :"+e.getMessage());
				throw new MartaException(PropertyReader
						.getValue(MartaConstants.BCWTS_PATRON_USER_FIND));
			}
			return orderList;	
		}

	/*
	
public ActionForward partnerNumbersearch(ActionMapping mapping, 
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws MartaException{

	final String MY_NAME = ME + "search: ";
	BcwtsLogger.debug(MY_NAME + "Search page");
	String returnPath = null;
	BcwtSearchDTO associatePartnersDTO = null;
	AssociatePartnersDAO associatePartnersDAO = new AssociatePartnersDAO();
	Long partnerId = new Long(0);
	String userName = null;
	returnPath = "search";
	
	try {
		form.reset(mapping, request);
		HttpSession session = request.getSession(true);
		if (null != session.getAttribute(Constants.USER_INFO)) {
			associatePartnersDTO = (BcwtSearchDTO) session
					.getAttribute(Constants.USER_INFO);
			
			partnerId =  associatePartnersDTO.getPartnerid();
			userName =  associatePartnersDTO.getFirstname() + " "
					+  associatePartnersDTO.getLastname();
		}			
		BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
		session.removeAttribute(Constants.CARD_DETAILS_LIST);
	}catch (Exception e) {			
		BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
		returnPath = ErrorHandler.handleError(e, "", request, mapping);
	}		
	return mapping.findForward(returnPath);		
}

public ActionForward GBSPatronsearch(ActionMapping mapping, 
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws MartaException{

	final String MY_NAME = ME + "search: ";
	BcwtsLogger.debug(MY_NAME + "Search page");
	String returnPath = null;
	BcwtSearchDTO associatePartnersDTO = null;
	AssociatePartnersDAO associatePartnersDAO = new AssociatePartnersDAO();
	Long partnerId = new Long(0);
	String userName = null;
	returnPath = "search";
	
	try {
		form.reset(mapping, request);
		HttpSession session = request.getSession(true);
		if (null != session.getAttribute(Constants.USER_INFO)) {
			associatePartnersDTO = (BcwtSearchDTO) session
					.getAttribute(Constants.USER_INFO);
			
			partnerId =  associatePartnersDTO.getPartnerid();
			userName =  associatePartnersDTO.getFirstname() + " "
					+  associatePartnersDTO.getLastname();
		}			
		BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
		session.removeAttribute(Constants.CARD_DETAILS_LIST);
	}catch (Exception e) {			
		BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
		returnPath = ErrorHandler.handleError(e, "", request, mapping);
	}		
	return mapping.findForward(returnPath);		
}
*/
	
	
}