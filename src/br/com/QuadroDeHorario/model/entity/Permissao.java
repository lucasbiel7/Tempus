/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.entity;

/**
 *
 * @author OCTI01
 */
public enum Permissao {

    INICIO("Início"), INFOGRAFICO("Infográfico"), CALENDARIO_EVENTOS("Calendário de eventos"), CALENDARIO_ESCOLAR("Calendário escolar"),
    AREA_ADMINISTRATIVA("Área administrativa"), PROJETO("Projeto"), CURSO("Curso"), RECURSO("Recursos"), AMBIENTE("Ambiente"), TURMA("Turma"), DISCIPLINA("Componente curricular"), INSTRUTOR("Instrutor"), CONSTANTES("Contantes do sistema"),
    QUADRO_HORARIO("Quadro de Horário"), CRIAR_QUADRO("Criar quadro de horário"), VISUALIZAR_TURMA("Visualizar quadro horário da turma"), VISUALIZAR_INSTRUTOR("Visualizar quadro horário do instrutor"),
    ESTATISTICA("Estatística"),
    LIXEIRA("Lixeria");
    private String nome;

    private Permissao(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.nome;
    }

}
