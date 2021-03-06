/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.CursoDAO;
import br.com.QuadroDeHorario.model.entity.Curso;
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
public class LixeiraCursoController implements Initializable {

    @FXML
    private TableView<Curso> tvCursos;
    @FXML
    private TableColumn<Curso, String> tcNome;
    @FXML
    private TableColumn<Curso, String> tcProjeto;
    @FXML
    private TableColumn<Curso, String> tcTipo;
    @FXML
    private TableColumn<Curso, Integer> tcCargaHoraria;

    private ObservableList<Curso> cursos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursos.setAll(new CursoDAO().pegarTodosLixeira());
        tvCursos.setItems(cursos);
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        tcProjeto.setCellValueFactory(new PropertyValueFactory<>("projeto"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoDoCurso"));
    }

    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        Curso curso = tvCursos.getSelectionModel().getSelectedItem();
        if (curso != null) {
            if (Mensagem.showConfirmation("Restaurar curso", "Você realmente deseja restaurar curso?")) {
                curso.setAtivo(true);
                new CursoDAO().editar(curso);
            }
        }
        cursos.setAll(new CursoDAO().pegarTodosLixeira());
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        Curso curso = tvCursos.getSelectionModel().getSelectedItem();
        if (curso != null) {
            if (Mensagem.showConfirmation("Excluir", "Você realmente excluir permanentemente esse curso?")) {
                new GenericaDAO<Curso>().excluir(curso);
                cursos.setAll(new CursoDAO().pegarTodosLixeira());
            }
        }
    }
}
