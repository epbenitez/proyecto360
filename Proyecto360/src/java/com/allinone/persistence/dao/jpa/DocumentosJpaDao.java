package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.DocumentosDao;
import com.allinone.persistence.model.Documentos;
import java.util.List;

/**
 *
 * @author Victor Lozano
 */
public class DocumentosJpaDao extends JpaDaoBase<Documentos, Long> implements DocumentosDao {
    public DocumentosJpaDao() {
        super(Documentos.class);
    }
}