<%-- 
    Document   : reporteEstadisticaCargosAbonos
    Created on : 27-jul-2016, 20:42:59
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<head>
    <title>Consulta de Cargos y Abonos</title>
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

            $('.fancybox').fancybox({
                autoSize: true,
                afterClose: function () {
                    //reload();
                }
            });

            $('#cargaForm').bootstrapValidator({});
            $('#desglose').hide();
            $('#detalle').hide();
            $('#detalleCargoDiv').hide();
            $('#detalleAbonoDiv').hide();

            $("#buscar").click(function () {
                if ($('#anioInicio').val() !== "" && $('#mesInicio').val() !== "" && $('#anioFin').val() !== "" && $('#mesFin').val()) {
                    reload();
                }
            });

        });

        function reload() {

            $('#desglose').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable();
                table.destroy();
            }

            var table = $('#listado').DataTable({
                "ajax": '/ajax/reporteEstadisticaCargosAbonosEstadoCuentaAjax?condominioId=' + $('#condominioId').val()
                        + '&anioInicio=' + $('#anioInicio').val()
                        + '&mesInicio=' + $('#mesInicio').val()
                        + '&anioFin=' + $('#anioFin').val()
                        + '&mesFin=' + $('#mesFin').val(),
                "order": [],
                "aoColumnDefs": [
                    {"sClass": "alignRight",
                        "aTargets": [2, 3, 4]
                    },
                    {"sClass": "alignCenter",
                        "aTargets": [0, 1]
                    }
                ],
                dom: 'Bfrtip',
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
                "language": {
                    "sProcessing": "Procesando...",
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
                }
            });

            $('#listado_length').hide();
            $('#listado_filter').hide();
            $('#listado_info').hide();
//            $('#listado_paginate').hide();

            $('#listado tbody').on('click', 'a', function () {
                    table.$('tr.selected').removeClass('selected');
                    $(this).parent().parent().addClass('selected');
            });

        }

        function reporteEstadisticaDetalleDepto(anio, mes, condominioId) {

            $('#detalle').show();
            $('#detalleCargoDiv').hide();
            $('#detalleAbonoDiv').hide();
            if ($.fn.dataTable.isDataTable('#listadoDetalle')) {
                table = $('#listadoDetalle').DataTable();
                table.destroy();
            }

            var table = $('#listadoDetalle').DataTable({
                "ajax": '/ajax/reporteEstadisticaDetalleDeptoEstadoCuentaAjax?condominioId=' + condominioId
                        + '&anioInicio=' + anio
                        + '&mesInicio=' + mes
                        + '&anioFin=' + anio
                        + '&mesFin=' + mes,
                "order": [2],
                dom: 'Bfrtip',
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
                "aoColumnDefs": [
                    {"sClass": "alignRight",
                        "aTargets": [5, 6, 7]
                    },
                    {"sClass": "alignCenter",
                        "aTargets": [0, 1, 2, 3, 4]
                    }
                ],
                dom: 'Bfrtip',
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
                "language": {
                    "sProcessing": "Procesando...",
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
                }
            });

            $('#listadoDetalle_length').hide();
            $('#listadoDetalle_filter').hide();
            $('#listadoDetalle_info').hide();
//            $('#listado_paginate').hide();

            $('#listadoDetalle tbody').on('click', 'a', function () {
                    table.$('tr.selected').removeClass('selected');
                    $(this).parent().parent().addClass('selected');
            });
        }

        function detalle(anio, mes, departamentoId) {

            $('#detalleCargoDiv').show();
            if ($.fn.dataTable.isDataTable('#detalleCargo')) {
                table = $('#detalleCargo').DataTable();
                table.destroy();
            }

            table = $('#detalleCargo').DataTable({
                "aoColumnDefs": [
                    {"sClass": "alignRight",
                        "aTargets": [3, 4]
                    }
                ],
                "ajax": '/ajax/detalleCobroEstadoCuentaAjax.action?anioInicio=' + anio
                        + '&mesInicio=' + mes
                        + '&anioFin=' + anio
                        + '&departamentoId=' + departamentoId
                        + '&mesFin=' + mes,
                "order": [],
                dom: 'Bfrtip',
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
                "columnDefs": [{
                        "targets": 'no-sort',
                        "orderable": false,
                    }],
                "language": {
                    "sProcessing": "Procesando...",
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
                }
            });
            $('#detalleCargo_length').hide();
            $('#detalleCargo_filter').hide();
            $('#detalleCargo_info').hide();
            $('#detalleCargo_paginate').hide();
            
        }


        function detalleAbono(anio, mes, departamentoId) {

            $('#detalleAbonoDiv').show();
            if ($.fn.dataTable.isDataTable('#detalleAbono')) {
                table = $('#detalleAbono').DataTable();
                table.destroy();
            }

            table = $('#detalleAbono').DataTable({
                "aoColumnDefs": [
                    {"sClass": "alignRight",
                        "aTargets": [4]
                    }
                ],
                "ajax": '/ajax/detalleAbonoEstadoCuentaAjax.action?anioInicio=' + anio
                        + '&mesInicio=' + mes
                        + '&anioFin=' + anio
                        + '&departamentoId=' + departamentoId
                        + '&mesFin=' + mes,
                "order": [],
                dom: 'Bfrtip',
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
                "columnDefs": [{
                        "targets": 'no-sort',
                        "orderable": false,
                    }],
                "language": {
                    "sProcessing": "Procesando...",
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
                }
            });
            $('#detalleAbono_length').hide();
            $('#detalleAbono_filter').hide();
            $('#detalleAbono_info').hide();
            $('#detalleAbono_paginate').hide();
        }
    </script>

