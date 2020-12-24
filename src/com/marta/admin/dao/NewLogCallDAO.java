package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.hibernate.BcwtLogCall;
import com.marta.admin.hibernate.BcwtPatronType;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

/**
 * DAO class for new log call
 * @author Raj
 *
 */
public class NewLogCallDAO extends MartaBaseDAO{
	
	final String ME = "NewLogCallDAO";
	
	/**
	 * Method to get caller details
	 * @param calleerUserName
	 * @param callerUserType
	 * @return
	 * @throws Exception
	 */
	public BcwtLogDTO getCallerDetails(String calleerUserName,String callerUserType) 
		throws Exception {
		
		final String MY_NAME = ME + "getCallerDetails: ";
		BcwtsLogger.debug(MY_NAME);		
		Session session = null;
		Transaction transaction = null;
		BcwtLogDTO resultBcwtLogDTO = null;		
		try {
			List bcwtPatronList = new ArrayList();
			resultBcwtLogDTO = new BcwtLogDTO();		
			if(!Util.isBlankOrNull(callerUserType) && 
					Util.equalsIgnoreCase(callerUserType, Constants.GBS_SUPER_ADMIN)){					
				session = getSession();
				transaction = session.beginTransaction();					
				String query1 = "select bcwtpatron.patronid,bcwtpatron.bcwtpatrontype.patrontypeid " +
						"from BcwtPatron bcwtpatron where bcwtpatron.emailid = '"+ calleerUserName +"' and " +
						"(bcwtpatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_SUPER_ADMIN+"' or " +
						"bcwtpatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_ADMIN+"' or " +
						"bcwtpatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_READONLY+"')  "; 
				
				bcwtPatronList = session.createQuery(query1).list();
				if(bcwtPatronList!=null){
					for (Iterator iter = bcwtPatronList.iterator(); iter.hasNext();) {
						Object[] element = (Object[]) iter.next();
						if(element[0]!=null){
							resultBcwtLogDTO.setPatronId(element[0].toString());							
						}
						if(element[1]!=null){
							resultBcwtLogDTO.setPatronTypeId(element[1].toString());
						}
						resultBcwtLogDTO.setUserType(Constants.GBS_SUPER_ADMIN);
					}
					
					
				}
				transaction.commit();
				session.flush();
				session.close();
			}
			
			if(!Util.isBlankOrNull(callerUserType) &&  
					Util.equalsIgnoreCase(callerUserType, Constants.IS)){					
				session = getSession();
				transaction = session.beginTransaction();					
				String query1 = "select bcwtpatron.patronid,bcwtpatron.bcwtpatrontype.patrontypeid " +
						"from BcwtPatron bcwtpatron where bcwtpatron.emailid = '"+ calleerUserName +"' " +
						"and bcwtpatron.bcwtpatrontype.patrontypeid = '"+Constants.IS +"' " ;
						
				
				bcwtPatronList = session.createQuery(query1).list();
				if(bcwtPatronList!=null){
					for (Iterator iter = bcwtPatronList.iterator(); iter.hasNext();) {
						Object[] element = (Object[]) iter.next();
						if(element[0]!=null){
							resultBcwtLogDTO.setPatronId(element[0].toString());
							
						}
						if(element[1]!=null){
							resultBcwtLogDTO.setPatronTypeId(element[1].toString());
						}
						resultBcwtLogDTO.setUserType(Constants.IS);
					}	
				}
				transaction.commit();
				session.flush();
				session.close();
			}			
			
			if(!Util.isBlankOrNull(callerUserType) &&  
					Util.equalsIgnoreCase(callerUserType, Constants.SUPER_ADMIN)){					
				session = getSession();
				transaction = session.beginTransaction();					
				
				String query1 = "select pad.partnerId,pad.partnerAdminRole.roleid from PartnerAdminInfo pai," +
						"PartnerAdminDetails pad where pai.userName = '"+calleerUserName+"' and " +
								"pai.partnerAdminDetails.partnerId = pad.partnerId"; 
						
				
				bcwtPatronList = session.createQuery(query1).list();
				if(bcwtPatronList!=null){
					for (Iterator iter = bcwtPatronList.iterator(); iter.hasNext();) {
						Object[] element = (Object[]) iter.next();
						if(element[0]!=null){
							resultBcwtLogDTO.setPatronId(element[0].toString());							
						}
						if(element[1]!=null){
							resultBcwtLogDTO.setPatronTypeId(element[1].toString());
						}
						resultBcwtLogDTO.setUserType(Constants.SUPER_ADMIN);
					}	
				}
				transaction.commit();
				session.flush();
				session.close();
			}
			
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return resultBcwtLogDTO;
	}
	
	
	/**
	 * New call is logged
	 * @param bcwtLogDTO
	 * @return
	 * @throws Exception
	 */
	public boolean addLogCall(BcwtLogDTO bcwtLogDTO) throws Exception {
		
		final String MY_NAME = ME + "addLogCall: ";
		BcwtsLogger.debug(MY_NAME);		
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		BcwtLogCall bcwtLogCall = null;
		
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			bcwtLogCall = new BcwtLogCall();
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorEmail())){
				bcwtLogCall.setAdminemailid(bcwtLogDTO.getAdministratorEmail());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorPhoneNumber())){
				bcwtLogCall.setAdminphonenumber(bcwtLogDTO.getAdministratorPhoneNumber());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorUserName())){
				bcwtLogCall.setAdminname(bcwtLogDTO.getAdministratorUserName());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdminPatronId())){
				bcwtLogCall.setAdminpatronid(Long.valueOf(bcwtLogDTO.getAdminPatronId()));
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdminPatronTypeId())){
				bcwtLogCall.setAdminpatrontypeid(Long.valueOf(bcwtLogDTO.getAdminPatronTypeId()));
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorUserName())){
				bcwtLogCall.setAdminname(bcwtLogDTO.getAdministratorUserName());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getNote())){
				bcwtLogCall.setNotes(bcwtLogDTO.getNote());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getPatronId())){
				bcwtLogCall.setPatronid(Long.valueOf(bcwtLogDTO.getPatronId()));
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getUserType())){
				BcwtPatronType bcwtPatronType = new BcwtPatronType();
				bcwtPatronType.setPatrontypeid(Long.valueOf(bcwtLogDTO.getUserType()));
				bcwtLogCall.setBcwtpatrontype(bcwtPatronType);
			}			
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getReasonForCall())){
				bcwtLogCall.setReason(bcwtLogDTO.getReasonForCall());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getNote())){
				bcwtLogCall.setNotes(bcwtLogDTO.getNote());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getSavedFileName())){
				bcwtLogCall.setSavedfilename(bcwtLogDTO.getSavedFileName());
			}
			
			if(!Util.isBlankOrNull(bcwtLogDTO.getUploadedFileName())){
				bcwtLogCall.setUploadedfilename(bcwtLogDTO.getUploadedFileName());
			}
			
			bcwtLogCall.setCalldate(Util.convertStringToDate(Util.getCurrentDateAndTime()));
			bcwtLogCall.setCalltime(Util.convertStringToDate(Util.getCurrentDateAndTime()));
			session.save(bcwtLogCall);
			isAdded = true;
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isAdded;
	}
	
	
}