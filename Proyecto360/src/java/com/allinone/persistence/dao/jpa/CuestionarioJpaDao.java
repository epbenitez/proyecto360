package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioDao;
import com.allinone.persistence.model.Cuestionario;

/**
 *
 * @author Tania G. Sánchez
 */
public class CuestionarioJpaDao extends JpaDaoBase<Cuestionario,Long> implements CuestionarioDao {

    public CuestionarioJpaDao() {
        super(Cuestionario.class);
    }
}