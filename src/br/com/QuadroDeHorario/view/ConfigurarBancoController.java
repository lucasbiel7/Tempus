package br.com.QuadroDeHorario.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import br.com.QuadroDeHorario.control.EncriptacaoAES;
import br.com.QuadroDeHorario.model.util.Mensagem;
import br.com.QuadroDeHorario.model.util.ParametrosBanco;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ConfigurarBancoController implements Initializable {

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private FormattedTextField ftfIp;
    @FXML
    private TextField tfBanco;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tfUsuario.setText(ParametrosBanco.getUSUARIO());
            pfSenha.setText(ParametrosBanco.getSENHA());
            ftfIp.setText(ParametrosBanco.getIP());
            tfBanco.setText(ParametrosBanco.getNOME_BANCO());
        } catch (NoClassDefFoundError e) {
            Mensagem.showError("Arquivo de configuração",
                    "O arquivo de configuração parece estar corrompido,\n"
                    + "será necessário excluir o arquivo e reiniciar o sistema.");
            File arquivoConf = new File("etc/banco.txt");
            if (arquivoConf.delete()) {
                Platform.runLater(() -> {
                    ((Stage) tfUsuario.getScene().getWindow()).close();
                });
            }
        }
        ftfIp.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0, 255));
        ftfIp.setPattern("h.h.h.h");
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        File arquivoConf = new File("etc/banco.txt");
        if (arquivoConf.isFile()) {
            Path path = Paths.get(arquivoConf.getAbsolutePath());
            List<String> dados = new ArrayList<>();
            dados.add(new EncriptacaoAES().encriptar(ftfIp.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfUsuario.getText()));
            dados.add(new EncriptacaoAES().encriptar(pfSenha.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfBanco.getText()));
            ParametrosBanco.setUSUARIO(tfUsuario.getText());
            ParametrosBanco.setSENHA(pfSenha.getText());
            ParametrosBanco.setIP(ftfIp.getText());
            ParametrosBanco.setNOME_BANCO(tfBanco.getText());
            Connection connection = ParametrosBanco.getConnection();
            if (connection != null) {
                try {
                    Mensagem.showInformation("Configurações alteradas", "O sistema será reiniciado para aplicar as \n"
                            + "alterações de configurações no banco de dados!");
                    Files.write(path, dados, StandardCharsets.UTF_8);
                    new Timeline(new KeyFrame(Duration.seconds(3), (ActionEvent e) -> {
                        btCancelarActionEvent(actionEvent);
                    })).playFromStart();
                } catch (IOException ex) {
                    Logger.getLogger(ConfigurarBancoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.showError("Conexão inválida", "Não foi possivel encontrar uma conexão com esses parametros");
            }
        }
        try {
            Runtime.getRuntime().exec("java -jar QuadroDeHorarioFX.jar");
            Platform.exit();
            System.exit(0);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) tfUsuario.getScene().getWindow()).close();
    }

}
