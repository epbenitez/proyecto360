package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CondominioDao;
import com.allinone.persistence.model.Condominio;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class CondominioJpaDao extends JpaDaoBase<Condominio, Long> implements CondominioDao {

    public CondominioJpaDao() {
        super(Condominio.class);
    }

    @Override
    public List<Condominio> findBy(Long torreId) {

        String jpql = "SELECT DISTINCT d.condominio FROM Departamento d WHERE  d.torre.id = ?1 ";
        List<Condominio> deptoList = executeQuery(jpql, torreId);

        return deptoList == null || deptoList.isEmpty() ? null : deptoList;
    }

    @Override
    public List<Condominio> condominiosPorUsuario(Long usuarioId) {

        String jpql = "SELECT DISTINCT d.condominio FROM UsuarioCondominio d WHERE  d.usuario.id = ?1 ";
        List<Condominio> condominioList = executeQuery(jpql, usuarioId);

        return condominioList == null || condominioList.isEmpty() ? null : condominioList;
    }

    @Override
    public List<Condominio> findByName(String nombre) {
        String jpql = "SELECT c FROM Condominio c WHERE  c.nombre like ?1 ";
        List<Condominio> condoList = executeQuery(jpql, nombre);

        return condoList == null || condoList.isEmpty() ? null : condoList;
    }
    
    @Override
    public Condominio findUniqueByName(String nombre) {
        List<Condominio> condoList = findByName(nombre);

        return condoList == null || condoList.isEmpty() ? null : condoList.get(0);
    }

}
