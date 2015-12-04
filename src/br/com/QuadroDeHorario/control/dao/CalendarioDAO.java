/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Calendario;
import br.com.QuadroDeHorario.model.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.model.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.model.entity.Evento;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

public class CalendarioDAO extends GenericaDAO<Calendario> {

    @Override
    public void excluir(Calendario entity) {
        entity.setAtivo(false);
        for (CalendarioUsuario calendarioUsuario : new CalendarioUsuarioDAO().pegarTodosPorCalendario(entity)) {
            new CalendarioUsuarioDAO().excluir(calendarioUsuario);
        }
        for (CalendarioAmbiente calendarioAmbiente : new CalendarioAmbienteDAO().pegarTodosPorCalendario(entity)) {
            new CalendarioAmbienteDAO().excluir(calendarioAmbiente);
        }
        editar(entity);
    }

    public List<Calendario> pegarTodosPorData(Date data) {
        entitys = criteria.add(Restrictions.eq("id.dataAcontecimento", data)).list();
        finalizarSession();
        return entitys;
    }

    public List<Calendario> pegarPorEventoData(Evento evento, Date dataAcontecimento) {
        entitys = criteria.add(Restrictions.eq("id.evento", evento)).add(Restrictions.eq("id.dataAcontecimento", dataAcontecimento)).list();
        finalizarSession();
        return entitys;
    }

    public List<Calendario> pegarTodosPorData(Date data, boolean escolar) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        List<Evento> eventos = new EventoDAO().pegarTodosPorAnoEscola(calendar.get(Calendar.YEAR), escolar);
        if (eventos.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.eq("id.dataAcontecimento", data)).add(Restrictions.in("id.evento", eventos)).list();
        finalizarSession();
        return entitys;
    }

    public List<Calendario> pegarTodosPorData(Date data, boolean escolar, boolean letivo) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        List<Evento> eventos = new EventoDAO().pegarTodosPorAnoEscola(calendar.get(Calendar.YEAR), escolar, letivo);
        if (eventos.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.eq("id.dataAcontecimento", data)).add(Restrictions.in("id.evento", eventos)).list();
        finalizarSession();
        return entitys;
    }

    public List<Calendario> pegarPorEvento(Evento evento) {
        entitys = criteria.add(Restrictions.eq("id.evento", evento)).list();
        finalizarSession();
        return entitys;
    }

}
