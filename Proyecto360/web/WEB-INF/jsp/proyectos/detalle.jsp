<%-- 
    Document   : alta
    Created on : 12-dic-2016, 16:33:39
    Author     : patriciabenitez
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Gesti&oacute;n de Proyectos y Tareas</title>

</head>

<div class="container">
    <p></p>
    <h1>Gesti&oacute;n de Proyectos y Tareas</h1>

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
            </h2>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="guardaProyectosForm" action="/proyectos/guardaDetalleProyectos.action" method="POST">
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        T&iacute;tulo:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="proyecto.titulo"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true" />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Condominio:
                    </label>
                    <div class="col-lg-9">
                        <s:textfield name="proyecto.condominio.nombre" readonly="true" />
                        <span class="help-block" id="condominioMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Categor&iacute;a:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="categoria"  class="form-control" 
                                  list="ambiente.proyectoCategoriaList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.categoria.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                        <span class="help-block" id="categoriaMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Tipo:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="tipo"  class="form-control" 
                                  list="ambiente.proyectoTipoList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.tipo.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                        <span class="help-block" id="tipoMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Prioridad:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="prioridad"  class="form-control" 
                                  list="ambiente.proyectoPrioridadList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.prioridad.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                        <span class="help-block" id="prioridadMessage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Estatus:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="estatus"  class="form-control" 
                                  list="ambiente.proyectoEstatusList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.estatus.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                        <span class="help-block" id="estatusMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Cuota:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="cuota"  class="form-control" 
                                  list="ambiente.proyectoCuotaList" listKey="id" listValue="nombre" headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.cuota.id"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Visible:
                    </label>
                    <div class="col-lg-9">
                        <s:select id="visible"  class="form-control" 
                                  list="service.respuestaBoolean"
                                  headerKey=""
                                  headerValue="-- Seleccione --"
                                  name="proyecto.visible"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido"
                                  required="true"
                                  />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-2 control-label text-left">
                        Fecha de Vencimiento:
                    </label>
                    <div class="col-lg-9">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fecha" name="proyecto.fechaVencimiento"
                                   value="<s:date name="proyecto.fechaVencimiento" format="dd/MM/yyyy" />"
                                   data-bv-notempty="true"
                                   data-bv-message="Este dato no es válido"
                                   required="true" 
                                   readonly="true" >
                            <span class="help-block" id="fechaMessage" />
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-2 control-label text-right">
                        Descripci&oacute;n:
                    </label>
                    <div class="col-lg-9">
                        <s:textarea name="proyecto.descripcion" rows="8" 
                                    data-bv-notempty="true"
                                    data-bv-notempty-message="Este campo es requerido"
                                    required="true" />
                    </div>
                </div>


                <div class="clearfix" >&nbsp;</div>
                <div class="clearfix" >&nbsp;</div>
                <security:authorize ifNotGranted="ROLE_COMITE">
                <div class="form-group">
                    <div class="col-lg-12 text-center ">
                        <s:hidden name="proyecto.id" />
                        <button type="submit" id="enviar" class="btn btn-success">Guardar</button>
                    </div>
                </div>
                </security:authorize>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>
        <div class="clearfix" >&nbsp;</div>
    </div>
    <div class="clearfix" >&nbsp;</div>
</div>

<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
//        $('#enviar').prop("disabled", true);
        $('#guardaProyectosForm').bootstrapValidator({});

        $('#fecha').datepicker();
        $('#fecha').css('cursor', 'default');

        $('select').addClass('form-control');
        $('textarea').addClass('form-control');
        $('input').addClass('form-control');
    }
    );
</script>