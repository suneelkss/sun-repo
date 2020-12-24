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
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.PartnerMonthlyUsageReportDTO;
import com.marta.admin.forms.PartnerMonthlyUsageReportForm;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Util;

public class PartnerMonthlyUsageReportDAO extends MartaBaseDAO{

	final String ME = "PartnerMonthlyUsageReportDAO: ";

	/**
	 * Method to get partner monthly usage summary report
	 * 
	 * @param date
	 * @param partnerMonthlyUsageReportForm
	 * @return
	 * @throws Exception
	 */
	public List getPartnerMonthlyUsageSummaryReport(String date, String todate,
					PartnerMonthlyUsageReportForm partnerMonthlyUsageReportForm) throws Exception {
		
 		final String MY_NAME = ME + "getPartnerMonthlyUsageSummaryReport: "+date+" to"+todate;
 		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		PartnerMonthlyUsageReportDTO partnerMonthlyUsageReportDTO = null;
		boolean flag = false;
		List benefitList = new ArrayList();
		List collectionList = new ArrayList();
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String queryForGetNextfareId = null;
			BcwtsLogger.debug(MY_NAME+"For first Name:"+partnerMonthlyUsageReportForm.getPartnerFirstName()+" and last name"+partnerMonthlyUsageReportForm.getPartnerLastName());
			if(null != partnerMonthlyUsageReportForm){
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getReportFormat())){
					flag = true;
					queryForGetNextfareId = "select " +
												"partnerComp.nextfareCompanyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
											"and " +
												"partnerComp.companyId = tmaComp.id.companyId";
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getTmaName()) && flag == true){
					queryForGetNextfareId = queryForGetNextfareId +" and lower(tmaInfo.tmaName) like '%"+
					partnerMonthlyUsageReportForm.getTmaName().trim().toLowerCase()+"%'";
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerFirstName()) && flag == true){
					queryForGetNextfareId = "";
					queryForGetNextfareId = "select " +
												"partnerCompInfo.nextfareCompanyId " +
											"from " +
												"PartnerAdminDetails partnerAdminDetails, " +
												"PartnerCompanyInfo partnerCompInfo " +
											"where " +
												"lower(partnerAdminDetails.firstName) like '%" +
													partnerMonthlyUsageReportForm.getPartnerFirstName()
																			     .trim().toLowerCase()+"%' " +
											"and " +
											    "partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerLastName()) && flag == true){
					queryForGetNextfareId = "";
					queryForGetNextfareId = "select " +
												"partnerCompInfo.nextfareCompanyId " +
											"from " +
												"PartnerAdminDetails partnerAdminDetails, " +
												"PartnerCompanyInfo partnerCompInfo " +
											"where " +
												"lower(partnerAdminDetails.lastName) like '%" +
													partnerMonthlyUsageReportForm.getPartnerLastName()
																				 .trim().toLowerCase()+"%' " +
											"and " +
												"partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerLastName()) &&
						!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerFirstName()) && flag == true){
					queryForGetNextfareId = "";
					queryForGetNextfareId = "select " +
												"partnerCompInfo.nextfareCompanyId " +
											"from " +
												"PartnerAdminDetails partnerAdminDetails, " +
												"PartnerCompanyInfo partnerCompInfo " +
											"where " +
												"lower(partnerAdminDetails.lastName) like '%" +
													partnerMonthlyUsageReportForm.getPartnerLastName()
																				 .trim().toLowerCase()+"%' " +
											"and " +
												"lower(partnerAdminDetails.firstName) like '%"+
													partnerMonthlyUsageReportForm.getPartnerFirstName()
																				 .trim().toLowerCase()+"%' " +
											"and " +
												"partnerAdminDetails.partnerCompanyInfo.companyId = partnerCompInfo.companyId";
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getTmaName()) && flag == true
						&& !Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerFirstName())){
					queryForGetNextfareId = null;
					queryForGetNextfareId ="select " +
												"distinct partnerComp.nextfareCompanyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp," +
												"PartnerAdminDetails partnerAdminDetails " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
											"and " +
												"partnerComp.companyId = tmaComp.id.companyId "+
											"and " +
												"partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "+
											"and " +
												"lower(tmaInfo.tmaName) like '%"+
													partnerMonthlyUsageReportForm.getTmaName()
																				 .trim().toLowerCase()+"%'"+
											"and " +
												"lower(partnerAdminDetails.firstName) like '%"+
													partnerMonthlyUsageReportForm.getPartnerFirstName()
																				 .trim().toLowerCase()+"%'" ;					
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getTmaName()) && flag == true
						&& !Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerLastName())){
					queryForGetNextfareId = null;
					queryForGetNextfareId ="select " +
												"distinct partnerComp.nextfareCompanyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp," +
												"PartnerAdminDetails partnerAdminDetails " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
											"and " +
												"partnerComp.companyId = tmaComp.id.companyId "+
											"and " +
												"partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "+
											"and " +
												"lower(tmaInfo.tmaName) like '%"+
													partnerMonthlyUsageReportForm.getTmaName()
																				 .trim().toLowerCase()+"%'"+
											"and " +
												"lower(partnerAdminDetails.lastName) like '%"+
													partnerMonthlyUsageReportForm.getPartnerLastName()
																				 .trim().toLowerCase()+"%'";
				}
				if(!Util.isBlankOrNull(partnerMonthlyUsageReportForm.getTmaName()) && flag == true
						&& !Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerLastName())
						&& !Util.isBlankOrNull(partnerMonthlyUsageReportForm.getPartnerFirstName())){
						
					queryForGetNextfareId = null;
					queryForGetNextfareId ="select " +
												"distinct partnerComp.nextfareCompanyId " +
											"from " +
												"TmaInformation tmaInfo, " +
												"TmaCompanyLink tmaComp, " +
												"PartnerCompanyInfo partnerComp," +
												"PartnerAdminDetails partnerAdminDetails " +
											"where " +
												"tmaInfo.tmaId = tmaComp.id.tmaId " +
											"and " +
												"partnerComp.companyId = tmaComp.id.companyId "+
											"and " +
												"partnerAdminDetails.partnerCompanyInfo.companyId = partnerComp.companyId "+
											"and " +
												"lower(tmaInfo.tmaName) like '%"+
													partnerMonthlyUsageReportForm.getTmaName()
																				 .trim().toLowerCase()+"%'"+
											"and " +
												"lower(partnerAdminDetails.lastName) like '%"+
												partnerMonthlyUsageReportForm.getPartnerLastName()
																			 .trim().toLowerCase()+"%'"+
											"and " +
												"lower(partnerAdminDetails.firstName) like '%"+
												partnerMonthlyUsageReportForm.getPartnerFirstName()
																			 .trim().toLowerCase()+"%'" ;					
				}
				List nextfareCompanyIdList1 = session.createQuery(queryForGetNextfareId).list();
				if(nextfareCompanyIdList1 !=null && !nextfareCompanyIdList1.isEmpty()){
					for(Iterator itr = nextfareCompanyIdList1.iterator();itr.hasNext();){
						partnerMonthlyUsageReportDTO = new PartnerMonthlyUsageReportDTO();
						String nextFareCompanyId = (String) itr.next();
						BcwtsLogger.debug("Monthly Usage Report for company "+nextFareCompanyId);			
						if(nextFareCompanyId != null && !Util.isBlankOrNull(nextFareCompanyId)){
										 benefitList = getMonthlyUsageSummaryReportFromNextfare(nextFareCompanyId, date,todate);
										 collectionList.addAll(benefitList);										 
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
	 * Method to get partner monthly usage summary report from next fare
	 * 
	 * @param nextFareCompanyId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List getMonthlyUsageSummaryReportFromNextfare(String nextFareCompanyId, String date, String todate) throws Exception{
		
		final String MY_NAME = ME + "getMonthlyUsageList:";
		BcwtsLogger.debug(MY_NAME);
		List activeBenefitsList = new ArrayList();
		PartnerMonthlyUsageReportDTO partnerMonthlyUsageReportDTO = null;
		Connection con = null;
		int benefit_Id=0;
		String benefitType = null;
	//	String firstDate = null;
		String mont = null;
		String dd  = null;
		String yr = null;
		String monthUsage = null;
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    Date today = df.parse(date);
	    DateFormat dfts = new SimpleDateFormat("dd/M/yyyy");
	 //   StringTokenizer st = new StringTokenizer(dfts.format(today),"//");
	    ResultSet rs = null;
		ResultSet rs1 = null;
		ArrayList allList = new ArrayList();
		ArrayList activeList  = new ArrayList();
	  /*  while(st.hasMoreTokens()){
	    	 dd =st.nextToken();
	    	 mont =st.nextToken();
	    	 yr=st.nextToken();
	    	firstDate =mont+"/"+01+"/"+yr;
	    }*/
	    	Date ftdate = df.parse(todate);
		try {
			con = NextFareConnection.getConnection();
			Statement stmt = con.createStatement();
			Statement stmtAll = con.createStatement();
		/*	String query = "select " +
								"distinct(member.SERIAL_NBR), " +
								"member.EFFECTIVE_DTM, member.BENEFIT_ID " +
       					   "from " +
								"nextfare_main.Member_Benefit_History member " +
			               "where " +
			               		"member.CUSTOMER_ID = "
			               		+Integer.valueOf(nextFareCompanyId)+" and " +
			               		"member.EFFECTIVE_DTM > to_date('"+dfts.format(ftdate)+"','DD-MM-YYYY') and " +
			               		"EFFECTIVE_DTM < last_day(to_date('"+dfts.format(today)+"','DD-MM-YYYY'))";

			ResultSet rs = stmt.executeQuery(query);
			if(rs != null){
				while(rs.next()){
					benefit_Id = rs.getInt(3);

							String Qery ="select " +
											"customerBenefitDefi.BENEFIT_NAME, " +
											"customerBenefitDefi.BENEFIT_FREQUENCY_TYPE_ID" +
										 " from " +
											"nextfare_main.Customer_Benefit_Definition customerBenefitDefi " +
										 "where " +
											"customerBenefitDefi.BENEFIT_ID = "+benefit_Id+" " +
										 "and " +
											"customerBenefitDefi.CUSTOMER_ID ="+Integer.valueOf(nextFareCompanyId);
							Statement sts = con.createStatement();
							ResultSet rt = sts.executeQuery(Qery);
							if(rt !=null)
							{
								while(rt.next()){
									benefitType = rt.getString(1)+rt.getString(2);
								}
								String Qry =" select " +
												"extract(Month from Inserted_Dtm)," +
												"extract(Year from Inserted_Dtm) "+
								            " from " +
								            	"nextfare_main.Member_Benefit_History "+
								            " where " +
								            	"Serial_Nbr ='"+rs.getString(1)+"' " +
								         	"and " +
								           		"Customer_Id ="+Integer.valueOf(nextFareCompanyId);
								Statement stst = con.createStatement();
								ResultSet rts = sts.executeQuery(Qry);
								if(rts !=null)
								{
								while(rts.next()){
									if(mont.equals(rts.getString(1)) && yr.equals(rts.getString(2))){
										monthUsage = "YES";
										break;
									}else{
										monthUsage = "NO";
									}
								}
								}
					            DateFormat dft = new SimpleDateFormat("MMM yy");
					            partnerMonthlyUsageReportDTO = new PartnerMonthlyUsageReportDTO();
					            partnerMonthlyUsageReportDTO.setBreezeCardSerialNumber(rs.getString(1));
					            partnerMonthlyUsageReportDTO.setBenifitName(benefitType);
					            partnerMonthlyUsageReportDTO.setBillingMonth(dft.format(today));
					            partnerMonthlyUsageReportDTO.setMonthlyUsage(monthUsage);
					            
					            activeBenefitsList.add(partnerMonthlyUsageReportDTO);
							}
							rt.close();
					}
			}else{
				rs.close();
			}
			rs.close();*/
			
			String queryActive =   "select MEMBER.SERIAL_NBR from nextfare_main.member member where  MEMBER.CUSTOMER_ID = "+Integer.valueOf(nextFareCompanyId)+" and MEMBER.SERIAL_NBR in "
	          + " (select  USE_TRANSACTION.SERIAL_NBR  from nextfare_main.use_transaction use_transaction" +
	          		" where  USE_TRANSACTION.TRANSACTION_DTM > to_date('"+dfts.format(today)+"','DD-MM-YYYY') and "+
	           " USE_TRANSACTION.TRANSACTION_DTM < last_day(to_date('"+dfts.format(ftdate)+"','DD-MM-YYYY'))) ";

			String queryAll =   "select member.serial_nbr, cc.org_name from nextfare_main.member member, nextfare_main.customer cc where member.benefit_status_id !='3' and cc.customer_id = member.customer_id and MEMBER.CUSTOMER_ID = "+Integer.valueOf(nextFareCompanyId);


			 rs = stmt.executeQuery(queryActive);

			 rs1 = stmtAll.executeQuery(queryAll);

			System.out.println("fff"+queryActive);

			if(rs != null){


				while(rs.next()){
				 	activeList.add(rs.getString(1));
					 }


			       }
			rs.close();
		//	System.out.println("size: "+activeList.size());
			if(activeList.size() > 0) {
			if(rs1 != null){

				while(rs1.next()){
					partnerMonthlyUsageReportDTO = new PartnerMonthlyUsageReportDTO();
					//System.out.println(rs1.getString(1));
				//	String temp = rs1.getString(1);
					for(Iterator iter = activeList.iterator(); iter.hasNext();){

						/*
						if(!iter.next().equals(rs1.getString(1))){

							 partnerReportSearchDTO.setMonthlyUsage("Yes");
						}
						else{
							partnerReportSearchDTO.setMonthlyUsage("No");
						}

						    DateFormat dft = new SimpleDateFormat("MMM yy");
							partnerReportSearchDTO.setCardSerialNo(rs1.getString(1));
							partnerReportSearchDTO.setBenefitType("Annual Pass");
							partnerReportSearchDTO.setBillingMonth(dft.format(today));

						*/

								String temp = (String) iter.next();
										if (temp.equals(rs1.getString(1))) {
											partnerMonthlyUsageReportDTO.setMonthlyUsage("Yes");
											DateFormat dft = new SimpleDateFormat("MMM yy");
											partnerMonthlyUsageReportDTO.setBreezeCardSerialNumber(rs1
													.getString(1));
										//	partnerMonthlyUsageReportDTO
											//		.setBenifitName("Annual Pass");
											partnerMonthlyUsageReportDTO.setBillingMonth(dft
													.format(today));
											partnerMonthlyUsageReportDTO.setCompanyName(rs1.getString(2));
											break;
										}
										else {
											partnerMonthlyUsageReportDTO.setMonthlyUsage("No");
											DateFormat dft = new SimpleDateFormat("MMM yy");
											partnerMonthlyUsageReportDTO.setBreezeCardSerialNumber(rs1
													.getString(1));
									//		partnerMonthlyUsageReportDTO
										//			.setBenifitName("Annual Pass");
											partnerMonthlyUsageReportDTO.setBillingMonth(dft
													.format(today));
											partnerMonthlyUsageReportDTO.setCompanyName(rs1.getString(2));
										}

					  			}

						 activeBenefitsList.add(partnerMonthlyUsageReportDTO);
					 }
			       }
			}
			rs1.close();
			
			
		}catch(Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return activeBenefitsList;
	}
}