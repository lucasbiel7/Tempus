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
public class MateriaRecursosID implements Serializable {

    @ManyToOne
    private Materia materia;
    @ManyToOne
    private Recurso recurso;

    public MateriaRecursosID() {
    }

    public MateriaRecursosID(Materia materia, Recurso recurso) {
        this.materia = materia;
        this.recurso = recurso;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.materia);
        hash = 79 * hash + Objects.hashCode(this.recurso);
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
        final MateriaRecursosID other = (MateriaRecursosID) obj;
        if (!Objects.equals(this.materia, other.materia)) {
            return false;
        }
        if (!Objects.equals(this.recurso, other.recurso)) {
            return false;
        }
        return true;
    }

}
