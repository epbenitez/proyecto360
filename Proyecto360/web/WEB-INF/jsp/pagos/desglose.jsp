<%-- 
    Document   : desglose
    Created on : 09-jun-2016, 21:55:04
    Author     : erikapatriciabenitezsoto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Desglose de conceptos por abono</title>
        <% String abonoDatosPagoId = request.getParameter("abonoDatosPagoId"); %>
        <script type="text/javascript" language="javascript" class="init">
            $(document).ready(function () {

                reload();
                
            });

            function reload() {

                $('#listado').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable();
                    table.destroy();
                }


                table = $('#listado').DataTable({
                    "aoColumnDefs": [
                        {"sClass": "alignRight",
                            "aTargets": [1]
                        }
                    ],
                    "paging": false,
                    "ajax": '/ajax/desglosePago.action?abonoDatosPagoId=<%=abonoDatosPagoId%>',
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
        <h1>Desglose de conceptos por abono</h1>
        <div class="container">
            <div id="detalle" class="col-lg-12">
                    <div class="main-box">
                        <table id="listado" class="table-hover">
                            <thead>
                                <tr>
                                    <th>Concepto</th>
                                    <th>Monto</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
        </div>
    </body>
</html>
