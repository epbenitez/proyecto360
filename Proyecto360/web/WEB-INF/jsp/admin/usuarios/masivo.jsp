<%-- 
    Document   : masivo
    Created on : 22-jun-2016, 21:08:47
    Author     : erikapatriciabenitezsoto
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Carga de Condominios y Usuarios</title>

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

        <script type="text/javascript">
            $(document).ready(function () {
                $('#tipoInmueblesId').DataTable();
                $('#tipoInmueblesId_length').hide();
                $('#tipoInmueblesId_filter').hide();
                $('#tipoInmueblesId_info').hide();
                $('#tipoInmueblesId_paginate').hide();

            });
        </script>
    </head>


    <s:form id="cargaForm" action="masivoResultadoAltaUsuario.action" method="POST" enctype="multipart/form-data">

        <h1>Carga de Inmuebles</h1>


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

                <h3 style="margin-top: 5px; margin-left: 10px"><span>Selecci&oacute;n de Archivo</span></h3>
                <p>
                    Es necesario ingresar el formato correcto. 
                    Si no cuenta con el formato, puede descargarlo <a target="_blank" href="/resources/ejemplos/360-CargaMasivaUsuarios.xlsx" >  aquí</a>.
                </p>
                <p>La columna "Tipo de Inmueble" debe contener uno de los siguientes valores:</p>
                <div class="main-box clearfix">
                    <div class="col-lg-6">    
                        <div class="clearfix">&nbsp;</div> 
                        <table  id="tipoInmueblesId" class="table-hover table-responsive">
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Tipo de Inmueble</th>
                                </tr>
                            </thead>
                            <tbody>
                                <s:iterator value="tipoInmuebleLista" >
                                    <tr>
                                        <th><s:property value="id" escape="false" ></s:property></th>
                                        <th><s:property value="nombre" escape="false" ></s:property></th>
                                        </tr>
                                </s:iterator>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-lg-6"></div>
                    <div class="col-lg-12">&nbsp;</div>
                    <div class="col-md-10">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-file-excel-o"></i></span>
                                <s:file  class="file" data-show-preview="true" labelposition="left" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="upload" />
                            <span class="help-block" id="estadoMessage" />
                        </div>
                    </div>
                    <div class="clearfix">&nbsp;</div>
                    <div class="clearfix">&nbsp;</div>  
                    <div class="col-md-12 text-center">
                        <button type="submit" id="cargar" class="btn btn-success">Cargar</button>
                    </div>
                    <div class="clearfix">&nbsp;</div>   
                </div>
            </div>


        </div>

    </s:form>

</html>
