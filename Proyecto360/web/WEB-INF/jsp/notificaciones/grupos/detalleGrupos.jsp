<%-- 
    Document   : edicionGrupos
    Created on : 15-feb-2017, 20:48:29
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
            $('select, input').addClass('form-control');

            $("#torre").change(function () {
                getDepartamentos();
            });

            $("#agregar").click(function () {
                agregaDepto();
            });
            
            //TODO: AGREGAR LA PRIMERA OPCION
            $("#torre").prepend("<option value='0' selected='selected'>-- Todos --</option>");
            $("#departamento").prepend("<option value='0' selected='selected'>-- Todos --</option>");

            reload();

        });

        function reload() {


            var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();


            $('#listadoDiv').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/getListaGruposDepartamentosNotificacionesAjax.action";

            url = url + "?grupo.id=" + $('#grupoId').val();

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
    <h1>Gesti&oacute;n de Grupo</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Departamentos por Grupo</span></h3>
            <h2 style="margin-left: 10px" >Seleccione  Torre y Departamento para agregarlo al grupo seleccionado.</h2>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="formAltaSolicitud" name="formAltaSolicitud" action="/solicitudes/guardaSolicitudes.action" method="POST">
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Nombre del grupo:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="grupo.nombre" id="nombre"
                                     />
                        <span class="help-block" id="tipoSolicitudMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Condominio:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="grupo.condominio.nombre" class="form-control" readonly="true" />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Torre:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="torre"  class="form-control"
                                  list="torres" listKey="id" listValue="nombre" 
                                  name="solicitud.departamento.torre.id"
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
                                  list="#{null}"
                                  headerValue="-- Todos --"
                                  name="solicitud.departamento.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
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
                        <s:hidden name="grupo.id" id="grupoId" />
                        <s:hidden name="grupo.condominio.id" id="condominioId" />
                        <button type="button" id="agregar" class="btn btn-success">Agregar Departamento(s)</button>
                    </div>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>


        <div class="clearfix" >&nbsp;</div>
        <div id="listadoDiv" class="col-lg-12">
            <div class="main-box">

                <table id="listado" class="table-hover ">
                    <thead>
                        <tr>
                            <th width="40%">Torre</th>
                            <th width="50%">Departamento</th>
                            <th width="10%"></th>
                        </tr>
                    </thead>
                </table>
            </div>
        </div>

    </div>
</div>

<script>


    function getDepartamentos() {
        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/getDepartamentosSolicitudesAjax.action',
            dataType: 'json',
            data: {pkCondominio: $('#condominioId').val(), pkTorre: $('#torre').val()},
            beforeSend: function () {
                dialog = BootstrapDialog.show({
                    title: 'Información',
                    message: 'Sus datos están siendo cargados. Por favor, espere.',
                    closable: false
                });
            },
            cache: false,
            success: function (aData) {
                dialog.close();
                $('#departamento').get(0).options.length = 0;
                $('#departamento').get(0).options[0] = new Option("-- Todos --", "0");

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

    function eliminaDepartamento(grupoDepartamentoId) {
        BootstrapDialog.confirm('Esto borrará el departamento seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaDepartamentoNotificacionesAjax.action',
                    dataType: 'json',
                    data: {'grupoDepartamentoId': grupoDepartamentoId
                    },
                    cache: false,
                    success: function (aData) {

                        if (aData.data[0] !== "OK") {
                            BootstrapDialog.alert('El departamento ha sido eliminado correctamente.');
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

    function agregaDepto() {
        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/agregaDepartamentoNotificacionesAjax.action',
            dataType: 'json',
            data: {'grupo.id': $('#grupoId').val(),
                    'departamentoId':$('#departamento').val(),
                    'torreId': $('#torre').val(),
                    'nombreGrupo': $('#nombre').val()
            },
            beforeSend: function () {
                dialog = BootstrapDialog.show({
                    title: 'Información',
                    message: 'Sus datos están siendo procesados. La operación podría demorar varios minutos. Por favor, espere.',
                    closable: false
                });
            },
            cache: false,
            success: function (aData) {
                dialog.close();
                if (aData.data[0] == "OK") {
                    BootstrapDialog.alert('El(los) departamento(s) ha(n) sido agregado(s) correctamente.');
                    reload();
                } else if (aData.data[0] == "EXISTENTE") {
                    BootstrapDialog.alert('Uno o varios de los departamento(s) especificado, ya existían en el grupo.');
                    reload();
                } else {
                    BootstrapDialog.alert('No se pudo agregar el departamento indicado.');
                }

            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });
    }

</script>



<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

