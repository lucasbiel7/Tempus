/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import br.com.QuadroDeHorario.model.entity.Usuario;

/**
 *
 * @author OCTI01
 */
public class SessaoUsuario {

    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        SessaoUsuario.usuario = usuario;
    }

}
