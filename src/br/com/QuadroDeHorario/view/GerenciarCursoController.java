/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.CursoDAO;
import br.com.QuadroDeHorario.dao.MateriaDAO;
import br.com.QuadroDeHorario.dao.TipoDoCursoDAO;
import br.com.QuadroDeHorario.entity.Curso;
import br.com.QuadroDeHorario.entity.Materia;
import br.com.QuadroDeHorario.entity.TipoDoCurso;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
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
public class GerenciarCursoController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private Spinner<Integer> spModulo;
    @FXML
    private ComboBox<TipoDoCurso> cbTipoDoCurso;
    @FXML
    private Spinner<Integer> spCargaHoraria;
    @FXML
    private TableView<Curso> tvCurso;
    @FXML
    private TableColumn<Curso, String> tcNome;
    @FXML
    private TableColumn<Curso, String> tcTipo;
    @FXML
    private TableColumn<Curso, String> tcCargaHoraria;

    private ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private ObservableList<TipoDoCurso> tiposDoCurso = FXCollections.observableArrayList();

    private Curso curso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        curso = new Curso();
        spModulo.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spModulo.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spModulo.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() - 1 > 0) {
                    setValue(spModulo.getValue() - 1);
                }
            }

            @Override
            public void increment(int steps) {
                if (spModulo.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spModulo.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() + 1 < 6) {
                    setValue(spModulo.getValue() + 1);
                }
            }
        });
        spCargaHoraria.setValueFactory(new SpinnerValueFactory<Integer>() {

            @Override
            public void decrement(int steps) {
                if (spCargaHoraria.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spCargaHoraria.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() - 1 >= 0) {
                    setValue(getValue() - 1);
                }
            }

            @Override
            public void increment(int steps) {
                if (spCargaHoraria.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spCargaHoraria.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                setValue(getValue() + 1);
            }
        });
        spCargaHoraria.getValueFactory().setValue(0);
        tvCurso.setItems(cursos);
        cursos.setAll(new CursoDAO().pegarTodos());
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoDoCurso"));
        cbTipoDoCurso.setItems(tiposDoCurso);
        tiposDoCurso.setAll(new TipoDoCursoDAO().pegarTodos());
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        if (curso == null) {
            curso = new Curso();
        }
        try {
            if (!spCargaHoraria.getEditor().getText().isEmpty()) {
                int valor = Integer.parseInt(spCargaHoraria.getEditor().getText());
                spCargaHoraria.getValueFactory().setValue(valor);
            }
        } catch (NumberFormatException e) {
            spCargaHoraria.getValueFactory().setValue(0);
        }
        curso.setCargaHoraria(spCargaHoraria.getValue());
        curso.setModulo(spModulo.getValue());
        curso.setTipoDoCurso(cbTipoDoCurso.getSelectionModel().getSelectedItem());
        curso.setNome(tfNome.getText());
        if (curso.getId() == null) {
            new CursoDAO().cadastrar(curso);
            Mensagem.showInformation("Cadastrado", "Curso foi cadastrado com sucesso!");
        } else {
            new CursoDAO().editar(curso);
            Mensagem.showInformation("Editado", "Curso foi editado com sucesso!");
        }
        curso = new Curso();
        carregarDados();
        cursos.setAll(new CursoDAO().pegarTodos());
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        curso = new Curso();
        carregarDados();
    }

    @FXML
    private void tvCursosMouseReleased(MouseEvent mouseEvent) {
        curso = tvCurso.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        curso = tvCurso.getSelectionModel().getSelectedItem();
        List<Materia> materias=new MateriaDAO().pegarTodosPorCurso(curso);
        if (curso != null) {
            if (Mensagem.showConfirmation("Alerta de exclusão", "Você deseja realmente deseja excluir esse curso?"+(materias.isEmpty()?"":"\nEste curso possui materias relaciconadas!"))) {
                new CursoDAO().excluir(curso);
            }
        }
        cursos.setAll(new CursoDAO().pegarTodos());
        curso = new Curso();
        carregarDados();
    }

    @FXML
    private void tfPesquisarKeyRelease(KeyEvent keyEvent) {
        cursos.setAll(new CursoDAO().pesquisarPorNome(tfPesquisar.getText()));
    }

    private void carregarDados() {
        if (curso == null) {
            tfNome.setText("");
            spModulo.getValueFactory().setValue(1);
            spCargaHoraria.getValueFactory().setValue(0);
            cbTipoDoCurso.getSelectionModel().clearSelection();
        } else {
            tfNome.setText(curso.getNome());
            spModulo.getValueFactory().setValue(curso.getModulo());
            spCargaHoraria.getValueFactory().setValue(curso.getCargaHoraria());
            cbTipoDoCurso.getSelectionModel().select(curso.getTipoDoCurso());
        }
    }
}
