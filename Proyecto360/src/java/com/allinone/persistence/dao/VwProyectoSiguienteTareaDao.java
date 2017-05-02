package com.allinone.persistence.dao;

import com.allinone.persistence.model.Proyecto;
import com.allinone.persistence.model.VwProyectoSiguienteTarea;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface VwProyectoSiguienteTareaDao extends DaoBase<VwProyectoSiguienteTarea,Long>{
    
    public List<VwProyectoSiguienteTarea> getProyectos(Proyecto proyecto);
    
}
