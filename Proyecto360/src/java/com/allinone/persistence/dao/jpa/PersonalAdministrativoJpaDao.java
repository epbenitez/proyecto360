package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.PersonalAdministrativoDao;
import com.allinone.persistence.model.PersonalAdministrativo;
import java.util.List;

/**
 *
 * @author Victor Lozano
 */
public class PersonalAdministrativoJpaDao extends JpaDaoBase<PersonalAdministrativo, Long> implements PersonalAdministrativoDao {

    public PersonalAdministrativoJpaDao() {
        super(PersonalAdministrativo.class);
    }

    @Override
    public PersonalAdministrativo findByUsuario(Long id) {
        return null;
    }
    
    @Override
    public PersonalAdministrativo findById(String id) {
        String jpql = "SELECT p FROM PersonalAdministrativo p "
                + "WHERE p.id = ?1";
        PersonalAdministrativo personalAdministrativo = executeSingleQuery(jpql, id);

        return personalAdministrativo;
    }

    @Override
    public List<PersonalAdministrativo> findAdministrativos() {
        String consulta = "SELECT p FROM PersonalAdministrativo p, Usuario u "
                + "WHERE p.rol.id != 2 "
                + "AND u.id = p.usuario.id "
                + "AND u.activo = 1";
        List<PersonalAdministrativo> personal = executeQuery(consulta);
        return personal == null ? null : personal;
    }

    @Override
    public List<PersonalAdministrativo> asociadaPersonalAdministrativo (Long unidadAcademicaId) {
        String jpql = "SELECT p FROM  PersonalAdministrativo p WHERE p.unidadAcademica.id = ?1";
        //SELECT * FROM allinone.ent_personal_administrativo where unidadacademica_id=1;
        List<PersonalAdministrativo> lista = executeQuery(jpql, unidadAcademicaId);
        return lista;
    }
}