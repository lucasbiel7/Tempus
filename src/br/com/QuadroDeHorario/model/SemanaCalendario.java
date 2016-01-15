/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author OCTI01
 */
public class SemanaCalendario {

    private Date segunda;
    private Date terca;
    private Date quarta;
    private Date quinta;
    private Date sexta;
    private Date sabado;
    private Date domingo;

    public Date getSegunda() {
        return segunda;
    }

    public void setSegunda(Date segunda) {
        this.segunda = segunda;
    }

    public Date getTerca() {
        return terca;
    }

    public void setTerca(Date terca) {
        this.terca = terca;
    }

    public Date getQuarta() {
        return quarta;
    }

    public void setQuarta(Date quarta) {
        this.quarta = quarta;
    }

    public Date getQuinta() {
        return quinta;
    }

    public void setQuinta(Date quinta) {
        this.quinta = quinta;
    }

    public Date getSexta() {
        return sexta;
    }

    public void setSexta(Date sexta) {
        this.sexta = sexta;
    }

    public Date getSabado() {
        return sabado;
    }

    public void setSabado(Date sabado) {
        this.sabado = sabado;
    }

    public Date getDomingo() {
        return domingo;
    }

    public void setDomingo(Date domingo) {
        this.domingo = domingo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final SemanaCalendario other = (SemanaCalendario) obj;
        if (!Objects.equals(this.segunda, other.segunda)) {
            return false;
        }
        if (!Objects.equals(this.terca, other.terca)) {
            return false;
        }
        if (!Objects.equals(this.quarta, other.quarta)) {
            return false;
        }
        if (!Objects.equals(this.quinta, other.quinta)) {
            return false;
        }
        if (!Objects.equals(this.sexta, other.sexta)) {
            return false;
        }
        if (!Objects.equals(this.sabado, other.sabado)) {
            return false;
        }
        if (!Objects.equals(this.domingo, other.domingo)) {
            return false;
        }
        return true;
    }

}
