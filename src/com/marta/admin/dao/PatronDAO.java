/**
 * 
 */
package com.marta.admin.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtPatronDisplayDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.dto.BcwtStateDTO;
import com.marta.admin.dto.NegativeListCardsDTO;
import com.marta.admin.dto.PatronCountDTO;
import com.marta.admin.forms.DisplayPatronForm;
import com.marta.admin.forms.NegativeListCardForm;
import com.marta.admin.forms.SignupForm;
import com.marta.admin.forms.UserForm;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtPatronPaymentCards;
import com.marta.admin.hibernate.BcwtPatronType;
import com.marta.admin.hibernate.BcwtPwdVerifiy;
import com.marta.admin.hibernate.BcwtSecretQuestions;
import com.marta.admin.hibernate.BcwtUserRolePermission;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.SimpleProtector;
import com.marta.admin.utils.Util;

/**
 * @author Subamathi
 * 
 */
public class PatronDAO extends MartaBaseDAO {
	final String ME = "PatronDAO: ";
	Date sysDate = new Date();
	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	public List getState() throws Exception {
		final String MY_NAME = ME + "getState: ";
		BcwtsLogger.debug(MY_NAME);
		List stateList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select stateid,statename from BcwtState order by statename";
			List stateListToIterate = session.createQuery(query).list();
			for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				stateList.add(new LabelValueBean(String.valueOf(element[1]),
						String.valueOf(element[0])));
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got State list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return stateList;
	}

	public List getStatus() throws Exception {
		final String MY_NAME = ME + "getStatus: ";
		BcwtsLogger.debug(MY_NAME);
		List statusList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select orderstatusid,orderstatusname from BcwtOrderStatus order by orderstatusname";
			List stateListToIterate = session.createQuery(query).list();
			for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				statusList.add(new LabelValueBean(String.valueOf(element[1]),
						String.valueOf(element[0])));
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got Status list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return statusList;
	}

