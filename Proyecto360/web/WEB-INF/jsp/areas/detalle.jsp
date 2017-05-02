<%-- 
    Document   : alta
    Created on : 16-sep-2016, 12:56:07
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
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
    <script src="/js/UtilString.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {

            $('#formAltaArea').bootstrapValidator({});


            $("#condominio").change(function () {
                getTorres();
            });

            $("#torre").change(function () {
                getDepartamentos();
            });

            $("#tipoSolicitud").change(function () {
                getAreas();
            });

            reloadHorarios();
            reloadReglas();
        });

        function reloadHorarios() {


            var areaId = $('#areaId').val() === null ? "" : $('#areaId').val();

            $.fancybox.close();
            $('#horarios').show();
            if ($.fn.dataTable.isDataTable('#horariosTable')) {
                table = $('#horariosTable').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/getHorariosAreasAjax.action";

            url = url + "?areaId=" + areaId;

            $('#horariosTable').DataTable({
                dom: 'Bfrtip',
                lengthMenu: [
                    [10, 25, 50, -1],
                    ['10 registros', '25 registros', '50 registros', 'Todos']
                ],
                "bFilter": false,
                "ajax": {
                    "type": "GET",
                    "url": url,
                    "dataSrc": function (json) {
                        //Make your callback here.
                        $('[data-toggle="tooltip"]').tooltip();
                        $('#significados').show();
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
                }
            });

//            }
        }



        function reloadReglas() {


            var areaId = $('#areaId').val() === null ? "" : $('#areaId').val();

            $.fancybox.close();
            $('#reglas').show();
            if ($.fn.dataTable.isDataTable('#reglasTable')) {
                table = $('#reglasTable').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/getReglasAreasAjax.action";

            url = url + "?areaId=" + areaId;

            $('#reglasTable').DataTable({
                dom: 'Bfrtip',
                lengthMenu: [
                    [10, 25, 50, -1],
                    ['10 registros', '25 registros', '50 registros', 'Todos']
                ],
                "bFilter": false,
                "ajax": {
                    "type": "GET",
                    "url": url,
                    "dataSrc": function (json) {
                        //Make your callback here.
                        $('[data-toggle="tooltip"]').tooltip();
                        $('#significados').show();
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
                }
            });

//            }
        }
    </script>
</head>
<div class="container">
    <p></p>
    <h1>Detalle de &Aacute;reas</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Detalle de &Aacute;rea</span></h3>
            <!--<h2 style="margin-left: 10px" >Seleccione Condominio y  Torre para encontrar el departamento.</h2>-->
            <div class="clearfix" >&nbsp;</div>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="formAltaArea" name="formAltaArea" action="/areas/guardaDetalleAreas.action" method="POST">
                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Condominio:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield name="area.condominio.nombre" class="form-control" readonly="true" />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Torre:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield name="area.torre.nombre" class="form-control" readonly="true" />
                        <span class="help-block" id="torreMessage" />
                    </div>
                </div>



                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        &Aacute;rea:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="area" name="area.nombre" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="areaMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        M&aacute;x. Personas:
                    </label>
                    <div class="col-lg-12">
                        <s:select id="personasMax"  class="form-control" 
                                  list="service.maxPersonas"
                                  headerValue="-- Seleccione --"
                                  name="area.personasMax"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="personasMaxMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">Pagos al Corriente:</label>
                    <div class="col-lg-12">
                        <s:textfield id="saldoMaxDeudor" name="area.saldoMaxDeudor" class="form-control"
                                     maxLength="200" />
                        <span class="help-block" id="saldoMaxDeudorMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Min. d&iacute;s para reservar:
                    </label>
                    <div class="col-lg-12">
                        <s:select id="diasReservaMin"  class="form-control" 
                                  list="service.diasReserva"
                                  headerValue="-- Seleccione --"
                                  name="area.diasReservaMin"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="diasReservaMinMessage" />
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        M&aacute;x. d&iacute;s para reservar:
                    </label>
                    <div class="col-lg-12">
                        <s:select id="diasReservaMax"  class="form-control" 
                                  list="service.diasReserva"
                                  headerValue="-- Seleccione --"
                                  name="area.diasReservaMax"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="diasReservaMaxMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Unidad de renta:
                    </label>
                    <div class="col-lg-12">
                        <s:select id="unidad"  class="form-control" 
                                  list="service.unidadReserva"
                                  headerValue="-- Seleccione --"
                                  name="area.unidad"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  />
                        <span class="help-block" id="unidadMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        M&aacute;x de eventos por d&iacute;a:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="eventosPorDiaMax" name="area.eventosPorDiaMax" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="eventosPorDiaMaxMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        M&aacute;x de eventos por mes:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="eventosPorMesMax" name="area.eventosPorMesMax" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="eventosPorMesMaxMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        M&aacute;x de eventos por a&ntilde;o:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="eventosPorAnioMax" name="area.eventosPorAnioMax" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="eventosPorAnioMaxMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Precio Renta:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="costo" name="area.costo" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="costoMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Dep&oacute;sito de Garant&iacute;a:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="depositoGarantia" name="area.depositoGarantia" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="depositoGarantiaMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        D&iacute;as de anticipaci&oacute;n para reservar:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="diasAnticipacionCancelar" name="area.diasAnticipacionCancelar" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="diasAnticipacionCancelarMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-12" ></div>    
                    <div class="col-lg-12" id="comentarioContador" text-left></div>    
                </div> 
                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <div class="form-group">
                    <div class="col-lg-12 text-center ">
                        <s:hidden id="areaId" name="area.id" />
                        <s:hidden name="area.condominio.id" />
                        <s:hidden name="area.torre.id" />
                        <button type="submit" id="guardar" class="btn btn-success">Guardar</button>
                    </div>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>

        <!-- HORARIOS -->
        <div class="row col-lg-12 main-box" id="horarios">
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Horarios de Reservaci&oacute;n</span></h3>
            <div class="col-lg-12 form-group">
                <label class="col-lg-6 control-label text-left">
                    D&iacute;a:
                </label>
                <div class="col-lg-6">
                    <s:select id="horarioDia"  class="form-control" 
                              list="service.horarioAreaDias"
                              headerValue="-- Seleccione --"
                              name="horarioDia"
                              data-bv-notempty="true"
                              data-bv-notempty-message="Este campo es requerido"
                              />
                    <span class="help-block" id="diasReservaMaxMessage" />
                </div>
            </div>
            <div class="col-lg-12 form-group">
                <label class="col-lg-6 control-label text-left">
                    Hora Inicial:
                </label>
                <div class="col-lg-6">
                    <s:select id="horarioHoraInicial"  class="form-control" 
                              list="service.horarioAreaHoras"
                              headerValue="-- Seleccione --"
                              name="horarioHoraInicial"
                              data-bv-notempty="true"
                              data-bv-notempty-message="Este campo es requerido"
                              />
                    <span class="help-block" id="diasReservaMaxMessage" />
                </div>
            </div>
            <div class="col-lg-12 form-group">
                <label class="col-lg-6 control-label text-left">
                    Hora Final:
                </label>
                <div class="col-lg-6">
                    <s:select id="horarioHoraFinal"  class="form-control" 
                              list="service.horarioAreaHoras"
                              headerValue="-- Seleccione --"
                              name="horarioHoraFinal"
                              data-bv-notempty="true"
                              data-bv-notempty-message="Este campo es requerido"
                              />
                    <span class="help-block" id="diasReservaMaxMessage" />
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-12 text-center ">
                    <button type="button" id="guardar" class="btn btn-success" onclick="agregaHorario()">Agregar</button>
                </div>
            </div>

            <div id="horariosLista">
                <div class="col-lg-12">    
                    <div class="clearfix">&nbsp;</div> 
                    <table  id="horariosTable" class="table-hover table-responsive">
                        <thead>
                            <tr>
                                <th>D&iacute;a</th>
                                <th>Hora Inicial</th>
                                <th>Hora Final</th>
                                <th></th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>

        </div>


        <!-- REGLAS -->
        <div class="row col-lg-12 main-box" id="reglas">
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Reglas de Reservaci&oacute;n</span></h3>
            <div class="col-lg-12 form-group">
                <label class="col-lg-6 control-label text-left">
                    Regla:
                </label>
                <div class="col-lg-6">
                    <s:textfield id="descripcion" name="descripcion" class="form-control"
                                 maxLength="200"
                                 data-bv-notempty="true"
                                 data-bv-notempty-message="Este campo es requerido" />
                    <span class="help-block" id="reglaMessage" />
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-12 text-center ">
                    <button type="button" id="guardar" class="btn btn-success" onclick="agregaRegla()">Agregar</button>
                </div>
            </div>

            <div id="reglasLista">
                <div class="col-lg-12">    
                    <div class="clearfix">&nbsp;</div> 
                    <table  id="reglasTable" class="table-hover table-responsive">
                        <thead>
                            <tr>
                                <th>Regla</th>
                                <th></th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>

        </div>



    </div>
</div>


<script>

    function agregaHorario() {
        $.ajax({
            type: 'POST',
            url: '/ajax/agregaHorarioAreasAjax.action',
            dataType: 'json',
            data: {'horario.area.id': $("#areaId").val(),
                'horario.dia': $("#horarioDia").val(),
                'horario.horaInicial': $("#horarioHoraInicial").val(),
                'horario.horaFinal': $("#horarioHoraFinal").val()
            },
            cache: false,
            success: function (aData) {

//                BootstrapDialog.alert('El permiso ha sido agregado correctamente.');

//                reload();
//                alert(aData.data[0]);
                if (aData.data[0] !== "OK") {
                    BootstrapDialog.alert('El horario ha sido agregado correctamente.');
                    reloadHorarios();
                } else {
                    BootstrapDialog.alert('No se pudo guardar el horario indicado.');
                }
            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }

    function eliminaHorario(horarioId) {
        BootstrapDialog.confirm('Esto borrará el horario seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaHorarioAreasAjax.action',
                    dataType: 'json',
                    data: {'horario.id': horarioId
                    },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] !== "OK") {
                            BootstrapDialog.alert('El horario ha sido eliminado correctamente.');
                            reloadHorarios();
                        } else {
                            BootstrapDialog.alert('No se pudo guardar el horario indicado.');
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


    function agregaRegla() {
        $.ajax({
            type: 'POST',
            url: '/ajax/agregaReglaAreasAjax.action',
            dataType: 'json',
            data: {'regla.area.id': $("#areaId").val(),
                'regla.descripcion': $("#descripcion").val()
            },
            cache: false,
            success: function (aData) {

                if (aData.data[0] !== "OK") {
                    BootstrapDialog.alert('La regla ha sido guardada correctamente.');
                    reloadReglas();
                } else {
                    BootstrapDialog.alert('No se pudo guardar la regla indicada.');
                }
            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }

    function eliminaRegla(reglaId) {
        BootstrapDialog.confirm('Esto borrará el horario seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaReglaAreasAjax.action',
                    dataType: 'json',
                    data: {'regla.id': reglaId
                    },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] !== "OK") {
                            BootstrapDialog.alert('La regla ha sido eliminada correctamente.');
                            reloadReglas();
                        } else {
                            BootstrapDialog.alert('No se pudo eliminar la regla indicada.');
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
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('select, input').addClass('form-control');
    }
    );
</script>




<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

