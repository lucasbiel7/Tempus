/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.EventoDAO;
import br.com.QuadroDeHorario.entity.Evento;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class CadastrarEventoController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private ColorPicker cpCor;
    @FXML
    private CheckBox cbLetivo;
    private Stage stage;
    private int ano;
    private boolean escolar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) tfNome.getScene().getWindow();
            Object[] dados = (Object[]) tfNome.getParent().getUserData();;
            ano = (int) dados[0];
            escolar = (boolean) dados[1];
        });

    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        stage.close();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        Evento evento = new Evento();
        evento.setNome(tfNome.getText());
        Color color = cpCor.getValue();
        evento.setColor(color.getRed() + "@" + color.getGreen() + "@" + color.getBlue() + "@" + color.getOpacity());
        evento.setAno(ano);
        evento.setEscolar(escolar);
        evento.setLetivo(!cbLetivo.isSelected());
        new EventoDAO().cadastrar(evento);
        btCancelarActionEvent(actionEvent);
    }

}
