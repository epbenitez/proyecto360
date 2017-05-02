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

            init_contadorTa("comentario", "comentarioContador", 200);

        });



    </script>
</head>
<div class="container">
    <p></p>
    <h1>Alta de &Aacute;reas</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Nueva &Aacute;rea</span></h3>
            <h2 style="margin-left: 10px" >Seleccione Condominio y  Torre para encontrar el departamento.</h2>
            <div class="clearfix" >&nbsp;</div>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="formAltaArea" name="formAltaArea" action="/areas/guardaAreas.action" method="POST">
                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-12 control-label text-left">
                            Condominio:
                        </label>
                        <div class="col-lg-12">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" headerKey=""
                                      name="area.condominio.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-12 control-label text-left">
                            Condominio:
                        </label>
                        <div class="col-lg-12">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" headerKey=""
                                      headerValue="-- Seleccione --"
                                      name="area.condominio.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="condominioMessage" />
                        </div>
                    </div>
                </security:authorize>

                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-12 control-label text-left">
                            Torre:
                        </label>
                        <div class="col-lg-12">
                            <s:select id="torre"  class="form-control"
                                      list="torres" listKey="id" listValue="nombre" 
                                      name="area.torre.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>
                </security:authorize>
                <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-12 control-label text-left">
                            Torre:
                        </label>
                        <div class="col-lg-12">
                            <s:select id="torre"  class="form-control"
                                      list="#{}"
                                      headerValue="-- Seleccione --"
                                      name="area.torre.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>
                </security:authorize>


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
                        <button type="submit" id="guardar" class="btn btn-success">Guardar</button>
                    </div>
                </div>
                <div class="clearfix" >&nbsp;</div>
            </form>
        </div>

    </div>
</div>
<script type="text/javascript" language="javascript" class="init">
    $(document).ready(function () {
        $('select, input').addClass('form-control');
    }
    );
</script>

<script>

    function getTorres() {
        $.ajax({
            type: 'POST',
            url: '/ajax/getTorresSolicitudesAjax.action',
            dataType: 'json',
            data: {pkCondominio: $('#condominio').val()},
            cache: false,
            success: function (aData) {

                $('#torre').get(0).options.length = 0;
                $('#torre').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#torre').get(0).options[$('#torre').get(0).options.length] = new Option(item[1], item[0]);
                    // Display      Value
                });

            },
            error: function () {
                alert("Connection Is Not Available");
            }
        });

        return false;
    }

</script>



<!-- BootStrap Validator -->
<script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>

