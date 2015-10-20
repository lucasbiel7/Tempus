/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.util.MesMapaDeUso;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author OCTI01
 */
public class TabelaMapaDeUso extends TableView<MesMapaDeUso> {

    private Ambiente ambiente;
    private int ano;
    private int mes;
    //Componentes da tabela
    //Novo teste com lista
    //Vamos ver se alivia o uso de dados
    private TableColumn<MesMapaDeUso, Object> tcNomeMes;
    private List<TableColumn<MesMapaDeUso, Object>> lTcDias = new ArrayList<>();
    private List<TableColumn<MesMapaDeUso, Object>> lTcNomeDias = new ArrayList<>();
    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfDia = new SimpleDateFormat("dd");
    private SimpleDateFormat sdfNomeDia = new SimpleDateFormat("EEE");
    private SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
    private SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
    private SimpleDateFormat sdfNomeMes = new SimpleDateFormat("MMMM");
    private Date inicio;
    private Date fim;

    public TabelaMapaDeUso(Ambiente ambiente, int ano, int mes) {
        this.ambiente = ambiente;
        this.ano = ano;
        this.mes = mes;
        setPrefSize(USE_PREF_SIZE, 150d);
        try {
            inicio = sdfData.parse("01/" + mes + "/" + ano);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            calendar.add(Calendar.MONTH, 1);
            fim = calendar.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(TabelaMapaDeUso.class.getName()).log(Level.SEVERE, null, ex);
        }
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setEditable(false);
        carregarColunas();
    }

    public void carregarDados() {

    }

    private void carregarColunas() {
        tcNomeMes = new TableColumn<>(sdfNomeMes.format(inicio));
        getColumns().add(tcNomeMes);
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(this.inicio);
        while (inicio.getTime().before(fim)) {
            TableColumn<MesMapaDeUso, Object> tcNomeDia = new TableColumn<>(sdfNomeDia.format(inicio.getTime()));
            TableColumn<MesMapaDeUso, Object> tcNumeroDia = new TableColumn<>(sdfDia.format(inicio.getTime()));
            tcNomeDia.getColumns().add(tcNumeroDia);
            lTcNomeDias.add(tcNomeDia);
            lTcDias.add(tcNumeroDia);
            tcNomeMes.getColumns().add(tcNomeDia);
            inicio.add(Calendar.DAY_OF_MONTH, 1);
        }
        lTcNomeDias.add(tcNomeMes);
    }

}
