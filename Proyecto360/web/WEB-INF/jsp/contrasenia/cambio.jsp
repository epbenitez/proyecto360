<%-- 
    Document   : cambio
    Created on : 18-ago-2015, 11:54:02
    Author     : Patricia Benitez
--%>
<%@taglib prefix="s" uri="/struts-tags" %>

<div class="row">
    <div class="col-lg-12">

        <div class="row">
            <div class="col-lg-12">
                <h1>Cambio de Contrase&ntilde;a</h1>
            </div>
        </div>

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

        <div class="row">
            <div class="col-lg-12">
                <div class="main-box clearfix" >
                    <div class="main-box-body ">
                        <form id="passwordForm" method="post" class="form-horizontal" action="/actualizaContrasenia.action" style="margin-top: 20px;">
                            <div class="form-group">
                                <label class="col-lg-3 control-label">Contrase&ntilde;a Actual</label>
                                <div class="col-lg-8">

                                    <input type="password" class="form-control" name="contraseniaActual" id="contraseniaActual" placeholder="Contraseña Actual"
                                           data-bv-message="Este dato no es válido"
                                           required="true" 
                                           data-bv-notempty="true"
                                           data-bv-notempty-message="La contraseña actual es requerida"
                                           data-bv-stringlength="true" 
                                           data-bv-stringlength-min="4" 
                                           data-bv-stringlength-max="12" 
                                           data-bv-stringlength-message="Tu contraseña anterior debe tener mínimo 4 caracteres"
                                           >
                                    <span class="help-block" id="contraseniaActualMessage" />
                                </div>

                            </div>   
                            <div class="form-group">
                                <label class="col-lg-3 control-label">Contrase&ntilde;a Nueva</label>
                                <div class="col-lg-8">
                                    <input type="password" class="form-control" name="contraseniaNueva" id="contraseniaNueva" placeholder="Nueva contraseña"
                                           data-bv-message="Este dato no es válido"
                                           required="true" 
                                           data-bv-notempty="true"
                                           data-bv-notempty-message="La nueva contraseña es requerida"
                                           data-bv-stringlength="true" 
                                           data-bv-stringlength-min="4" 
                                           data-bv-stringlength-max="12" 
                                           data-bv-stringlength-message="Tu contraseña  debe tener mínimo 4 caracteres"
                                           />
                                    <span class="help-block" id="contraseniaActualMessage" />
                                </div>

                            </div> 
                            <div class="form-group">
                                <label class="col-lg-3 control-label">Repite Contrase&ntilde;a Nueva</label>
                                <div class="col-lg-8">
                                    <input type="password" class="form-control"  name="contraseniaNuevaRepetir" id="contraseniaNuevaRepetir" placeholder="Nueva contraseña"
                                           data-bv-message="Este dato no es válido"
                                           required="true" 
                                           data-bv-notempty="true"
                                           data-bv-notempty-message="La nueva contraseña es requerida"
                                           data-bv-stringlength="true" 
                                           data-bv-stringlength-min="4" 
                                           data-bv-stringlength-max="12" 
                                           data-bv-stringlength-message="Tu contraseña  debe tener mínimo 4 caracteres"
                                           />
                                    <span class="help-block" id="contraseniaActualMessage" />
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="col-lg-5 col-lg-offset-3">
                                    <button type="submit" class="btn btn-primary">Cambiar Contraseña</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {

        //SE AGREGA LA CLASE A ELEMENTOS QUE SON CREADOS MEDIANTE
        //STRUTS
        $('input[type=text]').addClass('form-control');
    });

    $(document).ready(function () {

        $('#passwordForm')
                .bootstrapValidator({
                    excluded: [':disabled'],
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        contraseniaNueva: {
                            validators: {
                                notEmpty: {
                                    message: 'La nueva contraseña es requerida'
                                },
                                identical: {
                                    field: 'contraseniaNuevaRepetir',
                                    message: 'La contraseña y su confirmación deben ser iguales.'
                                }
                            }
                        },
                        contraseniaNuevaRepetir: {
                            validators: {
                                notEmpty: {
                                    message: 'La nueva contraseña es requerida'
                                },
                                identical: {
                                    field: 'contraseniaNueva',
                                    message: 'La contraseña y su confirmación deben ser iguales.'
                                }
                            }
                        },
                    }
                });
    });
</script>