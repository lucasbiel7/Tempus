/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.Calendario;
import br.com.QuadroDeHorario.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.util.DataHorario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class CalendarioAmbienteDAO extends GenericaDAO<CalendarioAmbiente> {

    @Override
    public void excluir(CalendarioAmbiente entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<CalendarioAmbiente> pegarTodosPorCalendario(Calendario calendario) {
        entitys = criteria.add(Restrictions.eq("id.calendario", calendario)).list();
        finalizarSession();
        return entitys;
    }

    public List<CalendarioAmbiente> pegarTodosPorData(Date dataSelecionada) {
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(dataSelecionada);
        if (calendarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.in("id.calendario", calendarios)).list();
        finalizarSession();
        return entitys;
    }

    public List<CalendarioAmbiente> pegarTodosPorDataAmbiente(Date data, Ambiente ambiente) {
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(data);
        if (calendarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.in("id.calendario", calendarios)).add(Restrictions.eq("id.ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }

    public List<CalendarioAmbiente> pegarTodosPorDataAmbienteTurno(Date data, Ambiente ambiente, DataHorario.Turno... turnos) {
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(data);
        if (calendarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        for (DataHorario.Turno turno : turnos) {
            criteria.add(Restrictions.eq(turno.toString().toLowerCase().replace("ã", "a"), true));
        }
        entitys = criteria.add(Restrictions.in("id.calendario", calendarios)).add(Restrictions.eq("id.ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }

    public List<CalendarioAmbiente> pegarPorAmbiente(Ambiente ambiente) {
        entitys = criteria.add(Restrictions.eq("id.ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }
}
