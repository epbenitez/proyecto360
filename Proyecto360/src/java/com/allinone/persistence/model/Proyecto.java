package com.allinone.persistence.model;

import com.allinone.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author erikapatriciabenitezsoto
 */
@Entity
@Table(name = "ent_proyecto")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Proyecto implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @ManyToOne
    private Condominio condominio;
    @ManyToOne
    private ProyectoCategoria categoria;
    @ManyToOne
    private ProyectoCuota cuota;
    @ManyToOne
    private ProyectoEstatus estatus;
    @ManyToOne
    private ProyectoPrioridad prioridad;
    @ManyToOne
    private ProyectoTipo tipo;
    private Boolean visible;
    @ManyToOne
    private ProyectoTarea tareaSiguiente;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    private String descripcion;

    @Transient
    private Long diasTranscurridos;
    @Transient
    private Long diasRestantes;
    @Transient
    private ProyectoTarea siguienteTarea;

    public Proyecto() {

    }
    
    public Proyecto(Long id) {
        this.id = id;
    }

    public Proyecto(Long id, String condominioNombre, String tipoNombre, String prioridadNombre, String estatusNombre, String cuotaNombre,
            Boolean visible, Date fechaRegistro, String categoriaNombre, String descripcion, Date fechaVencimiento, String titulo) {

        this.id = id;
        this.titulo = titulo;
        this.condominio = new Condominio();
        this.condominio.setNombre(condominioNombre);
        
        this.descripcion = descripcion;
        
        this.tipo = new ProyectoTipo();
        this.tipo.setNombre(tipoNombre);
        
        this.prioridad = new ProyectoPrioridad();
        this.prioridad.setNombre(prioridadNombre);
        
        this.estatus = new ProyectoEstatus();
        this.estatus.setNombre(estatusNombre);
        
        this.cuota = new ProyectoCuota();
        this.cuota.setNombre(cuotaNombre);
        
        this.visible = visible;
        
//        this.tareaSiguiente = new ProyectoTarea();
//        this.tareaSiguiente.setNombre(tareaSiguienteNombre);
        
        this.fechaRegistro = fechaRegistro;
        this.fechaVencimiento = fechaVencimiento;
        
        this.categoria = new ProyectoCategoria();
        this.categoria.setNombre(categoriaNombre);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public ProyectoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(ProyectoCategoria categoria) {
        this.categoria = categoria;
    }

    public ProyectoCuota getCuota() {
        return cuota;
    }

    public void setCuota(ProyectoCuota cuota) {
        this.cuota = cuota;
    }

    public ProyectoEstatus getEstatus() {
        return estatus;
    }

    public void setEstatus(ProyectoEstatus estatus) {
        this.estatus = estatus;
    }

    public ProyectoPrioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(ProyectoPrioridad prioridad) {
        this.prioridad = prioridad;
    }

    public ProyectoTipo getTipo() {
        return tipo;
    }

    public void setTipo(ProyectoTipo tipo) {
        this.tipo = tipo;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public ProyectoTarea getTareaSiguiente() {
        return tareaSiguiente;
    }

    public void setTareaSiguiente(ProyectoTarea tareaSiguiente) {
        this.tareaSiguiente = tareaSiguiente;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getDiasTranscurridos() {
        if (getFechaRegistro() != null) {
            return Util.numeroDeDias(getFechaRegistro(), new Date());
        } else {
            return 0L;
        }
    }

    public void setDiasTranscurridos(Long diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    public Long getDiasRestantes() {
        if (getFechaRegistro() != null && getFechaVencimiento()!=null) {
            return Util.numeroDeDias(getFechaRegistro(), getFechaVencimiento());
        } else {
            return 0L;
        }
    }

    public void setDiasRestantes(Long diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ProyectoTarea getSiguienteTarea() {
        return siguienteTarea;
    }

    public void setSiguienteTarea(ProyectoTarea siguienteTarea) {
        this.siguienteTarea = siguienteTarea;
    }

    @Override
    public String toString() {
        return "Proyecto{" + "id=" + id + ", titulo=" + titulo + ", condominio=" + condominio + ", categoria=" + categoria + ", cuota=" + cuota + ", estatus=" + estatus + ", prioridad=" + prioridad + ", tipo=" + tipo + ", visible=" + visible + ", tareaSiguiente=" + tareaSiguiente + ", fechaRegistro=" + fechaRegistro + ", fechaVencimiento=" + fechaVencimiento + ", descripcion=" + descripcion + ", diasTranscurridos=" + diasTranscurridos + ", diasRestantes=" + diasRestantes + '}';
    }

}
