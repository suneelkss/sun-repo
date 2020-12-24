package com.marta.admin.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.util.LabelValueBean;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.cubic.cts.webservices.client.HotlistApiPort;
import com.cubic.cts.webservices.client.HotlistApi_Impl;
import com.marta.admin.business.ConfigurationCache;
import com.marta.admin.dto.BcwtHotListReport;
import com.marta.admin.dto.BcwtNewCardBenefitDTO;
import com.marta.admin.dto.BcwtNewCardReportDTO;
import com.marta.admin.dto.BcwtReportDTO;
import com.marta.admin.dto.BcwtReportSearchDTO;
import com.marta.admin.dto.BcwtUsageBenifitDTO;
import com.marta.admin.dto.BcwtsPartnerIssueDTO;
import com.marta.admin.dto.DisplayProductSummaryDTO;
import com.marta.admin.dto.PartnerReportSearchDTO;
import com.marta.admin.dto.CompanyInfoDTO;
import com.marta.admin.exceptions.MartaException;
import com.marta.admin.forms.ReportForm;
import com.marta.admin.hibernate.BcwtReports;
import com.marta.admin.utils.Base64EncodeDecodeUtil;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Query;
import com.marta.admin.utils.SimpleProtector;
import com.marta.admin.utils.Util;
import com.marta.admin.dao.NextFareConnection;
import com.marta.admin.dto.UpCommingMonthBenefitDTO;
import com.marta.admin.hibernate.BcwtsPartnerIssue;
import com.marta.admin.hibernate.GLGBSCASHView;
import com.marta.admin.hibernate.GLGBSView;
import com.marta.admin.hibernate.GLISView;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.hibernate.TmaInformation;
import com.marta.admin.hibernate.UpassActivateList;
import com.marta.admin.hibernate.UpassNewCard;
import com.marta.admin.hibernate.UpassBatchDetails;
import com.marta.admin.dto.ProductDetailsReportDTO;
import com.marta.admin.dto.BcwtConfigParamsDTO;

/**
 * @author Sowjanya
 * 
 */
public class ReportDAO extends MartaBaseDAO {
	final String ME = "ReportDAO: ";
	
   public  Map<String,Integer> productName = new HashMap<String,Integer>();
	
	
	
	
	public Map<String, Integer> getProductName() {
		return productName;
	}

	public void setProductName() {
		productName.put(Constants.TMAMARTA10R,0);
		productName.put(Constants.TMAMARTA20R,0);
		productName.put(Constants.TMAMARTACALM,0);
		productName.put(Constants.TMACBLOCCALM,0);
		productName.put(Constants.TMAGWZ1EXP10R,0);
		productName.put(Constants.TMACBEXPCALM,0);
		productName.put(Constants.TMACBLOC10R,0);
		productName.put(Constants.TMAGWLOC10R,0);
		productName.put(Constants.TMAGW1EXPCALM,0);
		productName.put(Constants.TMAGWZ2EXP10R,0);
		productName.put(Constants.TMAGWZ2EXPCALM,0);
		productName.put(Constants.TMACBEXP20R,0);
		productName.put(Constants.TMAGWLOCCALM,0);
	}
	
	DecimalFormat numberFormat = new DecimalFormat("0.00");

	/**
	 * Method to get Usage Report
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public List getUsageReport(String date, ReportForm reportForm)
			throws Exception {
		final String MY_NAME = ME + "getUsageReport: ";
		BcwtsLogger.debug(MY_NAME);
		List newCardDataList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		BcwtReportDTO bcwtReportDTO = null;
		boolean flag = false;
		List benefitList = new ArrayList();
		List collectionList = new ArrayList();
		String nextFareCompanyId = null;
		String partnerId = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String queryForGetNextfareId = null;
			if (null != reportForm) {
				if (!Util.isBlankOrNull(reportForm.getReportFormat())) {
					flag = true;
					queryForGetNextfareId = "select tmaComp.id.companyId,partnerComp.nextfareCompanyId,"
							+ "partnerComp.companyName,tmaInfo.tmaName from "
							+ "TmaInformation tmaInfo, TmaCompanyLink tmaComp, PartnerCompanyInfo partnerComp "
							+ "where tmaInfo.tmaId = tmaComp.id.tmaId and partnerComp.companyId = tmaComp.id.companyId";

				}
				if (!Util.isBlankOrNull(reportForm.getTmaName())
						&& flag == true) {
					queryForGetNextfareId = queryForGetNextfareId
							+ " and lower(tmaInfo.tmaName) like '"
							+ reportForm.getTmaName().trim().toLowerCase()
							+ "%'";
				}
				if (reportForm.getPartnerCompanyName() != null
						&& !Util.isBlankOrNull(reportForm
								.getPartnerCompanyName())) {
					queryForGetNextfareId = queryForGetNextfareId
							+ " and lower(partnerComp.companyName) like '"
							+ reportForm.getPartnerCompanyName().trim()
									.toLowerCase() + "%'";
				}
				newCardDataList = session.createQuery(queryForGetNextfareId)
						.list();
				if (newCardDataList != null && !newCardDataList.isEmpty()) {
					for (Iterator iter = newCardDataList.iterator(); iter
							.hasNext();) {
						bcwtReportDTO = new BcwtReportDTO();
						Object element[] = (Object[]) iter.next();
						if (null != element[3]
								&& !Util.isBlankOrNull(element[3].toString())) {
							bcwtReportDTO.setTmaName(element[3].toString());
						} else {
							bcwtReportDTO.setTmaName(" ");
						}
						if (null != element[1]
								&& !Util.isBlankOrNull(element[1].toString())) {
							nextFareCompanyId = element[1].toString();
						}
						if (null != element[0]
								&& !Util.isBlankOrNull(element[0].toString())) {
							String query1 = MartaQueries.GET_NEW_CARD_PARTNERADMIN_QUERY
									+ " and partnerAdmin.partnerCompanyInfo.companyId = "
									+ element[0];
							if (reportForm.getPartnerFirstName() != null
									&& !Util.isBlankOrNull(reportForm
											.getPartnerFirstName())) {
								query1 = query1
										+ " and lower(partnerAdmin.firstName) like '"
										+ reportForm.getPartnerFirstName()
												.trim().toLowerCase() + "%'";
							}
							if (reportForm.getPartnerLastName() != null
									&& !Util.isBlankOrNull(reportForm
											.getPartnerLastName())) {
								query1 = query1
										+ " and lower(partnerAdmin.lastName) like '"
										+ reportForm.getPartnerLastName()
												.trim().toLowerCase() + "%'";
							}
							List partnerAdminList = session.createQuery(query1)
									.list();
							if (partnerAdminList != null
									&& !partnerAdminList.isEmpty()) {
								for (Iterator it = partnerAdminList.iterator(); it
										.hasNext();) {
									Object[] partnerAdminObj = (Object[]) it
											.next();
									if (partnerAdminObj[0] != null
											&& !Util.isBlankOrNull(partnerAdminObj[0]
													.toString())) {
										if (partnerAdminObj[1] != null
												&& !Util.isBlankOrNull(partnerAdminObj[1]
														.toString())) {
											bcwtReportDTO
													.setPartnerAdminName(partnerAdminObj[0]
															.toString()
															+ " "
															+ partnerAdminObj[1]
																	.toString());
										} else {
											bcwtReportDTO
													.setPartnerAdminName(partnerAdminObj[0]
															.toString());
										}
									} else {
										if (partnerAdminObj[1] != null
												&& !Util.isBlankOrNull(partnerAdminObj[1]
														.toString())) {
											bcwtReportDTO
													.setPartnerAdminName(partnerAdminObj[1]
															.toString());
										} else {
											bcwtReportDTO
													.setPartnerAdminName(" ");
										}
									}
									if (nextFareCompanyId != null
											&& partnerAdminObj[3] != null
											&& !Util.isBlankOrNull(partnerAdminObj[3]
													.toString())) {
										partnerId = partnerAdminObj[3]
												.toString();
										benefitList = getUsageSummaryReportFromNextfare(
												nextFareCompanyId, date,
												partnerId, reportForm);

									}
								}
							}
						}
						if (benefitList != null && !benefitList.isEmpty()) {
							collectionList.addAll(benefitList);
							break;
						}
					}

				}

			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return collectionList;
	}

	/**
	 * Method to get Usage Summery Report for Usage Report
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	public List getUsageSummaryReportFromNextfare(String nextFareCompanyId,
			String date, String partnerId, ReportForm reportForm)
			throws Exception {

		final String MY_NAME = ME + "getsUsageList:";
		BcwtsLogger.debug(MY_NAME);
		List activeBenefitsList = new ArrayList();
		BcwtUsageBenifitDTO bcwtUsageBenifitDTO = null;
		Connection con = null;
		int benefit_Id = 0;
		String benefitType = null;
		String firstDate = null;
		String mont = null;
		String dd = null;
		String yr = null;
		String monthUsage = null;
		ResultSet rs0 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ArrayList allList = new ArrayList();
		ArrayList activeList = new ArrayList();
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date today = df.parse(date);
		DateFormat dfts = new SimpleDateFormat("dd/M/yyyy");
		StringTokenizer st = new StringTokenizer(dfts.format(today), "//");
		while (st.hasMoreTokens()) {
			dd = st.nextToken();
			mont = st.nextToken();
			yr = st.nextToken();
			firstDate = mont + "/" + 01 + "/" + yr;
		}
		Date ftdate = df.parse(firstDate);
		try {

			con = NextFareConnection.getConnection();
			Statement stmt = con.createStatement();
			String qury = "select mb.CUSTOMER_MEMBER_ID,mb.SERIAL_NBR,mb.FIRST_NAME,mb.LAST_NAME "
					+ "from nextfare_main.Member mb "
					+ "where mb.CUSTOMER_ID = "
					+ nextFareCompanyId
					+ " and mb.MEMBER_ID = " + partnerId;
			if (reportForm.getEmployeeName() != null
					&& !Util.isBlankOrNull(reportForm.getEmployeeName())) {
				qury = qury + " and lower(mb.FIRST_NAME) like '"
						+ reportForm.getEmployeeName().trim().toLowerCase()
						+ "%'or " + "lower(mb.LAST_NAME) like '"
						+ reportForm.getEmployeeName().trim().toLowerCase()
						+ "%'";
			}
			if (reportForm.getEmployeeId() != null
					&& !Util.isBlankOrNull(reportForm.getEmployeeId())) {
				qury = qury + " and lower(mb.CUSTOMER_MEMBER_ID) like '"
						+ reportForm.getEmployeeId().trim().toLowerCase()
						+ "%'";
			}
			if (con != null) {
				if (stmt != null) {
					rs0 = stmt.executeQuery(qury);
				}
			}
			if (rs0 != null) {
				while (rs0.next()) {
					bcwtUsageBenifitDTO = new BcwtUsageBenifitDTO();
					if (rs0.getString(1) != null
							&& !Util.isBlankOrNull(rs0.getString(1))) {
						bcwtUsageBenifitDTO.setEmployeeId(rs0.getString(1));
					} else {
						bcwtUsageBenifitDTO.setEmployeeId(" ");
					}
					if (rs0.getString(2) != null
							&& !Util.isBlankOrNull(rs0.getString(2))) {
						bcwtUsageBenifitDTO.setBreezecardSerialNo(rs0
								.getString(2));
					} else {
						bcwtUsageBenifitDTO.setBreezecardSerialNo(" ");
					}
					if ((rs0.getString(3) != null && !Util.isBlankOrNull(rs0
							.getString(3)))
							|| (rs0.getString(4) != null && !Util
									.isBlankOrNull(rs0.getString(4)))) {
						bcwtUsageBenifitDTO.setEmployeeName(rs0.getString(3)
								+ rs0.getString(4));
					} else {
						bcwtUsageBenifitDTO.setEmployeeName(" ");
					}

					Statement stmt1 = con.createStatement();
					Statement stmtAll = con.createStatement();
					/*
					 * String query =
					 * "select distinct(member.SERIAL_NBR), member.EFFECTIVE_DTM, member.BENEFIT_ID from "
					 * +
					 * "nextfare_main.Member_Benefit_History member where member.CUSTOMER_ID = "
					 * +Integer.valueOf(nextFareCompanyId)+" and " +
					 * "member.EFFECTIVE_DTM > to_date('"
					 * +dfts.format(ftdate)+"','DD-MM-YYYY') and " +
					 * "EFFECTIVE_DTM < last_day(to_date('"
					 * +dfts.format(today)+"','DD-MM-YYYY'))";
					 */

					/*
					 * String query =
					 * "select MEMBER.SERIAL_NBR from nextfare_main.member member where MEMBER.CUSTOMER_ID = "
					 * +nextFareCompanyId+" and MEMBER.SERIAL_NBR in " +
					 * " (select  USE_TRANSACTION.SERIAL_NBR  from nextfare_main.use_transaction use_transaction"
					 * +
					 * " where  USE_TRANSACTION.TRANSACTION_DTM > to_date('"+dfts
					 * .format(ftdate)+"','DD-MM-YYYY') and "+
					 * " USE_TRANSACTION.JOURNEY_START_DTM < last_day(to_date('"
					 * +dfts.format(today)+"','DD-MM-YYYY'))) ";
					 */

					String queryActive = "select MEMBER.SERIAL_NBR from nextfare_main.member member where MEMBER.CUSTOMER_ID = "
							+ nextFareCompanyId
							+ " and MEMBER.SERIAL_NBR in "
							+ " (select  USE_TRANSACTION.SERIAL_NBR  from nextfare_main.use_transaction use_transaction"
							+ " where  USE_TRANSACTION.TRANSACTION_DTM > to_date('"
							+ dfts.format(ftdate)
							+ "','DD-MM-YYYY') and "
							+ " USE_TRANSACTION.TRANSACTION_DTM < last_day(to_date('"
							+ dfts.format(today) + "','DD-MM-YYYY'))) ";

					String queryAll = "select member.serial_nbr from  nextfare_main.member member where  MEMBER.CUSTOMER_ID = "
							+ nextFareCompanyId;

					rs = stmt.executeQuery(queryActive);

					rs1 = stmtAll.executeQuery(queryAll);

					// System.out.println("fff"+rs.getFetchSize());

