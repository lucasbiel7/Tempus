/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.ObservacaoAula;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.Date;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class ObservacaoAulaDAO extends GenericaDAO<ObservacaoAula> {

    @Override
    public void excluir(ObservacaoAula entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<ObservacaoAula> pegarPorDiaTurma(Date dia, Turma turma) {
        entitys = criteria.add(Restrictions.eq("dia", dia)).add(Restrictions.eq("turma", turma)).list();
        finalizarSession();
        return entitys;
    }
}
