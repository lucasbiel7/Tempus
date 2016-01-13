/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import br.com.QuadroDeHorario.control.dao.VariaveisDoSistemaDAO;
import br.com.QuadroDeHorario.model.entity.VariaveisDoSistema;
import java.io.ByteArrayInputStream;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author OCTI01
 */
public class Efeito {

    public static int imagem;
    public static Timeline logo;
    public static final int MAX_LOGOS = 3;
    public static VariaveisDoSistema nomeDoPrograma;
    public static final Image tempus;
    public static final Image fiemg;
    public static final Image program;

    static {
        tempus = new Image(GerenciarImagem.carregarImagem("logo.png"));
        fiemg = new Image(GerenciarImagem.carregarImagem("fiemg.png"));
        nomeDoPrograma = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.ESCOLA);
        if (nomeDoPrograma != null) {
            if (nomeDoPrograma.getFoto() != null) {
                program = new Image(new ByteArrayInputStream(nomeDoPrograma.getFoto()));
            } else {
                program = null;
            }
        } else {
            program = null;
        }
    }

    public static void logo(Label label, ImageView imageView) {
        if (logo != null) {
            logo.stop();
        }
        imagem = 1;
        label.setText(FxMananger.NOME_PROGRAMA);
        imageView.setImage(tempus);
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
            imageView.setFitWidth(61);
            switch (imagem) {
                case 1:
                    label.setText(FxMananger.NOME_PROGRAMA);
                    imageView.setImage(tempus);
                    break;
                case 2:
                    label.setText("");
                    imageView.setFitWidth(200);
                    imageView.setImage(fiemg);
                    break;
                case 3:
                    if (nomeDoPrograma != null) {
                        label.setText(nomeDoPrograma.getValor().replace("\\n", "\n"));
                        imageView.setImage(program);
                    } else {
                        label.setText("SENAI Belo Horizonte \nCETEL César Rodrigues");
                        imageView.setImage(new Image(GerenciarImagem.carregarImagem("tori.png")));
                    }
                    break;
            }
            fadeTransition.play();
        }));
        logo.setCycleCount(Timeline.INDEFINITE);
        logo.playFromStart();
    }

    public static Color brancoOuPreto(Color color) {
        String hexa = Integer.toHexString((int) (color.getRed() * 255)) + Integer.toHexString((int) (color.getGreen() * 255)) + Integer.toHexString((int) (color.getBlue() * 255));
        int value = Integer.valueOf(hexa, 16);
        int valorMaximo = Integer.valueOf("FFFFFF", 16);
        if (valorMaximo - value > value) {
            return Color.rgb(255, 255, 255);
        } else {
            return Color.rgb(0, 0, 0);
        }
    }
}
