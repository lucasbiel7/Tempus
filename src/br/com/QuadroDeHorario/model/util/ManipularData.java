/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author OCTI01
 */
public class ManipularData {

    public static String toHour(Date data) {
        return new SimpleDateFormat("HH:mm:ss").format(data);
    }

    public static String toDate(Date data) {
        return new SimpleDateFormat("dd/MM/yyyy").format(data);
    }
}
