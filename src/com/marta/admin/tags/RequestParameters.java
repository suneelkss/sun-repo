package com.marta.admin.tags;



import javax.servlet.ServletRequest;



/**

 * Class to access data grid request parameters.

 */



public class RequestParameters 

{

  private static String PARAMETER_NAME_PREFIX      = "p_datagrid_";

  private static String PARAMETER_NAME_ORDER_SUFIX = "_order_index";

  private static String PARAMETER_NAME_PAGE_SUFIX  = "_page_index";



  public static String getParameterOrderIndexName (String dataGridName)

  {

    return (PARAMETER_NAME_PREFIX + dataGridName + PARAMETER_NAME_ORDER_SUFIX);

  }

  

  public static String getParameterPageIndexName (String dataGridName)

  {

    return (PARAMETER_NAME_PREFIX + dataGridName + PARAMETER_NAME_PAGE_SUFIX);

  }



  public static int getParameterOrderIndexValue (ServletRequest request, String parameterName)

  {

    String parameterValue;

    int    orderIndex = 0;

    

    try

    {

      parameterValue = request.getParameter (parameterName);

      if ((parameterValue != null) && (parameterValue.length () > 0))

        orderIndex = Integer.parseInt (parameterValue);

    }

    catch (NumberFormatException e)

    {

    }    



    return (orderIndex);

  }



  public static int getParameterPageIndexValue (ServletRequest request, String parameterName)

  {

    String parameterValue;

    int    pageIndex = 0;

    

    try

    {

      parameterValue = request.getParameter (parameterName);

      if ((parameterValue != null) && (parameterValue.length () > 0))

        pageIndex = Integer.parseInt (parameterValue);



      if (pageIndex < 0) pageIndex = 0;

    }

    catch (NumberFormatException e)

    {

    }    



    return (pageIndex);    

  }

}

