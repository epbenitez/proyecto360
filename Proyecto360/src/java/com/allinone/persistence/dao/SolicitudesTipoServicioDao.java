
package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesTipoServicio;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudesTipoServicioDao extends DaoBase<SolicitudesTipoServicio,Long>{
    
    public List<SolicitudesTipoServicio> find(Long condominioId);
    
    public List<SolicitudesTipoServicio> findBySolicitudTipoInmueble(Long inmuebleId);

}
