package com.marta.admin.tags;
import java.util.Date;
import java.util.List;

import java.util.Calendar;

import java.util.Iterator;

import java.util.Collection;

import java.util.Map;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Arrays;

import java.text.Format;

import java.text.SimpleDateFormat;

import java.text.DecimalFormat;

import java.io.PrintWriter;

import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.jsp.tagext.*;

import javax.servlet.jsp.*;

import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;





import com.marta.admin.tags.aggregate.AggregateFunction;
import com.marta.admin.tags.helper.ExpressionLanguageHelper;
import com.marta.admin.tags.helper.RequestHelper;
import com.marta.admin.tags.helper.XmlHelper;
import com.marta.admin.tags.resource.Resource;
import com.marta.admin.utils.Constants;



import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;



/**

 * <p>JSP Tag <b>dataGrid</b>, used to create an HTML data grid based on a Collection.</p>

 * <p>JSP Tag Lib Descriptor</p>

 * <p><pre>

 * &lt;name&gt;dataGrid&lt;/name&gt;

 * &lt;tagclass&gt;org.apache.taglibs.datagrid.DataGridTag&lt;/tagclass&gt;

 * &lt;bodycontent&gt;tagdependent&lt;/bodycontent&gt;

 * &lt;display-name&gt;Data Grid Tag&lt;/display-name&gt;

 * &lt;description&gt;Data Grid Tag&lt;/description&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;name&lt;/name&gt;

 *     &lt;required&gt;true&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;items&lt;/name&gt;

 *     &lt;required&gt;true&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;var&lt;/name&gt;

 *     &lt;required&gt;true&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;scroll&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;styleClass&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;width&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;height&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;cellPadding&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;cellSpacing&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 *   &lt;attribute&gt;

 *     &lt;name&gt;requestPath&lt;/name&gt;

 *     &lt;required&gt;false&lt;/required&gt;

 *     &lt;rtexprvalue&gt;false&lt;/rtexprvalue&gt;

 *   &lt;/attribute&gt;

 * </pre></p>

 *

 */



public class DataGridTag extends BodyTagSupport

{

  private static final int    SCROLL_BAR_WIDTH = 20;



  private boolean     scroll;

  private String      itemVar;

  private Iterator    items;

  private Object      item;

  private long        itemsCount;

  private long        itemsSize;

  private ArrayList   rows;

  private ArrayList   columns;

  private int         columnsSize;

  private String      headerStyle;

  private String      headerStyleClass;

  private String      footerStyle;

  private String      footerStyleClass;

  private String      rowsStyle;

  private String      rowsStyleClass;

  private String      rowsHiliteStyleClass;

  private String      alternateRowsStyle;

  private String      alternateRowsStyleClass;

  private String      alternateRowsHiliteStyleClass;

  private String      height;

  private String      width;

  private boolean     headerShow;

  private boolean     footerShow;

  private String      cellPadding;

  private String      cellSpacing;

  private String      frame;

  private String      rules;

  private String      style;

  private String      styleClass;

  private String      name;

  private String      parameterNameOrderIndex;

  private String      parameterNamePageIndex;

  private String      nextUrlVar;

  private String      previousUrlVar;

  private String      pagesVar;

  private int         pageSize;

  private boolean     customPaging;

  private int         orderIndex;

  private long        pageIndex;

  private String      requestPath;




  private String      imgOrderAscending;

  private String      imgOrderDescending;

  private int         defaultOrderIndex;

  private boolean     initiated;

  private int headerLength;

  private long        pageIndexForSort;
  
  private  boolean isDateSort = false; 
  
  private boolean isInteger = false;
  
  private boolean isDouble = false;
  
  //Default number of pagination links to be displayed
  private int noOfPaginationLinks = 10;

  Page page = new Page();


  /**

   * Method called at start of the data grid tag.

   * @return EVAL_BODY_BUFFERED

   */

  public int doStartTag() throws JspException

  {

    return EVAL_BODY_BUFFERED;

  }



  /**

   * Method called after evaluating the content of the data grid tag. The method will parse the XML content of the tag and draw the data grid.

   * @return SKIP_BODY

   */

  public int doAfterBody () throws JspException

  {

    BodyContent bodyContent;



    try

    {

      bodyContent = getBodyContent();



      parseBodyContent ("<ui>" + bodyContent.getString () + "</ui>");


      parameterNameOrderIndex = DataGridParameters.getParameterOrderIndexName (name);

      parameterNamePageIndex  = DataGridParameters.getParameterPageIndexName  (name);

      orderIndex              = DataGridParameters.getDataGridOrderIndex      (pageContext.getRequest (), name);
      
      isDateSort			  =	DataGridParameters.isDateSort      (pageContext.getRequest (), "isDateSort");
      
      isInteger = DataGridParameters.isDateSort(pageContext.getRequest(), "isInteger");
      
      isDouble = DataGridParameters.isDateSort(pageContext.getRequest(), "isDouble");
      
      if (orderIndex == 0) orderIndex = defaultOrderIndex;

      pageIndex               = DataGridParameters.getDataGridPageIndex       (pageContext.getRequest (), name);



	      calculateValues  ();

	      if (!customPaging) orderValues();

	      generateDataGrid (bodyContent.getEnclosingWriter ());

	      setPagingVariables ();
      }



    catch (Throwable e)

    {

      e.printStackTrace ();

      throw (new JspException (e.getMessage ()));

    }



    return SKIP_BODY;

  }



  /**

   * Method that parses the content of the data grid tag.

   * @param String content XML content of the data grid tag
   *
   * and separated as node and the values are set in the column and cell

   */

  private void parseBodyContent (String content) throws Exception

