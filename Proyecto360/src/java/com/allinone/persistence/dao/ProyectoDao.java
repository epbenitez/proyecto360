package com.allinone.persistence.dao;

import com.allinone.persistence.model.Proyecto;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface ProyectoDao extends DaoBase<Proyecto,Long> {
    
    public List<Proyecto> getProyectos(Proyecto proyecto);
    public List<Proyecto> getTotalProyectos();
    public List<Proyecto> getProyectosAbiertos();
    public List<Proyecto> getProyectosVencidos();
    public List<Proyecto> getProyectosEnTiempo();
    public List<Proyecto> getProyectosVisibles();
    public List<Proyecto> getProyectosCuota();
    
}
