/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.control.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.model.entity.Permissao;
import br.com.QuadroDeHorario.model.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.model.util.DatePickerValidator;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.SessaoUsuario;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AbaInicioController implements Initializable {

    @FXML
    private ScrollPane spPrincipal;
    @FXML
    private ScrollPane spEventos;
    @FXML
    private Spinner<Integer> spAno;
    @FXML
    private DatePicker dpData;
    @FXML
    private Button btAbrirInfografico;
    @FXML
    private Button btAbrirCalendarioEventos;
    @FXML
    private Button btAbrirCalendarioEscolar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpData.setValue(LocalDate.now());
        spAno.setValueFactory(new SpinnerValueFactory<Integer>() {

            @Override
            public void decrement(int steps) {
                if (spAno.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAno.getEditor().getText()));
                }
                setValue(getValue() - steps);
            }

            @Override
            public void increment(int steps) {
                if (spAno.getEditor().getText().isEmpty()) {
                    setValue(0);
                } else {
                    setValue(Integer.parseInt(spAno.getEditor().getText()));
                }
                setValue(getValue() + steps);
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        spAno.getValueFactory().setValue(calendar.get(Calendar.YEAR));
        dpData.setOnKeyReleased(new DatePickerValidator(dpData));
        permissao();
    }

    @FXML
    private void btAbrirInfograficoActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(spPrincipal, "Infografico", Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        spPrincipal.setUserData(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        FxMananger.insertPane(spEventos, "GerenciarInstrutor", spPrincipal);
    }

    @FXML
    private void btCalendarioActionEvent(ActionEvent actionEvent) {
        Object[] dados = new Object[]{spAno.getValue(), false};
        FxMananger.insertPane(spPrincipal, "Calendario", dados);
        FxMananger.insertPane(spEventos, "GerenciarEventos", dados);
    }

    @FXML
    private void btCalendarioEscolaActionEvent(ActionEvent actionEvent) {
        Object[] dados = new Object[]{spAno.getValue(), true};
        FxMananger.insertPane(spPrincipal, "Calendario", dados);
        FxMananger.insertPane(spEventos, "GerenciarEventos", dados);
    }

    public void permissao() {
        PermissaoUsuario permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.INFOGRAFICO);
        if (permissaoUsuario != null) {
            btAbrirInfografico.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btAbrirInfografico.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CALENDARIO_ESCOLAR);
        if (permissaoUsuario != null) {
            btAbrirCalendarioEscolar.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btAbrirCalendarioEscolar.setDisable(true);
        }
        permissaoUsuario = new PermissaoUsuarioDAO().pegarTodosPorUsuarioPermissao(SessaoUsuario.getUsuario(), Permissao.CALENDARIO_EVENTOS);
        if (permissaoUsuario != null) {
            btAbrirCalendarioEventos.setDisable(!permissaoUsuario.isHabilitado());
        } else {
            btAbrirCalendarioEventos.setDisable(true);
        }
    }
}
