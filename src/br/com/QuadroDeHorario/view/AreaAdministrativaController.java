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
public class AreaAdministrativaController implements Initializable {

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
    private ScrollPane spInstrutor;
    @FXML
    private ScrollPane spPermissao;
    @FXML
    private ScrollPane spConstanteSistema;
    @FXML
    private ScrollPane spConexao;
    @FXML
    private ScrollPane spProduto;
    @FXML
    private TabPane tbAbas;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            tbProjetoActionEvent(null);
            if (tbAbas.getScene() != null) {
                stage = (Stage) tbAbas.getScene().getWindow();
            }
        });
        permissao();
    }

    @FXML
    private void tbProdutoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spProduto, "GerenciarTipoCurso");
    }

    @FXML
    private void tbProjetoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spProjeto, "GerenciarProjeto");
    }

    @FXML
    private void tbRecursoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spRecurso, "GerenciarRecurso");
    }

    @FXML
    private void tbCursoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spCurso, "GerenciarCurso");
    }

    @FXML
    private void tbAmbienteActionEvent(Event actionEvent) {
        FxMananger.insertPane(spAmbiente, "GerenciarAmbiente");
    }

    @FXML
    private void tbTurmaActionEvent(Event actionEvent) {
        FxMananger.insertPane(spTurma, "GerenciarTurma");
    }

    @FXML
    private void tbDisciplinaActionEvent(Event actionEvent) {
        FxMananger.insertPane(spDisciplina, "GerenciarDisciplina");
    }

    @FXML
    private void tbInstrutorActionEvent(Event actionEvent) {
        FxMananger.insertPane(spInstrutor, "GerenciarUsuario");
    }

    @FXML
    private void tbConstanteDoSistemaActionEvent(Event actionEvent) {
        FxMananger.insertPane(spConstanteSistema, "ConstanteDoSistema");
    }

    @FXML
    private void tbPermissaoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spPermissao, "GerenciarPermissao");
    }

    @FXML
    private void tbConexaoActionEvent(Event actionEvent) {
        FxMananger.insertPane(spConexao, "GerenciarConexoes");
    }

    private void permissao() {
        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.PROJETO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(0).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(0).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.PRODUTO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(1).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(1).setDisable(true);
        }

        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CURSO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(2).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(2).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CURSO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(2).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(2).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.RECURSO);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(3).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(3).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.AMBIENTE);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(4).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(4).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.TURMA);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(5).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(5).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.DISCIPLINA);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(6).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(6).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.INSTRUTOR);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(7).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(7).setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CONSTANTES);
        if (permissaoUsuario != null) {
            tbAbas.getTabs().get(8).setDisable(!permissaoUsuario.isHabilitado());
        } else {
            tbAbas.getTabs().get(8).setDisable(true);
        }
        if (SessaoUsuario.getUsuario().getGrupo() != null) {
            //Permitir supervisao e competidor a utilizar a aba permissao
//            tbAbas.getTabs().get(9).setDisable(!(SessaoUsuario.getUsuario().getGrupo().getDescricao().equalsIgnoreCase(SessaoUsuario.PERMISSAO.COORDERNACAO.toString()) || SessaoUsuario.getUsuario().getGrupo().getDescricao().equalsIgnoreCase(SessaoUsuario.PERMISSAO.COMPETIDOR.toString())));
            tbAbas.getTabs().get(9).setDisable(!SessaoUsuario.getUsuario().getGrupo().getDescricao().equalsIgnoreCase(SessaoUsuario.PERMISSAO.COORDERNACAO.toString()));
        } else {
            tbAbas.getTabs().get(9).setDisable(false);
        }
    }
}
