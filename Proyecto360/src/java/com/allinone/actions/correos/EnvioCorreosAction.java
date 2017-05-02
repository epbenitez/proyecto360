package com.allinone.actions.correos;

import com.allinone.actions.BaseAction;
import com.allinone.business.EnvioCorreosBO;
import com.allinone.business.EstadoCuentaBO;
import com.allinone.domain.AvisoCobro;
import com.allinone.domain.EstadoCuenta;
import com.allinone.persistence.model.Cargo;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.ConfiguracionEnvioCorreos;
import com.allinone.persistence.model.Usuario;
import static com.opensymphony.xwork2.Action.INPUT;
import com.opensymphony.xwork2.ActionContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patriciabenitez
 */
public class EnvioCorreosAction extends BaseAction {

    private List<Condominio> condominiolist = new ArrayList<Condominio>();
    private ConfiguracionEnvioCorreos configuracion = new ConfiguracionEnvioCorreos();

    /**
     * Formulario de configuración del envío de correos
     *
     * @return
     */
    public String configuracion() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
//        if (isAdministrator()) {
        condominiolist = getDaos().getCondominioDao().findAll();
//        } else if (isPropietario()) {
//            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
//            Condominio condominio = du.getDepartamento().getCondominio();
//            condominiolist.add(condominio);
//        } else {
//            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
//        }
        return SUCCESS;
    }

    public String envia() {
        if (configuracion.getCondominio() == null || configuracion.getCondominio().getId() == null
                || configuracion.getAsunto() == null || configuracion.getAsunto().trim().equals("")
                || configuracion.getEncabezado() == null || configuracion.getEncabezado().trim().equals("")
                || configuracion.getCuerpo() == null || configuracion.getCuerpo().trim().equals("")
                || configuracion.getFirma() == null || configuracion.getFirma().trim().equals("")) {
            addActionError("Falta alguno de los parámetros para realizar el envío de correos.");
//            condominiolist = getDaos().getCondominioDao().findAll();
            return ERROR;
        }

        EnvioCorreosBO bo = new EnvioCorreosBO(getDaos());

        //Guardamos/Actualizamos la configuracion para ese condominio-torre
        bo.guardaConfiguracion(configuracion);

        //Buscamos los departamentos de ese condominio/torre
        List<AvisoCobro> ecList = getDaos().getDepartamentoUsuarioDao().encuentraDatosDepartamentoUsuario(configuracion.getTorre().getId(), configuracion.getCondominio().getId(),null);

        if (ecList == null || ecList.isEmpty()) {
            addActionError("No hay datos disponibles por enviar para los parámetros especificados.");
            condominiolist = getDaos().getCondominioDao().findAll();
            return INPUT;
        }

        EstadoCuentaBO boEC = new EstadoCuentaBO(getDaos());
        for (AvisoCobro ec : ecList) {

            // Sólo si se solicita imprimir el aviso de cobro en el cuerpo del mensaje, 
            // buscamos los cargos relacionados
            if (configuracion.getCuerpo().contains("[avisodecobro]")) {
                //Buscamos los cargos
                ec = boEC.getDatosFinancieros(ec);
            }

            String body = bo.formatoCorreoElectronico(bo.convierteSaltosLinea(configuracion.getEncabezado()), bo.sustituyePalablasBody(configuracion, ec), bo.convierteSaltosLinea(configuracion.getFirma()));

            try {
//                System.out.println("--->" + tmpBody);
                sendEmail("allinone@servicesmx.com", ec.getCorreo(), configuracion.getAsunto(), body);
            } catch (Exception ex) {
                ex.printStackTrace();
                condominiolist = getDaos().getCondominioDao().findAll();
                addActionError("Ocurrió un problema al intentar hacer el envío. " + ex.getLocalizedMessage());
                Logger.getLogger(EnvioCorreosAction.class.getName()).log(Level.SEVERE, null, ex);
                return INPUT;
            }

        }
        addActionMessage("Correos enviados exitosamente.");
        configuracion = new ConfiguracionEnvioCorreos();
        condominiolist = getDaos().getCondominioDao().findAll();
        return INPUT;
    }

    public List<Condominio> getCondominiolist() {
        return condominiolist;
    }

    public void setCondominiolist(List<Condominio> condominiolist) {
        this.condominiolist = condominiolist;
    }

    public ConfiguracionEnvioCorreos getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionEnvioCorreos configuracion) {
        this.configuracion = configuracion;
    }

}
