/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Grupo;
import br.com.QuadroDeHorario.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class GrupoDAO extends GenericaDAO<Grupo> {

    @Override
    public void excluir(Grupo entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public Grupo pegarGrupo(String nome) {
        entity = (Grupo) criteria.add(Restrictions.eq("descricao", nome)).uniqueResult();
        finalizarSession();
        return entity;
    }

}
