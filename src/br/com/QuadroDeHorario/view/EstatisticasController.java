/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.util.FxMananger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EstatisticasController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void btRelatorioActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "Relatorios");
    }

    @FXML
    private void btGraficoActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "Graficos");
    }

    @FXML
    private void btMapaDeUsoActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "MapaDeUso");
    }
}
