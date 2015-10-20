/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.TurmaDAO;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LixeiraTurmaController implements Initializable {
    
    @FXML
    private TableView<Turma> tvTurma;
    @FXML
    private TableColumn<Turma, String> tcNome;
    @FXML
    private TableColumn<Turma, String> tcCurso;
    @FXML
    private TableColumn<Turma, String> tcEmail;
    @FXML
    private TableColumn<Turma, Boolean> tcEspelho;
    @FXML
    private TableColumn<Turma, Date> tcInicio;
    @FXML
    private TableColumn<Turma, Date> tcFim;
    @FXML
    private TableColumn<Turma, String> tcTurno;
    
    private ObservableList<Turma> turmas = FXCollections.observableArrayList();
    private Turma turma;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        turmas.setAll(new TurmaDAO().pegarTodosLixeira());
        tvTurma.setItems(turmas);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcEspelho.setCellValueFactory(new PropertyValueFactory<>("espelho"));
        tcInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        tcFim.setCellValueFactory(new PropertyValueFactory<>("fim"));
        tcTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        tcEspelho.setCellFactory((TableColumn<Turma, Boolean> param) -> new TableCell<Turma, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                setGraphic(null);
                if (!empty) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    checkBox.setDisable(true);
                    setGraphic(checkBox);
                }
            }
        });
        tcInicio.setCellFactory((TableColumn<Turma, Date> param) -> new TableCell<Turma, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (!empty) {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                } else {
                    setText("");
                }
            }
            
        });
        tcFim.setCellFactory((TableColumn<Turma, Date> param) -> new TableCell<Turma, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (!empty) {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                } else {
                    setText("");
                }
            }
            
        });
    }
    
    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        turma = tvTurma.getSelectionModel().getSelectedItem();
        if (turma != null) {
            if (Mensagem.showConfirmation("Restaurar turma", "Você realmente deseja restaurar essa turma?")) {
                turma.setAtivo(true);
                new TurmaDAO().editar(turma);
            }
        }
        turmas.setAll(new TurmaDAO().pegarTodosLixeira());
    }
}
