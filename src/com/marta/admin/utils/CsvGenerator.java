package com.marta.admin.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import com.marta.admin.exceptions.MartaException;

 public class CsvGenerator {
	 
    private static final char DELIMITER = ',';
    private static final char TEXTQUALIFIER = '"';
    private static final String CHARSET = "ISO-8859-1";
	public static String ME = "CsvGenerator: " ;
	
	/**
	 * To set the CSV Reader Attributes
	 * 
	 * @param csvReader
	 * @return csvReader
	 * @throws BullsEyeException
	 */
    private CsvReader setCsvReaderAttributes(CsvReader csvReader) throws MartaException {
    	final String methodName = "CsvGenerator.setCsvReaderAttributes(CsvReader csvReader):: ";
    	BcwtsLogger.info("Inside: " + methodName);
    	try{
	        csvReader.setDelimiter(DELIMITER);
	        csvReader.setTrimWhitespace(false);
	        csvReader.setTextQualifier(TEXTQUALIFIER);        
	        csvReader.setUseTextQualifier(true);
	        csvReader.setUseComments(false);
	        csvReader.setEscapeMode(CsvReader.ESCAPE_MODE_DOUBLED);
	        csvReader.readHeaders();
    	}catch (Exception e) {
    		BcwtsLogger.error("Exception in: " + methodName, e);
			throw new MartaException("Exception in" + methodName);
    	}    	
		return csvReader;
    }
    
    /**
     * To get the CSV Reader
     * 
     * @param fileName
     * @return csvReader
     * @throws BullsEyeException
     */
    private CsvReader getCSVReader(String fileName) throws MartaException {
    	
    	final String methodName = "CsvGenerator.getCSVReader(String fileName):: ";
    	BcwtsLogger.info("Inside: " + methodName);
    	CsvReader csvReader = null;
    	try{
	    	csvReader = new CsvReader(fileName, DELIMITER,Charset.forName(CHARSET));
	    	setCsvReaderAttributes(csvReader);
    	} catch (Exception e) {
    		BcwtsLogger.error("Exception in: " + methodName, e);
			throw new MartaException("Exception in" + methodName);
		}	
		return csvReader;
    }
     /**
     * 
     * To write into csv file.
     * 
     * @param fileName
     * @return csvWriter
     * @throws MartaException
     */
    private CsvWriter getCSVWriter(String fileName) throws MartaException {
    	
    	final String methodName = "ProcessMTADeliveryLog.getCSVWriter(String fileName):: ";
    	BcwtsLogger.info("Inside: " + methodName);
    	CsvWriter csvWriter = null;
    	try{
    		csvWriter = new CsvWriter(fileName, DELIMITER,Charset.forName(CHARSET));
    	} catch (Exception e) {
    		BcwtsLogger.error("Exception in: " + methodName, e);
			throw new MartaException("Exception in" + methodName);
		}	
    	return csvWriter;
    }

    /**
     * To Generate CSV file.
     * 
     * @param fileNameTobeUploaded
     * @param tempFileName
     * @throws MartaException
     */
	public void generateCsvFile(String fileNameTobeUploaded, String tempFileName)
	throws MartaException {
	
		String MY_NAME = ME + "generateDownloadBPExcel: ";
		BcwtsLogger.info(MY_NAME + "entering into excel generator");
		CsvReader csvReader = null;
		CsvWriter csvWriter = null;
		String rawRecord = null;
		try{
		   csvReader = getCSVReader(fileNameTobeUploaded);
		   csvWriter = getCSVWriter(tempFileName);
		   if(csvReader != null && csvWriter != null) {
			   while(csvReader.readRecord()){	
				   rawRecord = csvReader.getRawRecord();
				   if(!Util.isBlankOrNull(rawRecord)) {
				   csvWriter.write(rawRecord);
				   csvWriter.flush();
				   }
			   }
		   }
		}catch (Exception e) {
			BcwtsLogger.error(MY_NAME + e.getMessage());
			throw new MartaException("Exception in " + MY_NAME + e);            
		}finally {
		  if(csvReader != null) {
			  csvReader.close();
		  }
		  if(csvWriter != null) {
			  csvWriter.close();
		  }
	    }
	}
}
