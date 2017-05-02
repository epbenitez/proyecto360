package com.allinone.persistence.model;

/**
 * Esta interfaz representa las propiedades basicas que se necesitan de las entidades.
 *
 * @author Patricia Benitez
 * @version $Rev: 1165 $
 * @since   1.0
 */
public interface BaseEntity {

    /**
     * Obtiene el id de la Entidad.
     *
     * @return el valor de la variable id.
     */
    public Long getId();
    
    /**
     * Establece el valor del id de la Entidad.
     *
     * @param id
     */
    public void setId(Long id);

    @Override
    public String toString();

}
