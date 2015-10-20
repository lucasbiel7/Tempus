package br.com.QuadroDeHorario.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import br.com.QuadroDeHorario.control.EncriptacaoAES;
import br.com.QuadroDeHorario.util.Mensagem;
import br.com.QuadroDeHorario.util.ParametrosBanco;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

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
    private TextField tfIp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfUsuario.setText(ParametrosBanco.getUSUARIO());
        pfSenha.setText(ParametrosBanco.getSENHA());
        tfIp.setText(ParametrosBanco.getIP());

    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        File arquivoConf = new File("etc/banco.txt");
        if (arquivoConf.isFile()) {
            Path path = Paths.get(arquivoConf.getAbsolutePath());
            List<String> dados = new ArrayList<>();
            dados.add(new EncriptacaoAES().encriptar(tfIp.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfUsuario.getText()));
            dados.add(new EncriptacaoAES().encriptar(pfSenha.getText()));
            ParametrosBanco.setUSUARIO(tfUsuario.getText());
            ParametrosBanco.setSENHA(pfSenha.getText());
            ParametrosBanco.setIP(tfIp.getText());
            Connection connection = ParametrosBanco.getConnection();
            if (connection != null) {
                try {
                    Mensagem.showInformation("Configurações alteradas", "O sistema será reiniciado para aplicar as \n"
                            + "alterações de configurações no banco de dados!");
                    Files.write(path, dados, StandardCharsets.UTF_8);
                    Thread.sleep(5000);
                    btCancelarActionEvent(actionEvent);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(ConfigurarBancoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.showError("Conexão inválida", "Não foi possivel encontrar uma conexão com esses parametros");
            }
        }
    }

    @FXML
    private void btCancelarActionEvent(ActionEvent actionEvent) {
        ((Stage) tfUsuario.getScene().getWindow()).close();
    }

}
