package com.allinone.actions.proyectos;

import com.allinone.actions.BaseAction;
import com.allinone.domain.Indicadores;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Proyecto;
import com.allinone.persistence.model.ProyectoEstatus;
import com.allinone.persistence.model.ProyectoTarea;
import com.allinone.persistence.model.Usuario;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class ProyectosAction extends BaseAction {

    public static final String DETALLE = "detalle";
    public static final String GUARDA = "guarda";
    public static final String TAREAS = "tareas";
    public static final String ERRORTAREAS = "errortareas";

    private List<Condominio> condominiolist = new ArrayList<Condominio>();
    private Proyecto proyecto = new Proyecto();
    private ProyectoTarea proyectoTarea = new ProyectoTarea();
    private Indicadores indicadores = new Indicadores();

    public String gestion() {
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

    public String detalle() {
        if (proyecto == null || proyecto.getId() == null || proyecto.getId().equals("")) {
            addActionError("No se ha proporcionado el dato de detalle");
            return SUCCESS;
        }

        proyecto = getDaos().getProyectoDao().findById(proyecto.getId());

        gestion();
        return DETALLE;
    }

    public String guardaDetalle() {
        if (proyecto == null || proyecto.getId() == null) {
            addActionError("No se encontró el proyecto seleccionada.");
            return INPUT;
        }
        Proyecto p = getDaos().getProyectoDao().findById(proyecto.getId());
        proyecto.setCondominio(p.getCondominio());
        proyecto.setFechaRegistro(p.getFechaRegistro());

        try {
            getDaos().getProyectoDao().update(proyecto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActionMessage("Se ha guardado correctamente el  proyecto.");
        return detalle();
    }

    public String alta() {
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

    public String guarda() {
        if (proyecto == null || proyecto.getCondominio() == null || proyecto.getCondominio().getId() == null
                || proyecto.getCategoria() == null || proyecto.getCategoria().getId() == null
                || proyecto.getTipo() == null || proyecto.getTipo().getId() == null
                || proyecto.getPrioridad() == null || proyecto.getPrioridad().getId() == null
                || proyecto.getEstatus() == null || proyecto.getEstatus().getId() == null
                || proyecto.getCuota() == null || proyecto.getCuota().getId() == null
                || proyecto.getVisible() == null
                || proyecto.getDescripcion() == null || proyecto.getDescripcion().isEmpty()) {
            addActionError("No se han proporcionado uno o más datos necesarios para generar su proyecto/tarea, por favor, verifique.");
            return GUARDA;
        }

        proyecto.setFechaRegistro(new Date());
//        proyecto.setFechaVencimiento(new Date());

        try {
            getDaos().getProyectoDao().save(proyecto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addActionMessage("Se ha guardado correctamente el nuevo proyecto.");
        return detalle();
    }

    public String tareas() {
        if (proyectoTarea.getId() != null) {
            proyectoTarea = getDaos().getProyectoTareaDao().findById(proyectoTarea.getId());
        }
        return SUCCESS;
    }

    public String guardaTareas() {
        if (proyecto == null || proyecto.getId() == null
                || proyectoTarea.getNombre().isEmpty()
                || proyectoTarea.getNota().isEmpty()
                || proyectoTarea.getResponsable().isEmpty()) {
            addActionError("No se proporcionó la información necesaria para ingresar la nueva tarea.");
            return ERRORTAREAS;
        }
        try {
            proyectoTarea.setProyecto(proyecto);
//            proyectoTarea.setFecha(new Date());
//            ProyectoEstatus estatus = new ProyectoEstatus();
//            estatus.setId(1L);
//            proyectoTarea.setEstatus(estatus);

            if (proyectoTarea.getId() != null) {
                getDaos().getProyectoTareaDao().update(proyectoTarea);
            } else {
                getDaos().getProyectoTareaDao().save(proyectoTarea);
            }
        } catch (Exception e) {
            addActionError("No se pudo ingresar la nueva tarea.");
            return TAREAS;
        }
        addActionMessage("Se ha guardado correctamente la nueva tarea.");
        return TAREAS;
    }

    public String reporte() {
        return gestion();
    }

    public String indicadores() {

        List<Proyecto> total = getDaos().getProyectoDao().getTotalProyectos();
        List<Proyecto> abiertos = getDaos().getProyectoDao().getProyectosAbiertos();
        List<Proyecto> vencidos = getDaos().getProyectoDao().getProyectosVencidos();
        List<Proyecto> enTiempo = getDaos().getProyectoDao().getProyectosEnTiempo();
        List<Proyecto> visibles = getDaos().getProyectoDao().getProyectosVisibles();

        indicadores.setTotal(total == null ? 0L : total.size());
        indicadores.setAbiertos(abiertos == null ? 0L : abiertos.size());
        indicadores.setVencidos(vencidos == null ? 0L : vencidos.size());
        indicadores.setEnTiempo(enTiempo == null ? 0L : enTiempo.size());
        indicadores.setVisibles(visibles == null ? 0L : visibles.size());
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>" +indicadores.getTotal());
        return gestion();
    }

    public List<Condominio> getCondominiolist() {
        return condominiolist;
    }

    public void setCondominiolist(List<Condominio> condominiolist) {
        this.condominiolist = condominiolist;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public ProyectoTarea getProyectoTarea() {
        return proyectoTarea;
    }

    public void setProyectoTarea(ProyectoTarea proyectoTarea) {
        this.proyectoTarea = proyectoTarea;
    }

    public Indicadores getIndicadores() {
        return indicadores;
    }

    public void setIndicadores(Indicadores indicadores) {
        this.indicadores = indicadores;
    }

}
