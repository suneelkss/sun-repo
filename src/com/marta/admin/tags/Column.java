package com.marta.admin.tags;



import java.util.ArrayList;

import java.util.List;



/**

 * Class that stores a column definition of a data grid.

 *

 * @see DataGridTag

 */



public class Column 

{

  private ArrayList     aggregates = new ArrayList ();

  private String        itemStyleClass;

  private String        headerStyleClass;

  private String        width;

  private String        itemValue;

  private String        headerValue;

  private String        footerStyleClass;

  private String        footerValue;

  private String        itemHyperLinkValue;

  private String        headerHorizontalAlign;

  private String        headerVerticalAlign;

  private String        footerHorizontalAlign;

  private String        footerVerticalAlign;

  private String        itemHorizontalAlign;

  private String        itemVerticalAlign;

  private String        itemPattern;

  private String        totalPattern;

  private String        itemHyperLinkTarget;

  private boolean       order;

  private String        footerStyle;

  private String        headerStyle;

  private String        itemStyle;

  private String 		image;
  
  private String		location;

  private String		imgtarget; 
  
  private String 		headerHyperLink;
  
  private String 		headerHyperLinkTarget;
  
  private String		isShow;
  
  private String 		confirm;
  
  private String		isToolTip;
  
  private String 		onClick;
  
  private String 		isMenuVar;
  
  private String 		isDate;
  
  private String 		isCancelRetry;
  
  private String 		isShowMultipleIcon;
  
  private boolean isInteger;
  
  private boolean isDouble;
  
  //private String isShowHyperLink;

  
  public Column()

  {

  }



  public String getItemStyleClass()

  {

    return itemStyleClass;

  }



  public void setItemStyleClass(String newItemStyleClass)

  {

    itemStyleClass = newItemStyleClass;

  }



  public String getHeaderStyleClass()

  {

    return headerStyleClass;

  }



  public void setHeaderStyleClass(String newHeaderStyleClass)

  {

    headerStyleClass = newHeaderStyleClass;

  }



  public String getWidth()

  {

    return width;

  }



  public void setWidth(String newWidth)

  {

    width = newWidth;

  }



  public String getItemValue()

  {

    return itemValue;

  }



  public void setItemValue(String newItemValue)

  {

    itemValue = newItemValue;

  }





  public String getHeaderValue()

  {

    return headerValue;

  }



  public void setHeaderValue(String newHeaderValue)

  {

    headerValue = newHeaderValue;

  }



  public String getFooterStyleClass()

  {

    return footerStyleClass;

  }



  public void setFooterStyleClass(String newFooterStyleClass)

  {

    footerStyleClass = newFooterStyleClass;

  }



  public String getFooterValue()

  {

    return footerValue;

  }



  public void setFooterValue(String newFooterValue)

  {

    footerValue = newFooterValue;

  }



  public String getItemHyperLinkValue()

  {

    return itemHyperLinkValue;

  }



  public void setItemHyperLinkValue(String newItemHyperLinkValue)

  {

    itemHyperLinkValue = newItemHyperLinkValue;

  }



  public String getHeaderHorizontalAlign()

  {

    return headerHorizontalAlign;

  }



  public void setHeaderHorizontalAlign(String newHeaderHorizontalAlign)

  {

    headerHorizontalAlign = newHeaderHorizontalAlign;

  }



  public String getHeaderVerticalAlign()

  {

    return headerVerticalAlign;

  }



  public void setHeaderVerticalAlign(String newHeaderVerticalAlign)

  {

    headerVerticalAlign = newHeaderVerticalAlign;

  }



  public String getFooterHorizontalAlign()

  {

    return footerHorizontalAlign;

  }



  public void setFooterHorizontalAlign(String newFooterHorizontalAlign)

  {

    footerHorizontalAlign = newFooterHorizontalAlign;

  }



  public String getFooterVerticalAlign()

  {

    return footerVerticalAlign;

  }



  public void setFooterVerticalAlign(String newFooterVerticalAlign)

  {

    footerVerticalAlign = newFooterVerticalAlign;

  }



  public String getItemHorizontalAlign()

