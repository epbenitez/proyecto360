package com.allinone.util;

import java.util.*;
/**
 * 
 * @author Patricia Benitez
 * @param <E>
 */
abstract public class ElementoGeografico<E extends ElementoGeografico> {

    /**
     * Mapa de subelementos que conforman este elemento.
     */
    private Map<Long, E> elementos;

    /**
     * Obtiene la llave de identificacion del elemento.
     */
    abstract public Long getId();

    /**
     * Inicializa el mapa de subelementos.
     */
    public void init() {
        elementos = new LinkedHashMap<Long, E>();
    }

    /**
     * Agrega un subelemento, siempre y cuando no exista con anterioridad.
     *
     * @param elem El elemento a agregar.
     */
    public void agregaElemento(E elem) {
        if (elem != null) {
            Long pk = elem.getId();
            if (!elementos.containsKey(pk)) {
                elementos.put(pk, elem);
            }
        }
    }

    /**
     * Obtiene la lista de los sublementos en el orden que se registraron.
     *
     * @return Los subelementos.
     */
    public Collection<E> getElementos() {
        return Collections.unmodifiableCollection(elementos.values());
    }

}