package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.AreaDao;
import com.allinone.persistence.model.Area;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AreaJpaDao extends JpaDaoBase<Area,Long> implements AreaDao {
 
    public AreaJpaDao(){
        super(Area.class);
    }
    
    @Override
    public List<Area> areasPorCondominioTorre(Long condominioId, Long torreId) {
        String jpql = "select   a  from Area a "
                + "where a.condominio.id = ?1 and a.torre.id= ?2 ";

        List<Area> cargos = executeQuery(jpql, condominioId, torreId);

        return cargos;
    }
}
