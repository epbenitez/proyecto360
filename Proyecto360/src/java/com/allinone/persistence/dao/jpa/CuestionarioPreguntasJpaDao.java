/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioPreguntasDao;
import com.allinone.persistence.model.CuestionarioPreguntas;

/**
 *
 * @author Patricia Benitez
 */
public class CuestionarioPreguntasJpaDao extends JpaDaoBase<CuestionarioPreguntas,Long> implements CuestionarioPreguntasDao  {

    public CuestionarioPreguntasJpaDao() {
        super(CuestionarioPreguntas.class);
    }

    
}
