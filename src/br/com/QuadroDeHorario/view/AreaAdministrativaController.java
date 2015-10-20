/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.entity.Permissao;
import br.com.QuadroDeHorario.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.SessaoUsuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AreaAdministrativaController implements Initializable {

    @FXML
    private AnchorPane apProjeto;
    @FXML
    private AnchorPane apCurso;
    @FXML
    private AnchorPane apRecurso;
    @FXML
    private AnchorPane apAmbiente;
    @FXML
    private AnchorPane apTurma;
    @FXML
    private AnchorPane apDisciplina;
    @FXML
    private AnchorPane apInstrutor;
    @FXML
    private AnchorPane apPermissao;
    @FXML
    private AnchorPane apConstanteSistema;
    @FXML
    private TabPane tbAbas;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            tbProjetoActionEvent(null);
            stage = (Stage) apAmbiente.getScene().getWindow();
        });
        permissao();
    }

    @FXML
    private void tbProjetoActionEvent(Event actionEvent) {
        FxMananger.insertPane(apProjeto, "GerenciarProjeto");
    }

    @FXML
    private void tbRecursoActionEvent(Event actionEvent) {
        FxMananger.insertPane(apRecurso, "GerenciarRecurso");
    }

    @FXML
    private void tbCursoActionEvent(Event actionEvent) {
        FxMananger.insertPane(apCurso, "GerenciarCurso");
    }

    @FXML
    private void tbAmbienteActionEvent(Event actionEvent) {
        FxMananger.insertPane(apAmbiente, "GerenciarAmbiente");
    }

    @FXML
    private void tbTurmaActionEvent(Event actionEvent) {
        FxMananger.insertPane(apTurma, "GerenciarTurma");
    }

    @FXML
    private void tbDisciplinaActionEvent(Event actionEvent) {
        FxMananger.insertPane(apDisciplina, "GerenciarDisciplina");
    }

    @FXML
    private void tbInstrutorActionEvent(Event actionEvent) {
        FxMananger.insertPane(apInstrutor, "GerenciarUsuario");
    }

    @FXML
    private void tbConstanteDoSistemaActionEvent(Event actionEvent) {
        FxMananger.insertPane(apConstanteSistema, "ConstanteDoSistema");
    }

    @FXML
    private void tbPermissaoActionEvent(Event actionEvent) {
        FxMananger.insertPane(apPermissao, "GerenciarPermissao");
    }

    private void permissao() {
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
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CONSTANTES);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(7).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(7).setDisable(true);
        }
    }
}
