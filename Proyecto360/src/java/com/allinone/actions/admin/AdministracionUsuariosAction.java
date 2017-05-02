package com.allinone.actions.admin;

import com.allinone.actions.BaseAction;
import com.allinone.business.UsuariosBO;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.PersonalAdministrativo;
import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import com.allinone.persistence.model.UsuarioPrivilegio;
import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Patricia Benítez
 */
public class AdministracionUsuariosAction extends BaseAction implements MensajesAdmin {

    public static final String FORMULARIO = "formulario";
    public static final String LISTA = "lista";

    private Usuario usuario = new Usuario();
    private Rol rolUsuario;
    private String contraseniaNueva;
    private String contraseniaNuevaRepetir;
    private PersonalAdministrativo personalAdministrativo = new PersonalAdministrativo();

    private File upload;
    private String uploadFileName;
    private String uploadContentType;

    private List<String> log = new LinkedList<String>();

//  DATOS PARA CAMBIO DE CONTRASEÑA
    private String param;

    private List<Condominio> condominiolist = new ArrayList<Condominio>();
    private Long condominioId;

    /**
     * [form Función default para la administración de usuarios ]
     *
     * @return {[String]} [SUCCESS][Escenario de exito]
     */
    public String lista() {
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
        return SUCCESS;
    }

    /**
     * Formulario de registro de un nuevo usuario
     *
     * @return
     */
    public String nuevo() {
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
        return SUCCESS;
    }

    public String agregaNuevo() {
        if (condominioId == null) {
            addActionError("No se especificó el condominio.");
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
            return "nuevo";
        }
        Condominio c = getDaos().getCondominioDao().findById(condominioId);
        rolUsuario = getDaos().getRolDao().findById(rolUsuario.getId());

        Usuario u = new Usuario();

        try {
            u.setActivo(Boolean.TRUE);
            u.setPassword(contraseniaNueva);
            u.setUsuario(usuario.getUsuario());

            Set<UsuarioPrivilegio> roles = new HashSet<UsuarioPrivilegio>();
            UsuarioPrivilegio privilegio = new UsuarioPrivilegio();
            privilegio.setPrivilegio(rolUsuario);
            privilegio.setUsuario(u);
            roles.add(privilegio);
            u.setPrivilegios(roles);
            usuario = getDaos().getUsuarioDao().save(u);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Ocurrió un error al tratar de insertar el usuario.");
            return INPUT;
        }

        //Agregamos relacion Condominio Usuario
        try {
            UsuarioCondominio uc = new UsuarioCondominio();
            uc.setCondominio(c);
            uc.setUsuario(u);
            getDaos().getUsuarioCondominioDao().save(uc);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Ocurrió un error al tratar de asignar el condominio al usuario.");
            return INPUT;
        }

        addActionMessage("Usuario agregado correctamente.");
        return INPUT;
    }

    public String detalle() {
        if (usuario.getId() == null) {
            return ERROR;
        }
        usuario = getDaos().getUsuarioDao().findById(usuario.getId());
        return SUCCESS;
    }