  {

    Element   element;

    NodeList  list1, list2, list3;

    Node      node;

    Column    column;

    Aggregate function;

    String    value;

    int       index1, index2, index3, length1, length2, length3;

    Long      valueLong;

    Integer   valueInteger;

    Boolean   valueBoolean;



    columns                       = new ArrayList ();

    headerStyle                   = null;

    headerStyleClass              = null;

    footerStyle                   = null;

    footerStyleClass              = null;

    rowsStyle                     = null;

    rowsStyleClass                = null;

    rowsHiliteStyleClass          = null;

    alternateRowsStyle            = null;

    alternateRowsStyleClass       = null;

    alternateRowsHiliteStyleClass = null;

    headerShow                    = true;

    footerShow                    = false;

    itemsCount                    = -1;

    itemsSize                     = -1;

    pageSize                      = 0;

    customPaging                  = false;

    nextUrlVar                    = null;

    previousUrlVar                = null;

    imgOrderAscending             = null;

    imgOrderDescending            = null;

    defaultOrderIndex             = 0;



    element = DocumentBuilderFactory.newInstance ().newDocumentBuilder ().parse (new InputSource (new StringReader (content))).getDocumentElement ();

    list1   = element.getChildNodes ();

    length1 = list1.getLength ();

    for (index1 = 0; index1 < length1; index1++)

    {

      node = list1.item (index1);

      if (node.getNodeType () == Node.ELEMENT_NODE)

      {

        element = (Element) node;



        if      (element.getNodeName ().equals ("header"))

        {

          if (element.hasAttribute ("style"))      headerStyle      = ExpressionLanguageHelper.evalString ("style",      element.getAttribute ("style"),      this, pageContext);

          if (element.hasAttribute ("styleClass")) headerStyleClass = ExpressionLanguageHelper.evalString ("styleClass", element.getAttribute ("styleClass"), this, pageContext);

          if (element.hasAttribute ("show"))

          {

            valueBoolean = ExpressionLanguageHelper.evalBoolean ("show", element.getAttribute ("show"), this, pageContext);

            headerShow   = (valueBoolean != null) && (valueBoolean.booleanValue ());

          }

        }

        else if (element.getNodeName ().equals ("footer"))

        {

          if (element.hasAttribute ("style"))      footerStyle      = ExpressionLanguageHelper.evalString ("style",      element.getAttribute ("style"),      this, pageContext);

          if (element.hasAttribute ("styleClass")) footerStyleClass = ExpressionLanguageHelper.evalString ("styleClass", element.getAttribute ("styleClass"), this, pageContext);

          if (element.hasAttribute ("show"))

          {

            valueBoolean = ExpressionLanguageHelper.evalBoolean ("show", element.getAttribute ("show"), this, pageContext);

            footerShow   = (valueBoolean != null) && (valueBoolean.booleanValue ());

          }

        }

        else if (element.getNodeName ().equals ("rows"))

        {

          if (element.hasAttribute ("style"))            rowsStyle            = element.getAttribute ("style");

          if (element.hasAttribute ("styleClass"))       rowsStyleClass       = element.getAttribute ("styleClass");

          if (element.hasAttribute ("hiliteStyleClass")) rowsHiliteStyleClass = ExpressionLanguageHelper.evalString ("hiliteStyleClass", element.getAttribute ("hiliteStyleClass"), this, pageContext);

        }

        else if (element.getNodeName ().equals ("alternateRows"))

        {

          if (element.hasAttribute ("style"))            alternateRowsStyle            = element.getAttribute ("style");

          if (element.hasAttribute ("styleClass"))       alternateRowsStyleClass       = element.getAttribute ("styleClass");

          if (element.hasAttribute ("hiliteStyleClass")) alternateRowsHiliteStyleClass = ExpressionLanguageHelper.evalString ("hiliteStyleClass", element.getAttribute ("hiliteStyleClass"), this, pageContext);

        }

        else if (element.getNodeName ().equals ("paging"))

        {

          if (element.hasAttribute ("custom"))

          {

            valueBoolean = ExpressionLanguageHelper.evalBoolean ("custom", element.getAttribute ("custom"), this, pageContext);

            customPaging = (valueBoolean != null) && (valueBoolean.booleanValue ());

          }

          if (element.hasAttribute ("nextUrlVar"))     nextUrlVar      = ExpressionLanguageHelper.evalString ("nextUrlVar",     element.getAttribute ("nextUrlVar"),     this, pageContext);

          if (element.hasAttribute ("previousUrlVar")) previousUrlVar  = ExpressionLanguageHelper.evalString ("previousUrlVar", element.getAttribute ("previousUrlVar"), this, pageContext);

          if (element.hasAttribute ("pagesVar"))       pagesVar        = ExpressionLanguageHelper.evalString ("pagesVar",       element.getAttribute ("pagesVar"),       this, pageContext);

          if (element.hasAttribute ("size"))

          {

            valueInteger = ExpressionLanguageHelper.evalInteger ("size", element.getAttribute ("size"), this, pageContext);

            if (valueInteger != null) pageSize = valueInteger.intValue ();


          }

         if (element.hasAttribute ("count"))

          {

            valueLong = ExpressionLanguageHelper.evalLong ("count", element.getAttribute ("count"), this, pageContext);

            if (valueLong != null) itemsCount = valueLong.longValue ();

          }

        // itemsCount = 3;
        }

        else if (element.getNodeName().equals("order"))

        {

          if (element.hasAttribute ("defaultColumn"))

          {

            valueInteger = ExpressionLanguageHelper.evalInteger ("defaultColumn", element.getAttribute ("defaultColumn"), this, pageContext);

            if (valueInteger != null) defaultOrderIndex = valueInteger.intValue ();

          }

          if (element.hasAttribute ("imgAsc"))  imgOrderAscending  = ExpressionLanguageHelper.evalString  ("imgAsc",  element.getAttribute ("imgAsc"),  this, pageContext);

          if (element.hasAttribute ("imgDesc")) imgOrderDescending = ExpressionLanguageHelper.evalString  ("imgDesc", element.getAttribute ("imgDesc"), this, pageContext);

        }

        else if (element.getNodeName ().equals ("columns"))

        {

          list2   = element.getElementsByTagName ("column");

          length2 = list2.getLength ();

          for (index2 = 0; index2 < length2; index2++)

          {

            element = (Element) list2.item (index2);

            column  = new Column ();

            columns.add (column);



            if (element.hasAttribute ("width")) column.setWidth (ExpressionLanguageHelper.evalString ("width", element.getAttribute ("width"), this, pageContext));

            if (element.hasAttribute ("order"))

            {

              valueBoolean = ExpressionLanguageHelper.evalBoolean ("order", element.getAttribute ("order"), this, pageContext);

              column.setOrder ((valueBoolean != null) && (valueBoolean.booleanValue ()));

            }
            if(element.hasAttribute("isinteger"))
            {
                Boolean valueisInteger = ExpressionLanguageHelper.evalBoolean("isinteger", element.getAttribute("isinteger"), this, pageContext);
                column.setInteger(valueisInteger != null && valueisInteger.booleanValue());
            }
            if(element.hasAttribute("isdouble"))
            {
                Boolean valueisDouble = ExpressionLanguageHelper.evalBoolean("isdouble", element.getAttribute("isdouble"), this, pageContext);
                column.setDouble(valueisDouble != null && valueisDouble.booleanValue());
            }
            if (element.hasAttribute("isdate"))
            {
          	  column.setIsDate(element.getAttribute ("isdate"));
            }else{
          	  column.setIsDate("false");
            }
            if (element.hasAttribute("image"))
            {
            	column.setImage(ExpressionLanguageHelper.evalString ("image", element.getAttribute ("image"), this, pageContext));
            }
            if (element.hasAttribute("location"))
            {
            	column.setLocation(element.getAttribute ("location"));
            }

            if (element.hasAttribute("confirm"))
            {
            	column.setConfirm(element.getAttribute ("confirm"));
            }

            if (element.hasAttribute("imgtarget"))
            {
            	column.setImgtarget(ExpressionLanguageHelper.evalString ("imgtarget", element.getAttribute ("imgtarget"), this, pageContext));
            }
            list3   = element.getChildNodes ();

            length3 = list3.getLength ();

            for (index3 = 0; index3 < length3; index3++)

            {

              node = list3.item (index3);

              if (node.getNodeType () == Node.ELEMENT_NODE)

              {

                element = (Element) node;



                if      (element.getNodeName ().equals ("header"))

                {

                  if (element.hasAttribute ("style"))      column.setHeaderStyle           (ExpressionLanguageHelper.evalString ("style",      element.getAttribute ("style"),      this, pageContext));

                  if (element.hasAttribute ("styleClass")) column.setHeaderStyleClass      (ExpressionLanguageHelper.evalString ("styleClass", element.getAttribute ("styleClass"), this, pageContext));

                  if (element.hasAttribute ("value"))      column.setHeaderValue           (ExpressionLanguageHelper.evalString ("value",      element.getAttribute ("value"),      this, pageContext));

                  if (element.hasAttribute ("hAlign"))     column.setHeaderHorizontalAlign (ExpressionLanguageHelper.evalString ("hAlign",     element.getAttribute ("hAlign"),     this, pageContext));

                  if (element.hasAttribute ("vAlign"))     column.setHeaderVerticalAlign   (ExpressionLanguageHelper.evalString ("vAlign",     element.getAttribute ("vAlign"),     this, pageContext));

                  if (element.hasAttribute ("hyperLink"))  column.setHeaderHyperLink   (ExpressionLanguageHelper.evalString ("hyperLink",     element.getAttribute ("hyperLink"),     this, pageContext));

                  if (element.hasAttribute ("hyperLinkTarget"))  column.setHeaderHyperLinkTarget   (ExpressionLanguageHelper.evalString ("hyperLinkTarget",     element.getAttribute ("hyperLinkTarget"),     this, pageContext));

                  value = XmlHelper.getText (element);

                  if (value.length () > 0) column.setHeaderValue (value);

                }

                else if (element.getNodeName ().equals ("item"))

                {

                  if (element.hasAttribute ("style"))           column.setItemStyle           (element.getAttribute ("style"));

                  if (element.hasAttribute ("styleClass"))      column.setItemStyleClass      (element.getAttribute ("styleClass"));

                  if (element.hasAttribute ("value"))           column.setItemValue           (element.getAttribute ("value"));

                  if (element.hasAttribute ("pattern"))         column.setItemPattern         (ExpressionLanguageHelper.evalString  ("pattern", element.getAttribute ("pattern"), this, pageContext));

                  if (element.hasAttribute ("hAlign"))          column.setItemHorizontalAlign (ExpressionLanguageHelper.evalString  ("hAlign",  element.getAttribute ("hAlign"),  this, pageContext));

                  if (element.hasAttribute ("vAlign"))          column.setItemVerticalAlign   (ExpressionLanguageHelper.evalString  ("vAlign",  element.getAttribute ("vAlign"),  this, pageContext));

                  if (element.hasAttribute ("hyperLink"))       column.setItemHyperLinkValue  (element.getAttribute ("hyperLink"));

                  if (element.hasAttribute ("hyperLinkTarget")) column.setItemHyperLinkTarget (element.getAttribute ("hyperLinkTarget"));

                  if (element.hasAttribute("isshow"))
                  {
                  	column.setIsShow(element.getAttribute ("isshow"));
                  }
                  /*if(element.hasAttribute("isShowHyperLink"))
                  {
                	  column.setIsShowHyperLink(element.getAttribute ("isShowHyperLink"));
                  }*/
                  if (element.hasAttribute("istooltip"))
                  {
                  	column.setIsToolTip(element.getAttribute("istooltip"));
                  }
                  if (element.hasAttribute("onclick"))
                  {
                  	column.setOnClick(element.getAttribute("onclick"));
                  }
                  if (element.hasAttribute("ismenuvar"))
                  {
                  	column.setIsMenuVar(element.getAttribute ("ismenuvar"));
                  } 
                  if (element.hasAttribute("iscancelretry"))
                  {
                  	column.setIsCancelRetry(element.getAttribute ("iscancelretry"));
                  }
                  if (element.hasAttribute("isshowmultipleicon"))
                  {
                  	column.setIsShowMultipleIcon(element.getAttribute("isshowmultipleicon"));
                  }
                  
                  value = XmlHelper.getText (element);

                  if (value.length () > 0) column.setItemValue (value);

                }

                else if (element.getNodeName ().equals ("footer"))

                {

                  if (element.hasAttribute ("style"))      column.setFooterStyle           (ExpressionLanguageHelper.evalString ("style",      element.getAttribute ("style"),      this, pageContext));

                  if (element.hasAttribute ("styleClass")) column.setFooterStyleClass      (ExpressionLanguageHelper.evalString ("styleClass", element.getAttribute ("styleClass"), this, pageContext));

                  if (element.hasAttribute ("value"))      column.setFooterValue           (element.getAttribute ("value"));

                  if (element.hasAttribute ("hAlign"))     column.setFooterHorizontalAlign (ExpressionLanguageHelper.evalString ("hAlign",     element.getAttribute ("hAlign"),     this, pageContext));

                  if (element.hasAttribute ("vAlign"))     column.setFooterVerticalAlign   (ExpressionLanguageHelper.evalString ("vAlign",     element.getAttribute ("vAlign"),     this, pageContext));



                  value = XmlHelper.getText (element);

                  if (value.length () > 0) column.setFooterValue (value);

                }

                else if (element.getNodeName ().equals ("aggregate"))

                {

                  function = new Aggregate ();

               //   if (element.hasAttribute ("function")) function.setFunction ((AggregateFunction) Class.forName (Resource.DATAGRID.getString ("datagrid.aggregate.function." + ExpressionLanguageHelper.evalString ("function", element.getAttribute ("function").toLowerCase (), this, pageContext))).newInstance ());

                  if (element.hasAttribute ("pattern"))  function.setPattern  (ExpressionLanguageHelper.evalString ("pattern", element.getAttribute ("pattern"), this, pageContext));

                  if (element.hasAttribute ("var"))      function.setVar      (ExpressionLanguageHelper.evalString ("var",     element.getAttribute ("var"),     this, pageContext));



                  if ((function.getFunction () != null) && (function.getVar () != null))

                    column.getAggregates ().add (function);

                }

              }

            }

          }



          columnsSize = columns.size ();

        }

      }

    }

  }



