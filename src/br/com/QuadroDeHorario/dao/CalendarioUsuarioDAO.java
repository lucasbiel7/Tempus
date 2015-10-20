/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Calendario;
import br.com.QuadroDeHorario.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.entity.Usuario;
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
public class CalendarioUsuarioDAO extends GenericaDAO<CalendarioUsuario> {

    @Override
    public void excluir(CalendarioUsuario entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<CalendarioUsuario> pegarTodosPorCalendario(Calendario calendario) {
        entitys = criteria.add(Restrictions.eq("id.calendario", calendario)).list();
        finalizarSession();
        return entitys;
    }

    public List<CalendarioUsuario> pegarTodosPorUsuarioData(Usuario usuario, Date data) {
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(data);
        if (calendarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.eq("id.usuario", usuario)).add(Restrictions.in("id.calendario", calendarios)).list();
        finalizarSession();
        return entitys;
    }

    public List<CalendarioUsuario> pegarTodosPorUsuarioData(Usuario usuario, Date dataAula, DataHorario.Turno... turnos) {
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(dataAula);
        if (calendarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        for (DataHorario.Turno turno : turnos) {
            criteria.add(Restrictions.eq(turno.toString().toLowerCase().replace("ã", "a"), true));
        }
        entitys = criteria.add(Restrictions.eq("id.usuario", usuario)).add(Restrictions.in("id.calendario", calendarios)).list();
        finalizarSession();
        return entitys;
    }
}
