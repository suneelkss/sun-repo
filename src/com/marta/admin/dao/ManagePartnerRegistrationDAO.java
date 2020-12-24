package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.BcwtCompanyStatusDTO;
import com.marta.admin.dto.BcwtManagePartnerRegistrationDTO;
import com.marta.admin.dto.BcwtPartnerRegistrationRequestDTO;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.hibernate.PartnerAdminInfo;
import com.marta.admin.hibernate.PartnerAdminRole;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.hibernate.PartnerCompanyStatus;
import com.marta.admin.hibernate.PartnerCompanyType;
import com.marta.admin.hibernate.PartnerNewRegistration;
import com.marta.admin.hibernate.PartnerRegistrationStatus;
import com.marta.admin.hibernate.PartnerSecurityQuestion;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.EncryptDecryptPassword;
import com.marta.admin.utils.Util;

public class ManagePartnerRegistrationDAO extends MartaBaseDAO {

	final String ME = "ManagePartnerRegistrationDAO: ";

	Date sysDate = new Date();

	DateFormat formatDate = new SimpleDateFormat("mm/dd/yyyy");

	String encodedPassword = null;

	String decodePassword = null;

	String query = null;

	Connection con = null;

	Statement stmt = null;

	ResultSet rs = null;

	public List getStatus() throws Exception {
		final String MY_NAME = ME + "getStatus: ";
		BcwtsLogger.debug(MY_NAME);
		List statusList = new ArrayList();
		Session session = null;
		
		Transaction transaction = null;
		

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select registrationStatusid,registrationStatus "
					+ " from PartnerRegistrationStatus order by registrationStatusid ";
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

	public List getManagePartnerRegistrationDetails() throws Exception {

		final String MY_NAME = ME + "getManagePartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting Manage Partner Registration Details Started");
		Session session = null;
		Transaction transaction = null;

		List managePartnerRegistrationDetailsList = null;
		BcwtManagePartnerRegistrationDTO managePartnerRegistrationDTO = null;
		String query = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = "select partnerNewRegistration.registrationRequestid,"
					+ " partnerNewRegistration.companyName,partnerNewRegistration.firstName,"
					+ " partnerNewRegistration.lastName,"
					+ " partnerNewRegistration.registrationRequestdate ,"
					+ " partnerNewRegistration.partnerRegistrationstatus.registrationStatus "
					+ " from PartnerNewRegistration partnerNewRegistration "
					+ " where partnerNewRegistration.companyName is not null"
					+ " order by REGISTRATION_REQUESTDATE desc ";

			List listToIterate = session.createQuery(query).list();
			managePartnerRegistrationDetailsList = new ArrayList();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] managePartner = (Object[]) iter.next();

					managePartnerRegistrationDTO = new BcwtManagePartnerRegistrationDTO();

