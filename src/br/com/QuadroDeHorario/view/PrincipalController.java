/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.PermissaoGrupoDAO;
import br.com.QuadroDeHorario.control.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Permissao;
import br.com.QuadroDeHorario.model.entity.PermissaoGrupo;
import br.com.QuadroDeHorario.model.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.model.util.Efeito;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.SessaoUsuario;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ScrollPane spPrincipal;
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
    @FXML
    private Label lbLogo;
    @FXML
    private ImageView ivLogo;
    private Stage stage;
    private Timeline logo;
    private int casoLogo = 2;

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
        Efeito.logo(lbLogo, ivLogo);
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
        FxMananger.insertPane(spPrincipal, "AbaInicio");
    }

    @FXML
    private void btAdministrativoActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(spPrincipal, "AreaAdministrativa");
    }

    @FXML
    private void btLixeiraActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(spPrincipal, "Lixeira");
    }

    @FXML
    private void btQuadroDeHorarioActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(spPrincipal, "CarregarQuadroDeHorario",spPrincipal);
    }

    @FXML
    private void btEstatisticaActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(spPrincipal, "Estatisticas",spPrincipal);
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
