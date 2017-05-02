/**
* ALL IN ONE
* 
* 2015
**/
package com.allinone.persistence.dao;

import com.allinone.persistence.model.Menu;

/**
 *
 * @author Patricia Benitez 
 */
public interface MenuDao  extends DaoBase<Menu, Long> {

    public String getNombreModulo(String action);
}
