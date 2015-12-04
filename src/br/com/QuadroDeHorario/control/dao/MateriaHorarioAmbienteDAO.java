/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class MateriaHorarioAmbienteDAO extends GenericaDAO<MateriaHorarioAmbiente> {

    @Override
    public void excluir(MateriaHorarioAmbiente entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<MateriaHorarioAmbiente> pegarTodosPorMateriaHorario(MateriaHorario materiaHorario) {
        entitys = criteria.add(Restrictions.eq("id.materiaHorario", materiaHorario)).list();
        finalizarSession();
        return entitys;
    }

    public List<MateriaHorarioAmbiente> pegarTodosSaveDelete(MateriaHorario materiaHorario) {
        entitys = session.createCriteria(classe).add(Restrictions.eq("id.materiaHorario", materiaHorario)).list();
        finalizarSession();
        return entitys;
    }

    public MateriaHorarioAmbiente pegarTodosPorMateriaHorarioNumero(MateriaHorario materiaHorario, int numero) {
        entity = (MateriaHorarioAmbiente) criteria.add(Restrictions.eq("id.materiaHorario", materiaHorario)).add(Restrictions.eq("numero", numero)).uniqueResult();
        finalizarSession();
        return entity;
    }

    public List<MateriaHorarioAmbiente> pegarTodosPorAmbiente(Ambiente ambiente) {
        entitys = criteria.add(Restrictions.eq("id.ambiente", ambiente)).list();
        finalizarSession();
        return entitys;
    }
    
}
