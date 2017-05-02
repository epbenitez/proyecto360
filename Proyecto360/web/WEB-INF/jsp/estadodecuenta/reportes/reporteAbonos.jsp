<%-- 
    Document   : abonos
    Created on : 16-may-2016, 22:20:42
    Author     : erikapatriciabenitezsoto
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<head>
    <title>Reporte de Abonos</title>
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

            $('#cargaForm').bootstrapValidator({});
            $('#desglose').hide();

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

            $('#listado').DataTable({
                "ajax": '/ajax/reporteAbonosEstadoCuentaAjax?condominioId=' + $('#condominioId').val()
                        + '&anioInicio=' + $('#anioInicio').val()
                        + '&mesInicio=' + $('#mesInicio').val()
                        + '&anioFin=' + $('#anioFin').val()
                        + '&mesFin=' + $('#mesFin').val()
                        + '&tipoAbono=' + $('#tipoAbono').val(),
                "order": [],
                "aoColumnDefs": [
                    {"sClass": "alignRight",
                        "aTargets": [6]
                    },
                    {"sClass": "alignCenter",
                        "aTargets": [0, 1, 2, 3, 4]
                    },
                    {
                        "aTargets": [7, 8, 9, 10, 11],
                        "visible": false,
                        "searchable": false
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

            <h3 style="margin-top: 5px; margin-left: 10px"><span>Reporte de Abonos</span></h3>
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
            <div class="col-md-2">
                <label for="maskedDate">Tipo de Abono:</label>
            </div>
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-dollar"></i></span>
                        <s:select class="form-control" name="tipoAbono" id="tipoAbono"  list="service.tipoAbonos"  />
                    <span class="help-block" id="estadoMessage" />
                </div>
            </div>
            <div class="clearfix">&nbsp;</div>  

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
                    <th class="no-sort" width="10%">Torre</th>
                    <th class="no-sort" width="10%">Departamento</th>
                    <th class="no-sort" width="8%">A&ntilde;o</th>
                    <th class="no-sort" width="8%">Mes</th>
                    <th class="no-sort" width="8%">D&iacute;a</th>
                    <th class="no-sort" width="40%" >Concepto</th>
                    <th class="no-sort" width="10%" >Monto</th>

                    <th class="no-sort" width="1%" >Tipo de Pago</th>
                    <th class="no-sort" width="1%" >Núm Che/Dep/Tra</th>
                    <th class="no-sort" width="1%" >Fecha Emisi&oacute;n</th>
                    <th class="no-sort" width="1%" >Banco</th>
                    <th class="no-sort" width="1%" >Titular Emisor</th>
                    <th class="no-sort" width="1%" >Tipo Reporte</th>
                </tr>
            </thead>
        </table>

    </div>
</div>

<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

<script>

                    $('input[type=text]').addClass('form-control');
                    $('select').addClass('form-control');

</script>
