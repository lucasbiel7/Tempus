/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

import br.com.QuadroDeHorario.control.Banco;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.SQLGrammarException;

/**
 *
 * @author OCTI01
 * @param <Entity>
 */
public class GenericaDAO<Entity> implements DAO<Entity> {

    protected Session session;
    protected Entity entity;
    protected List<Entity> entitys;
    protected Class<Entity> classe;
    protected Criteria criteria;

    public GenericaDAO() {
        session = Banco.getSessionFactory().openSession();
        if (!getClass().getGenericSuperclass().equals(Object.class)) {
            classe = (Class<Entity>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            criteria = session.createCriteria(classe).add(Restrictions.eq("ativo", true));
        }
    }

    @Override
    public void cadastrar(Entity entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        finalizarSession();
    }

    @Override
    public void editar(Entity entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        finalizarSession();
    }

    @Override
    public void excluir(Entity entity) {
        session.beginTransaction();
        try {
            session.delete(entity);
            session.getTransaction().commit();
        } catch (SQLGrammarException e) {
            Mensagem.showError("Sem permissão", "Você não tem permissão para utilizar essa função\n"
                    + "do sistema!É necessário ser administrador da base de dados para excluir \n"
                    + "qualquer registro!");
        } finally {
            finalizarSession();
        }
    }

    public List<Entity> pegarTodos() {
        entitys = criteria.list();
        finalizarSession();
        return entitys;
    }

    public List<Entity> pegarTodosLixeira() {
        entitys = session.createCriteria(classe).add(Restrictions.eq("ativo", false)).list();
        finalizarSession();
        return entitys;
    }

    public Entity pegarPorId(Serializable serializable) {
        entity = (Entity) session.get(classe, serializable);
        finalizarSession();
        return entity;
    }

    public Date dataAtual() {
        Date data = (Date) session.createSQLQuery("SELECT now()").uniqueResult();
        finalizarSession();
        return data;
    }

    protected void finalizarSession() {
        session.flush();
        session.close();
    }
}
