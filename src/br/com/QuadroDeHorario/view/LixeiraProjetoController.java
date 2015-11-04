/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.ProjetoDAO;
import br.com.QuadroDeHorario.entity.Projeto;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.util.Mensagem;
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
public class LixeiraProjetoController implements Initializable {

    @FXML
    private TableView<Projeto> tvProjeto;
    @FXML
    private TableColumn<Projeto, String> tcNome;

    private ObservableList<Projeto> projetos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvProjeto.setItems(projetos);
        projetos.setAll(new ProjetoDAO().pegarTodosLixeira());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }

    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        Projeto projeto = tvProjeto.getSelectionModel().getSelectedItem();
        if (projeto != null) {
            projeto.setAtivo(true);
            new ProjetoDAO().editar(projeto);
        }
        projetos.setAll(new ProjetoDAO().pegarTodosLixeira());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        Projeto projeto = tvProjeto.getSelectionModel().getSelectedItem();
        if (projeto != null) {
            if (Mensagem.showConfirmation("Excluir permanentemente", "Você realmente deseja excluir permanentemente esse projeto?")) {
                new GenericaDAO<Projeto>().excluir(projeto);
                projetos.setAll(new ProjetoDAO().pegarTodosLixeira());
            }
        }
    }
}
