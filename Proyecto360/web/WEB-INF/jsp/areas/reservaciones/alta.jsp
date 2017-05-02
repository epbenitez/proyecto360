<%-- 
    Document   : alta
    Created on : 23-sep-2016, 12:36:34
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

            $('#fecha').datepicker();

            $('#fecha').css('cursor', 'default');




            $("#condominio").change(function () {
                getTorres();
            });

            $("#torre").change(function () {
                getDepartamentos();
            });

            $("#departamento").change(function () {
                getAreaPorReservar();
            });

            $("#personas").click(function () {
                var horario = $('#areaReservacion option:selected').text();
                var diaSeleccionado = $("#fecha").data('datepicker').getFormattedDate('DD');
                var intIndexOf = horario.indexOf(diaSeleccionado);
//                alert(intIndexOf);
                if(intIndexOf<0){
                    BootstrapDialog.alert("Debe seleccionar en el calendario, un día de la semana que coincida con la selección del Área ("+horario+"). Ya que usted ha seleccionado una fecha en " + diaSeleccionado);
                    $('#fecha').val("");
        
                }
                
            });
            
            if($("#condominioId").val()!==""){
                getTorres();
            }

        });



    </script>
</head>
<div class="container">
    <p></p>
    <h1>Alta de Reservaci&oacute;n de &Aacute;reas</h1>

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
            <h3 style="margin-top: 5px; margin-left: 10px"><span>Nueva Reservaci&oacute;n de &Aacute;rea</span></h3>
            <h2 style="margin-left: 10px" >Seleccione Condominio y  Torre para encontrar el departamento.</h2>
            <div class="clearfix" >&nbsp;</div>
            <div class="clearfix" >&nbsp;</div><div class="clearfix" >&nbsp;</div>
            <form id="formAltaArea" name="formAltaArea" action="/areas/guardaReservaciones.action" method="POST">
                <s:hidden id="condominioId" name="reservacion.departamento.condominio.id" />
                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                    <div class="form-group">
                        <label class="col-lg-12 control-label text-left">
                            Condominio:
                        </label>
                        <div class="col-lg-12">
                            <s:select id="condominio"  class="form-control" 
                                      list="condominios" listKey="id" listValue="nombre" headerKey=""
                                      name="reservacion.departamento.condominio.id"
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
                                      name="reservacion.departamento.condominio.id"
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
                                      name="reservacion.departamento.torre.id"
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
                                      name="reservacion.departamento.torre.id"
                                      data-bv-notempty="true"
                                      data-bv-notempty-message="Este campo es requerido"
                                      />
                            <span class="help-block" id="torreMessage" />
                        </div>
                    </div>
                </security:authorize>
                <div class="form-group">
                    <label class="col-lg-12 control-label">
                        Departamento:
                    </label>
                    <div class="col-lg-12">
                        <s:select id="departamento"  class="form-control"
                                  list="#{}"
                                  headerValue="-- Seleccione --"
                                  name="reservacion.departamento.id"
                                  />
                        <span class="help-block" id="torreMessage" />
                    </div>
                </div>


                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        &Aacute;rea:
                    </label>
                    <div class="col-lg-12">
                        <s:select id="areaReservacion" name="reservacion.areaHorario.id" class="form-control"
                                  list="#{}"
                                  headerValue="-- Seleccione --"
                                  data-bv-notempty="true"
                                  data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="areaMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Fecha:
                    </label>
                    <div class="col-lg-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                            <input type="text" class="form-control" id="fecha" name="reservacion.fecha"
                                   data-bv-notempty="true"
                                   data-bv-message="Este dato no es válido"
                                   required="true" 
                                   readonly="true" >
                            <span class="help-block" id="fechaMessage" />
                        </div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Personas:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="personas" name="reservacion.personas" class="form-control"
                                     maxLength="5"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="depositoGarantiaMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-lg-12 control-label text-left">
                        Comentarios:
                    </label>
                    <div class="col-lg-12">
                        <s:textfield id="comentarios" name="reservacion.comentario" class="form-control"
                                     maxLength="200"
                                     data-bv-notempty="true"
                                     data-bv-notempty-message="Este campo es requerido" />
                        <span class="help-block" id="diasAnticipacionCancelarMessage" />
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-12" ></div>    
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


    function getDepartamentos() {
        var dialog;
        $.ajax({
            type: 'POST',
            url: '/ajax/getDepartamentosSolicitudesAjax.action',
            dataType: 'json',
            data: {pkCondominio: $('#condominio').val(), pkTorre: $('#torre').val()},
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
                $('#departamento').get(0).options[0] = new Option("-- Seleccione --", "");

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


    function getAreaPorReservar() {
        $.ajax({
            type: 'POST',
            url: '/ajax/getAreaPorReservarAreasAjax.action',
            dataType: 'json',
            data: {condominioId: $('#condominio').val(),
                torreId: $('#torre').val()
            },
            cache: false,
            success: function (aData) {

                $('#areaReservacion').get(0).options.length = 0;
                $('#areaReservacion').get(0).options[0] = new Option("-- Seleccione --", "");

                $.each(aData.data, function (i, item) {
//                            console.log(item[0]);
                    $('#areaReservacion').get(0).options[$('#areaReservacion').get(0).options.length] = new Option(item[1], item[0]);
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

