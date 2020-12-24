package com.marta.admin.tags;

import java.io.Serializable;



public class Page implements Serializable

{

  private boolean current;

  private String url;

  private long startRow;

  private long endRow;

  private int index;

  
  public Page ()

  {

  }



  public boolean isCurrent ()

  {

    return current;

  }



  public void setCurrent (boolean newCurrent)

  {

    current = newCurrent;

  }



  public String getUrl()

  {

    return url;

  }



  public void setUrl(String newUrl)

  {

    url = newUrl;

  }



  public long getStartRow()

  {

    return startRow;

  }



  public void setStartRow(long newStartRow)

  {

    startRow = newStartRow;

  }



  public long getEndRow()

  {

    return endRow;

  }



  public void setEndRow(long newEndRow)

  {

    endRow = newEndRow;

  }



  public int getIndex()

  {

    return index;

  }



  public void setIndex(int newIndex)

  {

    index = newIndex;

  }








}