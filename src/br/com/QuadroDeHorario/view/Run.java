/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.SingleInstance;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author OCTI01
 */
public class Run extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Thread.sleep(1000);
            new SingleInstance();
            //Teste interessante
//        String teste = "bssid 10:a5:44:66";
//        Matcher m=Pattern.compile("\\w{2}:\\w{2}:\\w{2}:\\w{2}").matcher(teste);
//        while(m.find()){
//            System.out.println(m.group(0));
//        }
//        teste.substring(teste.ind)
//Código para criar o instalador
            primaryStage.setResizable(false);
            primaryStage.setTitle(FxMananger.NOME_PROGRAMA + " - Loading");
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("LoadScreen.fxml")));
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("image/icone.png")));
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
            //Modo desenvolvedor
            //        Teste Visualizar Horário/Interface para usuário final
//        FxMananger.show("VisualizarHorario", "Visualizar Horário", false, true, true);
//            Usuario usuario = new Usuario();
//            usuario.setLogin("ldutra");
//            usuario.setSenha("Lucas5@");
//            SessaoUsuario.setUsuario(new UsuarioDAO().login(usuario));
//            FxMananger.show("Principal", "Principal", false, true, true);
        } catch (IOException ex) {
            if (Mensagem.showConfirmation("Reiniciar sistema", "Parece que já existe uma instancia do sistema aberta!\n"
                    + "Não é possível abrir duas instancias por motivos de desempenho!"
                    + "Deseja reiniciar o sistema e tentar novamente?")) {
                try {
                    Runtime.getRuntime().exec("javaw -jar QuadroDeHorarioFX.jar");
                } catch (IOException ex1) {
                    Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Platform.exit();
                System.exit(0);
            }
            System.exit(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
