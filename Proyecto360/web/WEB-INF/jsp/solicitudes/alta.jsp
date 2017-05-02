<%-- 
    Document   : alta
    Created on : 27-feb-2016, 21:08:05
    Author     : luistlatelpa
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
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
    <script src="/js/UtilString.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {

            $('#formAltaSolicitud').bootstrapValidator({});


            $("#condominio").change(function () {
                getTorres();
            });

            $("#torre").change(function () {
                getDepartamentos();
            });

            $("#tipoSolicitud").change(function () {
                getAreas();
            });

            init_contadorTa("comentario", "comentarioContador", 200);

        });



    </script>
</head>
<div class="container">
    <p></p>
    <h1>Gesti&oacute;n de Solicitudes</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Nueva Solicitud</span></h3>
            <h2 style="margin-left: 10px" >Seleccione Condominio y  Torre para encontrar el departamento.</h2>
            <div class="clearfix" >&nbsp;</div>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="formAltaSolicitud" name="formAltaSolicitud" action="/solicitudes/guardaSolicitudes.action" method="POST">
                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" headerKey=""
                                      name="solicitud.departamento.condominio.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="solicitud.departamento.condominio.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>

                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Torre:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="torre"  class="form-control"
                                      list="torres" listKey="id" listValue="nombre" 
                                      name="solicitud.departamento.torre.id"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Torre:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="torre"  class="form-control"
                                      list="#{}"
                                      headerValue="-- Seleccione --"
                                      name="solicitud.departamento.torre.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>
                </security:authorize>

                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Departamento:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="departamento"  class="form-control"
                                      list="departamentos" listKey="id" listValue="nombre" 
                                      headerValue="-- Seleccione --"
                                      name="solicitud.departamento.id"
                                      />
                            <span class="help-block" id="departamentoMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Departamento:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="departamento"  class="form-control"
                                      list="#{}"
                                      headerValue="-- Seleccione --"
                                      name="solicitud.departamento.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="departamentoMessage" />
                        </div>
                    </div>
                </security:authorize>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo de Solicitud
                    </label>
                    <div class="col-lg-9">
                        <s:select id="tipoSolicitud"  class="form-control" 
                                  list="tipos" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="solicitud.tipoSolicitud.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="tipoSolicitudMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        &Aacute;rea
                    </label>
                    <div class="col-lg-9">
                        <s:select id="area"  class="form-control" 
                                  list="#{}"
                                  headerValue="-- Seleccione --"
                                  name="solicitud.area.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="tipoSolicitudMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">Cond&oacute;mino que solicita:</label>
                    <div class="col-lg-9">
                        <s:textfield id="solicitante" name="solicitud.solicitante" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="solicitanteMessage" />
                    </div>
                </div> 

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">Comentarios:</label>
                    <div class="col-lg-9">
                        <s:textfield id="comentario" name="solicitud.comentario" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="comentarioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-2" ></div>    
                    <div class="col-lg-9" id="comentarioContador" text-right></div>    
                </div> 
                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <div class="form-group">
                    <div class="col-lg-12 text-center ">
                        <button type="submit" id="guardar" class="btn btn-success">Guardar</button>
                    </div>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>

    </div>
</div>
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('select, input').addClass('form-control');
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

    function getDepartamentos() {
        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/getDepartamentosSolicitudesAjax.action',
            dataType: 'json',
            data: {pkCondominio: $('#condominio').val(), pkTorre: $('#torre').val()},
            beforeSend: function () {
                dialog = BootstrapDialog.show({
                    title: 'Información',
                    message: 'Sus datos están siendo cargados. Por favor, espere.',
                    closable: false
                });
            },
            cache: false,
            success: function (aData) {
                dialog.close();
                $('#departamento').get(0).options.length = 0;
                $('#departamento').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#departamento').get(0).options[$('#departamento').get(0).options.length] = new Option(item[1], item[0]);
                    // Display      Value
                });

            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }

    function getAreas() {

        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/getAreasSolicitudesAjax.action',
            dataType: 'json',
            data: {pkTipoSolicitud: $('#tipoSolicitud').val()},
            beforeSend: function () {
                dialog = BootstrapDialog.show({
                    title: 'Información',
                    message: 'Sus datos están siendo cargados. Por favor, espere.',
                    closable: false
                });
            },
            cache: false,
            success: function (aData) {
                dialog.close();
                $('#area').get(0).options.length = 0;
                $('#area').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#area').get(0).options[$('#area').get(0).options.length] = new Option(item[1], item[0]);
                    // Display      Value
                });

            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }
</script>



<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

