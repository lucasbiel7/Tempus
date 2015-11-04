/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author OCTI01
 */
public class DiaMapaDeUso {

    private Date data;
    private List eventos;

    public DiaMapaDeUso() {
        this.eventos = new ArrayList();
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List getEventos() {
        if (eventos == null) {
            eventos = new ArrayList();
        }
        return eventos;
    }

    public void setEventos(List eventos) {
        this.eventos = eventos;
    }

    @Override
    public String toString() {
        return "";
    }

}
