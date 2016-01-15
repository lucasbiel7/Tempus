/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author OCTI01
 */
public class Mensagem {

    public static void showInformation(String titulo, String mensagem) {
        Notifications notifications = Notifications.create();
        notifications.title(titulo);
        notifications.text(mensagem);
        notifications.showInformation();
    }

    public static void showError(String titulo, String mensagem) {
        Notifications notifications = Notifications.create();
        notifications.title(titulo);
        notifications.text(mensagem);
        notifications.hideAfter(Duration.seconds(10d));
        notifications.showError();
    }

    public static boolean showConfirmation(String titulo, String menssagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, menssagem, ButtonType.YES, ButtonType.NO);
        alert.setTitle("Deseja Continuar?");
        ((Stage) (alert.getDialogPane().getScene().getWindow())).getIcons().add(FxMananger.image);
        alert.setHeaderText(titulo);
        alert.setContentText(menssagem);
        Optional<ButtonType> botoes = alert.showAndWait();
        return botoes.get().equals(ButtonType.YES);
    }

}
