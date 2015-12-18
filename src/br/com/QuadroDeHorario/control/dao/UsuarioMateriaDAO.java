/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control.dao;

import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.Materia;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.entity.UsuarioMateria;
import br.com.QuadroDeHorario.model.util.DataHorario;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author OCTI01
 */
public class UsuarioMateriaDAO extends GenericaDAO<UsuarioMateria> {

    @Override
    public void excluir(UsuarioMateria entity) {
        entity.setAtivo(false);
        editar(entity);
    }

    public List<UsuarioMateria> pegarTodosPorUsuario(Usuario usuario) {
        entitys = criteria.add(Restrictions.eq("id.usuario", usuario)).list();
        finalizarSession();
        return entitys;
    }

    public UsuarioMateria pegarTodosPorUsuarioMateria(Usuario usuario, Materia materia) {
        entity = (UsuarioMateria) session.createCriteria(classe).add(Restrictions.eq("id.usuario", usuario)).add(Restrictions.eq("id.materia", materia)).uniqueResult();
        finalizarSession();
        return entity;
    }

    public List<UsuarioMateria> pegarTodosPorMateriaTipo(Materia materia, UsuarioMateria.Tipo tipo) {
        entitys = criteria.add(Restrictions.eq("id.materia", materia)).add(Restrictions.eq("tipo", tipo)).list();
        finalizarSession();
        return entitys;
    }

    public List<UsuarioMateria> pegarTodosPorMateriaTipoTurno(Materia materia, UsuarioMateria.Tipo tipo, DataHorario.Turno turno) {
        List<Usuario> usuarios = new UsuarioDAO().pegarPorTurno(turno);
        if (usuarios.isEmpty()) {
            finalizarSession();
            return entitys;
        }
        entitys = criteria.add(Restrictions.in("id.usuario", usuarios)).add(Restrictions.eq("id.materia", materia)).add(Restrictions.eq("tipo", tipo)).list();
        finalizarSession();
        return entitys;
    }

    public List<UsuarioMateria> pegarTodosPorMateria(Materia materia) {
        entitys = criteria.add(Restrictions.eq("id.materia", materia)).list();
        finalizarSession();
        return entitys;
    }

    public List<UsuarioMateria> pegarTodosPorUsuarioAgrupado(Usuario usuario, Curso cursos) {
        List<Materia> materias = new MateriaDAO().pegarTodosPorCurso(cursos);
        if (materias.isEmpty()) {
            finalizarSession();
            return new ArrayList<>();
        }
        entitys = criteria.add(Restrictions.eq("id.usuario", usuario)).add(Restrictions.in("id.materia", materias)).list();
        finalizarSession();
        return entitys;
    }

}
