/**
 * ALL IN ONE
 *
 * Created on : 18-ago-2015, 12:46:52
 *
 */
package com.allinone.actions.usuario;

import com.allinone.actions.BaseUploadFileAction;
import com.allinone.business.UsuarioBO;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Usuario;
import static com.opensymphony.xwork2.Action.INPUT;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author Patricia Benitez
 */
public class AdministraUsuariosAction extends BaseUploadFileAction
        implements ModelDriven<Usuario>, ServletRequestAware, ServletResponseAware, MensajesUsuario {

    private Usuario usuario = new Usuario();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private List<Condominio> condominiolist = new ArrayList<Condominio>();

    private String contraseniaActual;
    private String contraseniaNueva;

    private String usuarioId;

    private Usuario usr = new Usuario();

    @Override
    public Usuario getModel() {
        return usuario;
    }

    public String cambioContrasenia() {
        return INPUT;
    }

    public String actualizaContrasenia() {
        String usuarioSesion = SecurityContextHolder.getContext().getAuthentication().getName();
        UsuarioBO bo = new UsuarioBO(getDaos());
        if (usuarioSesion == null || usuarioSesion.isEmpty()) {
            addActionError(getText("actualiza.contrasenia.error"));
            return INPUT;
        }
        Boolean actualizaContrasenia = bo.actualizaContrasenia(usuarioSesion, contraseniaActual, contraseniaNueva);
        if (actualizaContrasenia) {
            addActionMessage(getText("actualiza.contrasenia.exito"));
        } else {
            addActionError(getText("actualiza.contrasenia.error.actual"));
        }
        return INPUT;
    }

    public String usuariosCondominio() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominiolist = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominiolist.add(condominio);
        } else {
            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return INPUT;
    }

    public String usuariosCondominioCambio() {
        if (usr.getId() == null || usr.getId().equals("")) {
            addActionError("No se encontró al usuario especificado");
            return INPUT;
        }
        usr = getDaos().getUsuarioDao().findById(usr.getId());
        return INPUT;
    }

    public String usuariosCondominioActualiza() {
        if (usr.getId() == null || usr.getId().equals("")) {
            addActionError("No se encontró al usuario especificado");
            return INPUT;
        }
        usr = getDaos().getUsuarioDao().findById(usr.getId());
        if (usr != null) {
            usr.setPassword(contraseniaNueva);

            try {
                getDaos().getUsuarioDao().update(usr);
                addActionMessage(getText("actualiza.contrasenia.exito"));
            } catch (Exception e) {
                e.printStackTrace();
                addActionError(getText("actualiza.contrasenia.error.actual"));
            }
        } else {
            addActionError(getText("actualiza.contrasenia.error.actual"));
        }
        return SUCCESS;
    }

    @Override
    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getContraseniaActual() {
        return contraseniaActual;
    }

    public void setContraseniaActual(String contraseniaActual) {
        this.contraseniaActual = contraseniaActual;
    }

    public String getContraseniaNueva() {
        return contraseniaNueva;
    }

    public void setContraseniaNueva(String contraseniaNueva) {
        this.contraseniaNueva = contraseniaNueva;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    public List<Condominio> getCondominiolist() {
        return condominiolist;
    }

    public void setCondominiolist(List<Condominio> condominiolist) {
        this.condominiolist = condominiolist;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Usuario getUsr() {
        return usr;
    }

    public void setUsr(Usuario usr) {
        this.usr = usr;
    }

}
