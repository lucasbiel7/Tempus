/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

import br.com.QuadroDeHorario.model.util.DataHorario;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author OCTI01
 * @param <Tipo>
 * @param <TipoDia>
 */
public class MesMapaDeUso<Tipo, TipoDia> {

    private Tipo tipo;
    private DataHorario.Turno turno;
    private List<TipoDia> diaMapaDeUsos;

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public DataHorario.Turno getTurno() {
        return turno;
    }

    public void setTurno(DataHorario.Turno turno) {
        this.turno = turno;
    }

    public List<TipoDia> getDiaMapaDeUsos() {
        if (diaMapaDeUsos == null) {
            diaMapaDeUsos = new ArrayList<>();
        }
        return diaMapaDeUsos;
    }

    public void setDiaMapaDeUsos(List<TipoDia> diaMapaDeUsos) {
        this.diaMapaDeUsos = diaMapaDeUsos;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.tipo);
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
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        if (this.turno != other.turno) {
            return false;
        }
        return true;
    }

}
