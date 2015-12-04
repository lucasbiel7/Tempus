/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

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
public class PermissaoUsuario implements Serializable {

    @Embeddable
    public static class PermissaoUsuarioID implements Serializable {

        @ManyToOne
        private Usuario usuario;
        private Permissao permissao;

        public PermissaoUsuarioID() {
        }

        public PermissaoUsuarioID(Usuario usuario, Permissao permissao) {
            this.usuario = usuario;
            this.permissao = permissao;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public Permissao getPermissao() {
            return permissao;
        }

        public void setPermissao(Permissao permissao) {
            this.permissao = permissao;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.usuario);
            hash = 97 * hash + Objects.hashCode(this.permissao);
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
            final PermissaoUsuarioID other = (PermissaoUsuarioID) obj;
            if (!Objects.equals(this.usuario, other.usuario)) {
                return false;
            }
            if (this.permissao != other.permissao) {
                return false;
            }
            return true;
        }

    }
    
    @EmbeddedId
    private PermissaoUsuarioID id;
    private boolean habilitado;
    private boolean ativo = true;

    public PermissaoUsuarioID getId() {
        return id;
    }

    public void setId(PermissaoUsuarioID id) {
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
        int hash = 7;
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
        final PermissaoUsuario other = (PermissaoUsuario) obj;
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
