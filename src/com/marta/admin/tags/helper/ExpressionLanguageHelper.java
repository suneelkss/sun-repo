package com.marta.admin.tags.helper;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import javax.servlet.jsp.JspException;

public class ExpressionLanguageHelper 


{    


  public static Object evalObject (String attrName, String attrValue, Tag tagObject, PageContext pageContext) throws JspException


  {


    Object result   = null;


    


    if (attrValue != null)


      result = ExpressionEvaluatorManager.evaluate (attrName, attrValue, Object.class, tagObject, pageContext);





    return (result);


  }





  public static String evalString (String attrName, String attrValue, Tag tagObject, PageContext pageContext) throws JspException


  {


    String result   = null;


    


    if (attrValue != null)


      result = (String) ExpressionEvaluatorManager.evaluate (attrName, attrValue, String.class, tagObject, pageContext);





    return (result);


  }





  public static Integer evalInteger (String attrName, String attrValue, Tag tagObject, PageContext pageContext) throws JspException


  {


    Integer result   = null;


    


    if (attrValue != null)


      result = (Integer) ExpressionEvaluatorManager.evaluate (attrName, attrValue, Integer.class, tagObject, pageContext);





    return (result);


  }





  public static Long evalLong (String attrName, String attrValue, Tag tagObject, PageContext pageContext) throws JspException


  {


    Long result   = null;


    


    if (attrValue != null)


      result = (Long) ExpressionEvaluatorManager.evaluate (attrName, attrValue, Long.class, tagObject, pageContext);





    return (result);


  }




  public static Boolean evalBoolean (String attrName, String attrValue, Tag tagObject, PageContext pageContext) throws JspException


  {


    Boolean result   = null;


    


    if (attrValue != null)


      result = (Boolean) ExpressionEvaluatorManager.evaluate (attrName, attrValue, Boolean.class, tagObject, pageContext);





    return (result);


  }


}