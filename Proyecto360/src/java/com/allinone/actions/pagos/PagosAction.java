package com.allinone.actions.pagos;

import com.allinone.actions.ajax.JSONAjaxAction;
import com.allinone.business.EstadoCuentaBO;
import com.allinone.persistence.model.AbonoDatosPago;
import com.allinone.persistence.model.AbonoTipo;
import com.allinone.persistence.model.Banco;
import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Usuario;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class PagosAction extends JSONAjaxAction {

    
    List<Condominio> condominiolist = new ArrayList<Condominio>();
    private List<AbonoTipo> abonoTipoList = new ArrayList<AbonoTipo>();
    private List<Banco> bancoList = new ArrayList<Banco>();
    private List<Concepto> conceptosList = new ArrayList<Concepto>();
    private Long departamentoId;
    private DepartamentoUsuario deptoUsuario;
    
    /**
     * Formulario de búsqueda de departamento
     *
     * @return
     */
    public String busquedaDepartamento() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominiolist = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominiolist.add(condominio);
        } else {
            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return INPUT;
    }

    /**
     * Formulario con campos para el registro del pago
     *
     * @return
     */
    public String registroPago() {

        abonoTipoList = getDaos().getAbonoTipoDao().findAll();
        bancoList = getDaos().getBancoDao().findAll();
        conceptosList = getDaos().getConceptoDao().findAll();

        if (departamentoId == null || departamentoId.equals("")) {
            addActionError("No se ha proporcionado el departamento");
            return INPUT;
        }

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        deptoUsuario = bo.getDepartamentoUsuario(departamentoId);

        return INPUT;
    }
    
    /**
     * Muestra un listado por departamento de los pagos registrados desde el sistema
     * @return 
     */
    public String administra(){
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominiolist = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominiolist.add(condominio);
        } else {
            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return INPUT;
    }
    /**
     * Muestra el desglose de conceptos en los que se aplicó un abono específico
     * @return 
     */
    public String desglose(){
        return INPUT;
    }

    public List<AbonoTipo> getAbonoTipoList() {
        return abonoTipoList;
    }

    public void setAbonoTipoList(List<AbonoTipo> abonoTipoList) {
        this.abonoTipoList = abonoTipoList;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public DepartamentoUsuario getDeptoUsuario() {
        return deptoUsuario;
    }

    public void setDeptoUsuario(DepartamentoUsuario deptoUsuario) {
        this.deptoUsuario = deptoUsuario;
    }

    public List<Banco> getBancoList() {
        return bancoList;
    }

    public void setBancoList(List<Banco> bancoList) {
        this.bancoList = bancoList;
    }

    public List<Concepto> getConceptosList() {
        return conceptosList;
    }

    public void setConceptosList(List<Concepto> conceptosList) {
        this.conceptosList = conceptosList;
    }

    public List<Condominio> getCondominiolist() {
        return condominiolist;
    }

    public void setCondominiolist(List<Condominio> condominiolist) {
        this.condominiolist = condominiolist;
    }


}
