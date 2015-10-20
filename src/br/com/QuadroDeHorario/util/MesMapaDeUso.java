/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import br.com.QuadroDeHorario.entity.Ambiente;
import java.util.List;

/**
 *
 * @author OCTI01
 */
public class MesMapaDeUso {

    private Ambiente ambiente;
    private DataHorario.Turno turno;
    private List<Object> ocupacao;

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

    public List<Object> getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(List<Object> ocupacao) {
        this.ocupacao = ocupacao;
    }

}
