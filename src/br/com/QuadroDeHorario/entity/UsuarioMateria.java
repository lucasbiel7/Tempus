/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author OCTI01
 */
@Entity
public class UsuarioMateria implements Serializable {

    public enum Tipo {

        COMPETENCIA, PREFERENCIA, INTERESSE, OUTROS;

        @Override
        public String toString() {
            switch (this) {
                case COMPETENCIA:
                    return "Competência";
                case PREFERENCIA:
                    return "Preferência";
                case INTERESSE:
                    return "Interesse";
                case OUTROS:
                    return "Outros";
                default:
                    return "";
            }
        }
    };
    @EmbeddedId
    private UsuarioMateriaID id;
    private Tipo tipo;
    private boolean ativo;

    public UsuarioMateria(UsuarioMateriaID id, Tipo tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public UsuarioMateria() {
    }

    public UsuarioMateriaID getId() {
        return id;
    }

    public void setId(UsuarioMateriaID id) {
        this.id = id;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final UsuarioMateria other = (UsuarioMateria) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
