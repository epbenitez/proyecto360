<%@taglib prefix="decorator" uri="/WEB-INF/decorators/tld/sitemesh-decorator.tld" %>
<%@taglib prefix="security" uri="/WEB-INF/decorators/tld/security.tld" %>
<%@taglib prefix="page" uri="/WEB-INF/decorators/tld/sitemesh-page.tld" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.opensymphony.xwork2.ActionContext;" %>



<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
    <!-- ROL ANONIMO-->
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8" />
            <title>360</title>
            <meta http-equiv="X-UA-Compatible" content="IE=edge;chrome=1" />


            <s:head />

            <decorator:head />

        </head>
        <body<decorator:getProperty property="body.onload" writeEntireProperty="true" />
            <decorator:getProperty property="body.class" writeEntireProperty="true" />
            <security:authorize ifNotGranted="ROLE_ANONYMOUS">
                oncontextmenu="return false;"
            </security:authorize> >
            <decorator:body />


        </body>
    </security:authorize>

    <security:authorize ifNotGranted="ROLE_ANONYMOUS">

        <!DOCTYPE html>
        <html>
            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

                <title>360</title>

                <!-- bootstrap -->
                <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap.min.css" />
                <!-- bootstrap validator -->
                <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap.css" />
                <link rel="stylesheet" href="/bootstrap-validator/css/bootstrapValidator.css"/>
                <script type="text/javascript" src="/bootstrap-validator/js/bootstrap.min.js"></script>

                <!-- 
                If you need RTL support just include here RTL CSS file <link rel="stylesheet" type="text/css" href="css/libs/bootstrap-rtl.min.css" />
                And add "rtl" class to <body> element - e.g. <body class="rtl"> 
                -->

                <!-- libraries -->
                <link rel="stylesheet" type="text/css" href="/css/libs/font-awesome.css" />
                <link rel="stylesheet" type="text/css" href="/css/libs/nanoscroller.css" />

                <!-- global styles -->
                <link rel="stylesheet" type="text/css" href="/css/compiled/theme_styles.css" />

                <!-- this page specific styles -->
                <link rel="stylesheet" href="/css/libs/datepicker.css" type="text/css" />
                <link rel="stylesheet" href="/css/libs/daterangepicker.css" type="text/css" />
                <link rel="stylesheet" href="/css/libs/bootstrap-timepicker.css" type="text/css" />
                <link rel="stylesheet" href="/css/libs/select2.css" type="text/css" />

                <!-- Favicon -->
                <link type="image/x-icon" href="/favicon.png" rel="shortcut icon" />

                <!-- google font libraries -->
                <!--<link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400' rel='stylesheet' type='text/css'>-->

                <!--[if lt IE 9]>
                                <script src="/js/html5shiv.js"></script>
                                <script src="/js/respond.min.js"></script>
                <![endif]-->

                <!--DataTables-->
                <link rel="stylesheet" type="text/css" href="/dataTables/media/css/jquery.dataTables.css">
                <link rel="stylesheet" type="text/css" href="/dataTables/resources/syntax/shCore.css">
                <!--<link rel="stylesheet" type="text/css" href="/dataTables/resources/demo.css">-->


                <style type="text/css" class="init">

                </style>
                <script type="text/javascript" language="javascript" src="/js/jquery-1.11.3.min.js"></script>
                <script type="text/javascript" language="javascript" src="/dataTables/media/js/jquery.dataTables.js"></script>
                <script type="text/javascript" language="javascript" src="/dataTables/resources/syntax/shCore.js"></script>
                <script type="text/javascript" language="javascript" src="/dataTables/resources/demo.js"></script>
                <!-- Responsive DataTables-->
                <link href="/dataTables/responsive/responsive.dataTables.min.css" rel="stylesheet" type="text/css"/>
                <link href="/dataTables/buttons/1.1.0/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
                <script src="/dataTables/responsive/dataTables.responsive.min.js" type="text/javascript"></script>

                <STYLE type="text/css">
                    .alignRight {text-align: right}
                    .alignCenter {text-align: center}
                </STYLE>
                <s:head />
                <decorator:head />

            </head>
            <body  class="fixed-header fixed-footer boxed-layout pace-done theme-blue-gradient">
                <div id="theme-wrapper">
                    <header class="navbar" id="header-navbar">
                        <div class="container">
                            <a href="/index.action" id="logo" class="navbar-brand">
                                <i class="fa fa-building-o"> &nbsp;</i>360
                            </a>

                            <div class="clearfix">
                                <button class="navbar-toggle" data-target=".navbar-ex1-collapse" data-toggle="collapse" type="button">
                                    <span class="sr-only">Toggle navigation</span>
                                    <span class="fa fa-bars"></span>
                                </button>

                                <div class="nav-no-collapse navbar-left pull-left hidden-sm hidden-xs">

                                    <ul class="nav navbar-nav pull-left">
                                        <li>
                                            <a class="btn" id="make-small-nav">
                                                <i class="fa fa-bars"></i>
                                            </a>
                                        </li>

                                        <!--                                        <li class="dropdown hidden-xs">
                                                                                    <a class="btn dropdown-toggle" data-toggle="dropdown">
                                                                                        New Item
                                                                                        <i class="fa fa-caret-down"></i>
                                                                                    </a>
                                                                                    <ul class="dropdown-menu">
                                                                                        <li class="item">
                                                                                            <a href="#">
                                                                                                <i class="fa fa-archive"></i> 
                                                                                                New Product
                                                                                            </a>
                                                                                        </li>
                                                                                        <li class="item">
                                                                                            <a href="#">
                                                                                                <i class="fa fa-shopping-cart"></i> 
                                                                                                New Order
                                                                                            </a>
                                                                                        </li>
                                                                                        <li class="item">
                                                                                            <a href="#">
                                                                                                <i class="fa fa-sitemap"></i> 
                                                                                                New Category
                                                                                            </a>
                                                                                        </li>
                                                                                        <li class="item">
                                                                                            <a href="#">
                                                                                                <i class="fa fa-file-text"></i> 
                                                                                                New Page
                                                                                            </a>
                                                                                        </li>
                                                                                    </ul>
                                                                                </li>-->

                                    </ul>
                                </div>

                                <div class="nav-no-collapse pull-right" id="header-nav">
                                    <ul class="nav navbar-nav pull-right">
                                        <li class="mobile-search">
                                            <a class="btn">
                                                <i class="fa fa-search"></i>
                                            </a>

                                            <div class="drowdown-search">
                                                <form role="search">
                                                    <div class="form-group">
                                                        <input type="text" class="form-control" placeholder="Search...">
                                                        <i class="fa fa-search nav-search-icon"></i>
                                                    </div>
                                                </form>
                                            </div>

                                        </li>
                                        <li class="dropdown profile-dropdown">
                                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                                                    <img src="/img/logo_torres/<s:property value="deptoUsuario.departamento.condominio.clave" />.png" alt="<s:property value="deptoUsuario.departamento.torre.nombre" />"/>
                                                </security:authorize>
                                                <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                                                    <img src="/custom/images/user.jpg" alt="" class="fa fa-user"/>
                                                </security:authorize>

                                                <span class="hidden-xs">
                                                    <%=ActionContext.getContext().getSession().get("nombreCompleto")%>
                                                </span> <b class="caret"></b>
                                            </a>
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                                                    <li><a href="#"><i class="fa fa-cog"></i>Torre: <s:property value="deptoUsuario.departamento.torre.nombre" /></a></li>
                                                    <li><a href="#"><i class="fa fa-envelope-o"></i>Departamento: <s:property value="deptoUsuario.departamento.nombre" /></a></li>
                                                    <li><a href="#"><i class="fa fa-user"></i><%=ActionContext.getContext().getSession().get("folio")%></a></li>
                                                    <li><a href="#"><i class="fa fa-star"></i><%=ActionContext.getContext().getSession().get("rol")%></a></li>
                                                    <li><a href="/j_spring_security_logout"><i class="fa fa-power-off"></i>Logout</a></li>
                                                    </security:authorize>
                                                    <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                                                    <li><a href="#"><i class="fa fa-user"></i><%=ActionContext.getContext().getSession().get("folio")%></a></li>
                                                    <li><a href="#"><i class="fa fa-star"></i><%=ActionContext.getContext().getSession().get("rol")%></a></li>
                                                    <li><a href="/j_spring_security_logout"><i class="fa fa-power-off"></i>Logout</a></li>
                                                    </security:authorize>
                                            </ul>
                                        </li>
                                        <li class="hidden-xxs">
                                            <a class="btn" href="/j_spring_security_logout">
                                                <i class="fa fa-power-off"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </header>
                                                <% String classcontainer="container"; %>
                                                    <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                                                        <% classcontainer="container nav-small"; %>
                                                    </security:authorize>                  
                    <div id="page-wrapper" class="<%=classcontainer%>">
                        <div class="row">
                            <div id="nav-col">
                                <section id="col-left" class="col-left-nano">
                                    <div id="col-left-inner" class="col-left-nano-content">

                                        <security:authorize ifAnyGranted="ROLE_PROPIETARIO">
                                            <div id="" class="clearfix hidden-sm hidden-xs dropdown profile2-dropdown" style="text-align: center">
                                                <img src="/img/logo_torres/<s:property value="deptoUsuario.departamento.condominio.clave" />.png" alt="<s:property value="deptoUsuario.departamento.torre.nombre" />"/>
                                            </div>
                                        </security:authorize>
                                        <security:authorize ifNotGranted="ROLE_PROPIETARIO">
                                            <div id="user-left-box" class="clearfix hidden-sm hidden-xs dropdown profile2-dropdown" style="text-align: center">
                                                <img src="/custom/images/user.jpg" alt="" class="fa fa-user"/>
                                            </div>
                                        </security:authorize>
                                        <div class="collapse navbar-collapse navbar-ex1-collapse" id="sidebar-nav">	
                                            <%=ActionContext.getContext().getSession().get("menu")%>
                                        </div>
                                    </div>
                                </section>
                                <div id="nav-col-submenu"></div>
                            </div>
                            <div id="content-wrapper">
                                <div class="row">
                                    <div class="col-lg-12">

                                        <div class="row">
                                            <div class="col-lg-12">
                                                <ol id="ruta" class="breadcrumb">
                                                    <!--                                                    <li><a href="#">Home</a></li>
                                                                                                        <li class="active"><span>Dashboard</span></li>-->
                                                    <s:property value="ubicacion" escape="false"></s:property>
                                                    </ol>

                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-lg-12">&nbsp;</div>
                                            </div>
                                            <div  class="row">
                                                <div class="col-lg-11">
                                                <decorator:body />
                                            </div>
                                        </div>

                                    </div>
                                </div>

                                <footer id="footer-bar" class="row">
                                    <p id="footer-copyright" class="col-xs-12">
                                        Proyecto 360 
                                    </p>
                                </footer>
                            </div>
                        </div>
                    </div>
                </div>



                <!-- global scripts -->

                <!--<script src="/js/jquery.js"></script>-->
                <script src="/js/bootstrap.js"></script>
                <script src="/js/jquery.nanoscroller.min.js"></script>


                <!--  DIALOG MESSAGES      -->
                <script src="/prettify/run_prettify.js"></script>
                <link href="/bootstrap-dialog/css/bootstrap-dialog.min.css" rel="stylesheet" type="text/css" />
                <script src="/bootstrap-dialog/js/bootstrap-dialog.min.js"></script>
                <!--  END DIALOG MESSAGES      -->

                <!--<script src="/js/demo.js"></script>  only for demo -->

                <!-- this page specific scripts -->
                <script src="/js/jquery.maskedinput.min.js"></script>
                <script src="/js/bootstrap-datepicker.js"></script>
                <script src="/js/moment.min.js"></script>
                <script src="/js/daterangepicker.js"></script>
                <script src="/js/bootstrap-timepicker.min.js"></script>
                <script src="/js/select2.min.js"></script>
                <script src="/js/hogan.js"></script>
                <script src="/js/typeahead.min.js"></script>
                <script src="/js/jquery.pwstrength.js"></script>

                <!-- theme scripts -->
                <script src="/js/scripts.js"></script>
                <script src="/js/pace.min.js"></script>

                <!-- this page specific inline scripts -->
                <script src="/js/wizard.js"></script>

                <!-- BootStrap Validator -->
                <script type="text/javascript" src="/bootstrap-validator/js/bootstrapValidator.js"></script>
            </body>
        </html>

    </security:authorize>
