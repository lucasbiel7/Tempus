/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import br.com.QuadroDeHorario.entity.Ambiente;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author OCTI01
 */
public class MesMapaDeUso {

    private Ambiente ambiente;
    private DataHorario.Turno turno;
    private List<DiaMapaDeUso> diaMapaDeUsos;

    public Ambiente getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(Ambiente ambiente) {
        this.ambiente = ambiente;
    }

    public DataHorario.Turno getTurno() {
        return turno;
    }

    public void setTurno(DataHorario.Turno turno) {
        this.turno = turno;
    }

    public List<DiaMapaDeUso> getDiaMapaDeUsos() {
        if (diaMapaDeUsos == null) {
            diaMapaDeUsos = new ArrayList<>();
        }
        return diaMapaDeUsos;
    }

    public void setDiaMapaDeUsos(List<DiaMapaDeUso> diaMapaDeUsos) {
        this.diaMapaDeUsos = diaMapaDeUsos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.ambiente);
        hash = 67 * hash + Objects.hashCode(this.turno);
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
        final MesMapaDeUso other = (MesMapaDeUso) obj;
        if (!Objects.equals(this.ambiente, other.ambiente)) {
            return false;
        }
        if (this.turno != other.turno) {
            return false;
        }
        return true;
    }

}
