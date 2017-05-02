<%-- 
    Document   : revertirAbonos
    Created on : 08-ene-2016, 9:24:09
    Author     : Patricia Benitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div class="col-lg-12">
    <div class="main-box clearfix">
        <div class="clearfix">&nbsp;</div>  
        <div class="col-md-12">
            <s:if test="hasActionErrors()">
                <div class="alert alert-danger">
                    <i class="fa fa-times-circle fa-fw fa-lg"></i>
                    <strong>&iexcl;Error!</strong> <s:actionerror/>
                </div>
            </s:if>
            <s:if test="hasActionMessages()">
                <div class="alert alert-success">
                    <i class="fa fa-check-circle fa-fw fa-lg"></i>
                    <strong>&iexcl;Realizado!</strong> <s:actionmessage />
                </div>

            </s:if>
        </div>
    </div>
</div>