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
 * @author erikapatriciabenitezsoto
 */
@Entity
@Table(name = "ent_proyecto_tarea")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class ProyectoTarea implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accion;
    @ManyToOne
    private Proyecto proyecto;
    private String nombre;
    private String responsable;
    private String nota;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToOne
    private ProyectoEstatus estatus;
    
    public ProyectoTarea(){
        
    }
    
    public ProyectoTarea(Long id, String condominioNombre, String tipoNombre, String prioridadNombre, String estatusNombre, String cuotaNombre,
            Boolean visible, Date fechaRegistro, String categoriaNombre, String descripcion, Date fechaVencimiento, String titulo, Date fecha) {

        this.proyecto = new Proyecto();
        this.proyecto.setId(id);
        this.proyecto.setTitulo(titulo);
        this.proyecto.setCondominio(new Condominio());
        this.proyecto.getCondominio().setNombre(condominioNombre);
        
        this.proyecto.setDescripcion(descripcion);
        
        this.proyecto.setTipo(new ProyectoTipo());
        this.proyecto.getTipo().setNombre(tipoNombre);
        
        this.proyecto.setPrioridad(new ProyectoPrioridad());
        this.proyecto.getPrioridad().setNombre(prioridadNombre);
        
        this.proyecto.setEstatus(new ProyectoEstatus());
        this.proyecto.getEstatus().setNombre(estatusNombre);
        
        this.proyecto.setCuota(new ProyectoCuota());
        this.proyecto.getCuota().setNombre(cuotaNombre);
        
        this.proyecto.setVisible(visible);
        
//        this.tareaSiguiente = new ProyectoTarea();
//        this.tareaSiguiente.setNombre(tareaSiguienteNombre);
        
        this.proyecto.setFechaRegistro(fechaRegistro);
        this.proyecto.setFechaVencimiento(fechaVencimiento);
        
        this.proyecto.setCategoria(new ProyectoCategoria());
        this.proyecto.getCategoria().setNombre(categoriaNombre);
        
        this.fecha= fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ProyectoEstatus getEstatus() {
        return estatus;
    }

    public void setEstatus(ProyectoEstatus estatus) {
        this.estatus = estatus;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    
    @Override
    public String toString() {
        return "ProyectoTarea{" + "id=" + id + ", nombre=" + nombre + ", responsable=" + responsable + ", nota=" + nota + ", fecha=" + fecha + '}';
    }
    
    
}
