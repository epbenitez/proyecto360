package com.allinone.persistence.dao.jpa;

import com.allinone.domain.AvisoCobro;
import com.allinone.persistence.dao.DepartamentoUsuarioDao;
import com.allinone.persistence.model.DepartamentoUsuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class DepartamentoUsuarioJpaDao extends JpaDaoBase<DepartamentoUsuario, Long> 
        implements DepartamentoUsuarioDao {

    public DepartamentoUsuarioJpaDao() {
        super(DepartamentoUsuario.class);
    }

    @Override
    public DepartamentoUsuario findByUserId(Long id) {

        String jpql = "SELECT u FROM DepartamentoUsuario u WHERE u.usuario.id = ?1";
        List<DepartamentoUsuario> userLst = executeQuery(jpql, id);

        if (userLst != null && userLst.size() == 1) {
            return userLst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public DepartamentoUsuario findByDepartamentoId(Long id) {

        String jpql = "SELECT u FROM DepartamentoUsuario u WHERE u.departamento.id = ?1";
        List<DepartamentoUsuario> userLst = executeQuery(jpql, id);

        if (userLst != null && userLst.size() == 1) {
            return userLst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Object[]> findByCondominioId(Long condominioId) {

        String sql = "SELECT u.id, c.`nombre`, t.nombre,d.`nombre`,u.usuario, u.contrasenia\n"
                + "FROM ent_departamento d\n"
                + "inner join rmm_departamento_usuario du on d.ID = du.DEPARTAMENTO_ID\n"
                + "inner join ent_condominio c on c.id = d.condominio_id\n"
                + "inner join ent_torre t on t.id = d.torre_id\n"
                + "inner join ent_usuario u on u.id = du.usuario_id\n"
                + "WHERE d.CONDOMINIO_ID = " + condominioId;
        List<Object[]> userLst = executeNativeQuery(sql);
        return userLst == null || userLst.isEmpty() ? null : userLst;
    }

    @Override
    public List<AvisoCobro> encuentraDatosDepartamentoUsuario(Long torreId, Long condominioId, Long departamentoId) {

        String sql = "select d.id, co.nombre,t.nombre,d.nombre,u.usuario,c.correoelectronico,co.clave, "
                + "co.id,co.direccion,co.banco, co.beneficiario, co.cuenta, co.sucursal, d.clabe, d.referencia, co.contactomail \n"
                + " from ent_departamento d  \n"
                + "left join rmm_departamento_contacto dc on d.id = dc.departamento_id\n"
                + "left join cat_contactos c on c.id = dc.contacto_id\n"
                + "inner join ent_condominio co on co.id = d.condominio_id\n"
                + "inner join ent_torre t on t.id = d.torre_id\n"
                + "inner join rmm_departamento_usuario du on du.departamento_id = d.id\n"
                + "inner join ent_usuario u on u.id = du.usuario_id\n"
                + "where d.condominio_id = " + condominioId + " and d.torre_id = " + torreId + ""
                + (departamentoId == null ? "" : " and d.id = " + departamentoId)
                + " and c.correoelectronico is not null ;";

        List<Object[]> results = executeNativeQuery(sql);

        if (results != null && !results.isEmpty()) {
            List<AvisoCobro> ecList = new ArrayList<AvisoCobro>();
            for (Object[] res : results) {
                AvisoCobro ec = new AvisoCobro();
                ec.setId((Long) res[0]);
                ec.setCondominio((String) res[1]);
                ec.setTorre((String) res[2]);
                ec.setDepartamento((String) res[3]);
                ec.setUsuario((String) res[4]);
                ec.setCorreo((String) res[5]);
                ec.setClaveCondominio((String) res[6]);
                ec.setIdCondominio(new Long(res[7].toString()));
                ec.setDireccionCondominio((String) res[8]);
                ec.setBanco(res[9] == null ? "" : (String) res[9]);
                ec.setBeneficiario(res[10] == null ? "" : (String) res[10]);
                ec.setCuenta(res[11] == null ? "" : (String) res[11]);
                ec.setSucursal(res[12] == null ? "" : (String) res[12]);
                ec.setClabe(res[13] == null ? "" : (String) res[13]);
                ec.setReferencia(res[14] == null ? "" : (String) res[14]);
                ec.setContactoMail(res[15] == null ? "" : (String) res[15]);
                ecList.add(ec);
            }

            return ecList == null || ecList.isEmpty() ? null : ecList;
        }
        return null;
    }

    @Override
    public List<Object[]> administra(Long condominioId) {

        String sql = "select distinct(u.usuario), c.nombre condominio, GROUP_CONCAT(DISTINCT(r.`clave`)) roles,u.id usuarioId\n"
                + "from ent_usuario u\n"
                + "left join rmm_rol_usuario ru on ru.usuario_id = u.id\n"
                + "inner join rmm_condominio_usuario cu on cu.usuario_id = u.id\n"
                + "inner join ent_condominio c on c.id = cu.condominio_id\n"
                + "left join cat_rol r on r.id = ru.rol_id\n"
                + "where ru.rol_id not in(2)\n"
                + "and c.id = " + condominioId + " \n"
                + "group by usuario\n"
                + "order by 2; ";
//        System.out.println("sql: " + sql);
        List<Object[]> userLst = executeNativeQuery(sql);
        return userLst == null || userLst.isEmpty() ? null : userLst;
    }
}
