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
public class CalendarioAmbiente implements Serializable {

    @EmbeddedId
    private CalendarioAmbienteID id;
    private boolean ativo = true;
    private boolean manha;
    private boolean tarde;
    private boolean noite;

    public CalendarioAmbiente(CalendarioAmbienteID id) {
        this.id = id;
    }

    public CalendarioAmbiente() {
    }

    public CalendarioAmbienteID getId() {
        return id;
    }

    public void setId(CalendarioAmbienteID id) {
        this.id = id;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
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
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final CalendarioAmbiente other = (CalendarioAmbiente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}
