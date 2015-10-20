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
public class MateriaHorarioAmbiente implements Serializable {

    @EmbeddedId
    private MateriaHorarioAmbienteID id;
    private int numero;
    private boolean ativo;

    public MateriaHorarioAmbienteID getId() {
        return id;
    }

    public void setId(MateriaHorarioAmbienteID id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public MateriaHorarioAmbiente() {

    }

    public MateriaHorarioAmbiente(MateriaHorarioAmbienteID id, int numero, boolean ativo) {
        this.id = id;
        this.numero = numero;
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final MateriaHorarioAmbiente other = (MateriaHorarioAmbiente) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
