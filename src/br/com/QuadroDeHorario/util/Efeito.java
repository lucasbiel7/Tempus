/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author OCTI01
 */
public class Efeito {

    public static int imagem;
    public static Timeline logo;
    public static final int MAX_LOGOS = 3;

    public static void logo(Label label, ImageView imageView) {
        imagem = 1;
        label.setText(FxMananger.NOME_PROGRAMA);
        imageView.setImage(new Image(GerenciarImagem.carregarImagem("logo.png")));
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3d), label);
        fadeTransition.setFromValue(0d);
        fadeTransition.setToValue(1d);
        fadeTransition.setOnFinished((ActionEvent event) -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(2d), label);
            fadeTransition1.setFromValue(1d);
            fadeTransition1.setToValue(0d);
            fadeTransition1.play();
        });
        fadeTransition.play();
        logo = new Timeline(new KeyFrame(Duration.seconds(5), (ActionEvent actionEvent) -> {
            imagem++;
            if (imagem > MAX_LOGOS) {
                imagem = 1;
            }
            FadeTransition nFadeTransition = new FadeTransition(Duration.seconds(3d), label);
            nFadeTransition.setFromValue(0.0);
            nFadeTransition.setToValue(1.0);
            nFadeTransition.setOnFinished((ActionEvent event) -> {
                FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(2), label);
                fadeTransition1.setFromValue(1.0);
                fadeTransition1.setToValue(0.0);
                fadeTransition1.play();
            });
            imageView.setFitWidth(61);
            switch (imagem) {
                case 1:
                    label.setText(FxMananger.NOME_PROGRAMA);
                    imageView.setImage(new Image(GerenciarImagem.carregarImagem("logo.png")));
                    break;
                case 2:
                    label.setText("");
                    imageView.setFitWidth(200);
                    imageView.setImage(new Image(GerenciarImagem.carregarImagem("fiemg.png")));
                    break;
                case 3:
                    label.setText("SENAI Belo Horizonte CETEL César Rodrigues");
                    imageView.setImage(new Image(GerenciarImagem.carregarImagem("tori.png")));
                    break;
            }
            nFadeTransition.play();
        }));
        logo.setCycleCount(Timeline.INDEFINITE);
        logo.playFrom(Duration.ZERO);
    }
}
