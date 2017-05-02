package com.allinone.actions.notificaciones;

import com.allinone.actions.BaseUploadFileAction;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Notificacion;
import com.allinone.persistence.model.NotificacionDocumento;
import com.allinone.persistence.model.NotificacionGrupo;
import com.allinone.persistence.model.NotificacionesGrupos;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionesAction extends BaseUploadFileAction {

    private List<Condominio> condominios = new ArrayList<Condominio>();
    private List<Torre> torres = new ArrayList<Torre>();
    private NotificacionGrupo grupo = new NotificacionGrupo();
    private Notificacion notificacion = new Notificacion();
    private NotificacionDocumento documento = new NotificacionDocumento();

    protected String contentDisposition = "inline";
    private InputStream fileInputStream;
    
    List<NotificacionGrupo> grupos = new ArrayList<NotificacionGrupo>();

    public String gestionGrupos() {

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominios.add(condominio);

        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return SUCCESS;
    }

    public String detalleGrupos() {
        if (grupo.getId() == null) {
            return ERROR;
        }

        grupo = getDaos().getNotificacionGrupoDao().findById(grupo.getId());
        torres = getDaos().getTorreDao().torrePorCondominio(grupo.getCondominio().getId());

        return SUCCESS;
    }

    public String altaGrupos() {

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominios.add(condominio);

        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        addActionMessage("El Grupo se ha creado exitósamente.");
        return SUCCESS;
    }

    public String guardaGrupos() {
        if (grupo.getNombre() == null || grupo.getNombre().isEmpty()
                || grupo.getCondominio() == null || grupo.getCondominio().getId() == null) {
            addActionError("No se pudo guardar el grupo debido a que no se proporcionaron los datos necesarios.");
            return INPUT;
        }

        grupo.setEstatus(Boolean.TRUE);
        grupo = getDaos().getNotificacionGrupoDao().save(grupo);

        grupo = getDaos().getNotificacionGrupoDao().findById(grupo.getId());
        torres = getDaos().getTorreDao().torrePorCondominio(grupo.getCondominio().getId());

        addActionMessage("El Grupo se ha guardado exitósamente.");

        return INPUT;
    }

    
    /**
     * ***************************NOTIFICACIONES******************************
     */
    public String gestion() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominios.add(condominio);

        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return SUCCESS;
    }

    public String alta() {

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominios.add(condominio);

        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return SUCCESS;
    }

    public String detalle() {
        if (notificacion.getId() == null) {
            return ERROR;
        }
        notificacion = getDaos().getNotificacionDao().findById(notificacion.getId());
        if (notificacion == null) {
            addActionError("No pudo ser encontrado el mensaje indicado.");
            return SUCCESS;
        }

        return SUCCESS;
    }

    public String guarda() {

        try {
            Usuario usuario = getDaos().getUsuarioDao().findById(SecurityContextHolder.getContext().getAuthentication().getName());
            notificacion.setUsuario(usuario);
            notificacion.setFechaModificacion(new Date());

            if (notificacion.getId() == null) {
                notificacion = getDaos().getNotificacionDao().save(notificacion);
            } else {
                notificacion = getDaos().getNotificacionDao().update(notificacion);
            }
        } catch (Exception e) {
            addActionError("No pudo ser encontrado el mensaje indicado.");
            return SUCCESS;
        }
        
        addActionMessage("El mensaje se ha creado exitósamente.");
        return INPUT;
    }

    public String guardaDocumento() {
        if (notificacion.getId() == null) {
            return ERROR;
        }
        notificacion = getDaos().getNotificacionDao().findById(notificacion.getId());
        try {
            if (getUpload() != null) {
                NotificacionDocumento doc = new NotificacionDocumento();
                doc.setNotificacion(notificacion);
                doc.setContentType(getUploadContentType());
                doc.setFilename(getUploadFileName());
                doc.setContent(UtilFile.getBytesFromFile(getUpload()));
                getDaos().getNotificacionDocumentoDao().save(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("No pudo ser guardado el documento indicado.");
            return SUCCESS;
        }
        addActionMessage("El mensaje se ha guardado exitósamente.");
        return INPUT;
    }

    public String descarga() {
        ///notificaciones/mensajeria/descargaArchivoAction.action?documento.id=1
        if (documento.getId() == null) {
            return ERROR;
        }

        documento = getDaos().getNotificacionDocumentoDao().findById(documento.getId());

        File f = UtilFile.bytesToFile(documento.getContent(), documento.getFilename());
        this.setContentDisposition("attachment;filename=\"" + documento.getFilename() + "\"");
        try {
            this.setFileInputStream(new FileInputStream(f));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NotificacionesAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return SUCCESS;
    }

    
    /**
     * **********************NOTIFICACIONES-GRUPOS******************************
     */
    
    public String detalleGruposNotificacion() {
        if (notificacion.getId() == null) {
            return ERROR;
        }
        notificacion = getDaos().getNotificacionDao().findById(notificacion.getId());
        if (notificacion == null) {
            addActionError("No pudo ser encontrado el mensaje indicado.");
            return SUCCESS;
        }
        
        grupos = getDaos().getNotificacionGrupoDao().findAll();

        return SUCCESS;
    }
    
    public String guardaGrupoNotificacion(){
        if (notificacion.getId() == null || grupo.getId()==null) {
            return ERROR;
        }
        notificacion = getDaos().getNotificacionDao().findById(notificacion.getId());
        grupo = getDaos().getNotificacionGrupoDao().findById(grupo.getId());
        
        if (notificacion == null || grupo == null) {
            addActionError("No pudo ser encontrado el grupo a la notificación indicada.");
            return SUCCESS;
        }
        
        NotificacionesGrupos notificacionesPorGrupo = new NotificacionesGrupos();
        notificacionesPorGrupo.setGrupo(grupo);
        notificacionesPorGrupo.setNotificacion(notificacion);
        getDaos().getNotificacionesGruposDao().save(notificacionesPorGrupo);
        
        grupos = getDaos().getNotificacionGrupoDao().findAll();

        addActionMessage("El Grupo se ha añadido exitósamente.");
        
        return "exito";
    }
    
    
    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public NotificacionGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(NotificacionGrupo grupo) {
        this.grupo = grupo;
    }

    public List<Torre> getTorres() {
        return torres;
    }

    public void setTorres(List<Torre> torres) {
        this.torres = torres;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public NotificacionDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(NotificacionDocumento documento) {
        this.documento = documento;
    }

    public String getContentDisposition() {
        return contentDisposition;
    }

    public void setContentDisposition(String contentDisposition) {
        this.contentDisposition = contentDisposition;
    }

    public InputStream getFileInputStream() {
        return fileInputStream;
    }

    public void setFileInputStream(InputStream fileInputStream) {
        this.fileInputStream = fileInputStream;
    }

    public List<NotificacionGrupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<NotificacionGrupo> grupos) {
        this.grupos = grupos;
    }

}
