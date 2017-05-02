package com.allinone.persistence.dao.jpa;

import com.allinone.business.BitacoraBO;
import com.allinone.persistence.dao.DaoBase;
import com.allinone.persistence.model.Accion;
import com.allinone.persistence.model.BaseEntity;
import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
import com.opensymphony.xwork2.ActionContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Implementacion para ejecutar operaciones CRUD
 *
 * @author Patricia Benitez
 */
@Transactional
public class JpaDaoBase<T extends BaseEntity, Id extends Serializable> implements DaoBase<T, Id> {

    protected Class<T> clase;
    protected EntityManager entityManager;

    /**
     * Obtiene el valor de la variable entityManager
     *
     * @return el valor de la variable entityManager
     */
    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Establece el valor de la variable entityManager
     *
     * @param entityManager nuevo valor de la variable entityManager
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public JpaDaoBase(Class<T> clase) {
        this.clase = clase;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T obj) {
        entityManager.persist(obj); //Esta línea se deja, en caso de que se qiera comentar la Bitacora
        Usuario usuario = (Usuario)ActionContext.getContext().getSession().get("usuario");
        String urlAction = (String) ActionContext.getContext().getSession().get("urlAction");
        BitacoraBO bitacoraBO = new BitacoraBO(getDaos());
        bitacoraBO.saveBitacora(usuario,
                obj.getClass().getSimpleName(),
                obj.getId() == null ? "" : obj.getId().toString(),
                urlAction,
                Accion.ALTA);
        entityManager.flush();//Esta línea se deja, en caso de que se qiera comentar la Bitacora
        return obj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T obj) {
        // Obtiene valor anterior del objeto. Solo para registrar en bitacora
        T old = entityManager.find(clase, obj == null ? "" : obj.getId());
        if (old == null) {
            return save(obj);
        }
        Usuario usuario = (Usuario)ActionContext.getContext().getSession().get("usuario");
        String urlAction = (String) ActionContext.getContext().getSession().get("urlAction");
        BitacoraBO bitacoraBO = new BitacoraBO(getDaos());

//        String oldObject = old.toString(); 
        T re = entityManager.merge(obj); //Esta línea se deja, en caso de que se qiera comentar la Bitacora
        bitacoraBO.saveBitacora(usuario,
                re.getClass().getSimpleName(),
                re.getId() == null ? "" : re.getId().toString(),
                urlAction,
                Accion.MODIFICACION);
        entityManager.flush(); //Esta línea se deja, en caso de que se qiera comentar la Bitacora
        return re;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(T obj) {
//        T old = entityManager.find(clase, obj.getId());
        BitacoraBO bitacoraBO = new BitacoraBO(getDaos());
        Usuario usuario = (Usuario)ActionContext.getContext().getSession().get("usuario");
//        String urlAction = (String) ActionContext.getContext().getSession().get("urlAction");
        entityManager.remove(entityManager.contains(obj) ? obj : entityManager.merge(obj)); //Esta línea se deja, en caso de que se qiera comentar la Bitacora
        bitacoraBO.saveBitacora(usuario,
                obj.getClass().getSimpleName(),
                obj.getId().toString(),
                obj.toString(),
                Accion.BAJA);
        entityManager.flush(); //Esta línea se deja, en caso de que se qiera comentar la Bitacora
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T obj) {
        return entityManager.contains(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        return entityManager.createQuery("select a from " + clase.getSimpleName() + " a").getResultList();
    }
    
    

    /**
     * {@inheritDoc}
     */
    @Override
    public T findById(Id id) {
        return entityManager.find(clase, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> executeQuery(String jpql, Object... values) {
        Query q = entityManager.createQuery(jpql);
        int i = 1;
        for (Object object : values) {
            q.setParameter(i, object);
            i++;
        }
        List<T> result = (List<T>) q.getResultList();
        return result;
    }

    /**
     *
     * @param jpql
     * @param mapa
     * @param maximoResultados
     * @return
     */
    public List<T> executeQuery(String jpql, Map<String, Object> mapa, Integer maximoResultados) {
        Query q = entityManager.createQuery(jpql);
        List<T> result;
        
        for (Map.Entry<String, Object> entry : mapa.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }

        if(maximoResultados==null){
            result = (List<T>) q.getResultList();
        }else{
            result = (List<T>) q.setMaxResults(maximoResultados).getResultList();
        }
        
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Object[]> executeNativeQuery(String jpql) {
        Query q = entityManager.createNativeQuery(jpql);

        return q.getResultList();
    }

    public List<T> executeQuery(int max, String jpql, Object... values) {
        Query q = entityManager.createQuery(jpql);
        int i = 1;
        for (Object object : values) {
            q.setParameter(i, object);
            i++;
        }
        q.setMaxResults(max);
        List<T> result = (List<T>) q.getResultList();
        return result;
    }

    public T executeSingleQuery(String jpql, Object... values) {
        Query q = entityManager.createQuery(jpql);
        int i = 1;
        for (Object object : values) {
            q.setParameter(i, object);
            i++;
        }
        T result = (T) q.getSingleResult();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List executeQueryObject(String jpql, Object... values) {
        Query q = entityManager.createQuery(jpql);
        int i = 1;
        for (Object object : values) {
            q.setParameter(i, object);
            i++;
        }
        List result = q.getResultList();
        return result;
    }
    
    public List executeQueryObject(String jpql, Map<String, Object> mapa) {
        Query q = entityManager.createQuery(jpql);

        for (Map.Entry<String, Object> entry : mapa.entrySet()) {
            q.setParameter(entry.getKey(), entry.getValue());
        }

        List result = q.getResultList();
        return result;
    }

    public Service getDaos() {
        Service svc = null;
        if (svc == null) {
            try {
                ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                svc = (Service) applicationContext.getBean("service");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return svc;
    }


}
