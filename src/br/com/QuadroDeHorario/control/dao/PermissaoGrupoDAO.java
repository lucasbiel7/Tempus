/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Grupo;
import br.com.QuadroDeHorario.model.entity.Permissao;
import br.com.QuadroDeHorario.model.entity.PermissaoGrupo;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PermissaoGrupoDAO extends GenericaDAO<PermissaoGrupo> {

    @Override
    public void excluir(PermissaoGrupo entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<PermissaoGrupo> pegarTodosPorGrupo(Grupo grupo) {
        entitys = criteria.add(Restrictions.eq("id.grupo", grupo)).list();
        finalizarSession();
        return entitys;
    }

    public PermissaoGrupo pegarPorGrupoPermissao(Grupo grupo, Permissao permissao) {
        entity = (PermissaoGrupo) criteria.add(Restrictions.eq("id.grupo", grupo)).add(Restrictions.eq("id.permissao", permissao)).uniqueResult();
        finalizarSession();
        return entity;
    }
}
