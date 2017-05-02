package com.allinone.persistence.dao;

import com.allinone.persistence.model.AreaReservacion;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface AreaReservacionDao extends DaoBase<AreaReservacion,Long> {
    
    public List<AreaReservacion> reservacionPorDepartamento(Long departamentoId);
    
    public List<AreaReservacion> reservaciones(Long condominioId, Long torreId);
    
    public List<AreaReservacion> reservacionesExistentes(Date fecha, Long areaHorarioId);
}
