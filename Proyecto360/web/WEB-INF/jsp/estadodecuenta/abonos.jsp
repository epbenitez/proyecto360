<%-- 
    Document   : abonos
    Created on : 06-ene-2016, 10:36:00
    Author     : Patricia Benitez
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <title>Abonos Masivos</title>
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

            $('#cargaForm').bootstrapValidator({});
//            $('#configuracionDiv').hide();
            $('#resumen').hide();

            $("#agregar").click(function () {
                if ($('#conceptoId').val() !== "" && $('#fecha').val() !== "" && $('#monto').val()) {
                    reload();
                } else {
                    BootstrapDialog.show({
                        title: 'Configuraci&oacute;n de la carga',
                        message: 'Es necesario completar todos los campos (Concepto '+ $('#conceptoId').val()+',Fecha '+ $('#fecha').val()+'y Monto'+ $('#monto').val()+'). Por favor, verifique.'
                    });
                }
            });

            $("#limpiar").click(function () {
                limpiaConfiguracion();
            });
            
            $("#condominioId").change(function () {
                configuracionInicial();
            });

        });

        function configuracionInicial() {

            $('#configuracionDiv').show();
            if ($.fn.dataTable.isDataTable('#configuracion')) {
                table = $('#configuracion').DataTable();
                table.destroy();
            }

            table = $('#configuracion').DataTable({
                "ajax": '/ajax/configuracionInicialMasivosAjax.action?cargo=false&condominioId=' + $('#condominioId').val(),
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

            $('#configuracion_length').hide();
            $('#configuracion_filter').hide();
            $('#configuracion_info').hide();
            $('#configuracion_paginate').hide();
            
            $('#conceptoId').val(1);
            $('#fecha').val(1);
            $('#monto').val(1);
        }

        function limpiaConfiguracion() {
            $.ajax({
                type: 'POST',
                url: '/ajax/limpiaConfiguracionMasivosAjax.action?cargo=false&condominioId=' + $('#condominioId').val(),
                dataType: 'json',
                cache: false,
                success: function (aData) {

                    $('#condominioId').val("");
                    $('#conceptoId').val("");
                    $('#fecha').val("");
                    $('#monto').val("")
                    reload();

                },
                error: function () {
                    alert("Connection Is Not Available");
                }
            });
        }

        function reload() {

            $('#configuracionDiv').show();
            if ($.fn.dataTable.isDataTable('#configuracion')) {
                table = $('#configuracion').DataTable();
                table.destroy();
            }

            table = $('#configuracion').DataTable({
                "ajax": '/ajax/configuracionMasivosAjax.action?cargo=false&condominioId=' + $('#condominioId').val()
                        + '&conceptoId=' + $('#conceptoId').val()
                        + '&columnaFecha=' + $('#fecha').val()
                        + '&columnaMonto=' + $('#monto').val(),
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

            $('#configuracion_length').hide();
            $('#configuracion_filter').hide();
            $('#configuracion_info').hide();
            $('#configuracion_paginate').hide();

        }

    </script>

</head>

<s:form id="cargaForm" action="cargaAbonosConfiguracionMasivos.action" method="POST" enctype="multipart/form-data">

    <div class="col-lg-12">
        <div class="main-box clearfix">
            <div class="clearfix">&nbsp;</div>  
            <div class="col-md-12">
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

            <div class="col-lg-12">

                <h3 style="margin-top: 5px; margin-left: 10px"><span>Configuraci&oacute;n de Abonos</span></h3>
                <div class="col-md-2">
                    <label for="maskedDate">Condominio:</label>
                </div>
                <div class="col-md-10">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <s:select id="condominioId"  
                                      class="form-control"
                                      name="condominioId"
                                      list="condominios" 
                                      listKey="id" 
                                      listValue="nombre" 
                                      headerKey=""
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Es necesario especificar el condominio"
                                      headerValue="-- Seleccione --" />
                        <span class="help-block" id="estadoMessage" />
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>  


            </div>

        </div>
    </div>

    <div class="col-md-6">
        <div class="main-box clearfix">

            <div class="clearfix">&nbsp;</div>  
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Configuraci&oacute;n de Columnas</span></h3>

            <div class="col-md-2">
                <label for="maskedDate">Concepto:</label>
            </div>
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                        <s:select id="conceptoId"  
                                  class="form-control"
                                  name="conceptoId"
                                  list="conceptos" 
                                  listKey="id" 
                                  listValue="nombre" 
                                  headerKey=""
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Es necesario especificar el concepto"
                                  headerValue="-- Seleccione --" />
                    <span class="help-block" id="estadoMessage" />
                </div>
            </div>
            <div class="clearfix">&nbsp;</div>  
            <div class="col-md-2">
                <label for="maskedDate">Fecha de pago:</label>
            </div>
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                        <s:select class="form-control" name="fecha" id="fecha"  list="service.nombreColumnasExcel" 
                                  headerKey=""
                                  required="true" 
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Campo requerido"
                                  headerValue="-- Seleccione --" />
                    <span class="help-block" id="fechaMessage" />
                </div>
            </div>
            <div class="clearfix">&nbsp;</div>  
            <div class="col-md-2">
                <label for="maskedDate">Monto:</label>
            </div>
            <div class="col-md-10">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                        <s:select class="form-control" name="monto" id="monto" list="service.nombreColumnasExcel" 
                                  headerKey=""
                                  required="true" 
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Campo requerido"
                                  headerValue="-- Seleccione --" />
                    <span class="help-block" id="fechaMessage" />
                </div>
            </div>

            <div class="clearfix">&nbsp;</div>  

            <div class="col-md-12 text-center">
                <button type="button"  id="agregar" class="btn btn-success">Agregar <i class="fa fa-arrow-circle-right"></i></button>
            </div>
            <div class="clearfix">&nbsp;</div>  

        </div>
    </div>


    <div id="configuracionDiv" class="col-md-6">
        <div class="main-box clearfix">

            <div class="clearfix">&nbsp;</div>  
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Conceptos y Columnas</span></h3>

            <table id="configuracion" class="table-hover table-responsive">
                <thead>
                    <tr>
                        <th class="no-sort sorting_disabled text-center" id="mes" width="50%">Concepto</th>
                        <th class="no-sort text-center dt-body-center" width="25%" > # Columna Fecha</th>
                        <th class="no-sort text-center" width="25%" ># Columna Monto</th>
                    </tr>
                </thead>
            </table>
            <div class="clearfix">&nbsp;</div>  
            <div class="col-md-12 text-center">
                <button  id="limpiar" class="btn btn-success">Limpiar</button>
            </div>
            <div class="clearfix">&nbsp;</div> 
        </div>
    </div>

    <div class="col-lg-12">
        <div class="main-box clearfix">
            <div class="clearfix">&nbsp;</div>  


            <div class="col-lg-12">

                <h3 style="margin-top: 5px; margin-left: 10px"><span>Archivos</span></h3>

                <div class="col-md-10">
                    <div class="input-group">
                        <span class="input-group-addon"><i class="fa fa-file-excel-o"></i></span>
                            <s:file  class="file" data-show-preview="true" labelposition="left" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="upload" />
                        <span class="help-block" id="estadoMessage" />
                    </div>
                </div>
                <div class="clearfix">&nbsp;</div>
                <div class="clearfix">&nbsp;</div>  
                <div class="col-md-12 text-center">
                    <button type="submit" id="cargar" class="btn btn-success">Cargar</button>
                </div>
                <div class="clearfix">&nbsp;</div>   

            </div>


        </div>
    </div>

</s:form>

<div class="clearfix">&nbsp;</div>

<div class="row white_box" id="resumen">
    <div class="col-md-12">

        <div class="main-box clearfix">

            <table id="listado" class="table-hover table-responsive">
                <thead>
                    <tr>
                        <th class="no-sort sorting_disabled text-center" id="mes" width="20%">Mes</th>
                        <th class="no-sort text-center dt-body-center" width="20%" >Saldo Inicial <br/>(Capital)</th>
                        <th class="no-sort text-center" width="20%" >Cargos del Mes</th>
                        <th class="no-sort text-center" width="20%" >Pagos del Mes</th>
                        <th class="no-sort text-center" width="20%" >Saldo Final <br/>(Capital)</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>

</div>
<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

<script>

        $('input[type=text]').addClass('form-control');
        $('select').addClass('form-control');
        $('input[type=file]').addClass('form-control');

</script>

