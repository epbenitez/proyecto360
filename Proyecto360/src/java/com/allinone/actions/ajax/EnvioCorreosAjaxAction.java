package com.allinone.actions.ajax;

import com.allinone.actions.correos.EnvioCorreosAction;
import com.allinone.business.EnvioCorreosBO;
import com.allinone.persistence.model.ConfiguracionEnvioCorreos;
import static com.opensymphony.xwork2.Action.INPUT;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author patriciabenitez
 */
public class EnvioCorreosAjaxAction extends JSONAjaxAction {

    private Long pkCondominio;
    private Long pkTorre;

    private ConfiguracionEnvioCorreos config = new ConfiguracionEnvioCorreos();
    private String emailPrueba;

    public static void main(String args[]) {
        String txt = "Este es el mensaje que queremos que salga [condominio] y aqui saldría la torre [torre] y aqui departamento [departamento] y el usuario: [usuario]. <BR><BR>Recuerde hacer clic en el aviso de cobro: [avisodecobro]";

        System.out.println(txt.replaceAll("\\[", "<<").replaceAll("\\]", ">>"));
    }

    public String getConfiguracion() {

        List<ConfiguracionEnvioCorreos> cList = getDaos().getConfiguracionEnvioCorreosDao().getConfiguraciones(pkCondominio, pkTorre);
        if (cList == null || cList.isEmpty()) {
            return SUCCESS_JSON;
        }
        EnvioCorreosBO bo = new EnvioCorreosBO(getDaos());
        ConfiguracionEnvioCorreos c = cList.get(0);
        getJsonResult().add("[\"" + c.getAsunto()
                + "\", \"" + bo.convierteSaltosLinea(c.getEncabezado()).replaceAll("\\[", "<<").replaceAll("\\]", ">>")
                + "\", \"" + bo.convierteSaltosLinea(c.getCuerpo()).replaceAll("\\[", "<<").replaceAll("\\]", ">>")
                + "\", \"" + bo.convierteSaltosLinea(c.getFirma()).replaceAll("\\[", "<<").replaceAll("\\]", ">>")
                + " \"]");
        return SUCCESS_JSON;
    }

    public String envioPrueba() {
        if (config.getCondominio() == null || config.getCondominio().getId() == null
                || config.getAsunto() == null || config.getAsunto().trim().equals("")
                || config.getEncabezado() == null || config.getEncabezado().trim().equals("")
                || config.getCuerpo() == null || config.getCuerpo().trim().equals("")
                || config.getFirma() == null || config.getFirma().trim().equals("")) {
            addActionError("Falta alguno de los parámetros para realizar el envío de correos.");
            getJsonResult().add("[\"ERROR\"]");
            return SUCCESS_JSON;
        }
        EnvioCorreosBO bo = new EnvioCorreosBO(getDaos());

        String body = bo.formatoCorreoElectronico(bo.convierteSaltosLinea(config.getEncabezado()), bo.convierteSaltosLinea(config.getCuerpo()), bo.convierteSaltosLinea(config.getFirma()));

        try {
            sendEmail("allinone@servicesmx.com", emailPrueba, config.getAsunto(), body);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            getJsonResult().add("[\"ERROR\"]");
            return SUCCESS_JSON;
        }

        getJsonResult().add("[\"OK\"]");
        return SUCCESS_JSON;
    }

    public Long getPkCondominio() {
        return pkCondominio;
    }

    public void setPkCondominio(Long pkCondominio) {
        this.pkCondominio = pkCondominio;
    }

    public Long getPkTorre() {
        return pkTorre;
    }

    public void setPkTorre(Long pkTorre) {
        this.pkTorre = pkTorre;
    }

    public ConfiguracionEnvioCorreos getConfig() {
        return config;
    }

    public void setConfig(ConfiguracionEnvioCorreos config) {
        this.config = config;
    }

    public String getEmailPrueba() {
        return emailPrueba;
    }

    public void setEmailPrueba(String emailPrueba) {
        this.emailPrueba = emailPrueba;
    }

}
