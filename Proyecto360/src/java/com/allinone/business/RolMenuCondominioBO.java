package com.allinone.business;

import com.allinone.domain.RolMenuCondominioEstatus;
import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.RelacionMenuRolesCondominio;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class RolMenuCondominioBO {

    public List<RolMenuCondominioEstatus> obtieneEstatus(List<RelacionMenuRoles> menu, List<RelacionMenuRolesCondominio> permisos) {
        HashMap<Long, RolMenuCondominioEstatus> hmMenu = new HashMap<Long, RolMenuCondominioEstatus>();
        if (menu != null) {
            for (RelacionMenuRoles r : menu) {
                RolMenuCondominioEstatus rEstatus = new RolMenuCondominioEstatus();
                rEstatus.setEstatus(Boolean.FALSE);
                rEstatus.setMenuRol(r);
                //Buscamos si tiene el permiso establecido
                if (permisos != null && permisos.size() > 0) {
                    for (RelacionMenuRolesCondominio rmCondom: permisos ) {
                        if(r.getId().equals(rmCondom.getMenuRol().getId())){
                            rEstatus.setEstatus(Boolean.TRUE);
                            rEstatus.setMenuRolesCondominio(rmCondom);
                            break;
                        }
                    }
                }
                hmMenu.put(r.getId(), rEstatus);
            }
        }

        List<RolMenuCondominioEstatus> resultado = new ArrayList<RolMenuCondominioEstatus>(hmMenu.values());

        return resultado.isEmpty() ? null : resultado;
    }
}
