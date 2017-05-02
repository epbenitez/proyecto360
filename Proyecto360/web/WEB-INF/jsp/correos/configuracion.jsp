<%-- 
    Document   : configuracion
    Created on : 23-nov-2016, 17:06:13
    Author     : patriciabenitez
--%>


<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <!-- Add fancyBox main JS and CSS files -->
        <script type="text/javascript" src="/resources/js/fancybox/source/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="/resources/js/fancybox/source/jquery.fancybox.css?v=2.1.5" media="screen" />

        <!-- Add Button helper (this is optional) -->
        <link rel="stylesheet" type="text/css" href="/resources/js/fancybox/source/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
        <script type="text/javascript" src="/resources/js/fancybox/source/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>

        <!-- Add Thumbnail helper (this is optional) -->
        <link rel="stylesheet" type="text/css" href="/resources/js/fancybox/source/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
        <script type="text/javascript" src="/resources/js/fancybox/source/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>

        <!-- Add Media helper (this is optional) -->
        <script type="text/javascript" src="/resources/js/fancybox/source/helpers/jquery.fancybox-media.js?v=1.0.6"></script>

        <script type="text/javascript" language="javascript" class="init">
            $(document).ready(function () {

                $("#condominio").change(function () {
                    getTorres();
                });
                $("#torre").change(function () {
                    getConfiguracion();
                });

                $("#mensajePruebaBtn").click(function () {
                    envioPrueba();
                });

                $("#condominioAux").click(function () {
                    $("#cuerpo").val($("#cuerpo").val() + "[condominio]");
                });
                $("#torreAux").click(function () {
                    $("#cuerpo").val($("#cuerpo").val() + "[torre]");
                });
                $("#departamentoAux").click(function () {
                    $("#cuerpo").val($("#cuerpo").val() + "[departamento]");
                });
                $("#usuarioAux").click(function () {
                    $("#cuerpo").val($("#cuerpo").val() + "[usuario]");
                });
                $("#avisodecobroAux").click(function () {
                    $("#cuerpo").val($("#cuerpo").val() + "[avisodecobro]");
                });

                $("#enviar").click(function () {
                    BootstrapDialog.show({
                        title: 'Información',
                        message: 'Se está realizando el envío de correos. Este proceso puede demorar. Por favor, no cierre ni actualice esta ventana.',
                        closable: false
                    });
                });
            });


        </script>
        <style>
            #aux span {
                cursor:pointer;
                color: #8BC34A;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <p></p>
            <h1>Configuraci&oacute;n de Env&iacute;o de Correos Electr&oacute;nicos</h1>

            <div class="row">
                <div class="col-lg-12">
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

            <div class="row main_content">
                <div class="row col-lg-12 main-box" id="busquedaForm">
                    <div class="clearfix" >&nbsp;</div>
                    <h3 style="margin-top: 5px; margin-left: 10px"><span>Configuraci&oacute;n</span></h3>
                    <h2 style="margin-left: 10px" >Seleccione Torre y Condominio y 
                        especifique la informaci&oacute;n requerida para el env&iacute;o de correos.<br>
                    </h2>
                    <div class="clearfix" >
                        <p>Puede realizar un envío de prueba, especificando el correo electr&oacute;nico 
                        y dando clic en el bot&oacute;n "Enviar Mensaje de Prueba". 
                        Este mensaje no contendr&aacute; los datos personalizados por departamento, pero
                        podr&aacute; observar el formato en el que se env&iacute;a
                        </p>
                        &nbsp;</div>
                    <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
                    <form id="envioCorreosForm" action="/correos/enviaCorreos.action" method="POST">
                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Condominio:
                            </label>
                            <div class="col-lg-9">
                                <s:select id="condominio"  class="form-control" 
                                          list="condominiolist" listKey="id" listValue="nombre" headerKey=""
                                          headerValue="-- Seleccione --"
                                          name="configuracion.condominio.id"
                                          data-bv-notempty="true"
                                          data-bv-notempty-message="Este campo es requerido"
                                          required="true"
                                          />
                                <span class="help-block" id="condominioMessage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Torre:
                            </label>
                            <div class="col-lg-9">
                                <s:select id="torre"  class="form-control"
                                          list="#{}"
                                          headerValue="-- Seleccione --"
                                          name="configuracion.torre.id"
                                          data-bv-notempty="true"
                                          data-bv-notempty-message="Este campo es requerido"
                                          required="true"
                                          />
                                <span class="help-block" id="torreMessage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Asunto:
                            </label>
                            <div class="col-lg-9">
                                <s:textfield id="asunto"  class="form-control" maxlength="100" name="configuracion.asunto"
                                             onkeypress="return validarn(event)"
                                             data-bv-notempty="true"
                                             data-bv-notempty-message="Este campo es requerido"
                                             required="true"/>
                                <span class="help-block" id="torreMessage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Encabezado del mensaje:
                            </label>
                            <div class="col-lg-9">
                                <s:textarea rows="5" id="encabezado"  class="form-control"  maxlength="100"  name="configuracion.encabezado"
                                            onkeypress="return validarn(event)"
                                            data-bv-notempty="true"
                                            data-bv-notempty-message="Este campo es requerido"
                                            required="true"/>
                                <span class="help-block" id="torreMessage" />
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Cuerpo del mensaje:
                            </label>
                            <div class="col-lg-9">
                                <s:textarea rows="5" id="cuerpo"  class="form-control"  maxlength="1000" name="configuracion.cuerpo"  
                                            onkeypress="return validarn(event)"
                                            data-bv-notempty="true"
                                            data-bv-notempty-message="Este campo es requerido"
                                            required="true"/>
                                <span class="help-block" id="torreMessage" ></span>
                                <p id="aux"> <span id="condominioAux">[condominio]</span> 
                                    <span id="torreAux">[torre]</span>
                                    <span id="departamentoAux">[departamento]</span>
                                    <span id="usuarioAux">[usuario]</span>
                                    <span id="avisodecobroAux">[avisodecobro]</span></p>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Pie o firma del mensaje:
                            </label>
                            <div class="col-lg-9">
                                <s:textarea rows="5" id="firma"  class="form-control" maxlength="100" name="configuracion.firma"
                                            onkeypress="return validarn(event)"
                                            data-bv-notempty="true"
                                            data-bv-notempty-message="Este campo es requerido"
                                            required="true"/>
                                <span class="help-block" id="torreMessage" />
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                Email Prueba:
                            </label>
                            <div class="col-lg-6">
                                <s:textfield id="mensajePrueba"  class="form-control" maxlength="100"/>
                                <span class="help-block" id="torreMessage" />
                            </div>
                            <div class="col-lg-3">
                                <button type="button" id="mensajePruebaBtn" class="btn btn-success">Enviar Mensaje de Prueba</button>
                            </div>
                            <div id="resultado" class="col-lg-1" style="display: none;">
                                <i class="fa fa-check-circle"></i>
                            </div>
                        </div>

                        <div class="clearfix" >&nbsp;</div>
                        <div class="clearfix" >&nbsp;</div>
                        <div class="form-group">
                            <div class="col-lg-12 text-center ">
                                <button type="submit" id="enviar" class="btn btn-success">Enviar Correos</button>
                            </div>
                        </div>
                        <div class="clearfix" >&nbsp;</div>
                    </form>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </div>
        </div>
        <script type="text/javascript" language="javascript" class="init">
            $(document).ready(function () {

                $('#envioCorreosForm').bootstrapValidator({});

                $('select').addClass('form-control');
                $('textarea').addClass('form-control');
                $('input').addClass('form-control');
            }
            );
        </script>

        <script>

            function getTorres() {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/getTorresSolicitudesAjax.action',
                    dataType: 'json',
                    data: {pkCondominio: $('#condominio').val()},
                    cache: false,
                    success: function (aData) {

                        $('#torre').get(0).options.length = 0;
                        $('#torre').get(0).options[0] = new Option("-- Seleccione --", "");

                        $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                            $('#torre').get(0).options[$('#torre').get(0).options.length] = new Option(item[1], item[0]);
                            // Display      Value
                        });

                    },
                    error: function () {
                        alert("Connection Is Not Available");
                    }
                });

                return false;
            }

            function getConfiguracion() {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/getConfiguracionEnvioCorreosAjax.action',
                    dataType: 'json',
                    data: {pkCondominio: $('#condominio').val(),
                        pkTorre: $('#torre').val()
                    },
                    cache: false,
                    success: function (aData) {

                        if (typeof aData.data[0] !== "undefined") {
                            $("#asunto").val(aData.data[0][0].replace(/<</gi, "[").replace(/>>/gi, "]").replace(/<BR>/gi, "\n"));
                            $("#encabezado").val(aData.data[0][1].replace(/<</gi, "[").replace(/>>/gi, "]").replace(/<BR>/gi, "\n"));
                            $("#cuerpo").val(aData.data[0][2].replace(/<</gi, "[").replace(/>>/gi, "]").replace(/<BR>/gi, "\n"));
                            $("#firma").val(aData.data[0][3].replace(/<</gi, "[").replace(/>>/gi, "]").replace(/<BR>/gi, "\n"));
                        } else {
                            $("#asunto").val("");
                            $("#encabezado").val("");
                            $("#cuerpo").val("");
                            $("#firma").val("");
                        }

                    },
                    error: function (aData) {
                        alert("Connection Is Not Available" + aData.responseText);
                    }
                });

                return false;
            }

            function envioPrueba() {
                $("#resultado").hide();
                if ($('#mensajePrueba').val().trim().length === 0) {
                    BootstrapDialog.alert('Para enviar la prueba del mensaje, por favor, ingrese el correo electrónico al que se desea enviar.');
                    return;
                }
                if ($('#condominio').val().trim().length === 0 ||
                        $('#torre').val().trim().length === 0 ||
                        $('#asunto').val().trim().length === 0 ||
                        $('#encabezado').val().trim().length === 0 ||
                        $('#cuerpo').val().trim().length === 0 ||
                        $('#firma').val().trim().length === 0
                        ) {
                    BootstrapDialog.alert('Para enviar la prueba del mensaje, por favor, completa todos los campos.');
                    return;
                }

                $.ajax({
                    type: 'POST',
                    url: '/ajax/envioPruebaEnvioCorreosAjax.action',
                    dataType: 'json',
                    data: {'config.condominio.id': $('#condominio').val(),
                        'config.torre.id': $('#torre').val(),
                        'config.asunto': $('#asunto').val(),
                        'config.encabezado': $('#encabezado').val(),
                        'config.cuerpo': $('#cuerpo').val(),
                        'config.firma': $('#firma').val(),
                        emailPrueba: $('#mensajePrueba').val()
                    },
                    cache: false,
                    success: function (aData) {

                        if (typeof aData.data[0] !== "undefined") {
                            if (aData.data[0][0] === "OK") {
                                $("#resultado").html('<i class="fa fa-check-circle" style="color:green;"></i>');
                                $("#resultado").show();
                            } else {
                                BootstrapDialog.alert('No se pudo enviar el correo. Podría ser causa del formato enviado, o de la disponibilidad del servidor de correos.');
                                $("#resultado").html('<i class="fa fa-circle" style="color:red;"></i>');
                                $("#resultado").show();
                            }
                        } else {
                            BootstrapDialog.alert('No se pudo enviar el correo. Podría ser causa del formato enviado, o de la disponibilidad del servidor de correos.');
                            $("#resultado").html('<i class="fa fa-circle" style="color:red;"></i>');
                            $("#resultado").show();
                        }

                    },
                    error: function (aData) {
                        alert("Connection Is Not Available" + aData.responseText);
                    }
                });

                return false;
            }


            function validarn(e) { // 1
                tecla = (document.all) ? e.keyCode : e.which; // 2
                if (tecla == 8)
                    return true; // 3
                if (tecla == 9)
                    return true; // 3
                if (tecla == 11)
                    return true; // 3
                patron = /[A-Za-zñÑáéíóúÁÉÍÓÚÑñ!$%&/()=:;,¡¿?\[\]\s\t]/; // 4

                te = String.fromCharCode(tecla); // 5
                return patron.test(te); // 6
            }
        </script>
    </body>

</html>