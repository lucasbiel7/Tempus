/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Curso;
import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class MateriaDAO extends GenericaDAO<Materia> {

    public MateriaDAO() {
        super();
        criteria.addOrder(Order.asc("nome"));
    }

    @Override
    public void excluir(Materia entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<Materia> pegarTodosPorCursoModulo(Curso curso, int modulo) {
        entitys = criteria.add(Restrictions.eq("curso", curso)).add(Restrictions.eq("modulo", modulo)).list();
        finalizarSession();
        return entitys;
    }

    public List<Materia> pegarTodosPorCurso(Curso curso) {
        entitys = criteria.add(Restrictions.eq("curso", curso)).list();
        finalizarSession();
        return entitys;
    }

    public List<Materia> pegarTodosPorNome(String nome) {
        entitys = criteria.add(Restrictions.like("nome", "%" + nome + "%")).list();
        finalizarSession();
        return entitys;
    }

}
