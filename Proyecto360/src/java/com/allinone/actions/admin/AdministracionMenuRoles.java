package com.allinone.actions.admin;

import com.allinone.actions.BaseAction;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Rol;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AdministracionMenuRoles extends BaseAction {

    private List<Condominio> condominios = new ArrayList<Condominio>();
    private List<Rol> roles = new ArrayList<Rol>();

    public String admin() {
        condominios = getDaos().getCondominioDao().findAll();

        Rol rol = getDaos().getRolDao().findById(2L);
        roles.add(rol);
        return SUCCESS;
    }

    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

}
