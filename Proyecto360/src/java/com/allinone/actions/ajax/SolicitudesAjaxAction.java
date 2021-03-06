package com.allinone.actions.ajax;

import static com.allinone.actions.ajax.JSONAjaxAction.SUCCESS_JSON;
import com.allinone.business.SolicitudesBO;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesArea;
import com.allinone.persistence.model.SolicitudesCategoria;
import com.allinone.persistence.model.SolicitudesPermisos;
import com.allinone.persistence.model.SolicitudesTipoServicio;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesAjaxAction extends JSONAjaxAction {

    private Long pkCondominio;
    private Long pkTorre;
    private Long pkDepartamento;
    private Long pkTipo;
    private String pkTipoServicios;
    private Long pkEstado;

    private Long pkPermiso;
    private Long pkUsuario;
    private String pkTipoPermiso;

    private Long pkTipoSolicitud;
    private Long pkInmueble;
    private Long pkTipoInmueble;
    private Long pkAreaId;
    private Long pkCategoria; //Tipo de Trabajo

    private Boolean busqueda = Boolean.FALSE;

    public String getTorres() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            List<Torre> lista = new ArrayList<Torre>();
            lista.add(du.getDepartamento().getTorre());
            if (lista == null) {
            } else {
                for (Torre d : lista) {
                    getJsonResult().add("[\"" + d.getId()
                            + "\", \"" + d.getNombre()
                            + " \"]");
                }
            }
        } else {
            List<Torre> lista = getDaos().getTorreDao().torrePorCondominio(pkCondominio);
            if (lista == null) {
            } else {
                for (Torre d : lista) {
                    getJsonResult().add("[\"" + d.getId()
                            + "\", \"" + d.getNombre()
                            + " \"]");
                }
            }
        }
        return SUCCESS_JSON;
    }

    public String getDepartamentos() {

        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            List<Departamento> lista = new ArrayList<Departamento>();
            lista.add(du.getDepartamento());
            if (lista == null) {
            } else {
                for (Departamento d : lista) {
                    getJsonResult().add("[\"" + d.getId()
                            + "\", \"" + d.getNombre()
                            + " \"]");
                }
            }
        } else {
            List<Departamento> lista = getDaos().getDepartamentoDao().findBy(pkTorre, pkCondominio);
            if (lista == null) {
            } else {
                for (Departamento d : lista) {
                    getJsonResult().add("[\"" + d.getId()
                            + "\", \"" + d.getNombre()
                            + " \"]");
                }
            }
        }
        return SUCCESS_JSON;
    }

    public String getTipoServicio() {
        Long restringirPermisosUsuarioId = null;
        if (isAdminCondominio()) { //Se restringen las opciones de búsqueda/nuevo ticket a lo indicado en la tabla de solicitudes-permisos
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            restringirPermisosUsuarioId = u.getId();
        }
        List<SolicitudesTipoServicio> lista = getDaos().getSolicitudesTipoServicioDao().findBySolicitudTipoInmueble(pkInmueble, restringirPermisosUsuarioId, busqueda);
        if (lista == null) {
            getJsonResult().add("[\"0"
                    + "\", \"No tiene permisos asignados para " + (busqueda ? "buscar Tickets de este Inmueble" : "ingresar un nuevo Ticket")
                    + " \"]");
        } else {
            for (SolicitudesTipoServicio d : lista) {
                getJsonResult().add("[\"" + d.getId()
                        + "\", \"" + d.getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getTipoImnueble() {
        Condominio inmueble = getDaos().getCondominioDao().findById(pkCondominio);
        if (inmueble != null) {
            getJsonResult().add("[\"" + inmueble.getTipoInmueble().getId()
                    + "\", \"" + inmueble.getTipoInmueble().getNombre()
                    + " \"]");
        }
        return SUCCESS_JSON;
    }

    public String getAreas() {
        List<SolicitudesArea> lista = getDaos().getSolicitudesAreaDao().findBySolicitudTipoInmueble(pkInmueble, pkTipoSolicitud);
        if (lista == null) {
        } else {
            for (SolicitudesArea d : lista) {
                getJsonResult().add("[\"" + d.getId()
                        + "\", \"" + d.getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getCategorias() {
        List<SolicitudesCategoria> lista = getDaos().getSolicitudesCategoriaDao().findBySolicitudTipoInmueble(pkInmueble, pkTipoSolicitud, pkAreaId);
        if (lista == null) {
        } else {
            for (SolicitudesCategoria d : lista) {
                getJsonResult().add("[\"" + d.getId()
                        + "\", \"" + d.getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getTiposDeSol() {
        List<SolicitudesTipoServicio> lista = getDaos().getSolicitudesTipoServicioDao().find(pkCondominio);
        if (lista == null) {
        } else {
            for (SolicitudesTipoServicio d : lista) {
                getJsonResult().add("[\"" + d.getId()
                        + "\", \"" + d.getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    /**
     * pkCondominio: Inmueble pkTipo: Tipo de Servicio pkAreaId: Area
     * pkCategoria: Tipo de Trabajo pkEstado: Estado
     *
     * @return
     */
    public String busqueda() {
        NumberFormat formatter = new DecimalFormat("0000");
        SolicitudesBO bo = new SolicitudesBO(getDaos());
        List<SolicitudHistorial> lista = null;
        if (isAdministrator()) {
            lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, pkTipo, pkEstado, pkAreaId, pkCategoria);
        } else {
            if (pkCondominio == null) {
                if (!isAdministrator()) {
                    getJsonResult().add("");
                    return SUCCESS_JSON;
                }
            } else if (isPropietario()) {
                lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, new ArrayList<SolicitudesTipoServicio>(), pkEstado, pkAreaId, pkCategoria);
            } else {
                //Solo una lista de las solicitudes del tipo que tiene permiso en la tabla rmm_solicitudes_permisos
                Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
                List<SolicitudesPermisos> permisos = getDaos().getSolicitudesPermisosDao().findByUsuario(u.getId(), Boolean.TRUE);
                List<SolicitudesTipoServicio> tipos = new ArrayList<SolicitudesTipoServicio>();
                if (permisos != null) {
                    for (SolicitudesPermisos p : permisos) {
                        //Si no seleccionaron Tipo de Servicio, muestra todos a los que tiene permiso
                        if (pkTipo == null || pkTipo.toString().trim().equals("")) {
                            tipos.add(p.getTipoServicio());
                        }else{
                            if(p.getTipoServicio().getId().equals(pkTipo)){
                                tipos.add(p.getTipoServicio());
                            }
                        }
                    }
                    //Quitando duplicados
                    Set<SolicitudesTipoServicio> tipoServicioSet = new LinkedHashSet<SolicitudesTipoServicio>(tipos);
                    tipos.clear();
                    tipos.addAll(tipoServicioSet);
                }
                lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, tipos, pkEstado, pkAreaId, pkCategoria);
            }
        }

        if (lista == null || lista.isEmpty()) {
            getJsonResult().add("");
            return SUCCESS_JSON;
        } else if (isAdministrator() || isPropietario()) {

            lista = bo.getUmbrales(lista);

            for (SolicitudHistorial sh : lista) {
//                    Date fechaSolucion = d.getFechaSolucion() == null ? new Date() : d.getFechaSolucion();
                String[] colorMasTooltip = new String[2];
                if (sh.getSolicitud().getEstadoSolicitud().getId() == 4) {
                    colorMasTooltip[0] = "#cef9ff";
                    colorMasTooltip[1] = "Cancelado";
                } else {
                    colorMasTooltip = bo.getColor(sh.getSolicitud().getUmbral(), sh.getSolicitud().getFechaIngresoTicket(), sh.getSolicitud().getFechaAtencion());
                }

                getJsonResult().add("[\"" + sh.getSolicitud().getCondominio().getNombre()
                        + "\", \"" + sh.getSolicitud().getTipoServicio().getNombre()
                        + "\", \"" + sh.getSolicitud().getEstadoSolicitud().getNombre()
                        + "\", \"" + (sh.getSolicitud().getFechaIngresoTicket() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaIngresoTicket(), "yyyy-MM-dd hh:mm aa"))
                        //                            + "\", \"" + (d.getFechaLectura() == null ? "" : UtilFile.dateToString(d.getFechaLectura(), "yyyy-MM-dd hh:mm") + " (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaLectura()) + " días)")
                        + "\", \"" + (sh.getSolicitud().getFechaProgramada() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaProgramada(), "yyyy-MM-dd") + " (" + UtilFile.getDias(sh.getSolicitud().getFechaIngresoTicket(), sh.getSolicitud().getFechaProgramada()) + " días)")
                        + "\", \"" + (sh.getSolicitud().getFechaAtencion() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaAtencion(), "yyyy-MM-dd") + " (" + UtilFile.getDias(sh.getSolicitud().getFechaIngresoTicket(), sh.getSolicitud().getFechaAtencion()) + " días)")
                        + "\", \"" + sh.getSolicitud().getCondominio().getClave() + "-" + sh.getSolicitud().getTipoServicio().getClave() + "-" + formatter.format(sh.getSolicitud().getConsecutivo())
                        //                            + "\", \"" + (d.getFechaNotificacionCliente() == null ? "" : UtilFile.dateToString(d.getFechaNotificacionCliente(), "yyyy-MM-dd hh:mm") + " (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaNotificacionCliente()) + " días)")

                        + "\", \"" + sh.getUsuarioRegistra()
                        //                        + "\", \"" + sh.getSolicitud().getSolicitante()
                        + "\", \"" + sh.getComentario()
                        + "\", \"" + sh.getUsuario().getUsuario()
                        + "\", \"<a class='fancybox fancybox.iframe' href='/solicitudes/detalleSolicitudes.action?solicitud.id=" + sh.getSolicitud().getId() + "'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-pencil  fa-stack-1x fa-inverse'></i></span></a>"
                        + "\", \"<span data-toggle='tooltip' data-placement='top' title='" + colorMasTooltip[1] + "' style='color:" + colorMasTooltip[0] + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span></a>"
                        + " \"]");
            }
        } else if (isAdminCondominio()) {
            for (SolicitudHistorial sh : lista) {
                String[] colorMasTooltip = new String[2];
                if (sh.getSolicitud().getEstadoSolicitud().getId() == 4) {
                    colorMasTooltip[0] = "#cef9ff";
                    colorMasTooltip[1] = "Cancelado";
                } else {
                    colorMasTooltip = bo.getColor(sh.getSolicitud().getUmbral(), sh.getSolicitud().getFechaIngresoTicket(), sh.getSolicitud().getFechaAtencion());
                }
                getJsonResult().add("[\"" + sh.getSolicitud().getCondominio().getNombre()
                        + "\", \"" + sh.getSolicitud().getTipoServicio().getNombre()
                        + "\", \"" + sh.getSolicitud().getEstadoSolicitud().getNombre()
                        + "\", \"" + (sh.getSolicitud().getFechaIngresoTicket() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaIngresoTicket(), "yyyy-MM-dd hh:mm aa"))
                        //                            + "\", \"" + (d.getFechaLectura() == null ? "" : UtilFile.dateToString(d.getFechaLectura(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaLectura()) + " días)")
                        + "\", \"" + (sh.getSolicitud().getFechaProgramada() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaProgramada(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(sh.getSolicitud().getFechaIngresoTicket(), sh.getSolicitud().getFechaProgramada()) + " días)")
                        + "\", \"" + (sh.getSolicitud().getFechaAtencion() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaAtencion(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(sh.getSolicitud().getFechaIngresoTicket(), sh.getSolicitud().getFechaAtencion()) + " días)")
                        + "\", \"" + sh.getSolicitud().getCondominio().getClave() + "-" + sh.getSolicitud().getTipoServicio().getClave() + "-" + formatter.format(sh.getSolicitud().getConsecutivo())
                        //                            + "\", \"" + (d.getFechaNotificacionCliente() == null ? "" : UtilFile.dateToString(d.getFechaNotificacionCliente(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaNotificacionCliente()) + " días)")

                        + "\", \"" + sh.getUsuarioRegistra()
                        //                        + "\", \"" + sh.getSolicitud().getSolicitante()
                        + "\", \"" + sh.getComentario()
                        + "\", \"" + sh.getUsuario().getUsuario()
                        + "\", \"<a class='fancybox fancybox.iframe' href='/solicitudes/detalleSolicitudes.action?solicitud.id=" + sh.getSolicitud().getId() + "'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-pencil  fa-stack-1x fa-inverse'></i></span></a>"
                        + "\", \"<span data-toggle='tooltip' data-placement='top' title='" + colorMasTooltip[1] + "' style='color:" + colorMasTooltip[0] + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span></a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String buscaPermisos() {
        SolicitudesBO bo = new SolicitudesBO(getDaos());
        if (pkCondominio == null) {
            getJsonResult().add("");
        } else {

            List<SolicitudesPermisos> permisos = getDaos().getSolicitudesPermisosDao().findBy(pkCondominio, pkTipo, pkTipoPermiso, pkUsuario);
            if (permisos != null && !permisos.isEmpty()) {
                for (SolicitudesPermisos p : permisos) {

                    getJsonResult().add("[\"" + p.getCondominio().getNombre()
                            + "\", \"" + p.getTipoServicio().getNombre()
                            + "\", \"" + (p.getPermiso().equals("a") ? "Atender" : "Ingresar")
                            + "\", \"" + p.getUsuario().getUsuario()
                            + "\", \"<a href='#' onclick='eliminar(" + p.getId() + ")'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-trash-o  fa-stack-1x fa-inverse'></i></span></a>"
                            + " \"]");
                }

            }
        }
        return SUCCESS_JSON;
    }

    public String agregaPermiso() {

        if (pkCondominio == null || pkTipo == null || pkTipoPermiso == null || pkUsuario == null
                || pkCondominio.equals("") || pkTipo.equals("") || pkTipoPermiso.equals("") || pkUsuario.equals("")) {
            return SUCCESS_JSON;
        }
        SolicitudesPermisos permiso = new SolicitudesPermisos();
        permiso.setCondominio(new Condominio(pkCondominio));
        permiso.setPermiso(pkTipoPermiso);
        permiso.setTipoServicio(new SolicitudesTipoServicio(pkTipo));
        permiso.setUsuario(new Usuario(pkUsuario));

        try {
            List<SolicitudesPermisos> permisos = getDaos().getSolicitudesPermisosDao().findBy(pkCondominio, pkTipo, pkTipoPermiso, pkUsuario);
            if (permisos == null || permisos.isEmpty()) {
                getDaos().getSolicitudesPermisosDao().save(permiso);
            } else {
                getJsonResult().add("[\"El permiso especificado ya existe, por lo cual no fué agregado.\"]");
            }
        } catch (Exception e) {
            e.printStackTrace();
            getJsonResult().add("[\"Hubo un problema al tratar de insertar el permiso, por lo cual no fué agregado.\"]");
        }
        getJsonResult().add("[\"El permiso especificado fue agregado exitósamente.\"]");
        return SUCCESS_JSON;
    }

    /**
     * Version 360 (varios tipos de servicio en una transacción)
     *
     * @return
     */
    public String agregaPrivilegio() {

        if (pkCondominio == null || pkTipoServicios == null || pkTipoPermiso == null || pkUsuario == null
                || pkCondominio.equals("") || pkTipoServicios.equals("") || pkTipoPermiso.equals("") || pkUsuario.equals("")) {
            return SUCCESS_JSON;
        }

//        System.out.println("pkTipoServicios" + pkTipoServicios);
        String[] listaServicios = pkTipoServicios.split(",");
        for (String servicio : listaServicios) {
            pkTipo = new Long(servicio.trim());
            SolicitudesPermisos permiso = new SolicitudesPermisos();
            permiso.setCondominio(new Condominio(pkCondominio));
            permiso.setPermiso(pkTipoPermiso);
            permiso.setTipoServicio(new SolicitudesTipoServicio(pkTipo));
            permiso.setUsuario(new Usuario(pkUsuario));

            try {
                List<SolicitudesPermisos> permisos = getDaos().getSolicitudesPermisosDao().findBy(pkCondominio, pkTipo, pkTipoPermiso, pkUsuario);
                if (permisos == null || permisos.isEmpty()) {
                    getDaos().getSolicitudesPermisosDao().save(permiso);
                } else {
                    getJsonResult().add("[\"El permiso especificado ya existe, por lo cual no fué agregado.\"]");
                }
            } catch (Exception e) {
                e.printStackTrace();
                getJsonResult().add("[\"Hubo un problema al tratar de insertar el permiso, por lo cual no fué agregado.\"]");
            }
        }
        getJsonResult().add("[\"El permiso especificado fue agregado exitósamente.\"]");
        return SUCCESS_JSON;
    }

    public String eliminaPermiso() {

        if (pkPermiso == null || pkPermiso.equals("")) {
            return SUCCESS_JSON;
        }
        SolicitudesPermisos permiso = getDaos().getSolicitudesPermisosDao().findById(pkPermiso);
        if (permiso != null) {
            getDaos().getSolicitudesPermisosDao().delete(permiso);
        }

        return SUCCESS_JSON;
    }

    public Long getPkTorre() {
        return pkTorre;
    }

    public void setPkTorre(Long pkTorre) {
        this.pkTorre = pkTorre;
    }

    public Long getPkCondominio() {
        return pkCondominio;
    }

    public void setPkCondominio(Long pkCondominio) {
        this.pkCondominio = pkCondominio;
    }

    public Long getPkDepartamento() {
        return pkDepartamento;
    }

    public void setPkDepartamento(Long pkDepartamento) {
        this.pkDepartamento = pkDepartamento;
    }

    public Long getPkTipo() {
        return pkTipo;
    }

    public void setPkTipo(Long pkTipo) {
        this.pkTipo = pkTipo;
    }

    public Long getPkEstado() {
        return pkEstado;
    }

    public void setPkEstado(Long pkEstado) {
        this.pkEstado = pkEstado;
    }

    public Long getPkUsuario() {
        return pkUsuario;
    }

    public void setPkUsuario(Long pkUsuario) {
        this.pkUsuario = pkUsuario;
    }

    public String getPkTipoPermiso() {
        return pkTipoPermiso;
    }

    public void setPkTipoPermiso(String pkTipoPermiso) {
        this.pkTipoPermiso = pkTipoPermiso;
    }

    public Long getPkPermiso() {
        return pkPermiso;
    }

    public void setPkPermiso(Long pkPermiso) {
        this.pkPermiso = pkPermiso;
    }

    public Long getPkTipoSolicitud() {
        return pkTipoSolicitud;
    }

    public void setPkTipoSolicitud(Long pkTipoSolicitud) {
        this.pkTipoSolicitud = pkTipoSolicitud;
    }

    public Long getPkTipoInmueble() {
        return pkTipoInmueble;
    }

    public void setPkTipoInmueble(Long pkTipoInmueble) {
        this.pkTipoInmueble = pkTipoInmueble;
    }

    public Long getPkAreaId() {
        return pkAreaId;
    }

    public void setPkAreaId(Long pkAreaId) {
        this.pkAreaId = pkAreaId;
    }

    public Boolean getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(Boolean busqueda) {
        this.busqueda = busqueda;
    }

    public Long getPkCategoria() {
        return pkCategoria;
    }

    public void setPkCategoria(Long pkCategoria) {
        this.pkCategoria = pkCategoria;
    }

    public Long getPkInmueble() {
        return pkInmueble;
    }

    public void setPkInmueble(Long pkInmueble) {
        this.pkInmueble = pkInmueble;
    }

    public String getPkTipoServicios() {
        return pkTipoServicios;
    }

    public void setPkTipoServicios(String pkTipoServicios) {
        this.pkTipoServicios = pkTipoServicios;
    }

}
