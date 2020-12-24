package com.marta.admin.tags.aggregate;
import java.math.BigDecimal;

import com.marta.admin.tags.aggregate.AggregateFunction;

import org.apache.commons.math.stat.univariate.summary.Sum;
public class SumAggregateFunction implements AggregateFunction

{

  private Sum stat;

  

  public SumAggregateFunction()

  {

    stat = new Sum ();

  }



  public void addValue (Object obj)

  {

    if ((obj != null) && (obj instanceof Number)) 

      stat.increment (((Number) obj).doubleValue ());

  }



  public Object totalValue ()

  {

    return (new Double (stat.getResult ()));

  }

}

