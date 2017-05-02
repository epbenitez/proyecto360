package com.allinone.actions.areas;

import com.allinone.actions.BaseAction;
import com.allinone.persistence.model.Area;
import com.allinone.persistence.model.Condominio;
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
public class AreasAction extends BaseAction {

    public static final String DETALLE = "detalle";
    public static final String GUARDA = "guarda";

    private List<Condominio> condominios = new ArrayList<Condominio>();
    private List<Torre> torres = new ArrayList<Torre>();

    private Area area = new Area();

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
        if (!validaCampos(Boolean.TRUE)) {
            addActionError("No se han proporcionado uno o más datos necesarios para dar de alta la nueva área, por favor, verifique.");
            return GUARDA;
        }
        getDaos().getAreaDao().save(area);

        return detalle();
    }

    public String guardaDetalle() {
        if (!validaCampos(Boolean.FALSE)) {
            addActionError("No se han proporcionado uno o más datos necesarios para dar de alta la nueva área, por favor, verifique.");
            return GUARDA;
        }
        getDaos().getAreaDao().update(area);

        return detalle();
    }

    public String detalle() {
        //Datos que vienen del post
        if (area == null || area.getId() == null) {
            addActionError("No se encontró el área seleccionada.");
            return INPUT;
        }
        area = getDaos().getAreaDao().findById(area.getId());

        //Verificamos si se encontró el registro
        if (area == null || area.getId() == null) {
            addActionError("No se encontró la área seleccionada.");
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
            if (area == null
                    || area.getCondominio() == null || area.getCondominio().getId() == null
                    || area.getTorre() == null || area.getTorre().getId() == null
                    || area.getPersonasMax() == null
                    //                ||                area.getDepositoGarantia()
                    || area.getDiasReservaMin() == null
                    || area.getDiasReservaMax() == null
                    || area.getUnidad() == null
                    || area.getEventosPorDiaMax() == null
                    || area.getEventosPorMesMax() == null
                    || area.getEventosPorAnioMax() == null
                    || area.getCosto() == null
                    || area.getDepositoGarantia() == null
                    || area.getDiasAnticipacionCancelar() == null) {
                return Boolean.FALSE;
            }
        } else if (area == null
                || area.getId() == null
                || area.getPersonasMax() == null
                //                ||                area.getDepositoGarantia()
                || area.getDiasReservaMin() == null
                || area.getDiasReservaMax() == null
                || area.getUnidad() == null
                || area.getEventosPorDiaMax() == null
                || area.getEventosPorMesMax() == null
                || area.getEventosPorAnioMax() == null
                || area.getCosto() == null
                || area.getDepositoGarantia() == null
                || area.getDiasAnticipacionCancelar() == null) {
            return Boolean.FALSE;
        }
        addActionMessage("Registro insertado correctamente.");
        return Boolean.TRUE;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

}
