<%-- 
    Document   : administraPagos
    Created on : 08-jun-2016, 17:05:45
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

                $('.fancybox').fancybox({
                    autoSize: true,
                    afterClose: function () {
                        reload();
                    }
                });

                $('#listado').hide();

                $("#buscar").click(function () {
                    reload();
                });

                $("#condominio").change(function () {
                    getTorres();
                });

                $("#torre").change(function () {
                    getDepartamentos();
                });

                getTorres();
                //reload();
            });

            function reload() {

                $('#listado').show();
                if ($.fn.dataTable.isDataTable('#listado')) {
                    table = $('#listado').DataTable();
                    table.destroy();
                }

                var condominio = $('#condominio').val() === "" || $('#condominio').val() == "null" || $('#condominio').val() == null ? "" : $('#condominio').val();
                var torre = $('#torre').val() === "" || $('#torre').val() == "null" || $('#torre').val() == null ? "" : $('#torre').val();
                var departamento = $('#departamento').val() === "" || $('#departamento').val() == "null" || $('#departamento').val() == null ? "" : $('#departamento').val();

                table = $('#listado').DataTable({
                    "aoColumnDefs": [
                        {"sClass": "alignRight",
                            "aTargets": [2, 3, 4, 5]
                        }
                    ],
                    "paging": false,
                    "ajax": '/ajax/administraPago.action?pkCondominio=' + condominio + '&pkTorre=' + torre + '&departamentoId=' + departamento,
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

            function eliminaAbonoDatosPago(id) {
                BootstrapDialog.confirm('Esto borrará el abono seleccionado, ¿Está seguro de proceder?', function (result) {
                    if (result) {
//                    $("#revertir").submit();
                        $.ajax({
                            type: 'POST',
                            url: '/ajax/eliminaAbonoDatosPago.action?abonoDatosPagoId=' + id,
                            dataType: 'json',
                            cache: false,
                            success: function (aData) {
                                BootstrapDialog.alert('El abono ha sido eliminado correctamente.');
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


        </script>
    </head>
    <body>
        <div class="container">
            <p></p>
            <h1>Administra Pagos</h1>

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
                    <h3 style="margin-top: 5px; margin-left: 10px"><span>B&uacute;squeda</span></h3>
                    <h2 style="margin-left: 10px" >Seleccione Torre y Condominio para encontrar el departamento.</h2>
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
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Torre:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="torre"  class="form-control"
                                      list="#{}"
                                      headerValue="-- Seleccione --"
                                      name="torre"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Departamento:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="departamento"  class="form-control"
                                      list="#{}"
                                      headerValue="-- Seleccione --"
                                      name="departamento"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>


                    <div class="clearfix" >&nbsp;</div>
                    <div class="form-group">
                        <div class="col-lg-12 text-right ">
                            <a class="btn btn-default" href="#" id="buscar" class="btn btn-default">Buscar Pagos</a>
                        </div>
                    </div>
                    <div class="clearfix" >&nbsp;</div>
                </div>
                <div class="clearfix" >&nbsp;</div>
                <div id="detalle" class="col-lg-12">
                    <div class="responsive-table table-responsive main-box clearfix">
                    <table id="listado" class="table table-striped table-hover display responsive">
                            <thead>
                                <tr>
                                    <th data-priority="1">Depto</th>
                                    <th data-priority="1">Torre</th>
                                    <th>N&uacute;mero</th>
                                    <th>Titular</th>
                                    <th>Fecha de Emisi&oacute;n</th>
                                    <th>Fecha de Pago</th>
                                    <th>Banco</th>
                                    <th>Abono Tipo</th>
                                    <th data-priority="1">Monto</th>
                                    <th data-priority="1">Usuario Recibe Pago</th>
                                    <th data-priority="1"></th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" language="javascript" class="init">
            $(document).ready(function () {
                $('select').addClass('form-control');
            }
            );
        </script>

        <script>

            function getTorres() {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/getTorresSolicitudesAjax.action',
                    dataType: 'json',
                    data: {pkCondominio: $('#condominio').val()},
                    cache: false,
                    success: function (aData) {

                        $('#torre').get(0).options.length = 0;
                        $('#torre').get(0).options[0] = new Option("-- Seleccione --", "");

                        $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                            $('#torre').get(0).options[$('#torre').get(0).options.length] = new Option(item[1], item[0]);
                            // Display      Value
                        });

                    },
                    error: function () {
                        alert("Connection Is Not Available");
                    }
                });

                return false;
            }

            function getDepartamentos() {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/getDepartamentosPago.action',
                    dataType: 'json',
                    data: {pkTorre: $('#torre').val(),
                        pkCondominio: $('#condominio').val(), },
                    cache: false,
                    success: function (aData) {

                        $('#departamento').get(0).options.length = 0;
                        $('#departamento').get(0).options[0] = new Option("-- Seleccione --", "");

                        $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                            $('#departamento').get(0).options[$('#departamento').get(0).options.length] = new Option(item[1], item[0]);
                            // Display      Value
                        });

                    },
                    error: function () {
                        alert("Connection Is Not Available");
                    }
                });

                return false;
            }
        </script>
    </body>

</html>