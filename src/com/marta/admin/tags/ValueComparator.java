package com.marta.admin.tags;
import java.util.Comparator;



public class ValueComparator implements Comparator

{

  private int     index;

  private boolean inverse;

  private boolean isInteger;
  
  private boolean isDouble;
  
  private int maxSize;

  public ValueComparator(int index, boolean isInteger,boolean isDouble)
  {
      maxSize = 10;
      if(index >= 0)
      {
          this.index = index - 1;
          this.isInteger = isInteger;
          this.isDouble = isDouble;
      } else
      {
          inverse = true;
          this.index = -index - 1;
          this.isInteger = isInteger;
          this.isDouble = isDouble;
      }
  }




  public int compare(Object row1, Object row2)
  {
      int result = 0;
      Object value1;
      Object value2;
      if(isDouble){
    	  value1 = Double.valueOf(((Row)row1).getCells()[index].getValue().toString());
          value2 = Double.valueOf(((Row)row2).getCells()[index].getValue().toString());
      }
      else if(!isInteger)
      {
          value1 = ((Row)row1).getCells()[index].getValue().toString();
          value2 = ((Row)row2).getCells()[index].getValue().toString();
      } else
      {
          value1 = Integer.valueOf(((Row)row1).getCells()[index].getValue().toString());
          value2 = Integer.valueOf(((Row)row2).getCells()[index].getValue().toString());
      }
      if(value1 == null)
          result = -1;
      else
      if(value2 == null)
          result = 1;
      else
      if((value1 instanceof Comparable) && (value2 instanceof Comparable))
      {
          result = 0;
          result = ((Comparable)value1).compareTo(value2);
         
      } else
      {
    	  
          result = value1.toString().compareTo(value2.toString());
          
      }
      if(inverse)
          result *= -1;
      return result;
  }


 

}