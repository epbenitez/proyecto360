package com.allinone.domain;

import com.allinone.persistence.model.EntidadFederativa;
import com.allinone.persistence.model.LocalidadColonia;
import com.allinone.persistence.model.MunicipioDelegacion;
import com.allinone.util.ElementoGeografico;

/**
 * Clase de dominio que agrupa los datos de código postal de una dirección
 * @author Patricia Benitez
 * @version $Rev: 1169 $
 * @since 1.0
 */
public class CodigoPostal extends ElementoGeografico<LocalidadColonia> implements Comparable<CodigoPostal> {

    /**
     * El codigo.
     */
    private Integer codigo;

    /**
     * El pais.
     */
//    private Pais pais;

    /**
     * La entidad.
     */
    private EntidadFederativa entidad;

    /**
     * El municipio o delegacion.
     */
    private MunicipioDelegacion muniDeleg;

    /**
     * Se construlle un código postal
     *
     * @param codigo
     */
    public CodigoPostal(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * Se construye con sus elementos base.
     */
    public CodigoPostal(Integer codigo, EntidadFederativa entidad, MunicipioDelegacion muniDeleg) {
        this.codigo = codigo;
//        this.pais = pais;
        this.entidad = entidad;
        this.muniDeleg = muniDeleg;
        init();
    }

    public Long getId() {
        return codigo.longValue();
    }

    /**
     * Extrae el codigo.
     *
     * @return El codigo.
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Extrae el pais.
     *
     * @return El pais.
     */
//    public Pais getPais() {
//        return pais;
//    }

    /**
     * Extrae la entidad.
     *
     * @return La entidad.
     */
    public EntidadFederativa getEntidad() {
        return entidad;
    }

    /**
     * Extrae el municipio o delegacion.
     *
     * @return El muniacionDeleg.
     */
    public MunicipioDelegacion getMuniDeleg() {
        return muniDeleg;
    }

    /**
     * Compara dos codigos para ordenarlos.
     *
     * @param otro El otro parametro contra el que hay que comparar.
     * @return El valor de la comparacion para poder ordenarlos.
     */
    public int compareTo(CodigoPostal otro) {
        return this.getId().intValue() - otro.getId().intValue();
    }

}