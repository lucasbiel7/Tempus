
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.entity.Aula;
import br.com.QuadroDeHorario.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.model.DiaMapaDeUso;
import br.com.QuadroDeHorario.model.DiaMapaDeUsoInstrutor;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class PopOverMapaDeUsoController implements Initializable {

    @FXML
    private AnchorPane apContainer;
    @FXML
    private ListView<Object> lvEventos;

    private ObservableList<Object> eventos = FXCollections.observableArrayList();

    @FXML
    private Label lbTitulo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            if (apContainer.getUserData() != null) {
                if (apContainer.getUserData() instanceof DiaMapaDeUso) {
                    DiaMapaDeUso diaMapaDeUso = (DiaMapaDeUso) apContainer.getUserData();
                    eventos.setAll(diaMapaDeUso.getEventos());
                    for (Object evento : diaMapaDeUso.getEventos()) {
                        if (evento instanceof Aula) {
                            Aula aula = (Aula) evento;
                            lbTitulo.setText("Aulas");
                        } else if (evento instanceof CalendarioAmbiente) {
                            CalendarioAmbiente calendarioAmbiente = (CalendarioAmbiente) evento;
                            lbTitulo.setText("Eventos");
                        }
                    }
                } else if (apContainer.getUserData() instanceof DiaMapaDeUsoInstrutor) {
                    lbTitulo.setText("Instrutores livres");
                    DiaMapaDeUsoInstrutor diaMapaDeUso = (DiaMapaDeUsoInstrutor) apContainer.getUserData();
                    eventos.setAll(diaMapaDeUso.getUsuario());
                }
            }
        });
        lvEventos.setItems(eventos);
        lvEventos.setCellFactory((ListView<Object> param) -> new ListCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                if (empty) {
                    setText(null);
                } else if (item instanceof Aula) {
                    Aula aula = (Aula) item;
                    setText("Turma: " + aula.getId().getTurma() + "\n"
                            + "Horário: " + aula.getId().getHorario() + "\n"
                            + "Disclina: " + aula.getMateriaHorario().getMateriaTurmaIntrutorSemestre().getMateria());
                } else if (item instanceof CalendarioAmbiente) {
                    CalendarioAmbiente calendarioAmbiente = (CalendarioAmbiente) item;
                    setText("Evento: " + calendarioAmbiente.getId().getCalendario().getId().getEvento() + "\n"
                            + "Data: " + new SimpleDateFormat("dd/MM/yyyy").format(calendarioAmbiente.getId().getCalendario().getId().getDataAcontecimento()) + "\n");
                } else if (item instanceof Usuario) {
                    Usuario usuario = (Usuario) item;
                    setText(usuario.toString());
                }
            }
        });
    }
}
