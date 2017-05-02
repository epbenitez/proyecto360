package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.AreaHorariosDao;
import com.allinone.persistence.model.AreaHorarios;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AreaHorariosJpaDao extends JpaDaoBase<AreaHorarios,Long> implements AreaHorariosDao {
    
    public AreaHorariosJpaDao(){
        super(AreaHorarios.class);
    }
    
    @Override
    public List<AreaHorarios> horarioPorArea(Long areaId) {
        String jpql = "select   a  from AreaHorarios a "
                + "where a.area.id = ?1  ";

        List<AreaHorarios> horarios = executeQuery(jpql, areaId);

        return horarios;
    }
    
    @Override
    public List<AreaHorarios> reservaciones(Long condominioId, Long torreId){
        String jpql = "select   a  from AreaHorarios a "
                + "where a.area.condominio.id = ?1 and a.area.torre.id = ?2  ";

        List<AreaHorarios> horarios = executeQuery(jpql, condominioId, torreId);

        return horarios;
    }
    
}
