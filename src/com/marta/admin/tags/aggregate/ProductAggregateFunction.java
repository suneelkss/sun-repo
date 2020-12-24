package com.marta.admin.tags.aggregate;
import java.math.BigDecimal;

import com.marta.admin.tags.aggregate.AggregateFunction;

import org.apache.commons.math.stat.univariate.summary.Product;


public class ProductAggregateFunction implements AggregateFunction

{

  private Product stat;

  

  public ProductAggregateFunction()

  {

    stat = new Product ();

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

