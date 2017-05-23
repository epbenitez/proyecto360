<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

        <title>Cube - Bootstrap Admin Template</title>

        <!-- bootstrap -->
        <link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.min.css" />

        <!-- RTL support - for demo only -->
        <script src="js/demo-rtl.js"></script>
        <!-- 
        If you need RTL support just include here RTL CSS file <link rel="stylesheet" type="text/css" href="css/libs/bootstrap-rtl.min.css" />
        And add "rtl" class to <body> element - e.g. <body class="rtl"> 
        -->

        <!-- libraries -->
        <link rel="stylesheet" type="text/css" href="css/libs/font-awesome.css" />
        <link rel="stylesheet" type="text/css" href="css/libs/nanoscroller.css" />

        <!-- global styles -->
        <link rel="stylesheet" type="text/css" href="css/compiled/theme_styles.css" />

        <!-- this page specific styles -->

        <!-- google font libraries -->
        <link href='//fonts.googleapis.com/css?family=Open+Sans:400,600,700,300|Titillium+Web:200,300,400' rel='stylesheet' type='text/css'>

        <!-- Favicon -->
        <link type="image/x-icon" href="favicon.png" rel="shortcut icon"/>

        <!--[if lt IE 9]>
                <script src="js/html5shiv.js"></script>
                <script src="js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body id="login-page-full">
        <div id="login-full-wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="login-box">
                            <div id="login-box-holder">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <header id="login-header">
                                            <div id="login-logo">
                                                <!--											<img src="img/logo.png" alt=""/>-->
                                                <i class="fa fa-building-o"> &nbsp;</i>360
                                                <div>Administraci&oacute;n de Condominios </div>
                                            </div>
                                        </header>
                                        <div id="login-box-inner">
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
                                            <!--<form role="form" action="index.html">-->
                                        <form role="form" id="j_spring_security_check" name="j_spring_security_check" action="/j_spring_security_check" method="post">
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-user"></i></span>
                                                <!--<input class="form-control" type="text" placeholder="Email address">-->
                                                <input class="form-control" type="text" placeholder="Nombre de Usuario" name="j_username" value="<s:if test="login_error"><s:property value="%{#session.SPRING_SECURITY_LAST_USERNAME}" /></s:if><s:else></s:else>" id="j_spring_security_check_j_username"  />
                                            </div>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-key"></i></span>
                                                <!--<input type="password" class="form-control" placeholder="Password">-->
                                                <input type="password" class="form-control" placeholder="Contrase�a" name="j_password" maxlength="15" id="j_spring_security_check_j_password" ondragstart="return false" onselectstart="return false"/>
                                            </div>
                                            <div id="remember-me-wrapper">
                                                <div class="row">
                                                    <div class="col-xs-6">
                                                        <div class="checkbox-nice">
                                                            <input type="checkbox" id="remember-me" checked="checked" />
                                                            <label for="remember-me">
                                                                Recuerdame
                                                            </label>
                                                        </div>
                                                    </div>
                                                    <a href="forgot-password.html" id="login-forget-link" class="col-xs-6">
                                                        &iquest;Olvidaste tu password?
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <button type="submit" class="btn btn-success col-xs-12">Entrar</button>
                                                </div>
                                            </div>
                                        </form>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div id="login-box-footer">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <a href="/registro/inicioRegistro.action">
                                        &iexcl;Registrate aqu&iacute;!
                                    </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="config-tool" class="closed">
            <a id="config-tool-cog">
                <i class="fa fa-cog"></i>
            </a>

            <div id="config-tool-options">
                <h4>Layout Options</h4>
                <ul>
                    <li>
                        <div class="checkbox-nice">
                            <input type="checkbox" id="config-fixed-header" />
                            <label for="config-fixed-header">
                                Fixed Header
                            </label>
                        </div>
                    </li>
                    <li>
                        <div class="checkbox-nice">
                            <input type="checkbox" id="config-fixed-sidebar" />
                            <label for="config-fixed-sidebar">
                                Fixed Left Menu
                            </label>
                        </div>
                    </li>
                    <li>
                        <div class="checkbox-nice">
                            <input type="checkbox" id="config-fixed-footer" />
                            <label for="config-fixed-footer">
                                Fixed Footer
                            </label>
                        </div>
                    </li>
                    <li>
                        <div class="checkbox-nice">
                            <input type="checkbox" id="config-boxed-layout" />
                            <label for="config-boxed-layout">
                                Boxed Layout
                            </label>
                        </div>
                    </li>
                    <li>
                        <div class="checkbox-nice">
                            <input type="checkbox" id="config-rtl-layout" />
                            <label for="config-rtl-layout">
                                Right-to-Left
                            </label>
                        </div>
                    </li>
                </ul>
                <br/>
                <h4>Skin Color</h4>
                <ul id="skin-colors" class="clearfix">
                    <li>
                        <a class="skin-changer" data-skin="" data-toggle="tooltip" title="Default" style="background-color: #34495e;">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer" data-skin="theme-white" data-toggle="tooltip" title="White/Green" style="background-color: #2ecc71;">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer blue-gradient" data-skin="theme-blue-gradient" data-toggle="tooltip" title="Gradient">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer" data-skin="theme-turquoise" data-toggle="tooltip" title="Green Sea" style="background-color: #1abc9c;">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer" data-skin="theme-amethyst" data-toggle="tooltip" title="Amethyst" style="background-color: #9b59b6;">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer" data-skin="theme-blue" data-toggle="tooltip" title="Blue" style="background-color: #2980b9;">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer" data-skin="theme-red" data-toggle="tooltip" title="Red" style="background-color: #e74c3c;">
                        </a>
                    </li>
                    <li>
                        <a class="skin-changer" data-skin="theme-whbl" data-toggle="tooltip" title="White/Blue" style="background-color: #3498db;">
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <!-- global scripts -->
        <script src="js/demo-skin-changer.js"></script> <!-- only for demo -->

        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery.nanoscroller.min.js"></script>

        <script src="js/demo.js"></script> <!-- only for demo -->

        <!-- this page specific scripts -->


        <!-- theme scripts -->
        <script src="js/scripts.js"></script>

        <!-- this page specific inline scripts -->

    </body>
</html>