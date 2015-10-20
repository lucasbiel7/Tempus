/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.PermissaoUsuarioDAO;
import br.com.QuadroDeHorario.entity.Permissao;
import br.com.QuadroDeHorario.entity.PermissaoUsuario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.SessaoUsuario;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class AbaInicioController implements Initializable {

    @FXML
    private AnchorPane apPrincipal;
    @FXML
    private AnchorPane apEventos;
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
        permissao();
    }

    @FXML
    private void btAbrirInfograficoActionEvent(ActionEvent actionEvent) {
        FxMananger.insertPane(apPrincipal, "Infografico", Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        apPrincipal.setUserData(Date.from(dpData.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        FxMananger.insertPane(apEventos, "GerenciarInstrutor", apPrincipal);
    }

    @FXML
    private void btCalendarioActionEvent(ActionEvent actionEvent) {
        Object[] dados = new Object[]{spAno.getValue(), false};
        FxMananger.insertPane(apPrincipal, "Calendario", dados);
        apEventos.getChildren().clear();
        FxMananger.insertPane(apEventos, "GerenciarEventos", dados);
    }

    @FXML
    private void btCalendarioEscolaActionEvent(ActionEvent actionEvent) {
        Object[] dados = new Object[]{spAno.getValue(), true};
        FxMananger.insertPane(apPrincipal, "Calendario", dados);
        apEventos.getChildren().clear();
        FxMananger.insertPane(apEventos, "GerenciarEventos", dados);
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
