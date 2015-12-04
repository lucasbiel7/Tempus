/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.CursoDAO;
import br.com.QuadroDeHorario.control.dao.MateriaDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.control.dao.MateriaRecursosDAO;
import br.com.QuadroDeHorario.control.dao.RecursoDAO;
import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.Materia;
import br.com.QuadroDeHorario.model.entity.MateriaHorario;
import br.com.QuadroDeHorario.model.entity.MateriaRecursos;
import br.com.QuadroDeHorario.model.entity.MateriaRecursosID;
import br.com.QuadroDeHorario.model.entity.Recurso;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.hibernate.exception.ConstraintViolationException;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarDisciplinaController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfSigla;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private Spinner<Integer> spCargaHoraria;
    @FXML
    private Spinner<Integer> spModulo;
    @FXML
    private ListView<Recurso> lvRecurso;
    @FXML
    private ListView<Recurso> lvRecursoAdicionado;
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
    private TableColumn<Materia, String> tcCargaHoraria;

    private ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private ObservableList<Recurso> recursos = FXCollections.observableArrayList();
    private ObservableList<Recurso> recursosAdicionados = FXCollections.observableArrayList();
    private ObservableList<Materia> materias = FXCollections.observableArrayList();

    private Materia materia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbCurso.setItems(cursos);
        tvMateria.setItems(materias);
        lvRecurso.setItems(recursos);
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
                setValue(spModulo.getValue() + 1);
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
                if (getValue() - 1 > 0) {
                    setValue(getValue() - steps);
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
                setValue(getValue() + steps);
            }
        });
        lvRecursoAdicionado.setItems(recursosAdicionados);
        cursos.setAll(new CursoDAO().pegarTodos());
        recursos.setAll(new RecursoDAO().pegarTodos());
        recursosAdicionados.clear();
        materias.setAll(new MateriaDAO().pegarTodos());
        tcSigla.setCellValueFactory(new PropertyValueFactory<>("sigla"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tcModulo.setCellValueFactory(new PropertyValueFactory<>("modulo"));
        tcCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("cargaHoraria"));
        materia = new Materia();
        carregarDados();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        materia = new Materia();
        carregarDados();
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        try {
            if (!spCargaHoraria.getEditor().getText().isEmpty()) {
                int valor = Integer.parseInt(spCargaHoraria.getEditor().getText());
                spCargaHoraria.getValueFactory().setValue(valor);
            }
        } catch (NumberFormatException e) {
            spCargaHoraria.getValueFactory().setValue(0);
        }
        try {
            if (!spModulo.getEditor().getText().isEmpty()) {
                int valor = Integer.parseInt(spModulo.getEditor().getText());
                spModulo.getValueFactory().setValue(valor);
            }
        } catch (NumberFormatException e) {
            spModulo.getValueFactory().setValue(0);
        }
        materia.setNome(tfNome.getText());
        materia.setSigla(tfSigla.getText());
        materia.setCurso(cbCurso.getSelectionModel().getSelectedItem());
        materia.setCargaHoraria(spCargaHoraria.getValue());
        materia.setModulo(spModulo.getValue());
        if (materia.getId() == null) {
            new MateriaDAO().cadastrar(materia);
            Mensagem.showInformation("Cadastrado", "Componente curricular cadastrado com sucesso!");
        } else {
            new MateriaDAO().editar(materia);
            Mensagem.showInformation("Editado", "Componente curricular editado com sucesso!");
        }
        for (MateriaRecursos materiaRecursos : new MateriaRecursosDAO().pegarTodosPorMateria(materia)) {
            materiaRecursos.setAtivo(false);
            new MateriaRecursosDAO().editar(materiaRecursos);
        }
        for (Recurso recursosAdicionado : recursosAdicionados) {
            try {
                MateriaRecursos materiaRecursos = new MateriaRecursosDAO().pegarPorMateriaRecurso(materia, recursosAdicionado);
                if (materiaRecursos != null) {
                    materiaRecursos.setAtivo(true);
                    new MateriaRecursosDAO().editar(materiaRecursos);
                } else {
                    new MateriaRecursosDAO().cadastrar(new MateriaRecursos(new MateriaRecursosID(materia, recursosAdicionado)));
                }
            } catch (ConstraintViolationException e) {
            }
        }
        materias.setAll(new MateriaDAO().pegarTodos());
        materia = new Materia();
        carregarDados();
    }

    @FXML
    private void tvMateriaMouseReleased(MouseEvent mouseEvent) {
        materia = tvMateria.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        materia = tvMateria.getSelectionModel().getSelectedItem();
        if (materia != null) {
            List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarPorMateria(materia);
            if (Mensagem.showConfirmation("Confirmar exclusão", "Você realmente deseja excluir esse componente curricular?" + (materiaHorarios.isEmpty() ? "" : "Este componente curricular está sendo usada no quadro de horário"))) {
                new MateriaDAO().excluir(materia);
            }
        }
        materias.setAll(new MateriaDAO().pegarTodos());
        materia = new Materia();
        carregarDados();
    }

    @FXML
    private void lvRecursoMouseReleased(MouseEvent mouseEvent) {
        Recurso recurso = lvRecurso.getSelectionModel().getSelectedItem();
        if (recurso != null && mouseEvent.getClickCount() > 1) {
            recursosAdicionados.add(recurso);
            recursos.remove(recurso);
        }
    }

    @FXML
    private void jtNomeKeyRelease(KeyEvent keyEvent) {
        materias.setAll(new MateriaDAO().pegarTodosPorNome(tfPesquisar.getText()));
    }

    @FXML
    private void lvRecursosAdicionadosMouseReleased(MouseEvent mouseEvent) {
        Recurso recurso = lvRecursoAdicionado.getSelectionModel().getSelectedItem();
        if (recurso != null && mouseEvent.getClickCount() > 1) {
            recursos.add(recurso);
            recursosAdicionados.remove(recurso);
        }
    }

    private void carregarDados() {
        if (materia == null) {
            tfNome.setText("");
            tfSigla.setText("");
            cbCurso.getSelectionModel().clearSelection();
            spCargaHoraria.getValueFactory().setValue(0);
            spModulo.getValueFactory().setValue(0);
            recursos.setAll(new RecursoDAO().pegarTodos());
            recursosAdicionados.clear();
        } else {
            tfNome.setText(materia.getNome());
            tfSigla.setText(materia.getSigla());
            cbCurso.getSelectionModel().select(materia.getCurso());
            spCargaHoraria.getValueFactory().setValue(materia.getCargaHoraria());
            spModulo.getValueFactory().setValue(materia.getModulo());
            recursos.setAll(new RecursoDAO().pegarTodos());
            recursosAdicionados.clear();
            if (materia.getId() != null) {
                for (MateriaRecursos materiaRecursos : new MateriaRecursosDAO().pegarTodosPorMateria(materia)) {
                    recursosAdicionados.add(materiaRecursos.getId().getRecurso());
                    recursos.remove(materiaRecursos.getId().getRecurso());
                }
            }
        }
    }
}
