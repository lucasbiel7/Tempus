/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Calendario;
import br.com.QuadroDeHorario.model.entity.Evento;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class EventoDAO extends GenericaDAO<Evento> {

    @Override
    public void excluir(Evento entity) {
        entity.setAtivo(false);
        for (Calendario calendario : new CalendarioDAO().pegarPorEvento(entity)) {
            new CalendarioDAO().excluir(calendario);
        }
        editar(entity);
    }

    public List<Evento> pegarTodosPorAno(int ano) {
        entitys = criteria.add(Restrictions.eq("ano", ano)).list();
        finalizarSession();
        return entitys;
    }

    public List<Evento> pegarTodosPorAnoEscola(int ano, boolean escolar) {
        entitys = criteria.add(Restrictions.eq("ano", ano)).add(Restrictions.eq("escolar", escolar)).list();
        finalizarSession();
        return entitys;
    }

    public List<Evento> pegarTodosPorAnoEscola(int ano, boolean escolar, boolean letivo) {
        entitys = criteria.add(Restrictions.eq("ano", ano)).add(Restrictions.eq("escolar", escolar)).add(Restrictions.eq("letivo", letivo)).list();
        finalizarSession();
        return entitys;
    }

}