	public List getSecretQuestions(String userName) throws Exception {
		final String MY_NAME = ME + "getSecretQuestions: ";
		BcwtsLogger.debug(MY_NAME);
		List stateList = null;
		Session session = null;
		Transaction transaction = null;
		String query = "";
		try {
			session = getSession();
			transaction = session.beginTransaction();
			if (null != userName && !userName.trim().equals("")) {
				query = "select question.secretquestionid,question.secretquestion,pwdverify.answer,patron.patronid "
						+ "from BcwtSecretQuestions question,BcwtPatron patron,BcwtPwdVerifiy pwdverify"
						+ " where patron.patronid = pwdverify.bcwtpatron.patronid and  "
						+ "question.secretquestionid = pwdverify.bcwtsecretquestions.secretquestionid "
						+ "and patron.emailid = '" + userName + "'";
			} else {
				query = "select secretquestionid,secretquestion from BcwtSecretQuestions";
			}
			stateList = session.createQuery(query).list();
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got Secret Questions from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return stateList;
	}

	public boolean addOrUpdatePatron(BcwtPatron bcwtPatron) throws Exception {
		final String MY_NAME = ME + "addPatron: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();

			if (bcwtPatron.getPatronid() == null) {
				BcwtPatron bcwtPatronObj = (BcwtPatron) session.createQuery(
						"from BcwtPatron where emailid= '"
								+ bcwtPatron.getEmailid() + "'").uniqueResult();
				if (null != bcwtPatronObj) {
					bcwtPatron.setPatronid(bcwtPatronObj.getPatronid());
					session.clear();
				}
			}
			session.saveOrUpdate(bcwtPatron);
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Patron saved to database ");
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

	public boolean addPatronSecretAnswer(BcwtPwdVerifiy bcwtPwdVerifiy)
			throws Exception {
		final String MY_NAME = ME + "addPatronSecretAnswer: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(bcwtPwdVerifiy);
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME
					+ "Patron secreat answer saved to database ");
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

	public boolean updatePatronPassword(String password, String patronId,
			String isAutoGenerated, BcwtPwdVerifiy bcwtPwdVerifiy)
			throws Exception {
		final String MY_NAME = ME + " updatePatronPassword: ";
		BcwtsLogger.debug(MY_NAME + " updating patron password ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();

			BcwtPatron bcwtPatron = (BcwtPatron) session.load(BcwtPatron.class,
					new Long(patronId));

			if (bcwtPatron != null) {
				bcwtPatron.setPatronpassword(password);
				bcwtPatron.setIsautogenerated(isAutoGenerated);
				session.update(bcwtPatron);
				transaction.commit();
				session.flush();
				if (null != bcwtPwdVerifiy
						&& null != bcwtPwdVerifiy.getBcwtsecretquestions()) {
					session = getSession();
					transaction = session.beginTransaction();
					bcwtPwdVerifiy.setBcwtpatron(bcwtPatron);
					session.save(bcwtPwdVerifiy);
				}
			}
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Patron password updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	public boolean updatePatron(SignupForm signUpForm, Long patronId,
			boolean updatePassword, boolean isAnswerUpdated) throws Exception {
		final String MY_NAME = ME + " updatePatron: ";

		BcwtsLogger.debug(MY_NAME + " updating patron details ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		Integer secretQuestionId = new Integer(0);
		boolean isAdd = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			BcwtPatron bcwtPatron = (BcwtPatron) session.load(BcwtPatron.class,
					patronId);
			bcwtPatron.setFirstname(signUpForm.getFirstName().trim());
			bcwtPatron.setLastname(signUpForm.getLastName().trim());
			bcwtPatron.setMiddlename(signUpForm.getMiddleName().trim());
			if (!Util.isBlankOrNull(signUpForm.getPhoneNumber())) {
				bcwtPatron.setPhonenumber(signUpForm.getPhoneNumber().trim());
			} else {
				bcwtPatron.setPhonenumber("");
			}

			if (updatePassword
					&& !Util.isBlankOrNull(signUpForm.getNewPassword())) {
				bcwtPatron.setPatronpassword(signUpForm.getNewPassword());
			}
			bcwtPatron.setEmailid(signUpForm.getEmail().trim());
			session.update(bcwtPatron);

			if (signUpForm.isSignup()) {
				BcwtSecretQuestions bcwtsecretquestions = new BcwtSecretQuestions();
				bcwtsecretquestions.setSecretquestionid(new Long(signUpForm
						.getSecretQuestion()));

				BcwtPwdVerifiy bcwtPwdVerifiy = (BcwtPwdVerifiy) session
						.createQuery(
								"from BcwtPwdVerifiy pwd where pwd.bcwtpatron.patronid = "
										+ patronId).uniqueResult();
				if (null == bcwtPwdVerifiy) {
					bcwtPwdVerifiy = new BcwtPwdVerifiy();
					bcwtPwdVerifiy.setBcwtpatron(bcwtPatron);
					isAdd = true;
				}
				bcwtPwdVerifiy.setBcwtsecretquestions(bcwtsecretquestions);
				if (isAnswerUpdated) {
					bcwtPwdVerifiy.setAnswer(signUpForm.getSecretAnswer()
							.trim());
				}
				session.saveOrUpdate(bcwtPwdVerifiy);
			}
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Patron password updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	public boolean checkUserEmail(String emailId, int patronId)
			throws Exception {
		final String MY_NAME = ME + "checkUserEmail: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isAvailable = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPatron where activestatus ='"
					+ Constants.ACTIVE_STATUS + "' and lower(emailid) = '"
					+ emailId.trim().toLowerCase() + "'";
			if (patronId > 0) {
				query = query + " and patronId != " + patronId;
			}
			List patronListToIterate = session.createQuery(query).list();
			if (patronListToIterate.size() > 0) {
				isAvailable = true;
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "is email already exists : "
					+ isAvailable);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isAvailable;
	}

	public BcwtPatronDTO getPatronDetails(String emailid) throws Exception {

		final String MY_NAME = ME + "getPatronDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = new BcwtPatron();
		BcwtPatronDTO bcwtPatronDTO = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		Date lastLoginTime = null;
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			bcwtPatron = (BcwtPatron) session.createQuery(
					"from BcwtPatron bcwtPatron where upper(bcwtPatron.emailid) = '"
							+ emailid.toUpperCase() + "'").uniqueResult();

			if (null != bcwtPatron) {

				String checkPatronTypeId = bcwtPatron.getBcwtpatrontype()
						.getPatrontypeid().toString();

				if (checkPatronTypeId.equalsIgnoreCase(Constants.MARTA_ADMIN)
						|| checkPatronTypeId
								.equalsIgnoreCase(Constants.MARTA_SUPREME_ADMIN)
						|| checkPatronTypeId
								.equalsIgnoreCase(Constants.MARTA_SUPER_ADMIN)
						|| checkPatronTypeId
								.equalsIgnoreCase(Constants.MARTA_READONLY)
						|| checkPatronTypeId
								.equalsIgnoreCase(Constants.MARTA_IT_ADMIN)) {

					bcwtPatronDTO = new BcwtPatronDTO();

					BeanUtils.copyProperties(bcwtPatronDTO, bcwtPatron);

					if (null != bcwtPatron.getLastlogin()
							&& !Util.isBlankOrNull(bcwtPatron.getLastlogin()
									.toString())) {
						lastLoginTime = (Date) bcwtPatron.getLastlogin();
						bcwtPatronDTO.setLastlogintime((formatter
								.format(lastLoginTime)));
					}
					if (!Util.isBlankOrNull(bcwtPatron.getBcwtpatrontype()
							.getPatrontypeid().toString())) {
						bcwtPatronDTO.setBcwtusertypeid(bcwtPatron
								.getBcwtpatrontype().getPatrontypeid());
						bcwtPatronDTO.setRole(bcwtPatron.getBcwtpatrontype()
								.getPatrontypeid().toString());
					}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron by email ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return bcwtPatronDTO;
	}

	public BcwtPatronDTO getPatronDetailsById(Long patronId) throws Exception {

		final String MY_NAME = ME + "getPatronDetailsById: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = new BcwtPatron();
		BcwtPatronDTO bcwtPatronDTO = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		Date lastLoginTime = null;
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			bcwtPatron = (BcwtPatron) session.createQuery(
					"from BcwtPatron where patronid=" + patronId)
					.uniqueResult();
			bcwtPatronDTO = new BcwtPatronDTO();
			BeanUtils.copyProperties(bcwtPatronDTO, bcwtPatron);
			if (null != bcwtPatron.getLastlogin()
					&& !Util
							.isBlankOrNull(bcwtPatron.getLastlogin().toString())) {
				lastLoginTime = (Date) bcwtPatron.getLastlogin();
				bcwtPatronDTO
						.setLastlogintime((formatter.format(lastLoginTime)));
			}
			

			if (!Util.isBlankOrNull(bcwtPatron.getBcwtpatrontype()
					.getPatrontypeid().toString())) {
				bcwtPatronDTO.setBcwtusertypeid(bcwtPatron.getBcwtpatrontype()
						.getPatrontypeid());
				bcwtPatronDTO.setRole(bcwtPatron.getBcwtpatrontype()
						.getPatrontypeid().toString());
			}
			if(null != bcwtPatron.getPhonenumber()){
				bcwtPatronDTO.setPhonenumber( bcwtPatron.getPhonenumber());
				
			}

			bcwtPatronDTO.setBcwtusertypeid(bcwtPatron.getBcwtpatrontype()
					.getPatrontypeid());
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron by Id");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return bcwtPatronDTO;
	}

	public HashMap getStateInfo() throws Exception {
		final String MY_NAME = ME + "getStateInfo: ";
		BcwtsLogger.debug(MY_NAME);
		HashMap stateMap = new HashMap();
		Session session = null;
		Transaction transaction = null;
		BcwtStateDTO bcwtStateDTO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select stateid,statename,statecode from BcwtState order by statename";
			List stateListToIterate = session.createQuery(query).list();
			for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				bcwtStateDTO = new BcwtStateDTO();
				bcwtStateDTO.setStatecode(element[2].toString());
				bcwtStateDTO.setStateid(Long.valueOf(element[0].toString()));
				bcwtStateDTO.setStatename(element[1].toString());
				stateMap.put(Long.valueOf(element[0].toString()), bcwtStateDTO);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got State list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return stateMap;
	}

	Set hierachicalId = new HashSet();

	public String fetchHierarchicalId(Long patronId, int usertypeId)
			throws Exception {
		final String MY_NAME = ME + "fetchHierarchicalId: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		String hierachicalID = "";
		String topApproachQuery = "";
		String bottompApproachQuery = "";
		try {
			session = getSession();
			transaction = session.beginTransaction();
			topApproachQuery = "select p.patronid from BcwtPatron p,BcwtPatron p1 where p1.patronid = p.parentpatronid and p1.bcwtpatrontype.patrontypeid =21 and p.parentpatronid=";

			bottompApproachQuery = "select p.parentpatronid from BcwtPatron p,BcwtPatron p1 where p1.patronid = p.parentpatronid and p1.bcwtpatrontype.patrontypeid =21 and p.patronid=";

			if (!topApproachQuery.equals("")) {
				goRecursive(session, patronId, topApproachQuery);
			}
			if (!bottompApproachQuery.equals("")) {
				goRecursiveBottom(session, patronId, bottompApproachQuery);
			}
			for (int i = 0; i < hierachicalId.toArray().length; i++) {
				Long topPatronId = new Long(Long.parseLong(hierachicalId
						.toArray()[i].toString()));
				goRecursive(session, topPatronId, topApproachQuery);
			}
			for (int i = 0; i < hierachicalId.toArray().length; i++) {
				hierachicalID = hierachicalID + hierachicalId.toArray()[i]
						+ ",";
			}
			if (null != hierachicalID && !hierachicalID.equals("")) {
				hierachicalID = hierachicalID.substring(0, hierachicalID
						.lastIndexOf(','));
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got State list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return hierachicalID;
	}

	private void goRecursive(Session session, Long patronId,
			String topApproachQuery) throws Exception {

		List topApproachListToIterate = session.createQuery(
				topApproachQuery + patronId).list();
		for (Iterator iter = topApproachListToIterate.iterator(); iter
				.hasNext();) {
			Object element = (Object) iter.next();
			if (null != element && !element.toString().equals("")
					&& !element.toString().equals("0")) {
				hierachicalId.add(element);
				goRecursive(session, Long.valueOf(element.toString()),
						topApproachQuery);
			}
		}

	}

	private void goRecursiveBottom(Session session, Long patronId,
			String bottomApproachQuery) throws Exception {
		List bottomApproachListToIterate = session.createQuery(
				bottomApproachQuery + patronId).list();
		for (Iterator iter = bottomApproachListToIterate.iterator(); iter
				.hasNext();) {
			Object element = (Object) iter.next();
			if (null != element && !element.toString().equals("")
					&& !element.toString().equals("0")) {
				hierachicalId.add(element);
				goRecursiveBottom(session, Long.valueOf(element.toString()),
						bottomApproachQuery);
			}
		}
	}

	/**
	 * This method will delete user
	 * 
	 * @param addressId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteUser(String patronId) throws Exception {
		final String MY_NAME = ME + " deleteUser: ";
		BcwtsLogger.debug(MY_NAME + " deleting user details ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			BcwtPatron bcwtPatron = (BcwtPatron) session.load(BcwtPatron.class,
					Long.valueOf(patronId));
			bcwtPatron.setActivestatus(Constants.IN_ACTIVE_STATUS);
			session.update(bcwtPatron);
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME
					+ "Patron active status is updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ Util.getFormattedStackTrace(e));
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	public boolean updateAdminPatron(UserForm signUpForm, String patronId)
			throws Exception {
		final String MY_NAME = ME + " updateAdminPatron: ";

		BcwtsLogger.debug(MY_NAME + " updating patron details ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			BcwtPatron bcwtPatron = (BcwtPatron) session.load(BcwtPatron.class,
					Long.valueOf(patronId));
			bcwtPatron.setFirstname(signUpForm.getEditFirstName().trim());
			bcwtPatron.setLastname(signUpForm.getEditLastName().trim());
			bcwtPatron.setMiddlename(signUpForm.getEditMiddleName().trim());
			String phoneNum = signUpForm.getPhoneNumberOneEdit()
					+ signUpForm.getPhoneNumberTwoEdit()
					+ signUpForm.getPhoneNumberThreeEdit();
			System.out.println("phonenum" + phoneNum);
			bcwtPatron.setPhonenumber(phoneNum.trim());
			bcwtPatron.setEmailid(signUpForm.getEditEmail().trim());
			BcwtPatronType bcwtpatrontype = new BcwtPatronType();
			bcwtpatrontype.setPatrontypeid(Long.valueOf(signUpForm
					.getEditPatronType()));
			bcwtPatron.setBcwtpatrontype(bcwtpatrontype);
			session.update(bcwtPatron);
			transaction.commit();
			session.flush();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Patron password updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ Util.getFormattedStackTrace(e));
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	/**
	 * to get details for welcome page
	 * 
	 * @param userids
	 * @return List
	 * @throws Exception
	 */

	/**
	 * to check for user association
	 * 
	 * @param userId
	 * @param patronId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean checkUserAssociation(String userId, int patronId)
			throws Exception {
		final String MY_NAME = ME + "checkUserAssociation: ";
		BcwtsLogger.debug(MY_NAME + "checking User Association");
		Session session = null;
		Transaction transaction = null;
		boolean isUserAssociated = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPatron bcwtPvents where bcwtPvents.patronid in (:patronId)";
			BcwtPatron bcwtpatron = (BcwtPatron) session.createQuery(query)
					.setParameter("patronId", userId).uniqueResult();
			if (null != bcwtpatron
					&& bcwtpatron.getParentpatronid().intValue() == patronId) {
				isUserAssociated = true;
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Finished checking user association");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUserAssociated;
	}

	/**
	 * to check for user security details
	 * 
	 * @param userId
	 * @param patronId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean getSecurityQuestionDetails(Long patronId) throws Exception {
		final String MY_NAME = ME + "getSecurityQuestionDetails: ";
		BcwtsLogger.debug(MY_NAME + "checking secutity user details");
		Session session = null;
		Transaction transaction = null;
		boolean isUserSecured = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtPwdVerifiy bcwtPwdVerifiy where bcwtPwdVerifiy.bcwtpatron.patronid ="
					+ patronId;
			BcwtPwdVerifiy bcwtPwdVerifiy = (BcwtPwdVerifiy) session
					.createQuery(query).uniqueResult();
			if (null != bcwtPwdVerifiy
					&& null != bcwtPwdVerifiy.getPwdverifyid()) {
				isUserSecured = true;
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Finished checking user secutity");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUserSecured;
	}

	/**
	 * to get user role permission
	 * 
	 * @param userId
	 * @param patronId
	 * @return List
	 * @throws Exception
	 */
	public List getRolePermission() throws Exception {
		final String MY_NAME = ME + "getStateInfo: ";
		BcwtsLogger.debug(MY_NAME);
		List rolePermissionList = null;
		Session session = null;
		Transaction transaction = null;
		BcwtUserRolePermission bcwtUserRolePermissionDTO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtUserRolePermission ";
			List rolePermissionListToIterate = session.createQuery(query)
					.list();
			rolePermissionList = new ArrayList();
			for (Iterator iter = rolePermissionListToIterate.iterator(); iter
					.hasNext();) {
				BcwtUserRolePermission element = (BcwtUserRolePermission) iter
						.next();
				bcwtUserRolePermissionDTO = new BcwtUserRolePermission();
				BeanUtils.copyProperties(bcwtUserRolePermissionDTO, element);
				rolePermissionList.add(bcwtUserRolePermissionDTO);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got State list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return rolePermissionList;
	}

	public List getPatronTypeName() throws Exception {
		final String MY_NAME = ME + "getPatronTypeName: ";
		BcwtsLogger.debug(MY_NAME);
		List patronList = null;
		Session session = null;
		Transaction transaction = null;
		String query = "";
		try {
			patronList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			// query="select patrontypeid,patrontypename from BcwtPatronType order by patrontypename"

			query = "select p.patrontypeid ,p.patrontypename from BcwtPatronType p"
					+ " where p.patrontypeid='"
					+ Constants.MARTA_SUPER_ADMIN
					+ "' "
					+ " or p.patrontypeid='"
					+ Constants.MARTA_ADMIN
					+ "' "
					+ " or p.patrontypeid='"
					+ Constants.MARTA_READONLY
					+ "'";

			List stateListToIterate = session.createQuery(query).list();
			for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				patronList.add(new LabelValueBean(String.valueOf(element[1]),
						String.valueOf(element[0])));
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got Patron list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return patronList;

	}

	public ArrayList getPatronDisplayDetails() throws Exception {

		final String MY_NAME = ME + "getPatronDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = new BcwtPatron();

		ArrayList displayList;
		// ArrayList displayList
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			displayList = (ArrayList) session.createQuery(
					"from BcwtPatron bcwtPatron order by lastlogin desc")
					.list();

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron by email ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return displayList;
	}
	
	public List getPatronCount() throws Exception {

		final String MY_NAME = ME + "getPatronCount: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		int isCount = 0;
		int gbsCount = 0;
		int adminCount= 0;
		PatronCountDTO patronCountDTOIS = new PatronCountDTO();
		PatronCountDTO patronCountDTOGBS = new PatronCountDTO();
		PatronCountDTO patronCountDTOADMIN = new PatronCountDTO();
		ArrayList displayList;
		 ArrayList countList = new ArrayList();;
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			String query = "select bcwtpatrontype.patrontypename, bcwtpatron.bcwtpatrontype.patrontypeid  " +
					"from BcwtPatron bcwtpatron, BcwtPatronType bcwtpatrontype where bcwtpatron.bcwtpatrontype.patrontypeid = bcwtpatrontype.patrontypeid and bcwtpatron.activestatus ='1'";
			
			
			displayList = (ArrayList) session.createQuery(query).list();
       for(Iterator iter = displayList.iterator();iter.hasNext();){
    	   Object[] element = (Object[]) iter.next();
    	  
    	   if(null !=element[1]){
    		   if(element[1].toString().equals("1"))   
    			   isCount++; 
    		   if(element[1].toString().equals("21") || element[1].toString().equals("22") || element[1].toString().equals("23"))   
        		   gbsCount++;
    		   if(element[1].toString().equals("50") ||element[1].toString().equals("51") || element[1].toString().equals("52") || element[1].toString().equals("53") || element[1].toString().equals("54"))   
        		  adminCount++;
    		 }
    	 }
       patronCountDTOIS.setPatronTypename("IS");
       patronCountDTOIS.setTypeCount(isCount);
       countList.add(patronCountDTOIS);  
       
       patronCountDTOGBS.setPatronTypename("GBS");
       patronCountDTOGBS.setTypeCount(gbsCount);
       countList.add(patronCountDTOGBS);
       
       patronCountDTOADMIN.setPatronTypename("MARTA Admins");
       patronCountDTOADMIN.setTypeCount(adminCount);
       countList.add(patronCountDTOADMIN);
			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron Count ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return countList;
	}
	
	
	
	

	public boolean updatePatronActiveStatus(
			BcwtPatronDisplayDTO bcwtPatronDisplayDTO) throws Exception {

		final String MY_NAME = " updatePatronActiveStatus: ";
		BcwtsLogger.debug(MY_NAME + " updatePatronActiveStatus ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;

		BcwtPatron bcwtPatron = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "from BcwtPatron bcwtPatron where "
					+ "bcwtPatron.patronid = '"
					+ bcwtPatronDisplayDTO.getPatronId() + "'";

			bcwtPatron = (BcwtPatron) session.createQuery(query).uniqueResult();
			transaction.commit();
			session.flush();
			session.close();
			if (bcwtPatron != null) {
				bcwtPatron.setActivestatus(String.valueOf(bcwtPatronDisplayDTO
						.getActiveStatus()));
			}
			session = getSession();
			transaction = session.beginTransaction();
			session.update(bcwtPatron);
			isUpdated = true;
			transaction.commit();
			session.flush();
			session.close();

			BcwtsLogger.info(MY_NAME
					+ "Application settings details updated to database ");
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details :"
					+ Util.getFormattedStackTrace(e));
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	public ArrayList getSearchPatronDetails(DisplayPatronForm displayPatronForm)
			throws Exception {

		final String MY_NAME = ME + "getPatronDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		BcwtPatron bcwtPatron = new BcwtPatron();
		String query = "";

		ArrayList displayList;
		// ArrayList displayList
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			query = "from BcwtPatron bcwtPatron where 1 = 1";

			if (!Util.isBlankOrNull(displayPatronForm.getFirstName())) {
				query = query + " and UPPER(bcwtPatron.firstname) like '%"
						+ displayPatronForm.getFirstName().toUpperCase() + "%'";
			}
			if (!Util.isBlankOrNull(displayPatronForm.getLastName())) {
				query = query + " and UPPER(bcwtPatron.lastname) like '%"
						+ displayPatronForm.getLastName().toUpperCase() + "%'";
			}

			if (!Util.isBlankOrNull(displayPatronForm.getEmailId())) {
				query = query + " and UPPER(bcwtPatron.emailid) like '%"
						+ displayPatronForm.getEmailId().toUpperCase() + "%'";
			}

			displayList = (ArrayList) session.createQuery(query).list();

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got Patron Search results ");
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;

		} finally {
			closeSession(session, transaction);

		}
		return displayList;
	}

}
