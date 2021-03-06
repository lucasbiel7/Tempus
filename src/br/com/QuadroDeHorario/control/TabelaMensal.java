/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.CalendarioDAO;
import br.com.QuadroDeHorario.model.SemanaCalendario;
import br.com.QuadroDeHorario.model.entity.Calendario;
import br.com.QuadroDeHorario.model.entity.Evento;
import br.com.QuadroDeHorario.model.util.Efeito;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.model.util.Mensagem;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.util.Callback;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author OCTI01
 */
public class TabelaMensal extends TableView<SemanaCalendario> {

    private TableColumn<SemanaCalendario, Date> tcSegunda;
    private TableColumn<SemanaCalendario, Date> tcTerca;
    private TableColumn<SemanaCalendario, Date> tcQuarta;
    private TableColumn<SemanaCalendario, Date> tcQuinta;
    private TableColumn<SemanaCalendario, Date> tcSexta;
    private TableColumn<SemanaCalendario, Date> tcSabado;
    private TableColumn<SemanaCalendario, Date> tcDomingo;

    public static Evento evento;
    private TableColumn<SemanaCalendario, Date> tcTitulo;
    private ObservableList<SemanaCalendario> semanaCalendarios = FXCollections.observableArrayList();
    private boolean escolar;

    public TabelaMensal(int mes, int ano, boolean escolar) {
        this.escolar = escolar;
        carregarDados(mes, ano);
        setEditable(true);
        int a = 0xff;
        tcSegunda = new TableColumn<>("Seg");
        tcTerca = new TableColumn<>("Ter");
        tcQuarta = new TableColumn<>("Qua");
        tcQuinta = new TableColumn<>("Qui");
        tcSexta = new TableColumn<>("Sex");
        tcSabado = new TableColumn<>("Sáb");
        tcDomingo = new TableColumn<>("Dom");
        tcSegunda.setOnEditStart(new ClicarTabela());
        tcTerca.setOnEditStart(new ClicarTabela());
        tcQuarta.setOnEditStart(new ClicarTabela());
        tcQuinta.setOnEditStart(new ClicarTabela());
        tcSexta.setOnEditStart(new ClicarTabela());
        tcSabado.setOnEditStart(new ClicarTabela());
        tcDomingo.setOnEditStart(new ClicarTabela());
        tcTitulo.getColumns().addAll(tcDomingo, tcSegunda, tcTerca, tcQuarta, tcQuinta, tcSexta, tcSabado);
        getColumns().setAll(tcTitulo);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setMinSize(250d, 150d);
        setPrefSize(250d, 175d);
        setItems(semanaCalendarios);
        setSortPolicy(null);
        tcTitulo.getColumns().addListener(new ListChangeListener<TableColumn<SemanaCalendario, ?>>() {
            private boolean suspender;

            @Override
            public void onChanged(ListChangeListener.Change<? extends TableColumn<SemanaCalendario, ?>> c) {
                c.next();
                if (c.wasReplaced() && !suspender) {
                    suspender = true;
                    tcTitulo.getColumns().setAll(tcDomingo, tcSegunda, tcTerca, tcQuarta, tcQuinta, tcSexta, tcSabado);
                    suspender = false;
                }
            }
        });
        this.getSelectionModel().cellSelectionEnabledProperty().setValue(Boolean.TRUE);
        tcSegunda.setCellValueFactory(new PropertyValueFactory<>("segunda"));
        tcTerca.setCellValueFactory(new PropertyValueFactory<>("terca"));
        tcQuarta.setCellValueFactory(new PropertyValueFactory<>("quarta"));
        tcQuinta.setCellValueFactory(new PropertyValueFactory<>("quinta"));
        tcSexta.setCellValueFactory(new PropertyValueFactory<>("sexta"));
        tcSabado.setCellValueFactory(new PropertyValueFactory<>("sabado"));
        tcDomingo.setCellValueFactory(new PropertyValueFactory<>("domingo"));
        tcSegunda.setCellFactory(new RenderTableView());
        tcTerca.setCellFactory(new RenderTableView());
        tcQuarta.setCellFactory(new RenderTableView());
        tcQuinta.setCellFactory(new RenderTableView());
        tcSexta.setCellFactory(new RenderTableView());
        tcSabado.setCellFactory(new RenderTableView());
        tcSabado.setCellFactory(new RenderTableView());
        tcDomingo.setCellFactory(new RenderTableView());
        //Nao deixar as colunas poderem ser ordenar
        tcSegunda.sortableProperty().set(false);
        tcTerca.sortableProperty().set(false);
        tcQuarta.sortableProperty().set(false);
        tcQuinta.sortableProperty().set(false);
        tcSexta.sortableProperty().set(false);
        tcSabado.sortableProperty().set(false);
        tcDomingo.sortableProperty().set(false);
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);

    }

    private void carregarDados(int mes, int ano) {
        try {
            Date inicio = new SimpleDateFormat("dd/MM/yyyy").parse("01/" + mes + "/" + ano);
            Date fim = new SimpleDateFormat("dd/MM/yyyy").parse("01/" + (mes + 1) + "/" + ano);
            tcTitulo = new TableColumn<>(new SimpleDateFormat("MMMM").format(inicio));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE");
            SemanaCalendario datas = new SemanaCalendario();
            Calendar dia = Calendar.getInstance();
            List<SemanaCalendario> semanasCalendarios = new ArrayList<>();
            dia.setTime(inicio);
            while (dia.getTime().before(fim)) {
                switch (dia.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.SUNDAY:
                        datas.setDomingo(dia.getTime());
                        break;
                    case Calendar.MONDAY:
                        datas.setSegunda(dia.getTime());
                        break;
                    case Calendar.TUESDAY:
                        datas.setTerca(dia.getTime());
                        break;
                    case Calendar.WEDNESDAY:
                        datas.setQuarta(dia.getTime());
                        break;
                    case Calendar.THURSDAY:
                        datas.setQuinta(dia.getTime());
                        break;
                    case Calendar.FRIDAY:
                        datas.setSexta(dia.getTime());
                        break;
                    case Calendar.SATURDAY:
                        datas.setSabado(dia.getTime());
                        semanasCalendarios.add(datas);
                        datas = new SemanaCalendario();
                        break;
                }
                dia.add(Calendar.DAY_OF_MONTH, 1);
            }
            if (datas.getDomingo() != null) {
                semanasCalendarios.add(datas);
            }
            semanaCalendarios.setAll(semanasCalendarios);
        } catch (ParseException ex) {
            Logger.getLogger(TabelaMensal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class ClicarTabela implements EventHandler<TableColumn.CellEditEvent<SemanaCalendario, Date>> {

        @Override
        public void handle(TableColumn.CellEditEvent<SemanaCalendario, Date> event) {
            Date dataSelecionada = event.getOldValue();
            if (dataSelecionada != null) {
                if (evento != null) {
                    Calendario calendario = new Calendario();
                    calendario.setId(new Calendario.EventoData(evento, dataSelecionada));
                    if (new CalendarioDAO().pegarPorId(calendario.getId()) == null) {
                        try {
                            new CalendarioDAO().cadastrar(calendario);
                            event.consume();
                        } catch (ConstraintViolationException e) {
                            Mensagem.showError("Evento cadastrado!", "O evento " + evento.getNome() + " já foi adicionado a este dia\n"
                                    + "do ano, não e permitido cadastrar no mesmo dia o mesmo evento!");
                        }
                    } else {
                        new CalendarioDAO().editar(calendario);
                        event.consume();
                    }
                }
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dataSelecionada);
                carregarDados(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
            }
        }
    }
    SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");

    public class RenderTableView implements Callback<TableColumn<SemanaCalendario, Date>, TableCell<SemanaCalendario, Date>> {

        @Override
        public TableCell<SemanaCalendario, Date> call(TableColumn<SemanaCalendario, Date> param) {
            return new TableCell<SemanaCalendario, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    if (!empty) {
                        if (item != null) {
                            setText(new SimpleDateFormat("dd").format(item));
                            List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(item, escolar);
                            if (!calendarios.isEmpty()) {
                                String tooltip = "";
                                for (Calendario calendario : calendarios) {
                                    tooltip += calendario.getId().getEvento().toString() + "\n";
                                }
                                setTooltip(new Tooltip(tooltip));
                                Color[] todasCores = new Color[calendarios.size()];
                                int i = 0;
                                for (Calendario calendario : calendarios) {
                                    String[] cores = calendario.getId().getEvento().getColor().split("@");
                                    todasCores[i] = new Color(Double.parseDouble(cores[0]), Double.parseDouble(cores[1]), Double.parseDouble(cores[2]), Double.parseDouble(cores[3]));
                                    i++;
                                }
                                if (todasCores.length < 2) {
                                    setTextFill(Efeito.brancoOuPreto(todasCores[0]));
                                    setBackground(new Background(new BackgroundFill(todasCores[0], CornerRadii.EMPTY, Insets.EMPTY)));
                                } else {
                                    setTextFill(Efeito.brancoOuPreto(todasCores[1]));
                                    setBackground(new Background(new BackgroundFill(LinearGradient.valueOf("linear-gradient(from 0% 0% to 100% 100%, " + FxMananger.toRGB(todasCores[0]) + "  0% , " + FxMananger.toRGB(todasCores[1]) + " 30%," + FxMananger.toRGB(todasCores.length > 2 ? todasCores[2] : todasCores[1]) + " 100%)"), CornerRadii.EMPTY, Insets.EMPTY)));
                                }
                                MenuItem miAdicionarRecursos = new MenuItem("Manipular recursos");
                                MenuItem miExcluir = new MenuItem("Excluir");
                                ContextMenu cmOpcoes = new ContextMenu(miAdicionarRecursos, miExcluir);
                                miAdicionarRecursos.setOnAction((ActionEvent event) -> {
                                    FxMananger.show("AdicionarRecursosEvento", "Manipular Recursos/Ambiente aos eventos", true, false, new Object[]{item, escolar});
                                });
                                miExcluir.setOnAction((ActionEvent event) -> {
                                    for (Calendario calendario : calendarios) {
                                        if (Mensagem.showConfirmation("Excluir calendário", "Marcador " + calendario.getId().getEvento().getNome() + "\n"
                                                + "Data marcada:" + sdfData.format(calendario.getId().getDataAcontecimento()) + "\n"
                                                + "Realmente deseja excluir?")) {
                                            new CalendarioDAO().excluir(calendario);
                                        }
                                    }
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(item);
                                    carregarDados(calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
                                });
                                setOnMouseReleased((MouseEvent event) -> {
                                    if (event.isPopupTrigger()) {
                                        cmOpcoes.show(this, event.getScreenX(), event.getScreenY());
                                    }
                                });
                            } else {
                                setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                                setTextFill(Color.BLACK);
                            }
                        } else {
                            setText("");
                        }
                    } else {
                        setText("");
                        setTooltip(null);
                        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        setTextFill(Color.BLACK);
                    }
                }
            };
        }

    }
}
