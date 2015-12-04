/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.AmbienteRecursos;
import br.com.QuadroDeHorario.model.entity.Recurso;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class AmbienteRecursosDAO extends GenericaDAO<AmbienteRecursos> {

    @Override
    public void excluir(AmbienteRecursos entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<AmbienteRecursos> pegarPorAmbiente(Ambiente ambiente) {
        entitys = session.createCriteria(classe).add(Restrictions.eq("id.ambiente", ambiente)).list();
        session.close();
        return entitys;
    }

    public List<AmbienteRecursos> pegarPorRecurso(Recurso recurso) {
        entitys = session.createCriteria(classe).add(Restrictions.eq("id.recurso", recurso)).list();
        session.close();
        return entitys;
    }
}
