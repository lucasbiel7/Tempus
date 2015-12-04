/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

import br.com.QuadroDeHorario.model.entity.Usuario;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author OCTI01
 */
public class DiaMapaDeUsoInstrutor {

    private Date data;
    private List<Usuario> usuario;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    public DiaMapaDeUsoInstrutor() {
    }

    public DiaMapaDeUsoInstrutor(Date data, List<Usuario> usuario) {
        this.data = data;
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.data);
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
        final DiaMapaDeUsoInstrutor other = (DiaMapaDeUsoInstrutor) obj;
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(getUsuario().size());
    }

}
