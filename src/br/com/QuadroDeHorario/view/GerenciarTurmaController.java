/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.CursoDAO;
import br.com.QuadroDeHorario.dao.MateriaHorarioDAO;
import br.com.QuadroDeHorario.dao.ProjetoDAO;
import br.com.QuadroDeHorario.dao.TurmaDAO;
import br.com.QuadroDeHorario.entity.Curso;
import br.com.QuadroDeHorario.entity.MateriaHorario;
import br.com.QuadroDeHorario.entity.Projeto;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.util.DataHorario;
import br.com.QuadroDeHorario.util.DatePickerValidator;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarTurmaController implements Initializable {

    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private TextField tfEmail;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFim;
    @FXML
    private ComboBox<DataHorario.Turno> cbTurno;
    @FXML
    private ComboBox<Projeto> cbProjeto;
    @FXML
    private CheckBox cbEspelho;
    @FXML
    private TableView<Turma> tvTurma;
    @FXML
    private TableColumn<Turma, String> tcDescricao;
    @FXML
    private TableColumn<Turma, String> tcCurso;
    @FXML
    private TableColumn<Turma, String> tcEmail;
    @FXML
    private TableColumn<Turma, Boolean> tcEspelho;
    @FXML
    private TableColumn<Turma, Date> tcInicio;
    @FXML
    private TableColumn<Turma, Date> tcFim;
    @FXML
    private TableColumn<Turma, String> tcTurno;
    @FXML
    private TableColumn<Turma, Integer> tcModulo;
    @FXML
    private TableColumn<Turma, String> tcProjeto;
    private Stage stage;
    private Turma turma;
    private ObservableList<DataHorario.Turno> turnos = FXCollections.observableArrayList();
    private ObservableList<Turma> turmas = FXCollections.observableArrayList();
    private ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private ObservableList<Projeto> projetos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (tvTurma.getScene() != null) {
                stage = (Stage) tvTurma.getScene().getWindow();
            }
        });
        turnos.setAll(DataHorario.Turno.values());
        cbTurno.setItems(turnos);
        cursos.setAll(new CursoDAO().pegarTodos());
        cbCurso.setItems(cursos);
        turmas.setAll(new TurmaDAO().pegarTodos());
        tvTurma.setItems(turmas);
        tcProjeto.setCellValueFactory(new PropertyValueFactory<>("projeto"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcCurso.setCellValueFactory(new PropertyValueFactory<>("curso"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tcEspelho.setCellValueFactory(new PropertyValueFactory<>("espelho"));
        tcInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        tcFim.setCellValueFactory(new PropertyValueFactory<>("fim"));
        tcTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        tcModulo.setCellValueFactory(new PropertyValueFactory<>("modulo"));
        tcEspelho.setCellFactory((TableColumn<Turma, Boolean> param) -> new TableCell<Turma, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                setGraphic(null);
                if (!empty) {
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    checkBox.setDisable(true);
                    setGraphic(checkBox);
                }
            }
        });
        tcInicio.setCellFactory((TableColumn<Turma, Date> param) -> new TableCell<Turma, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (!empty) {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                } else {
                    setText("");
                }
            }

        });
        tcFim.setCellFactory((TableColumn<Turma, Date> param) -> new TableCell<Turma, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                if (!empty) {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                } else {
                    setText("");
                }
            }

        });
        cbProjeto.setItems(projetos);
        projetos.setAll(new ProjetoDAO().pegarTodos());
        turma = new Turma();
        carregarDados();
        dpInicio.setOnKeyReleased(new DatePickerValidator(dpInicio));
        dpFim.setOnKeyReleased(new DatePickerValidator(dpFim));
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        turma.setDescricao(tfNome.getText());
        turma.setCurso(cbCurso.getSelectionModel().getSelectedItem());
        turma.setEmail(tfEmail.getText());
        turma.setInicio(Date.from(dpInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        turma.setFim(Date.from(dpFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        turma.setTurno(cbTurno.getSelectionModel().getSelectedItem());
        turma.setProjeto(cbProjeto.getSelectionModel().getSelectedItem());
        if (turma.getId() == null) {
            new TurmaDAO().cadastrar(turma);
            if (cbEspelho.isSelected()) {
                Turma turmaEspelho = new Turma();
                turmaEspelho.setTurmaPrincipal(turma);
                turmaEspelho.setEspelho(true);
                turmaEspelho.setDescricao(turma.getDescricao() + "(2)");
                turmaEspelho.setCurso(cbCurso.getSelectionModel().getSelectedItem());
                turmaEspelho.setEmail(tfEmail.getText());
                turmaEspelho.setInicio(Date.from(dpInicio.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                turmaEspelho.setFim(Date.from(dpFim.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                turmaEspelho.setTurno(cbTurno.getSelectionModel().getSelectedItem());
                new TurmaDAO().cadastrar(turmaEspelho);
            }
            Mensagem.showInformation("Cadastrado", "Turma foi cadastrada com sucesso!");
        } else {
            new TurmaDAO().editar(turma);
            Mensagem.showInformation("Editado", "Turma foi editada com sucesso!");
        }
        turmas.setAll(new TurmaDAO().pegarTodos());
        turma = new Turma();
        carregarDados();
    }

    @FXML
    private void btNovoActionEvent(ActionEvent actionEvent) {
        turma = new Turma();
        carregarDados();
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        turma = tvTurma.getSelectionModel().getSelectedItem();
        if (turma != null) {
            List<MateriaHorario> materiaHorarios = new MateriaHorarioDAO().pegarPorTurma(turma);
            if (Mensagem.showConfirmation("Excluir Turma!", "Você tem certeza que deseja excluir essa turma?" + (materiaHorarios.isEmpty() ? "" : "Esse registro possui dados relacionados a ele!"))) {
                new TurmaDAO().excluir(turma);
            }
        }
        turmas.setAll(new TurmaDAO().pegarTodos());
        turma = new Turma();
        carregarDados();
    }

    @FXML
    private void tvTurmaMouseReleased(MouseEvent mouseEvent) {
        turma = tvTurma.getSelectionModel().getSelectedItem();
        carregarDados();
    }

    @FXML
    private void tfPesquisarKeyRelease(KeyEvent keyEvent) {
        turmas.setAll(new TurmaDAO().pesquisarPorNome(tfPesquisar.getText()));
    }

    private void carregarDados() {
        if (turma == null) {
            tfNome.setText("");
            cbCurso.getSelectionModel().clearSelection();
            tfEmail.setText("");
            dpInicio.setValue(null);
            dpFim.setValue(null);
            cbTurno.getSelectionModel().clearSelection();
            cbProjeto.getSelectionModel().clearSelection();
            cbEspelho.setSelected(false);
        } else {
            tfNome.setText(turma.getDescricao());
            cbCurso.getSelectionModel().select(turma.getCurso());
            tfEmail.setText(turma.getEmail());
            if (turma.getInicio() != null) {
                dpInicio.setValue(Instant.ofEpochMilli(turma.getInicio().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                dpInicio.setValue(null);
            }
            if (turma.getFim() != null) {
                dpFim.setValue(Instant.ofEpochMilli(turma.getFim().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
            } else {
                dpFim.setValue(null);
            }
            cbProjeto.getSelectionModel().select(turma.getProjeto());
            cbTurno.getSelectionModel().select(turma.getTurno());
            cbEspelho.setSelected(turma.isEspelho());
        }
    }

}
