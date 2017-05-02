<%@taglib prefix="s" uri="/struts-tags" %>

    <!DOCTYPE html> html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
            <title>PROYECTO360</title>
            <style type="text/css">
                body {
                    margin-left: 0px;
                    margin-top: 0px;
                    margin-right: 0px;
                    margin-bottom: 0px;
                }


                .registro {
                    margin: 0px;
                    text-align: center;
                    /*                width: 230px;*/
                    font-family: Arial, Helvetica, sans-serif;
                    padding-top: 0px;
                    padding-right: 10px;
                    padding-bottom: 0px;
                    padding-left: 0px;
                    text-align: center;
                }
                .quitarMargenes {
                    margin: 0px;
                    padding: 0px;
                }

                .loginbody{
                    background-color: #FFFFFF;
                    background-image: none;
                }
            </style>
        </head>

        <body onload="document.j_spring_security_check.j_username.focus();" class="metro">

            <div class="container">
                <h1 style=" padding: 10px;" class="morado"> Bienvenido</h1>
                <div class="divFdo" align="center">

                    <table width="100%" border="0">
                        <tr>
                            <td width="50%">
                                <form id="j_spring_security_check" name="j_spring_security_check" action="/j_spring_security_check" method="post">
                                    <div align="left" style="padding-top: 20%; padding-left: 30%; ">

                                    <s:actionerror cssClass="messageError" />
                                    <s:actionmessage cssClass="messageSuccess" />
                                    <s:if test="fieldErrors.size > 0">
                                        <ul class="messageError">
                                            <li><span><s:text name="invalid.pantalla" /></span></li>
                                        </ul>
                                    </s:if>

                                    <table border="0" style="border-spacing: 5px;" class="login">
                                        <tr>
                                            <td style="font-size: 14px;"><b><s:text name="login.user" />:</b></td>
                                            <td><input type="text" name="j_username" value="<s:if test="login_error"><s:property value="%{#session.SPRING_SECURITY_LAST_USERNAME}" /></s:if><s:else></s:else>" id="j_spring_security_check_j_username" style="border: solid 1px #E7E9E9; border-left: solid 1px #D3D4D6; height: 22px;" /></td>
                                            </tr>
                                                <tr><td style="font-size: 14px;"><b><s:text name="login.password" />:</b></td>
                                            <td><input type="password" name="j_password" maxlength="15" id="j_spring_security_check_j_password" ondragstart="return false" onselectstart="return false" style="border: solid 1px #E7E9E9; border-left: solid 1px #D3D4D6; height: 22px;" />

                                            </td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><input type="submit" id="j_spring_security_check_login" name="login" value="Entrar" style="" class="submit" style="margin-top: 3%;" /></td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" style="text-align: right;">
                                            </td>
                                        </tr>
                                        <br/><br/>
                                    </table>
                                </div>
                            </form>
                        </td>
                        <td rowspan="2">
                            <div style="text-align: right">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div align="left" style="padding-top: 20%; padding-left: 30%; ">
                                <table border="0" style="border-spacing: 5px;">
                                    <tr>
                                        <td colspan="2">
                                            <b id="olvidaPwd"><a href="/inicio/recuperaFormContrasenia.action">&iquest;Olvidaste tu contrase&ntilde;a?</a></b>
                                        </td>
                                        <td width="70px">

                                        </td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <s:form id="formRegistro" namespace="/inicio" method="post" enctype="multipart/form-data" action="loginRecuperaContrasenia">
                                                <p style="font-size: 12px; color: #000000; font-family: Arial; line-height: normal;">Ingresa tu nombre de usuario o <br/>
                                                    direcci&oacute;n de correo electr&oacute;nico:
                                                </p>
                                                <input type="text" name="recupera" maxlength="100" value="" id="recupera" style="border: solid 1px #E7E9E9; border-left: solid 1px #D3D4D6; height: 22px;"/>
                                                <s:submit key="enviar" id="submitFormButton" labelposition="left" style="color: #fff"  />
                                            </s:form>
                                        </td>
                                        <td width="50px">

                                        </td>
                                        <s:form id="formRegistro" namespace="/registro" method="post" enctype="multipart/form-data" action="registroAlumno">
                                            <s:submit key="Inscríbete ya" id="submitFormButton" labelposition="left" style="background-color:#ED4F25; width:120px; height:25px; border: solid 1px #ED4F25; color:#fff; font-size:12px; font-weight:bold; " />
                                        </s:form>
                                    </tr>
                                    <br/>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>

            </div>


        </div>
    </body>
</html>