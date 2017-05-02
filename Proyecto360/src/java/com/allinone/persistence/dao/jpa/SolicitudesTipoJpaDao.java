package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesTipoDao;
import com.allinone.persistence.model.SolicitudesTipo;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesTipoJpaDao extends JpaDaoBase<SolicitudesTipo, Long> implements SolicitudesTipoDao {

    public SolicitudesTipoJpaDao() {
        super(SolicitudesTipo.class);
    }

    @Override
    public List<SolicitudesTipo> find(Long condominioId) {
        String jpql = "SELECT  s FROM SolicitudesTipo s ";
//        if (condominioId <= 66) {
//            jpql += "WHERE s.id <= 10 ";
//        } else {
//            jpql += "WHERE s.id > 10 ";
//        }
        List<SolicitudesTipo> list = executeQuery(jpql);
        return list == null || list.isEmpty() ? null : list;
    }
}
