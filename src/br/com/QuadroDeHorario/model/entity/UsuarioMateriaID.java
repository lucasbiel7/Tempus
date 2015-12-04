/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI01
 */
@Embeddable
public class UsuarioMateriaID implements Serializable {

    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Materia materia;

    public UsuarioMateriaID(Usuario usuario, Materia materia) {
        this.usuario = usuario;
        this.materia = materia;
    }

    public UsuarioMateriaID() {
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.usuario);
        hash = 31 * hash + Objects.hashCode(this.materia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioMateriaID other = (UsuarioMateriaID) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.materia, other.materia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getMateria().getNome();
    }

}
