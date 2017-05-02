/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.ConfiguracionDao;
import com.allinone.persistence.model.Configuracion;

/**
 *
 * @author Patricia Benitez
 */
public class ConfiguracionJpaDao  extends JpaDaoBase<Configuracion, Long> implements ConfiguracionDao {

    public ConfiguracionJpaDao() {
        super(Configuracion.class);
    }


}
