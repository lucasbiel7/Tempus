/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.dao;

import br.com.QuadroDeHorario.entity.Curso;
import br.com.QuadroDeHorario.entity.TipoDoCurso;
import br.com.QuadroDeHorario.model.GenericaDAO;

/**
 *
 * @author OCTI01
 */
public class TipoDoCursoDAO extends GenericaDAO<TipoDoCurso> {

    @Override
    public void excluir(TipoDoCurso entity) {
        entity.setAtivo(false);
        for (Curso curso : new CursoDAO().pegarTodosPorTipoDoCurso(entity)) {
            new CursoDAO().excluir(curso);
        }
        editar(entity);
    }

}
