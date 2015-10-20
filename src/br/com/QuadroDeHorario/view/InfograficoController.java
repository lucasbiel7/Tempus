/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AulaDAO;
import br.com.QuadroDeHorario.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.dao.GrupoDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.entity.Usuario;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.converter.PercentageStringConverter;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class InfograficoController implements Initializable {

    @FXML
    private BarChart<String, Double> barChart;
    private Date data = new Date();
    private ObservableList<XYChart.Series<String, Double>> dados = FXCollections.observableArrayList();
    @FXML
    private NumberAxis naNumeros;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        barChart.setData(dados);
        Platform.runLater(() -> {
            data = (Date) barChart.getParent().getUserData();
            if (dados.isEmpty()) {
                carregarDados();
            }
        });
        naNumeros.setUpperBound(100);
        naNumeros.setTickLabelFormatter(new PercentageStringConverter(Locale.ROOT));
    }

    private void carregarDados() {
        XYChart.Series<String, Double> aula = new XYChart.Series();
        XYChart.Series<String, Double> eventos = new XYChart.Series<>();
        XYChart.Series<String, Double> janelas = new XYChart.Series<>();
        aula.setName("Docência");
        eventos.setName("Outras atividades");
        janelas.setName("Janelas");
        List<Usuario> usuarios = new UsuarioDAO().pegarPorGrupo(new GrupoDAO().pegarGrupo("Instrutor"));
        double porcentagemJanela = 0;
        double porcentagemLecionando = 0;
        double ocupacaoEventos = 0;
        for (Usuario usuario : usuarios) {
            double porcetagemLecionadoUsuario = usuario.getCargaHoraria() == 0 ? 0 : new AulaDAO().pegarPorDiaInstrutor(usuario, data).size() * 100 / usuario.getCargaHoraria();
            List<CalendarioUsuario> calendarioUsuarios = new CalendarioUsuarioDAO().pegarTodosPorUsuarioData(usuario, data);
            double ocupacaoUsuarioEvento = calendarioUsuarios.isEmpty() ? 0 : (((calendarioUsuarios.get(0).isManha() ? 5 : 0) + (calendarioUsuarios.get(0).isTarde() ? 5 : 0) + (calendarioUsuarios.get(0).isNoite() ? 5 : 0)) * 100) / usuario.getCargaHoraria();
            ocupacaoEventos += ocupacaoUsuarioEvento;
            double porcentagemJanelaUsuario = 100 - porcetagemLecionadoUsuario - ocupacaoUsuarioEvento;
            porcentagemJanela += porcentagemJanelaUsuario;
            porcentagemLecionando += porcetagemLecionadoUsuario;

        }
        porcentagemJanela /= usuarios.size();
        porcentagemLecionando /= usuarios.size();
        ocupacaoEventos /= usuarios.size();
        janelas.getData().setAll(new XYChart.Data<>("% de Janelas", porcentagemJanela / 100));
        aula.getData().setAll(new XYChart.Data<>("% de Aulas", porcentagemLecionando / 100));
        eventos.getData().setAll(new XYChart.Data<>("% em Eventos", ocupacaoEventos / 100));
        dados.setAll(janelas, eventos, aula);
    }

}
