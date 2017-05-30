
package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesUmbrales;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudesUmbralesDao extends DaoBase<SolicitudesUmbrales,Long> {

    public SolicitudesUmbrales findUmbral(Long condominioId, Long tipoServicioId);
}
