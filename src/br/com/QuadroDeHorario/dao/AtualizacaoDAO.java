/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Atualizacao;
import br.com.QuadroDeHorario.entity.Sistema;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.util.ParametrosBanco;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class AtualizacaoDAO extends GenericaDAO<Atualizacao> {

    @Override
    public void excluir(Atualizacao entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public Atualizacao pegarPorSistema(Sistema sistema) {
        entitys = criteria.add(Restrictions.eq("sistema", sistema)).add(Restrictions.gt("version", ParametrosBanco.VERSAO)).addOrder(Order.desc("version")).list();
        finalizarSession();
        if (entitys.isEmpty()) {
            return null;
        }
        return entitys.get(0);
    }

    public List<Atualizacao> pegarTodosPorSistema(Sistema sistema) {
        entitys = criteria.add(Restrictions.eq("sistema", sistema)).list();
        session.close();
        return entitys;
    }

}
