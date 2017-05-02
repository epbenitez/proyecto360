package com.allinone.persistence.dao;

import com.allinone.persistence.model.CuestionarioRespuestasUsuario;
import java.util.List;

/**
 *
 * @author Tania G. Sánchez
 */
public interface CuestionarioRespuestasUsuarioDao extends DaoBase<CuestionarioRespuestasUsuario,Long>{
    public List<CuestionarioRespuestasUsuario> getResultadosUsuario(Long cuestionarioId, Long usuario);
    public void borrarESEporAlumno (Long usuarioId);
}