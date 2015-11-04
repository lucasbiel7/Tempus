/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.entity.MateriaHorario;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.util.DataHorario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class AulaDAO extends GenericaDAO<Aula> {

    @Override
    public void excluir(Aula entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public Aula pegarPorHorarioDiaTurma(DataHorario.Horario horario, Date dia, Turma turma) {
        if (turma != null) {
            entitys = session.getNamedQuery("Aula.pegarPorHorarioDiaTurma").setDate("dataAula", dia).setParameter("horario", horario).setEntity("turma", turma).list();
        }
        finalizarSession();
        if (entitys == null || entitys.isEmpty()) {
            return null;
        } else {
            return entitys.get(0);
        }
    }

    public List<Aula> pegarPorMateria(MateriaHorario materiaHorario) {
        entitys = criteria.add(Restrictions.eq("materiaHorario", materiaHorario)).addOrder(Order.asc("id.dataAula")).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> pegarPorDisciplinaTurma(Materia materia, Turma turma) {
        List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarTodosPorTurmaMateria(turma, materia);
        if (materiaHorarios.isEmpty()) {
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.in("materiaHorario", materiaHorarios)).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> validarAmbiente(Aula aula) {
        entitys = criteria.add(Restrictions.eq("ambiente", aula.getAmbiente())).add(Restrictions.eq("id.dataAula", aula.getId().getDataAula())).add(Restrictions.eq("id.horario", aula.getId().getHorario())).add(Restrictions.eq("id.turno", aula.getId().getTurno())).add(Restrictions.not(Restrictions.eq("id.turma", aula.getId().getTurma()))).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> validarInstrutor(Aula aula) {
        List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarTodosPorInstrutor(aula.getMateriaHorario().getMateriaTurmaIntrutorSemestre().getInstrutor());;
        if (materiaHorarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.eq("id.dataAula", aula.getId().getDataAula())).add(Restrictions.eq("id.turno", aula.getId().getTurno())).add(Restrictions.not(Restrictions.eq("id.turma", aula.getId().getTurma()))).add(Restrictions.in("materiaHorario", materiaHorarios)).list();
        finalizarSession();
        return entitys;
    }

    public Aula pegarPorHorarioDiaAmbiente(DataHorario.Horario horario, Date dia, Ambiente ambiente, DataHorario.Turno turno) {
        if (ambiente != null) {
            entitys = session.getNamedQuery("Aula.pegarPorHorarioDiaAmbiente").setDate("dataAula", dia).setParameter("horario", horario).setEntity("ambiente", ambiente).setParameter("turno", turno).list();
        }
        finalizarSession();
        if (entitys == null || entitys.isEmpty()) {
            return null;
        } else {
            return entitys.get(0);
        }
    }

    public Aula pegarPorDisciplinaUsuario(DataHorario.Horario horario, Date dia, Usuario usuario, DataHorario.Turno turno) {
        if (usuario != null) {
            entitys = session.getNamedQuery("Aula.pegarPorHorarioDiaUsuario").setDate("dataAula", dia).setParameter("horario", horario).setEntity("usuario", usuario).setParameter("turno", turno).list();
        }
        finalizarSession();
        if (entitys == null || entitys.isEmpty()) {
            return null;
        } else {
            return entitys.get(0);
        }
    }

    public List<Aula> pegarPorDiaInstrutor(Usuario usuario, Date date) {
        entitys = session.getNamedQuery("Aula.pegarPorDiaInstrutor").setParameter("usuario", usuario).setDate("dataAula", date).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> pegarPorDiaInstrutorTurnos(Usuario usuario, Date date, DataHorario.Turno... turnos) {
        List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarTodosPorInstrutor(usuario);
        if (materiaHorarios.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        if (turnos.length == 0) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.eq("id.dataAula", date)).add(Restrictions.in("id.turno", turnos)).add(Restrictions.in("materiaHorario", materiaHorarios)).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> pegarPorAmbienteDia(Date data, Ambiente ambiente) {
        entitys = criteria.add(Restrictions.eq("id.dataAula", data)).add(Restrictions.eq("ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> pegarPorDiaInstrutor(Usuario usuario, Date data, DataHorario.Turno turno) {
        entitys = session.getNamedQuery("Aula.pegarPorDiaInstrutorTurno").setParameter("usuario", usuario).setDate("dataAula", data).setParameter("turno", turno).list();
        finalizarSession();
        return entitys;
    }

    public Object[] mediaMensal(Usuario usuario, Date inicio, Date fim) {
        Object[] media = (Object[]) session.createQuery("SELECT datediff(:fim,:inicio),count(a) from Aula a where a.materiaHorario.materiaTurmaIntrutorSemestre.instrutor=:usuario AND a.id.dataAula BETWEEN :inicio AND :fim AND a.ativo=true").setParameter("usuario", usuario).setDate("inicio", inicio).setDate("fim", fim).uniqueResult();
        finalizarSession();
        return media;
    }

    public Aula pegarPorInstrutorTurnoDiaHorario(Usuario instrutor, DataHorario.Turno turno, Date dataAula, DataHorario.Horario horario) {
        entitys = session.getNamedQuery("Aula.pegarPorInstrutorTurnoDiaHorario").setParameter("instrutor", instrutor).setDate("dataAula", dataAula).setParameter("turno", turno).setParameter("horario", horario).list();
        finalizarSession();
        if (entitys.isEmpty()) {
            return null;
        } else {
            return entitys.get(0);
        }
    }

    public List<Aula> pegarPorAmbiente(Ambiente ambiente) {
        entitys = criteria.add(Restrictions.eq("ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }

    public Aula pegarPorHorarioDiaTurmaTurno(DataHorario.Horario horario, Date dia, Turma turma, DataHorario.Turno turno) {
        entitys = criteria.add(Restrictions.eq("id.horario", horario)).add(Restrictions.eq("id.turma", turma)).add(Restrictions.eq("id.dataAula", dia)).add(Restrictions.eq("id.turno", turno)).list();
        finalizarSession();
        if (entitys.isEmpty()) {
            return null;
        }
        return entitys.get(0);
    }

    public List<Aula> pegarPorAmbienteDia(Date data, Ambiente ambiente, DataHorario.Turno... turnos) {
        entitys = criteria.add(Restrictions.eq("id.dataAula", data)).add(Restrictions.eq("ambiente", ambiente)).add(Restrictions.in("id.turno", turnos)).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> pegarTodosPorTurma(Turma turma) {
        entitys = criteria.add(Restrictions.eq("id.turma", turma)).list();
        finalizarSession();
        return entitys;
    }

    public List<Aula> pegarPorMateriaAmbiente(MateriaHorario materiaHorario, Ambiente ambiente) {
        entitys = criteria.add(Restrictions.eq("materiaHorario", materiaHorario)).add(Restrictions.eq("ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }

}
