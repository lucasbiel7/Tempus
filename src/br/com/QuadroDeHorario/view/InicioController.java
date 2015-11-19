/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AtualizacaoDAO;
import br.com.QuadroDeHorario.dao.SistemaDAO;
import br.com.QuadroDeHorario.entity.Atualizacao;
import br.com.QuadroDeHorario.util.Efeito;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.ParametrosBanco;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class InicioController implements Initializable {

    @FXML
    private Label lbLogo;
    @FXML
    private ImageView ivLogo;
    @FXML
    private Label lbAtualizacao;
    @FXML
    private Label lbVersao;
    @FXML
    private Button btLogin;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) lbLogo.getScene().getWindow();
            if (stage != null) {
                stage.getScene().setOnKeyReleased((KeyEvent event) -> {
                    if (event.getCode().equals(KeyCode.INSERT)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        PasswordField passwordField = new PasswordField();
                        alert.setHeaderText("Autenticação");
                        alert.setTitle("Autenticação");
                        alert.setContentText("Cuidado!\n"
                                + "Área restrita do programador, ao digitar a senha incorreta\n"
                                + "pode ser fatal para o sistema " + FxMananger.NOME_PROGRAMA + "."
                                + "Caso você não seja desenvolvedor desse sistema feche essa \n"
                                + "tela caso contrário tente a sorte: Nothing else matters(8)");
                        alert.getDialogPane().setGraphic(passwordField);
                        Optional<ButtonType> button = alert.showAndWait();
                        if (passwordField.getText().equals("lucas123")) {
                            FxMananger.show("CadastrarAtualizacao", "Inserir atualização de sistema", true, false);
                        }
                    }
                });
            }
        });
        Efeito.logo(lbLogo, ivLogo);
        lbVersao.setText(FxMananger.NOME_PROGRAMA + " Versão: " + ParametrosBanco.VERSAO);
        Atualizacao atualizacao = new AtualizacaoDAO().pegarPorSistema(new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA));
       btLogin.setDisable(!FxMananger.ONLINE);
        if (atualizacao != null) {
            lbAtualizacao.setText("Existe uma atualização para ser baixada: " + atualizacao.getNome());
            lbAtualizacao.setOnMouseReleased((MouseEvent event) -> {
                if (atualizacao.getUrl().isEmpty()) {
                    try {
                        Desktop.getDesktop().browse(new URI(atualizacao.getUrl()));
                    } catch (IOException | URISyntaxException ex) {

                    }
                }
            });
        }
        lbLogo.setText(FxMananger.NOME_PROGRAMA);
    }

    @FXML
    private void btLoginActionEvent(ActionEvent actionEvent) {
        stage.close();
        FxMananger.show("Login", "Autenticação", false, false, true);
    }

    @FXML
    private void btVisualizarHorarioActionEvent(ActionEvent actionEvent) {
        stage.close();
        FxMananger.show("VisualizarHorario", "Visualizar Horário", false, true, true);
    }

}
