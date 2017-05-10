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
    public List<SolicitudesTipoArea> findByInmueble(Long tipoInmuebleId) {
        String jpql = "SELECT  s FROM SolicitudesTipoArea s WHERE s.tipoInmuebleSolicitud.id = ?1 ";
        List<SolicitudesTipoArea> list = executeQuery(jpql, tipoInmuebleId);
        return list == null || list.isEmpty() ? null : list;
    }
    
    @Override
    public List<SolicitudesTipoArea> findByServicio(Long tipoSolicitudId) {
        String jpql = "SELECT  s FROM SolicitudesTipoArea s WHERE s.tipoSolicitud.id = ?1 ";
        List<SolicitudesTipoArea> list = executeQuery(jpql, tipoSolicitudId);
        return list == null || list.isEmpty() ? null : list;
    }
    
    
    @Override
    public List<SolicitudesTipoArea> findByAreas(Long areaSolicitudId) {
        String jpql = "SELECT  s FROM SolicitudesTipoArea s WHERE s.areaSolicitud.id = ?1 ";
        List<SolicitudesTipoArea> list = executeQuery(jpql, areaSolicitudId);
        return list == null || list.isEmpty() ? null : list;
    }
    
    @Override
    public List<SolicitudesTipoArea> findByCategoria(Long categoriaSolicitudId) {
        String jpql = "SELECT  s FROM SolicitudesTipoArea s WHERE s.categoriaSolicitud.id = ?1 ";
        List<SolicitudesTipoArea> list = executeQuery(jpql, categoriaSolicitudId);
        return list == null || list.isEmpty() ? null : list;
    }
}
