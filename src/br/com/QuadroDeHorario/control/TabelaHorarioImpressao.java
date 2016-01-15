/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.AulaDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioDAO;
import br.com.QuadroDeHorario.control.dao.CalendarioUsuarioDAO;
import br.com.QuadroDeHorario.control.dao.MateriaHorarioAmbienteDAO;
import br.com.QuadroDeHorario.model.MesCalendario;
import br.com.QuadroDeHorario.model.entity.Ambiente;
import br.com.QuadroDeHorario.model.entity.Aula;
import br.com.QuadroDeHorario.model.entity.Calendario;
import br.com.QuadroDeHorario.model.entity.CalendarioUsuario;
import br.com.QuadroDeHorario.model.entity.MateriaHorarioAmbiente;
import br.com.QuadroDeHorario.model.entity.Turma;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.DataHorario;
import br.com.QuadroDeHorario.model.util.Efeito;
import br.com.QuadroDeHorario.model.util.FxMananger;
import br.com.QuadroDeHorario.view.QuadroDeHorarioController;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 *
 * @author OCTI01
 *
 *
 */
public class TabelaHorarioImpressao extends TableView<MesCalendario> {

    //Nome do mês
    private TableColumn<MesCalendario, Aula> tcNomeMes;
    private ObservableList<MesCalendario> aulas = FXCollections.observableArrayList();
    private SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat nomeDia = new SimpleDateFormat("EEE");
    private SimpleDateFormat nomeMes = new SimpleDateFormat("MMMM");
    private SimpleDateFormat numeroDia = new SimpleDateFormat("dd");
    private Date inicio, fim;
    private Usuario instrutor;
    public DataHorario.Turno turno;
    private Ambiente ambiente;
    private Turma turma;
    private Turma turmaEspelho;
    private List<TableColumn<MesCalendario, Aula>> colunas;

