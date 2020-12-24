package com.marta.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.marta.admin.dto.AdminBatchProcessListDTO;
import com.marta.admin.dto.BatchProcessPartnerListDTO;
import com.marta.admin.forms.AdminBatchProcessForm;
import com.marta.admin.hibernate.BcwtHotListCardDetails;
import com.marta.admin.hibernate.BcwtPartnerNewCard;
import com.marta.admin.hibernate.BcwtProduct;
import com.marta.admin.hibernate.PartnerAdminDetails;
import com.marta.admin.hibernate.PartnerBatchDetails;
import com.marta.admin.hibernate.PartnerBatchDetailsHistory;
import com.marta.admin.hibernate.PartnerBatchJob;
import com.marta.admin.hibernate.PartnerBatchStatuses;
import com.marta.admin.hibernate.PartnerCompanyInfo;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.MartaQueries;
import com.marta.admin.utils.Util;


public class AdminPartnerBatchProcessDAO extends MartaBaseDAO {
	final String ME = "AdminPartnerBatchProcessDAO: ";
	DecimalFormat numberFormat = new DecimalFormat("0.00");

	/**
	 * To update product price 
	 * @throws Exception
	 */
	public boolean updateProductPrice() throws Exception {
		final String MY_NAME = ME + "updateProductPrice: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;		
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumIntegerDigits(1);
		boolean isUpdated = false;
		
		try {
			
			List bcwtProductList = getBcwtProductList();
			
			Map fareInsIdsWithPrice = getPriceFromNextFareDB();

			session = getSession();
			transaction = session.beginTransaction();
			
			if(bcwtProductList != null && !bcwtProductList.isEmpty() &&
					fareInsIdsWithPrice != null && !fareInsIdsWithPrice.isEmpty()) {
				
				for (Iterator iter = bcwtProductList.iterator(); iter.hasNext();) {
					BcwtProduct bcwtProduct = (BcwtProduct) iter.next();
					try {
						if(bcwtProduct.getFare_instrument_id() != null && 
								fareInsIdsWithPrice.get("key-" + bcwtProduct.getFare_instrument_id()) != null){
							
							Double price = Double.valueOf((
									fareInsIdsWithPrice.get("key-" + bcwtProduct.getFare_instrument_id()).toString()));
							bcwtProduct.setPrice(price);
							session.update(bcwtProduct);
							session.flush();
						}
					}catch(Exception e) {
						BcwtsLogger.error(MY_NAME + "Exception while updating product price, productId=" 
								+ bcwtProduct.getProductid());
					}
				}
			}
			
			transaction.commit();
			isUpdated = true;
			BcwtsLogger.info(MY_NAME + "Product Price Updated Successfully");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}	
		
		return isUpdated;
	}
	
