/**
 * 
 */
package com.marta.admin.business;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.util.LabelValueBean;

import com.marta.admin.dao.NegativeListCardsDAO;
import com.marta.admin.dao.PatronDAO;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtPatronDisplayDTO;
import com.marta.admin.dto.BcwtStateDTO;
import com.marta.admin.dto.NegativeListCardsDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.DisplayPatronForm;
import com.marta.admin.forms.SignupForm;
import com.marta.admin.forms.UserForm;
import com.marta.admin.hibernate.BcwtPatron;
import com.marta.admin.hibernate.BcwtPatronType;
import com.marta.admin.hibernate.BcwtPwdVerifiy;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.Util;

/**
 * @author Subamathi Business class for add patron,edit patron,delete patron
 */
public class PatronManagement {
	final String ME = "PatronManagement: ";

	public List getStateInformation(HashMap stateMap) throws MartaException {
		final String MY_NAME = ME + " getStateInformation: ";
		BcwtsLogger.debug(MY_NAME + " getting state information ");
		List stateList = null;
		PatronDAO patronDAO = new PatronDAO();
		try {

			if (null != stateMap) {
				stateList = new ArrayList();
				Map sortedMap = new TreeMap(stateMap);
				for (Iterator iter = sortedMap.values().iterator(); iter
						.hasNext();) {
					BcwtStateDTO element = (BcwtStateDTO) iter.next();
					stateList.add(new LabelValueBean(String.valueOf(element
							.getStatename()), String.valueOf(element
							.getStateid())));
				}
			} else {
				stateList = patronDAO.getState();
			}
			BcwtsLogger.info(MY_NAME + " got state List" + stateList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting state list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
		}
		return stateList;
	}

	public List getSecretQuestions(String userName) throws MartaException {
		final String MY_NAME = ME + " getSecretQuestions: ";
		BcwtsLogger.debug(MY_NAME + " getting Secret Questions ");
		List secretQuestionsList = new ArrayList();
		PatronDAO patronDAO = new PatronDAO();
		try {
			List secretQuestionsListToIterate = patronDAO
					.getSecretQuestions(userName);
			if (null == userName || userName.equals("")) {
				for (Iterator iter = secretQuestionsListToIterate.iterator(); iter
						.hasNext();) {
					Object[] element = (Object[]) iter.next();
					secretQuestionsList.add(new LabelValueBean(String
							.valueOf(element[1]), String.valueOf(element[0])));
				}
			} else {
				secretQuestionsList.addAll(secretQuestionsListToIterate);
			}
			BcwtsLogger.info(MY_NAME + " got Secret Questions"
					+ secretQuestionsList.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting secret questions list :"
					+ e.getMessage());

			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_QUESTIONS));
		}
		return secretQuestionsList;
	}

	public BcwtPatronDTO getPatronDetails(String emailid) throws MartaException {
		final String MY_NAME = ME + " getPatronDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting patron details ");
		BcwtPatronDTO patronDetails = null;
		PatronDAO patronDAO = new PatronDAO();
		try {
			patronDetails = patronDAO.getPatronDetails(emailid);
			BcwtsLogger.info(MY_NAME
					+ " got patron details for the patron email:" + emailid);
		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME + " Exception in getting patron details :"
							+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND));
		}
		return patronDetails;
	}

	public boolean updatePatronDetails(BcwtPatronDTO bcwtPatronDTO)
			throws MartaException, IllegalArgumentException {
		final String MY_NAME = ME + " updatePatronDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating patron details ");
		boolean isUpdated = false;
		PatronDAO patronDAO = new PatronDAO();
		BcwtPatron bcwtPatron = new BcwtPatron();
		BcwtPatronType bcwtPatronType = new BcwtPatronType();
		Date currentTime = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		try {
			BeanUtils.copyProperties(bcwtPatron, bcwtPatronDTO);
			bcwtPatronType.setPatrontypeid(bcwtPatronDTO.getBcwtusertypeid());
			bcwtPatron.setBcwtpatrontype(bcwtPatronType);
			if (null != bcwtPatronDTO.getLastlogintime()) {
				currentTime = (Date) formatter.parse(bcwtPatronDTO
						.getLastlogintime());
				bcwtPatron.setLastlogin(currentTime);
			}
			bcwtPatron.setLastlogin(currentTime);
			if (null != bcwtPatronDTO.getPatronpassword()) {
				bcwtPatron.setPatronpassword(bcwtPatronDTO.getPatronpassword());
			}
			isUpdated = patronDAO.addOrUpdatePatron(bcwtPatron);
			BcwtsLogger.info(MY_NAME + " patron details updated?:" + isUpdated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details: "
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return isUpdated;
	}

	public boolean updatePatronPassword(String password, String patronId,
			String isAutoGenerated, BcwtPwdVerifiy bcwtPwdVerifiy)
			throws MartaException {
		final String MY_NAME = ME + " updatePatronPassword: ";
		BcwtsLogger.debug(MY_NAME + " updating patron password ");
		boolean isUpdated = false;
		PatronDAO patronDAO = new PatronDAO();
		BcwtPatron bcwtPatron = new BcwtPatron();
		try {
			isUpdated = patronDAO.updatePatronPassword(Base64EncodeDecodeUtil
					.encodeString(password), patronId, isAutoGenerated,
					bcwtPwdVerifiy);
			BcwtsLogger.info(MY_NAME + " patron details updated?:" + isUpdated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron password :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return isUpdated;
	}

	public SignupForm setSecreatQuestionAnswer(SignupForm signUpForm)
			throws MartaException {
		final String MY_NAME = ME + " setSecreatQuestionAnswer: ";
		BcwtsLogger.debug(MY_NAME + " setting secreat question ans answer ");
		boolean isUpdated = false;
		try {
			List secretQuestionListToIterate = getSecretQuestions(signUpForm
					.getEmail());
			for (Iterator iter = secretQuestionListToIterate.iterator(); iter
					.hasNext();) {
				Object[] element = (Object[]) iter.next();
				signUpForm.setSecretQuestion(String.valueOf(element[0]));
				signUpForm.setSecretAnswer(String.valueOf(element[2]));
			}
			BcwtsLogger.info(MY_NAME + " patron details updated?:" + isUpdated);
		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME + " Exception in secret answer updation :"
							+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return signUpForm;
	}

	public boolean checkUserEmail(String email, int patronId)
			throws MartaException {
		final String MY_NAME = ME + " checkUserEmail: ";
		BcwtsLogger.debug(MY_NAME + " checking user email ");
		boolean isAvailable = false;
		PatronDAO patronDAO = new PatronDAO();
		;
		try {
			isAvailable = patronDAO.checkUserEmail(email, patronId);
			BcwtsLogger.info(MY_NAME + " checking user email:" + isAvailable);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in check user email :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return isAvailable;
	}

	public BcwtPatronDTO getPatronDetailsById(Long patronId)
			throws MartaException {
		final String MY_NAME = ME + " getPatronDetailsById: ";
		BcwtsLogger.debug(MY_NAME + " getting patron details by Id");
		BcwtPatronDTO patronDetails = null;
		PatronDAO patronDAO = new PatronDAO();
		try {
			patronDetails = patronDAO.getPatronDetailsById(patronId);
			BcwtsLogger.info(MY_NAME
					+ " got patron details for the patron id: " + patronId);
		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME + " Exception in getting patron details :"
							+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_UPDATE));
		}
		return patronDetails;
	}

	public HashMap getState() throws MartaException {
		final String MY_NAME = ME + " getState: ";
		BcwtsLogger.debug(MY_NAME + " getting state information ");
		HashMap stateMap = null;
		PatronDAO patronDAO = new PatronDAO();
		try {
			stateMap = patronDAO.getStateInfo();
			BcwtsLogger.info(MY_NAME + " got state List" + stateMap.size());
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting state list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
		}
		return stateMap;
	}

	public String fetchHierarchicalId(Long patronId, int usertypeId)
			throws MartaException {
		final String MY_NAME = ME + " fetchHierarchicalId: ";
		BcwtsLogger.debug(MY_NAME + " getting hierarchial Ids");
		String hierarchicalIds = "";
		try {
			PatronDAO patronDAO = new PatronDAO();
			hierarchicalIds = patronDAO.fetchHierarchicalId(patronId,
					usertypeId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting state list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
		}
		return hierarchicalIds;
	}

	/**
	 * Method to get user information against patron id.
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */

	/**
	 * Method to delete user.
	 * 
	 * @param addressId
	 * @return
	 * @throws MartaException
	 */
	public boolean deleteUser(String patronId) throws MartaException {
		final String MY_NAME = ME + " deleteUser: ";
		BcwtsLogger.debug(MY_NAME + " deleting user ");
		boolean isAdded = false;
		try {
			PatronDAO patronDAO = new PatronDAO();
			isAdded = patronDAO.deleteUser(patronId);
			BcwtsLogger.info(MY_NAME + " user deleted to database");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in deleting user :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADDRESS_DELETE));
		}
		return isAdded;
	}

	/**
	 * Method to update patrons.
	 * 
	 * @param signUpForm
	 *            - SignupForm
	 * @param patronId
	 *            - Long
	 * @param updatePassword
	 *            - boolean
	 * @param isAnswerUpdated
	 *            - boolean
	 * @return boolean
	 */
	public boolean updateUser(UserForm userForm, String patronId)
			throws MartaException {
		final String MY_NAME = ME + " addPatron: ";
		BcwtsLogger.debug(MY_NAME + " adding patron details");
		boolean isUserUpdated = false;
		ConfigurationCache configurationCache = null;
		BcwtConfigParamsDTO configParamDTO = null;

		try {
			PatronDAO patronDAO = new PatronDAO();
			isUserUpdated = patronDAO.updateAdminPatron(userForm, patronId);

			configurationCache = new ConfigurationCache();
			configParamDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues
					.get(Constants.IS_MARTA_ENV);
			if ((Constants.YES)
					.equalsIgnoreCase(configParamDTO.getParamvalue())) {
			}

		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in updating patron :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADDRESS_UPDATE));
		}
		return isUserUpdated;
	}

	/**
	 * to get event and its order list
	 * 
	 * @param emailid
	 * @return List
	 * @throws MartaException
	 */

	/**
	 * To check user association
	 * 
	 * @param bcwtEvents
	 * @return boolean
	 * @throws MartaException
	 */
	public boolean checkUserAssociation(String userId, int patronId)
			throws MartaException {
		final String MY_NAME = ME + " checkUserAssociation: ";
		BcwtsLogger.debug(MY_NAME + " checking an usetr ");
		boolean isupdated = false;
		try {
			PatronDAO patronDAO = new PatronDAO();
			isupdated = patronDAO.checkUserAssociation(userId, patronId);
			BcwtsLogger.info(MY_NAME + " got user details ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in getting user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADDRESS_FIND));
		}
		return isupdated;
	}

	/**
	 * To get security details
	 * 
	 * @param patronId
	 * @return boolean
	 * @throws MartaException
	 */
	public boolean getSecurityQuestionDetails(Long patronId)
			throws MartaException {
		final String MY_NAME = ME + " getSecurityQuestionDetails: ";
		BcwtsLogger.debug(MY_NAME + " getting security details ");
		PatronDAO patronDAO = new PatronDAO();
		boolean isUserSecured = false;
		try {
			isUserSecured = patronDAO.getSecurityQuestionDetails(patronId);
			BcwtsLogger.info(MY_NAME
					+ " got security details for the patron id:" + patronId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting patron security details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND));
		}
		return isUserSecured;
	}

	/**
	 * to get user role permission
	 * 
	 * @return List
	 * @throws MartaException
	 */
	public List getRolePermission() throws MartaException {
		final String MY_NAME = ME + " getRolePermission: ";
		BcwtsLogger.debug(MY_NAME + " getting role permission ");
		List rolePermissionList = null;
		PatronDAO patronDAO = new PatronDAO();
		try {
			rolePermissionList = patronDAO.getRolePermission();
			BcwtsLogger.info(MY_NAME + " got role permission List");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting role permission List :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_STATE));
		}
		return rolePermissionList;
	}

	public List getPatronTypeName() throws MartaException {
		final String MY_NAME = ME + " getPatronTypeName: ";
		BcwtsLogger.debug(MY_NAME + " getting PatronTypeName ");
		List patronTypeList = null;
		// List patronTypeNameList = new ArrayList();
		PatronDAO patronDAO = new PatronDAO();
		try {

			patronTypeList = patronDAO.getPatronTypeName();

			BcwtsLogger.info(MY_NAME + " got patronTypeList"
					+ patronTypeList.size());
		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME + " Exception in getting patronTypeList :"
							+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_FIND_TYPE));
		}
		return patronTypeList;
	}

	public boolean addOrUpdatePatron(BcwtPatronDTO bcwtPatronDTO)
			throws MartaException, IllegalArgumentException {
		final String MY_NAME = ME + " updatePatronDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating patron details ");
		boolean isUpdated = false;
		PatronDAO patronDAO = new PatronDAO();
		BcwtPatron bcwtPatron = new BcwtPatron();
		BcwtPatronType bcwtPatronType = new BcwtPatronType();
		Date currentTime = null;
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		try {
			BeanUtils.copyProperties(bcwtPatron, bcwtPatronDTO);
			bcwtPatronType.setPatrontypeid(bcwtPatronDTO.getBcwtusertypeid());
			bcwtPatron.setBcwtpatrontype(bcwtPatronType);
			if (null != bcwtPatronDTO.getLastlogintime()) {
				currentTime = (Date) formatter.parse(bcwtPatronDTO
						.getLastlogintime());
				bcwtPatron.setLastlogin(currentTime);
			}
			bcwtPatron.setLastlogin(currentTime);
			if (null != bcwtPatronDTO.getPatronpassword()) {
				bcwtPatron.setPatronpassword(bcwtPatronDTO.getPatronpassword());
			}
			isUpdated = patronDAO.addOrUpdatePatron(bcwtPatron);
			BcwtsLogger.info(MY_NAME + " patron details updated?:" + isUpdated);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating patron details: "
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_PATRON_ADD));
		}
		return isUpdated;
	}

	public ArrayList getPatronDisplayDetails() {
		final String MY_NAME = ME + " getPatronDisplayDetails: ";
		BcwtsLogger.debug(MY_NAME + " getPatronDisplayDetails ");
		ArrayList displayList = new ArrayList();
		PatronDAO patronDAO = new PatronDAO();
		try {
			displayList = patronDAO.getPatronDisplayDetails();
			BcwtsLogger.info(MY_NAME + " getPatronDisplayDetails ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getPatronDisplayDetails :"
					+ e.getMessage());

		}
		return displayList;
	}
	
	public ArrayList getPatronCount() {
		final String MY_NAME = ME + " getPatronCount: ";
		BcwtsLogger.debug(MY_NAME + " getPatronCount ");
		ArrayList countList = new ArrayList();
		PatronDAO patronDAO = new PatronDAO();
		try {
			countList = (ArrayList) patronDAO.getPatronCount();
			BcwtsLogger.info(MY_NAME + " getPatronCount ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getPatronCount :"
					+ Util.getFormattedStackTrace(e));

		}
		return countList;
	}
	

	public ArrayList getSearchPatronDetails(DisplayPatronForm displayPatronForm) {
		final String MY_NAME = ME + " getSearchPatronDetails: ";
		BcwtsLogger.debug(MY_NAME + " getSearchPatronDetails ");
		ArrayList displayList = new ArrayList();
		PatronDAO patronDAO = new PatronDAO();
		try {
			displayList = patronDAO.getSearchPatronDetails(displayPatronForm);
			BcwtsLogger.info(MY_NAME + " getSearchPatronDetails ");
		} catch (Exception e) {
			BcwtsLogger
					.error(MY_NAME + " Exception in getSearchPatronDetails :"
							+ e.getMessage());

		}
		return displayList;
	}

	public boolean updatePatronActiveStatus(
			BcwtPatronDisplayDTO bcwtPatronDisplayDTO) throws MartaException {

		final String MY_NAME = " updatePatronActiveStatus: ";
		BcwtsLogger.debug(MY_NAME + " to update patron active status detail ");
		// OrderSearchDAO orderSearchDAO= null;
		PatronDAO patronDAO = null;

		boolean isUpdate = false;
		try {

			patronDAO = new PatronDAO();
			isUpdate = patronDAO.updatePatronActiveStatus(bcwtPatronDisplayDTO);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception updating patron active status :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.APPLICATION_SETTINGS_UPDATE));
		}
		return isUpdate;
	}

}
