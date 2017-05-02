<%-- 
    Document   : gestion
    Created on : 28-feb-2017, 13:51:02
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


    <link rel="stylesheet" type="text/css" href="/dataTables/buttons/1.1.0/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="/dataTables/buttons/1.1.0/css/buttons.dataTables.min.css" />

    <script type="text/javascript" src="/dataTables/buttons/1.1.0/js/dataTables.buttons.min.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
    <script type="text/javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
    <script type="text/javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
    <script type="text/javascript" src="/dataTables/buttons/1.1.0/js/buttons.html5.min.js"></script>

    <script type="text/javascript" src="//cdn.datatables.net/buttons/1.1.0/js/buttons.print.min.js"></script>


    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {

//            $("body").removeClass("boxed-layout");

            $('select').addClass('form-control');
            $('input').addClass('form-control');

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
            });

            $('#listadoDiv').hide();

            $("#buscar").click(function () {
                reload();
            });



            //reload();
        });

        function reload() {


            var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();


            $('#listadoDiv').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/getListaMensajesNotificacionesAjax.action";

            url = url + "?condominioId=" + condominio + "&mensaje=" + $('#mensaje').val();

            $('#listado').DataTable({
                "ajax": {
                    "type": "GET",
                    "url": url
                },
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
                "bFilter": false
            });

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
    <h1>Mensajer&iacute;a</h1>

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
            <form id="filtro" name="filtro">
                <div class="form-group">
                    <div class="col-lg-12 text-left ">
                        <a href="/notificaciones/mensajeria/altaMensajeria.action" id="nuevo" class="btn btn-success fancybox fancybox.iframe">Nuevo Grupo</a>
                    </div>
                    <div>&nbsp;</div>
                </div>
                <security:authorize ifAnyGranted="ROLE_ADMIN">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre"
                                      name="condominio"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" 
                                      name="condominio"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifAnyGranted="ROLE_ADMINCONDOMINIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" 
                                      name="condominio"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>


                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Mensaje:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="mensaje" id="mensaje"
                                     />
                        <span class="help-block" id="tipoSolicitudMessage" />
                    </div>
                </div>

                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <div class="form-group">
                    <div class="col-lg-12 text-center ">
                        <!--<a href="#" id="buscar" class="btn btn-default">Buscar</a>-->
                        <button type="submit" id="buscar" class="btn btn-success">Buscar</button>
                    </div>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>

        <div class="clearfix" >&nbsp;</div>
        <div id="listadoDiv" class="row col-lg-12 main-box">

                <table id="listado" class="table-hover ">
                    <thead>
                        <tr>
                            <th width="5%">#</th>
                            <th width="10%">Condominio</th>
                            <th width="20%">Asunto</th>
                            <th width="20%">Mensaje</th>
                            <th width="10%">Fecha<br>Inicio</th>
                            <th width="10%">Fecha<br>Final</th>
                            <th width="5%">Estatus</th>
                            <th width="5%">Documentos</th>
                            <th width="5%"></th>
                            <th width="5%"></th>
                            <th width="5%"></th>
                        </tr>
                    </thead>
                </table>
        </div>

    </div>
</div>
<script type="text/javascript" language="javascript" class="init">


    function eliminaMensaje(notificacionId) {
        BootstrapDialog.confirm('Esto borrará el mensaje seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaMensajeNotificacionesAjax.action',
                    dataType: 'json',
                    data: {'grupo.id': grupoId
                    },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] !== "OK") {
                            BootstrapDialog.alert('El mensaje ha sido eliminado correctamente.');
                            reload();
                        } else {
                            BootstrapDialog.alert('No se pudo eliminar el mensaje indicado.');
                        }

                    },
                    error: function () {
                        alert("Connection Is Not Available");
                    }
                });

                return false;
            }
        });
    }
    </script>


<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>