  /**

   * Method to process the values of the data grid items and aggregates.
   *
   * it will be added in the rows

   */

  private void calculateValues () throws Exception

  {

    Column    column;

    Row       row;

    Cell      cell;

    Format    format = null;

    int       indexColumn, indexAggregate, sizeAggregates;



    rows = new ArrayList ();

    if (items != null)

    {

      while (items.hasNext ())

      {

        pageContext.setAttribute (itemVar, items.next ());



        row = new Row ();

        row.setCells (new Cell [columnsSize]);

        if (rowsStyle               != null)

          row.setStyle               (ExpressionLanguageHelper.evalString ("style",      rowsStyle,               this, pageContext));

        if (rowsStyleClass          != null)

          row.setStyleClass          (ExpressionLanguageHelper.evalString ("styleClass", rowsStyleClass,          this, pageContext));

        if (alternateRowsStyle      != null)

          row.setAlternateStyle      (ExpressionLanguageHelper.evalString ("style",      alternateRowsStyle,      this, pageContext));

        if (alternateRowsStyleClass != null)

          row.setAlternateStyleClass (ExpressionLanguageHelper.evalString ("styleClass", alternateRowsStyleClass, this, pageContext));



        for (indexColumn = 0; indexColumn < columnsSize; indexColumn++)

        {

          column = (Column) columns.get (indexColumn);

         //it will change the cell values dynamically as activated and deactivated

          cell   = new Cell ();
          if(column.getIsShow() != null){

        	  cell.setIsShow (ExpressionLanguageHelper.evalString ("hyperLink",  column.getIsShow(), this, pageContext));
          }
         
          if(column.getIsCancelRetry() != null){

        	  cell.setIsCancelRetry(ExpressionLanguageHelper.evalString ("hyperLink",  column.getIsCancelRetry(), this, pageContext));
          }
          if(column.getIsToolTip() != null){

        	  cell.setIsToolTip (column.getIsToolTip());
          }
          if(column.getIsShowMultipleIcon()!= null){

        	  cell.setIsShowMultipleIcon(ExpressionLanguageHelper.evalString ("isshowmultipleicon", column.getIsShowMultipleIcon(), this, pageContext));
          }
          
          
          if(cell.getIsShow() == null){

            cell.setValue (ExpressionLanguageHelper.evalObject ("value", column.getItemValue (), this, pageContext));
          }else{
        	  if(cell.getIsShow().equalsIgnoreCase(Resource.YES)){
        		  cell.setValue (ExpressionLanguageHelper.evalObject ("value", column.getItemValue (), this, pageContext));
        	  }else{
        		 if(column.getItemValue ().equalsIgnoreCase(Resource.ACTIVATE))
        		     cell.setValue (Resource.DEACTIVATE);
        		 else
        			 cell.setValue (ExpressionLanguageHelper.evalObject ("value", column.getItemValue (), this, pageContext));

        	  }
          }
          if(cell.getIsCancelRetry() == null){

             // cell.setValue (ExpressionLanguageHelper.evalObject ("value", column.getItemValue (), this, pageContext));
          }else{
          	  if(cell.getIsCancelRetry().equalsIgnoreCase("C")){
          		  cell.setValue (Resource.RETRY);
          	  }else if(cell.getIsCancelRetry().equalsIgnoreCase("R")) {
          		  cell.setValue (Resource.CANCEL);
          	  }
          }
          if (column.getItemStyle () != null)

            cell.setStyle      (ExpressionLanguageHelper.evalString ("style",      column.getItemStyle (),          this, pageContext));

          if (column.getItemStyleClass () != null)

            cell.setStyleClass (ExpressionLanguageHelper.evalString ("styleClass", column.getItemStyleClass (),     this, pageContext));
          if (null != column.getIsMenuVar() && !column.getIsMenuVar().equals(""))
        	  cell.setIsMenuVar(column.getIsMenuVar());

          HttpServletRequest mnrequest=(HttpServletRequest)pageContext.getRequest();

          String menuVar = "";
    	  if(null != mnrequest.getAttribute("mainMenu") && !((String)mnrequest.getAttribute("mainMenu")).equals("") && null != mnrequest.getAttribute("subMenu")&& !((String)mnrequest.getAttribute("subMenu")).equals("")) {
    		  String mn = (String)mnrequest.getAttribute("mainMenu");
    		  String sm = (String)mnrequest.getAttribute("subMenu");
    		  menuVar = "~mn="+mn+"~sm="+sm;
    	  }
          //remove the link for the cell those who has the value as activated
    	  if(cell.getIsShow() == null){
            if (column.getItemHyperLinkValue () != null)
                 if(null != cell.getValue()&& !cell.getValue().equals("") &&  null != cell.getIsMenuVar() && !cell.getIsMenuVar().equals("") &&
                		 cell.getIsMenuVar().equalsIgnoreCase("no"))  {
                	 cell.setHyperLink  (ExpressionLanguageHelper.evalString ("hyperLink",  column.getItemHyperLinkValue(), this, pageContext));
		              cell.setImgHyperLink(ExpressionLanguageHelper.evalString ("Location",  column.getLocation(), this, pageContext));
                 }else{
		              cell.setHyperLink  (ExpressionLanguageHelper.evalString ("hyperLink",  column.getItemHyperLinkValue()+menuVar, this, pageContext));
		              cell.setImgHyperLink(ExpressionLanguageHelper.evalString ("Location",  column.getLocation(), this, pageContext));
                 }
    	  }else{
    		  if(cell.getIsShow().equalsIgnoreCase(Resource.YES)){
    			  if (column.getItemHyperLinkValue () != null )

    	              cell.setHyperLink  (ExpressionLanguageHelper.evalString ("hyperLink",  column.getItemHyperLinkValue()+menuVar, this, pageContext));
    	              cell.setImgHyperLink(ExpressionLanguageHelper.evalString ("Location",  column.getLocation(), this, pageContext));

    		  }else{
    			  if(column.getItemValue().equalsIgnoreCase(Resource.DEACTIVATE) || column.getItemValue().equalsIgnoreCase(Resource.ACTIVATE)){
    				  if (column.getItemHyperLinkValue () != null )

        	              cell.setHyperLink  (ExpressionLanguageHelper.evalString ("hyperLink",  column.getItemHyperLinkValue()+menuVar, this, pageContext));
        	              cell.setImgHyperLink(ExpressionLanguageHelper.evalString ("Location",  column.getLocation(), this, pageContext));

    			  }else{

    			  }

    		  }
    	  }

          if(null != column.getOnClick() && !column.getOnClick().equals("")){
        	  cell.setOnClick(ExpressionLanguageHelper.evalString ("onclick",  column.getOnClick(), this, pageContext));
          }
          row.getCells () [indexColumn] = cell;



          if (!customPaging)

          {

            sizeAggregates = column.getAggregates ().size ();

            for (indexAggregate = 0; indexAggregate < sizeAggregates; indexAggregate++)

              ((Aggregate) column.getAggregates ().get (indexAggregate)).getFunction ().addValue (cell.getValue ());

          }

        }



        rows.add (row);

      }



      pageContext.removeAttribute (itemVar);

    }



    if (itemsSize == -1) itemsSize = rows.size ();

  }



