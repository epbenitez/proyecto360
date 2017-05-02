package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.AreaReservacionDao;
import com.allinone.persistence.model.AreaReservacion;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AreaReservacionJpaDao extends JpaDaoBase<AreaReservacion,Long> implements AreaReservacionDao{
    
    public AreaReservacionJpaDao(){
        super(AreaReservacion.class);
    }
    
    @Override
    public List<AreaReservacion> reservacionPorDepartamento(Long departamentoId) {
        String jpql = "select   a  from AreaReservacion a "
                + "where a.departamento.id = ?1  ";

        List<AreaReservacion> reservaciones = executeQuery(jpql, departamentoId);

        return reservaciones;
    }
    
    @Override
    public List<AreaReservacion> reservaciones(Long condominioId, Long torreId) {
        String jpql = "select   a  from AreaReservacion a "
                + "where a.departamento.condominio.id = ?1 "
                + " and a.departamento.torre.id = ?2 ";

        List<AreaReservacion> reservaciones = executeQuery(jpql, condominioId, torreId);

        return reservaciones;
    }
    
    @Override
    public List<AreaReservacion> reservacionesExistentes(Date fecha, Long areaHorarioId) {
        String jpql = "select   a  from AreaReservacion a "
                + "where a.areaHorario.id = ?1 "
                + " and a.fecha = ?2 ";

        List<AreaReservacion> reservaciones = executeQuery(jpql, areaHorarioId, fecha);

        return reservaciones;
    }
}
