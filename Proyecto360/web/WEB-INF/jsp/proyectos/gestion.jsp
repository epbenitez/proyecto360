<%-- 
    Document   : gestion
    Created on : 09-dic-2016, 21:34:46
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Gestión de Tareas y Proyectos</title>
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
            $('#significados').hide();

            $('#enviar').click(function () {
                if ($('#condominio').val() === null ||
                        $('#categoria').val() === null ||
                        $('#tipo').val() === null) {

                }
                reload();
            });
            $('#expandir').click(function () {
                collapse_exand_rows();
            });

            $('#gestionProyectosForm').submit(function (evt) {

                evt.preventDefault();

            });

            function reload() {


                var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();
                var categoria = $('#categoria').val() === null ? "" : $('#categoria').val();
                var tipo = ($('#tipo').val() === null) ? "" : $('#tipo').val();
                var prioridad = $('#prioridad').val() === null ? "" : $('#prioridad').val();
                var estatus = ($('#estatus').val() === null) ? "" : $('#estatus').val();


                $.fancybox.close();
                $('#listadoDiv').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable({
                        destroy: true,
                        responsive: true
                    });
                    table.destroy();
                }
                var url = "/ajax/busquedaProyectosAjax.action";

                url = url + "?proyecto.condominio.id=" + condominio + "&proyecto.categoria.id=" + categoria + "&proyecto.tipo.id=" + tipo
                        + "&proyecto.prioridad.id=" + prioridad + "&proyecto.estatus.id=" + estatus;

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
                        {
                            text: 'Ver detalle',
                            action: function (e, dt, node, config) {
                                collapse_exand_rows();
                            }
                        }
                    ],
                    "ajax": {
                        "type": "GET",
                        "url": url,
                        "dataSrc": function (json) {
                            //Make your callback here.
                                    $('[data-toggle="tooltip"]').tooltip();  
                                    $('#significados').show();
                            $('#enviar').prop( "disabled", false );
                            $('#expandir').prop("disabled", false);
                            return json.data;
                        }
                    },
                    "columnDefs": [
                        {
                            "targets": [6,7],
                            "visible": false,
                            "searchable": false
                        }
                    ],
                    "language": {
                        "sProcessing": "Buscando información...",
                        "sLengthMenu": "Mostrar _MENU_ registros",
                        "sZeroRecords": "No se encontraron resultados",
                        "sEmptyTable": "Ningún dato disponible en esta tabla",
                        "sInfo": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                        "sInfoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                        "sInfoFiltered": "(filtrado de un total de _MAX_ registros)",
                        "sInfoPostFix": "",
                        "sSearch": "Búsqueda de Texto",
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
                    "order": [[0, 'asc']],
                    "bFilter": true
                });
                
                $("#listado_filter input").addClass('form-control');

            }
            
            //This function gets the length of the row and collapse and 
            //expend all the rows depending on the current state of the row:    
            function collapse_exand_rows() {
                    $('td[tabindex=0]').trigger('click');
            }

        });


    </script>
    <style type="text/css">
        /*div.container { max-width: 1400px }*/
        td, tr {font-size:13px}
    </style>
</head>
<div class="container">
    <p></p>
    <h1>Gesti&oacute;n de Proyectos y Tareas</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Configuraci&oacute;n</span></h3>
            <h2 style="margin-left: 10px" >Seleccione tarea o proyecto.<br>
            </h2>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="gestionProyectosForm" action="/proyectos/gestionProyectos.action" method="POST">
                <div class="form-group">
                    <div class="col-lg-12 text-left ">
                        <a href="/proyectos/altaProyectos.action" id="nuevo" class="btn btn-success fancybox fancybox.iframe"> Nuevo Proyecto</a>
                    </div>
                    <div>&nbsp;</div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Condominio:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="condominio"  class="form-control" 
                                  list="condominiolist" listKey="id" listValue="nombre"
                                  name="proyecto.condominio.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Categor&iacute;a:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="categoria"  class="form-control" 
                                  list="ambiente.proyectoCategoriaList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.categoria.id"
                                  />
                        <span class="help-block" id="categoriaMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="tipo"  class="form-control" 
                                  list="ambiente.proyectoTipoList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.tipo.id"
                                  />
                        <span class="help-block" id="tipoMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Prioridad:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="prioridad"  class="form-control" 
                                  list="ambiente.proyectoPrioridadList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.prioridad.id"
                                  />
                        <span class="help-block" id="prioridadMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Estatus:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="estatus"  class="form-control" 
                                  list="ambiente.proyectoEstatusList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.estatus.id"
                                  />
                        <span class="help-block" id="estatusMessage" />
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
            </form>
        </div>
        <div class="clearfix" >&nbsp;</div>
    </div>

    <div class="clearfix" >&nbsp;</div>
    <div id="listadoDiv"  class="row main_content">
        <div class="row col-lg-12 main-box" id="busquedaForm">
            <table id="listado" class="table table-striped table-hover display responsive">
                <thead>
                    <tr>
                        <th data-priority="1"></th>
                        <th data-priority="1">Fecha<br> Vencimiento</th>
                        <th data-priority="1" style="width:350px">T&iacute;tulo</th>
                        <th data-priority="1">Categor&iacute;a</th>
                        <th data-priority="1">Tipo</th>
                        <th data-priority="1">Prioridad</th>
                        <th data-priority="1">Cuota</th>
                        <th data-priority="2">Visible</th>
                        <th data-priority="2">Estatus</th>
                        <th data-priority="1">Fecha Sig. Tarea</th>
                        <th data-priority="2">D&iacute;as Restantes</th>
                        <th data-priority="2">D&iacute;as Restantes Sig. Tarea</th>
                        <th data-priority="1">Color</th>
                        <th data-priority="1" class="dt-body-center" style="width:5px"></th>
                        <th data-priority="1" class="dt-body-center" style="width:5px"></th>
                        <th data-priority="2"  style="width:1px"></th>
                   </tr>
                </thead>
            </table>
        </div>
    </div>
    
    
    <div id="significados">
            <div class="col-lg-12">
                <div class="col-lg-3 text-right ">
                    <span style="color:blue" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Proyecto Cerrado
                </div>
                <div class="col-lg-3 text-right ">
                    <span style="color:green" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Proyecto En Tiempo
                </div>
            </div>
            <div class="col-lg-12">
                <div class="col-lg-3 text-right ">
                    <span style="color:yellow" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Proyecto Atendido
                </div>
                <div class="col-lg-3 text-right ">
                    <span style="color:red" class="fa-stack">
                        <i class="fa fa-square fa-stack-2x"></i><i class="fa fa-stack-1x fa-inverse"></i></span>
                </div>
                <div class="col-lg-3 text-left ">
                        Atenci&oacute;n urgente
                </div>
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
