package com.allinone.persistence.dao;

import com.allinone.persistence.model.EntidadFederativa;

/**
 * Definición de las operaciones CRUD de las entidades federativas
 *
 * @author Patricia Benitez
 * @version $Rev: 801 $
 * @since   1.0
 */
public interface EntidadFederativaDao extends DaoBase<EntidadFederativa, Long> {

    public EntidadFederativa findByNombre(String nombre);

}