/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.TipoDoCurso;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class TipoDoCursoDAO extends GenericaDAO<TipoDoCurso> {

    @Override
    public void excluir(TipoDoCurso entity) {
        entity.setAtivo(false);
        for (Curso curso : new CursoDAO().pegarTodosPorTipoDoCurso(entity)) {
            new CursoDAO().excluir(curso);
        }
        editar(entity);
    }

    public List<TipoDoCurso> pegarPorPesquisa(String descricao) {
        entitys = criteria.add(Restrictions.like("descricao", "%" + descricao + "%")).list();
        finalizarSession();
        return entitys;
    }

}
