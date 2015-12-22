/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author OCTI01
 */
@Entity
public class Ambiente implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    private String nome;
    private String descricao;
    private int capacidade;
    private boolean ativo = true;
    @Column(unique = true, nullable = true)
    private String chave = null;
    @Column(unique = true, nullable = true)
    private String chaveReserva = null;
    @OneToMany(mappedBy = "ambiente")
    private List<Aula> aulas;
    @OneToMany(mappedBy = "ambiente")
    private List<EmprestaChave> usuarioAmbientes;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public List<EmprestaChave> getUsuarioAmbientes() {
        return usuarioAmbientes;
    }

    public void setUsuarioAmbientes(List<EmprestaChave> usuarioAmbientes) {
        this.usuarioAmbientes = usuarioAmbientes;
    }

    public String getChaveReserva() {
        return chaveReserva;
    }

    public void setChaveReserva(String chaveReserva) {
        this.chaveReserva = chaveReserva;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
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
        final Ambiente other = (Ambiente) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
