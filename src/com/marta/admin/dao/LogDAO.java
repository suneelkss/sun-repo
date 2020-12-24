package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtAlertDTO;
import com.marta.admin.dto.BcwtLogDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtUnlockAccountDTO;
import com.marta.admin.forms.LoggingCallsForm;
import com.marta.admin.hibernate.BcwtLogCall;
import com.marta.admin.hibernate.BcwtManualAlert;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtPatronType;
import com.marta.admin.hibernate.BcwtPwdVerifiy;
import com.marta.admin.hibernate.BcwtSecretQuestions;
import com.marta.admin.hibernate.PartnerAdmin;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class LogDAO extends MartaBaseDAO{
	final String ME = "LogDAO: ";
	
	public List getLogInformation(String fromDate,String patronId,String userType)throws Exception{
		final String MY_NAME = ME + "getLogDetails: ";
		BcwtsLogger.debug(MY_NAME + "getting log details started");
		Session session = null;
		List logList = null;
		Transaction transaction = null;
		BcwtLogDTO logDTO = null;
		//BcwtPatronDTO patron=null;
		String query=null;
		try {

			session = getSession();
			transaction = session.beginTransaction();
			
			 query ="select bcwtlogcall.adminname,bcwtlogcall.calldate," +
			"bcwtpatron.firstname,bcwtpatron.emailid from BcwtLogCall bcwtlogcall," +
			"BcwtPatron bcwtpatron where bcwtlogcall.logcallid = (select MAX(bcwtlogcall.logcallid)" +
			" from BcwtLogCall bcwtlogcall)and bcwtlogcall.patronid=bcwtpatron.patronid" ;

			List logListToIterate = session.createQuery(query).list();
			logList = new ArrayList();

			for (Iterator iter = logListToIterate.iterator(); iter.hasNext();) {
				Object[] bcwtLogs = (Object[]) iter.next();

				logDTO = new BcwtLogDTO();
								
				if (bcwtLogs != null) {
										
					if (null != bcwtLogs[0]) {
						logDTO.setAdministratorUserName(bcwtLogs[0]
								.toString());
					}
					if (null != bcwtLogs[1]) {
						logDTO.setDateOfCall(Util.getFormattedDate(bcwtLogs[1]));						
					}
					if (null != bcwtLogs[2]) {
						logDTO.setFirstname(bcwtLogs[2]
								.toString());
					}
					if (null != bcwtLogs[3]) {
						logDTO.setEmailid(bcwtLogs[3]
								.toString());
					}
										
					logList.add(logDTO);
					}
			}

			session.flush();
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			} finally {
				closeSession(session, transaction);
				BcwtsLogger.debug(MY_NAME + " Resources closed");
			}
			return logList;
		}	
	
	
	public List getPatronTypeList() throws Exception {
		List patronTypeList = new ArrayList();
		patronTypeList.add(Constants.MARTA_SUPER_ADMIN);
		patronTypeList.add(Constants.MARTA_ADMIN);
		patronTypeList.add(Constants.MARTA_READONLY);
		patronTypeList.add(Constants.IS);
		patronTypeList.add(Constants.GBS_SUPER_ADMIN);
		//patronTypeList.add("10");
		
		//String query="select bcwtlogcall.adminemailid from BcwtLogCall bcwtlogcall," +
				  //   "BcwtPatronType bcwtpatrontype where bcwtlogcall.bcwtpatrontype.patrontypeid= "; 
				
		
		return patronTypeList;
	}
	
	
	public List getBcwtPatronDetails(String patronType) throws Exception {		
		final String MY_NAME = ME + "getBcwtPatronDetails: ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;		
		Session session = null;		
		List bcwtPatronDetailsList = new ArrayList();
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			String query = "select" +
								" bcwtLogCall.patronid, bcwtPatron.emailid" +
							" from BcwtLogCall bcwtLogCall, BcwtPatron bcwtPatron" +
								" where bcwtLogCall.bcwtpatrontype.patrontypeid = " + patronType +
								" and bcwtPatron.patronid = bcwtLogCall.patronid";
			
			List queryList =  session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {					
					Object[] object = (Object[]) iter.next();
					BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
					if(object[0] != null){
						bcwtPatronDTO.setPatronid(Long.valueOf(object[0].toString()));
					}
					if(object[1] != null){
						bcwtPatronDTO.setEmailid(object[1].toString());
					}
					
					bcwtPatronDetailsList.add(bcwtPatronDTO);
				}
			}			
			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got BcwtPatronDetails");			
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		
		return bcwtPatronDetailsList;
		
	}
	
	/**
	 * 
	 * @param emailId
	 * @param patronId
	 * @param patronType
	 * @param userType
	 * @return
	 * @throws Exception
	 */
	public BcwtLogDTO getRecentCall(String emailId, String patronId, 
			String patronType,String userType) throws Exception {
		final String MY_NAME = ME + "getRecentCall: ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;		
		Session session = null;		
		BcwtLogDTO bcwtLogDTO = null;
		String query = "";
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			if(Util.equalsIgnoreCase(userType, Constants.IS)){
			
				query = "select" +
						" bcwtlogcall.adminname, bcwtlogcall.calldate," +
						"  bcwtPatron.emailid, bcwtlogcall.logcallid," +
						" bcwtPatron.firstname, bcwtlogcall.bcwtpatrontype.patrontypeid," +
						" bcwtlogcall.patronid, bcwtlogcall.adminpatrontypeid, bcwtlogcall.adminpatronid" +
						" from" +
						" BcwtLogCall bcwtlogcall, BcwtPatron bcwtPatron " +
						" where" +
						" bcwtlogcall.logcallid = " +
						" (select MAX(bcwtlogcall.logcallid) " +
						" from BcwtLogCall bcwtlogcall " +
						" where bcwtlogcall.patronid = " + patronId + 
						" and bcwtlogcall.bcwtpatrontype.patrontypeid = " + patronType + ")" + 
						" and bcwtPatron.patronid = bcwtlogcall.patronid and bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.IS +"'";
							
			}
			
			if(Util.equalsIgnoreCase(userType, Constants.GBS_SUPER_ADMIN)){
				
				query = "select" +
						" bcwtlogcall.adminname, bcwtlogcall.calldate," +
						"  bcwtPatron.emailid, bcwtlogcall.logcallid," +
						" bcwtPatron.firstname, bcwtlogcall.bcwtpatrontype.patrontypeid," +
						" bcwtlogcall.patronid, bcwtlogcall.adminpatrontypeid, bcwtlogcall.adminpatronid" +
						" from" +
						" BcwtLogCall bcwtlogcall, BcwtPatron bcwtPatron " +
						 " where" +
						" bcwtlogcall.logcallid = " +
						" (select MAX(bcwtlogcall.logcallid) " +
						" from BcwtLogCall bcwtlogcall " +
						" where bcwtlogcall.patronid = " + patronId + 
						" and bcwtlogcall.bcwtpatrontype.patrontypeid = " + patronType + ")" + 
						" and bcwtPatron.patronid = bcwtlogcall.patronid and " +
						"(bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_SUPER_ADMIN+"' or " +
						"bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_ADMIN+"' or " +
						"bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_READONLY+"') ";
							
			}
			
			if(Util.equalsIgnoreCase(userType, Constants.SUPER_ADMIN)){
				query = "select" +
				" bcwtlogcall.adminname, bcwtlogcall.calldate," +
				"  partnerAdminDetails.email, bcwtlogcall.logcallid," +
				" partnerAdminDetails.firstName, bcwtlogcall.bcwtpatrontype.patrontypeid," +
				" bcwtlogcall.patronid,bcwtlogcall.adminpatrontypeid, bcwtlogcall.adminpatronid" +
				" from" +
				" BcwtLogCall bcwtlogcall, PartnerAdminDetails partnerAdminDetails " +
				" where" +
		    	" bcwtlogcall.logcallid = " +		    	
		    	" (select MAX(bcwtlogcall.logcallid) " +
		    	" from BcwtLogCall bcwtlogcall " +
		    	" where bcwtlogcall.patronid = " + patronId + 
		    	" and bcwtlogcall.bcwtpatrontype.patrontypeid = " + patronType + ")" + 
		    	" and partnerAdminDetails.partnerId = bcwtlogcall.patronid";
			}
			
			/*if(Util.equalsIgnoreCase(userType, Constants.SUPER_ADMIN)){
				query = "select" +
				" bcwtlogcall.adminname, bcwtlogcall.calldate," +
				"  partnerAdminDetails.email, bcwtlogcall.logcallid," +
				" partnerAdminDetails.firstName, bcwtlogcall.bcwtpatrontype.patrontypeid," +
				" bcwtlogcall.patronid,bcwtlogcall.adminpatrontypeid, bcwtlogcall.adminpatronid" +
				" from" +
				" BcwtLogCall bcwtlogcall, PartnerAdminDetails partnerAdminDetails " +
				" where" +
		    	" bcwtlogcall.logcallid = " +		    	
		    	" (select MAX(bcwtlogcall.logcallid) " +
		    		" from BcwtLogCall bcwtlogcall " +
		    		" where bcwtlogcall.patronid = " + partnerId + 
		    		" and bcwtlogcall.bcwtpatrontype.patrontypeid = " + patronType + ")" + 
		    		" and partnerAdminDetails.partnerId = bcwtlogcall.patronid";
			}*/
			List queryList =  session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] object = (Object[]) iter.next();
					bcwtLogDTO = new BcwtLogDTO();
					
					if(object[0] != null) {
						bcwtLogDTO.setAdministratorUserName(object[0].toString());
					}
					if(object[1] != null) {
						bcwtLogDTO.setDateOfCall(Util.getFormattedDate(object[1]));
					}
					if(object[2] != null) {
						bcwtLogDTO.setEmailid(object[2].toString());
					}
					if(object[3] != null) {
						bcwtLogDTO.setLogCallId(object[3].toString());
					}
					if(object[4] != null) {
						bcwtLogDTO.setFirstname(object[4].toString());
					}
					if(object[5] != null) {
						bcwtLogDTO.setPatronTypeId(object[5].toString());
					}
					if(object[6] != null) {
						bcwtLogDTO.setPatronId(object[6].toString());
					}
					if(object[7] != null) {
						bcwtLogDTO.setAdminPatronTypeId(object[7].toString());
					}
					if(object[8] != null) {
						bcwtLogDTO.setAdminPatronId(object[8].toString());
					}			
				}
			}		
			
			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got recentCall List");			
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		
		return bcwtLogDTO;
	}
	
	
	public List getCallLogDetails(String userType, String loginPatronType)throws Exception{
		final String MY_NAME = ME + "getCallLogDetails: ";
		BcwtsLogger.debug(MY_NAME);
		
		BcwtLogDTO bcwtLogDTO = null;		
		String patronType = null;		
		List superAdminlogDetailsList = new ArrayList();
		List supremeAdminlogDetailsList = new ArrayList();
		List logDetailsList = new ArrayList();
		Map emailMap = new HashMap();
		
		try {
			
			if(Util.equalsIgnoreCase(userType, Constants.IS) || 
					Util.equalsIgnoreCase(userType, Constants.GBS_SUPER_ADMIN)){
				List patronTypeList = getPatronTypeList();
				
				if(patronTypeList != null && !patronTypeList.isEmpty()){
					for (Iterator iter = patronTypeList.iterator(); iter.hasNext();) {
						patronType = iter.next().toString();
						
						if(patronType != null) {
							List bcwtPatronDetailsList = getBcwtPatronDetails(patronType);
							 
							if(bcwtPatronDetailsList != null && !bcwtPatronDetailsList.isEmpty()) {
								for (Iterator iterator = bcwtPatronDetailsList.iterator(); iterator.hasNext();) {
									BcwtPatronDTO bcwtPatronDTO = (BcwtPatronDTO) iterator.next();
									String emailId = bcwtPatronDTO.getEmailid();									
									String patronId = bcwtPatronDTO.getPatronid().toString();
									
									String uniqueKey = patronType+patronId+emailId;
									
									if(emailMap.get(uniqueKey) == null){
										bcwtLogDTO = getRecentCall(emailId, patronId, patronType,userType);
										
										if(bcwtLogDTO != null) {
											if(bcwtLogDTO.getAdminPatronTypeId().equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)) {
												superAdminlogDetailsList.add(bcwtLogDTO);
											} 											
											supremeAdminlogDetailsList.add(bcwtLogDTO);											
										}
									}									
									emailMap.put(uniqueKey, emailId);
								}
							}
						}
					}
					emailMap.clear();
				}
			}
			
			if(userType.equalsIgnoreCase(Constants.SUPER_ADMIN)){
				
				List partnerTypeList = getPartnerTypeList();
				
				if(partnerTypeList != null && !partnerTypeList.isEmpty()){
					for (Iterator iter = partnerTypeList.iterator(); iter.hasNext();) {
						patronType = iter.next().toString();
						
						if(patronType != null) {
							List bcwtPartnerDetailsList = getPartnerAdminDetails(patronType);
							
							
							if(bcwtPartnerDetailsList != null && !bcwtPartnerDetailsList.isEmpty()) {
								for (Iterator iterator = bcwtPartnerDetailsList.iterator(); iterator.hasNext();) {
									PartnerAdminDetails partnerAdminDetails = (PartnerAdminDetails) iterator.next();
									String emailId = partnerAdminDetails.getEmail();									
									String patronId = partnerAdminDetails.getPartnerId().toString();
									
									String uniqueKey = patronType+patronId+emailId;
									
									if(emailMap.get(uniqueKey) == null){
										bcwtLogDTO = getRecentCall(emailId, patronId, patronType,userType);										
										if(bcwtLogDTO != null) {
											if(bcwtLogDTO.getAdminPatronTypeId().equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)) {
												superAdminlogDetailsList.add(bcwtLogDTO);
											} 											
											supremeAdminlogDetailsList.add(bcwtLogDTO);											
										}
									}									
									emailMap.put(uniqueKey, emailId);
								}
							}
						}
					}
					emailMap.clear();
				}
			}
			if(loginPatronType.equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)) {
				logDetailsList.clear();
				for (Iterator iter = superAdminlogDetailsList.iterator(); iter.hasNext();) {
					BcwtLogDTO superAdminBcwtLogDTO = (BcwtLogDTO) iter.next();
					logDetailsList.add(superAdminBcwtLogDTO);
				}				
			} else if(loginPatronType.equalsIgnoreCase(Constants.MARTA_SUPREME_ADMIN) ||
					loginPatronType.equalsIgnoreCase(Constants.MARTA_IT_ADMIN)) {
				logDetailsList.clear();
				for (Iterator iter = supremeAdminlogDetailsList.iterator(); iter.hasNext();) {
					BcwtLogDTO supremeAdminBcwtLogDTO = (BcwtLogDTO) iter.next();
					logDetailsList.add(supremeAdminBcwtLogDTO);
				}				
			}
			
			superAdminlogDetailsList.clear();
			supremeAdminlogDetailsList.clear();
			
			BcwtsLogger.info(MY_NAME + "got CallLogDetails");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		
		return logDetailsList;
	}
	
	
	public List getPartnerTypeList() throws Exception {
		List PartnerTypeList = new ArrayList();
		PartnerTypeList.add(Constants.SUPER_ADMIN);
		PartnerTypeList.add(Constants.ADMIN);
		PartnerTypeList.add(Constants.READ_ONLY);
		PartnerTypeList.add(Constants.BATCHPROCESS);
		PartnerTypeList.add(Constants.TMA);
		
		return PartnerTypeList;
	}
	
	public List getPartnerAdminDetails(String roleType) throws Exception {		
		final String MY_NAME = ME + "getPartnerAdminDetails: ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;		
		Session session = null;		
		List getPartnerAdminDetailsList = new ArrayList();
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			String query = "select" +
								" bcwtLogCall.patronid, partnerAdminDetails.email" +
							" from BcwtLogCall bcwtLogCall, PartnerAdminDetails partnerAdminDetails" +
								" where bcwtLogCall.bcwtpatrontype.patrontypeid = " + roleType +
								" and partnerAdminDetails.partnerId = bcwtLogCall.patronid";
			
			List queryList =  session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {					
					Object[] object = (Object[]) iter.next();
					PartnerAdminDetails partnerAdminDetails = new PartnerAdminDetails();
					if(object[0] != null){
						partnerAdminDetails.setPartnerId(Long.valueOf(object[0].toString()));
					}
					if(object[1] != null){
						partnerAdminDetails.setEmail(object[1].toString());
					}
					
					getPartnerAdminDetailsList.add(partnerAdminDetails);
				}
			}			
			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got PartnerAdminDetails");			
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		
		return getPartnerAdminDetailsList;
		
	}
	
	public BcwtLogDTO getPartnerRecentCall(String emailId, String partnerId, String roleType) throws Exception {
		final String MY_NAME = ME + "getPartnerRecentCall: ";
		BcwtsLogger.info(MY_NAME);
		Transaction transaction = null;		
		Session session = null;		
		BcwtLogDTO bcwtLogDTO = null;
		List recentCallList = new ArrayList();
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			String query = "select" +
								" bcwtlogcall.adminname, bcwtlogcall.calldate," +
								"  partnerAdminDetails.email, bcwtlogcall.logcallid," +
								" partnerAdminDetails.firstName, bcwtlogcall.bcwtpatrontype.patrontypeid," +
								" bcwtlogcall.patronid,bcwtlogcall.adminpatrontypeid, bcwtlogcall.adminpatronid" +
							" from" +
								" BcwtLogCall bcwtlogcall, PartnerAdminDetails partnerAdminDetails " +
						    " where" +
						    	" bcwtlogcall.logcallid = " +
						    	
						    	" (select MAX(bcwtlogcall.logcallid) " +
						    		" from BcwtLogCall bcwtlogcall " +
						    		" where bcwtlogcall.patronid = " + partnerId + 
						    		" and bcwtlogcall.bcwtpatrontype.patrontypeid = " + roleType + ")" + 
						    		" and partnerAdminDetails.partnerId = bcwtlogcall.patronid";
			
			List queryList =  session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] object = (Object[]) iter.next();
					bcwtLogDTO = new BcwtLogDTO();
					
					if(object[0] != null) {
						bcwtLogDTO.setAdministratorUserName(object[0].toString());
					}
					if(object[1] != null) {
						bcwtLogDTO.setDateOfCall(Util.getFormattedDate(object[1]));
					}
					if(object[2] != null) {
						bcwtLogDTO.setEmailid(object[2].toString());
					}
					if(object[3] != null) {
						bcwtLogDTO.setLogCallId(object[3].toString());
					}
					if(object[4] != null) {
						bcwtLogDTO.setFirstname(object[4].toString());
					}
					if(object[5] != null) {
						bcwtLogDTO.setPatronTypeId(object[5].toString());
					}
					if(object[6] != null) {
						bcwtLogDTO.setPatronId(object[6].toString());
					}
					if(object[7] != null) {
						bcwtLogDTO.setAdminPatronTypeId(object[7].toString());
					}
					if(object[8] != null) {
						bcwtLogDTO.setAdminPatronId(object[8].toString());
					}
					
					recentCallList.add(bcwtLogDTO);					
				}
			}		
			
			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got partner recentCall List");			
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		
		return bcwtLogDTO;
	}
	
	public List getPartnerCallLogDetails(String userType, String loginPatronType)throws Exception{
		final String MY_NAME = ME + "getPartnerCallLogDetails: ";
		BcwtsLogger.debug(MY_NAME);
		
		BcwtLogDTO bcwtLogDTO = null;		
		String roleType = null;	
		List superAdminlogDetailsList = new ArrayList();
		List supremeAdminlogDetailsList = new ArrayList();
		List logDetailsList = new ArrayList();
		Map emailMap = new HashMap();
		
		try {
			
			if(userType.equalsIgnoreCase(Constants.PARTNER)){
					
				List partnerTypeList = getPartnerTypeList();
				
				if(partnerTypeList != null && !partnerTypeList.isEmpty()){
					for (Iterator iter = partnerTypeList.iterator(); iter.hasNext();) {
						roleType = iter.next().toString();
						
						if(roleType != null) {
							List bcwtPartnerDetailsList = getPartnerAdminDetails(roleType);
							 
							for (Iterator iterator = bcwtPartnerDetailsList.iterator(); iterator.hasNext();) {
								PartnerAdminDetails partnerAdminDetails = (PartnerAdminDetails) iterator.next();
								String emailId = partnerAdminDetails.getEmail();
								String partnerId = partnerAdminDetails.getPartnerId().toString();
								
								String uniqueKey = roleType+partnerId+emailId;
								
								if(emailMap.get(uniqueKey) == null){
									bcwtLogDTO = getPartnerRecentCall(emailId, partnerId, roleType);
									logDetailsList.add(bcwtLogDTO);									
								}
								if(bcwtLogDTO != null) {
									if(bcwtLogDTO.getAdminPatronTypeId().equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)) {
										superAdminlogDetailsList.add(bcwtLogDTO);
									} 
									
									supremeAdminlogDetailsList.add(bcwtLogDTO);
									
								}
								
								emailMap.put(uniqueKey, emailId);
							}
						}
					}
					emailMap.clear();
				}
			}
			if(loginPatronType.equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)) {
				logDetailsList.clear();
				for (Iterator iter = superAdminlogDetailsList.iterator(); iter.hasNext();) {
					BcwtLogDTO superAdminBcwtLogDTO = (BcwtLogDTO) iter.next();
					logDetailsList.add(superAdminBcwtLogDTO);
				}				
			} else if(loginPatronType.equalsIgnoreCase(Constants.MARTA_SUPREME_ADMIN) ||
					loginPatronType.equalsIgnoreCase(Constants.MARTA_IT_ADMIN)) {
				logDetailsList.clear();
				for (Iterator iter = supremeAdminlogDetailsList.iterator(); iter.hasNext();) {
					BcwtLogDTO supremeAdminBcwtLogDTO = (BcwtLogDTO) iter.next();
					logDetailsList.add(supremeAdminBcwtLogDTO);
				}				
			}
			
			superAdminlogDetailsList.clear();
			supremeAdminlogDetailsList.clear();
		
			BcwtsLogger.info(MY_NAME + "got PartnerCallLogDetails");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		
		return logDetailsList;
	}
	
	public List populateLogViewDetails(String patronId,
			String patronTypeId,String emailId,String userType) throws Exception {

		final String MY_NAME = ME + "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = null;
		BcwtLogCall bcwtLogCall=null;
		List viewList = null;
		List logIdList = null;
		
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
		String query = "";
		try {
			session = getSession();
			transaction = session.beginTransaction();
			viewList = new ArrayList();
			logIdList=new ArrayList();
			/*String qry = "select bcwtlogcall.logcallid from BcwtLogCall bcwtlogcall," +
						 "BcwtPatronType bcwtpatrontype,BcwtPatron bcwtPatron "+
			             "where bcwtlogcall.bcwtpatrontype.patrontypeid = '"+patronTypeId+"' and bcwtlogcall.patronid = '"+patronId+"'";*/
			
			//Long patronIdLong = new Long(patronId);
			String qry = "select bcwtlogcall.logcallid from BcwtLogCall bcwtlogcall " +
				     	"where bcwtlogcall.patronid="+patronId;
			logIdList =session.createQuery(qry).list();
			if(logIdList !=null && !Util.isBlankOrNull(logIdList.toString())){
				for (Iterator it= logIdList.iterator();it.hasNext();){
					
					if(Util.equalsIgnoreCase(userType, Constants.IS)){
						query = " select bcwtlogcall.reason,bcwtlogcall.calldate,bcwtlogcall.notes,bcwtlogcall.logcallid," +
							"bcwtPatron.emailid,bcwtPatron.firstname,bcwtPatron.lastname" +
						   " from BcwtLogCall bcwtlogcall,BcwtPatron bcwtPatron" +
						   " where bcwtPatron.patronid = bcwtlogcall.patronid and " +
						   "bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.IS +"'" +
						   		" and bcwtlogcall.logcallid="+(Long) it.next() ;
					}
					
					if(Util.equalsIgnoreCase(userType, Constants.GBS_SUPER_ADMIN)){
						query = " select bcwtlogcall.reason,bcwtlogcall.calldate,bcwtlogcall.notes,bcwtlogcall.logcallid," +
							"bcwtPatron.emailid,bcwtPatron.firstname,bcwtPatron.lastname" +
						    " from BcwtLogCall bcwtlogcall,BcwtPatron bcwtPatron" +						   
						    " where bcwtPatron.patronid = bcwtlogcall.patronid and " +						   
						   	" bcwtlogcall.logcallid= '"+(Long) it.next()+"' and (bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_SUPER_ADMIN+"' or " +
						   	"bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_ADMIN+"' or " +
						   	"bcwtPatron.bcwtpatrontype.patrontypeid = '"+Constants.GBS_READONLY+"') ";
						
					}
					
					
					
					
					List  logListToIterate = session.createQuery(query).list(); 
					
					for (Iterator iter = logListToIterate.iterator(); iter.hasNext();) {
						Object[] bcwtLogs = (Object[]) iter.next();

						bcwtLogDTO = new BcwtLogDTO();
										
						if (bcwtLogs != null) {
												
							if (null != bcwtLogs[0]) {
								bcwtLogDTO.setReasonForCall(bcwtLogs[0]
										.toString());
							}
							if (null != bcwtLogs[1]) {								
								bcwtLogDTO.setDateOfCall(Util.getFormattedDate(bcwtLogs[1]));
							}
							if (null != bcwtLogs[2]) {
								bcwtLogDTO.setNote(bcwtLogs[2].toString());
							}
							if (null != patronId) {
								bcwtLogDTO.setPatronId(patronId);
							}
							if (null !=patronTypeId) {
								bcwtLogDTO.setPatronTypeId(patronTypeId);
							}
							if (null !=bcwtLogs[3].toString()) {
								bcwtLogDTO.setLogCallId(bcwtLogs[3].toString());
							}
							if (null !=bcwtLogs[4].toString()) {
								bcwtLogDTO.setEmailid(bcwtLogs[4].toString());
							}
							if (null !=bcwtLogs[5].toString()) {
								bcwtLogDTO.setFirstName(bcwtLogs[5].toString());
							}
							if (null !=bcwtLogs[6].toString()) {
								bcwtLogDTO.setLastName(bcwtLogs[6].toString());
							}
							
							}
						viewList.add(bcwtLogDTO);
					
				}
			  }
			}
			transaction.commit();
			session.flush();
			
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return viewList;
	}
	public List populatePartnerViewDetails(String patronId,String patronTypeId,String emailId) throws Exception {

		final String MY_NAME = ME + "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = null;
		BcwtLogCall bcwtLogCall=null;
		List viewList = null;
		List logIdList = null;
		
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
		//BcwtPwdVerifiy bcwtPwdVerifiy = null;
		//BcwtSecretQuestions bcwtSecretQuestions = null;
		
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			viewList = new ArrayList();
			logIdList=new ArrayList();
			/*String qry = "select bcwtlogcall.logcallid from BcwtLogCall bcwtlogcall," +
						 "BcwtPatronType bcwtpatrontype,BcwtPatron bcwtPatron "+
			             "where bcwtlogcall.bcwtpatrontype.patrontypeid = '"+patronTypeId+"' and bcwtlogcall.patronid = '"+patronId+"'";*/
			
			//Long patronIdLong = new Long(patronId);
			String qry = "select bcwtlogcall.logcallid from BcwtLogCall bcwtlogcall " +
				     	"where bcwtlogcall.patronid="+patronId;
			logIdList =session.createQuery(qry).list();
			if(logIdList !=null && !Util.isBlankOrNull(logIdList.toString())){
				for (Iterator it= logIdList.iterator();it.hasNext();){
					String query = " select bcwtlogcall.reason,bcwtlogcall.calldate,bcwtlogcall.notes,bcwtlogcall.logcallid," +
								   "partnerAdminInfo.userName,partnerAdminDetails.firstName,partnerAdminDetails.lastName" +
								   " from BcwtLogCall bcwtlogcall,PartnerAdminDetails partnerAdminDetails," +
								   "PartnerAdminInfo partnerAdminInfo" +
								   " where partnerAdminDetails.partnerId = bcwtlogcall.patronid and " +
								   "partnerAdminDetails.partnerId = partnerAdminInfo.partnerAdminDetails.partnerId " +
								   " and bcwtlogcall.logcallid="+(Long) it.next();
					List  logListToIterate = session.createQuery(query).list(); 
					
					for (Iterator iter = logListToIterate.iterator(); iter.hasNext();) {
						Object[] bcwtLogs = (Object[]) iter.next();

						bcwtLogDTO = new BcwtLogDTO();
										
						if (bcwtLogs != null) {
												
							if (null != bcwtLogs[0]) {
								bcwtLogDTO.setReasonForCall(bcwtLogs[0]
										.toString());
							}
							if (null != bcwtLogs[1]) {
								bcwtLogDTO.setDateOfCall(Util.getFormattedDate(bcwtLogs[1]));
							}
							if (null != bcwtLogs[2]) {
								bcwtLogDTO.setNote(bcwtLogs[2].toString());
							}
							if (null != patronId) {
								bcwtLogDTO.setPatronId(patronId);
							}
							if (null !=patronTypeId) {
								bcwtLogDTO.setPatronTypeId(patronTypeId);
							}
							if (null !=bcwtLogs[3].toString()) {
								bcwtLogDTO.setLogCallId(bcwtLogs[3].toString());
							}
							if (null !=bcwtLogs[4].toString()) {
								bcwtLogDTO.setEmailid(bcwtLogs[4].toString());
							}
							if (null !=bcwtLogs[5].toString()) {
								bcwtLogDTO.setFirstName(bcwtLogs[5].toString());
							}
							if (null !=bcwtLogs[6].toString()) {
								bcwtLogDTO.setLastName(bcwtLogs[6].toString());
							}
							
							}
						viewList.add(bcwtLogDTO);
					
				}
			  }
			}
			transaction.commit();
			session.flush();
			
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return viewList;
	}
	
	private String getPatronTypeId(String logcallId) throws Exception {

		final String MY_NAME = ME + "getPatronTypeId: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = null;
		BcwtLogCall bcwtLogCall=null;
		String patronTypeId = null;
		List patronList = null;
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
		BcwtPwdVerifiy bcwtPwdVerifiy = null;
		BcwtSecretQuestions bcwtSecretQuestions = null;

		
		try {
			patronList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			
			String query = " select bcwtlogcall.bcwtpatrontype.patrontypeid from BcwtLogCall bcwtlogcall,BcwtPatronType bcwtpatrontype " +
			" where bcwtlogcall.logcallid = '"+logcallId+"'";
			patronList = session.createQuery(query).list();
		  /*  for (Iterator iter = patronList.iterator(); iter.hasNext(); ){
		    	Object element = (Object) iter.next();
		    	if(null != element){
		    		
		    	}
		    }*/
			transaction.commit();
			session.flush();
			
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return patronList.get(0).toString();
	}
	
	public boolean addLogPatron(BcwtLogDTO bcwtLogDTO)
				throws Exception {
		final String MY_NAME = ME + "addLogPatron: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		BcwtLogCall bcwtLogCall=null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			if(bcwtLogDTO != null){
				bcwtLogCall = new BcwtLogCall();
				
				if(!Util.isBlankOrNull(bcwtLogDTO.getPatronTypeId())){
					BcwtPatronType bcwtPatronType = new BcwtPatronType();
					bcwtPatronType.setPatrontypeid(Long.valueOf(bcwtLogDTO.getPatronTypeId()));
					bcwtLogCall.setBcwtpatrontype(bcwtPatronType);					
				}
				if(!Util.isBlankOrNull(bcwtLogDTO.getPatronId())){
					bcwtLogCall.setPatronid(Long.valueOf(bcwtLogDTO.getPatronId()));
				}
				if(!Util.isBlankOrNull(bcwtLogDTO.getAdminPatronTypeId())){					
					bcwtLogCall.setAdminpatrontypeid(Long.valueOf(bcwtLogDTO.getAdminPatronTypeId()));					
				}
				if(!Util.isBlankOrNull(bcwtLogDTO.getAdminPatronId())){
					bcwtLogCall.setAdminpatronid(Long.valueOf(bcwtLogDTO.getAdminPatronId()));
				}			
				if(!Util.isBlankOrNull(bcwtLogDTO.getDateOfCall())) {
					bcwtLogCall.setCalldate(Util.convertStringToDate(bcwtLogDTO.getDateOfCall()));
				}
				/*if(!Util.isBlankOrNull(bcwtLogDTO.getTimeStampForCall())) {
					bcwtLogCall.setCalltime(Util.convertStringToDate(bcwtLogDTO.getTimeStampForCall()));
				}*/
				if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorEmail())){					
					bcwtLogCall.setAdminemailid(bcwtLogDTO.getAdministratorEmail());
				}
				if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorPhoneNumber())){
					bcwtLogCall.setAdminphonenumber(bcwtLogDTO.getAdministratorPhoneNumber());
				}
				if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorUserName())){					
					bcwtLogCall.setAdminname(bcwtLogDTO.getAdministratorUserName());
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
			}
			
			session.save(bcwtLogCall);
			transaction.commit();
			session.flush();
			isAdded = true;
			BcwtsLogger.info(MY_NAME + "Log Patron saved to database ");
		} catch (Exception ex) {
			isAdded = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isAdded;
	}
	
	public BcwtLogDTO populateViewLogUserDetails(String logcallId) throws Exception {

		final String MY_NAME = ME + "getAdminViewList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin view details started");
		Session session = null;
		Transaction transaction = null;
		//BcwtPatron bcwtPatron = null;
		BcwtLogCall bcwtLogCall=null;
		BcwtLogDTO bcwtLogDTO = new BcwtLogDTO();
				
		try {
			session = getSession();
			transaction = session.beginTransaction();
			bcwtLogCall=new BcwtLogCall();
			String query = " from BcwtLogCall where logcallid = '" + logcallId + "'";
			  /*String query="select bcwtlogcall.logcallid from BcwtLogCall bcwtlogcall " +
				     	"where bcwtlogcall.patronid="+patronId;*/
			bcwtLogCall = (BcwtLogCall) session.createQuery(
						query).uniqueResult();

			if (null != bcwtLogCall) {
				
				if(null != bcwtLogCall.getPatronid()){
					bcwtLogDTO.setPatronId(bcwtLogCall.getPatronid().toString());
				}
				if(null != bcwtLogCall.getBcwtpatrontype().getPatrontypeid()){
					bcwtLogDTO.setPatronTypeId(bcwtLogCall.getBcwtpatrontype().getPatrontypeid().toString());
				}
				if(null != bcwtLogCall.getCalldate()){
					bcwtLogDTO.setDateOfCall(bcwtLogCall.getCalldate().toString());
				}
				if(null != bcwtLogCall.getCalltime()){
					bcwtLogDTO.setTimeStampForCall(bcwtLogCall.getCalltime().toString());
				}
				if(null != bcwtLogCall.getAdminemailid()){
					bcwtLogDTO.setAdministratorEmail(bcwtLogCall.getAdminemailid().toString());
				}
				if(null != bcwtLogCall.getAdminphonenumber()){
					bcwtLogDTO.setAdministratorPhoneNumber(bcwtLogCall.getAdminphonenumber().toString());
				}
				if(null != bcwtLogCall.getAdminname()){
					bcwtLogDTO.setAdministratorUserName(bcwtLogCall.getAdminname().toString());
				}
				
				if(null != bcwtLogCall.getReason()){
					bcwtLogDTO.setReasonForCall(bcwtLogCall.getReason().toString());
				}
				if(null != bcwtLogCall.getNotes()){
					bcwtLogDTO.setNote(bcwtLogCall.getNotes().toString());
				}
				if(!Util.isBlankOrNull(bcwtLogCall.getSavedfilename())){
					bcwtLogDTO.setSavedFileName(bcwtLogCall.getSavedfilename());
				}
				if(!Util.isBlankOrNull(bcwtLogCall.getUploadedfilename())){
					bcwtLogDTO.setUploadedFileName(bcwtLogCall.getUploadedfilename());
				}
			}
			
			transaction.commit();
			session.flush();
			
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return bcwtLogDTO;
	}

	
	public List displayRecentLogList(String patronId,String patronTypeId,String emailId) throws Exception {
		final String MY_NAME = ME + "displayRecentLogList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtLogCall bcwtLogCall = null;
		List recentList = null;
		BcwtLogDTO bcwtlogDTO = null;
		List recentLogList=null;
		Long adminPatronTypeId = null;
		try {
			recentList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			adminPatronTypeId = Long.valueOf(Constants.MARTA_SUPER_ADMIN);
			String query="";
			if(Util.equalsIgnoreCase(patronTypeId,Constants.MARTA_SUPREME_ADMIN) ||
					Util.equalsIgnoreCase(patronTypeId,Constants.MARTA_IT_ADMIN)){
				query = "from BcwtLogCall bcwtlogcall order by bcwtlogcall.logcallid desc";
			}if(Util.equalsIgnoreCase(patronTypeId,Constants.MARTA_SUPER_ADMIN)){
				query = "from BcwtLogCall bcwtlogcall " +
						" where bcwtlogcall.adminpatrontypeid = "+ adminPatronTypeId + " order by bcwtlogcall.logcallid desc";
			}
			
			//query=" order by bcwtlogcall.logcallid desc";
			 List recentUserList = session.createQuery(query).list();
				if(recentUserList != null && !recentUserList.isEmpty()){
				for (Iterator iter = recentUserList.iterator(); iter.hasNext();){
					BcwtLogCall bcwtlogcall = (BcwtLogCall) iter.next();
					
					bcwtlogDTO = new BcwtLogDTO();
					if(null!=bcwtlogcall.getPatronid()){					
						bcwtlogDTO.setPatronId(bcwtlogcall.getPatronid().toString());
					}
					
					if(null!=bcwtlogcall.getBcwtpatrontype().getPatrontypeid()){					
						bcwtlogDTO.setPatronTypeId(bcwtlogcall.getBcwtpatrontype().getPatrontypeid().toString());
					}
					
					if(!Util.isBlankOrNull(bcwtlogcall.getReason())){					
						bcwtlogDTO.setReasonForCall(bcwtlogcall.getReason());
					}
					
					if(!Util.isBlankOrNull(bcwtlogcall.getNotes())){					
						bcwtlogDTO.setNote(bcwtlogcall.getNotes());
					}
					if(bcwtlogcall.getCalldate()!=null) {
						bcwtlogDTO.setDateOfCall(Util.convertDateToString(bcwtlogcall.getCalldate()));
					}
					if(!Util.isBlankOrNull(bcwtlogcall.getAdminemailid())){					
						bcwtlogDTO.setAdministratorEmail(bcwtlogcall.getAdminemailid());
					}
					
					recentList.add(bcwtlogDTO);
					if(recentUserList.size()==5){
						recentUserList = new ArrayList();
						break;
					}
				}
				}
				
				transaction.commit();
				session.flush();
				session.close();
				
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			}
			return recentList;
		}

			/*String qry="select bcwtlogcall.logcallid from BcwtLogCall bcwtlogcall " +
				     	"where bcwtlogcall.patronid="+patronId;
			recentLogList =session.createQuery(qry).list();
			if(recentLogList !=null && !Util.isBlankOrNull(recentLogList.toString())){
				for (Iterator it= recentLogList.iterator();it.hasNext();){
					String query = " select bcwtlogcall.reason,bcwtlogcall.notes,bcwtlogcall.calldate," +
									"bcwtlogcall.adminemailid,bcwtlogcall.logcallid" +
								   " from BcwtLogCall bcwtlogcall,BcwtPatron bcwtPatron" +
								   " where bcwtPatron.patronid = bcwtlogcall.patronid and bcwtlogcall.logcallid="+(Long) it.next();
				
			String query ="select bcwtLogCall.reason,bcwtLogCall.notes,bcwtLogCall.calldate," +
					     "bcwtPatron.emailid,bcwtLogCall.logcallid from BcwtLogCall bcwtLogCall,BcwtPatron bcwtPatron " +
					     "where bcwtLogCall.logcallid = (select MAX(bcwtLogCall.logcallid) from BcwtLogCall bcwtlogcall)" +
					     "and bcwtLogCall.patronid=bcwtPatron.patronid";
			
			
                 List  logListToIterate = session.createQuery(query).list(); 
					
					for (Iterator iter = logListToIterate.iterator(); iter.hasNext();) {
						Object[] bcwtLogs = (Object[]) iter.next();

						bcwtLogDTO = new BcwtLogDTO();
										
						if (bcwtLogs != null) {
												
							if (null != bcwtLogs[0]) {
								bcwtLogDTO.setReasonForCall(bcwtLogs[0]
										.toString());
							}
							if (null != bcwtLogs[1]) {
								bcwtLogDTO.setNote(bcwtLogs[1].toString());
							}
							if (null != bcwtLogs[2]) {
								bcwtLogDTO.setDateOfCall(bcwtLogs[2].toString());
							}
							if (null != bcwtLogs[3]) {
								bcwtLogDTO.setAdministratorEmail(bcwtLogs[3].toString());
							}
							if (null !=bcwtLogs[3].toString()) {
								bcwtLogDTO.setLogCallId(bcwtLogs[3].toString());
							}
							if (null != patronId) {
								bcwtLogDTO.setPatronId(patronId);
							}
							if (null !=patronTypeId) {
								bcwtLogDTO.setPatronTypeId(patronTypeId);
							}
							
							}
						recentList.add(bcwtLogDTO);
					
				}
			  }
			}*/
      public List getRecentLogData(String patronId,String patronTypeId)throws Exception{
    	final String MY_NAME = ME + "getRecentLogData";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtLogCall bcwtLogCall = null;
		BcwtLogDTO bcwtLogDTO = null;
		List recentLogList=null;
		try {
			recentLogList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query="select bcwtLogCall.adminemailid,bcwtPatron.emailid from BcwtLogCall bcwtLogCall,BcwtPatron bcwtPatron" +
					     "where bcwtLogCall.patronid=bcwtPatron.patronid ";
			
				transaction.commit();
				session.flush();
				session.close();
				
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			}
    	  
    	  return recentLogList;
      }
 
      
      public boolean getaddNewCall(BcwtLogDTO bcwtLogDTO) throws Exception {
  		final String MY_NAME = ME + "getaddNewCall: ";
  		BcwtsLogger.debug(MY_NAME);
  		boolean isAdded = false;
  		Session session = null;
  		Transaction transaction = null;
  		BcwtLogCall bcwtLogCall=null;
  		String query = "";
  		List patronList = null;
  		String patronTypeId="";
  		BcwtPatronType bcwtPatronType=null;
  		try {
  			session = getSession();
  			transaction = session.beginTransaction();
  			//userName=bcwtLogCall.getAdminname();
  			if(bcwtLogDTO != null){
  				bcwtLogCall = new BcwtLogCall();
  				
  				if(!Util.isBlankOrNull(bcwtLogDTO.getPatronTypeId())){
  					 bcwtPatronType = new BcwtPatronType();
  					bcwtPatronType.setPatrontypeid(Long.valueOf(bcwtLogDTO.getPatronTypeId()));
  					bcwtLogCall.setBcwtpatrontype(bcwtPatronType);					
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getPatronId())){
  					bcwtLogCall.setPatronid(Long.valueOf(bcwtLogDTO.getPatronId()));
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getAdminPatronTypeId())){					
  					bcwtLogCall.setAdminpatrontypeid(Long.valueOf(bcwtLogDTO.getAdminPatronTypeId()));					
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getAdminPatronId())){
  					bcwtLogCall.setAdminpatronid(Long.valueOf(bcwtLogDTO.getAdminPatronId()));
  				}			
  				if(!Util.isBlankOrNull(bcwtLogDTO.getDateOfCall())) {
  					bcwtLogCall.setCalldate(Util.convertStringToDate(bcwtLogDTO.getDateOfCall()));
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getTimeStampForCall())) {
  					bcwtLogCall.setCalltime(Util.convertStringToDate(bcwtLogDTO.getTimeStampForCall()));
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorEmail())){					
  					bcwtLogCall.setAdminemailid(bcwtLogDTO.getAdministratorEmail());
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorPhoneNumber())){
  					bcwtLogCall.setAdminphonenumber(bcwtLogDTO.getAdministratorPhoneNumber());
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getAdministratorUserName())){					
  					bcwtLogCall.setAdminname(bcwtLogDTO.getAdministratorUserName());
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getReasonForCall())){					
  					bcwtLogCall.setReason(bcwtLogDTO.getReasonForCall());
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getNote())){					
  					bcwtLogCall.setNotes(bcwtLogDTO.getNote());
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getUserName())){					
  					bcwtLogCall.setAdminname(bcwtLogDTO.getUserName());
  				}
  				if(!Util.isBlankOrNull(bcwtLogDTO.getUserType())){
  					query="select p.patrontypeid ,p.patrontypename from BcwtPatronType p" +
  					" where p.patrontypeid='"+bcwtLogDTO.getUserType()+"' " ;
  					 bcwtPatronType = new BcwtPatronType();
  					List patronListToIterate = session.createQuery(query).list();
  					for (Iterator iter = patronListToIterate.iterator(); iter.hasNext();) {
  						Object[] element = (Object[]) iter.next();
  						if (null != element[0]) {
  							bcwtPatronType.setPatrontypeid(Long.valueOf(element[0].toString()));
  		  					bcwtLogCall.setBcwtpatrontype(bcwtPatronType);	
  							session.clear();
  						}
  					}
  				}
  				}
  			session.save(bcwtLogCall);
  			transaction.commit();
  			session.flush();
  			isAdded = true;
  			BcwtsLogger.info(MY_NAME + "Log Patron saved to database ");
  		} catch (Exception ex) {
  			isAdded = false;
  			BcwtsLogger.error(MY_NAME + ex.getMessage());
  			throw ex;
  		} finally {
  			closeSession(session, transaction);
  			BcwtsLogger.debug(MY_NAME + " Resources closed");
  		}
  		return isAdded;
  	}
      
      /**
  	 * method to Generate log call document
  	 * @param uploadedFileName
  	 * @param savedFileName
  	 * @return
  	 * @throws Exception
  	 */
  	public boolean generateDocument(String uploadedFileName,String savedFileName,
  			String fileLocation)throws Exception {
  		final String MY_NAME = ME + "generateDocument: ";
  		BcwtsLogger.debug(MY_NAME);		
  		Session session = null;
  		Transaction transaction = null;
  		boolean isAdded = false;
  		BcwtLogCall bcwtLogCall = null;
  		
  		try {
  			
  		} catch (Exception ex) {
  			BcwtsLogger.error(MY_NAME + ex.getMessage());
  			throw ex;
  		}
  		
  		
  		return isAdded;
  	}
  	
}

	