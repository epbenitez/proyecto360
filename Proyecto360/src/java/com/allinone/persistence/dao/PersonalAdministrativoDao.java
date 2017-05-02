package com.allinone.persistence.dao;

import com.allinone.persistence.model.PersonalAdministrativo;
import java.util.List;

/**
 *
 * @author Victor Lozano
 */
public interface PersonalAdministrativoDao extends DaoBase<PersonalAdministrativo, Long> {
    public PersonalAdministrativo findByUsuario(Long id);
    public PersonalAdministrativo findById(String id);
    public List<PersonalAdministrativo> findAdministrativos();
    public List<PersonalAdministrativo> asociadaPersonalAdministrativo (Long unidadAcademicaId);
}