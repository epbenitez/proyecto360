<%@taglib prefix="decorator" uri="/WEB-INF/decorators/tld/sitemesh-decorator.tld" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
<%@taglib prefix="page" uri="/WEB-INF/decorators/tld/sitemesh-page.tld" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext;" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>ALL IN ONE</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1" />


        <s:head />

        <decorator:head />

    </head>
    <body<decorator:getProperty property="body.onload" writeEntireProperty="true" />
        <decorator:getProperty property="body.class" writeEntireProperty="true" />
        <security:authorize ifNotGranted="ROLE_ANONYMOUS">
            oncontextmenu="return false;"
        </security:authorize> >

        <security:authorize ifAnyGranted="ROLE_ANONYMOUS">
            <!--ENCABEZADO ROL ANONIMO-->
        </security:authorize>

        <security:authorize ifNotGranted="ROLE_ANONYMOUS">
            ENCABEZADO <BR>
            USUARIO:        <%=ActionContext.getContext().getSession().get("nombreCompleto")%>
            <div id = "col" width="50%" >
                <div id="salir" class="salir" align="right" onclick="window.location.href = '/j_spring_security_logout'">
                    Salir
                </div>
            </div>

        </security:authorize>
        <decorator:body />


    </body>
</html>
