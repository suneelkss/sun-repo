package com.marta.admin.business;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.actions.DispatchAction;

//import sun.security.krb5.internal.rcache.ba;

import com.marta.admin.dao.LogDAO;
import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.hibernate.BcwtLogCall;
import com.marta.admin.hibernate.BcwtPatronType;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.PropertyReader;




	
public class LogManagement extends DispatchAction {
	final String ME = "LogManagement: ";
	public List getLogInformation(String fromDate,String patronId,String userType) throws MartaException {
		final String MY_NAME = ME + " getLogInformation: ";
		BcwtsLogger.debug(MY_NAME + " getting log information ");
		List logDetails =null;
		
		try {
            LogDAO logDAO=new LogDAO();
            logDetails= new ArrayList();
            logDetails=logDAO.getLogInformation(fromDate,patronId,userType);
			BcwtsLogger.info(MY_NAME + " got log List" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting log list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_FIND_LOG_DETAILS));
		}
		return logDetails;
	}
	
	/**
	 * Get log call details for IS,GBS and PS
	 * @param userType
	 * @param patronType
	 * @return
	 * @throws MartaException
	 */
	
	public List getCallLogDetails(String userType, String patronType) throws MartaException {
		final String MY_NAME = ME + " getCallLogDetails: ";
		BcwtsLogger.debug(MY_NAME + "get CallLogDetails");
		List partnerDetails = new ArrayList();
		
		try {
            LogDAO logDAO=new LogDAO();
            partnerDetails=logDAO.getCallLogDetails(userType, patronType);
			BcwtsLogger.info(MY_NAME + " gotCallLogDetails" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting CallLogDetails :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_FIND_PARTNER_DETAILS));
		}
		return partnerDetails;
	}
	
	public List getPartnerCallLogDetails(String userType,String patronType) throws MartaException {
		final String MY_NAME = ME + " getPartnerCallLogDetails: ";
		BcwtsLogger.debug(MY_NAME + "get PartnerCallLogDetails");
		List partnerDetails = new ArrayList();
		
		try {
            LogDAO logDAO=new LogDAO();
            partnerDetails=logDAO.getPartnerCallLogDetails(userType,patronType);
			BcwtsLogger.info(MY_NAME + " gotPartnerCallLogDetails" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting CallLogDetails :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_FIND_PARTNER_DETAILS));
		}
		return partnerDetails;
	}
	
	public List populateLogViewDetails(String patronId,
			String patronTypeId,String emailId,String userType) throws MartaException {
		final String MY_NAME = ME + " populateViewUsersDetails: ";
		BcwtsLogger.debug(MY_NAME + " to populate view user details ");
		//List lockedUserAccounntList = new ArrayList();
		List viewList=null;
		try {
			viewList=new ArrayList();
			LogDAO logDAO = new LogDAO();
			viewList = logDAO.populateLogViewDetails(patronId,patronTypeId,emailId,userType);
			BcwtsLogger.info(MY_NAME + " got OrderList");
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_FIND));
		}
		return viewList;
	}

	public List populatePartnerViewDetails(String partnerId,String patronTypeId,String emailId) throws MartaException {
		final String MY_NAME = ME + " populatePartnerViewDetails: ";
		BcwtsLogger.debug(MY_NAME + " to populate partner view user details ");
		//List lockedUserAccounntList = new ArrayList();
		List viewList=null;
		try {
			viewList=new ArrayList();
			LogDAO logDAO = new LogDAO();
			viewList = logDAO.populatePartnerViewDetails(partnerId,patronTypeId,emailId);
			BcwtsLogger.info(MY_NAME + " got OrderList");
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ORDER_FIND));
		}
		return viewList;
	}
	
	public boolean addLogPatron(BcwtLogDTO bcwtLogDTO)
	throws MartaException, IllegalArgumentException {
		final String MY_NAME = ME + " addLogPatron: ";
		BcwtsLogger.debug(MY_NAME + " add log patron details ");
		boolean isAdded = false;
		LogDAO logDAO = new LogDAO();
		BcwtLogCall bcwtLogCall = new BcwtLogCall();
		BcwtPatronType bcwtPatronType = new BcwtPatronType();
		//Date currentTime = null;
		//SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		try {
			
			isAdded = logDAO.addLogPatron(bcwtLogDTO);
			BcwtsLogger.info(MY_NAME + " patron details added?:" + isAdded);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding patron details: "
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return isAdded;
}

	public BcwtLogDTO populateViewLogUserDetails(String logcallId) throws MartaException {
		final String MY_NAME = ME + " populateViewLogUserDetails: ";
		BcwtsLogger.debug(MY_NAME + " to populate view user details ");
		//List lockedUserAccounntList = new ArrayList();
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO(); 
		try {
			
			LogDAO logDAO = new LogDAO();
			bcwtLogDTO = logDAO.populateViewLogUserDetails(logcallId);
			BcwtsLogger.info(MY_NAME + " got ViewList");
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in view display admin email list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_DETAILS));
		}
		return bcwtLogDTO;
	}

	public List displayRecentLogList(String patronId,String patronTypeId,String emailId) throws MartaException {
		final String MY_NAME = ME + " displayRecentLogList: ";
		BcwtsLogger.debug(MY_NAME + " displayRecentLogList");
		LogDAO logDAO = null;
		List recentList =null;
		try {
			logDAO = new LogDAO();
			recentList = logDAO.displayRecentLogList(patronId,patronTypeId,emailId);
			BcwtsLogger.info(MY_NAME + "displayRecentLogList" );
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in display recentLogList :"
					+ e.getMessage());
			
		} 
		return recentList;
	}
	public boolean getaddNewCall(BcwtLogDTO bcwtLogDTO)
	throws MartaException, IllegalArgumentException {
		final String MY_NAME = ME + " addLogPatron: ";
		BcwtsLogger.debug(MY_NAME + " add log patron details ");
		boolean isAdded = false;
		LogDAO logDAO = new LogDAO();
		BcwtLogCall bcwtLogCall = new BcwtLogCall();
		BcwtPatronType bcwtPatronType = new BcwtPatronType();
		//Date currentTime = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		try {
			
			isAdded = logDAO.getaddNewCall(bcwtLogDTO);
			BcwtsLogger.info(MY_NAME + " patron details added?:" + isAdded);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in adding patron details: "
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return isAdded;
	}
	
	
	/**
	 * Helps to generate document
	 * @param uploadedFileName
	 * @param savedFileName
	 * @return
	 */
	public StringBuffer generateDocument(String savedFileName,
			String fileLocation)throws MartaException{
		
		final String MY_NAME = ME + " generateDocument: ";
		BcwtsLogger.debug(MY_NAME);
		StringBuffer contents = new StringBuffer();
        try 
        {
        	FileInputStream fin = new FileInputStream(fileLocation +File.separator+ savedFileName);
        	
            BufferedInputStream bis = new BufferedInputStream(fin);
            while (bis.available() > 0) 
            {
            	contents.append((char)bis.read());            	
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
		return contents;
	}
}