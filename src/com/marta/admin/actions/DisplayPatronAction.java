package com.marta.admin.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.NegativeListCardsBusiness;
import com.marta.admin.business.PatronManagement;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtPatronDisplayDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.dto.NegativeListCardsDTO;
import com.marta.admin.dto.PatronCountDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.DisplayPatronForm;
import com.marta.admin.forms.NegativeListCardForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.ExcelGenerator;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class DisplayPatronAction extends DispatchAction {

	/*
	 * public ActionForward execute(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) {
	 */

	private static final String ME = "DisplayPatronAction";

	public ActionForward displayPatrons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {
   
		PatronManagement patronManagement = new PatronManagement();
		ArrayList displayList;
		ArrayList countList;
		//PatronCountDTO patronCountDTO;
		
		displayList = patronManagement.getPatronDisplayDetails();
		countList   = patronManagement.getPatronCount();
		try {
			HttpSession session = request.getSession(true);

			session.setAttribute("displayList", displayList);
			session.setAttribute("countList",countList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					" >> Displa Marta Patrons");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (mapping.findForward("success"));
	}

	public ActionForward populateDisplayDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "populateDisplayDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for Display List");
		HttpSession session = request.getSession(true);
		String returnValue = "success";
		DisplayPatronForm displayPatronForm = (DisplayPatronForm) form;
		PatronManagement patronManagement = new PatronManagement();

		String patronId = "";

		// BcwtSearchDTO bcwtSearchDTO = null;

		BcwtPatronDTO bcwtPatronDTO = null;

		try {

			if (!Util.isBlankOrNull(request.getParameter("isPatronId"))) {
				patronId = request.getParameter("isPatronId");

				displayPatronForm.setPatronId(patronId);
			}

			bcwtPatronDTO = patronManagement.getPatronDetailsById(new Long(
					patronId));

			String no = "no";
			String yes = "yes";

			if (!Util.isBlankOrNull(bcwtPatronDTO.getFirstname())) {
				displayPatronForm.setFirstName(bcwtPatronDTO.getFirstname());

			}
			if (!Util.isBlankOrNull(bcwtPatronDTO.getLastname())) {
				displayPatronForm.setLastName(bcwtPatronDTO.getLastname());

			}
			if (!Util.isBlankOrNull(bcwtPatronDTO.getActivestatus())) {
				displayPatronForm.setActiveStatus(bcwtPatronDTO
						.getActivestatus());

			}
			if (!Util.isBlankOrNull(bcwtPatronDTO.getEmailid())) {
				displayPatronForm.setEmailId(bcwtPatronDTO.getEmailid());

			}
			if (!Util.isBlankOrNull(bcwtPatronDTO.getPhonenumber())) {
				displayPatronForm.setPhoneNum(bcwtPatronDTO.getPhonenumber());

			}

			session.setAttribute("DISPLAY_PATRON_DETAILS", bcwtPatronDTO);
			request.setAttribute("displayPatronForm", displayPatronForm);
			session.setAttribute("SHOW_DISPLAY_PATRON_DETAILS",
					"SHOW_DISPLAY_PATRON_DETAILS");

		} catch (MartaException e) {
			BcwtsLogger
					.error(MY_NAME
							+ " Exception while getting information for displaying unlock page:"
							+ Util.getFormattedStackTrace(e));
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Display Marta Patrons");

		return mapping.findForward(returnValue);

	}

	public ActionForward updatePatronActiveStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {

		final String MY_NAME = ME + "updatePatronActiveStatus: ";
		BcwtsLogger.debug(MY_NAME + "to update Patron Active Status");
		HttpSession session = request.getSession(true);
		String returnValue = "success";
		PatronManagement patronManagement = null;

		BcwtPatronDisplayDTO bcwtPatronDisplayDTO = null;

		boolean isUpdate = false;

		DisplayPatronForm displayPatronForm = (DisplayPatronForm) form;

		try {

			patronManagement = new PatronManagement();

			bcwtPatronDisplayDTO = new BcwtPatronDisplayDTO();

			if (!Util.isBlankOrNull(displayPatronForm.getPatronId())) {
				bcwtPatronDisplayDTO.setPatronId(displayPatronForm
						.getPatronId());
			}
			if (!Util.isBlankOrNull(displayPatronForm.getActiveStatus())) {

				bcwtPatronDisplayDTO.setActiveStatus(Integer
						.parseInt(displayPatronForm.getActiveStatus()));

			}

			isUpdate = patronManagement
					.updatePatronActiveStatus(bcwtPatronDisplayDTO);

			if (isUpdate) {
				request.setAttribute(Constants.MESSAGE,
						"Patron Active Status Updated");
				request.setAttribute(Constants.SUCCESS, "true");

			} else {
				request.setAttribute(Constants.MESSAGE,
						"Patron Active Status Update Failed");
				request.setAttribute(Constants.SUCCESS, "false");
			}

			session.removeAttribute("SHOW_DISPLAY_PATRON_DETAILS");

		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception while displaying marta admin page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				" >> Displa Marta Patrons");

		return mapping.findForward(returnValue);
	}

	public ActionForward searchPatrons(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {

		final String MY_NAME = "searchPatrons: ";
		BcwtsLogger
				.debug(MY_NAME + "Populated data for Search Patrons Display");
		HttpSession session = request.getSession(true);

		String returnValue = "success";

		DisplayPatronForm displayPatronForm = (DisplayPatronForm) form;
		PatronManagement patronManagement = new PatronManagement();
		ArrayList displayList;
	//	session.removeAttribute("displayList");

		try {

			request.setAttribute("displayPatronForm", displayPatronForm);
			
			displayList = patronManagement
					.getSearchPatronDetails(displayPatronForm);
			
			session.setAttribute("displayList", displayList);
			session.removeAttribute("SHOW_DISPLAY_PATRON_DETAILS");
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					" >> Display Marta Patrons");
			

		} catch (Exception e) {

			BcwtsLogger
					.error(MY_NAME
							+ " Exception while getting information for displaying Patron Search:"
							+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}

		return mapping.findForward(returnValue);
	}
	
	public ActionForward getSpreadSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws MartaException {
   
		PatronManagement patronManagement = new PatronManagement();
		ArrayList displayList;
		ArrayList countList;
		List fieldList = null;
		//PatronCountDTO patronCountDTO;
		
		displayList = patronManagement.getPatronDisplayDetails();
		countList   = patronManagement.getPatronCount();
		fieldList = new ArrayList();
		fieldList.add("Patron Id");
		fieldList.add("First Name");
		fieldList.add("Last Name");
		fieldList.add("last Login");
		fieldList.add("Account Creation Date");
			
		try {
			
			HttpSession session = request.getSession(true);

			session.setAttribute("displayList", displayList);
			session.setAttribute("countList",countList);
			session.setAttribute(Constants.BREAD_CRUMB_NAME,
					" >> Displa Marta Patrons");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = ExcelGenerator.generateDisplayPatronExcel(
					"Patron List", fieldList, displayList,request);
			responseGenerate("application/xls",
					"Patron List" + ".xls", response, baos);
			
			
		} catch (Exception e) {
			BcwtsLogger.error(Util.getFormattedStackTrace(e));
		}

		return (mapping.findForward("success"));
	}
	
	private void responseGenerate(String contentype,String filename,HttpServletResponse response,ByteArrayOutputStream baos )
	{
				final String MY_NAME =  "ResponseGenerate: ";
				BcwtsLogger.info(MY_NAME + "entering into ResponseGenerate method");
		     try{
			    response.setBufferSize(baos.size());		
			    byte requestBytes[] = baos.toByteArray();
			    ByteArrayInputStream bis = new ByteArrayInputStream(requestBytes);
			    response.reset();
			    response.setContentType(contentype);
			    response.setHeader("Content-disposition","attachment; filename="+filename);
			    byte[] buf = new byte[baos.size()];
			    int len;
			    while ((len = bis.read(buf)) > 0){
			     response.getOutputStream().write(buf, 0,buf.length);
			    }
			    bis.close();
			    response.getOutputStream().flush(); 
			    BcwtsLogger.info(MY_NAME + "response generated");
			}catch(Exception e){
				 BcwtsLogger.error(Util.getFormattedStackTrace(e));
		    }	
	}
	

}
