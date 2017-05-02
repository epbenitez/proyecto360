<%-- 
    Document   : contacto
    Created on : 31-ago-2016, 16:32:44
    Author     : patriciabenitez
--%>
<script type="text/javascript" language="javascript" src="/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
    
</script>

<h1>CONT&Aacute;CTENOS<div>&nbsp;</div></h1>

        <form role="form" id="j_spring_security_check" name="j_spring_security_check" action="/j_spring_security_check" method="post">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-child"></i></span>
                <!--<input class="form-control" type="text" placeholder="Email address">-->
                <input class="form-control" type="text" placeholder="Nombre" name="nombre" value="" id="nombre"  />
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-arrow-circle-right"></i></span>
                <!--<input type="password" class="form-control" placeholder="Password">-->
                <input type="text" class="form-control" placeholder="Email" name="email" maxlength="30" id="email"  />
            </div>
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-phone"></i></span>
                <!--<input type="password" class="form-control" placeholder="Password">-->
                <input type="text" class="form-control" placeholder="Teléfono Fijo" name="fijo" maxlength="30" id="fijo"  />
            </div>

            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
                <!--<input type="password" class="form-control" placeholder="Password">-->
                <input type="text" class="form-control" placeholder="Teléfono Celular" name="celular" maxlength="30" id="celular"  />
            </div>

            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-star"></i></span>
                <!--<input type="password" class="form-control" placeholder="Password">-->
                <input type="text" class="form-control" placeholder="Asunto" name="asunto" maxlength="30" id="asunto"  />
            </div>

            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-comment"></i></span>
                <!--<input type="password" class="form-control" placeholder="Password">-->
                <textarea type="text" class="form-control" rows="10" placeholder="Mensaje" name="mensaje" maxlength="30" id="mensaje"  ></textarea>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <button type="submit" class="btn btn-success">Enviar</button>
                </div>
            </div>
        </form>