	/**
	 * To get price from Next Fare DB
	 * @return fareInsIdsWithPrice
	 * @throws Exception
	 */
	private Map getPriceFromNextFareDB() throws Exception {
		final String MY_NAME = ME + "getPriceFromNextFareDB: ";
		BcwtsLogger.debug(MY_NAME);		
		Map fareInsIdsWithPrice = new HashMap();
		Connection nextFareDBCon = null;
		
		try {			
			nextFareDBCon = NextFareConnection.getConnection();		
			
			if(nextFareDBCon == null) {
				throw new Exception(MY_NAME + "nextFareDBCon is null");
			}
			
			String query = " select" +
						   " fare_instrument_id, price/100 price" + 
						   " from" +
            			   " nextfare_main.prod_catalog_fare_inst where price <> 0";


			/*String query = " select" +
								" fare_instrument_id, price" +
						   " from" +
								" nextfare_main.prod_catalog_fare_inst";*/
			
			PreparedStatement preparedStatement = nextFareDBCon.prepareStatement(query);

			ResultSet rs = preparedStatement.executeQuery();				
			while(rs.next()){
				fareInsIdsWithPrice.put("key-" + rs.getString("fare_instrument_id"), rs.getString("price"));
			}
			BcwtsLogger.info(MY_NAME + "Got fareInsIdsWithPrice map");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			NextFareConnection.closeConnection(nextFareDBCon);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return fareInsIdsWithPrice;
	}
	
	/**
	 * To get BcwtProduct List
	 * @return bcwtProductList
	 * @throws Exception
	 */
	private List getBcwtProductList() throws Exception {
		final String MY_NAME = ME + "getBcwtProductList: ";
		BcwtsLogger.debug(MY_NAME);
		List bcwtProductList = null;
		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtProduct bcwtProduct";
			bcwtProductList =  session.createQuery(query).list();
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Got BcwtProduct List");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return bcwtProductList;		
	}
	
	/**
	 * This will save batch process
	 * 
	 * @param partnerBatchJob
	 * @return Long
	 * @throws Exception
	 */
	public Long addBatchProcess(PartnerBatchJob partnerBatchJob)
			throws Exception {
		final String MY_NAME = ME + "addBatchProcess: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		Long batchJobID = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			batchJobID = (Long) session.save(partnerBatchJob);
			transaction.commit();
			// session.clear();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Batch Process saved to database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return batchJobID;
	}

	/**
	 * This will update batch process
	 * 
	 * @param partnerBatchJob
	 * @return Long
	 * @throws Exception
	 */
	public void updateBatchProcess(PartnerBatchJob partnerBatchJob)
			throws Exception {
		final String MY_NAME = ME + "updateBatchProcess: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.update(partnerBatchJob);
			transaction.commit();
			// session.clear();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Batch Process update to database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
	}
	
	public List getBatchJobsForPartnerId(Long partnerid) {
		List partnerBatchJobsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List batchJobsList = new ArrayList();
		BcwtsLogger.info("Fetching batchJObs for company:"+partnerid);
		ListCardsDAO listCardsDao = new ListCardsDAO();
		PartnerCompanyInfo partnerCompanyInfo = new PartnerCompanyInfo();
		
		
		try {
		//	partnerCompanyInfo = listCardsDao.getNextFareCustomerId(partnerid.toString());
	/*	String comId =	partnerCompanyInfo.getNextfareCompanyId();
		System.out.println("ComID"+comId);*/
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerBatchJob batchjob where batchjob.partnerId ="
					+ partnerid + " order by batchjob.batchJobid desc";
			partnerBatchJobsList =  session.createQuery(query).list();
			BcwtsLogger.debug("Size" +partnerBatchJobsList.size());
			for (Iterator iter = partnerBatchJobsList.iterator(); iter.hasNext();) {
				PartnerBatchJob partnerBatchJob = (PartnerBatchJob) iter.next();
				String logFile = partnerBatchJob.getLogfileLocation();
				if(null != logFile){
				if(logFile.endsWith("xml")){
					try{
						System.out.println("try:+"+logFile);
					String fileName = partnerBatchJob.getXlsFilename();
					String words[] = fileName.split("/");
	                partnerBatchJob.setFileName(words[3]);
					}
					catch(Exception e){
						BcwtsLogger.error(Util.getFormattedStackTrace(e));
					}	
					}
				batchJobsList.add(new AdminBatchProcessListDTO(partnerBatchJob));
			}
				
			}
		} catch (Exception ex) {
			BcwtsLogger.error("Unable to fetch BatchJobs: "+Util.getFormattedStackTrace(ex));
		} finally {
			closeSession(session, transaction);
		}
		BcwtsLogger.info("Fetched Batch Job List size:"+batchJobsList.size());
		return batchJobsList;
	}
	
	/**
	 * New Method Added for searching Batch Processing with from and to dates parameter and first & last names.
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public List getSearchBatchJobs(AdminBatchProcessForm form,Long partnerId) throws Exception {
		final String MY_NAME = ME + "getSearchBatchJobs: ";
		BcwtsLogger.debug(MY_NAME);
		List partnerBatchJobSearchList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List batchJobsList = new ArrayList();
		
		try {
			session = getSession();
			transaction = session.beginTransaction();
			Criteria query=session.createCriteria(PartnerAdminDetails.class).setProjection(Projections.property("partnerId"));
			if(!Util.isBlankOrNull(form.getFirstName())){
				query.add(Restrictions.like("firstName",form.getFirstName(),MatchMode.ANYWHERE).ignoreCase());
			}
			if(!Util.isBlankOrNull(form.getLastPartnerName())){
				query.add(Restrictions.like("lastName",form.getLastPartnerName(),MatchMode.ANYWHERE).ignoreCase());
			}
			if(!Util.isBlankOrNull(partnerId.toString())){
				query.add(Restrictions.like("partnerId",partnerId));
			}
			
			if(null != query.list() && !query.list().isEmpty()){
				Criteria query1 =session.createCriteria(PartnerBatchJob.class).add(Restrictions.in("partnerId", query.list().toArray()));
				try{
				if(!Util.isBlankOrNull(form.getFromDate()) || !Util.isBlankOrNull(form.getToDate())){
					 SimpleDateFormat formatter ; 
				     Date date = new Date() ; 
				     Date date1 = new Date();
				          formatter = new SimpleDateFormat("M/d/yyyy");
				             if(form.getFromDate() != null && !Util.isBlankOrNull(form.getFromDate())){
				              date = (Date)formatter.parse(form.getFromDate());
				             }
				             if(form.getToDate() != null && !Util.isBlankOrNull(form.getToDate())){
				              date1= (Date)formatter.parse(form.getToDate()); 
				             }
				             if(!Util.isBlankOrNull(form.getFromDate()) 
				            		  && Util.isBlankOrNull(form.getToDate())) {
				            	  query1.add(Restrictions.ge("uploadTime",date));
				              }
				              if(!Util.isBlankOrNull(form.getToDate()) 
				            		  && Util.isBlankOrNull(form.getFromDate())) {
				            	  query1.add(Restrictions.le("uploadTime", date1));
				            	}
				              if(!Util.isBlankOrNull(form.getFromDate()) 
				            		  && !Util.isBlankOrNull(form.getToDate())){
                            	     query1.add(Restrictions.ge("uploadTime",date)).add(Restrictions.le("uploadTime", (Date)formatter.parse(Util.getNextDay(form.getToDate()))));
                              }
				}
				
				batchJobsList=query1.list();
				
				for (Iterator iter = batchJobsList.iterator(); iter.hasNext();) {
					PartnerBatchJob partnerBatchJob = (PartnerBatchJob) iter.next();
					partnerBatchJobSearchList.add(new AdminBatchProcessListDTO(partnerBatchJob));
				}
			}
				catch(Exception e){
					BcwtsLogger.error(MY_NAME + e.getMessage());
					throw e;
				}
			}
			
		} 
		catch(HibernateException ex){
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		
		return partnerBatchJobSearchList;
	}

	/**
	 * This method returns the list of jobs for the 
	 * start date which is the same as the current system date
	 * 
	 * @return Returns list of partnerBatchJob
	 * @throws Exception
	 */
	public List getAllBatchJobsForTheDay() throws Exception {
		List allJobsList = getAllBatchJobs();
		List filteredList = new ArrayList();
		Date currentSystemDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String currSystemDateAsString = sdf.format(currentSystemDate);
		PartnerBatchJob partnerBatchJob;
		Date currentStartDateValue;
		String currStartDateAsString;
		
		for (Iterator iter = allJobsList.iterator(); iter.hasNext();) {
			partnerBatchJob = (PartnerBatchJob) iter.next();
			currentStartDateValue = partnerBatchJob.getProcessStarttime();
			
			if (currentStartDateValue!=null) {
				currStartDateAsString = sdf.format(currentStartDateValue);
				if (currStartDateAsString.equals(currSystemDateAsString)) {
					if(partnerBatchJob.getPartnerBatchstatuses().
							getStatusId().toString().equals(Constants.BATCH_PROCESS_INITIAL_STATUS_ID.toString())){
						filteredList.add(partnerBatchJob);
					}
				}
			}
		}
		
		return filteredList;
	}
	
	/**
	 * This method returns the list of jobs for the start date 
	 * which is the same month as that of the current system date
	 * 
	 * @return Returns list of partnerBatchJob
	 * @throws Exception
	 */
	public List getAllBatchJobsForTheMonth() throws Exception {
		List allJobsList = getAllBatchJobs();
		List filteredList = new ArrayList();
		Date currentSystemDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentSystemDate);
		int currentMonth = calendar.get(Calendar.MONTH);
		int currentYear = calendar.get(Calendar.YEAR);
		PartnerBatchJob partnerBatchJob;
		Date currentStartDateValue;
		int currentStartDateMonth;
		int currentStartDateYear;
		
		for (Iterator iter = allJobsList.iterator(); iter.hasNext();) {
			partnerBatchJob = (PartnerBatchJob) iter.next();
			currentStartDateValue = partnerBatchJob.getProcessStarttime();
			calendar.setTime(currentStartDateValue);
			currentStartDateMonth = calendar.get(Calendar.MONTH);
			currentStartDateYear = calendar.get(Calendar.YEAR);
			
			if (currentMonth == currentStartDateMonth && currentYear == currentStartDateYear) {
				filteredList.add(partnerBatchJob);
			}
		}
		
		return filteredList;
	}
	
