package com.marta.admin.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.marta.admin.business.AssociatePartnersBussiness;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.business.CreateManualAlertManagement;
import com.marta.admin.business.LogManagement;
import com.marta.admin.business.ManagePartnerRegistrationBusiness;
import com.marta.admin.business.Patron;
import com.marta.admin.business.PatronManagement;
import com.marta.admin.business.RequestToHotList;
import com.marta.admin.business.UnlockAccount;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.SignupForm;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtPwdVerifiy;
import com.marta.admin.hibernate.BcwtSecretQuestions;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.ErrorHandler;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.SendMail;
import com.marta.admin.utils.Util;
import com.marta.admin.business.AdminSalesPatron;
/**
 * Action class for handling request for Patron Management
 * 
 * @author Subamathi
 */

public class PatronManagementAction extends DispatchAction {
	final String ME = "PatronManagementAction: ";

	String dispatchTo = "";

	String currentPath = "";

	/**
	 * Method to display signup page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward displaySignUpPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "displaySignUpPage: ";
		BcwtsLogger.debug(MY_NAME
				+ "gathering pre populated data for signup page");
		HttpSession session = request.getSession(true);
		String returnValue = "signUp";
		SignupForm signUpForm = (SignupForm) form;
		try {
			// get state,secretquestion,captcha infoR

			PatronManagement patronManagement = new PatronManagement();
			HashMap stateMap = null;
			if (null != session.getAttribute(Constants.STATE_INFO_MAP)) {
				stateMap = (HashMap) session
				.getAttribute(Constants.STATE_INFO_MAP);
			}
			
			List stateList = patronManagement.getStateInformation(stateMap);
			List secretQuestionList = patronManagement.getSecretQuestions("");
			List patronTypeList = patronManagement.getPatronTypeName();
			session.setAttribute(Constants.STATE_LIST, stateList);
			session.setAttribute(Constants.SECRET_QUESTIONS_LIST,
					secretQuestionList);
			session.setAttribute(Constants.PATRON_TYPE_NAME_LIST, patronTypeList);
			request.setAttribute("SignUpForm", signUpForm);

		} catch (MartaException e) {
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while getting state/secretquestion information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}

	/**
	 * Method to add or update patron details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward addOrUpdatePatronDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "addOrUpdatePatronDetails: ";
		BcwtsLogger.debug(MY_NAME + "add or update patron details");
		HttpSession session = request.getSession(true);
		SignupForm signUpForm = (SignupForm) form;
		String returnValue = "signUp";
		String captchaGenerated = "";
		String captchaEntered = "";
		boolean isUpdate = false;
		BcwtPatronDTO patronDTOInSession = null;
		Long patronId = new Long(0);
		BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
		boolean isPasswordCheckMandatory = false;
		String answerFromDB = "";
		String answerDBmask = "";
		PatronManagement patronManagement = new PatronManagement();
		boolean isAnswerUpdated = false;
		try {
			
			signUpForm.setParentpatronid(Constants.ZERO);
			
			//signUpForm.setParentpatronid(Constants.MARTA_SUPER_ADMIN );
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				SignupForm SignupFormDB = new SignupForm();
				SignupFormDB.setEmail(patronDTOInSession.getEmailid());
				SignupFormDB = patronManagement
				.setSecreatQuestionAnswer(SignupFormDB);
				answerFromDB = SignupFormDB.getSecretAnswer();
				patronId = patronDTOInSession.getPatronid();
				isUpdate = true;
				if (null != answerFromDB) {
					for (int index = 0; index < answerFromDB.length(); index++) {
						answerDBmask = answerDBmask + "x";
					}
				}
			}

			//To concat phone numbers
			if(!Util.isBlankOrNull(signUpForm.getPhoneNumberOne()) 
					&& !Util.isBlankOrNull(signUpForm.getPhoneNumberTwo())
					&& !Util.isBlankOrNull(signUpForm.getPhoneNumberThree())){

				signUpForm.setPhoneNumber(signUpForm.getPhoneNumberOne()+
						signUpForm.getPhoneNumberTwo()+
						signUpForm.getPhoneNumberThree());

			}
			Patron patron = new AdminSalesPatron();
			signUpForm.setSignup(true);
			if (!isUpdate) {
				String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath()
				+ "/";
				boolean isPatronSaved = patron.addPatron(signUpForm,basePath);
if (isPatronSaved) {
					
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PATRON_ADD_SUCESS_MESAAGE));
					request.setAttribute(Constants.SUCCESS, "true");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PATRON_ADD_FAILURE_MESAAGE));
				}
				
				
				request.setAttribute("USER_EMAIL", signUpForm.getEmail());
				returnValue = "signupConfirmation";

			} else if (isUpdate && patronId.intValue() > 0) {

				bcwtPatronDTO = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				patronId = bcwtPatronDTO.getPatronid();

				if (!Util.isBlankOrNull(answerDBmask)
						&& !Util.isBlankOrNull(signUpForm.getSecretAnswer())
						&& answerDBmask.equalsIgnoreCase(signUpForm
								.getSecretAnswer())) {
					signUpForm.setSecretAnswer(answerFromDB);
					isAnswerUpdated = false;
				} else {
					isAnswerUpdated = true;
				}
				boolean isPatronUpdated = patron.updatePatron(signUpForm,
						patronId, isPasswordCheckMandatory, isAnswerUpdated);
				if (isPatronUpdated) {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PATRON_UPDATE_SUCESS_MESAAGE));
					request.setAttribute(Constants.SUCCESS, "true");

					bcwtPatronDTO = patronManagement
							.getPatronDetails(signUpForm.getEmail());
					session.setAttribute(Constants.USER_INFO, bcwtPatronDTO);
					request.setAttribute(Constants.IS_SHOW, "false");
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PATRON_UPDATE_FAILURE_MESAAGE));
				}
				
				
				// update session object also
				returnValue = "viewEditIndividualProfile";
			}
		} catch (MartaException e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while adding add/update Patron information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}

	/**
	 * Method to show forget password details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward forgotPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws MartaException {
		final String MY_NAME = ME + "forgotPassword: ";
		BcwtsLogger.debug(MY_NAME);
		HttpSession session = request.getSession(true);
		SignupForm signUpForm = (SignupForm) form;
		String secretAnswer = "";
		String returnValue = "forgotPassword";
		String password = Util.generatePassword(6);
		String patronId = "";
		boolean isUserAccountLocked = false;
		BcwtPatronDTO bcwtPatronDTO = null;
		try {
			String userEmail = signUpForm.getEmail();
			String userAnswerForSecretQuestion = signUpForm.getSecretAnswer();
			PatronManagement patronManagement = new PatronManagement();
			String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
			if (null == userEmail) {
				return mapping.findForward(returnValue);
			}
			if (null != userAnswerForSecretQuestion
					&& !userAnswerForSecretQuestion.equals("")
					&& null != session
					.getAttribute(Constants.SECRET_QUESTIONS_ANSWER)) {
				secretAnswer = (String) session
				.getAttribute(Constants.SECRET_QUESTIONS_ANSWER);
				if (userAnswerForSecretQuestion.equals(secretAnswer)) {
					BcwtsLogger.debug(MY_NAME + "starting sending mail....");
					ConfigurationCache configurationCache = new ConfigurationCache();
					BcwtConfigParamsDTO smtpPathDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get("SMTP_SERVER_PATH");
					BcwtsLogger.debug(MY_NAME + "smtp path : "
							+ smtpPathDTO.getParamvalue());
					BcwtConfigParamsDTO fromDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get("WEBMASTER_MAIL_ID");
					BcwtsLogger.debug(MY_NAME + "From Id : "
							+ fromDTO.getParamvalue());
					BcwtConfigParamsDTO mailportDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get(Constants.MAIL_PORT);
					BcwtConfigParamsDTO mailprotocolDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get(Constants.MAIL_PROTOCOL);
					String content = PropertyReader
					.getValue(Constants.FORGOT_PWD_MAIL_CONTENT);
					if (null != bcwtPatronDTO
							&& null != bcwtPatronDTO.getFirstname()) {
						content = content.replaceAll("\\$USERNAME",
								bcwtPatronDTO.getFirstname().trim());
					} else {
						content = content.replaceAll("\\$USERNAME",
						"Subscriber");
					}
					content = content.replaceAll("\\$PASSWORD", password);
					String imgPath = basePath + Constants.MARTA_LOGO;
					String imageContent = "<img src='" + imgPath + "'/>";
					content = content.replaceAll("\\$LOGOIMAGE", imageContent);
					content = content.replaceAll("\\$PWDCONDITION",
							PropertyReader
							.getValue(Constants.PASSWORD_CONDITION));
					BcwtsLogger.debug(MY_NAME + "content :" + content);
					String subject = PropertyReader
					.getValue(Constants.FORGOT_PWD_MAIL_SUBJECT);

					BcwtsLogger.debug(MY_NAME + "subject :" + subject
							+ "userEmail :" + userEmail);
					try {

						SendMail.sendMail(fromDTO.getParamvalue(), userEmail,
								content, smtpPathDTO.getParamvalue(), subject,
								"", mailportDTO.getParamvalue(),
								mailprotocolDTO.getParamvalue());
						BcwtsLogger.info(MY_NAME + "Mail sent sucess");
					} catch (Exception e) {
						e.printStackTrace();
					}
					// update DB the new Password
					if (null != session.getAttribute(Constants.PATRON_ID)) {
						patronId = (String) session
						.getAttribute(Constants.PATRON_ID);
					}
					boolean isPasswordUpdated = patronManagement
					.updatePatronPassword(password, patronId,
							Constants.YES,null);
					if (isPasswordUpdated) {
						request
						.setAttribute(
								Constants.MESSAGE,
								PropertyReader
								.getValue(Constants.PATRON_FORGET_PASSWORD_SUCESS_MESAAGE));
						request.setAttribute(Constants.SUCCESS, "true");
					} else {
						request
						.setAttribute(
								Constants.MESSAGE,
								PropertyReader
								.getValue(Constants.PATRON_FORGET_PASSWORD_FAILURE_MESAAGE));
					}
					// remove answer from session
					session.removeAttribute(Constants.SECRET_QUESTIONS_ANSWER);
					session.removeAttribute(Constants.SECRET_QUESTION);
					session.removeAttribute(Constants.PATRON_ID);
					session.removeAttribute(Constants.IS_SHOW);
					returnValue = "login";
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PATRON_WRONG_ANSWER_MESAAGE));
				}

			} else {
				// to check the locked status
				try {
					bcwtPatronDTO = patronManagement
					.getPatronDetails(userEmail);
					if (null != bcwtPatronDTO
							&& null != bcwtPatronDTO.getActivestatus()
							&& null != bcwtPatronDTO.getLockstatus()
							&& bcwtPatronDTO.getActivestatus().equals(
									Constants.ACTIVE_STATUS)
									&& bcwtPatronDTO.getLockstatus().equalsIgnoreCase(
											Constants.YES)) {
						isUserAccountLocked = true;						
					} else if (null == bcwtPatronDTO) {
						signUpForm.setEmail("");
						session.setAttribute("InvalidEmail", "Enter Valid Email Id.");
						return mapping.findForward(returnValue);
					}
				} catch (MartaException e) {
					BcwtsLogger
					.error(MY_NAME
							+ " Exception in checking lock status while forgot password :"
							+ e.getMessage());
				}
				if (!isUserAccountLocked) {
					if(Util.equalsIgnoreCase(bcwtPatronDTO.getRole(),Constants.MARTA_IT_ADMIN) || 
							Util.equalsIgnoreCase(bcwtPatronDTO.getRole(),Constants.MARTA_SUPREME_ADMIN)){
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.FORGOT_PASSWORD_ROLE_ERROR_MESSAGE));
						request.setAttribute(Constants.SUCCESS, "false");
						
						return mapping.findForward(returnValue);
					}
					session.removeAttribute("InvalidEmail");
					List secretQuestionListToIterate = patronManagement
					.getSecretQuestions(userEmail);
					if (secretQuestionListToIterate.size() > 0) {
						for (Iterator iter = secretQuestionListToIterate
								.iterator(); iter.hasNext();) {
							Object[] element = (Object[]) iter.next();
							secretAnswer = String.valueOf(element[2]);
							session.setAttribute(Constants.SECRET_QUESTION,
									element[1]);
							session.setAttribute(
									Constants.SECRET_QUESTIONS_ANSWER,
									secretAnswer);
							session.setAttribute(Constants.PATRON_ID, String
									.valueOf(element[3]));
							session.setAttribute(Constants.IS_SHOW, "true");
						}
					} else {
						request
						.setAttribute(
								Constants.MESSAGE,
								PropertyReader
								.getValue("BCWTS_PATRON_EMAIL_INCORRECT_MESSAGE"));
					}
				} else {
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PATRON_LOCKMESSAGES));
					BcwtsLogger.debug("User is in locked state ");
				}
			}

		} catch (MartaException e) {
			e.printStackTrace();
			BcwtsLogger
			.error(MY_NAME
					+ " Exception while fetching and updating forgot password :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}

	/**
	 * Method to get patron details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward getPatronDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "getPatronDetails: ";
		BcwtsLogger.debug(MY_NAME + "get patron details");
		HttpSession session = request.getSession(true);
		SignupForm signUpForm = (SignupForm) form;
		String returnValue = "viewEditIndividualProfile";

		BcwtPatronDTO patronDTOInSession = null;
		PatronManagement patronManagement = new PatronManagement();
		String maskAnswer = "";
		try {
			if (null != session.getAttribute(Constants.USER_INFO)) {
				patronDTOInSession = (BcwtPatronDTO) session
				.getAttribute(Constants.USER_INFO);
				try {
					signUpForm.setFirstName(patronDTOInSession.getFirstname());
					signUpForm
					.setMiddleName(patronDTOInSession.getMiddlename());
					signUpForm.setLastName(patronDTOInSession.getLastname());
					signUpForm.setPhoneNumber(patronDTOInSession
							.getPhonenumber());
					//	To set the phone number
					if(!Util.isBlankOrNull(patronDTOInSession.getPhonenumber()) 
							&& patronDTOInSession.getPhonenumber().length() 
							== Constants.PHONE_NUMBER_LENGTH){

						String phoneNumber = patronDTOInSession.getPhonenumber();

						signUpForm.setPhoneNumberOne(phoneNumber.substring(
								Constants.PHONE_NUMBER_START_INDEX, 
								Constants.FIRST_PHONE_NUMBER_END_INDEX));

						signUpForm.setPhoneNumberTwo(phoneNumber.substring(
								Constants.SECOND_PHONE_NUMBER_START_INDEX, 
								Constants.SECOND_PHONE_NUMBER_END_INDEX));

						signUpForm.setPhoneNumberThree(phoneNumber.substring(
								Constants.THIRD_PHONE_NUMBER_START_INDEX, 
								phoneNumber.length()));
					}

					signUpForm.setEmail(patronDTOInSession.getEmailid());
					signUpForm.setEmailReEntered(patronDTOInSession
							.getEmailid());
					signUpForm.setPhoneNumber(patronDTOInSession
							.getPhonenumber());
					signUpForm = patronManagement
					.setSecreatQuestionAnswer(signUpForm);
					if (!Util.isBlankOrNull(signUpForm.getSecretAnswer())) {
						for (int index = 0; index < signUpForm
						.getSecretAnswer().length(); index++) {
							maskAnswer = maskAnswer + "x";
						}
						signUpForm.setSecretAnswer(maskAnswer);
					}
					List patronTypeList = patronManagement.getPatronTypeName();
					session.setAttribute(Constants.PATRON_TYPE_NAME_LIST, patronTypeList);
					request.setAttribute("SignUpForm", signUpForm);
				} catch (Exception ex) {
					BcwtsLogger
					.error(MY_NAME
							+ " Exception while using bean utils copy properties :"
							+ ex.getMessage());
					returnValue = ErrorHandler.handleError(ex, "", request,
							mapping);
				}
				
				List secretQuestionList = patronManagement
				.getSecretQuestions("");
				session.setAttribute(Constants.SECRET_QUESTIONS_LIST,
						secretQuestionList);
				request.setAttribute("SignUpForm", signUpForm);
				returnValue = "viewEditIndividualProfile";
			}
			
		} catch (MartaException e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while adding add/update Patron information :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_VIEW_EDIT_PROFILE);
		return mapping.findForward(returnValue);

	}
	
	

	/**
	 * Method to change password.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws MartaException {
		final String MY_NAME = ME + "changePassword: ";
		BcwtsLogger.debug(MY_NAME);
		HttpSession session = request.getSession(true);
		SignupForm signUpForm = (SignupForm) form;
		BcwtPatronDTO bcwtPatronDTO = new BcwtPatronDTO();
		String returnValue = "";
		String passwordDb = "";
		String currentPassword = "";
		String newPassword = "";
		String confirmPassword = "";
		String patronId = "0";
		PatronManagement patronManagement = new PatronManagement();
		boolean isChanged = false;
		boolean isUserChangePassword = false;
		BcwtPwdVerifiy bcwtPwdVerifiy = null;
		try {
			if (null != session.getAttribute(Constants.IS_CHANGE_PASSWORD_SHOW)) {
				String userChangePassword = (String) session
				.getAttribute(Constants.IS_CHANGE_PASSWORD_SHOW);
				if (userChangePassword.equals("true")) {
					isUserChangePassword = true;
				}
			}
			bcwtPatronDTO = (BcwtPatronDTO) session
			.getAttribute(Constants.USER_INFO);
			passwordDb = Base64EncodeDecodeUtil.decodeString(bcwtPatronDTO
					.getPatronpassword());
			patronId = String.valueOf(bcwtPatronDTO.getPatronid());
			if (!Util.isBlankOrNull(signUpForm.getNewPassword())) {
				newPassword = signUpForm.getNewPassword();
			}
			if (!Util.isBlankOrNull(signUpForm.getConfirmPassword())) {
				confirmPassword = signUpForm.getConfirmPassword();
			}
			if (!Util.isBlankOrNull(signUpForm.getCurrentPassword())) {
				currentPassword = signUpForm.getCurrentPassword();
			}
			bcwtPwdVerifiy = new BcwtPwdVerifiy();
			BcwtSecretQuestions bcwtSecretQuestions = new BcwtSecretQuestions();
			if (!Util.isBlankOrNull(signUpForm.getSecretQuestion())) {
				bcwtSecretQuestions
				.setSecretquestionid(Long.valueOf(signUpForm
						.getSecretQuestion()));
				bcwtPwdVerifiy.setBcwtsecretquestions(bcwtSecretQuestions);
			}
			if (!Util.isBlankOrNull(signUpForm.getCurrentPassword())) {
				bcwtPwdVerifiy.setAnswer(signUpForm.getSecretAnswer());
			}
			if (!Util.isBlankOrNull(newPassword)
					&& !Util.isBlankOrNull(confirmPassword)
					&& newPassword.equals(confirmPassword)) {
				if (!Util.isBlankOrNull(passwordDb)
						&& !Util.isBlankOrNull(currentPassword)
						&& currentPassword.equals(passwordDb)) {

					isChanged = patronManagement.updatePatronPassword(
							newPassword, patronId, Constants.NO,bcwtPwdVerifiy);
					if (isChanged) {
						bcwtPatronDTO.setPatronpassword(Base64EncodeDecodeUtil
								.encodeString(newPassword));
						session
						.setAttribute(Constants.USER_INFO,
								bcwtPatronDTO);
						if (isUserChangePassword) {
							request.setAttribute(Constants.MESSAGE,
									PropertyReader
									.getValue(Constants.PWD_CHANGED));
							request.setAttribute(Constants.SUCCESS, "true");
							request.setAttribute(Constants.IS_SHOW, "false");
							returnValue = "userChangePassword";
						} else {
							returnValue = "changeSuccess";
						}
						session
						.removeAttribute(Constants.IS_CHANGE_PASSWORD_SHOW);
					} else {
						// changepassword failure
						request.setAttribute(Constants.MESSAGE, PropertyReader
								.getValue(Constants.PWD_NOTCHANGED));
						if (isUserChangePassword) {
							returnValue = "userChangePassword";
						} else {
							returnValue = "changePassword";
						}
					}
				} else {
					// currentpassword doesnot match with dbassword
					request.setAttribute(Constants.MESSAGE, PropertyReader
							.getValue(Constants.PWD_NOTMATCH_DB));
					if (isUserChangePassword) {
						returnValue = "userChangePassword";
					} else {
						returnValue = "changePassword";
					}
				}
			} else {
				// newpassword does not match with confirmpassword
				request.setAttribute(Constants.MESSAGE, PropertyReader
						.getValue(Constants.PWD_NOTMATCH_CONFIRMPWD));
				if (isUserChangePassword) {
					returnValue = "userChangePassword";
				} else {
					returnValue = "changePassword";
				}
			}
			// essential
			session.removeAttribute("isAutoGenerated");
			session.removeAttribute("SHOW_SECRET_QUESTION");
		} catch (MartaException e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception while changing the password  :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}

	/**
	 * Method to display patron welcome page.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	
	public ActionForward patronEventOrderList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws MartaException {
		final String MY_NAME = ME + "getPatronCardList: ";
		BcwtsLogger.debug(MY_NAME);
		String returnValue = "";
		HttpSession session = request.getSession(true);
		String userIds = "";
		List patronEventOrderList = new ArrayList();
		PatronManagement patronManagement = null;
		CreateManualAlertManagement alertManagement = null;
		List alertList = null;
		LogManagement logManagement=null;
		List recentList = null;
		AssociatePartnersBussiness associatePartnersBussiness=null;
		List searchList=null;
		ManagePartnerRegistrationBusiness managePartnerRegistrationBusiness = null;
		List welcomePartnerDetailsList = null;
		UnlockAccount unlockAccount = null;
		List recentlyLockedAccountsList = null ;
		List lockedList=null;
		String currentDate = null; 
		Long patronId = new Long(0);
		String patronTypeId = null;
		BcwtPatronDTO bcwtPatronDTO = null;
		String emailId = null;
		
		RequestToHotList requestToHotList = null;
		List recentlyHotListCardList = null ;
		try {
			if (null != session.getAttribute(Constants.GBS_SUPER_ADMIN_IDS)) {
				userIds = (String) session
				.getAttribute(Constants.GBS_SUPER_ADMIN_IDS);
			}
			if (null != session.getAttribute(Constants.USER_INFO)){
				bcwtPatronDTO = (BcwtPatronDTO) session.getAttribute(Constants.USER_INFO);
			}
			patronId = bcwtPatronDTO.getPatronid();
			patronTypeId =bcwtPatronDTO.getBcwtusertypeid().toString();
			emailId = bcwtPatronDTO.getEmailid();
			/*
			if (!userIds.equals("")) {
				patronManagement = new PatronManagement();
				patronEventOrderList = patronManagement
				.getEventsAndOrderDetails(userIds);
			}
			*/
//			code for Manual alerts display by maheswari starts here
			currentDate = Util.getCurrentDate();
			alertManagement = new CreateManualAlertManagement();
			alertList = alertManagement.displayAlerts(patronId.toString(), currentDate);
			session.setAttribute(Constants.ALERT_MESSAGE_LIST_WELCOMEPAGE, alertList);
			//code for Manual alerts display by maheswari ends here
			
