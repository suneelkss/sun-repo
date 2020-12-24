package com.marta.admin.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExportUtil {

	public static String getExportDataString(List values, String fileMode) {
		
		String headerString = "";
		String dataString = "";
		String seperator = getSeperatorByFileMode(fileMode);
		//String seperator = ",";		
		headerString = getHeaderRow(values, seperator);
		
		dataString = getDataRows(values, seperator);
		
		
		
		String exportDataString = headerString + newLineSeperator + dataString;
		return exportDataString;
		
	}
	
	public static String getExportData(List values, String fileMode) {
		
		String headerString = "";
		String dataString = "";
		String seperator = getSeperatorByFileMode(fileMode);
		//String seperator = ",";		
		//headerString = getHeaderRow(values, seperator);
		
		dataString = getRowData(values, seperator);
		
		String exportDataString = dataString;
		return exportDataString;
		
	}
	
	
	public static String getHeldSupUnSupDataRows(List values, String fileMode) {
				
		String dataString = "";
		String seperator = getSeperatorByFileMode(fileMode);
		//String seperator = ",";		
		//headerString = getHeaderRow(values, seperator);
		
		dataString = getHeldSupUnSupRows(values, seperator);
		
		String exportDataString = dataString;
		return exportDataString;
		
	}
	
	public static String getBlockedDomainsRows(List values, String fileMode) {
		
		String headerString = "";
		String dataString = "";
		String seperator = getSeperatorByFileMode(fileMode);
		//String seperator = ",";		
		headerString = "Blocked Domain Name"+seperator+"Comment"+seperator+
			"Created By"+seperator+"Created On"+seperator+
			"Updated By"+seperator+"Updated On"+seperator+
			"Domain Type";		
		dataString = getBDomainsRows(values, seperator);		
		String exportDataString = headerString + newLineSeperator + dataString;
		return exportDataString;
		
	}
	
	public static String getDataRows(List values, String seperator) {
	
		String headerStartString = null;
		StringBuffer dataRowsData = new StringBuffer();
		String currHeaderValue;
		int tempj = 0;
		try{
			for (Iterator iter = values.iterator(); iter.hasNext();) {
				Object[] objArr = (Object[]) iter.next();
				
				
				if (headerStartString == null) {
					headerStartString = (String) objArr[fieldNameRowIndex];
				} else {
					currHeaderValue = (String) objArr[fieldNameRowIndex];
					if (currHeaderValue.equals(headerStartString)) {
						dataRowsData.append(newLineSeperator);
						tempj = 0;
					}
				}
				String flag="";
				if(null!=objArr[flagRowIndex])
					flag = (String) objArr[flagRowIndex];
				
				if (flag.equalsIgnoreCase("yes")) {
					if(tempj ==0){
						dataRowsData.append(objArr[emailValueRowIndex]);
					}else{
						dataRowsData.append(seperator + objArr[emailValueRowIndex]);
					}
				} else {
					if(tempj ==0){
						dataRowsData.append(objArr[dataValueRowIndex]);
					}else{
						dataRowsData.append(seperator + objArr[dataValueRowIndex]);
					}
				}
				tempj++;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return dataRowsData.toString();
	}
	
	private static String getRowData(List values, String seperator) {
		
		StringBuffer dataRowsData = new StringBuffer();		
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			String objData = (String) iter.next();			
			dataRowsData.append(objData);					
			dataRowsData.append(newLineSeperator);			
		}		
		return dataRowsData.toString();
	}
	
	private static String getHeaderRow(List values, String seperator) {
		List headerData = new ArrayList();
		for (int i = 0; i < values.size(); i++) {
			Object[] currObjArr = (Object[])values.get(i);
			String currHeaderData = (String) currObjArr[fieldNameRowIndex];
			
			if (!headerData.contains(currHeaderData)) {
				headerData.add(currHeaderData);
			} 
		}
		
		StringBuffer headerDataBuff = new StringBuffer();
		int tempi=0;
		for (Iterator iter = headerData.iterator(); iter.hasNext();) {
			String currHeader = (String) iter.next();
			if(tempi==0){
				headerDataBuff.append(currHeader);
			}else{			
				headerDataBuff.append(seperator+currHeader);
			}
			
			tempi ++;
		}
		
		return headerDataBuff.toString();
	}
	
	private static String getSeperatorByFileMode(String fileMode){
		String seperator = "";
		if(null!=fileMode && !fileMode.equals("")){
			if(fileMode.equals(Constants.EXPORT_TO_CSV)){
				seperator = ",";
			}
			if(fileMode.equals(Constants.EXPORT_TO_TSV)){
				seperator = "\t";
			}			
			if(fileMode.equals(Constants.EXPORT_TO_TXT)){
				seperator = ",";
			}
			if(fileMode.equals(Constants.EXPORT_TO_PIPE)){
				seperator = "|";
			}
		}
		return seperator;
	}
	
	private static String getHeldSupUnSupRows(List values, String seperator) {
		
		StringBuffer dataRowsData = new StringBuffer();
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Object[] objArr = (Object[]) iter.next();			
			dataRowsData.append((String)objArr[fieldNameRowIndex]);					
			dataRowsData.append(newLineSeperator);			
		}		
		return dataRowsData.toString();
	}
	
	private static String getBDomainsRows(List values, String seperator) {
		
		StringBuffer dataRowsData = new StringBuffer();
		
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Object[] objArr = (Object[]) iter.next();
/*
 * 0 beBlockedDomainName.blockeddomainname,
 * 1 beBlockedDomainName.comments,
  "2 beBlockedDomainName.createdby,
  3 beBlockedDomainName.createdon," +
	4 "beBlockedDomainName.updatedby,
	5 beBlockedDomainName.updatedon
	6 type
 * */		
			try {
				String currVal = "";
				int tempk = 0;
				for(int j=0; j<objArr.length; j++){
					
					if (objArr[j] == null) {
						currVal = "";
					} else {
						if (objArr[j] instanceof java.sql.Timestamp) {
							currVal = getFormattedDate((java.sql.Timestamp)objArr[j]);
						} else {
							currVal = objArr[j].toString();
						}
					}
					if(tempk == 0){
						dataRowsData.append(currVal);
					}else{
						dataRowsData.append(seperator + currVal);
					}
					tempk ++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			dataRowsData.append(newLineSeperator);			
		}		
		return dataRowsData.toString();
	}
	
	private static String getFormattedDate(java.sql.Timestamp timestamp) {
		Date dateValue = new java.util.Date(timestamp.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_WITH_TIME_FORMAT);
		return sdf.format(dateValue);
	}
	
	public static String getAllBlockedDomains(List values, String fileMode) {
		
		String headerString = "";
		String dataString = "";
		String seperator = getSeperatorByFileMode(fileMode);
		//String seperator = ",";		
		headerString = "Company Name"+seperator+"Block Domains"+seperator+
			"Comments"+seperator+"Created By"+seperator+
			"Created On"+seperator+"Updated By"+seperator+
			"Updated On"+seperator+"DomainType";		
		dataString = getBDomainsRows(values, seperator);		
		String exportDataString = headerString + newLineSeperator + dataString;
		return exportDataString;
	}
	
	private static String getAllBDomainsRows(List values, String seperator) {
		
		StringBuffer dataRowsData = new StringBuffer();
		
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Object[] objArr = (Object[]) iter.next();
	
			try {
				String currVal = "";
				int tempk = 0;
				for(int j=0; j<objArr.length; j++){
				   
					currVal = objArr[j].toString();
					
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			dataRowsData.append(newLineSeperator);			
		}		
		return dataRowsData.toString();
	}
	
	public static String getStopWordsAllRows(List values, String fileMode) {
			
			String headerString = "";
			String dataString = "";
			String seperator = getSeperatorByFileMode(fileMode);
			//String seperator = ",";		
			headerString = "Company Name"+seperator+"Stop Word"+seperator+
				"Comments"+seperator+"Created By"+seperator+
				"Created On"+seperator+"Updated By"+seperator+
				"Updated On";		
			dataString = getStopwordRows(values, seperator);		
			String exportDataString = headerString + newLineSeperator + dataString;
			return exportDataString;
			
		}
	
	private static String getStopwordRows(List values, String seperator) {
		
		StringBuffer dataRowsData = new StringBuffer();
		
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Object[] objArr = (Object[]) iter.next();
/*
 * 0 companyname,
 * 1 stopword,
  2 comments,
  3 cretedby, +
	4 "cretedon
	5 updatedby
	6 updatedon
 * */		
			try {
				String currVal = "";
				int tempk = 0;
				for(int j=0; j<objArr.length; j++){
					
					if (objArr[j] == null) {
						currVal = "";
					} else {
						if (objArr[j] instanceof java.sql.Timestamp) {
							currVal = getFormattedDate((java.sql.Timestamp)objArr[j]);
						} else {
							currVal = objArr[j].toString();
						}
					}
					if(tempk == 0){
						dataRowsData.append(currVal);
					}else{
						dataRowsData.append(seperator + currVal);
					}
					tempk ++;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			dataRowsData.append(newLineSeperator);			
		}		
		return dataRowsData.toString();
	}
	
	
	public static String getDomainBreakDownExportData(List values, String fileMode,String delimeter) {
		
		String dataString = "";
		String seperator = getSeperatorByFileMode(fileMode);		
		dataString = getDomainBreakRowData(values, seperator,delimeter);		
		String exportDataString = dataString;
		return exportDataString;		
	}
	
	public static String getDomainBreakRowData(List values, String seperator,String delimeter){
		StringBuffer dataRowsData = new StringBuffer();
		dataRowsData.append("Domain Name"+seperator+"Count");
		dataRowsData.append(newLineSeperator);
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			String element = (String) iter.next();			
			String[] domainCount = element.split(delimeter);
			dataRowsData.append(domainCount[0]+seperator+domainCount[1]);					
			dataRowsData.append(newLineSeperator);			
		}		
		return dataRowsData.toString();
	}
	
	private static int fieldNameRowIndex = 0;
	private static int dataValueRowIndex = 1;
	private static int emailValueRowIndex = 2;
	private static int flagRowIndex = 3;
	//private static String newLineSeperator = System.getProperty("line.separator");
	private static String newLineSeperator = "\r\n";
	
	public static String getHeldDataRows(List values, String seperator) {
		
		String headerString = "";
		String dataString = "";
		try{		
			dataString = getRowData(values, seperator);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String exportDataString = dataString;
		return exportDataString;
		
	}
}
