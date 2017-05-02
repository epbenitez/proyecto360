<%-- 
    Document   : detalleGruposNotificacion
    Created on : 04-mar-2017, 18:25:39
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

            $('#formAltaSolicitud').bootstrapValidator({});
            $('select, input, textarea').addClass('form-control');

            $('#fechaInicio').datepicker();
            $('#fechaFinal').datepicker();

            $("#torre").change(function () {
                getDepartamentos();
            });

            $("#guardar").click(function () {
            });

            reload();
            $('#log').hide();
        });

        function reload() {

            $('#listadoDiv').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/getListaNotifGruposNotificacionesAjax.action";

            url = url + "?mensaje=" + $('#notificacionId').val();

            $('#listado').DataTable({
                "ajax": {
                    "type": "GET",
                    "url": url
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
                "bFilter": false
            });

        }

        var tableLog;
        var internalId;
        
        //Set AJAX Refresh interval.
//        $(function() {
//            setReloadInterval(10);  //Refresh every 10 seconds. 
//        }

        //Because function takes seconds we * 1000 to convert seconds to milliseconds.
        function setReloadInterval(reloadTime, identificador) {
            if (reloadTime > 0) {
                internalId = setInterval("consultaLog('"+identificador+"')", (reloadTime * 1000));
            }
        }

        //Auto Refresh JQuery DataTable 
//        function reloadTable() {
//            tableLog.ajax.reload();
//        }

        function consultaLog(identificador) {

            $('#log').show();
            if ($.fn.dataTable.isDataTable('#listadoLog')) {
                tableLog = $('#listadoLog').DataTable({
                    destroy: true,
                    responsive: true
                });
                tableLog.destroy();
            }
            var url = "/ajax/consultaLogEnvioCorreosNotificacionesAjax.action";

            url = url + "?mensaje=" + $('#notificacionId').val() + "&identificador="+identificador;

            $('#listadoLog').DataTable({
                "ajax": {
                    "type": "GET",
                    "url": url
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
                "bFilter": false
            });

        }

    </script>
</head>
<div class="container">
    <p></p>
    <h1>Grupos por Mensaje</h1>

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
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="formAltaSolicitud" name="formAltaSolicitud" action="/notificaciones/mensajeria/guardaMensajeria.action" method="POST"  enctype="multipart/form-data">

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Condominio:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="notificacion.condominio.nombre" class="form-control" readonly="true" />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>

            </form>
        </div>


        <div class="clearfix" >&nbsp;</div>
        <div id="listadoDiv" class="row main-box col-lg-12">
            <div class="clearfix" >&nbsp;</div>
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Grupos</span></h3>
            </h3>
            <form id="formAltaGrupos" name="formAltaGrupos" action="/notificaciones/mensajeria/guardaGrupoNotificacionMensajeria.action" method="POST">
                <div class="form-group">
                    <label class="col-lg-1 control-label text-right">
                    </label>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Grupo:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="grupos"  class="form-control" 
                                      list="grupos" listKey="id" listValue="nombre" 
                                      name="grupo.id"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                    <div class="col-lg-3 text-center ">
                        <s:hidden name="notificacion.id" id="notificacionId" />
                        <button type="submit" id="guardar" class="btn btn-success">Agregar Grupo</button>
                    </div>
                </div>

                <div class="clearfix" >&nbsp;</div>
            </form>
            <div class="clearfix" >&nbsp;</div>
            <table id="listado" class="table-hover ">
                <thead>
                    <tr>
                        <th width="10%">#</th>
                        <th width="70%">Grupo</th>
                        <th width="10%">Eliminar</th>
                        <th width="10%">Correo</th>
                    </tr>
                </thead>
            </table>
        </div>

        <div id="log" class="row main-box col-lg-12">
            <div class="clearfix" >&nbsp;</div>
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Log de envío de correos</span></h3>
            <div class="clearfix" >&nbsp;</div>
            <table id="listadoLog" class="table-hover ">
                <thead>
                    <tr>
                        <th width="20%">Fecha</th>
                        <th width="20%">Grupo</th>
                        <th width="20%">Mensaje</th>
                        <th width="20%">Usuario</th>
                        <th width="20%">Departamento</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>
</div>

<script>


    function eliminaNotifGrupo(notifGpo) {
        BootstrapDialog.confirm('Esto borrará el grupo seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaNotifGrupoNotificacionesAjax.action',
                    dataType: 'json',
                    data: {'notifGpo': notifGpo
                    },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] !== "OK") {
                            BootstrapDialog.alert('El grupo ha sido eliminado correctamente.');
                            reload();
                        } else {
                            BootstrapDialog.alert('No se pudo eliminar el grupo indicado.');
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

    function enviarNotifGrupo(notifGpo, identificador) {
        BootstrapDialog.confirm('Esto enviará correos electrónicos a los departamentos pertenecientes al grupo indicado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                consultaLog(identificador);
                setReloadInterval(2,identificador);
                $.ajax({
                    type: 'POST',
                    url: '/ajax/enviarNotifGrupoNotificacionesAjax.action',
                    dataType: 'json',
                    data: {'notifGpo': notifGpo,
                        'identificador':identificador
                    },
                     beforeSend: function () {
                        BootstrapDialog.alert({
                                title: 'Información',
                                message: 'Estamos procesando su información. Esto puede demorar varios minutos. Por favor, espere.'
                            });
                        },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] == "OK") {
                            BootstrapDialog.alert('El(los) correo(s) ha(n) sido enviado(s) correctamente.');
                            clearInterval(internalId);
                            consultaLog(identificador);
                        } else if (aData.data[0] == "EXCEPCION") {
                            BootstrapDialog.alert('Uno o varios de los correos(s) especificado(s), no pudieron ser enviados.');
                            reload();
                        } else {
                            BootstrapDialog.alert('No se pudo enviar el departamento indicado.');
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



<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

