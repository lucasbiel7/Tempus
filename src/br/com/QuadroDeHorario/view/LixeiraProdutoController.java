/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.TipoDoCursoDAO;
import br.com.QuadroDeHorario.model.entity.TipoDoCurso;
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
public class LixeiraProdutoController implements Initializable {

    @FXML
    private TableView<TipoDoCurso> tvTipoCurso;
    @FXML
    private TableColumn<TipoDoCurso, String> tcDescricao;
    private ObservableList<TipoDoCurso> tipoDoCursos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tvTipoCurso.setItems(tipoDoCursos);
        tipoDoCursos.setAll(new TipoDoCursoDAO().pegarTodosLixeira());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        TipoDoCurso tipoDoCurso = tvTipoCurso.getSelectionModel().getSelectedItem();
        if (tipoDoCurso != null) {
            if (Mensagem.showConfirmation("Excluir permanentemente", "Você realmente deseja excluir permanentemente o produto " + tipoDoCurso.getDescricao() + "?")) {
                new GenericaDAO<>().excluir(tipoDoCurso);
            }
        }
        tipoDoCursos.setAll(new TipoDoCursoDAO().pegarTodosLixeira());
    }

    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        TipoDoCurso tipoDoCurso = tvTipoCurso.getSelectionModel().getSelectedItem();
        if (tipoDoCurso != null) {
            if (Mensagem.showConfirmation("Restaurar", "Você tem certeza que deseja restaurar o produto " + tipoDoCurso.getDescricao() + "?")) {
                tipoDoCurso.setAtivo(true);
                new TipoDoCursoDAO().editar(tipoDoCurso);
            }
        }
        tipoDoCursos.setAll(new TipoDoCursoDAO().pegarTodosLixeira());
    }

}
