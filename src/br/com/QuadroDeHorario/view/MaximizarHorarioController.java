/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class MaximizarHorarioController implements Initializable {

    @FXML
    private AnchorPane apTabela;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            apTabela.getChildren().clear();
            apTabela.getChildren().add((Node) apTabela.getUserData());
            ((Stage) apTabela.getScene().getWindow()).setFullScreen(true);
            apTabela.getScene().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode().equals(KeyCode.ESCAPE)) {
                    ((Stage) apTabela.getScene().getWindow()).close();
                }
            });
            apTabela.setOnZoomFinished((ZoomEvent event) -> {
                Platform.runLater(() -> {
                    ((Stage) apTabela.getScene().getWindow()).close();
                });
                event.consume();
            });
        });
    }
}
