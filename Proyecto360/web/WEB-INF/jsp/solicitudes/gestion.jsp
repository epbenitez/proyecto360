<%-- 
    Document   : gestion
    Created on : 26-feb-2016, 16:17:41
    Author     : luistlatelpa
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
            
            $("body").removeClass("boxed-layout");

            $('.fancybox').fancybox({
                autoSize: true,
                afterClose: function () {
                    reload();
                }
            });
            
            $("#condominio").change(function () {
                getTipoImnueble();
                $("#tiposInmueble").get(0).options.length = 0;
                $("#tipoServicio").get(0).options.length = 0;
                $("#area").get(0).options.length = 0;
                $("#categoriaSolicitud").get(0).options.length = 0;
            });
            
            <security:authorize ifAnyGranted="ROLE_ADMINCONDOMINIO">
//                 getTipoImnueble();  
//            $('#condominio').attr('disabled', 'disabled');
            </security:authorize>


            $("#tiposInmueble").change(function () {
                getTipoServicio();
            });

            $("#tipoServicio").change(function () {
                getAreas();
            });

            $("#area").change(function () {
                getCategorias();
            });

            $('#filtro').bootstrapValidator({});
            $('#filtro').submit(function (evt) {
                evt.preventDefault();
                $('#buscar').removeAttr("disabled");
            });

            $('#listadoDiv').hide();
            $('#significados').hide();

            $("#buscar").click(function () {
                reload();
            });

        <security:authorize ifAnyGranted="ROLE_ADMINCONDOMINIO,ROLE_ADMIN">
            /*   Los roles se cargan de acuerdo a los permisos para atender */
