<%-- 
    Document   : usuariosCondominio
    Created on : 12-oct-2016, 21:18:49
    Author     : patriciabenitez
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <title>Accesos por Condominio</title>
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

                $('.fancybox').fancybox({
                    autoSize: true,
                    afterClose: function () {
                        reload();
                    }
                });

                $('#listadoDiv').hide();

                $("#buscar").click(function () {
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
                    var url = "/ajax/busquedaUsuariosAjax.action";

                    url = url + "?pkCondominio=" + condominio;

                    $('#listado').DataTable({
                        dom: 'Bfrtip',
                        buttons: [
                            'excel'
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
            });


        </script>
    </head>
    <body>
        <div class="container">
            <p></p>
            <h1>Accesos</h1>

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
                        <div class="col-lg-12 text-right ">
                            <a class="btn btn-default" href="#" id="buscar" class="btn btn-default">Consultar Accesos</a>
                        </div>
                    </div>
                    <div class="clearfix" >&nbsp;</div>
                </div>
                <div class="clearfix" >&nbsp;</div>
                <div id="listadoDiv" class="col-lg-12">
                    <div class="main-box">
                        <table id="listado" class="table-hover">
                            <thead>
                                <tr>
                                    <th>Condominio</th>
                                    <th>Torre</th>
                                    <th>Departamento</th>
                                    <th>Usuario</th>
                                    <th>Contraseña</th>
                                    <th></th>
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
    </body>

</html>
