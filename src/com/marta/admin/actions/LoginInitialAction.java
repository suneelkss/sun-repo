package com.marta.admin.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class LoginInitialAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String fromWhere = "";
		String returnValue = "gbsLoginPage";
		
		try{
			
			if(null != request.getParameter("fromWhere")){
				fromWhere = request.getParameter("fromWhere");
			}
			
			if(!Util.isBlankOrNull(fromWhere) 
					&& fromWhere.equals(Constants.GBS)){
				returnValue = "gbsLoginPage";
			}
			
		}catch(Exception e){
			
		}
		
		return mapping.findForward(returnValue);
	}
	
	

}
