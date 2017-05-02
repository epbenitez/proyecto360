<%-- 
    Document   : nuevo
    Created on : 25-ene-2017, 19:33:39
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Nuevo Usuario</title>
    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {

//            $('#guardaUsuarioForm').bootstrapValidator({});
            $('#guardaUsuarioForm')
                    .bootstrapValidator({
                        excluded: [':disabled'],
                        feedbackIcons: {
                            valid: 'glyphicon glyphicon-ok',
                            invalid: 'glyphicon glyphicon-remove',
                            validating: 'glyphicon glyphicon-refresh'
                        },
                        fields: {
                            contraseniaNueva: {
                                validators: {
//                                    notEmpty: {
//                                        message: 'La nueva contraseña es requerida'
//                                    },
                                    identical: {
                                        field: 'contraseniaNuevaRepetir',
                                        message: 'La contraseña y su confirmación deben ser iguales.'
                                    }
                                }
                            },
                            contraseniaNuevaRepetir: {
                                validators: {
//                                    notEmpty: {
//                                        message: 'La nueva contraseña es requerida'
//                                    },
                                    identical: {
                                        field: 'contraseniaNueva',
                                        message: 'La contraseña y su confirmación deben ser iguales.'
                                    }
                                }
                            },
                        }
                    });

            $('select').addClass('form-control');
            $('textarea').addClass('form-control');
            $('input').addClass('form-control');


            $('#detalle').hide();

            reload();
        });

        function reload() {

            $('#detalle').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable();
                table.destroy();
            }

            table = $('#listado').DataTable({
                "paging": false,
                "ajax": '/ajax/rolesUsuarioAjax.action?usuario.id=<s:property  value="usuario.id" />',
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

        function eliminaRolUsuario(rolId) {
            var dialog;
            BootstrapDialog.confirm('Esto borrará el permiso seleccionado, ¿Está seguro de proceder?', function (result) {
                if (result) {
                    dialog = BootstrapDialog.show({
                        title: 'Información',
                        message: 'Se está procesando su solicitud. Por favor, espere.',
                        closable: false
                    });
                    $.ajax({
                        type: 'POST',
                        url: '/ajax/eliminaRolUsuarioAjax.action',
                        dataType: 'json',
                        data: {'usuario.id': $("#usuarioId").val(),
                            'rolUsuario.id': rolId,
                        },
                        cache: false,
                        success: function (aData) {
                            dialog.close();
                            if (aData.data[0] == "OK") {
                                BootstrapDialog.alert('El permiso ha sido eliminado correctamente.');
                                reload();
                            } else {
                                BootstrapDialog.alert('No se pudo eliminar el permiso indicado.');
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

<div class="container">
    <p></p>
    <h1>Alta de nuevo Usuario</h1>

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
            </h2>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="guardaUsuarioForm" action="/admin/agregaNuevoUsuario.action" method="POST">
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Condominio:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="condominio"  class="form-control" 
                                  list="condominiolist" listKey="id" listValue="nombre"
                                  name="condominioId"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Usuario:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="usuario.usuario"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido"
                                     required="true" />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Contraseña Nueva:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="contraseniaNueva" id="contraseniaNueva"  />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Repetir Contraseña:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="contraseniaNuevaRepetir" id="contraseniaNuevaRepetir"  />
                        <span class="help-block" id="categoriaMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Agregar Rol:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="tipo"  class="form-control" 
                                  list="ambiente.rolList" listKey="id" listValue="clave" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="rolUsuario.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                        <span class="help-block" id="tipoMessage" />
                    </div>
                </div>


                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <security:authorize ifNotGranted="ROLE_COMITE">
                    <div class="form-group">
                        <div class="col-lg-12 text-center ">
                            <button type="submit" id="enviar" class="btn btn-success">Guardar</button>
                        </div>
                    </div>
                </security:authorize>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>
        <div class="clearfix" >&nbsp;</div>
        <div class="clearfix" >&nbsp;</div>
        <div id="detalle" class="row col-lg-12 main-box">
            <table id="listado" class="table table-striped table-hover display">
                <thead>
                    <tr>
                        <th data-priority="1">Rol(es)</th>
                        <th data-priority="1"></th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <div class="clearfix" >&nbsp;</div>
</div>
