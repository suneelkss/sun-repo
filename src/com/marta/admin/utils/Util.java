package com.marta.admin.utils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

import com.marta.admin.utils.Constants;

/**
 * This is the util class.
 * @author Administrator
 * 
 */
public class Util {
	
	static Date systemDate = new Date();
	
	 private static final String HIBERNATE_ESCAPE_CHAR = "\\";
	
	 public static String replaceAll(String value) {
	        return value
	        		.replace("#",  HIBERNATE_ESCAPE_CHAR + "#")
	                .replace("\\",  HIBERNATE_ESCAPE_CHAR + "\\")
	                .replace("_",   HIBERNATE_ESCAPE_CHAR + "_")
	                .replace("%",   HIBERNATE_ESCAPE_CHAR + "%")
	                .replace("$",  HIBERNATE_ESCAPE_CHAR + "$")
	                .replace("@",  HIBERNATE_ESCAPE_CHAR + "@")
	        .replace("&",  HIBERNATE_ESCAPE_CHAR + "&");
	 
	    }
	
	
	
	public static boolean greaterThenToday(Date input) {

		boolean flag = false;
	//	System.out.println("fare: " + fareProduct);

		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");


		
		


		Date today = new Date();

		try {
		//	System.out.println("today: " + today + " input: " + df.parse(temp));
			if (today.compareTo(input	) > 0) {
				flag = false;
			//	System.out.println("today is greater than date");
			}
			if (today.compareTo(input) < 0) {
				flag = true;
			//	System.out.println("today is less than date");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public static String removeHtmlFormatting(String input){
		String output ="";
		
		output = input.replace("<html>", " ").replace("</html>", " ").replace("<br>"," ");
		
		return output;
	}
	
	/**
	 * Method to get to Date as String.
	 *
	 * @return String
	 */
	public static String getDateAsString(Date date, String format) {
    	if (date == null) {
    		return "";
    	} else {
	    	SimpleDateFormat sdf = new SimpleDateFormat(format);
	    	return sdf.format(date);
    	}
    }
	
	
	public static String getFormattedStackTrace(Exception e){
		
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		return stacktrace;
		
	}
	
	public static String getPeriodName() throws Exception {
		   Calendar cal = Calendar.getInstance();
		   DateFormat dateFormat = new SimpleDateFormat("MMM-yy");
		   cal.add(Calendar.DATE, -1);
		   System.out.println("Today's date is "+dateFormat.format(cal.getTime()));

		   return dateFormat.format(cal.getTime()) ;
	}
	
	/*
	 * getting lastDate of next Month
	 * @return
	 * @throws Exception 
	 */
	public static Date getLastDateOfNextMonthOM() throws Exception {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.add(Calendar.MONTH, 1);
		Date date_ = calendar.getTime();
		
		return date_;
	}
	
	/**
	 * Method to add delemiter.
	 * 
	 * @param delimiterString
	 * @return
	 */
	public static String addDelemiter(String[] delimiterString) {
		StringBuffer delimiter = new StringBuffer();
		String delimiteredString = "";
		if (null != delimiterString) {
			for (int index = 0; index < delimiterString.length; index++) {
				delimiter.append(delimiterString[index] + ",");
			}
			if (!delimiter.toString().equals("")) {
				delimiteredString = delimiter.toString().substring(0,
						delimiter.toString().length() - 1);
			}
		}
		return delimiteredString;
	}
	
	/**
	 * get the cuurrent date
	 * 
	 * @return
	 */
	public final static String getDate() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		return (df.format(new Date()));
	}

	/**
	 * get the currenttime
	 * 
	 * @return
	 */
	public final static String getTime() {
		DateFormat df = new SimpleDateFormat("hh-mm-ss");
		return (df.format(new Date()));
	}
		
	/**
	 * Method to get formated date.
	 * 
	 * @param dateVal
	 * @return
	 * @throws Exception
	 */
	public static String getFormattedDate(Object dateVal) throws Exception {
		String formattedDate = "";
		formattedDate = new SimpleDateFormat(Constants.DATE_FORMAT)
				.format(dateVal);
		return formattedDate;
	}
	
	
	public static Date getFormattedDateForDate(Date currentdate) throws Exception {
		   
		   DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
			
			String dateAsString = formatter.format(currentdate);
			try {
				currentdate = formatter.parse(dateAsString);
			} catch (ParseException e) {
				System.out.println("ParseException");
			}
			return currentdate;
	}
	
	
	public static String getFormattedYesterday() throws Exception {
		  Calendar cal = Calendar.getInstance();
		   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		   cal.add(Calendar.DATE, -1);
		   return dateFormat.format(cal.getTime()) ;
	}
	
	public static String getFormattedtoday()  {
		  Calendar cal = Calendar.getInstance();
		   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		  
		   return dateFormat.format(cal.getTime()) ;
	}
	
	public static Date getFormattedYesterdayDate() throws Exception {
		  Calendar cal = Calendar.getInstance();
		   DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		   cal.add(Calendar.DATE, -1);
		   return cal.getTime() ;
	}
	
	/**
	 * Method to check whether the given strings are equal / not.
	 * 
	 * @param dataString
	 * @param findString
	 * @return
	 */
	public static boolean equalsIgnoreCase(String dataString, String findString) {
		dataString = dataString.toUpperCase();
		findString = findString.toUpperCase();
		if (dataString.equals(findString)) {
			return true;
		} else {
			return false;

		}
	}

	/**
	 * Method to get system date.
	 * 
	 * @return
	 */
	public static Date getSystemdate() {
		DateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT);
		Date currentdate = new Date();
		String dateAsString = formatter.format(currentdate);
		try {
			currentdate = formatter.parse(dateAsString);
		} catch (ParseException e) {
			System.out.println("ParseException");
		}
		return currentdate;
	}

