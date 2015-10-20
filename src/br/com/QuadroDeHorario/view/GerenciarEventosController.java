/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.TabelaMensal;
import br.com.QuadroDeHorario.dao.CalendarioDAO;
import br.com.QuadroDeHorario.dao.EventoDAO;
import br.com.QuadroDeHorario.entity.Calendario;
import br.com.QuadroDeHorario.entity.Evento;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class GerenciarEventosController implements Initializable {

    @FXML
    private TableView<Evento> tvEvento;
    @FXML
    private TableColumn<Evento, Evento> tcNome;
    private int ano;
    private boolean escolar;
    private ObservableList<Evento> eventos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            Object[] dados = (Object[]) tvEvento.getParent().getUserData();
            ano = (int) dados[0];
            escolar = (boolean) dados[1];
            eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(ano, escolar));
        });
        tvEvento.setItems(eventos);
        tcNome.setCellValueFactory((TableColumn.CellDataFeatures<Evento, Evento> param) -> new SimpleObjectProperty<>(param.getValue()));
        tcNome.setCellFactory((TableColumn<Evento, Evento> param) -> new TableCell<Evento, Evento>() {
            @Override
            protected void updateItem(Evento item, boolean empty) {
                if (empty) {
                    setText("");
                } else {
                    setText(item.toString());
                    String[] cores = item.getColor().split("@");
                    Color color = new Color(Double.parseDouble(cores[0]), Double.parseDouble(cores[1]), Double.parseDouble(cores[2]), Double.parseDouble(cores[3]));
                    Circle circle = new Circle(5d);
                    circle.setStroke(color);
                    circle.setFill(color);
                    setGraphic(circle);
                }
            }
        });
    }

    @FXML
    private void miNovoActionEvent(ActionEvent actionEvent) {
        Object[] dados = new Object[]{ano, escolar};
        FxMananger.show("CadastrarEvento", "Cadastrar evento", true, false, dados);
        eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(ano, escolar));
    }

    @FXML
    private void miEditarActionEvent(ActionEvent actionEvent) {
        Evento evento = tvEvento.getSelectionModel().getSelectedItem();
        if (evento != null) {
            FxMananger.show("EditarEvento", "Editar evento", true, false, evento);
            eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(ano, escolar));
        }
    }

    @FXML
    private void miExcluirActionEvent(ActionEvent actionEvent) {
        if (tvEvento.getSelectionModel().getSelectedItem() != null) {
            List<Calendario> calendarios = new CalendarioDAO().pegarPorEvento(tvEvento.getSelectionModel().getSelectedItem());
            if (Mensagem.showConfirmation("Excluir evento", "Você tem certeza que deseja excluir este evento?" + (calendarios.isEmpty() ? "" : "\nExistem datas cadastrados"))) {
                new EventoDAO().excluir(tvEvento.getSelectionModel().getSelectedItem());
                eventos.setAll(new EventoDAO().pegarTodosPorAnoEscola(ano, escolar));
            }
        }
    }

    @FXML
    private void tvEventoMouseReleased(MouseEvent mouseEvent) {
        TabelaMensal.evento = tvEvento.getSelectionModel().getSelectedItem();
    }
}
