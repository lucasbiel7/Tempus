/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Grupo;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import br.com.QuadroDeHorario.model.util.ParametrosBanco;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                        if (new UsuarioDAO().pegarTodos().isEmpty()) {
                            Usuario usuario = new Usuario();
//                            usuario.setGrupo();
                            usuario.setLogin("admin");
                            if (new GrupoDAO().pegarTodos().isEmpty()) {
                                Grupo grupo = new Grupo();
                                grupo.setDescricao("Coordenação");
                                new GrupoDAO().cadastrar(grupo);
                                grupo = new Grupo();
                                grupo.setDescricao("Instrutor");
                                new GrupoDAO().cadastrar(grupo);
                            }
                            usuario.setGrupo(new GrupoDAO().pegarGrupo("Coordenação"));
                            usuario.setSenha("admin");
                            new UsuarioDAO().cadastrar(usuario);
                        }
                        Platform.runLater(() -> {
                            if (new ParametrosBanco().atualizacao()) {
                                try {
                                    Runtime.getRuntime().exec("javaw -jar QuadroDeHorarioFX.jar");
                                    Platform.exit();
                                    System.exit(0);
                                } catch (IOException ex) {
                                    Logger.getLogger(LoadScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                stage.close();
                                FxMananger.show("Inicio", "Início", true, false, true);
                            }
                        });
                    } else {
                        ParametrosBanco.atribuirPropriedades(ParametrosBanco.LOCAL);
                        connection = ParametrosBanco.getConnection();
                        if (connection != null) {
                            connection.close();
                            FxMananger.NOME_PROGRAMA += " Offline";
                            FxMananger.ONLINE = false;
                            connection.close();
                            new UsuarioDAO().pegarTodos();
                           if (new ParametrosBanco().atualizacao()) {
                                try {
                                    Runtime.getRuntime().exec("javaw -jar QuadroDeHorarioFX.jar");
                                    Platform.exit();
                                    System.exit(0);
                                } catch (IOException ex) {
                                    Logger.getLogger(LoadScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                stage.close();
                                FxMananger.show("Inicio", "Início", true, false, true);
                            }
                        } else {
                            ParametrosBanco.atribuirPropriedades(ParametrosBanco.REMOTO);
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
                FxMananger.show("ConfigurarBanco", "Cofigurar Banco de dados", true, false);
            } else {
                System.exit(1);
            }
        });
    }
}
