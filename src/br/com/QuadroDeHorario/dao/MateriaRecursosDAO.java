/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.entity.MateriaRecursos;
import br.com.QuadroDeHorario.entity.Recurso;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class MateriaRecursosDAO extends GenericaDAO<MateriaRecursos> {

    @Override
    public void excluir(MateriaRecursos entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<MateriaRecursos> pegarTodosPorMateria(Materia materia) {
        entitys = criteria.add(Restrictions.eq("id.materia", materia)).list();
        finalizarSession();
        return entitys;
    }

    public MateriaRecursos pegarPorMateriaRecurso(Materia materia, Recurso recurso) {
        entity = (MateriaRecursos) session.createCriteria(classe).add(Restrictions.eq("id.materia", materia)).add(Restrictions.eq("id.recurso", recurso)).uniqueResult();
        finalizarSession();
        return entity;
    }

    public List<MateriaRecursos> pegarPorRecurso(Recurso recurso) {
        entitys = criteria.add(Restrictions.eq("id.recurso", recurso)).list();
        session.close();
        return entitys;
    }
}
