package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.RelacionMenuRolesDao;
import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioPrivilegio;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Query;

public class RelacionMenuRolesJpaDao extends JpaDaoBase<RelacionMenuRoles, Long> implements RelacionMenuRolesDao {

    /**
     * Crea una instancia de una <code>AlumnoJpaDao</code>.
     */
    public RelacionMenuRolesJpaDao() {
        super(RelacionMenuRoles.class);
    }

    /**
     * Obtiene las opciones del menú disponibles de acuerdo con un Rol de
     * usuario y las ordena
     *
     * @param roles Lista de roles del usuario
     * @return Cadena con formato HTML de las opciones del menú ordenada por
     * agrupadores
     */
    @Override
    public String findURLByRols(Usuario usuario) {
        Set<UsuarioPrivilegio> roles = usuario.getPrivilegios();
        String resultado = "";
        Map hdr = new LinkedHashMap();
        Map menuOpc = new LinkedHashMap();
        List<RelacionMenuRoles> result = new LinkedList<RelacionMenuRoles>();

        String rolesStr = "";
        Boolean isPropietario = Boolean.FALSE;

        //Este for es para ver si alguno de sus roles es 2
        //Por que sucede que pueda tener rol 6 y despues 2, y combinar los tipos de query que se deben mostrar
        for (UsuarioPrivilegio usuarioPrivilegio : roles) {
            Long rolId = usuarioPrivilegio.getPrivilegio().getId();
            if (rolId.equals(2L) || isPropietario ) {
                isPropietario = Boolean.TRUE;
            }
        }
        
        for (UsuarioPrivilegio usuarioPrivilegio : roles) {
            Long rolId = usuarioPrivilegio.getPrivilegio().getId();

            if ( isPropietario ) {
                rolesStr += " menuRolesCondominio.menuRol.rol.id = " + rolId + " or ";
            }else{
                rolesStr += " menuRoles.rol.id = " + rolId + " or ";
            }
        }

        if (!rolesStr.isEmpty() && rolesStr.length() > 0) {
            rolesStr = rolesStr.substring(0, rolesStr.lastIndexOf(" or "));
        }

        Query query = null;
        if (isPropietario) {
            String jpql = "SELECT menuRolesCondominio.menuRol FROM RelacionMenuRolesCondominio menuRolesCondominio "
                    + "WHERE menuRolesCondominio.condominio.id = "
                    + "(select du.departamento.condominio.id from DepartamentoUsuario du where du.usuario.id = " + usuario.getId() + ") "
                    + "and  (";
            jpql += rolesStr;
            jpql += ") AND menuRolesCondominio.menuRol.activo = TRUE "
                    + "ORDER BY menuRolesCondominio.menuRol.agrupador.orden asc, menuRolesCondominio.menuRol.orden desc";
            query = getEntityManager().createQuery(jpql);
        } else {
            String jpql = "SELECT menuRoles FROM RelacionMenuRoles menuRoles WHERE  (";
            jpql += rolesStr;
            jpql += ") AND menuRoles.activo = TRUE ORDER BY menuRoles.agrupador.orden asc, menuRoles.orden desc";
            query = getEntityManager().createQuery(jpql);
        }

        result = query==null?new ArrayList<RelacionMenuRoles>(): (List<RelacionMenuRoles>) query.getResultList();

        if (result.size() > 0) {
            List idMenu = new LinkedList();
            for (RelacionMenuRoles menu : result) {
                if (menu.getAgrupador() != null && !idMenu.contains(menu.getMenu().getId())) {
                    if (!hdr.containsKey(menu.getAgrupador().getId())) {
                        hdr.put(menu.getAgrupador().getId(), menu.getAgrupador().getDescripcion());
                    }
                    if (menuOpc.containsKey(menu.getAgrupador().getId())) {
                        String value = (String) menuOpc.remove(menu.getAgrupador().getId());
                        value += "\n<li><a href=\"" + menu.getMenu().getUrl() + "\" title=\"" + menu.getMenu().getNombre() + "\"><i class=\"" + menu.getMenu().getIcono() + "\"></i><span>" + menu.getMenu().getNombre() + "</span></a>" + "</li>";
                        menuOpc.put(menu.getAgrupador().getId(), value);
                    } else {
                        menuOpc.put(menu.getAgrupador().getId(), ("\n<li><a href=\"" + menu.getMenu().getUrl() + "\" title=\"" + menu.getMenu().getNombre() + "\"><i class=\"" + menu.getMenu().getIcono() + "\"></i><span>" + menu.getMenu().getNombre() + "</span></a>" + "</li>"));
                    }
                    idMenu.add(menu.getMenu().getId());
                }
            }
        }

        if (menuOpc.size() > 0) {
            Set keys = hdr.keySet();
            for (Object id : keys) {
                Long llave = (Long) id;
                if (menuOpc.containsKey(llave)) {
                    resultado += "<ul class=\"nav nav-pills nav-stacked\"><li class=\"nav-header  hidden-sm hidden-xs\">" + (String) hdr.get(llave) + (String) menuOpc.get(llave) + "</li></ul>";
                }
            }
        }

        return resultado;
    }

    @Override
    public List<RelacionMenuRoles> getMenuPorRol(Long rolId) {
        String jpql = "SELECT r FROM RelacionMenuRoles r WHERE r.rol.id = ?1 and r.activo = 1";
        List<RelacionMenuRoles> lst = executeQuery(jpql, rolId);
        return lst == null || lst.isEmpty() ? null : lst;
    }

}
