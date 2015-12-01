/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import java.time.LocalDate;
import java.time.Month;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;

/**
 *
 * @author OCTI01
 */
public class DatePickerValidator implements EventHandler<Event> {

    private final DatePicker datePicker;

    public DatePickerValidator(DatePicker datePicker) {
        this.datePicker = datePicker;
        datePicker.getEditor().setOnMouseReleased(this);
        datePicker.setOnAction((ActionEvent event) -> {
            DatePickerValidator.this.handle(null);
        });
        datePicker.getStylesheets().add("/br/com/QuadroDeHorario/view/estilo.css");
    }

    @Override
    public void handle(Event event) {
        datePicker.getStyleClass().remove("certo");
        datePicker.getStyleClass().add("error");
        String data = datePicker.getEditor().getText();
        if (data.matches("^(\\d{2}/\\d{2}/\\d{4})$")) {
            int dia = Integer.parseInt(data.substring(0, 2));
            int mes = Integer.parseInt(data.substring(3, 5));
            int ano = Integer.parseInt(data.substring(6, data.length()));
            if (!(dia > 31 || mes > 12)) {
                datePicker.setValue(LocalDate.of(ano, Month.of(mes), dia));
                datePicker.getStyleClass().remove("error");
                datePicker.getStyleClass().add("certo");
            }
        }
    }
}
