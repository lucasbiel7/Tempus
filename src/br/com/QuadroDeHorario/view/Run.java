/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.SessaoUsuario;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author OCTI01
 */
public class Run extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Código para criar o instalador
//        try {
//            primaryStage.setResizable(false);
//            primaryStage.setTitle(FxMananger.NOME_PROGRAMA + " - Loading");
//            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("LoadScreen.fxml")));
//            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("image/icone.png")));
//            primaryStage.setScene(scene);
//            primaryStage.initStyle(StageStyle.UNDECORATED);
//            primaryStage.show();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //Modo desenvolvedor
        Usuario usuario = new Usuario();
        usuario.setLogin("ldutra");
        usuario.setSenha("Lucas5@");
        SessaoUsuario.setUsuario(new UsuarioDAO().login(usuario));
        FxMananger.show("Principal", "Principal", false, true, true);
        //Teste Visualizar Horário/Interface para usuário final
//        FxMananger.show("VisualizarHorario", "Visualizar Horário", false, true, true);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
