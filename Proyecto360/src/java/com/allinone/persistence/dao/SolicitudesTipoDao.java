
package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesTipo;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudesTipoDao extends DaoBase<SolicitudesTipo,Long>{
    
    public List<SolicitudesTipo> find(Long condominioId);

}
