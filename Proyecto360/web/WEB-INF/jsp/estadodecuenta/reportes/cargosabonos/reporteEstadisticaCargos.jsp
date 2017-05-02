<%-- 
    Document   : reporteEstadisticaDetalleDeptoCargosAbonos.jsp
    Created on : 03-ago-2016, 18:11:40
    Author     : patriciabenitez
--%>
<%@taglib prefix="s" uri="/struts-tags" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cargos</title>    
        <link rel="stylesheet" type="text/css" href="/dataTables/buttons/1.1.0/css/jquery.dataTables.min.css" />
        <link rel="stylesheet" type="text/css" href="/dataTables/buttons/1.1.0/css/buttons.dataTables.min.css" />

        <script type="text/javascript" src="/dataTables/buttons/1.1.0/js/dataTables.buttons.min.js"></script>
        <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
        <script type="text/javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
        <script type="text/javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
        <script type="text/javascript" src="/dataTables/buttons/1.1.0/js/buttons.html5.min.js"></script>

        <script type="text/javascript" language="javascript" class="init">


            $(document).ready(function () {

                $('#detalleCargos').hide();
                reporteEstadisticaCargos();

            });

            function reporteEstadisticaCargos() {

                $('#detalleCargos').show();
                if ($.fn.dataTable.isDataTable('#cargos')) {
                    table = $('#cargos').DataTable();
                    table.destroy();
                }


                $('#cargos').DataTable({
                    "ajax": '/ajax/reporteEstadisticaCargosEstadoCuentaAjax?condominioId=' + $('#condominioId').val()
                            + '&anioInicio=' + $('#anioInicio').val()
                            + '&mesInicio=' + $('#mesInicio').val()
                            + '&anioFin=' + $('#anioInicio').val()
                            + '&mesFin=' + $('#mesInicio').val(),
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
                    "aoColumnDefs": [
                        {"sClass": "alignRight",
                            "aTargets": [5]
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

                $('#cargos_length').hide();
                $('#cargos_filter').hide();
                $('#cargos_info').hide();
//            $('#cargos_paginate').hide();

            }

        </script>
    </head>
    <body>
        <h3 style="margin-top: 5px; margin-left: 10px"><span>Consulta de Cargos por Condominio</span></h3>
        <s:hidden id="condominioId" name="condominioId" />
        <s:hidden id="anioInicio" name="anioInicio" />
        <s:hidden id="mesInicio" name="mesInicio" />

        <div id="detalleCargos" class="col-md-12">
            <div class="main-box clearfix">

                <table id="cargos" class="table-hover table-responsive">
                    <thead>
                        <tr>
                            <th class="no-sort" width="15%" >Condominio</th>
                            <th class="no-sort" width="10%" >Torre</th>
                            <th class="no-sort" width="20%" >Departamento</th>
                            <th class="no-sort" width="20%">Concepto</th>
                            <th class="no-sort" width="15%">Fecha</th>
                            <th class="no-sort" width="20%" >Monto</th>
                        </tr>
                    </thead>
                </table>

            </div>
        </div>

    </body>
</html>
