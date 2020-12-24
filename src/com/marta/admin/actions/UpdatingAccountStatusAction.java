package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;

public class UpdatingAccountStatusAction extends DispatchAction {
	
final String ME = "UpdatingAccountStatusAction: ";
	
	public ActionForward displayUpdatingAccountStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		
		final String MY_NAME = ME + "displayUpdatingAccountStatus: ";
		BcwtsLogger.debug(MY_NAME
				+ "Populated data for partnerss");
		HttpSession session = request.getSession(true);
		String returnValue = "updatingAccountStatus";
		
		try {
			
			List accountSearchList = new ArrayList();
			session.setAttribute(Constants.SEARCH_LIST_ADMIN, accountSearchList);
			returnValue = "updatingAccountStatus";
			
		} catch (Exception e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting information for displaying unlock page:"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}

}
