package com.marta.admin.hibernate;

import java.util.Date;


/**
 * Bcwtmanualalert generated by MyEclipse - Hibernate Tools
 */

public class BcwtManualAlert  implements java.io.Serializable {


    // Fields    

     private Long manualalertid;
     private BcwtPatron bcwtpatron;
     private Date releaseDate;
     private Date discontinueDate;
     private String alertTitle;
     private String alertMessage;


    // Constructors

   
    // Property accessors

    public Long getManualalertid() {
        return this.manualalertid;
    }
    
    public void setManualalertid(Long manualalertid) {
        this.manualalertid = manualalertid;
    }

   

    public BcwtPatron getBcwtpatron() {
		return bcwtpatron;
	}

	public void setBcwtpatron(BcwtPatron bcwtpatron) {
		this.bcwtpatron = bcwtpatron;
	}

	public Date getReleaseDate() {
        return this.releaseDate;
    }
    
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getDiscontinueDate() {
        return this.discontinueDate;
    }
    
    public void setDiscontinueDate(Date discontinueDate) {
        this.discontinueDate = discontinueDate;
    }

    public String getAlertTitle() {
        return this.alertTitle;
    }
    
    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertMessage() {
        return this.alertMessage;
    }
    
    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
   








}