  {

    return itemHorizontalAlign;

  }



  public void setItemHorizontalAlign(String newItemHorizontalAlign)

  {

    itemHorizontalAlign = newItemHorizontalAlign;

  }



  public String getItemVerticalAlign()

  {

    return itemVerticalAlign;

  }



  public void setItemVerticalAlign(String newItemVerticalAlign)

  {

    itemVerticalAlign = newItemVerticalAlign;

  }







  public String getItemPattern()

  {

    return itemPattern;

  }



  public void setItemPattern(String newItemPattern)

  {

    itemPattern = newItemPattern;

  }



  public String getItemHyperLinkTarget()

  {

    return itemHyperLinkTarget;

  }



  public void setItemHyperLinkTarget(String newItemHyperLinkTarget)

  {

    itemHyperLinkTarget = newItemHyperLinkTarget;

  }



  public boolean isOrder()

  {

    return order;

  }



  public void setOrder(boolean newOrder)

  {

    order = newOrder;

  }



  public ArrayList getAggregates ()

  {

    return aggregates;

  }



  public void setAggregates (ArrayList newAggregates)

  {

    aggregates = newAggregates;

  }



  public String getFooterStyle()

  {

    return footerStyle;

  }



  public void setFooterStyle(String newFooterStyle)

  {

    footerStyle = newFooterStyle;

  }



  public String getHeaderStyle()

  {

    return headerStyle;

  }



  public void setHeaderStyle(String newHeaderStyle)

  {

    headerStyle = newHeaderStyle;

  }



  public String getItemStyle()

  {

    return itemStyle;

  }



  public void setItemStyle(String newItemStyle)

  {

    itemStyle = newItemStyle;

  }



public String getImage() {
	return image;
}



public void setImage(String image) {
	this.image = image;
}



public String getLocation() {
	return location;
}



public void setLocation(String location) {
	this.location = location;
}



public String getImgtarget() {
	return imgtarget;
}



public void setImgtarget(String imgtarget) {
	this.imgtarget = imgtarget;
}



public String getHeaderHyperLink() {
	return headerHyperLink;
}



public void setHeaderHyperLink(String headerHyperLink) {
	this.headerHyperLink = headerHyperLink;
}



public String getHeaderHyperLinkTarget() {
	return headerHyperLinkTarget;
}



public void setHeaderHyperLinkTarget(String headerHyperLinkTarget) {
	this.headerHyperLinkTarget = headerHyperLinkTarget;
}



public String getIsShow() {
	return isShow;
}



public void setIsShow(String isShow) {
	this.isShow = isShow;
}



public String getConfirm() {
	return confirm;
}



public void setConfirm(String confirm) {
	this.confirm = confirm;
}



public String getIsToolTip() {
	return isToolTip;
}



public void setIsToolTip(String isToolTip) {
	this.isToolTip = isToolTip;
}



public String getOnClick() {
	return onClick;
}



public void setOnClick(String onClick) {
	this.onClick = onClick;
}



public String getIsMenuVar() {
	return isMenuVar;
}



public void setIsMenuVar(String isMenuVar) {
	this.isMenuVar = isMenuVar;
}



public String getIsDate() {
	return isDate;
}



public void setIsDate(String isDate) {
	this.isDate = isDate;
}



public String getIsCancelRetry() {
	return isCancelRetry;
}



public void setIsCancelRetry(String isCancelRetry) {
	this.isCancelRetry = isCancelRetry;
}



public boolean isInteger() {
	return isInteger;
}



public void setInteger(boolean isInteger) {
	this.isInteger = isInteger;
}



public String getIsShowMultipleIcon() {
	return isShowMultipleIcon;
}



public void setIsShowMultipleIcon(String isShowMultipleIcon) {
	this.isShowMultipleIcon = isShowMultipleIcon;
}



public boolean isDouble() {
	return isDouble;
}



public void setDouble(boolean isDouble) {
	this.isDouble = isDouble;
}



/*public String getIsShowHyperLink() {
	return isShowHyperLink;
}



public void setIsShowHyperLink(String isShowHyperLink) {
	this.isShowHyperLink = isShowHyperLink;
}*/









}

