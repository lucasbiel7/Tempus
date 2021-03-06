/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.AmbienteDAO;
import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.TurmaDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.DataHorario.Turno;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarHorarioSemestralController implements Initializable {

    @FXML
    private AnchorPane apContainer;
    public Class classe;
    @FXML
    private ComboBox cbObjeto;
    @FXML
    private ComboBox<DataHorario.Turno> cbTurno;
    @FXML
    private Label lbObjeto;
    @FXML
    private Label lbTurno;

    private ObservableList<Object> objetos = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Turno> turnos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            classe = (Class) apContainer.getUserData();
            if (classe.equals(Turma.class)) {
                lbObjeto.setText("Turma");
                objetos.setAll(new TurmaDAO().pegarTodasEntreData(new Date()));
                cbObjeto.setPromptText("Selecione a turma...");
                turnos.remove(Turno.NOITE);
            } else if (classe.equals(Usuario.class)) {
                cbObjeto.setPromptText("Selecione o instrutor...");
                objetos.setAll(new UsuarioDAO().pegarPorGrupo(new GrupoDAO().pegarGrupo("Instrutor")));
                lbObjeto.setText("Instrutor");
            } else if (classe.equals(Ambiente.class)) {
                lbObjeto.setText("Ambiente");
                cbObjeto.setPromptText("Selecione o ambiente...");
                objetos.setAll(new AmbienteDAO().pegarTodos());
            }
            cbTurno.setPromptText("Selecione o turno...");
            cbTurno.setVisible(!classe.equals(Turma.class));
            lbTurno.visibleProperty().bind(cbTurno.visibleProperty());
        });
        turnos.setAll(Turno.values());
        turnos.remove(Turno.DIURNO);
        cbObjeto.setItems(objetos);
        cbTurno.setItems(turnos);
    }

    @FXML
    private void btVerSemestralActionEvent(ActionEvent actionEvent) {
        if (cbTurno.getSelectionModel().getSelectedItem() instanceof Turno) {
            ((Stage) apContainer.getScene().getWindow()).close();
            FxMananger.show("VisualizarQuadroSemestral", "Quadro semestral", false, true, new Object[]{cbObjeto.getSelectionModel().getSelectedItem(), cbTurno.getSelectionModel().getSelectedItem()});
        } else {
            Mensagem.showError("Selecione o turno", "É necessário selecionar o turno");
        }
    }

    @FXML
    private void cbObjetoActionEvent(ActionEvent actionEvent) {
        if (classe.equals(Turma.class)) {
            cbTurno.setVisible(((Turma) cbObjeto.getSelectionModel().getSelectedItem()).getTurno().equals(Turno.DIURNO));
            cbTurno.getSelectionModel().clearSelection();
            if (!cbTurno.isVisible()) {
                cbTurno.getSelectionModel().select(((Turma) cbObjeto.getSelectionModel().getSelectedItem()).getTurno());
            }
        }
    }
}
