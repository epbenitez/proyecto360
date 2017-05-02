package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.RolDao;
import com.allinone.persistence.model.Rol;

/**
 *
 * @author Luis Tlatelpa Fonseca
 */
public class RolJpaDao extends JpaDaoBase<Rol, Long> implements RolDao{

    /**
     * Crea una instancia de una <code>RolJpaDao</code>.
     */
    public RolJpaDao() {
        super(Rol.class);
    }   

    /**
     * Localiza un Rol, guiandose por el String que es Id del Rol en la Tabla
     * @param id Es el identificador de tipo String, que es la llave (nombre) del Rol
     * @return Elemento coincidente
     */
    @Override
    public Rol findById(String id){

        Rol rol = new Rol();

        String jpql = "SELECT R FROM Rol r WHERE r.id = ?1";
        rol = executeSingleQuery(jpql, id);

        return rol;
    }


}