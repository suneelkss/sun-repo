package com.marta.admin.tags;



import com.marta.admin.tags.aggregate.AggregateFunction;



public class Aggregate 

{

  protected AggregateFunction function;

  protected String pattern;

  protected String var;



  public Aggregate ()

  {

  }



  public AggregateFunction getFunction()

  {

    return function;

  }



  public void setFunction (AggregateFunction newFunction)

  {

    function = newFunction;

  }



  public String getPattern()

  {

    return pattern;

  }



  public void setPattern(String newPattern)

  {

    pattern = newPattern;

  }



  public String getVar()

  {

    return var;

  }



  public void setVar(String newVar)

  {

    var = newVar;

  }

}