	private List getAllBatchJobs() throws Exception {
		final String MY_NAME = ME + "getBatchJobsForTheDay: ";
		List batchJobsList = new ArrayList();
		String query = "from PartnerBatchJob";

		Session session = null;
		Transaction transaction = null;

		try {
			session = getSession();
			transaction = session.beginTransaction();
			batchJobsList = (List) session.createQuery(query).list();
		} catch(HibernateException ex){
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
		}
		
		return batchJobsList;
	}
	
	/**
	 * This will save batch process history
	 * 
	 * @param partnerBatchDetailsHistory
	 * @return Long
	 * @throws Exception
	 */
	public void addPartnerBatchDetailsHistory(PartnerBatchDetailsHistory partnerBatchDetailsHistory)
			throws Exception {
		final String MY_NAME = ME + "addPartnerBatchDetailsHistory: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(partnerBatchDetailsHistory);
			transaction.commit();
			// session.clear();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Batch Process Details History saved to database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
	}	


	/**
	 * This will save batch details
	 * 
	 * @param partnerBatchDetails
	 * @return Long
	 * @throws Exception
	 */
	public void addPartnerBatchDetails(PartnerBatchDetails partnerBatchDetails)
			throws Exception {
		final String MY_NAME = ME + "addPartnerBatchDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(partnerBatchDetails);
			transaction.commit();
			// session.clear();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Batch Process Details saved to database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
	}
	
