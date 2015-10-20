/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

/**
 *
 * @author OCTI01
 * @param <Entity>
 */
public interface DAO<Entity> {

    void cadastrar(Entity entity);

    void editar(Entity entity);

    void excluir(Entity entity);

}
