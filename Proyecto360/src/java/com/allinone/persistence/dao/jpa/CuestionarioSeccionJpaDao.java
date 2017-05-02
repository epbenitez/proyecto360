package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioSeccionDao;
import com.allinone.persistence.model.CuestionarioSeccion;

/**
 *
 * @author Tania G. Sánchez
 */
public class CuestionarioSeccionJpaDao extends JpaDaoBase<CuestionarioSeccion,Long> implements CuestionarioSeccionDao{
    public CuestionarioSeccionJpaDao() {
        super(CuestionarioSeccion.class);
    }
}