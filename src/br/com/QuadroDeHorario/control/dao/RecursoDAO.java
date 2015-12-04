/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.AmbienteRecursos;
import br.com.QuadroDeHorario.model.entity.MateriaRecursos;
import br.com.QuadroDeHorario.model.entity.Recurso;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class RecursoDAO extends GenericaDAO<Recurso> {

    @Override
    public void excluir(Recurso entity) {
        entity.setAtivo(false);
        for (AmbienteRecursos ambienteRecursos : new AmbienteRecursosDAO().pegarPorRecurso(entity)) {
            new AmbienteRecursosDAO().excluir(ambienteRecursos);
        }
        for (MateriaRecursos materiaRecursos : new MateriaRecursosDAO().pegarPorRecurso(entity)) {
            new MateriaRecursosDAO().excluir(materiaRecursos);
        }
        editar(entity);
    }

    public List<Recurso> pesquisarPorNome(String text) {
        entitys = criteria.add(Restrictions.like("nome", "%" + text + "%")).list();
        finalizarSession();
        return entitys;
    }

}
