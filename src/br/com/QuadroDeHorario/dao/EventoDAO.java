/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Evento;
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
        entitys = criteria.add(Restrictions.eq("ano", ano)).add(Restrictions.eq("escolar", escolar)).list();
        finalizarSession();
        return entitys;
    }

}
