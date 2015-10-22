/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Curso;
import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.entity.TipoDoCurso;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class CursoDAO extends GenericaDAO<Curso> {

    public CursoDAO() {
        super();
        criteria.addOrder(Order.asc("nome"));
    }

    @Override
    public void excluir(Curso entity) {
        entity.setAtivo(false);
        for (Materia materia : new MateriaDAO().pegarTodosPorCurso(entity)) {
            new MateriaDAO().excluir(materia);
        }
        for (Turma turma : new TurmaDAO().pegarTodosPorCurso(entity)) {
            new TurmaDAO().excluir(turma);
        }
        editar(entity);
    }

    public List<Curso> pesquisarPorNome(String text) {
        entitys = criteria.add(Restrictions.like("nome", "%" + text + "%")).list();
        finalizarSession();
        return entitys;
    }

    public List<Curso> pegarTodosPorTipoDoCurso(TipoDoCurso tipoDoCurso) {
        entitys = criteria.add(Restrictions.eq("tipoDoCurso", tipoDoCurso)).list();
        finalizarSession();
        return entitys;
    }

}
