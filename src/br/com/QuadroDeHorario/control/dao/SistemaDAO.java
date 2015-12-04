/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Atualizacao;
import br.com.QuadroDeHorario.model.entity.Sistema;
import br.com.QuadroDeHorario.model.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class SistemaDAO extends GenericaDAO<Sistema> {

    @Override
    public void excluir(Sistema entity) {
        entity.setAtivo(false);
        for (VariaveisDoSistema variaveisDoSistema : new VariaveisDoSistemaDAO().pegarTodosPorSistema(entity)) {
            new VariaveisDoSistemaDAO().excluir(variaveisDoSistema);
        }
        for (Atualizacao atualizacao : new AtualizacaoDAO().pegarTodosPorSistema(entity)) {
            new AtualizacaoDAO().excluir(atualizacao);
        }
        editar(entity);
    }

    public Sistema pegarPorNome(String nome) {
        entity = (Sistema) criteria.add(Restrictions.eq("nome", nome)).uniqueResult();
        finalizarSession();
        return entity;
    }

}
