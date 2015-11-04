/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaMapaDeUso;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.util.DataHorario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private GridPane gpTabela;
    private Ambiente ambiente;
    private DataHorario.Semestre semestre;
    private int ano;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Object[] parametros = (Object[]) apPrincipal.getUserData();
            ambiente = (Ambiente) parametros[0];
            semestre = (DataHorario.Semestre) parametros[1];
            ano = (int) parametros[2];
            carregarTabelas();
        });
    }

    private void carregarTabelas() {
        for (int mes = 1; mes < 7; mes++) {
            TabelaMapaDeUso tabelaMapaDeUso = new TabelaMapaDeUso(ambiente, ano, semestre.equals(DataHorario.Semestre.SEMESTRE1) ? mes : mes + 6);
            gpTabela.addRow(mes - 1, tabelaMapaDeUso);
        }
    }
}
