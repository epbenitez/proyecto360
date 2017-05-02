
package com.allinone.persistence.dao;

import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface UsuarioCondominioDao extends DaoBase<UsuarioCondominio,Long>{

    public UsuarioCondominio getUsuarioCondominio(Long condominioId);
    public List<UsuarioCondominio> getUsuarioCondominio(Usuario usuario);
    public void eliminaUsuarioCondominio(Usuario usuario);
}
