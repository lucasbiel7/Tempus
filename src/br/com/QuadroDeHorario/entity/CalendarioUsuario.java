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
public class CalendarioUsuario implements Serializable {

    @EmbeddedId
    private CalendarioUsuarioID id;
    private boolean manha;
    private boolean tarde;
    private boolean noite;
    private boolean ativo = true;

    public CalendarioUsuarioID getId() {
        return id;
    }

    public void setId(CalendarioUsuarioID id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public CalendarioUsuario() {
    }

    public CalendarioUsuario(CalendarioUsuarioID id) {
        this.id = id;
    }

    public boolean isManha() {
        return manha;
    }

    public void setManha(boolean manha) {
        this.manha = manha;
    }

    public boolean isTarde() {
        return tarde;
    }

    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    public boolean isNoite() {
        return noite;
    }

    public void setNoite(boolean noite) {
        this.noite = noite;
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
        final CalendarioUsuario other = (CalendarioUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
