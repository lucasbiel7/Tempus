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
public class AmbienteRecursosID implements Serializable {

    @ManyToOne
    private Ambiente ambiente;
    @ManyToOne
    private Recurso recurso;

    public AmbienteRecursosID(Ambiente ambiente, Recurso recurso) {
        this.ambiente = ambiente;
        this.recurso = recurso;
    }

    public AmbienteRecursosID() {
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.ambiente);
        hash = 97 * hash + Objects.hashCode(this.recurso);
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
        final AmbienteRecursosID other = (AmbienteRecursosID) obj;
        if (!Objects.equals(this.ambiente, other.ambiente)) {
            return false;
        }
        if (!Objects.equals(this.recurso, other.recurso)) {
            return false;
        }
        return true;
    }
}
