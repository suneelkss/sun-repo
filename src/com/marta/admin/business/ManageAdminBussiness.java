package com.marta.admin.business;

import java.util.List;

import com.marta.admin.dao.ManageAdminDAO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtSearchDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.SendMail;
import com.marta.admin.utils.Util;

public class ManageAdminBussiness {

	final String ME = "ManageAdminBussiness";

	/**
	 * get the list user for the given parent patron id
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public List getUserDetailList(Long patronId, String roleId)
			throws MartaException {

		final String MY_NAME = ME + " getUserDetailList: ";
		BcwtsLogger.debug(MY_NAME + " to get user detail list ");
		List userDetailList = null;
		try {
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			userDetailList = manageAdminDAO.getUserDetailList(patronId, roleId);

			BcwtsLogger.info(MY_NAME + " got admin user list");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting the user details list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_USER_LIST_FIND));
		}
		return userDetailList;
	}

	/**
	 * get the patron details for the given patron id
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public BcwtPatronDTO getPatronDetails(Long patronId) throws MartaException {

		final String MY_NAME = ME + " getUserDetailList: ";
		BcwtsLogger.debug(MY_NAME + " to get user detail list ");
		BcwtPatronDTO bcwtPatronDTO = null;
		try {
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			bcwtPatronDTO = manageAdminDAO.getPatronDetails(patronId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in getting the patron user detail :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_USER_FIND));
		}
		return bcwtPatronDTO;
	}

	/**
	 * To add new patron details
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public String addPatron(BcwtPatronDTO bcwtPatronDTO, String basePath)
			throws MartaException {

		final String MY_NAME = ME + " addPatron: ";
		BcwtsLogger.debug(MY_NAME + " to add patron detail ");
		String check = "";
		try {
			String password = Util.generatePassword(6);
			String encodedPassword = Base64EncodeDecodeUtil.encodeString(Util
					.generatePassword(6));

			if (!Util.isBlankOrNull(encodedPassword)) {
				bcwtPatronDTO.setPatronpassword(encodedPassword);
			}
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			check = manageAdminDAO.addPatron(bcwtPatronDTO);

			if (Util.equalsIgnoreCase(check, Constants.SUCCESS)) {
				String smtpServerPath = ConfigurationCache
						.getParamValue(Constants.SMTP_SERVER_PATH);
				String webmasterMailId = ConfigurationCache
						.getParamValue(Constants.WEBMASTER_MAIL_ID);
				String mailPort = ConfigurationCache
						.getParamValue(Constants.MAIL_PORT);
				String mailProtocol = ConfigurationCache
						.getParamValue(Constants.MAIL_PROTOCOL);
				BcwtsLogger.debug(MY_NAME + "starting sending mail....");
				BcwtsLogger.debug(MY_NAME + "smtp path : " + smtpServerPath);
				BcwtsLogger.debug(MY_NAME + "From Id : " + webmasterMailId);
				String content = PropertyReader
						.getValue(Constants.NEW_USER_MAIL_CONTENT);
				if (!Util.isBlankOrNull(bcwtPatronDTO.getFirstname())) {
					content = content.replaceAll("\\$USERNAME", bcwtPatronDTO
							.getFirstname().trim());
				} else {
					content = content.replaceAll("\\$USERNAME", "Subscriber");
				}
				
				content = content.replaceAll("\\$PASSWORD", password);
				String imgPath = basePath + Constants.MARTA_LOGO;
				String imageContent = "<img src='" + imgPath + "'/>";				
				content = content.replaceAll("\\$MARTALOGIN", basePath);
				content = content.replaceAll("\\$LOGOIMAGE", imageContent);
				content = content.replaceAll("\\$PWDCONDITION", PropertyReader
						.getValue(Constants.PASSWORD_CONDITION));
				BcwtsLogger.debug(MY_NAME + "content :" + content);
				String subject = PropertyReader
						.getValue(Constants.NEW_ADMIN_MAIL_SUBJECT);

				BcwtsLogger.debug(MY_NAME + "subject :" + subject
						+ "userEmail :" + bcwtPatronDTO.getEmailid());
				try {
					if (!Util.isBlankOrNull(bcwtPatronDTO.getEmailid())
							&& !Util.isBlankOrNull(webmasterMailId)
							&& !Util.isBlankOrNull(content)
							&& !Util.isBlankOrNull(smtpServerPath)
							&& !Util.isBlankOrNull(subject)
							&& !Util.isBlankOrNull(mailPort)
							&& !Util.isBlankOrNull(mailProtocol)) {
						SendMail.sendMail(webmasterMailId, bcwtPatronDTO
								.getEmailid(), content, smtpServerPath,
								subject, "", mailPort, mailProtocol);
					}

					BcwtsLogger.info(MY_NAME + "Mail sent sucess");
				} catch (Exception e) {
					BcwtsLogger.error(MY_NAME
							+ " Exception while sending mail :"
							+ e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in adding a patron user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.ADMIN_USER_ADD_FAILURE_MESSAGE));
		}
		return check;
	}

	/**
	 * To update a patron details
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public boolean updatePatron(BcwtPatronDTO bcwtPatronDTO)
			throws MartaException {

		final String MY_NAME = ME + " updatePatron: ";
		BcwtsLogger.debug(MY_NAME + " to update patron detail ");
		boolean isUpdate = false;
		try {
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			isUpdate = manageAdminDAO.updatePatron(bcwtPatronDTO);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating a patron user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.ADMIN_USER_UPADTE_FAILURE_MESSAGE));
		}
		return isUpdate;
	}

	/**
	 * To delete a patron
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public boolean deletePatron(Long patronId) throws MartaException {

		final String MY_NAME = ME + " deletePatron: ";
		BcwtsLogger.debug(MY_NAME + " to delete a patron detail ");
		boolean isdelete = false;
		try {
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			isdelete = manageAdminDAO.deletePatron(patronId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in deleting a patron user details :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(Constants.ADMIN_USER_DELETED_FAILURE_MESSAGE));
		}
		return isdelete;
	}

	/**
	 * method to get list of searched asmin user
	 * 
	 * @param bcwtSearchDTO
	 * @param patronId
	 * @param roleId
	 * @return
	 * @throws MartaException
	 */
	public List searchAdminUser(BcwtSearchDTO bcwtSearchDTO, Long patronId,
			String roleId) throws MartaException {

		final String MY_NAME = ME + " deletePatron: ";
		BcwtsLogger.debug(MY_NAME + " to get searched admin user details ");
		List searchedAdminList = null;
		try {
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			searchedAdminList = manageAdminDAO.searchAdminUser(bcwtSearchDTO,
					patronId, roleId);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception while getting searched admin user list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_USER_DELETE));
		}
		return searchedAdminList;
	}

	/**
	 * check the user name for partner sales
	 * 
	 * @param userName
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public boolean checkUserName(String userName) throws MartaException {
		final String MY_NAME = ME + " checkUserName: ";
		BcwtsLogger.debug(MY_NAME + " checking user name ");
		boolean isAvailable = false;
		try {
			ManageAdminDAO manageAdminDAO = new ManageAdminDAO();
			isAvailable = manageAdminDAO.checkUserName(userName);
			BcwtsLogger.info(MY_NAME + " checking user name:" + isAvailable);
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in check user name :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_CHECK_USERNAME));
		}
		return isAvailable;
	}
}
