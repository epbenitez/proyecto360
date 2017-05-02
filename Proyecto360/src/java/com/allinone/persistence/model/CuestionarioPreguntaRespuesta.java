/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Tania G. Sánchez
 */
@Entity
@Table(name = "ent_cuestionario_pregunta_respuesta")
@Cache(alwaysRefresh=true,type= org.eclipse.persistence.annotations.CacheType.NONE)
public class CuestionarioPreguntaRespuesta implements Serializable, BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private CuestionarioPreguntas pregunta;
    private CuestionarioRespuestas respuesta;
    private CuestionarioSeccion seccion;
    private Integer orden;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CuestionarioPreguntas getPregunta() {
        return pregunta;
    }

    public void setPregunta(CuestionarioPreguntas pregunta) {
        this.pregunta = pregunta;
    }

    public CuestionarioRespuestas getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(CuestionarioRespuestas respuesta) {
        this.respuesta = respuesta;
    }

    public CuestionarioSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(CuestionarioSeccion seccion) {
        this.seccion = seccion;
    }
    
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @Override
    public String toString() {
        return "CuestionarioPreguntaRespuesta{" + "id=" + id + ", pregunta=" + pregunta + ", respuesta=" + respuesta + ", seccion=" + seccion + ", orden=" + orden + '}';
    }
}