	/**
	 * This method returns the list of all
	 * batch details
	 * 
	 * @return Returns list of PartnerBatchDetails
	 * @throws Exception
	 */
	public List getAllBatchDetails() throws Exception {
		List partnerBatchDetailsList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerBatchDetails batchDetails ";
			partnerBatchDetailsList =  session.createQuery(query).list();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
		}
		
		return partnerBatchDetailsList;
	}
	
	public boolean deleteAllBatchDetails() throws Exception {
		final String MY_NAME = ME + " deleteAllBatchDetails: ";
		BcwtsLogger.debug(MY_NAME + " deleting batch details ");
		boolean isUpdate = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			
			Query deleteQuery = session.createQuery("delete from PartnerBatchDetails");
			deleteQuery.executeUpdate();
			
			transaction.commit();
			session.flush();
			
			BcwtsLogger.info(MY_NAME + "Batch Details is Deleted from database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in deleting batch details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdate;
	}
	
	/*
	 * Method to update status in batchjob
	 */
	public boolean updateStatusBatchJob(Long batchJobId ) {
		
		Session session = null;
		Transaction transaction = null;
		List partnerBatchJobsList = new ArrayList();
		boolean isStatus = false;
		PartnerBatchJob partnerBatchJob = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerBatchJob batchjob where batchjob.batchJobid ="
					+ batchJobId;
			partnerBatchJobsList =  session.createQuery(query).list();
			
			for (Iterator iter = partnerBatchJobsList.iterator(); iter.hasNext();) {
				partnerBatchJob = (PartnerBatchJob) iter.next();
				PartnerBatchStatuses partnerBatchStatuses = new PartnerBatchStatuses();
				partnerBatchStatuses.setStatusId(Constants.BATCH_PROCESS_PROCESSSTARTED_STATUS_ID);
				partnerBatchJob.setPartnerBatchstatuses(partnerBatchStatuses);
			}
			session.update(partnerBatchJob);
			transaction.commit();
			isStatus = true;
			// session.clear();
			session.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
		}
		
		return isStatus;
	}
	
	/*
	 * Method to get emailid for the admin
	 */
	public String getEmailIdForPSAdmin(Long batchJobId ) {
		
		Session session = null;
		Transaction transaction = null;
		List partnerBatchJobsList = new ArrayList();
		String emailId = "";
		PartnerBatchJob partnerBatchJob = null;
		Long partnerId =new Long(0);
		PartnerAdminDetails partnerAdminDetails = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from PartnerBatchJob batchjob where batchjob.batchJobid ="
					+ batchJobId;
			partnerBatchJobsList =  session.createQuery(query).list();
			
			for (Iterator iter = partnerBatchJobsList.iterator(); iter.hasNext();) {
				partnerBatchJob = (PartnerBatchJob) iter.next();
				partnerId = partnerBatchJob.getPartnerId();
				String qerySt = "from PartnerAdminDetails partnerAdminDetails where " +
						        " partnerAdminDetails.partnerId ="+ partnerId;
				partnerAdminDetails = (PartnerAdminDetails) session.createQuery(qerySt).uniqueResult();
				if(partnerAdminDetails != null){
					emailId = partnerAdminDetails.getEmail();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
		}
		
		return emailId;
	}
	
	// to Dlete the Log file
	public boolean deleteLogFile(Long batchId) throws Exception {
		final String MY_NAME = ME + " deleteLogFile: ";
		BcwtsLogger.debug(MY_NAME + " deleting Log File ");
		boolean isUpdate = false;
		Session session = null;
		Transaction transaction = null;
		PartnerBatchJob partnerBatchJob = null; 
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String qry =" from PartnerBatchJob partnerBatchJob where "+
			            "partnerBatchJob.batchJobid = "+batchId;
			partnerBatchJob = (PartnerBatchJob) session.createQuery(qry).uniqueResult();
			session.delete(partnerBatchJob);
			transaction.commit();
			isUpdate = true;
			session.flush();
			BcwtsLogger.info(MY_NAME + "Log File is Deleted from database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME + " Exception in deleting log file  :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return isUpdate;
	}
	
	//to get the existings Breeze Card
    public BcwtHotListCardDetails getDetailsForHotlist(String breezeCard) {
		
		Session session = null;
		Transaction transaction = null;
		BcwtHotListCardDetails bcwtHotListCardDetails = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "from BcwtHotListCardDetails bcwtHotListCardDetails where "+
			               "bcwtHotListCardDetails.serialnumber = '"+ breezeCard+"'";
			if(session.createQuery(query).list().size() != 0 ) {
				bcwtHotListCardDetails = (BcwtHotListCardDetails)session.createQuery(query).list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
		}
		
		return bcwtHotListCardDetails;
	}
    
    //to update DB  for hotlist
    public boolean saveBreezeCardDetails(BcwtHotListCardDetails bcwtHotListCardDetails)	throws Exception {
		
    	final String MY_NAME = ME + "saveBreezeCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean breezeCardResult = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(bcwtHotListCardDetails);
			transaction.commit();
			breezeCardResult = true;
			session.flush();
			BcwtsLogger.info(MY_NAME + "BreezeCard Details are saved in Database ");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return breezeCardResult;
	}
    
    //get the company name
    public String  getCompanyID(String companyName) {
		
		Session session = null;
		Transaction transaction = null;
		BcwtHotListCardDetails bcwtHotListCardDetails = null;
		String companyId = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select partnerCompanyInfo.companyId from PartnerCompanyInfo partnerCompanyInfo where "+
			               "partnerCompanyInfo.companyName = '"+ companyName.trim()+"'";
			companyId = session.createQuery(query).uniqueResult().toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
		}
		
		return companyId;
	}
    //get the company list
    public List  getCompanyList(String partnerId) {
		
		Session session = null;
		Transaction transaction = null;
		BcwtHotListCardDetails bcwtHotListCardDetails = null;
		List companyList = null;
		List dataList = null;
		try {
			companyList = new ArrayList();
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select pc.companyId,pc.companyName from PartnerAdminDetails ad,"+
			               "PartnerCompanyInfo pc where ad.partnerId = "+partnerId+
			               " and ad.partnerCompanyInfo.companyId = pc.companyId ";
			companyList = session.createQuery(query).list();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
		}
		
		return companyList;
	}
    //to save new card monthly
    public boolean saveNewCardDetails(BcwtPartnerNewCard bcwtPartnerNewCard)	throws Exception {
		
    	final String MY_NAME = ME + "saveNewCardDetails: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		boolean newCardResult = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			session.save(bcwtPartnerNewCard);
			transaction.commit();
			newCardResult = true;
			session.flush();
			BcwtsLogger.info(MY_NAME + "New Card Details are saved in Database");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return newCardResult;
	}
    //to check breezecard in fare_media_inventory
    public boolean checkBreezeCard(String breezecard)	throws Exception {
		
    	final String MY_NAME = ME + "checkBreezeCard: ";
		BcwtsLogger.debug(MY_NAME);
		Session session = null;
		Transaction transaction = null;
		Connection con = null;
		boolean validBreezeCard = false;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			con = NextFareConnection.getConnection();
			Statement stmt = con.createStatement();
			String query = "  select inventory.Serial_NBR  from nextfare_main.Fare_Media_Inventory inventory where inventory.Serial_NBR='"+breezecard+"'" ;
			boolean rs = stmt.execute(query);
			
			/*if(rs != null){
				while(rs.next()){
					if(breezecard.equalsIgnoreCase(rs.getString(1))){
						validBreezeCard = true;
						break;
					}else{
						validBreezeCard = false;
					}
				}
			}*/
			
			if(rs){
				validBreezeCard = true;
			}else{
				validBreezeCard = false;
			}
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "Breeze Card is checked in Fare_Media_Inventory table." );
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return validBreezeCard;
	}
    
    /**
     * method to get no.ofupload file 
     */
    public int getFileCount(Long partnerid,String date) {
    	final String MY_NAME = ME + "getFileCount: ";
		int count =0;
		Session session = null;
		Transaction transaction = null;
		List batchJobsList = new ArrayList();
		SimpleDateFormat formatter ; 
        Date date1 ;
	    formatter = new SimpleDateFormat("MM/dd/yyyy");
        
		try {
			date1 = (Date)formatter.parse(date);
			session = getSession();
			transaction = session.beginTransaction();
			String query = "select count(*) from PartnerBatchJob batchjob where batchjob.partnerId ="
					+ partnerid +" and trunc(batchjob.uploadTime) = to_date('"+date+"','mm/dd/yyyy')" ;
			batchJobsList =  session.createQuery(query).list();
			for (Iterator iter = batchJobsList.iterator(); iter.hasNext();) {
				Object element = (Object) iter.next();
				count = Integer.parseInt(element.toString());
			}
			
		} catch (Exception ex) {
			BcwtsLogger.error("Error in getting file count"+ex.getMessage());
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return count;
	}
	//to retrive Partner List
    public List getPartnerList(Long partnerid,AdminBatchProcessForm batchProcessForm) {
    	final String MY_NAME = ME + "getPartnerList: ";
		List partnerList = new ArrayList();
		Session session = null;
		Transaction transaction = null;
		List batchProcessPartnerList = new ArrayList();
		BatchProcessPartnerListDTO partnerListDTO = null;
		String query = null;
		try {
			session = getSession();
			transaction = session.beginTransaction();
			if((partnerid.compareTo(new Long(0))) > 0){
				query = MartaQueries.GET_BATCH_PROCESS_PARTNERLIST;
				query = query + " and partnerAdmin.partnerId ="+partnerid;
				
			}else{
				query = MartaQueries.GET_BATCH_PROCESS_PARTNERLIST;				
			}
			if(batchProcessForm.getPartnerFirstName() !=null &&
					!Util.isBlankOrNull(batchProcessForm.getPartnerFirstName())){
				query = query + " and lower(partnerAdmin.firstName) like '"+batchProcessForm.getPartnerFirstName().toLowerCase().trim()+"%'";
			}
			if(batchProcessForm.getPartnerLastName() !=null &&
					!Util.isBlankOrNull(batchProcessForm.getPartnerLastName())){
				query = query + " and lower(partnerAdmin.lastName) like '"+batchProcessForm.getPartnerLastName().toLowerCase().trim()+"%'";
			}
			if(batchProcessForm.getCompanyName() !=null &&
					!Util.isBlankOrNull(batchProcessForm.getCompanyName())){
				query = query + " and lower(partnerAdmin.partnerCompanyInfo.companyName) like '"+batchProcessForm.getCompanyName().toLowerCase().trim()+"%'";
			}
			partnerList =  session.createQuery(query).list();
			for (Iterator iter = partnerList.iterator(); iter.hasNext();) {
				Object element[] = (Object[]) iter.next();
				partnerListDTO = new BatchProcessPartnerListDTO();
				if(element[0] != null){
					partnerListDTO.setPartnerId(element[0].toString());
				}
				if(element[1] != null){
					partnerListDTO.setFirstName(element[1].toString());
				}
				if(element[2] != null){
					partnerListDTO.setLastName(element[2].toString());
				}
				if(element[3] != null){
					partnerListDTO.setCompanyName(element[3].toString());
				}
				if(element[4] != null){
					partnerListDTO.setCompanyId(element[4].toString());
				}				
				batchProcessPartnerList.add(partnerListDTO);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return batchProcessPartnerList;
	}
    
    
}
