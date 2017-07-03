<%-- 
    Document   : privilegios
    Created on : 01-jul-2017, 19:59:43
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

            $("body").removeClass("boxed-layout");

            $('.fancybox').fancybox({
                autoSize: true,
                afterClose: function () {
                    reload();
                }
            });

            $('#filtro').bootstrapValidator({});
            $('#filtro').submit(function (evt) {
                evt.preventDefault();
                $('#buscar').removeAttr("disabled");
                $('#agregar').removeAttr("disabled");
            });

            $('#listadoDiv').hide();

            $("#buscar").click(function () {
                reload();
            });

            $("#agregar").click(function () {
                BootstrapDialog.confirm('Esto insertará un nuevo permiso, ¿Está seguro de proceder?', function (result) {
                    if (result) {
                        agregar();
                    } else {
                    }
                });
            });



            //reload();
        });

        function eliminar(id) {
            if (id === "") {
            } else {
                BootstrapDialog.confirm('Esto eliminará el permiso seleccionado, ¿Está seguro de proceder?', function (result) {
                    if (result) {

                        var dialog;
                        $.ajax({
                            type: 'POST',
                            url: '/ajax/eliminaPermisoSolicitudesAjax.action',
                            dataType: 'json',
                            data: {pkPermiso: id},
                            beforeSend: function () {
                                dialog = BootstrapDialog.show({
                                    title: 'Información',
                                    message: 'Estamos guardando su información. Por favor, espere.',
                                    closable: false
                                });
                            },
                            cache: false,
                            success: function (aData) {
                                dialog.close();
                                reload();
                            },
                            error: function () {
                                alert("Connection Is Not Available");
                            }
                        });
                    } else {

                    }
                });
            }
        }

        function agregar() {

            var servicios = $('input[name="pkTipoServicios"]:checked').serialize();
            var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();
            var tipoSolicitud = ($('#tipoSolicitud').val() === null) ? "" : $('input[name="tipoSolicitud"]:checked');
            var tipoPermiso = $('#tipoPermiso').val() === null ? "" : $('#tipoPermiso').val();
            var usuario = $('#usuario').val() === null ? "" : $('#usuario').val();
            if (condominio === "" || tipoSolicitud === "" || tipoPermiso === "" || usuario === "") {
                BootstrapDialog.show({
                    title: 'Atención',
                    message: 'Es necesario especificar todos los campos para poder agregar un nuevo permiso. Por favor, verifique.'
                });
                return false;
            }


            var dialog;
            $.ajax({
                type: 'POST',
                url: '/ajax/agregaPrivilegioSolicitudesAjax.action?'+servicios,
                dataType: 'json',
                data: {
                    pkCondominio: condominio,
                    pkUsuario: usuario,
                    pkTipoPermiso: tipoPermiso
                },
                beforeSend: function () {
                    dialog = BootstrapDialog.show({
                        title: 'Información',
                        message: 'Estamos guardando su información. Por favor, espere.',
                        closable: false
                    });
                },
                cache: false,
                success: function (aData) {
//                    alert(aData.data[0]);
                    dialog.close();
                    BootstrapDialog.show({
                        title: 'Información',
                        message: aData.data[0]
                    });
                    reload();
                },
                error: function () {
                    alert("Connection Is Not Available");
                }
            });
            return false;
        }

        function reload() {

            var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();
            var tipoSolicitud = ($('#tipoSolicitud').val() === null) ? "" : $('.tipoSolicitud:checked').val();
            var tipoPermiso = $('#tipoPermiso').val() === null ? "" : $('#tipoPermiso').val();
            var usuario = $('#usuario').val() === null ? "" : $('#usuario').val();

            if (condominio === "") {
                BootstrapDialog.show({
                    title: 'Atención',
                    message: 'Es necesario especificar por lo menos el inmueble para poder realizar una búsqueda. Por favor, verifique.'
                });
            } else {

                $.fancybox.close();
                $('#listadoDiv').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable({
                        destroy: true,
                        responsive: true
                    });
                    table.destroy();
                }
                var url = "/ajax/buscaPermisosSolicitudesAjax.action";

                url = url + "?pkCondominio=" + condominio + "&pkUsuario=" + usuario
                        + "&pkTipo=" + tipoSolicitud + "&pkTipoPermiso=" + tipoPermiso;

                $('#listado').DataTable({
                    "ajax": url,
//                    "bProcessing": true,
                    "language": {
                        "sProcessing": "Buscando información...",
                        "sLengthMenu": "Mostrar _MENU_ registros",
                        "sZeroRecords": "No se encontraron resultados",
                        "sEmptyTable": "Ningún dato disponible en esta tabla",
                        "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                        "sInfoPostFix": "",
                        "sSearch": "Buscar:",
                        "sUrl": "",
                        "sInfoThousands": ",",
                        "sLoadingRecords": "Cargando...",
                        "oPaginate": {
                            "sFirst": "Primero",
                            "sLast": "Último",
                            "sNext": "Siguiente",
                            "sPrevious": "Anterior"
                        },
                        "oAria": {
                            "sSortAscending": ": Activar para ordenar la columna de manera ascendente",
                            "sSortDescending": ": Activar para ordenar la columna de manera descendente"
                        }
                    },
                    "order": [[0, 'asc'], [3, 'asc']],
                    "bFilter": false
                });
            }
        }

    </script>
    <security:authorize ifAnyGranted="ROLE_ADMIN">
        <style type="text/css">
            td, tr {font-size:11px}
        </style>
    </security:authorize>
</head>
<div class="container">
    <p></p>
    <h1>Gesti&oacute;n de Permisos para Atención/Apertura de Solicitudes</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>B&uacute;squeda</span></h3>
            <div class="clearfix" >&nbsp;</div>
            <form id="filtro" name="filtro">
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Inmueble:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="condominio"  class="form-control" 
                                  list="condominios" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="condominio"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Usuario:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="usuario"  class="form-control" 
                                  list="usuarios" listKey="id" listValue="usuario" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="usuario"
                                  />
                        <span class="help-block" id="estadoSolicitudMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Permiso:
                    </label>
                    <div class="col-lg-9">
                        <s:select class="form-control" name="tipoPermiso" id="tipoPermiso"  list="service.tipoPermiso" 
                                  headerKey=""
                                  headerValue="-- Seleccione --" />
                        <span class="help-block" id="estadoSolicitudMessage" />
                    </div>
                </div>
                        
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo de Servicio:
                    </label>
                    <div class="col-lg-9">
                        <s:checkboxlist list="tipos" name="pkTipoServicios" id="tipoSolicitud" listKey="id" listValue="nombre" />
                        <span class="help-block" id="estadoSolicitudMessage"  />
                    </div>
                </div>

                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <div class="form-group">
                    <div class="col-lg-6 text-right ">
                        <!--<a href="#" id="buscar" class="btn btn-default">Buscar</a>-->
                        <button type="submit" id="buscar" class="btn btn-success">Buscar</button>
                        <button type="submit" id="agregar" class="btn btn-success">Agregar</button>
                    </div>

                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>

        <div class="clearfix" >&nbsp;</div>
        <div id="listadoDiv" class="col-lg-12">
            <div class="main-box">
                <table id="listado" class="table-hover">
                    <thead>
                        <tr>
                            <th>Inmueble</th>
                            <th>Tipo de Servicio</th>
                            <th>Permiso</th>
                            <th>Usuario</th>
                            <th width="5%"></th>
                        </tr>
                    </thead>
                </table>

            </div>
        </div>
    </div>
</div>
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('select').addClass('form-control');
    }
    );
</script>




<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>