  /**

   * Method to order the values of the data grid columns.

   */

  private void orderValues () throws Exception

  {
	
    ValueComparator comparator;
   
    DateValueComparator dateValueComparator;

    
    if(isDateSort){
	    
    	if ((rows != null) && (orderIndex != 0))
	    	
	    {
    		dateValueComparator = new DateValueComparator (orderIndex);
    		Collections.sort (rows, dateValueComparator);
	
	    }
    	
    }else{
	    if ((rows != null) && (orderIndex != 0))
	
	    {
	
	      comparator = new ValueComparator (orderIndex,isInteger,isDouble);
	
	      Collections.sort (rows, comparator);
	
	    }
    }

  }



  /**

   * Method that draws the data grid.
   *
   * genaerate the table

   */

  private void generateDataGrid (JspWriter out) throws Exception

  {



    String tableStart, tableEnd = "</table>";

    int    widthWithScrollBar = 0;



    try

    {

      widthWithScrollBar = Integer.parseInt (width) + SCROLL_BAR_WIDTH;

    }

    catch (NumberFormatException e)

    {

      scroll = false;

    }



    tableStart = generateTableStart ();

    if (scroll)

    {

      if (headerShow)

      {

        out.println (tableStart);

        generateHeaders (out);

        out.println (tableEnd);

      }

      out.print   ("<div style=\"width:");

      out.print   (widthWithScrollBar);

      out.print   ("px; height:");

      out.print   (height);

      out.println ("px; overflow:auto\">");

      out.println (tableStart);

      generateItems (out);

      out.println (tableEnd);

      out.println ("</div>");

      if (footerShow)

      {

        out.println (tableStart);

        generateFooters (out);

        out.println (tableEnd);

      }

    }

    else

    {

      out.println (tableStart);

      if (headerShow) generateHeaders (out);

           generateItems (out);




      if (footerShow) generateFooters (out);

      out.println (tableEnd);

    }

  }




