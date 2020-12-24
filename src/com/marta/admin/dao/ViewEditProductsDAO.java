package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.ViewEditProductsDTO;
import com.marta.admin.hibernate.BcwtProduct;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Util;

/**
 * This is the data access object to view edit products 
 * 
 * @author Jagadeesan
 *
 */
public class ViewEditProductsDAO extends MartaBaseDAO{

	final String ME = "ViewEditProductsDAO: ";

	/**
	 * This method will return products list
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getViewEditProductsList()
			throws Exception {
			
			final String MY_NAME = ME + "getViewEditProductsList: ";
			BcwtsLogger.debug(MY_NAME);
			Session session = null;
			Transaction transaction = null;
			List viewEditProductsParameters = new ArrayList();
			List viewEditProductsList = new ArrayList();
			ViewEditProductsDTO viewEditProductsDTO = null ;
			NumberFormat formatter = new DecimalFormat("#0.00");
			
			try {
				BcwtsLogger.debug(MY_NAME + "Get Session");
				session = getSession();
				BcwtsLogger.debug(MY_NAME + " Got Session");
				transaction = session.beginTransaction();
				BcwtsLogger.debug(MY_NAME + " Execute Query");
				String query = "from " +
									"BcwtProduct bcwtProduct " +
								"order by " +
									"bcwtProduct.bcwtregion.regionid";
				viewEditProductsParameters = session.createQuery(query).list();
			
			for (Iterator it = viewEditProductsParameters.iterator(); it.hasNext();){
				BcwtProduct bcwtProduct = (BcwtProduct)it.next();
				if(bcwtProduct != null ){
					viewEditProductsDTO = new ViewEditProductsDTO();
					
					if(null != bcwtProduct.getProductid()){
						viewEditProductsDTO.setProductId(bcwtProduct.getProductid().toString());
					}
					if(null != bcwtProduct.getFare_instrument_id()){
						viewEditProductsDTO.setFareInstrumentId(bcwtProduct.getFare_instrument_id().toString());
					}
					if(null != bcwtProduct.getBcwtregion().getRegionid()){
						viewEditProductsDTO.setRegionId(bcwtProduct.getBcwtregion().getRegionid().toString());
					}
					if(null != bcwtProduct.getBcwtregion().getRegionname()){
						viewEditProductsDTO.setRegionName(bcwtProduct.getBcwtregion().getRegionname().toString());
					}
					if(!Util.isBlankOrNull(bcwtProduct.getProductname())){
						viewEditProductsDTO.setProductName(bcwtProduct.getProductname());
					}
					if(!Util.isBlankOrNull(bcwtProduct.getProductdescription())){
						viewEditProductsDTO.setProductDescription(bcwtProduct.getProductdescription());
					}
					if(!Util.isBlankOrNull(bcwtProduct.getProductdetaildesc())){
						viewEditProductsDTO.setProductDetailedDesc(bcwtProduct.getProductdetaildesc());
					}
					if(null != bcwtProduct.getRiderclassification()){
						viewEditProductsDTO.setRiderClassification(bcwtProduct.getRiderclassification().toString());
					}
					if(null!=bcwtProduct.getPrice()){
						viewEditProductsDTO.setPrice(formatter.format((bcwtProduct.getPrice())).toString());
					}else
						viewEditProductsDTO.setPrice("0");
					if(null!=bcwtProduct.getSortorder()){
						viewEditProductsDTO.setSortOrder(bcwtProduct.getSortorder().toString());
					}
					if(!Util.isBlankOrNull(bcwtProduct.getActivestatus())){
						viewEditProductsDTO.setActiveStatus(bcwtProduct.getActivestatus().toString());
					}
					viewEditProductsList.add(viewEditProductsDTO);
				}
			}
			
			transaction.commit();
			session.flush();			
			BcwtsLogger.info(MY_NAME
					+ "Got view edit products list and whose size is "
						+ viewEditProductsParameters.size());
			} catch (Exception ex) {
				BcwtsLogger.error(MY_NAME + ex.getMessage());
				throw ex;
			
			} finally {
				closeSession(session, transaction);
			}
			return viewEditProductsList;
}
	/**
	 * This method will update product details for particular product id
	 * 
	 * @param viewEditProductsDTO
	 * @return
	 * @throws Exception
	 */
	public boolean updateViewEditProductsDetails(ViewEditProductsDTO viewEditProductsDTO) throws Exception {

		final String MY_NAME = ME + " updateViewEditProductsDetails: ";
		BcwtsLogger.debug(MY_NAME + " updating view edit products details ");
		boolean isUpdated = false;
		Session session = null;
		Transaction transaction = null;
		BcwtProduct bcwtProduct = null ;
		Long productId = new Long(viewEditProductsDTO.getProductId());
		try {
			session = getSession();
			transaction = session.beginTransaction();
			if(productId != null){
				String query = "from " +
									"BcwtProduct bcwtProduct " +
								"where " +
									"bcwtProduct.productid  = '"+ productId +"' ";
				bcwtProduct = (BcwtProduct)session.createQuery(query).uniqueResult();

				if(viewEditProductsDTO.getProductDescription() != null ){
					bcwtProduct.setProductdescription(viewEditProductsDTO.getProductDescription());
				}
				if(viewEditProductsDTO.getProductDetailedDesc() != null ){
					bcwtProduct.setProductdetaildesc(viewEditProductsDTO.getProductDetailedDesc());
				}
				if(viewEditProductsDTO.getActiveStatus() != null ){
					bcwtProduct.setActivestatus(viewEditProductsDTO.getActiveStatus());
				}
				if(!Util.isBlankOrNull(viewEditProductsDTO.getSortOrder())){
					bcwtProduct.setSortorder(new Integer(viewEditProductsDTO.getSortOrder()));
				}else{
					bcwtProduct.setSortorder(new Integer(0));
				}
			}
			session.update(bcwtProduct);
			isUpdated = true;
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "view edit product details updated to database ");
		} catch (Exception e) {
			BcwtsLogger.error(MY_NAME
					+ " Exception in updating view edit product details :"
					+ e.getMessage());
			throw e;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return isUpdated;
	}
	/**
	 * This method will return product details for particular product id
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ViewEditProductsDTO getViewEditProductDetails(Long productId)throws Exception{
		
		final String MY_NAME = ME + " getViewEditProductDetails: ";
		BcwtsLogger.debug(MY_NAME + " to get view edit product details list ");
		Transaction transaction = null;
		Session session = null;
		ViewEditProductsDTO viewEditProductsDTO = null;
		NumberFormat formatter = new DecimalFormat("#0.00");
		try {
			BcwtProduct bcwtProduct = new BcwtProduct();
			
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			String query = "from BcwtProduct where productid = "+productId;
			
			bcwtProduct = (BcwtProduct)session.createQuery(query).uniqueResult();
	
			if(bcwtProduct != null ){
				viewEditProductsDTO = new ViewEditProductsDTO();
				
				if(null != bcwtProduct.getProductid()){
					viewEditProductsDTO.setProductId(bcwtProduct.getProductid().toString());
				}
				if(null != bcwtProduct.getFare_instrument_id()){
					viewEditProductsDTO.setFareInstrumentId(bcwtProduct.getFare_instrument_id().toString());
				}
				if(null != bcwtProduct.getBcwtregion().getRegionid()){
					viewEditProductsDTO.setRegionId(bcwtProduct.getBcwtregion().getRegionid().toString());
				}
				if(null != bcwtProduct.getBcwtregion().getRegionname()){
					viewEditProductsDTO.setRegionName(bcwtProduct.getBcwtregion().getRegionname().toString());
				}
				if(null != bcwtProduct.getProductname()){
					viewEditProductsDTO.setProductName(bcwtProduct.getProductname().toString());
				}
				if(null != bcwtProduct.getProductdescription()){
					viewEditProductsDTO.setProductDescription(bcwtProduct.getProductdescription().toString());
				}
				if(null != bcwtProduct.getProductdetaildesc()){
					viewEditProductsDTO.setProductDetailedDesc(bcwtProduct.getProductdetaildesc().toString());
				}
				if(null != bcwtProduct.getPrice()){
					viewEditProductsDTO.setPrice(formatter.format((bcwtProduct.getPrice())).toString());
				}
				if(null != bcwtProduct.getActivestatus()){
					viewEditProductsDTO.setActiveStatus(bcwtProduct.getActivestatus().toString());
				}
				if(null != bcwtProduct.getSortorder()){
					viewEditProductsDTO.setSortOrder(bcwtProduct.getSortorder().toString());
				}
			}
			transaction.commit();
			session.flush();
			session.close();
		}
		catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		}
		return viewEditProductsDTO;
	}
}
