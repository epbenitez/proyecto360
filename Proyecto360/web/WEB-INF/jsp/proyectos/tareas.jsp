<%-- 
    Document   : tareas
    Created on : 13-dic-2016, 16:57:08
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
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

            reload();

//           $('#enviar').prop( "disabled", true );

            $('#fecha').datepicker();
            $('#fecha').css('cursor', 'default');

            $('#guardaTareasForm').bootstrapValidator({});

            $('select').addClass('form-control');
            $('textarea').addClass('form-control');
            $('input').addClass('form-control');

        });
        function reload() {

            $.fancybox.close();
            $('#listadoDiv').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/tareasProyectosAjax.action?proyecto.id=<s:property  value="proyecto.id"/>";

            $('#listado').DataTable({
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
                        <security:authorize ifAnyGranted="ROLE_COMITE">
                                "columnDefs": [
                        {
                            "targets": [6,7],
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
                "order": [[3, 'asc']],
                "bFilter": false
            });

            $('#listado_length').hide();
            $('#listado_filter').hide();
            $('#listado_info').hide();
//                $('#listado_paginate').hide();

        }


        function eliminaTarea(id) {
            if (id === "") {
            } else {
                BootstrapDialog.confirm('Esto eliminará la tarea seleccionada, ¿Está seguro de proceder?', function (result) {
                    if (result) {

                        var dialog;
                        $.ajax({
                            type: 'POST',
                            url: "/ajax/eliminaTareaProyectosAjax.action",
                            dataType: 'json',
                            data: {'proyectoTarea.id': id},
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



    </script>
</head>

<div class="container">
    <p></p>
    <h1>Administraci&oacute;n de  Tareas</h1>

    <div class="row main_content">
        <div class="row col-lg-12 main-box" id="busquedaForm">
            <div class="clearfix" >&nbsp;</div>
            </h2>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <security:authorize ifNotGranted="ROLE_COMITE">
                <form id="guardaTareasForm" action="/proyectos/guardaTareasProyectos.action" method="POST">
                    <div class="form-group">
                        <label class="col-lg-3 control-label text-right">
                            Acci&oacute;n:
                        </label>
                        <div class="col-lg-9">
                            <s:textfield name="proyectoTarea.accion"
                                         data-bv-notempty="true"
                                         data-bv-notempty-message="Este campo es requerido"
                                         required="true"
                                         />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label text-right">
                            Responsable:
                        </label>
                        <div class="col-lg-9">
                            <s:textfield name="proyectoTarea.responsable"
                                         data-bv-notempty="true"
                                         data-bv-notempty-message="Este campo es requerido"
                                         required="true"
                                         />
                            <span class="help-block" id="categoriaMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label text-right">
                            Nota:
                        </label>
                        <div class="col-lg-9">
                            <s:textfield name="proyectoTarea.nota"
                                         data-bv-notempty="true"
                                         data-bv-notempty-message="Este campo es requerido"
                                         required="true"
                                         />
                            <span class="help-block" id="tipoMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label text-right">
                            Fecha:
                        </label>
                        <div class="col-lg-9">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                <input type="text" class="form-control" id="fecha" name="proyectoTarea.fecha"
                                       value="<s:date name="proyectoTarea.fecha" format="dd/MM/yyyy" />"
                                       data-bv-notempty="true"
                                       data-bv-message="Este dato no es válido"
                                       required="true" 
                                       readonly="true" >
                                <span class="help-block" id="fechaMessage" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label text-right">
                            Siguiente tarea:
                        </label>
                        <div class="col-lg-9">
                            <s:textfield name="proyectoTarea.nombre"
                                         data-bv-notempty="true"
                                         data-bv-notempty-message="Este campo es requerido"
                                         required="true"
                                         />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-3 control-label text-right">
                            Estatus:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="estatus"  class="form-control" 
                                      list="ambiente.proyectoEstatusList" listKey="id" listValue="nombre" headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="proyectoTarea.estatus.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      required="true"
                                      />
                            <span class="help-block" id="estatusMessage" />
                        </div>
                    </div>

                    <div class="clearfix" >&nbsp;</div>
                    <div class="clearfix" >&nbsp;</div>
                    <div class="form-group">
                        <div class="col-lg-12 text-center ">
                            <s:hidden name="proyecto.id" />
                            <s:hidden name="proyectoTarea.id" />
                            <button type="submit" id="enviar" class="btn btn-success">Guardar</button>
                        </div>
                    </div>
                    <div class="clearfix" >&nbsp;</div>
                </form>

            </security:authorize>

        </div>
        <div class="clearfix" >&nbsp;</div>
    </div>
    <div id="listadoDiv" class="row main_content">
        <div class="row col-lg-12 main-box" >
            <table id="listado" class="table-hover">
                <thead>
                    <tr>
                        <th>Acci&oacute;n</th>
                        <th>Responsable</th>
                        <th>Nota</th>
                        <th>Fecha</th>
                        <th>Siguiente Tarea</th>
                        <th>Estatus</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>

</div>