  private String generateTableStart () throws Exception

  {

    StringBuffer tableStart = new StringBuffer ();



    tableStart.append ("<table");

    if (styleClass  != null)

    {

      tableStart.append (" class=\"");

      tableStart.append (styleClass);

      tableStart.append ("\"");

    }

    if (style  != null)

    {

      tableStart.append (" style=\"");

      tableStart.append (style);

      tableStart.append ("\"");

    }

    if (cellPadding != null)

    {

      tableStart.append (" cellpadding=\"");

      tableStart.append (cellPadding);

      tableStart.append ("\"");

    }

    if (cellSpacing != null)

    {

      tableStart.append (" cellspacing=\"");

      tableStart.append (cellSpacing);

      tableStart.append ("\"");

    }

    if (frame != null)

    {

      tableStart.append (" frame=\"");

      tableStart.append (frame);

      tableStart.append ("\"");

    }

    if (rules != null)

    {

      tableStart.append (" rules=\"");

      tableStart.append (rules);

      tableStart.append ("\"");

    }

    if (width != null)

    {

      tableStart.append (" width=\"");

      tableStart.append (width);

      tableStart.append ("\"");

    }

    tableStart.append (">");



    return (tableStart.toString ());

  }



  /**

   * Method that draws the headers of the data grid.

   */

  private void generateHeaders (JspWriter out) throws Exception

  {

    Iterator  columnIterator;

    Column    column;

    String    value;

    int       index, length, newOrderIndex;

    String linkTarget = "_self";

    out.print ("<tr");

    if (headerStyleClass != null) out.print (" class=\"" + headerStyleClass + "\"");

    if (headerStyle      != null) out.print (" style=\"" + headerStyle      + "\"");

    out.println (">");



    length = columns.size ();

    headerLength =  length;

    for (index = 0; index < length; index++)

    {

      column = (Column) columns.get (index);



      out.print ("<th");

      if (column.getWidth                 () != null) out.print (" width=\""  + column.getWidth ()                 + "\"");

      if (column.getHeaderHorizontalAlign () != null) out.print (" align=\""  + column.getHeaderHorizontalAlign () + "\"");

      if (column.getHeaderVerticalAlign   () != null) out.print (" valign=\"" + column.getHeaderVerticalAlign ()   + "\"");

      if (column.getHeaderStyleClass      () != null) out.print (" class=\""  + column.getHeaderStyleClass ()      + "\"");

      if (column.getHeaderStyle           () != null) out.print (" style=\""  + column.getHeaderStyle ()           + "\"");

      out.println (">");



      value = ExpressionLanguageHelper.evalString ("value", column.getHeaderValue (), this, pageContext);

      /*if(null != value && !value.equals("")) {
    	  if(value.contains("~")){
    		  value = value.replace("~", "<br>");
    	  }
      }*/

      if (column.getHeaderHyperLinkTarget() != null) {

    	   linkTarget = column.getHeaderHyperLinkTarget();
      }

      if (column.getHeaderHyperLink() != null) {

    	 String url = column.getHeaderHyperLink();

    	 url = url.replaceAll("~", "&");

         value = "<a href=\""+url+"\" target="+linkTarget+">"+value+"</a>";
      }

      if ((value != null) && (value.length () != 0))

      {

        if (column.isOrder () && itemsSize > 0)

        {

          if ((index + 1) == Math.abs (orderIndex))

            newOrderIndex = -orderIndex;

          else

            newOrderIndex = index + 1;

          //set the page index as 0 for going first page always once sorted
          pageIndexForSort = 0;

          out.print   ("<a href=\"");

         //to retain the old values in UI while sorting 
          
         // out.print   (RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (pageIndexForSort), String.valueOf (newOrderIndex) }));

          out.print	("#");
          
          out.print("\"");
         
          if(column.isInteger()) {
        	  out.print( "onclick=\"javascript:callSorting('"+newOrderIndex+"','"+column.getIsDate()+"','"+name+"','true');\"");
          }else if(column.isDouble()) {
            	  out.print( "onclick=\"javascript:callSorting('"+newOrderIndex+"','"+column.getIsDate()+"','"+name+"','true','true');\"");
          }else{
            	  out.print( "onclick=\"javascript:callSorting('"+newOrderIndex+"','"+column.getIsDate()+"','"+name+"');\"");
          }
          
          out.println (">");

          out.println (value);



          if ((imgOrderAscending != null) && ((index + 1) == orderIndex))

          {

            out.print ("<img src=\"");

            out.print (imgOrderAscending);

            out.print ("\" border=\"0\"/>");

          }

          if ((imgOrderDescending != null) && ((index + 1) == -orderIndex))

          {

            out.print ("<img src=\"");

            out.print (imgOrderDescending);

            out.print ("\" border=\"0\"/>");

          }



          out.println ("</a>");

        }

        else

          out.println (value);

      }

      else

        out.println ("&nbsp;");



      out.println ("</th>");

    }

    out.println ("</tr>");

  }