	/**
	 * Method to check whether the string value is null / blank.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlankOrNull(String value) {
		return value == null || value.trim().length() == 0;
	}
	
	/**
	 * Method to replace String
	 * 
	 * @param source
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replace(String source, String pattern, String replace) {
		if (source != null) {
			final int len = pattern.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				sb.append(source.substring(start, found));
				sb.append(replace);
				start = found + len;
			}

			sb.append(source.substring(start));

			return sb.toString();
		} else
			return "";
	}

	/**
	 * Method to replace String
	 * 
	 * @param source
	 * @param pattern
	 * @param replace
	 * @return
	 */
	public static String replaceFirstOccurance(String source, String pattern,
			String replace) {
		if (source != null) {
			final int len = pattern.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				sb.append(source.substring(start, found));
				sb.append(replace);
				start = found + len;
				break;
			}

			sb.append(source.substring(start));

			return sb.toString();
		} else
			return "";
	}

	public static String replaceNthOccurance(String source, String pattern,
			String replace, int occuaranceIndex) {
		if (source != null) {
			final int len = pattern.length();
			StringBuffer sb = new StringBuffer();
			int found = -1;
			int start = 0;
			int currentCountIndex = 0;

			while ((found = source.indexOf(pattern, start)) != -1) {
				currentCountIndex++;
				if (currentCountIndex == occuaranceIndex) {
					sb.append(source.substring(0, found));
					sb.append(replace);
					start = found + len;
					break;
				} else {
					start = found + pattern.length();
				}
			}

			sb.append(source.substring(start));

			return sb.toString();
		} else
			return "";
	}

	/**
	 * To rename the given directory with the given new name.
	 * 
	 * @param oldName
	 * @param newName
	 * @return
	 * @throws Exception
	 */
	public static boolean renameFile(String oldName, String newName)
			throws Exception {

		boolean isRenameSuccess = false;

		if (oldName == null || oldName.trim().length() == 0) {
			return false;
		}
		if (newName == null || newName.trim().length() == 0) {
			return false;
		}

		File oldFile = new File(oldName);
		File newFile = new File(newName);

		if (!oldFile.exists()) {
			return false;
		}

		isRenameSuccess = oldFile.renameTo(newFile);

		return isRenameSuccess;

	}

	/**
	 * Method to check whether the email is valid / not.
	 * 
	 * @param emailId
	 * @return
	 */
	public static boolean isValidEmailId(String emailId) {

		String regExPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}"
				+ "\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\"
				+ ".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern pattern = Pattern.compile(regExPattern);
		Matcher matcher = pattern.matcher(emailId);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 
	 * This method accepts request as argument and returns the context path.
	 * 
	 * 
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 */

	public static String getContextPath(HttpServletRequest request) {

		return request.getContextPath().replaceAll("/", "");

	}

	/**
	 * Method to return the real path of the web application up to context
	 * 
	 * @param request
	 * @return String
	 */
	public static String getRealPathUptoContext(HttpServletRequest request) {

		String pathInfo = request.getRequestURL().toString().split(

		getContextPath(request))[0];

		return pathInfo;

	}

	/**
	 * Method to convert array to string.
	 * 
	 * @param arrayStr
	 * @return
	 */
	public static String arrayToString(String[] arrayStr) {
		String valueStr = "";
		if (null != arrayStr && arrayStr.length > 0) {
			for (int cnt = 0; cnt < arrayStr.length; cnt++) {
				if (null != arrayStr[cnt] && !arrayStr[cnt].trim().equals("")) {
					valueStr += arrayStr[cnt] + ",";
					valueStr = valueStr.substring(0, valueStr.length() - 1);
				}
			}

		}

		return valueStr;
	}

	/**
	 * Method to get time stamp.
	 * 
	 * @return
	 */
	public static String getTimestamp() {
		String timestamp = "";
		timestamp = getDate() + "/" + getTime();
		return timestamp;
	}

	/**
	 * Method to get timestamp with under score.
	 *  
	 * @return
	 */
	public static String getTimestampUnderscore() {
		String timestamp = "";
		timestamp = getDate() + "_" + getTime();
		return timestamp;
	}

	/**
	 * Method to add days.
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, 5, amount);
	}

	/**
	 * Method to add date.
	 * 
	 * @param date
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	public static Date add(Date date, int calendarField, int amount) {
		if (date == null)
			throw new IllegalArgumentException("The date must not be null");
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}
	
	/**
	 * Method to generate a password in the specifed length.
	 * 
	 * @param length
	 * @return
	 */
	public static String generatePassword(int length) {		
		String charset = "!0123456789abcdefghijklmnopqrstuvwxyz";
		Random rand = new Random(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        return sb.toString();
		
	}

	/**
	 * Method to get action url.
	 * 
	 * @param actionURL
	 * @return
	 */
    public static String getActionDetails(String actionURL) {
    	String newActionURL = actionURL;
    	if (actionURL == null) {
    		return "";
    	}
    	
    	if (getCountOfCharInStr(actionURL, '/') > 1) {
    		newActionURL = actionURL.substring(actionURL.lastIndexOf("/"));
    	}
    	
    	return newActionURL;
    }

    /**
     * Method to get character count in a string.
     * 
     * @param strValue
     * @param charString
     * @return
     */
    private static int getCountOfCharInStr(String strValue, char charString) {
    	int count =0;
    	if (strValue == null || strValue.trim().equals("")) {
    		return count;
    	}
    	
    	for (int i = 0; i < strValue.length(); i++) {
			if (charString == strValue.charAt(i)) {
				count++;
			}
		}
    	
    	return count;
    }
	
	/**
	 * Method to get months list.
	 * 
	 * @return
	 */
	public static List getMonths() {
		   List monthList = new ArrayList();
		   monthList.add(new LabelValueBean("01","01"));
		   monthList.add(new LabelValueBean("02","02"));
		   monthList.add(new LabelValueBean("03","03"));
		   monthList.add(new LabelValueBean("04","04"));
		   monthList.add(new LabelValueBean("05","05"));
		   monthList.add(new LabelValueBean("06","06"));
		   monthList.add(new LabelValueBean("07","07"));
		   monthList.add(new LabelValueBean("08","08"));
		   monthList.add(new LabelValueBean("09","09"));
		   monthList.add(new LabelValueBean("10","10"));
		   monthList.add(new LabelValueBean("11","11"));
		   monthList.add(new LabelValueBean("12","12"));	   
		   return monthList;
	 }
	
	/**
	 * Method to get years list.
	 * 
	 * @return
	 */
	public static List getYears() {
		    List yearList = new ArrayList();
	        Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);
	        for (int i = 0; i < 12; i++) {
	    	    yearList.add(new LabelValueBean(""+year,""+year));
	    	    year++;
		    }
		    return yearList;
	    }

	/**
	 * Method to convert credit card number.
	 * 
	 * @param credCardNoParam
	 * @return
	 */
    public static String convertCreditCardNo(String credCardNoParam) {
	        StringBuffer 	credCardNo = new StringBuffer(credCardNoParam);
	        StringBuffer retCredCardNo = new StringBuffer();
	        for(int i =0;i<credCardNo.length();i++){
	        	if(i <= (credCardNo.length()-5))
	        	    retCredCardNo.append('x');
	        	else
	        		retCredCardNo.append(credCardNo.charAt(i));
	        }
	        int i=0;int j=1;
	        while(i<=retCredCardNo.length())
	        {
	        	if(j%5==0){
	        		j=0;
	        		retCredCardNo.insert(i, ' ');
	        	}else{
	        		
	        	}
	        	j++;
	        	i++;
	        }
	    	return retCredCardNo.toString().trim();
	    }

    /**
     * Method to get date of 30 day previous.
     * 
     * @param days
     * @return
     * @throws ParseException
     */
    public static String getDateFromNow(int days) throws ParseException{
	    	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	    	Calendar cal = new GregorianCalendar();
	    	cal.setTime(new Date());
	    	//Substract 30 days from the calendar
	        cal.add(Calendar.DAY_OF_MONTH, -days);
	        return formatter.format(cal.getTime());
	    }
   
    /**
     * To get the next day 
     * @param date
     * @return strNextDay
     * @throws Exception
     */
    public static String getNextDay(String date) throws Exception{    	
    	Date nextDay = addDay(convertStringToDate(date));    	
    	return convertDateToString(nextDay);
    }
    
    /**
     * To convert String to Date
     * @param dateVal
     * @return formattedDate
     * @throws Exception
     */
	public static Date convertStringToDate(String dateVal) throws Exception {
		Date formattedDate = new Date();
		formattedDate = new SimpleDateFormat(Constants.DATE_FORMAT).parse(dateVal);
		return formattedDate;
	}
	
	/**
	 * To convert Date to String
	 * 
	 * @param date
	 * @return strDate
	 * @throws Exception
	 */
	public static String convertDateToString(Date date) throws Exception {		
		String strDate = new SimpleDateFormat(Constants.DATE_FORMAT).format(date);
		return strDate;
	}
	
	/**
	 * To add the day to the given date.
	 * 
	 * @param date
	 * @return dateNextDay
	 * @throws Exception
	 */
	public static Date addDay(Date date) throws Exception{ 
	   Calendar cal = Calendar.getInstance(); 
	   cal.setTime (date); 
	   cal.add (Calendar.DATE, 1); 
	   return cal.getTime(); 
	} 
	   
    /**
     * Method to remove spaces in the breeze card serial number.
     * 
     * @param breezeCardSerialNumber
     * @return
     * @throws Exception
     */
    public static String removeSpaceInBreezeCardSerialNo(String breezeCardSerialNumber) throws Exception{
	    	String finalBreezeCardNumber = "";
	    	String[] serialNumberArray;
	    	try{
		    	serialNumberArray = breezeCardSerialNumber.split(" ");
		    	for(int arrayIncr=0;arrayIncr<serialNumberArray.length;arrayIncr++){
		    		finalBreezeCardNumber += serialNumberArray[arrayIncr];
		    	}
	    	}
	    	catch(Exception ex){
	    		BcwtsLogger.error(" Exception in removing space in breeze card serial number :"+ex.getMessage());
				throw ex;
	    	}
	    	return finalBreezeCardNumber;
		}

    /**
     * Method to insert space in the breeze card serial number.
     * 
     * @param breezeCardSerialNumber
     * @return
     * @throws Exception
     */
    public static String insertSpaceInBreezeCardSerialNo(String breezeCardSerialNumber) throws Exception{
	    	
			StringBuffer 	breezeCardNo = new StringBuffer(breezeCardSerialNumber);
	    	String finalBreezeCardNo = "";
	        
	    	int i=0;int j=1;
	    	try{
		        String regEx = "^\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}$";
				Pattern pattern = Pattern.compile(regEx);
				Matcher matcher = pattern.matcher(breezeCardNo.toString());
				if(breezeCardNo.indexOf(" ", 0) > 0){
					if(matcher.find()){
						finalBreezeCardNo = breezeCardNo.toString();
					}else{
						return null;
					}
				}else{
					if(breezeCardNo.length() == 16){
				        while(i<=breezeCardNo.length())
				        {
				        	if(j%5==0){
				        		j=0;
				        		breezeCardNo.insert(i, ' ');
				        	}else{
				        		
				        	}
				        	j++;
				        	i++;
				        }
			        finalBreezeCardNo = breezeCardNo.toString();
					}else{
						return null;
					}
				}
	    	}catch(Exception ex){
	    		BcwtsLogger.error(" Exception in insert space in breeze card serial number :"+ex.getMessage());
				throw ex;
	    	}
	    	return finalBreezeCardNo;
		}
	
    /**
     * Method to eecode secrect answer.
     * 
     * @param secreatAnswer
     * @return
     */
    public static String convertSecretAnswer(String secreatAnswer){
	    	StringBuffer answerWrapped = new StringBuffer();
	    	for (int i = 0; i < secreatAnswer.length(); i++) {
	    		answerWrapped.append("x");
				
			}
	    	return answerWrapped.toString();
	    }
	
    /**
     * Method to delete files in a folder.
     * 
     * @param fileResourcePath
     */
    public static void deleteFilesInFolder(String fileResourcePath){
	    	File folder = new File(fileResourcePath);			
		    File[] listOfFiles = folder.listFiles();
		    if(listOfFiles.length > 0){
			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			    	  listOfFiles[i].delete();		    	  
			      }
			    }			    
		    }
	    }
	    
	    /**
     * Method to get current date and time.
	     * @return
	     */
    public static String getCurrentDateAndTime(){
	    	
	    	 DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_WITH_TIME_FORMAT);
	         Date date = new Date();
	         return dateFormat.format(date);
	         
	    }
	
    /**
     * Method to check whether tge given value is double / not.
     * 
     * @param input
     * @return
     */
    public static boolean isDouble( String input )  
	    {  
	       try  
	       {  
	          Double.parseDouble( input );  
	          return true;  
	       }  
	       catch( Exception e)  
	       {  
	          return false;  
	       }  
	    }
	
    /**
     * Method to get temp directory path.
     * 
     * @return
     */
    public static String getTempPath(){
	    	return System.getProperty("java.io.tmpdir");
	    }
    
    /**
     * Method to find and replace the character.
     * 
     * @param source
     * @param find
     * @param replace
     * @return
     * @throws Exception
     */
    public static String findReplace(String source, char find, char replace) throws Exception {
		String result  = null;
		result = source.replace(find, replace);
		return result;		
	}
    
    /**
     * Method to return date in Month dd, YYYY format.
     * 
     * @param dateToFormat
     * @return
     */
    public static String dateFormatToDisplay(Date dateToFormat) {
    	SimpleDateFormat formatterLogin = new SimpleDateFormat("dd-MMMM-yyyy");
		return formatterLogin.format(dateToFormat);
    }
    /**
	 * To get the current date. 
	 * @return
	 * @throws Exception
	 */
    public static String getCurrentDate() throws Exception {
    	Date date = new Date();	
   	 	DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
        return dateFormat.format(date);
   }
    /**
	 * Method to get past years list.
	 * 
	 * @return
	 */
	public static List getPastYears() {
		    List yearList = new ArrayList();
	        Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);
	        for (int i = 0; i < 12; i++) {
	    	    yearList.add(new LabelValueBean(""+year,""+year));
	    	    year--;
		    }
		    return yearList;
	    }

	 public static int lastDayOfMonth(int month, int year) throws Exception {
		   Calendar calendar = Calendar.getInstance();
		   calendar.set(year, (month-1), 1);
		   return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	   }  
	 
	
	 /*
		 * getting firstdate of next month
		 * @return
		 * @throws Exception
		 */
		public static String getFirstDateOfNextMonth() throws Exception {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar
					.getActualMinimum(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.MONTH, 1);
			Date date_ = calendar.getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			return sdf1.format(date_);
		}
		/*
		 * getting lastDate of next Month
		 * @return
		 * @throws Exception 
		 */
		public static String getLastDateOfNextMonth() throws Exception {
			Date date = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, calendar
					.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.add(Calendar.MONTH, 1);
			Date date_ = calendar.getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			return sdf1.format(date_);
		}
		/**
		 * To get last date of the current month
		 * 
		 * @return
		 * @throws Exception
		 */
		public static String getLastDateOfMonth() throws Exception {
			Date d = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date dddd = calendar.getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.DATE_FORMAT);
			return sdf1.format(dddd);
		}
		
		public static String getLastDateOfMonth2() throws Exception {
			Date d = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date dddd = calendar.getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.DATE_FORMAT2);
			return sdf1.format(dddd);
		}
		
		public static String getFirstDateOfMonth() throws Exception {
			Date d = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date dddd = calendar.getTime();
			SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.DATE_FORMAT2);
			return sdf1.format(dddd);
		}
		
}
