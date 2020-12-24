package com.marta.admin.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.marta.admin.dto.AddressDTO;
import com.marta.admin.dto.PrintShippingLabelDTO;
import com.marta.admin.utils.BcwtsLogger;
import com.marta.admin.utils.Constants;
import com.marta.admin.utils.Util;

public class PrintShippingLabelDAO extends MartaBaseDAO{
	final String ME = "PrintShippingLabelDAO: ";
		
	public List getPrintShippingLabelDetails(String orderType, String nextFareOrderId) throws Exception {
		final String MY_NAME = ME + "getPrintShippingLabelDetails: ";
		BcwtsLogger.debug(MY_NAME);		
		Session session = null;
		Transaction transaction = null;
		
		List printShippingLabelList = new ArrayList();
		String query = null;

		try {
			BcwtsLogger.debug(MY_NAME + "Get Session");
			session = getSession();
			BcwtsLogger.debug(MY_NAME + " Got Session");
			transaction = session.beginTransaction();
			BcwtsLogger.debug(MY_NAME + " Execute Query");
			
			if(!Util.isBlankOrNull(orderType)) {
					if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
						query =
							"select" +
								" bcwtPartnerOrderInfo.partnerCardorder.orderId," +
								" bcwtPartnerOrderInfo.nextfareorderid," +
								" bcwtPartnerOrderInfo.batchid" +
							" from" +
								" BcwtPartnerOrderInfo bcwtPartnerOrderInfo, PartnerCardOrder partnerCardOrder" +
							" where" +
								" partnerCardOrder.orderId = bcwtPartnerOrderInfo.partnerCardorder.orderId" +
								" and bcwtPartnerOrderInfo.nextfareorderid is not null";
						
						if(!Util.isBlankOrNull(nextFareOrderId)) {
							query = query + " and bcwtPartnerOrderInfo.nextfareorderid = " + nextFareOrderId; 
						}
					} else if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
						query =
							"select" +								
								" bcwtOrderInfo.bcwtorder.orderid," +		
								" bcwtOrderInfo.nextfareorderid,"  +
								" bcwtOrderInfo.batchid" +
							" from " +
								" BcwtOrderInfo bcwtOrderInfo, BcwtOrder bcwtOrder" +
							" where" +	
								" bcwtOrder.orderid = bcwtOrderInfo.bcwtorder.orderid" +
								" and bcwtOrderInfo.nextfareorderid is not null";
						
						if(!Util.isBlankOrNull(nextFareOrderId)) {
							query = query + " and bcwtOrderInfo.nextfareorderid = " + nextFareOrderId; 
						}
					}
			} else {
				throw new Exception(MY_NAME + "orderType cannot be null");
			}
			
			List queryList = session.createQuery(query).list();
			