					if (managePartnerRegistrationDTO != null) {

						if (null != managePartner[0]) {
							managePartnerRegistrationDTO
									.setRegistrationRequestId(managePartner[0]
											.toString());
						}
						if (null != managePartner[1]) {
							managePartnerRegistrationDTO
									.setCompanyName(managePartner[1].toString());
						}
						if (null != managePartner[2]) {
							managePartnerRegistrationDTO
									.setFirstName(managePartner[2].toString());
						}
						if (null != managePartner[3]) {
							managePartnerRegistrationDTO
									.setLastName(managePartner[3].toString());
						}
						if (null != managePartner[4]) {
							String reqDate = Util.getDateAsString(
									(Date) managePartner[4], "MM-dd-yyyy");
							managePartnerRegistrationDTO
									.setRegistrationRequestDate(reqDate);
						}
						if (null != managePartner[5]) {
							managePartnerRegistrationDTO
									.setRegistrationStatus(managePartner[5]
											.toString());
						}
						managePartnerRegistrationDetailsList
								.add(managePartnerRegistrationDTO);
					}
				}
			}

			transaction.commit();
			session.flush();
			BcwtsLogger
					.info(MY_NAME + "got ManagePartner Registration details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return managePartnerRegistrationDetailsList;
	}

	public List getSerachPartnerRegistrationDetails(
			BcwtManagePartnerRegistrationDTO managePartnerRegistrationDTO)
			throws Exception {
		final String MY_NAME = ME + "getSearchPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting Search Partner Registration Details Started");

		Session session = null;
		Transaction transaction = null;
		List listToIterate = null;
		List searchPartnerDetailsList = null;
		BcwtManagePartnerRegistrationDTO searchPartnerRegistrationDTO = null;
		String statusName = null;
		boolean flag = false;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = "select partnerNewRegistration.registrationRequestid,"
					+ "	partnerNewRegistration.companyName,"
					+ "	partnerNewRegistration.firstName,"
					+ "	partnerNewRegistration.lastName,"
					+ "	partnerNewRegistration.registrationRequestdate,"
					+ "	partnerNewRegistration.partnerRegistrationstatus.registrationStatus "
					+ "	from PartnerNewRegistration partnerNewRegistration "
					+ " where partnerNewRegistration.companyName is not null ";

			if (null != managePartnerRegistrationDTO) {
				if (null != managePartnerRegistrationDTO.getCompanyName()) {
					query = query
							+ "  and lower(partnerNewRegistration.companyName)like '"
							+ managePartnerRegistrationDTO.getCompanyName()
									.trim().toLowerCase() + "%'";
					flag = true;
				}
				if (null != managePartnerRegistrationDTO.getFirstName()
						&& flag == true) {
					query = query
							+ " and lower(partnerNewRegistration.firstName)like '"
							+ managePartnerRegistrationDTO.getFirstName()
									.trim().toLowerCase() + "%'";
					flag = true;
				} else if (null != managePartnerRegistrationDTO.getFirstName()) {
					query = query
							+ " and lower(partnerNewRegistration.firstName)like '"
							+ managePartnerRegistrationDTO.getFirstName()
									.trim().toLowerCase() + "%'";
					flag = true;
				}
				if (null != managePartnerRegistrationDTO.getLastName()
						&& flag == true) {
					query = query
							+ " and lower(partnerNewRegistration.lastName)like '"
							+ managePartnerRegistrationDTO.getLastName().trim()
									.toLowerCase() + "%'";
					flag = true;
				} else if (null != managePartnerRegistrationDTO.getLastName()) {
					query = query
							+ " and lower(partnerNewRegistration.lastName)like '"
							+ managePartnerRegistrationDTO.getLastName().trim()
									.toLowerCase() + "%'";
					flag = true;
				}

				if (null != managePartnerRegistrationDTO.getStartDate()
						&& !managePartnerRegistrationDTO.getStartDate().equals(
								"")
						&& null != managePartnerRegistrationDTO.getEndDate()
						&& !managePartnerRegistrationDTO.getEndDate()
								.equals("") && flag == true) {
					query = query
							+ " and to_date(partnerNewRegistration.registrationRequestdate) >= "
							+ " to_date('"
							+ managePartnerRegistrationDTO.getStartDate()
							+ "','mm/dd/yyyy')"
							+ " and to_date(partnerNewRegistration.registrationRequestdate) <"
							+ " to_date('"
							+ Util.getNextDay(managePartnerRegistrationDTO
									.getEndDate()) + "','mm/dd/yyyy')";
					flag = true;
				} else if (null != managePartnerRegistrationDTO.getStartDate()
						&& !managePartnerRegistrationDTO.getStartDate().equals(
								"")
						&& null != managePartnerRegistrationDTO.getEndDate()
						&& !managePartnerRegistrationDTO.getEndDate()
								.equals("")) {
					query = query
							+ " and to_date(partnerNewRegistration.registrationRequestdate) >= "
							+ "  to_date('"
							+ managePartnerRegistrationDTO.getStartDate()
							+ "','mm/dd/yyyy')"
							+ " and to_date(partnerNewRegistration.registrationRequestdate) < "
							+ " to_date('"
							+ Util.getNextDay(managePartnerRegistrationDTO
									.getEndDate()) + "','mm/dd/yyyy')";
					flag = true;
				}

				if (null != managePartnerRegistrationDTO.getStartDate()
						&& !managePartnerRegistrationDTO.getStartDate().equals(
								"") && flag == true) {
					query = query
							+ " and to_date(partnerNewRegistration.registrationRequestdate) >= "
							+ " to_date('"
							+ managePartnerRegistrationDTO.getStartDate()
							+ "','mm/dd/yyyy')";

				} else if (null != managePartnerRegistrationDTO.getStartDate()
						&& !managePartnerRegistrationDTO.getStartDate().equals(
								"")) {

					query = query
							+ " and to_date(partnerNewRegistration.registrationRequestdate) >= "
							+ " to_date('"
							+ managePartnerRegistrationDTO.getStartDate()
							+ "','mm/dd/yyyy')";
				}
				if (null != managePartnerRegistrationDTO.getEndDate()
						&& !managePartnerRegistrationDTO.getEndDate()
								.equals("") && flag == true) {
					query = query
							+ " and to_date(partnerNewRegistration.registrationRequestdate) < "
							+ " to_date('"
							+ Util.getNextDay(managePartnerRegistrationDTO
									.getEndDate()) + "','mm/dd/yyyy')";

				} else if (null != managePartnerRegistrationDTO.getEndDate()
						&& !managePartnerRegistrationDTO.getEndDate()
								.equals("")) {
					query = query
							+ " and to_date(partnerNewRegistration.registrationRequestdate) < "
							+ " to_date('"
							+ Util.getNextDay(managePartnerRegistrationDTO
									.getEndDate()) + "','mm/dd/yyyy')";
				}
				if (null != managePartnerRegistrationDTO
						.getRegistrationStatus()
						&& !managePartnerRegistrationDTO
								.getRegistrationStatus().equals("")
						&& flag == true) {
					if (managePartnerRegistrationDTO.getRegistrationStatus()
							.equals("80")
							&& flag == true) {
						statusName = Constants.SUBMITTED;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (managePartnerRegistrationDTO
							.getRegistrationStatus().equals("81")
							&& flag == true) {
						statusName = Constants.PENDING;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (managePartnerRegistrationDTO
							.getRegistrationStatus().equals("82")
							&& flag == true) {
						statusName = Constants.APPROVED;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (managePartnerRegistrationDTO
							.getRegistrationStatus().equals("83")
							&& flag == true) {
						statusName = Constants.REJECTED;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus) like '"
								+ statusName.trim().toLowerCase() + "'";
					}

				} else if (null != managePartnerRegistrationDTO
						.getRegistrationStatus()
						&& !managePartnerRegistrationDTO
								.getRegistrationStatus().equals("")) {
					if (managePartnerRegistrationDTO.getRegistrationStatus()
							.equals("80")) {
						statusName = Constants.SUBMITTED;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (managePartnerRegistrationDTO
							.getRegistrationStatus().equals("81")) {
						statusName = Constants.PENDING;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (managePartnerRegistrationDTO
							.getRegistrationStatus().equals("82")) {
						statusName = Constants.APPROVED;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (managePartnerRegistrationDTO
							.getRegistrationStatus().equals("83")) {
						statusName = Constants.REJECTED;
						;
						query = query
								+ " and lower(partnerNewRegistration.partnerRegistrationstatus.registrationStatus) like '"
								+ statusName.trim().toLowerCase() + "'";
					}
				}
			}

			listToIterate = new ArrayList();
			listToIterate = session.createQuery(query).list();

			searchPartnerDetailsList = new ArrayList();
			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] managePartner = (Object[]) iter.next();

					searchPartnerRegistrationDTO = new BcwtManagePartnerRegistrationDTO();

					if (searchPartnerRegistrationDTO != null) {

						if (null != managePartner[0]) {
							searchPartnerRegistrationDTO
									.setRegistrationRequestId(managePartner[0]
											.toString());
						}
						if (null != managePartner[1]) {
							searchPartnerRegistrationDTO
									.setCompanyName(managePartner[1].toString());
						}
						if (null != managePartner[2]) {
							searchPartnerRegistrationDTO
									.setFirstName(managePartner[2].toString());
						}
						if (null != managePartner[3]) {
							searchPartnerRegistrationDTO
									.setLastName(managePartner[3].toString());
						}
						if (null != managePartner[4]) {
							String reqDate = Util.getDateAsString(
									(Date) managePartner[4], "MM-dd-yyyy");
							searchPartnerRegistrationDTO
									.setRegistrationRequestDate(reqDate);
						}
						if (null != managePartner[5]) {
							searchPartnerRegistrationDTO
									.setRegistrationStatus(managePartner[5]
											.toString());
						}
						searchPartnerDetailsList
								.add(searchPartnerRegistrationDTO);
					}
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME
					+ "got Search Partner Registration details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return searchPartnerDetailsList;
	}

	public List getPartnerRegistrationRequestDetails(String regReqId)
			throws Exception {
		final String MY_NAME = ME + "getPartnerRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting  Partner Registration Details Started");
		Session session = null;
		Transaction transaction = null;
		List partnerRegistrationDetailsList = null;
		BcwtPartnerRegistrationRequestDTO partnerRegistrationDTO = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			if (null != regReqId) {

				query = "select partnerNewRegistration.registrationRequestid,"
						+ " partnerNewRegistration.companyName,partnerCompanyType.companyType,"
						+ " partnerNewRegistration.lastName,partnerNewRegistration.firstName,"
						+ " partnerNewRegistration.phone1 ,partnerNewRegistration.phone2 ,"
						+ " partnerNewRegistration.fax ,"
						+ " partnerNewRegistration.email,partnerNewRegistration.userName, "
						+ " partnerNewRegistration.password, partnerNewRegistration.city,"
						+ " partnerNewRegistration.state,partnerNewRegistration.zip ,"
						+ " partnerNewRegistration.country ,partnerNewRegistration.senderNotes ,"
						+ " partnerNewRegistration.partnerRegistrationstatus.registrationStatus ,"
						+ " partnerNewRegistration.securityQuestionid,partnerNewRegistration.securityAnswer,"
						+ " partnerNewRegistration.companyTypeid "
						+ " from PartnerNewRegistration partnerNewRegistration ,PartnerCompanyType partnerCompanyType"
						+ " where partnerNewRegistration.registrationRequestid = '"
						+ regReqId
						+ "'"
						+ " and partnerNewRegistration.companyTypeid = partnerCompanyType.companyTypeId";
			}
			List listToIterate = session.createQuery(query).list();
			partnerRegistrationDetailsList = new ArrayList();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] partnerObj = (Object[]) iter.next();

					partnerRegistrationDTO = new BcwtPartnerRegistrationRequestDTO();

					if (partnerRegistrationDTO != null) {

						if (null != partnerObj[0]) {
							partnerRegistrationDTO
									.setRequestNumber(partnerObj[0].toString());
						}
						if (null != partnerObj[1]) {
							partnerRegistrationDTO.setCompanyName(partnerObj[1]
									.toString());
						}
						if (null != partnerObj[2]) {
							partnerRegistrationDTO.setCompanyType(partnerObj[2]
									.toString());
						}
						if (null != partnerObj[3]) {
							partnerRegistrationDTO.setLastName(partnerObj[3]
									.toString());
						}
						if (null != partnerObj[4]) {
							partnerRegistrationDTO.setFirstName(partnerObj[4]
									.toString());
						}
						if (null != partnerObj[5]) {
							partnerRegistrationDTO.setPhone1(partnerObj[5]
									.toString());
						}
						if (null != partnerObj[6]) {
							partnerRegistrationDTO.setPhone2(partnerObj[6]
									.toString());
						}
						if (null != partnerObj[7]) {
							partnerRegistrationDTO.setFax(partnerObj[7]
									.toString());
						}
						if (null != partnerObj[8]) {
							partnerRegistrationDTO.setEmail(partnerObj[8]
									.toString());
						}
						if (null != partnerObj[9]) {
							partnerRegistrationDTO.setUserName(partnerObj[9]
									.toString());
						}
						if (null != partnerObj[10]) {
							encodedPassword = Base64EncodeDecodeUtil
									.encodeString(partnerObj[10].toString());
							partnerRegistrationDTO.setPassword(encodedPassword
									.toString());
						}
						if (null != partnerObj[11]) {
							partnerRegistrationDTO.setCity(partnerObj[11]
									.toString());
						}
						if (null != partnerObj[12]) {
							partnerRegistrationDTO.setState(partnerObj[12]
									.toString());
						}
						if (null != partnerObj[13]) {
							partnerRegistrationDTO.setZip(partnerObj[13]
									.toString());
						}
						if (null != partnerObj[14]) {
							partnerRegistrationDTO.setCountry(partnerObj[14]
									.toString());
						}
						if (null != partnerObj[15]) {
							partnerRegistrationDTO.setSenderNote(partnerObj[15]
									.toString());
						}
						if (null != partnerObj[16]) {
							partnerRegistrationDTO
									.setCurrentStatus(partnerObj[16].toString());
						}
						if (null != partnerObj[17]) {
							partnerRegistrationDTO
									.setSecurityQuestionId(partnerObj[17]
											.toString());
						}
						if (null != partnerObj[18]) {
							partnerRegistrationDTO
									.setSecurityAnswer(partnerObj[18]
											.toString());
						}
						if (null != partnerObj[19]) {
							partnerRegistrationDTO
									.setCompanyTypeId(partnerObj[19].toString());
						}
						if (partnerRegistrationDTO.getCurrentStatus().trim()
								.toLowerCase().equals(
										"Approved".trim().toLowerCase())) {
							partnerRegistrationDTO.setNewStatus(" ");
							partnerRegistrationDTO.setNewStatusFlag("true");
						} else {
							partnerRegistrationDTO.setNewStatusFlag("false");
						}

						partnerRegistrationDetailsList
								.add(partnerRegistrationDTO);
					}
				}
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got ManagePartner "
					+ "Registration details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return partnerRegistrationDetailsList;
	}

	public List getWelcomePartnerRegistrationListDetails() throws Exception {
		final String MY_NAME = ME + "getWelcomePartnerRegistrationList"
				+ "Details: ";
		BcwtsLogger.debug(MY_NAME + "getting Welcome Page "
				+ "Manage Partner Registration Details Started");
		Session session = null;
		Transaction transaction = null;
		List welcomePartnerRegistrationDetailsList = null;
		BcwtManagePartnerRegistrationDTO welcomePartnerRegistrationDTO = null;
		String query = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = "select partnerNewRegistration.registrationRequestid,"
					+ " partnerNewRegistration.companyName,"
					+ " partnerNewRegistration.firstName,"
					+ " partnerNewRegistration.lastName,"
					+ " partnerNewRegistration.registrationRequestdate ,"
					+ " partnerNewRegistration.partnerRegistrationstatus.registrationStatus "
					+ " from PartnerNewRegistration partnerNewRegistration where "
					+ " lower(trim(partnerNewRegistration.partnerRegistrationstatus.registrationStatus)) = "
					+ " '" + (Constants.PENDING).trim().toLowerCase() + "' "
					+ " order by REGISTRATION_REQUESTDATE desc ";

			List listToIterate = session.createQuery(query).list();
			welcomePartnerRegistrationDetailsList = new ArrayList();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] managePartner = (Object[]) iter.next();
					welcomePartnerRegistrationDTO = new BcwtManagePartnerRegistrationDTO();

					if (welcomePartnerRegistrationDTO != null) {

						if (null != managePartner[0]) {
							welcomePartnerRegistrationDTO
									.setRegistrationRequestId(managePartner[0]
											.toString());
						}
						if (null != managePartner[1]) {
							welcomePartnerRegistrationDTO
									.setCompanyName(managePartner[1].toString());
						}
						if (null != managePartner[2]) {
							welcomePartnerRegistrationDTO
									.setFirstName(managePartner[2].toString());
						}
						if (null != managePartner[3]) {
							welcomePartnerRegistrationDTO
									.setLastName(managePartner[3].toString());
						}
						if (null != managePartner[4]) {
							String reqDate = Util.getDateAsString(
									(Date) managePartner[4], "MM-dd-yyyy");
							welcomePartnerRegistrationDTO
									.setRegistrationRequestDate(reqDate);
						}
						if (null != managePartner[5]) {
							welcomePartnerRegistrationDTO
									.setRegistrationStatus(managePartner[5]
											.toString());
						}
						welcomePartnerRegistrationDetailsList
								.add(welcomePartnerRegistrationDTO);
					}
				}
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got welcome Page ManagePartner "
					+ "Registration details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return welcomePartnerRegistrationDetailsList;
	}

	public boolean addPartnerRegistrationRequestDetails(
			BcwtPartnerRegistrationRequestDTO partnerRegistrationDTO)
			throws Exception {
		final String MY_NAME = ME + "addPartnerRegistrationRequestDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "adding Partner Registration Request Details Started");
		Session session = null;
		Transaction transaction = null;
		
		boolean isUpdated = false;
		List listToIterate = null;
		PartnerNewRegistration partnerNewRegistration = null;
		PartnerCompanyInfo partnerCompanyInfo = null;
		PartnerCompanyStatus partnerCompanyStatus = null;
		PartnerCompanyType partnerCompanyType = null;

		try {

			if (null != partnerRegistrationDTO) {

				session = getSession();
				transaction = session.beginTransaction();

				BcwtsLogger.debug(MY_NAME + " Update Query for Partner"
						+ " New Registration Request");
				query = " select partnerNewRegistration.partnerRegistrationstatus.registrationStatus "
						+ " from PartnerNewRegistration partnerNewRegistration "
						+ " where partnerNewRegistration.registrationRequestid = "
						+ " '"
						+ partnerRegistrationDTO.getRequestNumber()
						+ "' ";
				String currentStatus = (String) session.createQuery(query)
						.uniqueResult();

				if ((!currentStatus.trim().toLowerCase().equalsIgnoreCase(
						Constants.APPROVED.trim().toLowerCase()))
						&& !currentStatus.equals("")) {
					partnerNewRegistration = new PartnerNewRegistration();

					String requestId = partnerRegistrationDTO
							.getRequestNumber();
					partnerNewRegistration = (PartnerNewRegistration) session
							.load(PartnerNewRegistration.class, new Long(
									requestId));

					if (partnerRegistrationDTO.getNewStatus() != null
							&& partnerRegistrationDTO.getNewStatus().equals(
									Constants.APPROVED_STATUS_ID)) {
						partnerNewRegistration
								.setRegistrationApprovedate(sysDate);

						PartnerRegistrationStatus partnerRegistrationstatus = new PartnerRegistrationStatus();
						partnerRegistrationstatus
								.setRegistrationStatusid(Long
										.valueOf(partnerRegistrationDTO
												.getNewStatus()));
						partnerNewRegistration
								.setPartnerRegistrationstatus(partnerRegistrationstatus);
						partnerNewRegistration
								.setSenderNotes(partnerRegistrationDTO
										.getMartaAdminNote());
					} else if (partnerRegistrationDTO.getNewStatus() != null
							&& !partnerRegistrationDTO.getNewStatus()
									.equals("")) {
						PartnerRegistrationStatus partnerRegistrationstatus = new PartnerRegistrationStatus();
						partnerRegistrationstatus
								.setRegistrationStatusid(Long
										.valueOf(partnerRegistrationDTO
												.getNewStatus()));

						partnerNewRegistration
								.setPartnerRegistrationstatus(partnerRegistrationstatus);

						partnerNewRegistration
								.setSenderNotes(partnerRegistrationDTO
										.getMartaAdminNote());
					}
					session.update(partnerNewRegistration);
					isUpdated = true;
					session.flush();
					BcwtsLogger
							.info(MY_NAME
									+ "Partner New Registration Data updated to database ");
				} else if (currentStatus.trim().toLowerCase().equalsIgnoreCase(
						Constants.APPROVED.trim().toLowerCase())
						&& !currentStatus.equals("")) {
					transaction.rollback();
					isUpdated = false;
				}
			}
			/**
			 * Partner Company Info Table Coding Starts Here
			 */
			
	
			
			
			if (null != partnerRegistrationDTO
					&& partnerRegistrationDTO.getNewStatus().equals(
							Constants.APPROVED_STATUS_ID)) {
			/*	String companyId = null;

				query = "select partnerCompanyInfo.companyId ,"
						+ " 	partnerCompanyInfo.companyName from "
						+ " PartnerCompanyInfo partnerCompanyInfo where"
						+ " lower(trim(partnerCompanyInfo.companyName)) = '"
						+ partnerRegistrationDTO.getCompanyName().trim()
								.toLowerCase() + "'";

				listToIterate = session.createQuery(query).list();
				if (null != listToIterate && !listToIterate.isEmpty()) {
					for (Iterator iter = listToIterate.iterator(); iter
							.hasNext();) {
						Object[] companyInfoObj = (Object[]) iter.next();
						if (null != companyInfoObj[0]
								&& !companyInfoObj[0].equals("")) {
							companyId = companyInfoObj[0].toString();
						}
					}
				}
				if (!listToIterate.isEmpty()
						&& listToIterate != null
						&& partnerRegistrationDTO.getNewStatus().equals(
								Constants.APPROVED_STATUS_ID)) {

					BcwtsLogger.debug(MY_NAME + " Update Query for Partner"
							+ " Company Info");
					partnerCompanyInfo = new PartnerCompanyInfo();
					partnerCompanyStatus = new PartnerCompanyStatus();
					partnerCompanyInfo = (PartnerCompanyInfo) session.load(
							PartnerCompanyInfo.class, new Long(companyId));
					if (partnerRegistrationDTO.getNewStatus().equals(
							Constants.APPROVED_STATUS_ID)) {
						partnerCompanyStatus.setCompanyStatusId(Long
								.valueOf(Constants.COMPANY_STATUS_REGISTERED));
						partnerCompanyInfo
								.setPartnerCompanyStatus(partnerCompanyStatus);
					}
					partnerCompanyInfo.setPurchaseOrderno("Not Assigned");
					session.update(partnerCompanyInfo);
				
				
				
					session.flush(); 
					BcwtsLogger.info(MY_NAME
							+ "PartnerCompanyInfo Data updated to database ");
				}
				transaction.commit();
				isUpdated = true; */
				
				partnerCompanyInfo = new PartnerCompanyInfo();
				partnerCompanyStatus = new PartnerCompanyStatus();
				partnerCompanyType = new PartnerCompanyType();
				partnerCompanyStatus.setCompanyStatusId(Long
						.valueOf(Constants.COMPANY_STATUS_REGISTERED));
				partnerCompanyInfo
						.setPartnerCompanyStatus(partnerCompanyStatus);
				partnerCompanyInfo.setPurchaseOrderno("Not Assigned");
				partnerCompanyType.setCompanyTypeId(Long.valueOf(partnerRegistrationDTO.getCompanyTypeId()));
				partnerCompanyInfo.setCompanyName(partnerRegistrationDTO.getCompanyName());
				partnerCompanyInfo.setPartnerCompanyType(partnerCompanyType);
				session.save(partnerCompanyInfo); 
				session.flush();
				transaction.commit();
				isUpdated = true;
				
			} else if (null != partnerRegistrationDTO
					&& !partnerRegistrationDTO.getNewStatus().equals(
							Constants.APPROVED_STATUS_ID)) {

				isUpdated = true;
			} else {
				transaction.commit();
				isUpdated = false;
			}

		} catch (Exception ex) {
			transaction.rollback();
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}

	public List getCompanyStatusDetails() throws Exception {

		final String MY_NAME = ME + "getCompanyStatusDetails: ";
		BcwtsLogger.debug(MY_NAME + "getting  Company Status Details Started");
		Session session = null;
		Transaction transaction = null;
		List companyStatusDetailsList = null;
		BcwtCompanyStatusDTO companyStatusDTO = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = " select partnerNewRegistration.companyName,"
					+ " partnerNewRegistration.partnerRegistrationstatus.registrationStatus "
					+ " from PartnerNewRegistration partnerNewRegistration "
					+ " where partnerNewRegistration.partnerRegistrationstatus.registrationStatus = '"
					+ Constants.APPROVED
					+ "'or "
					+ " partnerNewRegistration.partnerRegistrationstatus.registrationStatus = '"
					+ Constants.PENDING + "'";
			List listToIterate = session.createQuery(query).list();
			companyStatusDetailsList = new ArrayList();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] companyStatusObj = (Object[]) iter.next();

					companyStatusDTO = new BcwtCompanyStatusDTO();

					if (companyStatusDTO != null) {
						if (null != companyStatusObj[0]) {
							companyStatusDTO.setCompanyName(companyStatusObj[0]
									.toString());
						}
						if (null != companyStatusObj[1]) {
							companyStatusDTO
									.setPartnerCompanyStatus(companyStatusObj[1]
											.toString());
						}
						companyStatusDetailsList.add(companyStatusDTO);
					}
				}
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got ManagePartner "
					+ "Registration details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return companyStatusDetailsList;
	}

	public List getCompanyStatusInformationDetails(String companyName)
			throws Exception {

		final String MY_NAME = ME + "getCompanyStatusInformationDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting  Company Status Information Details Started for company "+companyName);
		Session session = null;
		Transaction transaction = null;
		List companyStatusInformationList = null;
		BcwtCompanyStatusDTO companyInformationDTO = null;
		String nextFareId = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = " select partnerNewRegistration.companyName,"
					+ " partnerNewRegistration.address1 ,"
					+ " partnerNewRegistration.city ,"
					+ " partnerNewRegistration.state ,"
					+ " partnerNewRegistration.zip ,"
					+ " partnerNewRegistration.phone1 ,"
					+ " partnerNewRegistration.fax ,"
					+ " partnerNewRegistration.email ,"
					+ " partnerCompanyInfo.companyId ,"
					+ " partnerCompanyInfo.purchaseOrderno ,"
					+ " partnerCompanyInfo.nextfareCompanyId,"
					+ " partnerCompanyInfo.partnerCompanyStatus.companyStatusId ,"
					+ " partnerCompanyInfo.partnerCompanyStatus.companyStatus  "
					+ " from PartnerNewRegistration partnerNewRegistration ,"
					+ " PartnerCompanyInfo partnerCompanyInfo"
					+ " where lower(trim(partnerCompanyInfo.companyName)) like '%"
					+ companyName.trim().toLowerCase() + "%' and "
					+ " lower(trim(partnerNewRegistration.companyName)) like '%"
					+ companyName.trim().toLowerCase() + "%'";

			List listToIterate = session.createQuery(query).list();
			companyStatusInformationList = new ArrayList();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] companyInformationObj = (Object[]) iter.next();

					companyInformationDTO = new BcwtCompanyStatusDTO();

					if (companyInformationDTO != null) {
						if (null != companyInformationObj[0]) {
							companyInformationDTO
									.setCompanyName(companyInformationObj[0]
											.toString());
						}
						if (null != companyInformationObj[1]) {
							companyInformationDTO
									.setAddress(companyInformationObj[1]
											.toString());
						}
						if (null != companyInformationObj[2]) {
							companyInformationDTO
									.setCity(companyInformationObj[2]
											.toString());
						}
						if (null != companyInformationObj[3]) {
							companyInformationDTO
									.setState(companyInformationObj[3]
											.toString());
						}
						if (null != companyInformationObj[4]) {
							companyInformationDTO
									.setZip(companyInformationObj[4].toString());
						}
						if (null != companyInformationObj[5]) {
							companyInformationDTO
									.setPhone(companyInformationObj[5]
											.toString());
						}
						if (null != companyInformationObj[6]) {
							companyInformationDTO
									.setFax(companyInformationObj[6].toString());
						}
						if (null != companyInformationObj[7]) {
							companyInformationDTO
									.setEmail(companyInformationObj[7]
											.toString());
						}
						if (null != companyInformationObj[8]) {
							companyInformationDTO
									.setCompanyId(companyInformationObj[8]
											.toString());
						}
						if (null != companyInformationObj[9]) {
							companyInformationDTO
									.setPurchaseOrderNo(companyInformationObj[9]
											.toString());
						}
						if (null != companyInformationObj[10]) {
							companyInformationDTO
									.setNextFareId(companyInformationObj[10]
											.toString());
						}
						if (null != companyInformationObj[11]) {
							companyInformationDTO
									.setCompanyStatus(companyInformationObj[11]
											.toString());
						}
						String id = companyInformationDTO.getNextFareId();
						BcwtsLogger.info("Get company name from nextfare for id"+id);
						if (null != companyName && !companyName.equals("")
								&& (id == null)) {
							con = NextFareConnection.getConnection();
							Statement stmt = con.createStatement();
							query = " select customer.customer_id from"
									+ " nextfare_main.Customer customer where lower(trim(customer.org_name))"
									+ " like '%" + companyName.trim().toLowerCase()
									+ "%' ";
							rs = stmt.executeQuery(query);
							if (rs != null) {
								while (rs.next()) {
									nextFareId = rs.getString(1);
									if (null != nextFareId
											&& !nextFareId.equals("")) {
										companyInformationDTO
												.setNextFareId(nextFareId);
										companyInformationDTO
												.setNextFareIdFlag("false");
									}
								}

							}
						} else {
							query = " select partnerCompanyInfo.nextfareCompanyId from"
									+ " PartnerCompanyInfo partnerCompanyInfo where "
									+ " lower(trim(partnerCompanyInfo.companyName)) = '"
									+ companyName.trim().toLowerCase() + "'";
							String nextFareId1 = (String) session.createQuery(
									query).uniqueResult();
							companyInformationDTO.setNextFareId(nextFareId1);
							companyInformationDTO.setNextFareIdFlag("true");
						}

						String statusId = companyInformationObj[11].toString();
						BcwtsLogger.info("Company status is:"+statusId); 
						if (null != statusId && !statusId.equals("")) {
							query = " select partnerCompanyStatus.companyStatus"
									+ " from PartnerCompanyStatus partnerCompanyStatus"
									+ " where partnerCompanyStatus.companyStatusId = '"
									+ statusId + "'";
							String currentStatus = (String) session
									.createQuery(query).uniqueResult();
							if (null != currentStatus
									&& !currentStatus.equals("")) {
								companyInformationDTO
										.setCompanyCurrentStatus(currentStatus);
							}
						}
						companyStatusInformationList.add(companyInformationDTO);
					}
				}
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got Company "
					+ "Status Information details");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return companyStatusInformationList;
	}

	public List getCompanyStatus() throws Exception {
		final String MY_NAME = ME + "getCompanyStatus: ";
		BcwtsLogger.debug(MY_NAME);
		List companyStatusList = new ArrayList();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select partnerCompanyStatus.companyStatusId ,partnerCompanyStatus.companyStatus "
					+ " from PartnerCompanyStatus partnerCompanyStatus "
					+ " where partnerCompanyStatus.companyStatusId ='"
					+ Constants.COMPANY_STATUS_ACTIVE
					+ "'or"
					+ " partnerCompanyStatus.companyStatusId ='"
					+ Constants.COMPANY_STATUS_ON_HOLD
					+ "'"
					+ " order by partnerCompanyStatus.companyStatusId ";
			List statusListToIterate = session.createQuery(query).list();
			for (Iterator iter = statusListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				companyStatusList.add(new LabelValueBean(String
						.valueOf(element[1]), String.valueOf(element[0])));
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  Company Status list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return companyStatusList;
	}

	public boolean addCompanyStatusInformationDetails(
			BcwtCompanyStatusDTO companyStatusDTO) throws Exception {
		final String MY_NAME = ME
				+ "AddorUpdateCompanyInfo&AdminDetails&AdminInfo: ";
		Session session = null;
		Transaction transaction = null;
		Session session1 = null;
		Transaction transaction1 = null;
		boolean isUpdated = false;
		boolean isExist = false;
		BcwtPartnerRegistrationRequestDTO partnerRegistrationDTO = null;
		List nextFareIdList = null;
		PartnerCompanyInfo partnerCompanyInfo = null;
		PartnerAdminDetails partnerAdminDetails = null;
		PartnerAdminInfo partnerAdminInfo = null;
		PartnerCompanyStatus partnerCompanyStatus = null;
		Long partnerId = new Long(0);
		String comID = null;

		try {
			/**
			 * Update For Partner Company Info.
			 */

			if (null != companyStatusDTO
					&& !companyStatusDTO.equals("")
					&& !companyStatusDTO.getCompanyCurrentStatus().trim()
							.toLowerCase().equals(
									Constants.STATUS_PENDING_REGISTRATION
											.trim().toLowerCase())) {
				BcwtsLogger.debug(MY_NAME
						+ "Get Session for Update Partner CompanyInfo Table ");
				session = getSession();
				BcwtsLogger.debug(MY_NAME
						+ " Got Session for Update Partner CompanyInfo Table");
				transaction = session.beginTransaction();
				BcwtsLogger.debug(MY_NAME
						+ " Update Query for Update Partner CompanyInfo Table");

				/** Company Current Status as Registered */
				if (companyStatusDTO.getCompanyCurrentStatus().trim()
						.toLowerCase().equals(
								Constants.STATUS_REGISTRATION.trim()
										.toLowerCase())) {

					String companyId = companyStatusDTO.getCompanyId();
					partnerCompanyInfo = new PartnerCompanyInfo();
					partnerCompanyInfo = (PartnerCompanyInfo) session.load(
							PartnerCompanyInfo.class, new Long(companyId));

					if (null != companyStatusDTO.getConfirmNextFareId()
							&& !companyStatusDTO.getConfirmNextFareId().equals(
									"")) {
						nextFareIdList = new ArrayList();
						String nextFareId = null;
						query = " select partnerCompanyInfo.nextfareCompanyId,"
								+ " partnerCompanyInfo.companyId "
								+ " from PartnerCompanyInfo partnerCompanyInfo "
								+ " where partnerCompanyInfo.nextfareCompanyId = "
								+ " '"
								+ companyStatusDTO.getConfirmNextFareId()
								+ "' " + " and partnerCompanyInfo.companyId = "
								+ " '" + companyStatusDTO.getCompanyId() + "' ";
						nextFareIdList = (List) session.createQuery(query)
								.list();

						if (null != nextFareIdList && !nextFareIdList.isEmpty()) {
							for (Iterator iter = nextFareIdList.iterator(); iter
									.hasNext();) {
								Object[] partnerObj = (Object[]) iter.next();
								nextFareId = partnerObj[0].toString();
								companyId = partnerObj[1].toString();
							}
						}
						/**
						 * Company Current Status as Registered and nextFare Id
						 * is existed in local Company New Status as Active or
						 * Hotlisted or On Hold
						 */

						if (null != nextFareId && !nextFareId.equals("")
								&& null != companyId && !companyId.equals("")) {

							isExist = true;
							if (companyStatusDTO.getCompanyStatus().equals(
									Constants.COMPANY_STATUS_ACTIVE)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
								partnerCompanyInfo
										.setNextfareCompanyId(nextFareId);
							} else if (companyStatusDTO.getCompanyStatus()
									.equals(Constants.COMPANY_STATUS_ON_HOLD)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
								partnerCompanyInfo
										.setNextfareCompanyId(nextFareId);
							} else if (companyStatusDTO.getCompanyStatus()
									.equals(Constants.COMPANY_STATUS_HOTLISTED)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
								partnerCompanyInfo
										.setNextfareCompanyId(nextFareId);
							}
							session.update(partnerCompanyInfo);
							session.flush();
							BcwtsLogger.info(MY_NAME
									+ "Update to PartnerCompanyInfo DB ");
						} else {
							/**
							 * Company Current Status as Registered and nextFare
							 * Id is not existed in local
							 */

							query = " select partnerCompanyInfo.nextfareCompanyId "
									+ " from PartnerCompanyInfo partnerCompanyInfo "
									+ " where partnerCompanyInfo.nextfareCompanyId = "
									+ " '"
									+ companyStatusDTO.getConfirmNextFareId()
									+ "' ";
							nextFareId = (String) session.createQuery(query)
									.uniqueResult();

							if (null != nextFareId && !nextFareId.equals("")) {
								isUpdated = false;
							} else {
								isExist = false;
								isUpdated = true;
							}
						}

						/**
						 * Company Current Status as Registered and nextFare Id
						 * is not existed in local Company New Status as Active
						 * or Hotlisted or On Hold
						 */

						if (null != companyStatusDTO.getCompanyStatus()
								&& !companyStatusDTO.getCompanyStatus().equals(
										"") && isExist == false
								&& isUpdated == true) {
							if (companyStatusDTO.getCompanyStatus().equals(
									Constants.COMPANY_STATUS_ACTIVE)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
								partnerCompanyInfo
										.setNextfareCompanyId(companyStatusDTO
												.getConfirmNextFareId());
							} else {
								isExist = true;
								isUpdated = false;
							}
						}
						if (isExist == false && isUpdated == true) {
							session.update(partnerCompanyInfo);
							session.flush();
							BcwtsLogger.info(MY_NAME
									+ "Update to PartnerCompanyInfo DB ");
						} else if (isExist == true) {
							isUpdated = true;
						} else {
							isUpdated = false;
						}
					}
				} else if (companyStatusDTO.getCompanyCurrentStatus().trim()
						.toLowerCase().equals(
								Constants.STATUS_FLAG_ACTIVE.trim()
										.toLowerCase())
						|| companyStatusDTO.getCompanyCurrentStatus().trim()
								.toLowerCase().equals(
										Constants.STATUS_FLAG_HOTLISTED.trim()
												.toLowerCase())
						|| companyStatusDTO.getCompanyCurrentStatus().trim()
								.toLowerCase().equals(
										Constants.STATUS_FLAG_ONHOLD.trim()
												.toLowerCase())) {

					if (null != companyStatusDTO.getNextFareId()
							&& !companyStatusDTO.getNextFareId().equals("")) {
						String companyId = companyStatusDTO.getCompanyId();
						comID = companyId;
						partnerCompanyInfo = new PartnerCompanyInfo();
						partnerCompanyInfo = (PartnerCompanyInfo) session.load(
								PartnerCompanyInfo.class, new Long(companyId));

						if (!Util.isBlankOrNull(companyStatusDTO
								.getCompanyStatus())) {
							if (companyStatusDTO.getCompanyStatus().equals(
									Constants.COMPANY_STATUS_ACTIVE)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
							} else if (companyStatusDTO.getCompanyStatus()
									.equals(Constants.COMPANY_STATUS_ON_HOLD)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
							} else if (companyStatusDTO.getCompanyStatus()
									.equals(Constants.COMPANY_STATUS_HOTLISTED)) {
								partnerCompanyStatus = new PartnerCompanyStatus();
								partnerCompanyStatus.setCompanyStatusId(Long
										.valueOf(companyStatusDTO
												.getCompanyStatus()));
								partnerCompanyInfo
										.setPartnerCompanyStatus(partnerCompanyStatus);
							}
						}
						session.update(partnerCompanyInfo);
						session.flush();
						BcwtsLogger.info(MY_NAME
								+ "Update to PartnerCompanyInfo DB ");
					}
					isUpdated = true;
					isExist = false;
				}
				/**
				 * Company Current Status as Registered and Add or Update For
				 * Partner Admin Details & Partner AdminInfo.
				 */
				if (!companyStatusDTO.getCompanyCurrentStatus().trim()
						.toLowerCase().equals(
								Constants.STATUS_PENDING_REGISTRATION.trim()
										.toLowerCase())
						&& isUpdated == true && isExist == false) {

					query = "select partnerNewRegistration.registrationRequestid,"
							+ " partnerNewRegistration.companyName,partnerCompanyType.companyType,"
							+ " partnerNewRegistration.lastName,partnerNewRegistration.firstName,"
							+ " partnerNewRegistration.phone1 ,partnerNewRegistration.phone2 ,"
							+ " partnerNewRegistration.fax,"
							+ " partnerNewRegistration.email,partnerNewRegistration.userName, "
							+ " partnerNewRegistration.password, partnerNewRegistration.city,"
							+ " partnerNewRegistration.state,partnerNewRegistration.zip ,"
							+ " partnerNewRegistration.country ,partnerNewRegistration.senderNotes ,"
							+ " partnerNewRegistration.partnerRegistrationstatus.registrationStatus ,"
							+ " partnerNewRegistration.securityQuestionid,partnerNewRegistration.securityAnswer,"
							+ " partnerNewRegistration.companyTypeid "
							+ " from PartnerNewRegistration partnerNewRegistration ,PartnerCompanyType partnerCompanyType"
							+ " where lower(trim(partnerNewRegistration.companyName)) = '"
							+ companyStatusDTO.getCompanyName().trim()
									.toLowerCase()
							+ "'"
							+ " and partnerNewRegistration.companyTypeid = partnerCompanyType.companyTypeId";

					List listToIterate = session.createQuery(query).list();
					if (listToIterate != null && !listToIterate.isEmpty()
							&& isExist == false && isUpdated == true) {
						for (Iterator iter = listToIterate.iterator(); iter
								.hasNext();) {
							Object[] partnerObj = (Object[]) iter.next();

							partnerRegistrationDTO = new BcwtPartnerRegistrationRequestDTO();

							if (partnerRegistrationDTO != null) {
								if (null != partnerObj[0]) {
									partnerRegistrationDTO
											.setRequestNumber(partnerObj[0]
													.toString());
								}
								if (null != partnerObj[1]) {
									partnerRegistrationDTO
											.setCompanyName(partnerObj[1]
													.toString());
								}
								if (null != partnerObj[2]) {
									partnerRegistrationDTO
											.setCompanyType(partnerObj[2]
													.toString());
								}
								if (null != partnerObj[3]) {
									partnerRegistrationDTO
											.setLastName(partnerObj[3]
													.toString());
								}
								if (null != partnerObj[4]) {
									partnerRegistrationDTO
											.setFirstName(partnerObj[4]
													.toString());
								}
								if (null != partnerObj[5]) {
									partnerRegistrationDTO
											.setPhone1(partnerObj[5].toString());
								}
								if (null != partnerObj[6]) {
									partnerRegistrationDTO
											.setPhone2(partnerObj[6].toString());
								}
								if (null != partnerObj[7]) {
									partnerRegistrationDTO.setFax(partnerObj[7]
											.toString());
								}
								if (null != partnerObj[8]) {
									partnerRegistrationDTO
											.setEmail(partnerObj[8].toString());
								}
								if (null != partnerObj[9]) {
									partnerRegistrationDTO
											.setUserName(partnerObj[9]
													.toString());
								}
								if (null != partnerObj[10]) {
									encodedPassword = EncryptDecryptPassword.encryptPassword(partnerObj[10]
													.toString());
								/*			.encodeString(partnerObj[10]
													.toString());*/
									partnerRegistrationDTO
											.setPassword(encodedPassword
													.toString());
								}
								if (null != partnerObj[11]) {
									partnerRegistrationDTO
											.setCity(partnerObj[11].toString());
								}
								if (null != partnerObj[12]) {
									partnerRegistrationDTO
											.setState(partnerObj[12].toString());
								}
								if (null != partnerObj[13]) {
									partnerRegistrationDTO
											.setZip(partnerObj[13].toString());
								}
								if (null != partnerObj[14]) {
									partnerRegistrationDTO
											.setCountry(partnerObj[14]
													.toString());
								}
								if (null != partnerObj[15]) {
									partnerRegistrationDTO
											.setSenderNote(partnerObj[15]
													.toString());
								}
								if (null != partnerObj[16]) {
									partnerRegistrationDTO
											.setCurrentStatus(partnerObj[16]
													.toString());
								}
								if (null != partnerObj[17]) {
									partnerRegistrationDTO
											.setSecurityQuestionId(partnerObj[17]
													.toString());
								}
								if (null != partnerObj[18]) {
									partnerRegistrationDTO
											.setSecurityAnswer(partnerObj[18]
													.toString());
								}
								if (null != partnerObj[19]) {
									partnerRegistrationDTO
											.setCompanyTypeId(partnerObj[19]
													.toString());
								}

							}
						}

						if (null != partnerRegistrationDTO
								&& !partnerRegistrationDTO.equals("")
								&& isExist == false && isUpdated == true) {

							partnerAdminDetails = new PartnerAdminDetails();
							partnerCompanyInfo = new PartnerCompanyInfo();
							partnerAdminInfo = new PartnerAdminInfo();
							query = "select partnerAdminInfo.partnerAdminDetails.partnerId"
									+ " from PartnerAdminInfo partnerAdminInfo"
									+ " where lower(trim(partnerAdminInfo.userName)) = '"
									+ partnerRegistrationDTO.getUserName()
											.trim().toLowerCase() + "' ";

							partnerId = (Long) session.createQuery(query)
									.uniqueResult();

							/**
							 * Company current Status as Active or Hotlisted or
							 * On Hold Updation for Partner Admin Details table
							 */

						/*	if (null != partnerId && !partnerId.equals("")) {
								String partnerId1 = (partnerId).toString();

								partnerAdminDetails = (PartnerAdminDetails) session
										.load(PartnerAdminDetails.class,
												new Long(partnerId1));

								query = "select partnerCompanyStatus.companyStatus from "
										+ " PartnerCompanyStatus partnerCompanyStatus where "
										+ " trim(partnerCompanyStatus.companyStatusId) = "
										+ " '"
										+ companyStatusDTO.getCompanyStatus()
												.trim() + "'";

								String companyStatus = (String) session
										.createQuery(query).uniqueResult();

						
								
								
								if (null != companyStatus
										&& !companyStatus.equals("")) {
									if (companyStatus
											.trim()
											.toLowerCase()
											.equalsIgnoreCase(
													Constants.STATUS_FLAG_ONHOLD
															.trim()
															.toLowerCase())) {
										partnerAdminDetails
												.setStatusFlag(Constants.STATUS_FLAG_BARRED);
									} else if (companyStatus
											.trim()
											.toLowerCase()
											.equalsIgnoreCase(
													Constants.STATUS_FLAG_HOTLISTED
															.trim()
															.toLowerCase())) {
										partnerAdminDetails
												.setStatusFlag(Constants.STATUS_FLAG_BARRED);
									} else if (companyStatus
											.trim()
											.toLowerCase()
											.equalsIgnoreCase(
													Constants.STATUS_FLAG_ACTIVE
															.trim()
															.toLowerCase())) {
										partnerAdminDetails.setStatusFlag("");
									}
								}
								session.saveOrUpdate(partnerAdminDetails);
								session.flush();
								BcwtsLogger
										.info(MY_NAME
												+ "Partner Admin Details Data Updated to database ");
							} else */ if(true){
								/**
								 * Company Current Status as Registered and
								 * Adding a new record to Partner Admin Details
								 * table
								 */

								if (companyStatusDTO.getCompanyStatus().trim()
										.toLowerCase().equals(
												Constants.COMPANY_STATUS_ACTIVE
														.trim().toLowerCase())) {

									if (partnerRegistrationDTO.getLastName() != null
											&& !partnerRegistrationDTO
													.getLastName().equals("")) {

										partnerAdminDetails
												.setLastName(partnerRegistrationDTO
														.getLastName());
									}
									if (partnerRegistrationDTO.getFirstName() != null
											&& !partnerRegistrationDTO
													.getFirstName().equals("")) {
										partnerAdminDetails
												.setFirstName(partnerRegistrationDTO
														.getFirstName());
									}
									if (partnerRegistrationDTO.getPhone1() != null
											&& !partnerRegistrationDTO
													.getPhone1().equals("")) {
										partnerAdminDetails
												.setPhone1(partnerRegistrationDTO
														.getPhone1());
									}
									if (partnerRegistrationDTO.getPhone2() != null
											&& !partnerRegistrationDTO
													.getPhone2().equals("")) {
										partnerAdminDetails
												.setPhone2(partnerRegistrationDTO
														.getPhone2());
									}
									if (partnerRegistrationDTO.getFax() != null
											&& !partnerRegistrationDTO.getFax()
													.equals("")) {
										partnerAdminDetails
												.setFax(partnerRegistrationDTO
														.getFax());
									}
									if (partnerRegistrationDTO
											.getSecurityQuestionId() != null
											&& !partnerRegistrationDTO
													.getSecurityQuestionId()
													.equals("")) {
										PartnerSecurityQuestion partnerSecurityQuestion = new PartnerSecurityQuestion();
										partnerSecurityQuestion
												.setSecurityQuestionid(Long
														.valueOf(partnerRegistrationDTO
																.getSecurityQuestionId()));
										partnerAdminDetails
												.setPartnerSecurityquestion(partnerSecurityQuestion);
									}
									if (partnerRegistrationDTO
											.getSecurityAnswer() != null
											&& !partnerRegistrationDTO
													.getSecurityAnswer()
													.equals("")) {
										partnerAdminDetails
												.setSecurityAnswer(partnerRegistrationDTO
														.getSecurityAnswer());
									}
									if (partnerRegistrationDTO
											.getMartaAdminNote() != null
											&& !partnerRegistrationDTO
													.getMartaAdminNote()
													.equals("")) {
										partnerAdminDetails
												.setNotes(partnerRegistrationDTO
														.getMartaAdminNote());
									}
									if (partnerRegistrationDTO.getEmail() != null
											&& !partnerRegistrationDTO
													.getEmail().equals("")) {
										partnerAdminDetails
												.setEmail(partnerRegistrationDTO
														.getEmail());
									}

									PartnerAdminRole partnerAdminRole = new PartnerAdminRole();
									String roleId = Constants.SUPER_ADMIN;
									partnerAdminRole.setRoleid(Long
											.valueOf(roleId));
									partnerAdminDetails
											.setPartnerAdminRole(partnerAdminRole);

									partnerCompanyInfo.setCompanyId(Long
											.valueOf(companyStatusDTO
													.getCompanyId()));
									partnerAdminDetails
											.setPartnerCompanyInfo(partnerCompanyInfo);
									session.save(partnerAdminDetails);

									partnerId = partnerAdminDetails
											.getPartnerId();
									session.flush();
									isUpdated = true;
									BcwtsLogger
											.info(MY_NAME
													+ "Partner Admin Details Data added to database ");
								} else {
									isUpdated = false;
								}
								transaction.commit();
								session.flush();
							}
						}
						/**
						 * Partner Admin Info Table
						 */

						if (null != partnerId && null != partnerRegistrationDTO
								&& isExist == false && isUpdated == true) {

						/*	query = "select partnerAdminDetails.partnerId"
									+ " from PartnerAdminDetails partnerAdminDetails"
									+ " where partnerAdminDetails.partnerCompanyInfo.companyId = '"
									+ comID + "' ";*/
							session1 = getSession();
							transaction1 = session1.beginTransaction();
						/*	Long partId = (Long) session1.createQuery(
									query).uniqueResult();*/
							System.out.println("partner id"+partnerId);
							String query1 = "select partnerAdminInfo.userName"
								+ " from PartnerAdminInfo partnerAdminInfo"
								+ " where partnerAdminInfo.userName = '"
								+ partnerRegistrationDTO.getUserName() + "' ";
						String userName = (String) session1.createQuery(
								query1).uniqueResult();
							System.out.println("username"+userName);
							/** Update to Partner Admin Info table */

							if (null != userName && !userName.equals("")) {
								partnerAdminInfo = new PartnerAdminInfo();
								partnerAdminInfo = (PartnerAdminInfo) session
										.load(PartnerAdminInfo.class,
												new String(userName));

						//		String passStatus = Constants.PASSWORD_STATUS_TEMP;
						//		partnerAdminInfo.setPasswordStatus(passStatus);
								session1.update(partnerAdminInfo);
								session1.flush();
								BcwtsLogger
										.info(MY_NAME
												+ "Partner Admin Info Data Updated to database ");

							} else if (companyStatusDTO.getCompanyStatus()
									.trim().equals(
											Constants.COMPANY_STATUS_ACTIVE)) {

								/** Add a new record to Partner Admin Info table */
								
								BcwtsLogger.debug(MY_NAME
										+ " Got Session2 for Update Partner CompanyInfo Table");
								
								BcwtsLogger.debug(MY_NAME
										+ " Insert Query for Partner"
										+ " Admin Info");
								partnerAdminInfo = new PartnerAdminInfo();
								PartnerAdminDetails partnerAdminDetails1 = new PartnerAdminDetails();

								partnerAdminDetails1.setPartnerId(partnerId);
								partnerAdminInfo
										.setPartnerAdminDetails(partnerAdminDetails1);
								String Password = partnerRegistrationDTO
										.getPassword();

								encodedPassword = Base64EncodeDecodeUtil
										.encodeString(Password);
								if (null != encodedPassword
										&& !encodedPassword.equals("")) {
									decodePassword = Base64EncodeDecodeUtil
											.decodeString(encodedPassword);
									partnerAdminInfo
											.setPassword(decodePassword);
									partnerAdminInfo
											.setTempPassword(decodePassword);
								}
								if (partnerRegistrationDTO.getUserName() != null
										&& !partnerRegistrationDTO
												.getUserName().equals("")) {
									partnerAdminInfo
											.setUserName(partnerRegistrationDTO
													.getUserName());
								}
								String passStatus = Constants.PASSWORD_STATUS_TEMP;
								partnerAdminInfo.setPasswordStatus(passStatus);
								partnerAdminInfo.setWrongAttempts(new Long(0));

								session1.save(partnerAdminInfo);
								session1.flush();
								BcwtsLogger
										.info(MY_NAME
												+ "Partner Admin Info Data added to database ");
							}
							transaction1.commit();
							session1.flush();
							isUpdated = true;
						} else {
							isUpdated = false;
						}

					}
				} else {
					isUpdated = false;
				}
			}
		} catch (Exception ex) {
			transaction.rollback();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
}
