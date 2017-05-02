<%-- 
    Document   : listado
    Created on : 23-ene-2017, 17:05:45
    Author     : erikapatriciabenitezsoto
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
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

        <script type="text/javascript" language="javascript" class="init">
            $(document).ready(function () {

                $('select').addClass('form-control');

                $('.fancybox').fancybox({
                    autoSize: true,
                    afterClose: function () {
                        reload();
                    }
                });

                $('#detalle').hide();

                $("#buscar").click(function () {
                    reload();
                });




//                reload();
            });

            function reload() {

                $('#detalle').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable();
                    table.destroy();
                }

                table = $('#listado').DataTable({
//                    "aoColumnDefs": [
//                        {"sClass": "alignRight",
//                            "aTargets": [1]
//                        }
//                    ],
                    "paging": false,
                    "ajax": '/ajax/gestionUsuarioAjax.action?pkCondominio=' + $("#condominio").val(),
                    "order": [1],
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

            function eliminaUsuario(usuarioId) {
                var dialog;
                BootstrapDialog.confirm('Esto borrará el usuario seleccionado, esta acción no se puede revertir, ¿Está seguro de proceder?', function (result) {
                    if (result) {
                        dialog = BootstrapDialog.show({
                            title: 'Información',
                            message: 'Se está procesando su solicitud. Por favor, espere.',
                            closable: false
                        });
                        $.ajax({
                            type: 'POST',
                            url: '/ajax/eliminaUsuarioAjax.action',
                            dataType: 'json',
                            data: {'usuario.id': usuarioId
                            },
                            cache: false,
                            success: function (aData) {
                                dialog.close();
                                if (aData.data[0] == "OK") {
                                    BootstrapDialog.alert('El usuario ha sido eliminado correctamente.');
                                    reload();
                                } else {
                                    BootstrapDialog.alert('No se pudo eliminar el usuario indicado.');
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
    </head>
    <body>
        <div class="container">
            <p></p>
            <h1>Administra Usuarios</h1>

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
                    <div class="clearfix" >&nbsp;</div>
                    <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominiolist" listKey="id" listValue="nombre"
                                      name="condominio"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>


                    <div class="clearfix" >&nbsp;</div>
                    <div class="form-group">
                        <div class="col-lg-6 text-right ">
                            <a href="/admin/nuevoUsuario.action" id="nuevo" class="btn btn-default fancybox fancybox.iframe">Agregar Usuario</a>
                        </div>
                        <div class="col-lg-6 text-left ">
                            <a class="btn btn-default" href="#" id="buscar" class="btn btn-default">Buscar Usuarios</a>
                        </div>
                    </div>
                    <div class="clearfix" >&nbsp;</div>
                </div>
                <div class="clearfix" >&nbsp;</div>
                <div id="detalle" class="row col-lg-12 main-box">
                    <table id="listado" class="table table-striped table-hover display">
                        <thead>
                            <tr>
                                <th data-priority="1">Usuario</th>
                                <th data-priority="1">Condominio</th>
                                <th data-priority="1">Rol(es)</th>
                                <th data-priority="1"></th>
                                <th data-priority="1"></th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>


    </body>

</html>