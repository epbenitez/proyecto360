<%-- 
    Document   : reporteEjecutivo
    Created on : 06-oct-2016, 13:48:42
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plan de Mantenimiento</title>

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

        <STYLE type="text/css">
            .verde {text-align: center; background-color: #8bc34a;}
            .amarillo {text-align: center; background-color: #E9F581;}
            .anaranjado {text-align: center; background-color: #FA9B84;}
            th { font-size: 9px; }
            td { font-size: 9px; }
            #listado tbody tr.odd:hover {
                background: none;
            }
        </STYLE>
        <script type="text/javascript" language="javascript" class="init">


            $(document).ready(function () {
                $('#filtro').bootstrapValidator({});
                $("body").removeClass("boxed-layout");
                $("#page-wrapper").addClass("nav-small");
                $('#calendario').hide();
                $("#buscar").click(function () {
                    reload();
                });

            });

            function reload() {

                $('#calendario').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable();
                    table.destroy();
                }

                var d = new Date();
                var strDate = d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear()

                table = $('#listado').DataTable({
                    "aoColumnDefs": [
                        {"sClass": "alignCenter",
                            "aTargets": [2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                        }
                    ],
                    dom: 'Bfrtip',
                    buttons: [
//                        {
//                            extend: 'pageLength',
//                            text: 'Tamaño de Página'
//                        },
                        'excel'
                                ,
//                        'pdf',
//                        {
//                            extend: 'print',
//                            text: 'Imprimir'
//                        }
                    ],
                    "createdRow": function (row, data, index) {
                        for (i = 2; i <= 13; i++) {
                            if (data[i].indexOf("Realizado") >= 0) {
                                $('td', row).eq(i).addClass('verde');
                            }
                            if (data[i].indexOf("Programado") >= 0) {
                                $('td', row).eq(i).addClass('amarillo');
                            }

                            if (data[i].indexOf("Pendiente") >= 0) {
                                $('td', row).eq(i).addClass('anaranjado');
                            }
                        }
                    },
                    "paging": false,
                    "ajax": '/ajax/reporteEjecutivoEquiposAjax.action?anio=' + $('#anio').val() + '&condominioId=' + $('#condominio').val(),
//                            + '&mesFin=' + $('#mesFin').val(),
                    "order": [],
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

                $('#listado_length').hide();
                $('#listado_filter').hide();
                $('#listado_info').hide();
                $('#listado_paginate').hide();
            }
        </script>
    </head>
    <body>
        <div class="container">
            <p></p>
            <h1>Plan de Mantenimiento</h1>
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
                <form id="filtro" name="filtro">
                    <div class="row col-lg-12 main-box" id="busquedaForm">
                        <div class="clearfix" >&nbsp;</div>
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
                        <div class="form-group">
                            <label class="col-lg-2 control-label text-right">
                                A&ntilde;o:
                            </label>
                            <div class="col-lg-9">
                                <s:select id="anio"  class="form-control" 
                                          list="anios"
                                          headerValue="-- Seleccione --"
                                          name="anio"
                                          data-bv-notempty="true"
                                          data-bv-notempty-message="Este campo es requerido"
                                          />
                            </div>
                        </div>

                        <div class="clearfix" >&nbsp;</div>
                        <div class="clearfix" >&nbsp;</div>
                        <div class="form-group">
                            <div class="col-lg-12 text-right ">
                                <a class="btn btn-default" href="#" id="buscar" class="btn btn-default">Ver Información</a>
                            </div>
                        </div>
                        <div class="clearfix" >&nbsp;</div>
                    </div>
                </form>
            </div>


            <div class="clearfix">&nbsp;</div>

            <div class="row white_box" id="calendario">
                <div class="col-md-12">

                    <div class="main-box clearfix">
                        <!--<input type="button" class="btn btn-success" onclick="openPdf()" value="PDF" />-->
                        <table id="listado" class="display  table-responsive">
                            <thead>
                                <tr>
                                    <th class="no-sort sorting_disabled text-center" id="mes">Equipo</th>
                                    <th class="no-sort sorting_disabled text-center" id="mes" width="10%">Frecuencia</th>
                                    <th class="no-sort dt-body-right" >Ene</th>
                                    <th class="no-sort dt-body-right" >Feb</th>
                                    <th class="no-sort dt-body-right" >Mar</th>
                                    <th class="no-sort dt-body-right" >Abr</th>
                                    <th class="no-sort dt-body-right" >May</th>
                                    <th class="no-sort dt-body-right" >Jun</th>
                                    <th class="no-sort dt-body-right" >Jul</th>
                                    <th class="no-sort dt-body-right" >Ago</th>
                                    <th class="no-sort dt-body-right" >Sep</th>
                                    <th class="no-sort dt-body-right" >Oct</th>
                                    <th class="no-sort dt-body-right" >Nov</th>
                                    <th class="no-sort dt-body-right" >Dic</th>
                                </tr>
                            </thead>
                        </table>
                    </div>

                </div>

            </div>

            <script type="text/javascript" language="javascript" class="init">
                $(document).ready(function () {
                    $('select').addClass('form-control');
                }
                );
            </script>
    </body>
</html>
