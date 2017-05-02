<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang=""> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">

        <link rel="stylesheet" href="/aio/css/normalize.min.css">
        <link rel="stylesheet" href="/aio/css/main.css">
        
        <link rel="stylesheet" type="text/css" href="/css/bootstrap/bootstrap.css">

        <link rel="stylesheet" type="text/css" href="/css/libs/font-awesome.css" />
        
        <script src="/aio/js/vendor/modernizr-2.8.3-respond-1.4.2.min.js"></script>
        
    </head>
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

        <div class="header-container">
            <header class="wrapper clearfix">
                <img src="/aio/img/allinone.png" alt=""/>
<!--                <nav>
                    <ul>
                        <li><a href="/aio/index.jsp?page=quienessomos">&iquest;Qui&eacute;nes S&oacute;mos?</a></li>
                        <li><a href="/aio/index.jsp?page=servicios">Nuestros Servicios</a></li>
                        <li><a href="/aio/index.jsp?page=inicio">Cont&aacute;ctenos<br/>&nbsp;</a></li>
                    </ul>
                </nav>-->
            </header>
        </div>

        <div class="main-container">
            <div class="main wrapper clearfix">

                <article>
                    <%
                        if (request.getParameter("page") != null) {
                            String contextPath = "/aio/";
                            String p = request.getParameter("page");
                            p = p + ".jsp";
                            p = contextPath + p;
                            System.out.println(p);
                    %>    

                    <%--<%@include file="<%=p%>" %>--%>
<jsp:include page="<%=p%>" />
                    <%
                        }
                    %>
                </article>


                <aside>
                    <!--<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales urna non odio egestas tempor. Nunc vel vehicula ante. Etiam bibendum iaculis libero, eget molestie nisl pharetra in. In semper consequat est, eu porta velit mollis nec. Curabitur posuere enim eget turpis feugiat tempor. Etiam ullamcorper lorem dapibus velit suscipit ultrices.</p>-->
                    <img class="visible-lg" src="/aio/img/allinone_logo.jpg" width="200" alt=""/>
                </aside>
                <article>
                    <p>&nbsp;</p>
                    <p style="text-align: center">
                        <a href="/aio/index.jsp?page=quienessomos"><img src="/aio/img/1.png" width="136" alt=""/></a>
                        <a href="/aio/index.jsp?page=servicios"><img src="/aio/img/2.png" width="136" alt=""/></a>
                        <img src="/aio/img/3.png" width="136" alt=""/>
                        <a href="/access.action"><img src="/aio/img/4.png" width="136" alt=""/></a>
                    </p>
                </article>

            </div> <!-- #main -->
        </div> <!-- #main-container -->

        <div class="footer-container">
            <footer class="wrapper">
                <a href="/aio/index.jsp?page=inicio">Inicio</a> |
                <a href="/aio/index.jsp?page=quienessomos">&iquest;Qui&eacute;nes S&oacute;mos?</a> |
                <a href="/aio/index.jsp?page=servicios">Nuestros Servicios</a> |
                <a href="/aio/index.jsp?page=contacto">Cont&aacute;ctenos</a> |
                <a href="/access.action">Portal Condominios</a> |
                <a href="/aio/AVISO_PRIVACIDAD_PROYECTO360.pdf" target="_blank"> Aviso de Privacidad de datos</a>
            </footer>
        </div>

        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.js"></script>
        <script>window.jQuery || document.write('<script src="js/vendor/jquery-1.11.2.js"><\/script>')</script>

        <script src="js/main.js"></script>

        <!-- Google Analytics: change UA-XXXXX-X to be your site's ID. -->
        <script>
            (function (b, o, i, l, e, r) {
                b.GoogleAnalyticsObject = l;
                b[l] || (b[l] =
                        function () {
                            (b[l].q = b[l].q || []).push(arguments)
                        });
                b[l].l = +new Date;
                e = o.createElement(i);
                r = o.getElementsByTagName(i)[0];
                e.src = '//www.google-analytics.com/analytics.js';
                r.parentNode.insertBefore(e, r)
            }(window, document, 'script', 'ga'));
            ga('create', 'UA-XXXXX-X', 'auto');
            ga('send', 'pageview');
        </script>
    </body>
</html>
