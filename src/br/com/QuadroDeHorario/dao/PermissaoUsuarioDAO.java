/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Permissao;
import br.com.QuadroDeHorario.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class PermissaoUsuarioDAO extends GenericaDAO<PermissaoUsuario> {

    @Override
    public void excluir(PermissaoUsuario entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<PermissaoUsuario> pegarTodosPorUsuario(Usuario usuario) {
        entitys = criteria.add(Restrictions.eq("id.usuario", usuario)).list();
        finalizarSession();
        return entitys;
    }

    public PermissaoUsuario pegarTodosPorUsuarioPermissao(Usuario usuario, Permissao permissao) {
        entity = (PermissaoUsuario) criteria.add(Restrictions.eq("id.usuario", usuario)).add(Restrictions.eq("id.permissao", permissao)).uniqueResult();
        finalizarSession();
        return entity;
    }

}