  /**

   * Method that draws the column values of the data grid.
   *
   * generate values for all td s

   */

  private void generateItems (JspWriter out) throws Exception

  {

    Iterator  iterator;

    Column    column;

    Row       row;

    Cell      cell;

    String    value, hyperLink;

    int       indexColumn;

    long      indexRow, sizeRows;

    boolean   alternate = false;
    String imgtarget = "_self";


    if (rows != null && rows.size() >0)

    {

     HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
    	if(null != request.getAttribute("howManyRecord")
    			&& !request.getAttribute("howManyRecord").toString().equals("")
    			&& !request.getAttribute("howManyRecord").toString().equals("0")) {
        	pageSize = Integer.parseInt(request.getAttribute("howManyRecord").toString());
    	}
      if ((pageSize > 0) && (!customPaging))

      {

        indexRow = pageIndex;

        sizeRows = pageIndex + pageSize;

        if (sizeRows > itemsSize) sizeRows = itemsSize;

      }

      else

      {

        indexRow = 0;

        sizeRows = (int) itemsSize;

      }



      for (; indexRow < sizeRows; indexRow++)

      {

        row = (Row) rows.get ((int) indexRow);



        out.print ("<tr");

        if ((alternate = !alternate) || ((alternateRowsStyleClass == null) && (alternateRowsStyle == null)))

        {

          if (row.getStyle ()      != null) out.print (" style=\"" + row.getStyle () + "\"");

          if (row.getStyleClass () != null) out.print (" class=\"" + row.getStyleClass () + "\"");

          if (rowsHiliteStyleClass != null) out.print (" onMouseOver=\"this.className='" + rowsHiliteStyleClass + "'\" onMouseOut=\"this.className='" + rowsStyleClass + "'\"");

        }

        else

        {

          if (row.getAlternateStyle ()      != null) out.print (" style=\"" + row.getAlternateStyle ()      + "\"");

          if (row.getAlternateStyleClass () != null) out.print (" class=\"" + row.getAlternateStyleClass () + "\"");



          if      (alternateRowsHiliteStyleClass != null) out.print (" onMouseOver=\"this.className='" + alternateRowsHiliteStyleClass + "'\" onMouseOut=\"this.className='" + alternateRowsHiliteStyleClass + "'\"");

          else if (rowsHiliteStyleClass          != null) out.print (" onMouseOver=\"this.className='" + rowsHiliteStyleClass          + "'\" onMouseOut=\"this.className='" + alternateRowsStyleClass       + "'\"");

        }

        out.println (">");



        for (indexColumn = 0; indexColumn < columnsSize; indexColumn++)

        {

          column = (Column) columns.get (indexColumn);

          cell   = row.getCells () [indexColumn];



          out.print ("<td");

          if (column.getWidth               () != null) out.print (" width=\""  + column.getWidth ()               + "\"");

          if (column.getItemHorizontalAlign () != null) out.print (" align=\""  + column.getItemHorizontalAlign () + "\"");

          if (column.getItemVerticalAlign   () != null) out.print (" valign=\"" + column.getItemVerticalAlign ()   + "\"");

          if (cell.getStyleClass            () != null) out.print (" class=\""  + cell.getStyleClass ()      + "\"");

          if (cell.getStyle                 () != null) out.print (" style=\""  + cell.getStyle ()           + "\"");

          out.println (">");
          // replace the column value as activated or deactivated depending on the confirm attribute
          if (column.getImgtarget () != null) imgtarget = "_blank";

          if(null!=cell.getValue () && cell.getValue ().toString().equalsIgnoreCase(Resource.DEACTIVATE)) {

            if ((column.getImage() != null)&&(column.getLocation() != null)&&(cell.getImgHyperLink()!= null))out.println ("<img src=\"" + column.getImage() +"\" onclick=\"javascript:window.open('" +cell.getImgHyperLink().replace('~', '&')+ "','"+imgtarget+"');\"/>");

          }else if(null!=cell.getValue () && cell.getValue ().toString().equalsIgnoreCase(Resource.ACTIVATE)){

        	  if ((column.getImage() != null)&&(column.getLocation() != null)&&(cell.getImgHyperLink()!= null))out.println ("<img src=\"" + Resource.ACTIVATE_IMAGE +"\" onclick=\"javascript:window.open('" +cell.getImgHyperLink().replace('~', '&')+ "','"+imgtarget+"');\"/>");

          }else{
        	  if(column.getConfirm()!=null){
        		  if ((column.getImage() != null)&&(column.getLocation() != null)&&(cell.getImgHyperLink()!= null))out.println ("<img src=\"" + column.getImage() +"\" onclick=\"javascript:  if( confirm('"+column.getConfirm()+"')){ window.location.href='"+cell.getImgHyperLink().replace('~', '&')+"';}\"/>");
        	  }else{
        		  if ((column.getImage() != null)&&(column.getLocation() != null)&&(cell.getImgHyperLink()!= null))out.println ("<img src=\"" + column.getImage() +"\" onclick=\"javascript:window.open('" +cell.getImgHyperLink().replace('~', '&')+ "','"+imgtarget+"');\"/>");
        	  }
          }


          if (cell.getValue () != null)

          {

            if (column.getItemPattern () != null)

              value = formatValue (cell.getValue (), column.getItemPattern ());

            else

              value = cell.getValue ().toString ();

            // code for breaking the lines if its length greater than 50
            if(null != value || !value.equals("")){

            	int valueLength = value.length();
            	/*int size = valueLength/50;
            	String valueNew = "";

            	if(valueLength>=50){
            		for(int first=1;first<=size+1;first++) {
            		  int end =first*50;
            		  int start = end-50;
            		  if(end == 50){
            		      valueNew = valueNew+value.substring(0, 50)+"<br>";
            		  }else if(end > valueLength){
            			  valueNew = valueNew+value.substring(start, valueLength)+"<br>";
            		  }else if(end < valueLength){
            			  valueNew = valueNew+value.substring(start, valueLength)+"<br>";
            		  }
            		  else{
            			  valueNew = valueNew+value.substring(start, end)+"<br>";
            		  }

            		}
            		value = valueNew;

            	}*/
            	int gridLength = Resource.NO_OF_CHARCTERS_IN_GRID ;
            	if( null != cell.getIsToolTip() && cell.getIsToolTip().equalsIgnoreCase("true")) {
            	if(valueLength>gridLength){
            		String newValue =  value.substring(0,gridLength)+"...";
            		//String divClose = " [<A class=closeLink onclick=\"hideCurrentPopup(); return false;\" href=\"#\">close this tip</A>]";
	    			String divValue =  "";
	    			if(valueLength>=gridLength){
	    				String originalValue = value;
		            	StringBuffer sb = new StringBuffer();
		    			String splitedValue = "";
		    			String remaingValue = "";
		    			int endIndex=gridLength;
		    			do{
		    				splitedValue = originalValue.substring(0,endIndex);
		    				sb.append(splitedValue+"<br>");
		    				remaingValue = originalValue.substring(splitedValue.length()+1,originalValue.length());
		    				originalValue = remaingValue;
		    			}while(originalValue.length() > gridLength);
		    			sb.append(remaingValue);

		    			divValue = sb.toString();
	            	}

	    			 value = "<a href=\"#\" onclick=\"return !showPopup('columnFieldPopup', event,'"+divValue+"');\">"+newValue+"</a>";
            	}
            }
            }
            if (cell.getHyperLink () != null)

            {

            	if(column.getConfirm()!=null){
            		out.print   ("<a href=\"");

           	  		out.print   (((HttpServletResponse) pageContext.getResponse ()).encodeURL (cell.getHyperLink ().replaceAll("~", "&")));

           	  		out.print   ("\"");

           	  		out.print (" onclick=\"");

           	  		out.print ("return confirm('"+column.getConfirm()+"')");

           	  		out.print ("\"");

           	  	}else{
           	  		out.print   ("<a href=\"");

           	  		out.print   (((HttpServletResponse) pageContext.getResponse ()).encodeURL (cell.getHyperLink ().replaceAll("~", "&")));

           	  		out.print   ("\"");
           	  	}

              if (column.getItemHyperLinkTarget () != null)

              {

                out.print (" target=\"");

                out.print (column.getItemHyperLinkTarget ());

                out.print ("\"");

              }
              if(null != column.getOnClick() && !column.getOnClick().equals(""))  {
            	  if(null != value && !value.equals("") && value.equalsIgnoreCase("edit"))
            	     out.print (" onclick=\""+cell.getOnClick());
            	  
            	  if(null != value && !value.equals("") && value.equalsIgnoreCase(Resource.ACTIVATE)
            			   || value.equalsIgnoreCase(Resource.DEACTIVATE)
            			   || value.equalsIgnoreCase(Resource.DELETE)
            			   || value.equalsIgnoreCase(Resource.ADDMEMBERS)
            			   || value.equalsIgnoreCase(Resource.PAUSE) || value.equalsIgnoreCase(Resource.PREVIEW)
            			   || value.equalsIgnoreCase(Resource.CLONE) || value.equalsIgnoreCase(Resource.CANCEL) || value.equalsIgnoreCase(Resource.RETRY))
            		  out.print (" onclick=\""+cell.getOnClick());

            	  out.print ("\"");


              }

              out.println (">");

              out.println (value);

              out.println ("</a>");

            }
            
            else{
            	if( cell.getIsShowMultipleIcon()!= null && 
            			cell.getIsShowMultipleIcon().equalsIgnoreCase(Constants.YES)) {
            		
            		String imgDivValue = "";
            		
            		String imgValue = "";
            		
            		if(null != value ) {
            			
            			int commaIndex = 0;
            			
            			commaIndex = value.indexOf(",");
            			
            			imgValue = value.substring(0,commaIndex);
            			
            			imgDivValue = value.substring(commaIndex+1,value.length());
            			
            			out.print (imgValue);
                		
                		out.print ("<img src=\"");

                        out.print (Resource.MULTIICON_IMAGE);

                        out.print ("\" border=\"0\"/   title=\""+imgDivValue+"\">");
            			
            			
            		}

            		
                    
            	} else {
            		
            		out.println (value);
            	}
            }  

          }

          else

            out.println ("&nbsp;");



          out.println ("</td>");

        }

        out.println ("</tr>");

      }

    }else{
    	out.println("<tr><td colspan=\""+headerLength+"\" class=\"grid_main_cont\" align=\"center\" height=\"23px\"><b>No record(s) found.</b></td></tr>");
    }

  }



