<%-- 
    Document   : ajaxJSONPaginated
    Created on : 27/10/2014, 03:21:57 PM
    Author     : Erick
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

{
  "sEcho": <s:property value="jsonDraw" escape="false" />,
  "iTotalRecords": <s:property value="jsonTotalRecords" escape="false" />,
  "iTotalDisplayRecords": <s:property value="jsonDisplayRecords" escape="false" />,
  "aaData":
            <s:property value="jsonResult" escape="false" />
            
}