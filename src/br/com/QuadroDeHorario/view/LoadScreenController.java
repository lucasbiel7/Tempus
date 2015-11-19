/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.GenericaDAO;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.ManipularData;
import br.com.QuadroDeHorario.util.Mensagem;
import br.com.QuadroDeHorario.util.ParametrosBanco;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import org.hibernate.HibernateException;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class LoadScreenController implements Initializable {

    @FXML
    private ProgressIndicator piLoader;
    private Stage stage;
    private Task<Void> task;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) piLoader.getScene().getWindow();
            new Thread(task).start();

        });
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Connection connection = ParametrosBanco.getConnection();
                    if (connection != null) {
                        connection.close();
                        FxMananger.ONLINE = true;
                        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                            try {
                                File file = new File("backup/");
                                file.mkdirs();
                                Runtime.getRuntime().exec("mysqldump -u" + ParametrosBanco.getUSUARIO() + " -p" + ParametrosBanco.getSENHA() + " -h" + ParametrosBanco.getIP() + " --database sisCetel > backup/sisCetel-" + (ManipularData.toDate(new GenericaDAO<>().dataAtual()).replace("/", "-")) + ".sql");
                            } catch (IOException ex) {
                                System.out.println("Erro ao tentar Realizar backup!");
                            }
                        }));
                        new UsuarioDAO().pegarTodos();
                        new ParametrosBanco().atualizacao();
                        Platform.runLater(() -> {
                            stage.close();
                            FxMananger.show("Inicio", "Início", true, false, true);
                        });
                    } else {
                        ParametrosBanco.atribuirPropriedades(ParametrosBanco.LOCAL);
                        connection = ParametrosBanco.getConnection();
                        if (connection != null) {
                            FxMananger.NOME_PROGRAMA += " Offline";
                            FxMananger.ONLINE = false;
                            connection.close();
                            new UsuarioDAO().pegarTodos();
                            new ParametrosBanco().atualizacao();
                            Platform.runLater(() -> {
                                stage.close();
                                FxMananger.show("Inicio", "Início", true, false, true);
                            });
                            connection.close();
                        } else {
                            erroIniciar();
                        }
                    }
                } catch (HibernateException | ExceptionInInitializerError e) {
                    erroIniciar();
                }
                return null;
            }
        };
    }

    public void erroIniciar() {
        Platform.runLater(() -> {
            stage.close();
            if (Mensagem.showConfirmation("Erro de conexão", "Talvez as configurações de conexão estão incorretas, deseja fazer alteração nas configurações de conexão do sistema com a base de dados?")) {
                try {
                    FxMananger.show("ConfigurarBanco", "Cofigurar Banco de dados", true, false);
                    Runtime.getRuntime().exec("javaw -jar QuadroDeHorarioFX.jar");
                    Platform.exit();
                    System.exit(0);
                } catch (IOException ex) {
                    Mensagem.showError("Falha ao iniciar", "Não foi possível iniciar o sistema,\n"
                            + "foi encontrado erros em arquivos de configuração!");
                }
            } else {
                System.exit(1);
            }
        });
    }
}