  /**

   * Method that draws the footers of the data grid.
   *
   * construct pagination

   */

  private void generateFooters (JspWriter out) throws Exception

  {

    Iterator  iterator;

    Column    column;

    Aggregate aggregate;

    Object    total;

    String    value;

    int       index, length;



    out.print ("<tr");

    if (footerStyleClass != null) out.print (" class=\"" + footerStyleClass + "\"");

    if (footerStyle      != null) out.print (" style=\"" + footerStyle      + "\"");

    out.println (">");



    iterator = columns.iterator ();

    while (iterator.hasNext ())

    {

      column = (Column) iterator.next();



      length = column.getAggregates ().size ();

      for (index = 0; index < length; index++)

      {

        aggregate = (Aggregate) column.getAggregates ().get (index);

        total     = aggregate.getFunction ().totalValue ();

        if ((total != null) && (aggregate.getPattern () != null))

          total = formatValue (total, aggregate.getPattern ());



        pageContext.setAttribute (aggregate.getVar (), total);

      }



      out.print ("<td");

      if (column.getWidth                 () != null) out.print (" width=\""  + column.getWidth ()                 + "\"");

      if (column.getFooterHorizontalAlign () != null) out.print (" align=\""  + column.getFooterHorizontalAlign () + "\"");

      if (column.getFooterVerticalAlign   () != null) out.print (" valign=\"" + column.getFooterVerticalAlign ()   + "\"");

      if (column.getFooterStyleClass      () != null) out.print (" class=\""  + column.getFooterStyleClass ()      + "\"");

      if (column.getFooterStyle           () != null) out.print (" style=\""  + column.getFooterStyle ()           + "\"");

      out.println (">");



      value = ExpressionLanguageHelper.evalString ("value", column.getFooterValue (), this, pageContext);

      if ((value != null) && (value.length () != 0))

        out.println (value);

      else

        out.println ("&nbsp;");



      out.println ("</td>");



      for (index = 0; index < length; index++)

      {

        aggregate = (Aggregate) column.getAggregates ().get (index);

        pageContext.removeAttribute (aggregate.getVar ());

      }

    }

    out.println ("</tr>");

  }

// pagination variables

  public void setPagingVariables ()

