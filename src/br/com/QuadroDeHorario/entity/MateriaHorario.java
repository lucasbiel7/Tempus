/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.entity;

import br.com.QuadroDeHorario.util.DataHorario;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI01
 */
@Entity
public class MateriaHorario implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    @Embedded
    @Column(unique = true)
    private MateriaTurmaIntrutorSemestre materiaTurmaIntrutorSemestre;
    private int ano;
    private int cargaHoraria;
    private boolean subistito;
    private int numeroSubstituto;
    private boolean ativo = true;
    private int red;
    private int green;
    private int blue;

    @Embeddable
    public static class MateriaTurmaIntrutorSemestre implements Serializable {

        @ManyToOne
        private Materia materia;
        @ManyToOne
        private Turma turma;
        @ManyToOne
        private Usuario instrutor;
        private DataHorario.Semestre semestre;

        public MateriaTurmaIntrutorSemestre() {
        }

        public MateriaTurmaIntrutorSemestre(Materia materia, Turma turma, Usuario instrutor, DataHorario.Semestre semestre) {
            this.materia = materia;
            this.turma = turma;
            this.instrutor = instrutor;
            this.semestre = semestre;
        }

        public Materia getMateria() {
            return materia;
        }

        public void setMateria(Materia materia) {
            this.materia = materia;
        }

        public Turma getTurma() {
            return turma;
        }

        public void setTurma(Turma turma) {
            this.turma = turma;
        }

        public Usuario getInstrutor() {
            return instrutor;
        }

        public void setInstrutor(Usuario instrutor) {
            this.instrutor = instrutor;
        }

        public DataHorario.Semestre getSemestre() {
            return semestre;
        }

        public void setSemestre(DataHorario.Semestre semestre) {
            this.semestre = semestre;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.materia);
            hash = 79 * hash + Objects.hashCode(this.turma);
            hash = 79 * hash + Objects.hashCode(this.instrutor);
            hash = 79 * hash + Objects.hashCode(this.semestre);
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
            final MateriaTurmaIntrutorSemestre other = (MateriaTurmaIntrutorSemestre) obj;
            if (!Objects.equals(this.materia, other.materia)) {
                return false;
            }
            if (!Objects.equals(this.turma, other.turma)) {
                return false;
            }
            if (!Objects.equals(this.instrutor, other.instrutor)) {
                return false;
            }
            if (this.semestre != other.semestre) {
                return false;
            }
            return true;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MateriaTurmaIntrutorSemestre getMateriaTurmaIntrutorSemestre() {
        if (materiaTurmaIntrutorSemestre == null) {
            setMateriaTurmaIntrutorSemestre(new MateriaTurmaIntrutorSemestre());
        }
        return materiaTurmaIntrutorSemestre;
    }

    public void setMateriaTurmaIntrutorSemestre(MateriaTurmaIntrutorSemestre materiaTurmaIntrutorSemestre) {
        this.materiaTurmaIntrutorSemestre = materiaTurmaIntrutorSemestre;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public boolean isSubistito() {
        return subistito;
    }

    public void setSubistito(boolean subistito) {
        this.subistito = subistito;
    }

    public int getNumeroSubstituto() {
        return numeroSubstituto;
    }

    public void setNumeroSubstituto(int numeroSubstituto) {
        this.numeroSubstituto = numeroSubstituto;
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
        hash = 59 * hash + this.id;
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
        final MateriaHorario other = (MateriaHorario) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    @Override
    public String toString() {
        return (getMateriaTurmaIntrutorSemestre().getTurma().isEspelho() ? "(DD)" : "") + getMateriaTurmaIntrutorSemestre().getMateria().getSigla() + " " + (isSubistito() ? "(" + getNumeroSubstituto() + ")" : "");
    }

}
