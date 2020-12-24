package com.marta.admin.tags.aggregate;
import java.util.Iterator;

import java.util.ArrayList;

import java.math.BigDecimal;

import com.marta.admin.tags.aggregate.AggregateFunction;

import org.apache.commons.math.stat.univariate.moment.StandardDeviation;

public class StdDevAggregateFunction implements AggregateFunction

{

  private StandardDeviation stat;

  

  public StdDevAggregateFunction()

  {

    stat = new StandardDeviation ();

  }



  public void addValue (Object obj)

  {

    if ((obj != null) && (obj instanceof Number)) stat.increment (((Number) obj).doubleValue ());

  }



  public Object totalValue ()

  {

    return (new Double (stat.getResult ()));

  }

}

