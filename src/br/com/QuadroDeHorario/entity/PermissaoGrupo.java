/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI01
 */
@Entity
public class PermissaoGrupo implements Serializable {

    @Embeddable
    public static class PermissaoGrupoID implements Serializable {

        @ManyToOne
        private Grupo grupo;
        private Permissao permissao;

        public Grupo getGrupo() {
            return grupo;
        }

        public void setGrupo(Grupo grupo) {
            this.grupo = grupo;
        }

        public Permissao getPermissao() {
            return permissao;
        }

        public void setPermissao(Permissao permissao) {
            this.permissao = permissao;
        }

        public PermissaoGrupoID() {
        }

        public PermissaoGrupoID(Grupo grupo, Permissao permissao) {
            this.grupo = grupo;
            this.permissao = permissao;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.grupo);
            hash = 53 * hash + Objects.hashCode(this.permissao);
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
            final PermissaoGrupoID other = (PermissaoGrupoID) obj;
            if (!Objects.equals(this.grupo, other.grupo)) {
                return false;
            }
            if (this.permissao != other.permissao) {
                return false;
            }
            return true;
        }

    }

    @EmbeddedId
    private PermissaoGrupoID id;
    private boolean habilitado;
    private boolean ativo = true;

    public PermissaoGrupoID getId() {
        return id;
    }

    public void setId(PermissaoGrupoID id) {
        this.id = id;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
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
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final PermissaoGrupo other = (PermissaoGrupo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getId().getPermissao().toString();
    }

}
