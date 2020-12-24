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

import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtConfigParamsDTO;
import com.marta.admin.dto.BcwtManageAddCompanytoTMADTO;
import com.marta.admin.hibernate.TmaCompanyLink;
import com.marta.admin.hibernate.TmaCompanyLinkId;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class ManageAddCompanytoTMADAO extends MartaBaseDAO {

	final String ME = "ManageAddCompanytoTMADAO: ";

	Date sysDate = new Date();

	DateFormat formatDate = new SimpleDateFormat("mm/dd/yyyy");

	String encodedPassword = null;

	String decodePassword = null;

	String query = null;

	Connection con = null;

	Statement stmt = null;

	ResultSet rs = null;

	BcwtConfigParamsDTO configParamDTO = ConfigurationCache
			.getConfigurationValues(Constants.IS_MARTA_ENV);

	/**
	 * Method to get Company Types
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public List getCompanyTypes() throws Exception {

		final String MY_NAME = ME + "getCompanyTypes: ";
		BcwtsLogger.debug(MY_NAME);

		List companyTypeList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "select partnerCompanyType.companyType, partnerCompanyType.companyTypeId "
					+ " from PartnerCompanyType partnerCompanyType ";
			List stateListToIterate = session.createQuery(query).list();

			for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				companyTypeList.add(new LabelValueBean(String
						.valueOf(element[0]), String.valueOf(element[1])));
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
		return companyTypeList;
	}

	/**
	 * Method to get ManageAddCompanyTMADAODetails
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public List getManageAddCompanytoTMADAODetails() throws Exception {

		final String MY_NAME = ME + "getManageAddCompanytoTMADAODetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting Add Company to TMA CompanyTypes Details Started");
		Session session = null;
		Transaction transaction = null;

		List manageAddCompanytoTMADAODetailsList = null;

		BcwtManageAddCompanytoTMADTO bcwtManageAddCompanytoTMADTO = null;
		String query = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = " select partnerCompanyInfo.companyName,partnerCompanyInfo.nextfareCompanyId,partnerCompanyType.companyType,partnerCompanyInfo.companyId"
					+ " from PartnerCompanyInfo partnerCompanyInfo,PartnerCompanyType partnerCompanyType "
					+ " where partnerCompanyInfo.partnerCompanyType.companyTypeId = partnerCompanyType.companyTypeId and partnerCompanyInfo.companyId not in "
					+ " (select tmaComp.id.companyId from TmaCompanyLink tmaComp)and partnerCompanyInfo.partnerCompanyStatus.companyStatusId = '203'";

			List listToIterate = session.createQuery(query).list();
			manageAddCompanytoTMADAODetailsList = new ArrayList();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] managePartner = (Object[]) iter.next();

					bcwtManageAddCompanytoTMADTO = new BcwtManageAddCompanytoTMADTO();

					if (bcwtManageAddCompanytoTMADTO != null) {

						if (null != managePartner[0]) {
							bcwtManageAddCompanytoTMADTO
									.setCompanyName(managePartner[0].toString());
						}
						if (null != managePartner[1]) {
							bcwtManageAddCompanytoTMADTO
									.setNextfareCompanyId(managePartner[1]
											.toString());
						}
						if (null != managePartner[2]) {
							bcwtManageAddCompanytoTMADTO
									.setCompanyType(managePartner[2].toString());
						}

						manageAddCompanytoTMADAODetailsList
								.add(bcwtManageAddCompanytoTMADTO);
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
		return manageAddCompanytoTMADAODetailsList;
	}

	/**
	 * Method to get Search Add Companyto TMA Details
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public List getSearchAddCompanytoTMADetails(
			BcwtManageAddCompanytoTMADTO manageAddCompanytoTMADTO)
			throws Exception {
		final String MY_NAME = ME + "getSearchAddCompanytoTMADetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting Add Company to TMA CompanyTypes Details");

		Session session = null;
		Transaction transaction = null;
		List listToIterate = null;
		List searchAddCompanytoTMADetailsList = null;
		BcwtManageAddCompanytoTMADTO searchAddCompanytoTMADTO = null;
		boolean flag = false;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = " select partnerCompanyInfo.companyName,partnerCompanyInfo.nextfareCompanyId,partnerCompanyType.companyType,partnerCompanyInfo.companyId"
					+ " from PartnerCompanyInfo partnerCompanyInfo,PartnerCompanyType partnerCompanyType "
					+ " where partnerCompanyInfo.partnerCompanyType.companyTypeId = partnerCompanyType.companyTypeId and partnerCompanyInfo.companyId not in "
					+ " (select tmaComp.id.companyId from TmaCompanyLink tmaComp)and partnerCompanyInfo.partnerCompanyStatus.companyStatusId = '203'";

			if (null != manageAddCompanytoTMADTO) {
				if (null != manageAddCompanytoTMADTO.getCompanyName()) {
					query = query
							+ "  and lower(partnerCompanyInfo.companyName)like '"
							+ manageAddCompanytoTMADTO.getCompanyName().trim()
									.toLowerCase() + "%'";
					flag = true;
				}

				if (null != manageAddCompanytoTMADTO.getNextfareCompanyId()) {
					query = query
							+ "  and lower(partnerCompanyInfo.nextfareCompanyId)like '"
							+ manageAddCompanytoTMADTO.getNextfareCompanyId()
									.trim().toLowerCase() + "%'";
					flag = true;
				}

				if (null != manageAddCompanytoTMADTO.getCompanyType()) {
					if (Util.equalsIgnoreCase(manageAddCompanytoTMADTO
							.getCompanyType(), Constants.ZERO)) {
						query = query;
					} else {
						query = query
								+ "  and lower(partnerCompanyType.companyTypeId)like '"
								+ manageAddCompanytoTMADTO.getCompanyType()
										.trim().toLowerCase() + "%'";
						flag = true;
					}

				}

			}

			listToIterate = new ArrayList();
			listToIterate = session.createQuery(query).list();

			searchAddCompanytoTMADetailsList = new ArrayList();
			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] managePartner = (Object[]) iter.next();

					searchAddCompanytoTMADTO = new BcwtManageAddCompanytoTMADTO();

					if (searchAddCompanytoTMADTO != null) {

						if (null != managePartner[0]) {
							searchAddCompanytoTMADTO
									.setCompanyName(managePartner[0].toString());
						}
						if (null != managePartner[1]) {
							searchAddCompanytoTMADTO
									.setNextfareCompanyId(managePartner[1]
											.toString());
						}
						if (null != managePartner[2]) {
							searchAddCompanytoTMADTO
									.setCompanyType(managePartner[2].toString());
						}

						if (null != managePartner[3]) {
							searchAddCompanytoTMADTO
									.setCompanyId(managePartner[3].toString());
						}

						searchAddCompanytoTMADetailsList
								.add(searchAddCompanytoTMADTO);
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
		return searchAddCompanytoTMADetailsList;
	}

	/**
	 * Method to get Company Registration Request DetailsTypes
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */

	public BcwtManageAddCompanytoTMADTO getCompanyRegistrationRequestDetails(
			String regReqId) throws Exception {
		final String MY_NAME = ME + "getCompanyRegistrationDetails: ";
		BcwtsLogger.debug(MY_NAME
				+ "getting  Company Registration Details Started");
		Session session = null;
		Transaction transaction = null;
		BcwtManageAddCompanytoTMADTO bcwtManageAddCompanytoTMADTO = null;
		List listToIterate = new ArrayList();
		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			query = " select partnerCompanyInfo.companyName,"
					+ " partnerCompanyInfo.nextfareCompanyId,"
					+ " partnerCompanyInfo.partnerCompanyType.companyType,"
					+ " partnerCompanyInfo.partnerCompanyStatus.companyStatus,"
					+ " partnerAdminDetails.phone1,"
					+ " partnerAdminDetails.email,partnerAdminInfo.userName"
					+ " from PartnerCompanyInfo partnerCompanyInfo,PartnerCompanyStatus partnerCompanyStatus, "
					+ " PartnerAdminDetails partnerAdminDetails,PartnerAdminInfo partnerAdminInfo "
					+ " where partnerAdminDetails.partnerCompanyInfo.companyId = '"
					+ regReqId
					+ "' "
					+ " and partnerCompanyInfo.companyId = '"
					+ regReqId
					+ "' "
					+ " and partnerAdminDetails.partnerAdminRole.roleid = '"
					+ Constants.SUPER_ADMIN
					+ "' "
					+ " and partnerAdminDetails.partnerId = partnerAdminInfo.partnerAdminDetails.partnerId "
					+ " and partnerCompanyStatus.companyStatus = partnerCompanyInfo.partnerCompanyStatus.companyStatus "
					+ " and partnerCompanyStatus.companyStatus = 'Active'";

			listToIterate = (List) session.createQuery(query).list();

			if (listToIterate != null && !listToIterate.isEmpty()) {
				for (Iterator iter = listToIterate.iterator(); iter.hasNext();) {
					Object[] partnerObj = (Object[]) iter.next();

					bcwtManageAddCompanytoTMADTO = new BcwtManageAddCompanytoTMADTO();

					if (null != partnerObj[0]) {
						bcwtManageAddCompanytoTMADTO
								.setCompanyName(partnerObj[0].toString());
					}

					if (null != partnerObj[1]) {
						bcwtManageAddCompanytoTMADTO
								.setNextfareCompanyId(partnerObj[1].toString());
					}
					if (null != partnerObj[2]) {
						bcwtManageAddCompanytoTMADTO
								.setCompanyType(partnerObj[2].toString());
					}
					if (null != partnerObj[3]) {
						bcwtManageAddCompanytoTMADTO
								.setCompanyStatus(partnerObj[3].toString());
					}
					if (null != partnerObj[4]) {
						bcwtManageAddCompanytoTMADTO.setPhone1(partnerObj[4]
								.toString());
					}
					if (null != partnerObj[5]) {
						bcwtManageAddCompanytoTMADTO.setEmail(partnerObj[5]
								.toString());
					}
					if (null != partnerObj[6]) {
						bcwtManageAddCompanytoTMADTO
								.setPartnerUserName(partnerObj[6].toString());
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
		return bcwtManageAddCompanytoTMADTO;
	}

	/**
	 * Method to get TmaName Types
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public List getTmaName() throws Exception {

		final String MY_NAME = ME + "getTmaName: ";
		BcwtsLogger.debug(MY_NAME);

		List tmaNameList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();

			String query = "select tmaInformation.tmaName, tmaInformation.tmaId "
					+ " from TmaInformation tmaInformation  ";
			List stateListToIterate = session.createQuery(query).list();

			for (Iterator iter = stateListToIterate.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				tmaNameList.add(new LabelValueBean(String.valueOf(element[0]),
						String.valueOf(element[1])));
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
		return tmaNameList;
	}

	/**
	 * Method to get Company Types
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public boolean addCompany(
			BcwtManageAddCompanytoTMADTO bcwtManageAddCompanytoTMADTO)
			throws Exception {

		final String MY_NAME = ME + "getTmaName: ";
		BcwtsLogger.debug(MY_NAME);
		boolean isAdded = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();

			TmaCompanyLink tmaCompanyLink = new TmaCompanyLink();
			TmaCompanyLinkId tmaCompanyLinkId = new TmaCompanyLinkId();

			if (bcwtManageAddCompanytoTMADTO.getCompanyId() != null) {
				tmaCompanyLinkId.setCompanyId(Long
						.valueOf(bcwtManageAddCompanytoTMADTO.getCompanyId()));
			}

			if (bcwtManageAddCompanytoTMADTO.getTmaName() != null) {
				tmaCompanyLinkId.setTmaId(Long
						.valueOf(bcwtManageAddCompanytoTMADTO.getTmaName()));
			}

			if (bcwtManageAddCompanytoTMADTO.getPartnerUserName() != null) {
				tmaCompanyLinkId
						.setCompanyUsername(bcwtManageAddCompanytoTMADTO
								.getPartnerUserName());
			}

			if (bcwtManageAddCompanytoTMADTO.getCompanyName() != null) {
				tmaCompanyLinkId.setCompanyName(bcwtManageAddCompanytoTMADTO
						.getCompanyName());
			}
			tmaCompanyLink.setId(tmaCompanyLinkId);

			session.save(tmaCompanyLink);
			isAdded = true;
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}

		return isAdded;
	}

}
