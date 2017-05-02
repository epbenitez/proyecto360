package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.ConceptoDao;
import com.allinone.persistence.model.Concepto;

/**
 *
 * @author Patricia Benitez 
 */
public class ConceptoJpaDao extends JpaDaoBase<Concepto,Long> implements ConceptoDao{

    public ConceptoJpaDao(){
        super(Concepto.class);
    }
}
