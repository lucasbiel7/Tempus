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
public class MateriaHorarioAmbienteID implements Serializable {

    @ManyToOne
    private MateriaHorario materiaHorario;
    @ManyToOne
    private Ambiente ambiente;

    public MateriaHorario getMateriaHorario() {
        return materiaHorario;
    }

    public void setMateriaHorario(MateriaHorario materiaHorario) {
        this.materiaHorario = materiaHorario;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public MateriaHorarioAmbienteID() {
    }

    public MateriaHorarioAmbienteID(MateriaHorario materiaHorario, Ambiente ambiente) {
        this.materiaHorario = materiaHorario;
        this.ambiente = ambiente;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.materiaHorario);
        hash = 79 * hash + Objects.hashCode(this.ambiente);
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
        final MateriaHorarioAmbienteID other = (MateriaHorarioAmbienteID) obj;
        if (!Objects.equals(this.materiaHorario, other.materiaHorario)) {
            return false;
        }
        if (!Objects.equals(this.ambiente, other.ambiente)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getAmbiente().toString();
    }

}
