<%-- 
    Document   : admin
    Created on : 10-ago-2016, 17:36:46
    Author     : patriciabenitez
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Administraci&oacute;n de Men&uacute; por Condominio</title>

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

//            $("body").removeClass("boxed-layout");

            $("#success").attr('style', 'visibility:hidden');
            $('#filtro').bootstrapValidator({});
            $('#filtro').submit(function (evt) {
                evt.preventDefault();
                $('#buscar').removeAttr("disabled");
            });

            $('#listadoDiv').hide();

            $("#condominio").change(function () {
                if ($("#rol").val() !== "") {
                    reload();
                }
            });

            $("#rol").change(function () {
                reload();
            });



            //reload();
        });

        function reload() {

            $("#success").hide();
            var condominio = $('#condominio').val() === null ? "" : $('#condominio').val();
            var rol = $('#rol').val() === null ? "" : $('#rol').val();

            $('#listadoDiv').show();
            if ($.fn.dataTable.isDataTable('#listado')) {
                table = $('#listado').DataTable({
                    destroy: true,
                    responsive: true
                });
                table.destroy();
            }
            var url = "/ajax/getMenuPorRolesMenuRolAjax.action";

            url = url + "?pkCondominio=" + condominio + "&pkRol=" + rol;

            $('#listado').DataTable({
                dom: 'Bfrtip',
                lengthMenu: [
                    [10, 25, 50, -1],
                    ['10 registros', '25 registros', '50 registros', 'Todos']
                ],
                buttons: [
                    {
                        extend: 'pageLength',
                        text: 'Tamaño de Página'
                    }
//                    ,
//                    'excel'
                ],
                "ajax": {
                    "type": "GET",
                    "url": url,
//                    "dataSrc": function (json) {
                    //Make your callback here.
//                        $('[data-toggle="tooltip"]').tooltip();
//                        return json.data;
//                    }
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
//                ,
//                "order": [[0, 'asc'], [3, 'asc']],
//                "bFilter": false
            });

//            }
        }

    </script>

</head>
<div class="row">
    <div class="col-lg-12">
        <header class="main-box-header clearfix">
            <h1>Administraci&oacute;n de Men&uacute; por Condominio</h1>
        </header>
        <div class="main-box clearfix">

            <div class="clearfix" >&nbsp;</div>
            <h3 style="margin-top: -5px; margin-left: 10px"><span>Asignaci&oacute;n de permisos por condominio</span></h3>
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

            <div class="row " id="busquedaForm">
                <div class="col-lg-12">
                    <div class="clearfix" >&nbsp;</div>
                    <h2 style="margin-left: 10px" >Seleccione Condominio y rol para realizar su selecci&oacute;n</h2>
                    <div class="clearfix" >&nbsp;</div>

                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Condominio:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" 

                                      name="condominio"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label text-right">
                            Roles:
                        </label>
                        <div class="col-lg-9">
                            <s:select id="rol"  class="form-control" 
                                      list="roles" listKey="id" listValue="clave" headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="rol"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </div>
            </div>
            <div id="success" class="alert alert-success">
                <i class="fa fa-check-circle fa-fw fa-lg"></i>
                <strong>OK!</strong> El permiso ha sido agregado correctamente.
            </div>
            <div id="listadoDiv" class="col-lg-12">
                <table id="listado" class="table-hover">
                    <thead>
                        <tr>
                            <th>Men&uacute;</th>
                            <th width="5%">Estatus</th>
                        </tr>
                    </thead>
                </table>
            </div>

            <div class="clearfix" >&nbsp;</div>

        </div>
    </div>






</div>

<script>

    function agrega(obj, pkMenu, pkCondominio) {
        $("#success").attr('style', 'visibility:hidden');
//        obj.disabled = true;
        $.ajax({
            type: 'POST',
            url: '/ajax/agregaMenuRolAjax.action',
            dataType: 'json',
            data: {pkMenu: pkMenu,
                pkCondominio: pkCondominio
            },
            cache: false,
            success: function (aData) {

//                BootstrapDialog.alert('El permiso ha sido agregado correctamente.');

//                reload();
//                alert(aData.data[0]);
                if (aData.data[0] !== "") {
                    $(obj).attr('onclick', '').attr('onclick', 'elimina(' + aData.data[0] + ')');
                    $(obj).find("[class='fa fa-square-o  fa-stack-1x fa-inverse']").removeClass("fa fa-square-o  fa-stack-1x fa-inverse").addClass("fa fa-check-square-o  fa-stack-1x fa-inverse");
                    $("#success").attr('style', 'visibility:visible');
                } else {
                    BootstrapDialog.alert('No se pudo establecer el permiso.');
                }
            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }

    function elimina(pkMenuCondominio) {
        BootstrapDialog.confirm('Esto borrará el permiso seleccionado, ¿Está seguro de proceder?', function (result) {
            if (result) {
                $.ajax({
                    type: 'POST',
                    url: '/ajax/eliminaMenuRolAjax.action',
                    dataType: 'json',
                    data: {pkMenuCondominio: pkMenuCondominio
                    },
                    cache: false,
                    success: function (aData) {

                        BootstrapDialog.alert('El permiso ha sido eliminado correctamente.');
                        reload();

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
        $('select').addClass('form-control');
    }
    );
</script>

