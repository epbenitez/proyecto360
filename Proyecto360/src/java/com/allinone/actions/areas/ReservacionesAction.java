package com.allinone.actions.areas;

import com.allinone.actions.BaseAction;
import com.allinone.persistence.model.AreaHorarios;
import com.allinone.persistence.model.AreaReservacion;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class ReservacionesAction extends BaseAction {

    public static final String DETALLE = "detalle";
    public static final String GUARDA = "guarda";

    private List<Condominio> condominios = new ArrayList<Condominio>();
    private List<Torre> torres = new ArrayList<Torre>();

    private AreaReservacion reservacion = new AreaReservacion();

    private Long condominioId;
    private Long torreId;

    List<AreaReservacion> reservaciones = new ArrayList<AreaReservacion>();

    public String administra() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());

        } else {
            condominios = getDaos().getCondominioDao().findAll();
        }
        return INPUT;
    }

    public String alta() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());

        } else {
            condominios = getDaos().getCondominioDao().findAll();
        }
        return INPUT;
    }

    public String guarda() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());

        } else {
            condominios = getDaos().getCondominioDao().findAll();
        }

        if (!validaCampos(Boolean.TRUE)) {
            return GUARDA;
        }
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        reservacion.setUsuario(u);
        reservacion = getDaos().getAreaReservacionDao().save(reservacion);

        return detalle();
    }

    public String guardaDetalle() {
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());

        } else {
            condominios = getDaos().getCondominioDao().findAll();
        }

        if (!validaCampos(Boolean.FALSE)) {
            return GUARDA;
        }
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        reservacion.setUsuario(u);
        reservacion = getDaos().getAreaReservacionDao().update(reservacion);

        return detalle();
    }

    public String detalle() {
        //Datos que vienen del post
        if (reservacion == null || reservacion.getId() == null) {
            addActionError("No se encontró la reservación seleccionada.");
            return INPUT;
        }
        reservacion = getDaos().getAreaReservacionDao().findById(reservacion.getId());

        //Verificamos si se encontró el registro
        if (reservacion == null || reservacion.getId() == null) {
            addActionError("No se encontró la reservación seleccionada.");
            return INPUT;
        }

        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            DepartamentoUsuario d = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            condominios.add(d.getDepartamento().getCondominio());
            torres.add(d.getDepartamento().getTorre());

        } else {
            condominios = getDaos().getCondominioDao().findAll();
        }

        return DETALLE;
    }

    private Boolean validaCampos(Boolean nuevo) {
        if (nuevo) {
            if (reservacion == null
                    || reservacion.getDepartamento() == null
                    || reservacion.getDepartamento().getId() == null
                    || reservacion.getAreaHorario() == null
                    || reservacion.getAreaHorario().getId() == null
                    || reservacion.getFecha() == null
                    || reservacion.getPersonas() == null || reservacion.getPersonas().equals("") //                    || reservacion.getComentario() == null
                    ) {

                addActionError("No se han proporcionado uno o más datos necesarios para dar de alta la nueva área, por favor, verifique.");
                return Boolean.FALSE;
            }

            //Buscamos si ya está ocupado ese horario en esa fecha
            List<AreaReservacion> existentes = getDaos().getAreaReservacionDao().reservacionesExistentes(reservacion.getFecha(), reservacion.getAreaHorario().getId());

            if (existentes != null && existentes.size() > 0) {
                addActionError("Ese horario ya se encuentra ocupado. Por favor, elija otra fecha u otro horario.");
                return Boolean.FALSE;
            }
        } else if (reservacion == null
                || reservacion.getDepartamento() == null
                || reservacion.getDepartamento().getId() == null
                || reservacion.getPersonas() == null || reservacion.getPersonas().equals("") //                    || reservacion.getComentario() == null
                ) {

            addActionError("No se han proporcionado uno o más datos necesarios para dar de alta la nueva área, por favor, verifique.");
            return Boolean.FALSE;
        }

        AreaHorarios horario = getDaos().getAreaHorariosDao().findById(reservacion.getAreaHorario().getId());
        reservacion.setAreaHorario(horario);
        Departamento d = getDaos().getDepartamentoDao().findById(reservacion.getDepartamento().getId());
        reservacion.setDepartamento(d);
        addActionMessage("Registro insertado correctamente.");
        return Boolean.TRUE;
    }

    public String calendario() {
        if (condominioId == null || condominioId.equals("")
                || torreId == null || torreId.equals("")) {
            addActionError("No se especificó condominio ni torre.");
            return INPUT;
        }

        reservaciones = getDaos().getAreaReservacionDao().reservaciones(condominioId, torreId);

        return INPUT;
    }

    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public List<Torre> getTorres() {
        return torres;
    }

    public void setTorres(List<Torre> torres) {
        this.torres = torres;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }

    public Long getTorreId() {
        return torreId;
    }

    public void setTorreId(Long torreId) {
        this.torreId = torreId;
    }

    public List<AreaReservacion> getReservaciones() {
        return reservaciones;
    }

    public void setReservaciones(List<AreaReservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public AreaReservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(AreaReservacion reservacion) {
        this.reservacion = reservacion;
    }

}
