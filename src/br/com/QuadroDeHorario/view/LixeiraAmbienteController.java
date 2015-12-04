/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.AmbienteDAO;
import br.com.QuadroDeHorario.model.entity.Ambiente;
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
public class LixeiraAmbienteController implements Initializable {

    @FXML
    private TableView<Ambiente> tvAmbiente;
    @FXML
    private TableColumn<Ambiente, String> tcNome;
    @FXML
    private TableColumn<Ambiente, String> tcTipo;
    @FXML
    private TableColumn<Ambiente, String> tcDescricao;
    @FXML
    private TableColumn<Ambiente, String> tcCapacidade;

    private ObservableList<Ambiente> ambientes = FXCollections.observableArrayList();
    private Ambiente ambiente;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ambientes.setAll(new AmbienteDAO().pegarTodosLixeira());
        tvAmbiente.setItems(ambientes);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));
    }

    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        ambiente = tvAmbiente.getSelectionModel().getSelectedItem();
        if (ambiente != null) {
            if (Mensagem.showConfirmation("Restaurar ambiente", "Você realmente deseja restaurar ambiente?")) {
                ambiente.setAtivo(true);
                new AmbienteDAO().editar(ambiente);
            }
        }
        ambientes.setAll(new AmbienteDAO().pegarTodosLixeira());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        ambiente = tvAmbiente.getSelectionModel().getSelectedItem();
        if (ambiente != null) {
            if (Mensagem.showConfirmation("Excluir permanentemente", "Você realmente deseja excluir permanentemente esse ambiente?")) {
                new GenericaDAO<Ambiente>().excluir(ambiente);
                ambientes.setAll(new AmbienteDAO().pegarTodosLixeira());
            }
        }
    }
}
