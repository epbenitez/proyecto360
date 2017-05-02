<%-- 
    Document   : 401
    Created on : 14/06/2011, 11:36:22 AM
    Author     : Patricia Benitez
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <META HTTP-EQUIV="refresh" CONTENT="0; url=http://sso.abiertayadistancia.sep.gob.mx/sso/UI/Logout?goto=http%3A%2F%2Fservicios.abiertayadistancia.sep.gob.mx">
        <title>ESAD - Autenticando</title>
    </head>
    <body>
        <p>Redireccionando...</p>
        <%
                      for (int i = 0; i < request.getCookies().length; i++) {%>
        <%
                        request.getCookies()[i].setMaxAge(0);
                        request.getCookies()[i].setPath("/");
                        request.getCookies()[i].setDomain(".abiertayadistancia.sep.gob.mx");
                        response.addCookie(request.getCookies()[i]);
                    }%>

        <% session.invalidate();%>

    </body>
</html>
