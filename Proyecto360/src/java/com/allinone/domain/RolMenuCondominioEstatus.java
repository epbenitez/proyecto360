package com.allinone.domain;

import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.RelacionMenuRolesCondominio;

/**
 *
 * @author patriciabenitez
 */
public class RolMenuCondominioEstatus {
    
    private RelacionMenuRoles menuRol = new RelacionMenuRoles();
    private Boolean estatus = Boolean.FALSE;
    private RelacionMenuRolesCondominio menuRolesCondominio = new RelacionMenuRolesCondominio();

    public RelacionMenuRoles getMenuRol() {
        return menuRol;
    }

    public void setMenuRol(RelacionMenuRoles menuRol) {
        this.menuRol = menuRol;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public RelacionMenuRolesCondominio getMenuRolesCondominio() {
        return menuRolesCondominio;
    }

    public void setMenuRolesCondominio(RelacionMenuRolesCondominio menuRolesCondominio) {
        this.menuRolesCondominio = menuRolesCondominio;
    }
    
    
    
}
