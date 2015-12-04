/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Permissao;
import br.com.QuadroDeHorario.model.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.SessaoUsuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LixeiraController implements Initializable {

    @FXML
    private ScrollPane spProjeto;
    @FXML
    private ScrollPane spCurso;
    @FXML
    private ScrollPane spRecurso;
    @FXML
    private ScrollPane spAmbiente;
    @FXML
    private ScrollPane spTurma;
    @FXML
    private ScrollPane spDisciplina;
    @FXML
    private ScrollPane spProduto;
    @FXML
    private ScrollPane spInstrutor;
    @FXML
    private TabPane tbAbas;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            tbProjetoActionEvent(null);
            stage = (Stage) tbAbas.getScene().getWindow();
        });
        permissoes();
    }

    @FXML
    private void tbProjetoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spProjeto, "LixeiraProjeto");
    }

    @FXML
    private void tbRecursoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spRecurso, "LixeiraRecurso");
    }

    @FXML
    private void tbCursoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spCurso, "LixeiraCurso");
    }

    @FXML
    private void tbAmbienteActionEvent(Event actionEvent) {
        FxMananger.insertPane(spAmbiente, "LixeiraAmbiente");
    }

    @FXML
    private void tbTurmaActionEvent(Event actionEvent) {
        FxMananger.insertPane(spTurma, "LixeiraTurma");
    }

    @FXML
    private void tbDisciplinaActionEvent(Event actionEvent) {
        FxMananger.insertPane(spDisciplina, "LixeiraDisciplina");
    }

    @FXML
    private void tbInstrutorActionEvent(Event actionEvent) {
        FxMananger.insertPane(spInstrutor, "LixeiraUsuario");
    }

    @FXML
    private void tbProdutoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spProduto, "LixeiraProduto");
    }

    private void permissoes() {
        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.PROJETO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(0).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(0).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CURSO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(1).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(1).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.RECURSO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(2).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(2).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.AMBIENTE);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(3).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(3).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.TURMA);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(4).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(4).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.DISCIPLINA);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(5).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(5).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.INSTRUTOR);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(6).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(6).setDisable(true);
        }
    }
}
