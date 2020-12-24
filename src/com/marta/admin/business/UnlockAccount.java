package com.marta.admin.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.actions.DispatchAction;

import com.marta.admin.dao.UnlockAccountDAO;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtPatronDTO;
import com.marta.admin.dto.BcwtUnlockAccountDTO;
import com.marta.admin.exceptions.MartaConstants;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.PropertyReader;
import com.marta.admin.utils.SendMail;

public class UnlockAccount extends DispatchAction {

	final String ME = "UnlockAccount: ";

	/**
	 * method to get admin list
	 * 
	 * @return
	 * @throws MartaException
	 */
	public List getAdminList(String roleId) throws MartaException {
		final String MY_NAME = ME + " getAdminList: ";
		BcwtsLogger.debug(MY_NAME + " to get admin detail list ");

		List adminDetailedList = new ArrayList();

		try {
			UnlockAccountDAO unlockAccountDAO = new UnlockAccountDAO();
			adminDetailedList = unlockAccountDAO.getAdminList(roleId);
			BcwtsLogger.info(MY_NAME + " got OrderList");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_DETAILS));
		}
		return adminDetailedList;
	}

	/**
	 * Method to populate locked user details
	 * 
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public BcwtUnlockAccountDTO populateLockedUserDetails(String patronId)
			throws MartaException {
		final String MY_NAME = ME + " populateUnlockedUsersDetails: ";
		BcwtsLogger.debug(MY_NAME + " to populate locked user details ");
		// List lockedUserAccounntList = new ArrayList();
		BcwtUnlockAccountDTO bcwtUnlockAccountDTO = new BcwtUnlockAccountDTO();
		try {

			UnlockAccountDAO unlockAccountDAO = new UnlockAccountDAO();
			bcwtUnlockAccountDTO = unlockAccountDAO
					.populateLockedUserDetails(patronId);
			BcwtsLogger.info(MY_NAME + " got OrderList");

		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_DETAILS));
		}
		return bcwtUnlockAccountDTO;
	}

	/**
	 * Method to search for perticular locked user details
	 * 
	 * @param bcwtUnlockAccountDTO
	 * @param patronId
	 * @return
	 * @throws MartaException
	 */
	public List searchLockedAdmins(BcwtUnlockAccountDTO bcwtUnlockAccountDTO,
			String patronId, String roleId) throws MartaException {
		final String MY_NAME = ME + " searchLockedAdmins: ";
		BcwtsLogger.debug(MY_NAME + " to search locked user details ");
		List lockedUserAccounntList = new ArrayList();
		try {

			UnlockAccountDAO unlockAccountDAO = new UnlockAccountDAO();
			lockedUserAccounntList = unlockAccountDAO.searchLockedAdmins(
					bcwtUnlockAccountDTO, patronId, roleId);
			BcwtsLogger.info(MY_NAME + " got OrderList");

		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_ADMIN_DETAILS));
		}
		return lockedUserAccounntList;
	}

	/**
	 * Method to unlock the locked user and to send mail
	 * 
	 * @param bcwtPatronDTO
	 * @param basePath
	 * @return
	 * @throws MartaException
	 */
	public boolean unlockLockedAdmin(BcwtPatronDTO bcwtPatronDTO,
			String basePath) throws MartaException {
		final String MY_NAME = ME + " searchLockedAdmins: ";
		BcwtsLogger.debug(MY_NAME + " to search locked user details ");
		boolean isUnLocked = false;
		try {

			UnlockAccountDAO unlockAccountDAO = new UnlockAccountDAO();
			isUnLocked = unlockAccountDAO.unlockLockedAdmin(bcwtPatronDTO);
			BcwtsLogger.info(MY_NAME + " got OrderList");
			String decodedPassword = Base64EncodeDecodeUtil
					.decodeString(bcwtPatronDTO.getPatronpassword());

			if (isUnLocked) {
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
						.getValue(Constants.UNLOCK_MAIL_CONTENT);
				if (null != bcwtPatronDTO
						&& null != bcwtPatronDTO.getFirstname()) {
					content = content.replaceAll("\\$USERNAME", bcwtPatronDTO
							.getFirstname().trim());
				} else {
					content = content.replaceAll("\\$USERNAME", "Subscriber");
				}
				content = content.replaceAll("\\$PASSWORD", decodedPassword);
				String imgPath = basePath + Constants.MARTA_LOGO;
				String imageContent = "<img src='" + imgPath + "'/>";
				content = content.replaceAll("\\$LOGOIMAGE", imageContent);
				content = content.replaceAll("\\$PWDCONDITION", PropertyReader
						.getValue(Constants.PASSWORD_CONDITION));
				content = content.replaceAll("\\$NOTES", bcwtPatronDTO
						.getNotes());
				BcwtsLogger.debug(MY_NAME + "content :" + content);
				String subject = PropertyReader
						.getValue(Constants.FORGOT_PWD_MAIL_SUBJECT);

				BcwtsLogger.debug(MY_NAME + "subject :" + subject
						+ "userEmail :" + bcwtPatronDTO.getEmailid());
				try {

					SendMail.sendMail(fromDTO.getParamvalue(), bcwtPatronDTO
							.getEmailid(), content,
							smtpPathDTO.getParamvalue(), subject, "",
							mailportDTO.getParamvalue(), mailprotocolDTO
									.getParamvalue());
					BcwtsLogger.info(MY_NAME + "Mail sent sucess");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in order display admin list :"
					+ e.getMessage());
			throw new MartaException(PropertyReader
					.getValue(MartaConstants.BCWTS_UNLOCK_ADMIN_DETAILS));
		}
		return isUnLocked;
	}

	// to display Locked message in welcome page starts
	public List displayLockedCalls(String patronTypeId) throws MartaException {
		final String MY_NAME = ME + " removeLockedCall: ";
		BcwtsLogger.debug(MY_NAME + " removing locked call");
		UnlockAccountDAO unlockAccountDAO = null;
		List lockedList = null;
		try {
			unlockAccountDAO = new UnlockAccountDAO();
			lockedList = unlockAccountDAO.displayLockedCalls(patronTypeId);
			BcwtsLogger.info(MY_NAME + "removing locked call");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in removing locked call :"
					+ e.getMessage());

		}
		return lockedList;
	}

	// to display Locked message in welcome page ends
	/*
	 * public List getRecentlyLockedAccounts() throws MartaException{ final
	 * String MY_NAME = ME + " getRecentlyLockedAccounts: ";
	 * BcwtsLogger.debug(MY_NAME + " getting recently locked accounts");
	 * UnlockAccountDAO unlockAccountDAO = null ;
	 * 
	 * 
	 * List recentlyLockedAccountsList = null; try { unlockAccountDAO = new
	 * UnlockAccountDAO();
	 * 
	 * recentlyLockedAccountsList =
	 * unlockAccountDAO.getRecentlyLockedAccounts(); BcwtsLogger.info(MY_NAME +
	 * "removing alert message" ); } catch (Exception e) {
	 * BcwtsLogger.error(MY_NAME + " Exception in removing alert message :" +
	 * e.getMessage()); } return recentlyLockedAccountsList; }
	 */
}
