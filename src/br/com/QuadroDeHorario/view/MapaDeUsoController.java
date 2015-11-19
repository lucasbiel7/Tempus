/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.AmbienteDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.util.DataHorario;
import br.com.QuadroDeHorario.util.DataHorario.Semestre;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.Mensagem;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class MapaDeUsoController implements Initializable {

    @FXML
    private ComboBox<DataHorario.Semestre> cbSemestreInstrutor;
    @FXML
    private Spinner<Integer> spAnoInstrutor;
    @FXML
    private ComboBox<Ambiente> cbAmbiente;
    @FXML
    private ComboBox<DataHorario.Semestre> cbSemestreAmbiente;
    @FXML
    private Spinner<Integer> spAnoAmbiente;
    @FXML
    private AnchorPane apPrincipal;

    private ScrollPane spPrincipal;
    private ObservableList<Ambiente> ambientes = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Semestre> semestres = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> {
            spPrincipal = (ScrollPane) apPrincipal.getUserData();

        });
        spAnoAmbiente.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spAnoAmbiente.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAnoAmbiente.getEditor().getText()));
                }
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                if (spAnoAmbiente.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAnoAmbiente.getEditor().getText()));
                }
                setValue(getValue() + steps);
            }
        });
        spAnoInstrutor.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spAnoAmbiente.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAnoAmbiente.getEditor().getText()));
                }
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                if (spAnoAmbiente.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAnoAmbiente.getEditor().getText()));
                }
                setValue(getValue() + steps);
            }
        });
        ambientes.setAll(new AmbienteDAO().pegarTodos());
        semestres.setAll(Semestre.values());
        //Configurando os combobox
        cbAmbiente.setItems(ambientes);
        cbSemestreAmbiente.setItems(semestres);
        cbSemestreInstrutor.setItems(semestres);
        spAnoAmbiente.getValueFactory().setValue(Calendar.getInstance().get(Calendar.YEAR));
        spAnoInstrutor.getValueFactory().setValue(Calendar.getInstance().get(Calendar.YEAR));

    }

    @FXML
    private void btAbrirMapaDeUsoActionEvent(ActionEvent actionEvent) {
        if (cbAmbiente.getSelectionModel().getSelectedItem() != null && cbSemestreAmbiente.getSelectionModel().getSelectedItem() != null) {
            FxMananger.insertPane(spPrincipal, "VisualizarMapaDeUso", new Object[]{cbAmbiente.getSelectionModel().getSelectedItem(), cbSemestreAmbiente.getSelectionModel().getSelectedItem(), spAnoAmbiente.getValue()});
        } else {
            Mensagem.showError("Selecione os campos!", "Para continuar a utilizando o mapeamento de ambiente é\n"
                    + " necessário preencher todos os campos, 'Ambiente', 'Semestre' e 'Ano'.");
        }
    }

    @FXML
    private void btAbrirMapaDeUsoInstrutorActionEvent(ActionEvent actionEvent) {
        if (cbSemestreInstrutor.getSelectionModel().getSelectedItem() != null) {
            FxMananger.insertPane(spPrincipal, "VisualizarMapaDeUso", new Object[]{cbSemestreInstrutor.getSelectionModel().getSelectedItem(), spAnoInstrutor.getValue()});
        } else {
            Mensagem.showError("Selecione os campos",
                    "Para continuar usando o mapeamento dos instrutores\n"
                    + "é necessário selecionar o semestre e o ano.");
        }
    }
}
