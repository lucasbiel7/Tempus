/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
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

        SEMESTRE1("1º Semestre"),
        SEMESTRE2("2º Semestre");

        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        private Semestre(String nome) {
            this.nome = nome;
        }

        public static Semestre semestreAtual() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new UsuarioDAO().dataAtual());
            return calendar.get(Calendar.MONTH) < Calendar.JULY ? SEMESTRE1 : SEMESTRE2;
        }

        public static Semestre setSemestre(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH) < Calendar.JULY ? SEMESTRE1 : SEMESTRE2;
        }

        @Override
        public String toString() {
            return getNome();
        }
    }

    public enum Horario {

        HORARIO1("1º Horário"), HORARIO2("2º Horário"), HORARIO3("3º Horário"), HORARIO4("4º Horário"), HORARIO5("5º Horário");

        private Horario(String nome) {
            this.nome = nome;
        }

        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public static Horario getHorarioAtual() {
            Calendar horaAtual = Calendar.getInstance();
            horaAtual.setTime(Time.valueOf(ManipularData.toHour(new UsuarioDAO().dataAtual())));
            Calendar horaLimite = Calendar.getInstance();
            switch (DataHorario.Turno.getTurnoAtual()) {
                case MANHA:
                    horaLimite.setTime(Time.valueOf("07:30:00"));
                    for (Horario horario : Horario.values()) {
                        if (horario.equals(HORARIO3)) {
                            horaLimite.add(Calendar.MINUTE, INTERVALO);
                        }
                        horaLimite.add(Calendar.MINUTE, 45);
                        if (horaAtual.getTime().before(horaLimite.getTime())) {
                            return horario;
                        }
                    }
                    break;
                case TARDE:
                    horaLimite.setTime(Time.valueOf("13:00:00"));
                    for (Horario horario : Horario.values()) {
                        if (horario.equals(HORARIO3)) {
                            horaLimite.add(Calendar.MINUTE, INTERVALO);
                        }
                        horaLimite.add(Calendar.MINUTE, 45);
                        if (horaAtual.getTime().before(horaLimite.getTime())) {
                            return horario;
                        }
                    }
                    break;
                case NOITE:
                    horaLimite.setTime(Time.valueOf("18:00:00"));
                    for (Horario horario : Horario.values()) {
                        if (horario.equals(HORARIO3)) {
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
            return getNome();
        }
    }

    public enum Turno {

        MANHA("Manhã"),
        TARDE("Tarde"),
        NOITE("Noite"),
        DIURNO("Diurno");

        private String nome;

        private Turno(String nome) {
            this.nome = nome;
        }

        public static Turno getTurnoAtual() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new UsuarioDAO().dataAtual());
            int dia = calendar.get(Calendar.HOUR_OF_DAY);
            return dia < 12 ? Turno.MANHA : dia < 18 ? Turno.TARDE : Turno.NOITE;
        }

        @Override
        public String toString() {
            return nome;
        }
    }
}
