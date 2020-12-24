package com.marta.admin.tags.helper;
import java.util.Arrays;


import java.util.Enumeration;


import java.util.List;


import java.util.Locale;


import java.text.DateFormatSymbols;


import java.text.DecimalFormatSymbols;


import javax.servlet.ServletRequest;


import javax.servlet.http.HttpServletRequest;





/**


 * Helper class to build request paths.


 *


 * @author N&eacute;stor Bosc&aacute;n


 */





public class RequestHelper 


{


  public static String getRequestURLWithNewParameters (HttpServletRequest request, String requestPath, String [] newParameterNames, String [] newParameterValues)


  {


    Enumeration  parameterNames;


    List         newParameterNamesList;


    StringBuffer url;
    String       parameterName;


    String []    parameterValues;


    int          index, length;


    boolean      parameters = false;





    if (requestPath != null)


    {


      url = new StringBuffer (requestPath);    


      if (requestPath.indexOf ('?') >= 0) parameters = true;


    }


    else


    {


      url                   = request.getRequestURL ();
      
      String path = request.getRequestURI();
      String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

      


      newParameterNamesList = Arrays.asList (newParameterNames);


      parameterNames        = request.getParameterNames ();


      while (parameterNames.hasMoreElements ())


      {


        parameterName = (String) parameterNames.nextElement ();


        if (!newParameterNamesList.contains (parameterName))


        {


          parameterValues = request.getParameterValues (parameterName);


          length          = parameterValues.length;


          for (index = 0; index < length; index++)


          {


            if (!parameters)


            {


              url.append ('?');


              parameters = true;


            }


            else


              url.append ('&');


            url.append (parameterName);


            url.append ('=');          


            url.append (parameterValues [index]);


          }


        }


      }


    }





    length = newParameterNames.length;


    for (index = 0; index < length; index++)


    {


      if (!parameters)


      {


        url.append ('?');


        parameters = true;


      }


      else


        url.append ('&');


      url.append (newParameterNames [index]);


      url.append ('=');          


      url.append (newParameterValues [index]);


    }


      


    return (url.toString ());


  }


}