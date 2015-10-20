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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
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
    private ComboBox<Ambiente> cbAmbiente;
    @FXML
    private ComboBox<DataHorario.Semestre> cbSemestre;
    @FXML
    private Spinner<Integer> spAno;
    @FXML
    private AnchorPane apPrincipal;

    private ObservableList<Ambiente> ambientes = FXCollections.observableArrayList();
    private ObservableList<DataHorario.Semestre> semestres = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spAno.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spAno.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAno.getEditor().getText()));
                }
                setValue(getValue() + steps);
            }

            @Override
            public void increment(int steps) {
                if (spAno.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAno.getEditor().getText()));
                }
                setValue(getValue() - steps);
            }
        });
        ambientes.setAll(new AmbienteDAO().pegarTodos());
        semestres.setAll(Semestre.values());
        //Configurando os combobox
        cbAmbiente.setItems(ambientes);
        cbSemestre.setItems(semestres);
        spAno.getValueFactory().setValue(Calendar.getInstance().get(Calendar.YEAR));
    }

    @FXML
    private void btAbrirMapaDeUsoActionEvent(ActionEvent actionEvent) {
        if (cbAmbiente.getSelectionModel().getSelectedItem() != null && cbSemestre.getSelectionModel().getSelectedItem() != null) {
            FxMananger.insertPane(apPrincipal.getParent(), "VisualizarMapaDeUso", new Object[]{cbAmbiente.getSelectionModel().getSelectedItem(), cbSemestre.getSelectionModel().getSelectedItem(), spAno.getValue()});
        } else {
            Mensagem.showInformation("Preencha os campos!", "Para continuar a utilizando essa parte do sistema e necessário preencher todos os campos,\n"
                    + "'Ambiente', 'Semestre' e 'Ano'.");
        }
    }
}
