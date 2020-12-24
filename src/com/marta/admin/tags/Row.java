package com.marta.admin.tags;



public class Row 

{

  private String  style;

  private String  styleClass;

  private Cell [] cells;

  private String alternateStyle;

  private String alternateStyleClass;



  public Row ()

  {

  }



  public String getStyle ()

  {

    return style;

  }



  public void setStyle (String newStyle)

  {

    style = newStyle;

  }



  public String getStyleClass ()

  {

    return styleClass;

  }



  public void setStyleClass (String newStyleClass)

  {

    styleClass = newStyleClass;

  }



  public Cell [] getCells ()

  {

    return cells;

  }



  public void setCells (Cell [] newCells)

  {

    cells = newCells;

  }



  public String getAlternateStyle()

  {

    return alternateStyle;

  }



  public void setAlternateStyle(String newAlternateStyle)

  {

    alternateStyle = newAlternateStyle;

  }



  public String getAlternateStyleClass()

  {

    return alternateStyleClass;

  }



  public void setAlternateStyleClass(String newAlternateStyleClass)

  {

    alternateStyleClass = newAlternateStyleClass;

  }

}