package com.marta.admin.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtAlertDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.hibernate.BcwtManualAlert;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Util;
import com.marta.admin.dto.BcwtUnlockAccountDTO;

public class CreateAlertDAO extends MartaBaseDAO {
	final String ME = "CreateAlertDAO: ";
	Date sysDate = new Date();
	DateFormat formatter = new SimpleDateFormat(Constants.DATE_WITH_TIME_FORMAT);
	DateFormat formatter1 = new SimpleDateFormat(Constants.DATE_FORMAT);
	
	public boolean addOrUpdateAlertMessage(BcwtAlertDTO bcwtAlertDTO) throws Exception {
		final String MY_NAME = ME + "addOrUpdateAlertMessage: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		BcwtManualAlert manualAlert = null;
		int alertId = 0;
		String releaseDate = null;
		String discontinueDate = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			if(bcwtAlertDTO != null){
				if(bcwtAlertDTO.getManualAlertId() !=null &&
						!Util.isBlankOrNull(bcwtAlertDTO.getManualAlertId())){
					alertId = Integer.parseInt(bcwtAlertDTO.getManualAlertId());
				}
			}
			if(alertId == 0){
				manualAlert = new BcwtManualAlert();
				if(bcwtAlertDTO != null){
					if(bcwtAlertDTO.getPatronId() != null){
						BcwtPatron bcwtpatron = new BcwtPatron();
						bcwtpatron.setPatronid(bcwtAlertDTO.getPatronId());
						manualAlert.setBcwtpatron(bcwtpatron);
					}if(bcwtAlertDTO.getDateToReleaseAlert() !=null && 
							!Util.isBlankOrNull(bcwtAlertDTO.getDateToReleaseAlert())){
						releaseDate = bcwtAlertDTO.getDateToReleaseAlert();
						Date releasedDate = formatter1.parse(releaseDate);
						if(releasedDate != null){
							manualAlert.setReleaseDate(releasedDate);
						}
					}else{
						manualAlert.setReleaseDate(sysDate);
					}if(bcwtAlertDTO.getDateToDiscontinueAlert() !=null && 
							!Util.isBlankOrNull(bcwtAlertDTO.getDateToDiscontinueAlert())){
						discontinueDate = bcwtAlertDTO.getDateToDiscontinueAlert();
						Date disContinuedDate = formatter1.parse(discontinueDate);
						if(disContinuedDate != null){
							manualAlert.setDiscontinueDate(disContinuedDate);
						}
					}else{
						manualAlert.setDiscontinueDate(new Date());
					}if(bcwtAlertDTO.getAlertTitle() !=null &&
							 !Util.isBlankOrNull(bcwtAlertDTO.getAlertTitle())){
						manualAlert.setAlertTitle(bcwtAlertDTO.getAlertTitle());
					}else{
						manualAlert.setAlertTitle(" ");
					}if(bcwtAlertDTO.getAlertMessage() !=null &&
							 !Util.isBlankOrNull(bcwtAlertDTO.getAlertMessage())){
						manualAlert.setAlertMessage(bcwtAlertDTO.getAlertMessage());
					}else{
						manualAlert.setAlertMessage(" ");
					}
					
				}
				
			}else{
				manualAlert = (BcwtManualAlert) session.load(BcwtManualAlert.class,
						new Long(alertId));
				if(manualAlert != null){
					if(bcwtAlertDTO != null){
						if(bcwtAlertDTO.getAlertTitle() != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getAlertTitle())){
							manualAlert.setAlertTitle(bcwtAlertDTO.getAlertTitle());
						}
						if(bcwtAlertDTO.getAlertMessage() !=null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getAlertMessage())){
							manualAlert.setAlertMessage(bcwtAlertDTO.getAlertMessage());
						}
						if(bcwtAlertDTO.getPatronId() != new Long(0)){
							BcwtPatron bcwtPatron = new BcwtPatron();
							bcwtPatron.setPatronid(bcwtAlertDTO.getPatronId());
							manualAlert.setBcwtpatron(bcwtPatron);
						}
						if(bcwtAlertDTO.getDateToReleaseAlert() != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getDateToReleaseAlert())){
							releaseDate = bcwtAlertDTO.getDateToReleaseAlert();
							Date releasedDate = formatter1.parse(releaseDate);
							if(releasedDate != null){
								manualAlert.setReleaseDate(releasedDate);
							}
						}else{
							manualAlert.setReleaseDate(new Date());
						}
						if(bcwtAlertDTO.getDateToDiscontinueAlert() != null &&
								!Util.isBlankOrNull(bcwtAlertDTO.getDateToDiscontinueAlert())){
							discontinueDate = bcwtAlertDTO.getDateToDiscontinueAlert();
							Date discontinuedDate = formatter1.parse(discontinueDate);
							if(discontinuedDate != null){
								manualAlert.setDiscontinueDate(discontinuedDate);
							}
						}else{
							manualAlert.setDiscontinueDate(new Date());
						}
					}
				}
			}
			
			session.saveOrUpdate(manualAlert);
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Alert Message saved to database ");
		} catch (Exception ex) {
			isUpdated = false;
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	//to get list of alerts
	public List getAlertList(String alertId,HttpServletRequest request) throws Exception {
		final String MY_NAME = ME + "getAlertList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session1 = null;
		Transaction transaction = null;
		List manualAlertList = null;
		List alertList =null;
		DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String query = null;
		BcwtAlertDTO alertDTO = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		Long patronId = new Long(0);
		Long userTypeId = new Long(0);
		//HttpServletRequest request = null;
		HttpSession session = request.getSession(true);
		try {
			alertList = new ArrayList();
			session1 = getSession();
			transaction = session1.beginTransaction();
			if(alertId != null && !Util.isBlankOrNull(alertId)){
				query = MartaQueries.GET_ALERT_LIST_QUERY;
				query = query+" and bcwtManualAlert.manualalertid = "+alertId;   
			}else{
				query = MartaQueries.GET_ALERT_LIST_QUERY;
			}
			if(session.getAttribute(Constants.USER_INFO)!=null){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
				userTypeId = bcwtPatronDTO.getBcwtusertypeid();
			}
			
			manualAlertList = session1.createQuery(query).list();
			if(manualAlertList != null){
				for(Iterator iter = manualAlertList.iterator();iter.hasNext();){
					alertDTO = new BcwtAlertDTO();
				    Object element[] = (Object[])iter.next();     
				    if(element[0]  !=null &&
							!Util.isBlankOrNull(element[0].toString())){
						alertDTO.setManualAlertId(element[0].toString());
					}else{
						alertDTO.setManualAlertId(" ");
					}
				    if(element[1] != null &&
							!Util.isBlankOrNull(element[1].toString())){
						alertDTO.setPatronId((Long)element[1]);
					}else{
						alertDTO.setPatronId(new Long(0));
					}
				    if(element[2] !=null &&
							!Util.isBlankOrNull(element[2].toString())){
				    	Date releaseDate = formatter2.parse(element[2].toString());
						alertDTO.setDateToReleaseAlert(formatter1.format(releaseDate));
					}else{
						alertDTO.setDateToReleaseAlert(" ");
					}
				    if(element[3] !=null &&
							!Util.isBlankOrNull(element[3].toString())){
				    	Date discontinueDate = formatter2.parse(element[3].toString());
						alertDTO.setDateToDiscontinueAlert(formatter1.format(discontinueDate));
					}else{
						alertDTO.setDateToDiscontinueAlert(" ");
					}
					if(element[4] !=null &&
							!Util.isBlankOrNull(element[4].toString())){
						alertDTO.setAlertTitle(element[4].toString());
					}else{
						alertDTO.setAlertTitle(" ");
					}
					if(element[5] !=null &&
							!Util.isBlankOrNull(element[5].toString())){
						alertDTO.setAlertMessage(element[5].toString());
					}else{
						alertDTO.setAlertMessage(" ");
					}
					if(userTypeId.toString().equalsIgnoreCase(Constants.MARTA_SUPREME_ADMIN)||
							userTypeId.toString().equalsIgnoreCase(Constants.MARTA_IT_ADMIN)){
						alertDTO.setIsShow(Constants.YES);
						alertDTO.setIsShowRemove(Constants.YES);
					}if(userTypeId.toString().equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)){
						if(element[6] != null){
							if(userTypeId.toString().equalsIgnoreCase(element[6].toString())){
								alertDTO.setIsShow(Constants.YES);
								alertDTO.setIsShowRemove(Constants.YES);
							}else{
								alertDTO.setIsShow(Constants.NO);
								alertDTO.setIsShowRemove(Constants.NO);
							}
						}
					}if(!userTypeId.toString().equalsIgnoreCase(Constants.MARTA_SUPREME_ADMIN) &&
							!userTypeId.toString().equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN) &&
							!userTypeId.toString().equalsIgnoreCase(Constants.MARTA_IT_ADMIN)){
						alertDTO.setIsShow(Constants.NO);
						alertDTO.setIsShowRemove(Constants.NO);
					}
					
					alertList.add(alertDTO);
				}
				
			}
			transaction.commit();
			session1.flush();
			BcwtsLogger.info(MY_NAME + "Alert Message saved to database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session1, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return alertList;
	}
	//	to remove alert messages
	public boolean removeAlertMessage(String alertId) throws Exception {
		final String MY_NAME = ME + "removeAlertMessage: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		BcwtManualAlert manualAlert = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			if(alertId != null && !Util.isBlankOrNull(alertId)){
				manualAlert = (BcwtManualAlert) session.load(BcwtManualAlert.class,
						new Long(alertId));
				
			}
			if(manualAlert != null){
				session.delete(manualAlert);
				transaction.commit();
				isDeleted = true;
			}else{
				isDeleted = false;
			}
			session.flush();
			BcwtsLogger.info(MY_NAME + "Alert Message Removed from database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isDeleted;
	}
	//	to display alert messages in welcome page
	public List displayAlerts(String patronId,String curDate) throws Exception {
		final String MY_NAME = ME + "displayAlerts: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtManualAlert manualAlert = null;
		List alertList = null;
		BcwtAlertDTO alertDTO = null;
		String releaseDate = null;
		String discontinueDate = null;
		try {
			alertList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query ="select bcwtManualalert.alertTitle,bcwtManualalert.alertMessage,"+
						  "bcwtManualalert.releaseDate,bcwtManualalert.discontinueDate "+
						  "from BcwtManualAlert bcwtManualalert "+
			              "where to_date('"+curDate+"','mm/dd/yyyy') >= trunc(bcwtManualalert.releaseDate) and "+
			              "to_date('"+curDate+"','mm/dd/yyyy') <= trunc(bcwtManualalert.discontinueDate) order by bcwtManualalert.manualalertid desc";
			List queryList = session.createQuery(query).list();
			if(queryList != null && !queryList.isEmpty()){
				for(Iterator iter = queryList.iterator();iter.hasNext();){
					alertDTO = new BcwtAlertDTO();
					Object element[] = (Object[]) iter.next();
					if(element[0] != null &&
							!Util.isBlankOrNull(element[0].toString())){
						alertDTO.setAlertTitle(element[0].toString());
					}else{
						alertDTO.setAlertTitle("");
					}
					if(element[1] != null &&
							!Util.isBlankOrNull(element[1].toString())){
						alertDTO.setAlertMessage(element[1].toString());
					}else{
						alertDTO.setAlertMessage("");
					}
					if(element[2] != null &&
							!Util.isBlankOrNull(element[2].toString())){
						releaseDate = Util.getDateAsString( (Date)element[2],"MM-dd-yyyy");
						alertDTO.setDateToReleaseAlert(releaseDate);
					}else{
						alertDTO.setDateToReleaseAlert("");
					}
					if(element[3] != null &&
							!Util.isBlankOrNull(element[3].toString())){
						discontinueDate = Util.getDateAsString( (Date)element[3],"MM-dd-yyyy");
						alertDTO.setDateToDiscontinueAlert(discontinueDate);
					}else{
						alertDTO.setDateToDiscontinueAlert("");
					}
					alertList.add(alertDTO);
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Alert Message Retrived from database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return alertList;
	}
	
/*	public List showRecentlyLockedAccounts() throws Exception {
		final String MY_NAME = ME + "showRecentlyLockedAccounts: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = null;
		List recentlyLockedAccounts = null ;
		
		BcwtManualAlert manualAlert = null;
		List alertList = null;
		BcwtAlertDTO alertDTO = null;
		String releaseDate = null;
		String discontinueDate = null;
		try {
			alertList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query ="select bcwtManualalert.alertTitle,bcwtManualalert.alertMessage,"+
						  "bcwtManualalert.releaseDate,bcwtManualalert.discontinueDate "+
						  "from BcwtManualAlert bcwtManualalert "+
			              "where to_date('"+curDate+"','mm/dd/yyyy') >= trunc(bcwtManualalert.releaseDate) and "+
			              "to_date('"+curDate+"','mm/dd/yyyy') <= trunc(bcwtManualalert.discontinueDate)";
			List queryList = session.createQuery(query).list();
			if(queryList != null && !queryList.isEmpty()){
				for(Iterator iter = queryList.iterator();iter.hasNext();){
					alertDTO = new BcwtAlertDTO();
					Object element[] = (Object[]) iter.next();
					if(element[0] != null &&
							!Util.isBlankOrNull(element[0].toString())){
						alertDTO.setAlertTitle(element[0].toString());
					}else{
						alertDTO.setAlertTitle("");
					}
					if(element[1] != null &&
							!Util.isBlankOrNull(element[1].toString())){
						alertDTO.setAlertMessage(element[1].toString());
					}else{
						alertDTO.setAlertMessage("");
					}
					if(element[2] != null &&
							!Util.isBlankOrNull(element[2].toString())){
						releaseDate = Util.getDateAsString( (Date)element[2],"MM-dd-yyyy");
						alertDTO.setDateToReleaseAlert(releaseDate);
					}else{
						alertDTO.setDateToReleaseAlert("");
					}
					if(element[3] != null &&
							!Util.isBlankOrNull(element[3].toString())){
						discontinueDate = Util.getDateAsString( (Date)element[3],"MM-dd-yyyy");
						alertDTO.setDateToDiscontinueAlert(discontinueDate);
					}else{
						alertDTO.setDateToDiscontinueAlert("");
					}
					alertList.add(alertDTO);
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Alert Message Retrived from database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return alertList;
	}
	*/
}
