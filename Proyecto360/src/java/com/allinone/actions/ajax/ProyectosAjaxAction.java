package com.allinone.actions.ajax;

import com.allinone.business.ProyectosBO;
import com.allinone.persistence.model.Proyecto;
import com.allinone.persistence.model.ProyectoTarea;
import com.allinone.persistence.model.VwProyectoSiguienteTarea;
import com.allinone.util.Util;
import com.allinone.util.UtilFile;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class ProyectosAjaxAction extends JSONAjaxAction {

    public Proyecto proyecto = new Proyecto();
    public Integer indiceId;

    public ProyectoTarea proyectoTarea = new ProyectoTarea();

    public String busqueda() {

        if (proyecto.getCondominio().getId() == null //                ||
                //                proyecto.getCategoria().getId()==null ||
                //                proyecto.getTipo().getId()==null
                ) {
            getJsonResult().add("[\"No se ha podido realizar esta consulta, pues no se proporcionó la información necesaria.\"]");
            return SUCCESS_JSON;
        }

//        List<Proyecto> proyectosList = getDaos().getProyectoDao().getProyectos(proyecto);

        List<VwProyectoSiguienteTarea> proyectosList = getDaos().getVwProyectoSiguienteTareaDao().getProyectos(proyecto);

        if (proyectosList != null && !proyectosList.isEmpty()) {
            int i = 0;
            String[] colorMasTooltip = new String[2];
            ProyectosBO bo = new ProyectosBO();
            for (VwProyectoSiguienteTarea pt : proyectosList) {
//                proximaTarea = getDaos().getProyectoTareaDao().getSiguienteTarea(p);
//                diasProximaTarea = pt.getFecha() == null ? 0 : Util.numeroDeDias( new Date(),pt.getFecha());
                colorMasTooltip = bo.getColor(pt.getDiasRestantes(),pt.getEstatus());
                getJsonResult().add("[\"" + (++i)
                        + "\", \""+ UtilFile.dateToString(pt.getFechaVencimiento(), "yyyy-MM-dd")
                        + "\", \"" + pt.getTitulo()
                        + "\", \"" + pt.getCategoria()
                        + "\", \"" + pt.getTipo()
                        + "\", \"" + pt.getPrioridad()
                        + "\", \"" + pt.getCuota()
                        + "\", \"" + (pt.getVisible() ? "Si" : "No")
                        + "\", \"" + pt.getEstatus()
                        //                        + "\", \"" + (pt.getProyecto().getTareaSiguiente()==null?"":pt.getProyecto().getTareaSiguiente().getNombre())
                        //                        + "\", \"" + UtilFile.dateToString(pt.getProyecto().getFechaRegistro(), "yyyy-MM-dd")
                        + "\", \"" + (pt.getFechaSigTarea()== null ? "-" : UtilFile.dateToString(pt.getFechaSigTarea(), "yyyy-MM-dd"))
                        + "\", \"" + pt.getDiasRestantes()
                        + "\", \"" + (pt.getFechaSigTarea() == null ? 0 : Util.numeroDeDias( new Date(),pt.getFechaSigTarea()))
                        + "\", \"<span data-toggle='tooltip' data-placement='top' title='" + colorMasTooltip[1] + "' style='color:" + colorMasTooltip[0] + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span></a>"
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/proyectos/detalleProyectos.action?proyecto.id=" + pt.getId() + "'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-eye fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/proyectos/tareasProyectos.action?proyecto.id=" + pt.getId() + "'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-tasks fa-stack-1x fa-inverse'></i>  "
                        + "</span>"
                        + "</a>"
                        + "\", \"" + pt.getDescripcion()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String tareas() {
        if (proyecto == null || proyecto.getId() == null) {
            getJsonResult().add("[\"No se encontraron datos"
                    + "\", \""
                    + "\", \""
                    + "\", \""
                    + "\", \""
                    + " \"]");
            return SUCCESS_JSON;
        }
        List<ProyectoTarea> tareasList = getDaos().getProyectoTareaDao().getTareas(proyecto);
        if (tareasList != null && !tareasList.isEmpty()) {
            for (ProyectoTarea p : tareasList) {
                getJsonResult().add("[\"" + p.getAccion()
                        + "\", \"" + p.getResponsable()
                        + "\", \"" + p.getNota()
                        + "\", \"" + UtilFile.dateToString(p.getFecha(), "yyyy-MM-dd")
                        + "\", \"" + p.getNombre()
                        + "\", \"" + p.getEstatus().getNombre()
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/proyectos/tareasProyectos.action?proyecto.id=" + p.getProyecto().getId() + "&proyectoTarea.id=" + p.getId() + "'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-pencil fa-stack-1x fa-inverse'></i>  "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a class='fancybox fancybox.iframe'  href='#' onclick=eliminaTarea(" + p.getId() + ")>"
                        + "<span class='fa-stack' style='color:red'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i>  "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaTarea() {
        if (proyectoTarea == null || proyectoTarea.getId() == null) {
            addActionError("No se proporcionó la información necesaria para eliminar la tarea.");
            return SUCCESS_JSON;
        }
        try {
            proyectoTarea = getDaos().getProyectoTareaDao().findById(proyectoTarea.getId());
            getDaos().getProyectoTareaDao().delete(proyectoTarea);
        } catch (Exception e) {
            addActionError("No se pudo eliminar la tarea.");
            return SUCCESS_JSON;
        }

        addActionMessage("Se ha eliminado correctamente la tarea.");
        return SUCCESS_JSON;
    }

    public String indices() {

        if (indiceId == null) {
            getJsonResult().add("[\"No se ha podido realizar esta consulta, pues no se proporcionó la información necesaria.\"]");
            return SUCCESS_JSON;
        }

        List<Proyecto> proyectosList;

        switch (indiceId) {
            case 1:
                proyectosList = getDaos().getProyectoDao().getProyectosAbiertos();
                break;
            case 2:
                proyectosList = getDaos().getProyectoDao().getProyectosVencidos();
                break;
            case 3:
                proyectosList = getDaos().getProyectoDao().getProyectosEnTiempo();
                break;
            default:
                proyectosList = getDaos().getProyectoDao().getProyectosVisibles();
                break;
        }

        if (proyectosList != null && !proyectosList.isEmpty()) {
            for (Proyecto p : proyectosList) {
                getJsonResult().add("[\"" + p.getDescripcion()
                        + "\", \"" + p.getCategoria().getNombre()
                        + "\", \"" + p.getTipo().getNombre()
                        + "\", \"" + p.getPrioridad().getNombre()
                        + "\", \"" + p.getEstatus().getNombre()
                        + "\", \"" + p.getCuota().getNombre()
                        + "\", \"" + (p.getVisible() ? "Si" : "No")
                        //                        + "\", \"" + (p.getTareaSiguiente()==null?"":p.getTareaSiguiente().getNombre())
                        + "\", \"" + UtilFile.dateToString(p.getFechaRegistro(), "yyyy-MM-dd")
                        + "\", \"" + p.getDiasTranscurridos()
                        + "\", \"" + UtilFile.dateToString(p.getFechaVencimiento(), "yyyy-MM-dd")
                        + "\", \"" + p.getDiasRestantes()
                        + "\", \"" + "Color"
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/proyectos/detalleProyectos.action?proyecto.id=" + p.getId() + "'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-eye fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/proyectos/tareasProyectos.action?proyecto.id=" + p.getId() + "'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-tasks fa-stack-1x fa-inverse'></i>  "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Integer getIndiceId() {
        return indiceId;
    }

    public void setIndiceId(Integer indiceId) {
        this.indiceId = indiceId;
    }

    public ProyectoTarea getProyectoTarea() {
        return proyectoTarea;
    }

    public void setProyectoTarea(ProyectoTarea proyectoTarea) {
        this.proyectoTarea = proyectoTarea;
    }

}
