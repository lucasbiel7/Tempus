/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI01
 */
@Entity
public class Escolaridade implements Serializable {

    public enum TipoEscolaridade {

        MEDIO, APRENDIZAGEM, TECNICO, GRADUACAO, POSGRADUACAO, MESTRADO, QUALIFICACAO, ESPECIALIZACAO, DOUTORADO, POSDOUTORADO;

        @Override
        public String toString() {
            switch (this) {
                case MEDIO:
                    return "Médio";
                case APRENDIZAGEM:
                    return "Aprendizagem";
                case TECNICO:
                    return "Técnico";
                case GRADUACAO:
                    return "Graduação";
                case POSGRADUACAO:
                    return "Pós-graduação";
                case MESTRADO:
                    return "Mestrado";
                case QUALIFICACAO:
                    return "Qualificação";
                case ESPECIALIZACAO:
                    return "Especialização";
                case DOUTORADO:
                    return "Doutorado";
                case POSDOUTORADO:
                    return "Pós-doutorado";
                default:
                    return "Error";
            }
        }
    }

    @Id
    @GeneratedValue
    private int id;
    private TipoEscolaridade tipoEscolaridade;
    private String curso;
    private boolean completo;
    @ManyToOne
    private Usuario usuario;
    private boolean ativo = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoEscolaridade getTipoEscolaridade() {
        return tipoEscolaridade;
    }

    public void setTipoEscolaridade(TipoEscolaridade tipoEscolaridade) {
        this.tipoEscolaridade = tipoEscolaridade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public boolean isCompleto() {
        return completo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
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
        final Escolaridade other = (Escolaridade) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getCurso();
    }

}
