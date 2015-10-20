/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.util;

import br.com.QuadroDeHorario.dao.UsuarioDAO;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author OCTI01
 */
public class DataHorario {

    public static final int INTERVALO = 15;

    public enum Semestre {

        semestre1,
        semestre2;

        public static Semestre semestreAtual() {
            Calendar calendar = Calendar.getInstance();
            return calendar.get(Calendar.MONTH) < Calendar.JULY ? semestre1 : semestre2;
        }

        public static Semestre setSemestre(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH) < Calendar.JULY ? semestre1 : semestre2;
        }

        @Override
        public String toString() {
            return this.equals(semestre1) ? "1º Semestre" : "2º Semestre";
        }
    }

    public enum Horario {

        horario1, horario2, horario3, horario4, horario5;

        public static Horario getHorarioAtual() {
            Calendar horaAtual = Calendar.getInstance();
            horaAtual.setTime(Time.valueOf(ManipularData.toHour(new UsuarioDAO().dataAtual())));
            Calendar horaLimite = Calendar.getInstance();
            switch (DataHorario.Turno.getTurnoAtual()) {
                case manha:
                    horaLimite.setTime(Time.valueOf("07:30:00"));
                    for (Horario horario : Horario.values()) {
                        if (horario.equals(horario3)) {
                            horaLimite.add(Calendar.MINUTE, INTERVALO);
                        }
                        horaLimite.add(Calendar.MINUTE, 45);
                        if (horaAtual.getTime().before(horaLimite.getTime())) {
                            return horario;
                        }
                    }
                    break;
                case tarde:
                    horaLimite.setTime(Time.valueOf("13:00:00"));
                    for (Horario horario : Horario.values()) {
                        if (horario.equals(horario3)) {
                            horaLimite.add(Calendar.MINUTE, INTERVALO);
                        }
                        horaLimite.add(Calendar.MINUTE, 45);
                        if (horaAtual.getTime().before(horaLimite.getTime())) {
                            return horario;
                        }
                    }
                    break;
                case noite:
                    horaLimite.setTime(Time.valueOf("18:00:00"));
                    for (Horario horario : Horario.values()) {
                        if (horario.equals(horario3)) {
                            horaLimite.add(Calendar.MINUTE, INTERVALO);
                        }
                        horaLimite.add(Calendar.MINUTE, 45);
                        if (horaAtual.getTime().before(horaLimite.getTime())) {
                            return horario;
                        }
                    }
                    break;
            }
            return null;
        }

        @Override
        public String toString() {
            switch (this) {
                case horario1:
                    return "1º Horário";
                case horario2:
                    return "2º Horário";
                case horario3:
                    return "3º Horário";
                case horario4:
                    return "4º Horário";
                case horario5:
                    return "5º Horário";
                default:
                    return "Horário inválido";
            }
        }
    }

    public enum Turno {

        manha("Manhã"),
        tarde("Tarde"),
        noite("Noite"),
        diurno("Diurno");

        private String nome;

        private Turno(String nome) {
            this.nome = nome;
        }

        public static Turno getTurnoAtual() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new UsuarioDAO().dataAtual());
            int dia = calendar.get(Calendar.HOUR_OF_DAY);
            return dia < 12 ? Turno.manha : dia < 18 ? Turno.tarde : Turno.noite;
        }

        @Override
        public String toString() {
            return nome;
        }
    }
}
