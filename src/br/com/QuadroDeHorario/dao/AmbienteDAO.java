/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.AmbienteRecursos;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.entity.EmprestaChave;
import br.com.QuadroDeHorario.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class AmbienteDAO extends GenericaDAO<Ambiente> {

    @Override
    public void excluir(Ambiente entity) {
        entity.setAtivo(false);
        for (AmbienteRecursos ambienteRecursos : new AmbienteRecursosDAO().pegarPorAmbiente(entity)) {
            new AmbienteRecursosDAO().excluir(ambienteRecursos);
        }
        for (CalendarioAmbiente calendarioAmbiente : new CalendarioAmbienteDAO().pegarPorAmbiente(entity)) {
            new CalendarioAmbienteDAO().excluir(calendarioAmbiente);
        }
        for (MateriaHorarioAmbiente materiaHorarioAmbiente : new MateriaHorarioAmbienteDAO().pegarTodosPorAmbiente(entity)) {
            new MateriaHorarioAmbienteDAO().excluir(materiaHorarioAmbiente);
        }
        for (Aula aula : new AulaDAO().pegarPorAmbiente(entity)) {
            new AulaDAO().excluir(aula);
        }
        for (EmprestaChave emprestaChave : new EmprestaChaveDAO().pegarPorAmbiente(entity)) {
            new EmprestaChaveDAO().excluir(emprestaChave);
        }
        editar(entity);
    }

    public List<String> pegarTipoAmbiente() {
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("tipo"));
        criteria.setProjection(projectionList);
        List<String> tipo = criteria.list();
        finalizarSession();
        return tipo;
    }

    public List<Ambiente> pesquisarPorNome(String nome) {
        entitys = criteria.add(Restrictions.like("nome", "%" + nome + "%")).list();
        finalizarSession();
        return entitys;
    }

    public Ambiente pegarPorChave(String chave) {
        entity = (Ambiente) criteria.add(Restrictions.or(Restrictions.eq("chave", chave), Restrictions.eq("chaveReserva", chave))).uniqueResult();
        finalizarSession();
        return entity;
    }

}
