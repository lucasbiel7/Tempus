/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.Mail;
import br.com.QuadroDeHorario.control.dao.TokenCodeDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.TokenCode;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import org.hibernate.exception.ConstraintViolationException;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class RecuperarSenhaController implements Initializable {

    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfCodigoSeguranca;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private PasswordField pfConfirmaSenha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void btEnviarCodigoDeSegurancaActionEvent(ActionEvent actionEvent) {
        Usuario usuario = new Usuario();
        usuario.setEmail(tfEmail.getText());
        tfEmail.setDisable(true);
        if (new UsuarioDAO().pegarPorEmail(usuario) == null) {
            Mensagem.showError("E-mail inválido", "Não existe nenhum usuário com este email.");
        } else {
            try {
                Mail mail = new Mail();
                mail.sendEmail(new UsuarioDAO().pegarPorEmail(usuario));
                Mensagem.showInformation("Envio do código de segurança", "Código de segurança foi enviado!");
            } catch (ConstraintViolationException e) {
                Mensagem.showError("Falha ao reenviar e-mail", "Já foi enviado o e-mail espere o tempo de expiração para enviar outro e-mail!");
            } catch (MessagingException | UnsupportedEncodingException ex) {
                Mensagem.showError("Falha de conexão", "Não foi possível enviar o e-mail verifique a conexão da sua internet.");
                System.err.println(ex.getMessage());
            }
        }
        tfEmail.setDisable(false);
    }

    @FXML
    private void btTrocarSenhaActionEvent(ActionEvent actionEvent) {
        Usuario usuario = new Usuario();
        usuario.setLogin(tfLogin.getText());
        usuario = new UsuarioDAO().pegarPorLogin(usuario);
        TokenCode tokenCode = new TokenCode();
        tokenCode.setToken(tfCodigoSeguranca.getText());
        tokenCode.setUsuario(usuario);
        tokenCode = new TokenCodeDAO().pegarToken(tokenCode);
        if (usuario == null || tokenCode == null) {
            Mensagem.showError("Dados não encontrado", "Login ou código de segurança estão incorretos!");
        } else if (pfSenha.getText().equals(pfConfirmaSenha.getText())) {
            usuario.setSenha(pfSenha.getText());
            if (pfSenha.getText().matches(".*([a-z]{1,}).*") && pfSenha.getText().matches(".*([A-Z]{1,}).*") && pfSenha.getText().matches(".*([0-9]{1,}).*") && pfSenha.getText().matches(".*(\\W{1,}).*")) {
                tokenCode.setUsuario(usuario);
                new TokenCodeDAO().excluir(tokenCode);
                new UsuarioDAO().editar(usuario);
                Mensagem.showInformation("Recuperar conta", "A conta foi recuperada com sucesso!");
                btCancelarActionEvent(actionEvent);
            } else {
                Mensagem.showError("Senha está no formato incorreto!", "A senha deve conter uma letra minuscula,maiuscula,número e um carácter especial.");
            }
        } else {
            Mensagem.showError("As senhas não coincidem", "As senhas devem ser indênticas!");
            pfSenha.setText("");
            pfConfirmaSenha.setText("");
            pfSenha.requestFocus();
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) tfEmail.getScene().getWindow()).close();
    }
}
