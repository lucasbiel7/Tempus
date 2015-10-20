/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.SistemaDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.Mensagem;
import br.com.QuadroDeHorario.util.ParametrosBanco;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

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

                    new UsuarioDAO().pegarTodos();
                    new ParametrosBanco().atualizacao();
                    Platform.runLater(() -> {
                        stage.close();
                        FxMananger.show("Inicio", "Início", true, false, true);
                    });
                } catch (ExceptionInInitializerError e) {
                    Platform.runLater(() -> {
                        stage.close();
                        if (Mensagem.showConfirmation("Erro de conexão", "Talvez as configurações de conexão estão incorretas, deseja fazer alteração nas configurações de conexão do sistema com a base de dados?")) {
                            try {
                                FxMananger.show("ConfigurarBanco", "Cofigurar Banco de dados", false, false);
                                Runtime.getRuntime().exec("java -jar " + new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA).getNomeJar());
                                Platform.exit();
                                System.exit(0);
                            } catch (IOException ex) {
                                
                            }
                        } else {
                            System.exit(1);
                        }
                    });

                }
                return null;
            }
        };
    }

}
