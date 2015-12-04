/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.ProjetoDAO;
import br.com.QuadroDeHorario.control.dao.TurmaDAO;
import br.com.QuadroDeHorario.model.entity.Projeto;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.util.Mensagem;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarProjetoController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TableView<Projeto> tvProjeto;
    @FXML
    private TableColumn<Projeto, String> tcNome;

    private ObservableList<Projeto> projetos = FXCollections.observableArrayList();
    private Projeto projeto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tvProjeto.setItems(projetos);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        projetos.setAll(new ProjetoDAO().pegarTodos());
        projeto = new Projeto();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        projeto.setNome(tfNome.getText());
        if (projeto.getId() == 0) {
            new ProjetoDAO().cadastrar(projeto);
            Mensagem.showInformation("Cadastrado", "Projeto cadastrado com sucesso!");
        } else {
            new ProjetoDAO().editar(projeto);
            Mensagem.showInformation("Editado", "Projeto editado com sucesso!");
        }
        projeto = new Projeto();
        carregarDados();
        projetos.setAll(new ProjetoDAO().pegarTodos());
    }

    @FXML
    private void tvProjetoMouseReleased(MouseEvent mouseEvent) {
        projeto = tvProjeto.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        projeto = new Projeto();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        projeto = tvProjeto.getSelectionModel().getSelectedItem();
        if (projeto != null) {
            List<Turma> turmas = new TurmaDAO().pegarPorProjeto(projeto);
            if (Mensagem.showConfirmation("Alerta de exclusão", "Você deseja realmente deseja excluir esse projeto?" + (turmas.isEmpty() ? "" : "\nEle possui turmas relacionadas!"))) {
                new ProjetoDAO().excluir(projeto);
            }
        }
        projeto = new Projeto();
        carregarDados();
        projetos.setAll(new ProjetoDAO().pegarTodos());
    }

    private void carregarDados() {
        if (projeto != null) {
            tfNome.setText(projeto.getNome());
        } else {
            tfNome.setText("");
        }
    }
}
