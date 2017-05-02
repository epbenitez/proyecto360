/**
 * ALL IN ONE DIRECCION DE SERVICIOS
 * ESTUDIANTILES 2015
 *
 */
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.MenuDao;
import com.allinone.persistence.model.Menu;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class MenuJpaDao extends JpaDaoBase<Menu, Long> implements MenuDao {

    public MenuJpaDao() {
        super(Menu.class);
    }

    @Override
    public String getNombreModulo(String action) {
        String jpql = "select am.descripcion, m.nombre from ent_menu m  "
                + "inner join rmm_menu_roles mr on m.id = mr.menu_id "
                + "inner join ent_agrupador_menu am on am.id = mr.agrupador_id "
                + "where m.url like '%" + action + "%'";
        List<Object[]> result = executeNativeQuery(jpql);

        if (result == null || result.isEmpty()) {
            return "";
        }

        Object[] menu = result.get(0);
        return "<li>\n<a href=\"/index.action\">Inicio</a> </li>\n<li>\n\n<span>" + menu[0] + "<span></li>\n <li class=\"active\">\n\n<span> " + menu[1] + "<span></li>\n ";
    }
}
