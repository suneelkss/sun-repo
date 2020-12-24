package com.marta.admin.tags.aggregate;
import com.marta.admin.tags.aggregate.AggregateFunction;
public class MinAggregateFunction implements AggregateFunction

{

  private Comparable min;

  

  public MinAggregateFunction()

  {

    min = null;

  }



  public void addValue (Object obj)

  {

    if ((obj != null) && (obj instanceof Comparable) && (((min == null) || (min.compareTo (obj) >= 0))))

      min = (Comparable) obj;

  }



  public Object totalValue ()

  {

    return (min);

  }

}