  {

    long      index, size;

    List pages;

    Page      page;



    if (pageSize > 0)

    {

      if (nextUrlVar != null)

      {

        if (customPaging)

        {

          if (itemsCount >= 0)

          {

            if ((pageIndex + pageSize) < itemsCount)

              pageContext.setAttribute (nextUrlVar, RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (pageIndex + pageSize), String.valueOf (orderIndex) }));

          }

          else if (pageSize == itemsSize)

            pageContext.setAttribute (nextUrlVar, RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (pageIndex + pageSize), String.valueOf (orderIndex) }));

        }

        else if ((pageIndex + pageSize) < itemsSize)

          pageContext.setAttribute (nextUrlVar, RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (pageIndex + pageSize), String.valueOf (orderIndex) }));

      }



      if ((previousUrlVar != null) && (pageIndex > 0))

      {

        if ((pageIndex - pageSize) < 0)

          pageContext.setAttribute (previousUrlVar, RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (0), String.valueOf (orderIndex) }));

        else

          pageContext.setAttribute (previousUrlVar, RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (pageIndex - pageSize), String.valueOf (orderIndex) }));

      }



      if ((pagesVar != null) && (!customPaging || itemsCount >= 0))

      {

        if (customPaging)

          size = itemsCount;

        else

          size = itemsSize;



        pages = new ArrayList ();
        
        //Page number of the last pagination link 
        long lastPaginationLink = pageIndex/pageSize + noOfPaginationLinks;

        for (index = 0; index < size; )

        {

          page = new Page ();



          page.setIndex (pages.size () + 1);

          if ((index >= pageIndex) && (index < pageIndex + pageSize))

            page.setCurrent (true);



          page.setUrl (RequestHelper.getRequestURLWithNewParameters ((HttpServletRequest) pageContext.getRequest (), requestPath, new String [] { parameterNamePageIndex, parameterNameOrderIndex }, new String [] { String.valueOf (index), String.valueOf (orderIndex) }));


          page.setStartRow (index + 1);



          index += pageSize;

          if (index < size)

            page.setEndRow (index + 1);

          else

            page.setEndRow (size);


          pages.add (page);
          
          //Checking array size is equal to the last pagination link
          if(pages.size() == lastPaginationLink){
        	 
        	  index = size;
         
          }
          
        }
        
        //Ensure we remove unwanted pagination links 
        pages = pages.subList((pages.size() - noOfPaginationLinks*2) > 1 ? (pages.size() - noOfPaginationLinks*2) : 0, pages.size());

        pageContext.setAttribute (pagesVar, pages);

      }

    }

  }

  /**

   * Method that formats the column values using a pattern.

   *

   * @param Object value Number, Date or Calendar object to format.

   * @param String pattern used to format the value.

   */

  private String formatValue (Object value, String pattern)

  {

    Format format = null;

    String result = null;



    if      (value instanceof Calendar) value = ((Calendar) value).getTime ();



    if      (value instanceof Number)   format = new DecimalFormat    (pattern);

    else if (value instanceof Date)     format = new SimpleDateFormat (pattern);



    if (format != null) result = format.format (value);



    return (result);

  }



  /**

   * Method called at end of tag.

   * @return EVAL_PAGE

   */

  public int doEndTag()

  {

    return EVAL_PAGE;

  }



 /**

  * Set the boolean attribute <b>scroll</b> that specifies if the data grid will have a vertical scrollbar.

  *

  * @param String value boolean true if scroll bar enabled, false otherwise.

  */

  public void setScroll (boolean value)

  {

    scroll = value;

  }



 /**

  * Set the required attribute <b>items</b> that is an expression in the JSTL expression language that defines the Collection to be used to extract values.

  *

  * @param String items expression in the JSTL expression language that defines the Collection to be used to extract values.

  */

  public void setItems (String value) throws JspException

  {

    Object obj;



    obj = ExpressionLanguageHelper.evalObject ("items", value, this, pageContext);

    if      (obj instanceof Iterator)

      items = (Iterator) obj;

    else if (obj instanceof Object [])

      items = Arrays.asList ((Object []) obj).iterator ();

    else if (obj instanceof Collection)

      items = ((Collection) obj).iterator ();

    else if (obj instanceof Map)

      items = ((Map) obj).values ().iterator ();

    else

      throw (new IllegalArgumentException ());

  }



 /**

  * Set the required attribute <b>var</b> that specifies the name of the JSTL expression language variable that contains a single row in the data grid.

  *

  * @param String var expression in the JSTL expression language that defines the Collection to be used to extract values.

  */

  public void setVar (String value)

  {

    itemVar = value;

  }



 /**

  * Set the attribute <b>height</b> that defines the height in pixels of the data grid when scrolling is enabled.

  *

  * @param String height defines the height of the data grid when scrolling is enabled.

  */

  public void setHeight (String value) throws Exception

  {

    height = ExpressionLanguageHelper.evalString ("height", value, this, pageContext);

  }



 /**

  * Set the attribute <b>width</b> that defines the width in pixels or percentages of the data grid.

  *

  * @param String width defines the width in pixels or percentages of the data grid.

  */

  public void setWidth (String value) throws Exception

  {

    width = ExpressionLanguageHelper.evalString ("width", value, this, pageContext);

  }



 /**

  * Set the attribute <b>cellPadding</b> that defines the cell padding used in the data grid.

  *

  * @param String cellPadding defines the cell padding used in the data grid.

  */

  public void setCellPadding (String value) throws Exception

  {

    cellPadding = ExpressionLanguageHelper.evalString ("cellPadding", value, this, pageContext);

  }



 /**

  * Set the attribute <b>cellSpacing</b> that defines the cell spacing used in the data grid.

  *

  * @param String cellSpacing defines the cell spacing used in the data grid.

  */

  public void setCellSpacing (String value) throws Exception

  {

    cellSpacing = ExpressionLanguageHelper.evalString ("cellSpacing", value, this, pageContext);

  }



 /**

  * Set the attribute <b>style</b> that defines the CSS style to be used in the datagrid.

  *

  * @param String style defines the CSS style to be used in the datagrid.

  */

  public void setStyle (String value) throws Exception

  {

    style = ExpressionLanguageHelper.evalString ("style", value, this, pageContext);

  }



 /**

  * Set the attribute <b>styleClass</b> that defines the CSS class to be used in the datagrid.

  *

  * @param String styleClass defines the CSS class to be used in the datagrid.

  */

  public void setStyleClass (String value) throws Exception

  {

    styleClass = ExpressionLanguageHelper.evalString ("styleClass", value, this, pageContext);

  }



 /**

  * Set the required attribute <b>name</b> that defines the name of the datagrid.

  *

  * @param String name defines the name of the datagrid.

  */

  public void setName (String value)

  {

    name = value.toLowerCase ();

  }



 /**

  * Set the attribute <b>requestPath</b> that specifies the URL of the current document. The requestPath attribute is used to construct the URL to order a specific column. The requestPath is used to construct the URL for the page indexes.

  *

  * @param String requestPath specifies the URL of the current document.

  */

  public void setRequestPath (String value) throws Exception

  {

    requestPath = ExpressionLanguageHelper.evalString ("requestPath", value, this, pageContext);

  }



 /**

  * Set the attribute <b>frame</b> that specifies the frame value in the generated table.

  *

  * @param String frame specifies the frame value in the generated table.

  */

  public void setFrame (String value) throws Exception

  {

    frame = ExpressionLanguageHelper.evalString ("frame", value, this, pageContext);

  }



 /**

  * Set the attribute <b>rules</b> that specifies the rules value in the generated table.

  *

  * @param String rules specifies the frame value in the generated table.

  */

  public void setRules (String value) throws Exception

  {

    rules = ExpressionLanguageHelper.evalString ("rules", value, this, pageContext);

  }

}

