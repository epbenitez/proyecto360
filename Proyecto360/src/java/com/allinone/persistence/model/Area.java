package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author patriciabenitez
 */
@Entity
@Table(name = "ent_area")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Area  implements Serializable, BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Condominio condominio;
    private Torre torre;
    private Integer personasMax;
    private Float saldoMaxDeudor;
    private Integer diasReservaMax;
    private Integer diasReservaMin;
    private Integer unidad;
    private Integer eventosPorDiaMax;
    private Integer eventosPorMesMax;
    private Integer eventosPorAnioMax;
    private Double costo;
    private Double depositoGarantia;
    private Integer diasAnticipacionCancelar;
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public Torre getTorre() {
        return torre;
    }

    public void setTorre(Torre torre) {
        this.torre = torre;
    }

    public Integer getPersonasMax() {
        return personasMax;
    }

    public void setPersonasMax(Integer personasMax) {
        this.personasMax = personasMax;
    }

    public Float getSaldoMaxDeudor() {
        return saldoMaxDeudor;
    }

    public void setSaldoMaxDeudor(Float saldoMaxDeudor) {
        this.saldoMaxDeudor = saldoMaxDeudor;
    }

    public Integer getDiasReservaMax() {
        return diasReservaMax;
    }

    public void setDiasReservaMax(Integer diasReservaMax) {
        this.diasReservaMax = diasReservaMax;
    }

    public Integer getDiasReservaMin() {
        return diasReservaMin;
    }

    public void setDiasReservaMin(Integer diasReservaMin) {
        this.diasReservaMin = diasReservaMin;
    }

    public Integer getUnidad() {
        return unidad;
    }

    public void setUnidad(Integer unidad) {
        this.unidad = unidad;
    }

    public Integer getEventosPorDiaMax() {
        return eventosPorDiaMax;
    }

    public void setEventosPorDiaMax(Integer eventosPorDiaMax) {
        this.eventosPorDiaMax = eventosPorDiaMax;
    }

    public Integer getEventosPorMesMax() {
        return eventosPorMesMax;
    }

    public void setEventosPorMesMax(Integer eventosPorMesMax) {
        this.eventosPorMesMax = eventosPorMesMax;
    }

    public Integer getEventosPorAnioMax() {
        return eventosPorAnioMax;
    }

    public void setEventosPorAnioMax(Integer eventosPorAnioMax) {
        this.eventosPorAnioMax = eventosPorAnioMax;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getDepositoGarantia() {
        return depositoGarantia;
    }

    public void setDepositoGarantia(Double depositoGarantia) {
        this.depositoGarantia = depositoGarantia;
    }

    public Integer getDiasAnticipacionCancelar() {
        return diasAnticipacionCancelar;
    }

    public void setDiasAnticipacionCancelar(Integer diasAnticipacionCancelar) {
        this.diasAnticipacionCancelar = diasAnticipacionCancelar;
    }

    @Override
    public String toString() {
        return "Area{" + "id=" + id + ", nombre=" + nombre + ", condominio=" + condominio + ", torre=" + torre + ", personasMax=" + personasMax + ", saldoMaxDeudor=" + saldoMaxDeudor + ", diasReservaMax=" + diasReservaMax + ", diasReservaMin=" + diasReservaMin + ", unidad=" + unidad + ", eventosPorDiaMax=" + eventosPorDiaMax + ", eventosPorMesMax=" + eventosPorMesMax + ", eventosPorAnioMax=" + eventosPorAnioMax + ", costo=" + costo + ", depositoGarantia=" + depositoGarantia + ", diasAnticipacionCancelar=" + diasAnticipacionCancelar + '}';
    }
    
    
}
