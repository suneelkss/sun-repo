package com.marta.admin.dao;

import java.util.ArrayList;
//import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dao.MartaBaseDAO;
//import com.marta.admin.dto.BcwtAlertDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtUnlockAccountDTO;
import com.marta.admin.exceptions.MartaException;
//import com.marta.admin.hibernate.BcwtManualAlert;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtPwdVerifiy;
//import com.marta.admin.hibernate.BcwtSecretQuestions;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class UnlockAccountDAO extends MartaBaseDAO {
	
	final String ME = "UnlockAccountDAO: ";
	
	/**
	 * DB call to get admin details
	 * @return
	 * @throws Exception
	 */
	public List getAdminList(String roleId) throws Exception {
		final String MY_NAME = ME + "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		List adminList = null;
		Transaction transaction = null;
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "";
			if(Util.equalsIgnoreCase(roleId,Constants.MARTA_SUPREME_ADMIN)||
					Util.equalsIgnoreCase(roleId, Constants.MARTA_IT_ADMIN)){
				query = "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname, " +
						"bcwtpatron.emailid,bcwtpatron.phonenumber " +
						"from BcwtPatron bcwtpatron where bcwtpatron.lockstatus = '"+Constants.YES+"' and " +
						"(bcwtpatron.bcwtpatrontype.patrontypeid = " +
						"'"+Constants.MARTA_READONLY+"' or bcwtpatron.bcwtpatrontype.patrontypeid = " +
								"'"+Constants.MARTA_ADMIN+"' or bcwtpatron.bcwtpatrontype.patrontypeid = " +
										"'"+Constants.MARTA_SUPER_ADMIN+"')  ";
			}else{
				query= "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname, " +
						"bcwtpatron.emailid,bcwtpatron.phonenumber from BcwtPatron bcwtpatron " +
						" where bcwtpatron.lockstatus = '"+Constants.YES+"' and " +
					    "(bcwtpatron.bcwtpatrontype.patrontypeid " +
						" = '"+Constants.MARTA_READONLY+"' or bcwtpatron.bcwtpatrontype.patrontypeid " +
						" = '"+Constants.MARTA_ADMIN+"') ";
			}
			
			/*String query = "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname,"
					+ " bcwtpatron.emailid,bcwtpatron.phonenumber "
					+ " from BcwtPatron bcwtpatron "
					+ " where bcwtpatron.lockstatus = '"+Constants.YES+"'";*/
			
			List orderListToIterate = session.createQuery(query).list();
			adminList = new ArrayList();
			for (Iterator iter = orderListToIterate.iterator(); iter.hasNext();) {
				Object[] bcwtAdmin = (Object[]) iter.next();
				bcwtUnlockAccountDTO = new BcwtUnlockAccountDTO();
				if (bcwtAdmin != null) {
					if (null != bcwtAdmin[0]) {
						bcwtUnlockAccountDTO.setPatronId(bcwtAdmin[0]
								.toString());
					}
					if (null != bcwtAdmin[1]) {
						bcwtUnlockAccountDTO.setAdminFname(bcwtAdmin[1]
								.toString());
					}
					if (null != bcwtAdmin[2]) {
						bcwtUnlockAccountDTO.setAdminLname(bcwtAdmin[2]
								.toString());
					}
					if (null != bcwtAdmin[3]) {
						bcwtUnlockAccountDTO.setAdminEmail(bcwtAdmin[3]
								.toString());
					}
					if (null != bcwtAdmin[4]) {
						bcwtUnlockAccountDTO.setAdminPhoneNumber(bcwtAdmin[4]
								.toString());
					}
					adminList.add(bcwtUnlockAccountDTO);
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
		return adminList;
	}
	
	
/**
 * DB call for getting locked user details
 * @param patronId
 * @return
 * @throws Exception
 */
	public BcwtUnlockAccountDTO populateLockedUserDetails(String patronId) throws Exception {

		final String MY_NAME = ME + "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = null;
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = new BcwtUnlockAccountDTO();
		BcwtPwdVerifiy bcwtPwdVerifiy = null;
		//BcwtSecretQuestions bcwtSecretQuestions = null;
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			String query = " from BcwtPatron where patronid = '" + patronId + "'";
			  
			bcwtPatron = (BcwtPatron) session.createQuery(
						query).uniqueResult();

			if (null != bcwtPatron) {
				
				if(null != bcwtPatron.getFirstname()){
					bcwtUnlockAccountDTO.setAdminFname(bcwtPatron.getFirstname());
				}
				if(null != bcwtPatron.getLastname()){
					bcwtUnlockAccountDTO.setAdminLname(bcwtPatron.getLastname());
				}
				if(null != bcwtPatron.getEmailid()){
					bcwtUnlockAccountDTO.setAdminEmail(bcwtPatron.getEmailid());
				}
				if(null != bcwtPatron.getPhonenumber()){
					bcwtUnlockAccountDTO.setAdminPhoneNumber(bcwtPatron.getPhonenumber());
				}
				if(null != bcwtPatron.getDateofbirth()){
					bcwtUnlockAccountDTO.setDateOfBirth(Util.getFormattedDate(bcwtPatron.getDateofbirth()));
				}
				if(null != bcwtPatron.getPatronid()){
					bcwtUnlockAccountDTO.setPatronId(bcwtPatron.getPatronid().toString());
				}
				if(null != bcwtPatron.getPatronpassword()){
					bcwtUnlockAccountDTO.setUnlockPassword(bcwtPatron.getPatronpassword());
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			
			if(bcwtUnlockAccountDTO != null){
				session = getSession();
				transaction = session.beginTransaction();
				String query1 = " from BcwtPwdVerifiy bpv where bpv.bcwtpatron.patronid = '" + patronId + "'";
				bcwtPwdVerifiy = (BcwtPwdVerifiy) session.createQuery(
						query1).uniqueResult();
				if(bcwtPwdVerifiy!=null){
					bcwtUnlockAccountDTO.setSecurityQuestion(bcwtPwdVerifiy.getBcwtsecretquestions().getSecretquestion());
					bcwtUnlockAccountDTO.setSecurityAnswer(bcwtPwdVerifiy.getAnswer());
				}
				transaction.commit();
				session.flush();
			}
			
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return bcwtUnlockAccountDTO;
	}
	
	
	/**
	 * DB call to search for locked users
	 * @param bcwtUnlockAccountDTO
	 * @param patronId
	 * @return
	 * @throws Exception
	 */
	public List searchLockedAdmins(BcwtUnlockAccountDTO bcwtUnlockAccountDTO,String patronId,
			String roleId) throws Exception {

		final String MY_NAME = ME + "searchLockedAdmins: ";
		BcwtsLogger.debug(MY_NAME + "SEARCH admin details started");
		Session session = null;
		Transaction transaction = null;
		String fName = "";
		String lName = "";
		String eMail = "";
		String phoneNumber = "";
		List lockedAdminList = new ArrayList();
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			if (!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminFname())) {
				fName = bcwtUnlockAccountDTO.getAdminFname();
			}
			if (!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminLname())) {
				lName = bcwtUnlockAccountDTO.getAdminLname();
			}
			if (!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminEmail())) {
				eMail = bcwtUnlockAccountDTO.getAdminEmail();
			}
			if (!Util.isBlankOrNull(bcwtUnlockAccountDTO.getAdminPhoneNumber())) {
				phoneNumber = bcwtUnlockAccountDTO.getAdminPhoneNumber();
			}
			
			String query = "";
			
			if(Util.equalsIgnoreCase(roleId,Constants.MARTA_SUPREME_ADMIN)||
					Util.equalsIgnoreCase(roleId, Constants.MARTA_IT_ADMIN)){
				query = "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname, " +
						"bcwtpatron.emailid,bcwtpatron.phonenumber " +
						"from BcwtPatron bcwtpatron where bcwtpatron.lockstatus = '"+Constants.YES+"' and " +
						"(bcwtpatron.bcwtpatrontype.patrontypeid = " +
						"'"+Constants.MARTA_READONLY+"' or bcwtpatron.bcwtpatrontype.patrontypeid = " +
								"'"+Constants.MARTA_ADMIN+"' or bcwtpatron.bcwtpatrontype.patrontypeid = " +
										"'"+Constants.MARTA_SUPER_ADMIN+"')  ";
			}else{
				query= "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname, " +
						"bcwtpatron.emailid,bcwtpatron.phonenumber from BcwtPatron bcwtpatron " +
						" where bcwtpatron.lockstatus = '"+Constants.YES+"' and " +
					    "(bcwtpatron.bcwtpatrontype.patrontypeid " +
						" = '"+Constants.MARTA_READONLY+"' or bcwtpatron.bcwtpatrontype.patrontypeid " +
						" = '"+Constants.MARTA_ADMIN+"') ";
			}
			
			/*String query = "from BcwtPatron bcwtpatron where" +
							" bcwtpatron.lockstatus = '"+Constants.YES+"'";*/
			
			if (null != fName && !fName.equals("")) {
				query = query + " and lower(bcwtpatron.firstname) like '"
				+ fName.trim().toLowerCase() + "%'";
			}
			if (null != lName && !lName.equals("")) {
				query = query + " and lower(bcwtpatron.lastname) like '"
				+ lName.trim().toLowerCase() + "%'";
			}
			if (null != eMail && !eMail.equals("")) {
				query = query + " and lower(bcwtpatron.emailid) like '"
				+ eMail.trim().toLowerCase() + "%'";
			}
			if (null != phoneNumber && !phoneNumber.equals("")) {
				query = query + " and lower(bcwtpatron.phonenumber) like '"
				+ phoneNumber.trim().toLowerCase() + "%'";
			}
			List queryList = session.createQuery(query).list();
			
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				Object[] bcwtAdmin = (Object[]) iter.next();
				BcwtUnlockAccountDTO bcwtUnlockAccountDTO1 = new BcwtUnlockAccountDTO();
				if (bcwtAdmin != null) {
					if (null != bcwtAdmin[0]) {
						bcwtUnlockAccountDTO1.setPatronId(bcwtAdmin[0]
								.toString());
					}
					if (null != bcwtAdmin[1]) {
						bcwtUnlockAccountDTO1.setAdminFname(bcwtAdmin[1]
								.toString());
					}
					if (null != bcwtAdmin[2]) {
						bcwtUnlockAccountDTO1.setAdminLname(bcwtAdmin[2]
								.toString());
					}
					if (null != bcwtAdmin[3]) {
						bcwtUnlockAccountDTO1.setAdminEmail(bcwtAdmin[3]
								.toString());
					}
					if (null != bcwtAdmin[4]) {
						bcwtUnlockAccountDTO1.setAdminPhoneNumber(bcwtAdmin[4]
								.toString());
					}
					lockedAdminList.add(bcwtUnlockAccountDTO1);
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
		return lockedAdminList;
	}
	
	
	/**
	 * 
	 * @param bcwtPatronDTO
	 * @return
	 * @throws Exception
	 */
	
	public boolean unlockLockedAdmin(BcwtPatronDTO bcwtPatronDTO) throws Exception {

		final String MY_NAME = ME + "getAdminList: ";
		BcwtsLogger.debug(MY_NAME + "getting admin details started");
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = new BcwtPatron();
		boolean isUnLocked = false;
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPatron bcwtpatron where bcwtpatron.patronid= " + bcwtPatronDTO.getPatronid();
			
			
			bcwtPatron = (BcwtPatron) session.createQuery(query).uniqueResult();
			if(bcwtPatron != null) {
				bcwtPatron.setLockstatus(bcwtPatronDTO.getLockstatus());
			} else {
				throw new MartaException(MY_NAME + "Patron not available");
			}
			session.update(bcwtPatron);
			transaction.commit();
			session.flush();
			isUnLocked = true;
			BcwtsLogger.info(MY_NAME + "retriving into the DB");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUnLocked;
	}
	
//	to display Locked in welcome page starts
	public List displayLockedCalls(String patronTypeId) throws Exception {
		final String MY_NAME = ME + "displayLockedCalls: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List lockedList = null;
		//List queryList=null;
		//BcwtUnlockAccountDTO unlockAccountDTO = null;
		try {
			lockedList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query ="";
			if(Util.equalsIgnoreCase(patronTypeId,Constants.MARTA_SUPREME_ADMIN)||
					Util.equalsIgnoreCase(patronTypeId, Constants.MARTA_IT_ADMIN)){
				query = "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname, " +
						"bcwtpatron.emailid,bcwtpatron.phonenumber " +
						"from BcwtPatron bcwtpatron where bcwtpatron.lockstatus = '"+Constants.YES+"' and " +
						/*"TO_CHAR(bcwtpatron.lastlogin,'MM/DD/YYYY')=(SELECT distinct(TO_CHAR(bcwtpatron.lastlogin,'MM/DD/YYYY')) " +
						"FROM BcwtPatron bcwtpatron where TO_CHAR(bcwtpatron.lastlogin,'MM/DD/YYYY') = " +
						"'"+Util.getCurrentDate()+"') and " +*/
						"(bcwtpatron.bcwtpatrontype.patrontypeid = " +
						"'"+Constants.MARTA_READONLY+"' or bcwtpatron.bcwtpatrontype.patrontypeid = " +
								"'"+Constants.MARTA_ADMIN+"' or bcwtpatron.bcwtpatrontype.patrontypeid = " +
										"'"+Constants.MARTA_SUPER_ADMIN+"') order by bcwtpatron.lastlogin desc ";
			}if(Util.equalsIgnoreCase(patronTypeId,Constants.MARTA_SUPER_ADMIN)){
				query= "select bcwtpatron.patronid,bcwtpatron.firstname,bcwtpatron.lastname, " +
						"bcwtpatron.emailid,bcwtpatron.phonenumber from BcwtPatron bcwtpatron " +
						" where bcwtpatron.lockstatus = '"+Constants.YES+"' and " +
						/*"TO_CHAR(bcwtpatron.lastlogin,'MM/DD/YYYY')=(SELECT distinct(TO_CHAR(bcwtpatron.lastlogin,'MM/DD/YYYY')) " +
						"FROM BcwtPatron bcwtpatron where TO_CHAR(bcwtpatron.lastlogin,'MM/DD/YYYY') = " +
						"'"+Util.getCurrentDate()+"') and " +*/
					    "(bcwtpatron.bcwtpatrontype.patrontypeid " +
						" = '"+Constants.MARTA_READONLY+"' or bcwtpatron.bcwtpatrontype.patrontypeid " +
						" = '"+Constants.MARTA_ADMIN+"') order by bcwtpatron.lastlogin desc";
			}
			List queryList = session.createQuery(query).list();
			if(queryList != null && !queryList.isEmpty()){
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] bcwtAdmin = (Object[]) iter.next();
					BcwtUnlockAccountDTO bcwtUnlockAccountDTO = new BcwtUnlockAccountDTO();
					if (bcwtAdmin != null) {
						if (null != bcwtAdmin[0]) {
							bcwtUnlockAccountDTO.setPatronId(bcwtAdmin[0]
									.toString());
						}
						if (null != bcwtAdmin[1]) {
							bcwtUnlockAccountDTO.setAdminFname(bcwtAdmin[1]
									.toString());
						}
						if (null != bcwtAdmin[2]) {
							bcwtUnlockAccountDTO.setAdminLname(bcwtAdmin[2]
									.toString());
						}
						if (null != bcwtAdmin[3]) {
							bcwtUnlockAccountDTO.setAdminEmail(bcwtAdmin[3]
									.toString());
						}
						if (null != bcwtAdmin[4]) {
							bcwtUnlockAccountDTO.setAdminPhoneNumber(bcwtAdmin[4]
									.toString());
						}
						lockedList.add(bcwtUnlockAccountDTO);
						
						if(lockedList.size() == 5){
							queryList = new ArrayList();
							break;
						}
					}
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Locked Call Retrived from database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return lockedList;
	}
//	to display Locked Calls in welcome page ends
	
	/*public List getRecentlyLockedAccounts() throws Exception {
		final String MY_NAME = ME + "showRecentlyLockedAccounts: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = null;
		List recentlyLockedAccounts = null ;
		try {
			recentlyLockedAccounts = new ArrayList();

			session = getSession();
			transaction = session.beginTransaction();
			String query = "select bcwtpatron.patronid, bcwtpatron.firstname, bcwtpatron.lastname, " +
						"bcwtpatron.emailid, bcwtpatron.phonenumber, bcwtpatron.lastlogin " +
						"from BcwtPatron bcwtpatron where bcwtpatron.lockstatus = '"+Constants.YES+"' " +
								"order by bcwtpatron.lastlogin desc";
						
			List queryList = session.createQuery(query).list();
			int countList = queryList.size();
			
			if(queryList != null && !queryList.isEmpty()){
				for(Iterator it = queryList.iterator(); it.hasNext();){
					
					bcwtUnlockAccountDTO = new BcwtUnlockAccountDTO();
					Object element[] = (Object[])it.next();
					if(element[0] != null && !Util.isBlankOrNull(element[0].toString())){
						bcwtUnlockAccountDTO.setPatronId(element[0].toString());
					}else{
						bcwtUnlockAccountDTO.setPatronId("");
					}
					if(element[1] != null && !Util.isBlankOrNull(element[1].toString())){
						bcwtUnlockAccountDTO.setAdminFname(element[1].toString());
					}else{
						bcwtUnlockAccountDTO.setAdminFname("");
					}
					if(element[2] != null && !Util.isBlankOrNull(element[2].toString())){
						bcwtUnlockAccountDTO.setAdminLname(element[2].toString());
					}else{
						bcwtUnlockAccountDTO.setAdminLname("");
					}
					if(element[3] != null && !Util.isBlankOrNull(element[3].toString())){
						bcwtUnlockAccountDTO.setAdminEmail(element[3].toString());
					}else{
						bcwtUnlockAccountDTO.setAdminEmail("");
					}
					if(element[4] != null && !Util.isBlankOrNull(element[4].toString())){
						bcwtUnlockAccountDTO.setAdminPhoneNumber(element[4].toString());
					}else{
						bcwtUnlockAccountDTO.setAdminPhoneNumber("");
					}
					if(element[5] != null && !Util.isBlankOrNull(element[5].toString())){
						bcwtUnlockAccountDTO.setLastLogin(element[5].toString());
					}else{
						bcwtUnlockAccountDTO.setLastLogin("");
					}
					recentlyLockedAccounts.add(bcwtUnlockAccountDTO);
					
					if(recentlyLockedAccounts.size()==3){
						queryList = new ArrayList();
						break;
					}
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
		return recentlyLockedAccounts;
	}*/
	
	
	
}