package com.marta.admin.tags.helper;



import org.w3c.dom.Node;

import org.w3c.dom.Element;

import org.w3c.dom.NodeList;



public class XmlHelper 

{

  public static String getText (Node node)

  {

    StringBuffer text;

    NodeList     list;

    Node         child;

    int          index, length;



    text   = new StringBuffer ();

    list   = node.getChildNodes ();

    length = list.getLength ();

    for (index = 0; index < length; index++)

    {

      child = list.item (index);



      if (child.getNodeType () == Node.TEXT_NODE || child.getNodeType() == Node.CDATA_SECTION_NODE)

        text.append (child.getNodeValue ());

    }



    return (text.toString ());

  }

}