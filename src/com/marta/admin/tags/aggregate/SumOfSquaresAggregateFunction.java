package com.marta.admin.tags.aggregate;
import java.math.BigDecimal;

import com.marta.admin.tags.aggregate.AggregateFunction;

import org.apache.commons.math.stat.univariate.summary.SumOfSquares;
public class SumOfSquaresAggregateFunction implements AggregateFunction

{

  private SumOfSquares stat;

  

  public SumOfSquaresAggregateFunction()

  {

    stat = new SumOfSquares ();

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

