/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.Projeto;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.util.DataHorario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class TurmaDAO extends GenericaDAO<Turma> {

    @Override
    public void excluir(Turma entity) {
        entity.setAtivo(false);
        for (MateriaHorario materiaHorario : new MateriaHorarioDAO().pegarPorTurma(entity)) {
            new MateriaHorarioDAO().excluir(materiaHorario);
        }
        for (Aula aula : new AulaDAO().pegarTodosPorTurma(entity)) {
            new AulaDAO().excluir(aula);
        }
        editar(entity);
    }

    public List<Turma> pegarTodosPorCurso(Curso curso) {
        entitys = criteria.add(Restrictions.eq("curso", curso)).list();
        finalizarSession();
        return entitys;
    }

    public List<Turma> pegarTodasEntreData(Date dia) {
        entitys = criteria.add(Restrictions.le("inicio", dia)).add(Restrictions.ge("fim", dia)).list();
        finalizarSession();
        return entitys;
    }

    public List<Turma> pegarTodasEntreDataTurno(Date dia, DataHorario.Turno turno) {
        entitys = criteria.add(Restrictions.le("inicio", dia)).add(Restrictions.ge("fim", dia)).add(turno == DataHorario.Turno.NOITE ? Restrictions.eq("turno", turno) : Restrictions.in("turno", new DataHorario.Turno[]{turno, DataHorario.Turno.DIURNO})).list();
        finalizarSession();
        return entitys;
    }

    public List<Turma> pegarPorNaoTurno(DataHorario.Turno turno) {
        entitys = criteria.add(Restrictions.not(Restrictions.eq("turno", turno))).list();
        finalizarSession();
        return entitys;
    }

    public List<Turma> pegarPorTurno(DataHorario.Turno... turno) {
        if (turno.length == 0) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.in("turno", turno)).list();
        finalizarSession();
        return entitys;
    }

    public List<Turma> pesquisarPorNome(String nome) {
        entitys = criteria.add(Restrictions.like("descricao", "%" + nome + "%")).list();
        finalizarSession();
        return entitys;
    }

    public List<Turma> pegarPorProjeto(Projeto projeto) {
        entitys = criteria.add(Restrictions.eq("projeto", projeto)).list();
        finalizarSession();
        return entitys;
    }

    public Turma pegarPorTurmaPrincipal(Turma turma) {
        entity = (Turma) criteria.add(Restrictions.eq("turmaPrincipal", turma)).uniqueResult();
        finalizarSession();
        return entity;
    }

}
