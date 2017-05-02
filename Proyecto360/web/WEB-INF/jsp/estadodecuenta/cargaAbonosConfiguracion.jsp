<%-- 
    Document   : cargaAbonosConfiguracion
    Created on : 06-ene-2016, 22:21:28
    Author     : Patricia Benitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<head>

    <link rel="stylesheet" type="text/css" href="/dataTables/buttons/1.1.0/css/jquery.dataTables.min.css" />
    <link rel="stylesheet" type="text/css" href="/dataTables/buttons/1.1.0/css/buttons.dataTables.min.css" />

    <script type="text/javascript" src="/dataTables/buttons/1.1.0/js/dataTables.buttons.min.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
    <script type="text/javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
    <script type="text/javascript" src="//cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
    <script type="text/javascript" src="/dataTables/buttons/1.1.0/js/buttons.html5.min.js"></script>

    <script type="text/javascript" src="//cdn.datatables.net/buttons/1.1.0/js/buttons.print.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            
            $('#btnRevertir').click(function () {
                eliminar();
            })
            
            $('#log').DataTable({
                dom: 'Bfrtip',
                lengthMenu: [
                    [10, 25, 50, -1],
                    ['10 registros', '25 registros', '50 registros', 'Todos']
                ],
                buttons: [
                    {
                        extend: 'pageLength',
                        text: 'Tamaño de Página'
                    },
                    'excel'
//                    ,
//                    'pdf',
//                    {
//                        extend: 'print',
//                        text: 'Imprimir'
//                    }
                ],
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
        });
        
        function eliminar() {
            BootstrapDialog.confirm('Esto borrará todos los registros que coinciden con el identificador: <s:property value="identificadorCarga" />, ¿Está seguro de proceder?', function (result) {
                if (result) {
                    $("#revertir").submit();
                } else {
                }
            });
        }
    </script>
</head>

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

            <h3 style="margin-top: 5px; margin-left: 10px"><span>Resultado de la operaci&oacute;n de carga de abonos</span></h3>

            <p>Este identificador le permitir&aacute; revertir el movimiento, en caso de ser necesario.</p>
            <div class="col-lg-4"><b>Identificador de la carga:</b></div>
            <div class="col-lg-4"><s:property value="identificadorCarga" /> </div>
            <div class="col-lg-3">
                <a id="btnRevertir" href="#" class="btn btn-primary pull-right">
                    <i class="fa fa fa-undo fa-lg"></i> Revertir
                </a>
            </div>
            <div class="clearfix">&nbsp;</div> 
            <div class="clearfix">&nbsp;</div> 
             <form id="revertir" action="/upload/revertirAbonosMasivos.action" method="post" >
                <s:hidden name="identificadorCarga" />
            </form>
        </div>
    </div>

    <div class="main-box clearfix">
        <div class="col-lg-12">    
            <div class="clearfix">&nbsp;</div> 
            <table  id="log" class="display">
                <thead>
                    <tr>
                        <th>Rengl&oacute;n</th>
                        <th>Torre</th>
                        <th>Departamento</th>
                        <th>Concepto</th>
                        <th>Fecha</th>
                        <th>Monto</th>
                        <th>Estatus</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="log" >
                        <s:property escape="false" ></s:property>
                    </s:iterator>
                </tbody>
            </table>
        </div>
    </div>
</div>
