package com.allinone.actions.ajax;

import static com.allinone.actions.ajax.JSONAjaxAction.SUCCESS_JSON;
import com.allinone.business.SolicitudesBO;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesPermisos;
import com.allinone.persistence.model.SolicitudesTipo;
import com.allinone.persistence.model.SolicitudesTipoArea;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesAjaxAction extends JSONAjaxAction {

    private Long pkCondominio;
    private Long pkTorre;
    private Long pkDepartamento;
    private Long pkTipo;
    private Long pkEstado;

    private Long pkPermiso;
    private Long pkUsuario;
    private String pkTipoPermiso;

    private Long pkTipoSolicitud;

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

    public String getAreas() {
        List<SolicitudesTipoArea> lista = getDaos().getSolicitudesTipoAreaDao().find(pkTipoSolicitud);
        if (lista == null) {
        } else {
            for (SolicitudesTipoArea d : lista) {
                getJsonResult().add("[\"" + d.getAreaSolicitud().getId()
                        + "\", \"" + d.getAreaSolicitud().getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getTiposDeSol() {
        List<SolicitudesTipo> lista = getDaos().getSolicitudesTipoDao().find(pkCondominio);
        if (lista == null) {
        } else {
            for (SolicitudesTipo d : lista) {
                getJsonResult().add("[\"" + d.getId()
                        + "\", \"" + d.getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String busqueda() {
        NumberFormat formatter = new DecimalFormat("0000");
        SolicitudesBO bo = new SolicitudesBO(getDaos());
        List<SolicitudHistorial> lista;
        if (pkCondominio == null) {
            if (isAdministrator()) {
                lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, pkTorre, pkDepartamento, pkTipo, pkEstado);
            } else {
                getJsonResult().add("");
                return SUCCESS_JSON;
            }
        } else {

            if (pkTipo != null && pkTipo.toString().trim().length() > 0) {
                lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, pkTorre, pkDepartamento, pkTipo, pkEstado);
            } else if (isPropietario()) {
                lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, pkTorre, pkDepartamento, new ArrayList<SolicitudesTipo>(), pkEstado);
            } else {
                //Solo una lista de las solicitudes del tipo que tiene permiso en la tabla rmm_solicitudes_permisos
                Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
                List<SolicitudesPermisos> permisos = getDaos().getSolicitudesPermisosDao().findByUsuario(u.getId(), Boolean.TRUE);
                List<SolicitudesTipo> tipos = new ArrayList<SolicitudesTipo>();
                if (permisos != null) {
                    for (SolicitudesPermisos p : permisos) {
                        tipos.add(p.getTipoSolicitud());
                    }
                }
                lista = getDaos().getSolicitudHistorialDao().getSolicitudesHistorial(pkCondominio, pkTorre, pkDepartamento, tipos, pkEstado);
            }

            if (lista != null && !lista.isEmpty()) {
                lista = bo.getUmbrales(lista);
            }
        }

        if (lista == null) {
        } else if (isAdministrator() || isPropietario()) {
            for (SolicitudHistorial sh : lista) {
//                    Date fechaSolucion = d.getFechaSolucion() == null ? new Date() : d.getFechaSolucion();
                String[] colorMasTooltip = new String[2];
                if (sh.getSolicitud().getEstadoSolicitud().getId() == 4) {
                    colorMasTooltip[0] = "#cef9ff";
                    colorMasTooltip[1] = "Cancelado";
                } else {
                    colorMasTooltip = bo.getColor(sh.getSolicitud().getUmbral(), sh.getSolicitud().getFechaSolicitud(), sh.getSolicitud().getFechaSolucion());
                }

                getJsonResult().add("[\"" + sh.getSolicitud().getDepartamento().getCondominio().getNombre()
                        + "\", \"" + sh.getSolicitud().getDepartamento().getTorre().getNombre()
                        + "\", \"" + sh.getSolicitud().getDepartamento().getNombre()
                        + "\", \"" + sh.getSolicitud().getTipoSolicitud().getNombre()
                        + "\", \"" + sh.getSolicitud().getEstadoSolicitud().getNombre()
                        + "\", \"" + (sh.getSolicitud().getFechaSolicitud() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaSolicitud(), "yyyy-MM-dd hh:mm"))
                        //                            + "\", \"" + (d.getFechaLectura() == null ? "" : UtilFile.dateToString(d.getFechaLectura(), "yyyy-MM-dd hh:mm") + " (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaLectura()) + " días)")
                        //                            + "\", \"" + (d.getFechaCompromiso() == null ? "" : UtilFile.dateToString(d.getFechaCompromiso(), "yyyy-MM-dd hh:mm") + " (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaCompromiso()) + " días)")
                        + "\", \"" + (sh.getSolicitud().getFechaSolucion() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaSolucion(), "yyyy-MM-dd hh:mm") + " (" + UtilFile.getDias(sh.getSolicitud().getFechaSolicitud(), sh.getSolicitud().getFechaSolucion()) + " días)")
                        + "\", \"" + sh.getSolicitud().getDepartamento().getCondominio().getClave() + "-" + sh.getSolicitud().getTipoSolicitud().getClave() + "-" + formatter.format(sh.getSolicitud().getConsecutivo())
                        //                            + "\", \"" + (d.getFechaNotificacionCliente() == null ? "" : UtilFile.dateToString(d.getFechaNotificacionCliente(), "yyyy-MM-dd hh:mm") + " (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaNotificacionCliente()) + " días)")

                        + "\", \"" + sh.getUsuarioRegistra()
                        + "\", \"" + sh.getSolicitud().getSolicitante()
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
                    colorMasTooltip = bo.getColor(sh.getSolicitud().getUmbral(), sh.getSolicitud().getFechaSolicitud(), sh.getSolicitud().getFechaSolucion());
                }
                getJsonResult().add("[\"" + sh.getSolicitud().getDepartamento().getTorre().getNombre()
                        + "\", \"" + sh.getSolicitud().getDepartamento().getNombre()
                        + "\", \"" + sh.getSolicitud().getTipoSolicitud().getNombre()
                        + "\", \"" + sh.getSolicitud().getEstadoSolicitud().getNombre()
                        + "\", \"" + (sh.getSolicitud().getFechaSolicitud() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaSolicitud(), "yyyy-MM-dd hh:mm"))
                        //                            + "\", \"" + (d.getFechaLectura() == null ? "" : UtilFile.dateToString(d.getFechaLectura(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaLectura()) + " días)")
                        //                            + "\", \"" + (d.getFechaCompromiso() == null ? "" : UtilFile.dateToString(d.getFechaCompromiso(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaCompromiso()) + " días)")
                        + "\", \"" + (sh.getSolicitud().getFechaSolucion() == null ? "" : UtilFile.dateToString(sh.getSolicitud().getFechaSolucion(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(sh.getSolicitud().getFechaSolicitud(), sh.getSolicitud().getFechaSolucion()) + " días)")
                        + "\", \"" + sh.getSolicitud().getDepartamento().getCondominio().getClave() + "-" + sh.getSolicitud().getTipoSolicitud().getClave() + "-" + formatter.format(sh.getSolicitud().getConsecutivo())
                        //                            + "\", \"" + (d.getFechaNotificacionCliente() == null ? "" : UtilFile.dateToString(d.getFechaNotificacionCliente(), "yyyy-MM-dd") + "<br> (" + UtilFile.getDias(d.getFechaSolicitud(), d.getFechaNotificacionCliente()) + " días)")

                        + "\", \"" + sh.getUsuarioRegistra()
                        + "\", \"" + sh.getSolicitud().getSolicitante()
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
                            + "\", \"" + p.getTipoSolicitud().getNombre()
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
        permiso.setTipoSolicitud(new SolicitudesTipo(pkTipo));
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

}
