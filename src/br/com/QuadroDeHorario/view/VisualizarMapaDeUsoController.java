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
    private TabelaMapaDeUso tmu1;
    private TabelaMapaDeUso tmu2;
    private TabelaMapaDeUso tmu3;
    private TabelaMapaDeUso tmu4;
    private TabelaMapaDeUso tmu5;
    private TabelaMapaDeUso tmu6;

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
        int mes = 0;
        if (semestre == DataHorario.Semestre.SEMESTRE2) {
            mes += 6;
        }
        mes++;
        tmu1 = new TabelaMapaDeUso(ambiente, ano, mes);
        mes++;
        tmu2 = new TabelaMapaDeUso(ambiente, ano, mes);
        mes++;
        tmu3 = new TabelaMapaDeUso(ambiente, ano, mes);
        mes++;
        tmu4 = new TabelaMapaDeUso(ambiente, ano, mes);
        mes++;
        tmu5 = new TabelaMapaDeUso(ambiente, ano, mes);
        mes++;
        tmu6 = new TabelaMapaDeUso(ambiente, ano, mes);
        gpTabela.addRow(0, tmu1);
        gpTabela.addRow(1, tmu2);
        gpTabela.addRow(2, tmu3);
        gpTabela.addRow(3, tmu4);
        gpTabela.addRow(4, tmu5);
        gpTabela.addRow(5, tmu6);
    }
}
