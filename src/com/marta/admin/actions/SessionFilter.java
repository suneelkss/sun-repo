package com.marta.admin.actions;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

public class SessionFilter implements Filter {
	/**
	 * Method to implement session filteration in marta appalication
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession(true);	
		String contexPath = request.getContextPath();
		
		String path = request.getServletPath();
		if (path != null && path.trim().toLowerCase().endsWith(".do")) {
			String newPath = path.substring(0, path.indexOf(".do"));
			newPath = Util.getActionDetails(newPath);
			path = newPath + ".do";
		}
		if (validateURL(path)) {
			if (!path.equals("/forgotPassword.do") && !path.equals("/login.do")
					&& !path.equals("/signup.do")
					&& !path.equals("/patronMangementAddAction.do")
					&& !path.equals("/checkForgotPassword.do")
					&& !path.equals("/multiusercontrol.do")
					&& !path.equals("/loginInitial.do")
					) {
					if (null != session.getAttribute(Constants.USER_INFO)) {
						
						ServletContext context = session.getServletContext();	
						Long patronId = new Long(0);
						String currentSessionId = null;
						try{
							if(null!=session &&  null!=session.getAttribute(Constants.USER_INFO)){
								patronId = ((BcwtPatronDTO) session.getAttribute(Constants.USER_INFO)).getPatronid();
							}
							currentSessionId = session.getId();
							
							Map usersMap = (Map) context.getAttribute(Constants.MARTACONTEXTMAP);
							
							if (usersMap!=null && !usersMap.isEmpty()) {
								String sessionIdInMap = (String) usersMap.get(patronId);
								
								if (sessionIdInMap!=null && currentSessionId!=null && !currentSessionId.equals(sessionIdInMap)) {
									// forward to login page
									request.setAttribute(Constants.MESSAGE, PropertyReader
											.getValue(Constants.SESSION_EXPIRED));
									response.sendRedirect(contexPath+"/loginInitial.do");
									return;
								} else {
									arg2.doFilter(request, response);
									return;
								}
							}
						} catch (Exception ex) {
							request.setAttribute(Constants.MESSAGE, PropertyReader
									.getValue(Constants.SESSION_EXPIRED));
							response.sendRedirect(contexPath+"/loginInitial.do");
							return;
						}
					}
					else{
						response.sendRedirect(contexPath+"/loginInitial.do");
						return;
					}
				//
				arg2.doFilter(request, response);
				return;				
			} else {
				arg2.doFilter(request, response);
				return;
			}
		} else {
			arg2.doFilter(request, response);
			return;
		}

	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void destroy() {
	}

	public boolean validateURL(String url) {
		if (url.endsWith(".do")) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
