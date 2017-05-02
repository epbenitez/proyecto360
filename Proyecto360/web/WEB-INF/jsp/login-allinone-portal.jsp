<%-- 
    Document   : login-allinone-portal
    Created on : 13-abr-2016, 11:41:23
    Author     : Patricia Benítez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%--<s:property value="usuario.usuario" /><s:property value="usuario.password" />--%>

<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $("#form1").submit();
    });

</script>

<form id="form1" method="post" name="main" action="http://www.allinone-mexico.com/admon/acceso.asp">
    <input type="hidden" id="usuario" size="30" name="usr" value="<s:property value="usuario.usuario" />">
           <input type="hidden" id="password" size="30" type="password" name="pwd" value="<s:property value="usuario.password" />"></td>
</form>