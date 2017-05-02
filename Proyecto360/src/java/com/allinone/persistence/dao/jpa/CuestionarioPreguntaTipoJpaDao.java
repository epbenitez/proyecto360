package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioPreguntaTipoDao;
import com.allinone.persistence.model.CuestionarioPreguntaTipo;

/**
 *
 * @author Tania G. Sánchez
 */
public class CuestionarioPreguntaTipoJpaDao extends JpaDaoBase<CuestionarioPreguntaTipo,Long> implements CuestionarioPreguntaTipoDao{
    public CuestionarioPreguntaTipoJpaDao() {
        super(CuestionarioPreguntaTipo.class);
    }
}