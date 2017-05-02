/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.PaisDao;
import com.allinone.persistence.model.Pais;

/**
 *
 * @author Patricia Benitez
 */
public class PaisJpaDao extends JpaDaoBase<Pais, Long> implements PaisDao{

    public PaisJpaDao() {
        super(Pais.class);
    }
    
}
