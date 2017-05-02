package com.allinone.actions.ajax;

import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import com.allinone.persistence.model.UsuarioPrivilegio;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class UsuariosAjaxAction extends JSONAjaxAction {

    private Long pkCondominio;
    private Usuario usuario;
    private Rol rolUsuario; 
    private List<DepartamentoUsuario> deptoUsuarioList = new ArrayList<DepartamentoUsuario>();

    public String busqueda() {
        if (pkCondominio == null) {
            getJsonResult().add("");
        } else {
            List<Object[]> deptoUsuarioListObj = getDaos().getDepartamentoUsuarioDao().findByCondominioId(pkCondominio);
            if (deptoUsuarioListObj != null && !deptoUsuarioListObj.isEmpty()) {
                for (Object[] o : deptoUsuarioListObj) {

                    getJsonResult().add("[\"" + (o[1] == null ? null : o[1].toString())
                            + "\", \"" + (o[2] == null ? null : o[2].toString())
                            + "\", \"" + (o[3] == null ? null : o[3].toString())
                            + "\", \"" + (o[4] == null ? null : o[4].toString())
                            + "\", \"" + (o[5] == null ? null : o[5].toString())
                            + "\", \"<a class='fancybox fancybox.iframe' href='/admin/usuarios/usuariosCondominioCambioAdministracion.action?usr.id=" + (o[0] == null ? null : o[0].toString()) + "'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-pencil  fa-stack-1x fa-inverse'></i></span></a>"
                            + " \"]");
                }

            }
        }

        return SUCCESS_JSON;
    }

    public String gestion() {
        if (pkCondominio == null) {
            getJsonResult().add("");
        } else {
            List<Object[]> deptoUsuarioListObj = getDaos().getDepartamentoUsuarioDao().administra(pkCondominio);

            if (deptoUsuarioListObj != null && !deptoUsuarioListObj.isEmpty()) {
                for (Object[] o : deptoUsuarioListObj) {

                    getJsonResult().add("[\"" + (o[0] == null ? "" : o[0].toString())
                            + "\", \"" + (o[1] == null ? "" : o[1].toString())
                            + "\", \"" + (o[2] == null ? "" : o[2].toString())
                            + "\", \"<a class='fancybox fancybox.iframe' href='/admin/detalleUsuario.action?usuario.id=" + (o[3] == null ? null : o[3].toString()) + "'> <span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-pencil  fa-stack-1x fa-inverse'></i></span></a>"
                             + "\", \"<a onclick='eliminaUsuario(" + (o[3] == null ? null : o[3].toString()) + ")'> <span class='fa-stack' style='color:red'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-trash-o  fa-stack-1x fa-inverse'></i></span></a>"
                            + " \"]");
                }

            }
        }

        return SUCCESS_JSON;
    }

    public String roles() {
        if (usuario.getId() == null) {
            getJsonResult().add("");
        } else {
            usuario = getDaos().getUsuarioDao().findById(usuario.getId());

            StringBuilder s = new StringBuilder();
            for (UsuarioPrivilegio p : usuario.getPrivilegios()) {
                getJsonResult().add("[\"" + p.getPrivilegio().getClave()
                        + "\", \"<a class='fancybox fancybox.iframe' href='#' onclick='eliminaRolUsuario(" + (p.getPrivilegio().getId()) + ")'> <span class='fa-stack' style='color:red'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-trash-o  fa-stack-1x fa-inverse'></i></span></a>"
                        + " \"]");
            }

        }

        return SUCCESS_JSON;
    }

    public String eliminaRol() {
        if (usuario.getId() == null && rolUsuario.getId() == null) {
            getJsonResult().add("[\"No se proporcionaron los datos correctos\"]");
        } else {
            try {
                getDaos().getUsuarioPrivilegioDao().borraPrivilegios(usuario, rolUsuario);
                getJsonResult().add("[\"OK\"]");
            } catch (Exception e) {
                e.printStackTrace();
                getJsonResult().add("[\"ERROR\"]");
            }

        }
        return SUCCESS_JSON;
    }
    
    /**
     * Elimina el usuario indicado
     * @return 
     */
    public String elimina() {
        if (usuario.getId() == null) {
            getJsonResult().add("[\"No se proporcionaron los datos necesarios para realizar la operación\"]");
        } else {
            
            usuario = getDaos().getUsuarioDao().findById(usuario.getId());
            //ELIMINAMOS DE LA RELACION USUARIO CONDOMINIO
            try{
                getDaos().getUsuarioCondominioDao().eliminaUsuarioCondominio(usuario);
                
            }catch(Exception e){
                e.printStackTrace();
                getJsonResult().add("[\"ERROR\"]");
                
            }
            //ELIMINAMOS ROLES
            try {
                getDaos().getUsuarioPrivilegioDao().borraPrivilegios(usuario);
                getJsonResult().add("[\"OK\"]");
            } catch (Exception e) {
                e.printStackTrace();
                getJsonResult().add("[\"ERROR\"]");
            }
            
            //ELIMINAMOS USUARIO
            try {
                getDaos().getUsuarioDao().delete(usuario);
                getJsonResult().add("[\"OK\"]");
            } catch (Exception e) {
                e.printStackTrace();
                getJsonResult().add("[\"ERROR\"]");
            }

        }
        return SUCCESS_JSON;
    }


    public Long getPkCondominio() {
        return pkCondominio;
    }

    public void setPkCondominio(Long pkCondominio) {
        this.pkCondominio = pkCondominio;
    }

    public List<DepartamentoUsuario> getDeptoUsuarioList() {
        return deptoUsuarioList;
    }

    public void setDeptoUsuarioList(List<DepartamentoUsuario> deptoUsuarioList) {
        this.deptoUsuarioList = deptoUsuarioList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Rol getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(Rol rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

}