    public TabelaHorarioImpressao(int mes, int ano, Object tipo, DataHorario.Turno turno) {
        this.getStylesheets().add("/br/com/QuadroDeHorario/view/estilo.css");
        this.getStyleClass().add("tabelaImpressao");
        this.turno = turno;
        try {
            if (tipo instanceof Usuario) {
                instrutor = (Usuario) tipo;
                turma = null;
                turmaEspelho = null;
            } else if (tipo instanceof Turma) {
                turma = (Turma) tipo;
                instrutor = null;
                if (turma.isEspelho()) {
                    turmaEspelho = turma;
                    turma = turmaEspelho.getTurmaPrincipal();
                }
            } else if (tipo instanceof Ambiente) {
                ambiente = (Ambiente) tipo;
                instrutor = null;
                turma = null;
            }
            inicio = data.parse("01/" + mes + "/" + ano);
            fim = data.parse("01/" + (mes + 1) + "/" + ano);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            tcNomeMes = new TableColumn<>(nomeMes.format(inicio) + "(" + mes + ")");
            getColumns().add(tcNomeMes);
            setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
            colunas = new ArrayList<>();
            while (calendar.getTime().before(fim)) {
                colunas.add(adicionarColuna(calendar.getTime()));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            tcNomeMes.getColumns().addListener(new ListChangeListener<TableColumn<MesCalendario, ?>>() {
                private boolean suspender;

                @Override
                public void onChanged(ListChangeListener.Change<? extends TableColumn<MesCalendario, ?>> c) {
                    c.next();
                    if (c.wasReplaced() && !suspender) {
                        suspender = true;
                        tcNomeMes.getColumns().setAll(colunas);
                        suspender = false;
                    }
                }
            });
        } catch (ParseException ex) {
            Logger.getLogger(TabelaHorarioImpressao.class.getName()).log(Level.SEVERE, null, ex);
        }
        setEditable(true);
        this.getSelectionModel().cellSelectionEnabledProperty().setValue(Boolean.TRUE);
        setMaxSize(31 * 40 + 5, 195);
        setSortPolicy(null);
        setItems(aulas);
        carregarDados();
    }

    private TableColumn<MesCalendario, Aula> adicionarColuna(Date dia) {
        TableColumn<MesCalendario, Aula> tcNome = new TableColumn<>(nomeDia.format(dia));
        TableColumn<MesCalendario, Aula> tcDia = new TableColumn<>(numeroDia.format(dia));
        tcNome.getColumns().add(tcDia);
        tcNomeMes.getColumns().add(tcNome);
        tcDia.sortableProperty().set(false);
        tcDia.setCellValueFactory(new PropertyValueFactory<>("dia" + Integer.parseInt(numeroDia.format(dia))));
        List<Calendario> calendarios = new CalendarioDAO().pegarTodosPorData(dia);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dia);
        if (!calendarios.isEmpty()) {
            String eventos = "";
            for (Calendario calendario : calendarios) {
                eventos += calendario.getId().getEvento().getNome() + "\n";
            }
            Label label = new Label(tcNome.getText());
            label.setTooltip(new Tooltip(eventos));
            Color[] todasCores = new Color[calendarios.size()];
            int i = 0;
            for (Calendario calendario : calendarios) {
                String[] cores = calendario.getId().getEvento().getColor().split("@");
                todasCores[i] = new Color(Double.parseDouble(cores[0]), Double.parseDouble(cores[1]), Double.parseDouble(cores[2]), Double.parseDouble(cores[3]));
                i++;
            }
            if (todasCores.length < 2) {
                tcNome.setStyle("-fx-background-color: " + FxMananger.toRGB(todasCores[0]) + ";");
                String hexa = Integer.toHexString((int) (todasCores[0].getRed() * 255)) + Integer.toHexString((int) (todasCores[0].getGreen() * 255)) + Integer.toHexString((int) (todasCores[0].getBlue() * 255));
                int value = Integer.valueOf(hexa, 16);
                int valorMaximo = Integer.valueOf("FFFFFF", 16);
                if (valorMaximo - value > value) {
                    label.setTextFill(Color.rgb(255, 255, 255));
                } else {
                    tcNome.getStyleClass().add("fonte-preta");
                }
            } else {
                String hexa = Integer.toHexString((int) (todasCores[1].getRed() * 255)) + Integer.toHexString((int) (todasCores[1].getGreen() * 255)) + Integer.toHexString((int) (todasCores[1].getBlue() * 255));
                int value = Integer.valueOf(hexa, 16);
                int valorMaximo = Integer.valueOf("FFFFFF", 16);
                if (valorMaximo - value > value) {
                    label.setTextFill(Color.rgb(255, 255, 255));
                } else {
                    tcNome.getStyleClass().add("fonte-preta");
                }
                tcNome.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, " + FxMananger.toRGB(todasCores[0]) + "  0% , " + FxMananger.toRGB(todasCores[1]) + " 30%," + FxMananger.toRGB(todasCores.length > 2 ? todasCores[2] : todasCores[1]) + " 100%)");
            }
            tcNome.setGraphic(label);
            tcNome.setText("");
        }
        tcDia.setCellFactory(new RenderDia(dia));
        return tcNome;
    }

    private class RenderDia implements Callback<TableColumn<MesCalendario, Aula>, TableCell<MesCalendario, Aula>> {

        private Date dia;

        public RenderDia(Date dia) {
            this.dia = dia;
        }

        @Override
        public TableCell<MesCalendario, Aula> call(TableColumn<MesCalendario, Aula> param) {
            return new TableCell<MesCalendario, Aula>() {
                @Override
                protected void updateItem(Aula item, boolean empty) {
                    setTextAlignment(TextAlignment.CENTER);
                    setAlignment(Pos.CENTER);
                    if (empty) {
                        setText("");
                    } else if (item != null) {
                        if (turno != null) {
                            setText(item.toString());
                            setTooltip(new Tooltip(item.getMateriaHorario().getMateriaTurmaInstrutorSemestre().getTurma().getDescricao()));
                        } else {
                            setText(item.toString());
                        }
                        setTextFill(Color.rgb(item.getMateriaHorario().getRed(), item.getMateriaHorario().getGreen(), item.getMateriaHorario().getBlue()));
                        List<MateriaHorarioAmbiente> materiaHorarioAmbientes = new MateriaHorarioAmbienteDAO().pegarTodosPorMateriaHorario(item.getMateriaHorario());
                        int numeroAmbiente = 0;
                        for (MateriaHorarioAmbiente materiaHorarioAmbiente : materiaHorarioAmbientes) {
                            if (materiaHorarioAmbiente.getId().getAmbiente().equals(item.getAmbiente())) {
                                numeroAmbiente = materiaHorarioAmbiente.getNumero();
                                break;
                            }
                        }
                        if (ambiente == null) {
                            switch (numeroAmbiente) {
                                case 1:
                                    setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente1, CornerRadii.EMPTY, Insets.EMPTY)));
                                    break;
                                case 2:
                                    setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente2, CornerRadii.EMPTY, Insets.EMPTY)));
                                    break;
                                case 3:
                                    setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente3, CornerRadii.EMPTY, Insets.EMPTY)));
                                    break;
                                case 4:
                                    setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente4, CornerRadii.EMPTY, Insets.EMPTY)));
                                    break;
                                case 5:
                                    setBackground(new Background(new BackgroundFill(QuadroDeHorarioController.corAmbiente5, CornerRadii.EMPTY, Insets.EMPTY)));
                                    break;
                            }
                        }
                    } else if (instrutor != null && turno != null) {
                        List<CalendarioUsuario> calendarioUsuarios = new CalendarioUsuarioDAO().pegarTodosPorUsuarioData(instrutor, dia, turno);
                        if (!calendarioUsuarios.isEmpty()) {
                            CalendarioUsuario calendarioUsuario = calendarioUsuarios.get(0);
                            String[] colors = calendarioUsuario.getId().getCalendario().getId().getEvento().getColor().split("@");
                            Color color = new Color(Double.parseDouble(colors[0]), Double.parseDouble(colors[1]), Double.parseDouble(colors[2]), Double.parseDouble(colors[3]));
                            setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                            setTextFill(Efeito.brancoOuPreto(color));
                            setText("Evento");
                            setTooltip(new Tooltip(calendarioUsuario.toString()));
                        } else {
                            setText("");
                            setOnMouseReleased(null);
                            setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                            if (param.getParentColumn().getText().equalsIgnoreCase("Sáb")) {
                                setBackground(new Background(new BackgroundFill(Color.rgb(224, 224, 224), CornerRadii.EMPTY, Insets.EMPTY)));
                            } else if (param.getParentColumn().getText().equalsIgnoreCase("Dom")) {
                                setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), CornerRadii.EMPTY, Insets.EMPTY)));
                            } else {
                                setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                            }
                        }
                    } else {
                        setText("");
                        setOnMouseReleased(null);
                        setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        if (param.getParentColumn().getText().equalsIgnoreCase("Sáb")) {
                            setBackground(new Background(new BackgroundFill(Color.rgb(224, 224, 224), CornerRadii.EMPTY, Insets.EMPTY)));
                        } else if (param.getParentColumn().getText().equalsIgnoreCase("Dom")) {
                            setBackground(new Background(new BackgroundFill(Color.rgb(204, 204, 204), CornerRadii.EMPTY, Insets.EMPTY)));
                        } else {
                            setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
                        }
                    }
                }
            };
        }

    }

    private void carregarDados() {
        aulas.clear();
        for (DataHorario.Horario horario : DataHorario.Horario.values()) {
            MesCalendario mesCalendario = new MesCalendario(horario);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(inicio);
            while (calendar.getTime().before(fim)) {
                Aula aula = null;
                if (turma != null) {
                    if (turmaEspelho != null) {
                        aula = new AulaDAO().pegarPorHorarioDiaTurmaTurno(mesCalendario.getHorario(), calendar.getTime(), turmaEspelho, turno);
                        if (aula == null) {
                            aula = new AulaDAO().pegarPorHorarioDiaTurmaTurno(mesCalendario.getHorario(), calendar.getTime(), turma, turno);
                        }
                    } else {
                        aula = new AulaDAO().pegarPorHorarioDiaTurmaTurno(mesCalendario.getHorario(), calendar.getTime(), turma, turno);
                    }
                } else if (instrutor != null) {
                    aula = new AulaDAO().pegarPorInstrutorTurnoDiaHorario(instrutor, turno, calendar.getTime(), mesCalendario.getHorario());
                } else if (ambiente != null) {
                    aula = new AulaDAO().pegarPorHorarioDiaAmbiente(mesCalendario.getHorario(), calendar.getTime(), ambiente, turno);
                }
                inserirDados(mesCalendario, calendar.getTime(), horario, aula);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            aulas.add(mesCalendario);
        }
    }

    public MesCalendario inserirDados(MesCalendario mesCalendario, Date dataAula, DataHorario.Horario horario, Aula aula) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataAula);
        switch (calendar.get(Calendar.DAY_OF_MONTH)) {
            case 1:
                mesCalendario.setDia1(aula);
                break;
            case 2:
                mesCalendario.setDia2(aula);
                break;
            case 3:
                mesCalendario.setDia3(aula);
                break;
            case 4:
                mesCalendario.setDia4(aula);
                break;
            case 5:
                mesCalendario.setDia5(aula);
                break;
            case 6:
                mesCalendario.setDia6(aula);
                break;
            case 7:
                mesCalendario.setDia7(aula);
                break;
            case 8:
                mesCalendario.setDia8(aula);
                break;
            case 9:
                mesCalendario.setDia9(aula);
                break;
            case 10:
                mesCalendario.setDia10(aula);
                break;
            case 11:
                mesCalendario.setDia11(aula);
                break;
            case 12:
                mesCalendario.setDia12(aula);
                break;
            case 13:
                mesCalendario.setDia13(aula);
                break;
            case 14:
                mesCalendario.setDia14(aula);
                break;
            case 15:
                mesCalendario.setDia15(aula);
                break;
            case 16:
                mesCalendario.setDia16(aula);
                break;
            case 17:
                mesCalendario.setDia17(aula);
                break;
            case 18:
                mesCalendario.setDia18(aula);
                break;
            case 19:
                mesCalendario.setDia19(aula);
                break;
            case 20:
                mesCalendario.setDia20(aula);
                break;
            case 21:
                mesCalendario.setDia21(aula);
                break;
            case 22:
                mesCalendario.setDia22(aula);
                break;
            case 23:
                mesCalendario.setDia23(aula);
                break;
            case 24:
                mesCalendario.setDia24(aula);
                break;
            case 25:
                mesCalendario.setDia25(aula);
                break;
            case 26:
                mesCalendario.setDia26(aula);
                break;
            case 27:
                mesCalendario.setDia27(aula);
                break;
            case 28:
                mesCalendario.setDia28(aula);
                break;
            case 29:
                mesCalendario.setDia29(aula);
                break;
            case 30:
                mesCalendario.setDia30(aula);
                break;
            case 31:
                mesCalendario.setDia31(aula);
                break;
        }
        return mesCalendario;
    }

}
