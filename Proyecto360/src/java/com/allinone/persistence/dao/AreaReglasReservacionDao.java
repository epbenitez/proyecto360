package com.allinone.persistence.dao;

import com.allinone.persistence.model.AreaReglasReservacion;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface AreaReglasReservacionDao extends DaoBase<AreaReglasReservacion,Long> {
    
    public List<AreaReglasReservacion> reglasPorArea(Long areaId);
}
