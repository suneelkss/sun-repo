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
import org.apache.struts.util.LabelValueBean;

	import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.ListCardsManagement;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.BenefitDetailsDTO;
import com.marta.admin.dto.CustomerBenefitDetailsDTO;
import com.marta.admin.dto.ListCardsCompanyIdDTO;
import com.marta.admin.dto.MemberBenefitDetailsDTO;
import com.marta.admin.dto.ListCardDTO;
	
	import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ListCardForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;
import com.marta.admin.utils.SendMail;


	public class ListCardsAction extends DispatchAction {
		
		final String ME = "PSListCardsAction: ";
		ListCardsManagement listCardsManagement = new ListCardsManagement();
		
		public ActionForward activateUpassBenefit(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "activateBenefit: ";
			BcwtsLogger.debug(MY_NAME + "activateBenefit");
			String returnPath = null;
			
			Long partnerId = new Long(0);
			String userName = null;
			String benefitId = null;
			String benefitName = null;
			String companyId = null;
			String companyName = null;
			boolean isActivated = false;
			BcwtConfigParamsDTO configParamDTO = null;
			returnPath = "activateBenefit";
			
			try {
				ListCardForm psListCardsForm = (ListCardForm) form;
				ListCardDTO psListCardsDTO = new ListCardDTO();
				HttpSession session = request.getSession(true);	
				benefitId = request.getParameter("benefitId");
				if(!Util.isBlankOrNull(benefitId)) {
					psListCardsForm.setBenefitId(benefitId);
				}
				benefitName = request.getParameter("benefitName");
				if(!Util.isBlankOrNull(benefitName)) {
					psListCardsForm.setBenefitName(benefitName);
				}
				if(!Util.isBlankOrNull(benefitId)) {
					psListCardsForm.setBenefitId(benefitId);
				}
				benefitName = request.getParameter("benefitName");
				if(!Util.isBlankOrNull(benefitName)) {
					psListCardsForm.setBenefitName(benefitName);
				}
				if(null != session.getAttribute("COMPANYID") &&
						!Util.isBlankOrNull(session.getAttribute("COMPANYID").toString())){
					companyId = (String) session.getAttribute("COMPANYID");	
					
						
				}
				if(null != request.getParameter("companyName") &&
						!Util.isBlankOrNull(request.getParameter("companyName").toString())){
					companyName = (String) request.getParameter("companyName");	
						
				}
				
				
				psListCardsForm.setCompanyId(companyName);
				
				BeanUtils.copyProperties(psListCardsDTO, psListCardsForm);
				psListCardsDTO.setAction("Yes");
				psListCardsDTO.setAdminName(partnerId.toString());
				
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					isActivated = listCardsManagement.activateUpassBenefit(psListCardsDTO);
				}
				
				if(isActivated) {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_ACTIVATED_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_ACTIVATED_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_EDIT_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while activating benefit : " + Util.getFormattedStackTrace(e));
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}	
			form.reset(mapping, request);
			return mapping.findForward(returnPath);		
		}
	
		
		public ActionForward deActivateUpassBenefit(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "activateBenefit: ";
			BcwtsLogger.debug(MY_NAME + "activateBenefit");
			String returnPath = null;
			
			Long partnerId = new Long(0);
			String userName = null;
			String benefitId = null;
			String benefitName = null;
			String companyId = null;
			String companyName = null;
			boolean isActivated = false;
			BcwtConfigParamsDTO configParamDTO = null;
			returnPath = "activateBenefit";
			
			try {
				ListCardForm psListCardsForm = (ListCardForm) form;
				ListCardDTO psListCardsDTO = new ListCardDTO();
				HttpSession session = request.getSession(true);	
				benefitId = request.getParameter("benefitId");
				if(!Util.isBlankOrNull(benefitId)) {
					psListCardsForm.setBenefitId(benefitId);
				}
				benefitName = request.getParameter("benefitName");
				if(!Util.isBlankOrNull(benefitName)) {
					psListCardsForm.setBenefitName(benefitName);
				}
				if(!Util.isBlankOrNull(benefitId)) {
					psListCardsForm.setBenefitId(benefitId);
				}
				benefitName = request.getParameter("benefitName");
				if(!Util.isBlankOrNull(benefitName)) {
					psListCardsForm.setBenefitName(benefitName);
				}
				if(null != session.getAttribute("COMPANYID") &&
						!Util.isBlankOrNull(session.getAttribute("COMPANYID").toString())){
					companyId = (String) session.getAttribute("COMPANYID");	
					
						
				}
				if(null != request.getParameter("companyName") &&
						!Util.isBlankOrNull(request.getParameter("companyName").toString())){
					companyName = (String) request.getParameter("companyName");	
						
				}
				
				
				psListCardsForm.setCompanyId(companyName);
				
				BeanUtils.copyProperties(psListCardsDTO, psListCardsForm);
				psListCardsDTO.setAction("No");
				psListCardsDTO.setAdminName(partnerId.toString());
				
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					isActivated = listCardsManagement.activateUpassBenefit(psListCardsDTO);
				}
				
				if(isActivated) {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_ACTIVATED_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_ACTIVATED_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_EDIT_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while activating benefit : " + Util.getFormattedStackTrace(e));
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}	
			form.reset(mapping, request);
			return mapping.findForward(returnPath);		
		}
		
		
		
		
		/**
		 * Display card details.
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return returnPath
		 * @throws MartaException
		 */
		
		public ActionForward listCards(ActionMapping mapping, 
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws MartaException{
			
			final String MY_NAME = ME + "listCards: ";
			BcwtsLogger.debug(MY_NAME + "Showing list cards page");
			String returnPath = null;
			String userName = null;
			List memberBenefitListByMemberId = new ArrayList();
			returnPath = "listCards";
			List companyIdList = null;
			ListCardsCompanyIdDTO companyIdDTO = null;
			List cardDetailsList = null;			
			List tmaMemberList = null;
			List memberBenefitList = null;
			try {
				form.reset(mapping, request);
				ListCardForm listCardForm = new ListCardForm();
				tmaMemberList = new ArrayList();
				HttpSession session = request.getSession(true);				
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);
				if(null != request.getParameter("nextfareCompId") &&
						!Util.isBlankOrNull(request.getParameter("nextfareCompId"))){
					listCardForm.setNextfareCompanyId(request.getParameter("nextfareCompId").toString());
					//added for pagination in list card jsp
				//	System.out.println(request.getParameter("nextfareCompId").toString());
					session.setAttribute("PARTNERNEXTFARECOMPID", request.getParameter("nextfareCompId").toString());
				}else if(null != session.getAttribute("PARTNERNEXTFARECOMPID")){
					listCardForm.setNextfareCompanyId(session.getAttribute("PARTNERNEXTFARECOMPID").toString());
				}
				if(null != request.getParameter("companyId") &&
						!Util.isBlankOrNull(request.getParameter("companyId"))){
					listCardForm.setCompanyId(request.getParameter("companyId").toString());
					//added for pagination in list card jsp
					session.setAttribute("PARTNERCOMPANYID", request.getParameter("companyId").toString());
				}else if(null != session.getAttribute("PARTNERCOMPANYID")){
					listCardForm.setCompanyId(session.getAttribute("PARTNERCOMPANYID").toString());
				}
				if(null != request.getParameter("tmaId") &&
						!Util.isBlankOrNull(request.getParameter("tmaId"))){
					listCardForm.setTmaId(request.getParameter("tmaId").toString());
					//added for pagination in list card jsp
					session.setAttribute("TMAID", request.getParameter("tmaId").toString());
				}else if(null != session.getAttribute("TMAID")){
					listCardForm.setTmaId(session.getAttribute("TMAID").toString());
				}
				
				if(request.getParameter("nextfareCompId") == null &&
						request.getParameter("companyId") ==null &&
						request.getParameter("tmaId") == null ){
					if(null != session.getAttribute("PARTNERNEXTFARECOMPID")){
						listCardForm.setNextfareCompanyId(session.getAttribute("PARTNERNEXTFARECOMPID").toString());
						listCardForm.setCompanyId(session.getAttribute("PARTNERCOMPANYID").toString());
						listCardForm.setTmaId(session.getAttribute("TMAID").toString());
					}
				}
				companyIdList = listCardsManagement.getCompanyID(listCardForm);
		
					
				if(null != companyIdList && !companyIdList.isEmpty() ){
					for(Iterator ite = companyIdList.iterator();ite.hasNext();){
						companyIdDTO = (ListCardsCompanyIdDTO) ite.next();
						
						
						listCardForm.setCompanyId(companyIdDTO.getCompanyId());
						session.setAttribute("COMPANYID", companyIdDTO.getCompanyId());
						if(null != companyIdDTO.getCompanyName()){
							session.setAttribute("COMPANYNAME", companyIdDTO.getCompanyName());
						}
						if(null != request.getParameter("tmaId") &&
								!Util.isBlankOrNull(request.getParameter("tmaId"))){
						//	System.out.println("in list cards tma"+companyIdDTO.getNextfareCompanyId());
						//	session.setAttribute("PARTNERNEXTFARECOMPID", companyIdDTO.getNextfareCompanyId());
						}
						cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
						
						List customerBenefitList = listCardsManagement.
								getCustomerBenefitDetails(listCardForm.getNextfareCompanyId());
						if(null != request.getParameter("tmaId") &&
								!Util.isBlankOrNull(request.getParameter("tmaId"))){
							 memberBenefitList = listCardsManagement.
									getTmaMemberBenefitDetailsByCompanyId(companyIdDTO.getNextfareCompanyId(),companyIdDTO.getMemberId());
						
						}else{
							
							 memberBenefitList = listCardsManagement.
									getMemberBenefitDetailsByCompanyId(listCardForm.getNextfareCompanyId());
						}
						
						if(companyIdDTO.getMemberId() !=null &&
								!Util.isBlankOrNull(companyIdDTO.getMemberId())){
							listCardForm.setMemberId(companyIdDTO.getMemberId());
						}
						for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
							ListCardDTO listCardDTO = (ListCardDTO) iter.next();
						if(listCardDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
								listCardForm.setMemberId(companyIdDTO.getMemberId());
							}
							
							for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
								MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
								if(memberBenefitDetailsDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
									if(memberBenefitDetailsDTO.getMemberId().equalsIgnoreCase(listCardDTO.getMemberId())) {
										memberBenefitListByMemberId.add(memberBenefitDetailsDTO);
									}
									List benefitDetails = getBenefitDetails(customerBenefitList, memberBenefitListByMemberId, listCardForm);
					                   
									for (Iterator benefitIter = benefitDetails.iterator(); benefitIter.hasNext();) {
										BenefitDetailsDTO benefitDetailsDTO = (BenefitDetailsDTO) benefitIter.next();
										if(benefitDetailsDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
											if(listCardForm.getMemberId().equalsIgnoreCase(benefitDetailsDTO.getMemberId())){
												listCardDTO.getBenefitDetails().append(benefitDetailsDTO.getBenefitDetails()).append("; ");
											}
										}
										
									}
								}
							}
							listCardDTO.setNextfareCompanyId(listCardForm.getNextfareCompanyId());
							listCardDTO.setCompanyId(companyIdDTO.getCompanyId());
							listCardDTO.setCompanyName(companyIdDTO.getCompanyName());
							tmaMemberList.add(listCardDTO);	
						}
					
					}
				}
			
				session.setAttribute(Constants.CARD_DETAILS_LIST, tmaMemberList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_LIST_CARDS);
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}		
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * To get benefit details by Raj
		 * 
		 * @param customerBenefitList
		 * @param memberBenefitList
		 * @param psListCardsForm
		 * @return cardDetailsWithBenefits
		 * @throws Exception
		 */
		private List getBenefitDetails(List customerBenefitList, List memberBenefitList, 
				ListCardForm listCardForm) throws Exception {
			
			final String MY_NAME = ME + "getBenefitDetails: ";
			BcwtsLogger.debug(MY_NAME);
			List cardDetailsWithBenefits = new ArrayList();
			int count = 0;
			
			try {
				
				for (Iterator iter = customerBenefitList.iterator(); iter.hasNext();) {
					CustomerBenefitDetailsDTO customerBenefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();
					BenefitDetailsDTO benefitDetailsDTO = new BenefitDetailsDTO();
					benefitDetailsDTO.setCustomerId(listCardForm.getCompanyId());
					benefitDetailsDTO.setMemberId(listCardForm.getMemberId());
					benefitDetailsDTO.setBenefitId(customerBenefitDetailsDTO.getBenefitId());
					benefitDetailsDTO.setBenefitName(customerBenefitDetailsDTO.getBenefitName());
					
					// To show benefit status on mouse over in datagrid
					benefitDetailsDTO.setBenefitDetails(customerBenefitDetailsDTO.getBenefitName() + ": ");
					
					for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
						MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
						
						if(memberBenefitDetailsDTO.getCustomerId().equalsIgnoreCase(listCardForm.getCompanyId())
								&& memberBenefitDetailsDTO.getMemberId().equalsIgnoreCase(listCardForm.getMemberId())) {
								
							if(customerBenefitDetailsDTO.getBenefitId().
									equalsIgnoreCase(memberBenefitDetailsDTO.getBenefitId())){
								// To show benefit status on mouse over in datagrid
								benefitDetailsDTO.setBenefitDetails(benefitDetailsDTO.getBenefitDetails() + "Active");
								memberBenefitList.remove(memberBenefitDetailsDTO);
								count ++;
								break;							
							} else {
								// To show benefit status on mouse over in datagrid
								benefitDetailsDTO.setBenefitDetails(benefitDetailsDTO.getBenefitDetails() + "Inactive");
							}
						}
					}
					if(memberBenefitList.isEmpty() && count == 0) {
						benefitDetailsDTO.setBenefitDetails(benefitDetailsDTO.getBenefitDetails() + "Inactive");
					}
					count = 0;
					cardDetailsWithBenefits.add(benefitDetailsDTO);
				}
				BcwtsLogger.debug("cardDetailsWithBenefits.szie()= "  + cardDetailsWithBenefits.size());
			} catch (Exception e) {
				BcwtsLogger.error(MY_NAME + e.getMessage());
				throw e;
			}
			return cardDetailsWithBenefits;
		}
		
		
		/**
		 * Search list card action by Raj
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward searchCard(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
		final String MY_NAME = ME + "searchCard: ";
		BcwtsLogger.debug(MY_NAME + "Showing list cards page");
		String returnPath = null;
		String userName = null;
		List memberBenefitListByMemberId = new ArrayList();
		returnPath = "listCards";
		ListCardsCompanyIdDTO companyIdDTO = null;
		try {
			ListCardsManagement listCardsManagement = new ListCardsManagement();
			ListCardForm listCardForm = (ListCardForm) form;
			
			HttpSession session = request.getSession(true);
			List tmaMemberList = new ArrayList();	
			
			String companyID = request.getParameter("companyId").toString(); 
			if(companyID != null &&
					!Util.isBlankOrNull(companyID)){
				listCardForm.setCompanyId(request.getParameter("companyId").toString());
				//added for pagination in list card jsp
				session.setAttribute("PARTNERCOMPANYID", request.getParameter("companyId").toString());
			}
			
			if(null != request.getParameter("nextfareCompId").toString() &&
					!Util.isBlankOrNull(request.getParameter("nextfareCompId"))){
				listCardForm.setNextfareCompanyId(request.getParameter("nextfareCompId").toString());
				//added for pagination in list card jsp
				session.setAttribute("PARTNERNEXTFARECOMPID", request.getParameter("nextfareCompId").toString());
			}
			
			
			if(null != request.getParameter("tmaId") &&
					!Util.isBlankOrNull(request.getParameter("tmaId"))){
				listCardForm.setTmaId(request.getParameter("tmaId").toString());
				//added for pagination in list card jsp
				session.setAttribute("TMAID", request.getParameter("tmaId").toString());
			}
			
			
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			
			List companyIdList = listCardsManagement.getCompanyID(listCardForm);
			if(null != companyIdList && !companyIdList.isEmpty() ){
				for(Iterator ite = companyIdList.iterator();ite.hasNext();){
					companyIdDTO = (ListCardsCompanyIdDTO) ite.next();
					
					
					listCardForm.setCompanyId(companyIdDTO.getCompanyId());
					session.setAttribute("COMPANYID", companyIdDTO.getCompanyId());
					if(null != companyIdDTO.getCompanyName()){
						session.setAttribute("COMPANYNAME", companyIdDTO.getCompanyName());
					}
					if(null != request.getParameter("tmaId") &&
							!Util.isBlankOrNull(request.getParameter("tmaId"))){
						session.setAttribute("PARTNERNEXTFARECOMPID", companyIdDTO.getNextfareCompanyId());
					}
					List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
					List memberBenefitList= null;
					List customerBenefitList = listCardsManagement.
							getCustomerBenefitDetails(listCardForm.getNextfareCompanyId());
					if(null != request.getParameter("tmaId") &&
							!Util.isBlankOrNull(request.getParameter("tmaId"))){
						 memberBenefitList = listCardsManagement.
								getTmaMemberBenefitDetailsByCompanyId(companyIdDTO.getNextfareCompanyId(),companyIdDTO.getMemberId());
					
					}else{
						
						 memberBenefitList = listCardsManagement.
								getMemberBenefitDetailsByCompanyId(listCardForm.getNextfareCompanyId());
					}
					
					if(companyIdDTO.getMemberId() !=null &&
							!Util.isBlankOrNull(companyIdDTO.getMemberId())){
						listCardForm.setMemberId(companyIdDTO.getMemberId());
					}
					for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
						ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					if(listCardDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
							listCardForm.setMemberId(companyIdDTO.getMemberId());
						}
						
						for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
							MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
							if(memberBenefitDetailsDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
								if(memberBenefitDetailsDTO.getMemberId().equalsIgnoreCase(listCardDTO.getMemberId())) {
									memberBenefitListByMemberId.add(memberBenefitDetailsDTO);
								}
								List benefitDetails = getBenefitDetails(customerBenefitList, memberBenefitListByMemberId, listCardForm);
				                   
								for (Iterator benefitIter = benefitDetails.iterator(); benefitIter.hasNext();) {
									BenefitDetailsDTO benefitDetailsDTO = (BenefitDetailsDTO) benefitIter.next();
									if(benefitDetailsDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
										if(listCardForm.getMemberId().equalsIgnoreCase(benefitDetailsDTO.getMemberId())){
											listCardDTO.getBenefitDetails().append(benefitDetailsDTO.getBenefitDetails()).append("; ");
										}
									}
									
								}
							}
						}
						listCardDTO.setNextfareCompanyId(listCardForm.getNextfareCompanyId());
						listCardDTO.setCompanyId(companyIdDTO.getCompanyId());
						listCardDTO.setCompanyName(companyIdDTO.getCompanyName());
						tmaMemberList.add(listCardDTO);	
					}
				
				}
			}
			
			
			session.setAttribute(Constants.CARD_DETAILS_LIST, tmaMemberList);		
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_LIST_CARDS);			
		}catch (Exception e) {			
			BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
			returnPath = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnPath);		
	}
		
		
		/**
		 * Display card details based on search criteria.
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return returnPath
		 * @throws MartaException
		 */
		public ActionForward listCardsBasedOnSearch(ActionMapping mapping, 
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws MartaException{
			
			final String MY_NAME = ME + "listCards: ";
			BcwtsLogger.debug(MY_NAME + "Showing list cards page");
			String returnPath = null;		
			String userName = null;
			List memberBenefitListByMemberId = new ArrayList();
			returnPath = "listCards";
			List companyIdList = null;
			ListCardsCompanyIdDTO companyIdDTO = null;
			String companyId = null;
			List cardDetailsList = null;
			List tmaMemberList = null;
			String companyID = null;
			List memberBenefitList =null;
			try {
				tmaMemberList = new ArrayList();
				ListCardForm listCardForm = (ListCardForm) form;
				
				HttpSession session = request.getSession(true);				
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);
				if(null != request.getParameter("nextfareCompId").toString() &&
						!Util.isBlankOrNull(request.getParameter("nextfareCompId"))){
					listCardForm.setNextfareCompanyId(request.getParameter("nextfareCompId").toString());
					//added for pagination in list card jsp
					session.setAttribute("PARTNERNEXTFARECOMPID", request.getParameter("nextfareCompId").toString());
				}
				companyID = request.getParameter("companyId").toString(); 
				if(companyID != null &&
						!Util.isBlankOrNull(companyID)){
					listCardForm.setCompanyId(request.getParameter("companyId").toString());
					//added for pagination in list card jsp
					session.setAttribute("PARTNERCOMPANYID", request.getParameter("companyId").toString());
				}
				if(null != request.getParameter("tmaId") &&
						!Util.isBlankOrNull(request.getParameter("tmaId"))){
					listCardForm.setTmaId(request.getParameter("tmaId").toString());
					//added for pagination in list card jsp
					session.setAttribute("TMAID", request.getParameter("tmaId").toString());
				}
				//listCardForm.setCompanyId(String.valueOf(partnerAdminDetailsDTO.getCompanyid()));
				companyIdList = listCardsManagement.getCompanyID(listCardForm);
				for(Iterator ite = companyIdList.iterator();ite.hasNext();){
					companyIdDTO = (ListCardsCompanyIdDTO) ite.next();
					
					companyId = companyIdDTO.getCompanyId();
					if(companyId != null && !Util.isBlankOrNull(companyId)){
						listCardForm.setCompanyId(companyId);
					}
					if(companyIdDTO.getMemberId() !=null &&
							!Util.isBlankOrNull(companyIdDTO.getMemberId())){
						listCardForm.setMemberId(companyIdDTO.getMemberId());
					}
					if(companyIdDTO.getNextfareCompanyId() !=null &&
							!Util.isBlankOrNull(companyIdDTO.getNextfareCompanyId())){
						listCardForm.setNextfareCompanyId(companyIdDTO.getNextfareCompanyId());
					}
				cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				List customerBenefitList = listCardsManagement.
						getCustomerBenefitDetails(listCardForm.getNextfareCompanyId());
				
				if(null != request.getParameter("tmaId") &&
						!Util.isBlankOrNull(request.getParameter("tmaId"))){
					 memberBenefitList = listCardsManagement.
							getTmaMemberBenefitDetailsByCompanyId(companyIdDTO.getNextfareCompanyId(),companyIdDTO.getMemberId());
				
				}else{
					
					 memberBenefitList = listCardsManagement.
							getMemberBenefitDetailsByCompanyId(listCardForm.getNextfareCompanyId());
				}
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					
					if(listCardDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
						listCardForm.setMemberId(companyIdDTO.getMemberId());
					}
					
					for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
						MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
						if(memberBenefitDetailsDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
							if(memberBenefitDetailsDTO.getMemberId().equalsIgnoreCase(listCardForm.getMemberId())) {
								memberBenefitListByMemberId.add(memberBenefitDetailsDTO);
							}
							List benefitDetails = getBenefitDetails(customerBenefitList, memberBenefitListByMemberId, listCardForm);

							for (Iterator benefitIter = benefitDetails.iterator(); benefitIter.hasNext();) {
								BenefitDetailsDTO benefitDetailsDTO = (BenefitDetailsDTO) benefitIter.next();
								if(benefitDetailsDTO.getCustomerId().equalsIgnoreCase(companyIdDTO.getNextfareCompanyId())){
									if(listCardForm.getMemberId().equalsIgnoreCase(benefitDetailsDTO.getMemberId())){
										listCardDTO.getBenefitDetails().append(benefitDetailsDTO.getBenefitDetails()).append("; ");
									}
								}
							}
							
						}
					}
					listCardDTO.setNextfareCompanyId(listCardForm.getNextfareCompanyId());
					tmaMemberList.add(listCardDTO);
				}
				}	
				if(tmaMemberList != null && !tmaMemberList.isEmpty()){
					session.setAttribute(Constants.CARD_DETAILS_LIST, tmaMemberList);
				}else{
					tmaMemberList = new ArrayList();
					session.setAttribute(Constants.CARD_DETAILS_LIST, tmaMemberList);
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_LIST_CARDS);
				form.reset(mapping, request);
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}		
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Search Cards
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward search(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "search: ";
			BcwtsLogger.debug(MY_NAME + "Search page");
			String returnPath = "search";
			String userName = null;
			try {
				form.reset(mapping, request);
				HttpSession session = request.getSession(true);				
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
				session.removeAttribute(Constants.CARD_DETAILS_LIST);
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}		
			return mapping.findForward(returnPath);		
		}
		
		
		public ActionForward submitIssueReply(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "submitIssueReply: ";
			BcwtsLogger.debug(MY_NAME + "submitIssueReply");
			String returnPath = null;	
			returnPath = "showReportedIssues";
			String companyName = null; 
			BcwtPatronDTO bcwtPatronDTO = null;
			Long patronId = new Long(0);
			try {
				HttpSession session = request.getSession(true);	
				if(session.getAttribute(Constants.USER_INFO)!=null){
					bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
					patronId = bcwtPatronDTO.getPatronid();
				}
				
				BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO = new BcwtsPartnerIssueDTO();
				
				String issueReply = request.getParameter("issueDesc");
				String issueId = request.getParameter("id");
				String email = request.getParameter("email");
				bcwtsPartnerIssueDTO.setResolution(issueReply);
				bcwtsPartnerIssueDTO.setAdminid(patronId);
				bcwtsPartnerIssueDTO.setBcwtspartnerissueid(new Long(issueId));
				
				BcwtsPartnerIssueDTO bcwtsPartnerIssueDTOreply = new BcwtsPartnerIssueDTO();
				bcwtsPartnerIssueDTOreply = listCardsManagement.updateIssue(bcwtsPartnerIssueDTO);
				
				
				
				ConfigurationCache configurationCache = new ConfigurationCache();
				BcwtConfigParamsDTO smtpPathDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
						.get("SMTP_SERVER_PATH");
				BcwtConfigParamsDTO fromDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
						.get("WEBMASTER_MAIL_ID");
				String mailContent = "Reply for your issue id:"+issueId
						+"<br> MARTA Reply :"+issueReply
						+"<br> Breeze Card :"+bcwtsPartnerIssueDTOreply.getSerialnumber()
				        +"<br> Member Id :"+bcwtsPartnerIssueDTOreply.getMemberid()
				        +"<br> Company Name :"+bcwtsPartnerIssueDTOreply.getCompanyname(); 
						
				BcwtConfigParamsDTO mailportDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
						.get(Constants.MAIL_PORT);
				BcwtConfigParamsDTO mailprotocolDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
						.get(Constants.MAIL_PROTOCOL);
				
				String path = request.getContextPath();
				
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + path + "/";
				
				String imgPath = basePath + Constants.MARTA_LOGO;
				
			

				

				String mailSubject = "Troubleshooting Form";/*PropertyReader
						.getValue(Constants.BCWTS_ORDER_SUBJECT); */
				
				SendMail.sendMail(fromDTO.getParamvalue(), bcwtsPartnerIssueDTOreply.getEmail().trim(), mailContent, smtpPathDTO
						.getParamvalue(), mailSubject, "", mailportDTO
						.getParamvalue(), mailprotocolDTO.getParamvalue());
				
				SendMail.sendMail(fromDTO.getParamvalue(), "mapartners@itsmarta.com", mailContent, smtpPathDTO
						.getParamvalue(), mailSubject, "", mailportDTO
						.getParamvalue(), mailprotocolDTO.getParamvalue());
				
				if(!Util.isBlankOrNull(bcwtsPartnerIssueDTOreply.getTmaname())){
					SendMail.sendMail(fromDTO.getParamvalue(), bcwtsPartnerIssueDTOreply.getTmaname().trim(), mailContent, smtpPathDTO
							.getParamvalue(), mailSubject, "", mailportDTO
							.getParamvalue(), mailprotocolDTO.getParamvalue());	
				}
				session.removeAttribute("ISSUELIST");	
				List<BcwtsPartnerIssueDTO> bcwtsPartnerIssueList = new ArrayList<BcwtsPartnerIssueDTO>(); 
				bcwtsPartnerIssueList = listCardsManagement.getTMAIssueList();
				session.setAttribute("ISSUELIST", bcwtsPartnerIssueList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_ISSUE_LIST);
				
				
			}catch (Exception e) {			
				BcwtsLogger.error("Error while replying to issue: " + Util.getFormattedStackTrace(e));
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		public ActionForward showReportedIssues(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showReportedIssues: ";
			BcwtsLogger.debug(MY_NAME + "showReportedIssues");
			String returnPath = null;	
			returnPath = "showReportedIssues";
			String companyName = null; 
			
			try {
				
				HttpSession session = request.getSession(true);				
				List<BcwtsPartnerIssueDTO> bcwtsPartnerIssueList = new ArrayList<BcwtsPartnerIssueDTO>(); 
				bcwtsPartnerIssueList = listCardsManagement.getTMAIssueList();
				session.setAttribute("ISSUELIST", bcwtsPartnerIssueList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_ISSUE_LIST);
					
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		
		
		/**
		 * Edit Member Info
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showEditCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showEditCards: ";
			BcwtsLogger.debug(MY_NAME + "showEditCards");
			String returnPath = null;	
			returnPath = "showEditCards";
			String companyName = null; 
			
			try {
				ListCardForm listCardsForm = (ListCardForm) form;
				
				HttpSession session = request.getSession(true);				
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardsForm.setMemberId(memberId);
				}	
				String nextfareCompanyId = request.getParameter("nextfareComId");
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					listCardsForm.setNextfareCompanyId(nextfareCompanyId);
				}
				String companyId = request.getParameter("companyId");
				if(!Util.isBlankOrNull(companyId)){
					listCardsForm.setCompanyId(companyId);
				}
				if(null != session.getAttribute("COMPANYNAME") &&
						!Util.isBlankOrNull(session.getAttribute("COMPANYNAME").toString())){
					companyName = session.getAttribute("COMPANYNAME").toString();
				}
				
				BcwtsLogger.debug("nextfare id"+nextfareCompanyId+"member id"+memberId);
				List cardDetailsList = listCardsManagement.getCardDetails(listCardsForm);
			
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					listCardDTO.setCompanyId(listCardsForm.getCompanyId());
					listCardDTO.setNextfareCompanyId(nextfareCompanyId);
					listCardDTO.setCustomerId(nextfareCompanyId);
					listCardDTO.setCompanyName(companyName);
				
					BeanUtils.copyProperties(listCardsForm, listCardDTO);
					session.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}
				
				// Get benefit details
				List benefitDetails = listCardsManagement.getBenefitDetails(listCardsForm);
				if(benefitDetails !=null && !benefitDetails.isEmpty()){
					session.setAttribute(Constants.BENEFIT_DETAILS_LIST, benefitDetails);
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_EDIT_CARDS);
					
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}

		/**
		 * Edit Member Info
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward editCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "editCards: ";
			BcwtsLogger.debug(MY_NAME + "Edit page");			
			String userName = null;
			boolean isUpdated = false;
			BcwtConfigParamsDTO configParamDTO = null;
			String returnPath = "editCards";	
			
			try {
				ListCardForm listCardsForm = (ListCardForm) form;
				ListCardDTO listCardDTO = new ListCardDTO();
				
				HttpSession session = request.getSession(true);
				
				String nextfareCompanyId = request.getParameter("nextfareComId");
				
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					listCardsForm.setNextfareCompanyId(nextfareCompanyId);
					listCardsForm.setCompanyId(nextfareCompanyId);
					listCardDTO.setCustomerId(nextfareCompanyId);
					session.setAttribute("PARTNERNEXTFARECOMPID",nextfareCompanyId);
				}
				BcwtsLogger.info("nextfare_id"+nextfareCompanyId);
				String companyId = request.getParameter("companyId");
				if(!Util.isBlankOrNull(companyId)){
					listCardsForm.setCompanyId(nextfareCompanyId);
					session.setAttribute("PARTNERCOMPANYID",companyId);
				}
				BeanUtils.copyProperties(listCardDTO, listCardsForm);
				
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					isUpdated = listCardsManagement.updateMemberInfo(listCardDTO);
				} else {				
					isUpdated = true;
				}
							
				if(isUpdated){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.MEMBER_UPDATE_SUCCESS_MESAAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.MEMBER_UPDATE_FAILURE_MESAAGE));
				}
				
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);	
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_EDIT_CARDS);
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}	
			form.reset(mapping, request);
			return mapping.findForward(returnPath);		
		}
		
		
		/**
		 * Show Member Info
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showMemberInfo(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showMemberInfo: ";
			BcwtsLogger.debug(MY_NAME + "showMemberInfo");
			String returnPath = null;
			String userName = null;
			returnPath = "showMemberInfo";
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				
				HttpSession session = request.getSession(true);
				
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}			
				String nextfareCompanyId = request.getParameter("nextfareComId");
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					listCardForm.setNextfareCompanyId(nextfareCompanyId);
				}
				String companyId = request.getParameter("companyId");
				if(!Util.isBlankOrNull(companyId)){
					listCardForm.setCompanyId(companyId);
				}

				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					listCardDTO.setCompanyId(listCardForm.getCompanyId());
					listCardDTO.setNextfareCompanyId(nextfareCompanyId);
					listCardForm.setMemberId(listCardDTO.getMemberId());
					BeanUtils.copyProperties(listCardForm, listCardDTO);
					request.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}
				
				// Get benefit details
				List benefitDetails = listCardsManagement.getBenefitDetails(listCardForm);
				session.setAttribute(Constants.BENEFIT_DETAILS_LIST, benefitDetails);
				
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing Member Info page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Activate Benefit
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward activateBenefit(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "activateBenefit: ";
			BcwtsLogger.debug(MY_NAME + "activateBenefit");
			String returnPath = null;
			String userName = null;
			String benefitId = null;
			String benefitName = null;
			boolean isActivated = false;
			BcwtConfigParamsDTO configParamDTO = null;
			returnPath = "activateBenefit";
			String companyId = null;
			String companyName = null;
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				ListCardDTO listCardDTO = new ListCardDTO();
				
				HttpSession session = request.getSession(true);				
				benefitId = request.getParameter("benefitId");
				if(!Util.isBlankOrNull(benefitId)) {
					listCardForm.setBenefitId(benefitId);
				}
				benefitName = request.getParameter("benefitName");
				if(!Util.isBlankOrNull(benefitName)) {
					listCardForm.setBenefitName(benefitName);
				}
				if(null != session.getAttribute("COMPANYID") &&
						!Util.isBlankOrNull(session.getAttribute("COMPANYID").toString())){
					companyId = (String) session.getAttribute("COMPANYID");	
						
				}
				if(null != request.getParameter("companyName") &&
						!Util.isBlankOrNull(request.getParameter("companyName").toString())){
					companyName = (String) request.getParameter("companyName");	
						
				}
				if(null != listCardForm.getNextfareCompanyId() &&
						!Util.isBlankOrNull(listCardForm.getNextfareCompanyId())){
					listCardDTO.setCustomerId(listCardForm.getNextfareCompanyId());
				}
				listCardForm.setCompanyId(companyId);
				listCardDTO.setCompanyName(companyName);
				BeanUtils.copyProperties(listCardDTO, listCardForm);
				if(null != listCardForm.getSerialNumber() &&
						!Util.isBlankOrNull(listCardForm.getSerialNumber())){
					listCardDTO.setSerialNumber(listCardForm.getSerialNumber());
				}
				
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(listCardDTO.getBenefitName().equalsIgnoreCase(Constants.ANNUAL_PASS_BENEFIT_NAME)) {
					isActivated = listCardsManagement.activateBenefit(listCardDTO);
				} else {
					isActivated = true;
				}
				
				if(isActivated) {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_ACTIVATED_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_ACTIVATED_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_EDIT_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while activating benefit : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}	
			form.reset(mapping, request);
			return mapping.findForward(returnPath);		
		}

		/**
		 * De-Activate Benefit
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward deactivateBenefit(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "deactivateBenefit: ";
			BcwtsLogger.debug(MY_NAME + "deactivateBenefit");
			String returnPath = null;
			String userName = null;
			String benefitId = null;
			String benefitName = null;
			boolean isDeActivated = false;
			BcwtConfigParamsDTO configParamDTO = null;
			returnPath = "deactivateBenefit";
			String companyId = null;
			String companyName = null;
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				ListCardDTO listCardDTO = new ListCardDTO();				
				HttpSession session = request.getSession(true);				
				benefitId = request.getParameter("benefitId");
				if(!Util.isBlankOrNull(benefitId)) {
					listCardForm.setBenefitId(benefitId);
				}
				benefitName = request.getParameter("benefitName");
				if(!Util.isBlankOrNull(benefitName)) {
					listCardForm.setBenefitName(benefitName);
				}
				if(null != session.getAttribute("COMPANYID") &&
						!Util.isBlankOrNull(session.getAttribute("COMPANYID").toString())){
					companyId = (String) session.getAttribute("COMPANYID");	
						
				}
				if(null != request.getParameter("companyName") &&
						!Util.isBlankOrNull(request.getParameter("companyName").toString())){
					companyName = (String) request.getParameter("companyName");	
						
				}
				if(null != listCardForm.getNextfareCompanyId() &&
						!Util.isBlankOrNull(listCardForm.getNextfareCompanyId())){
					listCardDTO.setCustomerId(listCardForm.getNextfareCompanyId());
				}
				listCardForm.setCompanyId(companyId);
				listCardDTO.setCompanyName(companyName);
				BeanUtils.copyProperties(listCardDTO, listCardForm);
				if(null != listCardForm.getSerialNumber() &&
						!Util.isBlankOrNull(listCardForm.getSerialNumber())){
					listCardDTO.setSerialNumber(listCardForm.getSerialNumber());
				}
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					isDeActivated = listCardsManagement.deactivateBenefit(listCardDTO);
				} else if(listCardDTO.getBenefitName().equalsIgnoreCase(Constants.ANNUAL_PASS_BENEFIT_NAME)) {
					isDeActivated = listCardsManagement.deactivateBenefit(listCardDTO);
				} else {
					isDeActivated = true;
				}
							
				if(isDeActivated){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_DEACTIVATED_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.BENEFIT_DEACTIVATED_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_EDIT_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while deactivating benefit  : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}	
			form.reset(mapping, request);
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Replace Cards
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showReplaceCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showReplaceCards: ";
			BcwtsLogger.debug(MY_NAME + "showReplaceCards");
			String userName = null;
			String returnPath = "showReplaceCards";
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				
				HttpSession session = request.getSession(true);				
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}			
				String nextfareCompanyId = request.getParameter("nextfareComId");
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					listCardForm.setNextfareCompanyId(nextfareCompanyId);
				}
				String companyId = request.getParameter("companyId");
				if(!Util.isBlankOrNull(companyId)){
					listCardForm.setCompanyId(companyId);
				}
				
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					listCardDTO.setCompanyId(listCardForm.getCompanyId());
					if(!Util.isBlankOrNull(listCardForm.getNewSerialNumber()) && 
							!Util.isBlankOrNull(listCardForm.getConfirmSerialNumber())) {
						listCardDTO.setNewSerialNumber(listCardForm.getNewSerialNumber());
						listCardDTO.setConfirmSerialNumber(listCardForm.getConfirmSerialNumber());
						listCardDTO.setNextfareCompanyId(listCardForm.getNextfareCompanyId());
					}
					BeanUtils.copyProperties(listCardForm, listCardDTO);
					session.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}
				
				// Get benefit details
				List benefitDetails = listCardsManagement.getBenefitDetails(listCardForm);
				session.setAttribute(Constants.BENEFIT_DETAILS_LIST, benefitDetails);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_REPLACE_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing replace cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Replace Cards(Serial No)
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward replaceCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "replaceCards: ";
			BcwtsLogger.debug(MY_NAME + "replaceCards");
			String returnPath = null;
			String userName = null;
			boolean isReplaced = false;
			BcwtConfigParamsDTO configParamDTO = null;
			ListCardDTO listCardDTO = null;
			boolean isSerialNoExist = false;
			boolean isHotlisted = false;
			boolean isCardAssociated = false;
			returnPath = "replaceCards";
			String customerId = null;
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;							
				HttpSession session = request.getSession(true);				
				String memberId = request.getParameter("memberId");
				String serialNumber = listCardForm.getNewSerialNumber();				
				if(request.getParameter("nextfareComId") != null &&
						!Util.isBlankOrNull(request.getParameter("nextfareComId").toString())){
					customerId = request.getParameter("nextfareComId").toString();
				}
				
				
				isSerialNoExist = listCardsManagement.isSerialNoExist(serialNumber);
				isHotlisted = listCardsManagement.isCardHotlisted(serialNumber);
				isCardAssociated = listCardsManagement.isCardAssociated(serialNumber, customerId, memberId);
				
				if(isSerialNoExist && !isHotlisted && !isCardAssociated) {
					if(!Util.isBlankOrNull(memberId)){
						listCardForm.setMemberId(memberId);
					}
					listCardForm.setNextfareCompanyId(customerId);

					List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
					   
					for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
						listCardDTO = (ListCardDTO) iter.next();
						listCardDTO.setNewSerialNumber(listCardForm.getNewSerialNumber());
					}
					
					configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
					if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
						isReplaced = listCardsManagement.replaceCards(listCardDTO);
					} else {
						isReplaced = true;
					}
		
					if(isReplaced){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.SERIAL_NO_REPLACED_SUCCESS_MESSAGE));
						request.setAttribute(Constants.SUCCESS, "true");
					} else {
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.SERIAL_NO_REPLACED_FAILURE_MESSAGE));
					}
				} else if(!isSerialNoExist){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.SERIAL_NO_NOT_EXIST));
					returnPath = "showReplaceCards";
				} else if(isSerialNoExist && isHotlisted){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.CARD_HOTLISTED_MESSAGE));
					returnPath = "showReplaceCards";
				} else if(isSerialNoExist && !isHotlisted && isCardAssociated){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.CARD_ASSOCIATED_MESSAGE));
					returnPath = "showReplaceCards";
				}
				
				request.setAttribute("listCardForm", listCardForm);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_REPLACE_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing replace cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Hotlist Cards
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showHotlistCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showHotlistCards: ";
			BcwtsLogger.debug(MY_NAME + "showHotlistCards");
			String returnPath = null;			
			String userName = null;
			returnPath = "showHotlistCards";
			String nextfareCompId = null;
			
			try {
				ListCardForm listCardForm = new ListCardForm();				
				HttpSession session = request.getSession(true);			
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}			
				if(null !=request.getParameter("nextfareComId") &&
						!Util.isBlankOrNull(request.getParameter("nextfareComId").toString())){
					nextfareCompId = request.getParameter("nextfareComId").toString();
				}
				if(null != session.getAttribute("COMPANYID")&&
						!Util.isBlankOrNull(session.getAttribute("COMPANYID").toString())){
					listCardForm.setCompanyId(session.getAttribute("COMPANYID").toString());
				}
				listCardForm.setNextfareCompanyId(nextfareCompId);
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					listCardDTO.setCompanyId(listCardForm.getCompanyId());
					listCardDTO.setNextfareCompanyId(nextfareCompId);
					BeanUtils.copyProperties(listCardForm, listCardDTO);
					request.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}
				
				// Get benefit details
				List benefitDetails = listCardsManagement.getBenefitDetails(listCardForm);
				session.setAttribute(Constants.BENEFIT_DETAILS_LIST, benefitDetails);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_HOTLIST_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in Hotlisting cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		
		/**
		 * Hotlist Cards
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward hotlistCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "hotlistCards: ";
			BcwtsLogger.debug(MY_NAME + "hotlistCards");
			String returnPath = null;			
			Long partnerId = new Long(0);
			String userName = null;
			boolean isHotlisted = false;
			boolean isSuccess = false;
			BcwtConfigParamsDTO configParamDTO = null;
			ListCardDTO listCardDTO = null;
			returnPath = "hotlistCards";
			String nextfareCompId = null;
			BcwtPatronDTO bcwtPatronDTO = null;
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				HttpSession session = request.getSession(true);
				if (null != session.getAttribute(Constants.USER_INFO)) {
					bcwtPatronDTO = (BcwtPatronDTO) session
							.getAttribute(Constants.USER_INFO);
					userName = bcwtPatronDTO.getFirstname() + " "
							+ bcwtPatronDTO.getLastname();
				}
				String memberId = request.getParameter("memberId");
				if(userName !=null && !Util.isBlankOrNull(userName)){
					listCardForm.setAdminName(userName);
				}
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}			
				
				if(null !=request.getParameter("nextfareCompId") &&
						!Util.isBlankOrNull(request.getParameter("nextfareCompId").toString())){
					nextfareCompId = request.getParameter("nextfareCompId").toString();					
				}
				if(null != session.getAttribute("COMPANYID")&&
						!Util.isBlankOrNull(session.getAttribute("COMPANYID").toString())){
					listCardForm.setCompanyId(session.getAttribute("COMPANYID").toString());					
				}
				if(null != session.getAttribute("COMPANYNAME")&&
						!Util.isBlankOrNull(session.getAttribute("COMPANYNAME").toString())){
					listCardForm.setCompanyName(session.getAttribute("COMPANYNAME").toString());
					
				}				
				listCardForm.setNextfareCompanyId(nextfareCompId);
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					listCardDTO = (ListCardDTO) iter.next();
					listCardDTO.setCompanyId(listCardForm.getCompanyId());
					listCardDTO.setCompanyName(listCardForm.getCompanyName());
					listCardDTO.setAdminName(listCardForm.getAdminName());
					BeanUtils.copyProperties(listCardForm, listCardDTO);
					request.setAttribute("psListCardsDTO", listCardDTO);
					partnerId = Long.valueOf(listCardDTO.getMemberId());
					break;
				}
				
				isSuccess = listCardsManagement.saveHotlistedCards(listCardDTO, partnerId);
					
				if(isSuccess){
					configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
					if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
						isHotlisted = listCardsManagement.hotlistCards(listCardDTO);
					} else {
						isHotlisted = true;
					}
		
					if(isHotlisted){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.HOTLIST_CARDS_SUCCESS_MESSAGE));
						request.setAttribute(Constants.SUCCESS, "true");
					} else {
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.HOTLIST_CARDS_FAILURE_MESSAGE));
					}
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.HOTLIST_CARDS_SAVE_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_HOTLIST_CARDS);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing hotlist cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}

		/**
		 * Recurring
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showRecurring(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showRecurring: ";
			BcwtsLogger.debug(MY_NAME + "showRecurring");
			String returnPath = null;
			String userName = null;
			returnPath = "showRecurring";
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;				
				HttpSession session = request.getSession(true);				
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();
					request.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_RECURRING);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in recurring page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}

		/**
		 * To add Recurring
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward addRecurring(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "addRecurring: ";
			BcwtsLogger.debug(MY_NAME + "addRecurring");
			String returnPath = null;
			String userName = null;	
			boolean isThresholdAdded = false;
			BcwtConfigParamsDTO configParamDTO = null;
			ListCardDTO listCardDTO = null;
			returnPath = "addRecurring";
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				
				HttpSession session = request.getSession(true);				
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					listCardDTO = (ListCardDTO) iter.next();					
					request.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}
				
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					isThresholdAdded = listCardsManagement.addRecurring(listCardDTO);
				} else {
					isThresholdAdded = true;
				}

				if(isThresholdAdded){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.ADD_RECURRING_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.ADD_RECURRING_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_RECURRING);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in recurring page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Threshold
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showThreshold(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showThreshold: ";
			BcwtsLogger.debug(MY_NAME + "showThreshold");
			String returnPath = null;			
			String userName = null;		
			returnPath = "showThreshold";			
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				HttpSession session = request.getSession(true);
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
					ListCardDTO listCardDTO = (ListCardDTO) iter.next();				
					request.setAttribute("psListCardsDTO", listCardDTO);
					break;
				}			
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_THRESHOLD);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in threshold page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}

		/**
		 * To add Threshold
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward addThreshold(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "addThreshold: ";
			BcwtsLogger.debug(MY_NAME + "addThreshold");
			String returnPath = null;			
			String userName = null;	
			boolean isThresholdAdded = false;
			BcwtConfigParamsDTO configParamDTO = null;
			ListCardDTO listCardDTO = null;
			returnPath = "addThreshold";
			
			try {
				ListCardForm listCardForm = (ListCardForm) form;				
				HttpSession session = request.getSession(true);				
				String memberId = request.getParameter("memberId");
				if(!Util.isBlankOrNull(memberId)){
					listCardForm.setMemberId(memberId);
				}
				List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
				
				for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
						listCardDTO = (ListCardDTO) iter.next();
						listCardDTO.setThresholdLimit(listCardForm.getThresholdLimit());
						listCardDTO.setThresholdCashValue(listCardForm.getThresholdCashValue());
						request.setAttribute("psListCardsDTO", listCardDTO);
						break;
				}
				
				configParamDTO = ConfigurationCache.getConfigurationValues(Constants.IS_MARTA_ENV);
				if(configParamDTO.getParamvalue().equalsIgnoreCase(Constants.YES)){
					isThresholdAdded = listCardsManagement.addThreshold(listCardDTO);
				} else {
					isThresholdAdded = true;
				}

				if(isThresholdAdded){
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.ADD_THRESHOLD_SUCCESS_MESSAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.ADD_THRESHOLD_FAILURE_MESSAGE));
				}
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_THRESHOLD);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in threshold page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Add new card
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showNewCard(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showNewCard: ";
			BcwtsLogger.debug(MY_NAME + "showNewCard");
			List benefitDetails = new ArrayList();			
			String userName = null;		
			String returnPath = "showNewCard";		
			try {
				String nextfareCompanyId = "";
				String companyId = "";
				ListCardForm listCardForm = (ListCardForm) form;				
				HttpSession session = request.getSession(true);				
				
				if(request.getParameter("nextfareComId")!= null){
					nextfareCompanyId = request.getParameter("nextfareComId");
					listCardForm.setNextfareCompanyId(nextfareCompanyId);
				}
				if(request.getParameter("companyId")!=null){
					companyId = request.getParameter("companyId");
					listCardForm.setCompanyId(companyId);
				}
				BcwtsLogger.debug("Company Id"+companyId);
				/*if(Util.isBlankOrNull(nextfareCompanyId)){
					BcwtsLogger.debug("Inside if");
					nextfareCompanyId =  companyId;    
				}*/
				BcwtsLogger.debug("nextfare If"+nextfareCompanyId);
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					List benefitDetailsDTOList = listCardsManagement.getCustomerBenefitDetails(nextfareCompanyId);			
					for (Iterator iter = benefitDetailsDTOList.iterator(); iter.hasNext();) {
						CustomerBenefitDetailsDTO benefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();						
						benefitDetails.add(new LabelValueBean(benefitDetailsDTO.getBenefitName(),
									benefitDetailsDTO.getBenefitId()));					
					}						
				}
				session.setAttribute(Constants.BENEFIT_DETAILS_LIST, benefitDetails);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_NEW_CARD);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in new card page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			form.reset(mapping, request);
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * Add new card
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward showNewCardError(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "showNewCard: ";
			BcwtsLogger.debug(MY_NAME + "showNewCard");
			List benefitDetails = new ArrayList();			
			String userName = null;		
			String returnPath = "showNewCard";
			try {
				ListCardForm listCardForm = (ListCardForm) form;
				String nextfareCompanyId = null;
				String memberId = null;
				String companyId = null;			
				HttpSession session = request.getSession(true);
			
				if(!Util.isBlankOrNull(listCardForm.getMemberId())){
					memberId = listCardForm.getMemberId();
				}
				
				if(request.getParameter("nextfareCompId")!=null){
					nextfareCompanyId = request.getParameter("nextfareCompId");
				}
				
				if(request.getParameter("companyId")!=null){
					companyId = request.getParameter("companyId");
				}
				
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					List benefitDetailsDTOList = listCardsManagement.getCustomerBenefitDetails(nextfareCompanyId);			
					for (Iterator iter = benefitDetailsDTOList.iterator(); iter.hasNext();) {
						CustomerBenefitDetailsDTO benefitDetailsDTO = (CustomerBenefitDetailsDTO) iter.next();						
						benefitDetails.add(new LabelValueBean(benefitDetailsDTO.getBenefitName(),
									benefitDetailsDTO.getBenefitId()));
					}
				}
				
				listCardForm.setMemberId(memberId);
				session.setAttribute(Constants.BENEFIT_DETAILS_LIST, benefitDetails);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_NEW_CARD);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in new card page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
			return mapping.findForward(returnPath);		
		}
		
		
		/**
		 * Add new card
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward addNewCard(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
			final String MY_NAME = ME + "addNewCard: ";
			BcwtsLogger.debug(MY_NAME + "addNewCard");			
			String userName = null;
			boolean isCardAdded = false;
			boolean isMemberIdExist = false;
			boolean isSerialNoExist = false;
			boolean isHotlisted = false; 
			boolean isCardAssociated = false;
			boolean isSerialNoExistInNewCard = false;
			boolean isCorrectRiderClass = false;
			String returnPath = "newCard";
			String companyName = null;
			String nextfareCompanyId = "";
			try {
				ListCardForm listCardForm = (ListCardForm) form;				
				HttpSession session = request.getSession(true);				
				 nextfareCompanyId = request.getParameter("nextfareComId");
			
				
				
				if(!Util.isBlankOrNull(nextfareCompanyId)){
					listCardForm.setNextfareCompanyId(nextfareCompanyId);
				}
				String companyId = request.getParameter("companyId");
				
				if(!Util.isBlankOrNull(companyId)){
					listCardForm.setCompanyId(companyId);
				}
				String tmaId = request.getParameter("tmaId");
				if(!Util.isBlankOrNull(tmaId)){
					listCardForm.setTmaId(tmaId);
				}
				String memberId = listCardForm.getCustomerMemberId();
				String serialNumber = listCardForm.getNewSerialNumber();			
				
				isSerialNoExistInNewCard = listCardsManagement.isCardExistInPartnerNewCardTable(serialNumber);
				
				if(!isSerialNoExistInNewCard) {					
					isSerialNoExist = listCardsManagement.isSerialNoExist(serialNumber);
					isHotlisted = listCardsManagement.isCardHotlisted(serialNumber);
					if(tmaId != null){
						List tmaCompanyDetailsList = listCardsManagement.getCompanyID(listCardForm);
						for (Iterator iter = tmaCompanyDetailsList.iterator();iter.hasNext(); ){
							ListCardsCompanyIdDTO cardDTO = (ListCardsCompanyIdDTO) iter.next();							
							String tmaMemberId = cardDTO.getMemberId();
							companyName = cardDTO.getCompanyName();
							isMemberIdExist = listCardsManagement.isMemberIdExist(tmaMemberId, nextfareCompanyId);
							if(isMemberIdExist){
								break;
							}
							isCardAssociated = listCardsManagement.isCardAssociated(serialNumber, companyId, memberId);
							if(isCardAssociated){
								break;
							}
							isCorrectRiderClass = listCardsManagement.isCorrectRiderClass(serialNumber, companyId);
							
						}
					}else{
						isMemberIdExist = listCardsManagement.isMemberIdExist(memberId, nextfareCompanyId);
						isCardAssociated = listCardsManagement.isCardAssociated(serialNumber, nextfareCompanyId, memberId);
						isCorrectRiderClass = listCardsManagement.isCorrectRiderClass(serialNumber, nextfareCompanyId);
					}					
					if(!isMemberIdExist	&& isSerialNoExist	&& !isHotlisted && !isCardAssociated && isCorrectRiderClass
							&& !Util.isBlankOrNull(listCardForm.getCustomerMemberId())
							&& !Util.isBlankOrNull(listCardForm.getNewSerialNumber())
							&& !Util.isBlankOrNull(listCardForm.getConfirmSerialNumber())
							&& !Util.isBlankOrNull(listCardForm.getFirstName()) 
							&& !Util.isBlankOrNull(listCardForm.getLastName())
							&& !Util.isBlankOrNull(listCardForm.getEmailId())
							&& !Util.isBlankOrNull(listCardForm.getPhoneNumber())
							&& !Util.isBlankOrNull(listCardForm.getBenefitId())){
						ListCardDTO listCardDTO = new ListCardDTO();
						listCardDTO.setCompanyId(companyId);
						listCardDTO.setCompanyName(companyName);
						listCardForm.setCompanyName(companyName);
						BeanUtils.copyProperties(listCardDTO, listCardForm);
						
						if(listCardsManagement.isUpass(companyId)){
							isCardAdded =	listCardsManagement.addUpassNewCard(listCardDTO);
						//	System.out.println("In add new card"+companyId);
							 session.setAttribute("PARTNERNEXTFARECOMPID",companyId);
							 session.setAttribute("PARTNERCOMPANYID",companyId);
						}
						else
						isCardAdded = listCardsManagement.addNewCard(listCardDTO);
					}
					
					if(!isCorrectRiderClass){
						BcwtsLogger.debug("Display "+PropertyReader
								.getValue(Constants.WRONG_RIDER_CLASS));
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.WRONG_RIDER_CLASS));
						returnPath = "showNewCardError";
						
					}
					
					if(isMemberIdExist){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.MEMBER_ID_EXIST));
						returnPath = "showNewCardError";
					}
					
					if(!isMemberIdExist && !isSerialNoExist){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.SERIAL_NO_NOT_EXIST));
						returnPath = "showNewCardError";
					}
					
					if(!isMemberIdExist && isSerialNoExist && isHotlisted) {
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.CARD_HOTLISTED_MESSAGE));
						returnPath = "showNewCardError";
					}
					
					if(!isMemberIdExist && isSerialNoExist && !isHotlisted && isCardAssociated) {
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.CARD_ASSOCIATED_MESSAGE));
						returnPath = "showNewCardError";
					}
								
					if(!isMemberIdExist && isSerialNoExist && !isHotlisted 
							&& !isCardAssociated && isCardAdded && isCorrectRiderClass){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.ADD_NEW_CARD_SUCCESS_MESSAGE));
						request.setAttribute(Constants.SUCCESS, "true");
					} else if(!isMemberIdExist && isSerialNoExist && !isHotlisted 
							&& !isCardAssociated && !isCardAdded && isCorrectRiderClass){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.ADD_NEW_CARD_FAILURE_MESSAGE));
					}
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.CARD_ALREADY_ADDED_UNDER_PROCESS));
					returnPath = "showNewCardError";
				}				
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_NEW_CARD);
				BcwtsLogger.info(MY_NAME + "User Name:" + userName);		
			}catch (Exception e) {			
				BcwtsLogger.error("Error in new card page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}
		
			return mapping.findForward(returnPath);		
		}

		/**
		 * Display new card details .
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return returnPath
		 * @throws MartaException
		 */
		public ActionForward getNewCardDetails(ActionMapping mapping, 
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws MartaException{
			
			final String MY_NAME = ME + "getNewCardDetails: ";
			BcwtsLogger.debug(MY_NAME + "Showing list of new cards in the queue");
			String returnPath = null;			
			ListCardsManagement listCardsManagement = null;			
			List newCardList = null;
			returnPath = "newCardQueueTMA";
			
			try {
				listCardsManagement = new ListCardsManagement();
				HttpSession session = request.getSession(true);				
				newCardList = listCardsManagement.getNewCardDetails();
				session.setAttribute(Constants.NEW_CARD_QUEUE_LIST, newCardList);
				session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_NEW_CARD_QUEUE_LIST);
				if(!("TMA".equals(request.getParameter("fromAction")))){
					returnPath = "newCardQueue";
				}
				else{
					returnPath = "newCardQueueTMA";
				}
				
			}catch (Exception e) {			
				BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
				returnPath = ErrorHandler.handleError(e, "", request, mapping);
			}		
			return mapping.findForward(returnPath);		
		}
		
		/**
		 * adminList
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward adminListCards(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
		final String MY_NAME = ME + "adminListCards: ";
		BcwtsLogger.debug(MY_NAME + "Showing admin list cards page");
		String returnPath = "adminListCard";		
		String userName = null;
		String userType = null;
		ListCardsManagement listCardsManagement = null;
		List partnerDetailsList = null;
		try {
			form.reset(mapping, request);
			ListCardForm listCardForm = new ListCardForm();
			listCardsManagement = new ListCardsManagement();
			HttpSession session = request.getSession(true);				
			if(null != request.getParameter("userType") &&
					!Util.isBlankOrNull(request.getParameter("userType").toString())){
				userType = request.getParameter("userType").toString();
			}
			if(userType !=null){
				if(userType.equalsIgnoreCase(Constants.ACCOUNT_TYPE_PARTNER)){
					partnerDetailsList = listCardsManagement.getPartnerDetails(listCardForm);
					if(partnerDetailsList == null){
						partnerDetailsList =new ArrayList();
					}
					session.setAttribute("PARTNER_DETAILS_LIST", partnerDetailsList);
					session.removeAttribute("TMA_PARTNER_DETAILS_LIST");
					session.removeAttribute("TMA_COMPANY_DETAILS_LIST");
				}
				if(userType.equalsIgnoreCase(Constants.ACCOUNT_TYPE_TMA)){
					partnerDetailsList = listCardsManagement.getTMAPartnerDetails(listCardForm);
					if(partnerDetailsList == null){
						partnerDetailsList =new ArrayList();
					}
					session.setAttribute("TMA_PARTNER_DETAILS_LIST", partnerDetailsList);
					session.removeAttribute("PARTNER_DETAILS_LIST");
					session.removeAttribute("TMA_COMPANY_DETAILS_LIST");
				}
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			if(userType == null){
				session.removeAttribute("PARTNER_DETAILS_LIST");
				session.removeAttribute("TMA_PARTNER_DETAILS_LIST");
				session.removeAttribute("TMA_COMPANY_DETAILS_LIST");
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_LIST_CARDS);
		}catch (Exception e) {			
			BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
			returnPath = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnPath);		
	}
	
		
		/**
		 * getCompanyList
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws MartaException
		 */
		public ActionForward getCompanyList(ActionMapping mapping, 
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MartaException{
		
		final String MY_NAME = ME + "getCompanyList: ";
		BcwtsLogger.debug(MY_NAME + "Showing TMA Company List page");
		String returnPath = "adminListCard";		
		String userName = null;
		String tmaId = null;
		ListCardsManagement listCardsManagement = null;
		List companyDetailsList = null;
		List partnerDetailsList = null;
		
		try {
			form.reset(mapping, request);
			ListCardForm listCardForm = new ListCardForm();
			listCardsManagement = new ListCardsManagement();
			HttpSession session = request.getSession(true);			
			if(null != request.getParameter("tmaId") &&
					!Util.isBlankOrNull(request.getParameter("tmaId").toString())){
				tmaId = request.getParameter("tmaId").toString();
				session.setAttribute("tmaID", tmaId);
			}
			if(tmaId !=null && !Util.isBlankOrNull(tmaId)){
					companyDetailsList = listCardsManagement.getTmaCompanyList(tmaId);
					if(companyDetailsList == null){
						companyDetailsList =new ArrayList();
					}
					partnerDetailsList = listCardsManagement.getTMAPartnerDetails(listCardForm);
					if(partnerDetailsList == null){
						partnerDetailsList =new ArrayList();
					}
					session.removeAttribute("TMA_PARTNER_DETAILS_LIST");
					session.setAttribute("TMA_COMPANY_DETAILS_LIST", companyDetailsList);
					//session.setAttribute("TMA_PARTNER_DETAILS_LIST", partnerDetailsList);
					
					session.removeAttribute("PARTNER_DETAILS_LIST");
			}
			if(null != request.getParameter("tma") &&
					!Util.isBlankOrNull(request.getParameter("tma").toString())){
				if(request.getParameter("tma").toString().equalsIgnoreCase(Constants.ACCOUNT_TYPE_TMA)){
					tmaId = session.getAttribute("tmaID").toString();
				}
			}
			if(tmaId !=null && !Util.isBlankOrNull(tmaId)){
					companyDetailsList = listCardsManagement.getTmaCompanyList(tmaId);
					if(companyDetailsList == null){
						companyDetailsList =new ArrayList();
					}
					partnerDetailsList = listCardsManagement.getTMAPartnerDetails(listCardForm);
					if(partnerDetailsList == null){
						partnerDetailsList =new ArrayList();
					}
					
					session.setAttribute("TMA_COMPANY_DETAILS_LIST", companyDetailsList);
				//	session.setAttribute("TMA_PARTNER_DETAILS_LIST", partnerDetailsList);
					
			}
			BcwtsLogger.info(MY_NAME + "User Name:" + userName);
			if(tmaId == null){
				session.removeAttribute("PARTNER_DETAILS_LIST");
				session.removeAttribute("TMA_PARTNER_DETAILS_LIST");
				session.removeAttribute("TMA_COMPANY_DETAILS_LIST");
			}
			session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_LIST_CARDS);
		}catch (Exception e) {			
			BcwtsLogger.error("Error while showing list cards page : " + e.getMessage());
			returnPath = ErrorHandler.handleError(e, "", request, mapping);
		}		
		return mapping.findForward(returnPath);		
	}	
}
