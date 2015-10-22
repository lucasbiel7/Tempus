/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.EmprestaChave;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class EmprestaChaveDAO extends GenericaDAO<EmprestaChave> {

    @Override
    public void excluir(EmprestaChave entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<EmprestaChave> pegarPorAula(Aula aula) {
        entitys = criteria.add(Restrictions.eq("ambiente", aula.getAmbiente())).add(Restrictions.eq("usuario", aula.getMateriaHorario().getMateriaTurmaIntrutorSemestre().getInstrutor())).add(Restrictions.eq("dia", aula.getId().getDataAula())).list();
        finalizarSession();
        return entitys;
    }

    public List<EmprestaChave> pegarPorAmbiente(Ambiente ambiente) {
        entitys = criteria.add(Restrictions.eq("ambiente", ambiente)).list();
        session.close();
        return entitys;
    }

}
