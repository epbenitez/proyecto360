package com.allinone.persistence.dao;

import com.allinone.persistence.model.Proyecto;
import com.allinone.persistence.model.ProyectoTarea;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface ProyectoTareaDao extends DaoBase<ProyectoTarea,Long> {
    
    public List<ProyectoTarea> getTareas(Proyecto p);
    public ProyectoTarea getSiguienteTarea(Proyecto p);
    public List<ProyectoTarea> getProyectos(Proyecto proyecto);
}