			if (queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator(); iter.hasNext();) {
					Object[] element = (Object[]) iter.next();
					PrintShippingLabelDTO printShippingLabelDTO = new PrintShippingLabelDTO();
						if(element[0] != null) {
							printShippingLabelDTO.setOrderId(element[0].toString());
						}
						if(element[1] != null) {
							printShippingLabelDTO.setNextFareOrderId(element[1].toString());
						}
						if(element[2] != null) {
							printShippingLabelDTO.setBatchId(element[2].toString());
						}
						if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
							printShippingLabelDTO.setOrderType(Constants.ORDER_TYPE_PS);
						} else if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
							printShippingLabelDTO.setOrderType(Constants.ORDER_TYPE_GBS);
						}
						
						printShippingLabelList.add(printShippingLabelDTO);
					}
				}
			
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got Print Shipping Labeldetails");
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		return printShippingLabelList;
	}
	
	public AddressDTO getPrintPrintShippingLabelDetails(String orderId, String orderType) throws Exception {
		final String MY_NAME = ME + "getPrintPrintShippingLabelDetails: ";
		BcwtsLogger.debug(MY_NAME);
		String query = "";			
		AddressDTO addressDTO = null;		
		Session session = null;
		Transaction transaction = null;
			
		try{
			session = getSession();
			transaction = session.beginTransaction();
			
			if(Util.isBlankOrNull(orderType)){
				throw new Exception(MY_NAME + "orderType cannot be null");
			}
			
			if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)){
				query = " select bcwtpatronaddress.addresseename,bcwtpatronaddress.address1,bcwtpatronaddress.address2," +
							" bcwtpatronaddress.city,bcwtstate.statename,bcwtpatronaddress.zip,bcwtpatronaddress.patronaddressid," +
							" bcwtorderinfo.batchid" +
						" from BcwtPatronAddress bcwtpatronaddress, BcwtOrder bcwtorder," +
							" BcwtState bcwtstate, BcwtOrderInfo bcwtorderinfo" +
						" where" +
							" bcwtorder.orderid=" + orderId +
							" and bcwtorder.bcwtpatronaddress.patronaddressid = bcwtpatronaddress.patronaddressid" +
							" and bcwtorderinfo.bcwtorder.orderid = bcwtorder.orderid" +
							" and bcwtpatronaddress.bcwtstate.stateid = bcwtstate.stateid";
				
			} else if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)){
				query = "select partneraddress.fullname,partneraddress.address1,partneraddress.address2," +
							" partneraddress.city,partneraddress.state,partneraddress.zip,partneraddress.addressId," +
							" bcwtpartnerorderinfo.batchid" +
						" from" +
								" PartnerCardOrder partnercardorder,PartnerAddress partneraddress," +
								" BcwtPartnerOrderInfo bcwtpartnerorderinfo" +
						" where" +
							" partnercardorder.orderId = '"+orderId+"' " +
							" and bcwtpartnerorderinfo.partnerCardorder.orderId = partnercardorder.orderId" +
							" and partnercardorder.partnerAddress.addressId = partneraddress.addressId";
			}	
			
			List shippingAddressList = session.createQuery(query).list();
			
			if(shippingAddressList != null && !shippingAddressList.isEmpty()) {
				for (Iterator iter = shippingAddressList.iterator(); iter.hasNext();) {
					addressDTO = new AddressDTO();
					Object[] bcwtAddress = (Object[])iter.next();
					
					if(null != bcwtAddress[0])
						addressDTO.setAddresseename(bcwtAddress[0].toString());
					if(null != bcwtAddress[1])
						addressDTO.setAddress1(bcwtAddress[1].toString());
					if(null != bcwtAddress[2])
						addressDTO.setAddress2(bcwtAddress[2].toString());
					if(null != bcwtAddress[3])
						addressDTO.setCity(bcwtAddress[3].toString());
					if(null != bcwtAddress[4])
						addressDTO.setState(bcwtAddress[4].toString());
					if(null != bcwtAddress[5])
						addressDTO.setZip(bcwtAddress[5].toString());
					if(null != bcwtAddress[6])
						addressDTO.setPatronaddressid(new Long(bcwtAddress[6].toString()));
					if(null != bcwtAddress[7])
						addressDTO.setPatronid(new Long(bcwtAddress[7].toString()));
				}
			}
			
			transaction.commit();
			session.flush();
			BcwtsLogger.info(MY_NAME + "got Print Shipping Label details");
		}catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + "Exception while getting shipping address");
			throw ex;
		}finally {
			closeSession(session, transaction);
			BcwtsLogger.debug(MY_NAME + " Resources closed");
		}
		
		return addressDTO;
	}
	
	public String getBarcode(String orderId, String orderType) throws Exception {
		final String MY_NAME = ME + "getBarcode: ";
		BcwtsLogger.debug(MY_NAME);
		Transaction transaction = null;
		Session hibSession = null;
		String hibQuery = null;
		String barcode = null;
		
		try {
			if(Util.isBlankOrNull(orderId)) {
				throw new Exception(MY_NAME + "orderId is null");
			}
			if(Util.isBlankOrNull(orderType)) {
				throw new Exception(MY_NAME + "orderType is null");
			}
			
			hibSession = getSession();
			transaction = hibSession.beginTransaction();
			
			if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_PS)) {
				hibQuery = "select barcode from BcwtPartnerBarCode where orderid = " + orderId;
			} else if(orderType.equalsIgnoreCase(Constants.ORDER_TYPE_GBS)) {
				hibQuery = "select barcode from BcwtBarCode where orderid = " + orderId;
			}
			
			List queryList = hibSession.createQuery(hibQuery).list();
			
			if(queryList != null && !queryList.isEmpty()) {
				for (Iterator iter = queryList.iterator();iter.hasNext();){				
					Object element = (Object) iter.next(); 
					if(element != null){
						barcode = element.toString();
						break;
					}
				}
			}
			
			transaction.commit();
			hibSession.flush();
			BcwtsLogger.info(MY_NAME + "Got BarCode for order id: " + orderId);
		} catch (Exception ex) {
			BcwtsLogger.error(MY_NAME + ex.getMessage());
			throw ex;
		} finally {
			closeSession(hibSession, transaction);
			BcwtsLogger.info(MY_NAME + " Resources closed");
		}
		return barcode;
	}	

}
