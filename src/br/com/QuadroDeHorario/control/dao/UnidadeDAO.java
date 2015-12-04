/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Entidade;
import br.com.QuadroDeHorario.model.entity.Unidade;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class UnidadeDAO extends GenericaDAO<Unidade> {

    @Override
    public void excluir(Unidade entity) {
        for (Usuario usuario : new UsuarioDAO().pegarPorUnidade(entity)) {
            new UsuarioDAO().excluir(usuario);
        }
        entity.setAtivo(false);
        editar(entity);
    }

    public List<Unidade> pegarTodosPorEntidade(Entidade entidade) {
        entitys = criteria.add(Restrictions.eq("entidade", entidade)).list();
        finalizarSession();
        return entitys;
    }

}
