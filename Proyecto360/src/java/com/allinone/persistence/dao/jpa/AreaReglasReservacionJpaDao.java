package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.AreaReglasReservacionDao;
import com.allinone.persistence.model.AreaReglasReservacion;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AreaReglasReservacionJpaDao extends JpaDaoBase<AreaReglasReservacion,Long> implements AreaReglasReservacionDao {
    
    public AreaReglasReservacionJpaDao(){
        super(AreaReglasReservacion.class);
    }
    
    @Override
    public List<AreaReglasReservacion> reglasPorArea(Long areaId) {
        String jpql = "select   a  from AreaReglasReservacion a "
                + "where a.area.id = ?1  ";

        List<AreaReglasReservacion> reglas = executeQuery(jpql, areaId);

        return reglas;
    }
}
