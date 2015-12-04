/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Entidade;
import br.com.QuadroDeHorario.model.entity.Unidade;
import br.com.QuadroDeHorario.model.GenericaDAO;

/**
 *
 * @author OCTI01
 */
public class EntidadeDAO extends GenericaDAO<Entidade> {

    @Override
    public void excluir(Entidade entity) {
        entity.setAtivo(false);
        for (Unidade unidade : new UnidadeDAO().pegarTodosPorEntidade(entity)) {
            new UnidadeDAO().excluir(unidade);
        }
        editar(entity);
    }

}
