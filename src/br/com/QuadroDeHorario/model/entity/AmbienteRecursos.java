/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author OCTI01
 */
@Entity
public class AmbienteRecursos implements Serializable {

    @EmbeddedId
    private AmbienteRecursosID id;
    private boolean ativo = true;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public AmbienteRecursos() {
    }

    public AmbienteRecursos(AmbienteRecursosID id) {
        this.id = id;
    }

    public AmbienteRecursosID getId() {
        return id;
    }

    public void setId(AmbienteRecursosID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
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
        final AmbienteRecursos other = (AmbienteRecursos) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getId().getRecurso().getNome();
    }
}
