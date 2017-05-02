package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.VwProyectoSiguienteTareaDao;
import com.allinone.persistence.model.Proyecto;
import com.allinone.persistence.model.VwProyectoSiguienteTarea;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class VwProyectoSiguienteTareaJpaDao extends JpaDaoBase<VwProyectoSiguienteTarea,Long> implements VwProyectoSiguienteTareaDao {
    
    public VwProyectoSiguienteTareaJpaDao(){
        super(VwProyectoSiguienteTarea.class);
    }
    
    @Override
    public List<VwProyectoSiguienteTarea> getProyectos(Proyecto proyecto) {

        StringBuilder jpql = new StringBuilder("SELECT  p FROM VwProyectoSiguienteTarea p ")
//                .append(" LEFT JOIN p.tareaSiguiente s on s.id = p.tareaSiguiente.id ")
                .append("WHERE 1=1 ");

        if (proyecto.getCondominio().getId() != null ) {
            jpql.append(" and p.condominioId= ").append(proyecto.getCondominio().getId());
        }

        if (proyecto.getCategoria().getId() != null) {
            jpql.append(" and p.categoriaId = ").append(proyecto.getCategoria().getId());
        }

        if (proyecto.getTipo().getId() != null ) {
            jpql.append(" and p.tipoId = ").append(proyecto.getTipo().getId());
        }

        if (proyecto.getPrioridad().getId() != null) {
            jpql.append(" and p.prioridadId = ").append(proyecto.getPrioridad().getId());
        }

        if (proyecto.getEstatus().getId() != null ) {
            jpql.append(" and p.estatusId = ").append(proyecto.getEstatus().getId());
        }

        List<VwProyectoSiguienteTarea> proyectosList = executeQuery(jpql.toString());

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
}
