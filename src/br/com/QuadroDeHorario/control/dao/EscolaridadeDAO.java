/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.model.entity.Escolaridade;
import br.com.QuadroDeHorario.model.entity.Usuario;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class EscolaridadeDAO extends GenericaDAO<Escolaridade> {

    @Override
    public void excluir(Escolaridade entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<Escolaridade> pegarPorUsuario(Usuario usuario) {
        entitys = criteria.add(Restrictions.eq("usuario", usuario)).list();
        finalizarSession();
        return entitys;
    }

}
