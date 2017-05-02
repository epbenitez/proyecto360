package com.allinone.persistence.dao;

import com.allinone.persistence.model.Area;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface AreaDao extends DaoBase<Area,Long>{
 
    public List<Area> areasPorCondominioTorre(Long condominioId, Long torreId);
}
