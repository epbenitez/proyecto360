package com.allinone.persistence.dao.jpa;


import com.allinone.persistence.dao.AbonoTipoDao;
import com.allinone.persistence.model.AbonoTipo;


/**
 *
 * @author Patricia Benítez
 */
public class AbonoTipoJpaDao extends JpaDaoBase<AbonoTipo,Long> implements AbonoTipoDao{
    
    public AbonoTipoJpaDao(){
        super(AbonoTipo.class);
    }

}
