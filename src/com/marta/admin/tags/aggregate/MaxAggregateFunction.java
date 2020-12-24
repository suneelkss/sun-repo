package com.marta.admin.tags.aggregate;
import com.marta.admin.tags.aggregate.AggregateFunction;

public class MaxAggregateFunction implements AggregateFunction

{

  private Comparable max;

  

  public MaxAggregateFunction()

  {

    max = null;

  }



  public void addValue (Object obj)

  {

    if ((obj != null) && (obj instanceof Comparable) && (((max == null) || (max.compareTo (obj) <= 0))))

      max = (Comparable) obj;

  }



  public Object totalValue ()

  {

    return (max);

  }

}

