/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.Mensagem;
import br.com.QuadroDeHorario.util.ParametrosBanco;
import br.com.QuadroDeHorario.util.SessaoUsuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LoginController implements Initializable {

    @FXML
    private Label lbLogo;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfSenha;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) lbLogo.getScene().getWindow();
        });
        lbLogo.setText(FxMananger.NOME_PROGRAMA);
    }

    @FXML
    private void btLoginActionEvent(ActionEvent actionEvent) {
        Usuario usuario = new Usuario();
        usuario.setLogin(tfLogin.getText());
        usuario.setSenha(pfSenha.getText());
        usuario = new UsuarioDAO().login(usuario);
        if (usuario == null) {
            Mensagem.showError("Falha de Autenticação", "Usuário ou senha incorretos!");
        } else {
            SessaoUsuario.setUsuario(usuario);
            usuario.setLogado(true);
            usuario.setSistemaLogado(FxMananger.NOME_PROGRAMA + " - " + ParametrosBanco.VERSAO);
            new UsuarioDAO().editar(usuario);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (SessaoUsuario.getUsuario() != null) {
                    SessaoUsuario.getUsuario().setLogado(false);
                    SessaoUsuario.getUsuario().setSistemaLogado(FxMananger.NOME_PROGRAMA + " - " + ParametrosBanco.VERSAO);
                    new UsuarioDAO().editar(SessaoUsuario.getUsuario());
                }
            }));
            stage.close();
            FxMananger.show("Principal", "Principal", false, true, true);
        }
    }

    @FXML
    private void btVisualizarHorarioActionEvent(ActionEvent actionEvent) {
        stage.close();
        FxMananger.show("Inicio", "Início", false, false, true);
    }

    @FXML
    private void btRecuperarSenhaActionEvent(ActionEvent actionEvent) {
        FxMananger.show("RecuperarSenha", "Recuperar a senha", true, false);
    }
}
