/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model;

/**
 *
 * @author OCTI01
 */
public enum SerialConstants {

    TESTE_CONECTIVIDADE("1010101"),
    SEPARADOR(":"),
    VERDADEIRO("01"),
    FALSO("00"),
    INVALIDO("11"),
    CONEXAO_ESTABELECIDA("99"),
    REQUISITAR_CARTAO("77"),
    REQUISITAR_CHAVE("88"),
    CANCELAR("22"),
    RESET("33"),
    SLEEP("44"),
    DEVOLVER_CHAVE("55"),
    DADO_INCOMPLETO("66"),
    ALIVE("12");

    private String valor;

    private SerialConstants(String valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return valor;
    }

}
