package com.marta.admin.actions;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;

/**
 * 
 * This class is used for logout
 *
 */
public class LogoutAction extends DispatchAction {
	
	final String ME = "LogoutAction: ";
	BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
	String userName = "";
	/**
	 * Method to logout the users
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userLogout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final String MY_NAME = ME + "userLogout: ";
		BcwtsLogger.info(MY_NAME + "entering into logout action");
		HttpSession httpSession = request.getSession(true);
		String returnValue = "";
		
		
		try {
			if (null != httpSession.getAttribute(Constants.USER_INFO)) {
				bcwtPatronDTO = (BcwtPatronDTO) httpSession
									.getAttribute(Constants.USER_INFO);
							userName = bcwtPatronDTO.getFirstname() + " "
									+ bcwtPatronDTO.getLastname();
						}
			BcwtsLogger.info(ME + "User Name:" + userName);
			String currsessionId = httpSession.getId();
			BcwtPatronDTO bcwtPatronDTO = (BcwtPatronDTO)httpSession.getAttribute(Constants.USER_INFO);
			if (null != bcwtPatronDTO) 
			{
				ServletContext context  = httpSession.getServletContext(); 
				Map usersMap = (Map) context.getAttribute(Constants.MARTACONTEXTMAP);
				
				String userSessionId = (String) usersMap.get(bcwtPatronDTO.getPatronid());
				if (userSessionId!=null && currsessionId!=null && currsessionId.equals(userSessionId)) {
					usersMap.remove(bcwtPatronDTO.getPatronid());
				}
				httpSession.invalidate();
				
			}
			BcwtsLogger.info(ME + "User Name:" + userName);
			returnValue = "logout";
		} catch (Exception e) {
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);
	}
	
	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("userLogout");
	}
	
}
