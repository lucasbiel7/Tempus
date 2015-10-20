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
public class EditarEventoController implements Initializable {
    
    @FXML
    private TextField tfNome;
    @FXML
    private ColorPicker cpCor;
    @FXML
    private CheckBox cbLetivo;
    private Stage stage;
    private int ano;
    private Evento evento;
    
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) tfNome.getScene().getWindow();
            evento = (Evento) tfNome.getParent().getUserData();
            tfNome.setText(evento.getNome());
            String[] cores = evento.getColor().split("@");
            cbLetivo.setSelected(!evento.isLetivo());
            Color color = new Color(Double.parseDouble(cores[0]), Double.parseDouble(cores[1]), Double.parseDouble(cores[2]), Double.parseDouble(cores[3]));
            cpCor.setValue(color);
        });
    }
    
    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        stage.close();
    }
    
    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        evento.setNome(tfNome.getText());
        Color color = cpCor.getValue();
        evento.setColor(color.getRed() + "@" + color.getGreen() + "@" + color.getBlue() + "@" + color.getOpacity());
        evento.setLetivo(!cbLetivo.isSelected());
        new EventoDAO().editar(evento);
        btCancelarActionEvent(actionEvent);
    }
    
}
