/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.GrupoDAO;
import br.com.QuadroDeHorario.control.dao.UsuarioDAO;
import br.com.QuadroDeHorario.model.DiaMapaDeUsoInstrutor;
import br.com.QuadroDeHorario.model.MesMapaDeUso;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.DataHorario.Turno;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static javafx.scene.control.TableView.CONSTRAINED_RESIZE_POLICY;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import org.controlsfx.control.PopOver;

/**
 *
 * @author OCTI01
 */
public class TabelaMapaDeUsoInstrutor extends TableView<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>> {

    private Date inicio;
    private Date fim;
    private PopOver popOver;
    private SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
    private SimpleDateFormat sdfNomeMes = new SimpleDateFormat("MMMM");
    private SimpleDateFormat sdfNomeDia = new SimpleDateFormat("EEE");
    private SimpleDateFormat sdfDia = new SimpleDateFormat("dd");

    private TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, DiaMapaDeUsoInstrutor> tcNomeMes;
    private TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, Turno> tcTurno;
    private List<TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, ?>> colunas;
    private ObservableList<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>> mesMapaDeUsos = FXCollections.observableArrayList();

    public TabelaMapaDeUsoInstrutor(int ano, int mes) {
        this.getStylesheets().add("/br/com/QuadroDeHorario/view/estilo.css");
        popOver = new PopOver();
        popOver.setTitle("Atividades");
        popOver.setAutoHide(true);
        setPrefSize(USE_PREF_SIZE, 145d);
        try {
            inicio = sdfData.parse("01/" + mes + "/" + ano);
            Calendar fim = Calendar.getInstance();
            fim.setTime(inicio);
            fim.add(Calendar.MONTH, 1);
            this.fim = fim.getTime();
        } catch (ParseException ex) {
            Logger.getLogger(TabelaMapaDeUso.class.getName()).log(Level.SEVERE, null, ex);
        }
        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setEditable(false);
        carregarColunas();
        tcNomeMes.getColumns().addListener(new ListChangeListener<TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, ?>>() {

            private boolean permuted;

            @Override
            public void onChanged(ListChangeListener.Change<? extends TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, ?>> c) {
                c.next();
                if (c.wasReplaced() && !permuted) {
                    permuted = true;
                    tcNomeMes.getColumns().setAll(colunas);
                    permuted = false;
                }
            }
        });
        setItems(mesMapaDeUsos);
        getSelectionModel().setCellSelectionEnabled(true);
        carregarDados();
    }

    private void carregarColunas() {
        tcNomeMes = new TableColumn<>(sdfNomeMes.format(inicio) + " (" + sdfMes.format(inicio) + ")");
        tcTurno = new TableColumn<>("Turno");
        tcNomeMes.getColumns().add(tcTurno);
        tcTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));
        tcTurno.setCellFactory((TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, Turno> param) -> new TableCell<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, Turno>() {
            @Override
            protected void updateItem(Turno item, boolean empty) {
                setTextFill(Color.WHITE);
                setBackground(new Background(new BackgroundFill(Color.rgb(130, 151, 178), CornerRadii.EMPTY, Insets.EMPTY)));
                if (empty) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        getColumns().setAll(tcNomeMes);
        colunas = new ArrayList<>();
        colunas.add(tcTurno);
        Calendar inicio = Calendar.getInstance();
        inicio.setTime(this.inicio);
        while (inicio.getTime().before(fim)) {
            TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, DiaMapaDeUsoInstrutor> tcNomeDia = new TableColumn<>(sdfNomeDia.format(inicio.getTime()));
            final int dia = inicio.get(Calendar.DAY_OF_MONTH) - 1;
            TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, DiaMapaDeUsoInstrutor> tcDia = new TableColumn<>(sdfDia.format(inicio.getTime()));
            tcDia.setCellValueFactory((TableColumn.CellDataFeatures<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, DiaMapaDeUsoInstrutor> param) -> {
                return new SimpleObjectProperty<>(param.getValue().getDiaMapaDeUsos().get(dia));
            });
            tcDia.setCellFactory((TableColumn<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, DiaMapaDeUsoInstrutor> param) -> new TableCell<MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor>, DiaMapaDeUsoInstrutor>() {
                @Override
                protected void updateItem(DiaMapaDeUsoInstrutor item, boolean empty) {
                    setAlignment(Pos.CENTER);
                    if (empty) {
                        setText("");
                    } else {
                        setText(String.valueOf(item.getUsuario().size()));
                        if (item.getUsuario().isEmpty()) {
                            setBackground(new Background(new BackgroundFill(Color.rgb(170, 0, 4), CornerRadii.EMPTY, Insets.EMPTY)));
                        } else {
                            setBackground(new Background(new BackgroundFill(Color.rgb(57, 198, 92), CornerRadii.EMPTY, Insets.EMPTY)));
                            setOnMouseReleased((MouseEvent event) -> {
                                try {
                                    Parent parent = FxMananger.loadFXML("PopOverMapaDeUso");
                                    parent.setUserData(item);
                                    popOver.setContentNode(parent);
                                    popOver.show(TabelaMapaDeUsoInstrutor.this, event.getScreenX(), event.getScreenY());
                                } catch (IOException ex) {
                                    Logger.getLogger(TabelaMapaDeUsoInstrutor.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                        }
                    }
                }
            });
            tcDia.sortableProperty().set(false);
            tcNomeMes.getColumns().add(tcNomeDia);
            tcNomeDia.getColumns().add(tcDia);
            colunas.add(tcNomeDia);
            inicio.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    private void carregarDados() {
        new Thread(() -> {
            mesMapaDeUsos.clear();
            for (DataHorario.Turno turno : DataHorario.Turno.values()) {
                if (turno != DataHorario.Turno.DIURNO) {
                    MesMapaDeUso<Usuario, DiaMapaDeUsoInstrutor> mapaDeUso = new MesMapaDeUso<>();
                    mapaDeUso.setTurno(turno);
                    Calendar inicio = Calendar.getInstance();
                    inicio.setTime(this.inicio);
                    while (inicio.getTime().before(fim)) {
                        DiaMapaDeUsoInstrutor diaMapaDeUsoInstrutor = new DiaMapaDeUsoInstrutor();
                        diaMapaDeUsoInstrutor.setData(inicio.getTime());
                        List<Usuario> usuarios = new UsuarioDAO().pegarPorGrupoTurno(new GrupoDAO().pegarGrupo("Instrutor"), turno);
                        usuarios.removeIf((Usuario t) -> {
                            return (!new AulaDAO().pegarPorDiaInstrutor(t, inicio.getTime(), turno).isEmpty()) || (!new CalendarioUsuarioDAO().pegarTodosPorUsuarioData(t, inicio.getTime(), turno).isEmpty());
                        });
                        diaMapaDeUsoInstrutor.setUsuario(usuarios);
                        mapaDeUso.getDiaMapaDeUsos().add(diaMapaDeUsoInstrutor);
                        inicio.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    mesMapaDeUsos.add(mapaDeUso);
                }
            }
        }, "Carregando dados mapa de uso Instrutor").start();
    }
}
