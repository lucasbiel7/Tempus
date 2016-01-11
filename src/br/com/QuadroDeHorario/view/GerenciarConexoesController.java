/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jidefx.scene.control.field.FormattedTextField;
import jidefx.scene.control.field.verifier.IntegerRangePatternVerifier;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarConexoesController implements Initializable {

    @FXML
    private FormattedTextField mtfIpLocal;
    @FXML
    private FormattedTextField mtfIpRemoto;
    @FXML
    private TextField tfUsuarioLocal;
    @FXML
    private TextField tfUsuarioRemoto;
    @FXML
    private PasswordField pfSenhaLocal;
    @FXML
    private PasswordField pfSenhaRemoto;
    @FXML
    private TextField tfBanco;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mtfIpLocal.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0, 255));
        mtfIpLocal.setPattern("h.h.h.h");
        mtfIpRemoto.getPatternVerifiers().put("h", new IntegerRangePatternVerifier(0, 255));
        mtfIpRemoto.setPattern("h.h.h.h");
        ParametrosBanco.atribuirPropriedades(ParametrosBanco.REMOTO);
        tfUsuarioRemoto.setText(ParametrosBanco.getUSUARIO());
        pfSenhaRemoto.setText(ParametrosBanco.getSENHA());
        mtfIpRemoto.setText(ParametrosBanco.getIP());
        tfBanco.setText(ParametrosBanco.getNOME_BANCO());
        ParametrosBanco.atribuirPropriedades(ParametrosBanco.LOCAL);
        tfUsuarioLocal.setText(ParametrosBanco.getUSUARIO());
        pfSenhaLocal.setText(ParametrosBanco.getSENHA());
        mtfIpLocal.setText(ParametrosBanco.getIP());
    }

    @FXML
    private void btSalvarRemotoActionEvent(ActionEvent actionEvent) {
        File arquivoConf = new File("etc/" + ParametrosBanco.REMOTO + ".txt");
        if (arquivoConf.isFile()) {
            Path path = Paths.get(arquivoConf.getAbsolutePath());
            List<String> dados = new ArrayList<>();
            dados.add(new EncriptacaoAES().encriptar(mtfIpRemoto.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfUsuarioRemoto.getText()));
            dados.add(new EncriptacaoAES().encriptar(pfSenhaRemoto.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfBanco.getText()));
            ParametrosBanco.setUSUARIO(tfUsuarioRemoto.getText());
            ParametrosBanco.setSENHA(pfSenhaRemoto.getText());
            ParametrosBanco.setIP(mtfIpRemoto.getText());
            ParametrosBanco.setNOME_BANCO(tfBanco.getText());
            Connection connection = ParametrosBanco.getConnection();
            if (connection != null) {
                try {
                    Mensagem.showInformation("Configurações alteradas", "O endereço do servidor remoto foi configurado \n"
                            + "com sucesso para o sistema entrar utilizando as novas \n"
                            + "configurações é necessário reiniciar o sistema!");
                    Files.write(path, dados, StandardCharsets.UTF_8);
                } catch (IOException ex) {
                    Logger.getLogger(ConfigurarBancoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.showError("Conexão inválida", "Não foi possivel encontrar uma conexão com esses parametros");
            }
        }
    }

    @FXML
    private void btSalvarLocalActionEvent(ActionEvent actionEvent) {
        File arquivoConf = new File("etc/" + ParametrosBanco.LOCAL + ".txt");
        if (arquivoConf.isFile()) {
            Path path = Paths.get(arquivoConf.getAbsolutePath());
            List<String> dados = new ArrayList<>();
            dados.add(new EncriptacaoAES().encriptar(mtfIpLocal.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfUsuarioLocal.getText()));
            dados.add(new EncriptacaoAES().encriptar(pfSenhaLocal.getText()));
            dados.add(new EncriptacaoAES().encriptar(tfBanco.getText()));
            ParametrosBanco.setUSUARIO(tfUsuarioLocal.getText());
            ParametrosBanco.setSENHA(pfSenhaLocal.getText());
            ParametrosBanco.setIP(mtfIpLocal.getText());
            ParametrosBanco.setNOME_BANCO(tfBanco.getText());
            Connection connection = ParametrosBanco.getConnection();
            if (connection != null) {
                try {
                    Mensagem.showInformation("Configurações alteradas", "O endereço do servidor remoto foi configurado \n"
                            + "com sucesso para o sistema entrar utilizando as novas \n"
                            + "configurações é necessário reiniciar o sistema!");
                    Files.write(path, dados, StandardCharsets.UTF_8);
                } catch (IOException ex) {
                    Logger.getLogger(ConfigurarBancoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Mensagem.showError("Conexão inválida", "Não foi possivel encontrar uma conexão com esses parametros");
            }
        }
    }
}
