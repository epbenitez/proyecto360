<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

        <title>360</title>

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
    <body id="login-page" class="theme-whbl fixed-header fixed-footer boxed-layout">
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <div id="login-box">
                        <div id="login-box-holder">
                            <div class="row">
                                <div class="col-xs-12">
                                    <header id="login-header">
                                        <div id="login-logo">
                                            <!--<img src="img/logo.png" alt=""/>-->
                                            <i class="fa fa-building-o"> &nbsp;</i>360
                                            <div>Administraci&oacute;n de Condominios</div>
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
                                                <input type="password" class="form-control" placeholder="Contraseña" name="j_password" maxlength="15" id="j_spring_security_check_j_password" ondragstart="return false" onselectstart="return false"/>
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
        <!-- global scripts -->
        <script src="js/jquery.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery.nanoscroller.min.js"></script>
        <!-- this page specific scripts -->
        <!-- theme scripts -->
        <script src="js/scripts.js"></script>
        <!-- this page specific inline scripts -->
    </body>
</html>