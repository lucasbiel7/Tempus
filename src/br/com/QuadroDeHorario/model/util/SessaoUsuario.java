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

    public enum PERMISSAO {
        COORDERNACAO("Coordenação"), INSTRUTOR("Instrutor"), COMPETIDOR("Competidor");

        private PERMISSAO(String nome) {
            this.nome = nome;
        }

        private final String nome;

        @Override
        public String toString() {
            return nome;
        }

    }
    private static Usuario usuario;

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        SessaoUsuario.usuario = usuario;
    }

}
