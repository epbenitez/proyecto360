<%-- 
    Document   : indicadores
    Created on : 14-dic-2016, 22:34:46
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Indicadores de Tareas y Proyectos</title>
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

            $('#listadoDiv').hide();




        });

        function reload(indiceId, titulo) {

            $.fancybox.close();
            $('#listadoDiv').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable({
                    destroy: true
//                        ,responsive: true
                });
                table.destroy();
            }
            $("#titulo").html(titulo);
            var url = "/ajax/indicesProyectosAjax.action?indiceId=" + indiceId;

            $('#listado').DataTable({
                dom: 'Bfrtip',
                buttons: [
//                        {
//                            extend: 'pageLength',
//                            text: 'Tamaño de Página'
//                        },
                    'excel'
                ],
                "ajax": {
                    "type": "GET",
                    "url": url,
                    "dataSrc": function (json) {
                        //Make your callback here.
//                                    $('[data-toggle="tooltip"]').tooltip();  
//                                    $('#significados').show();
                        return json.data;
                    }
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
                "order": [[0, 'asc'], [3, 'asc']],
                "bFilter": false
            });

        }


    </script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load("current", {packages: ["corechart"]});
        google.charts.setOnLoadCallback(abiertos);
        function abiertos() {
            var diferencia = (<s:property value="indicadores.total" /> -<s:property value="indicadores.abiertos" />);
            var data = google.visualization.arrayToDataTable([
                ['Abiertos', 'Percentage'],
                ['Abiertos', <s:property value="indicadores.abiertos" />],
                ['No Abiertos', diferencia]
            ]);

            var options = {
                legend: 'none',
                title: 'Abiertos',
                pieSliceText: 'percentage',
                pieStartAngle: 1,
//          tooltip: { trigger: 'none' },
                slices: {
                    0: {color: '#939313'},
                    1: {color: '#FBFB84'}
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('abiertos'));
            chart.draw(data, options);
        }

        google.charts.setOnLoadCallback(vencidos);
        function vencidos() {
            var data = google.visualization.arrayToDataTable([
                ['vencidos', 'Percentage'],
                ['Vencidos', <s:property value="indicadores.vencidos" />],
                ['No Vencidos', (<s:property value="indicadores.total" /> -<s:property value="indicadores.vencidos" />)]
            ]);

            var options = {
                legend: 'none',
                title: 'Vencidos',
                pieSliceText: 'percentage',
                pieStartAngle: 1,
//          tooltip: { trigger: 'none' },
                slices: {
                    0: {color: '#9FC94C'},
                    1: {color: '#C4EB7B'}
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('vencidos'));
            chart.draw(data, options);
        }


        google.charts.setOnLoadCallback(enTiempo);
        function enTiempo() {
            var data = google.visualization.arrayToDataTable([
                ['enTiempo', 'Percentage'],
                ['En Tiempo', <s:property value="indicadores.enTiempo" />],
                ['Restraso', (<s:property value="indicadores.total" /> -<s:property value="indicadores.enTiempo" />)]
            ]);

            var options = {
                legend: 'none',
                title: 'En Tiempo',
                pieSliceText: 'percentage',
                pieStartAngle: 1,
//          tooltip: { trigger: 'none' },
                slices: {
                    0: {color: '#AC417E'},
                    1: {color: '#CA6AA0'}
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('enTiempo'));
            chart.draw(data, options);
        }

        google.charts.setOnLoadCallback(visibles);
        function visibles() {
            var data = google.visualization.arrayToDataTable([
                ['visibles', 'Percentage'],
                ['Visibles', <s:property value="indicadores.visibles" />],
                ['Ocultos', (<s:property value="indicadores.total" /> -<s:property value="indicadores.visibles" />)]
            ]);

            var options = {
                legend: 'none',
                width: '100%',
                height: '100%',
                title: 'Visibles',
                pieSliceText: 'percentage',
                pieStartAngle: 1,
//          tooltip: { trigger: 'none' },
                slices: {
                    0: {color: '#723B90'},
                    1: {color: '#8D5CA8'}
                }
            };

            var chart = new google.visualization.PieChart(document.getElementById('visibles'));
            chart.draw(data, options);
        }
    </script>
    <style type="text/css">
        td, tr {font-size:11px}
    </style>
</head>
<div class="container">
    <p></p>
    <h1>Indicadores de Proyectos y Tareas</h1>

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
            <h2 style="margin-left: 10px" >Indicadores principales de tareas o proyectos.<br>
            </h2>
            <div id="indicadoresDiv" class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <div class="form-group">
                <div class="col-lg-2 text-center ">
                    <div id="abiertos" style="width: 200px; height: 200px;"></div>
                    <a href="#indicadoresDiv" onclick="reload(1, 'Abiertos')">Ver Informaci&oacute;n</a>
                </div>
                <div class="col-lg-2 text-center ">
                    <div id="vencidos" style="width: 200px; height: 200px;"></div>
                    <a href="#indicadoresDiv" onclick="reload(2, 'Vencidos')">Ver Informaci&oacute;n </a>
                </div>
                <div class="col-lg-2 text-center ">
                    <div id="enTiempo" style="width: 200px; height: 200px;"></div>
                    <a href="#indicadoresDiv" onclick="reload(3, 'En Tiempo')">Ver Informaci&oacute;n </a>
                </div>
                <div class="col-lg-2 text-center ">
                    <div id="visibles" style="width: 200px; height: 200px;"></div>
                    <a href="#indicadoresDiv" onclick="reload(4, 'Visibles')">Ver Informaci&oacute;n </a>
                </div>
            </div>



            <div class="clearfix" >&nbsp;</div>
            <div class="clearfix" >&nbsp;</div>
            <div class="form-group">
                <div class="col-lg-12 text-center ">
                    <button type="submit" id="enviar" class="btn btn-success">Buscar</button>
                </div>
            </div>
            <div class="clearfix" >&nbsp;</div>
        </div>
        <div class="clearfix" >&nbsp;</div>
    </div>

    <div class="clearfix" >&nbsp;</div>

    <h3 id="titulo"></h3>
    <div id="listadoDiv" class="row main_content">
        <div class="row col-lg-12 main-box" id="busquedaForm">
            <table id="listado" class="table-hover">
                <thead>
                    <tr>
                        <th>Descripci&oacute;n</th>
                        <th>Categor&iacute;a</th>
                        <th>Tipo</th>
                        <th>Prioridad</th>
                        <th>Estatus</th>
                        <th>Cuota</th>
                        <th>Visible</th>
                        <!--<th>Siguiente Tarea</th>-->
                        <th>Fecha de Registro</th>
                        <th>D&iacute;as Transcurridos</th>
                        <th>Fecha Vencimiento</th>
                        <th>D&iacute;as Restantes</th>
                        <th>Color</th>
                        <th class="dt-body-center" width="5%"></th>
                        <th class="dt-body-center" width="5%"></th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>

</div>
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {

        $('#gestionProyectosForm').bootstrapValidator({});

        $('select').addClass('form-control');
        $('textarea').addClass('form-control');
        $('input').addClass('form-control');
    }
    );
</script>
