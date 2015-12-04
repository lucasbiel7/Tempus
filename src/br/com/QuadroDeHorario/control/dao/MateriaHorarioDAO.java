/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.Materia;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.model.util.DataHorario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class MateriaHorarioDAO extends GenericaDAO<MateriaHorario> {

    @Override
    public void excluir(MateriaHorario entity) {
        entity.setAtivo(false);
        for (MateriaHorarioAmbiente materiaHorarioAmbiente : new MateriaHorarioAmbienteDAO().pegarTodosPorMateriaHorario(entity)) {
            new MateriaHorarioAmbienteDAO().excluir(materiaHorarioAmbiente);
        }
        for (Aula aula : new AulaDAO().pegarPorMateria(entity)) {
            new AulaDAO().excluir(aula);
        }
        editar(entity);
    }

    public List<MateriaHorario> pegarTodosPorTurmaSemestreAno(Turma turma, DataHorario.Semestre semestre, int ano) {
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.turma", turma)).add(Restrictions.eq("materiaTurmaIntrutorSemestre.semestre", semestre)).add(Restrictions.eq("ano", ano)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorTurma(Turma turma) {
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.turma", turma)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorTurno(Turma turma) {
        List<Turma> turmas = new TurmaDAO().pegarPorNaoTurno(turma.getTurno());
        turmas.add(turma);
        entitys = criteria.add(Restrictions.in("materiaTurmaIntrutorSemestre.turma", turmas)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorTurmaMateria(Turma turma, Materia materia) {
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.turma", turma)).add(Restrictions.eq("materiaTurmaIntrutorSemestre.materia", materia)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorInstrutorNaoTurma(Usuario instrutor, Turma turma) {
        List<Turma> turmas = new TurmaDAO().pegarPorNaoTurno(turma.getTurno());
        turmas.add(turma);
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.instrutor", instrutor)).add(Restrictions.not(Restrictions.in("materiaTurmaIntrutorSemestre.turma", turmas))).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorInstrutorTurnoSemestreAno(Usuario instrutor, DataHorario.Turno turno, DataHorario.Semestre semestre, Integer ano) {
        List<Turma> turmas = new TurmaDAO().pegarPorTurno(turno != DataHorario.Turno.NOITE ? new DataHorario.Turno[]{turno, DataHorario.Turno.DIURNO} : new DataHorario.Turno[]{turno});
        if (turmas.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.in("materiaTurmaIntrutorSemestre.turma", turmas)).add(Restrictions.eq("materiaTurmaIntrutorSemestre.instrutor", instrutor)).add(Restrictions.eq("materiaTurmaIntrutorSemestre.semestre", semestre)).add(Restrictions.eq("ano", ano)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarPorMateria(Materia materia) {
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.materia", materia)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarPorTurma(Turma turma) {
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.turma", turma)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorInstrutor(Usuario instrutor) {
        entitys = criteria.add(Restrictions.eq("materiaTurmaIntrutorSemestre.instrutor", instrutor)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorario> pegarTodosPorAmbienteTurnoSemestreAno(Ambiente ambiente, DataHorario.Turno turno, DataHorario.Semestre semestre, int ano) {
        List<Turma> turmas = new TurmaDAO().pegarPorTurno(turno != DataHorario.Turno.NOITE ? new DataHorario.Turno[]{turno, DataHorario.Turno.DIURNO} : new DataHorario.Turno[]{turno});
        if (turmas.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(
                Restrictions.in("materiaTurmaIntrutorSemestre.turma", turmas)).
                add(Restrictions.eq("materiaTurmaIntrutorSemestre.semestre", semestre)).
                add(Restrictions.eq("ano", ano)).list();
        finalizarSession();
        List<MateriaHorarioAmbiente> materiaHorarioAmbientes = new MateriaHorarioAmbienteDAO().pegarTodosPorAmbiente(ambiente);
        entitys.removeIf((MateriaHorario t) -> {
            for (MateriaHorarioAmbiente materiaHorarioAmbiente : materiaHorarioAmbientes) {
                if (materiaHorarioAmbiente.getId().getMateriaHorario().equals(t)) {
                    return false;
                }
            }
            return true;
        });
        return entitys;
    }

}
