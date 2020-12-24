/**
 * 
 */
package com.marta.admin.actions;

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

import com.marta.admin.business.AssociatePartnersBussiness;
import com.marta.admin.dao.SearchDAO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.SearchForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.BcwtPatronDTO;

public class SearchAction extends DispatchAction {
	final String ME = "AssociatePartnersAction: ";

	/**
	 * Display partners search details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */

	public ActionForward displaySearchPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "partnerSearch: ";
		BcwtsLogger.debug(MY_NAME + "Searching for the partner users");
		String returnValue = "adminSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.SEARCH_LIST);
		session.removeAttribute(Constants.ORDER_SEARCH_LIST);
		String userName = "";
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					String patronId = loginPatronDTO.getPatronid().toString();
					userName = loginPatronDTO.getFirstname() + " "
							+ loginPatronDTO.getLastname();
				}
			}

			returnValue = "adminSearch";

			// session.setAttribute(Constants.PARTNER_SEARCH_LIST, searchList);
			request.setAttribute("searchForm", searchForm);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME, " >> Search >> Locate Records");
		return mapping.findForward(returnValue);
	}
	/**
	 * Display breezeCard search details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */
	public ActionForward breezeCardSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "breezeCardSearch: ";
		BcwtsLogger.debug(MY_NAME + "Searching for the breeze card users");
		String returnValue = "breezeCardSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		String patronId = "";

		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.SEARCH_LIST);
		session.removeAttribute(Constants.ORDER_SEARCH_LIST);
		String userName = "";

		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();					
				}
			}

			BcwtsLogger.info(MY_NAME + "User Name:" + userName);

			if (!Util.isBlankOrNull(searchForm.getBreezeCardSerialNumber())) {
				searchDTO.setBreezeCardSerialNumber(searchForm
						.getBreezeCardSerialNumber());
			}
			if (!Util.isBlankOrNull(searchForm.getMemberId())) {
				searchDTO.setMemberId(searchForm.getMemberId());
			}
			if (!Util.isBlankOrNull(searchForm.getLastName())) {
				searchDTO.setLastName(searchForm.getLastName());
			}
			if (!Util.isBlankOrNull(searchForm.getFirstName())) {
				searchDTO.setFirstName(searchForm.getFirstName());
			}

			List searchList = associatePartnersBussiness.getBreezeCardDetails(searchDTO, patronId);

			session.setAttribute(Constants.BREEZE_CARD_SEARCH_LIST, searchList);
			request.setAttribute("searchForm", searchForm);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Search >> Locate Records >> Breeze Card Search");
		return mapping.findForward(returnValue);
	}

	/**
	 * Action to retrive Partner Search  details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */

	public ActionForward partnerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {

		final String MY_NAME = ME + "partnerSearch: ";
		BcwtsLogger.debug(MY_NAME + "Searching for the partner users");
		String returnValue = "partnerSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		String patronId = "";

		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.SEARCH_LIST);
		session.removeAttribute(Constants.ORDER_SEARCH_LIST);
		String userName = "";

		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
				}
			}

			BcwtsLogger.info(MY_NAME + "User Name:" + userName);

			if (!Util.isBlankOrNull(searchForm.getPartnerFirstName())) {
				searchDTO.setPartnerFirstName(searchForm.getPartnerFirstName());
			}
			if (!Util.isBlankOrNull(searchForm.getPartnerLastName())) {
				searchDTO.setPartnerLastName(searchForm.getPartnerLastName());
			}
			if (!Util.isBlankOrNull(searchForm.getCompanyName())) {
				searchDTO.setCompanyName(searchForm.getCompanyName());
			}
			if (!Util.isBlankOrNull(searchForm.getCompanyID())) {
				searchDTO.setCompanyID(searchForm.getCompanyID());
			}

			List searchList = associatePartnersBussiness.getPartnerDetails(
					searchDTO, patronId);

			session.setAttribute(Constants.PARTNER_SEARCH_LIST, searchList);
			request.setAttribute("searchForm", searchForm);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Search >> Locate Records >> Partner Search");
		return mapping.findForward(returnValue);
	}

	public ActionForward partnerMemberSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "partnerMemberSearch: ";
		BcwtsLogger.debug(MY_NAME
				+ "Searching for the partnerMemberSearch users");
		String returnValue = "partnerMemberSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		String patronId = "";

		HttpSession session = request.getSession(true);

		String companyName = "";

		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
				}
			}

			BcwtsLogger.info(MY_NAME + "User Name:" + companyName);

			if (!Util.isBlankOrNull(searchForm.getCompanyName())) {
				searchDTO.setCompanyName(searchForm.getCompanyName());
			}
			if (!Util.isBlankOrNull(searchForm.getMemberFirstName())) {
				searchDTO.setMemberFirstName(searchForm.getMemberFirstName());
			}
			if (!Util.isBlankOrNull(searchForm.getMemberLastName())) {
				searchDTO.setMemberLastName(searchForm.getMemberLastName());
			}

			List partnerMemberSearchList = associatePartnersBussiness
					.getpartnerMemberSearch(searchDTO, patronId);

			session.setAttribute(Constants.PARTNER_MEMBER_SEARCH_LIST,
					partnerMemberSearchList);
			request.setAttribute("searchForm", searchForm);
			BcwtsLogger.info(MY_NAME + "User Name:" + companyName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Search >> Locate Records >> Partner Member Search");
		return mapping.findForward(returnValue);
	}

	/**
	 * Action to retrive GBS Patron search details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward gbsPatronSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "gbspatronSearch: ";

		BcwtsLogger.debug(MY_NAME + "Searching for the GBS patron ");
		String returnValue = "gbsPatron";
		String patronId = "";

		List searchList = null;

		HttpSession session = request.getSession(true);

		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
				}
			}

			if (!Util.isBlankOrNull(searchForm.getFirstName())) {
				searchDTO.setPatronFirstName(searchForm.getFirstName());
			}
			if (!Util.isBlankOrNull(searchForm.getLastName())) {
				searchDTO.setPatronLastName(searchForm.getLastName());
			}

			searchList = associatePartnersBussiness.gbsPatronSearch(searchDTO,
					patronId);

			session.setAttribute(Constants.GBS_PATRON_SEARCH_LIST, searchList);
			request.setAttribute("searchForm", searchForm);
			returnValue = "gbsPatron";
			// BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Search >> Locate Records >> GBS Patron Search");
		return mapping.findForward(returnValue);
	}

	/**
	 * Action to retrive order search details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward orderSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {

		final String MY_NAME = ME + "orderSearch: ";
		BcwtsLogger.debug(MY_NAME + "Searching for the order users");
		String returnValue = "orderSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		String patronId = "";
		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.SEARCH_LIST);
		session.removeAttribute(Constants.ORDER_SEARCH_LIST);
		String userName = "";
		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
					userName = loginPatronDTO.getFirstname() + " "
							+ loginPatronDTO.getLastname();
				}
			}

			BcwtsLogger.info(MY_NAME + "User Name:" + userName);

			if (!Util.isBlankOrNull(searchForm.getOrderNumber())) {
				searchDTO.setOrderNumber(searchForm.getOrderNumber());
			}
			if (!Util.isBlankOrNull(searchForm.getOrderType())) {
				searchDTO.setOrderType(searchForm.getOrderType());
			}
			if (!Util.isBlankOrNull(searchForm.getMemberFirstName())) {
				searchDTO.setMemberFirstName(searchForm.getMemberFirstName());
			}
			if (!Util.isBlankOrNull(searchForm.getMemberLastName())) {
				searchDTO.setMemberLastName(searchForm.getMemberLastName());
			}

			List searchList = associatePartnersBussiness.getOrderDetails(
					searchDTO, patronId);

			session.setAttribute(Constants.ORDER_SEARCH_LIST, searchList);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while order search :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Search >> Locate Records >> Order Search");
		return mapping.findForward(returnValue);
	}
	/**
	 * Display Account Admin search details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return returnPath
	 * @throws MartaException
	 */
	public ActionForward accountAdminSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "accountAdminSearch: ";
		BcwtsLogger.debug(MY_NAME + "Searching for the partner users");
		String returnValue = "accountAdminSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		String patronId = "";

		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.SEARCH_LIST);
		session.removeAttribute(Constants.ORDER_SEARCH_LIST);
		String userName = "";

		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
				}
			}

			BcwtsLogger.info(MY_NAME + "User Name:" + userName);

			if (!Util.isBlankOrNull(searchForm.getAdminEmail())) {
				searchDTO.setAdminEmail(searchForm.getAdminEmail());
			}
			if (!Util.isBlankOrNull(searchForm.getAdminName())) {
				searchDTO.setAdminName(searchForm.getAdminName());
			}
			if (!Util.isBlankOrNull(searchForm.getAdminPhoneNumber())) {
				searchDTO.setAdminPhoneNumber(searchForm.getAdminPhoneNumber());
			}

			List searchList = associatePartnersBussiness
					.getAccountAdminDetails(searchDTO, patronId);

			session.setAttribute(Constants.ACCOUNT_ADMIN_SEARCH_LIST,
					searchList);
			request.setAttribute("searchForm", searchForm);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Search >> Locate Records >> Account Admin Search");
		return mapping.findForward(returnValue);
	}
	public ActionForward displayPartnerSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {

		final String MY_NAME = ME + "displayPartnerSearch: ";
		BcwtsLogger.debug(MY_NAME + "Searching for the partner users");
		String returnValue = "displayPartnerSearch";
		SearchForm searchForm = (SearchForm) form;
		AssociatePartnersBussiness associatePartnersBussiness = new AssociatePartnersBussiness();
		BcwtSearchDTO searchDTO = new BcwtSearchDTO();
		String patronId = "";

		HttpSession session = request.getSession(true);
		session.removeAttribute(Constants.SEARCH_LIST);
		session.removeAttribute(Constants.ORDER_SEARCH_LIST);
		String userName = "";

		BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();

		try {

			if (null != session.getAttribute(Constants.USER_INFO)) {
				loginPatronDTO = (BcwtPatronDTO) session
						.getAttribute(Constants.USER_INFO);
				if (null != loginPatronDTO) {
					patronId = loginPatronDTO.getPatronid().toString();
				}
			}

			BcwtsLogger.info(MY_NAME + "User Name:" + userName);

			if (!Util.isBlankOrNull(searchForm.getPartnerFirstName())) {
				searchDTO.setPartnerFirstName(searchForm.getPartnerFirstName());
			}
			if (!Util.isBlankOrNull(searchForm.getPartnerLastName())) {
				searchDTO.setPartnerLastName(searchForm.getPartnerLastName());
			}
			if (!Util.isBlankOrNull(searchForm.getCompanyName())) {
				searchDTO.setCompanyName(searchForm.getCompanyName());
			}
			if (!Util.isBlankOrNull(searchForm.getCompanyID())) {
				searchDTO.setCompanyID(searchForm.getCompanyID());
			}

			List searchList = associatePartnersBussiness.getPartnerDetails(
					searchDTO, patronId);

			session.setAttribute(Constants.PARTNER_SEARCH_LIST_WELCOMEPAGE, searchList);
			request.setAttribute("searchForm", searchForm);
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Error while creating an event :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
			e.printStackTrace();
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_SEARCH);
		return mapping.findForward(returnValue);
	}


	/*
	 * public ActionForward breezeCardSearch(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) throws
	 * MartaException {
	 * 
	 * final String MY_NAME = ME + "breezeCardSearch: ";
	 * BcwtsLogger.debug(MY_NAME + "Searching for the breeze card users");
	 * String returnValue = "breezeCardSearch"; SearchForm searchForm =
	 * (SearchForm) form; AssociatePartnersBussiness associatePartnersBussiness =
	 * new AssociatePartnersBussiness(); BcwtSearchDTO searchDTO = new
	 * BcwtSearchDTO(); String patronId = "";
	 * 
	 * HttpSession session = request.getSession(true);
	 * session.removeAttribute(Constants.SEARCH_LIST);
	 * session.removeAttribute(Constants.ORDER_SEARCH_LIST); String userName =
	 * "";
	 * 
	 * 
	 * BcwtPatronDTO loginPatronDTO = new BcwtPatronDTO();
	 * 
	 * try {
	 * 
	 * if (null != session.getAttribute(Constants.USER_INFO)) { loginPatronDTO =
	 * (BcwtPatronDTO) session .getAttribute(Constants.USER_INFO); if (null !=
	 * loginPatronDTO) { patronId = loginPatronDTO.getPatronid().toString(); } }
	 * 
	 * BcwtsLogger.info(MY_NAME + "User Name:" + userName);
	 * 
	 * 
	 * if (!Util.isBlankOrNull(searchForm.getCardSerialNumber())) {
	 * searchDTO.setCardSerialNumber(searchForm.getCardSerialNumber());
	 *  } if (!Util.isBlankOrNull(searchForm.getActiveStatus())) {
	 * searchDTO.setActiveStatus(searchForm.getActiveStatus()); } if
	 * (!Util.isBlankOrNull(searchForm.getBreezeCardName())) {
	 * searchDTO.setBreezeCardName(searchForm.getBreezeCardName()); }
	 * 
	 * List searchList = associatePartnersBussiness.getPartnerDetails(searchDTO,
	 * patronId);
	 * 
	 * session.setAttribute(Constants.BREEZE_CARD_SEARCH_LIST, searchList);
	 * request.setAttribute("searchForm", searchForm); BcwtsLogger.info(MY_NAME +
	 * "User Name:" + userName); } catch (Exception e) {
	 * BcwtsLogger.error(MY_NAME + " Error while creating an event :" +
	 * e.getMessage()); returnValue = ErrorHandler.handleError(e, "", request,
	 * mapping); e.printStackTrace(); }
	 * session.setAttribute(Constants.BREAD_CRUMB_NAME,
	 * Constants.BREADCRUMB_SEARCH); return mapping.findForward(returnValue); }
	 */
}