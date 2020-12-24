package com.marta.admin.utils;

import java.io.File;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * This class is used to send mail.
 * @author admin
 *
 */

public class SendMail {
	
	final static String ME = "SendMail :";
	
	/**
	 * To send mail 
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean sendMail(String from,String to,String content,String smtpPath,String subject,String filePath,
			String port,String protocol) {
		final  String MY_NAME = ME + " sendMail():" ;
		boolean result  = false;
		String msgContent = "";
		Provider javaMailApiProvider = null;
		try{
			BcwtsLogger.debug("from-->"+from+"<--");
			BcwtsLogger.debug("to-->"+to+"<--");
			BcwtsLogger.debug("smtpPath-->"+smtpPath+"<--");
			
			java.util.Properties props = new java.util.Properties();
			
		    props.put("mail.smtp.host", smtpPath);
		    props.put("mail.port",port);
		    props.put("mail.transport.protocol", protocol);
		    props.getProperty("recipient");
		    
		    Session session = Session.getDefaultInstance(props, null);
		    
		    //Find out which implementations are available
		    
		    Provider[] providers = session.getProviders();

		    //Pick a provider
		   
		    for(int i=0;i<providers.length;i++){
		    	BcwtsLogger.debug("java classpath mail API name "+providers[i].getClassName());
		    	if(!Util.isBlankOrNull(providers[i].getClassName())){
		    		if(providers[i].getClassName().equals("com.sun.mail.smtp.SMTPTransport")){
		    			javaMailApiProvider = providers[i];
		    			 //Then, set a provider
		    		    BcwtsLogger.debug("javaMailApiProvider.getClassName()"+javaMailApiProvider.getClassName());
		    		    BcwtsLogger.debug("javaMailApiProvider.getVendor()"+javaMailApiProvider.getVendor());
		    		    session.setProvider(javaMailApiProvider);	
		    		}
		    	}
		    }	    		    
		 	    
		    Message msg = new MimeMessage(session);
		    
		    msg.setFrom(new InternetAddress(from));
		    
		    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		    
		    //msg.setText(content);
		    BcwtsLogger.debug("content-->"+content+"<--");
		    BcwtsLogger.debug("subject-->"+subject+"<--");
		   	    
		    if (null != content) {
		    
		    	msg.setDataHandler(new DataHandler(new ByteArrayDataSource(content, "text/html")));
		    
		    }
		    
		    msg.setHeader("X-Mailer", "text/html");
		    
		    MimeBodyPart messageBodyPart = new MimeBodyPart();
		    
		    messageBodyPart.setText(content);
		    
		    Multipart multipart = new MimeMultipart();
		    
		    multipart.addBodyPart(messageBodyPart);	
		    
		    //attach the file to the message
		    if(!Util.isBlankOrNull(filePath)) {
		      
			    File file = new File(filePath); 
				  
			    if(file.exists()&& file.isFile()) {
			    	
					  MimeBodyPart mbp2 = new MimeBodyPart();
					  FileDataSource fds = new FileDataSource(filePath);
					  mbp2.setDataHandler(new DataHandler(fds));
					  mbp2.setFileName(fds.getName()); 
					  multipart.addBodyPart(mbp2); 
					  msg.setContent(multipart);
			    }
		    }
		   
		 	//msg.setSubject(Constants.FORGERTPASSWORD_MAIL_SUBJECT);
		    
		    msg.setSubject(subject);		  
		    BcwtsLogger.debug("sending Mail");
	    	Transport.send(msg);
	    	BcwtsLogger.debug("Mail send success");	    	
			result = true;

			//Reset provider to default 
			//javaMailApiProvider = providers[0];
			//session.setProvider(javaMailApiProvider);
			session = Session.getDefaultInstance(props, null);
			
		}catch(Exception ex){
			ex.printStackTrace();
			BcwtsLogger.error(MY_NAME + ex.getMessage());
		}
		return result;
	}
	
	/**
	 * To generate random password
	 *  @return String
	 * 
	 */
	public static String generatePassword() {
		final  String MY_NAME = ME + " generatePassword():" ;
		String password = "";
		try{
			BcwtsLogger.info(MY_NAME);
			Random rand = new Random();
		    String str=new  String("aAbBcCdDeEfFgGhHiIjJkKlLmMnNOopPqQrRsStTuUvVwWxXyYzZbghfdxsertyh");
		    StringBuffer sb=new StringBuffer();     
		    int index=rand.nextInt();
		    String textValue=Integer.toString(index);
		    int start=0;
		    for(int j=1;j<4;j++){
		    	start=rand.nextInt(62);
		        sb.append(str.charAt(start));
		    }
		    password=sb.toString()+textValue.substring(1, 4);
			
		}catch(Exception ex){
			//AdministratorLog.error(MY_NAME + ex.getMessage());
		}
		return password;
	}
	
	/**
     * Method to replace String 
     * @param source
     * @param pattern
     * @param replace
     * @return
     */
    public static String replace(String source, String pattern, String replace)
    {
        if (source!=null)
        {
        final int len = pattern.length();
        StringBuffer sb = new StringBuffer();
        int found = -1;
        int start = 0;

        while( (found = source.indexOf(pattern, start) ) != -1) {
            sb.append(source.substring(start, found));
            sb.append(replace);
            start = found + len;
        }

        sb.append(source.substring(start));

        return sb.toString();
        }
        else return "";
    }
    
}
