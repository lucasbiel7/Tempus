/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Conteudo;
import br.com.QuadroDeHorario.model.GenericaDAO;

/**
 *
 * @author OCTI01
 */
public class ConteudoDAO extends GenericaDAO<Conteudo> {

    @Override
    public void excluir(Conteudo entity) {
        entity.setAtivo(false);
        editar(entity);
    }

}
