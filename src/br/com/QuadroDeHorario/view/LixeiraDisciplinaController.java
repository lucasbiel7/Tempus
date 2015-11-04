/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.MateriaDAO;
import br.com.QuadroDeHorario.entity.Materia;
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
public class LixeiraDisciplinaController implements Initializable {

    @FXML
    private TableView<Materia> tvMateria;
    @FXML
    private TableColumn<Materia, String> tcSigla;
    @FXML
    private TableColumn<Materia, String> tcNome;
    @FXML
    private TableColumn<Materia, String> tcCurso;
    @FXML
    private TableColumn<Materia, String> tcModulo;
    @FXML
    private TableColumn<Materia, Integer> tcCargaHoraria;

    private ObservableList<Materia> materias = FXCollections.observableArrayList();
    private Materia materia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materias.setAll(new MateriaDAO().pegarTodosLixeira());
        tvMateria.setItems(materias);
        tcSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tcModulo.setCellValueFactory(new PropertyValueFactory<>("modulo"));
        tcCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
    }

    @FXML
    private void miRestaurarActionEvent(ActionEvent actionEvent) {
        materia = tvMateria.getSelectionModel().getSelectedItem();
        if (materia != null) {
            if (Mensagem.showConfirmation("Restaurar componente curricular", "Você deseja restaurar esse componente curricular?")) {
                materia.setAtivo(true);
                new MateriaDAO().editar(materia);
            }
            materias.setAll(new MateriaDAO().pegarTodosLixeira());
        }
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        materia = tvMateria.getSelectionModel().getSelectedItem();
        if (materia != null) {
            if (Mensagem.showConfirmation("Excluir", "Deseja excluir permanentemente o componente curricular?")) {
                new GenericaDAO<Materia>().excluir(materia);
                materias.setAll(new MateriaDAO().pegarTodosLixeira());
            }
        }
    }

}
