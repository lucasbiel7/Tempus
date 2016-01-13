/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioAmbienteDAO;
import br.com.QuadroDeHorario.model.DiaMapaDeUso;
import br.com.QuadroDeHorario.model.MesMapaDeUso;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.CalendarioAmbiente;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.FxMananger;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;

/**
 *
 * @author OCTI01
 */
public class TabelaMapaDeUso extends TableView<MesMapaDeUso<Ambiente, DiaMapaDeUso>> {

    private Ambiente ambiente;
    //Componentes da tabela
    //Novo teste com lista
    //Vamos ver se alivia o uso de dados
    private TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DiaMapaDeUso> tcNomeMes;
    private List<TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, ?>> colunas = new ArrayList<>();
    private TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DataHorario.Turno> tcTurno;

    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfDia = new SimpleDateFormat("dd");
    private SimpleDateFormat sdfNomeDia = new SimpleDateFormat("EEE");
    private SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
    private SimpleDateFormat sdfAno = new SimpleDateFormat("yyyy");
    private SimpleDateFormat sdfNomeMes = new SimpleDateFormat("MMMM");
    private Date inicio;
    private Date fim;
    private PopOver popOver;
    private ObservableList<MesMapaDeUso<Ambiente, DiaMapaDeUso>> mapaDeUsos = FXCollections.observableArrayList();
    private Node popOverMapaDeUso;

    public TabelaMapaDeUso(Ambiente ambiente, int ano, int mes) {
        this.ambiente = ambiente;
        this.getStylesheets().add("/br/com/QuadroDeHorario/view/estilo.css");
        popOver = new PopOver();
        popOver.setTitle("Atividades");
        popOver.setAutoHide(true);
        setPrefSize(USE_PREF_SIZE, 145d);
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
        tcNomeMes.getColumns().addListener(new ListChangeListener<TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, ?>>() {
            private boolean permuted;

            @Override
            public void onChanged(ListChangeListener.Change<? extends TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, ?>> c) {
                c.next();
                if (c.wasReplaced() && !permuted) {
                    permuted = true;
                    tcNomeMes.getColumns().setAll(colunas);
                    permuted = false;
                }
            }
        });
        setItems(mapaDeUsos);
        getSelectionModel().setCellSelectionEnabled(true);
        carregarDados();
    }

    private void carregarDados() {
        new Thread(() -> {
            mapaDeUsos.clear();
            for (DataHorario.Turno turno : DataHorario.Turno.values()) {
                if (!turno.equals(DataHorario.Turno.DIURNO)) {
                    MesMapaDeUso mapaDeUso = new MesMapaDeUso();
                    mapaDeUso.setTurno(turno);
                    mapaDeUso.setTipo(ambiente);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(inicio);
                    while (calendar.getTime().before(fim)) {
                        DiaMapaDeUso diaMapaDeUso = new DiaMapaDeUso();
                        diaMapaDeUso.setData(calendar.getTime());
                        for (CalendarioAmbiente calendarioAmbiente : new CalendarioAmbienteDAO().pegarTodosPorDataAmbienteTurno(calendar.getTime(), ambiente, mapaDeUso.getTurno())) {
                            diaMapaDeUso.getEventos().add(calendarioAmbiente);
                        }
                        for (Aula aula : new AulaDAO().pegarPorAmbienteDia(calendar.getTime(), ambiente, mapaDeUso.getTurno())) {
                            diaMapaDeUso.getEventos().add(aula);
                        }
                        mapaDeUso.getDiaMapaDeUsos().add(diaMapaDeUso);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    mapaDeUsos.add(mapaDeUso);
                }
            }
        },"Carregando dados da tabela mapa de uso").start();
    }

    private void carregarColunas() {
        tcNomeMes = new TableColumn<>(sdfNomeMes.format(inicio) + " (" + sdfMes.format(inicio) + ")");
        tcTurno = new TableColumn<>("Turno");
        tcTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        tcTurno.setCellFactory((TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DataHorario.Turno> param) -> new TableCell<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DataHorario.Turno>() {
            @Override
            protected void updateItem(DataHorario.Turno item, boolean empty) {
                setTextFill(Color.WHITE);
                setBackground(new Background(new BackgroundFill(Color.rgb(130, 151, 178), CornerRadii.EMPTY, Insets.EMPTY)));
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        colunas.add(tcTurno);
        tcNomeMes.getColumns().add(tcTurno);
        getColumns().add(tcNomeMes);
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(this.inicio);
        while (inicio.getTime().before(fim)) {
            TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DiaMapaDeUso> tcNomeDia = new TableColumn<>(sdfNomeDia.format(inicio.getTime()));
            TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DiaMapaDeUso> tcNumeroDia = new TableColumn<>(sdfDia.format(inicio.getTime()));
            tcNumeroDia.sortableProperty().set(false);
            int dia = inicio.get(Calendar.DAY_OF_MONTH) - 1;
            tcNumeroDia.setCellValueFactory((TableColumn.CellDataFeatures<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DiaMapaDeUso> param) -> {
                if (param.getValue().getDiaMapaDeUsos().isEmpty()) {
                    return null;
                }
                return new SimpleObjectProperty<>(param.getValue().getDiaMapaDeUsos().get(dia));
            });
            tcNumeroDia.sortableProperty().set(false);
            tcNumeroDia.setCellFactory((TableColumn<MesMapaDeUso<Ambiente, DiaMapaDeUso>, DiaMapaDeUso> param) -> new TableCell< MesMapaDeUso<Ambiente, DiaMapaDeUso>, DiaMapaDeUso>() {
                @Override
                protected void updateItem(DiaMapaDeUso item, boolean empty) {
                    setText("");
                    if (empty) {
                        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                    } else if (item.getEventos().isEmpty()) {
                        setBackground(new Background(new BackgroundFill(Color.rgb(57, 198, 92), CornerRadii.EMPTY, Insets.EMPTY)));
                    } else {
                        setBackground(new Background(new BackgroundFill(Color.rgb(170, 0, 4), CornerRadii.EMPTY, Insets.EMPTY)));
                        setOnMouseReleased((MouseEvent event) -> {
                            String texto = "Descrição das atividades: \n";
                            for (Object evento : item.getEventos()) {
                                if (evento instanceof Aula) {
                                    Aula aula = (Aula) evento;
                                    texto += "Aula \n"
                                            + "Turma: " + aula.getId().getTurma() + "\n"
                                            + "";
                                } else if (evento instanceof CalendarioAmbiente) {
                                    CalendarioAmbiente calendarioAmbiente = (CalendarioAmbiente) evento;
                                    texto += "Evento\n"
                                            + "Evento" + calendarioAmbiente.getId().getCalendario().getId().getEvento() + "\n"
                                            + "";
                                }
                            }
                            try {
                                popOverMapaDeUso = FxMananger.loadFXML("PopOverMapaDeUso");
                                popOverMapaDeUso.setUserData(item);
                            } catch (IOException ex) {
                                Logger.getLogger(TabelaMapaDeUso.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            popOver.setContentNode(popOverMapaDeUso);
                            popOver.show(TabelaMapaDeUso.this, event.getScreenX(), event.getScreenY());
                        });
                    }
                }
            });
            tcNomeDia.getColumns().add(tcNumeroDia);
            colunas.add(tcNomeDia);
            tcNomeMes.getColumns().add(tcNomeDia);
            inicio.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
