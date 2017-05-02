package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesTipoAreaDao;
import com.allinone.persistence.model.SolicitudesTipoArea;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class SolicitudesTipoAreaJpaDao extends JpaDaoBase<SolicitudesTipoArea,Long> implements SolicitudesTipoAreaDao {
    
    public SolicitudesTipoAreaJpaDao(){
        super(SolicitudesTipoArea.class);
    }
    
    @Override
    public List<SolicitudesTipoArea> find(Long tipoSolicitudId) {
        String jpql = "SELECT  s FROM SolicitudesTipoArea s WHERE s.tipoSolicitud.id = ?1 ";
        List<SolicitudesTipoArea> list = executeQuery(jpql, tipoSolicitudId);
        return list == null || list.isEmpty() ? null : list;
    }
}
