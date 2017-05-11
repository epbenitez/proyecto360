<%-- 
    Document   : detalle
    Created on : 26-feb-2016, 21:45:12
    Author     : luistlatelpa
--%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>

<head>
    <title>Detalle de Ticket</title>
    <script src="/js/UtilString.js" type="text/javascript"></script>
    <script type="text/javascript" language="javascript" class="init">
        $(document).ready(function () {
            $('input, select').addClass('form-control');
            $('#historial').DataTable({
                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    "columnDefs": [
                        {
                            "targets": [3],
                            "visible": false,
                            "searchable": false
                        }
                    ],
                </security:authorize>
                "aoColumnDefs": [
                    {"sClass": "alignCenter",
                        "aTargets": [1, 2]
                    }
                ],
                "order": [],
                "columnDefs": [{
                        "targets": 'no-sort',
                        "orderable": false,
                    }]
            });

            $('#fechaCompromiso').datepicker({
                language: 'es',
                format: 'dd-mm-yyyy'
            });
            $('#fechaCompromiso').css('cursor', 'default');

            $('#fechaLlegadaAdmin').datepicker({
                language: 'es',
                format: 'dd-mm-yyyy'
            });
            $('#fechaLlegadaAdmin').css('cursor', 'default');

            $('#fechaEntrega').datepicker({
                language: 'es',
                format: 'dd-mm-yyyy'
            });
            $('#fechaEntrega').css('cursor', 'default');

            $('#fechaCompromisoDiv').hide();
            $('#fechaLlegadaAdminDiv').hide();
            $('#fechaEntregaDiv').hide();

            $('#historial_length').hide();
            $('#historial_filter').hide();
            $('#historial_info').hide();
            $('#historial_paginate').hide();

            $('#estadoSolicitud').change(function () {
                var id = this.value;
//                alert(id);
                $('#fechaCompromisoDiv').hide();
                $('#fechaLlegadaAdminDiv').hide();
                $('#fechaEntregaDiv').hide();
                if (id == 2) {
                    $('#fechaCompromisoDiv').show();
                }
                if (id == 3) {
                    $('#fechaLlegadaAdminDiv').show();
                    $('#fechaEntregaDiv').show();
                }
            });

            $('#estatusForm').submit(function (evt) {
                var id = $('#estadoSolicitud').val();
                if (id == 2 && $('#fechaCompromiso').val() == "") {
                    BootstrapDialog.alert({
                        title: 'Atención',
                        message: 'Para poder cambiar el estatus de la solicitud, es necesario establecer la fecha de compromiso.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
                    evt.preventDefault();
                    $('#buscar').removeAttr("disabled");
                }
                if (id == 3 && ($('#fechaLlegadaAdmin').val() == "" || $('#fechaEntrega').val() == "")) {
                    BootstrapDialog.alert({
                        title: 'Atención',
                        message: 'Para poder cambiar el estatus de la solicitud, es necesario establecer la fecha de llegada a la administración y la fecha de entrega.',
                        type: BootstrapDialog.TYPE_WARNING
                    });
                    evt.preventDefault();
                    $('#buscar').removeAttr("disabled");
                }

            });

            init_contadorTa("comentario", "comentarioContador", 200);
        });
    </script>
</head>
<div class="row">
    <div class="col-lg-12">
        <div class="main-box clearfix">
            <header class="main-box-header clearfix">
                <h1>Detalle de Ticket</h1>
            </header>

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

            <s:if test="!hasActionErrors()">
                <form id="estatusForm" action="/solicitudes/guardaDetalleSolicitudes.action" method="POST" >

                    <h3 style="margin-top: -5px; margin-left: 10px"><span>Informaci&oacute;n de la solicitud</span></h3>
                    <div class="col-md-12">
                        <label for="maskedDate">Usuario:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <s:textfield name="histAbrioSolicitud.usuario.usuario" class="form-control" id="maskedDate" readonly="true" />
                        </div>
                    </div>

                    <div class="col-md-12">
                        <label for="maskedDate">Folio:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-building"></i></span>
                            <s:set var="folio"><s:property  value="solicitud.condominio.clave" />-<s:property  value="solicitud.tipoSolicitud.clave" />-<s:property  value="getText('{0,number,0000}',{solicitud.consecutivo})" /></s:set>
                            <s:textfield name="folio" class="form-control" readonly="true" />
                        </div>
                    </div>

                    <div class="col-md-12">
                        <label for="maskedDate">Inmueble:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-building"></i></span>
                                <s:textfield name="solicitud.condominio.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                                <%--
                    <div class="col-md-12">
                        <label for="maskedDate">Torre:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-building"></i></span>
                                <s:textfield name="solicitud.departamento.torre.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label for="maskedDate">Departamento:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-building"></i></span>
                                <s:textfield name="solicitud.departamento.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                        --%>
                    <div class="col-md-12">
                        <label for="maskedDate">Tipo de Inmueble:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-database"></i></span>
                                <s:textfield name="solicitud.condominio.tipoInmueble.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label for="maskedDate">Tipo de Servicio:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-database"></i></span>
                                <s:textfield name="solicitud.tipoSolicitud.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label for="maskedDate">&Aacute;rea:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-database"></i></span>
                                <s:textfield name="solicitud.area.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label for="maskedDate">Tipo de trabajo:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-database"></i></span>
                                <s:textfield name="solicitud.categoriaSolicitud.nombre" class="form-control" readonly="true" />
                        </div>
                    </div>
                    <div class="col-md-12">
                        <label for="maskedDate">Estatus:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-sliders"></i></span>
                                <s:select id="estadoSolicitud"  class="form-control" 
                                          list="estados" listKey="id" listValue="nombre" headerKey=""
                                          headerValue="-- Seleccione --"
                                          name="solicitud.estadoSolicitud.id"
                                          />
                            <span class="help-block" id="estadoSolicitudMessage" />
                        </div>
                    </div>
                    <!-- FECHAS -->
                    <div id="fechaCompromisoDiv" class="col-md-12">
                        <label for="maskedDate">Fecha Compromiso:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fechaCompromiso" name="fechaCompromiso"
                                   readonly="true">
                            <span class="help-block" id="fechaCompromisoMessage"></span>
                        </div>
                    </div>

                    <div id="fechaLlegadaAdminDiv" class="col-md-12">
                        <label for="maskedDate">Fecha de Soluci&oacute;n</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fechaLlegadaAdmin" name="fechaLlegadaAdmin"
                                   readonly="true">
                            <span class="help-block" id="fechaLlegadaAdminMessage"></span>
                        </div>
                    </div>

                    <div id="fechaEntregaDiv" class="col-md-12">
                        <label for="maskedDate">Fecha de Notificaci&oacute;n</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fechaEntrega" name="fechaEntrega"
                                   readonly="true">
                            <span class="help-block" id="fechaEntregaMessage"></span>
                        </div>
                    </div>

                    <!-- FECHAS -->
<!--                    <div class="col-md-12">
                        <label for="maskedDate">Cond&oacute;mino que solicita:</label>
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                <s:textfield name="solicitud.solicitante" class="form-control" readonly="true" />
                        </div>
                    </div> -->
                    
                    <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ADMINCONDOMINIO">       
                        <div class="col-md-12">
                            <label for="maskedDate">Comentarios:</label>
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-quote-left"></i></span>
                                    <s:textfield name="solicitud.asunto" id="comentario" class="form-control" />
                            </div>
                        </div> 
                        <div class="form-group">
                            <div class="col-lg-2" ></div>    
                            <div class="col-lg-9" id="comentarioContador" text-right></div>    
                        </div> 
                        <div class="clearfix" >&nbsp;</div>
                        <div class="form-group">
                            <div class="col-lg-12 text-right ">
                                <s:hidden name="solicitud.id" />
                                <s:if test="solicitud.estadoSolicitud.id==3 || solicitud.estadoSolicitud.id==4">
                                    <button type="button" id="buscar" class="btn btn-success" disabled="disables">Esta solicitud ya no puede ser cambiada de estatus</button>
                                </s:if><s:else>
                                    <button type="submit" id="buscar" class="btn btn-success">Guardar</button>
                                </s:else>
                            </div>
                        </div>
                    </security:authorize> 

                    <div class="clearfix">&nbsp;</div>  
                    <div class="clearfix" >&nbsp;</div>
                    <h3 style="margin-top: -5px; margin-left: 10px"><span>Historial de la solicitud</span></h3>
                    <div class="col-lg-12">
                        <div class="main-box">
                            <table id="historial" class="table-hover display">
                                <thead>
                                    <tr>
                                        <th width="10%">Estatus</th>
                                        <th width="10%">Fecha</th>
                                        <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ADMINCONDOMINIO">
                                        <th width="10%">Usuario</th>
                                        </security:authorize>
                                        <th width="70%">Comentarios</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <s:iterator value="historial" >
                                        <tr>
                                            <td><s:property value="estadoSolicitud.nombre" escape="false" /></td>
                                            <td><s:date name="fecha" format="dd/MM/yyyy" /></td>
                                            <security:authorize ifAnyGranted="ROLE_ADMIN,ROLE_ADMINCONDOMINIO">
                                            <td><s:property value="usuario.usuario" escape="false" /></td>
                                            </security:authorize>
                                            <td><s:property value="comentario" escape="false" /></td>
                                        </tr>
                                    </s:iterator>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </form>
            </s:if>
        </div>
    </div>
</div>
