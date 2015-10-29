/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.CalendarioDAO;
import br.com.QuadroDeHorario.dao.VariaveisDoSistemaDAO;
import br.com.QuadroDeHorario.entity.Calendario;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.util.DataHorario;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.util.converter.PercentageStringConverter;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class DetalheInstrutorController implements Initializable {

    @FXML
    private Label lbTitulo;
    @FXML
    private Label lbDetalhe;
    @FXML
    private LineChart<String, Double> lcGrafico;
    @FXML
    private NumberAxis naAulas;

    private Usuario usuario;
    private Date data;
    private ObservableList<XYChart.Series<String, Double>> dadosGrafico = FXCollections.observableArrayList();

    SimpleDateFormat sdfData;
    SimpleDateFormat sdfMes;
    SimpleDateFormat sdfDia;
    SimpleDateFormat sdfNomeDia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            usuario = (Usuario) ((Object[]) lbTitulo.getParent().getUserData())[0];
            data = (Date) ((Object[]) lbTitulo.getParent().getUserData())[1];
            lbTitulo.setText(usuario.getNome());
            lbDetalhe.setText("Aulas por periodo:\n"
                    + DataHorario.Turno.MANHA + ": " + new AulaDAO().pegarPorDiaInstrutor(usuario, data, DataHorario.Turno.MANHA).size() + "\n"
                    + DataHorario.Turno.TARDE + ": " + new AulaDAO().pegarPorDiaInstrutor(usuario, data, DataHorario.Turno.TARDE).size() + "\n"
                    + DataHorario.Turno.NOITE + ": " + new AulaDAO().pegarPorDiaInstrutor(usuario, data, DataHorario.Turno.NOITE).size() + "\n");
            lcGrafico.setTitle("Ocupação referente ao mês de " + sdfMes.format(data));
            lcGrafico.setData(dadosGrafico);
            carregarGrafico();
        });
        new Timeline(new KeyFrame(Duration.seconds(1), (ActionEvent action)->{
            carregarGrafico();
        })).play();
        naAulas.setTickLabelFormatter(new PercentageStringConverter(Locale.ROOT));
    }

    public DetalheInstrutorController() {
        sdfData = new SimpleDateFormat("dd/MM/yyyy");
        sdfMes = new SimpleDateFormat("MMMM");
        sdfDia = new SimpleDateFormat("dd");
        sdfNomeDia = new SimpleDateFormat("EEE");
    }

    private void carregarGrafico() {
        XYChart.Series<String, Double> seriesOcupacao = new XYChart.Series<>();
        XYChart.Series<String, Double> seriesMeta = new XYChart.Series<>();
        XYChart.Series<String, Double> media = new XYChart.Series<>();
        seriesOcupacao.setName("Ocupação diária");
        seriesMeta.setName("Referência de ocupação");
        media.setName("Média");
        if (usuario != null && data != null) {
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                VariaveisDoSistema variaveisDoSistema = new VariaveisDoSistemaDAO().pegarPorNome("ocupacao");
                Date inicio = sdfData.parse("01/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR));
                Date fim = sdfData.parse("01/" + (calendar.get(Calendar.MONTH) + 2) + "/" + calendar.get(Calendar.YEAR));
                Object[] mediaValor = new AulaDAO().mediaMensal(usuario, inicio, fim);
                int dia = (int) mediaValor[0];
                long quantAula = (long) mediaValor[1];
                calendar.setTime(inicio);
                while (calendar.getTime().before(fim)) {
                    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        dia--;
                    } else {
                        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(data);
                        if (!calendarios.isEmpty()) {
                            for (Calendario calendario : calendarios) {
                                if (!calendario.getId().getEvento().isLetivo()) {
                                    dia--;
                                    break;
                                }
                            }
                        }
                    }
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
                calendar.setTime(inicio);
                while (calendar.getTime().before(fim)) {
                    inicio = calendar.getTime();
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    seriesOcupacao.getData().add(new XYChart.Data<>(sdfDia.format(inicio), new AulaDAO().pegarPorDiaInstrutor(usuario, inicio).size() * 1.0 / usuario.getCargaHoraria()));
                    if (variaveisDoSistema != null) {
                        seriesMeta.getData().add(new XYChart.Data<>(sdfDia.format(inicio), Double.parseDouble(variaveisDoSistema.getValor()) / 100));
                    }
                    media.getData().add(new XYChart.Data<>(sdfDia.format(inicio), (double) ((double) quantAula / ((double) dia * (double) usuario.getCargaHoraria()))));

                }
            } catch (ParseException ex) {
                Logger.getLogger(DetalheInstrutorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        dadosGrafico.setAll(seriesOcupacao, seriesMeta, media);
    }
}
