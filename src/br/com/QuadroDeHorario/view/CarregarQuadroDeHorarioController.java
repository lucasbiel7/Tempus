/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaHorario;
import br.com.QuadroDeHorario.control.dao.CursoDAO;
import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.TurmaDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Curso;
import br.com.QuadroDeHorario.model.entity.Permissao;
import br.com.QuadroDeHorario.model.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.DataHorario.Turno;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.SessaoUsuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class CarregarQuadroDeHorarioController implements Initializable {

    @FXML
    private ComboBox<Curso> cbCurso;
    @FXML
    private ComboBox<Turma> cbTurma;
    @FXML
    private ComboBox<Curso> cbCursoImpressao;
    @FXML
    private ComboBox<Turma> cbTurmaImpressao;
    @FXML
    private ComboBox<Usuario> cbInstrutor;
    @FXML
    private ComboBox<DataHorario.Turno> cbTurno;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Button btAbrir;
    @FXML
    private Button btVisualizarImpressaoTurma;
    @FXML
    private Button btVisualizarImpressaoInstrutor;

    private ScrollPane spPrincipal;

    private ObservableList<Curso> cursos = FXCollections.observableArrayList();
    private ObservableList<Turma> turmas = FXCollections.observableArrayList();
    private ObservableList<Curso> cursosImpressao = FXCollections.observableArrayList();
    private ObservableList<Turma> turmasImpressao = FXCollections.observableArrayList();
    private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Turno> turnos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            spPrincipal = (ScrollPane) apPrincipal.getUserData();
        });
        cbCurso.setItems(cursos);
        cbTurma.setItems(turmas);
        cbCursoImpressao.setItems(cursosImpressao);
        cbTurmaImpressao.setItems(turmasImpressao);
        cbInstrutor.setItems(usuarios);
        cbTurno.setItems(turnos);
        cursos.setAll(new CursoDAO().pegarTodos());
        cursosImpressao.setAll(new CursoDAO().pegarTodos());
        usuarios.setAll(new UsuarioDAO().pegarPorGrupo(new GrupoDAO().pegarPorId(2)));
        turnos.setAll(Turno.values());
        turnos.removeIf((Turno t) -> t == Turno.DIURNO);
        permissoes();
    }

    @FXML
    private void cbCursoActionEvent(ActionEvent actionEvent) {
        Curso curso = cbCurso.getSelectionModel().getSelectedItem();
        if (curso != null) {
            turmas.setAll(new TurmaDAO().pegarTodosPorCurso(curso));
        } else {
            turmas.clear();
        }
    }

    @FXML
    private void cbCursoImpressaoActionEvent(ActionEvent actionEvent) {
        Curso curso = cbCursoImpressao.getSelectionModel().getSelectedItem();
        if (curso != null) {
            turmasImpressao.setAll(new TurmaDAO().pegarTodosPorCurso(curso));
        } else {
            turmas.clear();
        }
    }

    @FXML
    private void btAbrirActionEvent(ActionEvent actionEvent) {
        if (cbTurma.getSelectionModel().getSelectedItem() != null) {
//            apPrincipal.getChildren().clear();
            TabelaHorario.aulasGeminadas = false;
            TabelaHorario.autoPreencher = false;
            TabelaHorario.ambienteSelecionado = null;
            TabelaHorario.turma = null;
            TabelaHorario.materiaHorarioSelecionado = null;
            FxMananger.insertPane(spPrincipal, "QuadroDeHorario", cbTurma.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void btVisualizarImpressaoTurmaActionEvent(ActionEvent actionEvent) {
        if (cbTurmaImpressao.getSelectionModel().getSelectedItem() != null) {
            FxMananger.insertPane(spPrincipal, "ImprimirQuadro", new Object[]{cbTurmaImpressao.getSelectionModel().getSelectedItem()});
        }
    }

    @FXML
    private void btVisualizarImpressaoInstrutorActionEvent(ActionEvent actionEvent) {
        if (cbInstrutor.getSelectionModel().getSelectedItem() != null && cbTurno.getSelectionModel().getSelectedItem() != null) {
            FxMananger.insertPane(spPrincipal, "ImprimirQuadro", new Object[]{cbInstrutor.getSelectionModel().getSelectedItem(), cbTurno.getSelectionModel().getSelectedItem()});
        }
    }

    private void permissoes() {
        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CRIAR_QUADRO);
        if (permissaoUsuario != null) {
            btAbrir.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btAbrir.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.VISUALIZAR_TURMA);
        if (permissaoUsuario != null) {
            btVisualizarImpressaoTurma.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btVisualizarImpressaoTurma.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.VISUALIZAR_INSTRUTOR);
        if (permissaoUsuario != null) {
            btVisualizarImpressaoInstrutor.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btVisualizarImpressaoInstrutor.setDisable(true);
        }
    }
}