</head>
<div class="col-lg-12">
    <div class="main-box clearfix">
        <div class="clearfix">&nbsp;</div>  
        <div class="col-md-12">
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

        <div class="col-lg-12">

            <h3 style="margin-top: 5px; margin-left: 10px"><span>Consulta de Cargos y Abonos</span></h3>
            <div class="col-md-2">
                <label for="maskedDate">Condominio:</label>
            </div>
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                        <s:select id="condominioId"  
                                  class="form-control"
                                  name="condominioId"
                                  list="condominiolist" 
                                  listKey="id" 
                                  listValue="nombre"  />
                    <span class="help-block" id="estadoMessage" />
                </div>
            </div>


            <div class="row">
                <form id="filtro" name="filtro" onsubmit="return false;">
                    <div class="col-lg-12">
                        <div class="main-box clearfix">

                            <h3 style="margin-top: 5px; margin-left: 10px"><span>Filtro</span></h3>
                            <div class="col-sm-2">
                                <label for="maskedDate">Fecha de Inicio:</label>
                            </div>
                            <div class="col-sm-5">
                                <label for="maskedDate">A&ntilde;o:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <s:select class="form-control" name="anioInicio" list="service.anios" 
                                                  headerKey=""
                                                  required="true" 
                                                  data-bv-notempty="true"
                                                  data-bv-notempty-message="Campo requerido"/>
                                </div>
                            </div>

                            <div class="col-sm-5">
                                <label for="maskedDate">Mes:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <s:select class="form-control" name="mesInicio" list="service.mesesAnio" 
                                                  headerKey=""
                                                  required="true" 
                                                  data-bv-notempty="true"
                                                  data-bv-notempty-message="Campo requerido" />
                                </div>
                            </div>
                            <div class="clearfix">&nbsp;</div>  
                            <div class="col-sm-2">
                                <label for="maskedDate">Fecha de Fin</label>
                            </div>
                            <div class="col-sm-5">
                                <label for="maskedDate">A&ntilde;o:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <s:select class="form-control" name="anioFin" list="service.anios" 
                                                  headerKey=""
                                                  required="true" 
                                                  data-bv-notempty="true"
                                                  data-bv-notempty-message="Campo requerido" />
                                </div>
                            </div>

                            <div class="col-sm-5">
                                <label for="maskedDate">Mes:</label>
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <s:select class="form-control" name="mesFin" list="service.mesesAnio" 
                                                  headerKey=""
                                                  required="true" 
                                                  data-bv-notempty="true"
                                                  data-bv-notempty-message="Campo requerido" />
                                </div>
                            </div>
                            <div class="clearfix">&nbsp;</div>  

                            <div class="col-sm-12">
                                <button  id="buscar" class="btn btn-success">Consultar</button>
                            </div>
                            <div class="clearfix">&nbsp;</div>  

                        </div>
                    </div>
                </form>
            </div>

        </div>

    </div>
