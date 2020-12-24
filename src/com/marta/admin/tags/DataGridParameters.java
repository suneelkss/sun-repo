package com.marta.admin.tags;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequest;







public class DataGridParameters 



{



  private static String PARAMETER_NAME_PREFIX      = "p_datagrid_";



  private static String PARAMETER_NAME_ORDER_SUFIX = "_order_index";



  private static String PARAMETER_NAME_PAGE_SUFIX  = "_page_index";







  /**



   * Method to get name of the servlet request parameter that stores the order index.



   * 



   * @param dataGridName The name defined in the "name" attribute in the data grid.



   * 



   * @return The servlet request parameter name of the order index.



   **/



  protected static String getParameterOrderIndexName (String dataGridName)



  {



    return (PARAMETER_NAME_PREFIX + dataGridName.toLowerCase () + PARAMETER_NAME_ORDER_SUFIX);



  }



  



  /**



   * Method to get name of the servlet request parameter that stores the page index.



   * 



   * @param dataGridName The name defined in the "name" attribute in the data grid.



   * 



   * @return The servlet request parameter name of the page index.



   **/



  protected static String getParameterPageIndexName (String dataGridName)



  {



    return (PARAMETER_NAME_PREFIX + dataGridName.toLowerCase () + PARAMETER_NAME_PAGE_SUFIX);



  }







  /**



   * Method to get the order index of a data grid. The order index defines the column number that is going to be ordered starting at 1. If the number is positive it means that the column should be ordered in ascending order. If the number is negative it means that the column should be ordered in descending order.



   * 



   * @param request The servlet request object.



   * @param dataGridName The name defined in the "name" attribute in the data grid.



   * 



   * @return The order index of the datagrid.



   **/



  public static int getDataGridOrderIndex (ServletRequest request, String dataGridName)



  {



    String parameterValue;



    int    orderIndex = 0;



    



    try



    {



      parameterValue = request.getParameter (getParameterOrderIndexName (dataGridName));



      if ((parameterValue != null) && (parameterValue.length () > 0))



        orderIndex = Integer.parseInt (parameterValue);



    }



    catch (NumberFormatException e)



    {



    }    







    return (orderIndex);



  }





  public static boolean isDateSort (ServletRequest request, String QueryStringName) {
    String parameterValue;
    boolean isDateSort = false;
    try
    {
      parameterValue = request.getParameter (QueryStringName);
      if ((parameterValue != null) && !parameterValue.equals("") &&(parameterValue.length () > 0)) {
        if(parameterValue.equalsIgnoreCase("true")){
        	isDateSort = true;
        }
      }  
    }catch (Exception e){
    }    
    return (isDateSort);
  }




  /**



   * Method to get the page index of a data grid. The page index defines the number of the first row displayed in the datagrid.



   * 



   * @param request The servlet request object.



   * @param dataGridName The name defined in the "name" attribute in the data grid.



   * 



   * @return The page index of the datagrid.



   **/



  public static long getDataGridPageIndex (ServletRequest request, String dataGridName)



  {



    String parameterValue;



    long   pageIndex = 0;



    



    try



    {



      parameterValue = request.getParameter (getParameterPageIndexName (dataGridName));



      if ((parameterValue != null) && (parameterValue.length () > 0))



        pageIndex = Long.parseLong (parameterValue);







      if (pageIndex < 0) pageIndex = 0;



    }



    catch (NumberFormatException e)



    {



    }    







    return (pageIndex);    



  }



}