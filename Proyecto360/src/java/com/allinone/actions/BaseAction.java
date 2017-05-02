package com.allinone.actions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.logging.commons.CommonsLogger;
import com.allinone.business.BaseBO;
import com.allinone.business.EstadoCuentaBO;
import com.allinone.persistence.model.Configuracion;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
import com.allinone.util.Ambiente;
import com.allinone.util.AmbienteEnums;
import com.allinone.util.AsyncMailSender;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.LogManager;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Accion base con las propiedades requeridas por todas las acciones del
 * sistema.
 *
 * @author Patricia Benitez
 */
public abstract class BaseAction extends ActionSupport implements Mensajes {

    private static final long serialVersionUID = -4001850137117080470L;
    private Ambiente ambiente = null;
    private Service daos = null;
    private AsyncMailSender mailSender;
    private String ubicacion;

    //Objeto para ayudarnos a hacer login en el sistema propietario
    private Usuario usuario;

    private DepartamentoUsuario deptoUsuario;

    /**
     * Inicializa el objeto <code>BaseAction</code>.
     */
    public BaseAction() {
        LOG = new CommonsLogger(new Log4JLogger(LogManager.getLogger(this.getClass().getName())));
        setVariablesPersonalizadas();
        if (SecurityContextHolder.getContext().getAuthentication().getName() != null
                && !SecurityContextHolder.getContext().getAuthentication().getName().equals("")
                && !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            String nombre = getPrincipalFullName();
            BaseBO BO = new BaseBO();
            String folio = SecurityContextHolder.getContext().getAuthentication().getName();
            usuario = BO.getUsuarioByFolio(folio, getDaos());
            ActionContext.getContext().getSession().put("usuario", usuario);
            ActionContext.getContext().getSession().put("nombreCompleto", usuario.getUsuario());
            ActionContext.getContext().getSession().put("folio", usuario.getUsuario());
            ActionContext.getContext().getSession().put("rol", getRol(getRol()));

            if (isPropietario()) {
                EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
                deptoUsuario = bo.getDepartamentoUsuario(usuario);
            }

            try {
                // OBTENIENDO LA RUTA EN LA QUE SE ENCUENTRA
                HttpServletRequest request = ServletActionContext.getRequest();
                String action = request.getRequestURI();
                ubicacion = daos.getMenuDao().getNombreModulo(action);
                ActionContext.getContext().getSession().put("urlAction", ubicacion);
                System.out.println("INDEXOF::::" + action);
                if (action.indexOf("ajax") <= 0) {
                    setMenu(usuario);
                }
            } catch (Exception e) {
                e.printStackTrace();
                ActionContext.getContext().getSession().put("urlAction", "NO IDENTIFICADO");
            }
        }
    }

    public void setMenu(Usuario usuario) {
        String menuStr = "";
        if (ActionContext.getContext().getSession().get("menu") == null
                || ActionContext.getContext().getSession().get("menu").toString().length() == 0) {
            menuStr = getDaos().getRelacionMenuRolesDao().findURLByRols(usuario);
            ActionContext.getContext().getSession().put("menu", menuStr);
        }
    }

    /**
     *
     * Obtiene de los objets de Ambiente los valores de la configuracion de la
     * aplicacion
     */
    public void setVariablesPersonalizadas() {
        System.out.println(":::::: setVariablesPersonalizadas ::::::");
        try {
            List<Configuracion> configuracionList = getAmbiente().getConfiguracion();
//            List<Configuracion> configuracionList = getDaos().getConfiguracionDao().findAll();
            for (Configuracion c : configuracionList) {
                System.out.println(c.getPropiedad() + "," + c.getValor());
                ActionContext.getContext().getSession().put(c.getPropiedad(), c.getValor());
            }
        } catch (Exception e) {
            e.printStackTrace();
            // VERSION ESTABLECIENDO VARIABLES DE AMBIENTE
        }
    }

    /**
     * Obtiene la interfaz JavaMailSender, para la creación correos
     * electrónicos.
     *
     * @return manejador del envío de emails.
     */
    private AsyncMailSender getMailSender() {
        if (mailSender == null) {
            try {
                ApplicationContext applicationContext
                        = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                mailSender = (AsyncMailSender) applicationContext.getBean("mailSender");
            } catch (BeansException beansException) {
                beansException.printStackTrace();
            }
        }
        return mailSender;
    }

