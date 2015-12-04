/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.RecursoDAO;
import br.com.QuadroDeHorario.model.entity.Recurso;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LixeiraRecursoController implements Initializable {

    @FXML
    private TableView<Recurso> tvRecursos;
    @FXML
    private TableColumn<Recurso, String> tcNome;
    @FXML
    private TableColumn<Recurso, String> tcDescricao;

    private ObservableList<Recurso> recursos = FXCollections.observableArrayList();
    private Recurso recurso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvRecursos.setItems(recursos);
        recursos.setAll(new RecursoDAO().pegarTodosLixeira());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        recurso = tvRecursos.getSelectionModel().getSelectedItem();
        if (recurso != null) {
            if (Mensagem.showConfirmation("Restaurar recurso", "Você realmente deseja restaurar recursos?")) {
                recurso.setAtivo(true);
                new RecursoDAO().editar(recurso);
            }
        }
        recursos.setAll(new RecursoDAO().pegarTodosLixeira());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        recurso = tvRecursos.getSelectionModel().getSelectedItem();
        if (recurso != null) {
            if (Mensagem.showConfirmation("Excluir permanente", "Você realmente deseja excluir permanentemente o recurso?")) {
                new GenericaDAO<Recurso>().excluir(recurso);
                recursos.setAll(new RecursoDAO().pegarTodosLixeira());
            }
        }
    }
}
