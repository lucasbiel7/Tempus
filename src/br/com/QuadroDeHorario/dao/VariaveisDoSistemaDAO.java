/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Sistema;
import br.com.QuadroDeHorario.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class VariaveisDoSistemaDAO extends GenericaDAO<VariaveisDoSistema> {

    public VariaveisDoSistema pegarPorNome(String nome) {
        entity = (VariaveisDoSistema) criteria.add(Restrictions.eq("nome", nome)).uniqueResult();
        finalizarSession();
        return entity;
    }

    public List<VariaveisDoSistema> pegarTodosPorSistema(Sistema sistema) {
        entitys = criteria.add(Restrictions.eq("sistema", sistema)).list();
        finalizarSession();
        return entitys;
    }

}
