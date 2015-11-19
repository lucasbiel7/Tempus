/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaMensal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class CalendarioController implements Initializable {

    private TabelaMensal tmJaneiro;
    private TabelaMensal tmFeveiro;
    private TabelaMensal tmMarco;
    private TabelaMensal tmAbril;
    private TabelaMensal tmMaio;
    private TabelaMensal tmJunho;
    private TabelaMensal tmJulho;
    private TabelaMensal tmAgosto;
    private TabelaMensal tmSetembro;
    private TabelaMensal tmNovembro;
    private TabelaMensal tmOutubro;
    private TabelaMensal tmDezembro;
    @FXML
    private GridPane gpMesses;
    @FXML
    private AnchorPane apContainer;
    private int ano;
    private boolean escolar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apContainer.getUserData() == null) {
                ano = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
            } else {
                Object[] dados = (Object[]) apContainer.getUserData();
                ano = (int) dados[0];
                escolar = (boolean) dados[1];
            }
            new Thread(() -> {
                tmJaneiro = new TabelaMensal(1, ano, escolar);
                tmFeveiro = new TabelaMensal(2, ano, escolar);
                tmMarco = new TabelaMensal(3, ano, escolar);
                tmAbril = new TabelaMensal(4, ano, escolar);
                Platform.runLater(() -> {
                    gpMesses.addRow(0, tmJaneiro, tmFeveiro, tmMarco, tmAbril);
                });
                
            }).start();
            new Thread(() -> {
                tmMaio = new TabelaMensal(5, ano, escolar);
                tmJunho = new TabelaMensal(6, ano, escolar);
                tmJulho = new TabelaMensal(7, ano, escolar);
                tmAgosto = new TabelaMensal(8, ano, escolar);
                Platform.runLater(() -> {
                    gpMesses.addRow(1, tmMaio, tmJunho, tmJulho, tmAgosto);
                });
            }).start();
            new Thread(() -> {
                tmSetembro = new TabelaMensal(9, ano, escolar);
                tmOutubro = new TabelaMensal(10, ano, escolar);
                tmNovembro = new TabelaMensal(11, ano, escolar);
                tmDezembro = new TabelaMensal(12, ano, escolar);
                Platform.runLater(() -> {
                    gpMesses.addRow(2, tmSetembro, tmOutubro, tmNovembro, tmDezembro);
                });
            }).start();

        });
    }
}
