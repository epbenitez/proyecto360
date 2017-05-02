package com.allinone.persistence.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Interfaz para ejecutar operaciones CRUD
 *
 * @param <T>
 * @param <Id>
 * @author Patricia Benitez
 */
public interface DaoBase<T, Id extends Serializable> {

    public T save(T obj);

    public T update(T obj);

    public void delete(T obj);

    public boolean contains(T obj);

    public List<T> findAll();

    public T findById(Id id);

    public List<T> executeQuery(String jpql, Object... values);

    public EntityManager getEntityManager();

    public List executeQueryObject(String jpql, Object... values);

}
