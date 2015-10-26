/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.ObservacaoAulaDAO;
import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.ObservacaoAula;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AdicionarObservacaoController implements Initializable {

    @FXML
    private AnchorPane apContainer;
    @FXML
    private DatePicker dpData;
    @FXML
    private TextField tfTurma;
    @FXML
    private TextArea taObservacao;

    private ObservacaoAula observacaoAula;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apContainer.getUserData() instanceof Aula) {
                Aula aula = (Aula) apContainer.getUserData();
                observacaoAula.setDia(aula.getId().getDataAula());
                observacaoAula.setTurma(aula.getId().getTurma());
            } else {
                observacaoAula = (ObservacaoAula) apContainer.getUserData();
                taObservacao.setText(observacaoAula.getObservacao());
            }
            tfTurma.setText(observacaoAula.getTurma().toString());
            dpData.setValue(LocalDateTime.ofInstant(Instant.ofEpochMilli(observacaoAula.getDia().getTime()), ZoneId.systemDefault()).toLocalDate());
        });
        observacaoAula = new ObservacaoAula();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        observacaoAula.setObservacao(taObservacao.getText());
        if (observacaoAula.getId() == 0) {
            new ObservacaoAulaDAO().cadastrar(observacaoAula);
        } else {
            new ObservacaoAulaDAO().editar(observacaoAula);
        }
        btCancelarActionEvent(actionEvent);
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) apContainer.getScene().getWindow()).close();
    }
}
