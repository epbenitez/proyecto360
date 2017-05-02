package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.TipoDepartamentoDao;
import com.allinone.persistence.model.TipoDepartamento;

/**
 *
 * @author Patricia Benitez 
 */
public class TipoDepartamentoJpaDao extends JpaDaoBase<TipoDepartamento,Long> implements TipoDepartamentoDao {

    public TipoDepartamentoJpaDao(){
        super(TipoDepartamento.class);
    }
}
