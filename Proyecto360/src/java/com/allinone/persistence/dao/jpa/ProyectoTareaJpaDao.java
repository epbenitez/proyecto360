package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.ProyectoTareaDao;
import com.allinone.persistence.model.Proyecto;
import com.allinone.persistence.model.ProyectoTarea;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class ProyectoTareaJpaDao extends JpaDaoBase<ProyectoTarea,Long> implements ProyectoTareaDao {
 
    public ProyectoTareaJpaDao(){
        super(ProyectoTarea.class);
    }
    
    @Override
    public List<ProyectoTarea> getTareas(Proyecto p){
        String jpql = "SELECT DISTINCT pt FROM ProyectoTarea pt WHERE  pt.proyecto.id = ?1 order by pt.fecha asc ";
        List<ProyectoTarea> lista = executeQuery(jpql, p.getId());
        return lista;
    }
    
     @Override
    public ProyectoTarea getSiguienteTarea(Proyecto p){
        String jpql = "SELECT DISTINCT pt FROM ProyectoTarea pt WHERE  pt.proyecto.id = ?1 and pt.estatus.id=1 order by pt.fecha asc";
        List<ProyectoTarea> lista = executeQuery(jpql, p.getId());
        return lista==null || lista.isEmpty()?null:lista.get(0);
    }
    
     @Override
    public List<ProyectoTarea> getProyectos(Proyecto proyecto) {

        StringBuilder jpql = new StringBuilder("SELECT  new com.allinone.persistence.model.ProyectoTarea(pt.proyecto.id, pt.proyecto.condominio.nombre, pt.proyecto.tipo.nombre,pt.proyecto.prioridad.nombre, pt.proyecto.estatus.nombre, pt.proyecto.cuota.nombre, pt.proyecto.visible, ");
        jpql.append(" pt.proyecto.fechaRegistro, pt.proyecto.categoria.nombre, pt.proyecto.descripcion, pt.proyecto.fechaVencimiento,pt.proyecto.titulo, pt.fecha) FROM ProyectoTarea pt ")
//                .append(" LEFT JOIN pt.tareaSiguiente s on s.id = pt.tareaSiguiente.id ")
                .append("WHERE 1=1 ");

        if (proyecto.getCondominio().getId() != null ) {
            jpql.append(" and pt.proyecto.condominio.id= ").append(proyecto.getCondominio().getId());
        }

        if (proyecto.getCategoria().getId() != null) {
            jpql.append(" and pt.proyecto.categoria.id = ").append(proyecto.getCategoria().getId());
        }

        if (proyecto.getTipo().getId() != null ) {
            jpql.append(" and pt.proyecto.tipo.id = ").append(proyecto.getTipo().getId());
        }

        if (proyecto.getPrioridad().getId() != null) {
            jpql.append(" and pt.proyecto.prioridad.id = ").append(proyecto.getPrioridad().getId());
        }

        if (proyecto.getEstatus().getId() != null ) {
            jpql.append(" and pt.proyecto.estatus.id = ").append(proyecto.getEstatus().getId());
        }

        List<ProyectoTarea> proyectosList = executeQuery(jpql.toString());

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
}
