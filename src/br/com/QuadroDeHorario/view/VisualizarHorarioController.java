
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.SerialCommunication;
import br.com.QuadroDeHorario.control.SerialUtil;
import br.com.QuadroDeHorario.control.TabelaVisualizarHorario;
import br.com.QuadroDeHorario.dao.VariaveisDoSistemaDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.Turma;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.model.SerialConstants;
import br.com.QuadroDeHorario.util.DataHorario;
import br.com.QuadroDeHorario.util.Efeito;
import br.com.QuadroDeHorario.util.FxMananger;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class VisualizarHorarioController implements Initializable {

    @FXML
    private Label lbLogo;
    @FXML
    private ImageView ivLogo;
    @FXML
    private ComboBox<DataHorario.Turno> cbTurno;
    @FXML
    private DatePicker dpDataAula;
    @FXML
    private AnchorPane apHorario;
    @FXML
    private Label lbStatus;
    @FXML
    private Button btEntregarChave;

    private Stage stage;
    private ObservableList<DataHorario.Turno> turnos = FXCollections.observableArrayList();
    private Thread comunication;
    private Timeline mensagemStatus;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            stage = (Stage) lbLogo.getScene().getWindow();
            VariaveisDoSistema variaveisDoSistema = new VariaveisDoSistemaDAO().pegarPorNome(VariaveisDoSistema.NOME.KEYGUARDIAN);
            if (variaveisDoSistema != null) {
                lbStatus.setVisible(Boolean.valueOf(variaveisDoSistema.getValor()));
                btEntregarChave.setVisible(Boolean.valueOf(variaveisDoSistema.getValor()));
                
                if (Boolean.valueOf(variaveisDoSistema.getValor())) {
                    comunication = new Thread(SerialUtil.serialCommunication, "VisualizarHorarioCommunicationSerial");
                    comunication.start();
                }
            } else {
                lbStatus.setVisible(false);
            }
        });
        Efeito.logo(lbLogo, ivLogo);
        if (SerialUtil.serialCommunication == null) {
            SerialUtil.serialCommunication = new SerialCommunication();
        }
        Tooltip tooltip = new Tooltip();
        lbStatus.setTooltip(tooltip);
        mensagemStatus = new Timeline(new KeyFrame(Duration.seconds(3), (ActionEvent event) -> {
            lbStatus.setText(SerialUtil.serialCommunication.getStatus());
            tooltip.setText(SerialUtil.serialCommunication.getDebug());
            btEntregarChave.setDisable(!SerialUtil.serialCommunication.isConexao());
            if (SerialUtil.serialCommunication.isConexao()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(SerialUtil.serialCommunication.getLastInfo());
                calendar.add(Calendar.MINUTE, 1);
                if (calendar.getTime().before(new Date())) {
                    SerialUtil.serialCommunication.enviarMensagem(SerialConstants.SLEEP);
                }
            }
        }));
        lbStatus.setEffect(new DropShadow(1.0d, 1.0d, 1.0d, Color.rgb(90, 71, 23)));
        mensagemStatus.setCycleCount(Timeline.INDEFINITE);
        mensagemStatus.playFromStart();
        cbTurno.setItems(turnos);
        dpDataAula.setValue(LocalDate.now());
        cbTurno.getSelectionModel().select(DataHorario.Turno.getTurnoAtual());
        lbLogo.setText(FxMananger.NOME_PROGRAMA);
        turnos.setAll(DataHorario.Turno.values());
        turnos.remove(DataHorario.Turno.DIURNO);
    }

    @FXML
    private void btInicioActionEvent(ActionEvent actionEvent) {
        if (SerialUtil.serialCommunication.isConexao()) {
            SerialUtil.serialCommunication.enviarMensagem(SerialConstants.RESET);
        }
        mensagemStatus.stop();
        stage.close();
        FxMananger.show("Inicio", "Início", true, false);
    }

    private Class clicado = Turma.class;

    @FXML
    private void btInstrutorActionEvent(ActionEvent actionEvent) {
        clicado = Usuario.class;
        carregarTabela(clicado);
    }

    @FXML
    private void btSemestralActionEvent(ActionEvent actionEvent) {
        FxMananger.show("VisualizarHorarioSemestral", "Visualizar horário semestral", true, false, clicado);
    }

    @FXML
    private void btAmbienteActionEvent(ActionEvent actionEvent) {
        clicado = Ambiente.class;
        carregarTabela(clicado);
    }

    @FXML
    private void btMaximizarActionEvent(ActionEvent actionEvent) {
        TabelaVisualizarHorario tabelaVisualizarHorario = new TabelaVisualizarHorario(cbTurno.getSelectionModel().getSelectedItem(), clicado, Date.from(dpDataAula.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        FxMananger.show("MaximizarHorario", "Horário diário", true, true, tabelaVisualizarHorario);
    }

    @FXML
    private void btTurmaActionEvent(ActionEvent actionEvent) {
        clicado = Turma.class;
        carregarTabela(clicado);
    }

    @FXML
    private void cbValueActionEvent(ActionEvent actionEvent) {
        carregarTabela(clicado);
    }

    private void carregarTabela(Class classe) {
        apHorario.getChildren().clear();
        TabelaVisualizarHorario tabelaVisualizarHorario = new TabelaVisualizarHorario(cbTurno.getSelectionModel().getSelectedItem(), classe, Date.from(dpDataAula.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        apHorario.getChildren().add(tabelaVisualizarHorario);
    }

    @FXML
    private void btEntregarChaveActionEvent(ActionEvent actionEvent) {
        SerialUtil.serialCommunication.enviarMensagem(SerialConstants.DEVOLVER_CHAVE);
    }
}
