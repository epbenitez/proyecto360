package com.allinone.actions.solicitudes;

import com.allinone.actions.BaseAction;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesCategoria;
import com.allinone.persistence.model.SolicitudesDocumento;
import com.allinone.persistence.model.SolicitudesEstado;
import com.allinone.persistence.model.SolicitudesPermisos;
import com.allinone.persistence.model.SolicitudesTipoInmueble;
import com.allinone.persistence.model.SolicitudesTipoServicio;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import com.allinone.util.UtilFile;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;
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

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesAction extends BaseAction {

    public static final String DETALLE = "detalle";
    public static final String GUARDA = "guarda";

    private List<Condominio> condominios = new ArrayList<Condominio>();
    private List<Torre> torres = new ArrayList<Torre>();
    private List<Departamento> departamentos = new ArrayList<Departamento>();
    private List<SolicitudesTipoInmueble> tiposInmueble = new ArrayList<SolicitudesTipoInmueble>();
    private List<SolicitudesTipoServicio> tipos = new ArrayList<SolicitudesTipoServicio>();
    private List<SolicitudesEstado> estados = new ArrayList<SolicitudesEstado>();
    private List<SolicitudesCategoria> categorias = new ArrayList<SolicitudesCategoria>();
    private Solicitud solicitud = new Solicitud();
    private UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
    private List<SolicitudHistorial> historial = new ArrayList<SolicitudHistorial>();

    private List<Usuario> usuarios = new ArrayList<Usuario>();

    private String fechaProgramada;
    private String fechaLlegadaAdmin;
    private String fechaEntrega;

    private SolicitudHistorial histAbrioSolicitud;

    protected String contentDisposition = "inline";
    private InputStream fileInputStream;
    
    private File uploadF1;
    private String uploadF1FileName;
    private String uploadF1ContentType;

    private File uploadF2;
    private String uploadF2FileName;
    private String uploadF2ContentType;

    private File uploadF3;
    private String uploadF3FileName;
    private String uploadF3ContentType;
    
    private Long solicitudesDocumentoId;

    public SolicitudesAction() {

    }

    public String gestion() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());
            departamentos.add(d.getDepartamento());
//            tipos = getDaos().getSolicitudesTipoDao().findAll();
            tiposInmueble = getDaos().getSolicitudesTipoInmuebleDao().findAll();
            estados = getDaos().getSolicitudesEstadoDao().findAll();

        } else {
            establecePermisosCombos(Boolean.TRUE);
        }
        return INPUT;
    }

    public void establecePermisosCombos(Boolean atender) {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        List<SolicitudesPermisos> permisos = getDaos().getSolicitudesPermisosDao().findByUsuario(u.getId(), atender);
        if (permisos != null) {
            for (SolicitudesPermisos p : permisos) {
//                tipos.add(p.getTipoSolicitud());
                tiposInmueble = getDaos().getSolicitudesTipoInmuebleDao().findAll();
            }
        }
        estados = getDaos().getSolicitudesEstadoDao().findAll();

        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominios.add(condominio);
//            tipos = getDaos().getSolicitudesTipoDao().findAll();
            tiposInmueble = getDaos().getSolicitudesTipoInmuebleDao().findAll();

        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
    }

    public String alta() {
        //Carga la lista de Tipos de Solicitud que le corresponden de acuerdo a su tabla de permisos
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());
            departamentos.add(d.getDepartamento());
