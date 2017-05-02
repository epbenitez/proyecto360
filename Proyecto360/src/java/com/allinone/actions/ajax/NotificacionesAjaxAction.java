package com.allinone.actions.ajax;

import static com.allinone.actions.ajax.JSONAjaxAction.ERROR_JSON;
import static com.allinone.actions.ajax.JSONAjaxAction.SUCCESS_JSON;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoContacto;
import com.allinone.persistence.model.Notificacion;
import com.allinone.persistence.model.NotificacionDocumento;
import com.allinone.persistence.model.NotificacionGrupo;
import com.allinone.persistence.model.NotificacionGrupoDepartamento;
import com.allinone.persistence.model.NotificacionLog;
import com.allinone.persistence.model.NotificacionesGrupos;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionesAjaxAction extends JSONAjaxAction {

    private NotificacionGrupo grupo = new NotificacionGrupo();
    private Long condominioId;
    private Long grupoDepartamentoId;
    private Long departamentoId;
    private Long torreId;
    private Long documentoId;
    private String nombreGrupo;
    private String mensaje;
    private Long notifGpo;
    private String identificador;

    private List<DepartamentoContacto> logEnvioCorreos = new ArrayList<DepartamentoContacto>();

    public String getListaGrupos() {
        if ((grupo.getNombre().equals("") && condominioId == null)) {
            return ERROR_JSON;
        }
        List<NotificacionGrupo> grupos = getDaos().getNotificacionGrupoDao().gruposPorCondominioNombre(condominioId, grupo.getNombre());
        if (grupos == null) {
        } else {
            for (NotificacionGrupo gd : grupos) {
                getJsonResult().add("[\"" + gd.getNombre()
                        + "\", \"" + gd.getCondominio().getNombre()
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/notificaciones/grupos/detalleGruposNotificaciones.action?grupo.id=" + gd.getId() + "' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-pencil fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a onclick='eliminaGrupo(" + gd.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaGrupo() {
        if (grupo == null || grupo.getId() == null) {
            return ERROR_JSON;
        }

        try {
            grupo = getDaos().getNotificacionGrupoDao().findById(grupo.getId());
            grupo.setEstatus(Boolean.FALSE);
            getDaos().getNotificacionGrupoDao().update(grupo);
            getJsonResult().add("[\"OK\"]");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String getListaGruposDepartamentos() {
        if ((grupo.getId() == null)) {
            return ERROR_JSON;
        }
        List<NotificacionGrupoDepartamento> grupos = getDaos().getNotificacionGrupoDepartamentoDao().departamentosPorGrupo(grupo.getId());
        if (grupos == null) {
        } else {
            for (NotificacionGrupoDepartamento gd : grupos) {
                getJsonResult().add("[\"" + gd.getDepartamento().getTorre().getNombre()
                        + "\", \"" + gd.getDepartamento().getNombre()
                        + "\", \"<a onclick='eliminaDepartamento(" + gd.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");

            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaDepartamento() {
        if (grupoDepartamentoId == null || grupoDepartamentoId.equals("")) {
            return ERROR_JSON;
        }

        try {
            NotificacionGrupoDepartamento grupoDepto = getDaos().getNotificacionGrupoDepartamentoDao().findById(grupoDepartamentoId);
            if (grupoDepto == null) {
                getJsonResult().add("[\"ERROR\"]");
            } else {
                getDaos().getNotificacionGrupoDepartamentoDao().delete(grupoDepto);
                getJsonResult().add("[\"OK\"]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String agregaDepartamento() {

        if (departamentoId == null || departamentoId.equals("")
                || torreId == null || torreId.equals("")
                || grupo == null || grupo.getId() == null) {
            return ERROR_JSON;
        }

        grupo = getDaos().getNotificacionGrupoDao().findById(grupo.getId());

        //SI CAMBIA EL NOMBRE, SE ACTUALIZA EL GRUPO
        if (nombreGrupo != null && !nombreGrupo.equals("")
                && !nombreGrupo.equals(grupo.getNombre())) {

            grupo.setNombre(nombreGrupo);
            getDaos().getNotificacionGrupoDao().update(grupo);

        }

        //SE AGREGA UN DEPARTAMENTO EN PARTICULAR
        if (departamentoId != 0L) {
            try {
                Departamento departamento = getDaos().getDepartamentoDao().findById(departamentoId);

                if (departamento == null || grupo == null) {
                    getJsonResult().add("[\"ERROR\"]");
                } else {
                    NotificacionGrupoDepartamento grupoDepto = new NotificacionGrupoDepartamento();
                    grupoDepto.setDepartamento(departamento);
                    grupoDepto.setGrupo(grupo);
                    try {
                        getDaos().getNotificacionGrupoDepartamentoDao().save(grupoDepto);
                    } catch (Exception e) {
                        getJsonResult().add("[\"EXISTENTE\"]");
                    }
                    getJsonResult().add("[\"OK\"]");
                }

            } catch (Exception e) {
                e.printStackTrace();
                return ERROR_JSON;
            }
        } else {

            List<Departamento> departamentos = null;
            //Todas las torres y todos los departamentos
            if (torreId == 0L) {
                departamentos = getDaos().getDepartamentoDao().findBy(grupo.getCondominio().getId());

            } else //TODOS LOS DEPARTAMENTOS DE UN CONDOMINIO
             if (departamentoId == 0L) {
                    departamentos = getDaos().getDepartamentoDao().findBy(torreId, grupo.getCondominio().getId());

                }

            if (departamentos == null || departamentos.isEmpty()) {
                getJsonResult().add("[\"ERROR\"]");
            } else {
                NotificacionGrupoDepartamento grupoDepto;
                for (Departamento d : departamentos) {
                    grupoDepto = new NotificacionGrupoDepartamento();
                    grupoDepto.setDepartamento(d);
                    grupoDepto.setGrupo(grupo);
                    try {
                        getDaos().getNotificacionGrupoDepartamentoDao().save(grupoDepto);
                    } catch (Exception e) {
                        getJsonResult().add("[\"EXISTENTE\"]");
                    }
                }
                getJsonResult().add("[\"OK\"]");
            }

        }
        return SUCCESS_JSON;
    }

    /**
     * ********************************************************************
     */
    /**
     * **************************NOTIFICACIONES****************************
     */
    public String getListaMensajes() {
        if ((mensaje.equals("") && condominioId == null)) {
            return ERROR_JSON;
        }
        List<Notificacion> mensajes = getDaos().getNotificacionDao().notificacionesPorCondominioAsunto(condominioId, grupo.getNombre());
        if (mensajes == null) {
        } else {
            for (Notificacion n : mensajes) {
                getJsonResult().add("[\"" + n.getId()
                        + "\", \"" + n.getCondominio().getNombre()
                        + "\", \"" + n.getAsunto()
                        + "\", \"" + n.getMensaje()
                        + "\", \"" + UtilFile.dateToString(n.getFechaInicio(), "yyyy-MM-dd")
                        + "\", \"" + UtilFile.dateToString(n.getFechaFinal(), "yyyy-MM-dd")
                        + "\", \"" + n.getEstatus().getNombre()
                        + "\", \""
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/notificaciones/mensajeria/detalleMensajeria.action?notificacion.id=" + n.getId() + "' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-pencil fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/notificaciones/mensajeria/detalleGruposNotificacionMensajeria.action?notificacion.id=" + n.getId() + "' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-users fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a onclick='eliminaMensaje(" + n.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getListaDocumentos() {
        if (mensaje.equals("")) {
            return ERROR_JSON;
        }

        Notificacion msg = getDaos().getNotificacionDao().findById(new Long(mensaje));
        List<NotificacionDocumento> documentos = getDaos().getNotificacionDocumentoDao().getDocumentos(msg.getId());
        if (documentos == null) {
        } else {
            int i = 0;
            for (NotificacionDocumento d : documentos) {
                getJsonResult().add("[\"" + (++i)
                        //                        + "\", \"" + d.getFilename()
                        + "\", \"<a target='_blank'  href='notificaciones/mensajeria/descargaArchivoAction.action?documento.id=" + d.getId() + "' >"
                        //                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        //                        + "<i class='fa fa-download fa-stack-1x fa-inverse'></i> "
                        //                        + "</span>"
                        + d.getFilename()
                        + "</a>"
                        + "\", \"<a onclick='eliminaDocumento(" + d.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaDocumento() {
        if (documentoId == null || documentoId.equals("")) {
            return ERROR_JSON;
        }

        try {
            NotificacionDocumento documento = getDaos().getNotificacionDocumentoDao().findById(documentoId);
            if (documento == null) {
                getJsonResult().add("[\"ERROR\"]");
            } else {
                getDaos().getNotificacionDocumentoDao().delete(documento);
                getJsonResult().add("[\"OK\"]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String getListaNotifGrupos() {
        if (mensaje.equals("")) {
            return ERROR_JSON;
        }
        //Reset al log de envio de correos
        logEnvioCorreos = new ArrayList<DepartamentoContacto>();

        Notificacion msg = getDaos().getNotificacionDao().findById(new Long(mensaje));
        List<NotificacionesGrupos> gruposNotifList = getDaos().getNotificacionesGruposDao().getGrupos(msg.getId());
        if (gruposNotifList == null) {
        } else {
            int i = 0;
            
            for (NotificacionesGrupos d : gruposNotifList) {
                String identificador = "RNG-" + d.getId() + "-" + UtilFile.dateToString(new Date(), "yyMMddhhmmss");
                getJsonResult().add("[\"" + (++i)
                        + "\", \"" + d.getGrupo().getNombre()
                        //                        + "\", \"<a target='_blank'  href='notificaciones/mensajeria/descargaArchivoAction.action?documento.id=" + d.getId() + "' >"
                        //                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        //                        + "<i class='fa fa-download fa-stack-1x fa-inverse'></i> "
                        //                        + "</span>"
                        //                        + "</a>"
                        + "\", \"<a onclick='eliminaNotifGrupo(" + d.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a onclick=enviarNotifGrupo(" + d.getId() + ",'" + identificador + "') >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-send-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaNotifGrupo() {
        if (notifGpo == null || notifGpo.equals("")) {
            return ERROR_JSON;
        }

        try {
            NotificacionesGrupos notificacionesGrupo = getDaos().getNotificacionesGruposDao().findById(notifGpo);
            if (notificacionesGrupo == null) {
                getJsonResult().add("[\"ERROR\"]");
            } else {
                getDaos().getNotificacionesGruposDao().delete(notificacionesGrupo);
                getJsonResult().add("[\"OK\"]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String enviarNotifGrupo() {
        if (notifGpo == null || notifGpo.equals("") || 
                identificador==null || identificador.equals("")) {
            return ERROR_JSON;
        }

        NotificacionesGrupos notificacionesGrupo = getDaos().getNotificacionesGruposDao().findById(notifGpo);
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        
        try {
            NotificacionGrupo gpo = notificacionesGrupo.getGrupo();
            List<DepartamentoContacto> datosContacto = getDaos().getDepartamentoContactoDao().getContactos(gpo.getId());
            if (datosContacto == null) {
                getJsonResult().add("[\"ERROR\"]");
            } else {
                for (DepartamentoContacto dc : datosContacto) {
                    String asunto = notificacionesGrupo.getNotificacion().getAsunto();
                    String mensaje = notificacionesGrupo.getNotificacion().getMensaje();
                    try {
                        sendEmail("allinone@servicesmx.com", dc.getContacto().getCorreoElectronico(), asunto, mensaje);

                        NotificacionLog notificacionLog = new NotificacionLog();
                        notificacionLog.setFecha(new Date());
                        notificacionLog.setGrupo(gpo);
                        notificacionLog.setNotificacion(notificacionesGrupo.getNotificacion());
                        notificacionLog.setIdentificador(identificador);
                        notificacionLog.setDepartamento(dc.getDepartamento());
                        notificacionLog.setUsuario(u);
                        getDaos().getNotificacionLogDao().save(notificacionLog);


                    } catch (Exception e) {
                        getJsonResult().add("[\"EXCEPCION\"]");
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            getJsonResult().add("[\"ERROR\"]");
            return SUCCESS_JSON;
        }
        getJsonResult().add("[\"OK\"]");
        return SUCCESS_JSON;
    }

    public String consultaLogEnvioCorreos() {
        if(identificador==null || identificador.isEmpty()){
            return ERROR_JSON;
        }
        
        List<NotificacionLog> notificacionLogList = getDaos().getNotificacionLogDao().getLog(identificador);
        if (notificacionLogList!=null && notificacionLogList.size() >= 0) {
            for (NotificacionLog log : notificacionLogList) {
                getJsonResult().add("[\"" + UtilFile.dateToString(log.getFecha(), "yyyy-MM-dd hh:mm")
                        + "\", \"" + log.getGrupo().getNombre()
                        + "\", \"" + log.getNotificacion().getAsunto()
                        + "\", \"" + log.getUsuario().getUsuario()
                        + "\", \"" + log.getDepartamento().getNombre()
                        + " \"]");

            }
        }
        return SUCCESS_JSON;
    }

    public NotificacionGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(NotificacionGrupo grupo) {
        this.grupo = grupo;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }

    public Long getGrupoDepartamentoId() {
        return grupoDepartamentoId;
    }

    public void setGrupoDepartamentoId(Long grupoDepartamentoId) {
        this.grupoDepartamentoId = grupoDepartamentoId;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Long getTorreId() {
        return torreId;
    }

    public void setTorreId(Long torreId) {
        this.torreId = torreId;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }

    public Long getNotifGpo() {
        return notifGpo;
    }

    public void setNotifGpo(Long notifGpo) {
        this.notifGpo = notifGpo;
    }

    public List<DepartamentoContacto> getLogEnvioCorreos() {
        return logEnvioCorreos;
    }

    public void setLogEnvioCorreos(List<DepartamentoContacto> logEnvioCorreos) {
        this.logEnvioCorreos = logEnvioCorreos;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

}
