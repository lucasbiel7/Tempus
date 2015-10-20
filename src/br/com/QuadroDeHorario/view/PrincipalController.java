/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.PermissaoGrupoDAO;
import br.com.QuadroDeHorario.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Permissao;
import br.com.QuadroDeHorario.entity.PermissaoGrupo;
import br.com.QuadroDeHorario.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.SessaoUsuario;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PrincipalController implements Initializable {

    @FXML
    private Label lbNome;
    @FXML
    private Circle cProfile;
    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private Button btInicio;
    @FXML
    private Button btAreaAdministrativa;
    @FXML
    private Button btQuadroDeHorario;
    @FXML
    private Button btEstatisticas;
    @FXML
    private Button btLixeira;
    @FXML
    private Button btAtivarPermissoes;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) lbNome.getScene().getWindow();
            lbNome.setText(SessaoUsuario.getUsuario().getNome());
            if (SessaoUsuario.getUsuario().getFoto() != null) {
                cProfile.setFill(new ImagePattern(new Image(new ByteArrayInputStream(SessaoUsuario.getUsuario().getFoto()))));
            } else {
                cProfile.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/br/com/QuadroDeHorario/view/image/perfil.png"))));
            }
        });
        permissoes();
    }

    @FXML
    private void btSairActionEvent(ActionEvent actionEvent) {
        if (SessaoUsuario.getUsuario() != null) {
            SessaoUsuario.getUsuario().setLogado(false);
            new UsuarioDAO().editar(SessaoUsuario.getUsuario());
        }
        stage.close();
        FxMananger.show("Login", "Login", false, false, true);
    }

    @FXML
    private void btInicioActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "AbaInicio");
    }

    @FXML
    private void btAdministrativoActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "AreaAdministrativa");
    }

    @FXML
    private void btLixeiraActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "Lixeira");
    }

    @FXML
    private void btQuadroDeHorarioActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "CarregarQuadroDeHorario");
    }

    @FXML
    private void btEstatisticaActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "Estatisticas");
    }

    @FXML
    private void btAtivarPermissoesActionEvent(ActionEvent actionEvent) {
        for (Permissao permissao : Permissao.values()) {
            PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarPorId(new PermissaoUsuario.PermissaoUsuarioID(SessaoUsuario.getUsuario(), permissao));
            if (permissaoUsuario == null) {
                permissaoUsuario = new PermissaoUsuario();
                PermissaoGrupo permissaoGrupo = new PermissaoGrupoDAO().pegarPorId(new PermissaoGrupo.PermissaoGrupoID(SessaoUsuario.getUsuario().getGrupo(), permissao));
                if (permissaoGrupo != null) {
                    permissaoUsuario.setHabilitado(permissaoGrupo.isHabilitado());
                } else {
                    permissaoUsuario.setHabilitado(true);
                }
                permissaoUsuario.setId(new PermissaoUsuario.PermissaoUsuarioID(SessaoUsuario.getUsuario(), permissao));
                new PermissaoUsuarioDAO().cadastrar(permissaoUsuario);
            }
        }
        permissoes();
    }

    private void permissoes() {
        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.INICIO);
        if (permissaoUsuario != null) {
            btInicio.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btInicio.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.AREA_ADMINISTRATIVA);
        btAtivarPermissoes.setVisible(permissaoUsuario == null);
        if (permissaoUsuario != null) {
            btAreaAdministrativa.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btAreaAdministrativa.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.QUADRO_HORARIO);
        if (permissaoUsuario != null) {
            btQuadroDeHorario.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btQuadroDeHorario.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.ESTATISTICA);
        if (permissaoUsuario != null) {
            btEstatisticas.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btEstatisticas.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.LIXEIRA);
        if (permissaoUsuario != null) {
            btLixeira.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btLixeira.setDisable(true);
        }
    }

}
