/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

import br.com.QuadroDeHorario.entity.Aula;
import java.util.Objects;

/**
 *
 * @author OCTI01
 */
public class HorarioDiario {

    private Object objeto;
    private Aula aula1Horario;
    private Aula aula2Horario;
    private Aula aula3Horario;
    private Aula aula4Horario;
    private Aula aula5Horario;

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public Aula getAula1Horario() {
        return aula1Horario;
    }

    public void setAula1Horario(Aula aula1Horario) {
        this.aula1Horario = aula1Horario;
    }

    public Aula getAula2Horario() {
        return aula2Horario;
    }

    public void setAula2Horario(Aula aula2Horario) {
        this.aula2Horario = aula2Horario;
    }

    public Aula getAula3Horario() {
        return aula3Horario;
    }

    public void setAula3Horario(Aula aula3Horario) {
        this.aula3Horario = aula3Horario;
    }

    public Aula getAula4Horario() {
        return aula4Horario;
    }

    public void setAula4Horario(Aula aula4Horario) {
        this.aula4Horario = aula4Horario;
    }

    public Aula getAula5Horario() {
        return aula5Horario;
    }

    public void setAula5Horario(Aula aula5Horario) {
        this.aula5Horario = aula5Horario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.objeto);
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
        final HorarioDiario other = (HorarioDiario) obj;
        if (!Objects.equals(this.objeto, other.objeto)) {
            return false;
        }
        return true;
    }

}
