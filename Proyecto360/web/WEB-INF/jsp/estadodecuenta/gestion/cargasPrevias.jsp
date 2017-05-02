<%-- 
    Document   : cargasPrevias
    Created on : 27-may-2016, 21:06:39
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

                $('#listadoDiv').hide();

                $("#buscar").click(function () {
                    var id = $("#idCarga").val();
                    $("#idCargaBusqueda").val(id);
                    reload();
                });

                $("#opcionCarga").change(function () {
                    getIdentificadores();
                });

                $("#eliminaTodo").click(function(){
                    eliminaTodo();
                });
                //reload();
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
                var url = "/ajax/getMovimientosPorIdentificadorCargaEstadoCuentaAjax.action";


                url = url + "?opcionCarga=" + $('#opcionCarga').val() + "&idCarga=" + $('#idCarga').val() + "&condominioId=" + $('#condominioId').val();

                $('#listado').DataTable({
                    "ajax": url,
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
                    },
                    "order": [[0, 'asc'], [3, 'asc']],
                    "bFilter": false
                });
            }

        </script>
    </head>
    <body>
        <div class="container">
            <p></p>
            <h1>Gesti&oacute;n de Cargas Masivas de Informaci&oacute;n</h1>

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
                    <h2 style="margin-left: 10px" >Seleccione el tipo de carga, y el identificador de carga para obtener los movimientos realizados.</h2>
                    <div class="clearfix" >&nbsp;</div>
                    <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">Condominio:</label>
                        <div class="col-lg-9">
                            <s:select id="condominioId"  
                                      class="form-control"
                                      name="condominioId"
                                      list="condominios" 
                                      listKey="id" 
                                      listValue="nombre"  />
                            <span class="help-block" id="estadoMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Tipo de Carga:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="opcionCarga"  class="form-control" 
                                      list="service.opcionesCargas" headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="opcionCarga"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Identificador de Carga:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="idCarga"  class="form-control"
                                      list="#{}"
                                      headerValue="-- Seleccione --"
                                      name="idCarga"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>


                    <div class="clearfix" >&nbsp;</div>
                    <div class="form-group">
                        <div class="col-lg-12 text-right ">
                            <input type="hidden" id="idCargaBusqueda" />
                            <button id="buscar" class="btn btn-default">Buscar</button>
                        </div>
                    </div>
                    <div class="clearfix" >&nbsp;</div>
                </div>
                <div class="clearfix" >&nbsp;</div>
                <div id="listadoDiv" class="col-lg-12">
                    <div class="main-box form-group">
                        <div class="clearfix" >&nbsp;</div>
                        <div class="col-lg-8 text-right ">
                            ¿Desea eliminar todos los registros relacionados al identificador de carga seleccionado?
                        </div>
                        <div class="col-lg-4 text-right ">
                            <button id="eliminaTodo" class="btn btn-default">
                                <span class="fa fa-trash-o"></span>
                                Eliminar Todos</button>
                        </div>
                        <div class="clearfix" >&nbsp;</div>
                    </div>
                    <div class="main-box">
                        <table id="listado" class="table-hover">
                            <thead>
                                <tr>
                                    <th>Condominio</th>
                                    <th>Torre</th>
                                    <th>Depto.</th>
                                    <th>Concepto</th>
                                    <th>Fecha</th>
                                    <th>Monto</th>
                                    <th width="5%"></th>
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

            function eliminaAbono(idMovimiento) {
                BootstrapDialog.confirm('Esto borrará el abono seleccionado. Una vez realizada la operación, no hay forma de recuperar la información. ¿Está seguro de proceder?', function (result) {
                    if (result) {
                        $.ajax({
                            type: 'POST',
                            url: '/ajax/eliminaAbonoEstadoCuentaAjax.action?idMovimiento=' + idMovimiento,
                            dataType: 'json',
                            cache: false,
                            success: function (aData) {

                                dialog = BootstrapDialog.show({
                                    title: 'Información',
                                    message: 'Se ha borrado el abono seleccionado',
                                    closable: true
                                });
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

            function eliminaCargo(idMovimiento) {
                BootstrapDialog.confirm('Esto borrará el cargo seleccionado. Una vez realizada la operación, no hay forma de recuperar la información. ¿Está seguro de proceder?', function (result) {
                    if (result) {
                        $.ajax({
                            type: 'POST',
                            url: '/ajax/eliminaCargoEstadoCuentaAjax.action?idMovimiento=' + idMovimiento,
                            dataType: 'json',
                            cache: false,
                            success: function (aData) {

                                dialog = BootstrapDialog.show({
                                    title: 'Información',
                                    message: 'Se ha borrado el cargo seleccionado',
                                    closable: true
                                });
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

            function getIdentificadores() {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/getIdentificadoresCargaEstadoCuentaAjax.action?opcionCarga=' + $('#opcionCarga').val() + "&condominioId=" + $('#condominioId').val(),
                    dataType: 'json',
                    data: {pkCondominio: $('#condominio').val()},
                    cache: false,
                    success: function (aData) {

                        $('#idCarga').get(0).options.length = 0;
                        $('#idCarga').get(0).options[0] = new Option("-- Seleccione --", "");

                        $.each(aData.data, function (i, item) {
                            $('#idCarga').get(0).options[$('#idCarga').get(0).options.length] = new Option(item[1], item[0]);
                        });

                    },
                    error: function () {
                        alert("Connection Is Not Available");
                    }
                });

                return false;
            }

            function eliminaTodo() {
                BootstrapDialog.confirm('Esto borrará todos los registros que coinciden con el identificador:' + $('#idCargaBusqueda').val() + ', ¿Está seguro de proceder?', function (result) {
                    if (result) {
                        $.ajax({
                            type: 'POST',
                            url: '/ajax/eliminaMovimientosPorIdentificadorCargaEstadoCuentaAjax.action',
                            dataType: 'json',
                            data: {idCarga: $('#idCargaBusqueda').val(),
                            opcionCarga: $('#opcionCarga').val()},
                            cache: false,
                            success: function (aData) {
                                BootstrapDialog.show({
                                    title: 'Información',
                                    message: 'Se han eliminado los abonos relacionados al identificador de carga ' + $('#idCarga').val() + '.',
                                    closable: true
                                });

                                reload();

                            },
                            error: function () {
                                alert("Connection Is Not Available");
                            }
                        });
                    } else {
                    }
                });


                return false;
            }
        </script>
    </body>

</html>
