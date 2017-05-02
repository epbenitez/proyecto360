package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.UsuarioDao;
import com.allinone.persistence.model.Usuario;
import java.util.List;

/**
 * Implementación de las operaciones CRUD.
 *
 * @author Patricia Benitez
 */
public class UsuarioJpaDao extends JpaDaoBase<Usuario, Long> implements UsuarioDao {

    /**
     * Crea una instancia de una <code>UsuarioJpaDao</code>.
     */
    public UsuarioJpaDao() {
        super(Usuario.class);
    }

    @Override
    public Usuario findById(String id) {

        String jpql = "SELECT u FROM Usuario u WHERE u.usuario = ?1";
        List<Usuario> userLst = executeQuery(jpql, id);

        if (userLst != null && userLst.size() == 1) {
            return userLst.get(0);
        } else {
            return null;
        }
    }
    
    @Override
    public Usuario findById(Long id) {

        String jpql = "SELECT u FROM Usuario u WHERE u.id = ?1";
        List<Usuario> userLst = executeQuery(jpql, id);

        if (userLst != null && userLst.size() == 1) {
            return userLst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Usuario findByIdAlumno(Long idAlumno) {

        String sql = "select u.id,u.usuario,u.contrasenia,u.activo from ent_usuario u "
                + "inner join ent_alumno a on a.usuario_id = u.id "
                + "where a.id = " + idAlumno;
//                + " and u.activo = 1";
        List<Object[]> results = executeNativeQuery(sql);

        for (Object[] res : results) {
            Usuario u = new Usuario();
            u.setId((Long) res[0]);
            u.setUsuario((String) res[1]);
            u.setPassword((String) res[2]);
            u.setActivo(Boolean.TRUE);
            return u;
        }
        return null;
    }

}
