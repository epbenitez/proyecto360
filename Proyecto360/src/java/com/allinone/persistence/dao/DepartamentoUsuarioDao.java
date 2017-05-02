package com.allinone.persistence.dao;

import com.allinone.domain.AvisoCobro;
import com.allinone.persistence.model.DepartamentoUsuario;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public interface DepartamentoUsuarioDao extends DaoBase<DepartamentoUsuario,Long>{

    public DepartamentoUsuario findByUserId(Long id);
    public DepartamentoUsuario findByDepartamentoId(Long id);
    public List<Object[]> findByCondominioId(Long condominioId);
    public List<AvisoCobro> encuentraDatosDepartamentoUsuario(Long torreId, Long condominioId, Long departamentoId);
    public List<Object[]> administra(Long condominioId);
    
}
