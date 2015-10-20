/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.TokenCode;
import br.com.QuadroDeHorario.model.GenericaDAO;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class TokenCodeDAO extends GenericaDAO<TokenCode> {

    @Override
    public void excluir(TokenCode entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public TokenCode pegarToken(TokenCode tokenCode) {
        entity = (TokenCode) criteria.add(Restrictions.eq("usuario", tokenCode.getUsuario())).add(Restrictions.eq("token", tokenCode.getToken())).uniqueResult();
        finalizarSession();
        return entity;
    }

}
