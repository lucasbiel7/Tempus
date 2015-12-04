/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.entity.Grupo;
import br.com.QuadroDeHorario.model.entity.PermissaoGrupo;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class GrupoDAO extends GenericaDAO<Grupo> {

    @Override
    public void excluir(Grupo entity) {
        entity.setAtivo(false);
        for (PermissaoGrupo permissaoGrupo : new PermissaoGrupoDAO().pegarTodosPorGrupo(entity)) {
            new PermissaoGrupoDAO().excluir(permissaoGrupo);
        }
        for (Usuario usuario : new UsuarioDAO().pegarPorGrupo(entity)) {
            new UsuarioDAO().excluir(usuario);
        }
        editar(entity);
    }

    public Grupo pegarGrupo(String nome) {
        entity = (Grupo) criteria.add(Restrictions.eq("descricao", nome)).uniqueResult();
        finalizarSession();
        return entity;
    }

}