//            $("#condominio").change(function () {
//                getTorres();
//            });
            $("#torre").change(function () {
                getDepartamentos();
            });

        </security:authorize>


        });

        function reload() {


            var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();
            var torre = $('#torre').val() === null ? "" : $('#torre').val();
            var departamento = ($('#departamento').val() === null) ? "" : $('#departamento').val();

//            if (condominio === "") {
//
//            } else {

                $.fancybox.close();
                $('#listadoDiv').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable({
                        destroy: true,
                        responsive: true
                    });
                    table.destroy();
                }
                var url = "/ajax/busquedaSolicitudesAjax.action";

                url = url + "?pkCondominio=" + condominio + "&pkTorre=" + torre + "&pkDepartamento=" + departamento
                        + "&pkTipo=" + $('#tipoSolicitud').val() + "&pkEstado=" + $('#estadoSolicitud').val()
                        + "&pkAreaId=" + $('#area').val()
                        + "&pkCategoria=" + $('#categoriaSolicitud').val();

                $('#listado').DataTable({
                dom: 'Bfrtip',
                lengthMenu: [
                    [10, 25, 50, -1],
                    ['10 registros', '25 registros', '50 registros', 'Todos']
                ],
                buttons: [
                    {
                        extend: 'pageLength',
                        text: 'Tamaño de Página'
                    },
                    'excel'
//                    ,
//                    'pdf',
//                    {
//                        extend: 'print',
//                        text: 'Imprimir'
//                    }
                ],
                    "ajax": {
                                "type" : "GET",
                                "url" : url,
                                "dataSrc": function ( json ) {
                                    //Make your callback here.
                                    $('[data-toggle="tooltip"]').tooltip();  
//                                    $('#significados').show();
                                    return json.data;
                                }
                            },
//            url,
//                    "bProcessing": true,
        <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    "columnDefs": [
                        {
                            "targets": [7,8,9,10,11],
                            "visible": false,
                            "searchable": false
                        }
                    ],
        </security:authorize>
        <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                    "columnDefs": [
                        {
                            "targets": [11],
                            "visible": false,
                            "searchable": false
                        }
                    ],
        </security:authorize>
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
                
//            }
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
    <h1>Sistema de Tickets</h1>

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
                        <a href="/solicitudes/altaSolicitudes.action" id="nuevo" class="btn btn-success fancybox fancybox.iframe">Nuevo Ticket</a>
                    </div>
                    <div>&nbsp;</div>
                </div>
                <security:authorize ifAnyGranted="ROLE_ADMIN">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Inmueble:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="condominio"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Inmueble:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue=" nombre"headerKey=""
                                      headerValue="-- Seleccione --"
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
                            Inmueble:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre"  headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="condominio"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>
                
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo de Inmueble
                    </label>
                    <div class="col-lg-9">
                        <s:select id="tiposInmueble"  class="form-control" 
                                  list="#{null}"
                                  headerValue="-- Seleccione --"
                                  name="solicitud.tipoInmuebleSolicitud.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="tipoSolicitudMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo de Servicio
                    </label>
                    <div class="col-lg-9">
                        <s:select id="tipoServicio"  class="form-control" 
                                  list="#{null}"
                                  headerValue="-- Seleccione --"
                                  name="solicitud.tipoServicio.id"
                                  />
                        <span class="help-block" id="tipoServicioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        &Aacute;rea
                    </label>
                    <div class="col-lg-9">
                        <s:select id="area"  class="form-control" 
                                  list="#{null}"
                                  headerValue="-- Seleccione --"
                                  name="solicitud.area.id"
                                  />
                        <span class="help-block" id="areaMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo de trabajo
                    </label>
                    <div class="col-lg-9">
                        <s:select id="categoriaSolicitud"  class="form-control" 
                                  list="#{null}"
                                  headerValue="-- Seleccione --"
                                  name="solicitud.categoriaSolicitud.id"
                                  />
                        <span class="help-block" id="categoriaSolicitudMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Estado:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="estadoSolicitud"  class="form-control" 
                                  list="estados" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="estadoSolicitud"
                                  />
                        <span class="help-block" id="estadoSolicitudMessage" />
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
            <div class="main-box">
                <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_PROPIETARIO">
                    <table id="listado" class="table-hover">
                        <thead>
                            <tr>
                                <th>Inmueble</th>
<!--                                <th>Torre</th>
                                <th>Depto.</th>-->
                                <th>Tipo de Servicio</th>
                                <th>Estatus</th>
                                <th>Ingreso de<br> Ticket</th>
                                <!--                                <th>Fecha de Lectura</th>-->
                                                                <th>Fecha <br>Programada</th>
                                <th>Fecha Atenci&oacute;n</th>
                                <th>Folio</th>
                                
                                <th>Usuario que Registra</th>
                                <!--<th>Condómino que solicita</th>-->
                                <th>Comentario</th>
                                <th>Atendio</th>
                                <th width="5%"></th>
                                <th width="5%"></th>
                            </tr>
                        </thead>
                    </table>
                </security:authorize>
                <security:authorize ifAnyGranted="ROLE_ADMINCONDOMINIO">
                    <table id="listado" class="table-hover ">
                        <thead>
                            <tr>
<!--                                <th width="10%">Torre</th>
                                --><th width="10%">Inmueble</th>
                                <th width="15%">Tipo de<br> Servicio</th>
                                <th width="10%">Estatus</th>
                                <th width="10%">Ingreso de<br> Ticket</th>
                                <!--                                <th width="10%">Fecha de<br> Lectura</th>
                                -->
                                 <th>Fecha <br>Programada</th>
                                <th width="10%">Fecha <br>Soluci&oacute;n</th>
                                <th width="15%">Folio</th>
                                <th>Usuario que Registra</th>
                                <!--<th>Condómino que solicita</th>-->
                                <th>Comentario</th>
                                <th>Atendio</th>
                                
                                <th width="10%"></th>
                                <th width="10%"></th>
                                
                            </tr>
                        </thead>
                    </table>
                </security:authorize>
            </div>
        </div>
        <div id="significados">
            <div class="col-lg-12">
                <div class="col-lg-3 text-right ">
                    <span style="color:#cef9ff" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Solicitudes Atendidas
                </div>
                <div class="col-lg-3 text-right ">
                    <span style="color:green" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Solicitudes en Tiempo
                </div>
            </div>
            <div class="col-lg-12">
                <div class="col-lg-3 text-right ">
                    <span style="color:orange" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Solicitudes que requieren atenci&oacute;n
                </div>
                <div class="col-lg-3 text-right ">
                    <span style="color:red" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Solicitudes que requieren atenci&oacute;n urgente
                </div>
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

<script>

    function getTipoImnueble() {
        $.ajax({
            type: 'POST',
            url: '/ajax/getTipoImnuebleSolicitudesAjax.action',
            dataType: 'json',
            data: {pkCondominio: $('#condominio').val()},
            cache: false,
            success: function (aData) {

//                $('#tiposInmueble').get(0).options.length = 0;
//                $('#tiposInmueble').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#tiposInmueble').get(0).options[$('#tiposInmueble').get(0).options.length] = new Option(item[1], item[0]);
                    // Display      Value
                    $('#tiposInmueble').val(item[0]);
                    $('#tiposInmueble').prop('disabled', true);
                    getTipoServicio();
                });

            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }
    function getTipoServicio() {

        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/getTipoServicioSolicitudesAjax.action',
            dataType: 'json',
            data: { busqueda: true,
                    pkTipoInmueble: $('#tiposInmueble').val()},
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
                $('#tipoServicio').get(0).options.length = 0;
                $('#tipoServicio').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#tipoServicio').get(0).options[$('#tipoServicio').get(0).options.length] = new Option(item[1], item[0]);
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
            data: {
                pkTipoInmueble: $('#tiposInmueble').val(),
                pkTipoSolicitud: $('#tipoServicio').val()
            },
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

    function getCategorias() {

        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/getCategoriasSolicitudesAjax.action',
            dataType: 'json',
            data: {
                pkTipoInmueble: $('#tiposInmueble').val(),
                pkTipoSolicitud: $('#tipoServicio').val(),
                pkAreaId: $('#area').val()},
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
                $('#categoriaSolicitud').get(0).options.length = 0;
                $('#categoriaSolicitud').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#categoriaSolicitud').get(0).options[$('#categoriaSolicitud').get(0).options.length] = new Option(item[1], item[0]);
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
