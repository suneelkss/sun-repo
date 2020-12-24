package com.marta.admin.tags.aggregate;
import com.marta.admin.tags.aggregate.AggregateFunction;

public class CountAggregateFunction implements AggregateFunction

{

  private long count;

  

  public CountAggregateFunction()

  {

    count = 0;

  }



  public void addValue (Object obj)

  {

    if (obj != null) count++;

  }



  public Object totalValue ()

  {

    return (new Long (count));

  }

}

