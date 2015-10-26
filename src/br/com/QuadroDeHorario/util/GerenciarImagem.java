/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import java.io.InputStream;

/**
 *
 * @author OCTI01
 */
public class GerenciarImagem {

    public static final String VIEW = "/br/com/QuadroDeHorario/view/";

    public static InputStream carregarImagem(String imagem) {
        return GerenciarImagem.class.getResourceAsStream(VIEW +"image/"+ imagem);
    }
}
