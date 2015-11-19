/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.TipoDoCursoDAO;
import br.com.QuadroDeHorario.entity.TipoDoCurso;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarTipoCursoController implements Initializable {

    private TipoDoCurso tipoCurso;
    @FXML
    private TextField tfDescricao;
    @FXML
    private TableView<TipoDoCurso> tvTipoCurso;
    @FXML
    private TableColumn<TipoDoCurso, String> tcDescricao;
    @FXML
    private TextField tfPesquisa;
    private ObservableList<TipoDoCurso> tipoDoCursos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tvTipoCurso.setItems(tipoDoCursos);
        tipoDoCursos.setAll(new TipoDoCursoDAO().pegarTodos());
        tipoCurso=new TipoDoCurso();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        tipoCurso.setDescricao(tfDescricao.getText());
        if (tipoCurso.getId() == null) {
            new TipoDoCursoDAO().cadastrar(tipoCurso);
            Mensagem.showInformation("Cadastrado", "Produto foi cadastrado com sucesso!");
        } else {
            new TipoDoCursoDAO().editar(tipoCurso);
            Mensagem.showInformation("Editado", "Produto foi editado com sucesso!");
        }
        tipoCurso = new TipoDoCurso();
        carregarTipoCurso();
        tipoDoCursos.setAll(new TipoDoCursoDAO().pegarTodos());
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        tipoCurso = new TipoDoCurso();
        carregarTipoCurso();
    }

    @FXML
    private void tfPesquisaKeyRelease(KeyEvent keyEvent) {
        tipoDoCursos.setAll(new TipoDoCursoDAO().pegarPorPesquisa(tfPesquisa.getText()));
    }

    @FXML
    private void tvTipoDoCursoMouseReleased(MouseEvent mouseEvent) {
        if (tvTipoCurso.getSelectionModel().getSelectedItem() != null) {
            tipoCurso = tvTipoCurso.getSelectionModel().getSelectedItem();
        } else {
            tipoCurso = new TipoDoCurso();
        }
        carregarTipoCurso();
    }

    private void carregarTipoCurso() {
        tfDescricao.setText(tipoCurso.getDescricao());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        if (tvTipoCurso.getSelectionModel().getSelectedItem() != null) {
            if (Mensagem.showConfirmation("Excluir Produto", "Você realmente deseja excluir o Produto?\n"
                    + "Ao excluir o produto todos os cursos \n"
                    + "desse produto será apagado também")) {
                new TipoDoCursoDAO().excluir(tipoCurso);
                tipoCurso = new TipoDoCurso();
                carregarTipoCurso();
                tipoDoCursos.setAll(new TipoDoCursoDAO().pegarPorPesquisa(tfPesquisa.getText()));
            }
        }
    }
}
