package com.allinone.business;

import com.allinone.domain.AvisoCobro;
import com.allinone.persistence.model.Cargo;
import com.allinone.persistence.model.ConfiguracionEnvioCorreos;
import com.allinone.service.Service;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class EnvioCorreosBO {

    private Service service;

    public EnvioCorreosBO(Service s) {
        this.service = s;
    }

    public void guardaConfiguracion(ConfiguracionEnvioCorreos configuracion) {
        List<ConfiguracionEnvioCorreos> cList = service.getConfiguracionEnvioCorreosDao().getConfiguraciones(configuracion.getCondominio().getId(), configuracion.getTorre().getId());
        if (cList == null || cList.isEmpty()) {
            //Guardamos nuevo registro
            service.getConfiguracionEnvioCorreosDao().save(configuracion);
            return;
        }

        ConfiguracionEnvioCorreos c = cList.get(0);
        c.setAsunto(configuracion.getAsunto());
        c.setEncabezado(configuracion.getEncabezado());
        c.setCuerpo(configuracion.getCuerpo());
        c.setFirma(configuracion.getFirma());
        c.setFecha(new Date());

        service.getConfiguracionEnvioCorreosDao().update(c);
    }

    /**
     * Sustituye los saltos de linea por BR
     *
     * @param text
     * @return
     */
    public String agregaSaltosLinea(StringBuffer text) {
        int loc = (new String(text)).indexOf('\n');
        while (loc > 0) {
            text.replace(loc, loc + 1, "<BR>");
            loc = (new String(text)).indexOf('\n');
        }

        return text.toString();
    }

    public String convierteSaltosLinea(String text) {

        text = text.replaceAll("\n", "<BR>").replaceAll("\r", "");

        return text;
    }

    /**
     * Sustituye las palabras reservadas en los datos correspondientes al
     * departamento
     *
     * @param configuracion
     * @param ec
     * @return
     */
    public String sustituyePalablasBody(ConfiguracionEnvioCorreos configuracion, AvisoCobro ec) {
        String tmpBody = configuracion.getCuerpo().replaceAll("\\[condominio\\]", ec.getCondominio())
                .replaceAll("\\[torre\\]", ec.getTorre())
                .replaceAll("\\[departamento\\]", ec.getDepartamento())
                .replaceAll("\\[usuario\\]", ec.getUsuario())
                .replaceAll("\\[avisodecobro\\]", htmlAvisoCobro(ec))//"<a href='http://148.247.220.72:8080/estadodecuenta/avisoCobroEstadoDeCuenta.action' target='_blank'>Aviso de Cobro</a>")
                .replaceAll("\n", "<BR>").replaceAll("\r", "<BR>");

        return tmpBody;

    }

    public String formatoCorreoElectronico(String encabezado, String cuerpo, String firma) {

        StringBuilder body = new StringBuilder("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html> <head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /> <title>All In One</title> </head> <body leftmargin=\"0\" marginwidth=\"0\" topmargin=\"0\" marginheight=\"0\" offset=\"0\"> <center> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"backgroundTable\"> <tr> <td align=\"center\" valign=\"top\"> <!-- // Begin Template Preheader \\\\ --> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"templatePreheader\"> <tr> <td valign=\"top\" class=\"preheaderContent\"> <!-- // Begin Module: Standard Preheader \\ --> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\"> <tr> <td valign=\"top\"> <div mc:edit=\"std_preheader_content\"> <!--Recuperaci&oacute;n de Contraseña--> </div> </td> <!-- *|IFNOT:ARCHIVE_PAGE|* --> <td valign=\"top\" width=\"170\"> <div mc:edit=\"std_preheader_links\"> </div> </td> <!-- *|END:IF|* --> </tr> </table> <!-- // End Module: Standard Preheader \\ --> </td> </tr> </table> <!-- // End Template Preheader \\\\ --> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateContainer\"> <tr> <td align=\"center\" valign=\"top\"> <!-- // Begin Template Header \\\\ --> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"background-color:#FFFFFF;border-bottom:5px solid #505050;\"> <tr> <td class=\"headerContent\" width=\"100%\" style=\"padding-left:20px; padding-right:10px;\"> <div mc:edit=\"Header_content\"> <h1></h1> </div> </td> <td class=\"headerContent\"> <img src=\"http://148.247.220.72:8080/aio/img/allinone_logo.jpg\" style=\"max-width:100px;\" id=\"headerImage campaign-icon\" mc:label=\"header_image\" mc:edit=\"header_image\" mc:allowtext /> </td> </tr> </table> <!-- // End Template Header \\\\ --> </td> </tr> <tr> <td align=\"center\" valign=\"top\"> <!-- // Begin Template Body \\\\ --> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"600\" id=\"templateBody\"> <tr> <td valign=\"top\" class=\"bodyContent\"> <!-- // Begin Module: Standard Content \\\\ --> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\"> <tr> <td valign=\"top\" style=\"padding-right:0;\"> <div mc:edit=\"std_content00\">");
        body.append(" <h2 class=\"h2\">").append(encabezado).append("</h2>");
        body.append(cuerpo);
        body.append(" <p>").append(firma).append("</p> </div> </td> </tr> </table>");
        body.append(" <!-- // End Module: Standard Content \\\\ --> </td> </table> <!-- // End Template Body \\\\ --> </td> </tr> <tr> <td align=\"center\" valign=\"top\"> <!-- // Begin Template Footer \\\\ --> <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" id=\"templateFooter\"> <tr> <td valign=\"top\" class=\"footerContent\"> <!-- // Begin Module: Standard Footer \\\\ --> <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" width=\"100%\"> <!-- <tr> <td colspan=\"2\" valign=\"middle\" id=\"social\"> <div mc:edit=\"std_social\"> &nbsp;<a href=\"*|TWITTER:PROFILEURL|*\">follow on Twitter</a> | <a href=\"*|FACEBOOK:PROFILEURL|*\">friend on Facebook</a> | <a href=\"*|FORWARD|*\">forward to a friend</a>&nbsp; </div> </td> </tr>--> <tr> <td valign=\"top\" width=\"350\"> <div mc:edit=\"std_footer\"> <em>Copyright &copy; 2016 All In One, todos los derechos reservados.</em> </div> </td> <td valign=\"top\" width=\"190\" id=\"monkeyRewards\"> <div mc:edit=\"monkeyrewards\"> &nbsp; </div> </td> </tr> <tr> <td colspan=\"2\" valign=\"middle\" id=\"utility\"> <div mc:edit=\"std_utility\"> &nbsp; </div> </td> </tr> </table> <!-- // End Module: Standard Footer \\\\ --> </td> </tr> </table> <!-- // End Template Footer \\\\ --> </td> </tr> </table> <br /> </td> </tr> </table> </center> </body></html>");

        return body.toString();
    }

    public String htmlAvisoCobro(AvisoCobro ec) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        Double sumatoriaCargos = 0d;
        Double sumatoriaCargosIntereses = 0d;

        StringBuilder sb = new StringBuilder("<p>&nbsp;</p><table width=\"100%\"><tbody><tr style=\"height: 62px;\"><td style=\"height: 101px;\" rowspan=\"2\" width=\"92\"><img src=\"http://148.247.220.72:8080/img/logo_torres/").append(ec.getClaveCondominio()).append(".png\" alt=\"\" width=\"100\" /></td><td style=\"height: 101px;\" colspan=\"4\" rowspan=\"2\" width=\"255\"><p style=\"text-align: center;\"><strong>CONDOMINIO ").append(ec.getCondominio().toUpperCase()).append(" A.C.</strong></p><p style=\"text-align: center;\">").append(ec.getDireccionCondominio()).append("</p></td><td  align=\"center\" style=\"height: 62px;border: 1px solid #CCC;\" width=\"94\"><p><strong>AVISO DE COBRO</strong></p></td></tr><tr style=\"height: 39px;\">");
        sb.append("<td style=\"height: 39px;border: 1px solid #CCC; color:red;\" width=\"94\"><p><strong>").append(ec.getFolioAvisoCobro()).append("</strong></p></td></tr>");
        sb.append("<tr style=\"height: 51px;\"><td style=\"height: 51px;\" colspan=\"2\" width=\"508\"><p><strong>ESTADO DE M&Eacute;XICO, A</strong></p></td><td style=\"height: 51px;\" colspan=\"5\" width=\"343\"><p><u>");
        sb.append(dateFormat.format(new Date()));
        sb.append("</u></p></td></tr><tr style=\"height: 31px;\">");
        sb.append("<td colspan=\"2\" width=\"108\"><p><strong>DEPARTAMENTO:</strong></p></td><td style=\"height: 31px;\" width=\"65\"><p><u>").append(ec.getDepartamento()).append("</u></p></td>");
        sb.append("<td><p><strong>TORRE:</strong></p></td><td style=\"height: 31px;\" colspan=\"3\" width=\"220\"><p><u>").append(ec.getTorre()).append("</u></p></td></tr>");
        sb.append("<tr><td colspan=\"2\" width=\"108\"><p><strong>SALDO INICIAL DEL MES: </strong></p></td><td  style=\"border: 1px solid #CCC;\"><p>\\$").append(ec.getSaldoInicial()).append("</p></td><td style=\"width: 234px;\" colspan=\"3\"><p>&nbsp;</p></td></tr>");
        sb.append("</tbody></table><p><strong>&nbsp;</strong></p>");

        if (ec.getCargos() != null && !ec.getCargos().isEmpty()) {

            sb.append("<table width=\"100%\"><tbody>");
            sb.append("<tr><td style=\"width: 441px;border: 1px solid #CCC;\" colspan=\"5\"><p><strong>CARGOS DEL MES</strong></p></td></tr>");
            sb.append("<tr><td style=\"width: 207px;\" colspan=\"2\"><p><strong>CONCEPTO</strong></p></td><td style=\"width: 104px;\"><p><strong>HASTA EL D&Iacute;A 10</strong></p></td><td style=\"width: 22px;\"><p><strong>&nbsp;</strong></p></td><td style=\"width: 108px;\"><p><strong>DESPU&Eacute;S DEL D&Iacute;A 10</strong></p></td></tr>");

            int i = 0;
            for (Cargo c : ec.getCargos()) {
                sb.append("<tr><td style=\"width: 207px;\" colspan=\"2\"><p>");
                sb.append("CUOTA DE MANTENIMIENTO");
                sb.append("</p></td><td style=\"width: 104px;\"><p>\\$");
                sb.append((c.getConcepto().getId() == 1 ? df.format(c.getMonto() - 300) : df.format(c.getMonto())));
                sb.append("</p></td><td style=\"width: 22px;\"><p>&nbsp;</p></td><td style=\"width: 108px;\"><p>\\$");
                sb.append(df.format((c.getMonto())));
                sb.append("</p></td></tr>");
                sumatoriaCargos += (c.getConcepto().getId() == 1 ? (c.getMonto() - 300) : (c.getMonto()));
                sumatoriaCargosIntereses += (c.getMonto());
                i = c.getConcepto().getId() == 1 ?i+1:i;
            }

            sb.append("</tbody></table><p><strong>&nbsp;</strong></p>");
            sb.append("<table width=\"100%\"><tbody><tr style=\"height: 31px;\"><td style=\"width: 119%; height: 31px;border: 1px solid #CCC;\" colspan=\"4\"><p><strong>GRAN TOTAL A PAGAR</strong></p></td></tr>");
            sb.append("<tr style=\"height: 38px;\"><td style=\"width: 38%; height: 38px;\"><p>&nbsp;</p></td><td style=\"width: 42.6923%; height: 38px;\"><p>HASTA EL D&Iacute;A 10:&nbsp;</p></td><td style=\"width: 28.3077%; height: 38px;\"><p>\\$");
            sb.append(df.format(new Double(ec.getSaldoFinal().replaceAll(",", "")) - (300 * i)));
            sb.append("</p></td></tr>");
            sb.append("<tr style=\"height: 22px;\"><td style=\"width: 38%; height: 22px;\">&nbsp;</td><td style=\"width: 42.6923%; height: 22px;\"><p>&nbsp;DESPU&Eacute;S DEL D&Iacute;A 10:</p></td><td style=\"width: 28.3077%; height: 22px;\"><p>\\$");
            sb.append(ec.getSaldoFinal());
            sb.append("</p></td></tr></tbody></table>");
        } else {
            sb.append("<p>&nbsp;</p>");
            sb.append("<p> <i> No se han reportado cargos para este mes. </i></p>");
        }
        sb.append("<p>&nbsp;</p>");
        sb.append("<table><tbody><tr><td width=\"100%\"><p><strong>FORMAS DE PAGO:</strong></p>");
        sb.append("<p>1) Sucursal bancaria: cheque cruzado a nombre del residencial.</p>");
        sb.append("<p>2) Transferencia electr&oacute;nica: por medio de la clabe interbancaria</p>");
        sb.append("<p>3) Por ning&uacute;n motivo se aceptan pagos en efectivo en la administraci&oacute;n, ya que no es un medio autorizado ni aprobado por el corporativo ni por el condominio, no haci&eacute;ndonos responsables del mismo.</p><p><strong>&nbsp;</strong></p>");
        sb.append("<p><strong>IMPORTANTE:</strong></p>");
        sb.append("<p>En cualquier forma de pago deber&aacute; colocar su referencia ").append(ec.getReferencia()).append(" y enviar su comprobante de pago al correo:&nbsp;");
        sb.append("<a href=\"mailto:").append(ec.getContactoMail()).append("\">").append(ec.getContactoMail()).append("</a></p>");
        sb.append("<p>&nbsp;</p><p><strong>DATOS BANCARIOS:</strong></p>");
        sb.append("<p>Banco: ").append(ec.getBanco()).append("</p>");
        sb.append("<p>Beneficiario: ").append(ec.getBeneficiario()).append("</p>");
        sb.append("<p>Cuenta: ").append(ec.getCuenta()).append("</p>");
        sb.append("<p>Clabe interbancaria: ").append(ec.getClabe()).append("</p>");
        sb.append("<p>Sucursal: ").append(ec.getSucursal()).append("</p>");
        sb.append("<p>Referencia: ").append(ec.getReferencia()).append("</p>");
        sb.append("</td></tr></tbody></table>");
        sb.append("<p>&nbsp;</p>");

        return sb.toString();
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
