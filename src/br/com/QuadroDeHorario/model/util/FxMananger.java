/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import br.com.QuadroDeHorario.view.Run;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author OCTI01
 */
public class FxMananger {

    public static Image image;
    public static String NOME_PROGRAMA = "Tempus";
    public static boolean ONLINE;
    public static final String VIEW="/br/com/QuadroDeHorario/view/";
    static {
        image = new Image(FxMananger.class.getResourceAsStream("/br/com/QuadroDeHorario/view/image/icone.png"));
    }

    public static void show(String arquivo, String titulo, boolean dialogo, boolean maximizado) {
        try {
            Scene scene = new Scene(loadFXML(arquivo));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo + " - " + NOME_PROGRAMA);
            stage.setMaximized(maximizado);
            stage.getIcons().add(image);
            if (dialogo) {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            System.err.println("Error na classe FXMananger\n"
                    + "" + ex.getMessage());
        }
    }

    public static String toRGB(Color color) {
        return "rgb(" + (int) (color.getRed() * 255) + "," + (int) (color.getGreen() * 255) + "," + (int) (color.getBlue() * 255) + ")";
    }

    public static void show(String arquivo, String titulo, boolean dialogo, boolean maximizado, Object object) {
        try {
            Parent parent = loadFXML(arquivo);
            parent.setUserData(object);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo + " - " + NOME_PROGRAMA);
            stage.setMaximized(maximizado);
            stage.getIcons().add(image);
            if (dialogo) {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            System.err.println("Error na classe FXMananger\n"
                    + "" + ex.getMessage());
        }
    }

    public static void show(String arquivo, String titulo, boolean dialogo, boolean maximizado, boolean sair) {
        try {
            Scene scene = new Scene(loadFXML(arquivo));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(titulo + " - " + NOME_PROGRAMA);
            stage.setMaximized(maximizado);
            stage.getIcons().add(image);
            if (sair) {
                stage.setOnCloseRequest((WindowEvent event) -> {
                    Platform.exit();
                    System.exit(0);
                });
            }
            if (dialogo) {
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } else {
                stage.show();
            }
        } catch (IOException ex) {
            System.err.println("Error na classe FXMananger\n"
                    + "" + ex.getMessage());
        }
    }

    public static Parent loadFXML(String arquivo) throws IOException {
        return FXMLLoader.load(Run.class.getResource(arquivo + ".fxml"));
    }

    public static void insertPane(ScrollPane parente, String arquivo) {
        parente.setFitToHeight(true);
        parente.setFitToWidth(true);
        try {
            Parent child = loadFXML(arquivo);
            AnchorPane.setBottomAnchor(child, 0d);
            AnchorPane.setTopAnchor(child, 0d);
            AnchorPane.setLeftAnchor(child, 0d);
            AnchorPane.setRightAnchor(child, 0d);
            parente.setContent(child);
        } catch (IOException ex) {
            Logger.getLogger(FxMananger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insertPane(ScrollPane parente, String arquivo, Object object) {
        parente.setFitToHeight(true);
        parente.setFitToWidth(true);
        try {
            Parent child = loadFXML(arquivo);
            AnchorPane.setBottomAnchor(child, 0d);
            AnchorPane.setTopAnchor(child, 0d);
            AnchorPane.setLeftAnchor(child, 0d);
            AnchorPane.setRightAnchor(child, 0d);
            child.setUserData(object);
            parente.setContent(child);
        } catch (IOException ex) {
            Logger.getLogger(FxMananger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