					if (rs != null) {

						while (rs.next()) {
							activeList.add(rs.getString(1));
						}

					}
					rs.close();
					// System.out.println("size: "+activeList.size());
					if(activeList.size() > 0) {
					if (rs1 != null) {

						while (rs1.next()) {
							bcwtUsageBenifitDTO = new BcwtUsageBenifitDTO();
							// System.out.println(rs1.getString(1));
							// String temp = rs1.getString(1);
							for (Iterator iter = activeList.iterator(); iter
									.hasNext();) {
								if (!iter.next().equals(rs1.getString(1))) {

									bcwtUsageBenifitDTO.setMonthlyUsage("Yes");
								} else {
									bcwtUsageBenifitDTO.setMonthlyUsage("No");
								}

								DateFormat dft = new SimpleDateFormat("MMM yy");
								bcwtUsageBenifitDTO.setBreezecardSerialNo(rs1
										.getString(1));
								bcwtUsageBenifitDTO
										.setBenefitName("Annual Pass");
								bcwtUsageBenifitDTO.setBillingMonthYear(dft
										.format(today));
							}

							activeBenefitsList.add(bcwtUsageBenifitDTO);
						}
					}
					}
					rs1.close();
				}
			}
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {

			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return activeBenefitsList;
	}

	public List<BcwtsPartnerIssueDTO> getTMAIssueList(String fromDate,
			String toDate) throws Exception {
		final String MY_NAME = ME + "getTMAIssueList: ";
		BcwtsLogger.debug(MY_NAME);
		List<BcwtsPartnerIssueDTO> bcwtsPartnerIssueList = new ArrayList<BcwtsPartnerIssueDTO>();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtsPartnerIssue bcwtsPartnerIssue where "
					+ "  to_date(bcwtsPartnerIssue.creationdate) between to_date('"
					+ fromDate + "','mm/dd/yyyy') and to_date('" + toDate
					+ "','mm/dd/yyyy')";
			;

			List<BcwtsPartnerIssue> queryList = session.createQuery(query)
					.list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				BcwtsPartnerIssue bcwtsPartnerIssue = (BcwtsPartnerIssue) iter
						.next();
			//	bcwtsPartnerIssue.setCreationdate(Util.getFormattedDateForDate(bcwtsPartnerIssue.getCreationdate()) );
				BcwtsPartnerIssueDTO bcwtsPartnerIssueDTO = new BcwtsPartnerIssueDTO();
				BeanUtils.copyProperties(bcwtsPartnerIssueDTO,
						bcwtsPartnerIssue);
				bcwtsPartnerIssueDTO.setCreationdate(bcwtsPartnerIssue.getCreationdate() );
				bcwtsPartnerIssueDTO.setSerialnumber(Util.insertSpaceInBreezeCardSerialNo(bcwtsPartnerIssue.getSerialnumber()));
				if (bcwtsPartnerIssue.getIssuestatus().equalsIgnoreCase(
						"Replied")) {
					String adminName = bcwtsPartnerIssue.getAdminid()
							.getFirstname()
							+ " "
							+ bcwtsPartnerIssue.getAdminid().getLastname();
					bcwtsPartnerIssueDTO.setAdminName(adminName);
				} else {
					bcwtsPartnerIssueDTO.setAdminName("");
					bcwtsPartnerIssueDTO.setResolution("");

				}
				bcwtsPartnerIssueList.add(bcwtsPartnerIssueDTO);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got new card details list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return bcwtsPartnerIssueList;
	}

	/**
	 * Method to get HotList Report
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	// method for HotList Report

	public List getTMAHotListReport(ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getTMAHotListReport: ";
		BcwtsLogger.debug(MY_NAME);
		List hotList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List list = null;
		BcwtHotListReport bcwtHotListReport = null;
		String statusName = null;
		String fromHotListDate = null;
		String toHotlistDate = null;
		String tmaid = reportForm.getTmaId();
		boolean flag = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select bcwtHotListCardDetails.firstname, bcwtHotListCardDetails.lastname, bcwtHotListCardDetails.serialnumber, bcwtHotListCardDetails.companyname, bcwtHotListCardDetails.hotlisteddate, bcwtHotListCardDetails.adminname from BcwtHotListCardDetails bcwtHotListCardDetails, TmaCompanyLink tcl"
					+ " where bcwtHotListCardDetails.partnerCompanyinfo.companyId = tcl.id.companyId and tcl.id.tmaId ='"
					+ tmaid + "' ";
			if (null != reportForm) {

				if (null != reportForm.getFromDate()) {
					fromHotListDate = reportForm.getFromDate();
					toHotlistDate = reportForm.getToDate();
					if (null != fromHotListDate && !fromHotListDate.equals("")) {
						query = query
								+ " and to_date(bcwtHotListCardDetails.hotlisteddate) between to_date('"
								+ fromHotListDate
								+ "','mm/dd/yyyy') and to_date('"
								+ toHotlistDate + "','mm/dd/yyyy')";
					}
				}

			}
			list = new ArrayList();
			list = session.createQuery(query).list();
			if (list != null && !list.isEmpty()) {
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					bcwtHotListReport = new BcwtHotListReport();
					Object element[] = (Object[]) iter.next();
					if (null != element[0]
							&& !Util.isBlankOrNull(element[0].toString())) {
						bcwtHotListReport.setFirstName(element[0].toString());
					} else {
						bcwtHotListReport.setFirstName(" ");
					}
					if (null != element[1]
							&& !Util.isBlankOrNull(element[1].toString())) {
						bcwtHotListReport.setLastName(element[1].toString());
					} else {
						bcwtHotListReport.setLastName(" ");
					}
					if (null != element[4]
							&& !Util.isBlankOrNull(element[4].toString())) {
						bcwtHotListReport.setHotListDate(Util
								.getFormattedDate(element[4]));
					} else {
						bcwtHotListReport.setHotListDate(" ");
					}
					if (null != element[2]
							&& !Util.isBlankOrNull(element[2].toString())) {
						bcwtHotListReport.setCardSerialNumber(element[2]
								.toString());
					} else {
						bcwtHotListReport.setCardSerialNumber(" ");
					}
					if (null != element[5]
							&& !Util.isBlankOrNull(element[5].toString())) {
						bcwtHotListReport.setAdminName(element[5].toString());
					} else {
						bcwtHotListReport.setAdminName(" ");
					}
					if (null != element[3]
							&& !Util.isBlankOrNull(element[3].toString())) {
						bcwtHotListReport.setCompanyName(element[3].toString());
					} else {
						bcwtHotListReport.setCompanyName(" ");
					}
					hotList.add(bcwtHotListReport);
				}

			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return hotList;
	}

	public List getHotListReport(ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getHotListReport: ";
		BcwtsLogger.debug(MY_NAME);
		List hotList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List list = null;
		BcwtHotListReport bcwtHotListReport = null;
		String statusName = null;
		String hotListDate = null;
		boolean flag = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select bcwtHotListCardDetails.firstname,bcwtHotListCardDetails.lastname,bcwtHotListCardDetails.pointofsale,bcwtHotListCardDetails.hotlisteddate,"
					+ " bcwtHotListCardDetails.serialnumber,bcwtHotListCardDetails.adminname from BcwtHotListCardDetails bcwtHotListCardDetails "
					+ "where bcwtHotListCardDetails.hotlistcarddetailsid is not null";
			if (null != reportForm) {
				if (null != reportForm.getHotlistActionType()) {
					flag = true;
				}
				if (null != reportForm.getFirstName() && flag == true) {
					query = query
							+ "  and lower(bcwtHotListCardDetails.firstname)like '"
							+ reportForm.getFirstName().trim().toLowerCase()
							+ "%'";
					flag = true;
				} else if (null != reportForm.getFirstName()) {
					query = query
							+ " and lower(bcwtHotListCardDetails.firstname)like '"
							+ reportForm.getFirstName().trim().toLowerCase()
							+ "%'";
					flag = true;
				}
				if (null != reportForm.getLastName() && flag == true) {
					query = query
							+ " and lower(bcwtHotListCardDetails.lastname)like '"
							+ reportForm.getLastName().trim().toLowerCase()
							+ "%'";
					flag = true;
				} else if (null != reportForm.getLastName()) {
					query = query
							+ " and lower(bcwtHotListCardDetails.lastname)like '"
							+ reportForm.getLastName().trim().toLowerCase()
							+ "%'";
					flag = true;
				}
				if (null != reportForm.getHotListDate()) {
					hotListDate = reportForm.getHotListDate();
					if (null != hotListDate && !hotListDate.equals("")) {
						query = query
								+ "and to_date(bcwtHotListCardDetails.hotlisteddate) = to_date('"
								+ hotListDate + "','mm/dd/yyyy')";
					}
				}
				if (null != reportForm.getBreezeCardSerialNumber()
						&& flag == true) {
					query = query
							+ " and lower(bcwtHotListCardDetails.serialnumber)like '"
							+ reportForm.getBreezeCardSerialNumber().trim()
									.toLowerCase() + "%'";
					flag = true;
				} else if (null != reportForm.getBreezeCardSerialNumber()) {
					query = query
							+ " and lower(bcwtHotListCardDetails.serialnumber)like '"
							+ reportForm.getBreezeCardSerialNumber().trim()
									.toLowerCase() + "%'";
					flag = true;
				}
				if (null != reportForm.getAdminName() && flag == true) {
					query = query
							+ " and lower(bcwtHotListCardDetails.adminname)like '"
							+ reportForm.getAdminName().trim().toLowerCase()
							+ "%'";
					flag = true;
				} else if (null != reportForm.getAdminName()) {
					query = query
							+ " and lower(bcwtHotListCardDetails.adminname)like '"
							+ reportForm.getAdminName().trim().toLowerCase()
							+ "%'";
					flag = true;
				}
				if (null != reportForm.getPointOfSale()
						&& !reportForm.getPointOfSale().equals("")
						&& flag == true) {
					if (reportForm.getPointOfSale().equals("1") && flag == true) {
						statusName = "IS";
						query = query
								+ " and lower(bcwtHotListCardDetails.pointofsale)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (reportForm.getPointOfSale().equals("2")
							&& flag == true) {
						statusName = "GBS";
						query = query
								+ " and lower(bcwtHotListCardDetails.pointofsale) like '"
								+ statusName.trim().toLowerCase() + "'";
					}
					if (reportForm.getPointOfSale().equals("3") && flag == true) {
						statusName = "PS";
						query = query
								+ " and lower(bcwtHotListCardDetails.pointofsale) like '"
								+ statusName.trim().toLowerCase() + "'";
					}
				} else if (null != reportForm.getPointOfSale()
						&& !reportForm.getPointOfSale().equals("")) {
					if (reportForm.getPointOfSale().equals("1")) {
						statusName = "IS";
						query = query
								+ " and lower(bcwtHotListCardDetails.pointofsale)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (reportForm.getPointOfSale().equals("2")) {
						statusName = "GBS";
						query = query
								+ " and lower(bcwtHotListCardDetails.pointofsale)like '"
								+ statusName.trim().toLowerCase() + "'";
					} else if (reportForm.getPointOfSale().equals("3")) {
						statusName = "PS";
						query = query
								+ " and lower(bcwtHotListCardDetails.pointofsale) like '"
								+ statusName.trim().toLowerCase() + "'";
					}
				}
				list = new ArrayList();
				list = session.createQuery(query).list();
				if (list != null && !list.isEmpty()) {
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						bcwtHotListReport = new BcwtHotListReport();
						Object element[] = (Object[]) iter.next();
						if (null != element[0]
								&& !Util.isBlankOrNull(element[0].toString())) {
							bcwtHotListReport.setFirstName(element[0]
									.toString());
						} else {
							bcwtHotListReport.setFirstName(" ");
						}
						if (null != element[1]
								&& !Util.isBlankOrNull(element[1].toString())) {
							bcwtHotListReport
									.setLastName(element[1].toString());
						} else {
							bcwtHotListReport.setLastName(" ");
						}
						if (null != element[2]
								&& !Util.isBlankOrNull(element[2].toString())) {
							bcwtHotListReport.setPointOfSale(element[2]
									.toString());
						} else {
							bcwtHotListReport.setPointOfSale(" ");
						}
						if (null != element[3]
								&& !Util.isBlankOrNull(element[3].toString())) {
							bcwtHotListReport.setHotListDate(Util
									.getFormattedDate(element[3]));
						} else {
							bcwtHotListReport.setHotListDate(" ");
						}
						if (null != element[4]
								&& !Util.isBlankOrNull(element[0].toString())) {
							bcwtHotListReport.setCardSerialNumber(element[4]
									.toString());
						} else {
							bcwtHotListReport.setCardSerialNumber(" ");
						}
						if (null != element[5]
								&& !Util.isBlankOrNull(element[5].toString())) {
							bcwtHotListReport.setAdminName(element[5]
									.toString());
						} else {
							bcwtHotListReport.setAdminName(" ");
						}
						hotList.add(bcwtHotListReport);
					}
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return hotList;
	}

	/**
	 * Method to get New card Report
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	// method for New Card Report

	public List getNewCardReport(ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getNewCardReport: ";
		BcwtsLogger.debug(MY_NAME);
		List newCardList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List newCardDataList = null;
		List benefitList = null;
		BcwtNewCardReportDTO bcwtNewCardReportDTO = null;
		String nextFareCompanyId = null;
		String partnerId = null;
		String benefitName = null;
		BcwtNewCardBenefitDTO bcwtNewCardBenefitDTO = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = MartaQueries.GET_NEW_CARD_QUERY;
			if (reportForm.getCompanyName() != null
					&& !Util.isBlankOrNull(reportForm.getCompanyName())) {
				query = query + " and lower(tmaInfo.tmaName) like '"
						+ reportForm.getCompanyName().trim().toLowerCase()
						+ "%'";

			}
			if (reportForm.getSubCompanyName() != null
					&& !Util.isBlankOrNull(reportForm.getSubCompanyName())) {
				query = query + " and lower(partnerComp.companyName) like '"
						+ reportForm.getSubCompanyName().trim().toLowerCase()
						+ "%'";
			}

			newCardDataList = session.createQuery(query).list();
			if (newCardDataList != null && !newCardDataList.isEmpty()) {
				for (Iterator iter = newCardDataList.iterator(); iter.hasNext();) {
					bcwtNewCardReportDTO = new BcwtNewCardReportDTO();
					Object element[] = (Object[]) iter.next();
					if (null != element[3]
							&& !Util.isBlankOrNull(element[3].toString())) {
						bcwtNewCardReportDTO.setCompanyName(element[3]
								.toString());
					} else {
						bcwtNewCardReportDTO.setCompanyName(" ");
					}
					if (null != element[1]
							&& !Util.isBlankOrNull(element[1].toString())) {
						nextFareCompanyId = element[1].toString();
					}
					if (null != element[0]
							&& !Util.isBlankOrNull(element[0].toString())) {
						String query1 = MartaQueries.GET_NEW_CARD_PARTNERADMIN_QUERY
								+ " and partnerAdmin.partnerCompanyInfo.companyId = "
								+ element[0];
						if (reportForm.getPartnerFirstName() != null
								&& !Util.isBlankOrNull(reportForm
										.getPartnerFirstName())) {
							query1 = query1
									+ " and lower(partnerAdmin.firstName) like '"
									+ reportForm.getPartnerFirstName().trim()
											.toLowerCase() + "%'";
						}
						if (reportForm.getPartnerLastName() != null
								&& !Util.isBlankOrNull(reportForm
										.getPartnerLastName())) {
							query1 = query1
									+ " and lower(partnerAdmin.lastName) like '"
									+ reportForm.getPartnerLastName().trim()
											.toLowerCase() + "%'";
						}

						List partnerAdminList = session.createQuery(query1)
								.list();
						if (partnerAdminList != null
								&& !partnerAdminList.isEmpty()) {
							for (Iterator it = partnerAdminList.iterator(); it
									.hasNext();) {
								Object[] partnerAdminObj = (Object[]) it.next();
								if (partnerAdminObj[0] != null
										&& !Util.isBlankOrNull(partnerAdminObj[0]
												.toString())) {
									bcwtNewCardReportDTO
											.setPartnerAdminName(partnerAdminObj[0]
													.toString());
								} else {
									bcwtNewCardReportDTO
											.setPartnerAdminName(partnerAdminObj[1]
													.toString());
								}
								if (partnerAdminObj[1] != null
										&& !Util.isBlankOrNull(partnerAdminObj[1]
												.toString())) {
									bcwtNewCardReportDTO
											.setPartnerAdminName(partnerAdminObj[1]
													.toString());
									// partnerAdminObj[0].toString()+" "+
								} else {
									bcwtNewCardReportDTO
											.setPartnerAdminName(partnerAdminObj[0]
													.toString());
								}
								if (nextFareCompanyId != null
										&& partnerAdminObj[3] != null
										&& !Util.isBlankOrNull(partnerAdminObj[3]
												.toString())) {
									partnerId = partnerAdminObj[3].toString();
									benefitList = getBenefitDetails(
											nextFareCompanyId, partnerId,
											reportForm);

									if (benefitList != null
											&& !benefitList.isEmpty()) {
										for (Iterator benefitlist = benefitList
												.iterator(); benefitlist
												.hasNext();) {
											bcwtNewCardBenefitDTO = (BcwtNewCardBenefitDTO) benefitlist
													.next();

											if (bcwtNewCardBenefitDTO
													.getEmployeeId() != null
													&& !Util.isBlankOrNull(bcwtNewCardBenefitDTO
															.getEmployeeId())) {
												bcwtNewCardReportDTO
														.setEmployeeId(bcwtNewCardBenefitDTO
																.getEmployeeId());
											} else {
												bcwtNewCardReportDTO
														.setEmployeeId(" ");
											}
											if (bcwtNewCardBenefitDTO
													.getBreezecardSerialNo() != null
													&& !Util.isBlankOrNull(bcwtNewCardBenefitDTO
															.getBreezecardSerialNo())) {
												bcwtNewCardReportDTO
														.setBreezecardSerialNo(bcwtNewCardBenefitDTO
																.getBreezecardSerialNo());
											} else {
												bcwtNewCardReportDTO
														.setBreezecardSerialNo(" ");
											}
											if (bcwtNewCardBenefitDTO
													.getEmployeeName() != null
													&& !Util.isBlankOrNull(bcwtNewCardBenefitDTO
															.getEmployeeName())) {
												bcwtNewCardReportDTO
														.setEmployeeName(bcwtNewCardBenefitDTO
																.getEmployeeName());
											} else {
												bcwtNewCardReportDTO
														.setEmployeeName(" ");
											}
											if (bcwtNewCardBenefitDTO
													.getEffectiveDate() != null
													&& !Util.isBlankOrNull(bcwtNewCardBenefitDTO
															.getEffectiveDate())) {
												DateFormat format = new SimpleDateFormat(
														"yyyy-mm-dd hh:mm:ss");
												Date fDate = (Date) format
														.parse(bcwtNewCardBenefitDTO
																.getEffectiveDate());
												bcwtNewCardReportDTO
														.setEffectiveDate(Util
																.getFormattedDate(fDate));
											} else {
												bcwtNewCardReportDTO
														.setEffectiveDate(" ");
											}
											if (bcwtNewCardBenefitDTO
													.getBenefitName() != null
													&& !Util.isBlankOrNull(bcwtNewCardBenefitDTO
															.getBenefitName())) {
												bcwtNewCardReportDTO
														.setBenefitName(bcwtNewCardBenefitDTO
																.getBenefitName());
											} else {
												bcwtNewCardReportDTO
														.setBenefitName(" ");
											}

										}
										// bcwtNewCardReportDTO.setBenefitName(benefitName);

									}
								}
							}
						}
					}
					if (benefitList != null && !benefitList.isEmpty()) {
						newCardList.add(bcwtNewCardReportDTO);
					}
				}

			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return newCardList;
	}

	public List getUpassActiveBenefitsList(String partnerId) throws Exception {

		final String MY_NAME = ME + "getActiveBenefitsList:";
		BcwtsLogger.debug(MY_NAME + " for company id" + partnerId);
		List activeBenefitsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		PartnerReportSearchDTO partnerReportSearchDTO = null;
		Connection con = null;
		int count_Month = 0;
		int count_Year = 0;
		int benefit_Id = 0;
		String benefitType = null;
		ListCardsDAO psDAO = new ListCardsDAO();

		try {

			session = getSession();
			transaction = session.beginTransaction();
			con = NextFareConnection.getConnection();
			Statement stmt = con.createStatement();
			String query = " select member.Member_id,member.Customer_member_id,member.Serial_nbr,"
					+ " member.First_name,member.Last_name, INVENTORY.HOTLIST_ACTION, CBD.BENEFIT_NAME  "
					+ "from nextfare_main.Member member, NEXTFARE_MAIN.FARE_MEDIA_INVENTORY inventory, nextfare_main.customer_benefit_definition  cbd "
					+ "where member.Customer_Id = '"
					+ partnerId
					+ "'"
					+ " and INVENTORY.SERIAL_NBR = member.SERIAL_NBR and member.BENEFIT_STATUS_ID = '1'"
					+ " and cbd.customer_id = member.customer_id and CBD.BENEFIT_STATUS_ID = '1'";
			ResultSet rs = stmt.executeQuery(query);
			if (rs != null) {

				while (rs.next()) {
					String holderName = rs.getString(4) + " " + rs.getString(5);
					int memberId = rs.getInt(1);

					// if (rs.getString(6).equals("0")) {
					if (Util.isBlankOrNull(rs.getString(6))
							|| rs.getString(6).equals("0")) {
						count_Year = count_Year + 1;
						partnerReportSearchDTO = new PartnerReportSearchDTO();
						partnerReportSearchDTO.setBenefitType(rs.getString(7));
						partnerReportSearchDTO.setCardHolderName(holderName);
						partnerReportSearchDTO.setCustomerMemberId(rs
								.getString(2));
						partnerReportSearchDTO.setCardSerialNo(rs.getString(3));
						partnerReportSearchDTO.setCountMonth(count_Month);
						partnerReportSearchDTO.setCountYear(count_Year);
						activeBenefitsList.add(partnerReportSearchDTO);
					}

				}

			}

			rs.close();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return activeBenefitsList;
	}

	/**
	 * Method to get Active Benefits Report
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	// method to get connected with nextfare table

	public List getActiveBenefitsList(ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getPartnerNewCardReportList: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		List benefitList = new ArrayList();
		List collectionList = new ArrayList();
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String queryForGetNextfareId = null;
			if (null != reportForm) {
				if (!Util.isBlankOrNull(reportForm.getReportFormat())) {
					flag = true;

					queryForGetNextfareId = "select "
							+ "partnerComp.nextfareCompanyId " + "from "
							+ "TmaInformation tmaInfo, "
							+ "TmaCompanyLink tmaComp, "
							+ "PartnerCompanyInfo partnerComp " + "where "
							+ "tmaInfo.tmaId = tmaComp.id.tmaId " + "and "
							+ "partnerComp.companyId = tmaComp.id.companyId";
				}
				if (!Util.isBlankOrNull(reportForm.getTmaName())
						&& flag == true) {
					queryForGetNextfareId = queryForGetNextfareId
							+ " and lower(tmaInfo.tmaName) like '%"
							+ reportForm.getTmaName().trim().toLowerCase()
							+ "%'";
				}

				if (!Util.isBlankOrNull(reportForm.getPartnerFirstName())
						&& flag == true) {

					queryForGetNextfareId = "select "
							+ "partnerCompInfo.nextfareCompanyId "
							+ "from "
							+ "PartnerAdminDetails partnerAdminDetails, "
							+ "PartnerCompanyInfo partnerCompInfo "
							+ "where "
							+ "lower(partnerAdminDetails.firstName) like '%"
							+ reportForm.getPartnerFirstName().trim()
									.toLowerCase()
							+ "%' "
							+ "and partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";

				}
				if (!Util.isBlankOrNull(reportForm.getPartnerLastName())
						&& flag == true) {

					queryForGetNextfareId = "select "
							+ "partnerCompInfo.nextfareCompanyId "
							+ "from "
							+ "PartnerAdminDetails partnerAdminDetails, "
							+ "PartnerCompanyInfo partnerCompInfo "
							+ "where "
							+ "lower(partnerAdminDetails.lastName) like '%"
							+ reportForm.getPartnerLastName().trim()
									.toLowerCase()
							+ "%' "
							+ "and partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";

				}
				if (!Util.isBlankOrNull(reportForm.getPartnerLastName())
						&& !Util.isBlankOrNull(reportForm.getPartnerFirstName())
						&& flag == true) {

					queryForGetNextfareId = "select "
							+ "partnerCompInfo.nextfareCompanyId "
							+ "from "
							+ "PartnerAdminDetails partnerAdminDetails, "
							+ "PartnerCompanyInfo partnerCompInfo "
							+ "where "
							+ "lower(partnerAdminDetails.lastName) like '%"
							+ reportForm.getPartnerLastName().trim()
									.toLowerCase()
							+ "%' "
							+ "and lower(partnerAdminDetails.firstName) like '%"
							+ reportForm.getPartnerFirstName().trim()
									.toLowerCase()
							+ "%' "
							+ "and partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";

				}
				if (!Util.isBlankOrNull(reportForm.getTmaName())
						&& flag == true
						&& !Util.isBlankOrNull(reportForm.getPartnerFirstName())) {

					queryForGetNextfareId = "select "
							+ "distinct partnerComp.nextfareCompanyId "
							+ "from "
							+ "TmaInformation tmaInfo, "
							+ "TmaCompanyLink tmaComp, "
							+ "PartnerCompanyInfo partnerComp,"
							+ "PartnerAdminDetails partnerAdminDetails "
							+ "where "
							+ "tmaInfo.tmaId = tmaComp.id.tmaId "
							+ "and partnerComp.companyId = tmaComp.id.companyId "
							+ "and partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "
							+ "and lower(tmaInfo.tmaName) like '%"
							+ reportForm.getTmaName().trim().toLowerCase()
							+ "%'"
							+ "and lower(partnerAdminDetails.firstName) like '%"
							+ reportForm.getPartnerFirstName().trim()
									.toLowerCase() + "%'";
				}
				if (!Util.isBlankOrNull(reportForm.getTmaName())
						&& flag == true
						&& !Util.isBlankOrNull(reportForm.getPartnerLastName())) {

					queryForGetNextfareId = "select "
							+ "distinct partnerComp.nextfareCompanyId "
							+ "from "
							+ "TmaInformation tmaInfo, "
							+ "TmaCompanyLink tmaComp, "
							+ "PartnerCompanyInfo partnerComp,"
							+ "PartnerAdminDetails partnerAdminDetails "
							+ "where "
							+ "tmaInfo.tmaId = tmaComp.id.tmaId "
							+ "and partnerComp.companyId = tmaComp.id.companyId "
							+ "and partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "
							+ "and lower(tmaInfo.tmaName) like '%"
							+ reportForm.getTmaName().trim().toLowerCase()
							+ "%'"
							+ "and lower(partnerAdminDetails.lastName) like '%"
							+ reportForm.getPartnerLastName().trim()
									.toLowerCase() + "%'";

				}
				if (!Util.isBlankOrNull(reportForm.getTmaName())
						&& flag == true
						&& !Util.isBlankOrNull(reportForm.getPartnerLastName())
						&& !Util.isBlankOrNull(reportForm.getPartnerFirstName())) {

					queryForGetNextfareId = "select "
							+ "distinct partnerComp.nextfareCompanyId "
							+ "from "
							+ "TmaInformation tmaInfo, "
							+ "TmaCompanyLink tmaComp, "
							+ "PartnerCompanyInfo partnerComp,"
							+ "PartnerAdminDetails partnerAdminDetails "
							+ "where "
							+ "tmaInfo.tmaId = tmaComp.id.tmaId "
							+ "and partnerComp.companyId = tmaComp.id.companyId "
							+ "and partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "
							+ "and lower(tmaInfo.tmaName) like '%"
							+ reportForm.getTmaName().trim().toLowerCase()
							+ "%'"
							+ "and lower(partnerAdminDetails.lastName) like '%"
							+ reportForm.getPartnerLastName().trim()
									.toLowerCase()
							+ "%'"
							+ "and lower(partnerAdminDetails.firstName) like '%"
							+ reportForm.getPartnerFirstName().trim()
									.toLowerCase() + "%'";
				}
				List nextfareCompanyIdList1 = session.createQuery(
						queryForGetNextfareId).list();
				if (nextfareCompanyIdList1 != null
						&& !nextfareCompanyIdList1.isEmpty()) {
					for (Iterator itr = nextfareCompanyIdList1.iterator(); itr
							.hasNext();) {
						String nextFareCompanyId = (String) itr.next();
						if (nextFareCompanyId != null
								&& !Util.isBlankOrNull(nextFareCompanyId)) {
							benefitList = getActiveBenefitsListFromNextNew(
									nextFareCompanyId, reportForm.getRegion());
							collectionList.addAll(benefitList);

						}
					}
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got  list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return collectionList;
	}

	/**
	 * Method to get Active Benefits from Next Fare
	 * 
	 * @param
	 * @return List
	 * @throws Exception
	 */

	public List getActiveBenefitsListFromNextNew(String nextFareCompanyId,
			String region) throws Exception {

		final String MY_NAME = ME + "getActiveBenefitsList for nextfare id"
				+ nextFareCompanyId;
		BcwtsLogger.debug(MY_NAME);
		List<PartnerReportSearchDTO> activeBenefitsList = new ArrayList<PartnerReportSearchDTO>();
		PartnerReportSearchDTO partnerReportSearchDTO = null;
		//productName = new HashMap<String,Integer>();
		Connection con = null;
		int count_Month = 0;
		int count_Year = 0;
		int benefit_Id = 0;
		String benefitType = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement st = null;
		ResultSet r = null;
		Statement sts = null;
		ResultSet rt = null;
		
	
		
		try {

			con = NextFareConnection.getConnection();
			stmt = con.createStatement();
			String query = "select m.serial_nbr,m.first_name,m.last_name, m.customer_member_id, mb.benefit_action_id, mb.benefit_id, cbd.benefit_name, op.short_desc, op.fare_instrument_category_id, cc.org_name    "
					+ " from nextfare_main.member m,nextfare_main.member_benefit mb, nextfare_main.customer_benefit_definition "
					+ " cbd,    "
					+ " nextfare_main.fare_instrument fi, nextfare_main.fare_instrument_category op, nextfare_main.customer cc    "
					+ "  where "
					+ "  cc.customer_id = mb.customer_id "
					+ "  and cc.customer_id = m.customer_id "
					+ "  and cbd.customer_id = cc.customer_id"
					+ "  and mb.customer_id = cbd.customer_id     "
					+ "  and m.member_id = mb.member_id    "
					+ "  and m.customer_id = mb.customer_id    "
					+ "  and cbd.fare_instrument_id = fi.fare_instrument_id    "
					+ "  and fi.fare_instrument_category_id = op.fare_instrument_category_id     "
					+ "  and mb.benefit_id = cbd.benefit_id     "
					+ "  and mb.customer_id = '"
					+ nextFareCompanyId
					+ "'"
					+ " and mb.benefit_action_id = '2'";

			if (!region.equals("0")){
				if(region.equals("17"))
				query = query + " and op.fare_instrument_category_id in ('17','18') ";
				else
					query = query + " and op.fare_instrument_category_id = '"+ region+"' ";
			}

			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					// if(rs.getString(6).equals("0")){

					String holderName = rs.getString(2) + " " + rs.getString(3);
					// int memberId = rs.getInt(1);

					count_Year = count_Year + 1;
					partnerReportSearchDTO = new PartnerReportSearchDTO();
					partnerReportSearchDTO.setBenefitType(rs.getString(7));
					partnerReportSearchDTO.setCardHolderName(holderName);
					partnerReportSearchDTO.setCustomerMemberId(rs.getString(4));
					partnerReportSearchDTO.setCardSerialNo(rs.getString(1));
					partnerReportSearchDTO.setCountMonth(count_Month);
					partnerReportSearchDTO.setCountYear(count_Year);
					partnerReportSearchDTO.setFirstName(rs.getString(2));
					partnerReportSearchDTO.setLastName(rs.getString(3));
					partnerReportSearchDTO.setCompanyName(rs.getString(10));

					activeBenefitsList.add(partnerReportSearchDTO);

				}

			}
			if (stmt != null && rs != null) {
				stmt.close();
				rs.close();
			}
			if (st != null && r != null) {
				st.close();
				r.close();
			}
			if (sts != null && rt != null) {
				sts.close();
				rt.close();
			}
			con.close();
			
			 for (Map.Entry<String, Integer> entry : productName.entrySet()){
					for(PartnerReportSearchDTO artnerReportSearchDTO:activeBenefitsList){
						
				        if(null != artnerReportSearchDTO.getBenefitType()){     
						if(entry.getKey().equals(artnerReportSearchDTO.getBenefitType().trim())){
								productName.put(entry.getKey(),entry.getValue()+1); 
				             }
				        }
			 }
			 }

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return activeBenefitsList;
	}

	public List getActiveBenefitsListFromNext(String nextFareCompanyId)
			throws Exception {

		final String MY_NAME = ME + "getActiveBenefitsList for nextfare id"
				+ nextFareCompanyId;
		BcwtsLogger.debug(MY_NAME);
		List activeBenefitsList = new ArrayList();
		PartnerReportSearchDTO partnerReportSearchDTO = null;
		Connection con = null;
		int count_Month = 0;
		int count_Year = 0;
		int benefit_Id = 0;
		String benefitType = null;
		Statement stmt = null;
		ResultSet rs = null;
		Statement st = null;
		ResultSet r = null;
		Statement sts = null;
		ResultSet rt = null;
		try {

			con = NextFareConnection.getConnection();
			stmt = con.createStatement();
			String query = " select member.Member_id,member.Customer_member_id,member.Serial_nbr,"
					+ " member.First_name,member.Last_name, INVENTORY.HOTLIST_ACTION from nextfare_main.Member member, NEXTFARE_MAIN.FARE_MEDIA_INVENTORY inventory where member.Customer_Id = '"
					+ nextFareCompanyId
					+ "'"
					+ " and INVENTORY.SERIAL_NBR = member.SERIAL_NBR  and member.BENEFIT_STATUS_ID = '1'";
			rs = stmt.executeQuery(query);
			if (rs != null) {
				while (rs.next()) {
					// if(rs.getString(6).equals("0")){
					if (Util.isBlankOrNull(rs.getString(6))
							|| rs.getString(6).equals("0")) {
						String holderName = rs.getString(4) + " "
								+ rs.getString(5);
						int memberId = rs.getInt(1);

						count_Year = count_Year + 1;
						partnerReportSearchDTO = new PartnerReportSearchDTO();
						partnerReportSearchDTO.setBenefitType("Annual Pass");
						partnerReportSearchDTO.setCardHolderName(holderName);
						partnerReportSearchDTO.setCustomerMemberId(rs
								.getString(2));
						partnerReportSearchDTO.setCardSerialNo(rs.getString(3));
						partnerReportSearchDTO.setCountMonth(count_Month);
						partnerReportSearchDTO.setCountYear(count_Year);
						activeBenefitsList.add(partnerReportSearchDTO);
					}
				}

			}
			if (stmt != null && rs != null) {
				stmt.close();
				rs.close();
			}
			if (st != null && r != null) {
				st.close();
				r.close();
			}
			if (sts != null && rt != null) {
				sts.close();
				rt.close();
			}
			con.close();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return activeBenefitsList;
	}

	public boolean checkHotlist(String serialNbr) {
		String MY_NAME = "checkHotlist";
		boolean flag = true;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = NextFareConnection.getConnection();

			String query = "SELECT HOTLIST_ACTION from NEXTFARE_MAIN.FARE_MEDIA_INVENTORY where SERIAL_NBR=?";
			stmt = con.prepareStatement(query);
			stmt.setString(1, serialNbr);

			rs = stmt.executeQuery();
			while (rs.next()) {

				if (Util.isBlankOrNull(rs.getString(1))) {
					flag = false;
				}
				if (rs.getString(1).equals("0")) {
					flag = false;

				}

			}

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));

		} finally {
			try {

				rs.close();
				stmt.close();

				NextFareConnection.closeConnection(con);
				BcwtsLogger.debug(MY_NAME + " Resources closed");
			} catch (SQLException ex) {
				BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			}
		}
		return flag;
	}
    
	public List<ProductDetailsReportDTO> getPartnerProductDetails(
			ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getPartnerProductSummary: ";
		BcwtsLogger.debug(MY_NAME);

		Connection con = null;
		ResultSet rs = null;
		List<ProductDetailsReportDTO> productSummaryList = null;
		ProductDetailsReportDTO productDetailsReportDTO = null;
		String fromHotListDate = null;
		String toHotlistDate = null;
		ConfigurationCache configurationCache = null;
		BcwtConfigParamsDTO configParamDTO = new BcwtConfigParamsDTO();
		try {
			configurationCache = new ConfigurationCache();
			configParamDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues.get("MARTA_TMA_CALM");
			fromHotListDate = reportForm.getFromDate();
			toHotlistDate = reportForm.getToDate();
            String companylist = "";
			List<PartnerCompanyInfo> compList = getCompanyList(reportForm.getTmaId());
			
			for(PartnerCompanyInfo temp:compList){
				if(null != temp.getNextfareCompanyId() )
				companylist = companylist+",'"+temp.getNextfareCompanyId()+"'";
			}
			BcwtsLogger.debug("comp list "+companylist);
			productSummaryList = new ArrayList<ProductDetailsReportDTO>();
			con = NextFareConnection.getConnection();
			String query = " select count(*) as benefitCount, mb.first_name, mb.last_name , AU.SERIAL_NBR, mb.customer_member_id , cbd.BENEFIT_NAME, cc.org_name, op.short_desc "
                 +"    from NEXTFARE_MAIN.MEMBER mb, nextfare_main.autoload au, "
                  +"    NEXTFARE_MAIN.CUSTOMER_BENEFIT_DEFINITION cbd,nextfare_main.customer cc,  "
                  +"   nextfare_main.fare_instrument fi, "
                  +"   nextfare_main.fare_instrument_category op "
                  +"      where  mb.serial_nbr = au.serial_nbr and au.INSERTED_DTM  "
                  +"      between to_date('"+fromHotListDate+"','MM/dd/YYYY') and to_date('"+toHotlistDate+"','MM/dd/YYYY') and mb.CUSTOMER_ID in ("+companylist.substring(1)+")  "
                  +"      and au.FARE_INSTRUMENT_ID = cbd.FARE_INSTRUMENT_ID and mb.customer_id = cbd.customer_id  "
                  +"      and au.autoload_action_id in ('1','3')  and au.fare_instrument_id = fi.fare_instrument_id "
                  +"    and fi.fare_instrument_category_id = op.fare_instrument_category_id and cc.customer_id = cbd.customer_id and cbd.fare_instrument_id not in ("+configParamDTO.getParamvalue().trim()+") "
                  +"    GROUP BY cbd.BENEFIT_NAME, mb.first_name, mb.last_name ,AU.SERIAL_NBR, mb.customer_member_id , cbd.BENEFIT_NAME, cc.org_name, op.short_desc ";

			BcwtsLogger.debug("Query "+query);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				productDetailsReportDTO = new ProductDetailsReportDTO();
				     
				productDetailsReportDTO.setCompanyname(rs.getString("ORG_NAME"));
				productDetailsReportDTO.setBenefitname(rs.getString("BENEFIT_NAME"));
				productDetailsReportDTO.setBreezecard(rs.getString("SERIAL_NBR"));
				productDetailsReportDTO.setCount(rs.getInt("BENEFITCOUNT"));
				productDetailsReportDTO.setFirstname(rs.getString("FIRST_NAME"));
				productDetailsReportDTO.setLastname(rs.getString("LAST_NAME"));
				productDetailsReportDTO.setMemberid(rs.getString("CUSTOMER_MEMBER_ID"));
				if(rs.getString("SHORT_DESC").startsWith("CSC"))
					productDetailsReportDTO.setRegion("MARTA");
				else
					productDetailsReportDTO.setRegion(rs.getString("SHORT_DESC"));
				productDetailsReportDTO.setTransactiontype("Add");
				productSummaryList.add(productDetailsReportDTO);
			}

			
			
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		}
		return productSummaryList;
	}
	
	
	public List<ProductDetailsReportDTO> getPartnerProductDetailsCalM(
			ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getPartnerProductDetailsCalM: ";
		BcwtsLogger.debug(MY_NAME);

		Connection con = null;
		ResultSet rs = null;
		List<ProductDetailsReportDTO> productSummaryList = null;
		ProductDetailsReportDTO productDetailsReportDTO = null;
		ConfigurationCache configurationCache = null;
		BcwtConfigParamsDTO configParamDTO = new BcwtConfigParamsDTO();
		try {
			configurationCache = new ConfigurationCache();
			configParamDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues.get("MARTA_TMA_CALM");
            String companylist = "";
			List<PartnerCompanyInfo> compList = getCompanyList(reportForm.getTmaId());
			
			for(PartnerCompanyInfo temp:compList){
				if(null != temp.getNextfareCompanyId() )
				companylist = companylist+",'"+temp.getNextfareCompanyId()+"'";
			}
			BcwtsLogger.debug("comp list "+companylist);
			productSummaryList = new ArrayList<ProductDetailsReportDTO>();
			con = NextFareConnection.getConnection();
		
			String query = " select count(*) as benefitCount,m.serial_nbr, m.first_name, m.last_name, m.customer_member_id, cbd.benefit_name, cc.org_name, op.short_desc from  "
	                   +" nextfare_main.member m, nextfare_main.member_benefit mb, nextfare_main.customer_benefit_definition cbd, nextfare_main.customer cc,    "
	                   +"   nextfare_main.fare_instrument_category op,  nextfare_main.fare_instrument fi    "
	                   +"   where cbd.customer_id = mb.customer_id   "
	                   +"   and m.member_id = mb.member_id  "
	                   +"   and M.CUSTOMER_ID = cbd.customer_id  "
	                   +"   and m.customer_id = cc.customer_id  "
	                   +"   and cbd.benefit_id = MB.BENEFIT_ID   "
	                   +"   and cbd.customer_id = CC.CUSTOMER_ID   "
	                   +"   and CBD.fare_instrument_id = fi.fare_instrument_id  "  
	                   +"   and FI.FARE_INSTRUMENT_CATEGORY_ID = op.FARE_INSTRUMENT_CATEGORY_ID  " 
	                   +"   and cbd.customer_id in ("+companylist.substring(1)+")   "
	                   +"   and cbd.fare_instrument_id in ("+configParamDTO.getParamvalue().trim()+")  " 
	                   +"   and MB.BENEFIT_ACTION_ID = '2'  "
	                   +"   GROUP BY m.serial_nbr, m.first_name, m.last_name, m.customer_member_id, cbd.benefit_name, cc.org_name, op.short_desc" ;
			
			
			BcwtsLogger.debug("Query "+query);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				productDetailsReportDTO = new ProductDetailsReportDTO();
				     
				productDetailsReportDTO.setCompanyname(rs.getString("ORG_NAME"));
				productDetailsReportDTO.setBenefitname(rs.getString("BENEFIT_NAME"));
				productDetailsReportDTO.setBreezecard(rs.getString("SERIAL_NBR"));
				productDetailsReportDTO.setCount(rs.getInt("BENEFITCOUNT"));
				productDetailsReportDTO.setFirstname(rs.getString("FIRST_NAME"));
				productDetailsReportDTO.setLastname(rs.getString("LAST_NAME"));
				productDetailsReportDTO.setMemberid(rs.getString("CUSTOMER_MEMBER_ID"));
				if(rs.getString("SHORT_DESC").startsWith("CSC"))
					productDetailsReportDTO.setRegion("MARTA");
				else
					productDetailsReportDTO.setRegion(rs.getString("SHORT_DESC"));
				productDetailsReportDTO.setTransactiontype("Add");
				productSummaryList.add(productDetailsReportDTO);
			}

			
			
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		}
		return productSummaryList;
	}
	
	public List<ProductDetailsReportDTO> getPartnerProductDetailsRemove(
			ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getPartnerProductDetailsRemove: ";
		BcwtsLogger.debug(MY_NAME);

		Connection con = null;
		ResultSet rs = null;
		List<ProductDetailsReportDTO> productSummaryList = null;
		ProductDetailsReportDTO productDetailsReportDTO = null;
		String fromHotListDate = null;
		String toHotlistDate = null;
		try {
			fromHotListDate = reportForm.getFromDate();
			toHotlistDate = reportForm.getToDate();
            String companylist = "";
			List<PartnerCompanyInfo> compList = getCompanyList(reportForm.getTmaId());
			
			for(PartnerCompanyInfo temp:compList){
				if(null != temp.getNextfareCompanyId() )
				companylist = companylist+",'"+temp.getNextfareCompanyId()+"'";
			}
			BcwtsLogger.debug("comp list "+companylist);
			productSummaryList = new ArrayList<ProductDetailsReportDTO>();
			con = NextFareConnection.getConnection();
			String query = " select count(*) as benefitCount, mb.first_name, mb.last_name , AU.SERIAL_NBR, mb.customer_member_id , cbd.BENEFIT_NAME, cc.org_name, op.short_desc "
                 +"    from NEXTFARE_MAIN.MEMBER mb, nextfare_main.autoload au, "
                  +"    NEXTFARE_MAIN.CUSTOMER_BENEFIT_DEFINITION cbd,nextfare_main.customer cc,  "
                  +"   nextfare_main.fare_instrument fi, "
                  +"   nextfare_main.fare_instrument_category op "
                  +"      where  mb.serial_nbr = au.serial_nbr and au.INSERTED_DTM  "
                  +"     between to_date('"+fromHotListDate+"','MM/dd/YYYY') and to_date('"+toHotlistDate+"','MM/dd/YYYY') and mb.CUSTOMER_ID in ("+companylist.substring(1)+")  "
                  +"      and au.FARE_INSTRUMENT_ID = cbd.FARE_INSTRUMENT_ID and mb.customer_id = cbd.customer_id  "
                  +"      and au.autoload_action_id in ('2')  and au.fare_instrument_id = fi.fare_instrument_id "
                  +"    and fi.fare_instrument_category_id = op.fare_instrument_category_id and cc.customer_id = cbd.customer_id "
                  +"    GROUP BY cbd.BENEFIT_NAME, mb.first_name, mb.last_name ,AU.SERIAL_NBR, mb.customer_member_id , cbd.BENEFIT_NAME, cc.org_name, op.short_desc ";
			BcwtsLogger.debug("Query "+query);
			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				productDetailsReportDTO = new ProductDetailsReportDTO();
				     
				productDetailsReportDTO.setCompanyname(rs.getString("ORG_NAME"));
				productDetailsReportDTO.setBenefitname(rs.getString("BENEFIT_NAME"));
				productDetailsReportDTO.setBreezecard(rs.getString("SERIAL_NBR"));
				productDetailsReportDTO.setCount(rs.getInt("BENEFITCOUNT"));
				productDetailsReportDTO.setFirstname(rs.getString("FIRST_NAME"));
				productDetailsReportDTO.setLastname(rs.getString("LAST_NAME"));
				productDetailsReportDTO.setMemberid(rs.getString("CUSTOMER_MEMBER_ID"));
				if(rs.getString("SHORT_DESC").startsWith("CSC"))
					productDetailsReportDTO.setRegion("MARTA");
				else
					productDetailsReportDTO.setRegion(rs.getString("SHORT_DESC"));
				productDetailsReportDTO.setTransactiontype("Remove");
				productSummaryList.add(productDetailsReportDTO);
			}

			
			
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		}
		return productSummaryList;
	}
	
	public List<DisplayProductSummaryDTO> getPartnerProductSummary(
			ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getPartnerProductSummary: ";
		BcwtsLogger.debug(MY_NAME);

		Connection con = null;
		ResultSet rs = null;
		List<DisplayProductSummaryDTO> productSummaryList = null;
		DisplayProductSummaryDTO displayProductSummaryDTO = null;
		String fromHotListDate = null;
		String toHotlistDate = null;
		ConfigurationCache configurationCache = null;
		BcwtConfigParamsDTO configParamDTO = new BcwtConfigParamsDTO();
		try {
			configurationCache = new ConfigurationCache();
			configParamDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues.get("MARTA_TMA_CALM");
			fromHotListDate = reportForm.getFromDate();
			toHotlistDate = reportForm.getToDate();
            String companylist = "";
			List<PartnerCompanyInfo> compList = getCompanyList(reportForm.getTmaId());
			
			for(PartnerCompanyInfo temp:compList){
				if(null != temp.getNextfareCompanyId() )
				companylist = companylist+",'"+temp.getNextfareCompanyId()+"'";
			}
			BcwtsLogger.debug("comp list "+companylist);
			productSummaryList = new ArrayList<DisplayProductSummaryDTO>();
			con = NextFareConnection.getConnection();
			String query = "select  count(*) , cbd.BENEFIT_NAME, cc.org_name, op.short_desc  "
					+ "from NEXTFARE_MAIN.MEMBER mb, nextfare_main.autoload au, NEXTFARE_MAIN.CUSTOMER_BENEFIT_DEFINITION cbd,nextfare_main.customer cc,  nextfare_main.fare_instrument fi, nextfare_main.fare_instrument_category op "
					+ "   where "
					+ "   mb.serial_nbr = au.serial_nbr and au.INSERTED_DTM "
					+ "   between to_date('"+fromHotListDate+"','MM/dd/YYYY') and to_date('"+toHotlistDate+"','MM/dd/YYYY') and mb.CUSTOMER_ID in ("
					+ companylist.substring(1)
					+ ")  "
					+ "   and au.FARE_INSTRUMENT_ID = cbd.FARE_INSTRUMENT_ID "
					+ "   and mb.customer_id = cbd.customer_id "
					+ "   and au.autoload_action_id in ('1','3') "
					+ "   and au.fare_instrument_id = fi.fare_instrument_id "
                    + " and fi.fare_instrument_category_id = op.fare_instrument_category_id "
					+ " and cc.customer_id = cbd.customer_id and cbd.FARE_INSTRUMENT_ID not in ("+configParamDTO.getParamvalue().trim()+") GROUP BY cbd.BENEFIT_NAME, cc.org_name, op.short_desc ";

			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				displayProductSummaryDTO = new DisplayProductSummaryDTO();
				
					displayProductSummaryDTO.setCount(rs.getInt(1));
					// benefitList.add(rs.getString(1));
					displayProductSummaryDTO.setCompanyName(rs.getString(3));
					displayProductSummaryDTO.setBenefitName(rs.getString(2));
					if(rs.getString(4).startsWith("CSC"))
						displayProductSummaryDTO.setRegion("MARTA");
					else
					displayProductSummaryDTO.setRegion(rs.getString(4));
					

				productSummaryList.add(displayProductSummaryDTO);
			}

			
			
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		}
		return productSummaryList;
	}
	
	public List<DisplayProductSummaryDTO> getPartnerProductSummaryCalM(
			ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getPartnerProductSummary: ";
		BcwtsLogger.debug(MY_NAME);

		Connection con = null;
		ResultSet rs = null;
		List<DisplayProductSummaryDTO> productSummaryList = null;
		DisplayProductSummaryDTO displayProductSummaryDTO = null;
		String fromHotListDate = null;
		String toHotlistDate = null;
		ConfigurationCache configurationCache = null;
		BcwtConfigParamsDTO configParamDTO = new BcwtConfigParamsDTO();
		try {
			configurationCache = new ConfigurationCache();
			configParamDTO = (BcwtConfigParamsDTO) configurationCache.configurationValues.get("MARTA_TMA_CALM");
			fromHotListDate = reportForm.getFromDate();
			toHotlistDate = reportForm.getToDate();
            String companylist = "";
			List<PartnerCompanyInfo> compList = getCompanyList(reportForm.getTmaId());
			
			for(PartnerCompanyInfo temp:compList){
				if(null != temp.getNextfareCompanyId() )
				companylist = companylist+",'"+temp.getNextfareCompanyId()+"'";
			}
			BcwtsLogger.debug("comp list "+companylist);
			productSummaryList = new ArrayList<DisplayProductSummaryDTO>();
			con = NextFareConnection.getConnection();
			String query = " select count(*), cbd.benefit_name, cc.org_name, op.short_desc from "
		            +"  nextfare_main.member_benefit mb, nextfare_main.customer_benefit_definition cbd, nextfare_main.customer cc, " 
		            +"  nextfare_main.fare_instrument_category op,  nextfare_main.fare_instrument fi  "
		            +"  where cbd.customer_id = mb.customer_id "
		            +"  and cbd.benefit_id = MB.BENEFIT_ID "
		            +"  and cbd.customer_id = CC.CUSTOMER_ID "
		            +"  and CBD.fare_instrument_id = fi.fare_instrument_id " 
		            +"  and FI.FARE_INSTRUMENT_CATEGORY_ID = op.FARE_INSTRUMENT_CATEGORY_ID "
		            +"  and cbd.customer_id in ("
					+ companylist.substring(1)
					+ ")  "
		            +"  and cbd.fare_instrument_id in ("+configParamDTO.getParamvalue().trim()+") "
		            +"  and MB.BENEFIT_ACTION_ID = '2' "
		            +"  GROUP BY cbd.benefit_name, cc.org_name,  op.short_desc";

			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				displayProductSummaryDTO = new DisplayProductSummaryDTO();
				
					displayProductSummaryDTO.setCount(rs.getInt(1));
					// benefitList.add(rs.getString(1));
					displayProductSummaryDTO.setCompanyName(rs.getString(3));
					displayProductSummaryDTO.setBenefitName(rs.getString(2));
					if(rs.getString(4).startsWith("CSC"))
						displayProductSummaryDTO.setRegion("MARTA");
					else
					displayProductSummaryDTO.setRegion(rs.getString(4));
					

				productSummaryList.add(displayProductSummaryDTO);
			}

			
			
			
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		}
		return productSummaryList;
	}

	private List getMemberBenefitDetails(String nextFareCompanyId,
			String memberId) throws Exception {
		final String MY_NAME = ME + "getMemberBenefitDetails: ";
		BcwtsLogger.debug(MY_NAME);
		String newCardBenefit = null;
		// Session session = null;
		// Transaction transaction = null;
		Connection con = null;
		ResultSet rs = null;
		List benefitList = null;
		BcwtNewCardBenefitDTO bcwtNewCardBenefitDTO = null;
		try {
			// session = getSession();
			// transaction = session.beginTransaction();

			benefitList = new ArrayList();
			con = NextFareConnection.getConnection();
			String query = "select mb.CUSTOMER_MEMBER_ID,mb.SERIAL_NBR,mb.FIRST_NAME,mb.LAST_NAME "
					+ "from nextfare_main.Member mb "
					+ "where mb.CUSTOMER_ID = "
					+ nextFareCompanyId
					+ " and mb.MEMBER_ID = " + memberId + " and ";

			Statement stmt = con.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				bcwtNewCardBenefitDTO = new BcwtNewCardBenefitDTO();
				if (rs.getString(1) != null
						&& !Util.isBlankOrNull(rs.getString(1))) {
					bcwtNewCardBenefitDTO.setEmployeeId(rs.getString(1));
					// benefitList.add(rs.getString(1));
				} else {
					bcwtNewCardBenefitDTO.setEmployeeId(" ");
					// benefitList.add(" ");
				}
				if (rs.getString(2) != null
						&& !Util.isBlankOrNull(rs.getString(2))) {
					bcwtNewCardBenefitDTO
							.setBreezecardSerialNo(rs.getString(2));
					// benefitList.add(rs.getString(2));
				} else {
					bcwtNewCardBenefitDTO.setBreezecardSerialNo(" ");
				}
				if ((rs.getString(3) != null && !Util.isBlankOrNull(rs
						.getString(3)))
						|| (rs.getString(4) != null && !Util.isBlankOrNull(rs
								.getString(4)))) {
					bcwtNewCardBenefitDTO.setEmployeeName(rs.getString(3)
							+ rs.getString(4));
					// benefitList.add(rs.getString(3));
				} else {
					bcwtNewCardBenefitDTO.setEmployeeName(" ");
				}
			}

		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + e.getMessage());
			throw e;
		}
		return benefitList;
	}

	/**
	 * Method to get Benefit Details
	 * 
	 * @param form
	 * @return List
	 * @throws Exception
	 */
	private List getBenefitDetails(String nextFareCompanyId, String partnerId,
			ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getBenefitDetails: ";
		BcwtsLogger.debug(MY_NAME);
		String newCardBenefit = null;
		// Session session = null;
		// Transaction transaction = null;
		Connection con = null;
		ResultSet rs = null;
		List benefitList = null;
		BcwtNewCardBenefitDTO bcwtNewCardBenefitDTO = null;
		try {
			// session = getSession();
			// transaction = session.beginTransaction();

			benefitList = new ArrayList();
			con = NextFareConnection.getConnection();
			String query = "select mb.CUSTOMER_MEMBER_ID,mb.SERIAL_NBR,mb.FIRST_NAME,mb.LAST_NAME "
					+ "from nextfare_main.Member mb "
					+ "where mb.CUSTOMER_ID = "
					+ nextFareCompanyId
					+ " and mb.MEMBER_ID = " + partnerId;
			if (reportForm.getEmployeeName() != null
					&& !Util.isBlankOrNull(reportForm.getEmployeeName())) {
				query = query + " and lower(mb.FIRST_NAME) like '"
						+ reportForm.getEmployeeName().trim().toLowerCase()
						+ "%'or " + "lower(mb.LAST_NAME) like '"
						+ reportForm.getEmployeeName().trim().toLowerCase()
						+ "%'";
			}
			if (reportForm.getEmployeeId() != null
					&& !Util.isBlankOrNull(reportForm.getEmployeeId())) {
				query = query + " and lower(mb.CUSTOMER_MEMBER_ID) like '"
						+ reportForm.getEmployeeId().trim().toLowerCase()
						+ "%'";
			}
			if (con != null) {
				Statement stmt = con.createStatement();
				if (stmt != null) {
					rs = stmt.executeQuery(query);
				}
			}
			if (rs != null) {
				while (rs.next()) {
					bcwtNewCardBenefitDTO = new BcwtNewCardBenefitDTO();
					if (rs.getString(1) != null
							&& !Util.isBlankOrNull(rs.getString(1))) {
						bcwtNewCardBenefitDTO.setEmployeeId(rs.getString(1));
						// benefitList.add(rs.getString(1));
					} else {
						bcwtNewCardBenefitDTO.setEmployeeId(" ");
						// benefitList.add(" ");
					}
					if (rs.getString(2) != null
							&& !Util.isBlankOrNull(rs.getString(2))) {
						bcwtNewCardBenefitDTO.setBreezecardSerialNo(rs
								.getString(2));
						// benefitList.add(rs.getString(2));
					} else {
						bcwtNewCardBenefitDTO.setBreezecardSerialNo(" ");
					}
					if ((rs.getString(3) != null && !Util.isBlankOrNull(rs
							.getString(3)))
							|| (rs.getString(4) != null && !Util
									.isBlankOrNull(rs.getString(4)))) {
						bcwtNewCardBenefitDTO.setEmployeeName(rs.getString(3)
								+ rs.getString(4));
						// benefitList.add(rs.getString(3));
					} else {
						bcwtNewCardBenefitDTO.setEmployeeName(" ");
					}
					String Qery = " select distinct(mbh.BENEFIT_ID),mbh.EFFECTIVE_DTM"
							+ " from nextfare_main.Member_Benefit_History mbh where mbh.CUSTOMER_ID ="
							+ nextFareCompanyId
							+ "and"
							+ " mbh.MEMBER_ID ="
							+ partnerId
							+ " and mbh.SERIAL_NBR = '"
							+ rs.getString(2) + "'";
					if (reportForm.getMonthYear() != null
							&& reportForm.getMonthYearTo() != null
							&& !Util.isBlankOrNull(reportForm.getMonthYear())
							&& !Util.isBlankOrNull(reportForm.getMonthYearTo())) {
						SimpleDateFormat sdfInput = new SimpleDateFormat(
								"dd/mm/yyyy");
						SimpleDateFormat sdfOutput = new SimpleDateFormat(
								"mm/dd/yyyy");

						Date fromDate = sdfInput.parse("01/"
								+ reportForm.getMonthYear());

						int month = Integer.parseInt(reportForm
								.getEndBillingMonth());
						int year = Integer.parseInt(reportForm
								.getEndBillingYear());
						Date toDate = sdfInput.parse(Util.lastDayOfMonth(month,
								year) + "/" + reportForm.getMonthYearTo());

						String strFromDate = sdfOutput.format(fromDate);
						String strToDate = sdfOutput.format(toDate);
						Qery = Qery + "and mbh.EFFECTIVE_DTM >= TO_DATE('"
								+ strFromDate + "', " + "'MM/DD/YYYY')"
								+ " and mbh.EFFECTIVE_DTM <= TO_DATE('"
								+ strToDate + "', " + "'MM/DD/YYYY')";

					}
					Statement sts = con.createStatement();
					ResultSet rt = sts.executeQuery(Qery);
					if (rt != null) {
						while (rt.next()) {
							if (rt.getString(2) != null
									&& !Util.isBlankOrNull(rt.getString(2))) {
								bcwtNewCardBenefitDTO.setEffectiveDate(rt
										.getString(2));
								// benefitList.add(rt.getString(2));
							} else {
								bcwtNewCardBenefitDTO.setEffectiveDate(" ");
							}
							// benefitList.add(rt.getString(2));
							String Qery1 = " select distinct(cbd.BENEFIT_NAME) from nextfare_main.Customer_Benefit_Definition cbd"
									+ " where cbd.CUSTOMER_ID = "
									+ nextFareCompanyId
									+ " and cbd.BENEFIT_ID = "
									+ rt.getString(1);
							Statement sts1 = con.createStatement();
							ResultSet rt1 = sts1.executeQuery(Qery1);
							if (rt1 != null) {
								while (rt1.next()) {
									if (rt1.getString(1) != null
											&& !Util.isBlankOrNull(rt1
													.getString(1))) {
										bcwtNewCardBenefitDTO
												.setBenefitName(rt1
														.getString(1));
										// benefitList.add(rt1.getString(1));
									} else {
										bcwtNewCardBenefitDTO
												.setBenefitName(" ");
									}
									// benefitList.add(rt1.getString(1));
								}
							}

						}
					}
					benefitList.add(bcwtNewCardBenefitDTO);
				}
			}
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + e.getMessage());
			throw e;
		}
		return benefitList;
	}

	public List getGeneralLedgerReportOld(BcwtReportSearchDTO bcwtReportSearchDTO)throws Exception{
		final String MY_NAME = ME + "getGeneralLedgerReport:";
		BcwtsLogger.debug(MY_NAME);
		List reportList = new ArrayList();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);
		BcwtReportDTO productDetails = null;
		BcwtReportDTO shippingAmount = null;
		BcwtReportDTO breezeCard = null;
		int quantity = 1;		
		double subTot = 00.00;
		double tot = 00.00;
		String subTotalStr = "Sub Total: ";
		String totalStr = "Total: ";
		Session session = null;
		Transaction transaction = null;		
		List finalReportList = new ArrayList();
		
		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();
			
			List ordersList = getOrdersForGeneralLedgerReport(fromDate, toDate);
			
			session = getSession();
			transaction = session.beginTransaction();

			if(ordersList != null && !ordersList.isEmpty()) {
				for (Iterator iter = ordersList.iterator(); iter.hasNext();) {
					BcwtReportDTO orderDetails = (BcwtReportDTO) iter.next();
					
					if(orderDetails != null){
					
						// Set Product Details
						List productDetailsList = getProductAccountNo(orderDetails.getOrderType(), orderDetails.getOrderRequestId(), session);
						for (Iterator productIter = productDetailsList.iterator(); productIter.hasNext();) {
							productDetails = (BcwtReportDTO) productIter.next();
							productDetails.setOrderRequestId(orderDetails.getOrderRequestId());
							productDetails.setOrderType(orderDetails.getOrderType());
							productDetails.setCustomerName(orderDetails.getFirstName() + " " + orderDetails.getLastName());
							productDetails.setApprovalCode(orderDetails.getApprovalCode());
							productDetails.setTransactionDate(orderDetails.getTransactionDate());
							//productDetails.setOrderQty(String.valueOf(quantity));
							
							reportList.add(productDetails);
						}
						
						//Set Cash 
						
					
						
						//Set Shipping charge					
						if(!Util.isBlankOrNull(orderDetails.getShippingAmount())){
							shippingAmount = new BcwtReportDTO();
							shippingAmount.setAccountNumber(getProductAccountNo(Constants.PRODUCT_NAME_SHIPPING, session));
							shippingAmount.setOrderRequestId(orderDetails.getOrderRequestId());
							shippingAmount.setOrderType(orderDetails.getOrderType());
							shippingAmount.setCustomerName(orderDetails.getFirstName() + " " + orderDetails.getLastName());
							shippingAmount.setApprovalCode(orderDetails.getApprovalCode());
							shippingAmount.setTransactionDate(orderDetails.getTransactionDate());
							shippingAmount.setProducts(Constants.PRODUCT_NAME_SHIPPING);
							shippingAmount.setPrice(numberFormat.format(Double.valueOf(orderDetails.getShippingAmount())));
							shippingAmount.setAmount("-" + numberFormat.format(Double.valueOf(orderDetails.getShippingAmount())));
														
							reportList.add(shippingAmount);
						}
						
						
		
						
						
						//Set IS Breezecard
						if(orderDetails.getOrderType().equalsIgnoreCase(Constants.ORDER_TYPE_IS)){
							if(!Util.isBlankOrNull(orderDetails.getAddressId())){
								breezeCard = new BcwtReportDTO();
								breezeCard.setAccountNumber(getProductAccountNo(Constants.PRODUCT_NAME_BREEZECARD, session));
								breezeCard.setOrderRequestId(orderDetails.getOrderRequestId());
								breezeCard.setOrderType(orderDetails.getOrderType());
								breezeCard.setCustomerName(orderDetails.getFirstName() + " " + orderDetails.getLastName());
								breezeCard.setApprovalCode(orderDetails.getApprovalCode());
								breezeCard.setTransactionDate(orderDetails.getTransactionDate());
								breezeCard.setOrderQty(String.valueOf(quantity));
								breezeCard.setProducts(Constants.PRODUCT_NAME_BREEZECARD);
								if(!Util.isBlankOrNull(orderDetails.getPrice())){
									breezeCard.setPrice(numberFormat.format(Double.valueOf(orderDetails.getPrice())));
									breezeCard.setAmount("-" + numberFormat.format(Double.valueOf(orderDetails.getPrice())));
								}
								
								reportList.add(breezeCard);								
							}						
						}
						
						// Set GBS Details
						if(!Util.isBlankOrNull(orderDetails.getOrderType())){						
							if(orderDetails.getOrderType().equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
								setGLRDetailsForGBS(reportList, orderDetails, session);								
							}
						}						
					}
				}
				
				List accNosList = new ArrayList();
				for (Iterator iterator1 = reportList.iterator(); iterator1.hasNext();) {
					BcwtReportDTO bcwtReportDTO1 = (BcwtReportDTO) iterator1.next();
					if(!Util.isBlankOrNull(bcwtReportDTO1.getAccountNumber())) {
					
						
						accNosList.add(bcwtReportDTO1.getAccountNumber());
					}	
				}						
				
				Collections.sort(accNosList);
				
				
				
				Set accNoSet = new HashSet();
				for (int j = 0; j<accNosList.size(); j++) {
					String accountNo = (String) accNosList.get(j);
					
					if(accNoSet.add(accountNo)){
						
						List bcwtReportDTOList = getBcwtReportDTOs(reportList, accountNo);
						
				
						
						
						BcwtReportDTO bcwtReportDTO2 = null;
						for(int k = 0; k < bcwtReportDTOList.size(); k++) {
							bcwtReportDTO2 = (BcwtReportDTO) bcwtReportDTOList.get(k);
						
							if(bcwtReportDTO2 != null) {
								finalReportList.add(bcwtReportDTO2);						
								
								if(bcwtReportDTO2.getProducts().equalsIgnoreCase(Constants.PRODUCT_NAME_BREEZECARD)){
									if(bcwtReportDTO2.getOrderType().equalsIgnoreCase(Constants.ORDER_TYPE_IS) && 
											!Util.isBlankOrNull(bcwtReportDTO2.getPrice())){
										subTot = subTot + Double.parseDouble(bcwtReportDTO2.getPrice());
									} 
									if(bcwtReportDTO2.getOrderType().equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
										subTot = subTot + bcwtReportDTO2.getGbsSubTotal();
									}	
								} else if(bcwtReportDTO2.getProducts().equalsIgnoreCase(Constants.PRODUCT_NAME_GBS_DISCOUNT)){
									subTot = subTot + bcwtReportDTO2.getGbsDiscount();
																		
								} else {		
									if(!Util.isBlankOrNull(bcwtReportDTO2.getPrice())){
										if(null != bcwtReportDTO2.getOrderQty())
										subTot = subTot + (Double.parseDouble(bcwtReportDTO2.getPrice()) * Double.parseDouble(bcwtReportDTO2.getOrderQty()));
										else
										subTot = subTot + Double.parseDouble(bcwtReportDTO2.getPrice());	
									} 
									if(bcwtReportDTO2.getOrderType().equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
										subTot = subTot + bcwtReportDTO2.getGbsSubTotal();
											
									}									
								}
							}	
						}
						
						//Sub Total Amount
						BcwtReportDTO subTotal = new BcwtReportDTO();
						subTotal.setOrderQty(subTotalStr);	
						if(bcwtReportDTO2.getProducts().equalsIgnoreCase(Constants.PRODUCT_NAME_GBS_DISCOUNT)){
							subTotal.setAmount("+" + numberFormat.format(subTot));
							tot = tot - subTot;
						} else {
							subTotal.setAmount("-" + numberFormat.format(subTot));
							tot = tot + subTot;
						}
						finalReportList.add(subTotal);											
						subTot = 00.00;						
					} 
				}	
				
				// Total Amount
				BcwtReportDTO total = new BcwtReportDTO();
				total.setOrderQty(totalStr);
				total.setAmount("-" + numberFormat.format(tot));
				
				finalReportList.add(total);				
			}		
			
			transaction.commit();
			session.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}  finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return finalReportList;
	}
	
	private void setGLRDetailsForGBS(List reportList, BcwtReportDTO orderDetails, Session session) throws Exception {
		final String MY_NAME = ME + "setGLRDetailsForGBS:";
		BcwtsLogger.debug(MY_NAME);		
		String query = null;
		BcwtReportDTO breezeCard = null;
		BcwtReportDTO gbsDiscount = null;
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);		
		double price = 00.00;	
		double amount = 00.00; 
		int quantity = 0;
		double gbsSubTotal = 00.00;
		double gbsDiscountDouble = 00.00;
		try {			
					
			query = 
				"select" +
 	    			" bcwtOrderInfo.noofcards," +
 	    			" bcwtOrderInfo.discountedamount" +
 	    		" from" +
 	    			" BcwtGbsProductDetails bcwtGbsProductDetails," +
 	    			" BcwtOrderInfo bcwtOrderInfo," +
 	    			" BcwtOrder bcwtOrder" +
 	    		" where" +
 	    			" bcwtOrder.orderid = " + orderDetails.getOrderRequestId() +
 	    			" and bcwtOrder.orderid = bcwtOrderInfo.bcwtorder.orderid" +
 	    			" and bcwtOrderInfo.orderinfoid = bcwtGbsProductDetails.bcwtorderinfo.orderinfoid";
		
			List queryList = session.createQuery(query).list();
			
			if(queryList != null && !queryList.isEmpty()) {
				
				for (Iterator iterator = queryList.iterator(); iterator.hasNext();) {
					Object[] element = (Object[]) iterator.next();
					
					// Set GBS Breezecard
					if(!Util.isBlankOrNull(orderDetails.getAddressId())){
						if(element[0] != null) {
							quantity = Integer.parseInt(element[0].toString());
						}						
						breezeCard = new BcwtReportDTO();
						breezeCard.setAccountNumber(getProductAccountNo(Constants.PRODUCT_NAME_BREEZECARD, session));
						breezeCard.setOrderRequestId(orderDetails.getOrderRequestId());
						breezeCard.setOrderType(orderDetails.getOrderType());
						breezeCard.setCustomerName(orderDetails.getFirstName() + " " + orderDetails.getLastName());
						breezeCard.setApprovalCode(orderDetails.getApprovalCode());
						breezeCard.setTransactionDate(orderDetails.getTransactionDate());
						breezeCard.setOrderQty(String.valueOf(quantity));
						breezeCard.setProducts(Constants.PRODUCT_NAME_BREEZECARD);
						if(!Util.isBlankOrNull(orderDetails.getPrice())){
							breezeCard.setPrice(numberFormat.format(Double.valueOf(orderDetails.getPrice())));
						
							price = Double.parseDouble(orderDetails.getPrice());
							amount = price * quantity;
							breezeCard.setAmount("-" + numberFormat.format(amount));
						}	
						
						gbsSubTotal = gbsSubTotal + amount;
						breezeCard.setGbsSubTotal(gbsSubTotal);
						
						reportList.add(breezeCard);						
					}
					
					//Set GBS Discount
					if(element[1] != null) {
						gbsDiscount = new BcwtReportDTO();
						gbsDiscount.setAccountNumber(getProductAccountNo(Constants.PRODUCT_NAME_GBS_DISCOUNT, session));									
						gbsDiscount.setOrderRequestId(orderDetails.getOrderRequestId());
						gbsDiscount.setOrderType(orderDetails.getOrderType());
						gbsDiscount.setCustomerName(orderDetails.getFirstName() + " " + orderDetails.getLastName());
						gbsDiscount.setApprovalCode(orderDetails.getApprovalCode());
						gbsDiscount.setTransactionDate(orderDetails.getTransactionDate());
						gbsDiscount.setProducts(Constants.PRODUCT_NAME_GBS_DISCOUNT);
						gbsDiscount.setAmount("+" + numberFormat.format(Double.valueOf(element[1].toString())));
						
						gbsDiscountDouble = Double.parseDouble(element[1].toString());
						gbsDiscount.setGbsDiscount(gbsDiscountDouble);
						
						reportList.add(gbsDiscount);
					}
					break;
				}
			}			
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * Method to get GeneralLedgerReport
	 * 
	 * @param bcwtReportSearchDTO
	 * @return reportDTOList
	 * @throws Exception
	 */
	public List getGeneralLedgerReport(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {
		final String MY_NAME = ME + "getGeneralLedgerReport:";
		BcwtsLogger.debug(MY_NAME);
		List reportList = new ArrayList();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);
		BcwtReportDTO productDetails = null;
		BcwtReportDTO shippingAmount = null;
		BcwtReportDTO cashAmount = null;
		BcwtReportDTO breezeCard = null;
		int quantity = 1;
		double subTot = 00.00;
		double tot = 00.00;
		String subTotalStr = "Sub Total: ";
		String totalStr = "Total: ";
		Session session = null;
		Transaction transaction = null;
		List finalReportList = new ArrayList();

		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();
			session = getSession();
			transaction = session.beginTransaction();
			List<GLISView> ordersList = getGLView(fromDate, toDate,session);// getOrdersForGeneralLedgerReport(fromDate,
															// toDate);
			
			ArrayList<String> checkDups = new ArrayList<String>();
			ArrayList<String> checkDups3 = new ArrayList<String>();
			ArrayList<String> checkDups2 = new ArrayList<String>();
		//	

			if (ordersList != null && !ordersList.isEmpty()) {

				for (Iterator iter = ordersList.iterator(); iter.hasNext();) {
					GLISView glview = (GLISView) iter.next();
					
					// Set Product Details
					if(!glview.getProductname().equals(Constants.PRODUCT_NAME_STORED_VALUE)){
					productDetails = new BcwtReportDTO();
					// productDetails = (BcwtReportDTO) productIter.next();
					productDetails.setOrderRequestId(glview.getOrderid());
					productDetails.setOrderType(glview.getOrdertype());
					productDetails.setCustomerName(glview.getFirstname() + " "
							+ glview.getLastname());
					productDetails.setApprovalCode(glview.getApprovalcode());
					productDetails.setTransactionDate(String.valueOf(glview
							.getTransactiondate()));
					productDetails.setAccountNumber(glview.getAccountnumber());
					productDetails.setProducts(glview.getProductname());
					productDetails.setPrice(glview.getPrice());
					// productDetails.setOrderQty(String.valueOf(quantity));

					reportList.add(productDetails);
					}
					
					// Set Cash
					if (!Util.isBlankOrNull(glview.getCashvalue())) {
						if(!checkDups.contains(glview.getOrderid())){
							
						checkDups.add(glview.getOrderid().toString());
						cashAmount = new BcwtReportDTO();
						cashAmount.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_STORED_VALUE, session));
						cashAmount.setOrderRequestId(glview.getOrderid());
						cashAmount.setOrderType(glview.getOrdertype());
						cashAmount.setCustomerName(glview.getFirstname()
								+ " " + glview.getLastname());
						cashAmount
								.setApprovalCode(glview.getApprovalcode());
						cashAmount.setTransactionDate(String.valueOf(glview
								.getTransactiondate()));
						cashAmount
								.setProducts(Constants.PRODUCT_NAME_STORED_VALUE);
					
						cashAmount.setPrice(numberFormat.format(Double
								.valueOf(glview.getCashvalue())));
						
						
						
						reportList.add(cashAmount);
					} else if (glview.getAccountnumber().trim().equals(getProductAccountNo(
								Constants.PRODUCT_NAME_STORED_VALUE, session).trim())){
						checkDups.add(glview.getOrderid().toString());
						cashAmount = new BcwtReportDTO();
						cashAmount.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_STORED_VALUE, session));
						cashAmount.setOrderRequestId(glview.getOrderid());
						cashAmount.setOrderType(glview.getOrdertype());
						cashAmount.setCustomerName(glview.getFirstname()
								+ " " + glview.getLastname());
						cashAmount
								.setApprovalCode(glview.getApprovalcode());
						cashAmount.setTransactionDate(String.valueOf(glview
								.getTransactiondate()));
						cashAmount
								.setProducts(Constants.PRODUCT_NAME_STORED_VALUE);
					
						cashAmount.setPrice(numberFormat.format(Double
								.valueOf(glview.getCashvalue())));
						reportList.add(cashAmount);
					}
					}
					
					// Set Shipping charge
					if (!Util.isBlankOrNull(glview.getShippingamount())) {
						if(!checkDups3.contains(glview.getOrderid())){
						
						checkDups3.add(glview.getOrderid());
						
						shippingAmount = new BcwtReportDTO();
						shippingAmount.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_SHIPPING, session));
						shippingAmount.setOrderRequestId(glview.getOrderid());
						shippingAmount.setOrderType(glview.getOrdertype());
						shippingAmount.setCustomerName(glview.getFirstname()
								+ " " + glview.getLastname());
						shippingAmount
								.setApprovalCode(glview.getApprovalcode());
						shippingAmount.setTransactionDate(String.valueOf(glview
								.getTransactiondate()));
						shippingAmount
								.setProducts(Constants.PRODUCT_NAME_SHIPPING);
						shippingAmount.setPrice(numberFormat.format(Double
								.valueOf(glview.getShippingamount())));
						shippingAmount.setAmount("-"
								+ numberFormat.format(Double.valueOf(glview
										.getShippingamount())));

						reportList.add(shippingAmount);
					}
					}
					// Set IS Breezecard
				
					if (glview.getOrdertype().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
						if (!Util.isBlankOrNull(glview.getPatronaddressid())) {
							
							if(!checkDups2.contains(glview.getOrderid())){
							
							checkDups2.add(glview.getOrderid());
							
							breezeCard = new BcwtReportDTO();
							breezeCard
									.setAccountNumber(getProductAccountNo(
											Constants.PRODUCT_NAME_BREEZECARD,
											session));
							breezeCard.setOrderRequestId(glview.getOrderid());
							breezeCard.setOrderType(glview.getOrdertype());
							breezeCard.setCustomerName(glview.getFirstname()
									+ " " + glview.getLastname());
							breezeCard
									.setApprovalCode(glview.getApprovalcode());
							breezeCard.setTransactionDate(String.valueOf(glview
									.getTransactiondate()));
							breezeCard.setOrderQty(String.valueOf(quantity));
							breezeCard
									.setProducts(Constants.PRODUCT_NAME_BREEZECARD);
					//		if (!Util.isBlankOrNull(glview.getPrice())) {
								breezeCard.setPrice(numberFormat.format(Double
										.valueOf(glview.getPriceofbreezecard())));
								breezeCard.setAmount("-"
										+ numberFormat.format(Double
												.valueOf(glview.getPriceofbreezecard())));
						//	}

							reportList.add(breezeCard);
						}
						}		
					}

				}
				List<GLGBSView> queryList = getGLGBSView(fromDate, toDate,
						session);

				for (Iterator iterg = queryList.iterator(); iterg.hasNext();) {
					GLGBSView orderDetails = (GLGBSView) iterg.next();
					BcwtReportDTO productDetail = new BcwtReportDTO();
					// productDetails = (BcwtReportDTO) productIter.next();
					productDetail.setOrderRequestId(orderDetails.getOrderid());
					productDetail.setOrderType(orderDetails.getOrdertype());
					productDetail.setCustomerName(orderDetails.getFirstname()
							+ " " + orderDetails.getLastname());
					productDetail.setApprovalCode(orderDetails
							.getApprovalcode());
					productDetail.setTransactionDate(String
							.valueOf(orderDetails.getTransactiondate()));
					productDetail.setAccountNumber(orderDetails
							.getAccountnumber());
					productDetail.setProducts(orderDetails.getProductname());
					productDetail.setPrice(orderDetails.getPrice());
					productDetail.setOrderQty(orderDetails.getNoofcards());

					if (!Util.isBlankOrNull(orderDetails.getPrice())) {

						productDetail.setAmount("-"
								+ numberFormat.format(Double
										.valueOf(orderDetails.getPrice())
										* Double.valueOf(orderDetails
												.getNoofcards())));
					}

					// productDetails.setOrderQty(String.valueOf(quantity));

					reportList.add(productDetail);
					
					double price = 00.00;
					double amount = 00.00;
					
					double gbsSubTotal = 00.00;
					double gbsShippingSubTotal = 00.00;
					
					
					if (orderDetails.getNoofcards() != null) {
						quantity = Integer.parseInt(orderDetails.getNoofcards());
						BcwtReportDTO breezeCardCash = new BcwtReportDTO();
						breezeCardCash.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_BREEZECARD, session));
						breezeCardCash.setOrderRequestId(orderDetails.getOrderid());
						breezeCardCash.setOrderType(orderDetails.getOrdertype());
						breezeCardCash.setCustomerName(orderDetails.getFirstname() + " "
								+ orderDetails.getLastname());
						breezeCardCash.setApprovalCode(orderDetails.getApprovalcode());
						breezeCardCash.setTransactionDate(String.valueOf(orderDetails
								.getTransactiondate()));
						breezeCardCash.setOrderQty(orderDetails.getNoofcards());
						breezeCardCash
								.setProducts(Constants.PRODUCT_NAME_BREEZECARD);
						if (!Util.isBlankOrNull(orderDetails.getPrice())) {
							breezeCardCash.setPrice(numberFormat.format(Double
									.valueOf(orderDetails.getPriceofbreezecard())));

							price = Double.parseDouble(orderDetails.getPriceofbreezecard());
							amount = price * quantity;
							breezeCardCash.setAmount("-"
									+ numberFormat.format(amount));
							BcwtsLogger.info("price" + price + "amount "
									+ amount + " q" + quantity);
						}

						gbsSubTotal = gbsSubTotal + amount;
						breezeCardCash.setGbsSubTotal(gbsSubTotal);

						reportList.add(breezeCardCash);
					}
					
					// shipping
					
					if (orderDetails.getShippingamount() != null) {
					//	quantity = Integer.parseInt(element.getNoofcards());
						BcwtReportDTO gbsShipping = new BcwtReportDTO();
						gbsShipping.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_SHIPPING, session));
						gbsShipping.setOrderRequestId(orderDetails.getOrderid());
						gbsShipping.setOrderType(orderDetails.getOrdertype());
						gbsShipping.setCustomerName(orderDetails.getFirstname() + " "
								+ orderDetails.getLastname());
						gbsShipping.setApprovalCode(orderDetails.getApprovalcode());
						gbsShipping.setTransactionDate(String.valueOf(orderDetails
								.getTransactiondate()));
						
						gbsShipping
								.setProducts(Constants.PRODUCT_NAME_SHIPPING);
						if (!Util.isBlankOrNull(orderDetails.getPrice())) {
							gbsShipping.setPrice(numberFormat.format(Double
									.valueOf(orderDetails.getShippingamount())));

							
							amount = Double.parseDouble(orderDetails.getShippingamount());
							gbsShipping.setAmount("-"
									+ numberFormat.format(amount));
							
						}

					//	gbsShippingSubTotal = gbsShippingSubTotal + amount;
					//	gbsShipping.setGbsSubTotal(gbsShippingSubTotal);

						reportList.add(gbsShipping);
					}
					double gbsDiscountDouble = 00.00;
					if (orderDetails.getDiscountedamount() != null
							&& !orderDetails.getDiscountedamount().equals("0")) {
						BcwtReportDTO gbsDiscount = new BcwtReportDTO();
						gbsDiscount.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_GBS_DISCOUNT, session));
						gbsDiscount.setOrderRequestId(orderDetails.getOrderid());
						gbsDiscount.setOrderType(orderDetails.getOrdertype());
						gbsDiscount.setCustomerName(orderDetails.getFirstname()
								+ " " + orderDetails.getLastname());
						gbsDiscount.setApprovalCode(orderDetails.getApprovalcode());
						gbsDiscount.setTransactionDate(String.valueOf(orderDetails
								.getTransactiondate()));
						gbsDiscount
								.setProducts(Constants.PRODUCT_NAME_GBS_DISCOUNT);
						gbsDiscount.setAmount("+"
								+ numberFormat.format(Double.valueOf(orderDetails
										.getDiscountedamount())));

						gbsDiscountDouble = Double.parseDouble(orderDetails
								.getDiscountedamount());
						gbsDiscount.setGbsDiscount(gbsDiscountDouble);

						reportList.add(gbsDiscount);
					}
				}
				
				List<GLGBSCASHView> queryListCash = getGLGBSCASHView(fromDate, toDate,
						session);

				double price = 00.00;
				double amount = 00.00;
				
				double gbsSubTotal = 00.00;
				double gbsShippingSubTotal = 00.00;
				
				
			
				
				for (Iterator iterg = queryListCash.iterator(); iterg.hasNext();) {
					GLGBSCASHView orderDetails = (GLGBSCASHView) iterg.next();
					BcwtReportDTO cashDetail = new BcwtReportDTO();
					// productDetails = (BcwtReportDTO) productIter.next();
					cashDetail.setOrderRequestId(orderDetails.getOrderid());
					cashDetail.setOrderType(orderDetails.getOrdertype());
					cashDetail.setCustomerName(orderDetails.getFirstname()
							+ " " + orderDetails.getLastname());
					cashDetail.setApprovalCode(orderDetails
							.getApprovalcode());
					cashDetail.setTransactionDate(String
							.valueOf(orderDetails.getTransactiondate()));
					cashDetail.setAccountNumber(orderDetails
							.getAccountnumber());
					cashDetail.setProducts(orderDetails.getProductname());
					cashDetail.setPrice(orderDetails.getPrice());
					cashDetail.setOrderQty(orderDetails.getNoofcards());

					if (!Util.isBlankOrNull(orderDetails.getPrice())) {

						cashDetail.setAmount("-"
								+ numberFormat.format(Double
										.valueOf(orderDetails.getPrice())
										* Double.valueOf(orderDetails
												.getNoofcards())));
					}

					// productDetails.setOrderQty(String.valueOf(quantity));

					reportList.add(cashDetail);
					
					if (orderDetails.getNoofcards() != null) {
						quantity = Integer.parseInt(orderDetails.getNoofcards());
						BcwtReportDTO breezeCardCash = new BcwtReportDTO();
						breezeCardCash.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_BREEZECARD, session));
						breezeCardCash.setOrderRequestId(orderDetails.getOrderid());
						breezeCardCash.setOrderType(orderDetails.getOrdertype());
						breezeCardCash.setCustomerName(orderDetails.getFirstname() + " "
								+ orderDetails.getLastname());
						breezeCardCash.setApprovalCode(orderDetails.getApprovalcode());
						breezeCardCash.setTransactionDate(String.valueOf(orderDetails
								.getTransactiondate()));
						breezeCardCash.setOrderQty(orderDetails.getNoofcards());
						breezeCardCash
								.setProducts(Constants.PRODUCT_NAME_BREEZECARD);
						if (!Util.isBlankOrNull(orderDetails.getPrice())) {
							breezeCardCash.setPrice(numberFormat.format(Double
									.valueOf(orderDetails.getPriceofbreezecard())));

							price = Double.parseDouble(orderDetails.getPriceofbreezecard());
							amount = price * quantity;
							breezeCardCash.setAmount("-"
									+ numberFormat.format(amount));
							BcwtsLogger.info("price" + price + "amount "
									+ amount + " q" + quantity);
						}

						gbsSubTotal = gbsSubTotal + amount;
						breezeCardCash.setGbsSubTotal(gbsSubTotal);

						reportList.add(breezeCardCash);
					}
					
					// shipping
					
					if (orderDetails.getShippingamount() != null) {
					//	quantity = Integer.parseInt(element.getNoofcards());
						BcwtReportDTO gbsShipping = new BcwtReportDTO();
						gbsShipping.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_SHIPPING, session));
						gbsShipping.setOrderRequestId(orderDetails.getOrderid());
						gbsShipping.setOrderType(orderDetails.getOrdertype());
						gbsShipping.setCustomerName(orderDetails.getFirstname() + " "
								+ orderDetails.getLastname());
						gbsShipping.setApprovalCode(orderDetails.getApprovalcode());
						gbsShipping.setTransactionDate(String.valueOf(orderDetails
								.getTransactiondate()));
						
						gbsShipping
								.setProducts(Constants.PRODUCT_NAME_SHIPPING);
						if (!Util.isBlankOrNull(orderDetails.getPrice())) {
							gbsShipping.setPrice(numberFormat.format(Double
									.valueOf(orderDetails.getShippingamount())));

							
							amount = Double.parseDouble(orderDetails.getShippingamount());
							gbsShipping.setAmount("-"
									+ numberFormat.format(amount));
							
						}

					//	gbsShippingSubTotal = gbsShippingSubTotal + amount;
					//	gbsShipping.setGbsSubTotal(gbsShippingSubTotal);

						reportList.add(gbsShipping);
					}
					
				}

		//		setGLRDetailsForGBS(reportList, queryList, fromDate, toDate,
		//				session);

				/*
				 * for (Iterator iter = ordersList.iterator(); iter.hasNext();)
				 * { BcwtReportDTO orderDetails = (BcwtReportDTO) iter.next();
				 * 
				 * if (orderDetails != null) {
				 * 
				 * // Set Product Details List productDetailsList =
				 * getProductAccountNo( orderDetails.getOrderType(),
				 * orderDetails.getOrderRequestId(), session); for (Iterator
				 * productIter = productDetailsList .iterator();
				 * productIter.hasNext();) { productDetails = (BcwtReportDTO)
				 * productIter.next();
				 * productDetails.setOrderRequestId(orderDetails
				 * .getOrderRequestId());
				 * productDetails.setOrderType(orderDetails .getOrderType());
				 * productDetails.setCustomerName(orderDetails .getFirstName() +
				 * " " + orderDetails.getLastName());
				 * productDetails.setApprovalCode(orderDetails
				 * .getApprovalCode());
				 * productDetails.setTransactionDate(orderDetails
				 * .getTransactionDate()); //
				 * productDetails.setOrderQty(String.valueOf(quantity));
				 * 
				 * reportList.add(productDetails); }
				 * 
				 * // Set Cash
				 * 
				 * // Set Shipping charge if (!Util.isBlankOrNull(orderDetails
				 * .getShippingAmount())) { shippingAmount = new
				 * BcwtReportDTO(); shippingAmount
				 * .setAccountNumber(getProductAccountNo(
				 * Constants.PRODUCT_NAME_SHIPPING, session));
				 * shippingAmount.setOrderRequestId(orderDetails
				 * .getOrderRequestId());
				 * shippingAmount.setOrderType(orderDetails .getOrderType());
				 * shippingAmount.setCustomerName(orderDetails .getFirstName() +
				 * " " + orderDetails.getLastName());
				 * shippingAmount.setApprovalCode(orderDetails
				 * .getApprovalCode());
				 * shippingAmount.setTransactionDate(orderDetails
				 * .getTransactionDate()); shippingAmount
				 * .setProducts(Constants.PRODUCT_NAME_SHIPPING); shippingAmount
				 * .setPrice(numberFormat.format(Double .valueOf(orderDetails
				 * .getShippingAmount()))); shippingAmount.setAmount("-" +
				 * numberFormat.format(Double .valueOf(orderDetails
				 * .getShippingAmount())));
				 * 
				 * reportList.add(shippingAmount); }
				 * 
				 * // Set IS Breezecard if
				 * (orderDetails.getOrderType().equalsIgnoreCase(
				 * Constants.ORDER_TYPE_IS)) { if (!Util
				 * .isBlankOrNull(orderDetails.getAddressId())) { breezeCard =
				 * new BcwtReportDTO(); breezeCard
				 * .setAccountNumber(getProductAccountNo(
				 * Constants.PRODUCT_NAME_BREEZECARD, session));
				 * breezeCard.setOrderRequestId(orderDetails
				 * .getOrderRequestId()); breezeCard.setOrderType(orderDetails
				 * .getOrderType()); breezeCard.setCustomerName(orderDetails
				 * .getFirstName() + " " + orderDetails.getLastName());
				 * breezeCard.setApprovalCode(orderDetails .getApprovalCode());
				 * breezeCard.setTransactionDate(orderDetails
				 * .getTransactionDate()); breezeCard
				 * .setOrderQty(String.valueOf(quantity)); breezeCard
				 * .setProducts(Constants.PRODUCT_NAME_BREEZECARD); if (!Util
				 * .isBlankOrNull(orderDetails.getPrice())) {
				 * breezeCard.setPrice(numberFormat
				 * .format(Double.valueOf(orderDetails .getPrice())));
				 * breezeCard.setAmount("-" + numberFormat.format(Double
				 * .valueOf(orderDetails .getPrice()))); }
				 * 
				 * reportList.add(breezeCard); } }
				 * 
				 * // Set GBS Details if
				 * (!Util.isBlankOrNull(orderDetails.getOrderType())) { if
				 * (orderDetails.getOrderType().equalsIgnoreCase(
				 * Constants.ORDER_TYPE_GBS)) { setGLRDetailsForGBS(reportList,
				 * orderDetails, session); } } } }
				 */
				BcwtsLogger.info("size " + reportList.size() + " ");
				List accNosList = new ArrayList();
				for (Iterator iterator1 = reportList.iterator(); iterator1
						.hasNext();) {
					BcwtReportDTO bcwtReportDTO1 = (BcwtReportDTO) iterator1
							.next();
					if (!Util.isBlankOrNull(bcwtReportDTO1.getAccountNumber())) {

						accNosList.add(bcwtReportDTO1.getAccountNumber());
					}
				}

				Collections.sort(accNosList);
				BcwtsLogger.info("size 2 " + accNosList.size() + " ");
				Set accNoSet = new HashSet();
				for (int j = 0; j < accNosList.size(); j++) {
					String accountNo = (String) accNosList.get(j);
                    
					if (accNoSet.add(accountNo)) {

						List bcwtReportDTOList = getBcwtReportDTOs(reportList,
								accountNo);

						BcwtReportDTO bcwtReportDTO2 = null;
						for (int k = 0; k < bcwtReportDTOList.size(); k++) {
							bcwtReportDTO2 = (BcwtReportDTO) bcwtReportDTOList
									.get(k);

							if (bcwtReportDTO2 != null) {
								finalReportList.add(bcwtReportDTO2);

								if (bcwtReportDTO2
										.getProducts()
										.equalsIgnoreCase(
												Constants.PRODUCT_NAME_BREEZECARD)) {
									if (bcwtReportDTO2.getOrderType()
											.equalsIgnoreCase(
													Constants.ORDER_TYPE_IS)
											&& !Util.isBlankOrNull(bcwtReportDTO2
													.getPrice())) {
										subTot = subTot
												+ Double.parseDouble(bcwtReportDTO2
														.getPrice());
									}
									if (bcwtReportDTO2.getOrderType()
											.equalsIgnoreCase(
													Constants.ORDER_TYPE_GBS)) {
										subTot = subTot
												+ bcwtReportDTO2
														.getGbsSubTotal();
									}
								} else if (bcwtReportDTO2
										.getProducts()
										.equalsIgnoreCase(
												Constants.PRODUCT_NAME_GBS_DISCOUNT)) {
									subTot = subTot
											+ bcwtReportDTO2.getGbsDiscount();

								} else {
									if (!Util.isBlankOrNull(bcwtReportDTO2
											.getPrice())) {
										if (null != bcwtReportDTO2
												.getOrderQty())
											subTot = subTot
													+ (Double
															.parseDouble(bcwtReportDTO2
																	.getPrice()) * Double
															.parseDouble(bcwtReportDTO2
																	.getOrderQty()));
										else
											subTot = subTot
													+ Double.parseDouble(bcwtReportDTO2
															.getPrice());
									}
									if (bcwtReportDTO2.getOrderType()
											.equalsIgnoreCase(
													Constants.ORDER_TYPE_GBS)) {
										subTot = subTot
												+ bcwtReportDTO2
														.getGbsSubTotal();

									}
								}
							}
						}

						// Sub Total Amount
						BcwtReportDTO subTotal = new BcwtReportDTO();
						subTotal.setOrderQty(subTotalStr);
						if (bcwtReportDTO2.getProducts().equalsIgnoreCase(
								Constants.PRODUCT_NAME_GBS_DISCOUNT)) {
							subTotal.setAmount("+"
									+ numberFormat.format(subTot));
							tot = tot - subTot;
						} else {
							subTotal.setAmount("-"
									+ numberFormat.format(subTot));
							tot = tot + subTot;
						}
						finalReportList.add(subTotal);
						subTot = 00.00;
					}
				}

				// Total Amount
				BcwtReportDTO total = new BcwtReportDTO();
				total.setOrderQty(totalStr);
				total.setAmount("-" + numberFormat.format(tot));

				finalReportList.add(total);
			}

			transaction.commit();
			session.flush();
		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return finalReportList;
	}

	private List getBcwtReportDTOs(List reportList, String accountNumber)
			throws Exception {
		List bcwtReportDTOList = new ArrayList();
		for (Iterator iter = reportList.iterator(); iter.hasNext();) {
			BcwtReportDTO bcwtReportDTO = (BcwtReportDTO) iter.next();
			
			  if(bcwtReportDTO.getOrderRequestId().equals("592")){
			  System.out.println("Acc"+ bcwtReportDTO.getAccountNumber());
			  System.out.println("Order: "+bcwtReportDTO.getOrderRequestId());
			  System.out.println("Product: "+bcwtReportDTO.getProducts());}
			 
			if (bcwtReportDTO.getAccountNumber()
					.equalsIgnoreCase(accountNumber)) {

				bcwtReportDTOList.add(bcwtReportDTO);

			}
		}
		return bcwtReportDTOList;
	}

	private void setGLRDetailsForGBS(List reportList, List queryList,
			String fromDate, String toDate, Session session) throws Exception {
		final String MY_NAME = ME + "setGLRDetailsForGBS:";
		BcwtsLogger.debug(MY_NAME);
		String query = null;
		BcwtReportDTO breezeCard = null;
		BcwtReportDTO gbsDiscount = null;
		BcwtReportDTO gbsShipping = null;
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);
		double price = 00.00;
		double amount = 00.00;
		int quantity = 0;
		double gbsSubTotal = 00.00;
		double gbsShippingSubTotal = 00.00;
		double gbsDiscountDouble = 00.00;
		try {

			/*
			 * query = "select" + " bcwtOrderInfo.noofcards," +
			 * " bcwtOrderInfo.discountedamount" + " from" +
			 * " BcwtGbsProductDetails bcwtGbsProductDetails," +
			 * " BcwtOrderInfo bcwtOrderInfo," + " BcwtOrder bcwtOrder" +
			 * " where" + " bcwtOrder.orderid = " + orderDetails.getOrderid() +
			 * " and bcwtOrder.orderid = bcwtOrderInfo.bcwtorder.orderid" +
			 * " and bcwtOrderInfo.orderinfoid = bcwtGbsProductDetails.bcwtorderinfo.orderinfoid"
			 * ;
			 */

			if (queryList != null && !queryList.isEmpty()) {

				for (Iterator iterator = queryList.iterator(); iterator
						.hasNext();) {
					GLGBSView element = (GLGBSView) iterator.next();

					// Set GBS Breezecard
					if (element.getNoofcards() != null) {
						quantity = Integer.parseInt(element.getNoofcards());
						breezeCard = new BcwtReportDTO();
						breezeCard.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_BREEZECARD, session));
						breezeCard.setOrderRequestId(element.getOrderid());
						breezeCard.setOrderType(element.getOrdertype());
						breezeCard.setCustomerName(element.getFirstname() + " "
								+ element.getLastname());
						breezeCard.setApprovalCode(element.getApprovalcode());
						breezeCard.setTransactionDate(String.valueOf(element
								.getTransactiondate()));
						breezeCard.setOrderQty(element.getNoofcards());
						breezeCard
								.setProducts(Constants.PRODUCT_NAME_BREEZECARD);
						if (!Util.isBlankOrNull(element.getPrice())) {
							breezeCard.setPrice(numberFormat.format(Double
									.valueOf(element.getPriceofbreezecard())));

							price = Double.parseDouble(element.getPriceofbreezecard());
							amount = price * quantity;
							breezeCard.setAmount("-"
									+ numberFormat.format(amount));
							BcwtsLogger.info("price" + price + "amount "
									+ amount + " q" + quantity);
						}

						gbsSubTotal = gbsSubTotal + amount;
						breezeCard.setGbsSubTotal(gbsSubTotal);

						reportList.add(breezeCard);
					}
					
					// shipping
					
					if (element.getShippingamount() != null) {
					//	quantity = Integer.parseInt(element.getNoofcards());
						gbsShipping = new BcwtReportDTO();
						gbsShipping.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_SHIPPING, session));
						gbsShipping.setOrderRequestId(element.getOrderid());
						gbsShipping.setOrderType(element.getOrdertype());
						gbsShipping.setCustomerName(element.getFirstname() + " "
								+ element.getLastname());
						gbsShipping.setApprovalCode(element.getApprovalcode());
						gbsShipping.setTransactionDate(String.valueOf(element
								.getTransactiondate()));
						
						gbsShipping
								.setProducts(Constants.PRODUCT_NAME_SHIPPING);
						if (!Util.isBlankOrNull(element.getPrice())) {
							gbsShipping.setPrice(numberFormat.format(Double
									.valueOf(element.getShippingamount())));

							
							amount = Double.parseDouble(element.getShippingamount());
							gbsShipping.setAmount("-"
									+ numberFormat.format(amount));
							
						}

					//	gbsShippingSubTotal = gbsShippingSubTotal + amount;
					//	gbsShipping.setGbsSubTotal(gbsShippingSubTotal);

						reportList.add(gbsShipping);
					}

					// Set GBS Discount
					if (element.getDiscountedamount() != null
							&& !element.getDiscountedamount().equals("0")) {
						gbsDiscount = new BcwtReportDTO();
						gbsDiscount.setAccountNumber(getProductAccountNo(
								Constants.PRODUCT_NAME_GBS_DISCOUNT, session));
						gbsDiscount.setOrderRequestId(element.getOrderid());
						gbsDiscount.setOrderType(element.getOrdertype());
						gbsDiscount.setCustomerName(element.getFirstname()
								+ " " + element.getLastname());
						gbsDiscount.setApprovalCode(element.getApprovalcode());
						gbsDiscount.setTransactionDate(String.valueOf(element
								.getTransactiondate()));
						gbsDiscount
								.setProducts(Constants.PRODUCT_NAME_GBS_DISCOUNT);
						gbsDiscount.setAmount("+"
								+ numberFormat.format(Double.valueOf(element
										.getDiscountedamount())));

						gbsDiscountDouble = Double.parseDouble(element
								.getDiscountedamount());
						gbsDiscount.setGbsDiscount(gbsDiscountDouble);

						reportList.add(gbsDiscount);
					}
					break;
				}
			}
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		}
	}

	private String getCashValue(String orderId, Session session)
			throws Exception {
		final String MY_NAME = ME + "getCashValue:";
		BcwtsLogger.debug(MY_NAME);
		String qry = null;
		String cashValue = null;
		double cash = 0;

		try {
			qry = "select" + " bcwtOrderDetails.cashvalue" + " from"
					+ " BcwtOrderDetails bcwtOrderDetails" + " where"
					+ " bcwtOrderDetails.bcwtorder.orderid = '" + orderId + "'";

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						// cashValue = element.toString();
						cash = Double.parseDouble(element.toString()) + cash;
					}
				}
			}
			BcwtsLogger.info(MY_NAME + "got the account no from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		}
		cashValue = String.valueOf(cash);
		return cashValue;
	}

	private String getProductAccountNo(String productName, Session session)
			throws Exception {
		final String MY_NAME = ME + "getProductAccountNo:";
	//	BcwtsLogger.debug(MY_NAME);
		String qry = null;
		String accountNo = null;
	//	Session session = null;
	//	Transaction transaction = null;
		try {
			qry = "select" + " bcwtProductAccntNums.salesAccnt" + " from"
					+ " BcwtProductAccntNums bcwtProductAccntNums" + " where"
					+ " bcwtProductAccntNums.id.productname = '" + productName
					+ "'";

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						accountNo = element.toString();
					}
				}
			}
		//	BcwtsLogger.info(MY_NAME + "got the account no from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return accountNo;
	}

	private List getProductAccountNo(String orderType, String orderId,
			Session session) throws Exception {
		final String MY_NAME = ME + "getProductAccountNo:";
	//	BcwtsLogger.debug(MY_NAME);
		String qry = null;
		List queryList = null;
		String sv = null;
		List productDetailsList = new ArrayList();
		if (orderId.equals("195053"))
			System.out.println("hello");
		try {

			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				qry = "select"
						+ " bcwtBreezeCardProduct.bcwtproduct.productname,"
						+ " bcwtProductAccntNums.salesAccnt,"
						+ " bcwtBreezeCardProduct.price,"
						+ " bcwtOrderDetails.cashvalue"
						+ " from"
						+ " BcwtProductAccntNums bcwtProductAccntNums,"
						+ " BcwtBreezeCardProduct bcwtBreezeCardProduct,"
						+ " BcwtOrderDetails bcwtOrderDetails,"
						+ " BcwtOrder bcwtOrder"
						+ " where"
						+ " bcwtOrder.orderid = "
						+ orderId
						+ " and bcwtOrder.orderid = bcwtOrderDetails.bcwtorder.orderid"
						+ " and bcwtOrderDetails.orderdetailsid = bcwtBreezeCardProduct.bcwtorderdetails.orderdetailsid"
						+ " and bcwtProductAccntNums.id.fareInstrumentId = bcwtBreezeCardProduct.bcwtproduct.fare_instrument_id"
						+ " order by bcwtProductAccntNums.salesAccnt asc";
			} else if (orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				qry = "select"
						+ " bcwtGbsProductDetails.bcwtproduct.productname,"
						+ " bcwtProductAccntNums.salesAccnt,"
						+ " bcwtGbsProductDetails.price,"
						+ "bcwtOrderInfo.cashvalue,"
						+ "bcwtOrderInfo.noofcards"
						+ " from"
						+ " BcwtProductAccntNums bcwtProductAccntNums,"
						+ " BcwtGbsProductDetails bcwtGbsProductDetails,"
						+ " BcwtOrderInfo bcwtOrderInfo,"
						+ " BcwtOrder bcwtOrder"
						+ " where"
						+ " bcwtOrder.orderid = "
						+ orderId
						+ " and bcwtOrder.orderid = bcwtOrderInfo.bcwtorder.orderid"
						+ " and bcwtOrderInfo.orderinfoid = bcwtGbsProductDetails.bcwtorderinfo.orderinfoid"
						+ " and bcwtProductAccntNums.id.fareInstrumentId = bcwtGbsProductDetails.bcwtproduct.fare_instrument_id"
						+ " order by bcwtProductAccntNums.salesAccnt asc";
			}

			if (!Util.isBlankOrNull(qry)) {
				queryList = session.createQuery(qry).list();
			}
			boolean count = false;
			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO productDetails = new BcwtReportDTO();

					if (element[0] != null) {
						productDetails.setProducts(element[0].toString());

					}
					if (element[1] != null) {
						productDetails.setAccountNumber(element[1].toString());

					}
					if (element[2] != null) {
						productDetails.setPrice(numberFormat.format(Double
								.parseDouble(element[2].toString())));
						productDetails.setAmount("-"
								+ numberFormat.format(Double
										.parseDouble(element[2].toString())));

					}
					if (element[3] != null && !count) {
						count = true;
						sv = element[3].toString();
					}

					if (element.length == 5) {
						if (element[4] != null) {
							productDetails.setOrderQty(element[4].toString());
							double total = Double.parseDouble(element[2]
									.toString())
									* Double.parseDouble(element[4].toString());
							productDetails.setAmount("-"
									+ numberFormat.format(total));

						} else {
							productDetails.setOrderQty("1");
						}
					} else
						productDetails.setOrderQty("1");

					productDetailsList.add(productDetails);
				}

				/*
				 * if (null != sv) { BcwtReportDTO productDetailsSV = new
				 * BcwtReportDTO();
				 * 
				 * productDetailsSV.setAccountNumber(getProductAccountNo(
				 * "STORED VALUE", session));
				 * productDetailsSV.setProducts("Stored Value");
				 * productDetailsSV.setPrice(numberFormat.format(Double
				 * .parseDouble(sv))); productDetailsSV.setAmount("-" +
				 * numberFormat.format(Double.parseDouble(sv)));
				 * productDetailsList.add(productDetailsSV); }
				 */

			}
			if (null != getCashValue(orderId, session)) {

				BcwtsLogger.debug("Size: " + productDetailsList.size() + ": "
						+ orderId);

				BcwtReportDTO productDetailsSV = new BcwtReportDTO();
				productDetailsSV.setAccountNumber(getProductAccountNo(
						"STORED VALUE", session));
				productDetailsSV.setProducts("Stored Value");
				productDetailsSV.setPrice(numberFormat.format(Double
						.parseDouble(getCashValue(orderId, session))));
				productDetailsSV.setAmount("-"
						+ numberFormat.format(Double.parseDouble(getCashValue(
								orderId, session))));
				productDetailsList.add(productDetailsSV);

			}
		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		}
		return productDetailsList;
	}

	private List<GLISView> getGLView(String fromDate, String toDate,Session session) throws Exception {

		final String MY_NAME = ME + "getGLView:";
		BcwtsLogger.debug(MY_NAME);
	//	Session session = null;
	//	Transaction transaction = null;
		List<GLISView> ordersList = new ArrayList<GLISView>();
		String query = null;

		try {
		//	session = getSession();
		//	transaction = session.beginTransaction();
			query = "select  glview.productname, glview.accountnumber, glview.orderid ,glview.firstname, glview.lastname, glview.ordertype, glview.approvalcode, glview.price , glview.cashvalue , glview.transactiondate , glview.shippingamount, glview.patronaddressid, glview.priceofbreezecard "
					+ " from  GLISView glview  where"
					+ " glview.transactiondate is not null";

			if (!Util.isBlankOrNull(fromDate) && !Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " glview.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(fromDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				query = query + " and glview.transactiondate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

		//	ordersList = session.createQuery(query).list();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					GLISView gLISView = new GLISView();
					if (null != element[0]) {
						gLISView.setProductname(element[0].toString());
					} else{
						gLISView.setProductname(Constants.PRODUCT_NAME_STORED_VALUE);
					}
					if (null != element[1]) {
						gLISView.setAccountnumber(element[1].toString());
					
					} else {
					
						 gLISView.setAccountnumber(getProductAccountNo(
	 								Constants.PRODUCT_NAME_STORED_VALUE, session));
	                     } 
						
					if (element[2] != null) {
						gLISView.setOrderid(element[2].toString());
					}
					if (element[3] != null) {
						gLISView.setFirstname(element[3].toString());
					}
					if (element[4] != null) {
						gLISView.setLastname(element[4].toString());
					}
					if (element[5] != null) {
						gLISView.setOrdertype(element[5].toString());
					}
					if (element[6] != null) {
						gLISView.setApprovalcode(element[6].toString());
					}
					if (element[7] != null) {
						gLISView.setPrice(element[7].toString());
					}
					if (element[8] != null) {
						gLISView.setCashvalue(element[8].toString());
					}
					if (element[9] != null) {
						gLISView.setTransactiondate((Date) element[9]);
					}
					if (element[10] != null) {
						gLISView.setShippingamount(element[10].toString());
					}
				
					if (element[11] != null) {
						gLISView.setPatronaddressid(element[11].toString());
					}
					if (element[12] != null) {
						gLISView.setPriceofbreezecard(element[12].toString());
					}
					
					
					
					
					ordersList.add(gLISView);
				}
			}
			
		
			
		//	transaction.commit();
		//	session.flush();
			BcwtsLogger.info(MY_NAME
					+ "got General Ledger Report list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
		//	closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return ordersList;

	}

	private List getGLGBSView(String fromDate, String toDate, Session session)
			throws Exception {

		final String MY_NAME = ME + "getGLView:";
		BcwtsLogger.debug(MY_NAME);

		Transaction transaction = null;
		List<GLGBSView> ordersList = new ArrayList<GLGBSView>();
		String query = null;

		try {

		//	transaction = session.beginTransaction();
			query = "select  glview.productname, glview.accountnumber, glview.orderid ,glview.firstname, glview.lastname, glview.ordertype, glview.approvalcode, glview.price , glview.cashvalue , glview.transactiondate , glview.shippingamount, glview.patronaddressid, glview.priceofbreezecard , glview.noofcards, glview.discountedamount from  GLGBSView glview  where"
					+ " glview.transactiondate is not null";

			if (!Util.isBlankOrNull(fromDate) && !Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " glview.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(fromDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				query = query + " and glview.transactiondate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			//ordersList = session.createQuery(query).list();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					GLGBSView gLISView = new GLGBSView();
					if (element[0] != null) {
						gLISView.setProductname(element[0].toString());
					}
					if (element[1] != null) {
						gLISView.setAccountnumber(element[1].toString());
					}
					if (element[2] != null) {
						gLISView.setOrderid(element[2].toString());
					}
					if (element[3] != null) {
						gLISView.setFirstname(element[3].toString());
					}
					if (element[4] != null) {
						gLISView.setLastname(element[4].toString());
					}
					if (element[5] != null) {
						gLISView.setOrdertype(element[5].toString());
					}
					if (element[6] != null) {
						gLISView.setApprovalcode(element[6].toString());
					}
					if (element[7] != null) {
						gLISView.setPrice(element[7].toString());
					}
					if (element[8] != null) {
						gLISView.setCashvalue(element[8].toString());
					}
					if (element[9] != null) {
						gLISView.setTransactiondate((Date) element[9]);
					}
					if (element[10] != null) {
						gLISView.setShippingamount(element[10].toString());
					}
				
					if (element[11] != null) {
						gLISView.setPatronaddressid(element[11].toString());
					}
					if (element[12] != null) {
						gLISView.setPriceofbreezecard(element[12].toString());
					}
					if (element[13] != null) {
						gLISView.setNoofcards(element[13].toString());
					}
					if (element[14] != null) {
						gLISView.setDiscountedamount(element[14].toString());
					}
					
					
					
					
					ordersList.add(gLISView);
				}
			}
		//	transaction.commit();
		//	session.flush();
			BcwtsLogger.info(MY_NAME
					+ "got General Ledger Report list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			// closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return ordersList;

	}

	private List getGLGBSCASHView(String fromDate, String toDate, Session session)
			throws Exception {

		final String MY_NAME = ME + "getGLView:";
		BcwtsLogger.debug(MY_NAME);

		Transaction transaction = null;
		List<GLGBSCASHView> ordersList = new ArrayList<GLGBSCASHView>();
		String query = null;

		try {

		//	transaction = session.beginTransaction();
			query = "select  glview.productname, glview.accountnumber, glview.orderid ,glview.firstname, glview.lastname, glview.ordertype, glview.approvalcode, glview.price , glview.cashvalue , glview.transactiondate , glview.shippingamount, glview.patronaddressid, glview.priceofbreezecard , glview.noofcards, glview.discountedamount from  GLGBSCASHView glview  where"
					+ " glview.transactiondate is not null";

			if (!Util.isBlankOrNull(fromDate) && !Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " glview.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(fromDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query + " and glview.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				query = query + " and glview.transactiondate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			//ordersList = session.createQuery(query).list();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					GLGBSCASHView gLISView = new GLGBSCASHView();
					if (element[0] != null) {
						gLISView.setProductname(element[0].toString());
					}
					if (element[1] != null) {
						gLISView.setAccountnumber(element[1].toString());
					}
					if (element[2] != null) {
						gLISView.setOrderid(element[2].toString());
					}
					if (element[3] != null) {
						gLISView.setFirstname(element[3].toString());
					}
					if (element[4] != null) {
						gLISView.setLastname(element[4].toString());
					}
					if (element[5] != null) {
						gLISView.setOrdertype(element[5].toString());
					}
					if (element[6] != null) {
						gLISView.setApprovalcode(element[6].toString());
					}
					if (element[7] != null) {
						gLISView.setPrice(element[7].toString());
					}
					if (element[8] != null) {
						gLISView.setCashvalue(element[8].toString());
					}
					if (element[9] != null) {
						gLISView.setTransactiondate((Date) element[9]);
					}
					if (element[10] != null) {
						gLISView.setShippingamount(element[10].toString());
					}
				
					if (element[11] != null) {
						gLISView.setPatronaddressid(element[11].toString());
					}
					if (element[12] != null) {
						gLISView.setPriceofbreezecard(element[12].toString());
					}
					if (element[13] != null) {
						gLISView.setNoofcards(element[13].toString());
					}
					if (element[14] != null) {
						gLISView.setDiscountedamount(element[14].toString());
					}
					
					
					
					
					ordersList.add(gLISView);
				}
			}
		//	transaction.commit();
		//	session.flush();
			BcwtsLogger.info(MY_NAME
					+ "got General Ledger Report list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			// closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return ordersList;

	}
	
	private List getOrdersForGeneralLedgerReport(String fromDate, String toDate)
			throws Exception {
		final String MY_NAME = ME + "getOrdersForGeneralLedgerReport:";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List ordersList = new ArrayList();
		String query = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			query = "select"
					+ " bcwtOrder.orderid, bcwtOrder.orderType, bcwtOrder.transactiondate,"
					+ " bcwtOrder.paymentAuthorization, bcwtOrder.bcwtpatron.firstname,"
					+ " bcwtOrder.bcwtpatron.lastname, bcwtOrder.shippingamount,"
					+ " bcwtOrder.bcwtpatronaddress.patronaddressid, bcwtOrder.priceofbreezecard"
					+ " from" + " BcwtOrder bcwtOrder" + " where"
					+ " bcwtOrder.transactiondate is not null";

			if (!Util.isBlankOrNull(fromDate) && !Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query
							+ " and bcwtOrder.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " bcwtOrder.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(fromDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query
							+ " and bcwtOrder.transactiondate >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(query)) {
					query = query
							+ " and bcwtOrder.transactiondate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				query = query + " and bcwtOrder.transactiondate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();
					if (element[0] != null) {
						bcwtReportDTO.setOrderRequestId(element[0].toString());
					}
					if (element[1] != null) {
						bcwtReportDTO.setOrderType(element[1].toString());
					}
					if (element[2] != null) {
						bcwtReportDTO.setTransactionDate(Util
								.getFormattedDate(element[2]));
					}
					if (element[3] != null) {
						bcwtReportDTO.setApprovalCode(element[3].toString());
					}
					if (element[4] != null) {
						bcwtReportDTO.setFirstName(element[4].toString());
					}
					if (element[5] != null) {
						bcwtReportDTO.setLastName(element[5].toString());
					}
					if (element[6] != null) {
						bcwtReportDTO.setShippingAmount(element[6].toString());
					}
					if (element[7] != null) {
						bcwtReportDTO.setAddressId(element[5].toString());
					}
					if (element[8] != null) {
						bcwtReportDTO.setPrice(element[8].toString());
					}
					ordersList.add(bcwtReportDTO);
				}
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME
					+ "got General Ledger Report list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return ordersList;
	}

	/***************** OF - Starts here **********************/

	/**
	 * To get the status information for PS
	 * 
	 * @return statusList
	 * @throws Exception
	 */
	public List getPSStatusInformation() throws Exception {
		final String MY_NAME = ME + "getPSStatusInformation: ";
		BcwtsLogger.debug(MY_NAME);
		List statusList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = " select partnercardorderstatus.orderStatusid, partnercardorderstatus.orderStatus"
					+ " from PartnerCardOrderStatus partnercardorderstatus"
					+ " where partnercardorderstatus.displayInHistory = "
					+ Constants.ACTIVE_STATUS;

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
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return statusList;
	}

	/**
	 * To get the status information
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List getStatusInformation() throws Exception {
		final String MY_NAME = ME + "getStatusInformation: ";
		BcwtsLogger.debug(MY_NAME);
		List statusList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = " select orderStatus.orderstatusid,orderStatus.orderstatusname from BcwtOrderStatus orderStatus "
					+ " where orderStatus.displayinhistory = "
					+ Constants.ACTIVE_STATUS;

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
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return statusList;
	}

	/**
	 * Method to get order summary report
	 * 
	 * @param bcwtReportSearchDTO
	 * @return
	 * @throws Exception
	 */
	public BcwtReportDTO getOrderSummary(
			BcwtReportSearchDTO bcwtReportSearchDTO, String type)
			throws Exception {

		final String MY_NAME = ME + "getOrderSummary:";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;

		BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();
		String qry = null;

		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);

		double orderValue = 0.00;
		double shippingAmount = 0.00;
		double total = 0.00;

		String orderQty = "0";

		try {

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
				qry = Query.OFOrderSummaryReportForIS;
			} else if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFOrderSummaryReportForGBS;
			} else {
				qry = Query.OFOrderSummaryReportForPS;
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())) {

				if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
						Constants.ORDER_TYPE_IS)
						|| bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
								Constants.ORDER_TYPE_GBS)) {

					if (null != bcwtReportSearchDTO.getOrderFromDate()
							&& !bcwtReportSearchDTO.getOrderFromDate().equals(
									"")
							&& null != bcwtReportSearchDTO.getOrderTodate()
							&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
						qry = qry
								+ " and bcwtOrder.orderdate >= to_date('"
								+ bcwtReportSearchDTO.getOrderFromDate()
								+ "','mm/dd/yyyy') and "
								+ " bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(bcwtReportSearchDTO
										.getOrderTodate()) + "','mm/dd/yyyy')";

					} else if (null != bcwtReportSearchDTO.getOrderFromDate()
							&& !bcwtReportSearchDTO.getOrderFromDate().equals(
									"")) {
						qry = qry + " and bcwtOrder.orderdate >= to_date('"
								+ bcwtReportSearchDTO.getOrderFromDate()
								+ "','mm/dd/yyyy')";

					} else if (null != bcwtReportSearchDTO.getOrderTodate()
							&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
						qry = qry
								+ " and bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(bcwtReportSearchDTO
										.getOrderTodate()) + "','mm/dd/yyyy')";
					} else {
						// To fetch last 30 days orders
						String dateFromToday = Util.getDateFromNow(30);
						qry = qry + " and bcwtOrder.orderdate >= to_date('"
								+ dateFromToday + "','mm/dd/yyyy')";
					}

					if (type.equalsIgnoreCase(Constants.OUTSTANDING_ORDERS)) {
						qry = qry
								+ " and bcwtOrder.bcwtorderstatus.orderstatusid = "
								+ Constants.ORDER_STATUS_PENDING;
					}
					if (type.equalsIgnoreCase(Constants.FULLFILED_ORDERS)) {
						qry = qry
								+ " and bcwtOrder.bcwtorderstatus.orderstatusid = "
								+ Constants.ORDER_STATUS_SHIPPED
								+ " or bcwtOrder.bcwtorderstatus.orderstatusid = "
								+ Constants.ORDER_STATUS_COMPLETED;
					}
					if (type.equalsIgnoreCase(Constants.CANCELLED_ORDERS)) {
						qry = qry
								+ " and bcwtOrder.bcwtorderstatus.orderstatusid = "
								+ Constants.ORDER_STATUS_CANCELLED;
					}
					if (type.equalsIgnoreCase(Constants.RETURNED_ORDERS)) {
						qry = qry
								+ " and bcwtOrder.bcwtorderstatus.orderstatusid = "
								+ Constants.ORDER_STATUS_RETURNED;
					}

				} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
						Constants.ORDER_TYPE_PS)) {

					String fromDate = bcwtReportSearchDTO.getOrderFromDate();
					String toDate = bcwtReportSearchDTO.getOrderTodate();

					if (type.equalsIgnoreCase(Constants.OUTSTANDING_ORDERS)) {
						qry = qry
								+ " where partnerCardOrder.partnerCardorderstatus.orderStatusid != "
								+ Constants.ORDER_STATUS_PS_SHIPPED;
					}
					if (type.equalsIgnoreCase(Constants.FULLFILED_ORDERS)) {
						qry = qry
								+ " where partnerCardOrder.partnerCardorderstatus.orderStatusid = "
								+ Constants.ORDER_STATUS_PS_SHIPPED;
					}
					if (type.equalsIgnoreCase(Constants.CANCELLED_ORDERS)) {
						qry = qry
								+ " where partnerCardOrder.partnerCardorderstatus.orderStatusid = "
								+ Constants.ORDER_STATUS_PS_REJECTED;
					}
					if (type.equalsIgnoreCase(Constants.RETURNED_ORDERS)) {
						qry = qry
								+ " where partnerCardOrder.partnerCardorderstatus.orderStatusid = "
								+ Constants.ORDER_STATUS_PS_RETURNED;
					}

					if (null != fromDate && !fromDate.equals("")
							&& null != toDate && !toDate.equals("")) {
						qry = qry
								+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
								+ fromDate
								+ "', 'mm/dd/yyyy')"
								+ " and to_date(partnerCardOrder.orderDate) < to_date('"
								+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

					} else if (null != fromDate && !fromDate.equals("")) {
						qry = qry
								+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
								+ fromDate + "', 'mm/dd/yyyy')";

					} else if (null != toDate && !toDate.equals("")) {
						qry = qry
								+ " and to_date(partnerCardOrder.orderDate) < to_date('"
								+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";
					} else {
						// To fetch last 30 days orders
						String dateFromToday = Util.getDateFromNow(30);
						qry = qry
								+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
								+ dateFromToday + "','mm/dd/yyyy')";
					}
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {

					if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
							&& bcwtReportSearchDTO.getOrderType()
									.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
						Object element = (Object) iter.next();
						if (element != null) {
							orderQty = element.toString();
						}
						bcwtReportDTO.setOrderQty(orderQty);

						bcwtReportDTO.setOrderValue("N/A");
						bcwtReportDTO.setShippingAmount("N/A");
						bcwtReportDTO.setTotal("N/A");
						break;
					} else {
						Object[] element = (Object[]) iter.next();
						if (element[0] != null) {
							orderValue = Double.parseDouble(element[0]
									.toString());
						}
						if (element[1] != null) {
							shippingAmount = Double.parseDouble(element[1]
									.toString());
						}

						total = orderValue + shippingAmount;

						bcwtReportDTO.setOrderQty("N/A");
						bcwtReportDTO.setOrderValue(numberFormat
								.format((orderValue)));
						bcwtReportDTO.setShippingAmount(numberFormat
								.format(shippingAmount));
						bcwtReportDTO.setTotal(numberFormat.format(total
								- shippingAmount));
						break;
					}

				}
			}

			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return bcwtReportDTO;
	}

	/**
	 * Method to get Outstanding Orders report
	 * 
	 * @param bcwtReportSearchDTO
	 * @return
	 * @throws Exception
	 */
	public List getOutstandingOrdersReport(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {

		final String MY_NAME = ME + "getOutstandingOrdersReport:";
		BcwtsLogger.debug(MY_NAME);
		List queryList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();
		String qry = null;
		try {

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFOutstandingOrdersReportForGBS;
				if (!Util.isBlankOrNull(qry)
						&& !Util.isBlankOrNull(bcwtReportSearchDTO.getBatchId())) {
					qry = qry + " and bcwtorderinfo.batchid = "
							+ bcwtReportSearchDTO.getBatchId();
				}
			} else if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
				qry = Query.OFOutstandingOrdersReportForIS;
			} else {
				return getPSOutstandingOrderDetails(bcwtReportSearchDTO);
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtorder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " bcwtorder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtorder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtorder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and bcwtorder.orderdate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO
							.getOrderRequestId())) {
				qry = qry + " and bcwtorder.orderid = "
						+ bcwtReportSearchDTO.getOrderRequestId();
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO
							.getCardSerialNo())) {
				qry = qry
						+ " and upper(bcwtpatronbreezecard.breezecardserialnumber) like '%"
						+ bcwtReportSearchDTO.getCardSerialNo().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				qry = qry + " and upper(bcwtpatron.firstname) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				qry = qry + " and upper(bcwtpatron.lastname) like '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getEmail())) {
				qry = qry + " and upper(bcwtpatron.emailid) like '%"
						+ bcwtReportSearchDTO.getEmail().toUpperCase() + "%'";
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getPhoneNumber())) {
				qry = qry + " and upper(bcwtpatron.phonenumber) like '%"
						+ bcwtReportSearchDTO.getPhoneNumber().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getOrderStatus())) {
				qry = qry + " and bcwtorder.bcwtorderstatus.orderstatusid = "
						+ bcwtReportSearchDTO.getOrderStatus();
			}

			session = getSession();
			transaction = session.beginTransaction();

			queryList = session.createQuery(qry).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

				if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
						&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
								Constants.ORDER_TYPE_IS)) {
					if (element[0] != null) {
						bcwtReportDTO.setCardSerialNo(element[0].toString());
					}
					bcwtReportDTO.setBatchId("N/A");
				} else {
					bcwtReportDTO.setCardSerialNo("N/A");
					if (element[0] != null) {
						bcwtReportDTO.setBatchId(element[0].toString());
					}
				}
				if (element[1] != null) {
					bcwtReportDTO.setOrderRequestId(element[1].toString());
				}
				if (element[2] != null) {
					bcwtReportDTO.setFirstName(element[2].toString());
				}
				if (element[3] != null) {
					bcwtReportDTO.setLastName(element[3].toString());
				}
				if (element[4] != null) {
					bcwtReportDTO.setEmail(element[4].toString());
				}
				if (element[5] != null) {
					bcwtReportDTO.setPhoneNumber(element[5].toString());
				}
				if (element[6] != null) {
					bcwtReportDTO.setZip(element[6].toString());
				}
				if (element[7] != null) {
					bcwtReportDTO.setOrderDate(Util
							.getFormattedDate(element[7]));
				}
				if (element[8] != null) {
					bcwtReportDTO.setOrderStatus(element[8].toString());
				}
				if (element[9] != null) {
					bcwtReportDTO.setOrderType(element[9].toString());
				}
				reportDTOList.add(bcwtReportDTO);
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getPSQualityDetailsReport(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getPSQualityDetailsReport: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();

		try {

			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			String qry = Query.OFQualityDetailReportForPS;

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry
						+ " and bcwtPartnerQualityResult.processed >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy') and "
						+ " bcwtPartnerQualityResult.processed < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				qry = qry
						+ " and bcwtPartnerQualityResult.processed >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry
						+ " and bcwtPartnerQualityResult.processed < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else {
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry
						+ " and bcwtPartnerQualityResult.processed >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getQaFirstName())) {
				qry = qry
						+ " and upper(bcwtPartnerQualityResult.processedby.firstName) like '%"
						+ bcwtReportSearchDTO.getQaFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getQaLastName())) {
				qry = qry
						+ " and upper(bcwtPartnerQualityResult.processedby.lastName) like '%"
						+ bcwtReportSearchDTO.getQaLastName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				qry = qry
						+ " and upper(partnerCardOrder.partnerAdmindetails.firstName) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				qry = qry
						+ " and upper(partnerCardOrder.partnerAdmindetails.lastName) like '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

					if (element[0] != null) {
						bcwtReportDTO.setFirstName(element[0].toString());
					}
					if (element[1] != null) {
						bcwtReportDTO.setLastName(element[1].toString());
					}
					if (element[2] != null) {
						bcwtReportDTO.setProcessedDate(Util
								.getFormattedDate(element[2]));
					}
					if (element[3] != null) {
						bcwtReportDTO.setOrderRequestId(element[3].toString());
					}
					bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_PS);

					reportDTOList.add(bcwtReportDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "got PS Quality Details list");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getPSReturnedOrderDetails(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getPSReturnedOrderDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();

		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();

			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			String query = Query.OFReturnedOrdersReportForPS;

			if (null != fromDate && !fromDate.equals("") && null != toDate
					&& !toDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ fromDate
						+ "', 'mm/dd/yyyy')"
						+ " and to_date(partnerCardOrder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else if (null != fromDate && !fromDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ fromDate + "', 'mm/dd/yyyy')";

			} else if (null != toDate && !toDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				query = query + " and partnerCardOrder.orderDate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

					if (element[0] != null) {
						bcwtReportDTO.setOrderRequestId(element[0].toString());
					}
					if (element[1] != null) {
						bcwtReportDTO.setOrderDate(Util
								.getFormattedDate(element[1]));
					}
					if (element[2] != null) {
						bcwtReportDTO.setReturnedDate(Util
								.getFormattedDate(element[2]));
					}

					bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_PS);
					bcwtReportDTO.setCardSerialNo("N/A");

					reportDTOList.add(bcwtReportDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "got PS Returned Order Details list");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getPSCancelledOrderDetails(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getPSCancelledOrderDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();
		String dateFromToday = null;

		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();
			String orderStatus = bcwtReportSearchDTO.getOrderStatus();
			String orderNumber = bcwtReportSearchDTO.getOrderRequestId();

			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			dateFromToday = Util.getDateFromNow(30);

			String query = Query.CancelledOrderReportForPS;

			if (null != fromDate && !fromDate.equals("") && null != toDate
					&& !toDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ fromDate
						+ "', 'mm/dd/yyyy')"
						+ " and to_date(partnerCardOrder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else if (null != fromDate && !fromDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ fromDate + "', 'mm/dd/yyyy')";

			} else if (null != toDate && !toDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ dateFromToday + "', 'mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderDate())) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) = to_date('"
						+ bcwtReportSearchDTO.getOrderDate()
						+ "', 'mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(orderStatus)) {
				query = query
						+ " and partnerCardOrder.partnerCardorderstatus.orderStatusid = "
						+ orderStatus;
			}
			if (!Util.isBlankOrNull(orderNumber)) {
				query = query + " and partnerCardOrder.orderId = "
						+ orderNumber;
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				query = query
						+ " and upper(partnerCardOrder.partnerAdmindetails.firstName) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				query = query
						+ " and upper(partnerCardOrder.partnerAdmindetails.lastName) like '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getPhoneNumber())) {
				query = query
						+ " and partnerCardOrder.partnerAdmindetails.phone1 like '%"
						+ bcwtReportSearchDTO.getPhoneNumber() + "%'";
			}

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

					if (element[0] != null) {
						bcwtReportDTO.setOrderRequestId(element[0].toString());
					}
					if (element[1] != null) {
						bcwtReportDTO.setFirstName(element[1].toString());
					}
					if (element[2] != null) {
						bcwtReportDTO.setLastName(element[2].toString());
					}
					if (element[3] != null) {
						bcwtReportDTO.setCancelledDate(Util
								.getFormattedDate(element[3]));
					}
					if (element[4] != null) {
						bcwtReportDTO.setMediasalesAdmin(element[4].toString());
					}
					if (element[5] != null) {
						bcwtReportDTO.setReasonType(element[5].toString());
					}

					bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_PS);
					bcwtReportDTO.setCardSerialNo("N/A");

					reportDTOList.add(bcwtReportDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "got PS Cancelled Order Details list");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getPSMediaSalesPerformanceMetricsReport(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getPSMediaSalesPerformanceMetricsReport: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();

		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();

			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			String query = Query.OFMediaSalesPerformanceMetricsReportForPS;

			if (null != fromDate && !fromDate.equals("") && null != toDate
					&& !toDate.equals("")) {
				query = query
						+ " and to_date(partnercardorder.orderDate) >= to_date('"
						+ fromDate
						+ "', 'mm/dd/yyyy')"
						+ " and to_date(partnercardorder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else if (null != fromDate && !fromDate.equals("")) {
				query = query
						+ " and to_date(partnercardorder.orderDate) >= to_date('"
						+ fromDate + "', 'mm/dd/yyyy')";

			} else if (null != toDate && !toDate.equals("")) {
				query = query
						+ " and to_date(partnercardorder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";
			} else {
				String dateFromToday = Util.getDateFromNow(30);
				query = query
						+ " and to_date(partnercardorder.orderDate) >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				query = query
						+ " and upper(partnercardorder.partnerAdmindetails.firstName) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				query = query
						+ " and upper(partnercardorder.partnerAdmindetails.lastName) like '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

					if (null != element[0]) {
						bcwtReportDTO.setOrderRequestId(element[0].toString());
					}
					if (null != element[1]) {
						bcwtReportDTO.setOrderDate(Util
								.getFormattedDate(element[1]));
					}
					if (null != element[2]) {
						bcwtReportDTO.setFirstName(element[2].toString());
					}
					if (null != element[3]) {
						bcwtReportDTO.setLastName(element[3].toString());
					}
					if (null != element[4]) {
						bcwtReportDTO.setQaFirstName(element[4].toString());
					}
					if (null != element[5]) {
						bcwtReportDTO.setQaLastName(element[5].toString());
					}

					bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_PS);

					reportDTOList.add(bcwtReportDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME
					+ "got PS MediaSalesPerformanceMetrics Details list");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getPSOutstandingOrderDetails(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getPSOutstandingOrderDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();
		String dateFromToday = null;

		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();
			String orderStatus = bcwtReportSearchDTO.getOrderStatus();
			String orderNumber = bcwtReportSearchDTO.getOrderRequestId();

			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			dateFromToday = Util.getDateFromNow(30);

			String query = "select "
					+ " partnercardorder.orderId, partnercardorder.partnerCardorderstatus.orderStatus,"
					+ " partnercardorder.orderDate, partnercardorder.partnerAdmindetails.firstName, "
					+ " partnercardorder.partnerAdmindetails.lastName, partnercardorder.partnerAdmindetails.email,"
					+ " partnercardorder.partnerAdmindetails.phone1, bcwtpartnerorderinfo.batchid"
					+ " from"
					+ " PartnerCardOrder partnercardorder, PartnerCardOrderStatus partnercardorderstatus, "
					+ " PartnerAdminDetails partneradmindetails, BcwtPartnerOrderInfo bcwtpartnerorderinfo"
					+ " where "
					+ " partnercardorder.partnerCardorderstatus.orderStatusid = partnercardorderstatus.orderStatusid"
					+ " and partnercardorder.partnerAdmindetails.partnerId = partneradmindetails.partnerId"
					+ " and partnercardorder.orderId = bcwtpartnerorderinfo.partnerCardorder.orderId"
					+ " and partnercardorder.partnerCardorderstatus.orderStatusid != "
					+ Constants.ORDER_STATUS_PS_SHIPPED;

			if (null != fromDate && !fromDate.equals("") && null != toDate
					&& !toDate.equals("")) {
				query = query
						+ " and to_date(partnercardorder.orderDate) >= to_date('"
						+ fromDate
						+ "', 'mm/dd/yyyy')"
						+ " and to_date(partnercardorder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else if (null != fromDate && !fromDate.equals("")) {
				query = query
						+ " and to_date(partnercardorder.orderDate) >= to_date('"
						+ fromDate + "', 'mm/dd/yyyy')";

			} else if (null != toDate && !toDate.equals("")) {
				query = query
						+ " and to_date(partnercardorder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else {
				query = query
						+ " and to_date(partnercardorder.orderDate) >= to_date('"
						+ dateFromToday + "', 'mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(orderStatus)) {
				query = query
						+ " and partnercardorder.partnerCardorderstatus.orderStatusid = "
						+ orderStatus;
			}
			if (!Util.isBlankOrNull(orderNumber)) {
				query = query + " and partnercardorder.orderId = "
						+ orderNumber;
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				query = query
						+ " and upper(partnercardorder.partnerAdmindetails.firstName) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				query = query
						+ " and upper(partnercardorder.partnerAdmindetails.lastName) like '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getEmail())) {
				query = query
						+ " and upper(partnercardorder.partnerAdmindetails.email) like '%"
						+ bcwtReportSearchDTO.getEmail().toUpperCase() + "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getPhoneNumber())) {
				query = query
						+ " and partnercardorder.partnerAdmindetails.phone1 like '%"
						+ bcwtReportSearchDTO.getPhoneNumber() + "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getBatchId())) {
				query = query + " and bcwtpartnerorderinfo.batchid = "
						+ bcwtReportSearchDTO.getBatchId();
			}

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

					if (element[0] != null) {
						bcwtReportDTO.setOrderRequestId(element[0].toString());
					}
					if (element[1] != null) {
						bcwtReportDTO.setOrderStatus(element[1].toString());
					}
					if (element[2] != null) {
						bcwtReportDTO.setOrderDate(Util
								.getFormattedDate(element[2]));
					}
					if (element[3] != null) {
						bcwtReportDTO.setFirstName(element[3].toString());
					}
					if (element[4] != null) {
						bcwtReportDTO.setLastName(element[4].toString());
					}
					if (element[5] != null) {
						bcwtReportDTO.setEmail(element[5].toString());
					}
					if (element[6] != null) {
						bcwtReportDTO.setPhoneNumber(element[6].toString());
					}
					if (element[7] != null) {
						bcwtReportDTO.setBatchId(element[7].toString());
					}

					bcwtReportDTO.setOrderType(Constants.ORDER_TYPE_PS);
					bcwtReportDTO.setCardSerialNo("N/A");

					reportDTOList.add(bcwtReportDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "got PS Outstanding Order Details list");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getPSalesMetricsReportDetails(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getPSalesMetricsReportDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();

		try {
			String fromDate = bcwtReportSearchDTO.getOrderFromDate();
			String toDate = bcwtReportSearchDTO.getOrderTodate();

			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");

			String query = Query.OFSalesMetricReportForPS;

			if (null != fromDate && !fromDate.equals("") && null != toDate
					&& !toDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ fromDate
						+ "', 'mm/dd/yyyy')"
						+ " and to_date(partnerCardOrder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";

			} else if (null != fromDate && !fromDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) >= to_date('"
						+ fromDate + "', 'mm/dd/yyyy')";

			} else if (null != toDate && !toDate.equals("")) {
				query = query
						+ " and to_date(partnerCardOrder.orderDate) < to_date('"
						+ Util.getNextDay(toDate) + "', 'mm/dd/yyyy')";
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				query = query + " and partnerCardOrder.orderDate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getZip())) {
				query = query + " and partnerAddress.zip = '"
						+ bcwtReportSearchDTO.getZip() + "'";
			}

			List queryList = session.createQuery(query).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

				if (null != element) {
					bcwtReportDTO.setNoOfCardsSold(element.toString());
				}

				bcwtReportDTO.setZip(bcwtReportSearchDTO.getZip());

				reportDTOList.add(bcwtReportDTO);
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME
					+ "got PS Sales Metrics Report Details list");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	/**
	 * Method to getQualityAssuranceSummary report
	 * 
	 * @param bcwtReportSearchDTO
	 * @return
	 * @throws Exception
	 */
	public List getQualityAssuranceSummary(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {

		final String MY_NAME = ME + "getQualityAssuranceSummary:";
		BcwtsLogger.debug(MY_NAME);
		List orderList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List reportDtoList = new ArrayList();
		int noOfPassed = 0;
		int noOfFailed = 0;

		try {
			session = getSession();
			transaction = session.beginTransaction();

			String qry = Query.OFQualityAssuranceSummary;

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry + " and bcwtorder.orderdate >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy') and "
						+ " bcwtorder.orderdate < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				qry = qry + " and bcwtorder.orderdate >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry + " and bcwtorder.orderdate < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			}

			orderList = session.createQuery(qry).list();
			BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();
			for (Iterator iter = orderList.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				if (null != element) {
					if (element.toString().equalsIgnoreCase(Constants.Pass)) {
						noOfPassed++;
					}
					if (element.toString().equalsIgnoreCase(Constants.Fail)) {
						noOfFailed++;
					}
				}
			}
			bcwtReportDTO.setNoOfPassed(String.valueOf(noOfPassed));
			bcwtReportDTO.setNoOfFailed(String.valueOf(noOfFailed));

			reportDtoList.add(bcwtReportDTO);

			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDtoList;
	}

	/**
	 * Method to get Returned Orders Report
	 * 
	 * @param bcwtReportSearchDTO
	 * @return reportDTOList
	 * @throws Exception
	 */
	public List getReturnedOrdersReport(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {

		final String MY_NAME = ME + "getReturnedOrdersReport:";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();
		String qry = null;

		try {

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
				qry = Query.OFReturnedOrdersReportForIS;
			} else if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFReturnedOrdersReportForGBS;
			} else {
				return getPSReturnedOrderDetails(bcwtReportSearchDTO);
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry + " and bcwtorder.orderdate >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy') and "
						+ " bcwtorder.orderdate < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				qry = qry + " and bcwtorder.orderdate >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry + " and bcwtorder.orderdate < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and bcwtorder.orderdate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

				if (element[0] != null) {
					bcwtReportDTO.setOrderRequestId(element[0].toString());
				}
				if (element[1] != null) {
					bcwtReportDTO.setOrderDate(Util
							.getFormattedDate(element[1]));
				}
				if (element[2] != null) {
					bcwtReportDTO.setReturnedDate(Util
							.getFormattedDate(element[2]));
				}
				if (element[3] != null) {
					bcwtReportDTO.setOrderType(element[3].toString());
				}
				if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
						&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
								Constants.ORDER_TYPE_IS)) {
					bcwtReportDTO.setCardSerialNo(element[4].toString());
				} else {
					bcwtReportDTO.setCardSerialNo("N/A");
				}

				reportDTOList.add(bcwtReportDTO);
			}

			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	/**
	 * Method to get reportlist
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getReportList() throws Exception {

		final String MY_NAME = ME + "getReportList:";
		BcwtsLogger.debug(MY_NAME);
		List reportList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		BcwtReports bcwtReports = null;
		LabelValueBean bean = null;
		List reportBeanList = new ArrayList();
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String Query = "from BcwtReports report where report.activestatus ="
					+ Constants.ACTIVE_STATUS;
			reportList = session.createQuery(Query).list();
			for (Iterator iter = reportList.iterator(); iter.hasNext();) {
				bcwtReports = new BcwtReports();
				bcwtReports = (BcwtReports) iter.next();
				bean = new LabelValueBean();
				bean.setLabel(bcwtReports.getReportname());
				bean.setValue(bcwtReports.getReportid().toString());
				reportBeanList.add(bean);
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportBeanList;
	}

	public List getCancelledOrderReport(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {

		final String MY_NAME = ME + "getCancelledOrderReport:";
		BcwtsLogger.debug(MY_NAME);
		List orderList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		BcwtReportDTO reportDTO = null;
		List reportDtoList = new ArrayList();
		String orderType = null;

		try {

			String qry = null;
			if (Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())) {
				throw new MartaException(MY_NAME + "orderType cannot be null");
			} else {
				orderType = bcwtReportSearchDTO.getOrderType();
			}
			if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_IS)) {
				qry = Query.CancelledOrderReportForIS;
			} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_GBS)) {
				qry = Query.CancelledOrderReportForGBS;
			} else {
				return getPSCancelledOrderDetails(bcwtReportSearchDTO);
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and bcwtOrder.orderdate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderRequestId())) {
				qry = qry + " and bcwtOrder.orderid ="
						+ bcwtReportSearchDTO.getOrderRequestId();
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getCardSerialNo())) {
				qry = qry
						+ " and bcwtOrderDetails.bcwtpatronbreezecard.breezecardid ="
						+ bcwtReportSearchDTO.getCardSerialNo();
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				qry = qry
						+ " and upper(bcwtOrder.bcwtpatron.firstname) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				qry = qry
						+ " and upper(bcwtOrder.bcwtpatron.lastname) like  '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderDate())) {
				qry = qry + " and to_date(bcwtOrder.orderdate) = to_date('"
						+ bcwtReportSearchDTO.getOrderDate()
						+ "','mm/dd/yyyy')";
			}

			/*
			 * if(null != bcwtReportSearchDTO.getOrderFromDate() &&
			 * !bcwtReportSearchDTO.getOrderFromDate().equals("") && null !=
			 * bcwtReportSearchDTO.getOrderTodate() &&
			 * !bcwtReportSearchDTO.getOrderTodate().equals("")){
			 * 
			 * qry = qry + " and bcwtCancelledOrder.cancelleddate >= to_date('"+
			 * bcwtReportSearchDTO.getOrderFromDate()+"','mm/dd/yyyy') and " +
			 * " bcwtCancelledOrder.cancelleddate < to_date('"
			 * +Util.getNextDay(bcwtReportSearchDTO
			 * .getOrderTodate())+"','mm/dd/yyyy')";
			 * 
			 * }else if(null != bcwtReportSearchDTO.getOrderFromDate() &&
			 * !bcwtReportSearchDTO.getOrderFromDate().equals("")){ qry = qry +
			 * " and bcwtCancelledOrder.cancelleddate >= to_date('"
			 * +bcwtReportSearchDTO.getOrderFromDate()+"','mm/dd/yyyy')" ;
			 * 
			 * }else if(null != bcwtReportSearchDTO.getOrderTodate() &&
			 * !bcwtReportSearchDTO.getOrderTodate().equals("")){ qry = qry +
			 * " and bcwtCancelledOrder.cancelleddate < to_date('"
			 * +Util.getNextDay
			 * (bcwtReportSearchDTO.getOrderTodate())+"','mm/dd/yyyy')" ; }else
			 * { //To fetch last 30 days orders String dateFromToday =
			 * Util.getDateFromNow(30); qry = qry +
			 * " and bcwtCancelledOrder.cancelleddate >= to_date('"
			 * +dateFromToday+"','mm/dd/yyyy')"; }
			 * 
			 * if(!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderRequestId())){
			 * qry = qry +
			 * " and bcwtCancelledOrder.bcwtorder.orderid ="+bcwtReportSearchDTO
			 * .getOrderRequestId(); }
			 * if(!Util.isBlankOrNull(bcwtReportSearchDTO.getCardSerialNo())){
			 * qry = qry +
			 * " and bcwtOrderDetails.bcwtpatronbreezecard.breezecardid ="
			 * +bcwtReportSearchDTO.getCardSerialNo(); }
			 * if(!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())){ qry
			 * = qry +
			 * " and upper(bcwtCancelledOrder.bcwtorder.bcwtpatron.firstname) like '%"
			 * + bcwtReportSearchDTO.getFirstName().toUpperCase() + "%'"; }
			 * if(!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())){ qry =
			 * qry +
			 * " and upper(bcwtCancelledOrder.bcwtorder.bcwtpatron.lastname) like  '%"
			 * + bcwtReportSearchDTO.getLastName().toUpperCase() + "%'"; }
			 * if(!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderDate())){ qry
			 * = qry +
			 * " and to_date(bcwtCancelledOrder.bcwtorder.orderdate) = to_date('"
			 * + bcwtReportSearchDTO.getOrderDate() + "','mm/dd/yyyy')"; }
			 */
			session = getSession();
			transaction = session.beginTransaction();

			orderList = session.createQuery(qry).list();

			for (Iterator iter = orderList.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

				if (!Util.isBlankOrNull(orderType)
						&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
					if (null != element[0]) {
						bcwtReportDTO.setCardSerialNo(element[0].toString());
					}
				} else {
					bcwtReportDTO.setCardSerialNo("N/A");
				}
				if (null != element[1]) {
					bcwtReportDTO.setOrderRequestId(element[1].toString());
				}
				if (null != element[2]) {
					bcwtReportDTO.setFirstName(element[2].toString());
				}
				if (null != element[3]) {
					bcwtReportDTO.setLastName(element[3].toString());
				}
				if (null != element[4]) {
					bcwtReportDTO.setCancelledDate(Util
							.getFormattedDate(element[4]));
				}
				if (null != element[5]) {
					bcwtReportDTO.setMediasalesAdmin(element[5].toString());
				}
				if (null != element[6]) {
					bcwtReportDTO.setReasonType(element[6].toString());
				}
				if (null != element[7]) {
					bcwtReportDTO.setOrderType(element[7].toString());
				}
				reportDtoList.add(bcwtReportDTO);
			}
			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDtoList;
	}

	public List getQualityDetailedReport(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {
		final String MY_NAME = ME + "getQualityDetailedReport: ";
		BcwtsLogger.debug(MY_NAME);
		List orderList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List reportDtoList = new ArrayList();
		String qry = null;

		try {
			if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_IS)) {
				qry = Query.OFQualityDetailReportForIS;
			} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFQualityDetailReportForGBS;
			} else {
				return getPSQualityDetailsReport(bcwtReportSearchDTO);
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry + " and bcwtQualityResult.processeddate >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy') and "
						+ " bcwtQualityResult.processeddate < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				qry = qry + " and bcwtQualityResult.processeddate >= to_date('"
						+ bcwtReportSearchDTO.getOrderFromDate()
						+ "','mm/dd/yyyy')";
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				qry = qry + " and bcwtQualityResult.processeddate < to_date('"
						+ Util.getNextDay(bcwtReportSearchDTO.getOrderTodate())
						+ "','mm/dd/yyyy')";
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and bcwtQualityResult.processeddate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getQaFirstName())) {
				qry = qry
						+ " and upper(bcwtQualityResult.bcwtpatron.firstname) like '%"
						+ bcwtReportSearchDTO.getQaFirstName() + "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getQaLastName())) {
				qry = qry
						+ " and upper(bcwtQualityResult.bcwtpatron.lastname) like '%"
						+ bcwtReportSearchDTO.getQaLastName() + "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				qry = qry
						+ " and upper(bcwtOrder.bcwtpatron.firstname) like '%"
						+ bcwtReportSearchDTO.getFirstName() + "%'";
			}
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				qry = qry + " and upper(bcwtOrder.bcwtpatron.lastname) like '%"
						+ bcwtReportSearchDTO.getLastName() + "%'";
			}

			session = getSession();
			transaction = session.beginTransaction();

			orderList = session.createQuery(qry).list();

			for (Iterator iter = orderList.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();
				if (null != element[0]) {
					bcwtReportDTO.setFirstName(element[0].toString());
				}
				if (null != element[1]) {
					bcwtReportDTO.setLastName(element[1].toString());
				}
				if (null != element[2]) {
					bcwtReportDTO.setProcessedDate(Util
							.getFormattedDate(element[2]));
				}
				if (null != element[3]) {
					bcwtReportDTO.setOrderRequestId(element[3].toString());
				}
				if (null != element[4]) {
					bcwtReportDTO.setOrderType(element[4].toString());
				}
				reportDtoList.add(bcwtReportDTO);
			}

			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(e));
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return reportDtoList;
	}

	public List getMediaSalesPerformanceMetricsReport(
			BcwtReportSearchDTO bcwtReportSearchDTO) throws Exception {
		final String MY_NAME = ME + "getMediaSalesPerformanceMetricsReport:";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();
		String qry = null;

		try {
			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
				qry = Query.OFMediaSalesPerformanceMetricsReportForIS;

			} else if (!Util.isBlankOrNull(bcwtReportSearchDTO.getOrderType())
					&& bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFMediaSalesPerformanceMetricsReportForGBS;

			} else {
				return getPSMediaSalesPerformanceMetricsReport(bcwtReportSearchDTO);
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and to_date(bcwtOrder.orderdate) >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getFirstName())) {
				qry = qry
						+ " and upper(bcwtOrder.bcwtpatron.firstname) like '%"
						+ bcwtReportSearchDTO.getFirstName().toUpperCase()
						+ "%'";
			}
			if (!Util.isBlankOrNull(qry)
					&& !Util.isBlankOrNull(bcwtReportSearchDTO.getLastName())) {
				qry = qry + " and upper(bcwtOrder.bcwtpatron.lastname) like '%"
						+ bcwtReportSearchDTO.getLastName().toUpperCase()
						+ "%'";
			}
			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				Object[] element = (Object[]) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

				if (null != element[0]) {
					bcwtReportDTO.setOrderRequestId(element[0].toString());
				}
				if (null != element[1]) {
					bcwtReportDTO.setOrderDate(Util
							.getFormattedDate(element[1]));
				}
				if (null != element[2]) {
					bcwtReportDTO.setFirstName(element[2].toString());
				}
				if (null != element[3]) {
					bcwtReportDTO.setLastName(element[3].toString());
				}
				if (null != element[4]) {
					bcwtReportDTO.setQaFirstName(element[4].toString());
				}
				if (null != element[5]) {
					bcwtReportDTO.setQaLastName(element[5].toString());
				}
				if (null != element[6]) {
					bcwtReportDTO.setOrderType(element[6].toString());
				}
				reportDTOList.add(bcwtReportDTO);
			}

			transaction.commit();
			session.flush();
			session.close();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getSalesMetricsReport(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {
		final String MY_NAME = ME + "getSalesMetricsReport:";
		BcwtsLogger.debug(MY_NAME);

		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();

		try {
			String qry = null;
			if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_IS)) {
				qry = Query.OFSalesMetricReportForIS;
			} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFSalesMetricReportForGBS;
			} else {
				return getPSalesMetricsReportDetails(bcwtReportSearchDTO);
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtorder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " bcwtorder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtorder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtorder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtorder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			if (!Util.isBlankOrNull(bcwtReportSearchDTO.getZip())) {
				qry = qry + " and bcwtPatronAddress.zip = '"
						+ bcwtReportSearchDTO.getZip() + "'";
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

				if (null != element) {
					bcwtReportDTO.setNoOfCardsSold(element.toString());
				}

				bcwtReportDTO.setZip(bcwtReportSearchDTO.getZip());

				reportDTOList.add(bcwtReportDTO);
			}

			transaction.commit();
			session.flush();
			session.close();
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public List getRevenueReport(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {
		final String MY_NAME = ME + "getRevenueReport:";
		BcwtsLogger.info(MY_NAME);

		Session session = null;
		Transaction transaction = null;
		List reportDTOList = new ArrayList();

		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);

		String products = null;
		String qry = null;

		try {

			if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_IS)) {
				qry = Query.OFRevenueReportForIS;
			} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFRevenueReportForGBS;
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					BcwtReportDTO bcwtReportDTO = new BcwtReportDTO();

					if (element[0] != null) {
						bcwtReportDTO.setOrderRequestId(element[0].toString());

						products = getProducts(
								bcwtReportSearchDTO.getOrderType(),
								element[0].toString(), session);

						if (!Util.isBlankOrNull(products)) {
							bcwtReportDTO.setProducts(products);
						}
					}

					String customerName = null;
					if (element[1] != null) {
						customerName = element[1].toString();
					}
					if (element[2] != null) {
						customerName = customerName + " "
								+ element[2].toString();
					}
					if (element[3] != null) {
						customerName = customerName + " "
								+ element[3].toString();
					}
					if (!Util.isBlankOrNull(customerName)) {
						bcwtReportDTO.setCustomerName(customerName);
					}

					if (element[4] != null) {
						bcwtReportDTO.setOrderModule(element[4].toString());
					}
					if (element[5] != null) {
						bcwtReportDTO.setCreditCardType(element[5].toString());
					}

					double orderValue = 00.00;
					double shippingAmount = 00.00;
					double total = 00.00;

					if (element[6] != null) {
						orderValue = Double.parseDouble(element[6].toString());
					}
					if (element[7] != null) {
						shippingAmount = Double.parseDouble(element[7]
								.toString());
					}

					total = orderValue;
					bcwtReportDTO.setAmount(numberFormat.format(total));

					if (element[8] != null) {
						String securityKey = ConfigurationCache
								.getConfigurationValues(
										Constants.CREDIT_CARD_SECURITY_KEY)
								.getParamvalue();
						// BcwtsLogger.info("CC: "+element[8].toString() );
						String last4Digits = "";
						try {
							String creditCardNo = Util
									.removeSpaceInBreezeCardSerialNo(SimpleProtector
											.decrypt(
													element[8].toString(),
													Base64EncodeDecodeUtil
															.decodeString(securityKey))
											.trim());

							last4Digits = creditCardNo.substring(
									(creditCardNo.length() - 4),
									creditCardNo.length());
						} catch (Exception e) {

							bcwtReportDTO.setCreditCardLast4("");
						}
						bcwtReportDTO.setCreditCardLast4(last4Digits);
					}
					if (element[9] != null) {
						bcwtReportDTO.setTransactionDate(Util
								.getFormattedDate(element[9]));
					}
					if (element[10] != null) {
						bcwtReportDTO.setApprovalCode(element[10].toString());
					}

					if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
						if (element[11] != null) {
							bcwtReportDTO.setOrderType("New");
						} else {
							bcwtReportDTO.setOrderType("Reload");
						}
					} else if (bcwtReportSearchDTO.getOrderType()
							.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
						bcwtReportDTO.setOrderType("N/A");
					}

					if (element[12] != null) {
						bcwtReportDTO
								.setTransactionDate(element[12].toString());
					}

					reportDTOList.add(bcwtReportDTO);
				}
			}

			transaction.commit();
			session.flush();
			session.close();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return reportDTOList;
	}

	public BcwtReportDTO getRevenueSummaryReport(
			BcwtReportSearchDTO bcwtReportSearchDTO, String creditCardType)
			throws Exception {
		final String MY_NAME = ME + "getRevenueSummaryReport:";
		BcwtsLogger.info(MY_NAME);

		Session session = null;
		Transaction transaction = null;

		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);

		BcwtReportDTO bcwtReportDTO = null;
		String qry = null;

		try {

			if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_IS)) {
				qry = Query.OFRevenueSummaryReportForIS;
			} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFRevenueSummaryReportForGBS;
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			if (!Util.isBlankOrNull(creditCardType)) {
				if (creditCardType.equalsIgnoreCase(Constants.VISA)) {
					qry = qry
							+ " and bcwtOrder.bcwtpatronpaymentcards.bcwtcreditcardtype.creditcardtypeid in ("
							+ Constants.CREDITCARD_TYPE_VISA + ", "
							+ Constants.CREDITCARD_TYPE_VISA_2 + ")";
				}
				if (creditCardType.equalsIgnoreCase(Constants.MASTERCARD)) {
					qry = qry
							+ " and bcwtOrder.bcwtpatronpaymentcards.bcwtcreditcardtype.creditcardtypeid in ("
							+ Constants.CREDITCARD_TYPE_MASTERCARD + ", "
							+ Constants.CREDITCARD_TYPE_MASTERCARD_2 + ")";
				}
				if (creditCardType.equalsIgnoreCase(Constants.DISCOVER)) {
					qry = qry
							+ " and bcwtOrder.bcwtpatronpaymentcards.bcwtcreditcardtype.creditcardtypeid = "
							+ Constants.CREDITCARD_TYPE_DISCOVER;
				}
				if (creditCardType.equalsIgnoreCase(Constants.AMEX)) {
					qry = qry
							+ " and bcwtOrder.bcwtpatronpaymentcards.bcwtcreditcardtype.creditcardtypeid = "
							+ Constants.CREDITCARD_TYPE_AMEX;
				}
			} else {
				BcwtsLogger.error(MY_NAME + "creditCardType is null");
				throw new Exception(MY_NAME + "creditCardType is null");
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					bcwtReportDTO = new BcwtReportDTO();

					if (element[0] != null) {
						bcwtReportDTO.setNoOfOrders(element[0].toString());
					}

					double orderValue = 00.00;
					double shippingAmount = 00.00;
					double total = 00.00;

					if (element[1] != null) {
						orderValue = Double.parseDouble(element[1].toString());
					}
					if (element[2] != null) {
						shippingAmount = Double.parseDouble(element[2]
								.toString());
					}

					total = orderValue;
					bcwtReportDTO.setAmount(numberFormat.format(total));

					if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
							Constants.ORDER_TYPE_IS)) {
						bcwtReportDTO.setOrderModule(Constants.ORDER_TYPE_IS);
					} else if (bcwtReportSearchDTO.getOrderType()
							.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
						bcwtReportDTO.setOrderModule(Constants.ORDER_TYPE_GBS);
					}
					break;
				}
			}

			transaction.commit();
			session.flush();
			session.close();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.info(MY_NAME + " Resources closed");
		}
		return bcwtReportDTO;
	}

	private String getProducts(String orderType, String orderId, Session session)
			throws Exception {
		final String MY_NAME = ME + "getProducts:";
		BcwtsLogger.info(MY_NAME);

		String products = null;
		String qry = null;

		try {

			if (orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				qry = "select"
						+ " bcwtBreezeCardProduct.bcwtproduct.productname"
						+ " from"
						+ " BcwtBreezeCardProduct bcwtBreezeCardProduct,"
						+ " BcwtOrderDetails bcwtOrderDetails,"
						+ " BcwtOrder bcwtOrder"
						+ " where"
						+ " bcwtOrder.orderid = "
						+ orderId
						+ " and bcwtOrder.orderid = bcwtOrderDetails.bcwtorder.orderid"
						+ " and bcwtOrderDetails.orderdetailsid = bcwtBreezeCardProduct.bcwtorderdetails.orderdetailsid";
			} else if (orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				qry = "select"
						+ " bcwtGbsProductDetails.bcwtproduct.productname"
						+ " from"
						+ " BcwtGbsProductDetails bcwtGbsProductDetails,"
						+ " BcwtOrderInfo bcwtOrderInfo,"
						+ " BcwtOrder bcwtOrder"
						+ " where"
						+ " bcwtOrder.orderid = "
						+ orderId
						+ " and bcwtOrder.orderid = bcwtOrderInfo.bcwtorder.orderid"
						+ " and bcwtOrderInfo.orderinfoid = bcwtGbsProductDetails.bcwtorderinfo.orderinfoid";
			}

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						if (!Util.isBlankOrNull(products)) {
							products = products + element.toString() + ", ";
						} else {
							products = element.toString() + ", ";
						}
					}
				}
			}

			if (!Util.isBlankOrNull(products)) {
				products = products.substring(0, (products.length() - 2));
			}
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		}
		return products;
	}

	public int getNumberOfPendingOrders(String orderType, String fromDate,
			String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfPendingOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfOutstandingOrders = 0;
		Session session = null;
		Transaction transaction = null;

		try {
			String qry = null;
			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				qry = "select" + " count(*)" + " from" + " BcwtOrder bcwtOrder"
						+ " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_PENDING
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_IS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				qry = "select" + " count(*)" + " from" + " BcwtOrder bcwtOrder"
						+ " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_PENDING
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_GBS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				qry = "select"
						+ " count(*)"
						+ " from"
						+ " PartnerCardOrder partnercardorder"
						+ " where "
						+ " partnercardorder.partnerCardorderstatus.orderStatusid = "
						+ Constants.ORDER_STATUS_PS_SUBMITTED;
			}

			if (!Util.isBlankOrNull(fromDate) && !Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(fromDate)) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";
				}
			} else if (!Util.isBlankOrNull(toDate)) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and bcwtOrder.orderdate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
				}
			} else if (!orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and bcwtOrder.orderdate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			} else {
				String dateFromToday = Util.getDateFromNow(30);
				qry = qry + " and partnercardorder.orderDate >= to_date('"
						+ dateFromToday + "','mm/dd/yyyy')";
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(qry).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						noOfOutstandingOrders = Integer.parseInt(element
								.toString());
						break;
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got noOfPendingOrders = "
					+ noOfOutstandingOrders);
		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return noOfOutstandingOrders;
	}

	public int getNumberOfOutstandingOrders(String orderType, String fromDate,
			String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfOutstandingOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfOutstandingOrders = 0;
		Session session = null;
		Transaction transaction = null;

		try {
			String query = null;
			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtOrder bcwtOrder" + " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_PENDING
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_IS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtOrder bcwtOrder" + " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid != "
						+ Constants.ORDER_STATUS_SHIPPED
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_GBS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " PartnerCardOrder partnercardorder"
						+ " where "
						+ " partnercardorder.partnerCardorderstatus.orderStatusid != "
						+ Constants.ORDER_STATUS_PS_SHIPPED;
			}

			if (!orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query + " and bcwtOrder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			} else {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query
							+ " and partnercardorder.orderDate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						noOfOutstandingOrders = Integer.parseInt(element
								.toString());
						break;
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got noOfOutstandingOrders = "
					+ noOfOutstandingOrders);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return noOfOutstandingOrders;
	}

	public int getNumberOfFulfilledOrders(String orderType, String fromDate,
			String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfFulfilledOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfFulfilledOrders = 0;
		Session session = null;
		Transaction transaction = null;

		try {
			String query = null;
			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtOrder bcwtOrder" + " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_SHIPPED
						+ " or bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_COMPLETED
						+ "  and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_IS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtOrder bcwtOrder" + " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_SHIPPED
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_GBS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " PartnerCardOrder partnercardorder"
						+ " where "
						+ " partnercardorder.partnerCardorderstatus.orderStatusid = "
						+ Constants.ORDER_STATUS_PS_SHIPPED;
			}

			if (!orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query + " and bcwtOrder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			} else {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query
							+ " and partnercardorder.orderDate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						noOfFulfilledOrders = Integer.parseInt(element
								.toString());
						break;
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got noOfFulfilledOrders = "
					+ noOfFulfilledOrders);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return noOfFulfilledOrders;
	}

	public int getNumberOfCancelledOrders(String orderType, String fromDate,
			String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfCancelledOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfCancelledOrders = 0;
		Session session = null;
		Transaction transaction = null;

		try {
			String query = null;
			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " BcwtOrder bcwtOrder, BcwtCancelledOrder bcwtCancelledOrder"
						+ " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_CANCELLED
						+ " and bcwtCancelledOrder.bcwtorder.orderid = bcwtOrder.orderid"
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_IS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " BcwtOrder bcwtOrder, BcwtCancelledOrder bcwtCancelledOrder"
						+ " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_CANCELLED
						+ " and bcwtCancelledOrder.bcwtorder.orderid = bcwtOrder.orderid"
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_GBS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " PartnerCardOrder partnercardorder, BcwtPartnerCancelledOrder bcwtPartnerCancelledOrder"
						+ " where "
						+ " partnercardorder.partnerCardorderstatus.orderStatusid = "
						+ Constants.ORDER_STATUS_PS_REJECTED
						+ " and bcwtPartnerCancelledOrder.partnercardorder.orderId = partnercardorder.orderId";
			}

			if (!orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query + " and bcwtOrder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			} else {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query
							+ " and partnercardorder.orderDate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						noOfCancelledOrders = Integer.parseInt(element
								.toString());
						break;
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got noOfCancelledOrders = "
					+ noOfCancelledOrders);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return noOfCancelledOrders;
	}

	public int getNumberOfReturnedOrders(String orderType, String fromDate,
			String toDate) throws Exception {
		final String MY_NAME = ME + "getNumberOfReturnedOrders: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfReturnedOrders = 0;
		Session session = null;
		Transaction transaction = null;

		try {
			String query = null;
			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " BcwtOrder bcwtOrder, BcwtReturnedOrder bcwtReturnedOrder"
						+ " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_RETURNED
						+ " and bcwtReturnedOrder.bcwtorder.orderid = bcwtOrder.orderid"
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_IS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " BcwtOrder bcwtOrder, BcwtReturnedOrder bcwtReturnedOrder"
						+ " where"
						+ " bcwtOrder.bcwtorderstatus.orderstatusid = "
						+ Constants.ORDER_STATUS_RETURNED
						+ " and bcwtReturnedOrder.bcwtorder.orderid = bcwtOrder.orderid"
						+ " and bcwtOrder.orderType = '"
						+ Constants.ORDER_TYPE_GBS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				query = "select"
						+ " count(*)"
						+ " from"
						+ " PartnerCardOrder partnercardorder, BcwtPartnerReturnedOrder bcwtPartnerReturnedOrder"
						+ " where "
						+ " partnercardorder.partnerCardorderstatus.orderStatusid = "
						+ Constants.ORDER_STATUS_PS_RETURNED
						+ " and bcwtPartnerReturnedOrder.partnercardorder.orderId = partnercardorder.orderId";
			}

			if (!orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query + " and bcwtOrder.orderdate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query + " and bcwtOrder.orderdate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			} else {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy') and "
								+ " partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(fromDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate >= to_date('"
								+ fromDate + "','mm/dd/yyyy')";
					}
				} else if (!Util.isBlankOrNull(toDate)) {
					if (!Util.isBlankOrNull(query)) {
						query = query
								+ " and partnercardorder.orderDate < to_date('"
								+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";
					}
				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query
							+ " and partnercardorder.orderDate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						noOfReturnedOrders = Integer.parseInt(element
								.toString());
						break;
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got noOfReturnedOrders = "
					+ noOfReturnedOrders);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return noOfReturnedOrders;
	}

	public int getNumberOfFails(String orderType, String fromDate, String toDate)
			throws Exception {
		final String MY_NAME = ME + "getNumberOfFails: ";
		BcwtsLogger.debug(MY_NAME);
		int noOfFails = 0;
		Session session = null;
		Transaction transaction = null;

		try {
			String query = null;
			if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_IS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtQualityResult bcwtQualityResult" + " where"
						+ " bcwtQualityResult.qualityresult = '"
						+ Constants.Fail + "'"
						+ " and bcwtQualityResult.bcwtorder.orderType = '"
						+ Constants.ORDER_TYPE_IS + "'"
						+ " and bcwtQualityResult.activestatus = '"
						+ Constants.ACTIVE_STATUS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtQualityResult bcwtQualityResult" + " where"
						+ " bcwtQualityResult.qualityresult = '"
						+ Constants.Fail + "'"
						+ " and bcwtQualityResult.bcwtorder.orderType = '"
						+ Constants.ORDER_TYPE_GBS + "'"
						+ " and bcwtQualityResult.activestatus = '"
						+ Constants.ACTIVE_STATUS + "'";

			} else if (!Util.isBlankOrNull(orderType)
					&& orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				query = "select" + " count(*)" + " from"
						+ " BcwtPartnerQualityResult bcwtPartnerQualityResult"
						+ " where"
						+ " bcwtPartnerQualityResult.qualityresult = '"
						+ Constants.Fail + "'"
						+ " and bcwtPartnerQualityResult.activestatus = '"
						+ Constants.ACTIVE_STATUS + "'";

			} else if (!orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					query = query
							+ " and bcwtQualityResult.processeddate >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " bcwtQualityResult.processeddate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";

				} else if (!Util.isBlankOrNull(fromDate)) {
					query = query
							+ " and bcwtQualityResult.processeddate >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";

				} else if (!Util.isBlankOrNull(toDate)) {
					query = query
							+ " and bcwtQualityResult.processeddate < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";

				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query
							+ " and bcwtQualityResult.processeddate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			} else {
				if (!Util.isBlankOrNull(fromDate)
						&& !Util.isBlankOrNull(toDate)) {
					query = query
							+ " and bcwtPartnerQualityResult.processed >= to_date('"
							+ fromDate + "','mm/dd/yyyy') and "
							+ " bcwtPartnerQualityResult.processed < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";

				} else if (!Util.isBlankOrNull(fromDate)) {
					query = query
							+ " and bcwtPartnerQualityResult.processed >= to_date('"
							+ fromDate + "','mm/dd/yyyy')";

				} else if (!Util.isBlankOrNull(toDate)) {
					query = query
							+ " and bcwtPartnerQualityResult.processed < to_date('"
							+ Util.getNextDay(toDate) + "','mm/dd/yyyy')";

				} else {
					// To fetch last 30 days orders
					String dateFromToday = Util.getDateFromNow(30);
					query = query
							+ " and bcwtPartnerQualityResult.processed >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}

			session = getSession();
			transaction = session.beginTransaction();

			List queryList = session.createQuery(query).list();

			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object element = (Object) iter.next();
					if (element != null) {
						noOfFails = Integer.parseInt(element.toString());
						break;
					}
				}
			}

			transaction.commit();
			session.flush();
			session.close();
			BcwtsLogger.info(MY_NAME + "Got noOfFails = " + noOfFails);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return noOfFails;
	}

	/***************** OF - Ends here **********************/

	// --------------------------------------Sunil Code
	// Starts---------------------------------------- Query chnaged by Ubaid
	// Ju;y 31 2012

	public int getActivebenefitsofCompany(String companyId) throws Exception {
		// String NewQuery =
		// "select A.SERIAL_NBR,A.CUSTOMER_MEMBER_ID,A.FIRST_NAME,A.LAST_NAME, B.FARE_INSTRUMENT_ID,B.BENEFIT_NAME, C.HOTLIST_ACTION from NEXTFARE_MAIN.PPB_MEMBER_INFO_V A, NEXTFARE_MAIN.PPB_BENEFIT_INFO_V B, NEXTFARE_MAIN.FARE_MEDIA_INVENTORY C where B.CUSTOMER_ID=?  and A.BENEFIT_STATUS_ID=1 and A.CUSTOMER_ID=B.CUSTOMER_ID and A.SERIAL_NBR=B.SERIAL_NBR and A.MEMBER_ID=B.MEMBER_ID and c.SERIAL_NBR=B.SERIAL_NBR";

		String NewQuery = "select A.SERIAL_NBR,A.CUSTOMER_MEMBER_ID,A.FIRST_NAME,A.LAST_NAME, C.HOTLIST_ACTION "
				+ " from NEXTFARE_MAIN.PPB_MEMBER_INFO_V A, NEXTFARE_MAIN.FARE_MEDIA_INVENTORY C "
				+ "  where A.CUSTOMER_ID=?  and A.BENEFIT_STATUS_ID=1 "
				+ "  and c.SERIAL_NBR=A.SERIAL_NBR ";

		Connection conn = null;
		ResultSet resultSet = null;
		PreparedStatement prepStmt = null;
		Collection reportRequestVOs = new ArrayList();
		conn = NextFareConnection.getConnection();
		int count = 0;
		try {
			prepStmt = conn.prepareStatement(NewQuery);
			String nextfareid = getCubiccompanyid(companyId);
			prepStmt.setString(1, nextfareid);
			resultSet = prepStmt.executeQuery();
			while (resultSet.next()) {

				if (Util.isBlankOrNull(resultSet.getString(5))
						|| resultSet.getString(5).equals("0")) {

					count++;

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
				if (prepStmt != null)
					prepStmt.close();
				if (conn != null)
					conn.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return count;
	}

	
	public List<PartnerCompanyInfo> getCompanyList(String tmaid) throws Exception {
		final String MY_NAME = ME + "getNextFareCustomerId: ";
		BcwtsLogger.info(MY_NAME + "for companyid:" +tmaid );
		Transaction transaction = null;
		Session session = null;
		PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();
List<PartnerCompanyInfo> companyList = new ArrayList();
		try {
			session = getSession();
			transaction = session.beginTransaction();

		/*	String query = "from PartnerCompanyInfo partnerCompanyInfo" +
					" where partnerCompanyInfo.companyId = (select partnerAdminDetails.com from PartnerAdminDetails partnerAdminDetails ompanyId from p'" + companyId + "'";
			*/
			String query =  " from PartnerCompanyInfo partnerCompanyInfo where partnerCompanyInfo.companyId in "
					+ "(select tcl.id.companyId from TmaCompanyLink tcl where tcl.id.tmaId='"+tmaid+"')";

			companyList = (List<PartnerCompanyInfo>)
								session.createQuery(query).list();

			transaction.commit();
			session.clear();
			BcwtsLogger.info(MY_NAME + "got getNextFareCustomerId");
		} catch (Exception ex) {
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));

			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		return companyList;
	}
	
	public String getCubiccompanyid(String id) {

		String nextfareid = "";
		ResultSet resultSet = null;
		PreparedStatement prepStmt = null;
		Connection conn = null;
		try {
			conn = BatchOMJobDAO.getConnection();
			prepStmt = conn
					.prepareStatement("select nextfare_company_id from partner_companyinfo where company_id=?");
			prepStmt.setString(1, id);
			resultSet = prepStmt.executeQuery();
			while (resultSet.next()) {
				nextfareid = resultSet.getString("NEXTFARE_COMPANY_ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (resultSet != null)
					resultSet.close();
				if (prepStmt != null)
					prepStmt.close();
			} catch (Exception e) {
				System.out.println("Exception while closing Connetions....");
				e.printStackTrace();
			}
		}

		return nextfareid;
	}

	public List getTmacompanyList(String tma_id) {
		List list = new ArrayList();
		String PartnerAdminGetSecurityQuestionsQuery = "select * from TMA_COMPANY_LINK where TMA_ID=? order by company_name asc";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			conn = BatchOMJobDAO.getConnection();
			ps = conn.prepareStatement(PartnerAdminGetSecurityQuestionsQuery
					.trim());
			ps.setString(1, tma_id);
			rs = ps.executeQuery();
			while (rs.next()) {
				CompanyInfoDTO tcvo = new CompanyInfoDTO();
				tcvo.setCompanyName(rs.getString("COMPANY_NAME"));
				tcvo.setCompanyId(rs.getString("COMPANY_ID"));
				list.add(tcvo);
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;

	}

	public String getTmaID(String tma_name) {
		String tmaid = null;
		String PartnerAdminGetSecurityQuestionsQuery = "select TMA_ID from TMA_INFORMATION where lower(TMA_NAME)=?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			conn = BatchOMJobDAO.getConnection();
			ps = conn.prepareStatement(PartnerAdminGetSecurityQuestionsQuery
					.trim());
			ps.setString(1, tma_name.toLowerCase());
			rs = ps.executeQuery();
			if (rs.next()) {
				tmaid = rs.getInt("TMA_ID") + "".trim();
			} else {
				if (tma_name.equalsIgnoreCase("PARTNERS")) {
					tmaid = "10";
				}
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tmaid;

	}
	
	
	public String getTmaName(String tmaid) {
		String tmaname = null;
		String PartnerAdminGetSecurityQuestionsQuery = "select TMA_NAME from TMA_INFORMATION where (TMA_ID)=?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;

		try {
			conn = BatchOMJobDAO.getConnection();
			ps = conn.prepareStatement(PartnerAdminGetSecurityQuestionsQuery
					.trim());
			ps.setString(1, tmaid);
			rs = ps.executeQuery();
			if (rs.next()) {
				tmaname = rs.getString("TMA_NAME");
			
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rs.close();
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tmaname;

	}

	// --------------------------------------Sunil Code
	// Ends--------------------------------------------

	/**
	 * To get details for new card report.
	 * 
	 * @param reportForm
	 * @return newCardDetailsList
	 * @throws Exception
	 */
	public List getNewCardReportList(ReportForm reportForm) throws Exception {
		final String MY_NAME = ME + "getNewCardReportList: ";
		BcwtsLogger.debug(MY_NAME + " for company id"
				+ reportForm.getCompanyId());
		List newCardDetailsList = new ArrayList();
		String companyId = null;
		Connection nextFareConnection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = null;
		String strFromDate = null;
		String strToDate = null;
		ListCardsDAO psDAO = new ListCardsDAO();

		try {
			// partnerCompanyInfo =
			// psDAO.getNextFareCustomerId(reportForm.getCompanyId().toString());
			companyId = reportForm.getCompanyId().toString();

			if (Util.isBlankOrNull(companyId)) {
				throw new MartaException(MY_NAME + "Company Id is null");
			} else {
				SimpleDateFormat sdfInput = new SimpleDateFormat("dd/mm/yyyy");
				SimpleDateFormat sdfOutput = new SimpleDateFormat("mm/dd/yyyy");

				Date fromDate = sdfInput.parse("01/"
						+ reportForm.getMonthYear());

				int month = Integer.parseInt(reportForm.getCardToMonth());
				int year = Integer.parseInt(reportForm.getCardToYear());
				Date toDate = sdfInput.parse(Util.lastDayOfMonth(month, year)
						+ "/" + reportForm.getMonthYearTo());

				strFromDate = sdfOutput.format(fromDate);
				strToDate = sdfOutput.format(toDate);

				query = "select"
						+ " distinct mbh.SERIAL_NBR, mbh.SERIAL_NBR_ASSIGNED_DTM,  cbd.benefit_name"
						+ " from"
						+ " NEXTFARE_MAIN.MEMBER mbh, nextfare_main.customer_benefit_definition cbd"
						+

						" where" +

						"  mbh.CUSTOMER_ID = " + companyId
						+ "  and mbh.CUSTOMER_ID = cbd.customer_id "
						+ " and CBD.BENEFIT_STATUS_ID = '1'"
						+ "and mbh.SERIAL_NBR_ASSIGNED_DTM >= TO_DATE('"
						+ strFromDate + "', " + "'MM/DD/YYYY')"
						+ " and mbh.SERIAL_NBR_ASSIGNED_DTM <= TO_DATE('"
						+ strToDate + "', " + "'MM/DD/YYYY')";

				nextFareConnection = NextFareConnection.getConnection();

				if (nextFareConnection != null) {
					pstmt = nextFareConnection.prepareStatement(query);
					if (pstmt != null) {
						rs = pstmt.executeQuery();
						if (rs != null) {
							while (rs.next()) {

								BcwtNewCardReportDTO newCardReportDTO = new BcwtNewCardReportDTO();
								newCardReportDTO.setBreezecardSerialNo(rs
										.getString("SERIAL_NBR"));
								newCardReportDTO.setBenefitName(rs
										.getString("BENEFIT_NAME"));
								newCardReportDTO
										.setEffectiveDate(Util.getFormattedDate(rs
												.getObject("SERIAL_NBR_ASSIGNED_DTM")));

								newCardDetailsList.add(newCardReportDTO);
							}
						}
					}
				}
			}

			BcwtsLogger.info(MY_NAME + "got new card details list");
		} catch (Exception ex) {
			// ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			pstmt.close();
			if (nextFareConnection != null) {
				nextFareConnection.close();
			}
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return newCardDetailsList;
	}

	public List getMonthlyUsageList(String date, String customerId)
			throws Exception {

		final String MY_NAME = ME + "getMonthlyUsageList:" + customerId;
		BcwtsLogger.debug(MY_NAME);
		List activeBenefitsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		PartnerReportSearchDTO partnerReportSearchDTO = null;

		Connection con = null;
		int count_Month = 0;
		int count_Year = 0;
		int benefit_Id = 0;
		String benefitType = null;
		String firstDate = null;
		String mont = null;
		String dd = null;
		String yr = null;
		ArrayList allList = new ArrayList();
		ArrayList activeList = new ArrayList();
		String monthUsage = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date today = df.parse(date);
		DateFormat dfts = new SimpleDateFormat("dd/M/yyyy");
		StringTokenizer st = new StringTokenizer(dfts.format(today), "//");
		ResultSet rs = null;
		ResultSet rs1 = null;
		;
		while (st.hasMoreTokens()) {
			dd = st.nextToken();
			mont = st.nextToken();
			yr = st.nextToken();
			firstDate = mont + "/" + 01 + "/" + yr;
		}
		Date ftdat = df.parse(firstDate);

		try {

			session = getSession();
			transaction = session.beginTransaction();
			con = NextFareConnection.getConnection();
			Statement stmt = con.createStatement();
			Statement stmtAll = con.createStatement();

			String queryActive = "select MEMBER.SERIAL_NBR from nextfare_main.member member where MEMBER.CUSTOMER_ID = "
					+ customerId
					+ " and MEMBER.SERIAL_NBR in "
					+ " (select  USE_TRANSACTION.SERIAL_NBR  from nextfare_main.use_transaction use_transaction"
					+ " where  USE_TRANSACTION.TRANSACTION_DTM > to_date('"
					+ dfts.format(ftdat)
					+ "','DD-MM-YYYY') and "
					+ " USE_TRANSACTION.TRANSACTION_DTM < last_day(to_date('"
					+ dfts.format(today) + "','DD-MM-YYYY'))) ";

			String queryAll = "select member.serial_nbr, cbd.benefit_name "
					+ "from nextfare_main.member member, nextfare_main.customer_benefit_definition cbd "
					+ "where  MEMBER.CUSTOMER_ID = " + customerId
					+ " and cbd.customer_id =  member.customer_id "
					+ " and cbd.benefit_status_id = '1'";

			rs = stmt.executeQuery(queryActive);

			rs1 = stmtAll.executeQuery(queryAll);

			// System.out.println("fff"+rs.getFetchSize());
			partnerReportSearchDTO = new PartnerReportSearchDTO();
			if (rs.next()) {

				while (rs.next()) {
					activeList.add(rs.getString(1));
				}

				System.out.println("Inside If");

				if (rs1 != null) {

					while (rs1.next()) {

						for (Iterator iter = activeList.iterator(); iter
								.hasNext();) {

							String temp = (String) iter.next();
							if (temp.equals(rs1.getString(1))) {
								partnerReportSearchDTO.setMonthlyUsage("Yes");
								DateFormat dft = new SimpleDateFormat("MMM yy");
								partnerReportSearchDTO.setCardSerialNo(rs1
										.getString(1));
								partnerReportSearchDTO.setBenefitType(rs1
										.getString(2));
								partnerReportSearchDTO.setBillingMonth(dft
										.format(today));
								break;
							} else {

								partnerReportSearchDTO.setMonthlyUsage("No");
								DateFormat dft = new SimpleDateFormat("MMM yy");
								partnerReportSearchDTO.setCardSerialNo(rs1
										.getString(1));
								partnerReportSearchDTO.setBenefitType(rs1
										.getString(2));
								partnerReportSearchDTO.setBillingMonth(dft
										.format(today));
							}
						}

						activeBenefitsList.add(partnerReportSearchDTO);
					}
				}
			} else {
				while (rs1.next()) {

					partnerReportSearchDTO.setMonthlyUsage("No");
					DateFormat dft = new SimpleDateFormat("MMM yy");
					partnerReportSearchDTO.setCardSerialNo(rs1.getString(1));
					partnerReportSearchDTO.setBenefitType(rs1.getString(2));
					partnerReportSearchDTO.setBillingMonth(dft.format(today));
					activeBenefitsList.add(partnerReportSearchDTO);
				}

			}
			rs.close();
			rs1.close();

		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return activeBenefitsList;
	}

	public List getUpcommingMonthlyReportList(Long companyid) throws Exception {

		final String MY_NAME = ME + "getUpcommingMonthlyReportList1:";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Connection con = null;
		ResultSet rs = null;
		Statement stmt = null;
		con = NextFareConnection.getConnection();

		try {

			if (null != companyid) {
				String query = "SELECT  MB.SERIAL_NBR,MB.FIRST_NAME,MB.LAST_NAME,MB.CUSTOMER_MEMBER_ID,"
						+ "MB.BENEFIT_STATUS_ID,INVENTORY.HOTLIST_ACTION FROM NEXTFARE_MAIN.MEMBER MB, "
						+ "NEXTFARE_MAIN.FARE_MEDIA_INVENTORY INVENTORY WHERE MB.CUSTOMER_ID='"
						+ companyid
						+ "'"
						+ " AND  MB.BENEFIT_STATUS_ID = '1'  and  "
						+ "INVENTORY.SERIAL_NBR = MB.SERIAL_NBR order by MB.LAST_NAME asc";
				stmt = con.createStatement();
				rs = stmt.executeQuery(query);
				if (rs != null) {

					while (rs.next()) {

						String memberName = rs.getString(2) + " "
								+ rs.getString(3);
						UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
						upcommingmonthbenefitdto.setCardSerialNumber(rs
								.getString(1));
						upcommingmonthbenefitdto.setMemberName(memberName);
						upcommingmonthbenefitdto.setMemberId(rs.getString(4));
						if (null != rs.getString(5)) {
							// if(checkHotlist(rs.getString(1))){//NextFareMethods.IsHotlisted(rs.getString(1),
							// "160")){
							/*
							 * if (!rs.getString(6).equals("0")) {
							 * upcommingmonthbenefitdto
							 * .setIsBenefitActivated("No");
							 * 
							 * } else { if ("1".equals(rs.getString(5))) {
							 * 
							 * upcommingmonthbenefitdto
							 * .setIsBenefitActivated("Yes"); } else {
							 * 
							 * upcommingmonthbenefitdto
							 * .setIsBenefitActivated("No");
							 * 
							 * } }
							 */
							upcommingmonthbenefitdto
									.setIsBenefitActivated("No");
						}
						monthlySet.add(upcommingmonthbenefitdto);

					}

				}
			}
		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			rs.close();
			stmt.close();
			con.close();
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return new ArrayList(monthlySet);
	}

	public List getHotlistCardDetails(Long companyIdStr) throws Exception {
		final String MY_NAME = ME + "getHotlistCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from UpassActivateList upassActivateList where upassActivateList.nfsid = "
					+ companyIdStr;
			List queryList = session.createQuery(query).list();
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				UpassActivateList upassActivateList = (UpassActivateList) iter
						.next();

				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(upassActivateList
						.getSerialnumber() + " queue");
				upcommingmonthbenefitdto
						.setMemberName(upassActivateList.getFirstname() + " "
								+ upassActivateList.getLastname());
				// upcommingmonthbenefitdto.setMemberId(partnerHotlistHistory.getMemberId());

				// Sunil code fix to show customer memberid insted of member id
				upcommingmonthbenefitdto.setMemberId(upassActivateList
						.getCustomermemberid());

				if ("yes".equals(upassActivateList.getActivatestatus()))
					upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
				else
					upcommingmonthbenefitdto.setIsBenefitActivated("No");

				monthlySet.add(upcommingmonthbenefitdto);
			}

			transaction.commit();
			session.flush();
			BcwtsLogger
					.info(MY_NAME + "got hotlist card details list from DB ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return new ArrayList(monthlySet);
	}

	public List getNewCardDetails(Long partnerId) throws Exception {
		final String MY_NAME = ME + "getNewCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from UpassNewCard upassNewCard where upassNewCard.nextfareid = "
					+ partnerId;

			List queryList = session.createQuery(query).list();

			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				UpassNewCard upassNewCard = (UpassNewCard) iter.next();
				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(upassNewCard
						.getSerialnumber() + " New Card");
				upcommingmonthbenefitdto.setMemberName(upassNewCard
						.getFirstname() + " " + upassNewCard.getLastname());
				upcommingmonthbenefitdto.setMemberId(upassNewCard
						.getCustomermemberid().toString());
				upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
				monthlySet.add(upcommingmonthbenefitdto);
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got new card details list from DB ");
		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return new ArrayList(monthlySet);
	}

	public List getBatchDetailsList(Long companyIdStr) throws Exception {
		final String MY_NAME = ME + "PartnerBatchDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Set monthlySet = new HashSet();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from UpassBatchDetails upassBatchDetails where upassBatchDetails.nfsid = "
					+ companyIdStr;
			List queryList = session.createQuery(query).list();
			for (Iterator iter = queryList.iterator(); iter.hasNext();) {
				UpassBatchDetails upassBatchDetails = (UpassBatchDetails) iter
						.next();

				UpCommingMonthBenefitDTO upcommingmonthbenefitdto = new UpCommingMonthBenefitDTO();
				upcommingmonthbenefitdto.setCardSerialNumber(upassBatchDetails
						.getSerialnumber() + " queue");
				upcommingmonthbenefitdto
						.setMemberName(upassBatchDetails.getFirstname() + " "
								+ upassBatchDetails.getLastname());
				upcommingmonthbenefitdto.setMemberId(upassBatchDetails
						.getCustomermemberid());

				if ("Activate".equalsIgnoreCase(upassBatchDetails
						.getBenefitactionid()))
					upcommingmonthbenefitdto.setIsBenefitActivated("Yes");
				else
					upcommingmonthbenefitdto.setIsBenefitActivated("No");

				monthlySet.add(upcommingmonthbenefitdto);
			}

			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME
					+ "got  partnerBatchDetails list from DB :" + companyIdStr);
		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}

		return new ArrayList(monthlySet);
	}

	public List getTMAList() throws Exception {

		final String MY_NAME = ME + "getTMAList: ";
		BcwtsLogger.debug(MY_NAME);
		List<TmaInformation> tmaList = new ArrayList<TmaInformation>();
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from TmaInformation tmaInformation";
			tmaList = session.createQuery(query).list();

		} catch (Exception ex) {

			BcwtsLogger.error(MY_NAME + Util.getFormattedStackTrace(ex));
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return tmaList;
	}

	public List getRevenueReportNew(BcwtReportSearchDTO bcwtReportSearchDTO)
			throws Exception {
		final String MY_NAME = ME + "getRevenueReportNew:";
		BcwtsLogger.info(MY_NAME);

		List reportDTOList = new ArrayList();
		BcwtReportDTO bcwtReportDTO = null;

		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);

		String products = null;
		String qry = null;

		Connection conn = null;

		Statement st = null;
		ResultSet rs = null;

		try {

			if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_IS)) {
				qry = Query.OFRevenueReportForISNew;
			} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
					Constants.ORDER_TYPE_GBS)) {
				qry = Query.OFRevenueReportForGBS;
			}

			if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")
					&& null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " where transactiondate  >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy') and "
							+ " transactiondate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderFromDate()
					&& !bcwtReportSearchDTO.getOrderFromDate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and transactiondate >= to_date('"
							+ bcwtReportSearchDTO.getOrderFromDate()
							+ "','mm/dd/yyyy')";
				}
			} else if (null != bcwtReportSearchDTO.getOrderTodate()
					&& !bcwtReportSearchDTO.getOrderTodate().equals("")) {
				if (!Util.isBlankOrNull(qry)) {
					qry = qry
							+ " and transactiondate < to_date('"
							+ Util.getNextDay(bcwtReportSearchDTO
									.getOrderTodate()) + "','mm/dd/yyyy')";
				}
			} else {
				// To fetch last 30 days orders
				String dateFromToday = Util.getDateFromNow(30);
				if (!Util.isBlankOrNull(qry)) {
					qry = qry + " and transactiondate >= to_date('"
							+ dateFromToday + "','mm/dd/yyyy')";
				}
			}
			qry = qry
					+ "group by orderid, firstname, lastname, addressid, price, priceofbreezecard, ordertype, "
					+ "approvalcode, creditcardnumber, cardtype, cashvalue, transactiondate, shippingamount, ordervalue ";

			BatchGLJobDAO batchGLJobDAO = new BatchGLJobDAO();

			conn = batchGLJobDAO.getConnection();

			st = conn.createStatement();
			rs = st.executeQuery(qry);

			while (rs.next()) {
				bcwtReportDTO = new BcwtReportDTO();

				if (rs.getString("ORDERID") != null) {
					bcwtReportDTO.setOrderRequestId(rs.getString("ORDERID"));

					if (rs.getString("PRODUCTS") != null) {
						bcwtReportDTO.setProducts(rs.getString("PRODUCTS"));
					}
				}

				String customerName = null;
				if (rs.getString("FIRSTNAME") != null) {
					customerName = rs.getString("FIRSTNAME");
				}
				if (rs.getString("LASTNAME") != null) {
					customerName = customerName + " "
							+ rs.getString("LASTNAME");
				}

				if (!Util.isBlankOrNull(customerName)) {
					bcwtReportDTO.setCustomerName(customerName);
				}

				if (rs.getString("ORDERTYPE") != null) {
					bcwtReportDTO.setOrderModule(rs.getString("ORDERTYPE"));
				}
				if (rs.getString("CARDTYPE") != null) {
					bcwtReportDTO.setCreditCardType(rs.getString("CARDTYPE"));
				}

				double orderValue = 00.00;
				double shippingAmount = 00.00;
				double total = 00.00;

				if (rs.getString("ORDERVALUE") != null) {
					orderValue = Double.parseDouble(rs.getString("ORDERVALUE"));
				}
				if (rs.getString("SHIPPINGAMOUNT") != null) {
					shippingAmount = Double.parseDouble(rs
							.getString("SHIPPINGAMOUNT"));
				}

				total = orderValue;
				bcwtReportDTO.setAmount(numberFormat.format(total));

				if (rs.getString("CREDITCARDNUMBER") != null) {
					String securityKey = ConfigurationCache
							.getConfigurationValues(
									Constants.CREDIT_CARD_SECURITY_KEY)
							.getParamvalue();
					// BcwtsLogger.info("CC: "+element[8].toString() );
					String last4Digits = "";
					try {
						String creditCardNo = Util
								.removeSpaceInBreezeCardSerialNo(SimpleProtector
										.decrypt(
												rs.getString("CREDITCARDNUMBER"),
												Base64EncodeDecodeUtil
														.decodeString(securityKey))
										.trim());

						last4Digits = creditCardNo.substring(
								(creditCardNo.length() - 4),
								creditCardNo.length());
					} catch (Exception e) {

						bcwtReportDTO.setCreditCardLast4("");
					}
					bcwtReportDTO.setCreditCardLast4(last4Digits);
				}
				if (rs.getString("TRANSACTIONDATE") != null) {
					bcwtReportDTO.setTransactionDate(rs
							.getString("TRANSACTIONDATE"));
				}
				if (rs.getString("APPROVALCODE") != null) {
					bcwtReportDTO.setApprovalCode(rs.getString("APPROVALCODE"));
				}

				if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
						Constants.ORDER_TYPE_IS)) {
					if (rs.getString("ADDRESSID") != null) {
						bcwtReportDTO.setOrderType("New");
					} else {
						bcwtReportDTO.setOrderType("Reload");
					}
				} else if (bcwtReportSearchDTO.getOrderType().equalsIgnoreCase(
						Constants.ORDER_TYPE_GBS)) {
					bcwtReportDTO.setOrderType("N/A");
				}

				reportDTOList.add(bcwtReportDTO);
			}
		} catch (Exception e) {
			BcwtsLogger.error("Error" + Util.getFormattedStackTrace(e));
		} finally {
			rs.close();
			st.close();
			conn.close();
		}

		return reportDTOList;
	}

}
