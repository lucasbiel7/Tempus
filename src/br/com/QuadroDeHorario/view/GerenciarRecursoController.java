/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AmbienteRecursosDAO;
import br.com.QuadroDeHorario.dao.RecursoDAO;
import br.com.QuadroDeHorario.entity.AmbienteRecursos;
import br.com.QuadroDeHorario.entity.Recurso;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarRecursoController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private TextArea taDescricao;
    @FXML
    private TableView<Recurso> tvRecurso;
    @FXML
    private TableColumn<Recurso, String> tcNome;
    @FXML
    private TableColumn<Recurso, String> tcDescricao;

    private Recurso recurso;
    private ObservableList<Recurso> recursos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recursos.setAll(new RecursoDAO().pegarTodos());
        tvRecurso.setItems(recursos);
        recurso = new Recurso();
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        recurso.setNome(tfNome.getText());
        recurso.setDescricao(taDescricao.getText());
        if (recurso.getId() == 0) {
            new RecursoDAO().cadastrar(recurso);
            Mensagem.showInformation("Cadastrado", "Recurso cadastrado com sucesso!");
        } else {
            new RecursoDAO().editar(recurso);
            Mensagem.showInformation("Editado", "Recurso editado com sucesso!");
        }
        recursos.setAll(new RecursoDAO().pegarTodos());
        recurso = new Recurso();
        carregarDados();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        recurso = new Recurso();
        carregarDados();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        recurso = tvRecurso.getSelectionModel().getSelectedItem();
        if (recurso != null) {
            List<AmbienteRecursos> ambienteRecurso=new AmbienteRecursosDAO().pegarPorRecurso(recurso);
            if (Mensagem.showConfirmation("Alerta de exclusão", "Você deseja realmente deseja excluir esse recurso?")) {
                new RecursoDAO().excluir(recurso);
            }
        }
        recurso = new Recurso();
        carregarDados();
        recursos.setAll(new RecursoDAO().pegarTodos());
    }

    @FXML
    private void tvRecursoMouseReleased(MouseEvent mouseEvent) {
        recurso = tvRecurso.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void tfPesquisarKeyRelease(KeyEvent keyEvent) {
        recursos.setAll(new RecursoDAO().pesquisarPorNome(tfPesquisar.getText()));
    }

    private void carregarDados() {
        if (recurso == null) {
            tfNome.setText("");
            taDescricao.setText("");
        } else {
            tfNome.setText(recurso.getNome());
            taDescricao.setText(recurso.getDescricao());
        }
    }

}
