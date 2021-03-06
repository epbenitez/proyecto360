package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudDao;
import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudesTipoServicio;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudJpaDao extends JpaDaoBase<Solicitud, Long> implements SolicitudDao {

    public SolicitudJpaDao() {
        super(Solicitud.class);
    }

    @Override
    public List<Solicitud> getSolicitudes(Long condominioId, Long torreId, Long departamentoId, Long tipoId, Long estadoId) {

        StringBuilder jpql = new StringBuilder("SELECT  new com.allinone.persistence.model.Solicitud(s.id, s.condominio.id, s.tipoServicio.id, s.tipoServicio.nombre, s.estadoSolicitud.nombre, ");
        jpql.append(" s.fechaSolicitud, s.fechaSolucion, s.fechaNotificacionCliente, s.consecutivo) FROM Solicitud s WHERE 1=1 ");

        if (condominioId != null && !condominioId.toString().isEmpty()) {
            jpql.append(" and s.departamento.condominio.id= ").append(condominioId);
        }

        if (torreId != null && !torreId.toString().isEmpty()) {
            jpql.append(" and s.departamento.torre.id = ").append(torreId);
        }

        if (departamentoId != null && !departamentoId.toString().isEmpty()) {
            jpql.append(" and s.departamento.id = ").append(departamentoId);
        }

        if (tipoId != null && !tipoId.toString().isEmpty()) {
            jpql.append(" and s.tipoServicio.id = ").append(tipoId);
        }

        if (estadoId != null && !estadoId.toString().isEmpty()) {
            jpql.append(" and s.estadoSolicitud.id = ").append(estadoId);
        }

        List<Solicitud> solicitudesList = executeQuery(jpql.toString());

        return solicitudesList == null || solicitudesList.isEmpty() ? null : solicitudesList;
    }
    
    @Override
    public List<Solicitud> getSolicitudes(Long departamentoId, Long tipoId, Long estadoId) {

        StringBuilder jpql = new StringBuilder("SELECT  s FROM Solicitud s WHERE 1=1 ");

       
        if (departamentoId != null && !departamentoId.toString().isEmpty()) {
            jpql.append(" and s.departamento.id = ").append(departamentoId);
        }

        if (tipoId != null && !tipoId.toString().isEmpty()) {
            jpql.append(" and s.tipoServicio.id = ").append(tipoId);
        }

        if (estadoId != null && !estadoId.toString().isEmpty()) {
            jpql.append(" and s.estadoSolicitud.id = ").append(estadoId);
        }

        List<Solicitud> solicitudesList = executeQuery(jpql.toString());

        return solicitudesList == null || solicitudesList.isEmpty() ? null : solicitudesList;
    }

    @Override
    public List<Solicitud> getSolicitudes(Long condominioId, Long torreId, Long departamentoId, List<SolicitudesTipoServicio> tipoLst, Long estadoId) {

        StringBuilder jpql = new StringBuilder("SELECT  s FROM Solicitud s WHERE 1=1 ");

        if (condominioId != null && !condominioId.toString().isEmpty()) {
            jpql.append(" and s.departamento.condominio.id= ").append(condominioId);
        }

        if (torreId != null && !torreId.toString().isEmpty()) {
            jpql.append(" and s.departamento.torre.id = ").append(torreId);
        }

        if (departamentoId != null && !departamentoId.toString().isEmpty()) {
            jpql.append(" and s.departamento.id = ").append(departamentoId);
        }

        if (tipoLst != null && !tipoLst.isEmpty()) {
            jpql.append(" and (");
            for (SolicitudesTipoServicio st : tipoLst) {
                jpql.append("  s.tipoServicio.id = ").append(st.getId()).append(" or ");
            }
            jpql.delete(jpql.lastIndexOf(" or "), jpql.lastIndexOf(" or ") + 4);
            jpql.append(" )");
        }

        if (estadoId != null && !estadoId.toString().isEmpty()) {
            jpql.append(" and s.estadoSolicitud.id = ").append(estadoId);
        }

        List<Solicitud> solicitudesList = executeQuery(jpql.toString());

        return solicitudesList == null || solicitudesList.isEmpty() ? null : solicitudesList;
    }

    @Override
    public Integer getConsecutivo(Long condominioId, Long tipoServicioId) {
        String sql = "select s.consecutivo from ent_solicitudes s "
//                + " inner join ent_departamento d on d.id = s.departamento_id "
                + " where s.condominio_id ="+condominioId+" and tipoServicio_id = " + tipoServicioId
                + " order by s.condominio_id, s.tipoServicio_id, s.consecutivo desc limit 1;";

        List<Object[]> lista = executeNativeQuery(sql);
        
        if(lista==null || lista.isEmpty() ){
            return 0;
        }
        System.out.println("lista" + lista.get(0));
        Object res = lista.get(0) ;
        Integer i = (Integer)(res);

        return lista==null || lista.isEmpty()?0:i;
    }
}
