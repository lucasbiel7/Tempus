/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.util.FxMananger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class EstatisticasController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private ScrollPane spPrincipal;
    private ScrollPane spGeral;

    private Button lastButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            spGeral = (ScrollPane) apPrincipal.getUserData();
        });
    }

    @FXML
    private void btRelatorioActionEvent(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        removerSelected(button);
        FxMananger.insertPane(spPrincipal, "Relatorios");
    }

    @FXML
    private void btGraficoActionEvent(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        removerSelected(button);
        FxMananger.insertPane(spPrincipal, "Graficos");
    }

    @FXML
    private void btMapaDeUsoActionEvent(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        removerSelected(button);
        FxMananger.insertPane(spPrincipal, "MapaDeUso", spGeral);
    }

    private void removerSelected(Button button) {
        if (lastButton != null) {
            lastButton.getStyleClass().remove("button-selected");
        }
        button.getStyleClass().add("button-selected");
        lastButton = button;
    }
}
