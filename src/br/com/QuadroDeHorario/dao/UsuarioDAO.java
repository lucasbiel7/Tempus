/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Grupo;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.util.DataHorario;
import java.util.List;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class UsuarioDAO extends GenericaDAO<Usuario> {

    public UsuarioDAO() {
        super();
        criteria.addOrder(Order.asc("nome"));
    }

    @Override
    public void excluir(Usuario entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public Usuario login(Usuario usuario) {
        entity = (Usuario) criteria.add(Restrictions.or(Restrictions.eq("login", usuario.getLogin()), Restrictions.eq("email", usuario.getLogin()))).add(Restrictions.eq("senha", usuario.getSenha())).uniqueResult();
        finalizarSession();
        return entity;
    }

    public Usuario pegarPorLogin(Usuario usuario) {
        entity = (Usuario) criteria.add(Restrictions.or(Restrictions.eq("login", usuario.getLogin()), Restrictions.eq("email", usuario.getLogin()))).uniqueResult();
        finalizarSession();
        return entity;
    }

    public List<Usuario> pegarPorGrupo(Grupo grupo) {
        entitys = criteria.add(Restrictions.eq("grupo", grupo)).list();
        finalizarSession();
        return entitys;
    }

    public List<Usuario> pegarPorNome(String nome) {
        entitys = criteria.add(Restrictions.like("nome", "%" + nome + "%")).list();
        finalizarSession();
        return entitys;
    }

    public Usuario pegarPorEmail(Usuario usuario) {
        entity = (Usuario) criteria.add(Restrictions.eq("email", usuario.getEmail())).uniqueResult();
        finalizarSession();
        return entity;
    }

    public List<Usuario> pegarPorGrupoTurno(Grupo grupo, DataHorario.Turno turno) {
        entitys = criteria.add(Restrictions.eq("grupo", grupo)).add(Restrictions.eq(turno == DataHorario.Turno.manha ? "manha" : turno == DataHorario.Turno.tarde ? "tarde" : "noite", true)).list();
        finalizarSession();
        return entitys;
    }

    public Usuario pegarPorCartao(String parte) {
        entity = (Usuario) criteria.add(Restrictions.eq("cartao", parte)).uniqueResult();
        session.close();
        return entity;
    }
}
