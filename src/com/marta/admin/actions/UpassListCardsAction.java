package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.ListCardsManagement;
import com.marta.admin.dto.BenefitDetailsDTO;
import com.marta.admin.dto.CustomerBenefitDetailsDTO;
import com.marta.admin.dto.ListCardDTO;
import com.marta.admin.dto.MemberBenefitDetailsDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ListCardForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.Util;


public class UpassListCardsAction extends DispatchAction{
	
	final String ME = "UpassListCardsAction: ";
	ListCardsManagement listCardsManagement = new ListCardsManagement();
	
	public ActionForward upassSchoolList(ActionMapping mapping, 
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException{
	
	final String MY_NAME = ME + "upassSchoolList: ";
	BcwtsLogger.debug(MY_NAME + "Showing school list");
	String returnPath = "upassSchoolList";		
	String userName = null;
	
	ListCardsManagement listCardsManagement = null;
	List schoolList = null;
	try {
	
		listCardsManagement = new ListCardsManagement();
		HttpSession session = request.getSession(true);				
			schoolList = listCardsManagement.getSchoolDetails();
		session.setAttribute("schoolList", schoolList);
		
		BcwtsLogger.info(MY_NAME + "User Name:" + userName);
		
		session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_SCHOOLLIST_CARDS);
	}catch (Exception e) {			
		BcwtsLogger.error("Error while showing school list  page : " + Util.getFormattedStackTrace(e));
		returnPath = ErrorHandler.handleError(e, "", request, mapping);
	}		
	return mapping.findForward(returnPath);		
}
	
	public ActionForward listCards(ActionMapping mapping, 
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException{
	
	final String MY_NAME = ME + "listCards: ";
	BcwtsLogger.debug(MY_NAME + "Showing list cards page");
	String returnPath = null;
	
	String partnerId = null;
	String userName = null;
	List memberBenefitListByMemberId = new ArrayList();
	returnPath = "listCards";
	
	try {
		form.reset(mapping, request);
		ListCardForm listCardForm = new ListCardForm();
		
		HttpSession session = request.getSession(true);
		if(null != request.getParameter("nextfareCompId") &&
				!Util.isBlankOrNull(request.getParameter("nextfareCompId"))){
			listCardForm.setNextfareCompanyId(request.getParameter("nextfareCompId").toString());
			listCardForm.setCompanyId(request.getParameter("nextfareCompId").toString());
			//added for pagination in list card jsp
		
			session.setAttribute("PARTNERNEXTFARECOMPID", request.getParameter("nextfareCompId"));
			session.setAttribute("PARTNERCOMPANYID", request.getParameter("nextfareCompId"));			
			
		}		
		
		
		
		List cardDetailsList = listCardsManagement.getCardDetails(listCardForm);
		
		List customerBenefitList = listCardsManagement.
				getCustomerBenefitDetails(listCardForm.getNextfareCompanyId());
	//	System.out.println("com: "+psListCardsForm.getCompanyId());
		List memberBenefitList = listCardsManagement.
				getMemberBenefitDetailsByCompanyId(listCardForm.getNextfareCompanyId());
		
		
		for (Iterator iter = cardDetailsList.iterator(); iter.hasNext();) {
			ListCardDTO psListCardsDTO = (ListCardDTO) iter.next();
			
			listCardForm.setMemberId(psListCardsDTO.getMemberId());
			
			for (Iterator iterator = memberBenefitList.iterator(); iterator.hasNext();) {
				MemberBenefitDetailsDTO memberBenefitDetailsDTO = (MemberBenefitDetailsDTO) iterator.next();
				if(memberBenefitDetailsDTO.getMemberId().equalsIgnoreCase(listCardForm.getMemberId())) {
					memberBenefitListByMemberId.add(memberBenefitDetailsDTO);
				}
			}
			
			List benefitDetails = getBenefitDetails(customerBenefitList, memberBenefitListByMemberId, listCardForm);
               
			for (Iterator benefitIter = benefitDetails.iterator(); benefitIter.hasNext();) {
				BenefitDetailsDTO benefitDetailsDTO = (BenefitDetailsDTO) benefitIter.next();
				if(psListCardsDTO.getMemberId().equalsIgnoreCase(benefitDetailsDTO.getMemberId())){
					psListCardsDTO.getBenefitDetails().append(benefitDetailsDTO.getBenefitDetails()).append("; ");
				}
			}
		}
		
		session.setAttribute(Constants.CARD_DETAILS_LIST, cardDetailsList);
		session.setAttribute(Constants.BREAD_CRUMB_NAME, Constants.BREADCRUMB_LIST_CARDS);
	}catch (Exception e) {			
		BcwtsLogger.error("Error while showing list cards page  " + Util.getFormattedStackTrace(e));
		returnPath = ErrorHandler.handleError(e, "", request, mapping);
	}		
	return mapping.findForward(returnPath);		
}	

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
	

}