//            tipos = getDaos().getSolicitudesTipoDao().findAll();
            tiposInmueble = getDaos().getSolicitudesTipoInmuebleDao().findAll();
            estados = getDaos().getSolicitudesEstadoDao().findAll();

        } else {
            establecePermisosCombos(Boolean.FALSE);
        }
        return INPUT;
    }

    public String guarda() {
        if (solicitud == null || solicitud.getCondominio() == null
                || solicitud.getTipoServicio() == null
                || solicitud.getArea() == null
                || solicitud.getCategoriaSolicitud() == null
                || solicitud.getAsunto() == null
                || solicitud.getDescripcion() == null) {
            System.out.println("solicitud: " + solicitud);
            addActionError("No se han proporcionado uno o más datos necesarios para generar su solicitud, por favor, verifique.");
            return GUARDA;
        }

        Integer consecutivo = getDaos().getSolicitudDao().getConsecutivo(solicitud.getCondominio().getId(), solicitud.getTipoServicio().getId());

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        SolicitudHistorial h = new SolicitudHistorial();
        try {
            solicitud.setFechaIngresoTicket(new Date());
            solicitud.setEstadoSolicitud(new SolicitudesEstado(1L));
            solicitud.setConsecutivo(consecutivo.longValue() + 1);
            solicitud = getDaos().getSolicitudDao().save(solicitud);

            
            h.setSolicitud(solicitud);
            h.setFecha(new Date());
            h.setUsuario(u);
            h.setEstadoSolicitud(solicitud.getEstadoSolicitud());
            h.setComentario(solicitud.getAsunto());

            h = getDaos().getSolicitudHistorialDao().save(h);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Ocurrió un error al intentar dar de alta la solicitud. " + e.getLocalizedMessage());
            return alta();
        }

        //Guardando documentos
        try {
            if (getUploadF1() != null) {
                guardaDocumento(h, uploadF1, uploadF1FileName, uploadF1ContentType);
            }
            if (getUploadF2() != null) {
                guardaDocumento(h, uploadF2, uploadF2FileName, uploadF2ContentType);
            }
            if (getUploadF3() != null) {
                guardaDocumento(h, uploadF3, uploadF3FileName, uploadF3ContentType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Ocurrió un error al intentar guardar los documentos de la solicitud. " + e.getLocalizedMessage());
            return alta();
        }
        addActionMessage("Se ha dado de alta la solicitud exitósamente.");
        return detalle();

    }

    public String detalle() {
        //Datos que vienen del post
        if (solicitud == null || solicitud.getId() == null) {
            addActionError("No se encontró la solicitud seleccionada.");
            return INPUT;
        }
        solicitud = getDaos().getSolicitudDao().findById(solicitud.getId());

        //Verificamos si se encontró el registro
        if (solicitud == null || solicitud.getId() == null) {
            addActionError("No se encontró la solicitud seleccionada.");
            return INPUT;
        }
        //Actualizamos la fecha de lectura
        if (solicitud.getFechaLectura() == null) {
            solicitud.setFechaLectura(new Date());
            getDaos().getSolicitudDao().update(solicitud);
        }

        historial = getDaos().getSolicitudHistorialDao().getHistorial(solicitud.getId());
        usuarioCondominio = getDaos().getUsuarioCondominioDao().getUsuarioCondominio(solicitud.getCondominio().getId());

        if (solicitud.getEstadoSolicitud().getId() == 1L) {                         //ABIERTO
            estados = new ArrayList<SolicitudesEstado>();
            SolicitudesEstado edo = getDaos().getSolicitudesEstadoDao().findById(2L);
            estados.add(edo);
            edo = getDaos().getSolicitudesEstadoDao().findById(4L);
            estados.add(edo);
        }
        if (solicitud.getEstadoSolicitud().getId() == 2L) {                         //EN PROCESO
            estados = new ArrayList<SolicitudesEstado>();
            SolicitudesEstado edo = getDaos().getSolicitudesEstadoDao().findById(3L);
            estados.add(edo);
            edo = getDaos().getSolicitudesEstadoDao().findById(4L);
            estados.add(edo);
        }
        if (solicitud.getEstadoSolicitud().getId() == 3L) {                         //CONCLUIDO
            estados = new ArrayList<SolicitudesEstado>();
            SolicitudesEstado edo = getDaos().getSolicitudesEstadoDao().findById(3L);
            estados.add(edo);
        }

        if (solicitud.getEstadoSolicitud().getId() == 4L) {                         //CANCELADO
            estados = new ArrayList<SolicitudesEstado>();
            SolicitudesEstado edo = getDaos().getSolicitudesEstadoDao().findById(4L);
            estados.add(edo);
        }

        categorias = getDaos().getSolicitudesCategoriaDao().findAll();

        histAbrioSolicitud = getDaos().getSolicitudHistorialDao().getHistorial(solicitud.getId(), solicitud.getEstadoSolicitud().getId());

        return DETALLE;
    }

    public String guardaDetalle() {
        if (solicitud == null || solicitud.getId() == null) {
            addActionError("No se encontró la solicitud seleccionada.");
            return INPUT;
        }

        Solicitud solicitudOriginal = getDaos().getSolicitudDao().findById(solicitud.getId());

//        solicitudOriginal.setComentario(solicitud.getComentario());
        if (solicitud.getEstadoSolicitud() == null || solicitud.getEstadoSolicitud().getId() == null || solicitud.getEstadoSolicitud().getId().equals("")) {
            solicitud.setEstadoSolicitud(solicitudOriginal.getEstadoSolicitud());
        } else {
            solicitudOriginal.setEstadoSolicitud(solicitud.getEstadoSolicitud());

            if (solicitud.getEstadoSolicitud().getId() == 2L) {                         //EN PROCESO
                if (fechaProgramada == null) {
                    addActionError("Debe establecer la fecha programada.");
                    return detalle();
                }
                Date d = UtilFile.strToDate(fechaProgramada, "dd-MM-yyyy");
                solicitudOriginal.setFechaProgramada(d);
            }
            if (solicitud.getEstadoSolicitud().getId() == 3L) {                         //CONCLUIDO
                if (fechaLlegadaAdmin == null || fechaEntrega == null) {
                    addActionError("Debe establecer la fecha de llegada a la administración, y la fecha de entrega.");
                    return detalle();
                }
                Date d = UtilFile.strToDate(fechaLlegadaAdmin, "dd-MM-yyyy");
                solicitudOriginal.setFechaAtencion(d);
                d = UtilFile.strToDate(fechaEntrega, "dd-MM-yyyy");
                solicitudOriginal.setFechaNotificacionCliente(d);
            }
            if (solicitud.getCategoriaSolicitud() != null && solicitud.getCategoriaSolicitud().getId() != null) {
                solicitudOriginal.setCategoriaSolicitud(solicitud.getCategoriaSolicitud());
            }
        }

        getDaos().getSolicitudDao().update(solicitudOriginal);

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        SolicitudHistorial hsolicitud = new SolicitudHistorial();
        hsolicitud.setFecha(new Date());
        hsolicitud.setComentario(solicitud.getAsunto());
        hsolicitud.setEstadoSolicitud(solicitud.getEstadoSolicitud());
        hsolicitud.setSolicitud(solicitud);
        hsolicitud.setUsuario(u);

        hsolicitud = getDaos().getSolicitudHistorialDao().save(hsolicitud);
        
        
        //Guardando documentos
        try {
            if (getUploadF1() != null) {
                guardaDocumento(hsolicitud, uploadF1, uploadF1FileName, uploadF1ContentType);
            }
            if (getUploadF2() != null) {
                guardaDocumento(hsolicitud, uploadF2, uploadF2FileName, uploadF2ContentType);
            }
            if (getUploadF3() != null) {
                guardaDocumento(hsolicitud, uploadF3, uploadF3FileName, uploadF3ContentType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Ocurrió un error al intentar guardar los documentos. " + e.getLocalizedMessage());
            return detalle();
        }
        
        addActionMessage("Estado de solicitud actualizado correctamente");
        return detalle();
    }

    /**
     * Establece permisos por usuario, por condominio
     *
     * @return
     */
    public String permisos() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        tipos = getDaos().getSolicitudesTipoServicioDao().findAll();
        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        usuarios = getDaos().getUsuarioDao().findAll();
        return INPUT;
    }

    public String guardaDocumento(SolicitudHistorial s, File f, String fname, String fcontenttype) {
        if (solicitud.getId() == null) {
            return ERROR;
        }
        try {
            if (getUploadF1() != null) {
                SolicitudesDocumento doc = new SolicitudesDocumento();
                doc.setSolicitudHistorial(s);
                doc.setContentType(fcontenttype);
                doc.setFilename(fname);
                doc.setContent(UtilFile.getBytesFromFile(f));
                doc.setFecha(new Date());
                getDaos().getSolicitudesDocumentoDao().save(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("No pudo ser guardado el documento indicado.");
            return SUCCESS;
        }
        addActionMessage("El mensaje se ha guardado exitósamente.");
        return INPUT;
    }
    
    public String descargaDocumento() {
        ///notificaciones/mensajeria/descargaArchivoAction.action?documento.id=1
        if (solicitudesDocumentoId == null) {
            return ERROR;
        }

        SolicitudesDocumento doc = getDaos().getSolicitudesDocumentoDao().findById(solicitudesDocumentoId);

        File f = UtilFile.bytesToFile(doc.getContent(), doc.getFilename());
        this.setContentDisposition("attachment;filename=\"" + doc.getFilename() + "\"");
        try {
            this.setFileInputStream(new FileInputStream(f));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolicitudesAction.class.getName()).log(Level.SEVERE, null, ex);
        }

        return SUCCESS;
    }

    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public List<SolicitudesTipoInmueble> getTiposInmueble() {
        return tiposInmueble;
    }

    public void setTiposInmueble(List<SolicitudesTipoInmueble> tiposInmueble) {
        this.tiposInmueble = tiposInmueble;
    }

    public List<SolicitudesEstado> getEstados() {
        return estados;
    }

    public void setEstados(List<SolicitudesEstado> estados) {
        this.estados = estados;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public UsuarioCondominio getUsuarioCondominio() {
        return usuarioCondominio;
    }

    public void setUsuarioCondominio(UsuarioCondominio usuarioCondominio) {
        this.usuarioCondominio = usuarioCondominio;
    }

    public List<SolicitudHistorial> getHistorial() {
        return historial;
    }

    public void setHistorial(List<SolicitudHistorial> historial) {
        this.historial = historial;
    }

    public String getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(String fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getFechaLlegadaAdmin() {
        return fechaLlegadaAdmin;
    }

    public void setFechaLlegadaAdmin(String fechaLlegadaAdmin) {
        this.fechaLlegadaAdmin = fechaLlegadaAdmin;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<SolicitudesCategoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<SolicitudesCategoria> categorias) {
        this.categorias = categorias;
    }

    public List<Torre> getTorres() {
        return torres;
    }

    public void setTorres(List<Torre> torres) {
        this.torres = torres;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public SolicitudHistorial getHistAbrioSolicitud() {
        return histAbrioSolicitud;
    }

    public void setHistAbrioSolicitud(SolicitudHistorial histAbrioSolicitud) {
        this.histAbrioSolicitud = histAbrioSolicitud;
    }

    public List<SolicitudesTipoServicio> getTipos() {
        return tipos;
    }

    public void setTipos(List<SolicitudesTipoServicio> tipos) {
        this.tipos = tipos;
    }

    public File getUploadF1() {
        return uploadF1;
    }

    public void setUploadF1(File uploadF1) {
        this.uploadF1 = uploadF1;
    }

    public String getUploadF1FileName() {
        return uploadF1FileName;
    }

    public void setUploadF1FileName(String uploadF1FileName) {
        this.uploadF1FileName = uploadF1FileName;
    }

    public String getUploadF1ContentType() {
        return uploadF1ContentType;
    }

    public void setUploadF1ContentType(String uploadF1ContentType) {
        this.uploadF1ContentType = uploadF1ContentType;
    }

    public File getUploadF2() {
        return uploadF2;
    }

    public void setUploadF2(File uploadF2) {
        this.uploadF2 = uploadF2;
    }

    public String getUploadF2FileName() {
        return uploadF2FileName;
    }

    public void setUploadF2FileName(String uploadF2FileName) {
        this.uploadF2FileName = uploadF2FileName;
    }

    public String getUploadF2ContentType() {
        return uploadF2ContentType;
    }

    public void setUploadF2ContentType(String uploadF2ContentType) {
        this.uploadF2ContentType = uploadF2ContentType;
    }

    public File getUploadF3() {
        return uploadF3;
    }

    public void setUploadF3(File uploadF3) {
        this.uploadF3 = uploadF3;
    }

    public String getUploadF3FileName() {
        return uploadF3FileName;
    }

    public void setUploadF3FileName(String uploadF3FileName) {
        this.uploadF3FileName = uploadF3FileName;
    }

    public String getUploadF3ContentType() {
        return uploadF3ContentType;
    }

    public void setUploadF3ContentType(String uploadF3ContentType) {
        this.uploadF3ContentType = uploadF3ContentType;
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

    public Long getSolicitudesDocumentoId() {
        return solicitudesDocumentoId;
    }

    public void setSolicitudesDocumentoId(Long solicitudesDocumentoId) {
        this.solicitudesDocumentoId = solicitudesDocumentoId;
    }

}
