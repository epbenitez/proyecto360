<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext;" %>

<html>
    <head>
        <title>Sesi&oacute;n finalizada</title>
    </head>
    <body>

        <div>
           <h1>La sesi&oacute;n del usuario ha caducado.</h1>
            <br>
            Ingrese al Sistema desde la p&aacute;gina principal del <a href="#" onclick="window.top.location='<%=ActionContext.getContext().getSession().get("urlPortal")%>/'">PORTAL</a>
        </div>
    </body>
</html>