			//code for Recently Locked Accounts List by Jagadeesan Starts Here
			//recentlyLockedAccountsList = new ArrayList();
			//unlockAccount = new UnlockAccount();
			//recentlyLockedAccountsList = unlockAccount.getRecentlyLockedAccounts();
			//session.setAttribute(Constants.LOCKED_USER_DETAILS, recentlyLockedAccountsList);
			//code for Recently Locked Accounts List by Jagadeesan Ends Here
			
//			//code for Logging Calls display by Sowjanya starts here
			//currentDate = Util.getCurrentDate();
			logManagement = new LogManagement();
			recentList = logManagement.displayRecentLogList(patronId.toString(),patronTypeId,emailId);
			session.setAttribute(Constants.RECENT_LOGCALL_LIST_WELCOMEPAGE, recentList);
			//code for Logging Calls display by Sowjanya ends here
			
			//code for recently hotlisting card List by jagadeesan starts here
			requestToHotList = new RequestToHotList();
			recentlyHotListCardList = requestToHotList.getRecentlyHotlistCardList();
			session.setAttribute(Constants.RECENT_HOTLIST_CARDLIST_WELCOMEPAGE, recentlyHotListCardList);
			//code for recently hotlisting card List by jagadeesan ends here
			
			//code for Locked Calls display by Sowjanya starts here
			unlockAccount = new UnlockAccount();
			lockedList = unlockAccount.displayLockedCalls(patronTypeId.toString());
			session.setAttribute(Constants.LOCKED_LIST_WELCOMEPAGE, lockedList);
			//code for Locked Calls display by Sowjanya ends here
			
//			//code for Partner Search Fields display by Subba Reddy starts here
			//currentDate = Util.getCurrentDate();
			associatePartnersBussiness = new AssociatePartnersBussiness();
			searchList = associatePartnersBussiness.displayPartnerSearchList(patronId.toString(),patronTypeId,emailId);
			session.setAttribute(Constants.PARTNER_SEARCH_LIST_WELCOMEPAGE, searchList);
			//code for Partner Search Fields display by Subba Reddy ends here
			session.setAttribute(Constants.BREAD_CRUMB_NAME, "");
			session.setAttribute(Constants.EVENT_ORDER_DETAILS,
					patronEventOrderList);
			returnValue = "welcome";
		} catch (Exception e) {
			e.printStackTrace();
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting patron card details :"
					+ e.getMessage());
			returnValue = ErrorHandler.handleError(e, "", request, mapping);
		}
		return mapping.findForward(returnValue);

	}
	

	/**
	 * Method to change password details.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePasswordDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = null;
		try {

			String cssModify = "";
			session = request.getSession(true);
			if (null != request.getParameter("cssModify")) {
				cssModify = request.getParameter("cssModify");
			} else if (null != session.getAttribute("cssModify")) {
				cssModify = (String) session.getAttribute("cssModify");
			}
			session.setAttribute("cssModify", cssModify);

			if (null != request.getParameter("dispatchTo")) {
				currentPath = request.getParameter("dispatchTo");
			}
			session.setAttribute("currentPath", currentPath);
		} catch (Exception e) {
			throw e;
		}

		return mapping.findForward("changePassword");

	}

	/**
	 * Method to display change password screen.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userChangePassword(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = null;
		try {

			String cssModify = "";
			session = request.getSession(true);
			if (null != request.getParameter("cssModify")) {
				cssModify = request.getParameter("cssModify");
			} else if (null != session.getAttribute("cssModify")) {
				cssModify = (String) session.getAttribute("cssModify");
			}
			session.setAttribute("cssModify", cssModify);

			if (null != request.getParameter("dispatchTo")) {
				currentPath = request.getParameter("dispatchTo");
			}
			session.setAttribute("currentPath", currentPath);
			session.setAttribute(Constants.IS_CHANGE_PASSWORD_SHOW, "true");
		} catch (Exception e) {
			throw e;
		}

		session.setAttribute(Constants.BREAD_CRUMB_NAME,
		">> My Account >> Change Password");
		return mapping.findForward("userChangePassword");

	}

	/**
	 * Method to check the password.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward checkPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws MartaException {
		final String MY_NAME = ME + "checkPassword: ";
		BcwtsLogger.debug(MY_NAME + "check details");
		HttpSession session = request.getSession(true);
		SignupForm signUpForm = (SignupForm) form;
		String returnValue = "password";
		BcwtPatronDTO patronDTOInSession = null;
		String password = "password";

		if (null != session.getAttribute(Constants.USER_INFO)) {
			patronDTOInSession = (BcwtPatronDTO) session
			.getAttribute(Constants.USER_INFO);
			password = Base64EncodeDecodeUtil.decodeString(patronDTOInSession
					.getPatronpassword());
		}
		if (signUpForm.getCurrentPassword().equals(password)) {
			returnValue = "editprofile";
		} else {
			request.setAttribute(Constants.MESSAGE, PropertyReader
					.getValue(Constants.PWD_NOTMATCH_DB));
		}

		return mapping.findForward(returnValue);
	}

	/**
	 * Method to get password.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MartaException
	 */
	public ActionForward getPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws MartaException {
		final String MY_NAME = ME + "getPassword: ";
		BcwtsLogger.debug(MY_NAME + "get patron details");
		HttpSession session = request.getSession(true);
		String returnValue = "password";
		session.setAttribute(Constants.BREAD_CRUMB_NAME,
				Constants.BREADCRUMB_VIEW_EDIT_PROFILE);
		return mapping.findForward(returnValue);

	}

}
