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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Patricia Benitez
 */
@Entity
@Table(name = "rmm_cuestionario_pregunta_respuesta_usuario")
@Cache(alwaysRefresh=true,type= org.eclipse.persistence.annotations.CacheType.NONE)
public class CuestionarioRespuestasUsuario implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuario;
    @ManyToOne
    private CuestionarioPreguntas pregunta;
    @ManyToOne
    private CuestionarioRespuestas respuesta;
    @ManyToOne
    private Cuestionario cuestionario;
    private String respuestaAbierta;
    @Transient
    private CuestionarioSeccion seccion;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public Cuestionario getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(Cuestionario cuestionario) {
        this.cuestionario = cuestionario;
    }
    
    public String getRespuestaAbierta() {
        return respuestaAbierta;
    }

    public void setRespuestaAbierta(String respuestaOtra) {
        this.respuestaAbierta = respuestaOtra;
    }

    public CuestionarioSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(CuestionarioSeccion seccion) {
        this.seccion = seccion;
    }

    @Override
    public String toString() {
        return "CuestionarioRespuestasUsuario{" + "id=" + id + ", usuario=" + usuario + ", pregunta=" + pregunta + ", respuesta=" + respuesta + ", cuestionario=" + cuestionario + ", respuestaAbierta=" + respuestaAbierta + '}';
    }
}