</div>

<div id="desglose" class="col-md-12">
    <div class="main-box clearfix">

        <table id="listado" class="table-hover table-responsive">
            <thead>
                <tr>
                    <th class="no-sort" width="10%">A&ntilde;</th>
                    <th class="no-sort" width="10%">Mes</th>
                    <th class="no-sort" width="30%" >Cargos</th>
                    <th class="no-sort" width="30%" >Abonos</th>
                    <th class="no-sort" width="20%" >Saldo</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<div id="detalle" class="col-md-12">
    <div class="main-box clearfix">

        <table id="listadoDetalle" class="table-hover table-responsive">
            <thead>
                <tr>
                    <th class="no-sort" width="10%" >Condominio</th>
                    <th class="no-sort" width="10%" >Torre</th>
                    <th class="no-sort" width="10%" >Departamento</th>
                    <th class="no-sort" width="10%">A&ntilde;o</th>
                    <th class="no-sort" width="10%">Mes</th>
                    <th class="no-sort" width="20%" >Cargos</th>
                    <th class="no-sort" width="20%" >Abonos</th>
                    <th class="no-sort" width="10%" >Saldo</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<div class="row white_box" id="detalleCargoDiv">
    <div class="col-md-12">
        <h3 style="margin-top: 5px; margin-left: 10px"><span>Detalle de Cargos del Mes</span></h3>

        <div class="main-box clearfix">
            <table id="detalleCargo" class="table-hover table-responsive">
                <thead>
                    <tr>
                        <th class="no-sort sorting_disabled text-center" id="anio" width="10%">Año</th>
                        <th class="no-sort sorting_disabled text-center" id="mes" width="10%">Mes</th>
                        <th class="no-sort sorting_disabled text-center" id="concepto" width="40%">Concepto</th>
                        <th class="no-sort text-center dt-body-center" width="20%" >Monto a pagar hasta el d&iacute;a 10</th>
                        <th class="no-sort text-center" width="20%" >Monto a pagar despu&eacute;s del d&iacute;a 10</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>

</div>


<div id="seccionDetalleAbono" class="clearfix">&nbsp;</div>

<div class="row white_box" id="detalleAbonoDiv">
    <div class="col-md-12">
        <h3 style="margin-top: 5px; margin-left: 10px"><span>Detalle de Abonos del Mes</span></h3>

        <div class="main-box clearfix">
            <table id="detalleAbono" class="table-hover table-responsive">
                <thead>
                    <tr>
                        <th class="no-sort sorting_disabled text-center" id="anio" width="10%">Año</th>
                        <th class="no-sort sorting_disabled text-center" id="mes" width="10%">Mes</th>
                        <th class="no-sort sorting_disabled text-center" id="mes" width="10%">D&iacute;a</th>
                        <th class="no-sort sorting_disabled text-center" id="concepto" width="50%">Concepto</th>
                        <th class="no-sort text-center" width="20%" >Monto</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>

</div>

<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

<script>

                    $('input[type=text]').addClass('form-control');
                    $('select').addClass('form-control');

</script>
