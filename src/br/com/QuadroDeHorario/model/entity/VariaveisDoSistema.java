/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author OCTI01
 */
@Entity
public class VariaveisDoSistema implements Serializable {

    public enum NOME {
        KEYGUARDIAN("KeyGuardian"), OCUPACAO("ocupacao");
        private String nome;

        private NOME(String nome) {
            this.nome = nome;
        }

        @Override
        public String toString() {
            return nome;
        }
    }
    @Id
    @GeneratedValue
    private int id;
    @Column(unique = true)
    private String nome;
    private String valor;
    @ManyToOne
    private Sistema sistema;
    private boolean ativo = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final VariaveisDoSistema other = (VariaveisDoSistema) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome() + "=" + valor;
    }

}
