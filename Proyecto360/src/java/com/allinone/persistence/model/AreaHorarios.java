package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author patriciabenitez
 */
@Entity
@Table(name = "ent_area_horarios")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class AreaHorarios  implements Serializable, BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Area area;
    private String dia;
    private String horaInicial;
    private String horaFinal;
    
    @Transient
    private String horaICalendario;
    @Transient
    private String horaFCalendario;
    @Transient
    private String detalle;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(String horaInicial) {
        this.horaInicial = horaInicial;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getHoraICalendario() {
        if(getHoraInicial()==null){
            return "12";
        }
        Integer indexOfAM = getHoraInicial().indexOf("a.m.");
        String[] splitHI = getHoraInicial().split(":");
        
        if(indexOfAM>=0){
            return splitHI[0];
        }else{
            Integer inicio = new Integer(splitHI[0]);
            return (inicio + 12) +"";
        }
        
    }

    public void setHoraICalendario(String horaICalendario) {
        this.horaICalendario = horaICalendario;
    }

    public String getHoraFCalendario() {
        if(getHoraFinal()==null){
            return "12";
        }
        Integer indexOfAM = getHoraFinal().indexOf("a.m.");
        String[] splitHF = getHoraFinal().split(":");
        
        if(indexOfAM>=0){
            return splitHF[0];
        }else{
            Integer inicio = new Integer(splitHF[0]);
            return (inicio + 12) +"";
        }
    }

    public void setHoraFCalendario(String horaFCalendario) {
        this.horaFCalendario = horaFCalendario;
    }

    public String getDetalle() {
        return this.area.getNombre() + " : " + this.getDia() + " : " + this.getHoraInicial()  + " : " + this.getHoraFinal();
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
    
    
    

    @Override
    public String toString() {
        return "AreaHorarios{" + "id=" + id + ", area=" + area + ", dia=" + dia + ", horaInicial=" + horaInicial + ", horaFinal=" + horaFinal + '}';
    }
    
}