    /**
     * Verifica que exista un usuario autentificado.
     *
     * @return true si el usuario se encuentra autentificado, false de lo
     * contrario.
     */
    protected boolean isAuthenticated() {
        Collection<GrantedAuthority> gas = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority ga : gas) {
            if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {
                return false;
            }
        }
        return true;
    }

    protected List<Rol> getRol() {
        Collection<GrantedAuthority> gas = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        List<Rol> roles = new ArrayList<Rol>();
        for (GrantedAuthority ga : gas) {
            System.out.println(ga.getAuthority());
            if (ga.getAuthority().equals("ROLE_ADMIN")) {
                Rol r = getDaos().getRolDao().findById(1L);
                roles.add(r);
            }
            if (ga.getAuthority().equals("ROLE_PROPIETARIO")) {
                Rol r = getDaos().getRolDao().findById(2L);
                roles.add(r);
            }
            if (ga.getAuthority().equals("ROLE_ADMINCONDOMINIO")) {
                Rol r = getDaos().getRolDao().findById(3L);
                roles.add(r);
            }
            if (ga.getAuthority().equals("ROLE_ ADMONCOBRANZA")) {
                Rol r = getDaos().getRolDao().findById(4L);
                roles.add(r);
            }
        }
        return roles;
    }

    protected String getRol(List<Rol> roles) {
        StringBuilder rolesStr = new StringBuilder();
        for (Rol r : roles) {
            rolesStr.append(r.getClave()).append(",");
        }

        return rolesStr.toString().length() == 0 ? "" : rolesStr.toString().substring(0, rolesStr.toString().length() - 1);
    }

    /**
     * Verifica que el usuario sea un administrador.
     *
     * @return true si es un admistrador, false de lo contrario.
     */
    protected boolean isAdministrator() {
        Collection<GrantedAuthority> gas = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority ga : gas) {
            if (ga.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    protected boolean isAdminCondominio() {
        Collection<GrantedAuthority> gas = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority ga : gas) {
            if (ga.getAuthority().equals("ROLE_ADMINCONDOMINIO")) {
                return true;
            }
        }
        return false;
    }

    protected boolean isPropietario() {
        Collection<GrantedAuthority> gas = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority ga : gas) {
            if (ga.getAuthority().equals("ROLE_PROPIETARIO")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Valida si el usuario está autenticado y obtiene los privilegios del
     * usuario y obtiene las opciones (ligas) del menú del usuario con base en
     * su rol
     *
     * @return Un string con formato HTML de las opciones de menú disponibles
     * para el usuario con base en la lista de roles asociados al usuario
     */
    public String getMenuUsuario() {
        if (!isAuthenticated()) {
            return "";
        }
        Usuario usuario = getDaos().getUsuarioDao().findById(SecurityContextHolder.getContext().getAuthentication().getName());
        return getDaos().getRelacionMenuRolesDao().findURLByRols(usuario);
    }

    public String getPrincipalFullName() {
        String fullname = null;
        LOG.debug(SecurityContextHolder.getContext().getAuthentication().getName());
        fullname = "NOMBRE DE USUARIO";
        LOG.debug("fullname:" + fullname);

        return fullname;
    }

    /**
     * Envía un correo electrónico al destinatario deseado.
     *
     * @param from Correo electrónico del remitente (De).
     * @param to Correo electrónico del destinatario (Para).
     * @param subject Asunto del correo electrónico.
     * @param body Cuerpo del correo electrónico.
     * @throws MessagingException
     */
    protected final void sendEmail(String from, String to, String subject, String body) throws MessagingException {
        String[] toArray = {};
        if (to != null && !to.isEmpty()) {
            StringTokenizer st = new StringTokenizer(to, ",");
            toArray = new String[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                toArray[i] = st.nextToken().trim();
                i++;
            }
        }
        sendEmail(from, toArray, subject, body);
    }

    protected final void sendEmailMonitoreo(String from, String subject, String body) throws MessagingException {
        String[] toArray = {};
        String to = "pbenitez@cinvestav.mx,jcedillo@cinvestav.mx,anava@cinvestav.mx";
        if (to != null && !to.isEmpty()) {
            StringTokenizer st = new StringTokenizer(to, ",");
            toArray = new String[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                toArray[i] = st.nextToken().trim();
                i++;
            }
        }
        sendEmail(from, toArray, subject, body);
    }

    /**
     * Envía un correo electrónico a una lista de destinatarios.
     *
     * @param from Correo electrónico del remitente (De).
     * @param to Lista correos electrónicos de los destinatarios (Para).
     * @param subject Asunto del correo electrónico.
     * @param body Cuerpo del correo electrónico.
     * @throws MessagingException
     */
    protected final void sendEmail(String from, String[] to, String subject, String body) throws MessagingException {
        sendEmail(from, to, subject, body, null);
    }

    /**
     * Envía un correo electrónico a una lista de destinatarios junto con un
     * archivo anexo.
     *
     * @param from Correo electrónico del remitente (De).
     * @param to Lista correos electrónicos de los destinatarios (Para).
     * @param subject Asunto del correo electrónico.
     * @param body Cuerpo del correo electrónico.
     * @param attach Archivo anexo.
     * @throws MessagingException
     */
    protected final void sendEmail(String from, String[] to, String subject, String body, InputStreamSource attach) throws MessagingException {
        AsyncMailSender sender = getMailSender();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
//        helper.setBcc(to);
        helper.setSubject(subject);

        helper.setText("<html><body>" + body + "</body></html>", true);
        sender.send(message);
    }

    /**
     * Obtiene el ambiente donde estan listas y otros objetos comunes.
     *
     * @return ambiente general.
     * @throws Exception
     * @see Ambiente
     */
    public Ambiente getAmbiente() throws Exception {
        if (ambiente == null) {
            try {
                ApplicationContext applicationContext
                        = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                ambiente = (Ambiente) applicationContext.getBean("ambiente");
            } catch (BeansException beansException) {
                beansException.printStackTrace();
            }
        }
        return ambiente;
    }

    /**
     * Obtiene los DAOs, donde podremos encontrar el manejo de persitencia de
     * los objetos.
     *
     * @return service para el manejo de persistencia.
     */
    public Service getDaos() {
        if (daos == null) {
            try {
                ApplicationContext applicationContext
                        = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                daos = (Service) applicationContext.getBean("service");
            } catch (BeansException beansException) {
                beansException.printStackTrace();
            }
        }
        return daos;
    }

    /**
     * Obtiene el ambiente donde estan listas pertenecientes a un catalogo.
     *
     * @return ambiente donde estan listas pertenecientes a un catalogo.
     */
    public AmbienteEnums getService() {
        return AmbienteEnums.getInstance();
    }

    /**
     * Obtiene el nombre de la acción que se está ejecutando.
     *
     * @return Nombre de acción que se ejecuta.
     */
    public String getActionName() {
        LOG.debug("Obteniedo el Accion Name:" + ActionContext.getContext().getName());
        return ActionContext.getContext().getName();
    }

    /**
     * Accion Base: En caso de que no se sobreescriba este metodo, la ejecución
     * directa de dicha acción generará un error.
     *
     * @return un string representando el resultado lógico de la ejecución de la
     * acción : ERROR
     */
    @Override
    public String execute() {
        LOG.error("Accion %s ejecutada directamente", this.getClass().getName());
        return ERROR;
    }

    /**
     * Acción Cancelar: Cancela la ejecución de la acción.
     *
     * @return un string representando el resultado lógico de la ejecución de la
     * acción: cancel
     */
    public String accionCancelar() {
        return "cancel";
    }

    /**
     * Acción Cancelar: Cancela la ejecución de la acción y regresa al menú
     * principal
     *
     * @return un string representando el resultado lógico de la ejecución de la
     * acción: cancel
     */
    public String cancelar() {
        return "cancel";
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public DepartamentoUsuario getDeptoUsuario() {
        return deptoUsuario;
    }

    public void setDeptoUsuario(DepartamentoUsuario deptoUsuario) {
        this.deptoUsuario = deptoUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
