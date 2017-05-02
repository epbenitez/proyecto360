package com.allinone.actions.solicitudes;

import com.allinone.actions.BaseAction;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesCategoria;
import com.allinone.persistence.model.SolicitudesEstado;
import com.allinone.persistence.model.SolicitudesPermisos;
import com.allinone.persistence.model.SolicitudesTipo;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<SolicitudesTipo> tipos = new ArrayList<SolicitudesTipo>();
    private List<SolicitudesEstado> estados = new ArrayList<SolicitudesEstado>();
    private List<SolicitudesCategoria> categorias = new ArrayList<SolicitudesCategoria>();
    private Solicitud solicitud = new Solicitud();
    private UsuarioCondominio usuarioCondominio = new UsuarioCondominio();
    private List<SolicitudHistorial> historial = new ArrayList<SolicitudHistorial>();

    private List<Usuario> usuarios = new ArrayList<Usuario>();

    private String fechaCompromiso;
    private String fechaLlegadaAdmin;
    private String fechaEntrega;
    
    private SolicitudHistorial histAbrioSolicitud;

    public SolicitudesAction() {

    }

    public String gestion() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());
            departamentos.add(d.getDepartamento());
            tipos = getDaos().getSolicitudesTipoDao().findAll();
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
                tipos.add(p.getTipoSolicitud());
            }
        }
        estados = getDaos().getSolicitudesEstadoDao().findAll();

        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominios.add(condominio);
            tipos = getDaos().getSolicitudesTipoDao().findAll();
            
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
            tipos = getDaos().getSolicitudesTipoDao().findAll();
            estados = getDaos().getSolicitudesEstadoDao().findAll();
            
        } else {
            establecePermisosCombos(Boolean.FALSE);
        }
        return INPUT;
    }

    public String guarda() {
        if (solicitud == null || solicitud.getDepartamento() == null
                || solicitud.getDepartamento().getTorre() == null
                || solicitud.getDepartamento().getCondominio() == null
                || solicitud.getComentario() == null
                || solicitud.getTipoSolicitud() == null) {
            addActionError("No se han proporcionado uno o más datos necesarios para generar su solicitud, por favor, verifique.");
            return GUARDA;
        }

        Integer consecutivo = getDaos().getSolicitudDao().getConsecutivo(solicitud.getDepartamento().getCondominio().getId(), solicitud.getTipoSolicitud().getId());

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        try {
            solicitud.setFechaSolicitud(new Date());
            solicitud.setEstadoSolicitud(new SolicitudesEstado(1L));
            solicitud.setConsecutivo(consecutivo.longValue() + 1);
            solicitud = getDaos().getSolicitudDao().save(solicitud);

            SolicitudHistorial h = new SolicitudHistorial();
            h.setSolicitud(solicitud);
            h.setFecha(new Date());
            h.setUsuario(u);
            h.setEstadoSolicitud(solicitud.getEstadoSolicitud());
            h.setComentario(solicitud.getComentario());

            getDaos().getSolicitudHistorialDao().save(h);
        } catch (Exception e) {
            e.printStackTrace();
            addActionError("Ocurrió un error al intentar dar de alta la solicitud. " + e.getLocalizedMessage());
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
        usuarioCondominio = getDaos().getUsuarioCondominioDao().getUsuarioCondominio(solicitud.getDepartamento().getCondominio().getId());

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
        
        histAbrioSolicitud = getDaos().getSolicitudHistorialDao().getHistorial(solicitud.getId(),solicitud.getEstadoSolicitud().getId());

        solicitud.setComentario("");
        return DETALLE;
    }

    public String guardaDetalle() {
        if (solicitud == null || solicitud.getId() == null) {
            addActionError("No se encontró la solicitud seleccionada.");
            return INPUT;
        }

        Solicitud solicitudOriginal = getDaos().getSolicitudDao().findById(solicitud.getId());

        solicitudOriginal.setComentario(solicitud.getComentario());
        if (solicitud.getEstadoSolicitud() == null || solicitud.getEstadoSolicitud().getId() == null || solicitud.getEstadoSolicitud().getId().equals("")) {
            solicitud.setEstadoSolicitud(solicitudOriginal.getEstadoSolicitud());
        } else {
            solicitudOriginal.setEstadoSolicitud(solicitud.getEstadoSolicitud());

            if (solicitud.getEstadoSolicitud().getId() == 2L) {                         //EN PROCESO
                if (fechaCompromiso == null) {
                    addActionError("Debe establecer la fecha compromiso.");
                    return detalle();
                }
                Date d = UtilFile.strToDate(fechaCompromiso, "dd-MM-yyyy");
                solicitudOriginal.setFechaCompromiso(d);
            }
            if (solicitud.getEstadoSolicitud().getId() == 3L) {                         //CONCLUIDO
                if (fechaLlegadaAdmin == null || fechaEntrega == null) {
                    addActionError("Debe establecer la fecha de llegada a la administración, y la fecha de entrega.");
                    return detalle();
                }
                Date d = UtilFile.strToDate(fechaLlegadaAdmin, "dd-MM-yyyy");
                solicitudOriginal.setFechaSolucion(d);
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
        hsolicitud.setComentario(solicitud.getComentario());
        hsolicitud.setEstadoSolicitud(solicitud.getEstadoSolicitud());
        hsolicitud.setSolicitud(solicitud);
        hsolicitud.setUsuario(u);

        getDaos().getSolicitudHistorialDao().save(hsolicitud);
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
        tipos = getDaos().getSolicitudesTipoDao().findAll();
        if (isAdministrator()) {
            condominios = getDaos().getCondominioDao().findAll();
        } else {

            condominios = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        usuarios = getDaos().getUsuarioDao().findAll();
        return INPUT;
    }

    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public List<SolicitudesTipo> getTipos() {
        return tipos;
    }

    public void setTipos(List<SolicitudesTipo> tipos) {
        this.tipos = tipos;
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

    public String getFechaCompromiso() {
        return fechaCompromiso;
    }

    public void setFechaCompromiso(String fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
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
    
    

}
