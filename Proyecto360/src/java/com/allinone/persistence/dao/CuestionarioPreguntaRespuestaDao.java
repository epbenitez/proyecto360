package com.allinone.persistence.dao;

import com.allinone.persistence.model.CuestionarioPreguntaRespuesta;
import java.util.List;

/**
 *
 * @author Tania G. Sánchez
 */
public interface CuestionarioPreguntaRespuestaDao extends DaoBase<CuestionarioPreguntaRespuesta,Long>{
    public List<CuestionarioPreguntaRespuesta> findByCuestionario(Long cuestionarioId);
    public Integer totalPreguntas (Long cuestionarioId);
}
