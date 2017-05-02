package com.allinone.actions.ajax;

import com.allinone.persistence.model.Area;
import com.allinone.persistence.model.AreaHorarios;
import com.allinone.persistence.model.AreaReglasReservacion;
import com.allinone.persistence.model.AreaReservacion;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AreasAjaxAction extends JSONAjaxAction {

    private Long condominioId;
    private Long torreId;
    private Long departamentoId;
    private Long areaId;
    private AreaHorarios horario;
    private AreaReglasReservacion regla;
    private AreaReservacion reservacion;

    public String getAreas() {

        if (condominioId == null || condominioId.equals("")
                || torreId == null || torreId.equals("")) {
            return SUCCESS_JSON;
        }
        List<Area> areas = getDaos().getAreaDao().areasPorCondominioTorre(condominioId, torreId);

        if (areas == null) {
        } else {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            for (Area a : areas) {
                getJsonResult().add("[\"" + a.getTorre().getNombre()
                        + "\", \"" + a.getNombre()
                        + "\", \"" + a.getPersonasMax()
                        + "\", \"" + (a.getSaldoMaxDeudor() == null ? "No" : "Si")
                        + "\", \"" + a.getDiasReservaMax()
                        + "\", \"" + a.getDiasReservaMin()
                        + "\", \"" + a.getUnidad()
                        + "\", \"$" + df.format(a.getCosto())
                        + "\", \"$" + df.format(a.getDepositoGarantia())
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/areas/detalleAreas.action?area.id=" + a.getId() + "' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-pencil fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getHorarios() {
        if (areaId == null || areaId.equals("")) {
            return SUCCESS_JSON;
        }
        List<AreaHorarios> horarios = getDaos().getAreaHorariosDao().horarioPorArea(areaId);

        if (horarios == null) {
        } else {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            for (AreaHorarios a : horarios) {
                getJsonResult().add("[\"" + a.getDia()
                        + "\", \"" + a.getHoraInicial()
                        + "\", \"" + a.getHoraFinal()
                        + "\", \"<a onclick='eliminaHorario(" + a.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String agregaHorario() {
        if (horario == null
                || horario.getArea() == null || horario.getArea().getId() == null
                || horario.getDia() == null || horario.getDia().length() == 0
                || horario.getHoraInicial() == null || horario.getHoraInicial().length() == 0
                || horario.getHoraFinal() == null || horario.getHoraFinal().length() == 0) {
            return ERROR_JSON;
        }

        try {
            getDaos().getAreaHorariosDao().save(horario);
            getJsonResult().add("[\"OK\"]");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String eliminaHorario() {
        if (horario == null
                || horario.getId() == null) {
            return ERROR_JSON;
        }

        try {
            horario = getDaos().getAreaHorariosDao().findById(horario.getId());
            getDaos().getAreaHorariosDao().delete(horario);
            getJsonResult().add("[\"OK\"]");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String getReglas() {
        if (areaId == null || areaId.equals("")) {
            return SUCCESS_JSON;
        }
        List<AreaReglasReservacion> reglas = getDaos().getAreaReglasReservacionDao().reglasPorArea(areaId);

        if (reglas == null) {
        } else {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            for (AreaReglasReservacion a : reglas) {
                getJsonResult().add("[\"" + a.getDescripcion()
                        + "\", \"<a onclick='eliminaRegla(" + a.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String agregaRegla() {
        if (regla == null
                || regla.getArea() == null || regla.getArea().getId() == null
                || regla.getDescripcion() == null || regla.getDescripcion().length() == 0) {
            return ERROR_JSON;
        }

        try {
            getDaos().getAreaReglasReservacionDao().save(regla);
            getJsonResult().add("[\"OK\"]");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String eliminaRegla() {
        if (regla == null
                || regla.getId() == null) {
            return ERROR_JSON;
        }

        try {
            regla = getDaos().getAreaReglasReservacionDao().findById(regla.getId());
            getDaos().getAreaReglasReservacionDao().delete(regla);
            getJsonResult().add("[\"OK\"]");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String getReservaciones() {

        if (condominioId == null || condominioId.equals("")
                || torreId == null || torreId.equals("")
                || departamentoId == null || departamentoId.equals("")) {
            return SUCCESS_JSON;
        }
        List<AreaReservacion> reservaciones = getDaos().getAreaReservacionDao().reservacionPorDepartamento(departamentoId);

        if (reservaciones == null) {
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            for (AreaReservacion a : reservaciones) {
                getJsonResult().add("[\"" + a.getAreaHorario().getArea().getNombre()
                        + "\", \"" + dateFormat.format(a.getFecha())
                        + "\", \"" + a.getAreaHorario().getHoraInicial()
                        + "\", \"" + a.getAreaHorario().getHoraFinal()
                        + "\", \"" + a.getPersonas()
                        + "\", \"" + a.getComentario()
                        + "\", \"<a class='fancybox fancybox.iframe' href='/areas/detalleReservaciones.action?reservacion.id=" + a.getId() + "' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-eye fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a onclick='eliminaReservacion(" + a.getId() + ")' >"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaReservaciones() {
        if (reservacion == null
                || reservacion.getId() == null) {
            return ERROR_JSON;
        }

        try {
            reservacion = getDaos().getAreaReservacionDao().findById(reservacion.getId());
            getDaos().getAreaReservacionDao().delete(reservacion);
            getJsonResult().add("[\"OK\"]");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }
        return SUCCESS_JSON;
    }

    public String getAreaPorReservar() {
        if (condominioId == null || condominioId.equals("")
                || torreId == null || torreId.equals("")) {
            return SUCCESS_JSON;
        }
        List<AreaHorarios> horarios = getDaos().getAreaHorariosDao().reservaciones(condominioId,torreId);

        for (AreaHorarios a : horarios) {
        getJsonResult().add("[\"" + a.getId()
                            + "\", \"" + a.getArea().getNombre() + " : " + a.getDia() + " : " + a.getHoraInicial()  + " : " + a.getHoraFinal()
                            + " \"]");
        }
        return SUCCESS_JSON;
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

    public AreaHorarios getHorario() {
        return horario;
    }

    public void setHorario(AreaHorarios horario) {
        this.horario = horario;
    }

    public AreaReglasReservacion getRegla() {
        return regla;
    }

    public void setRegla(AreaReglasReservacion regla) {
        this.regla = regla;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public AreaReservacion getReservacion() {
        return reservacion;
    }

    public void setReservacion(AreaReservacion reservacion) {
        this.reservacion = reservacion;
    }

}
