/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaMapaDeUso;
import br.com.QuadroDeHorario.control.TabelaMapaDeUsoInstrutor;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.util.DataHorario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarMapaDeUsoController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Label lbTipoRelatorio;
    @FXML
    private GridPane gpTabela;
    @FXML
    private Label lbVerde;
    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbVermelho;
    private Ambiente ambiente;
    private DataHorario.Semestre semestre;
    private int ano;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Object[] parametros = (Object[]) apPrincipal.getUserData();
            for (Object parametro : parametros) {
                if (parametro instanceof Ambiente) {
                    ambiente = (Ambiente) parametro;
                    lbTipoRelatorio.setText(lbTipoRelatorio.getText() + (lbTipoRelatorio.getText().isEmpty() ? "" : "/") + ambiente.getNome());
                } else if (parametro instanceof DataHorario.Semestre) {
                    semestre = (DataHorario.Semestre) parametro;
                    lbTipoRelatorio.setText(lbTipoRelatorio.getText() + (lbTipoRelatorio.getText().isEmpty() ? "" : "/") + semestre);
                } else if (parametro instanceof Integer) {
                    ano = (Integer) parametro;
                    lbTipoRelatorio.setText(lbTipoRelatorio.getText() + (lbTipoRelatorio.getText().isEmpty() ? "" : "/") + ano);
                }
                if (ambiente == null) {
                    lbVerde.setText("Instrutores disponíveis");
                    lbVermelho.setText("Nenhum Instrutor disponível");
                } else {
                    lbVerde.setText("Ambiente livre");
                    lbVermelho.setText("Ambiente ocupado");
                }
            }
            carregarTabelas();
        });
    }

    private void carregarTabelas() {
        if (ambiente == null) {
            for (int mes = 1; mes < 7; mes++) {
                TabelaMapaDeUsoInstrutor tabelaMapaDeUso = new TabelaMapaDeUsoInstrutor(ano, semestre.equals(DataHorario.Semestre.SEMESTRE1) ? mes : mes + 6);
                gpTabela.addRow(mes - 1, tabelaMapaDeUso);
            }
            lbTitulo.setText("Disponibilidade dos instrutores");
        } else {
            for (int mes = 1; mes < 7; mes++) {
                TabelaMapaDeUso tabelaMapaDeUso = new TabelaMapaDeUso(ambiente, ano, semestre.equals(DataHorario.Semestre.SEMESTRE1) ? mes : mes + 6);
                gpTabela.addRow(mes - 1, tabelaMapaDeUso);
            }
            lbTitulo.setText("Disponibilidade do ambiente");
        }
    }
}