    public String agregaRol() {
        if (usuario.getId() == null) {
//            getJsonResult().add("[\"No se proporcionaron los datos correctos\"]");
            return ERROR;
        }

        Usuario u = getDaos().getUsuarioDao().findById(usuario.getId());
        if (u != null) {
            if (rolUsuario != null && rolUsuario.getId() != null) {
                try {

                    Rol rol = getDaos().getRolDao().findById(rolUsuario.getId());

                    if (u != null && rol != null) {
                        UsuarioPrivilegio up = new UsuarioPrivilegio();
                        up.setPrivilegio(rol);
                        up.setUsuario(usuario);
                        getDaos().getUsuarioPrivilegioDao().save(up);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                getJsonResult().add("[\"ERROR\"]");
                }
            }
            if (contraseniaNueva != null && !contraseniaNueva.isEmpty()) {
                u.setPassword(contraseniaNueva);
                getDaos().getUsuarioDao().update(u);
            }
        }
//        getJsonResult().add("[\"OK\"]");
        return INPUT;
    }

    /**
     * Formulario para la carga masiva de usuarios Si el condominio, torre o
     * departamento especificados no existen en BD, se dan de alta
     *
     * @return
     */
    public String masivo() {

        return SUCCESS;
    }

    /**
     * Carga el archivo excel y comienza a realizar el guardado de datos.
     *
     * @return
     */
    public String masivoResultadoAlta() {
        if (getUpload() != null) {

            UsuariosBO bo = new UsuariosBO(getDaos());
            // Excel 2003 Format
            if ("application/vnd.ms-excel".equalsIgnoreCase(getUploadContentType())) {
                LOG.debug("Excel 2003 Format");
                LOG.debug("calling readXmlFile(File xls) method...");

                try {
                    //INVOCA PROCESA ARCHIVO
                    log = bo._readXlsFile(new FileInputStream(getUpload()));

                    for (String ce : log) {
                        System.out.println(":>:" + ce);
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    addActionError(getText("carga.archivo.errorFmto"));
                    return INPUT;
                } catch (java.lang.Exception le) {
                    addActionError(getText("carga.archivo.error.sistema")); // + hideError(le));
                    le.printStackTrace();
                    return INPUT;
                }
            } // Excel 2007 Format
            else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(getUploadContentType())) {
                LOG.debug("Excel 2007 Format");
                LOG.debug("calling readXmlxFile(File xlsx) method...");

                try {
                    //
                    log = bo._readXlsxFile(new FileInputStream(getUpload()));

                    for (String ce : log) {
                        System.out.println(":>:" + ce);
                    }

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    addActionError(getText("carga.archivo.errorFmto"));
                    return INPUT;
                } catch (java.lang.Exception le) {
                    addActionError(getText("carga.archivo.error.sistema"));// + hideError(le));
                    le.printStackTrace();
                    return INPUT;
                }
            } // Si no es el content type deseado
            else {
                addActionError(getText("carga.archivo.errorFmto"));
                return INPUT;
            }

        } else {
            addActionError(getText("carga.archivo.errorFile"));
            return INPUT;

        }
        return SUCCESS;
    }

    /**
     * Cambio de contraseña desde el portal
     *
     * @return
     */
    public String administracionPortal() {
        System.out.println(param);
        String[] datos = param.split(",");
        if (datos.length != 3) {
            param = "Faltan parámetros para realizar la operación.";
            return SUCCESS;
        }
        String user = datos[0];
        String oldPwd = datos[1];
        String newPwd = datos[2];

        Usuario u = getDaos().getUsuarioDao().findById(user.trim());
        if (u == null) {
            param = "No se encontró al usuario especificado.";
            return SUCCESS;
        }
        if (u.getPassword().equals(oldPwd.trim())) {
            u.setPassword(newPwd.trim());
            getDaos().getUsuarioDao().update(u);
            param = "OK";
        } else {
            param = "La contraseña anterior, no coincide.";
        }

        return SUCCESS;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PersonalAdministrativo getPersonalAdministrativo() {
        return personalAdministrativo;
    }

    public void setPersonalAdministrativo(PersonalAdministrativo personalAdministrativo) {
        this.personalAdministrativo = personalAdministrativo;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public List<String> getLog() {
        return log;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public List<Condominio> getCondominiolist() {
        return condominiolist;
    }

    public void setCondominiolist(List<Condominio> condominiolist) {
        this.condominiolist = condominiolist;
    }

    public Rol getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(Rol rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    public String getContraseniaNueva() {
        return contraseniaNueva;
    }

    public void setContraseniaNueva(String contraseniaNueva) {
        this.contraseniaNueva = contraseniaNueva;
    }

    public String getContraseniaNuevaRepetir() {
        return contraseniaNuevaRepetir;
    }

    public void setContraseniaNuevaRepetir(String contraseniaNuevaRepetir) {
        this.contraseniaNuevaRepetir = contraseniaNuevaRepetir;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }

}
