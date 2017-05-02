<%-- 
    Document   : detalle
    Created on : 28-feb-2017, 13:51:26
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
            var url = "/ajax/getListaDocumentosNotificacionesAjax.action";

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

    </script>
</head>
<div class="container">
    <p></p>
    <h1>Detalle de Mensaje</h1>

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

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Asunto:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="notificacion.asunto" class="form-control" />
                        <span class="help-block" id="torreMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Mensaje:
                    </label>
                    <div class="col-lg-9">
                        <s:textarea name="notificacion.mensaje" class="form-control" />
                        <span class="help-block" id="torreMessage" />
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Fecha Inicial:
                    </label>
                    <div class="col-lg-9">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fechaInicio" name="notificacion.fechaInicio"
                                   readonly="true" 
                                   value="<s:date name="notificacion.fechaInicio" format="dd/MM/yyyy" />"
                                   >
                            <span class="help-block" id="fechaMessage" />
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Fecha Final:
                    </label>
                    <div class="col-lg-9">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fechaFinal" name="notificacion.fechaFinal"
                                   readonly="true" 
                                   value="<s:date name="notificacion.fechaFinal" format="dd/MM/yyyy" />"
                                   >
                            <span class="help-block" id="fechaMessage" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Estatus:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="estatus"  class="form-control"
                                  list="ambiente.notificacionEstatusList" listKey="id" listValue="nombre" 
                                  name="notificacion.estatus.id"
                                  />
                        <span class="help-block" id="departamentoMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-2" ></div>    
                    <div class="col-lg-9" id="comentarioContador" text-right></div>    
                </div> 
                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <div class="form-group">
                    <div class="col-lg-12 text-center ">
                        <s:hidden name="notificacion.id" id="notificacionId" />
                        <s:hidden name="notificacion.condominio.id" id="condominioId" />
                        <button type="submit" id="guardar" class="btn btn-success">Guardar</button>
                    </div>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>


        <div class="clearfix" >&nbsp;</div>
        <div id="listadoDiv" class="row main-box col-lg-12">
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Documentos</span></h3>
            </h3>
            <form id="formAltadocumento" name="formAltadocumento" action="/notificaciones/mensajeria/guardaDocumentoMensajeria.action" method="POST"  enctype="multipart/form-data">
                <div class="form-group">
                    <label class="col-lg-1 control-label text-right">
                    </label>
                    <div class="col-lg-7">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-file-excel-o"></i></span>
                                <s:file  class="file" data-show-preview="true" labelposition="left" name="upload" /> 
                            <!--accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"-->
                            <span class="help-block" id="estadoMessage" />
                        </div>
                    </div>
                    <div class="col-lg-3 text-center ">
                        <s:hidden name="notificacion.id" id="notificacionId" />
                        <s:hidden name="notificacion.condominio.id" id="condominioId" />
                        <button type="submit" id="guardar" class="btn btn-success">Agregar Documento</button>
                    </div>
                </div>

                <div class="clearfix" >&nbsp;</div>
            </form>
            <div class="clearfix" >&nbsp;</div>
            <table id="listado" class="table-hover ">
                <thead>
                    <tr>
                        <th width="10%">#</th>
                        <th width="80%">Documento</th>
                        <th width="10%">Eliminar</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>
</div>

<script>


    function eliminaDocumento(documentoId) {
        BootstrapDialog.confirm('Esto borrará el documento seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaDocumentoNotificacionesAjax.action',
                    dataType: 'json',
                    data: {'documentoId': documentoId
                    },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] !== "OK") {
                            BootstrapDialog.alert('El documento ha sido eliminado correctamente.');
                            reload();
                        } else {
                            BootstrapDialog.alert('No se pudo eliminar el documento indicado.');
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

