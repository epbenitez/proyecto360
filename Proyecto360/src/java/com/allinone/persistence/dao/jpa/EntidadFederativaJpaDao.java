package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EntidadFederativaDao;
import com.allinone.persistence.model.EntidadFederativa;
import java.util.List;

/**
 * Implementación de las operaciones CRUD de las entidades federativas.
 *
 * @author Patricia Benitez
 * @version $Rev: 1169 $
 * @since   1.0
 */
public class EntidadFederativaJpaDao extends JpaDaoBase<EntidadFederativa, Long> implements EntidadFederativaDao {

    /**
     * Crea una instancia de una <code>EntidadFederativaJpaDao</code>.
     */
    public EntidadFederativaJpaDao() {
        super(EntidadFederativa.class);
    }

    /**
     * Obtiene las entidad federativa por nombre
     * @param nombre Nombre de la Entidad Federativa
     * @return Entidad Federativa
     */
    @Override
    public EntidadFederativa findByNombre(String nombre) {
        String jpql = "SELECT estado FROM EntidadFederativa estado WHERE estado.nombre = ?1";
        List<EntidadFederativa> estado = executeQuery(jpql, nombre);

        if (estado != null && estado.size() == 1) {
            return estado.get(0);
        } else {
            return null;
        }
    }

}