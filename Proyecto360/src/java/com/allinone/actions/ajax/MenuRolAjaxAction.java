package com.allinone.actions.ajax;

import com.allinone.business.RolMenuCondominioBO;
import com.allinone.domain.RolMenuCondominioEstatus;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.RelacionMenuRolesCondominio;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class MenuRolAjaxAction extends JSONAjaxAction {

    private Long pkCondominio;
    private Long pkRol;
    private Long pkMenu;
    private Long pkMenuCondominio;

    public String getMenuPorRoles() {
        if (pkRol == null || pkRol.equals("") || pkCondominio == null || pkCondominio.equals("")) {
            return SUCCESS_JSON;
        }
        List<RelacionMenuRoles> listaMenu = getDaos().getRelacionMenuRolesDao().getMenuPorRol(pkRol);
        List<RelacionMenuRolesCondominio> listaMenuActivos = getDaos().getRelacionMenuRolesCondominioDao().getMenuRolPorCondominio(pkRol, pkCondominio);

        RolMenuCondominioBO bo = new RolMenuCondominioBO();
        List<RolMenuCondominioEstatus> listaPermisos = bo.obtieneEstatus(listaMenu, listaMenuActivos);
        if (listaPermisos == null) {
        } else {
            for (RolMenuCondominioEstatus r : listaPermisos) {
//                String check = r.getEstatus()?"checked disabled":"";
                getJsonResult().add("[\"" + r.getMenuRol().getMenu().getNombre()
                        //                        + "\", \"" + "<input type='checkbox' "+check+" onclick='agrega(this,"+r.getMenuRol().getId()+","+pkCondominio+")'> "
                        + (r.getEstatus() ? "\", \"<a class='fancybox fancybox.iframe' onclick='elimina(" + r.getMenuRolesCondominio().getId() + ")'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-check-square-o  fa-stack-1x fa-inverse'></i></span></a>"
                                : "\", \"<a class='fancybox fancybox.iframe' onclick='agrega(this," + r.getMenuRol().getId() + "," + pkCondominio + ")'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-square-o  fa-stack-1x fa-inverse'></i></span></a>")
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String agrega() {
        if (pkMenu == null || pkMenu.equals("") || pkCondominio == null || pkCondominio.equals("")) {
            return ERROR_JSON;
        }

        Condominio c = getDaos().getCondominioDao().findById(pkCondominio);
        if (c == null) {
            return ERROR_JSON;
        }
        RelacionMenuRoles menu = getDaos().getRelacionMenuRolesDao().findById(pkMenu);
        if (menu == null) {
            return ERROR_JSON;
        }

        List<RelacionMenuRolesCondominio> existentes = getDaos().getRelacionMenuRolesCondominioDao().getMenuRolPorCondominio(menu, c);
        RelacionMenuRolesCondominio nuevoPermiso = null;
        if (existentes == null) {
            RelacionMenuRolesCondominio rmrCondo = new RelacionMenuRolesCondominio();
            rmrCondo.setCondominio(c);
            rmrCondo.setMenuRol(menu);
            try {
                nuevoPermiso = getDaos().getRelacionMenuRolesCondominioDao().save(rmrCondo);
            } catch (Exception e) {
                e.printStackTrace();
                return ERROR_JSON;
            }
        }

        getJsonResult().add("[\""+(nuevoPermiso==null?"":nuevoPermiso.getId())+"\"]");
        
        return SUCCESS_JSON;
    }

    public String elimina() {
        if (pkMenuCondominio == null || pkMenuCondominio.equals("")) {
            return ERROR_JSON;
        }

        RelacionMenuRolesCondominio r = getDaos().getRelacionMenuRolesCondominioDao().findById(pkMenuCondominio);
        if (r == null) {
            return ERROR_JSON;
        }
        try {
            getDaos().getRelacionMenuRolesCondominioDao().delete(r);
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_JSON;
        }

        return SUCCESS_JSON;
    }

    public Long getPkCondominio() {
        return pkCondominio;
    }

    public void setPkCondominio(Long pkCondominio) {
        this.pkCondominio = pkCondominio;
    }

    public Long getPkRol() {
        return pkRol;
    }

    public void setPkRol(Long pkRol) {
        this.pkRol = pkRol;
    }

    public Long getPkMenu() {
        return pkMenu;
    }

    public void setPkMenu(Long pkMenu) {
        this.pkMenu = pkMenu;
    }

    public Long getPkMenuCondominio() {
        return pkMenuCondominio;
    }

    public void setPkMenuCondominio(Long pkMenuCondominio) {
        this.pkMenuCondominio = pkMenuCondominio;
    }

}
