/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author OCTI01
 */
@Entity
public class EmprestaChave implements Serializable {

    public enum Transferencia {
        EMPRESTIMO, DEVOLVER;
    }

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne
    private Ambiente ambiente;
    @ManyToOne
    private Usuario usuario;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dia;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horario;
    private Transferencia transferencia;
    private boolean valido;
    private boolean reserva;
    private boolean ativo = true;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public boolean isReserva() {
        return reserva;
    }

    public void setReserva(boolean reserva) {
        this.reserva = reserva;
    }

    public Transferencia getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
        this.transferencia = transferencia;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmprestaChave other = (EmprestaChave) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getAmbiente().toString();
    }
}
