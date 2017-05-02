/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioRespuestasDao;
import com.allinone.persistence.model.CuestionarioRespuestas;

/**
 *
 * @author Patricia Benitez
 */
public class CuestionarioRespuestasJpaDao extends JpaDaoBase<CuestionarioRespuestas,Long> implements CuestionarioRespuestasDao  {

    public CuestionarioRespuestasJpaDao() {
        super(CuestionarioRespuestas.class);
    }

   
}
