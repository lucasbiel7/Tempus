/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Projeto;
import br.com.QuadroDeHorario.model.GenericaDAO;

/**
 *
 * @author OCTI01
 */
public class ProjetoDAO extends GenericaDAO<Projeto> {

    @Override
    public void excluir(Projeto entity) {
        entity.setAtivo(false);
        editar(entity);
    }

}
