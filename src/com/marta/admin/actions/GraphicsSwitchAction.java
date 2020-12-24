/**
 * 
 */
package com.marta.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 * @author Administrator
 *
 */
public class GraphicsSwitchAction extends DispatchAction{
	
	
	/**
	 * This is to switch over application to without graphics
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward graphicsSwitchAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = null;
		session = request.getSession(true);
		String currentPath = "";		
		try {
			String cssModify = "";
			if(null != request.getParameter("cssModify")){
				cssModify =  request.getParameter("cssModify");
			}
			session.setAttribute("cssModify", cssModify);
			if(null != session.getAttribute("currentPath")){
				currentPath = (String)session.getAttribute("currentPath"); 			
			}				
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward(currentPath);
	}
	
	/**
	 * testing modified by krg
	 * very latest --- committed part
	 */
	

}
