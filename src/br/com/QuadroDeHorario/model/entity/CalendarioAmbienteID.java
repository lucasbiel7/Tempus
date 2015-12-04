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
public class CalendarioAmbienteID implements Serializable {

    @ManyToOne
    private Calendario calendario;
    @ManyToOne
    private Ambiente ambiente;

    public CalendarioAmbienteID(Calendario calendario, Ambiente ambiente) {
        this.calendario = calendario;
        this.ambiente = ambiente;
    }

    public CalendarioAmbienteID() {
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.calendario);
        hash = 67 * hash + Objects.hashCode(this.ambiente);
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
        final CalendarioAmbienteID other = (CalendarioAmbienteID) obj;
        if (!Objects.equals(this.calendario, other.calendario)) {
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
