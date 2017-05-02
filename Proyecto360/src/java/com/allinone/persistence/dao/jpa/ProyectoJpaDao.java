package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.ProyectoDao;
import com.allinone.persistence.model.Proyecto;
import com.allinone.util.Util;
import com.allinone.util.UtilFile;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class ProyectoJpaDao extends JpaDaoBase<Proyecto,Long> implements ProyectoDao {
 
    public ProyectoJpaDao(){
        super(Proyecto.class);
    }
    
    @Override
    public List<Proyecto> getProyectos(Proyecto proyecto) {

        StringBuilder jpql = new StringBuilder("SELECT  new com.allinone.persistence.model.Proyecto(p.id, p.condominio.nombre, p.tipo.nombre,p.prioridad.nombre, p.estatus.nombre, p.cuota.nombre, p.visible, ");
        jpql.append(" p.fechaRegistro, p.categoria.nombre, p.descripcion, p.fechaVencimiento,p.titulo) FROM Proyecto p ")
//                .append(" LEFT JOIN p.tareaSiguiente s on s.id = p.tareaSiguiente.id ")
                .append("WHERE 1=1 ");

        if (proyecto.getCondominio().getId() != null ) {
            jpql.append(" and p.condominio.id= ").append(proyecto.getCondominio().getId());
        }

        if (proyecto.getCategoria().getId() != null) {
            jpql.append(" and p.categoria.id = ").append(proyecto.getCategoria().getId());
        }

        if (proyecto.getTipo().getId() != null ) {
            jpql.append(" and p.tipo.id = ").append(proyecto.getTipo().getId());
        }

        if (proyecto.getPrioridad().getId() != null) {
            jpql.append(" and p.prioridad.id = ").append(proyecto.getPrioridad().getId());
        }

        if (proyecto.getEstatus().getId() != null ) {
            jpql.append(" and p.estatus.id = ").append(proyecto.getEstatus().getId());
        }

        List<Proyecto> proyectosList = executeQuery(jpql.toString());

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
    
    @Override
    public List<Proyecto> getTotalProyectos(){
        String jpql = "SELECT  new com.allinone.persistence.model.Proyecto(p.id) FROM Proyecto p ";
        List<Proyecto> proyectosList = executeQuery(jpql);

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
    
    @Override
    public List<Proyecto> getProyectosAbiertos() {

        StringBuilder jpql = new StringBuilder("SELECT  new com.allinone.persistence.model.Proyecto(p.id, p.condominio.nombre, p.tipo.nombre,p.prioridad.nombre, p.estatus.nombre, p.cuota.nombre, p.visible, ");
        jpql.append(" p.fechaRegistro, p.categoria.nombre, p.descripcion, p.fechaVencimiento) FROM Proyecto p WHERE  p.estatus.id = 1");
        List<Proyecto> proyectosList = executeQuery(jpql.toString());

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
    
    /**
     * Proyectos que ya haya pasado su fecha de vencimiento y que sigan en estatus "Abierto" (1)
     * @return 
     */
    @Override
    public List<Proyecto> getProyectosVencidos() {

        StringBuilder jpql = new StringBuilder("SELECT  new com.allinone.persistence.model.Proyecto(p.id, p.condominio.nombre, p.tipo.nombre,p.prioridad.nombre, p.estatus.nombre, p.cuota.nombre, p.visible, p.fechaRegistro, p.categoria.nombre, p.descripcion, p.fechaVencimiento) FROM Proyecto p ")
                .append("WHERE  p.fechaVencimiento < '").append(UtilFile.dateToString(new Date(), "yyyy-MM-dd")).append("' ")
                .append (" and p.estatus.id = 1");
        List<Proyecto> proyectosList = executeQuery(jpql.toString());

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
    
    @Override
    public List<Proyecto> getProyectosEnTiempo() {

        StringBuilder jpql = new StringBuilder("SELECT  new com.allinone.persistence.model.Proyecto(p.id, p.condominio.nombre, p.tipo.nombre,p.prioridad.nombre, p.estatus.nombre, p.cuota.nombre, p.visible, p.fechaRegistro, p.categoria.nombre, p.descripcion, p.fechaVencimiento) FROM Proyecto p ")
                .append("WHERE  p.fechaVencimiento < '").append(UtilFile.dateToString(new Date(), "yyyy-MM-dd")).append("' ")
                .append (" and p.estatus.id <> 1");
        List<Proyecto> proyectosList = executeQuery(jpql.toString());

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
    
    @Override
    public List<Proyecto> getProyectosVisibles() {

        String jpql = "SELECT  new com.allinone.persistence.model.Proyecto(p.id, p.condominio.nombre, p.tipo.nombre,p.prioridad.nombre, p.estatus.nombre, p.cuota.nombre, p.visible, p.fechaRegistro, p.categoria.nombre, p.descripcion, p.fechaVencimiento) FROM Proyecto p WHERE  p.visible = true ";
        List<Proyecto> proyectosList = executeQuery(jpql);

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
    
    @Override
    public List<Proyecto> getProyectosCuota() {

        String jpql = "SELECT  new com.allinone.persistence.model.Proyecto(p.id, p.condominio.nombre, p.tipo.nombre,p.prioridad.nombre, p.estatus.nombre, p.cuota.nombre, p.visible, p.fechaRegistro, p.categoria.nombre, p.descripcion, p.fechaVencimiento) FROM Proyecto p WHERE  p.cuota = 1 ";
        List<Proyecto> proyectosList = executeQuery(jpql);

        return proyectosList == null || proyectosList.isEmpty() ? null : proyectosList;
    }
}
