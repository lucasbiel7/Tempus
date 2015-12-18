/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import br.com.QuadroDeHorario.model.util.DataHorario;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Aula.pegarPorHorarioDiaTurma", query = "SELECT a FROM Aula a WHERE a.id.dataAula=:dataAula AND a.id.horario=:horario AND a.materiaHorario.materiaTurmaInstrutorSemestre.turma=:turma AND a.ativo=true"),
    @NamedQuery(name = "Aula.pegarPorHorarioDiaAmbiente", query = "SELECT a FROM Aula a WHERE a.id.dataAula=:dataAula AND a.id.horario=:horario AND a.ambiente=:ambiente AND a.id.turno=:turno AND a.ativo=true"),
    @NamedQuery(name = "Aula.pegarPorHorarioDiaUsuario", query = "SELECT a FROM Aula a WHERE a.id.dataAula=:dataAula AND a.id.horario=:horario AND a.materiaHorario.materiaTurmaInstrutorSemestre.instrutor=:usuario AND a.id.turno=:turno AND a.ativo=true"),
    @NamedQuery(name = "Aula.pegarPorDiaInstrutor", query = "SELECT a FROM Aula a WHERE a.id.dataAula=:dataAula  AND a.materiaHorario.materiaTurmaInstrutorSemestre.instrutor=:usuario AND a.ativo=true"),
    @NamedQuery(name = "Aula.pegarPorDiaInstrutorTurno", query = "SELECT a FROM Aula a WHERE a.id.dataAula=:dataAula  AND a.materiaHorario.materiaTurmaInstrutorSemestre.instrutor=:usuario AND a.id.turno =:turno AND a.ativo=true"),
    @NamedQuery(name = "Aula.pegarPorInstrutorTurnoDiaHorario", query = "SELECT a FROM Aula a where a.materiaHorario.materiaTurmaInstrutorSemestre.instrutor=:instrutor and a.id.turno=:turno and a.id.dataAula=:dataAula and a.id.horario=:horario AND a.ativo=true")
})
public class Aula implements Serializable {

    //Classe para gerar Id Composto
    //Atributos 
    @EmbeddedId
    private DataHorarioTurnoTurma id;
    @ManyToOne
    private Ambiente ambiente;
    private boolean ativo = true;
    @ManyToOne
    private MateriaHorario materiaHorario;

    @Embeddable
    public static class DataHorarioTurnoTurma implements Serializable {

        @Temporal(javax.persistence.TemporalType.DATE)
        private Date dataAula;
        private DataHorario.Horario horario;
        private DataHorario.Turno turno;
        @ManyToOne
        private Turma turma;

        public DataHorarioTurnoTurma() {
        }

        public DataHorarioTurnoTurma(Date dataAula, DataHorario.Horario horario, DataHorario.Turno turno, Turma turma) {
            this.dataAula = dataAula;
            this.horario = horario;
            this.turno = turno;
            this.turma = turma;
        }

        public Turma getTurma() {
            return turma;
        }

        public void setTurma(Turma turma) {
            this.turma = turma;
        }

        public DataHorario.Turno getTurno() {
            return turno;
        }

        public void setTurno(DataHorario.Turno turno) {
            this.turno = turno;
        }

        public Date getDataAula() {
            return dataAula;
        }

        public void setDataAula(Date dataAula) {
            this.dataAula = dataAula;
        }

        public DataHorario.Horario getHorario() {
            return horario;
        }

        public void setHorario(DataHorario.Horario horario) {
            this.horario = horario;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 71 * hash + Objects.hashCode(this.dataAula);
            hash = 71 * hash + Objects.hashCode(this.horario);
            hash = 71 * hash + Objects.hashCode(this.turno);
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
            final DataHorarioTurnoTurma other = (DataHorarioTurnoTurma) obj;
            if (!Objects.equals(this.dataAula, other.dataAula)) {
                return false;
            }
            if (this.horario != other.horario) {
                return false;
            }
            if (this.turno != other.turno) {
                return false;
            }
            return true;
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public DataHorarioTurnoTurma getId() {
        return id;
    }

    public void setId(DataHorarioTurnoTurma id) {
        this.id = id;
    }

    public MateriaHorario getMateriaHorario() {
        return materiaHorario;
    }

    public void setMateriaHorario(MateriaHorario materiaHorario) {
        this.materiaHorario = materiaHorario;
    }

    @Override
    public String toString() {
        return getMateriaHorario().toString();
    }
}
