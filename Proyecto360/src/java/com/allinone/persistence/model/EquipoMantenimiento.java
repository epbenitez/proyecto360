package com.allinone.persistence.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author patriciabenitez
 */
@Entity
@Table(name = "ent_equipo_mantenimiento")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class EquipoMantenimiento  implements Serializable,  BaseEntity {

private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Equipo equipo;
    @ManyToOne
    private EquipoMantenimientoEstatus estatusMantenimiento;
    @ManyToOne
    private EquipoMantenimientoTipo tipoMantenimiento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaProgramada;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRealizada;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public EquipoMantenimientoEstatus getEstatusMantenimiento() {
        return estatusMantenimiento;
    }

    public void setEstatusMantenimiento(EquipoMantenimientoEstatus estatusMantenimiento) {
        this.estatusMantenimiento = estatusMantenimiento;
    }

    public EquipoMantenimientoTipo getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(EquipoMantenimientoTipo tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Date getFechaRealizada() {
        return fechaRealizada;
    }

    public void setFechaRealizada(Date fechaRealizada) {
        this.fechaRealizada = fechaRealizada;
    }

    @Override
    public String toString() {
        return "EquipoMantenimiento{" + "id=" + id + ", equipo=" + equipo + ", estatusMantenimiento=" + estatusMantenimiento + ", tipoMantenimiento=" + tipoMantenimiento + ", fechaProgramada=" + fechaProgramada + ", fechaRealizada=" + fechaRealizada + '}';
    }

}
