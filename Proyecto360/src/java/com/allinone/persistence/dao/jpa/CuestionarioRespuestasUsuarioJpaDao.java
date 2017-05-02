package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioRespuestasUsuarioDao;
import com.allinone.persistence.model.CuestionarioPreguntas;
import com.allinone.persistence.model.CuestionarioRespuestas;
import com.allinone.persistence.model.CuestionarioRespuestasUsuario;
import com.allinone.persistence.model.CuestionarioSeccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Tania G. Sánchez
 */
public class CuestionarioRespuestasUsuarioJpaDao extends JpaDaoBase<CuestionarioRespuestasUsuario,Long> implements CuestionarioRespuestasUsuarioDao{

    public CuestionarioRespuestasUsuarioJpaDao() {
        super(CuestionarioRespuestasUsuario.class);
    }
    
    @Override
    public List<CuestionarioRespuestasUsuario> getResultadosUsuario(Long cuestionarioId, Long usuario){
        String sql = "select cpru.id, cs.id, cs.nombre, cp.id, cp.nombre, cr.id, cr.nombre, cpru.respuestaabierta "
                    + "from rmm_cuestionario_pregunta_respuesta_usuario cpru "
                    + "inner join ent_cuestionario_pregunta cp on cpru.pregunta_id = cp.id "
                    + "inner join ent_cuestionario_respuestas cr on cpru.respuesta_id = cr.id "
                    + "inner join ent_cuestionario_pregunta_respuesta cpr on cpr.pregunta_id = cp.id "
                    + "inner join ent_cuestionario_seccion cs on cs.id = cpr.seccion_id "
                    + "where cs.activo = 1 and cp.activo = 1 and cpru.cuestionario_id=" + cuestionarioId 
                    + " and cpru.usuario_id=" + usuario
                    + " group by cp.id, cr.id order by cs.id, cp.id, cr.id";
        List<Object[]> lista = executeNativeQuery(sql);
        List<CuestionarioRespuestasUsuario> lcru = new ArrayList<CuestionarioRespuestasUsuario>();
        for (Object[] res : lista) {
            CuestionarioRespuestasUsuario cru = new CuestionarioRespuestasUsuario();
            cru.setId((Long) res[0]);
            //Sección
            CuestionarioSeccion cs = new CuestionarioSeccion();
            cs.setId((Long) res[1]);
            cs.setNombre((String) res[2]);
            cru.setSeccion(cs);
            //Preguntas
            CuestionarioPreguntas cp = new CuestionarioPreguntas();
            cp.setId((Long) res[3]);
            cp.setNombre((String) res[4]);
            cru.setPregunta(cp);
            //Respuestas
            CuestionarioRespuestas cr = new CuestionarioRespuestas();
            cr.setId((Long) res[5]);
            cr.setNombre((String) res[6]);
            cru.setRespuesta(cr);
            cru.setRespuestaAbierta((String) res[7]);
            lcru.add(cru);
        }
        return lcru;
    }
    
    @Override
    public void borrarESEporAlumno (Long usuarioId){
        String jpql = "delete from CuestionarioRespuestasUsuario where usuario.id= ?1";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter(1, usuarioId);
        query.executeUpdate();
        
    }
}