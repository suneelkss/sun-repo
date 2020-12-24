package com.marta.admin.tags;
import java.util.Comparator;



public class DateValueComparator implements Comparator

{

  private int     index;

  private boolean inverse;

  

  public DateValueComparator (int index)

  {

    if (index >= 0)

      this.index   = index - 1;

    else

    {

      this.inverse = true;

      this.index   = -index - 1;

    }

  }



  public int compare (Object row1, Object row2)

  {

    Object tempValue1, tempValue2;
    String value1 = "";
    String value2 = "";

    int    result;



    tempValue1 = ((Row) row1).getCells () [index].getValue ();

    tempValue2 = ((Row) row2).getCells () [index].getValue ();
    if(null != tempValue1) {
    	value1 = tempValue1.toString();
    	if(value1.length()==10 )
    	  value1 = value1.substring(6)+"/"+value1.substring(0,2)+"/"+value1.substring(3,5);
    	else if(value1.length()> 10 )
      	  value1 = value1.substring(6,10)+"/"+value1.substring(0,2)+"/"+value1.substring(3,5)+value2.substring(10);
    }
    if(null != tempValue2) {
	    value2 = tempValue2.toString();
	    if(value2.length()== 10 )
	      value2 = value2.substring(6)+"/"+value2.substring(0,2)+"/"+value2.substring(3,5);
	    else if(value2.length()> 10 )
		      value2 = value2.substring(6,10)+"/"+value2.substring(0,2)+"/"+value2.substring(3,5)+value2.substring(10);
    }		

    if      (value1 == null)

      result = -1;

    else if (value2 == null)

      result = 1;

    else if ((value1 instanceof Comparable) && (value2 instanceof Comparable))

      result = ((Comparable) value1).compareTo (value2);

    else 

      result = value1.toString ().compareTo (value2.toString ());



    if (inverse) result = -result;

    

    return (result);

  }
 

}