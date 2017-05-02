package com.allinone.persistence.dao;

import com.allinone.persistence.model.AreaHorarios;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface AreaHorariosDao extends DaoBase<AreaHorarios,Long> {
    public List<AreaHorarios> horarioPorArea(Long areaId);
    public List<AreaHorarios> reservaciones(Long condominioId, Long torreId);
}
