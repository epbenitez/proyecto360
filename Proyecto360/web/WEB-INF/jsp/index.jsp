
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize ifAnyGranted="ROLE_PROPIETARIO">
    <p  style="text-align: center;"><img src="/img/logo_torres/<s:property value="deptoUsuario.departamento.condominio.clave" />.png" alt="<s:property value="deptoUsuario.departamento.torre.nombre" />"/></p>
    <p  style="text-align: center;">Departamento: <s:property value="deptoUsuario.departamento.nombre" /></p>
</security:authorize>


<iframe id="loginFrame" src="/portal.action" frameborder="0" height="1" width="1"></iframe>
