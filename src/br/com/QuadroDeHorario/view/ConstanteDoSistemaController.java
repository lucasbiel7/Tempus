/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.view;

import br.com.QuadroDeHorario.dao.SistemaDAO;
import br.com.QuadroDeHorario.dao.VariaveisDoSistemaDAO;
import br.com.QuadroDeHorario.entity.VariaveisDoSistema;
import br.com.QuadroDeHorario.util.FxMananger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

/**
 * FXML Controller class
 *
 * @author OCTI01
 */
public class ConstanteDoSistemaController implements Initializable {

    @FXML
    private Spinner<Integer> spOcupacaoMinima;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spOcupacaoMinima.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                if (spOcupacaoMinima.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spOcupacaoMinima.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() - steps <= 0) {
                    setValue(0);
                } else {
                    setValue(getValue() - steps);
                }
            }

            @Override
            public void increment(int steps) {
                if (spOcupacaoMinima.getEditor().getText().isEmpty()) {
                    if (getValue() == null) {
                        setValue(0);
                    }
                } else {
                    try {
                        setValue(Integer.parseInt(spOcupacaoMinima.getEditor().getText()));
                    } catch (NumberFormatException nfe) {
                        setValue(0);
                    }
                }
                if (getValue() + steps > 100) {
                    setValue(100);
                } else {
                    setValue(getValue() + steps);
                }
            }
        });
        VariaveisDoSistema ocupacao = new VariaveisDoSistemaDAO().pegarPorNome("ocupacao");
        if (ocupacao != null) {
            spOcupacaoMinima.getValueFactory().setValue(Integer.parseInt(ocupacao.getValor()));

        }
    }

    @FXML
    private void btSalvarActionEvent(ActionEvent actionEvent) {
        VariaveisDoSistema ocupacao = new VariaveisDoSistemaDAO().pegarPorNome("ocupacao");
        if (ocupacao == null) {
            ocupacao = new VariaveisDoSistema();
            ocupacao.setNome("ocupacao");
            ocupacao.setValor(String.valueOf(spOcupacaoMinima.getValue()));
            ocupacao.setSistema(new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA));
            new VariaveisDoSistemaDAO().cadastrar(ocupacao);
        } else {
            ocupacao.setValor(String.valueOf(spOcupacaoMinima.getValue()));
            new VariaveisDoSistemaDAO().editar(ocupacao);
        }
    }
}
