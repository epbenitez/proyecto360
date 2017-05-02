
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesEstadoDao;
import com.allinone.persistence.model.SolicitudesEstado;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesEstadoJpaDao extends JpaDaoBase<SolicitudesEstado,Long> implements SolicitudesEstadoDao{

    public SolicitudesEstadoJpaDao(){
        super(SolicitudesEstado.class);
    }
}
